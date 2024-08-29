package com.sepractice.lenovoshop.controller;

import com.sepractice.lenovoshop.entity.User;
import com.sepractice.lenovoshop.mapper.UserMapper;
import com.sepractice.lenovoshop.service.UserService;
import com.sepractice.lenovoshop.utils.JwtUtil;
import com.sepractice.lenovoshop.utils.Result;
import com.sepractice.lenovoshop.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "/login")
    public Result login(String email, String password) {
        if (!userService.isUserExistsByEmail(email)) {
            return Result.error("用户已存在");
        }
        User user = userService.findUserByEmail(email);
        if (!user.getPassword().equals(password)) {
            return Result.error("密码错误");
        }
        String token = JwtUtil.getToken(user.getId().toString());
        return Result.success(token);
    }

    @PostMapping(value = "/register")
    public Result register(String email, String password) {
        if (userService.isUserExistsByEmail(email)) {
            return Result.error("用户已存在");
        }
        userService.register(email, password);
        return Result.success();
    }

    @GetMapping(value = "/info")
    public Result info() {
        String userId = ThreadLocalUtil.get();
        User user = userMapper.selectById(userId);
        return Result.success(user);
    }

    @PostMapping(value = "/update")
    public Result update(@RequestBody @Validated User user) {
        String userId = ThreadLocalUtil.get();
        if (!userId.equals(user.getId().toString())) {
            return Result.error("无权限");
        }
        userService.updateUser(user);
        return Result.success();
    }
}
