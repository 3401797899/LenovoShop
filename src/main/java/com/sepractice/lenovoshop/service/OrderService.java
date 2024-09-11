package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepractice.lenovoshop.entity.*;
import com.sepractice.lenovoshop.mapper.OrderMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sepractice.lenovoshop.mapper.ProductConfigMapper;
import com.sepractice.lenovoshop.mapper.ProductCountMapper;
import com.sepractice.lenovoshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    @Autowired
    private ProductCountService productCountService; // 注入 OrderItemService

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductConfigMapper productConfigMapper;

    @Autowired
    private ProductCountMapper productCountMapper;

    @Autowired
    private ProductMapper productMapper;

    public Order createOrder(OrderCreationDTO orderCreationDTO) {
        Order order = new Order();
        order.setUserId(orderCreationDTO.getUserId());
        order.setDz(orderCreationDTO.getDz());
        order.setName(orderCreationDTO.getName());
        order.setPhone(orderCreationDTO.getPhone());
        //order.setRemarks(orderCreationDTO.getRemarks());
        order.setStatus(2);  // Default status for a new order
        order.setCreatedTime(new Date());
        order.setPayment(orderCreationDTO.getPayment());

        // Save the order first to get the order ID
        this.save(order);

        // Process each item in the order
        for (ProductCount item : orderCreationDTO.getItems()) {
            // Create an OrderItem entity (assuming you have one)
            ProductCount orderItem = new ProductCount();
            orderItem.setOrderId(order.getId());
            orderItem.setProductCode(item.getProductCode());
            orderItem.setCount(item.getCount());
            orderItem.setPrice(item.getPrice());

            // Save the order item (you would need an OrderItemService/Mapper for this)
            productCountService.save(orderItem);
        }

        return order;
    }

    public Page<OrderDTO> getOrdersByUserId(Long userId, Integer page, Integer limit) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Page<Order> rowPage = new Page(page, limit);
        Page<Order> orders = orderMapper.selectPage(rowPage,queryWrapper);


        // 创建一个 Page 对象用于返回
        Page<OrderDTO> resultPage = new Page<>();
        resultPage.setCurrent(orders.getCurrent());
        resultPage.setSize(orders.getSize());
        resultPage.setTotal(orders.getTotal());
        resultPage.setPages(orders.getPages());

        // 将 Order 转换为 OrderDTO
        resultPage.setRecords(
                orders.getRecords().stream()
                        .map(order -> {
                            OrderDTO dto = new OrderDTO();
                            dto.setId(order.getId());
                            dto.setUserId(order.getUserId());
                            dto.setPayment(order.getPayment());
                            dto.setStatus(order.getStatus());
                            dto.setCreatedTime(order.getCreatedTime());
                            dto.setName(order.getName());
                            dto.setPhone(order.getPhone());
                            dto.setDz(order.getDz());

                            List<ProductCount> temp =   productCountMapper.findByOrderId(order.getId());

                            dto.setItems(temp.stream()
                                    .map(productCount -> {
                                        ProductList productList = new ProductList();

                                        ProductConfig pconfig =  productConfigMapper.selectByProductCode(productCount.getProductCode());

                                        productList.setName(pconfig.getName());
                                        productList.setBrief(pconfig.getBrief());
                                        productList.setCount(productCount.getCount());
                                        productList.setPicUrl(productMapper.selectById(pconfig.getProductId()).getPicUrl());

                                        return productList;
                                    }
                                    )
                                    .collect(Collectors.toList()));
                            // TODO: 根据需求添加其他字段的转换
                            return dto;
                        })
                        .collect(Collectors.toList())
        );

        return resultPage;
    }

    public IPage<Order> getOrdersByConditions(Long userId, Long orderId, Long status, Integer page, Integer limit) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();

        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        }
        if (orderId != null) {
            queryWrapper.eq("id", orderId);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        Page<Order> rowPage = new Page(page, limit);

        Page<Order> t = orderMapper.selectPage(rowPage,queryWrapper);
        t.getRecords().forEach(order -> {

                    List<ProductCount> temp =   productCountMapper.findByOrderId(order.getId());

                    order.setProducts(temp.stream()
                            .map(productCount -> {
                                        ProductList productList = new ProductList();

                                        ProductConfig pconfig =  productConfigMapper.selectByProductCode(productCount.getProductCode());

                                        productList.setName(pconfig.getName());
                                        productList.setBrief(pconfig.getBrief());
                                        productList.setCount(productCount.getCount());
                                        productList.setPicUrl(productMapper.selectById(pconfig.getProductId()).getPicUrl());

                                        return productList;
                                    }
                            )
                            .collect(Collectors.toList()));
                });
        return t;


    }


    public Order getOrderById(Long id,Long userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("user_id", userId);

        Order tempOrder = orderMapper.selectOne(queryWrapper);
        List<ProductCount> temp = productCountMapper.findByOrderId(tempOrder.getId());

        tempOrder.setProducts(temp.stream()
                .map(productCount -> {
                            ProductList productList = new ProductList();

                            ProductConfig pconfig =  productConfigMapper.selectByProductCode(productCount.getProductCode());

                            productList.setName(pconfig.getName());
                            productList.setBrief(pconfig.getBrief());
                            productList.setCount(productCount.getCount());
                            productList.setPicUrl(productMapper.selectById(pconfig.getProductId()).getPicUrl());
                            productList.setPrice(productCount.getPrice());
                            productList.setProductCode(productCount.getProductCode());


                            return productList;
                        }
                )
                .collect(Collectors.toList()));


        return tempOrder;
    }

    public boolean updateOrder(OrderUpdateDTO orderUpdateDTO) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        // 假设 orderUpdateDTO 中有一个 id 字段来标识要更新的订单
        updateWrapper.eq("id", orderUpdateDTO.getId());

        Order order = new Order();
        if (orderUpdateDTO.getPayment() != null) {
            order.setPayment(orderUpdateDTO.getPayment());
        }
        if (orderUpdateDTO.getStatus() != null) {
            order.setStatus(orderUpdateDTO.getStatus());
        }
        if (orderUpdateDTO.getName() != null) {
            order.setName(orderUpdateDTO.getName());
        }
        if (orderUpdateDTO.getDz() != null) {
            order.setDz(orderUpdateDTO.getDz());
        }
        if (orderUpdateDTO.getPhone() != null) {
            order.setPhone(orderUpdateDTO.getPhone());
        }

        return orderMapper.update(order, updateWrapper) > 0;
    }



}
