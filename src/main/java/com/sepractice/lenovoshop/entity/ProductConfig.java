package com.sepractice.lenovoshop.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "configs", autoResultMap = true)
public class ProductConfig {
    @TableId(value = "id", type = IdType.AUTO)
    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private Integer productId;

    private String name;

    private String brief;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Object> value;

    private Integer price;

    private Long productCode;

}