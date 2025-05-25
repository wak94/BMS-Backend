package com.wak.backend.controller;

import com.github.pagehelper.PageInfo;
import com.wak.backend.entity.dto.LoginRequest;
import com.wak.backend.entity.dto.QueryRequest;
import com.wak.backend.entity.vo.UserVO;
import com.wak.backend.response.Result;
import com.wak.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String,String>> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/User/list")
    public Result<PageInfo<UserVO>> userList(QueryRequest request) {
        return userService.userList(request);
    }
}
