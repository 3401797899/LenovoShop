package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepractice.lenovoshop.entity.Category;
import com.sepractice.lenovoshop.entity.Product;
import com.sepractice.lenovoshop.mapper.CategoryMapper;
import com.sepractice.lenovoshop.mapper.ProductConfigAssociationMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductConfigAssociationMapper associationMapper;


}

