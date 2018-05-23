package com.zhitail.frame.util.jpa;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;



@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> 
    extends PagingAndSortingRepository<T, ID> {

    public T updateByUpdater(Updater<T> updater) ;
    public List<T> findListByFinder(Finder finder);
    public Pagination<T> findPageByFinder(Finder finder,int pageNo,int pageSize);
    public List<Object> findObjectListByFinder(Finder finder);
    public List<Map<String,Object>> findMapByFinder(Finder finder);
    public List<Object[]> findObjectArrayListByFinder(Finder finder);
    public int countQueryResult(Finder finder);
    public Double getAvgQueryResult(String sql);
    public List<Object> findObjectListBySql(String sql);
    public List<Object[]> findObjectListBySql2(String sql);
    public List<Map> getListMapBySQL(String sql);
    public Pagination<Long> findIdsByFinder(Finder finder,int pageNo,int pageSize);

}

