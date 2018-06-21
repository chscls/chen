package com.zhitail.frame.util.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.zhitail.frame.util.hibernate.Finder;
import com.zhitail.frame.util.hibernate.MyBeanUtils;
import com.zhitail.frame.util.hibernate.Updater;
import com.zhitail.frame.util.page.Pagination;


public class BaseRepositoryImpl<T, ID extends Serializable> 
    extends SimpleJpaRepository<T, ID> 
    implements BaseRepository<T, ID> {

    private final Class<T> domainClass;
    private final EntityManager em;
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
        this.em = entityManager;
    }

 
    
    @Transactional(readOnly = false)
    @SuppressWarnings("unchecked")
	public T updateByUpdater(Updater<T> updater) {
		ClassMetadata cm = getSession().getSessionFactory().getClassMetadata(domainClass);
		T bean = updater.getBean();
		
		//T po = (T) getSession().get(getEntityClass(),cm.getIdentifier(bean, POJO));
		T po = (T) getSession().get(domainClass,cm.getIdentifier(bean,(SessionImplementor)getSession()));
	 updaterCopyToPersistentObject(updater, po, cm);
		
	// em.createQuery("update Account bean set bean.password=99999 where bean.id=1").executeUpdate();
		return po;
	}

	private void updaterCopyToPersistentObject(Updater<T> updater, T po,
			ClassMetadata cm) {
		// TODO Auto-generated method stub
		String[] propNames = cm.getPropertyNames();
		String identifierName = cm.getIdentifierPropertyName();
		T bean = updater.getBean();
		Object value;
		for (String propName : propNames) {
			
			if (propName.equals(identifierName)) {
				continue;
			}
			try {
				value = MyBeanUtils.getSimpleProperty(bean, propName);
				
				if (!updater.isUpdate(propName, value)) {
					continue;
				}
				//cm.setPropertyValue(po, propName, value, POJO);
				
				cm.setPropertyValue(po, propName, value);
			} catch (Exception e) {
				throw new RuntimeException(
						"copy property to persistent object failed: '"
								+ propName + "'", e);
			}
		}
		
	}

	private Session getSession() {
		// TODO Auto-generated method stub
		return this.em.unwrap(org.hibernate.Session.class);
	}



	


	public List<T> findListByFinder(Finder finder) {
		// TODO Auto-generated method stub
		Query query = finder.createQuery(getSession());
		List<T> list = query.list();
		return list;
	}



	public Pagination<T> findPageByFinder(Finder finder,int pageNo,int pageSize) {
		// TODO Auto-generated method stub
		int totalCount = countQueryResult(finder);
		Pagination<T> p = new Pagination<T>(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList<T>());
			return p;
		}
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		List<T> list = query.list();
		p.setList(list);
		return p;
	}
	
	public int countQueryResult(Finder finder) {
		Query query = getSession().createQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		return ((Number) query.iterate().next()).intValue();
	}

	public Double getAvgQueryResult(String sql) {
		Query query =getSession().createQuery(sql);
		return query.uniqueResult()==null?0:(Double)query.uniqueResult();
	}


	public List<Map<String,Object>> findMapByFinder(Finder finder) {
		// TODO Auto-generated method stub
		Query query = finder.createQuery(getSession());
		
		List<Map<String,Object>> result  = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
			return result ;
		
		
	}



	public List<Object> findObjectListByFinder(Finder finder) {
		// TODO Auto-generated method stub
		Query query = finder.createQuery(getSession());
		List<Object> list = query.list();
		return list;
	}



	public List<Object[]> findObjectArrayListByFinder(Finder finder) {
		// TODO Auto-generated method stub
		Query query = finder.createQuery(getSession());
		List<Object[]> list = query.list();
		return list;
	}


public List<Object> findObjectListBySql(String sql) {
		
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List<Object> list = query.list(); 
		
		return list;
	}
public List<Object[]> findObjectListBySql2(String sql) {
	
	SQLQuery query = this.getSession().createSQLQuery(sql);
	
	List<Object[]> list = query.list(); 
	
	return list;
}


public List<Map> getListMapBySQL(String sql) {
	// TODO Auto-generated method stub
	List<Map> list=
			this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	
	return list;
}



@Override
public Pagination<Long> findIdsByFinder(Finder finder, int pageNo, int pageSize) {
	// TODO Auto-generated method stub
	int totalCount = countQueryResult(finder);
	Pagination<Long> p = new Pagination<Long>(pageNo, pageSize, totalCount);
	if (totalCount < 1) {
		p.setList(new ArrayList<Long>());
		return p;
	}
	Query query = getSession().createQuery(finder.getOrigHql());
	finder.setParamsToQuery(query);
	query.setFirstResult(p.getFirstResult());
	query.setMaxResults(p.getPageSize());
	if (finder.isCacheable()) {
		query.setCacheable(true);
	}
	List<Long> list = query.list();
	p.setList(list);
	return p;
}



@Override
public Pagination<String> findCodesByFinder(Finder finder, int pageNo, int pageSize) {
	// TODO Auto-generated method stub
	int totalCount = countQueryResult(finder);
	Pagination<String> p = new Pagination<String>(pageNo, pageSize, totalCount);
	if (totalCount < 1) {
		p.setList(new ArrayList<String>());
		return p;
	}
	Query query = getSession().createQuery(finder.getOrigHql());
	finder.setParamsToQuery(query);
	query.setFirstResult(p.getFirstResult());
	query.setMaxResults(p.getPageSize());
	if (finder.isCacheable()) {
		query.setCacheable(true);
	}
	List<String> list = query.list();
	p.setList(list);
	return p;
}



@Override
public Pagination<Object> findObjectPageByFinder(Finder finder, Integer pageNo, Integer pageSize) {
	// TODO Auto-generated method stub
	int totalCount = countQueryResult(finder);
	Pagination<Object> p = new Pagination<Object>(pageNo, pageSize, totalCount);
	if (totalCount < 1) {
		p.setList(new ArrayList<Object>());
		return p;
	}
	Query query = getSession().createQuery(finder.getOrigHql());
	finder.setParamsToQuery(query);
	query.setFirstResult(p.getFirstResult());
	query.setMaxResults(p.getPageSize());
	if (finder.isCacheable()) {
		query.setCacheable(true);
	}
	List<Object> list = query.list();
	p.setList(list);
	return p;
}


}

