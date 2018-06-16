package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyTest;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;

public interface FyTestMng {
	public  void fullQuestions(FyTest test) ;
	

	public FyTest update(FyTest test);

	public FyTest save(FyTest test);

	public void delete(Long[] ids);

	public FyTest findById(Long id);

	public FyTest  updateTestQuestions(Long id, Long[] qids);

	public List<FyTest> findByCodes(String[] codes);

	public List<FyTest> getList(Integer start, Integer count, FyTest search);

	public FyTest findByCode(String code);

	public List<FyTest> findByQuestionId(Long id);


	 public Pagination<FyTest> getPage(Long id, Integer pageNo, Integer pageSize, String title, String isQuestionnaire,
			String mode, String status);


	public void recycle(Long[] ids);

}
