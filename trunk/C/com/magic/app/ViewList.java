package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * 列表浏览界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class ViewList extends BaseServlet {
  
   // private static int row_count=0;
    
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
     
       /* if(row_count==0)
        {
          try{
            DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
            stmt=dblink.createStatement();rs= stmt.executeQuery("select value from s_config_keys where key='LIST_MAX_ROW_COUNT'");
            rs.next();
            row_count=Integer.parseInt(rs.getString("value"));
          }catch(Exception e)
           {
            System.out.println("ERROR:没有找到最大返回记录行数配置信息，采用缺省值:200\n"+e);
            row_count=200;
          }
        }*/
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
        String data_source ;
        try{
            doc_type=Integer.parseInt(request.getParameter("doc_type"));
            doc=new DocType(doc_type);
        }catch(Exception e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
        }


				switch(doc_type){
          
					//配货单
					case DocType.ORDERSHIPPING:
						response.sendRedirect("../crmjsp/order_shipping_list.jsp?doc_type="+doc_type);
					  break;

					//会员活动
					case DocType.MEMBER_ACTIVITY_SET:
						response.sendRedirect("../crmjsp/member_activity_list.jsp?doc_type="+doc_type);
					  break;

					//未解决投诉
			case DocType.MEMBER_INQUIRY_SET:
				response.sendRedirect("../crmjsp/member_inquiry_list.jsp?doc_type="+doc_type);
				break;

					//业务参数设置
			case DocType.CONFIG_KEYS:
				response.sendRedirect("../crmjsp/config_keys.jsp?doc_type="+doc_type);
				break;

					//供应商
			case DocType.PROVIDERS:
				response.sendRedirect("../crmjsp/providers_list.jsp?doc_type="+doc_type);
				break;
            
          //信件模板打印
			case DocType.MEMBER_LETTERS:
				
				response.sendRedirect("../crmjsp/document_templates_event_detail.jsp?doc_type="+doc_type);
				break;  
          //采购单审核
          case DocType.STO_PUR_MST:
            response.sendRedirect("../crmjsp/sto_pur_mst_list.jsp?doc_type="+doc_type);
					  break; 
          case DocType.MBR_REMITTANCE:
            response.sendRedirect("../crmjsp/member_deposits_upload_query.jsp?doc_type="+doc_type);
            break;
          //会员卡导入
          case DocType.MBR_CARD_UPLOAD:
            response.sendRedirect("../crmjsp/member_card_upload.jsp?doc_type="+doc_type);
            break;
          //会员礼券
//          case DocType.MBR_GIFT_CERTIFICATE:
//            System.out.println("*********************88");
//            response.sendRedirect("/mbr_gift_certificates_list.jsp?doc_type="+doc_type);
//					  break;           
					default:

					  HTMLView hv=new HTMLView();
            hv.setSessionInfo(sessionInfo);

            hv.setDocType(doc_type);

            hv.setWidth(1);

            hv.addSearchBar(doc_type);

            hv.setSubject(doc.getDocName());
 
            String searchStr="";
            try{
            	

               searchStr=hv.getSearchStr();
					     if(request.getParameter("query_sql")!=null){
						     searchStr = request.getParameter("query_sql");
					     }

            }catch(KException e){
                    e.printStackTrace();
              message("未完成",e.getMessage(),request, response);
              return;
            }
            int row=0;
            if(searchStr.trim().equals("1=1") && StringUtil.cEmpty(request.getParameter("first")).equals("1"))
              hv.addContent("<br><table><tr><td>请输入查询条件进行查询.</td></tr></table>");
            else
            {
              int current_page = 1;
              int sum_page = 1;
              String method="first";
              if(request.getParameter("current_page")!=null){
                current_page = Integer.parseInt(request.getParameter("current_page"));
              }
              if(request.getParameter("sum_page")!=null){
                sum_page = Integer.parseInt(request.getParameter("sum_page"));
              }
              method = StringUtil.cEmpty(request.getParameter("method"));
			        if(doc_type==DocType.MEMBER){       //如果是会员则按分页显示，按照原来的显示
				        row=hv.addListView(doc_type,searchStr);
                hv.addContent("<table width=\"50%\" valign<tr><td width=\"100%\" align=\"left\" >查询显示记录行数：<u><b>"+row+"</b></u>条.</td></tr>\n");
			        } else {
                row=hv.addListView(doc_type,searchStr,method,current_page,sum_page);
			        }           
            }
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter(); 
            out.println(hv.getHTML());
            out.close();
				}
    }
}