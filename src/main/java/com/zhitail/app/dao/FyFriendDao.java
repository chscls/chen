package com.zhitail.app.dao;

import org.springframework.data.jpa.repository.Query;

import com.zhitail.app.entity.FyFriend;
import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.frame.util.jpa.BaseRepository;



public interface FyFriendDao extends BaseRepository<FyFriend,Long> {
	@Query("select d from FyFriend d where d.userId =:userId and d.friendId=:friendId") 
	FyFriend check(Long userId, Long friendId);

}
