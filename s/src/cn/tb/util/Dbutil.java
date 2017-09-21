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
			stat.executeUpdate("create table table1(name varchar(64));");// ����һ��������
			stat.executeUpdate("insert into table1 values('aa',12);"); // ��������
			stat.executeUpdate("insert into table1 values('bb',13);");
			stat.executeUpdate("insert into table1 values('cc',20);");
			ResultSet rs = stat.executeQuery("select * from table1;"); // ��ѯ����

			while (rs.next()) { // ����ѯ�������ݴ�ӡ����
				System.out.print("name = " + rs.getString("name") + " "); // ������һ
				System.out.println("age = " + rs.getString("age")); // �����Զ�
			}
			rs.close();
			stat.close();
			conn.close(); // �������ݿ������
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
