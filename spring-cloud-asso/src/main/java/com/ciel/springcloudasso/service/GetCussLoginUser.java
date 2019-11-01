package com.ciel.springcloudasso.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class GetCussLoginUser {

    public Authentication getloginAuthentication(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            return authentication;
        }
        return null;

    }


    public User getloginUser(){
        UsernamePasswordAuthenticationToken authenticationToken
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //details里面可能存放了当前登录用户的详细信息，也可以通过cast后拿到
        User userDetails = (User) authenticationToken.getPrincipal();

        return userDetails;

    }

//    Authentication在认证请求时用到，也可在层次间传递。最常见的场景就是登录，登录中的name、password、permission，
//    对于过来就是Authentication的Principal、Credentials、Authorities。

}
