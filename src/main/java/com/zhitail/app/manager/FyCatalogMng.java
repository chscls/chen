package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyCatalog;
import com.zhitail.frame.util.page.Pagination;

public interface FyCatalogMng {

	public Pagination<FyCatalog> getPage(Integer pageNo, Integer pageSize,
			FyCatalog search);

	public FyCatalog save(FyCatalog bean);

	public FyCatalog update(FyCatalog bean);

	public void delete(Long[] ids);

	public List<FyCatalog> findByIds(Long[] ids);

}
