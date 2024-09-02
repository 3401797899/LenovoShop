package com.sepractice.lenovoshop.controller;

import com.sepractice.lenovoshop.entity.Category;
import com.sepractice.lenovoshop.entity.Product;
import com.sepractice.lenovoshop.mapper.CategoryMapper;
import com.sepractice.lenovoshop.service.ProductService;
import com.sepractice.lenovoshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
public class HomepageController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryMapper categoryMapper; // 用于获取所有的 categoryId

    @GetMapping(value = "/")
    public Result show() {
        // 获取所有的 categoryId
        List<Long> categoryIds = categoryMapper.selectList(null)  // 假设你的 CategoryMapper 有一个 selectList 方法
                .stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());

        // 创建一个 List 存储每个 categoryId 对应的 Product 列表
//        Map<Category, List<Product>> result = new HashMap<>();

        List<Map<String,Object>> result = new ArrayList<>();
        // 遍历所有的 categoryId，并获取对应的 Product 列表
        for (Long categoryId : categoryIds) {
            Category category = categoryMapper.selectById(categoryId);
            Map<String, Object> category_info = new HashMap<>();
            category_info.put("id", category.getId());
            category_info.put("name", category.getName());
            category_info.put("imgUrl", category.getImgUrl());
            List<Product> products = productService.getProductsInCategory(categoryId);
            category_info.put("products", products);
            result.add(category_info);
        }

        return Result.success(result);
    }
}