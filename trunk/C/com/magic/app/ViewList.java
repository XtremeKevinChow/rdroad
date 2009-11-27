package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * �б��������
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
            System.out.println("ERROR:û���ҵ���󷵻ؼ�¼����������Ϣ������ȱʡֵ:200\n"+e);
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
            message("δ���",e.getMessage(),request, response);
            return;
        }


				switch(doc_type){
          
					//�����
					case DocType.ORDERSHIPPING:
						response.sendRedirect("../crmjsp/order_shipping_list.jsp?doc_type="+doc_type);
					  break;

					//��Ա�
					case DocType.MEMBER_ACTIVITY_SET:
						response.sendRedirect("../crmjsp/member_activity_list.jsp?doc_type="+doc_type);
					  break;

					//δ���Ͷ��
			case DocType.MEMBER_INQUIRY_SET:
				response.sendRedirect("../crmjsp/member_inquiry_list.jsp?doc_type="+doc_type);
				break;

					//ҵ���������
			case DocType.CONFIG_KEYS:
				response.sendRedirect("../crmjsp/config_keys.jsp?doc_type="+doc_type);
				break;

					//��Ӧ��
			case DocType.PROVIDERS:
				response.sendRedirect("../crmjsp/providers_list.jsp?doc_type="+doc_type);
				break;
            
          //�ż�ģ���ӡ
			case DocType.MEMBER_LETTERS:
				
				response.sendRedirect("../crmjsp/document_templates_event_detail.jsp?doc_type="+doc_type);
				break;  
          //�ɹ������
          case DocType.STO_PUR_MST:
            response.sendRedirect("../crmjsp/sto_pur_mst_list.jsp?doc_type="+doc_type);
					  break; 
          case DocType.MBR_REMITTANCE:
            response.sendRedirect("../crmjsp/member_deposits_upload_query.jsp?doc_type="+doc_type);
            break;
          //��Ա������
          case DocType.MBR_CARD_UPLOAD:
            response.sendRedirect("../crmjsp/member_card_upload.jsp?doc_type="+doc_type);
            break;
          //��Ա��ȯ
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
              message("δ���",e.getMessage(),request, response);
              return;
            }
            int row=0;
            if(searchStr.trim().equals("1=1") && StringUtil.cEmpty(request.getParameter("first")).equals("1"))
              hv.addContent("<br><table><tr><td>�������ѯ�������в�ѯ.</td></tr></table>");
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
			        if(doc_type==DocType.MEMBER){       //����ǻ�Ա�򰴷�ҳ��ʾ������ԭ������ʾ
				        row=hv.addListView(doc_type,searchStr);
                hv.addContent("<table width=\"50%\" valign<tr><td width=\"100%\" align=\"left\" >��ѯ��ʾ��¼������<u><b>"+row+"</b></u>��.</td></tr>\n");
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