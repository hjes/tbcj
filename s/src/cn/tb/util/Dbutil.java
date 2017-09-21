package cn.tb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbutil {

	private static Connection conn = null;

	static {
		OpenConnection();
	}
	
	private static void CloseConnection() throws SQLException{
		if(conn!=null){
			conn.close();
		}
	}
	private static void OpenConnection(){
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:/lincj.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 
	
	
	public static void main(String[] args) {
		try {
			Statement stat = conn.createStatement();
			stat.executeUpdate("create table table1(name varchar(64));");// 创建一个表，两列
			stat.executeUpdate("insert into table1 values('aa',12);"); // 插入数据
			stat.executeUpdate("insert into table1 values('bb',13);");
			stat.executeUpdate("insert into table1 values('cc',20);");
			ResultSet rs = stat.executeQuery("select * from table1;"); // 查询数据

			while (rs.next()) { // 将查询到的数据打印出来
				System.out.print("name = " + rs.getString("name") + " "); // 列属性一
				System.out.println("age = " + rs.getString("age")); // 列属性二
			}
			rs.close();
			stat.close();
			conn.close(); // 结束数据库的连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
