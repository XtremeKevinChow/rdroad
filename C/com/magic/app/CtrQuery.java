package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
/**
 * 系统处理查询时Servlet
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class CtrQuery extends BaseServlet 
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
            
       try{
            int doc_type=Integer.parseInt(request.getParameter("doc_type"));
            
             getServletConfig().getServletContext().getRequestDispatcher("/app/viewlist?doc_type="+doc_type).forward(request,response);
            //     response.sendRedirect("/app/viewlist?doc_type="+doc_type);
            return;
        }catch(Exception e)
        {
             System.out.println(e);
             message("未完成",e.getMessage(),request, response);
             return;
        }
    }
}