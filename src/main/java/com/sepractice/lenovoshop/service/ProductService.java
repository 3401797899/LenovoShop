package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepractice.lenovoshop.entity.Product;
import com.sepractice.lenovoshop.entity.ProductConfigAssociation;
import com.sepractice.lenovoshop.mapper.CategoryMapper;
import com.sepractice.lenovoshop.mapper.ProductConfigAssociationMapper;
import com.sepractice.lenovoshop.mapper.ProductConfigMapper;
import com.sepractice.lenovoshop.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductConfigMapper productConfigMapper;
    @Autowired
    private ProductConfigAssociationMapper associationMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Product> getProductsInCategory(Long categoryId) {
        // 创建 QueryWrapper 来查询 productId 与 categoryId 的映射
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        return productMapper.selectList(queryWrapper);
    }
}
