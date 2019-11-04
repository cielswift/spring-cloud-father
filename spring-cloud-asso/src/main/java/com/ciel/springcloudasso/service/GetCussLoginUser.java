package com.ciel.springcloudasso.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

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


    /**
     * 异步回调消息方法
     *
     * @return 字符串
     */
    @Async
    public Future<String> returnMessage() {
        System.out.println(Thread.currentThread().getName());
        String message = "夏培鑫 异步返回值 阻塞";
        return new AsyncResult<>(message);
    }

    /**
     * 异步回调消息方法
     *
     * @return 字符串
     */
    @Async
    public ListenableFuture<String> returnMsg() {
        System.out.println(Thread.currentThread().getName());
        String message = "夏培鑫 异步返回值 非阻塞";
        return new AsyncResult<>(message);
    }


}
