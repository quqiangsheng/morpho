package com.max256.morpho.sys.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.max256.morpho.common.annotation.SysRes;
import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.dto.DataGridReturnData;
import com.max256.morpho.common.dto.GeneralReturnData;
import com.max256.morpho.common.dto.R;
import com.max256.morpho.common.entity.HttpSession;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.security.shiro.session.ShiroSessionDAO;
import com.max256.morpho.common.util.IPUtils;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.SysUserService;

/**
 * HttpSessionController
 * 控制器
 * @author fbf
 */
@RequestMapping("/sys/httpsession")
@Controller
@SysRes(name="Session管理",type=Constants.MENU)
@RequiresPermissions("sys:session:*")
public class HttpSessionController extends AbstractBaseController {
	@Resource
	private SysUserService sysUserService;
	@Autowired
	ShiroSessionDAO shiroSessionDao;
	/**
	 * 到httpsession管理页面
	 */
	@RequestMapping(value="/httpsession", method=RequestMethod.GET)
	@SysRes(name="session管理页面访问",type=Constants.BUTTON)
	@RequiresPermissions("sys:session:page")
	public String goSysUser(){
		return "sys/httpsession/httpsession";
	}
	
	/**
	 * 删除用户动作
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="删除",type=Constants.BUTTON)
	@RequiresPermissions("sys:session:delete")
	public  GeneralReturnData doDelete(String sessionId){
		GeneralReturnData result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(sessionId)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//删除
		Session shiroSession=shiroSessionDao.readSession(sessionId);
		if(shiroSession==null){
			result.setInfo("该session不存在");
			result.setStatus("0");
			return result;
		}
		String currentSysUserName=(String) shiroSession.getAttribute(Constants.CURRENT_USER_NAME);
		if(currentSysUserName==null){
			result.setInfo("该session没有用户登录,无需离线");
			result.setStatus("0");
			return result;
		}
		Object flag=shiroSession.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY);
		if(flag!=null){
			result.setInfo("已经处于离线状态,无需重复点击");
			result.setStatus("0");
			return result;
		}else{
			shiroSession.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY, "true");
			shiroSessionDao.update(shiroSession);
			result.setInfo("强制离线成功");
			result.setStatus("1");
			return result;
		}	
	}
	
	/**
	 * 所有session列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	@SysRes(name="查询",type=Constants.BUTTON)
	@RequiresPermissions("sys:session:query")
	public DataGridReturnData<HttpSession> list(
		@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
		HttpSession httpSession//自定义的HttpSession视图
		){
		//参数去空格
		if(httpSession.getUserName()!=null){
			httpSession.setUserName(httpSession.getUserName().trim());
		}
		if(httpSession.getSessionId()!=null){
			httpSession.setSessionId(httpSession.getSessionId().trim());
		}
		if(httpSession.getUserId()!=null){
			httpSession.setUserId(httpSession.getUserId().trim());
		}
		DataGridReturnData<HttpSession> r=R.dataGrid();
		r.setPageNumber(pageNumber);
		//sessions肯定不为空 因为当前查看的管理员至少要有一条session
		Collection<Session> sessions = shiroSessionDao.getActiveSessions();
		//不再分页
		List<HttpSession> httpSessionList=new ArrayList<>();
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session item =  iterator.next();
			if(StringUtils.isNotBlank(httpSession.getSessionId())){
				if(!item.getId().toString().equals(httpSession.getSessionId())){
					continue;
				}
			}
			
			HttpSession session=new HttpSession();
			
			String userName=(String) item.getAttribute(Constants.CURRENT_USER_NAME);
			SysUser sysUser=(SysUser) item.getAttribute(Constants.CURRENT_USER);
			//已经有用户登录
			if(userName!=null){
				if(StringUtils.isNotBlank(httpSession.getUserName())){
					if(!userName.equals(httpSession.getUserName())){
						continue;
					}
				}
				if(StringUtils.isNotBlank(httpSession.getUserId())){
					if(!sysUser.getUserId().equals(httpSession.getUserId())){
						continue;
					}
				}
				session.setUserName(userName);
				session.setUserId(sysUser.getUserId());
				session.setHost(IPUtils.getIpInfo(item.getHost()));
				session.setSessionId(item.getId().toString());
				session.setLastAccess(item.getLastAccessTime());
				session.setStartTime(item.getStartTimestamp());
				Object statusFlag=item.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY);
				if(statusFlag!=null){
					session.setSessionStatus(false);
				}else{
					session.setSessionStatus(true);
				}
				if(httpSession.isSessionStatus()!=null&&httpSession.isSessionStatus()==false){
					//条件 在线状态 不为空 只要离线 在线的全过滤
					if(session.isSessionStatus()==true){
						continue;
					}
				}
				httpSessionList.add(session);
			}else{
				//没有用户登录
				if(StringUtils.isNotBlank(httpSession.getUserName())){
						//查询条件要求有用户 所以直接跳过
						continue;
				}
				//没有用户登录
				if(StringUtils.isNotBlank(httpSession.getUserId())){
						//查询条件要求有用户 所以直接跳过
						continue;
				}
				if(httpSession.isSessionStatus()==null){
					
				}else if(httpSession.isSessionStatus()==false){
					//条件要求是离线 由于本if里都是在线的所以跳过
					continue;
				}
				session.setSessionId(item.getId().toString());
				session.setHost(IPUtils.getIpInfo(item.getHost()));
				session.setLastAccess(item.getLastAccessTime());
				session.setStartTime(item.getStartTimestamp());
				session.setSessionStatus(true);
				httpSessionList.add(session);
			}
		}
		r.setRows(httpSessionList);
		r.setTotal(httpSessionList.size());
		r.setPageSize(httpSessionList.size());
		r.setStatus("1");
		r.setInfo("所有session列表");
		return r;
	}
	
}
