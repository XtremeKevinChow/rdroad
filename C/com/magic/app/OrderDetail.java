package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * ����������ϸ��Ϣ����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class OrderDetail extends BaseServlet 
{
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      
        
        request.setCharacterEncoding("GBK");
      int doc_id=0;
      int doc_type = 0;
    
      int headerStatus = 0;
      //������Դ
      int pr_type = 0;
      int order_category=0;
    
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
            doc_type = Integer.parseInt(request.getParameter("doc_type"));            
            stmt=dblink.createStatement();
            
            rs = stmt.executeQuery("select A.status,A.Pr_Type,order_category from ord_headers A where A.id="+doc_id);
            if(rs.next()){
                headerStatus = rs.getInt("status");
                pr_type = rs.getInt("pr_type");
                order_category=rs.getInt("order_category");
                if(order_category>0) order_category=1;
            }else
            {
                 message("δ���","�˶��������ڣ����ܴ˶����վ��������޸ģ������²�ѯ",request, response);
                 return;
            }
            //�����Ա����
            if (pr_type == 5)
            {
                response.sendRedirect("../app/viewdetail?doc_type="+DocType.ORG_ORDER+"&doc_id="+doc_id);
                return;
            }
        
        rs.close();
        
        DocType doc=new DocType(DocType.ORDER);
        
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject("��Ա����");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.ORDER,doc_id);
        hv.addContent("<BR>");

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
                
        boolean cancelAll = true;
        if(headerStatus==0 || headerStatus==15)                                                                                                                                                                                                                                                            
            rs= stmt.executeQuery("select a.item_id,a.id,a.item_code,a.item_name,a.mbr_award_id,a.price,a.quantity,a.shipped_quantity,amount, decode(a.status,-10,-10,TRANSACTION.f_check_item_inventory(A.item_id,A.quantity,A.sell_type,"+order_category+")) status ,b.name status_name From vw_order_lines a , s_order_line_status b where b.id= decode(a.status,-10,-10,TRANSACTION.f_check_item_inventory(a.item_id,a.quantity,a.sell_type,"+order_category+"))  and a.order_id="+doc_id);
		    else
            rs= stmt.executeQuery("select id,item_id,item_code,item_name,price,mbr_award_id,quantity,shipped_quantity,amount, status ,status_name From vw_order_lines where  order_id="+doc_id);
        int status = 0;
		    while(rs.next()){
            status = rs.getInt("STATUS");
            //System.out.println("status="+status);
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
                
            if(status > -5 && status!=50 && status!=100 &&  pr_type==3 ){
              if(!((StringUtil.cEmpty(rs.getString("mbr_award_id"))).equals(""))){
                if(rs.getInt("mbr_award_id")>0)
                  s = s + "<input type=\"button\" class=\"button2\" value=\"ȡ��\" onClick=\"javascript:document.location.href='../app/ordersubmit?action=cancels&doc_type=2090&parent_doc_id="+doc_id+"&doc_id="+rs.getInt("id")+"&item_id="+rs.getInt("item_id")+"';\">" + "</td>\n";                         
              } 
            } 
            if((status==100 || status==50 || headerStatus<0) && headerStatus != -6){
              cancelAll = false;
            }
            s = s + "</tr>\n" ;
            hv.addContent(s);
          }
          s = "</table>\n";
          hv.addContent(s);
          hv.addContent("<BR>");
            if(cancelAll){
              if ( headerStatus == -6 ){
                hv.addButton("ȡ���޸�", "../app/ordersubmit?action=cancelupdate&doc_id="+doc_id + "&doc_type="+doc_type);
              }
              if ( headerStatus != -6){
                hv.addButton("ȡ��", "../app/ordersubmit?action=ordercancel&doc_id="+doc_id + "&doc_type="+doc_type);
              }
              if (pr_type != 3 && headerStatus != -6 ){
                hv.addButton("����", "../app/ordersubmit?action=order&doc_id="+doc_id + "&doc_type="+doc_type);
              }
            }      
            hv.addButton("����", "../app/viewlist?doc_type=4000&first=1&t_code=3120");
            hv.addButtons();
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter(); 
            out.println(hv.getHTML());
            out.close();  
		}catch(Exception e){e.printStackTrace();}
        finally
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