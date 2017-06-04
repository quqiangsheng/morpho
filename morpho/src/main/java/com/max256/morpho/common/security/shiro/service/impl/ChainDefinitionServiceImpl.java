package com.max256.morpho.common.security.shiro.service.impl;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.Resource;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.max256.morpho.common.exception.SystemException;
import com.max256.morpho.common.security.shiro.service.ChainDefinitionService;

/**
 *  ChainDefinitionServiceImpl
 *  初始化拦截shiro列表从读取的配置文件中
 */
public class ChainDefinitionServiceImpl implements ChainDefinitionService{
	
	private static final Logger logger = LoggerFactory.getLogger(ChainDefinitionServiceImpl.class);
	
	@Resource
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	/*
	 * 回车换行符
	 */
	private static final String CRLF = "\r\n";


	@Override
	public String initFilterChainDefinitions() {
		final StringBuilder chain = new StringBuilder();
		Map<String, String> chainMap = initChainDefinitionsMap();
		chainMap.forEach(new BiConsumer<String, String>() {
			@Override
			public void accept(String key, String value) {
				chain.append(key).append(" = ").append(value).append(CRLF);
			}
		});
		return chain.toString();
	}

	@Override
	public synchronized void reloadFilterChainDefinitions() {
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();

			PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
					.getFilterChainResolver();
			DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
					.getFilterChainManager();
	
			// 清空老的权限控制
			manager.getFilterChains().clear();
	
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
			shiroFilterFactoryBean.setFilterChainDefinitions(initFilterChainDefinitions());
			// 重新构建生成
			Map<String, String> chains = shiroFilterFactoryBean
					.getFilterChainDefinitionMap();
			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue().trim().replace(" ", "");
				manager.createChain(url, chainDefinition);
			}
		} catch (Exception e) {
			logger.error("getShiroFilter from shiroFilterFactoryBean error!", e);
			throw new SystemException("get ShiroFilter from shiroFilterFactoryBean error!");
		}
	}

	@Override
	public Map<String, String> initChainDefinitionsMap() {
		Map<String, String> chainMap = new LinkedHashMap<String, String>();
		try {
			String shiroConfig = ChainDefinitionServiceImpl.class.getResource("/shiroAuth.xml").toURI().getPath();
			//解析xml
			SAXReader sax = new SAXReader();
			Document dom = null;
			try {
				 dom = sax.read(new File(shiroConfig));
			} catch (DocumentException e) {
				//
			}
			// 获取根元素
		    Element root = dom.getRootElement();
		    @SuppressWarnings("unchecked")
			Iterator<Element> it = root.elementIterator();
		    //得到define
		    while (it.hasNext()) {
		        Element define = it.next();
		        String url = define.elementText("url");
		        String filter = define.elementText("filter");
		        String desc = define.elementText("desc");
		        if(logger.isDebugEnabled()){
		        	logger.debug("加载shiro filter配置 url:"+url+" filter:"+filter +" 配置说明:"+desc);
		        }
		        chainMap.put(url, filter);
		    }
		   
		} catch (URISyntaxException e) {
			logger.error("获取文件shiro默认权限配置文件路径", e);
		} 
		return chainMap;
	}

	
}
