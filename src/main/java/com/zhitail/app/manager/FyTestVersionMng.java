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

	

	public Pagination<FyTestVersion> getDetailPage(Integer pageNo,
			Integer pageSize, FyTestVersion search, Long[] ids, String sort);

	public List<FyTestVersion> getList(Integer start, Integer count, FyTestVersion search);

	

	public Integer getTotal(  FyTestVersion search);

	public  List<FyTestRecordStatistics> groupByCodes(String[] codes);

	public FyTestVersion addTestRecord(Long userId, String code, Long recordId);


	public Pagination<FyTestVersion> getMyPage(Integer pageNo, Integer pageSize, FyTestVersion search);

	

}
