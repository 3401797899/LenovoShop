package com.sepractice.lenovoshop.service;

import com.sepractice.lenovoshop.entity.ProductCount;
import com.sepractice.lenovoshop.mapper.CategoryMapper;
import com.sepractice.lenovoshop.mapper.ProductCountMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ProductCountService extends ServiceImpl<ProductCountMapper, ProductCount> {
    @Autowired
    private ProductCountMapper productCountMapper;


}
