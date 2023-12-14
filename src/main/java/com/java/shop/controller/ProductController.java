package com.java.shop.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.shop.dto.Category;
import com.java.shop.dto.Product;
import com.java.shop.service.CategoryService;
import com.java.shop.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class ProductController {
    @Autowired
    ProductService sv;

    @Autowired
    CategoryService csv;

    // 애미
    @GetMapping("/cloth")
    public String crawl(@RequestParam("keyword") String keyword){
        System.out.println(sv.crawling(keyword));
        System.out.println("-------------------------------------");
        return null;
    }

    //상품 정보수정
    //ex:) /product?page=1&category=1&keyword=패딩
    @GetMapping({"/product"})
    public ResponseEntity<?> productsearch(@RequestParam(
        value = "page", defaultValue = "1") int page, @RequestParam(
            value = "keyword", defaultValue = "") String key, @RequestParam(
                value="category", defaultValue="0") int cate){
        int end = page*10 - 1;
        int start = end - 9;

        String keys = "%" + key + "%";
        String category;
        if(cate == 0){
            category = "";
        }
        else{
            category = "and cCode = "+ cate;
        }
        System.out.println(category);

        HashMap<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);
        map.put("keyword", keys);
        map.put("cCode", category);

        List<Product> response = sv.productSearchCategory(map);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/product/categoryset")
    public String productCategorySet(){
        List<Category> list = csv.productCategory();
        for (Category str : list){
            System.out.println(str);
        }
        return "null";
    }
    

}
