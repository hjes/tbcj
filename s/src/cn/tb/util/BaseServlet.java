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
		String name = req.getParameter("method");//��ȡ������
        if(name == null || name.isEmpty()){
            throw new RuntimeException("û�д���method����,�����������õķ���");
        }
        Class c = this.getClass();//��õ�ǰ���Class����
        Method method = null;
        try {
            //���Method����
            method =  c.getMethod(name, HttpServletRequest.class,HttpServletResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("û���ҵ�"+name+"����������÷����Ƿ����");
        }
        try {
            method.invoke(this, req,resp);//������÷���
        } catch (Exception e) {
            System.out.println("����õķ���"+name+",�ڲ��������쳣");
            throw new RuntimeException(e);
        }
	}
	
}
