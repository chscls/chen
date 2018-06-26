package com.zhitail.app.manager;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.FyTestVersion;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.frame.util.page.Pagination;

public interface FyTestRecordMng {
	
	public FyTestRecord findById(Long id);


	public FyTestRecord update(FyTestRecord testRecord);

	public void delete(Long[] ids);

	

	public Pagination<FyTestRecord> getDetailPage(Integer pageNo,
			Integer pageSize, FyTestRecord search, FyTestVersion version, Long[] ids, String sort);

	public List<FyTestRecord> getList(Integer start, Integer count, FyTestVersion version, FyTestRecord search);

	

	public Integer getTotal(  FyTestVersion vr, FyTestRecord search);


	public FyTestRecord addTestRecord(Long userId,Long id, Long recordId);

	public FyTestRecord submit(Long id, String answers, String sign);

	public FyTestRecord findByUuid(String code);


	  public Pagination<FyTestRecord> getMyPage(Integer pageNo, Integer pageSize, FyTestVersion version, FyTestRecord search);

	public Pagination<String> getPage(Integer pageNo, Integer pageSize, FyTestVersion search);


	public Pagination<FyTestRecordStatistics> groupByCodes(FyTestVersion version, Integer pageNo, Integer pageSize);



	public FyTestRecord makeScore(Long id, JSONObject scores);


	public FyTestRecord getWait(String code);

	

}
