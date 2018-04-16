package com.zhitail.app.manager;

import com.zhitail.app.entity.FySensitive;
import com.zhitail.frame.util.page.Pagination;

public interface FySensitiveMng {

	public Pagination<FySensitive> getPage(Integer pageNo, Integer pageSize,
			FySensitive search);

	public FySensitive save(FySensitive sensitive);

	public FySensitive update(FySensitive sensitive);

	public void delete(Long[] ids);

}
