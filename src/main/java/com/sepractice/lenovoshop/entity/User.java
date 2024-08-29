package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("users")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(min= 5,max = 20, message = "昵称长度应在5-20之间")
    private String nickname;

    private String avatar;

    // TODO: 改成枚举类型校验
    @Min(0)
    @Max(2)
    private Integer gender;

    @JsonIgnore
    @Size(min = 6, max = 20, message = "密码长度应在6-20之间")
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer balance;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdTime;

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