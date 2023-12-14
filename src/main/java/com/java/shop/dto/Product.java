package com.java.shop.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Product {
    int pCode;
    int cCode;
    String pBrand;
    String pName;
    String pOpt1;
    String pOpt2;
    String pOpt3;
    int pPrice;
    int pCount;
    String pDesc;
    Date pDate;
    int pJoin;
    int pFee;

    String pImageThumb;
    String pImageMain;
    String pImageDetail;



}
