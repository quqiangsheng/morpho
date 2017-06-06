/**
 * Data Dictionary Binder数据字典绑定工具
 */
(function($) {
	$.extend({
		//定义方法 获得数据字典 code 字典value   需要遍历的dictionary字典数组
		getSysCode : function(dictionary, code,url,method) {
			var dic = $(document).data(dictionary);
			//加载dic如果为空则从服务器加载 不为空遍历查找
			if (dic) {
				//遍历dic
				for(item in dic){
					//CODE_NAME是专属字典表属性名如果有值则说明是sys_code表的数据 
					if(item.codeName==dictionary&&item.codeValue==code){
						return item.codeText;
					}
				}
				//如果没有找到返回原函数值
				return code;
			}
			//从服务器加载
			$.loaddictionary(dictionary,url,method);
			dic = $(document).data(dictionary);
			//
			if (dic) {
				//遍历dic
				for(item in dic){
					//CODE_NAME是专属字典表属性名如果有值则说明是sys_code表的数据 
					if(item.codeName==dictionary&&item.codeValue==code){
						return item.codeText;
					}
				}
				//如果没有找到返回原函数值
				return code;
			}
		}
	});
	$.extend({
		//定义方法 获得数据字典数组  自定义数据结构自行遍历
		getSysCodeArray : function(dictionary, code,url,method) {
			var dic = $(document).data(dictionary);
			//加载dic如果为空则从服务器加载 
			if (dic) {
				return dic;
			}
			//从服务器加载
			$.loaddictionary(dictionary,url,method);
			dic = $(document).data(dictionary);
			//
			if (dic) {
				return dic;
			}else{
				return null;
			}
		}
	});
	//初始化页面时执行下面这段js 向服务器加在需要的字典列表 并缓存在客户端
	//dictionary字典名字   url请求url  请求方式method ‘get’或者‘post’
	$.loaddictionary = function(dictionary, url,method) {
		//数据缓存dictionary存储的数据名
		var dic = $(document).data(dictionary);
		//dic存储的value是[]数组
		if (!dic) {
			//没有数据的话 向服务器加载数据  
			var param = {
				pageNum:1,
				pageSize:100000,
				codeName:dictionary//codeName是需要查找的字典名称 如果为空则返回全部字典 前边的分页参数默认取前10w条 实际不可能达到这已经很大了 具体要根据服务器端实现
			};
			$.ajax({
				type : method,
				url : url ,
				data : param,
				dataType : 'json',
				async:false,
				success : function(data) {
					if(data.status=='1'){
						//如果成功
						dic = [];//数组
						dic =data.rows;//缓存
						$(document).data(dictionary, dic);
					}else{
						console.warn("加载数据字典时失败！返回信息"+data.info);
					}
					
				},
				error : function() {
					console.warn("加载数据字典时失败！网络错误");
				}
			});
		}
	};
})(jQuery);
