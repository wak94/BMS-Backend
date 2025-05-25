package com.wak.backend.util;

import com.wak.backend.entity.dto.LoginUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {
    private String secret;
    private long expiration;
    private String header;
    private String tokenPrefix;

    /**
     * 获取原始令牌
     * remove 'Bearer ' string
     *
     * @param authorizationHeader 授权头
     */
    public String getRawToken(String authorizationHeader) {
        return authorizationHeader.substring(tokenPrefix.length());
    }

    /**
     * 获取授权头
     * add 'Bearer ' string
     *
     * @param rawToken 原始令牌
     */
    public String getTokenHeader(String rawToken) {
        return tokenPrefix + rawToken;
    }

    /**
     * 校验授权头是否合法
     *
     * @param authorizationHeader 授权头
     */
    public boolean validate(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(tokenPrefix);
    }

    /**
     * 获取授权头前缀
     *
     * @return 授权头前缀
     */
    public String getAuthorizationHeaderPrefix() {
        return header;
    }

    /**
     * 生成 token
     *
     * @param user 用户 信息
     * @return 生成的 token
     */
    public String generateToken(LoginUserInfo user) {
        // 创建密钥
        final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        //header参数
        final Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");

        // payload参数
        Map<String, LoginUserInfo> payload = new HashMap<>();
        payload.put("user", user);

        // 生成token
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payload)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token token
     */
    public Claims extractClaims(String token) {
        // 创建密钥
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // 解析token
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从token中获取用户信息
     *
     * @param token token
     */
    public LoginUserInfo extractUserinfo(String token) {
        // 解析token
        Claims claims = extractClaims(token);

        // 获取用户信息
        return claims.get("user", LoginUserInfo.class);
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     */
    public boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        LoginUserInfo user = extractUserinfo(token);
        return (user.getUsername().equals(username) && !isTokenExpired(token));
    }


}
