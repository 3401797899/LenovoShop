package com.sepractice.lenovoshop.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sepractice.lenovoshop.entity.User;
import com.sepractice.lenovoshop.mapper.UserMapper;
import com.sepractice.lenovoshop.service.UserService;
import com.sepractice.lenovoshop.utils.JwtUtil;
import com.sepractice.lenovoshop.utils.Result;
import com.sepractice.lenovoshop.utils.ThreadLocalUtil;
import com.sepractice.lenovoshop.utils.Validate;
import jakarta.validation.Path;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    private static String ROOT_PATH = System.getProperty("user.dir");

    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String, String> params) {
        // JSON传输数据的获取方式
        String email = params.get("email");
        String password = params.get("password");

        if (!userService.isUserExistsByEmail(email)) {
            return Result.error("用户不存在");
        }
        User user = userService.findUserByEmail(email);
        if (!user.getPassword().equals(password)) {
            return Result.error("密码错误");
        }
        String token = JwtUtil.getToken(user.getId().toString());
        return Result.success(token);
    }

    @PostMapping(value = "/register")
//    public Result register(@Email(message = "邮箱输入不正确") String email, @Size(min= 6,max = 20, message = "密码长度应该在6-20之间") String password) {
//    使用校验会让interceptor中的exclude失效，太逆天了
    public Result register(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        String password = params.get("password");

        if (userService.isUserExistsByEmail(email)) {
            return Result.error("用户已存在");
        }
        if(!Validate.validate_pwd(password).equals("ok")){
            return Result.error(Validate.validate_pwd(password));
        }
        if(!Validate.validate_email(email).equals("ok")){
            return Result.error(Validate.validate_email(email));
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
    public Result update(@RequestBody @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 获取第一个错误并返回
            String errorMessage = bindingResult.getAllErrors().stream()
                    .findFirst()
                    .map(error -> error.getDefaultMessage())
                    .orElse("未知错误");

            return Result.error(errorMessage);
        }

        String userId = ThreadLocalUtil.get();
        if (!userId.equals(user.getId().toString())) {
            return Result.error("无权限修改别人的信息");
        }
        userService.updateUser(user);
        return Result.success();
    }

    @PostMapping(value = "/password")
    public Result update_password(@RequestBody Map<String, String> params) {
        String oldpwd = params.get("old");
        String newpwd = params.get("new");
        String userId = ThreadLocalUtil.get();
        User user = userMapper.selectById(userId);
        if (!user.getPassword().equals(oldpwd)) {
            return Result.error("密码错误");
        }
        // 校验密码
        if(!Validate.validate_pwd(newpwd).equals("ok")){
            return Result.error(Validate.validate_pwd(newpwd));
        }
        user.setPassword(newpwd);
        userMapper.updateById(user);
        return Result.success();
    }

    @PostMapping(value = "/avatar")
    public Result update_avatar(@RequestParam("avatar") MultipartFile avatar) {
        String filename = avatar.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID() + suffixName;
        File dest = new File(ROOT_PATH, "media/" + filename);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            avatar.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
        userService.updateAvatar(ThreadLocalUtil.get(), "/media/" + filename);
        return Result.success();
    }
}
