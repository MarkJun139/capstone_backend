package com.java.shop.service;

// 네이버 검색 API 예제 - 블로그 검색
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.java.shop.dao.ProductDao;
import com.java.shop.dto.Product;


    @Service
    public class ProductService {
        @Autowired
        ProductDao dao;

        @Value("${client.id}")
        String clientId; //애플리케이션 클라이언트 아이디

        @Value("${client.secret}")
        String clientSecret;
        
        public List<Product> productAll(HashMap<String, Integer> list){
            return dao.productAll(list);
        }
        public List<Product> productSearch(HashMap<String, Object> list){
            return dao.productSearch(list);
        }
        public List<Product> productSearchCategory(HashMap<String, Object> list){
            return dao.productSearchCategory(list);
        }


        public String crawling(String keyword){ 


            String text = null;
            try {
                text = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("검색어 인코딩 실패",e);
            }


            String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text + "&display=20";    // JSON 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과


            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            String responseBody = get(apiURL,requestHeaders);

            makeJson(responseBody);
            System.out.println(responseBody);

            return responseBody;
        }
        
        private void makeJson(String responseBody){
            JSONParser parser = new JSONParser();
            JSONObject jObject = new JSONObject();

            // try{
            //     JSONArray jArray = (JSONArray) parser.parse(responseBody);
            //     for(int i = 0; i< jArray.size(); i++){
            //         JSONObject 
            //     }

            // }
            // catch(Exception e){

            // }
        }


        private String get(String apiUrl, Map<String, String> requestHeaders){
            HttpURLConnection con = connect(apiUrl);
            try {
                con.setRequestMethod("GET");
                for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }


                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                    return readBody(con.getInputStream());
                } else { // 오류 발생
                    return readBody(con.getErrorStream());
                }
            } catch (IOException e) {
                throw new RuntimeException("API 요청과 응답 실패", e);
            } finally {
                con.disconnect();
            }
        }


        private HttpURLConnection connect(String apiUrl){
            try {
                URL url = new URL(apiUrl);
                return (HttpURLConnection)url.openConnection();
            } catch (MalformedURLException e) {
                throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
            } catch (IOException e) {
                throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
            }
        }


        private String readBody(InputStream body){
            InputStreamReader streamReader = new InputStreamReader(body);


            try (BufferedReader lineReader = new BufferedReader(streamReader)) {
                StringBuilder responseBody = new StringBuilder();


                String line;
                while ((line = lineReader.readLine()) != null) {
                    responseBody.append(line);
                }


                return responseBody.toString();
            } catch (IOException e) {
                throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
            }
        }
    }
