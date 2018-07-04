package com.zhitail.app.manager;

import java.util.List;
import java.util.Set;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyTest;
import com.zhitail.app.entity.FyUser;
import com.zhitail.frame.util.page.Pagination;

public interface FyQuestionMng {
	
	
	public void delete(Long[] ids,FyUser user);

	public FyQuestion update(FyQuestion question, Boolean isChange);

	public FyQuestion save(FyQuestion question,FyUser user);

	public FyQuestion findById(Long id);

	public Pagination<FyQuestion> getPage(String sorter, Long[] alreadyIds, Integer pageNo, Integer pageSize,
			Long userId, String title, String type, String difficulty, String status, String tag);

	public List<FyQuestion> findByIds(Long[] qids);
	public void recycle(Long[] ids, FyUser user) ;

	public Pagination<FyQuestion> getRecyclePage(String sorter, Integer pageNo, Integer pageSize, Long userId, String title,
			String tag);

	public void recovery(Long[] ids, FyUser user);

	public void clear();

	public Long getCount(Long userId, Boolean isRecycle);

	public FyQuestion updateQuestionQuestions(Long id, Set<Long> qids,Double rate);

}
