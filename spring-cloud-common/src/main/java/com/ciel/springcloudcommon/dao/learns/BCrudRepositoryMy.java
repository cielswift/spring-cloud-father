package com.ciel.springcloudcommon.dao.learns;

import com.ciel.entity.AppEntity;
import org.springframework.data.repository.CrudRepository;

public interface BCrudRepositoryMy extends CrudRepository<AppEntity, Integer> {
	

    //CrudRepository继承了Repository
}