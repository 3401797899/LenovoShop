package com.sepractice.lenovoshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.sepractice.lenovoshop.entity.ProductConfig;
import com.sepractice.lenovoshop.mapper.ProductConfigMapper;
import com.sepractice.lenovoshop.mapper.ProductMapper;
import com.sepractice.lenovoshop.service.ProductService;
import com.sepractice.lenovoshop.utils.Result;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductConfigMapper configMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping(value = "/")
    public Result infoPage(Long configCode) {
        Long productId = productService.getIdByCodeInConfig(configCode);
        List<ProductConfig> configs = productService.getConfigsFromProduct(productId);

        return Result.success(configs);
    }


}
