package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyFriend;
import com.zhitail.frame.util.page.Pagination;

public interface FyFriendMng {

	public Pagination<FyFriend> getPage(Integer pageNo, Integer pageSize,
			FyFriend search);

	public FyFriend save(FyFriend friend);

	public FyFriend update(FyFriend friend);

	public void delete(Long[] ids);

	public FyFriend check(Long userId, Long friendId);

	public List<FyFriend> findByIds(Long userId, Long[] fids);

	public void changeGroup(Long[] ids, Long groupId);

}
