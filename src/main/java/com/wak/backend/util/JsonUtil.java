package com.wak.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wak.backend.entity.dto.LoginUserInfo;

/**
 * 将 JSON 字符串和 Java 对象相互转换
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转 JSON 字符串
     * eg:
     * User user = new User("Tom", 25);
     * String json = JsonUtils.toJson(user);
     * System.out.println(json); // {"name":"Tom","age":25}
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转JSON失败", e);
        }
    }

    /**
     * JSON 字符串转对象
     * eg:
     * String json = "{\"name\":\"Tom\",\"age\":25}";
     * User user = JsonUtils.fromJson(json, User.class);
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON转对象失败", e);
        }
    }

    /**
     * JSON 转复杂类型对象（如 List、Map）
     * eg:
     * String json = "[{\"name\":\"Tom\",\"age\":25},{\"name\":\"Jerry\",\"age\":22}]";
     * List<User> list = JsonUtils.fromJson(json, new TypeReference<List<User>>() {});
     */
    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        try {
            return mapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("JSON转复杂类型对象失败", e);
        }
    }

    public static void main(String[] args) {
        LoginUserInfo info = new LoginUserInfo();
        info.setUsername("wak");
        info.setId(1L);
        String json = JsonUtil.toJson(info);
        System.out.println(json);
    }

}
