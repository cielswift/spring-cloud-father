package com.ciel.springcloudasso.interceptor;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) { //加密
        return charSequence.toString();
    }


    @Override
    public boolean matches(CharSequence charSequence, String s) { //对比
        new ArrayList<String>();
        return s.equals(charSequence.toString());
    }

}