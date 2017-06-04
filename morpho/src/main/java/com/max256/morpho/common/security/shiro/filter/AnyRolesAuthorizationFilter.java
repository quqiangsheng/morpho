package com.max256.morpho.common.security.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * Shiro filterChainDefinitions中 roles默认是and，定制一个过滤器支持any语法
 * 示例：/admin/** = anyRoles[ROLE_MGMT_USER]
 */
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {

	/* 
	 * 如果请求可以正常地进行过滤，则返回true;
	 * 如果请求应该由onaccess否认(请求、响应、mappedValue)方法处理，则返回false。
	 */
	@Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {

        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) {
                return true;
            }
        }
        return false;
    }

}