package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 活动详细类
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */


public class ActivityDetail extends BaseServlet 
{
    public void init( ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int doc_id=0;
        int doc_type;
        int member_id=0;
        int ref_doc_id=0;
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
            
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
            stmt=dblink.createStatement();
            rs=stmt.executeQuery("select member_id,event_type ,ref_doc_id from mbr_events where id="+doc_id);
            rs.next();
            doc_type=rs.getInt("event_type");
            member_id=rs.getInt("member_id");
            ref_doc_id=rs.getInt("ref_doc_id");
        }catch(Exception e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
        }finally
        {
            try{
                if (rs != null ) rs.close();
                if (stmt != null ) stmt.close();
                if(dblink!=null) dblink.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
        
		switch(doc_type){

		case DocType.ORDER:
			response.sendRedirect("../app/viewdetail?doc_type="+doc_type+"&doc_id="+ref_doc_id);
			break;
                
        case DocType.ORDER_CHANGE:
			response.sendRedirect("../app/viewdetail?doc_type=4000&doc_id="+ref_doc_id);
			break;
            
        case DocType.MBR_ORDER_CANCEL:
					 response.sendRedirect("../app/viewdetail?doc_type=4000&doc_id="+ref_doc_id);
				   break;
				 
				 case DocType.MEMBER_ANIMUS_ORDER:
					   response.sendRedirect("../app/orderreturndetail?doc_type="+DocType.MEMBER_RETURN_ORDER+"&doc_id="+ref_doc_id);
				   break;

				 case DocType.MEMBER_RETURN_ORDER:
					   response.sendRedirect("../app/orderreturndetail?doc_type="+DocType.MEMBER_RETURN_ORDER+"&doc_id="+ref_doc_id);
				   break;

				 default:
           DocType doc=new DocType(doc_type);

           HTMLView hv=new HTMLView();
           hv.setSessionInfo(sessionInfo);
           hv.setWidth(750);
           hv.setStyle(HTMLView.REPORT_STYLE);
           hv.setSubject(doc.getDocName());
           hv.addSubject("会员信息");
           hv.addDetailViewEx(DocType.MEMBER,member_id);
           hv.addContent("<br>");
           hv.addSubject("事件明细");
           hv.addDetailViewEx(doc_type,doc_id);
           hv.addContent("<br>");
          // hv.addButton("更改","/app/viewupdate?doc_type="+doc_type+"&doc_id="+doc_id);
          //  hv.addButton("删除","/app/ctrdelete?doc_type="+doc_type+"&doc_id="+doc_id);
           hv.addButton("返回", "history.go(-1)");
           hv.addButtons();
           response.setContentType(CONTENT_TYPE);
           PrintWriter out = response.getWriter(); 
       
           out.println(hv.getHTML());
           out.close();  
			 }
    }
}