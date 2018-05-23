package com.zhitail.app.manager;

import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyTestRecord;
import com.zhitail.frame.util.page.Pagination;

public interface FyTestRecordMng {

	public FyTestRecord findById(Long id);

	public Pagination<FyTestRecord> getPage(Integer pageNo, Integer pageSize,
			FyTestRecord search);

	public FyTestRecord update(FyTestRecord testRecord);

	public void delete(Long[] ids);

}
