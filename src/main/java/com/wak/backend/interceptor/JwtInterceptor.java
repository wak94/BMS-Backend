package com.wak.backend.interceptor;

import com.wak.backend.annotation.NoAuth;
import com.wak.backend.entity.dto.LoginUserInfo;
import com.wak.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置response响应数据类型为json和编码为utf-8
        response.setContentType("application/json;charset=utf-8");
        // 判断对象是否是映射到一个方法，如果不是则直接通过
        if (!(handler instanceof HandlerMethod)) {
            // instanceof运算符是用来在运行时指出对象是否是特定类的一个实例
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;


        Method method = handlerMethod.getMethod();
        //检查方法是否有NoAuth注解，有则跳过认证
        if (method.isAnnotationPresent(NoAuth.class)) {
            return true;
        }
        // 从HTTP请求头中获取token信息
        String token = request.getHeader("token");
        //判断Authorization是否为空
        if (StringUtils.isEmpty(token)) {
            log.error("header中不存在token");
            throw new RuntimeException("请先登录");
        }
        log.info(token);

        // HTTP请求头中TOKEN解析出的用户信息
        Claims claims = jwtUtil.extractClaims(token);
        LoginUserInfo user = claims.get("user", LoginUserInfo.class);
        if (user == null) {
            log.error("token无效");
            throw new RuntimeException("登录信息失效，请重新登录");
        }
        log.info(user.toString());
        //校验是否过期
        if (jwtUtil.isTokenExpired(token)) {
            log.error("token过期");
            throw new RuntimeException("登录信息过期，请重新登录");
        }

        //token正常，获取用户信息
        String username = user.getUsername();
        Long id = user.getId();
//        String roleId = claims.get("roleId", String.class);

        //将用户信息存入MDC，以便后面处理请求使用
        MDC.put("username", username);
        MDC.put("userId", String.valueOf(id));
//        MDC.put("roleId", roleId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}

