package com.sepractice.lenovoshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.sepractice.lenovoshop.service.OrderService;
import com.sepractice.lenovoshop.entity.Order;
import com.sepractice.lenovoshop.entity.OrderDTO;
import com.sepractice.lenovoshop.entity.OrderCreationDTO;
import com.sepractice.lenovoshop.utils.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result createOrder(@RequestBody OrderCreationDTO orderCreationDTO) {
        Order newOrder = orderService.createOrder(orderCreationDTO);

        return Result.success(newOrder.getId());
    }

    @GetMapping("/list")
    public Result getAllOrders(@RequestParam Long userId) {
        if (userId == null) {
            return Result.error("非法参数");
        }

        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return Result.success(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
