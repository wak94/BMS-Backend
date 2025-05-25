package com.wak.backend.mapper;


import com.wak.backend.entity.User;
import com.wak.backend.entity.dto.QueryRequest;
import com.wak.backend.entity.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectByUsername(@Param("username") String username);

    List<UserVO> selectUser(@Param("username") String username, @Param("phone") String phone);

    List<UserVO> selectAllUser();

    int insert(User user);

    int update(User user);

    int deleteById(Long id);
}
