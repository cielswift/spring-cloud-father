package com.ciel.springcloudasso.controller;

import com.ciel.entity.UserEntity;
import com.ciel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@SessionAttributes("user")
public class IndexController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
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

    @RequestMapping("/defeat")
    public ModelAndView defeat(){
        ModelAndView ma = new ModelAndView();
        ma.addObject("msg","用户名或密码错误");
        ma.setViewName("fram/login");
        return ma;
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

    @RequestMapping("/love")
    public String test3(){
        return "i lovo you";
    }

    @RequestMapping("/hello")
    public ModelAndView hello() {
       return new ModelAndView("fram/hello");
    }

    @Secured("ROLE_BOSS")  //基于注解的访问控制,参数可以以ROLE_开头。
    @RequestMapping("/like")
    public String test4(){
        return "i like you";
    }

    @PreAuthorize("@myServiceImpl.hasPermission(request,authentication)")
    //表示访问方法或类在执行之前先判断权限,参数和access()方法参数取值相同，都是权限表达式。
    @RequestMapping("/test1")
    public String test1(HttpServletRequest request) {
        return "test1";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/test2")
    public String test2() {
        return "test2";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping("/test6")
    public String test6() {
        return "test6";
    }

    @PreAuthorize("hasRole('ADMIN')") //使用hasRole去设置权限，那么必须是以ROLE_开头的角色。这里省略ROLE_即可
    @RequestMapping("/test7")
    public String test7() {
        return "test7";
    }

    @PostAuthorize("hasAuthority('USER')")
    //表示方法或类执行结束后判断权限，此注解很少被使用到。
    @RequestMapping("/test5")
    public String aa(){
        return "aaaa";
    }


    @PreAuthorize("hasIpAddress('127.0.0.1')")
    @RequestMapping("/test8")
    public String test8() {
        return "test8";
    }
}
