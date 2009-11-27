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
public class OrgMemberDetail extends BaseServlet 
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
        DocType doc=new DocType(DocType.MEMBER);
        
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject("团体会员");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.MEMBER_ORGANIZATION,doc_id);
				//hv.addButton("更改","/app/viewupdate?doc_type=1710&doc_id="+doc_id);
        //hv.addButton("返回", "/goto?t_code=1330");

				String s="<table width=\"750.0\" border=0 cellspacing=1 cellpadding=5  align=center>\n";
				s=s+"<tr><td align=right>\n";
				s=s+"<input type=\"button\" class=\"button2\" value=\"更改\"  onClick=\"javascript:document.location.href='../app/viewupdate?doc_type=1710&doc_id="+doc_id+"';\">";
        s=s+"<input type=\"button\" class=\"button2\" value=\"返回\"  onClick=\"javascript:document.location.href='../goto?t_code=1330';\">";
				s=s+"</td></tr></table>";
        hv.addContent(s);       
        //hv.addButtons();
				hv.addContent("<BR>");
        hv.addSubject("会员联系地址");
        hv.addConfigListView(DocType.ORGANIZATION_ADDRESS,"member_id="+doc_id);
        hv.addContent("<BR>");
				//hv.addButton("事件列表","/app/viewlist?doc_type=2000&member_id="+doc_id);


        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();
    }
}