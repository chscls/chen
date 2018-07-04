package com.zhitail.app.manager;

import com.zhitail.app.entity.FySkin;
import com.zhitail.frame.util.page.Pagination;

public interface FySkinMng {

	public Pagination<FySkin> getPage(Integer pageNo, Integer pageSize,
			FySkin search);

	public FySkin save(FySkin skin);

	public FySkin update(FySkin skin);

	public void delete(Long[] ids);

}
