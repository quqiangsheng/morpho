package com.max256.morpho.common.util.generator;

import java.io.File;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.HashMap;

import com.max256.morpho.common.annotation.Th;
import com.max256.morpho.common.entity.SysUser;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 用于构造页面的查询框
 * @author fbf
 * @since 2016年11月7日 上午9:29:33
 * @version V1.0
 * 
 */
public class QueryAreaDivGenerator {
	
	public static void main(String[] args) throws Exception {
		QueryAreaDivGenerator queryAreaDivGenerator=new QueryAreaDivGenerator();
		queryAreaDivGenerator.init();
		//在这里放上你想生成的实体
		queryAreaDivGenerator.process(SysUser.class);
	}

	
	
	private Configuration cfg; // 模版配置对象
	public void init() throws Exception {
		// 初始化FreeMarker配置
		// 创建一个Configuration实例
		cfg = new Configuration(Configuration.VERSION_2_3_23);
		// 设置FreeMarker的模版文件夹位置
		cfg.setDirectoryForTemplateLoading(new File(this.getClass().getClassLoader().getResource("template/easyui").toURI()));
		cfg.setDefaultEncoding("UTF-8");
	
	}

	public void process(Class<?> clazz) throws Exception {
		HashMap<String, String> queryDiv=generatorQueryAreaDiv(clazz);
		// 创建模版对象
		Template t = cfg.getTemplate("queryAreaDiv.ftl");
		// 在模版上执行插值操作，并输出到制定的输出流中
		t.process(queryDiv, new OutputStreamWriter(System.out));
	}
	
	 public static HashMap<String, String> generatorQueryAreaDiv(Class<?> clazz)
	 {
		//填充数据
	    Field[] fields = clazz.getDeclaredFields();
	    HashMap<String, String> map = new HashMap<String, String>(fields.length);
	    for (int j = 0; j < fields.length; j++) {
	    	 String label = "Label";
	    	 if (fields[j].isAnnotationPresent(Th.class)) {
	 	        Th th = (Th)fields[j].getAnnotation(Th.class);
	 	        label = th.value();
	 	     }
	    	 String itemName = fields[j].getName().substring(0, 1).toLowerCase() + fields[j].getName().substring(1);
	    	 map.put(itemName,label);
		}
	    return map;
	  }
	
	
}
