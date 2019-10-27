package com.ciel.springcloudasso.interceptor;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) { // 加密类
        return charSequence.toString();
    }


    @Override
    public boolean matches(CharSequence charSequence, String s) { //第一个是提交的原始密码,第二个是存储的密码;
        new ArrayList<String>();
        return s.equals(charSequence.toString());
    }

}