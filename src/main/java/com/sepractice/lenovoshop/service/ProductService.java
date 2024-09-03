package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepractice.lenovoshop.entity.Product;
import com.sepractice.lenovoshop.entity.ProductConfig;
import com.sepractice.lenovoshop.mapper.CategoryMapper;
import com.sepractice.lenovoshop.mapper.ProductConfigMapper;
import com.sepractice.lenovoshop.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductConfigMapper configMapper;  // Remove static

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Product> getProductsInCategory(Long categoryId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        return productMapper.selectList(queryWrapper);
    }

    public List<ProductConfig> getConfigsFromProduct(Long productId) {
        QueryWrapper<ProductConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        return configMapper.selectList(queryWrapper);
    }

    public Long getIdByCode(Long productCode) {
        QueryWrapper<ProductConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_code", productCode);
        ProductConfig config = configMapper.selectOne(queryWrapper);
        return config != null ? config.getProductId().longValue() : null;
    }

    public ProductConfig getConfigByCode(Long productCode) {
        QueryWrapper<ProductConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_code", productCode);
        return configMapper.selectOne(queryWrapper);
    }

    public List<Product> findConfigsByString(String key) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", key);  // Using "like" for fuzzy search
        return productMapper.selectList(queryWrapper);  // Directly return the result
    }
}
