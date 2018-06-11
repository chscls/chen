package com.zhitail.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.frame.util.jpa.BaseRepository;



public interface FyTestRecordDao extends BaseRepository<FyTestRecord,Long> {
@Query("select d from FyTestRecord d where d.uuid =:uuid") 
	
 	public FyTestRecord findByUuid(@Param(value = "uuid") String uuid);
	

}
