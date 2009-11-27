package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 产生会员详细信息界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class MemberDetail extends BaseServlet 
{
  

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
     
        super.service(request, response);
         if(!assertSession(request))
        {
           response.sendRedirect("/relogin.html");
           return;
        }
        request.setCharacterEncoding("GBK");
           HttpSession session=request.getSession();
           SessionInfo sessionInfo = new SessionInfo(
            ((Integer)session.getAttribute("login_company_id")).intValue(), 
            ((Integer)session.getAttribute("login_operator_id")).intValue(), 
            (java.util.HashMap)session.getAttribute("powermap"),
            request.getParameterMap());
            
        int doc_id=0;
          String card_id = "" ;
          
        DBLink dblink =new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
            String Is_Organization = "";
            stmt=dblink.createStatement();rs=stmt.executeQuery("Select card_id,Is_Organization From mbr_members Where id = "+doc_id);
            while(rs.next()){
            card_id = rs.getString("card_id");
            Is_Organization = rs.getString("Is_Organization");
            }
           
            //团体会员
            if ("1".equals(Is_Organization))
            {
              response.sendRedirect("../app/orgmemberdetail?doc_type="+DocType.MEMBER_ORGANIZATION+"&doc_id="+doc_id);
            }     
        }catch(Exception e)
        {
            e.printStackTrace();
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
        DocType doc=new DocType(DocType.MEMBER);
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject("俱乐部会员");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.MEMBER,doc_id);
        hv.addContent("<BR>");
         hv.addSubject("会员状态");
        hv.addDetailViewEx(DocType.MEMBER_STATUS,doc_id);
        hv.addContent("<BR>");
        hv.addSubject("会员联系地址");
        hv.addConfigListView(DocType.MEMBER_ADDRESS,"member_id="+doc_id);
        hv.addContent("<BR>");
        hv.addButton("事件列表","../app/viewlist?doc_type=2000&member_id_key="+card_id);
        hv.addButton("付款","../app/viewnew?doc_type=2020&member_id_key="+card_id);
        hv.addButton("挂失","../app/viewnew?doc_type=2040&member_id_key="+card_id);
        hv.addButton("投诉","../app/viewnew?doc_type=2050&member_id_key="+card_id);
        hv.addButton("询问","../app/viewnew?doc_type=2051&member_id_key="+card_id);
        hv.addButton("升级","../app/viewnew?doc_type=2030&member_id_key="+card_id);
        hv.addButton("购买","../member_order_add.jsp?doc_type=4000&card_id_key="+card_id);
        hv.addButton("更改","../app/viewupdate?doc_type=1000&doc_id="+doc_id);
        hv.addButton("推荐信息","../app/viewlist?doc_type=1050&cardid="+card_id);
        hv.addButton("被推荐信息","../app/viewlist?doc_type=1050&recommended_cardid="+card_id);
        hv.addButton("返回", "history.go(-1)");
       
        hv.addButtons();
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close(); 
      
    }
}