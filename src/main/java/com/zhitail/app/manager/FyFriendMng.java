package com.zhitail.app.manager;

import com.zhitail.app.entity.FyFriend;
import com.zhitail.frame.util.page.Pagination;

public interface FyFriendMng {

	public Pagination<FyFriend> getPage(Integer pageNo, Integer pageSize,
			FyFriend search);

	public FyFriend save(FyFriend friend);

	public FyFriend update(FyFriend friend);

	public void delete(Long[] ids);

	public FyFriend check(Long userId, Long friendId);

}
