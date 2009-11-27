package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 产生团体会员详细信息界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class MemberGroupDetail extends BaseServlet 
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
         
            
        int doc_id=0;
        try{
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
        }catch(Exception e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
        }
        DocType doc=new DocType(DocType.MEMBER_GROUP);
        
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject("会员组");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.MEMBER_GROUP,doc_id);
        
        hv.addContent("<BR>");
        hv.addButton("会员详细","../crmjsp/member_group_detail.jsp?doc_type=1000&doc_id="+doc_id);
        hv.addButton("发送目录","../crmjsp/catalog_print_submit.jsp?doc_type="+DocType.MEMBER_GROUP+"&doc_id="+doc_id);
        hv.addButton("发送信件","../crmjsp/document_templates_print.jsp?doc_type="+DocType.MEMBER_LETTERS+"&mbr_groups="+doc_id + "&type=0");
        hv.addButton("导出地址","../crmjsp/document_templates_print_submit.jsp?doc_type="+DocType.MEMBER_LETTERS+"&mbr_groups="+doc_id + "&type=0&act=addr");
        hv.addButton("邮件列表","../crmjsp/document_templates_print.jsp?doc_type="+DocType.MEMBER_LETTERS+"&mbr_groups="+doc_id + "&type=1");
        hv.addButton("返回", "history.go(-1)");
       
        hv.addButtons();
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();  
    }
}