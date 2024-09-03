package com.sepractice.lenovoshop.service;

import com.sepractice.lenovoshop.entity.Address;
import com.sepractice.lenovoshop.mapper.AddressMapper;

import com.sepractice.lenovoshop.mapper.ProductCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class AddressService extends ServiceImpl<AddressMapper, Address>{
    @Autowired
    private AddressMapper addressMapper;


}
