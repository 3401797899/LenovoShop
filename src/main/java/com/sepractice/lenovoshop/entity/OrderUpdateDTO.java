package com.sepractice.lenovoshop.entity;


import lombok.Data;
import java.util.List;

@Data
public class OrderUpdateDTO {
    private Integer userId;
    private Integer id;
    private Integer payment;
    private String create_time;
    private Integer status;
    private String name;
    private String dz;
    private String phone;

    // Getters and Setters
}
