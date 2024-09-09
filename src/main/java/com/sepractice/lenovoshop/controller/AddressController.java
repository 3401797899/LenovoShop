package com.sepractice.lenovoshop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepractice.lenovoshop.entity.Address;

import com.sepractice.lenovoshop.service.AddressService;
import com.sepractice.lenovoshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;


    @PostMapping("/create")
    public Result createAddress(@RequestBody Address address) {
        addressService.save(address);
        return Result.success(address.getId());
    }

    @GetMapping("/list")
    public Result getAllAddresses(@RequestParam Long userId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        List<Address> addresses = addressService.list(queryWrapper); // Retrieves all addresses
        return Result.success(addresses);
    }

    @GetMapping("/delete")
    public Result deleteAddresses(@RequestParam Long Id) {
        boolean removed = addressService.removeById(Id);
        if (removed) {
            return Result.success(removed);
        }
        else{
            return Result.error("删除失败");
        }

    }

}
