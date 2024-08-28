package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("orders_products")
public class OrderProduct {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("order_id")
    private Long orderId;

    @TableField("productcount_id")
    private Long productCountId;
}