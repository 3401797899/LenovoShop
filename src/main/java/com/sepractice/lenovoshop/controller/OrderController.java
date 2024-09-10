package com.sepractice.lenovoshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sepractice.lenovoshop.mapper.UserMapper;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;
import org.springframework.beans.factory.annotation.Autowired;
import com.sepractice.lenovoshop.service.OrderService;
import com.sepractice.lenovoshop.entity.Order;
import com.sepractice.lenovoshop.entity.OrderDTO;
import com.sepractice.lenovoshop.entity.OrderCreationDTO;
import com.sepractice.lenovoshop.utils.*;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/create")
    public Result createOrder(@RequestBody OrderCreationDTO orderCreationDTO) {

        Integer payment =  orderCreationDTO.getPayment();
        Integer balance = userMapper.selectById(orderCreationDTO.getUserId()).getBalance();
        if(payment > balance){
            return Result.error("余额不足");
        }
        Order newOrder = orderService.createOrder(orderCreationDTO);

        userMapper.selectById(orderCreationDTO.getUserId()).setBalance(balance - payment);


        return Result.success(newOrder.getId());
    }

    @GetMapping("/list")
    public Result getAllOrders(@RequestParam Long userId,@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer limit) {
        if (userId == null) {
            return Result.error("非法参数");
        }


        IPage<OrderDTO> orders = orderService.getOrdersByUserId(userId,page,limit);
        return Result.success(orders);
    }

    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id,@RequestParam Long userId) {
        Order order = orderService.getOrderById(id,userId);
        return Result.success(order);
    }
}
