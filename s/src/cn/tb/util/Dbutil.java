package cn.tb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dbutil {

 
	
	public static int executeSql(String sql) throws  Exception{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:/lincj.db");
		Statement stat = conn.createStatement();
		int i = stat.executeUpdate(sql);
		stat.close();
		conn.close();
		return i;
	}
	public static  List<Map<String, Object>> selectSql(String sql) throws  Exception{
		 List<Map<String, Object>> list = new ArrayList<>();
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:/lincj.db");
		Statement stat = conn.createStatement();
		ResultSet rs=stat.executeQuery(sql);
	    ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columnList = new ArrayList<String>();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            columnList.add(rsmd.getColumnName(i + 1));
        }
        // ѭ��������¼
        while (rs.next()) {
            Map<String, Object> obj = new HashMap<>();
            // ����һ����¼�е�������
            for (int i = 0; i < columnList.size(); i++) {
                // ��ȡ����
                String column = columnList.get(i);
                obj.put(column, rs.getObject(column));
            }
            list.add(obj);
        }
        stat.close();
		conn.close();
		return list;
	}
	
	 

}
