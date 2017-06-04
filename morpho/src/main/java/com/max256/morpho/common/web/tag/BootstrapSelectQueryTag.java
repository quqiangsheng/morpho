package com.max256.morpho.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * BootstrapSelectQueryTag
 * 构造select查询条件框
 * 自定义jsp标签库
 * @author fbf
 *
 */
public class BootstrapSelectQueryTag extends TagSupport {

	
	private static final long serialVersionUID = 1L;
	public BootstrapSelectQueryTag() {} 
	//属性
	private String id = ""; 
	private String name = ""; 
	
    private String  disabled="false";

	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int doStartTag()  
    {  
        JspWriter writer=pageContext.getOut();  
        try {  
        	writer.println("<div class=\"input-group\">");
        	writer.println("<div class=\"input-group-btn\">");
        	writer.println("<button data-toggle=\"dropdown\" class=\"btn btn-white dropdown-toggle\"");
        	writer.println("type=\"button\">"+name+" ");
        	writer.println("</button>");
        	writer.println("</div>");
        	if(disabled.equals("true")){
        		writer.println("<select disabled=\""+disabled+"\" class=\"form-control\" id=\""+id+"\"> ");
        	}else{
        		writer.println("<select  class=\"form-control\" id=\""+id+"\"> ");
        	}
        	
        } catch (Exception e) {  
        }  
        return Tag.EVAL_BODY_INCLUDE;  
    }  
	
    public int doEndTag() throws JspException  
    {  	

        JspWriter writer=pageContext.getOut();  
        try {  
        	writer.println("</select>");
        	writer.println("</div>");
        } catch (Exception e) {  
        }  
        return Tag.EVAL_PAGE;  
    }  

}
