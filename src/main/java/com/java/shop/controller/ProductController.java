package com.java.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        
        Map<String, Integer> cloth1 = new HashMap<>();
        Map<String, Integer> cloth2 = new HashMap<>();
        Map<String, Integer> cloth3 = new HashMap<>();
        Map<String, Map<String, Integer>> categoryList = new HashMap<>();

        for(int i = 0; i < list.size(); i++){ 
            if(list.get(i).getCName1().equals("상의")) {
                cloth1.put(list.get(i).getCName2(), list.get(i).getCCode());
            }
            else if(list.get(i).getCName1().equals("하의")) {
                cloth2.put(list.get(i).getCName2(), list.get(i).getCCode());
            }
            else if(list.get(i).getCName1().equals("아우터")) {
                cloth3.put(list.get(i).getCName2(), list.get(i).getCCode());
            }
        }
        categoryList.put("상의", cloth1);
        categoryList.put("하의", cloth2);
        categoryList.put("아우터", cloth3);

        System.out.println(categoryList);
        System.out.println(categoryList.get("아우터"));

        // for (Category str : list){
        //     System.out.println(str);
        // }
        return "null";
    }
    

}
