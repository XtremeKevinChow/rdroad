package com.magic.app;
import com.magic.utils.MD5;
import com.magic.utils.*;
import javax.servlet.*;
import javax.servlet.http.*;


import java.sql.*;
import java.net.*;
import java.io.*;
import java.util.HashMap;
/**
 * 会员登录处理
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class UserLogin extends javax.servlet.http.HttpServlet {

	HttpSession session;
	HttpServletRequest request;
	HttpServletResponse response;
	String uid;
	String password;
	String app;

	public void service(HttpServletRequest request, HttpServletResponse response)throws IOException,ServletException
   {
		this.request=request;
		this.response=response;
		this.session=request.getSession();
    
   // System.out.println("******************* is 1");
		String action_type=request.getParameter("action");
    if(request.getParameter("action")==null)
				 action_type="user_login";
    if(action_type.equals("logout"))
    {
        try{
            handleLogout();
        }catch(Exception e)
        {
            e.printStackTrace();
            HttpSession session=request.getSession();
            session.setAttribute("showMsgTitle","未完成");
            session.setAttribute("showMsgContent",e.getMessage());            
            response.sendRedirect("/app/message");
            //message("未完成",e.getMessage(),request, response);
        }
       // System.out.println("******************* is 2");
        return;
    }
/*
 * 密码验证在struts系统已经存在
 * 这里注释掉不做出里
 */
		
		uid=request.getParameter("userName");
        MD5 m= new MD5();
        password=m.getMD5ofStr(request.getParameter("password"));
    
		/*
		if(request.getParameter("uid") == null || request.getParameter("password")==null)
	   {
			System.out.println("******************* is 3");
          response.sendRedirect("login_failed.jsp");
          return;
	   }
	       */
		app=request.getParameter("app");
		if(app==null) app="DEFAULT";		
		try
	   {
//		if (uid.equals("admin"))
//			handleAdminLogin();
//		else
//		   {
				handleUserLogin();
//		   }
	   }catch(Exception e)
	   {
			e.printStackTrace();
      HttpSession session=request.getSession();
      session.setAttribute("showMsgTitle","未完成");
      session.setAttribute("showMsgContent",e.getMessage());            
      response.sendRedirect("/app/message");
	   }
   }

	private void handleAdminLogin() throws Exception 
	{
		DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
		CallableStatement cstmt = dblink.prepareCall("{?=call admin_login_check( ?,? ) }"); 
		cstmt.setString(2,uid); 
		cstmt.setString(3,password);
		cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
		cstmt.execute();
		if(cstmt.getShort(1)<0)
		{
          dblink.close();
          response.sendRedirect("login_failed.jsp");
		  return;
        }
        dblink.close();
		session.setAttribute("login_company_id",new Integer(0));//Info,Warning,Error
    session.setAttribute("login_operator_id",new Integer(0));
		response.sendRedirect("sys_admin_index.jsp");
		return;
	}
    public void handleUserLogin() throws Exception
    {

       DBLink dblink=new DBLink();
       Statement stmt=null;
       ResultSet rs=null;
       try
       {
           String sql="select company_id,id from org_persons where userid='"+uid+"' and pwd='"+password+"' and status=0";
		 stmt=dblink.createStatement();rs= stmt.executeQuery(sql);
		if(!rs.next())
		{
			//System.out.println("******************* is 4");
            rs.close();
            stmt.close();
            dblink.close();
            response.sendRedirect("login_failed.jsp");
		  return;
        }
        
		int company_id=rs.getInt("company_id");
		int operator_id=rs.getInt("id");
        
		rs.close();

		//将用户权限加入的HashMap中
		HashMap powerMap = null;
		sql = "select role_id from org_person_role where person_id="+operator_id;

		String session_id=session.getId();
		rs=stmt.executeQuery(sql);
		
		int doc_type = 0;
		int read_only = 0;
		powerMap = new HashMap();
		while(rs.next()){
			ResultSet subRs = null;
			sql = "select doc_type,read_only from org_role_privilege where role_id="+rs.getInt("role_id");
			Statement stmt2=dblink.createStatement();subRs=stmt2.executeQuery(sql);
			while(subRs.next()){
				doc_type = subRs.getInt("doc_type");
				read_only = subRs.getInt("read_only");
				if(powerMap.containsKey(new Integer(doc_type))){
					int power = ((Integer)powerMap.get(new Integer(doc_type))).intValue();
					if(power < read_only){
						powerMap.remove(new Integer(doc_type));
						powerMap.put(new Integer(doc_type),new Integer(read_only));
					}
				} else {
				  powerMap.put(new Integer(doc_type),new Integer(read_only));
				}
			}
            subRs.close();
            stmt2.close();
		}
    //End
        rs.close();
        stmt.close();

		CallableStatement cstmt =dblink.prepareCall("{call session_refresh(?,?,?)}");
		cstmt.setString(1,session_id); 
		cstmt.setInt(2,operator_id);
        cstmt.setString(3,request.getRemoteAddr());
		cstmt.execute();
		//System.out.println("******************* is 5");
        session.setAttribute("login_company_id",new Integer(company_id));
        session.setAttribute("login_operator_id",new Integer(operator_id));
        session.setAttribute("session_id",session.getId());
		session.setAttribute("powermap",powerMap);
		//System.out.println("******************* is 6 is "+session.getId());
        cstmt.close();         
        dblink.close();
       }catch(Exception e)
       {
           e.printStackTrace();
            HttpSession session=request.getSession();
            session.setAttribute("showMsgTitle","未完成");
            session.setAttribute("showMsgContent",e.getMessage());            
            response.sendRedirect("/app/message"); 
            return;           
       }finally
       {
           try
           {
               if(rs!=null) rs.close();
               if(stmt!=null) stmt.close();
               dblink.close();
           }catch(Exception e)
           {
               e.printStackTrace();
           }
       }
         
		//response.sendRedirect("/app/welcome");
       response.sendRedirect("/CRM/index.jsp");
       

		return;
	}
    public void handleLogout()throws Exception
    {
        session.removeAttribute("login_company_id");
        session.removeAttribute("login_operator_id");
        DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
        dblink.executeDelete("delete s_session where session_id='"+request.getSession().getId()+"'");
        dblink.close();
        response.sendRedirect("/login.html");
    }
   
}