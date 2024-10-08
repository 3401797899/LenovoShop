package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

@Data
@TableName("product_counts")
public class ProductCount {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String productCode;

    private Integer price;

    private Integer count;
}