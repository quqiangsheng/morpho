package com.max256.morpho.common.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.urule.Utils;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.sys.service.SysUserService;

/**
 * UruleTest单元测试 调用示例
 * 参考http://wiki.bsdn.org/pages/viewpage.action?pageId=75071581
 * 
 * @author fbf
 *
 */
@Component(value = "uruleTest")
public class UruleTest {

	@Autowired
	SysUserService sysUserService;

	// 调用知识包
	public void doTestZhiShiBao() throws Exception {
		// 从Spring中获取KnowledgeService接口实例
		KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
		// 通过KnowledgeService接口获取指定的资源包"test123"
		KnowledgePackage knowledgePackage = service.getKnowledge("demo/知识包");
		// 通过取到的KnowledgePackage对象创建KnowledgeSession对象
		KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
		List<SysUser> selectAll = sysUserService.selectAll();
		// 将业务数据对象Employee插入到KnowledgeSession中
		session.insert(selectAll.get(0));
		// 执行所有满足条件的规则
		session.fireRules();

	}

	// 调用规则流
	public void doTestRuleFlow() throws Exception {
		// 从Spring中获取KnowledgeService接口实例
		KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
		// 通过KnowledgeService接口获取指定的资源包"test123"
		KnowledgePackage knowledgePackage = service.getKnowledge("demo/知识包");
		// 通过取到的KnowledgePackage对象创建KnowledgeSession对象
		KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

		List<SysUser> selectAll = sysUserService.selectAll();
		// 将业务数据对象Employee插入到KnowledgeSession中
		session.insert(selectAll.get(0));
		// 执行所有满足条件的规则
		Map<String, Object> parameter = new HashMap<String, Object>();
		// 开始规则流并设置参数
		session.startProcess("决策流", parameter);

	}

}
