package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyUser.Type;
import com.zhitail.frame.util.page.Pagination;

public interface FyUserMng {

	public Pagination<FyUser> getPage(Type type, Integer pageNo, Integer pageSize,
			FyUser search);

	public FyUser update(FyUser user);

	public FyUser save(FyUser user);

	public void delete(Long[] ids);

	public FyUser findById(Long id);

	public FyUser findByUserName(String userName);

	public List<FyUser> findByIds(Long[] ids);

	public  FyUser findByOpenid(String openid);

	public List<Object> findIdsByName(String userkey);

}
