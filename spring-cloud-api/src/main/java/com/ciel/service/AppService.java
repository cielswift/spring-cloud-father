package com.ciel.service;

import com.ciel.entity.AppEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AppService {

    public List<AppEntity> getAll();

    public List<AppEntity> getByWapper() throws JsonProcessingException;

    public AppEntity byId(Integer id);

    public void test();
    public void test2();
    public void test3();
}
