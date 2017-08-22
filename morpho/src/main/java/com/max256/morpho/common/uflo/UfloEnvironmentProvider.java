package com.max256.morpho.common.uflo;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.bstek.uflo.env.EnvironmentProvider;

/**
 * SessionFactory及TransactionManager都是通过Spring环境注入；
 * getLoginUser方法用于返回当前登录用户，
 * 因为我们这里没有用户登录的概念，
 * 所以这里返回一个固定值“anonymous”；
 * getCategoryId方法返回null，
 * 表示不对流程进行分类处理。
 * @author fbf
 *
 */
@Component
public class UfloEnvironmentProvider implements EnvironmentProvider {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	@Resource(name="transactionManager")
    private PlatformTransactionManager platformTransactionManager;
 
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }
 
    public void setPlatformTransactionManager(
            PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }
    public String getCategoryId() {
        return null;
    }
    public String getLoginUser() {
        return "anonymous";
    }

}
