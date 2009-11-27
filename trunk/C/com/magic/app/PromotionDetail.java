package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * ������ҳ��ϸ��Ϣ����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */

public class PromotionDetail extends BaseServlet 
{
        
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("GBK");
        int doc_id=0;
        int doc_type=0;
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
        int price_type_id=0;
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            if(!"".equals(StringUtil.cEmpty(request.getParameter("doc_id")))){
              doc_id=Integer.parseInt(request.getParameter("doc_id"));
            }
            if(!"".equals(StringUtil.cEmpty(request.getParameter("parent_doc_id")))){
              doc_id = Integer.parseInt(request.getParameter("parent_doc_id"));
            }
			doc_type=Integer.parseInt(request.getParameter("doc_type")); 
			String sql = "Select status,price_type_id From prd_pricelists Where id = " + doc_id;
			
			stmt=dblink.createStatement();rs=stmt.executeQuery(sql);
			rs.next();
			status = rs.getInt("status");
            price_type_id=rs.getInt("price_type_id");
        }catch(Exception e)
        {
            e.printStackTrace();
            message("δ���",e.getMessage(),request,response);
            return;
        }finally
        {
            try
            {
                if(rs!=null) rs.close();
                if(stmt!=null) {
                  stmt.close();
                  stmt = null;
                }
                dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        switch(price_type_id)
            {
              case 1:
              //  response.sendRedirect("/app/viewdetail?doc_type="+DocType.MEMBER_PROMOTION+"&doc_id="+doc_id);
               break;
              case 3:
               response.sendRedirect("../app/viewdetail?doc_type="+DocType.CATALOG+"&doc_id="+doc_id);
               return;
              case 4:
                break;                 
              default:
                response.sendRedirect("../app/viewdetail?doc_type="+DocType.PRICELIST+"&doc_id="+doc_id);
                return;
            }
					
		
       
        
        DocType doc=new DocType(DocType.MEMBER);
        
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);

        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);

        hv.setSubject("��ҳ");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.MEMBER_PROMOTION,doc_id);
        hv.addContent("<BR>");
				if(status==0) {
          hv.addButton("����","../app/pricelistsubmit?act=release&parent_doc_type=1510&card_id="+doc_id);
          hv.addButton("ɾ��","../app/pricelistsubmit?act=delete&parent_doc_type=1510&card_id="+doc_id);
				}
				if(status==0 || status==100){
          hv.addButton("��ֹ","../app/pricelistsubmit?act=pause&parent_doc_type=1510&card_id="+doc_id);
				}
				hv.addButton("������","../app/viewnew?doc_type=1671&parent_doc_id="+doc_id);

				  hv.addButton("��������","../crmjsp/data_upload.jsp?doc_type=1510&t_code=2230&parent_doc_id="+doc_id);
	
        hv.addButton("�޸�", "../app/viewupdate?doc_type="+doc_type + "&doc_id="+doc_id);
        hv.addButton("������ϸ","#catalog");
				//hv.addButton("��Ʒ��Ʒ","#gift");
				//hv.addButton("���ͻ���","#delivery");
        hv.addButton("����", "history.go(-1)");
        hv.addButtons();

        hv.addSubject("���������鵥����");
        String s="<table width=\"750.0\" border=0 cellspacing=1 cellpadding=5  align=center >\n";
        s=s+"<form name=\"fm_copy\" method=\"post\" action=\"../app/pricelistsubmit\">\n";
				s=s+"<input type=\"hidden\" name=\"act\" value=\"copy\">\n";
        s=s+"<input type=\"hidden\" name=\"parent_doc_id\" value=\""+doc_id+"\">\n";
				s=s+"<input type=\"hidden\" name=\"parent_doc_type\" value=\""+doc_type+"\">\n";
        s=s+"<tr><td width=\"20%\" align=\"right\" ><font color=red>*</font>&nbsp;ѡ���Ŀ��</td>\n";
        s=s+"<td align=\"left\" width=\"40%\" nowarp><input type=\"hidden\" id=\"pricelist_id\" name=\"pricelist_id\" value=\"\"> <input id=\"pricelist_name\" name=\"pricelist_name\" value=\"\" readonly onclick=\" select_item('promotion_id',fm_copy.pricelist_id,fm_copy.pricelist_name,pricelist_id_display);\"> \n";
        s=s+"<a href=\"javascript:select_item('promotion_id',fm_copy.pricelist_id,fm_copy.pricelist_name,pricelist_id_display);\"><img src=\"../crmjsp/images/icon_lookup.gif\" border=0 align=\"top\"><a>\n";
				s=s+"<label id='pricelist_id_display' name='pricelist_id_display' style='display:none'></label>";
        s=s+"</td><td width=\"40%\"  align=\"right\"><input type=submit class=\"button2\" value=\"����\"></td></tr></form></table>\n";
        hv.addContent(s);


       // hv.addSubject("���Ӽ�Ŀ����");
        //hv.addNewView(DocType.PRICELIST_LINE,doc_id);
        
        s="<a name='catalog'>\n";
				hv.addContent(s);
				hv.addSubject("����Ϣ");
        //�޸ĳɷ�ҳ��ʾ
        try{
          int row_count = 200;
          int sum_num = 0;
          String sql = "";
          dblink = new DBLink();
          if(stmt==null){
            stmt=dblink.createStatement();
          }
          rs = stmt.executeQuery("select value from s_config_keys where key='LIST_MAX_ROW_COUNT'");
          if(rs.next()){
            row_count=Integer.parseInt(rs.getString("value"));
          }
          rs = stmt.executeQuery("select count(*) count from vw_prd_pricelist_lines where pricelist_id="+doc_id);
          if(rs.next()){
            sum_num = rs.getInt("count");
          }
          int current_page = 1;
          int sum_page = 1;
          sum_page = sum_num / row_count;
          if(sum_num%row_count>0){
            sum_page = sum_page + 1;
          }
          String method="first";
          if(request.getParameter("current_page")!=null){
            current_page = Integer.parseInt(request.getParameter("current_page"));
          }
          if(request.getParameter("sum_page")!=null){
            sum_page = Integer.parseInt(request.getParameter("sum_page"));
          }
          method = StringUtil.cEmpty(request.getParameter("method"));   
          if(method.equals("first")){
            current_page = 1;
          } else if(method.equals("next")){
            current_page = current_page + 1;
          } else if(method.equals("previous")){
            current_page = current_page - 1;
          }else if(method.equals("last")){
            current_page = sum_page;
          }
          if(current_page>sum_page){
            current_page = sum_page;
          }          
          if(current_page<=0){
            current_page = 1;
          }          
          sql = "select * from (select row_.*,rownum rownum_ from(select * from vw_prd_pricelist_lines where pricelist_id="+doc_id +" order by item_id) row_ where rownum<="+(current_page) * row_count+") where rownum_>"+(current_page-1)*row_count;
          rs = stmt.executeQuery(sql);
          StringBuffer ss = new StringBuffer("<table width=\"750.0\" border=0 cellspacing=1 cellpadding=5 align=center>\n");
          String fm = "fm_config_"+DocType.PRICELIST_PROMOTION_LINE;
          ss.append("<form name=\"").append(fm).append("\" method=\"post\">\n");
          ss.append("<input type=\"hidden\" name=\"doc_type\" value=\"").append(DocType.PRICELIST_PROMOTION_LINE).append("\">\n");
          ss.append("<input type=\"hidden\" name=\"parent_doc_id\" value=\"").append(doc_id).append("\">\n");
          ss.append("<input type=\"hidden\" name=\"current_page\" value=\"").append(current_page).append("\">\n");
          ss.append("<input type=\"hidden\" name=\"sum_page\" value=\"").append(sum_page).append("\">\n");
          ss.append("<input type=\"hidden\" name=\"method\" value=\"\">\n");          
          ss.append("<tr>\n");
          ss.append("<th width=\"3%\" class=OraTableColumnHeader noWrap align=middle></th>\n");
          ss.append("<th width=\"25%\" class=OraTableColumnHeader noWrap align=middle>��Ʒ����</th>\n");
          ss.append("<th width=\"25%\" class=OraTableColumnHeader noWrap align=middle>��Ʒ����</th>\n");
          ss.append("<th width=\"25%\" class=OraTableColumnHeader noWrap align=middle>���۷�ʽ</th>\n");
          ss.append("<th width=\"25%\" class=OraTableColumnHeader noWrap align=middle>������</th>\n");
          ss.append("</tr>\n");
          while(rs.next()){
            ss.append("<tr>\n");
            ss.append("<td class=OraTableCellText noWrap align=middle>\n");
            ss.append("<input type=\"radio\" name=\"doc_id\" value=\"").append(rs.getInt("id")).append("\">\n");
            ss.append("</td>\n");
            ss.append("<td class=OraTableCellText noWrap align=middle>\n");
            ss.append("<a href=\"../app/viewdetail?doc_type=1500&doc_id=").append(rs.getInt("item_id")).append("\">");
            ss.append(rs.getString("item_code"));
            ss.append("</a>\n");
            ss.append("</td>\n");
            ss.append("<td class=OraTableCellText noWrap align=middle>\n");
            ss.append(rs.getString("item_name"));
            ss.append("</td>\n");
            ss.append("<td class=OraTableCellText noWrap align=middle>\n");
            ss.append(rs.getString("sell_type_name"));
            ss.append("</td>\n");     
            ss.append("<td class=OraTableCellText noWrap align=middle>\n");
            if(rs.getString("common_price")!=null){
              ss.append(rs.getFloat("common_price"));
            }
            ss.append("</td>\n");            
            ss.append("</tr>\n");
          }
          ss.append("</form>\n");
          ss.append("</table>");
          ss.append("<table width=\"750.0\" border=0 cellspacing=1 cellpadding=5 align=center>\n");
          ss.append("<tr>\n");
          ss.append("<td width=\"60%\" align=\"left\">\n");
          ss.append("һ����<u><b>").append(sum_num).append("</b></u>����¼�������ѯ����  ��ǰ��ʾ��<u><b>").append((current_page-1) * row_count+1).append("</u></b>�� �� ��<u><b>").append((current_page)*row_count>sum_num?sum_num:(current_page)*row_count).append("</u></b>�� ��¼");          
          ss.append("</td>\n");
          ss.append("<td width=\"40%\" align=\"right\">\n");
          if(sum_page>1){
            if(current_page==1){
              ss.append("��ҳ").append("&nbsp;&nbsp;|&nbsp;&nbsp;");
              ss.append("��һҳ").append("&nbsp;&nbsp;|&nbsp;&nbsp;");               
            } else {
              ss.append("<a href=\"javascript:document.").append(fm).append(".method.value='first';document.").append(fm).append(".submit();\">").append("��ҳ").append("</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
              ss.append("<a href=\"javascript:document.").append(fm).append(".method.value='previous';document.").append(fm).append(".submit();\">").append("��һҳ").append("</a>&nbsp;&nbsp;|&nbsp;&nbsp;");          
            }
            if(current_page==sum_page){
              ss.append("��һҳ").append("&nbsp;&nbsp;|&nbsp;&nbsp;");
              ss.append("ĩҳ");                
            } else {
              ss.append("<a href=\"javascript:document.").append(fm).append(".method.value='next';document.").append(fm).append(".submit();\">").append("��һҳ").append("</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
              ss.append("<a href=\"javascript:document.").append(fm).append(".method.value='last';document.").append(fm).append(".submit();\">").append("ĩҳ").append("</a>");          
            }
          }
          ss.append("</td>\n");
          ss.append("</tr>\n");
          ss.append("<tr class=\"OraBGAccentDark\">\n");
          ss.append("<td colspan=\"2\" align=\"right\">\n");
          ss.append("<input type=\"button\" class=\"button3\" value=\"���Ӽ�¼\" onClick=\"").append(fm).append(".action='../app/viewnew';").append(fm).append(".submit();\">\n");
          ss.append("<input type=\"button\" class=\"button3\" value=\"�鿴��ϸ\" onClick=\"").append(fm).append(".action='../app/viewdetail';").append(fm).append(".submit();\">\n");
          ss.append("<input type=\"button\" class=\"button3\" value=\"���ļ�¼\" onClick=\"").append(fm).append(".action='../app/viewupdate';").append(fm).append(".submit();\">\n");
          ss.append("<input type=\"button\" class=\"button3\" value=\"ɾ����¼\" onClick=\"").append(fm).append(".action='../app/ctrdelete';").append(fm).append(".submit();\">\n");
          ss.append("</td>\n");
          ss.append("</tr>\n");
          ss.append("</table>\n");
          hv.addContent(ss.toString());
        }catch(Exception e){
          e.printStackTrace();
        }finally{
          try{
            if(stmt!=null){
              stmt.close();
              stmt = null;
            }
            dblink.close();
          }catch(Exception e){}
        }
        //hv.addConfigListView(DocType.PRICELIST_PROMOTION_LINE,"pricelist_id="+doc_id);
        hv.addContent("<BR>");
        
        /* --------delete by zhux 20050801
        s="<a name='gift'>\n";
				hv.addContent(s);
				hv.addSubject("��Ʒ��Ʒ");
        hv.addConfigListView(DocType.PROMOTION_PRICELIST_GIFT,"pricelist_id="+doc_id);
        hv.addContent("<BR>");
        s="<a name='delivery'>\n";
				hv.addContent(s);
				hv.addSubject("���ͻ��ѹ���");
        hv.addConfigListView(DocType.FREE_DELIVERY,"pricelist_id="+doc_id);
        hv.addContent("<BR>");
        */
        
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();  
    }
}