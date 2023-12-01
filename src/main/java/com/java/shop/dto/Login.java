package com.java.shop.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Login {
    private int uCode;
    private String uId;
    private String uPw;
    private String uNick;
    private String uEMail;
    private int uEmailCheck;
    private String uPhone;
    private String uGrade;
    private Date uRegiDate;
    private Date uLastDate;
    private int uDrop;

    private String uAccess;
    
    
}
