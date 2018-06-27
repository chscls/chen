package com.zhitail.app.manager;

import com.zhitail.app.entity.FyAd;
import com.zhitail.frame.util.page.Pagination;

public interface FyAdMng {

	public Pagination<FyAd> getPage(Integer pageNo, Integer pageSize,
			FyAd search);

	public FyAd save(FyAd ad);

	public FyAd update(FyAd ad);

	public void delete(Long[] ids);

}
