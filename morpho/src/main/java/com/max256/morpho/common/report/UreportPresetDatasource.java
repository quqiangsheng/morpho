package com.max256.morpho.common.report;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;


import com.bstek.ureport.definition.datasource.BuildinDatasource;

/**
 * 本系统提供ureport2开源报表工具集成 相关文档参考
 * http://wiki.bsdn.org/pages/viewpage.action?pageId=76448364
 * https://github.com/youseries/ureport
 * ureport报表预设数据源
 * 参看文档 http://wiki.bsdn.org/pages/viewpage.action?pageId=76448468
 * @author fbf
 *
 */
@Component
public class UreportPresetDatasource implements BuildinDatasource{
	@Resource(name="druidDataSource")
    DataSource druidDataSource;
	

    public String name(){
    	return "系统预设数据源";
    }

    public Connection getConnection(){
    		try {
				return druidDataSource.getConnection();
			} catch (SQLException e) {
				
				return null;
			}
    }
}
