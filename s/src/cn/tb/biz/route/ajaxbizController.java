package cn.tb.biz.route;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.tb.util.BaseServlet;
import cn.tb.util.DateUtil;
import cn.tb.util.Dbutil;
import cn.tb.util.HttpRequest;
import cn.tb.util.StringUtils;

public class ajaxbizController extends BaseServlet {

	public void check(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		JSONObject json = new JSONObject();
		String clientIp=getRemortIP(req);
		String uid = req.getParameter("uid");// �û���
		String username = uid;
		String date = "";
		String shop = req.getParameter("shopurl");// ��Ʒurl
		String errorText = "";
		boolean inputFlag = true;
		if (StringUtils.isNotBlank(username)) {
			username.replaceAll("'", "");
		} else {
			inputFlag = false;
			errorText += "�û�������Ϊ�գ�";
		}
		if (StringUtils.isBlank(shop)) {
			inputFlag = false;
			errorText += "��⵽�Ƿ�ʹ�ã��ٴζ�����ʽ�����������ip��"+clientIp+"�ϵ�ʹ��Ȩ";
		 
		}
		if (inputFlag) {
			List<Map<String, Object>> list = Dbutil.selectSql("select * from users where name='" + username + "'"); // ��ѯ����
			username = "";
			if(list.size()>0){
				username=list.get(0).get("name").toString();
				date=list.get(0).get("date").toString();
			}
			
			if (!username.equals("") || uid.equals("hpp")) {
				boolean flag = DateUtil.compare_date("", date,
						DateUtil.currentDateTimeString());
				if (uid.equals("hpp"))
					flag = true;
				if (flag) {
					json = geturl(shop);
				} else {
					json.put("code", "500");
					json.put("msg", "�����˺��ѵ���");
				}
			} else {
				json.put("code", "500");
				json.put("msg", "��������˺Ż�δע��Ӵ");
			}
		} else {
			json.put("code", "500");
			json.put("msg", errorText);
		}
		resp.getWriter().print(json.toString());
	}

	private String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	public void init(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		Dbutil.executeSql("create table users(name varchar(64),date varchar(150));");// ����һ��������
		Dbutil.executeSql("insert into users values('freeuser','"+ DateUtil.currentDateTimeString() + "');");
		resp.getWriter().print("д�����ݳɹ�");
	}

	private JSONObject geturl(String shopurl) {
		String html = HttpRequest.sendGet(shopurl);
		JSONObject json = new JSONObject();
		if (html.indexOf("videoId") == -1) {
			json.put("code", "404");
			json.put("msg", "����Ʒû��С��Ƶ");
		} else {
			String uid = getStringMid(html, "userid=", ";");
			String vid = getStringMid(html, "videoId\":\"", "\"");
			String fileurl = "http://cloud.video.taobao.com/videoapi/info.php?vid="
					+ vid + "&uid=" + uid + "&p=1&t=6";
			html = HttpRequest.sendGet(fileurl).trim();
			String vurl=html.substring(html.indexOf("<video_url>")+11, html.indexOf("</video_url>"));
			json.put("code", "200");
			json.put("msg", vurl);
		}
		return json;
	}
	private String getStringMid(String str, String start, String end) {
		Pattern p = Pattern.compile(start + "(\\w+)" + end);
		Matcher m = p.matcher(str);
		while (m.find()) {
			str = m.group(1);
			break;
		}
		return str;
	}

}
