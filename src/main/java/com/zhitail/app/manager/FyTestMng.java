package com.zhitail.app.manager;

import com.zhitail.app.entity.FyTest;
import com.zhitail.frame.util.page.Pagination;

public interface FyTestMng {

	public Pagination<FyTest> getPage(Integer pageNo, Integer pageSize, FyTest search);

	public FyTest update(FyTest test);

	public FyTest save(FyTest test);

	public void delete(Long[] ids);

	public FyTest findById(Long id);

}
