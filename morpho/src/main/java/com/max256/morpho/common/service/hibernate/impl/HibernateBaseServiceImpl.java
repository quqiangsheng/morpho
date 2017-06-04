package com.max256.morpho.common.service.hibernate.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.max256.morpho.common.dao.BaseDao;
import com.max256.morpho.common.dto.DataGridReturnData;
import com.max256.morpho.common.service.hibernate.HibernateBaseService;


/**
 * hibernate基础业务逻辑实现类
 * 不允许直接使用 只能被继承
 * 不允许外部业务service直接继承
 * 内部使用 除非您明白您在做什么
 * BaseServiceImpl才是我们对外提供的一致性的接口
 * @author fbf
 * 
 * @param <T> 实体类型
 */
@Service
@Primary
public  class HibernateBaseServiceImpl<T> implements HibernateBaseService<T> {
	//日志
	private final Logger logger=LoggerFactory.getLogger(HibernateBaseServiceImpl.class);
	//实体类
	private Class<T> persistentClass;
	/**
	 * 注入hibernate dao
	 */
	@Autowired
	protected BaseDao<T> baseDao;
	
	
	public BaseDao<T> getBaseDao() {
		return baseDao;
	}


	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}


	/**
	 * 注入hibernateTemplate
	 */
	@Autowired
	public HibernateTemplate hibernateTemplate;


	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}


	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}


	@SuppressWarnings("unchecked")
	public HibernateBaseServiceImpl() {
		Type type = getClass().getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			type = getClass().getSuperclass().getGenericSuperclass();
		}
		if (type instanceof ParameterizedType) {
			Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
			if (trueType instanceof Class) {
				this.persistentClass = (Class<T>) trueType;
			}
		}	
	}


	/**
	 * 获取序列的下一个值
	 * 
	 * @param seqName
	 *            序列名称
	 * @return 序列下一个值
	 */
	@Override
	public Long getSeqNextvalByHibernate(String seqName) {
		return baseDao.getSeqNextval(seqName);
	}


	/**
	 * 通过sql获取单个数据
	 * 
	 * @param sql语句
	 * @return
	 */
	@Override
	public Object getObjectBySqlByHibernate(String sql) {
		return baseDao.getObjectBySql(sql);

	}


	/**
	 * 从sql语句中获取String
	 * 
	 * @param sql
	 *            sql语句
	 * @return
	 */
	@Override
	public String getStringBySqlByHibernate(String sql) {
		return baseDao.getStringBySql(sql);

	}


	/**
	 * 从sql语句中获取String
	 * 
	 * @param sql
	 *            sql语句
	 * @return
	 */
	@Override
	public long getLongBySqlByHibernate(String sql) {
		return baseDao.getLongBySql(sql);
	}


	@Override
	public Serializable saveByHibernate(T o) {
		return baseDao.save(o);
	}


	/**
	 * 保存对象列表
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	@Override
	public Serializable saveListByHibernate(List<T> o) {
		return baseDao.saveList(o);

	}


	@Override
	public void deleteByHibernate(T o) {
		baseDao.delete(o);
	}


	@Override
	public void updateByHibernate(T o) {
		baseDao.update(o);
	}


	@Override
	public void saveOrUpdateByHibernate(T o) {
		baseDao.saveOrUpdate(o);
	}


	@Override
	public T getByIdByHibernate(Serializable id) {
		return baseDao.getById(persistentClass, id);
	}


	@Override
	public T getByHql(String hql) {
		return baseDao.getByHql(hql);
	}


	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		return baseDao.getByHql(hql, params);
	}


	@Override
	public List<T> findListByHibernate(String hql) {
		return baseDao.findByHql(hql);
	}


	@Override
	public List<T> findListByHibernate(String hql, Map<String, Object> params) {
		return baseDao.findByHql(hql, params);
	}


	@Override
	public List<T> findListByHibernate(String hql, int page, int size) {
		return baseDao.findByHql(hql, page, size);
	}


	@Override
	public List<T> findListByHibernate(String hql, Map<String, Object> params, int page, int size) {
		return baseDao.findByHql(hql, params, page, size);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findByHql(String hql, Class resultTransformerCalss) {
		return baseDao.findByHql(hql, resultTransformerCalss);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findByHql(String hql, Map<String, Object> params,Class resultTransformerCalss) {
		return baseDao.findByHql(hql, params,resultTransformerCalss);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findByHql(String hql, int page, int size,Class resultTransformerCalss) {
		return baseDao.findByHql(hql, page,size,resultTransformerCalss);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findByHql(String hql, Map<String, Object> params, int page,int size, Class resultTransformerCalss) {	
		return baseDao.findByHql(hql, params,page,size,resultTransformerCalss);
	}



	@Override
	public Long countByHibernate() {
		if (persistentClass != null) {
			String hql = "select count(*) from " + persistentClass.getName();
			return baseDao.countByHql(hql);
		}
		return null;
	}


	@Override
	public Long countByHibernate(String hql) {
		return baseDao.countByHql(hql);
	}


	@Override
	public Long countByHibernate(String hql, Map<String, Object> params) {
		return baseDao.countByHql(hql, params);
	}


	@Override
	
	public int executeHql(String hql) {
		return baseDao.executeHql(hql);
	}


	@Override
	
	public int executeHql(String hql, Map<String, Object> params) {
		return baseDao.executeHql(hql, params);
	}


	@Override
	public List<?> findBySqlByHibernate(String sql) {
		return baseDao.findBySql(sql);
	}


	@Override
	public List<?> findBySqlByHibernate(String sql, int page, int size) {
		return baseDao.findBySql(sql, page, size);
	}


	@Override
	public List<?> findBySqlByHibernate(String sql, Map<String, Object> params) {
		return baseDao.findBySql(sql, params);
	}


	@Override
	public List<?> findBySqlByHibernate(String sql, Map<String, Object> params, int page, int size) {
		return baseDao.findBySql(sql, params, page, size);
	}


	@Override
	public int executeSqlByHibernate(String sql) {
		return baseDao.executeSql(sql);
	}


	@Override
	public int executeSqlByHibernate(String sql, Map<String, Object> params) {
		return baseDao.executeSql(sql, params);
	}


	@Override
	public BigInteger countBySqlByHibernate(String sql) {
		return baseDao.countBySql(sql);
	}


	@Override
	public BigInteger countBySqlByHibernate(String sql, Map<String, Object> params) {
		return baseDao.countBySql(sql, params);
	}


	@Override
	public List<T> findListByHibernate() {
		if (persistentClass != null) {
			String hql = "from " + persistentClass.getName();
			return baseDao.findByHql(hql);
		}
		return null;
	}


	@Override
	public List<T> findListByHibernate(int page, int size) {
		if (persistentClass != null) {
			String hql = "from " + persistentClass.getName();
			return baseDao.findByHql(hql, page, size);
		}
		return null;
	}


	@Override
	public DataGridReturnData<T> findGridDataByHibernate(int page, int size) {
		DataGridReturnData<T> data = new DataGridReturnData<T>();
		data.setPageNumber(page);
		data.setPageSize(size);
		return findGridDataByHibernate(data);
	}


	@Override
	public DataGridReturnData<T> findGridDataByHibernate(String hql, int page, int size) {
		DataGridReturnData<T> data = new DataGridReturnData<T>();
		data.setPageNumber(page);
		data.setPageSize(size);
		return findGridDataByHibernate(data, hql);
	}


	@Override
	public DataGridReturnData<T> findGridDataByHibernate(String hql, Map<String, Object> params, int page, int size) {
		DataGridReturnData<T> data = new DataGridReturnData<T>();
		data.setPageNumber(page);
		data.setPageSize(size);
		return findGridDataByHibernate(data, hql, params);
	}


	@Override
	public DataGridReturnData<T> findGridDataByHibernate(DataGridReturnData<T> data) {
		if (persistentClass != null) {
			String hql = "from " + persistentClass.getName();
			data.setRows(baseDao.findByHql(hql, data.getPageNumber(), data.getPageSize()));
		}
		data.setTotal(this.countByHibernate());
		return data;
	}


	@Override
	public DataGridReturnData<T> findGridDataByHibernate(DataGridReturnData<T> data, String hql) {
		data.setRows(baseDao.findByHql(hql, data.getPageNumber(), data.getPageSize()));
		if (StringUtils.trimToEmpty(hql).toLowerCase().startsWith("from")) {
			data.setTotal(this.countByHibernate("select count(*) " + hql));
		}
		return data;
	}


	@Override
	public DataGridReturnData<T> findGridDataByHibernate(DataGridReturnData<T> data, String hql, Map<String, Object> params) {
		data.setRows(baseDao.findByHql(hql, params, data.getPageNumber(), data.getPageSize()));
		if (StringUtils.trimToEmpty(hql).toLowerCase().startsWith("from")) {
			data.setTotal(this.countByHibernate("select count(*) " + persistentClass.getName()));
		}
		return data;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public DataGridReturnData findGridDataTransformerByHibernate(DataGridReturnData data, Class resultTransformerCalss,String hql, Map<String, Object> params) {
		data.setRows(baseDao.findByHql(hql, params, data.getPageNumber(), data.getPageSize(),resultTransformerCalss));
		
		return data;
	}


	@Override
	public List<T> findByCriteriaByHibernate(List<Criterion> criterions, List<Order> orders) {
		return this.baseDao.findByCriteria(persistentClass, criterions, orders);
	}


	@Override
	public DataGridReturnData<T> findByCriteriaByHibernate(DataGridReturnData<T> data, List<Criterion> criterions, List<Order> orders) {
		long rowCount = this.baseDao.countByCriteria(persistentClass, criterions, orders);
		data.setTotal(rowCount);
		if(logger.isDebugEnabled()){
			logger.debug("Count " + persistentClass.getSimpleName() + " : " + rowCount);
		}
		if (rowCount > 0) {
			/**
			 * 如果rowCount不为0，但是查询结果为0，则进行多次查询，直到加载到数据为止。
			 * 
			 * 发现是别的问题，暂不恢复，将尝试次数改到两次以下
			 */
			List<T> rows = null;
			int tryCount = 0;
			while ((rows == null || rows.size() == 0) && tryCount++ < 1) {
				if (data.getPageNumber() + data.getPageSize() < 1) {
					rows = baseDao.findByCriteria(persistentClass, criterions, orders);
				} else {
					rows = baseDao.findByCriteria(persistentClass, criterions, orders, data.getPageNumber(), data.getPageSize());
				}
			}
			if(logger.isDebugEnabled()){
				logger.debug("Load " + persistentClass.getSimpleName() + " " + tryCount + " time(s)" + " by findByCriteria with [" + criterions + "] ");	
			}
			data.setRows(rows);
		} else {
			data.setRows(new ArrayList<T>());
		}
		return data;
	}


	@Override
	public DataGridReturnData<T> sumByCriteriaByHibernate(DataGridReturnData<T> data, List<Criterion> criterions, List<Order> orders, Projection projection,
			Map<Object, Object> titles) {
		List<?> list = baseDao.sumByCriteria(persistentClass, criterions, orders, projection);

		for (Object object : list) {
			Map<Object, Object> m = new HashMap<>();
			Object[] objectArray = (Object[]) object;
			Object[] aliases = projection.getAliases();
			for (int i = 0; i < aliases.length && i < objectArray.length; i++) {
				m.put(aliases[i], objectArray[i]);
			}
			m.putAll(titles);
			data.addFooter(m);
		}

		return data;
	}
}
