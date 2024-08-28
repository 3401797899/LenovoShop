package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String email;

    private String nickname;

    private String avatar;

    private Integer gender;

    private String password;

    private Integer balance;

    private Date createdTime;

    // 枚举类型用于性别
    public enum Gender {
        SECRET(0, "保密"), MALE(1, "男"), FEMALE(2, "女");

        private int code;
        private String description;

        Gender(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}