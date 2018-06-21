package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestVersion;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;

import com.zhitail.frame.util.page.Pagination;

public interface FyTestVersionMng {
	
	public FyTestVersion findById(Long id);

	public Pagination<String> getPage(Integer pageNo, Integer pageSize,
			FyTestVersion search, Long orgId);

	public FyTestVersion update(FyTestVersion testRecord);

	public void delete(Long[] ids);

	


	

	public FyTestVersion findByCode(String code);

	public FyTestVersion save(FyTestVersion vr);

	

}
