package com.max256.morpho.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * InputQueryTag
 * 构造查询条件框
 * 自定义jsp标签库
 * @author fbf
 *
 */
public class InputQueryTag extends TagSupport {

	
	private static final long serialVersionUID = 1L;
	public InputQueryTag() {} 
	//属性
	private String id = ""; 
	private String name = ""; 
	private String placeHolder = ""; 
	private String readonly="";
    

	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
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
	public String getPlaceHolder() {
		return placeHolder;
	}
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
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
        		writer.println("<input type=\"text\" readonly=\"readonly\" class=\"form-control\" id=\""+id+"\" placeholder=\""+placeHolder+"\" />");
        	}else{
        		writer.println("<input type=\"text\" class=\"form-control\" id=\""+id+"\" placeholder=\""+placeHolder+"\" />");
        	}
        	
        	writer.println("</div>");
        } catch (Exception e) {  
        }  
        return Tag.EVAL_PAGE;  
    }  

}
