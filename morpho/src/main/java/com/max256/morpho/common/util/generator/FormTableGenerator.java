package com.max256.morpho.common.util.generator;

import java.lang.reflect.Field;

import com.max256.morpho.common.annotation.Th;
import com.max256.morpho.common.entity.SysUser;
/**
 * 用于构造Form表单 table布局风格
 * @author fbf
 * @since 2016年11月7日 下午2:59:06
 * @version V1.0
 * 
 */
public class FormTableGenerator {
	public static void main(String[] args)
	  {
	    System.out.println(
	    generatorTable(SysUser.class,6)//第二个参数表示每行显示多少列这个是table布局
	    );
	  }

	  public static String generatorTable(Class<?> clazz, int columns)
	  {  
	    String name = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
	    Field[] fields = clazz.getDeclaredFields();
	    StringBuffer sb = new StringBuffer("<form id=\"" + name + "_Form\" name=\"" + name + "_Form\" method=\"post\">\n<table class=\"fitem\">\n\t<tr>\n");
	    for (int i = 0; i < fields.length; i++) {
	      String itemName = fields[i].getName().substring(0, 1).toLowerCase() + fields[i].getName().substring(1);
	      if ((i > 0) && (i % columns == 0)) {
	        sb.append("\t</tr>\n\t<tr>\n");
	      }
	      sb.append("\t\t<th>\n\t\t\t");
	      String label = "Label  "+fields[i].getName();
	      if (fields[i].isAnnotationPresent(Th.class)) {
	        Th th = (Th)fields[i].getAnnotation(Th.class);
	        label = th.value();
	      }
	      sb.append(label);
	      sb.append("：");
	      sb.append("\n\t\t</th>\n");
	      sb.append("\t\t<td>\n\t\t\t");
	      if (fields[i].getType().equals(Short.class))
	      {
	        sb.append("<re:dict id=\"" + itemName + "\" codeType=\"" + itemName + "\" required=\"true\"/>");
	      }
	      
	      else sb.append("<input type=\"text\" class=\"easyui-validatebox\" data-options=\"required:true\" name=\"" + itemName + "\"/>");

	      sb.append("\n\t\t</td>\n");
	    }
	    sb.append("\t</tr>\n</table>\n</form>");
	    return sb.toString();
	  }
	 
}
