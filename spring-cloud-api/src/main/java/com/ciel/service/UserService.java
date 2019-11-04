package com.ciel.service;

import com.ciel.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "SPRINGCLOUD-PROVIDER",path = "/provider/user",fallbackFactory = UserServiceFullbackImpl.class)
public interface UserService  {


    @RequestMapping("/getAll")
    public List<UserEntity> getAll();

    @RequestMapping("/getByWapper")
    public List<UserEntity> getByWapper() throws JsonProcessingException;


    UserEntity findByName(String userName);

    UserEntity save(UserEntity userEntity);
}
