package com.sepractice.lenovoshop.mapper;

import com.sepractice.lenovoshop.entity.ProductConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface ProductConfigMapper extends BaseMapper<ProductConfig> {

    @Select("SELECT * FROM configs WHERE product_code = #{productCode}")
    ProductConfig selectByProductCode(String productCode);

    @Select("SELECT COUNT(*) FROM configs WHERE product_code = #{productCode}")
    int countByProductId(String productId);
}
