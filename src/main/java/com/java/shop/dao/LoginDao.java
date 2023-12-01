package com.java.shop.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.java.shop.dto.Login;

@Mapper
public interface LoginDao {
    @Select("select * from users where uId=#{uId} and uPw=#{uPw}")
    Login login(HashMap<String, Object> map);

    @Insert("insert into users value (#{uCode}, #{uId}, #{uPw}, #{uName}, #{uNick}, #{uEmail}, #{uEmailCheck}, #{uPhone}, #{uAccess}, #{uGrade}, #{uRegiDate}, #{uLastDate}, #{uDrop})")
    Login register(HashMap<String, Object> map);

    @Select("select count(*) from users where uId=#{id}")
    int idCheck(String id);

    @Select("select count(*) from users where uPhone=#{phone}")
    int phoneCheck(String phone);

    int phoneCheck2(String num);
}
