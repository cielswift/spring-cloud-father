package com.ciel.springcloudcommon.dao;

import com.ciel.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LangDao extends JpaSpecificationExecutor<LanguageEntity>, BaseJpaRepository<LanguageEntity, Integer> {

}
