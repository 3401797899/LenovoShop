package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepractice.lenovoshop.entity.User;
import com.sepractice.lenovoshop.mapper.UserMapper;
import com.sepractice.lenovoshop.utils.RandomString;
import com.sepractice.lenovoshop.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean isUserExistsByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    public User findUserByEmail(String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectOne(queryWrapper);
    }

    public void register(String username, String password) {
        User u = new User();
        u.setEmail(username);
        // TODO: 加密存储
        u.setPassword(password);
        u.setNickname("lenovo用户" + RandomString.generateRandomString(10));
        u.setAvatar("/media/avatar.jpg"); // 默认头像
        u.setGender(0);
        u.setBalance((int) 1e9);
        u.setCreatedTime(LocalDateTime.now());
        userMapper.insert(u);
    }

    @Transactional
    public void updateUser(User request) {
        String userId = ThreadLocalUtil.get();

        User existingUser = userMapper.selectById(Integer.valueOf(userId));

        // 只更新非空字段
        if (request.getNickname() != null) {
            existingUser.setNickname(request.getNickname());
        }
        if (request.getGender() != null) {
            existingUser.setGender(request.getGender());
        }

        userMapper.updateById(existingUser);
    }


    public boolean updateAvatar(String userId, String avatar){
        User user = new User();
        user.setId(Integer.valueOf(userId));
        user.setAvatar(avatar);
        return userMapper.updateById(user) > 0;
    }
}
