package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * ��������������ϸ��Ϣ����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class OrderReturnDetail extends BaseServlet 
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
            message("δ���",e.getMessage(),request, response);
            return;
        }
        
               
         
         
        DocType doc=new DocType(DocType.MEMBER_RETURN_ORDER);
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject("��Ա����");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.MEMBER_RETURN_ORDER,doc_id);
        hv.addContent("<BR>");
       
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;  
        try
        {
            stmt=dblink.createStatement();
            rs= stmt.executeQuery("select id from ord_delivery_lines where order_id ="+doc_id);
            rs.next();
            int delivery_id=rs.getInt("id");
            rs.close();
            hv.addDetailViewEx(DocType.ORDER_DELIVERY,delivery_id);

            hv.addContent("<BR>");
			String s = "<table width=750.0 border=0 cellspacing=1 cellpadding=5 align=center>\n";
            s = s + "<tr>\n";
            s = s + "<th width=\"16%\" class=OraTableColumnHeader noWrap align=middle>���</th>\n";
            s = s + "<th width=\"16%\" class=OraTableColumnHeader noWrap align=middle>��Ʒ����</th>\n";
            s = s + "<th width=\"16%\" class=OraTableColumnHeader noWrap align=middle>��Ʒ����</th>\n";
            s = s + "<th width=\"8%\" class=OraTableColumnHeader noWrap align=middle>��Ʒ�۸�</th>\n";
            s = s + "<th width=\"8%\" class=OraTableColumnHeader noWrap align=middle>��Ʒ����</th>\n";
            s = s + "<th width=\"8%\" class=OraTableColumnHeader noWrap align=middle>ʵ������</th>\n";
            s = s + "<th width=\"16%\" class=OraTableColumnHeader noWrap align=middle>���</th>\n";
            s = s + "<th width=\"16%\" class=OraTableColumnHeader noWrap align=middle>״̬</th>\n";
            s = s + "<th width=\"8%\" class=OraTableColumnHeader noWrap align=middle></th>\n";
            s = s + "</tr>\n";
			hv.addContent(s);
				
			int status = 0;
            rs= stmt.executeQuery("select * From vw_order_lines where order_id="+doc_id);
			while(rs.next()){
					status = rs.getInt("STATUS");
					s = "<tr>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>" + rs.getInt("id") + "</td>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>" + rs.getString("item_code") + "</td>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>" + rs.getString("item_name") + "</td>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>" + rs.getFloat("price") + "</td>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>" + rs.getInt("quantity") + "</td>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>" + rs.getInt("shipped_quantity") + "</td>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>" + rs.getFloat("amount") + "</td>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>" + rs.getString("status_name") + "</td>\n";
					s = s + "<td class=OraTableCellText noWrap align=middle>"; 
					if(status!=-10 && status!=-8){
					  s = s + "<input type=\"button\" class=\"button2\" value=\"ȡ��\" onClick=\"javascript:document.location.href='../app/ordersubmit?action=cancels&doc_type=2090&parent_doc_id="+doc_id+"&doc_id="+rs.getInt("id")+"&item_id="+rs.getInt("item_id")+"';\">" + "</td>\n";
					}
					s = s + "</tr>\n" ;
					hv.addContent(s);
			}

            s = "</table>\n";
            hv.addContent(s);

        
            //hv.addListView(DocType.ORDER_LINE,"order_id="+doc_id);
            hv.addContent("<BR>");
            //hv.addButton("ȷ�϶���","/app/order?action=confirm&doc_type=2050&card_id="+doc_id);
            //hv.addButton("��������","/app/order?action=asn&doc_type=2030&card_id="+doc_id);
            //hv.addButton("ȡ������","/app/order?action=cancel&doc_type=2090&card_id="+doc_id);
            //hv.addButton("���Ķ���","/app/order?action=update&doc_id="+doc_id);
            hv.addButton("����", "history.go(-1)");
           
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