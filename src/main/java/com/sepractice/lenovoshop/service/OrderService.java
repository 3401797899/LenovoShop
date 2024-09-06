package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepractice.lenovoshop.entity.Order;
import com.sepractice.lenovoshop.entity.OrderDTO;
import com.sepractice.lenovoshop.entity.OrderCreationDTO;
import com.sepractice.lenovoshop.entity.ProductCount;
import com.sepractice.lenovoshop.mapper.OrderMapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    public Order createOrder(OrderCreationDTO orderCreationDTO) {
        Order order = new Order();
        order.setUserId(orderCreationDTO.getUserId());
        order.setDz(orderCreationDTO.getDz());
        order.setName(orderCreationDTO.getName());
        order.setPhone(orderCreationDTO.getPhone());
        //order.setRemarks(orderCreationDTO.getRemarks());
        order.setStatus(0);  // Default status for a new order
        order.setCreatedTime(new Date());
        order.setPayment(0);

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

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Order> orders = this.list(queryWrapper);

        // 转换 Order 为 OrderDTO
        return orders.stream()
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

                    //TODO：根据rainning的需求加
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<Order> getOrdersByConditions(Long userId, Long orderId, Long status) {
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

        // 执行查询并返回结果
        return orderMapper.selectList(queryWrapper);
    }


    public Order getOrderById(Long id,Long userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("user_id", userId);
        return orderMapper.selectOne(queryWrapper);
    }




}
