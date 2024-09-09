package com.sepractice.lenovoshop.controller;


import com.sepractice.lenovoshop.entity.Order;
import com.sepractice.lenovoshop.entity.OrderCreationDTO;
import com.sepractice.lenovoshop.entity.OrderUpdateDTO;
import com.sepractice.lenovoshop.entity.OrderDTO;
import com.sepractice.lenovoshop.service.OrderService;
import com.sepractice.lenovoshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController
{
    @Autowired
    private OrderService orderService;

    @PostMapping("/orders/create")
    public Result createOrder(@RequestBody OrderCreationDTO orderCreationDTO) {
        Order newOrder = orderService.createOrder(orderCreationDTO);

        return Result.success(newOrder.getId());
    }

    @GetMapping("/orders/search")
    public Result getAllOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Long status) {


        // 调用服务层方法，根据条件查询订单
        List<Order> orders = orderService.getOrdersByConditions(userId, orderId, status);

        return Result.success(orders);
    }

    @GetMapping("/orders/delete")
    public Result deleteOrder(@RequestParam(required = false) Long orderId) {


        boolean success = orderService.removeById(orderId);
        if (success) {
            return Result.success(orderId);
        }
        else{
            return Result.error("删除失败");
        }
    }

    @PostMapping("/orders/update")
    public Result updateOrder(@RequestBody OrderUpdateDTO orderUpdateDTO) {
        // 假设你有一个 Service 类来处理业务逻辑
        boolean success = orderService.updateOrder(orderUpdateDTO);
        if (success) {
            return Result.success();
        }else{return Result.error("更新失败");}
    }

}
