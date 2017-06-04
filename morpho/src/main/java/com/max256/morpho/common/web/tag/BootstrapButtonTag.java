package com.max256.morpho.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * BootstrapButtonTag
 * 自定义jsp标签库
 * @author fbf
 *
 */
public class BootstrapButtonTag extends TagSupport {

	
	private static final long serialVersionUID = 1L;
	public BootstrapButtonTag() {} 
	//属性
	private String id = ""; 
	
	/* btnType : 按钮的类型决定了颜色(default-灰色,primary-绿色,success-蓝色,info-淡蓝色,warning-黄色,danger-红色,white-白色)
     space : 按钮左侧是否有间隔(true/false)
     clickFun : 点击按钮所执行的方法
     icon : 按钮上的图标的样式
     name : 按钮名称*/
	private String btnType = ""; 
	private Boolean space = true; 
	private String clickFun = ""; 
	private String icon = ""; 
	private String name = ""; 
	
   
    public String getBtnType() {
		return btnType;
	}
	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}
	
	public Boolean getSpace() {
		return space;
	}
	public void setSpace(Boolean space) {
		this.space = space;
	}
	public String getClickFun() {
		return clickFun;
	}
	public void setClickFun(String clickFun) {
		this.clickFun = clickFun;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	
    

	public int doStartTag()  
    {  
        return Tag.SKIP_BODY;  
    }  
    public int doEndTag() throws JspException  
    {  	
    	//判断如果为空给出默认值
    	if(StringUtils.isBlank(getBtnType())){
    		setBtnType("primary");
    	}
    	String spaceCss="";
    	if(getSpace()){
    		spaceCss = "button-margin";
    	}else{
    		spaceCss="";
    	}

    	
        JspWriter writer=pageContext.getOut();  
        try {  
        	writer.println("<button id=\""+getId()+"\" type=\"button\" class=\"btn btn-"+getBtnType()+" "+spaceCss+"\" onclick=\""+getClickFun()+"\"  >");
        	writer.println("<i class=\"fa "+getIcon()+"\"></i>&nbsp;"+getName()+"");
        	writer.println("</button>");
        } catch (Exception e) {  
        }  
        return Tag.EVAL_PAGE;  
    }  

}
