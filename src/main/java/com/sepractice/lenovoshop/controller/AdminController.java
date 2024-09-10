package com.sepractice.lenovoshop.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sepractice.lenovoshop.entity.Order;
import com.sepractice.lenovoshop.entity.OrderCreationDTO;
import com.sepractice.lenovoshop.entity.OrderUpdateDTO;
import com.sepractice.lenovoshop.entity.User;
import com.sepractice.lenovoshop.service.OrderService;
import com.sepractice.lenovoshop.service.UserService;
import com.sepractice.lenovoshop.utils.Result;
import com.sepractice.lenovoshop.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController
{
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private com.sepractice.lenovoshop.mapper.UserMapper userMapper;

    @PostMapping("/orders/create")
    public Result createOrder(@RequestBody OrderCreationDTO orderCreationDTO) {
        Order newOrder = orderService.createOrder(orderCreationDTO);

        return Result.success(newOrder.getId());
    }

    @GetMapping("/orders/search")
    public Result getAllOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Long status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {


        // 调用服务层方法，根据条件查询订单
        IPage<Order> orders = orderService.getOrdersByConditions(userId, orderId, status,page,limit);
        return Result.success(orders);
    }

    @GetMapping("/user/search")
    public Result getAllUsers(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit
    ) {
        // 调用服务层方法，查询所有用户
        IPage<User> users = userService.getUsersByCondition(userId, email, page, limit);
        return Result.success(users);
    }

    @PostMapping("/user/create")
    public Result createUsers(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        String password = params.get("password");
        if(!Validate.validate_email(email).equals("ok")){
            return Result.error(Validate.validate_pwd(email));
        }
        if(!Validate.validate_pwd(password).equals("ok")){
            return Result.error(Validate.validate_pwd(password));
        }
        userService.register(email, password);
        return Result.success();
    }

    @GetMapping("/user/delete")
    public Result deleteUser(@RequestParam String userId) {
        boolean success = userService.deleteUser(userId);
        if (!success) {
            return Result.error("删除失败");
        }
        return Result.success();
    }

    @PostMapping("/user/update")
    public Result updateUser(@RequestBody Map<String, String> params) {
        try {
            String userId = params.get("userId");
            String nickname = params.get("nickname");
            Integer gender = Integer.parseInt(params.get("gender"));
            String email = params.get("email");
            Integer balance = Integer.parseInt(params.get("balance"));

            User user = userMapper.selectById(Integer.valueOf(userId));
            user.setId(Integer.parseInt(userId));
            if (nickname == null) {
                return Result.error("昵称不能为空");
            }
            if (gender < 0 || gender > 2) {
                return Result.error("性别参数错误");
            }
            if (!Validate.validate_email(email).equals("ok")) {
                return Result.error(Validate.validate_email(email));
            }
            if (balance < 0) {
                return Result.error("余额不能为负数");
            }
            user.setNickname(nickname);
            user.setGender(gender);
            user.setEmail(email);
            user.setBalance(balance);

            userMapper.updateById(user);
            return Result.success();
        }catch (Exception e) {
            return Result.error("更新失败");
        }
    }
}
