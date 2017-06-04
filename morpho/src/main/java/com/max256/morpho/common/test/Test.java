package com.max256.morpho.common.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import com.asprise.ocr.Ocr;
import com.max256.morpho.common.config.HttpStatus;

public class Test {

/*	public static void main(String[] args) {
		 //数据库连接  
        Connection connection = null;  
        //预编译的Statement，使用预编译的Statement提高数据库性能  
        PreparedStatement preparedStatement1 = null;  
        PreparedStatement preparedStatement2 = null;  
        //结果 集  
        ResultSet resultSet1 = null;  
        ResultSet resultSet2 = null;  
        
        Set<String> set1=new TreeSet<>();
        Set<String> set2=new TreeSet<>();
        Set<String> set3=new TreeSet<>();
        
        
        try {  
            //加载数据库驱动                                                                 
            Class.forName("com.mysql.jdbc.Driver");  
              
            //通过驱动管理类获取数据库链接  
            connection =  (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/weixin?characterEncoding=utf-8", "root", "root");  
            //定义sql语句 ?表示占位符  
            String sql1 = "select aac002 from aaa ";  
            String sql2 = "select aac002 from bbb ";  
            //获取预处理statement  
            preparedStatement1 = connection.prepareStatement(sql1);  
            preparedStatement2 = connection.prepareStatement(sql2);  
            //向数据库发出sql执行查询，查询出结果集  
            resultSet1 =  preparedStatement1.executeQuery();  
            resultSet2 =  preparedStatement2.executeQuery();  
            //遍历查询结果集  
            while(resultSet1.next()){  
                //System.out.println(resultSet1.getString("id")+"  "+resultSet1.getString("username"));  
            	set1.add(resultSet1.getString("aac002").trim());
            }  
            while(resultSet2.next()){  
                //System.out.println(resultSet1.getString("id")+"  "+resultSet1.getString("username"));  
            	set2.add(resultSet2.getString("aac002").trim());
            } 
            //下面是 sql1是照片  2是我本地备份库
            //找
            for (Iterator iterator = set1.iterator(); iterator.hasNext();) {
				String string1 = (String) iterator.next();
				if(!set2.contains(string1)){
					System.out.println(string1);
					set3.add(string1);
				}
			}
            System.out.println(set3.size());
            System.out.println(set3.size());
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            //释放资源  
            if(resultSet1!=null){  
                try {  
                    resultSet1.close();  
                } catch (SQLException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
            if(preparedStatement1!=null){  
                try {  
                    preparedStatement1.close();  
                } catch (SQLException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
            if(connection!=null){  
                try {  
                    connection.close();  
                } catch (SQLException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
  
        }  

	}*/
	
	public static void main(String[] args)throws IOException {
		/*test1134();*/
		String name="com.max256.SysUser";
		StringBuilder builder = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < builder.length() - 1; i++) {
			if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i),
					builder.charAt(i + 1))) {
				builder.insert(i++, '_');
			}
		}
		System.out.println(builder.toString());
	}
	private static boolean isUnderscoreRequired(char before, char current, char after) {
		return Character.isLowerCase(before) && Character.isUpperCase(current)
				&& Character.isLowerCase(after);
	}
	public static void test11(){
		
	}
	public static void test113(){
		//测试httpStatus
		System.out.println(HttpStatus.BAD_GATEWAY);
	}
	public static void test1134() throws IOException{
		String code=null;
		//测试爬虫的验证码模拟登录
		//登录主页 获取cookie
		Map<String, String> cookies = null;
		Response res = Jsoup.connect("http://localhost:8080/morpho/login").method(Method.GET).timeout(30000).execute();
		cookies = res.cookies();
		System.out.println(cookies);
		
		for(;;){
			if(StringUtils.isBlank(code)){
				//带cookie获取验证码
				Response resCaptcha = Jsoup.connect("http://localhost:8080/morpho/captcha.jpg").ignoreContentType(true).cookies(cookies).method(Method.GET).timeout(30000).execute();
				//图片的字节数组
				byte[] captcha=resCaptcha.bodyAsBytes();
				ByteArrayInputStream in = new ByteArrayInputStream(captcha); //将b作为输入流；
				BufferedImage image = ImageIO.read(in); //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
				//识别验证码
				//尝试多次识别
				code=recognize(image);
			}else{
				break;
			}
			
		}
		
		//空的话
		//请求参数
		Map<String, String> map = new HashMap<String, String>();  
		map.put("uname", "admin");  
		map.put("upass", "admin");  
		map.put("captcha", code);  
		//识别的验证码+用户名密码登录
		Response res2 = Jsoup.connect("http://localhost:8080/morpho/login").
				data(map).method(Method.POST).timeout(30000).execute();
		
		//调用其他api
		if(res2.body().contains("系统验证码错误")){
			test1134();
		}else{
			System.out.println(res2.body());
		}
		
	}

	public static String recognize(BufferedImage image) {
        Ocr.setUp();
        Ocr ocr = new Ocr();
        ocr.startEngine("eng", Ocr.SPEED_SLOW);
        String result = ocr.recognize(image,com.asprise.ocr.Ocr.RECOGNIZE_TYPE_ALL, com.asprise.ocr.Ocr.OUTPUT_FORMAT_PLAINTEXT,0,null);
        ocr.stopEngine();
        System.out.println("识别结果:"+result);
        return result;
	}
}
