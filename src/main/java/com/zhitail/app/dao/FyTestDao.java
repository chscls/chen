package com.zhitail.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyUser;
import com.zhitail.frame.util.jpa.BaseRepository;



public interface FyTestDao extends BaseRepository<FyTest,Long> {
 	@Query("select d from FyTest d where d.code =:code") 
	
 	public FyTest findByCode(@Param(value = "code") String code);

}
