package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

@Data
@TableName("categories")
public class Category {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;
}