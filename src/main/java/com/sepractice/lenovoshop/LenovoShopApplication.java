package com.sepractice.lenovoshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sepractice.lenovoshop.mapper")
public class LenovoShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(LenovoShopApplication.class, args);
    }

}
