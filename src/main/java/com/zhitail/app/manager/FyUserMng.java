package com.zhitail.app.manager;

import com.zhitail.app.entity.FyUser;
import com.zhitail.app.entity.FyUser.Type;
import com.zhitail.frame.util.page.Pagination;

public interface FyUserMng {

	public Pagination<FyUser> getPage(Type type, Integer pageNo, Integer pageSize,
			FyUser search);

}
