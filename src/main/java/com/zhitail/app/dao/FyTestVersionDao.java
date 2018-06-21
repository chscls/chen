package com.zhitail.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FyTestVersion;
import com.zhitail.frame.util.jpa.BaseRepository;



public interface FyTestVersionDao extends BaseRepository<FyTestVersion,Long> {
	@Query("select d from FyTestVersion d where d.code =:code") 
	
	FyTestVersion findByCode(@Param(value = "code")String code);

	

}
