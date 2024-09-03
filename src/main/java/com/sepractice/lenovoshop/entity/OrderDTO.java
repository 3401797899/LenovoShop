package com.sepractice.lenovoshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    //用来在返回订单列表时用
    private Long id;

    private String orderId;

    private Integer userId;

    private Integer payment;

    private Integer status;

    //private String expressName;

    //private String expressNumber;

    private Date createdTime;

    //private Date paymentTime;

    //private Date consignTime;

    //private Date endTime;

    private String name;

    private String phone;

    private String dz;

}

