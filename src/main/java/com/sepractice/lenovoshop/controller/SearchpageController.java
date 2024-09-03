package com.sepractice.lenovoshop.controller;

import com.sepractice.lenovoshop.entity.ProductConfig;
import com.sepractice.lenovoshop.entity.Product;
import com.sepractice.lenovoshop.mapper.ProductConfigMapper;
import com.sepractice.lenovoshop.mapper.ProductMapper;
import com.sepractice.lenovoshop.service.ProductService;
import com.sepractice.lenovoshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;  // 导入Comparator
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchpageController {

    @Autowired
    private ProductConfigMapper configMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;


    @GetMapping(value = "/")
    public Result searchPage(String pattern,
                             Long limit,
                             Long currentPage,
                             @RequestParam(required = false) Integer categoryId,
                             @RequestParam(defaultValue = "0") Integer sorter) {
        List<Product> result = productService.findConfigsByString(pattern);  // 调用实例方法

        if (categoryId != null) {
            result = result.stream()
                    .filter(product -> categoryId.equals(product.getCategoryId()))  // 假设Product类有getCategoryId()方法
                    .collect(Collectors.toList());
        }

        if(sorter == 1) {
            result = result.stream()
                    .sorted(Comparator.comparing(Product::getPrice))  // 假设ProductConfig有getPrice()方法
                    .collect(Collectors.toList());
        }
        else if(sorter == 2) {
            result = result.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())  // 假设ProductConfig有getPrice()方法
                    .collect(Collectors.toList());
        }
        // 计算分页信息
        int totalItems = result.size();
        int pageSize = limit.intValue();
        int currentPageIndex = currentPage.intValue();

        // 计算起始和结束索引
        int startIndex = (currentPageIndex - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);

        // 获取当前页的数据
        List<Product> pagedResult = result.subList(startIndex, endIndex);

        // 计算总页数
        int pageSum = (int) Math.ceil((double) totalItems / pageSize);

        Map<String, Object> finalResult = new HashMap<>();
        finalResult.put("totalItems", totalItems);
        finalResult.put("pageSum", pageSum);
        finalResult.put("pageResult", pagedResult);
        
        // 返回分页结果
        return Result.success(finalResult);
    }

}
