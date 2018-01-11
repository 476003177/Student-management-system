/*
 * 对数据库进行操作的类
 */
package Sms;

import java.sql.*;

public class SqlHelper {

	Connection ct=null;
	ResultSet rs=null; 
	PreparedStatement ps=null; 
	String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:sqlserver://localhost:1433; DatabaseName=spdb1";
	String user="sa";
	String passwd="20070401";
	
	public boolean link()
	{
		boolean b = true;
		try {
			b = true;
			Class.forName(driver); //加载驱动
			ct = DriverManager.getConnection(url, user, passwd); //得到连接
			if (!ct.isClosed()) {
				System.out.println("数据库连接成功");
			} else {
				System.out.println("数据库连接失败");
				b=false;
			} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return b;
	}
	
	public boolean close()                          
	{
		boolean b=true;
		try {
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
			if(ct!=null)ct.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	
	//增删改
	public boolean updExectue(String sql,String []paras)
	{
		boolean b=true;
		try {
			if(this.link()==false)
			{
				b=false;
				return b;
			}
			ps=ct.prepareStatement(sql);
			for (int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			if(ps.executeUpdate()!=1)b=false;
		} catch (Exception e2) {
			e2.printStackTrace();
		}finally{
			this.close();
		}
		return b;
	}
	
	
	//查询
	public ResultSet queryExectue(String sql,String []paras)
	{
		try {
			this.link();
			ps=ct.prepareStatement(sql);
			for (int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
		} catch (Exception e2) {
			e2.printStackTrace();
		}finally{
			
		}
		return rs;
	}
	
}
