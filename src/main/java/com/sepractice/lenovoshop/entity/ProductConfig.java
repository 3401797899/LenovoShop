package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

@Data
@TableName("configs")
public class ProductConfig {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer productId;

    private String name;

    private String brief;

    private String value;

    private Integer price;

    private Long productCode;
}