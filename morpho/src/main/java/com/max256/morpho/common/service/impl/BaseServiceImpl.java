package com.max256.morpho.common.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;

import com.max256.morpho.common.service.BaseService;
import com.max256.morpho.common.service.hibernate.impl.HibernateBaseServiceImpl;

/**
 * 基础业务逻辑实现类 
 * 具体业务类请继承该类(BaseServiceImpl)
 * 基于mybatis抽象的通用接口
 * 和hibernate5的通用接口(可选 如果要同时用hibernate 请在该类继承HibernateBaseServiceImpl,如果不需要hibernate,请不要继承HibernateBaseServiceImpl)!!!
 * 其他service层独有方法请在各自具体的接口中定义
 * @author fbf
 */
public class BaseServiceImpl<T> extends HibernateBaseServiceImpl<T> implements BaseService<T> {
	
	/**
	 * slf4j 日志
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * mybatis的通用mapper和hibernate有异曲同工的作用
	 */
	@Autowired
    protected Mapper<T> mapper;

	public Mapper<T> getMapper() {
		return mapper;
	}
	
	public void setMapper(Mapper<T> mapper) {
		this.mapper = mapper;
	}

	@Override
	public Integer updateByPrimaryKey(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	@Override
	public List<T> select(T entity) { 
		return mapper.select(entity);
	}

	@Override
	public T selectOne(T entity) {
		return mapper.selectOne(entity);
	}

	@Override
	public Integer insert(T entity) {
		return mapper.insert(entity);
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public Integer updateByExampleSelective(T entity, Object example) {
		return mapper.updateByExampleSelective(entity,example);
	}

	@Override
	public Integer deleteByPrimaryKey(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public Integer insertSelective(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public Integer updateByPrimaryKeySelective(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public Integer deleteByExample(Object example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public List<T> selectByExampleAndRowBounds(Object example,RowBounds rowBounds) {
		return mapper.selectByExampleAndRowBounds(example,rowBounds);
	}

	@Override
	public Integer selectCount(T entity) {
		return mapper.selectCount(entity);
	}

	@Override
	public Integer delete(T entity) {
		return mapper.delete(entity) ;
	}

	@Override
	public Integer updateByExample(T entity, Object example) { 
		return mapper.updateByExample(entity,example);
	}

	@Override
	public List<T> selectByExample(Object example) {
		return mapper.selectByExample(example);
	}

	@Override
	public List<T> selectByRowBounds(T entity, RowBounds rowBounds) {
		return mapper.selectByRowBounds(entity,rowBounds);
	}

	@Override
	public Integer selectCountByExample(Object example) {
		return mapper.selectCountByExample(example);
	}
}
