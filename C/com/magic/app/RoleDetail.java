package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * ������Ա��ɫ������ϸ����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class RoleDetail extends BaseServlet 
{
  
        
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //request.setCharacterEncoding("GB2312");
        int doc_id=0;
        super.service(request, response);
         if(!assertSession(request))
        {
           response.sendRedirect("/relogin.html");
           return;
        }
       HttpSession session=request.getSession(); 
           SessionInfo sessionInfo = new SessionInfo(
            ((Integer)session.getAttribute("login_company_id")).intValue(), 
            ((Integer)session.getAttribute("login_operator_id")).intValue(), 
            (java.util.HashMap)session.getAttribute("powermap"),
            request.getParameterMap());
            
            
        try
		{
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
        }
		    catch(Exception e)
        {
            System.out.println(e);
            message("δ���",e.getMessage(),request, response);
            return;
        }
        
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
            
        try
        {
        
        	stmt=dblink.createStatement();
            rs=stmt.executeQuery("select doc_type ,name,read_only,description from vw_org_role where role_id="+doc_id);
            HTMLView hv=new HTMLView();
            hv.setSessionInfo(sessionInfo);
            hv.setWidth(750);
            hv.setSubject("��ɫ����Ȩ��");
            hv.addListView(rs);
            hv.addButton("����","../crmjsp/role_init.jsp?doc_id="+doc_id);
            hv.addButton("����", "history.go(-1)");
            hv.addButtons();

            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter(); 
           
            out.println(hv.getHTML());
            out.close();  
        }
        	catch(Exception e)
        {
            System.err.println("ERROR>>> error in sql."+e);
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
           

        
    }
}