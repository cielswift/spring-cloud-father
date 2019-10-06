package com.ciel.springcloudasso.controller;

import com.ciel.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@RestController
@SessionAttributes("user")
public class IndexController {

    @RequestMapping({"/","/index"})
    public ModelAndView index(@SessionAttribute("user")UserEntity userEntity){


        ModelAndView ma = new ModelAndView();
        ma.setViewName("fram/body");
        return ma;
    }

}
