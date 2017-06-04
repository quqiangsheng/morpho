package com.max256.morpho.common.web.listener;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

import com.max256.morpho.common.annotation.SysResHandle;
import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.entity.SysResource;
import com.max256.morpho.common.util.ReflectUtils;
import com.max256.morpho.sys.service.SysResourceService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 自定义spring容器启动监听器 
 * 功能:当spring 核心容器加载完成时，
 * 扫描控制器层的注解情况 找到符合条件的控制器进行资源权限的初始化
 * 开发人员使用 生产环境请勿使用 除非你非常清楚在做什么
 * @author fbf
 * 
 */
public class SysResHandlerSpringContextListener implements ApplicationListener<ContextRefreshedEvent> {

	// 日志
	private static final Logger logger = LoggerFactory.getLogger(SysResHandlerSpringContextListener.class);
	//是否生成到数据库新的资源 默认不生成
	public  boolean generateFlag =false;
	

	public boolean isGenerateFlag() {
		return generateFlag;
	}

	public void setGenerateFlag(boolean generateFlag) {
		this.generateFlag = generateFlag;
	}

	@Resource
	private SysResourceService sysResourceService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//先预置根资源
		int sysResourceCount=sysResourceService.selectCount(new SysResource());
		if(sysResourceCount==0){
			//没有数据时先插入一条数据
			SysResource firstSysResource=new SysResource();
			firstSysResource.setResourceId(0);
			firstSysResource.setUuid("0");
			firstSysResource.setResourceName("资源树根");
			firstSysResource.setIsValid("1");
			firstSysResource.setResourceType(Constants.MENU);;
			firstSysResource.setParentId(-1);
			firstSysResource.setPermission("*");
			firstSysResource.setResourceUrl("");
			sysResourceService.insert(firstSysResource);
		}
		
		if (event.getApplicationContext().getParent() != null) {		
			// root application context 没有parent，他就是老大.
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			Map<String, Object> beansWithAnnotation = event.getApplicationContext().getBeansWithAnnotation(Controller.class);
			 for (String key : beansWithAnnotation.keySet()) {
				 	
				   //反射工具类得到被代理过的class之前的class
				   List<SysResource> sysResource = SysResHandle.getSysResourceListByClass(ReflectUtils.getRealClass(beansWithAnnotation.get(key))) ;
				   //如果sysResource为空则说明不符合规则不处理跳过
				   if(sysResource==null||sysResource.size()==0){
					   if(generateFlag){
						   logger.info("资源权限扫描结果:["+ key  + beansWithAnnotation.get(key)+"]不包含资源权限");	
					   }
					   continue;
				   }
				   //遍历所有的资源
				   for (Iterator<SysResource> iterator = sysResource.iterator(); iterator.hasNext();) {
					   //根据不同情况进行id和主键的设置
					   SysResource sysResource2 =  iterator.next();					
					  
						if(generateFlag){
							//得到一个连续的id号 用于 id uuid 设置
							//资源id号自增 需要在这里查询出来 10000以内的 手动设置请设置10000以上开始编号 10000以下为系统自动编号区
							Example example=new Example(SysResource.class);
							Criteria criteria=example.createCriteria();
							criteria.andLessThan("uuid", 10000);
							example.orderBy("resourceId").desc();
							List<SysResource> find=sysResourceService.selectByExample(example);
							SysResource sr=find.get(0);
							Integer newId=(sr.getResourceId()+1);
							//编程时没有指定id 根据数据库取最新的没用过的顺序号赋值
							if(sysResource2.getResourceId()==null){
								sysResource2.setResourceId(newId);
								sysResource2.setUuid(newId.toString());
								sysResourceService.insert(sysResource2);
							}else{
								//编程时指定了id号 这里要判断这个id号是否被使用过；
								SysResource param1=new SysResource();
								param1.setResourceId(sysResource2.getResourceId());
								SysResource findIsUse=sysResourceService.selectOne(param1);
								if(findIsUse!=null){
									//不为空说，则被使用过 不使用它指定的 使用发号器指定的
									sysResource2.setResourceId(newId);
									sysResource2.setUuid(newId.toString());
									sysResourceService.insert(sysResource2);
								}else{
									//为空 说明编程时指定的可以用则使用编程时指定的
									sysResource2.setUuid(sysResource2.getResourceId().toString());
									sysResourceService.insert(sysResource2);
								}
							}
							logger.info("资源权限扫描结果:["+ key  + beansWithAnnotation.get(key)+"]包含资源权限共["+sysResource.size()+"]条，["+sysResource2.toString()+"]全部已保存到数据库");	
						}
				   }   
			}


	}

}
}