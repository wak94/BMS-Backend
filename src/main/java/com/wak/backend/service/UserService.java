package com.wak.backend.service;

import com.github.pagehelper.PageInfo;
import com.wak.backend.entity.dto.LoginRequest;
import com.wak.backend.entity.dto.QueryRequest;
import com.wak.backend.entity.vo.UserVO;
import com.wak.backend.response.Result;

import java.util.Map;

public interface UserService {
    Result<Map<String, String>> login(LoginRequest request);

    Result<PageInfo<UserVO>> userList(QueryRequest request);

    Result<Boolean> userSave(UserVO user);
}
