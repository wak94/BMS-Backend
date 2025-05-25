package com.wak.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wak.backend.entity.User;
import com.wak.backend.entity.dto.LoginRequest;
import com.wak.backend.entity.dto.LoginUserInfo;
import com.wak.backend.entity.dto.QueryRequest;
import com.wak.backend.entity.vo.UserVO;
import com.wak.backend.mapper.UserMapper;
import com.wak.backend.response.Result;
import com.wak.backend.service.UserService;
import com.wak.backend.util.JsonUtil;
import com.wak.backend.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public Result<Map<String, String>> login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (StringUtils.isAnyBlank(username, password)) {
            throw new IllegalArgumentException("用户名或密码不能为空");
        }

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUserInfo info = new LoginUserInfo();
        info.setUsername(username);
        info.setId(user.getId());
        String token = jwtUtil.generateToken(info);
        String userdata = JsonUtil.toJson(info);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("user", userdata);
        return Result.success(map);
    }

    @Override
    public Result<PageInfo<UserVO>> userList(QueryRequest request) {
        if (request.getCurrentPage() < 0) {
            request.setCurrentPage(0);
        }
        if (request.getPageSize() < 0) {
            request.setPageSize(10);
        }
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        if (request.getUsername() == null && request.getPhone() == null) {
            List<UserVO> users = userMapper.selectAllUser();
            return Result.success(new PageInfo<>(users));
        }
        List<UserVO> users = userMapper.selectUser(request.getUsername(), request.getPhone());
        return Result.success(new PageInfo<>(users));
    }
}
