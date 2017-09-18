package com.max256.morpho.common.dto;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

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
	
	/**
	 * 包装返回数据 可以封装一些字典项对照内容或者其他您需要的内容
	 * @param returnData
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static GeneralReturnData wrapList(GeneralReturnData returnData,WrapCommand wrapCommand)  {
		if(returnData==null){
			new ParameterException("returnData不能为null");
		}
		if(wrapCommand==null){
			new ParameterException("wrapCommand不能为null");
		}
		//判断GeneralReturnData类型
		if(returnData instanceof TreeReturnData){
			//TreeReturnData不包装直接返回
			return returnData;

		}else if(returnData instanceof ListPageReturnData){
			List<HashMap> result=new ArrayList();
			ListPageReturnData listPageReturnData=(ListPageReturnData)returnData;
			List list = listPageReturnData.getList();
			//为空判断
			if(list==null||list.size()==0){
				//不处理
				return returnData;
			}
			for (Object item : list) {
				HashMap describe = null;
				try {
					describe = (HashMap) BeanUtils.describe(item);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					//包装失败 返回原对象
					return returnData;
				}
				HashMap describe1 = null;
				try {
					describe1 = (HashMap) BeanUtils.cloneBean(describe);
				} catch (IllegalAccessException | InstantiationException | InvocationTargetException
						| NoSuchMethodException e) {
					//包装失败 返回原对象
					return returnData;
				}//返回的单个bean的加上属性后的map
				Set<String> keySet = describe.keySet();//bean propertity key
				for (String object : keySet) {
					describe1.put(object, describe.get(object));
					if(object.equals("class")){
						//class跳过
					}else{
						//其他调用用户命令处理
						wrapCommand.wrap(object, describe.get(object), describe1);
					}
				}
				result.add(describe1);
			}
			//设置返回数据
			listPageReturnData.setList(result);
			
		}else if(returnData instanceof DataGridReturnData){
			List<HashMap> result=new ArrayList();
			DataGridReturnData dataGridReturnData=(DataGridReturnData)returnData;
			List list = dataGridReturnData.getRows();
			//为空判断
			if(list==null||list.size()==0){
				//不处理
				return returnData;
			}
			for (Object item : list) {
				HashMap describe = null;
				try {
					describe = (HashMap) BeanUtils.describe(item);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					//包装失败 返回原对象
					return returnData;
				}
				HashMap describe1 = null;
				try {
					describe1 = (HashMap) BeanUtils.cloneBean(describe);
				} catch (IllegalAccessException | InstantiationException | InvocationTargetException
						| NoSuchMethodException e) {
					//包装失败 返回原对象
					return returnData;
				}//返回的单个bean的加上属性后的map
				Set<String> keySet = describe.keySet();//bean propertity key
				for (String object : keySet) {
					describe1.put(object, describe.get(object));
					if(object.equals("class")){
						//class跳过
					}else{
						//其他调用用户命令处理
						wrapCommand.wrap(object, describe.get(object), describe1);
					}
				}
				result.add(describe1);
			}
			//设置返回数据
			dataGridReturnData.setRows(result);
			return dataGridReturnData;
		}else{
			//本身的类型 不包装直接返回
			return returnData;
		}
		return returnData;
		
	}
	//带有抽象方法的内部函数式接口 用于实现操纵包装对象的命令
	@FunctionalInterface
	public interface  WrapCommand{
		public  void wrap(String fieldName,Object fildValue,HashMap<String,Object> wrapHashMap);
	}
	
}
