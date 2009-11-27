package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import com.magic.utils.*;
/**
 * 详细信息的浏览界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class ViewDetail extends BaseServlet
{
   

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("GBK");
        int doc_type =0;
        
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
            doc_type=Integer.parseInt(request.getParameter("doc_type"));
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
        }catch(Exception e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
        }
        
        DocType doc=new DocType(doc_type);
        


        switch(doc_type)
        {
          case DocType.MEMBER:
             response.sendRedirect("../app/memberdetail?doc_id="+doc_id);
              break;
              
          case DocType.MEMBER_ACTIVITY:
             response.sendRedirect("../app/activitydetail?doc_type="+doc_type+"&doc_id="+doc_id);
             break;
            
          case DocType.CATALOG:
             response.sendRedirect("../app/catalogdetail?doc_type="+doc_type+"&doc_id="+doc_id);
              break;
          
          case DocType.MEMBER_GROUP:
             response.sendRedirect("../app/membergroupdetail?doc_type="+doc_type+"&doc_id="+doc_id);
              break;
          
           case DocType.ORDER:
              response.sendRedirect("../app/orderdetail?doc_type="+doc_type+"&doc_id="+doc_id);
              break;
              
           case DocType.PRODUCT:
              response.sendRedirect("../app/productdetail?doc_type="+doc_type+"&doc_id="+doc_id);
              break;
              
          case DocType.PRICELIST:
            response.sendRedirect("../app/pricelistdetail?doc_type="+doc_type+"&doc_id="+doc_id);
              break;

          case DocType.ORG_PERSON:
            response.sendRedirect("../app/persondetail?doc_type="+doc_type+"&doc_id="+doc_id);
              break;

          case DocType.ORG_ROLES:
               response.sendRedirect("../crmjsp/role_init.jsp?doc_type="+doc_type+"&doc_id="+doc_id);
              break;
              
        case DocType.MEMBER_PROMOTION:
               response.sendRedirect("../app/promotiondetail?doc_type="+doc_type+"&doc_id="+doc_id);
              break;
              
		case DocType.ORDER_SHIPPING_NOTICE:
               response.sendRedirect("../crmjsp/shippingnotices_edit.jsp?doc_id="+doc_id+"&doc_type="+doc_type);
               break;

		case  DocType.ORDERSHIPPING:
				       response.sendRedirect("./app/ordershippingdetail?doc_type=" + doc_type + "&doc_id=" + doc_id);
               break;
		case  DocType.ORG_ORDER:
				       response.sendRedirect("../app/orgorderdetail?doc_type=" + doc_type + "&doc_id=" + doc_id);
               break;

		case  DocType.MEMBER_ORGANIZATION:
				       response.sendRedirect("../app/orgmemberdetail?doc_type=" + doc_type + "&doc_id=" + doc_id);
               break;
               
		case DocType.MEMBER_INQUIRY:
               response.sendRedirect("../crmjsp/member_inquiry_view.jsp?doc_id="+doc_id+"&doc_type="+doc_type);
               break;  
               
          //信件模板内容
          case DocType.DOCUMENT_TEMPLATE:
               response.sendRedirect("../crmjsp/document_template.jsp?doc_type="+doc_type + "&act=view&doc_id="+doc_id);
               break;   
         //信件打印内容
         case DocType.MEMBER_LETTERS:
               response.sendRedirect("../app/letterdetail?doc_type="+doc_type + "&doc_id="+doc_id);
               break;  
       // case DocType
        /*  case DocType.DOCUMENT_TEMPLATE:
               this.forward("/app/viewUpdate?doc_type="+doc_type+"&doc_id="+doc_id);
              break;
         */
          
          default:
                HTMLView hv=new HTMLView();
                hv.setSessionInfo(sessionInfo);
                hv.setWidth(750);
                
                hv.setSubject(doc.getDocName());
                
                hv.addDetailView(doc_type,doc_id);
                
                hv.addButtons();
                response.setContentType(CONTENT_TYPE);
                PrintWriter out = response.getWriter(); 
               
                out.println(hv.getHTML());
                out.close();  
                break;
        }
    }
}