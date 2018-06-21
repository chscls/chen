package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FyTestVersion;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.frame.util.page.Pagination;

public interface FyTestRecordMng {
	
	public FyTestRecord findById(Long id);

	/*public Pagination<String> getPage(Integer pageNo, Integer pageSize,
			FyTestRecord search, Long orgId);*/

	public FyTestRecord update(FyTestRecord testRecord);

	public void delete(Long[] ids);

	

	public Pagination<FyTestRecord> getDetailPage(Integer pageNo,
			Integer pageSize, FyTestRecord search, FyTestVersion version, Long[] ids, String sort);

	public List<FyTestRecord> getList(Integer start, Integer count, FyTestVersion version, FyTestRecord search);

	

	public Integer getTotal(  FyTestVersion vr, FyTestRecord search);

	public  List<FyTestRecordStatistics> groupByCodes(String[] codes);

	public FyTestRecord addTestRecord(Long userId, String code, Long recordId);

	public FyTestRecord submit(Long id, String answers, String sign);

	public FyTestRecord findByUuid(String code);


	  public Pagination<FyTestRecord> getMyPage(Integer pageNo, Integer pageSize, FyTestVersion version, FyTestRecord search);

	

}
