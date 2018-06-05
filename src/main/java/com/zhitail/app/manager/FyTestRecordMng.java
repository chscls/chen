package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.app.entity.middle.FyTestRecordStatistics;
import com.zhitail.frame.util.page.Pagination;

public interface FyTestRecordMng {

	public FyTestRecord findById(Long id);

	public Pagination<Long> getPage(Long teaId,Integer pageNo, Integer pageSize,
			FyTestRecord search);

	public FyTestRecord update(FyTestRecord testRecord);

	public void delete(Long[] ids);

	public List<FyTestRecordStatistics> groupByIds(Long[] ids);

	public Pagination<FyTestRecord> getDetailPage(Integer pageNo,
			Integer pageSize, FyTestRecord search);

	public List<FyTestRecord> getList(Long id, Integer start, Integer count, FyTestRecord search);

	public FyTestRecord addTestRecord(Long userId, String code);

}
