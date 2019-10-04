package com.ciel.springcloudconsumer.controller;

import com.ciel.entity.UserEntity;
import com.ciel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/searchFeign")
public class SearchFeignController{

    @Autowired(required = false)
    private UserService userService;  //Feign

    @RequestMapping("/getAll")
    public List<UserEntity> getAll(){
        return userService.getAll();
    }

}
