package com.java.shop.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.java.shop.dto.Product;

@Mapper
public interface ProductDao {

    @Select("select * from product order by pCode desc limit #{start}, #{end}")
    List<Product> productAll(HashMap<String, Integer> list);

    @Select("select * from product where pName like #{keyword} order by pCode desc limit #{start}, #{end}")
    List<Product> productSearch(HashMap<String, Object> list);

    @Select("select * from product where pName like #{keyword} ${cCode} order by pCode asc limit #{start}, #{end}")
    List<Product> productSearchCategory(HashMap<String, Object> list);
}
