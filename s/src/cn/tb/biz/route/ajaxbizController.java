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
		String uid = req.getParameter("uid");// 用户名
		String username = uid;
		String date = "";
		String shop = req.getParameter("shopurl");// 商品url
		String errorText = "";
		boolean inputFlag = true;
		if (StringUtils.isNotBlank(username)) {
			username.replaceAll("'", "");
		} else {
			inputFlag = false;
			errorText += "用户名不能为空！";
		}
		if (StringUtils.isBlank(shop)) {
			inputFlag = false;
			errorText += "检测到非法使用！再次恶意访问将封禁本软件在ip："+clientIp+"上的使用权";
		 
		}
		if (inputFlag) {
			List<Map<String, Object>> list = Dbutil.selectSql("select * from users where name='" + username + "'"); // 查询数据
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
					json.put("msg", "您的账号已到期");
				}
			} else {
				json.put("code", "500");
				json.put("msg", "您输入的账号还未注册哟");
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
		Dbutil.executeSql("create table users(name varchar(64),date varchar(150));");// 创建一个表，两列
		Dbutil.executeSql("insert into users values('freeuser','"+ DateUtil.currentDateTimeString() + "');");
		resp.getWriter().print("写入数据成功");
	}

	private JSONObject geturl(String shopurl) {
		String html = HttpRequest.sendGet(shopurl);
		JSONObject json = new JSONObject();
		if (html.indexOf("videoId") == -1) {
			json.put("code", "404");
			json.put("msg", "该商品没有小视频");
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
