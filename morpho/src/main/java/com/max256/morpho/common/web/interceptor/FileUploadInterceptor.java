package com.max256.morpho.common.web.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 文件上传拦截器
 * 一个系统中可能有多个模块需要分别控制文件上传 
 * 这是用来对细粒度的文件上传进行控制 如
 * 限制上传大小 文件类型 等 在进入处理器映射器之前工作
 * @author fbf
 * @since 2016年8月15日 下午4:30:28
 * @version V1.0
 * 
 */
public class FileUploadInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		HttpServletRequest req=(HttpServletRequest)request;
		//springMVC用的multipart文件解析器 采用apache common fileupload
		MultipartResolver res=new org.springframework.web.multipart.commons.CommonsMultipartResolver();
		if(res.isMultipart(req)){
			//此步骤判断本次请求是否是包含文件上传的内容，如果包含springMVC的dispatcher servlet已经把http转换为MultipartHttpServletRequest
			MultipartHttpServletRequest  multipartRequest=(MultipartHttpServletRequest) req;
			//得到所有的上传文件
			Map<String, MultipartFile> files= multipartRequest.getFileMap();
			//迭代
			Iterator<String> iterator = files.keySet().iterator();
			//请求编码
			if (request.getCharacterEncoding() == null) {  
		           request.setCharacterEncoding("UTF-8");  
			}  
			while (iterator.hasNext()) {
				//上传文件的表单key
				String formKey = (String) iterator.next();
				//上传文件的表单key对应的内容
				MultipartFile multipartFile = multipartRequest.getFile(formKey);
				//不为空
				if (null!=multipartFile&&null!=multipartFile.getOriginalFilename()&&StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
					//返回原来的文件名在客户机的文件系统。
					String filename = multipartFile.getOriginalFilename();
					//例如检查文件后缀
					if(checkFile(filename)){
						return true;
						//还可以加入检查文件大小 精确检查文件类型等
					}else{
						//拒绝
						return false;
					}
				}
			}
			return true;
		}else{
			return true;
		}
	}
	//检查文件名
	private  boolean checkFile(String fileName){
		boolean flag=false;
		String suffixList="xls,xlsx,jpg,gif,png,ico,bmp,jpeg";
		//获取文件后缀
		String suffix=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		//全部小写 不区分大小写
		if(suffixList.contains(suffix.trim().toLowerCase())){
			flag=true;
		}
		return flag;
	}
}
