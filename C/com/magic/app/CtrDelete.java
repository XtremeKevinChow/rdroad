package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * ϵͳ����ɾ��Servlet
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class CtrDelete extends BaseServlet 
{
  public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
     
       request.setCharacterEncoding("GBK");
       int doc_type=0;
       int doc_id=0;
       String sp=null;
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
             message("δ���",e.getMessage(),request, response);
             return;
        }


         
        DocType doc=new DocType(doc_type);
        Field[] fds=doc.getFields();
        switch(doc_type)
        {
        	
            //ɾ����Ա���
  	    case DocType.MEMBER_CATEGORIES: 
  	    	    sp="{?=call CONFIG.f_member_categories_delete(?)}";
                  break;
            //ɾ����Ա��ļ��������
  	    case DocType.RECRUITEMENT_CATEGORY: 
  	    	    sp="{?=call CONFIG.f_recruitment_category_delete(?)}";
                  break;
            //ɾ����Ա��ļ������ϸ����
  	    case DocType.RECRUITEMENT_TYPE: 
  	    	    sp="{?=call CONFIG.f_recruitment_type_delete(?)}";
                  break;
            //ɾ����Աѯ���¼�����
  	    case DocType.INQUIRY_TYPE: 
  	    	    sp="{?=call CONFIG.f_inquiry_type_delete(?)}";
                  break;

         //ɾ����ԱͶ�ߵȼ�
  	    case DocType.INQUIRY_LEVEL: 
  	    	    sp="{?=call CONFIG.f_inquiry_level_delete(?)}";
                  break;
            //ɾ����Ա������Ϊ����
  	    case DocType.MALPRACTICE_TYPE: 
  	    	    sp="{?=call CONFIG.f_malpractice_type_delete(?)}";
                  break;
            //ɾ��������������
  	    case DocType.DELIVERY_TYPE: 
  	    	    sp="{?=call CONFIG.f_delivery_type_delete(?)}";
                  break;
            //ɾ��Ŀ¼���
  	    case DocType.CATALOG_EDITION: 
  	    	    sp="{?=call CONFIG.f_catalog_edtion_delete(?)}";
                  break;
            //ɾ����Ʒ����
  	    case DocType.ITEM_TYPE: 
  	    	    sp="{?=call CONFIG.f_item_type_delete(?)}";
                  break;
            //ɾ����Ʒ˰��
  	    case DocType.ITEM_TAX: 
  	    	    sp="{?=call CONFIG.f_item_tax_delete(?)}";
                  break;                  
            //ɾ����Ӧ���ͻ���ʽ
  	    case DocType.SUPPLIER_DELIVERY_TYPE: 
  	    	    sp="{?=call CONFIG.f_supply_delivery_type_delete(?)}";
                  break;
            //ɾ����Ա����������
  	    case DocType.MBR_FREE_COMMITMENT: 
  	    	    sp="{?=call CONFIG.f_mbr_free_commitment_delete(?)}";
                  break;
            //ɾ����ԱeԪ��ñ���
  	    case DocType.MBR_EMONEY_RATES: 
  	    	    sp="{?=call CONFIG.f_mbr_emoney_rates_delete(?)}";
                  break;


	    //ɾ����˾��ַ 
            case DocType.ORG_LOCATION:
              sp="{?=call ORGANIZATION.f_org_location_delete(?)}";
            break;
            //ɾ����˾�������� 
            case DocType.DEPARTMENT_TYPE:
              sp="{?=call ORGANIZATION.f_department_type_delete(?)}";
            break;
            //ɾ����˾����
            case DocType.ORG_DEPARTMENT:
              sp="{?=call ORGANIZATION.f_org_department_delete(?)}";
            break;
            //ɾ����Ա���� 
            case DocType.EMPLOYEE_TYPE:
              sp="{?=call ORGANIZATION.f_employee_type_delete(?)}";
            break;
            //ɾ����˾��Ա 
            case DocType.ORG_PERSON:
              sp="{?=call ORGANIZATION.f_org_person_delete(?)}";
            break;
            //ɾ����ɫ��Ϣ 
            case DocType.ORG_ROLES:
              sp="{?=call ORGANIZATION.f_org_roles_delete(?)}";
            break;
         
        case DocType.DOCUMENT_TEMPLATE:
              sp="{?=call catalog.f_document_template_delete(?)}";
              break;   
          //ɾ����Ʒ����
            case DocType.PRODUCT:
             sp="{?=call product.f_product_delete(?)}";
            break;
                        
            //ɾ����Ա�����¼�
            case DocType.MEMBER_DEPOSITS:
              sp="{?=call member.F_MEMBER_DEPOSITS_DELETE(?)}";
            break;
            //ɾ����Ա����ʧ�¼�
            case DocType.MEMBER_CARD:
              sp="{?=call member.F_MEMBER_CARD_DELETE(?)}";
            break;
            //ɾ����Աѯ���¼�
            case DocType.MEMBER_INQUIRY:
              sp="{?=call member.F_MEMBER_INQUIRY_DELETE(?)}";
            break;              
             //ɾ�������Ա 
            case DocType.MEMBER_ORGANIZATION:
              sp="{?=call member.F_ORGANIZATION_DELETE(?)}";
            break;
            //ɾ����Ա��ַ
            case DocType.MEMBER_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_DELETE(?)}";
            break;
            //ɾ����Ʒ���
  	    case DocType.ITEM_CATEGORY: 
  	    	sp="{?=call PRODUCT.F_ITEM_CATEGORY_DELETE(?)}";
            break;   
            
				 //ɾ����Ŀ��
				 case DocType.PRICELIST_LINE:
  	        sp="{?=call catalog.f_pricelist_lines_del(?,?)}";
                  break;
         //ɾ��Ŀ¼����ϸ
				 case DocType.CATALOG_LINE:
  	        sp="{?=call catalog.F_CATALOG_LINES_DEL(?,?)}";
                  break;
            
            //ɾ����Ʒ����
  	    case DocType.PRICELIST_GIFT: 
  	    	    sp="{?=call CATALOG.F_PRICELIST_LINE_GIFT_DEL(?,?)}";
                  break; 
            //ɾ�����ͻ���
  	    case DocType.FREE_DELIVERY: 
  	    	    sp="{?=call CATALOG.F_PRD_FREE_DELIVERY_DEL(?)}";
                  break; 
         //ɾ����Ա�˿��¼�
            case DocType.MEMBER_DRAWBACK:
              sp="{?=call member.F_MEMBER_DRAWBACK_DELETE(?)}";
            break; 
          //ɾ����������Ʒ
             case DocType.GET_MBR_GIFT:
              sp="{?=call product.f_get_mbr_gifts_delete(?)}";
            break;
         //ɾ���׼���Ʒ��ϸ
         case DocType.SET_PRODUCTS:
              sp="{?=call product.F_SET_ITEM_LINE_DEL(?,?)}";
            break;
         //�����Ա��ַɾ��
         case DocType.ORGANIZATION_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_DELETE(?)}";
            break;
         case DocType.MBR_GIFTS:
              sp="{?=call CATALOG.F_MBR_GIFTS_DEL(?)}";
            break;
					//ɾ��Ŀ¼
         case DocType.CATALOG:
              sp= "{?=call catalog.f_pricelist_delete(?,?)}";
            break;

					//ɾ��������Ŀ����
         case DocType.PRICELIST_PROMOTION_LINE:
              sp="{?=call catalog.f_promotion_lines_del(?,?)}";
              break;

				 case 10100:
					    sp="{?=call config.f_bas_saved_queries_del(?)}";
				      break;
  	    case DocType.PROMOTION_PRICELIST_GIFT: 
  	    	    sp="{?=call CATALOG.F_PRICELIST_LINE_GIFT_DEL(?,?)}";
                  break; 
          //ɾ����������
	       case DocType.SERVICE_CONFIG:
              sp="{?=call CONFIG.F_BAS_JOBS_DELETE(?)}";
            break;
          //ɾ���ʱ��ַ
	       case DocType.MBR_POSTCODE:
              sp="{?=call CONFIG.F_POSTCODE_DELETE(?)}";
            break;
          //ɾ����ȯ
	       case DocType.MBR_GIFT_CERTIFICATE:
           sp="{?=call member.f_gift_certificate_delete(?)}";
           break;   
          //ɾ����Ʒ��λ
	       case DocType.PRD_UOM:
           sp="{?=call config.f_item_uom_delete(?)}";
           break;             
          //��Ա��ļ
          //case DocType.MEMBER_RECRUITEMENT:
          //sp="{?=call member.F_MEMBER_RECRUITMENTS(?)}";
          // break;
        }
        DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
        CallableStatement cstmt=null;
        try
        {
             int para_index=2;
             cstmt= dblink.prepareCall(sp); 
             cstmt.setInt(para_index,doc_id); 
             //System.out.println("��������:doc_id");
            // System.out.println("����ֵ��"+doc_id);
             //System.out.println("��������:INTEGER"); 
             //System.out.println(fds.length);
             
             
            if(doc_type==doc.CATALOG || doc_type==doc.MEMBER_GROUP || doc_type == doc.PRICELIST_LINE || 
							 doc_type == doc.CATALOG_LINE || doc_type==doc.PRICELIST_GIFT  || doc_type==doc.MEMBER_DRAWBACK || 
							 doc_type == doc.PRICELIST_PROMOTION_LINE || doc_type==doc.PROMOTION_PRICELIST_GIFT)
            {
                 para_index++;
                cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
                //System.out.println("��������:����ԱID");
                //System.out.println("����ֵ��"+sessionInfo.getOperatorID());
                //System.out.println("��������:INTEGER");
            }

						if(doc_type==doc.SET_PRODUCTS){
                para_index++;
                cstmt.setInt(para_index,Integer.parseInt(request.getParameter("parent_doc_id"))); 
                //System.out.println("��������:���ĵ�ID");
                //System.out.println("����ֵ��"+Integer.parseInt(request.getParameter("parent_doc_id")));
                //System.out.println("��������:INTEGER");
						}
           
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getShort(1);
            if(re<0)
            {
                   KException ke=new KException(re);
                   message("δ���","�������:"+ke.getErrorCode()+"<br>��������:"+ke.getMessage(),request, response);
                   return;
            }
            else
            {
                Log log=new Log();
                log.audit(sessionInfo.getCompanyID(),sessionInfo.getOperatorID(),doc_type,log.EVENT_DELETE,doc_id);
                  if(doc_type==doc.PRICELIST_LINE || doc_type == doc.PRICELIST_PROMOTION_LINE)
              {
                  success("���","���ɾ����¼����.<br><a href=\"../app/viewdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
              }
              if(doc_type==doc.MEMBER || doc_type==doc.PRODUCT)
              {
                 success("���","��ɼ�¼ɾ������.<br>[<a href=\"../app/viewlist?doc_type="+doc_type+"\">����</a>]",request, response);   
              }
							if(doc_type==doc.CATALOG_LINE || doc_type==doc.PRICELIST_GIFT || doc_type == doc.FREE_DELIVERY || doc_type==doc.PROMOTION_PRICELIST_GIFT){
                  success("���","���ɾ����¼����.<br><a href=\"../app/viewdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
							}
                if(doc_type==doc.ORGANIZATION_ADDRESS )
                {
                  success("���","���ɾ����¼����.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER_ORGANIZATION+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
                }	
								if(doc_type==doc.SET_PRODUCTS){
                  success("���","���ɾ����¼����.<br><a href=\"../app/viewdetail?doc_type=1500&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
								}
								if(doc_type==doc.MEMBER_ADDRESS )
                {
                  success("���","���ɾ����¼����.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
                }
								if(doc_type==doc.MBR_GIFTS){
                  success("���","���ɾ����¼����.<br><a href=\"../app/viewdetail?doc_type="+DocType.CATALOG+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
								}
                if(doc_type==doc.PRICELIST||doc_type==doc.CATALOG||doc_type==doc.MEMBER_PROMOTION){
                  success("���","���ɾ����¼����.<br><a href=\"../app/viewdetail?doc_type="+doc_type+">����.</a>",request, response);
                   return;
								}
                if(doc_type==doc.MBR_POSTCODE){
                 success("���","��ɼ�¼ɾ������.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_POSTCODE + "&t_code=9224\">����.</a>",request, response);
                   return;                
                }
                if(doc_type==doc.MBR_GIFT_CERTIFICATE){
                 success("���","��ɼ�¼ɾ������.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_GIFT_CERTIFICATE + "&t_code=22600\">����.</a>",request, response);
                   return;                
                }     
                if(doc_type==doc.PRD_UOM){
                 success("���","��ɼ�¼ɾ������.<br><a href=\"../app/viewconfig?doc_type="+doc.PRD_UOM + "&t_code=9443\">����.</a>",request, response);
                   return;                
                }                  
                success("���","��ɼ�¼ɾ������.<br>[<a href=\"../app/viewconfig?doc_type="+doc_type+"\">����</a>]",request, response);   
                
            }
        }catch(Exception e)
        {
          System.out.println(e);
        }finally
      {
          try
          {
            if(cstmt!=null) cstmt.close();
            dblink.close();
          }catch(Exception e)
          {
              e.printStackTrace();
          }
         
      }
    }     
}