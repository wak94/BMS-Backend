package com.wak.backend.mapper;


import com.wak.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectByUsername(@Param("username") String username);

    int insert(User user);

    int update(User user);

    int deleteById(Long id);
}
