package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import com.magic.utils.*;
/**
 * 修改界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class ViewUpdate extends BaseServlet 
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
            
            
            int doc_type =0;
            DocType doc;
            int doc_id=0;
            int parent_doc_id=0;
            try{
                doc_type=Integer.parseInt(request.getParameter("doc_type"));
                doc_id=Integer.parseInt(request.getParameter("doc_id"));
                if(request.getParameter("parent_doc_id")!=null)  parent_doc_id=Integer.parseInt(request.getParameter("parent_doc_id"));
                doc=new DocType(doc_type);
            }catch(Exception e)
            {
                System.out.println(e);
               message("未完成",e.getMessage(),request, response);
                return;
          }


        switch(doc_type)
        {
//          case DocType.CATALOG:
//              this.forward("/app/catalogdetail?doc_type="+doc_type+"&doc_id="+doc_id);
//              break;
//            case DocType.PRICELIST:
//              this.forward("/app/pricelistdetail?doc_type="+doc_type+"&doc_id="+doc_id);
//              break;
          case DocType.MEMBER:
               response.sendRedirect("../crmjsp/member_update.jsp?doc_type="+doc_type + "&doc_id="+doc_id);
               break; 
          case DocType.ORG_ROLES:
               response.sendRedirect("../app/roledetail?doc_type="+doc_type+"&doc_id="+doc_id);
               break;
          //信件模板内容
          case DocType.DOCUMENT_TEMPLATE:
               response.sendRedirect("../crmjsp/document_template.jsp?doc_type="+doc_type + "&act=updateSave&doc_id="+doc_id);
               break; 
          //修改条形码
          case DocType.BARCODE:
               response.sendRedirect("../crmjsp/product_barcode_edit.jsp?doc_type="+doc_type+"&doc_id="+doc_id);
               break;
          default:
              HTMLView hv=new HTMLView();
              hv.setSessionInfo(sessionInfo);
              hv.setWidth(750);
              
              hv.setSubject("修改&nbsp;"+doc.getDocName());
              hv.addUpdateView(doc_type,doc_id,parent_doc_id);
              
              response.setContentType(CONTENT_TYPE);
              PrintWriter out = response.getWriter(); 
             
              
              out.println(hv.getHTML());
              out.close();
        }
    }
}