package com.sepractice.lenovoshop.controller;

import com.sepractice.lenovoshop.entity.User;
import com.sepractice.lenovoshop.mapper.UserMapper;
import com.sepractice.lenovoshop.service.UserService;
import com.sepractice.lenovoshop.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

    @PostMapping(value = "/login/mail")
    public Result loginByMail(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        String code = params.get("code");

        if (!userService.isUserExistsByEmail(email)) {
            return Result.error("用户不存在");
        }
        User user = userService.findUserByEmail(email);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String redis_code = operations.get("login:mail:" + email);
        if (redis_code == null ) {
            return Result.error("请先获取验证码");
        }
        if(!redis_code.equals(code)){
            return Result.error("验证码错误");
        }
        stringRedisTemplate.delete("login:mail:" + email);
        String token = JwtUtil.getToken(user.getId().toString());
        return Result.success(token);
    }

    @PostMapping(value = "/login/mail/code")
    public Result loginSendMail(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        if(!Validate.validate_email(email).equals("ok")) {
            return Result.error(Validate.validate_email(email));
        }
        String code = RandomString.generateCode(6);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String redis_code = operations.get("login:mail:" + email);
        if (redis_code != null) {
            return Result.error("请勿频繁发送验证码");
        }
        operations.set("login:mail:" + email, code, 1, java.util.concurrent.TimeUnit.MINUTES);
        emailService.sendEmail(email, "LenovoShop登录验证码", "【LenovoShop】验证码为：" + code + "，5分钟内有效，为了保证您的账户安全，请勿泄露给他人。");
        return Result.success();
    }

    @PostMapping(value = "/register")
//    public Result register(@Email(message = "邮箱输入不正确") String email, @Size(min= 6,max = 20, message = "密码长度应该在6-20之间") String password) {
//    使用校验会让interceptor中的exclude失效，太逆天了
    public Result register(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        String password = params.get("password");
        String code = params.get("code");

        if (userService.isUserExistsByEmail(email)) {
            return Result.error("用户已存在");
        }
        if(!Validate.validate_pwd(password).equals("ok")){
            return Result.error(Validate.validate_pwd(password));
        }
        if(!Validate.validate_email(email).equals("ok")){
            return Result.error(Validate.validate_email(email));
        }
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String redis_code = operations.get("register:mail:" + email);
        if (redis_code == null ) {
            return Result.error("请先获取验证码");
        }
        if(!redis_code.equals(code)){
            return Result.error("验证码错误");
        }
        userService.register(email, password);
        stringRedisTemplate.delete("register:mail:" + email);
        return Result.success();
    }

    @PostMapping(value = "/register/mail/code")
    public Result registerSendMail(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        if (userService.isUserExistsByEmail(email)) {
            return Result.error("该邮箱已注册");
        }
        if(!Validate.validate_email(email).equals("ok")) {
            return Result.error(Validate.validate_email(email));
        }
        String code = RandomString.generateCode(6);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String redis_code = operations.get("register:mail:" + email);
        if (redis_code != null ) {
            return Result.error("请勿频繁发送验证码");
        }
        operations.set("register:mail:" + email, code, 1, java.util.concurrent.TimeUnit.MINUTES);
        emailService.sendEmail(email, "LenovoShop注册验证码", "【LenovoShop】验证码为：" + code + "，5分钟内有效，为了保证您的账户安全，请勿泄露给他人。");
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
        };

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
