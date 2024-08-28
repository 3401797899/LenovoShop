package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("addresses")
public class Address {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String name;

    private String phone;

    private String dz;
}