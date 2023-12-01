package com.java.shop.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.shop.dao.LoginDao;
import com.java.shop.dto.Login;

@Service
public class LoginService {
    @Autowired
    LoginDao dao;
    
    public Login login(HashMap<String, Object> map){
        return dao.login(map);
    }
    public Login register(HashMap<String, Object> map){
        return dao.register(map);
    }

    public int idCheck(String id){
        return dao.idCheck(id);
    }

    public int phoneCheck(String phone){
        return dao.phoneCheck(phone);
    }

    public void phoneCheck2(String num){
    }
}
