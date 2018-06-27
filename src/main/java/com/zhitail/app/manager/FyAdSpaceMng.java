package com.zhitail.app.manager;

import com.zhitail.app.entity.FyAdSpace;
import com.zhitail.frame.util.page.Pagination;

public interface FyAdSpaceMng {

	public Pagination<FyAdSpace> getPage(Integer pageNo, Integer pageSize,
			FyAdSpace search);

	public FyAdSpace save(FyAdSpace adSpace);

	public FyAdSpace update(FyAdSpace adSpace);

	public void delete(Long[] ids);

}
