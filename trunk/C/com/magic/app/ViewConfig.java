package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * 带按钮的浏览界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class ViewConfig extends BaseServlet 
{
  
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("GBK");
        super.service(request, response);
        /*
         * 去掉原由的权限判断
         */
      
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
        String data_source ;
        try{
            doc_type=Integer.parseInt(request.getParameter("doc_type"));
            doc=new DocType(doc_type);
        }catch(Exception e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
        }

        

        
				switch(doc_type){

					//促销产品组
					case DocType.GROUP_LIST:
						System.out.println("1");
						response.sendRedirect("../crmjsp/group_list.jsp?doc_type="+doc_type);
					  break;
          default:
				    HTMLView hv=new HTMLView();
            hv.setSessionInfo(sessionInfo);
            hv.setDocType(doc_type);
            hv.setWidth(750);
            hv.setSubject(doc.getDocName());
            hv.addConfigListView(doc_type);
            hv.addContent("<br>\n");
            hv.addButtons();
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter(); 
       
            out.println(hv.getHTML());
            out.close();
				}
    }
}