<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	  <head>
	  <meta charset="utf-8" />
	  <base href="<%=basePath%>">
	  <title>您已退出登陆</title> 
	  <style type="text/css"> 
	        body { background-color: #fff; color: #666; text-align: center; font-family: arial, sans-serif; }
	        div.dialog {
	            width: 80%;
	            padding: 1em 4em;
	            margin: 4em auto 0 auto;
	            border: 1px solid #ccc;
	            border-right-color: #999;
	            border-bottom-color: #999;
	        }
	        h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
	  </style> 
     
</head> 
 
<body> 
  <div class="dialog"> 
    <h1>您已被管理员强制退出系统,此账户被锁定，请联系管理员</h1> 
    <p>	
    	
		<a href="login">返回登录页面</a> 
    </p> 

  </div>
  
 
</body> 
</html>
