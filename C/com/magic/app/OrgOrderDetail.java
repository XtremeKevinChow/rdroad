package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 产生团体会员订单详细信息界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class OrgOrderDetail extends BaseServlet 
{
 

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        
        request.setCharacterEncoding("GBK");
        int doc_id=0;
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
          doc_id=Integer.parseInt(request.getParameter("doc_id"));
        }catch(Exception e)
        {
            message("未完成",e.getMessage(),request, response);
            return;
        }
        DocType doc=new DocType(DocType.ORDER);
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject("团体会员订单");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.ORG_ORDER,doc_id);
        
        hv.addContent("<BR>");
     
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            stmt=dblink.createStatement();
            rs= stmt.executeQuery("select id from ord_delivery_lines where order_id ="+doc_id);
            rs.next();
            int delivery_id=rs.getInt("id");
            hv.addDetailViewEx(DocType.ORDER_DELIVERY,delivery_id);
            hv.addContent("<BR>");
            hv.addListView(DocType.ORDER_LINE,"order_id="+doc_id,true);
            hv.addContent("<BR>");
            int status = 0;

           stmt=dblink.createStatement();
           rs= stmt.executeQuery("select order_status from vw_org_orders where order_id="+doc_id);
           if(!rs.next()) throw new KException("没有找到记录");
           
           status = rs.getInt("order_status");
           if(status >=0 && status<=15){
                hv.addButton("更改","../app/ordersubmit?action=orgorder&doc_id="+doc_id + "&doc_type=5050");
                hv.addButton("取消","../app/ordersubmit?action=orgordercancel&doc_type=5050&doc_id="+doc_id);
           }
           if(status==-6){
             hv.addButton("取消修改","../app/ordersubmit?action=canceledit&doc_type=5050&doc_id="+doc_id);
           }    
           
            hv.addButton("返回", "history.go(-1)");
            hv.addButtons();
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter(); 
            out.println(hv.getHTML());
            out.close(); 
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}