package com.max256.morpho.common.urule;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bstek.urule.console.DefaultUser;
import com.bstek.urule.console.User;
import com.bstek.urule.console.servlet.RequestContext;

/**
 * @author fbf
 * @since 2018年1月30日
 */
@Component(value="uruleCustomEnvironmentProvider")
public class CustomEnvironmentProvider implements com.bstek.urule.console.EnvironmentProvider {

	@Override
	public User getLoginUser(RequestContext context) {
		DefaultUser user=new DefaultUser();
		user.setCompanyId("bstek");
		user.setUsername("admin");
		user.setAdmin(true);
		return user;
	}

	@Override
	public List<User> getUsers() {
		DefaultUser user1=new DefaultUser();
		user1.setCompanyId("bstek");
		user1.setUsername("user1");
		DefaultUser user2=new DefaultUser();
		user2.setCompanyId("bstek");
		user2.setUsername("user2");
		DefaultUser user3=new DefaultUser();
		user3.setCompanyId("bstek");
		user3.setUsername("user3");
		List<User> users=new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		return users;
	}
}
