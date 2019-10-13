package com.ciel.service;

import com.ciel.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.hystrix.FallbackFactory;

import java.util.List;

public class UserServiceFullbackImpl implements FallbackFactory<UserService> {

    @Override
    public UserService create(Throwable throwable) { //统一降级处理,本地的处理,FallbackFactory
       return new UserService() {
           @Override
           public List<UserEntity> getAll() {
               return null;
           }

           @Override
           public List<UserEntity> getByWapper() throws JsonProcessingException {
               return null;
           }

           @Override
           public UserEntity findByName(String userName) {
               return null;
           }

           @Override
           public UserEntity save(UserEntity userEntity) {
               return null;
           }
       };
    }
}
