package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * 产生会员信件列表界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
 
public class LetterDetail extends BaseServlet 
{
    
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        request.setCharacterEncoding("GBK");
        
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
            
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;   
        int doc_id=0;
        int status = 0;
        try{
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
            stmt=dblink.createStatement();rs=stmt.executeQuery("select status from mbr_member_letters where id="+doc_id);
            if(rs.next()){
              status = rs.getInt("status");
            }   
            rs.close();
        }catch(Exception e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
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
        DocType doc=new DocType(DocType.MEMBER_LETTERS);
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject("信件打印");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.MEMBER_LETTERS,doc_id);
        hv.addContent("<BR>");
        hv.addButton("信件浏览","../crmjsp/document_templates_print_submit.jsp?doc_type=5090&act=view&doc_id="+doc_id);
        if(status==100){
          hv.addButton("事件查询","../crmjsp/document_templates_event.jsp?doc_type=5090&doc_id="+doc_id);
        }
        hv.addButton("导出地址","../crmjsp/document_templates_print_submit.jsp?doc_type=5090&act=export&doc_id="+doc_id);
        hv.addButton("信件确定","../crmjsp/document_templates_print_submit.jsp?doc_type=5090&act=submit&doc_id="+doc_id);
        hv.addButton("信件删除","../crmjsp/document_templates_print_submit.jsp?doc_type=5090&act=del&doc_id="+doc_id);
        hv.addButton("返回", "../app/welcome");
       
        hv.addButtons();
        
        dblink.close();
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();  
    }
}