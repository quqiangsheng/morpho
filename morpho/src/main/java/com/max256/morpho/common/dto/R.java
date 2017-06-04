package com.max256.morpho.common.dto;

import java.io.Serializable;

import com.max256.morpho.common.exception.BusinessException;
import com.max256.morpho.common.exception.ParameterException;



/**
 * JSON返回值 
 * 简便方法 封装了其他返回类型
 * 本类都是静态方法 无需实例化
 * @author fbf
 * @param <T>  泛型参数 请参看具体实现用该参数是什么作用 这里不做限制 根据实现确定
 */
public class R implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 构造方法
	 */
	public R() {
		
	}
	
	/**
	 * 返回easyui的datagrid json
	 * @param <T>  泛型参数 请参看DataGridReturnData中T的定义
	 * @return DataGridReturnData
	 */
	public static <T> DataGridReturnData<T> dataGrid(){
		return new DataGridReturnData<T>() ;
	};
	
	/**
	 * 返回easyui的datagrid json (不带泛型参数的)
	 * @return DataGridReturnData(不带泛型参数的)
	 */
	@SuppressWarnings("rawtypes")
	public static  DataGridReturnData dataGridWithoutT(){
		return new DataGridReturnData() ;
	};

	
	/**返回普通的json
	 * @param <T>  泛型参数 请参看GeneralReturnData中T的定义
	 * @return GeneralReturnData
	 */
	public static <T> GeneralReturnData<T> data(){
		return new GeneralReturnData<T>() ;
	};
	
	/**返回jqgrid数据格式的json
	 * @param <T>  泛型参数 请参看ListPageReturnData中T的定义
	 * @return ListPageReturnData
	 */
	public static <T> ListPageReturnData<T> jqGrid(){
		return new ListPageReturnData<T>() ;
	};
	/**返回jqgrid数据格式的json(不带泛型参数的)
	 * @return ListPageReturnData(不带泛型参数的)
	 */
	@SuppressWarnings("rawtypes")
	public static  ListPageReturnData jqGridWithoutT(){
		return new ListPageReturnData() ;
	};
	
	/**返回zTree数据格式的json
	 * @param 
	 * @return TreeReturnData
	 */
	public static TreeReturnData zTree(){
		return new TreeReturnData() ;
	};

	
	/**
	 * 抛出业务异常  异常代码0 没有异常信息
	 * @throws BusinessException
	 */
	public static void businessException() throws BusinessException{
		throw new BusinessException(0);
	}
	
	/**
	 * 抛出业务异常  异常代码0 异常信息message
	 * @throws BusinessException
	 */
	public static void businessException(String message) throws BusinessException{
		throw new BusinessException(message,0);
	}
	
	/**
	 * 抛出业务异常  异常代码code 异常信息message
	 * @throws BusinessException
	 */
	public static void businessException(String message,int code) throws BusinessException{
		throw new BusinessException(message,code);
	}
	
	/**
	 * 抛出参数异常  异常信息message
	 * @throws ParameterException
	 */
	public static void paramException(String message) throws ParameterException{
		throw new ParameterException(message);
	}
	
	/**
	 * 抛出参数异常   异常代码code 异常信息message
	 * @throws ParameterException
	 */
	public static void paramException(String message,int code) throws ParameterException{
		throw new ParameterException(message,code);
	}

}
