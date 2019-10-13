package com.ciel.springcloudasso.controller;

import com.ciel.entity.UserEntity;
import com.ciel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@SessionAttributes("user")
public class IndexController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping({"/","/login"})
    public ModelAndView index(HttpServletRequest request){
        String path = request.getServletPath();
        logger.warn(path);
        ModelAndView ma = new ModelAndView();
        ma.setViewName("fram/login");
        return ma;
    }

    @RequestMapping("/log")
    public String log(UserEntity userEntity){

        UserEntity byName = userService.findByName(userEntity.getName());
        if(byName != null){
            return "ok";
        }else{
            return "defeat";
        }
    }

    @RequestMapping("/regis")
    public ModelAndView regis(HttpServletRequest request){
        String path = request.getServletPath();
        logger.warn(path);
        ModelAndView ma = new ModelAndView();
        ma.setViewName("fram/regis");
        return ma;
    }


    @RequestMapping("/reg")
    public String reg(UserEntity userEntity){
        UserEntity byName = userService.findByName(userEntity.getName());
        if(byName==null){
            UserEntity save = userService.save(userEntity);
            return "ok";
        }else{
           return "defeat";
        }
    }


    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping("/test1")

    public String test1() {
        return "test1";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/test2")
    public String test2() {
        return "test2";
    }


}
