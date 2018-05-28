package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyTest;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;

public interface FyTestMng {

	public Pagination<FyTest> getPage(Long userId, Integer pageNo, Integer pageSize, FyTest search);

	public FyTest update(FyTest test);

	public FyTest save(FyTest test);

	public void delete(Long[] ids);

	public FyTest findById(Long id);

	public FyTest  updateTestQuestions(Long id, Long[] qids);

	public List<FyTest> findByIds(Long[] orgIds);

}
