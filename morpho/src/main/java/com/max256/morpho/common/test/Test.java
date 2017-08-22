package com.max256.morpho.common.test;

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
	
	
}
