package com.max256.morpho.common.web.converter;

import java.sql.Timestamp;

import org.springframework.core.convert.converter.Converter;

public class StringToTimestamp implements Converter<String, Timestamp>  {
	@Override    
	public Timestamp convert(String source) {      
	    try {    
	        return new Timestamp(Long.valueOf(source));  
	    } catch (NumberFormatException e) {    
	          
	    }           
	    return null;    
	}
}


