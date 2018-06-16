package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.app.entity.FyUser;
import com.zhitail.frame.util.page.Pagination;

public interface FyQuestionMng {



	public void delete(Long[] ids,FyUser user);

	public FyQuestion update(FyQuestion question);

	public FyQuestion save(FyQuestion question,FyUser user);

	public FyQuestion findById(Long id);

	public Pagination<FyQuestion> getPage(String sorter, Long[] alreadyIds, Integer pageNo, Integer pageSize,
			Long userId, String title, String type, String difficulty, String status, String tag);

	public List<FyQuestion> findByIds(Long[] qids);
	public void recycle(Long[] ids) ;

	public Pagination<FyQuestion> getRecyclePage(String sorter, Integer pageNo, Integer pageSize, Long userId, String title,
			String tag);

	public void recovery(Long[] ids);

}
