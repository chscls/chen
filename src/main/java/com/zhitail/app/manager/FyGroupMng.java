package com.zhitail.app.manager;

import com.zhitail.app.entity.FyGroup;
import com.zhitail.frame.util.page.Pagination;

public interface FyGroupMng {

	public Pagination<FyGroup> getPage(Integer pageNo, Integer pageSize,
			FyGroup search);

	public FyGroup save(FyGroup sensitive);

	public FyGroup update(FyGroup sensitive);

	public void delete(Long[] ids);

}
