package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepractice.lenovoshop.entity.Order;
import com.sepractice.lenovoshop.entity.Product;
import com.sepractice.lenovoshop.entity.ProductConfig;
import com.sepractice.lenovoshop.entity.User;
import com.sepractice.lenovoshop.mapper.CategoryMapper;
import com.sepractice.lenovoshop.mapper.OrderMapper;
import com.sepractice.lenovoshop.mapper.ProductConfigMapper;
import com.sepractice.lenovoshop.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductService extends ServiceImpl<ProductMapper, Product>  {
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
        // 获取 ProductConfig 列表
        QueryWrapper<ProductConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        List<ProductConfig> configList = configMapper.selectList(queryWrapper);

        // 为每个 ProductConfig 查询对应的 Product，获取 picUrl 并设置
        for (ProductConfig config : configList) {
            Product product = productMapper.selectById(config.getProductId());
            if (product != null) {
                config.setPicUrl(product.getPicUrl());
            }
        }
        return configList;
    }

    public Long getIdByCodeInConfig(Long productCode) {
        QueryWrapper<ProductConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_code", productCode);
        ProductConfig config = configMapper.selectOne(queryWrapper);
        return config != null ? config.getProductId().longValue() : null;
    }

    public Integer getIdByCodeInProduct(String productId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        Product product = productMapper.selectOne(queryWrapper);
        return product != null ? product.getId() : null;
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

    public Product getProductById(Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return productMapper.selectOne(queryWrapper);
    }

    public IPage<Product> getProductsByCondition(String productName, Integer page, Integer limit) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (productName != null) {
            queryWrapper.like(productName != null, "productName", productName);
        }
        Page<Product> rowPage = new Page(page, limit);
        LambdaQueryWrapper<Product> lambdaQueryWrapper = queryWrapper.lambda();
        rowPage = productMapper.selectPage(rowPage, lambdaQueryWrapper);
        return rowPage;
    }

    public Product createProduct(Map<String, String> param) {
        Product product = new Product();
        product.setProductId(param.get("productId"));
        product.setName(param.get("name"));
        product.setBrief(param.get("brief"));
        product.setPrice(Integer.parseInt(param.get("price")));
        product.setCategoryId(Integer.parseInt(param.get("categoryId")));

        this.save(product);
        return product;
    }

    @Transactional
    public boolean deleteProduct(String productId) {
        Integer id = this.getIdByCodeInProduct(productId);
        if (configMapper.countByProductId(productId) > 0) {
            return false;
        }
        return productMapper.deleteById(id) > 0;
    }

}
