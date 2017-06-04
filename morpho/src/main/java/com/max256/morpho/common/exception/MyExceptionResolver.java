package com.max256.morpho.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.max256.morpho.common.dto.GeneralReturnData;
import com.max256.morpho.common.util.ExceptionUtils;
import com.max256.morpho.common.util.JsonUtils;


/**
 * spring mvc 全局处理异常捕获 
 * 根据请求区分ajax和普通网页请求分别进行响应
 * MyExceptionResolver自定义springMVC异常解析器类
 * 扩展自HandlerExceptionResolver接口
 * 
 * @author fbf
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

	// 日志
	private static final Logger logger = LoggerFactory.getLogger(MyExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//判断请求是否为Ajax请求
		boolean isAjax = isAjax(request,response,handler);
		//获得最原始的异常出处
		Throwable deepestException = deepestException(ex);
		//后台日志记录
		//异常警告日志记录开启并且是自定义业务异常时才进行日志记录
		if(logger.isWarnEnabled()&&isBusinessException(ex)){
			BusinessException ex1=(BusinessException) ex;
			//处理异常信息
			logger.warn("异常解析记录====================异常开始=========================");
			logger.warn("异常代码:"+ex1.getErrCode()+" ");
			logger.warn("异常内容:"+ex1.getErrMsg()+" ");
		    logger.warn("异常解析记录====================异常结束========================="); 
		}
		//错误警告日志记录开启并且是系统异常时才进行日志记录
		if(logger.isErrorEnabled()&& (ex instanceof SystemException)){
			SystemException ex1=(SystemException) ex;
			//处理异常信息
			logger.error("系统错误解析记录==============系统错误开始=========================");
			logger.error("系统错误代码:"+ex1.getErrCode()+" ");
			logger.error("系统错误内容:"+ex1.getErrMsg()+" ");
		    logger.error("异常解析记录=================系统错误结束========================="); 
		}
		//其它运行时异常仅进行info记录
		if(logger.isInfoEnabled()){
			//判断是否是其他运行时异常
			if(!(ex instanceof SystemException)
					&&!(ex instanceof BusinessException)
					&&(ex instanceof RuntimeException))
			{
				//处理异常信息
				logger.info("运行时异常解析记录==============运行时异常开始=========================");
				logger.info("原始异常出处:"+deepestException); // logger记录
				if (ex != null) {
					logger.info("详细异常信息:"+ex.getClass()+" : "+ex.getMessage());
					StackTraceElement[] elements = ex.getStackTrace();
					for (StackTraceElement stackTraceElement : elements) {
						logger.info(stackTraceElement.toString());
					}
				}
			    logger.info("运行时异常解析记录=================运行时异常结束========================="); 
			}
		}
	    //根据访问类型返回不同的视图交前台处理
	    if(isAjax){
			return processAjax(request,response,handler,ex);
		}else{
			return processNotAjax(request,response,handler,ex);
		}
	
	}
	/**
	 * 判断当前请求是否为异步请求.
	 * 综合两种判断方法
	 * 1.根据客户端的http请求头判断
	 * 2.根据访问controller是否有ResponseBody注解判断
	 */
	private boolean isAjax(HttpServletRequest request, HttpServletResponse response,Object handler){
		//强制类型装换
		HandlerMethod handlerMethod=(HandlerMethod)handler;//取得handler 判断是否有ResponseBody的注解
		//判断是否有ResponseBody注解
		boolean hasResponseBody=handlerMethod.hasMethodAnnotation(ResponseBody.class);
		if(hasResponseBody){
			//有ResponseBody注解 则肯定为返回json请求 不再进行以下判断
			return (true);
		}
		//没有的话再继续判断请求头
		String flagHeader=request.getHeader("X-Requested-With");
		if (flagHeader != null && !flagHeader.equals("") && !flagHeader.equals("null")) {
			return (true);
		}
		String acceptHeader=request.getHeader("Accept");
		if (acceptHeader != null 
				&& !acceptHeader.equals("")
				&& !acceptHeader.equals("null")
				//表明带json字符
				&&acceptHeader.toLowerCase().indexOf("json")!=-1) {
			return (true);
		}
		return (false);
	}
	
	/**
	 * 获取最原始的异常出处，即最初抛出异常的地方
	 */
    private Throwable deepestException(Throwable e){
        Throwable tmp = e;
        int breakPoint = 0;
        while(tmp.getCause()!=null){
            if(tmp.equals(tmp.getCause())){
                break;
            }
            tmp=tmp.getCause();
            breakPoint++;
            if(breakPoint>1000){
                break;
            }
        } 
        return tmp;
    }
    
    /**
	 * ajax异常处理并返回.
	 * @param request
	 * @param response
	 * @param handler
	 * @param deepestException
	 * @return
	 */
	private ModelAndView processAjax(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			Throwable ex){
		//设置响应头
		response.setHeader("Cache-Control", "no-store");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		//ajax就返回json信息
		GeneralReturnData<String> json = new GeneralReturnData<>();
		//填充
		if(isBusinessException(ex)){
			BusinessException be=(BusinessException)ex;
			json.setStatus(be.getErrCode()+"");//业务异常代码
			json.setInfo(be.getErrMsg());//业务异常信息
		}else{
			//非业务异常 系统异常 运行时异常 异常 错误等
			if(ex instanceof SystemException){
				//系统异常
				SystemException ex1=(SystemException)ex;
				json.setStatus(ex1.getErrMsg()+"");
				json.setInfo(ex1.getErrMsg());//异常信息	
			}
			else{
				//剩下的就是系统异常 运行时异常 异常 错误等未被被系统包装的异常
				//Unauthorized Exception
				//Unauthenticated Exception
				if(ex instanceof UnauthorizedException){
					json.setStatus("0");
					json.setInfo("您没有权限访问");//异常信息	
				}else if(ex instanceof UnauthenticatedException){
					json.setStatus("0");
					json.setInfo("您没有登陆无法访问");//异常信息	
				}else{
					json.setStatus("0");
					json.setInfo(ex.getMessage());//异常信息	
					
				}
				
			}		
		}
		PrintWriter pw = null;
		try {
			pw=response.getWriter();//得到输出流
			pw.write(JsonUtils.writeValueAsString(json));
			pw.flush();
		} catch (IOException e) {
			
		}finally{
			pw.close();
		}
		//永远不会被执行前边response已经提交
		return null;
	}
	/**
	 * 普通页面异常处理并返回.
	 * @param request
	 * @param response
	 * @param handler
	 * @param deepestException
	 * @return
	 */
	private ModelAndView processNotAjax(HttpServletRequest request,
			HttpServletResponse response, Object handler, Throwable ex) {

			ModelAndView mv = new ModelAndView("/error/businessException");
			// 把异常信息放到ModelAndView中 跳转到exception页面中
			if (ex instanceof UnauthorizedException) {
				// 没有权限访问 转发到没有权限页面
				mv.setViewName("/error/unauth");
				mv.addObject("exception", "您没有访问权限" + ex);
			} else if(ex instanceof UnauthenticatedException){
				mv.setViewName("/error/unauth");
				mv.addObject("exception", "您没有登录无法访问" + ex);
			}
			else if(isBusinessException(ex)){
				//业务异常
				BusinessException be=(BusinessException)ex;
				mv.addObject("exception", "[业务异常代码："+be.getErrCode()+"][业务异常信息："+be.getErrMsg()+"]");
			}else{
				//非业务异常 系统异常 运行时异常 异常 错误等
				if(ex instanceof SystemException){
					//系统异常
					SystemException ex1=(SystemException)ex;
					mv.addObject("exception", "[系统异常代码："+ex1.getErrCode()+"][系统异常信息："+ex1.getErrMsg()+"]");
				}
				else{
					//剩下的就是系统异常 运行时异常 异常 错误等未被被系统包装的异常
					mv.addObject("exception", ExceptionUtils.getStackTraceAsString(ex));
				}		
			}
			
			return mv;
	}
	//判断是否为业务异常
	public static boolean isBusinessException(Throwable ex){
		if(ex instanceof BusinessException){
			return true;
		}else{
			return false;			
		}
	}		


}
