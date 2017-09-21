package cn.tb.biz.route;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.tb.util.BaseServlet;
import cn.tb.util.HttpRequest;

public class ajaxbizController extends BaseServlet {
	
	 public void check(HttpServletRequest req, HttpServletResponse resp) throws  Exception  {
		 	String username="";
		 	String shop=req.getParameter("shopurl");
		 	Class.forName("org.sqlite.JDBC");
		 	Connection conn = DriverManager.getConnection("jdbc:sqlite:/lincj.db");
		 	Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from users where name='freeuser'"); // ��ѯ����
			while (rs.next()) { // ����ѯ�������ݴ�ӡ����
				username=rs.getString("name"); // ������һ
			}
		 	stat.close();
			conn.close(); // �������ݿ������
			if(username!=""){
				geturl(shop);
			}
			resp.getWriter().print(username);
	  }
	 public void init(HttpServletRequest req, HttpServletResponse resp) throws  Exception {
		 	Class.forName("org.sqlite.JDBC");
		 	Connection conn = DriverManager.getConnection("jdbc:sqlite:/lincj.db");
		 	Statement stat = conn.createStatement();
		 	stat.executeUpdate("drop table users;");// ����һ��������
			stat.executeUpdate("create table users(name varchar(64));");// ����һ��������
			stat.executeUpdate("insert into users values('freeuser');");
			stat.close();
			conn.close(); // �������ݿ������
			resp.getWriter().print("д�����ݳɹ�");
	  }
	 
	 private JSONObject geturl(String shopurl){
		 String html=HttpRequest.sendGet(shopurl, "");
		 JSONObject json=new JSONObject();
		 if (html.indexOf("videoId")==-1) {
			 json.put("code","-1");
			 json.put("msg","����Ʒû��С��Ƶ");
		 }else{
			 String uid=getStringMid(html,"userid:", ";");
			 String vid=getStringMid(html,"videoId\":\"", "\"");
			 String fileurl = "http://cloud.video.taobao.com/videoapi/info.php?vid=" + vid + "&uid=" + uid + "&p=1&t=6";
			 html=HttpRequest.sendGet(fileurl, "");
			 String vurl=getStringMid(html,"video_url>", "</video_");
			 json.put("code","-1");
			 json.put("msg",vurl);
		 }
		 return json;
	 }
	 
	 private String getStringMid(String str,String start,String end){
		    Pattern p=Pattern.compile(start+"(\\w+)</icon>"+end);
		    Matcher m=p.matcher(str);
		    while(m.find()){
		       str=m.group(1);
		       break;
		    }
		    return str;
	 }
	
}
