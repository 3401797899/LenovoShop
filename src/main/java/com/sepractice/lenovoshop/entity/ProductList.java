package com.sepractice.lenovoshop.entity;


import lombok.Data;

@Data
public class ProductList {

    private String name;

    private String brief;

    private Integer count;

    private String picUrl;

    private String productCode;

    private Integer price;
}
