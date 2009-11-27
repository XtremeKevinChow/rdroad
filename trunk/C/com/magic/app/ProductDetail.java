package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 产生产品详细信息界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */

public class ProductDetail extends BaseServlet 
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("GBK");
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
            
            
        int doc_id=0;
		int is_set = 0 ;
		String item_code="";
                
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
			String sql = "Select IS_SET ,item_code From VW_ITEMS Where id = " + doc_id;
			stmt=dblink.createStatement();rs=stmt.executeQuery(sql);
			while(rs.next()){
				is_set = rs.getInt("IS_SET");
				item_code=rs.getString("item_code");
			}
        }catch(Exception e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
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
        
        DocType doc=new DocType(DocType.PRODUCT);
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject(doc.getDocName());
        hv.addContent("<BR>");
        if(is_set==1){
          hv.addDetailViewEx(DocType.SETITEM,doc_id);
        } else {

            hv.addDetailViewEx(DocType.PRODUCT,doc_id);
          
        }
       // hv.addContent("<BR>");
        //hv.addDetailViewEx(DocType.PRODUCT_STATUS,doc_id);
        hv.addContent("<BR>");
        //hv.addButton("删除","/app/ctrdelete?doc_type=1500&doc_id="+doc_id);
          hv.addButton("产品价目","../app/viewlist?doc_type=1610&item_id_key="+item_code);
          hv.addButton("价目变更","../app/viewlist?doc_type=1620&item_id_key="+item_code);
          if(is_set==1){
            hv.addButton("更改","../app/viewupdate?doc_type=5010&doc_id="+doc_id);
          } else {
            hv.addButton("更改","../app/viewupdate?doc_type=1500&doc_id="+doc_id);
          }
          hv.addButton("更改条形码","../app/viewupdate?doc_type=1501&doc_id="+doc_id);
          hv.addButton("返回", "history.go(-1)");
       
        hv.addButtons();
        if(is_set==1){
				  hv.addSubject("套件产品明细");
          hv.addConfigListView(DocType.SET_PRODUCTS,"SET_ITEM_ID="+doc_id);
          hv.addContent("<BR>");
				}
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();  
    }
}