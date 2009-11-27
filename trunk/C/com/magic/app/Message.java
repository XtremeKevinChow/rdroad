package com.magic.app;
import com.magic.utils.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
/**
 * ������ʾ��Ϣ����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class Message extends BaseServlet 
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	System.out.println(getServletContext().getRealPath("/"));
        request.setCharacterEncoding("GBK");
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
        hv.setSubject("��ʾ��Ϣ");
        hv.addMessageBox(title,message);
        
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();    
    }
}