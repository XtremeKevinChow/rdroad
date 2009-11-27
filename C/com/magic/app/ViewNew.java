package com.magic.app;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.magic.utils.*;
/**
 * ��������
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class ViewNew extends BaseServlet
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
        int parent_doc_id=0;  

      
        try{
            doc_type=Integer.parseInt(request.getParameter("doc_type"));
            doc=new DocType(doc_type);
            if(request.getParameter("parent_doc_id")!=null){
							parent_doc_id=Integer.parseInt(request.getParameter("parent_doc_id"));
						}
            
        }catch(Exception e)
        {
        	
            System.out.println(e);
            message("δ���",e.getMessage(),request, response);
            
            return;
        }



          switch(doc_type)
        {
          case DocType.MEMBER_RECRUITEMENT:
              response.sendRedirect("../crmjsp/member_new.jsp?doc_type="+doc_type);
              break;
          case DocType.ORDER:
              response.sendRedirect("../crmjsp/member_order_add.jsp?doc_type="+doc_type);
              break;

        //  case DocType.MEMBER:
          //    response.sendRedirect("/app/viewnew?doc_type=2010");
           //   break;
           
          //��Ա��ļ
          case DocType.MEMBER:
              response.sendRedirect("../crmjsp/member_new.jsp?doc_type="+DocType.MEMBER_RECRUITEMENT);
              break;

          case DocType.MEMBER_GROUP:
              response.sendRedirect("../app/memberquery");
              break;
					//��Ӧ������
					case DocType.PROVIDERS:
						   response.sendRedirect("../crmjsp/providers_edit.jsp?doc_type="+doc_type);
					     break;
					//��Ա����
					case DocType.MEMBER_DEPOSITS:
						   response.sendRedirect("../crmjsp/member_deposits.jsp?doc_type="+doc_type + "&member_id_key="+StringUtil.cEmpty(request.getParameter("member_id_key")));
					     break;

					//��װ����
					case DocType.SETITEM:
						   response.sendRedirect("../crmjsp/set_item.jsp?doc_type="+doc_type);
					     break;
					//��Ա����ʧ
					case DocType.MEMBER_CARD:
						   response.sendRedirect("../crmjsp/member_card_loss.jsp?doc_type="+doc_type + "&old_card_id=" + StringUtil.cEmpty(request.getParameter("member_id_key")));
					     break;
					//��Ա�˿�
					case DocType.MEMBER_DRAWBACK:
						   response.sendRedirect("../crmjsp/member_drawback.jsp?doc_type="+doc_type);
					     break;
					//�������嶩��
					case DocType.GROUP_ORDER:
						   response.sendRedirect("../crmjsp/member_group_order_add.jsp?doc_type="+doc_type);
					     break;
					//����
					case 5080:   //Ҫ�޸�
						   response.sendRedirect("../crmjsp/member_deposits_upload.jsp?doc_type="+doc_type);
					     break;
          //�ż�ģ������
          case DocType.DOCUMENT_TEMPLATE:
               response.sendRedirect("../crmjsp/document_template.jsp?doc_type="+doc_type + "&act=addSave");
               break;
          //��Աѯ��
					case DocType.MBR_INQUIRY:
						   response.sendRedirect("../crmjsp/member_inquiry.jsp?doc_type="+doc_type + "&member_id_key="+StringUtil.cEmpty(request.getParameter("member_id_key")));
					     break;
          //����
          //��Ʒ������ϸ��
					case DocType.PDF_SELL_ALL:
						   response.sendRedirect("../crmjsp/pdf_prd_sell_all.jsp?doc_type="+doc_type);
					     break;

          //��Ʒ���ͷ�������
					case DocType.PDF_SELL_TYPE:
						   response.sendRedirect("../crmjsp/pdf_prd_type_sell_analyze.jsp?doc_type="+doc_type);
					     break;
          //���ݶ�����Դ����
					case DocType.PDF_SELL_PR:
						   response.sendRedirect("../crmjsp/pdf_prd_pr_type_analyze.jsp?doc_type="+doc_type);
					     break;
          //���ݲɹ��༭����
					case DocType.PDF_SELL_OWENER:
						   response.sendRedirect("../crmjsp/pdf_prd_owener_analyze.jsp?doc_type="+doc_type);
					     break;
          //���ݲ�Ʒ������
					case DocType.PDF_SELL_CATELOG:
						   response.sendRedirect("../crmjsp/pdf_prd_catelog_analyze.jsp?doc_type="+doc_type);
					     break;
          //���ݲ�Ʒ�ɱ��۷���
					case DocType.PDF_SELL_MONEY:
						   response.sendRedirect("../crmjsp/pdf_prd_money.jsp?doc_type="+doc_type);
					     break;
          //���ݹ�Ӧ�̷���
					case DocType.PDF_SELL_PROVIDER:
						   response.sendRedirect("../crmjsp/pdf_prd_provider_analyze.jsp?doc_type="+doc_type);
					     break;
          //����������������
					case DocType.PDF_SELL_DELIVERY:
						   response.sendRedirect("../crmjsp/pdf_prd_delivery_analyze.jsp?doc_type="+doc_type);
					     break;
          //ÿ�ն���
					case DocType.PDF_ORDER_EVERYDAY:
						   response.sendRedirect("../crmjsp/pdf_ord_every_days.jsp?doc_type="+doc_type);
					     break;
          //ÿ�ղ�Ʒ�۵���
					case DocType.PDF_ORDER_WAITING:
						   response.sendRedirect("../crmjsp/pdf_order_waiting.jsp?doc_type="+doc_type);
					     break;
          //�۵�������
					case DocType.PDF_ORDER_SHORTAGE:
						   response.sendRedirect("../crmjsp/pdf_order_shortage_goods.jsp?doc_type="+doc_type);
					     break;
           //����������
					case DocType.PDF_ORDER_ANALYZE:
						   response.sendRedirect("../crmjsp/pdf_order_analyze.jsp?doc_type="+doc_type);
					     break;
          //�������������
					case DocType.PDF_ORDER_ZONE:
						   response.sendRedirect("../crmjsp/pdf_order_zone.jsp?doc_type="+doc_type);
					     break;
          //�������������
					case DocType.PDF_ORDER_AGE:
						   response.sendRedirect("../crmjsp/pdf_order_age.jsp?doc_type="+doc_type);
					     break;
           //��Ʒ�պ�̨���۱���
					case DocType.PDF_SELL_ANALYZE:
						   response.sendRedirect("../crmjsp/pdf_prd_sell_analyze.jsp?doc_type="+doc_type);
					     break;
          //��Ʒ��ǰ̨���۱���
					case DocType.PDF_SELL_ANALYZE_FOREGROUND:
						   response.sendRedirect("../crmjsp/pdf_prd_sell_analyze_foreground.jsp?doc_type="+doc_type);
                         break;
          //��Ա��������ֲ�
					case DocType.PDF_CUSTOM_LEVEL:
						   response.sendRedirect("../crmjsp/pdf_ord_level.jsp?doc_type="+doc_type);
					     break;
          //��Ա����������
					case DocType.PDF_CUSTOM_MONEY:
						   response.sendRedirect("../crmjsp/pdf_ord_money.jsp?doc_type="+doc_type);
					     break;
          //�����Ա�б�
					case DocType.PDF_CUSTOM_LIST:
						   response.sendRedirect("../crmjsp/pdf_prd_custom_lists.jsp?doc_type="+doc_type);
					     break;
          //��Ա���۵���ֲ�
					case DocType.PDF_CUSTOM_POSTCODE:
						   response.sendRedirect("../crmjsp/pdf_ord_postcode.jsp?doc_type="+doc_type);
					     break;
          //���յ������
					case DocType.PDF_CUSTOM_CITY:
						   response.sendRedirect("../crmjsp/pdf_mbr_city.jsp?doc_type="+doc_type);
					     break;
          //��Ա��������ֲ�
					case DocType.PDF_CUSTOM_AGE:
						   response.sendRedirect("../crmjsp/pdf_ord_age.jsp?doc_type="+doc_type);
					     break;
          //��ļ��������
					case DocType.PDF_CUSTOM_MSC:
						   response.sendRedirect("../crmjsp/pdf_mbr_msc.jsp?doc_type="+doc_type);
					     break;
          //�����������ļ��������
					case DocType.PDF_CUSTOM_MSC_AGE:
						   response.sendRedirect("../crmjsp/pdf_mbr_msc_age.jsp?doc_type="+doc_type);
					     break;
          //member get member ��Ʒ���۷���
					case DocType.PDF_CUSTOM_GET:
						   response.sendRedirect("../crmjsp/pdf_prd_gifts.jsp?doc_type="+doc_type);
					     break;
          //Ŀ¼��Ҫ
					case DocType.PDF_PRICELIST_TOTAL:
						   response.sendRedirect("../crmjsp/pdf_pricelist_total.jsp?doc_type="+doc_type);
					     break;
          //��������top20
					case DocType.PDF_PRICELIST_QUANTITY:
						   response.sendRedirect("../crmjsp/pdf_pricelist_quantity.jsp?doc_type="+doc_type);
					     break;
          //���۽��top20
					case DocType.PDF_PRICELIST_MONEY:
						   response.sendRedirect("../crmjsp/pdf_pricelist_money.jsp?doc_type="+doc_type);
					     break;
          //ҳ������top10
					case DocType.PDF_PRICELIST_PAGE:
						   response.sendRedirect("../crmjsp/pdf_pricelist_page.jsp?doc_type="+doc_type);
					     break;
          //���ݲ�Ʒ����
					case DocType.PDF_PRICELIST_CATEGORY:
						   response.sendRedirect("../crmjsp/pdf_pricelist_category.jsp?doc_type="+doc_type);
					     break;
          //����С���
					case DocType.PDF_PRICELIST_SMALL:
						   response.sendRedirect("../crmjsp/pdf_pricelist_small.jsp?doc_type="+doc_type);
					     break;
          //ҳ�����
					case DocType.PDF_PRICELIST_EDITION:
						   response.sendRedirect("../crmjsp/pdf_pricelist_edition.jsp?doc_type="+doc_type);
					     break;
          //��������
					case DocType.PDF_PRICELIST_COMMON:
						   response.sendRedirect("../crmjsp/pdf_selltype_common.jsp?doc_type="+doc_type);
					     break;
          //��������
					case DocType.PDF_PRICELIST_DISCOUNT:
						   response.sendRedirect("../crmjsp/pdf_selltype_discount.jsp?doc_type="+doc_type);
					     break;
          //��Ʒ��Ʒ
					case DocType.PDF_PRICELIST_PRICE:
						   response.sendRedirect("../crmjsp/pdf_selltype_price.jsp?doc_type="+doc_type);
					     break;
          //��������Ʒ
					case DocType.PDF_PRICELIST_INTRODUCE:
						   response.sendRedirect("../crmjsp/pdf_selltype_introduce.jsp?doc_type="+doc_type);
					     break;
          //Ŀ¼������ʽ
					case DocType.PDF_PRICELIST_PROMOTION:
						   response.sendRedirect("../crmjsp/pdf_pricelist_promotion.jsp?doc_type="+doc_type);
					     break;
          //��ҳ����
					case DocType.PDF_PRICELIST_MSC:
						   response.sendRedirect("../crmjsp/pdf_pricelist_analyze.jsp?doc_type="+doc_type);
					     break;
          //�ͷ�����ͳ��
					case DocType.PDF_PERSON_ORDER:
						   response.sendRedirect("../crmjsp/pdf_order_receive.jsp?doc_type="+doc_type);
					     break;
          //��Ա�¼�������
					case DocType.PDF_PERSON_EVENTS:
						   response.sendRedirect("../crmjsp/pdf_mbr_events.jsp?doc_type="+doc_type);
					     break;
          //����Ʒ���ͷ���
					case DocType.PDF_STOCK_TYPE:
						   response.sendRedirect("../crmjsp/pdf_stock_type.jsp?doc_type="+doc_type);
					     break;
          //����������
					case DocType.PDF_STOCK_AGE:
						   response.sendRedirect("../crmjsp/pdf_stock_time.jsp?doc_type="+doc_type);
					     break;
          //�ɹ�������
					case DocType.PDF_STOCK_PUR:
						   response.sendRedirect("../crmjsp/pdf_stock_pur.jsp?doc_type="+doc_type);
					     break;
          //��Ա��ֵ��¼
					case DocType.PDF_MEMBER_DEPOSIT:
						   response.sendRedirect("../crmjsp/pdf_member_deposit.jsp?doc_type="+doc_type);
					     break;
          //��Ա������֧ͳ�Ʊ�
					case DocType.PDF_MEMBER_CARD:
						   response.sendRedirect("../crmjsp/pdf_card_analyze.jsp?doc_type="+doc_type);
					     break;
          default:
              HTMLView hv=new HTMLView();
              hv.setSessionInfo(sessionInfo);
              hv.setWidth(750);
              hv.setSubject("����&nbsp;"+doc.getDocName());
              hv.addNewView(doc_type,parent_doc_id);

              response.setContentType(CONTENT_TYPE);
              PrintWriter out = response.getWriter();

              out.println(hv.getHTML());
              out.close();
        }
    }
}