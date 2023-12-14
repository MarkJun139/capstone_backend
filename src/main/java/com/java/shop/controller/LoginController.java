package com.java.shop.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.shop.dto.Login;
import com.java.shop.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService lsv;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody HashMap<String,Object> map) {
            Login login = lsv.login(map);

            System.out.println("uId:"+ login.getUId());
            String sessionId = login.getUId();
            HttpHeaders header = new HttpHeaders();

            if(sessionId != null){
                // header.setContentType(MediaType.APPLICATION_JSON);
                header.set("LoginSession", sessionId);
                System.out.println(login);
                System.out.println("session" + sessionId);
            }
            
            return ResponseEntity.ok()                
                .headers(header)
                .body(login);
    }

    

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<Login> register(@RequestBody HashMap<String,Object> map) {
            lsv.register(map);
            Login login = lsv.login(map);

            return ResponseEntity.ok(login);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody HashMap<String, Object> map) {
        String id = (String)map.get("uId");
        if(lsv.idCheck(id) == 1){
            lsv.edit(map);
            System.out.println(map);
            return ResponseEntity.ok("true");
        }
        else{
            return ResponseEntity.ok("false");
        }
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
