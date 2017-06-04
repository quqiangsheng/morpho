package com.max256.morpho.sys.web.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.max256.morpho.common.annotation.SysRes;
import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.dto.DataGridReturnData;
import com.max256.morpho.common.dto.GeneralReturnData;
import com.max256.morpho.common.dto.R;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.security.shiro.ShiroUtils;
import com.max256.morpho.common.util.PasswordData;
import com.max256.morpho.common.util.PasswordUtils;
import com.max256.morpho.common.util.UUIDUtils;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.SysUserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 系统用户
 * 控制器
 * @author fbf
 */
@RequestMapping("/sys/sysuser")
@Controller
@SysRes(name="用户管理",type=Constants.MENU)
@RequiresPermissions("sys:user:*")
public class SysUserController extends AbstractBaseController {
	@Resource
	private SysUserService sysUserService;

	/**
	 * 到用户管理页面
	 */
	@SysRes(name="页面访问")
	@RequiresPermissions("sys:user:page")
	@RequestMapping(value="/sysuser", method=RequestMethod.GET)
	public String goSysUser(){
		return "sys/sysuser/sysuser";
	}
	/**
	 * 到修改密码页面
	 */
	@RequestMapping(value="/changepassword", method=RequestMethod.GET)
	public String goChangePassword(){
		return "sys/sysuser/changepassword";
	}
	/**
	 * 到详细信息页面
	 */
	@RequiresPermissions("sys:user:page")
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public String goDetail(){
		return "sys/sysuser/sysuser_detail";
	}
	
	/**
	 * 删除用户动作
	 */
	@SuppressWarnings({ "rawtypes" })
	@SysRes(name="删除")
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public  GeneralReturnData doDelete(String uuid){
		GeneralReturnData result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//删除
		sysUserService.deleteByPrimaryKey(uuid);
		result.setInfo("删除成功");
		result.setStatus("1");
		return result;
	}
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	@SysRes(name="查询")
	@RequiresPermissions("sys:user:query")
	public DataGridReturnData<SysUser> list(
		@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
		SysUser sysUser,
		@RequestParam(name="beginDate", defaultValue="") String beginDate, 
		@RequestParam(name="endDate", defaultValue="") String endDate){
		//示例hibernate方式 当然也可以用mybatis方式
		DataGridReturnData<SysUser> r=R.dataGrid();
		r.setPageNumber(pageNumber);
		r.setPageSize(pageSize);
		Example example =new Example(SysUser.class);
		Criteria criteria=example.createCriteria();
		if(!beginDate.equals("")&&!endDate.equals("")){
			criteria.andBetween("registerTime", beginDate, endDate);
		}
		if(StringUtils.isNotBlank(sysUser.getUserName())){
			criteria.andEqualTo("userName", sysUser.getUserName());
		}
		//sysUserService.findGridDataByHibernate(r);
		List<SysUser> findList=sysUserService.selectByExampleAndRowBounds(example, new RowBounds((pageNumber-1)*pageSize, pageSize));
		r.setRows(findList);
		r.setTotal(sysUserService.selectCountByExample(example));
		r.setStatus("1");
		r.setInfo("所有用户列表");
		return r;
	}
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/uuid/{uuid}")
	@ResponseBody
	@RequiresPermissions("sys:user:query")
	public GeneralReturnData uuid(@PathVariable("uuid") String uuid){
		GeneralReturnData<SysUser> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//查找SysUser
		SysUser findSysUser=sysUserService.selectByPrimaryKey(uuid);
		if(null==findSysUser){
			result.setInfo("查询结果为空");
			result.setStatus("0");
			return result;
		}
		result.setData(findSysUser);
		result.setStatus("1");
		return result;
	}
	/**
	 * 创建新用户
	 * @param 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/createsysuser")
	@ResponseBody
	@SysRes(name="新增")
	@RequiresPermissions("sys:user:create")
	public GeneralReturnData doCreateSysUser(SysUser sysUser){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(null==sysUser){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		sysUser.setUuid(UUIDUtils.get32UUID());
		//校验id
		if(StringUtils.isNotBlank(sysUser.getUserId())){
			//用户有输入 检验是否被使用
			SysUser paramSysUser=new SysUser();
			paramSysUser.setUserId(sysUser.getUserId());
			int findCount=sysUserService.selectCount(paramSysUser);
			if(findCount!=0){
				result.setInfo("该用户ID已经被使用,请重新输入");
				result.setStatus("0");
				return result;
			}
		}else{
			//用户没有输入自动生成和uuid一样的
			sysUser.setUserId(sysUser.getUuid());
		}
		if(StringUtils.isBlank(sysUser.getUserName())){
			result.setInfo("用户名不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysUser.getIsValid())){
			result.setInfo("请选择是否启用用户,您没有选择");
			result.setStatus("0");
			return result;
		}
		if(!sysUser.getIsValid().equals("0")&&!sysUser.getIsValid().equals("1")){
			result.setInfo("是否启用用户参数错误,只能为0或1,分别代表不启用和启用");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysUser.getUserNickname())){
			result.setInfo("用昵称不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysUser.getUserPassword())){
			result.setInfo("密码不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(sysUser.getUserPassword().length()<5||sysUser.getUserPassword().length()>20){
			result.setInfo("密码长度错误,请输入大于等于5小于等于20个字符长度的密码");
			result.setStatus("0");
			return result;
		}
		//校验用户名是否重复
		SysUser paramSysUser=new SysUser();
		paramSysUser.setUserName(sysUser.getUserName());
		int findCount=sysUserService.selectCount(paramSysUser);
		if(findCount!=0){
			result.setInfo("该用户名已经被使用,请重新输入");
			result.setStatus("0");
			return result;
		}
		// 注册时间时间戳
		sysUser.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		// 处理密码加密
		String newUserPassword = sysUser.getUserPassword();// 用户输入的未加密密码
		PasswordData pd = PasswordUtils.encryptPassword(newUserPassword);// 密码加密
		// 重新保存到newUser中
		sysUser.setUserPassword(pd.getPassword());
		// 密码加salt
		sysUser.setSalt(pd.getSalt());
		// 保存
		sysUserService.insert(sysUser);
		result.setInfo("新增用户成功");
		result.setStatus("1");
		return result;
	}
	/**
	 * 修改用户信息
	 * @param 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/updatesysuser")
	@ResponseBody
	@SysRes(name="更新")
	@RequiresPermissions("sys:user:update")
	public GeneralReturnData doUpdateSysUser(SysUser sysUser){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(null==sysUser){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysUser.getUuid())){
			result.setInfo("参数中主键错误");
			result.setStatus("0");
			return result;
		}
		//查找SysUser
		SysUser findSysUser=sysUserService.selectByPrimaryKey(sysUser.getUuid());
		if(null==findSysUser){
			result.setInfo("找不到对应的用户信息");
			result.setStatus("0");
			return result;
		}
		//查到结果 
		//修改email
		if(StringUtils.isNotBlank(sysUser.getEmail())){
			findSysUser.setEmail(sysUser.getEmail());
		}
		
		//修改是否可用
		if(StringUtils.isBlank(sysUser.getIsValid())){
			result.setInfo("请选择是否启用用户,您没有选择");
			result.setStatus("0");
			return result;
		}
		if(!sysUser.getIsValid().equals("0")&&!sysUser.getIsValid().equals("1")){
			result.setInfo("是否启用用户参数错误,只能为0或1,分别代表不启用和启用");
			result.setStatus("0");
			return result;
		}else{
			findSysUser.setIsValid(sysUser.getIsValid());
		}
		//修改昵称
		if(StringUtils.isNotBlank(sysUser.getUserNickname())){
			findSysUser.setUserNickname(sysUser.getUserNickname());
		}
		//修改密码
		//先判断客户端传来加密后的密码是否和数据库中的相同 相同则不修改 不相同说明使用了新密码 使用新salt加密并保存
		if(StringUtils.isBlank(sysUser.getUserPassword())){
			result.setInfo("密码不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(sysUser.getUserPassword().equals(findSysUser.getUserPassword())){
			//密码没有被修改 就不用修改
		}else{
			//不相等 说明用户输入了新的密码
			//校验新输入的密码格式
			if(sysUser.getUserPassword().length()<5||sysUser.getUserPassword().length()>20){
				result.setInfo("密码长度错误,请输入大于等于5小于等于20个字符长度的密码");
				result.setStatus("0");
				return result;
			}
			//新密码校验通过
			// 处理密码加密
			String newUserPassword = sysUser.getUserPassword();// 用户输入的未加密密码
			PasswordData pd = PasswordUtils.encryptPassword(newUserPassword);// 密码加密
			// 重新保存到findSysUser中
			findSysUser.setUserPassword(pd.getPassword());
			// 密码加salt
			findSysUser.setSalt(pd.getSalt());
		}
		//修改角色
		if(StringUtils.isNotBlank(sysUser.getSysRoleIds())){
			findSysUser.setSysRoleIds(sysUser.getSysRoleIds());;
		}
		
		// 保存修改
		sysUserService.updateByPrimaryKey(findSysUser);
		result.setInfo("修改用户成功");
		result.setStatus("1");
		return result;
	}
	
	/**
	 * 自己修改自己的密码 不需要加权限
	 */
	@RequestMapping(value="/changepassword", method=RequestMethod.POST)
	@ResponseBody
	public GeneralReturnData<String> doChangePassword(@RequestParam(defaultValue="" ,name="oldPassword") String oldPassword,
			@RequestParam(defaultValue="" ,name="newPassword")String newPassword,
			@RequestParam(defaultValue="" ,name="newPassword2")String newPassword2
			){
		GeneralReturnData<String> r=R.data();
		//
		if(StringUtils.isBlank(oldPassword)||StringUtils.isBlank(newPassword)||StringUtils.isBlank(newPassword2)||newPassword.length()<5){
			r.setInfo("密码为空,或者密码位数不符合要求");
			r.setStatus("0");
			return r;
		}
		if(!newPassword.equals(newPassword2)){
			r.setInfo("新密码两次输入不一致");
			r.setStatus("0");
			return r;
		}
		//获得当前密码
		SysUser sessionSysUser=ShiroUtils.getSysUser();
		//查询数据库里最新的数据
		SysUser dbSysUser=sysUserService.selectByPrimaryKey(sessionSysUser.getUuid());
		//根据用户输入的旧密码加密判断是否正确
		 PasswordData oldPasswordData=PasswordUtils.encryptPassword(oldPassword, dbSysUser.getSalt());
		if(!oldPasswordData.getPassword().equals(dbSysUser.getUserPassword())){
			r.setInfo("旧密码错误,修改失败");
			r.setStatus("0");
			return r;
		}
		//
		PasswordData newPasswordData = PasswordUtils.encryptPassword(newPassword);// 密码加密
		// 重新保存到findSysUser中
		dbSysUser.setUserPassword(newPasswordData.getPassword());
		// 密码加salt
		dbSysUser.setSalt(newPasswordData.getSalt());
		// 保存修改
		sysUserService.updateByPrimaryKey(dbSysUser);
		r.setInfo("修改密码成功,下次登录请用新密码");
		r.setStatus("1");
		return r;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
