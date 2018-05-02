package com.zhitail.app.manager;

import com.zhitail.app.entity.FyQuestion;
import com.zhitail.app.entity.FySensitive;
import com.zhitail.frame.util.page.Pagination;

public interface FyQuestionMng {

	public Pagination<FyQuestion> getPage(Integer pageNo, Integer pageSize,
			FyQuestion search);

	public void delete(Long[] ids);

	public FyQuestion update(FyQuestion question);

	public FyQuestion save(FyQuestion question);

	public FyQuestion findById(Long id);

}
