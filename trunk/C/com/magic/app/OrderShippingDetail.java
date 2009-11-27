package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * ������������ϸ��Ϣ����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
            message("δ���","û��������Ч�����ݻ���ѡ����Ч������",request, response);
            return;
        }
        
        DocType doc=new DocType(DocType.MEMBER);
        
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);

        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);

        hv.setSubject("�����");
        hv.addContent("<BR>");

        hv.addDetailViewEx(DocType.ORDERSHIPPING,doc_id);
		hv.addContent("<BR>");
		hv.addSubject("�������ϸ");
        hv.addListView(DocType.ORDERSHIPPING_LINES,"id="+doc_id);
        hv.addContent("<BR>");

		hv.addButton("����", "history.go(-1)");
		hv.addButton("��ӡ", "#");
		hv.addButtons();

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();  
    }
}