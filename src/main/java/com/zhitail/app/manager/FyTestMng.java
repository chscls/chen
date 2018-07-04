package com.zhitail.app.manager;

import java.util.List;
import java.util.Set;

import com.zhitail.app.entity.FyTest;
import com.zhitail.frame.util.page.Pagination;
import com.zhitail.frame.util.service.Result;

public interface FyTestMng {


	public FyTest update(FyTest test);

	public FyTest save(FyTest test);

	public void delete(Long[] ids);

	public FyTest findById(Long id);



	public List<FyTest> findByCodes(String[] codes);

	public List<FyTest> getList(Integer start, Integer count, FyTest search);

	public FyTest findByCode(String code);

	public List<FyTest> findByQuestionId(Long id);



	public void recycle(Long[] ids);


	public  Pagination<FyTest> getPage(Long userId, Integer pageNo, Integer pageSize, String code, String title,
			String isQuestionnaire,String isNoOrder, String mode, String status,String sorter);



	public Pagination<FyTest> getRecyclePage(Long userId, Integer pageNo, Integer pageSize, String title, String code,
			String sorter);


	public void recovery(Long[] ids);


	public void clear();


	public void upShopTest(Long[] ids);


	public void downShopTest(Long[] ids);


	FyTest updateTestQuestions(Long id, Set<Long> qids, Double score);

}
