package com.sepractice.lenovoshop.mapper;

import com.sepractice.lenovoshop.entity.ProductCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ProductCountMapper extends BaseMapper<ProductCount> {

    @Select("SELECT * FROM product_counts WHERE order_id = #{orderId}")
    List<ProductCount> findByOrderId(Long orderId);
}