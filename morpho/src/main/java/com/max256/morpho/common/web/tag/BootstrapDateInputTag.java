package com.max256.morpho.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * BootstrapDateInputTag
 * Bootstrap风格的日期时间输入
 * 自定义jsp标签库
 * @author fbf
 *
 */
public class BootstrapDateInputTag extends TagSupport {

	
	private static final long serialVersionUID = 1L;
	public BootstrapDateInputTag() {} 
	//属性
	  /*name : 查询条件的名称
	  id : 查询内容的input框id
	  isTime : 日期是否带有小时和分钟(true/false)
	  pattern : 日期的正则表达式(例如:"YYYY-MM-DD")*/
	private String id = ""; 
	private String name = ""; 
	private Boolean isTime = false; 
	private String pattern = "YYYYMMDD"; 
	private String placeHolder= ""; 
	private String readonly="";
	
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	public String getPlaceHolder() {
		return placeHolder;
	}
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIsTime() {
		return isTime;
	}
	public void setIsTime(Boolean isTime) {
		this.isTime = isTime;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public int doStartTag()  
    {  
        return Tag.SKIP_BODY;  
    }  
    public int doEndTag() throws JspException  
    {  	
        JspWriter writer=pageContext.getOut();  
        try {  
        	writer.println("<div class=\"input-group\">");
        	writer.println("<div class=\"input-group-btn\">");
        	writer.println("<button data-toggle=\"dropdown\" class=\"btn btn-white dropdown-toggle\"");
        	writer.println("type=\"button\">"+name+" ");
        	writer.println("</button>");
        	writer.println("</div>");
        	if(getReadonly()!=null&&getReadonly().equals("readonly")){
        		writer.println(" <input type=\"text\" readonly=\"readonly\" class=\"form-control layer-date\" placeholder=\""+placeHolder+"\" ");
        	}else{
        		writer.println(" <input type=\"text\"  class=\"form-control layer-date\" placeholder=\""+placeHolder+"\" ");
        	}
        	
        	writer.println(" onclick=\"laydate({istime: "+isTime+", format: '"+pattern+"'})\" id=\""+id+"\"/>");
        	writer.println("</div>");
        } catch (Exception e) {  
        }  
        return Tag.EVAL_PAGE;  
    }  

}
