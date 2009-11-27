package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
/**
 * 操作成功提示界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class Success extends BaseServlet 
{
  public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        super.service(request,response);
         if(!assertSession(request))
        {
           response.sendRedirect("/relogin.html");
           return;
        }
        HttpSession session=request.getSession();
        String title=(String)session.getAttribute("showMsgTitle");
        String message=(String)session.getAttribute("showMsgContent");
        
        HTMLView hv=new HTMLView();
        hv.setSubject("提示信息");
        hv.addSuccessMessageBox(title,message);
        
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();    
    }
}