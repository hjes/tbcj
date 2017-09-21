package cn.tb.util;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8"); 
		resp.setContentType("text/html;charset=utf-8"); 
		resp.setCharacterEncoding("utf-8"); 
		String name = req.getParameter("method");//获取方法名
        if(name == null || name.isEmpty()){
            throw new RuntimeException("没有传递method参数,请给出你想调用的方法");
        }
        Class c = this.getClass();//获得当前类的Class对象
        Method method = null;
        try {
            //获得Method对象
            method =  c.getMethod(name, HttpServletRequest.class,HttpServletResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("没有找到"+name+"方法，请检查该方法是否存在");
        }
        try {
            method.invoke(this, req,resp);//反射调用方法
        } catch (Exception e) {
            System.out.println("你调用的方法"+name+",内部发生了异常");
            throw new RuntimeException(e);
        }
	}
	
}
