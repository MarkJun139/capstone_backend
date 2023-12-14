package com.java.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.java.shop.dto.Category;

@Mapper
public interface CategoryDao {
    @Select("select * from product_category")
    List<Category> productCategory();
}
