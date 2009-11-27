package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * 产生发货单详细信息界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class OrderShippingDetail extends BaseServlet 
{

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //request.setCharacterEncoding("GB2312");
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
            
            
		int status = 0 ;
        int doc_id=0;
		int doc_type=0;
        try{
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
        }catch(Exception e)
        {
            message("未完成","没有输入有效的数据或者选择有效的数据",request, response);
            return;
        }
        
        DocType doc=new DocType(DocType.MEMBER);
        
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);

        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);

        hv.setSubject("配货单");
        hv.addContent("<BR>");

        hv.addDetailViewEx(DocType.ORDERSHIPPING,doc_id);
		hv.addContent("<BR>");
		hv.addSubject("配货单详细");
        hv.addListView(DocType.ORDERSHIPPING_LINES,"id="+doc_id);
        hv.addContent("<BR>");

		hv.addButton("返回", "history.go(-1)");
		hv.addButton("打印", "#");
		hv.addButtons();

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();  
    }
}