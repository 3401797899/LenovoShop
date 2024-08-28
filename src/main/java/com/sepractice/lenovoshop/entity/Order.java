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
@TableName("orders")
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String orderId;

    private Integer userId;

    private Integer payment;

    private Status status;

    private String expressName;

    private String expressNumber;

    private Date createdTime;

    private Date paymentTime;

    private Date consignTime;

    private Date endTime;

    private String name;

    private String phone;

    private String dz;

    @TableField(exist = false)
    private List<ProductCount> products;

    // 枚举类型用于订单状态
    public enum Status {
        WAIT_PAYMENT(1, "待付款"), WAIT_DELIVERY(2, "待发货"), WAIT_RECEIVE(3, "待收货"), COMPLETED(4, "已完成");

        private int code;
        private String description;

        Status(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}