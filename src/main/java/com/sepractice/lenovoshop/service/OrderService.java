package com.sepractice.lenovoshop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepractice.lenovoshop.entity.Order;
import com.sepractice.lenovoshop.entity.OrderDTO;
import com.sepractice.lenovoshop.mapper.OrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    public Order createOrder(Order order) {
        this.save(order);
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
                    dto.setOrderId(order.getOrderId());
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

    public Order getOrderById(Long id) {
        return this.getById(id);
    }
}
