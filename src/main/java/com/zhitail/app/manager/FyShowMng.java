package com.zhitail.app.manager;

import com.zhitail.app.entity.FyShow;
import com.zhitail.frame.util.page.Pagination;

public interface FyShowMng {

	public Pagination<FyShow> getPage(Integer pageNo, Integer pageSize,
			FyShow search);

	public FyShow save(FyShow bean);

	public FyShow update(FyShow bean);

	public void delete(Long[] ids);

	public FyShow findById(Long id);

}
