package com.max256.morpho.common.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.max256.morpho.common.service.hibernate.HibernateBaseService;

/**
 * 基础业务逻辑对外统一接口
 * 其他service层独有方法请在各自具体的接口中定义
 * 本系统可以和hibernate同时使用 
 * 如果需要同时使用  请继承hibernate5的通用接口
 * (可选 如果要同时用hibernate 请在该类继承HibernateBaseService,如果不需要hibernate,请不要继承HibernateBaseService)!!!
 * @author fbf
 */
public interface BaseService<T> extends HibernateBaseService<T>{

	/**按主键更新实体
	 * @param entity
	 * @return
	 */
	public Integer updateByPrimaryKey(T entity);

	/**按不为空的各属性拼接查询条件查找
	 * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
	 * @param entity
	 * @return
	 */
	public List<T> select(T entity);

	/**按条件查找唯一的实体
	 * @param entity
	 * @return
	 */
	public T selectOne(T entity);

	/**新增实体
	 * 插入一条数据
	 * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
	 * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
	 * @param entity
	 * @return
	 */
	public Integer insert(T entity);

	/**根据主键查找实体
	 * 必须保证结果唯一
	 * 单个字段做主键时,可以直接写主键的值
	 * 联合主键时,key可以是实体类,也可以是Map
	 * @param key
	 * @return
	 */
	public T selectByPrimaryKey(Object key);

	public Integer updateByExampleSelective(T entity, Object example);

	/**
	 * 根据主键删除实体
	 * @param key
	 * 单个字段做主键时,可以直接写主键的值
	 * 联合主键时,key可以是实体类,也可以是map
	 * @return
	 */
	public Integer deleteByPrimaryKey(Object key);

	/**查找所有实体
	 * @return
	 */
	public List<T> selectAll();

	/**
	 * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段
	 * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
     * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
	 * @param entity
	 * @return
	 */
	public Integer insertSelective(T entity);

	/**根据主键进行更新
	 * 这里只会更新不是null的数据
	 * @param entity
	 * @return
	 */
	public Integer updateByPrimaryKeySelective(T entity);

	public Integer deleteByExample(Object example);

	public List<T> selectByExampleAndRowBounds(Object example,RowBounds rowBounds);

	/**统计实体个数
	 * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
	 * @param entity
	 * @return
	 */
	public Integer selectCount(T entity);

	/**删除实体
	 * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
	 * @param entity
	 * @return
	 */
	public Integer delete(T entity);

	public Integer updateByExample(T entity, Object example);

	public List<T> selectByExample(Object example);

	public List<T> selectByRowBounds(T entity, RowBounds rowBounds);

	public Integer selectCountByExample(Object example);

}

/*
 * 4. 泛型(实体类)<T>的类型必须符合要求 实体类按照如下规则和数据库表进行转换,注解全部是JPA中的注解:
 * 
 * 表名默认使用类名,驼峰转下划线,如UserInfo默认对应的表名为user_info.
 * 
 * 表名可以使用@Table(name = "tableName")进行指定,对不符合第一条默认规则的可以通过这种方式指定表名.
 * 
 * 字段默认和@Column一样,都会作为表字段,表字段默认为Java对象的Field名字驼峰转下划线形式.
 * 
 * 可以使用@Column(name = "fieldName")指定不符合第3条规则的字段名
 * 
 * 使用@Transient注解可以忽略字段,添加该注解的字段不会作为表字段使用.
 * 
 * 建议一定是有一个@Id注解作为主键的字段,可以有多个@Id注解的字段作为联合主键.
 * 
 * 默认情况下,实体类中如果不存在包含@Id注解的字段,所有的字段都会作为主键字段进行使用(这种效率极低).
 * 
 * 实体类可以继承使用,可以参考测试代码中的com.github.abel533.model.UserLogin2类.
 * 
 * 由于基本类型,如int作为实体类字段时会有默认值0,而且无法消除,所以实体类中建议不要使用基本类型.
 * 
 * 除了上面提到的这些,Mapper还提供了序列(支持Oracle)、UUID(任意数据库,字段长度32)、主键自增(类似Mysql,Hsqldb)三种方式，
 * 其中序列和UUID可以配置多个，主键自增只能配置一个。
 * 
 * 这三种方式不能同时使用,同时存在时按照 序列>UUID>主键自增的优先级进行选择.下面是具体配置方法:
 * 
 * 使用序列可以添加如下的注解:
 * 
 * //可以用于数字类型,字符串类型(需数据库支持自动转型)的字段
 * 
 * @SequenceGenerator(name="Any",sequenceName="seq_userid")
 * 
 * @Id private Integer id; 使用UUID时:
 * 
 * //可以用于任意字符串类型长度超过32位的字段
 * 
 * @GeneratedValue(generator = "UUID") private String countryname; 使用主键自增:
 * 
 * //不限于@Id注解的字段,但是一个实体类中只能存在一个(继承关系中也只能存在一个)
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
 */
