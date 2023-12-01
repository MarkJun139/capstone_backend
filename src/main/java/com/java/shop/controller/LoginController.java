package com.java.shop.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.shop.dto.Login;
import com.java.shop.service.LoginService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService lsv;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody HashMap<String,Object> map) {
            Login login = lsv.login(map);

            System.out.println(login);

            return ResponseEntity.ok(login);
    }

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<Login> register(@RequestBody HashMap<String,Object> map) {
            lsv.register(map);
            Login login = lsv.login(map);

            return ResponseEntity.ok(login);
    }

    //아이디 중복확인
    @PostMapping("/idcheck")
    public Boolean idCheck(@RequestBody HashMap<String, Object> json) { //리퀘스트 json으로 받을수 있게 해주고
        logger.info(json.toString());
        String uid = (String) json.get("id"); //json으로 받은거에서 태그값을 추출해서 변수에 저장하고
        logger.info(uid);
        int idCheck = lsv.idCheck(uid); //추출한값으로 매개변수지정
        if(idCheck != 0){
            logger.info( "{"+idCheck+"}" );
            return true;
        }
        logger.info( "{"+idCheck+"}" );
        return false;
    }

    //폰번호 중복확인
    @PostMapping("/phonecheck")
    public Boolean phoneCheck(@RequestBody HashMap<String, Object> json) {
        String phone = (String) json.get("phoneNumber");
        logger.info("phone"+phone);
        int phoneCheck = lsv.phoneCheck(phone);
        if(phoneCheck != 0){
            logger.info("phoneCheck:"+phoneCheck);
            return true;
        }
        logger.info("phoneCheck:"+phoneCheck);
        return false;
    }
}
