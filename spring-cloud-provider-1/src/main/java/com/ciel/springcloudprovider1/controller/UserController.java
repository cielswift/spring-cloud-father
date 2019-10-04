package com.ciel.springcloudprovider1.controller;

import com.ciel.entity.UserEntity;
import com.ciel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getAll")
    public List<UserEntity> getAll(){
        List<UserEntity> all = userService.getAll();
        all.get(0).setName("PROVIDER-1");
        return all;
    }

}
