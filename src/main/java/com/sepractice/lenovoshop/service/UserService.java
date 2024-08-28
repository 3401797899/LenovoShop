package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepractice.lenovoshop.entity.User;
import com.sepractice.lenovoshop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean findUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    public void register(String username, String password) {
        User u = new User();
        u.setEmail(username);
        u.setPassword(password);
        userMapper.insert(u);
    }
}
