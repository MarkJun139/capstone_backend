package com.java.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.shop.dao.CategoryDao;
import com.java.shop.dto.Category;

@Service
public class CategoryService {
    @Autowired
    CategoryDao dao;

    public List<Category> productCategory(){
        return dao.productCategory();
    }
}
