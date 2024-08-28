package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_configs")
public class ProductConfigAssociation {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("product_id")
    private Long productId;

    @TableField("config_id")
    private Long configId;
}