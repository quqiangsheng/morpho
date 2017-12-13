package com.max256.morpho.common.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.max256.morpho.common.dao.BaseDao;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
@Repository("baseDao")
@Primary
/**
 * BaseDaoImpl 封装了常用功能的BaseDao基于hibernate5实现 本系统可以同时支持hibernate5和mybatis
 * 其中mybtais是必备 hibernate5根据您的需要可以选择使用也可以不使用 如果使用hibernate5您可能会用到本类
 * 本类封装了hibernate5的常用api
 * 
 * @author fbf
 *
 * @param <T>
 *            实体类
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

	/**
	 * 注入hibernate5的sessionFactory
	 */
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取序列的下一个值
	 * 
	 * @param seqName
	 *            序列名称
	 * @return 序列下一个值 取不到返回null
	 */
	public Long getSeqNextval(String seqName) {
		String sql = "select " + seqName + ".Nextval from dual";
		BigDecimal nextval = (BigDecimal) getObjectBySql(sql);
		if (nextval != null) {
			return nextval.longValue();
		} else {
			return null;
		}

	}

	/**
	 * 通过sql获取单个数据
	 * 
	 * @param sql语句
	 * @return 返回Object 取不到返回null
	 */
	public Object getObjectBySql(String sql) {
		List<?> list = getSession().createSQLQuery(sql).list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 从sql语句中获取String
	 * 
	 * @param sql
	 *            sql语句
	 * @return String 取不到返回null
	 */
	public String getStringBySql(String sql) {
		Object obj = getObjectBySql(sql);
		if (obj instanceof String) {
			return (String) obj;
		} else {
			return null;
		}
	}

	/**
	 * 从sql语句中获取long
	 * 
	 * @param sql
	 *            sql语句
	 * @return long类型 如果是null或者结果不是数字则返回null
	 */
	@SuppressWarnings("null")
	public long getLongBySql(String sql) {
		Object find = getObjectBySql(sql);
		if (find == null) {
			return (Long) null;
		}
		if (find instanceof Number) {
			return NumberUtils.toLong(find.toString());
		}
		// 判断是否是Long
		Boolean isNum = NumberUtils.isNumber((String) find);
		if (isNum) {
			return NumberUtils.toLong(find.toString());
		} else {
			return (Long) null;
		}

	}

	/**
	 * 获得当前事务的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * 保存新的实体对象
	 */
	@Override
	public Serializable save(T o) {
		if (o != null) {
			return getSession().save(o);
		}
		return null;
	}

	/*
	 * 保存新的实体对象列表
	 */
	@Override
	public Serializable saveList(List<T> ol) {
		Session se = getSession();
		if (ol != null && !ol.isEmpty()) {
			for (T o : ol) {
				se.save(o);
			}
		}
		return null;
	}

	@Override
	public T getById(Class<T> c, Serializable id) {
		return (T) getSession().get(c, id);
	}

	@Override
	public T getByHql(String hql) {
		Query q = getSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void delete(T o) {
		if (o != null) {
			getSession().delete(o);
			getSession().flush();
		}
	}

	@Override
	public void update(T o) {
		if (o != null) {
			getSession().update(o);
			getSession().flush();
		}
	}

	@Override
	public void saveOrUpdate(T o) {
		if (o != null) {
			getSession().saveOrUpdate(o);
			getSession().flush();
		}
	}

	@Override
	public List<T> findByHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<T> findByHql(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return q.list();
	}

	@Override
	public List<T> findByHql(String hql, Map<String, Object> params, int page, int size) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return q.setFirstResult((page > 0 ? page - 1 : 0) * size).setMaxResults(size).list();
	}

	@Override
	public List<T> findByHql(String hql, int page, int size) {
		Query q = getSession().createQuery(hql);
		return q.setFirstResult((page > 0 ? page - 1 : 0) * size).setMaxResults(size).list();
	}

	@Override
	public List findByHql(String hql, Class resultTransformerCalss) {
		Query q = getSession().createQuery(hql);
		if (null != resultTransformerCalss && resultTransformerCalss instanceof Class) {
			q.setResultTransformer(Transformers.aliasToBean(resultTransformerCalss));
		}
		return q.list();
	}

	@Override
	public List findByHql(String hql, Map<String, Object> params, Class resultTransformerCalss) {
		Query q = getSession().createQuery(hql);
		if (null != resultTransformerCalss && resultTransformerCalss instanceof Class) {
			q.setResultTransformer(Transformers.aliasToBean(resultTransformerCalss));
		}
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return q.list();
	}

	@Override
	public List findByHql(String hql, int page, int size, Class resultTransformerCalss) {
		Query q = getSession().createQuery(hql);
		if (null != resultTransformerCalss && resultTransformerCalss instanceof Class) {
			q.setResultTransformer(Transformers.aliasToBean(resultTransformerCalss));
		}
		return q.setFirstResult((page > 0 ? page - 1 : 0) * size).setMaxResults(size).list();
	}

	@Override
	public List findByHql(String hql, Map<String, Object> params, int page, int size, Class resultTransformerCalss) {

		Query q = getSession().createQuery(hql);
		if (null != resultTransformerCalss && resultTransformerCalss instanceof Class) {
			q.setResultTransformer(Transformers.aliasToBean(resultTransformerCalss));
		}
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return q.setFirstResult((page > 0 ? page - 1 : 0) * size).setMaxResults(size).list();

	}

	@Override
	public long countByHql(String hql) {
		Query q = getSession().createQuery(hql);
		return (long) q.uniqueResult();
	}

	@Override
	public long countByHql(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return (long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return q.executeUpdate();
	}

	@Override
	public List<Map> findBySql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map> findBySql(String sql, int page, int size) {
		SQLQuery q = getSession().createSQLQuery(sql);
		return q.setFirstResult((page > 0 ? page - 1 : 0) * size).setMaxResults(size)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int size) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return q.setFirstResult((page > 0 ? page - 1 : 0) * size).setMaxResults(size)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public int executeSql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return q.executeUpdate();
	}

	@Override
	public BigInteger countBySql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	@Override
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					q.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(key, (Object[]) obj);
				} else {
					q.setParameter(key, obj);
				}
			}
		}
		return (BigInteger) q.uniqueResult();
	}

	@Override
	public List<T> findByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		return criteria.list();
	}

	@Override
	public List<T> findByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders, int page,
			int size) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		return criteria.setFirstResult((page > 0 ? page - 1 : 0) * size).setMaxResults(size).list();
	}

	@Override
	public List<?> sumByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders,
			Projection projection) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		criteria.setProjection(projection);
		return criteria.list();
	}

	@Override
	public List<?> sumByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders,
			Projection projection, int page, int size) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		criteria.setProjection(projection);
		return criteria.setFirstResult((page > 0 ? page - 1 : 0) * size).setMaxResults(size).list();
	}

	@Override
	public Long countByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		for (Order order : orders) {
			criteria.addOrder(order);
		}

		Projection projection = Projections.projectionList().add(Projections.rowCount(), "rowCount");
		criteria.setProjection(projection);

		List<?> l = criteria.list();
		if (l != null && l.size() > 0) {
			return new Long(l.get(0).toString());
		}
		return null;
	}

}
