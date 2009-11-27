package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * ������Ա��ϸ��Ϣ����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
           
            //�����Ա
            if ("1".equals(Is_Organization))
            {
              response.sendRedirect("../app/orgmemberdetail?doc_type="+DocType.MEMBER_ORGANIZATION+"&doc_id="+doc_id);
            }     
        }catch(Exception e)
        {
            e.printStackTrace();
            message("δ���",e.getMessage(),request, response);
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
        hv.setSubject("���ֲ���Ա");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.MEMBER,doc_id);
        hv.addContent("<BR>");
         hv.addSubject("��Ա״̬");
        hv.addDetailViewEx(DocType.MEMBER_STATUS,doc_id);
        hv.addContent("<BR>");
        hv.addSubject("��Ա��ϵ��ַ");
        hv.addConfigListView(DocType.MEMBER_ADDRESS,"member_id="+doc_id);
        hv.addContent("<BR>");
        hv.addButton("�¼��б�","../app/viewlist?doc_type=2000&member_id_key="+card_id);
        hv.addButton("����","../app/viewnew?doc_type=2020&member_id_key="+card_id);
        hv.addButton("��ʧ","../app/viewnew?doc_type=2040&member_id_key="+card_id);
        hv.addButton("Ͷ��","../app/viewnew?doc_type=2050&member_id_key="+card_id);
        hv.addButton("ѯ��","../app/viewnew?doc_type=2051&member_id_key="+card_id);
        hv.addButton("����","../app/viewnew?doc_type=2030&member_id_key="+card_id);
        hv.addButton("����","../member_order_add.jsp?doc_type=4000&card_id_key="+card_id);
        hv.addButton("����","../app/viewupdate?doc_type=1000&doc_id="+doc_id);
        hv.addButton("�Ƽ���Ϣ","../app/viewlist?doc_type=1050&cardid="+card_id);
        hv.addButton("���Ƽ���Ϣ","../app/viewlist?doc_type=1050&recommended_cardid="+card_id);
        hv.addButton("����", "history.go(-1)");
       
        hv.addButtons();
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close(); 
      
    }
}