package com.zhitail.app.manager;

import java.util.List;

import com.zhitail.app.entity.FyOrder;

import com.zhitail.frame.util.page.Pagination;

public interface FyOrderMng {

	public Pagination<FyOrder> getPage( Integer pageNo, Integer pageSize,
			FyOrder search);

	public FyOrder update(FyOrder user);

	public FyOrder save(FyOrder user);

	public void delete(Long[] ids);

	public FyOrder findById(Long id);


	public List<FyOrder> findByIds(Long[] ids);


}
