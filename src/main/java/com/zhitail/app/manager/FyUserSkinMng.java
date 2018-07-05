package com.zhitail.app.manager;

import com.zhitail.app.entity.FyUserSkin;
import com.zhitail.frame.util.page.Pagination;

public interface FyUserSkinMng {

	public Pagination<FyUserSkin> getPage(Integer pageNo, Integer pageSize,
			FyUserSkin search,String name,String code);

	public FyUserSkin save(FyUserSkin userSkin);

	public FyUserSkin update(FyUserSkin userSkin);

	public void delete(Long[] ids);

}
