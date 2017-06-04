package com.max256.morpho.common.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

/**
 * BaseDao
 * 封装了常用功能的BaseDao基于hibernate5实现
 * 本系统可以同时支持hibernate5和mybatis 
 * 其中mybtais是必备 hibernate5根据您的需要可以选择使用也可以不使用
 * 如果使用hibernate5您可能会用到本类 本类封装了hibernate5的常用api
 * @author fbf
 *
 * @param <T> 实体类
 */
public interface BaseDao<T> {

	/**
	 * 获取序列的下一个值
	 * 
	 * @param seqName
	 *            序列名称
	 * @return 序列下一个值
	 */
	public Long getSeqNextval(String seqName);


	/**
	 * 通过sql获取单个数据
	 * 
	 * @param sql语句
	 * @return
	 */
	public Object getObjectBySql(String sql);


	/**
	 * 从sql语句中获取String
	 * 
	 * @param sql
	 *            sql语句
	 * @return
	 */
	public String getStringBySql(String sql);


	/**
	 * 从sql语句中获取String
	 * 
	 * @param sql
	 *            sql语句
	 * @return
	 */
	public long getLongBySql(String sql);


	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable save(T o);


	/**
	 * 保存对象列表
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable saveList(List<T> o);


	/**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void delete(T o);


	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void update(T o);


	/**
	 * 保存或更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void saveOrUpdate(T o);


	/**
	 * 通过主键获得对象
	 * 
	 * @param c
	 *            类名.class
	 * @param id
	 *            主键
	 * @return 对象
	 */
	public T getById(Class<T> c, Serializable id);


	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 对象
	 */
	public T getByHql(String hql);


	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 对象
	 */
	public T getByHql(String hql, Map<String, Object> params);


	/**
	 * 获得对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @return List
	 */
	public List<T> findByHql(String hql);


	/**
	 * 获得对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return List
	 */
	public List<T> findByHql(String hql, Map<String, Object> params);


	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            要显示第几页
	 * @param size
	 *            每页显示多少条
	 * @return List
	 */
	public List<T> findByHql(String hql, int page, int size);


	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param size
	 *            每页显示多少条
	 * @return List
	 */
	public List<T> findByHql(String hql, Map<String, Object> params, int page, int size);
	
	//------------------
	/**
	 * 获得对象列表
	 * 
	 * @param hql
	 *            HQL语句
     * @param resultTransformerCalssName 结果集需要转化为另个POJO的类
	 * @return List 里实际是resultTransformerCalss类型的 需要时自行转型
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(String hql,Class resultTransformerCalss);


	/**
	 * 获得对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param resultTransformerCalssName 结果集需要转化为另个POJO的类
	 * @return List 里实际是resultTransformerCalss类型的 需要时自行转型
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(String hql, Map<String, Object> params,Class resultTransformerCalss);


	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            要显示第几页
	 * @param size
	 *            每页显示多少条
	 * @param resultTransformerCalssName 结果集需要转化为另个POJO的类
	 * @return List 里实际是resultTransformerCalss类型的 需要时自行转型
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(String hql, int page, int size,Class resultTransformerCalss);


	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param size
	 *            每页显示多少条
	 * @param resultTransformerCalssName 结果集需要转化为另个POJO的类
	 * @return List 里实际是resultTransformerCalss类型的 需要时自行转型
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(String hql, Map<String, Object> params, int page, int size,Class resultTransformerCalss);
	//---------------------------------------------


	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 结果集
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findBySql(String sql);


	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            要显示第几页
	 * @param size
	 *            每页显示多少条
	 * @return 结果集
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findBySql(String sql, int page, int size);


	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 结果集
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findBySql(String sql, Map<String, Object> params);


	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param size
	 *            每页显示多少条
	 * @return 结果集
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int size);


	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 响应结果数目
	 */
	public int executeHql(String hql);


	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 响应结果数目
	 */
	public int executeHql(String hql, Map<String, Object> params);


	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 响应行数
	 */
	public int executeSql(String sql);


	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 响应行数
	 */
	public int executeSql(String sql, Map<String, Object> params);


	/**
	 * 统计数目
	 * 
	 * @param hql
	 *            HQL语句(select count(*) from T)
	 * @return long
	 */
	public long countByHql(String hql);


	/**
	 * 统计数目
	 * 
	 * @param hql
	 *            HQL语句(select count(*) from T where xx = :xx)
	 * @param params
	 *            参数
	 * @return long
	 */
	public long countByHql(String hql, Map<String, Object> params);


	/**
	 * 统计
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 数目
	 */
	public BigInteger countBySql(String sql);


	/**
	 * 统计
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 数目
	 */
	public BigInteger countBySql(String sql, Map<String, Object> params);


	public List<T> findByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders);


	public List<T> findByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders, int page, int size);


	public List<?> sumByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders, Projection projection);


	public List<?> sumByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders, Projection projection, int page, int size);


	public Long countByCriteria(Class<T> persistentClass, List<Criterion> criterions, List<Order> orders);

}
