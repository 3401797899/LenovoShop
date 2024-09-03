package com.sepractice.lenovoshop.entity;



import lombok.Data;
import java.util.List;

@Data
public class OrderCreationDTO {
    private Integer userId;
    private String dz;
    private String name;
    private String phone;
    private List<ProductCount> items;
    private String remarks;

    // Getters and Setters
}