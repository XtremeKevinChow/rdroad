package com.magic.app;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.magic.utils.*;
/**
 * 生成新增团体会员界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class MemberGroupNew extends BaseServlet 
{

    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
   
         request.setCharacterEncoding("GB2312");
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
            
            
        int doc_type =0;
         DocType doc;
        
        doc=new DocType(DocType.MEMBER_GROUP);
              HTMLView hv=new HTMLView();
              hv.setSessionInfo(sessionInfo);
              hv.setWidth(750);
              
              hv.setSubject("增加&nbsp;"+doc.getDocName());
              hv.addContent("<table width=750 ><tr><td width=20% align=\"right\" >内容</td><td>字段1>内容;<br>字段2=内容;</td></tr></table>\n");
              hv.addNewView(DocType.MEMBER_GROUP);
              
              response.setContentType(CONTENT_TYPE);
              PrintWriter out = response.getWriter(); 
             
              out.println(hv.getHTML());
              out.close();    
   
  }
}