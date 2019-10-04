package com.ciel.service;

import com.ciel.entity.AppEntity;
import com.ciel.service.AppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.hystrix.FallbackFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppServiceFullbackImpl implements FallbackFactory<AppService> {

    @Override
    public AppService create(Throwable throwable) { //统一错误处理方法
        return new AppService() {
            @Override
            public List<AppEntity> getAll() {
                AppEntity appEntity = new AppEntity();
                appEntity.setName("ERROR-COMMON");
                return new ArrayList<>(Arrays.asList(new AppEntity[]{appEntity}));
            }

            @Override
            public List<AppEntity> getByWapper() throws JsonProcessingException {
                AppEntity appEntity = new AppEntity();
                appEntity.setName("ERROR-COMMON");
                return new ArrayList<>(Arrays.asList(new AppEntity[]{appEntity}));
            }

            @Override
            public AppEntity byId(Integer id) {
                AppEntity appEntity = new AppEntity();
                appEntity.setName("ERROR-COMMON");
                return appEntity;
            }

            @Override
            public void test() {

            }

            @Override
            public void test2() {

            }

            @Override
            public void test3() {

            }
        };
    }
}
