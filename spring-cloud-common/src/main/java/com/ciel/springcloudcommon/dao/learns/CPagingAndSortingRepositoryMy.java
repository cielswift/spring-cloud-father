package com.ciel.springcloudcommon.dao.learns;

import com.ciel.entity.AppEntity;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CPagingAndSortingRepositoryMy extends PagingAndSortingRepository<AppEntity, Integer> {
    //PagingAndSortingRepository继承了CrudRepository
}
