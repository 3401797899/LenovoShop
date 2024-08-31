package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.util.List;

@Data
@TableName("products")
public class Product {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String productId;


    private String name;

    private String brief;

    private String picUrl;

    private Integer categoryId;

    // 产品配置列表，通过多对多关系映射
    //@TableField(exist=false)
    //private List<ProductConfig> configs;

}