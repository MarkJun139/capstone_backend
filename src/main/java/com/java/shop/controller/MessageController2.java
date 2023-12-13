package com.java.shop.controller;

import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.shop.service.LoginService;
import com.java.shop.util.RedisUtil;
import com.java.shop.util.SmsUtil;



@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class MessageController2 {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService lsv;

    @Autowired
    SmsUtil sms;

    RedisUtil redis;


    @PostMapping("/phonecheck3")
    public ResponseEntity<?> sendToEmail(@RequestBody HashMap<String, Object> json){
        String phone = (String) json.get("phoneNumber");

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        sms.sendOne(phone, numStr);

        //String key = "";
        //redis.setDataExpire(key, numStr, 60 * 1L);

        //System.out.println(key);
        //System.out.println("흠" + numStr);
        
        return ResponseEntity.ok(numStr);
    }


}
