package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepractice.lenovoshop.entity.Product;
import com.sepractice.lenovoshop.entity.ProductConfig;
import com.sepractice.lenovoshop.mapper.CategoryMapper;
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
    private ProductConfigMapper configMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Product> getProductsInCategory(Long categoryId) {
        // 创建 QueryWrapper 来查询 productId 与 categoryId 的映射
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

        // 使用 selectOne 方法查找符合条件的单个记录
        ProductConfig config = configMapper.selectOne(queryWrapper);

        // 返回找到的 config_id，如果没有找到则返回 null
        return config.getProductId().longValue();
    }

    public String getConfigByCode(Long productCode) {
        QueryWrapper<ProductConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_code", productCode);
        return configMapper.selectOne(queryWrapper).getValue();
    }

    public String get
}
