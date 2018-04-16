package com.zhitail.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FyUser;
import com.zhitail.frame.util.jpa.BaseRepository;



public interface FyUserDao extends BaseRepository<FyUser,Long> {

	 	@Query("select d from FyUser d where d.mobile =:username") 
		public FyUser findByUserName(@Param(value = "username")  String username);

}
