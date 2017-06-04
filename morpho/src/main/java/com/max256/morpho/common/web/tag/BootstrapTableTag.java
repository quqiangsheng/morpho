package com.max256.morpho.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * BootstrapTableTag
 * 自定义jsp标签库
 * @author fbf
 *
 */
public class BootstrapTableTag extends TagSupport {

	
	private static final long serialVersionUID = 1L;
	public BootstrapTableTag() {} 
	//属性
	private String id = ""; 
	
	
   
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
    

	public int doStartTag()  
    {  
        return Tag.SKIP_BODY;  
    }  
    public int doEndTag() throws JspException  
    {  	
    	//id必须有 
    	/*<table id="${id}" data-mobile-responsive="true" data-click-to-select="true">
	        <thead>
	            <tr>
	                <th data-field="selectItem" data-checkbox="true"></th>
	            </tr>
	        </thead>
        </table>*/
    	
        JspWriter writer=pageContext.getOut();  
        try {  
        	writer.println("<table id=\""+id+"\" data-mobile-responsive=\"true\" data-click-to-select=\"true\" >");
        	writer.println("<thead>");
        	writer.println("<tr>");
        	writer.println("<th data-field=\"selectItem\" data-checkbox=\"true\"></th>");
        	writer.println("</tr>");
        	writer.println("</thead>");
        	writer.println("</table>");
        } catch (Exception e) {  
        }  
        return Tag.EVAL_PAGE;  
    }  

}
