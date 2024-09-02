package com.sepractice.lenovoshop.utils;

public class Validate {
    public static String validate_pwd(String pwd){
        // 校验密码
        if (pwd.length() < 6 || pwd.length() > 20) {
            return "密码长度应在6-20之间";
        }
        return "ok";
    }

    public static String validate_email(String email){
        // 校验邮箱
        if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            return "邮箱格式不正确";
        }
        return "ok";
    }
}
