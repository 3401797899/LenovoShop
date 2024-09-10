package com.sepractice.lenovoshop.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sepractice.lenovoshop.entity.*;
import com.sepractice.lenovoshop.service.OrderService;
import com.sepractice.lenovoshop.service.ProductService;
import com.sepractice.lenovoshop.service.UserService;
import com.sepractice.lenovoshop.utils.Result;
import com.sepractice.lenovoshop.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.module.Configuration;
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
    @Autowired
    private com.sepractice.lenovoshop.mapper.ProductMapper productMapper;
    @Autowired
    private com.sepractice.lenovoshop.mapper.ProductConfigMapper configMapper;
    @Autowired
    private ProductService productService;


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

    @GetMapping("/product/delete")
    public Result deleteProduct(@RequestParam String productId) {
        boolean success = productService.deleteProduct(productId);
        if (!success) {
            return Result.error("删除失败");
        }
        return Result.success();
    }

    @PostMapping("/product/create")
    public Result createProducts(@RequestBody Map<String, String> params){
        Product product = productService.createProduct(params);

        return Result.success(product);
    }

    @GetMapping("/product/search")
    public Result getAllProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit
    ) {
        if(page == null) page = 1;
        if(limit == null) limit = 1;
        // 调用服务层方法，查询所有用户
        IPage<Product> products = productService.getProductsByCondition(productName, page, limit);
        return Result.success(products);
    }


    @PostMapping("/product/update")
    public Result updateProduce(@RequestBody Map<String, String> params) {
        try {
            Integer id = Integer.parseInt(params.get("id"));
            String productId = params.get("productId"); // code
            String productName = params.get("name"); // configs_name
            String description = params.get("brief"); // brief
            Integer unitPrice = Integer.parseInt(params.get("price"));  // price
            Integer categoryId = Integer.parseInt(params.get("categoryId"));

            Product product = productService.getProductById(id);
            if (productId == null) {
                return Result.error("productId不能为空");
            }
            if (productName == null) {
                return Result.error("商品名不能为空");
            }
            if (description == null) {
                return Result.error("配置信息不能为空");
            }
            if (unitPrice == null) {
                return Result.error("单价不能为空");
            }
            if (unitPrice < 0) {
                return Result.error("单价不能为负数");
            }
            if (categoryId == null) {
                return Result.error("分类不能为空");
            }
            if (categoryId < 3 || categoryId > 11) {
                return Result.error("无该类别");
            }

            product.setProductId(productId);
            product.setName(productName);
            product.setBrief(description);
            product.setPrice(unitPrice);
            product.setCategoryId(categoryId);

            productMapper.updateById(product);
            return Result.success();
        }catch (Exception e) {
            return Result.error("更新失败");
        }
    }
}
