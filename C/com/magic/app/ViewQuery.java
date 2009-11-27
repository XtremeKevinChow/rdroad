package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import com.magic.utils.*;
/**
 * 查询界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class ViewQuery extends BaseServlet 
{
  
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    
    
   
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("GBK");
        int doc_type =0;
        DocType doc;
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
            
    
        try{

            doc_type=Integer.parseInt(request.getParameter("doc_type"));
            doc=new DocType(doc_type);
        }catch(Exception e)
        {
            System.out.println(e);

            message("未完成",e.getMessage(),request, response);
            return;
        }


        switch(doc_type)
        {       
          /*case DocType.MEMBER:
              this.forward("/app/memberquery");
              break;   
              
          case DocType.PRODUCT:
              this.forward("/app/productquery");
              break;   */

          default:
              HTMLView hv=new HTMLView();
              hv.setSessionInfo(sessionInfo);
              hv.setWidth(750);
              
              hv.setSubject("查询&nbsp;"+doc.getDocName());
              hv.addQueryView(doc_type);
              
              response.setContentType(CONTENT_TYPE);
              PrintWriter out = response.getWriter(); 
             
              out.println(hv.getHTML());
              out.close();    
        }
    }
}