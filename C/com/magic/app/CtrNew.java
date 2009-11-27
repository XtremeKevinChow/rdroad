package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import com.magic.crm.util.DBManager;

import java.sql.*;
//import oracle.sql.*;
/**
 * ϵͳ��������ʱServlet
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class CtrNew extends BaseServlet 
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
            
       int doc_type=0;
       String sp=null;
      
       try{
            doc_type=Integer.parseInt(request.getParameter("doc_type"));
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
             //��Ա��ļ
            case DocType.MEMBER_RECRUITEMENT:
              sp="{?=call member.F_MEMBER_RECRUITMENTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;
            //���ӻ�Ա�������� 
            case DocType.MEMBER_PROMOTION:
              sp="{?=call member.F_PRD_PRICELIST_ADD(?,?,?,?,?,?,?,?)}";
              break;
            //��Ա�����¼�
            case DocType.MEMBER_DEPOSITS:
              sp="{?=call member.F_MEMBER_DEPOSITS(?,?,?,?,?,?,?,?)}";
              break;
            //���ӻ�Ա�����¼�
            case DocType.MEMBER_UPGRADE:
              sp="{?=call member.F_MEMBER_UPGRADE(?,?,?,?,?,?)}";
              break;
            //���ӻ�Ա����ʧ�¼�
            case DocType.MEMBER_CARD:
              sp="{?=call member.F_MEMBER_CARD(?,?,?,?,?,?,?,?)}";
              break;
            //��Ա�˻�
            case DocType.MEMBER_CANCEL:
              sp="{?=call member.F_MEMBER_CANCEL(?,?,?,?,?)}";
              break;
            //���ӻ�Աѯ���¼�
            case DocType.MEMBER_INQUIRY:
              sp="{?=call member.F_MEMBER_INQUIRY(?,?,?,?,?,?,?)}";
              break;
           
            //���������Ա 
            case DocType.MEMBER_ORGANIZATION:
              sp="{?=call member.F_ORGANIZATION(?,?,?,?,?,?,?,?)}";
              break;
             //���ӻ�Ա��ַ
            case DocType.MEMBER_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_ADD(?,?,?,?,?)}";
              break;
            //��˾��ַ 
            case DocType.ORG_LOCATION:
              sp="{?=call ORGANIZATION.F_org_location(?,?,?,?,?,?,?,?,?,?)}";
              break;
            //��˾�������� 
            case DocType.DEPARTMENT_TYPE:
              sp="{?=call ORGANIZATION.F_department_type(?,?,?)}";
              break;
            //��˾����
            case DocType.ORG_DEPARTMENT:
              sp="{?=call ORGANIZATION.F_org_department(?,?,?,?,?,?)}";
              break;
            //��Ա���� 
            case DocType.EMPLOYEE_TYPE:
              sp="{?=call ORGANIZATION.f_employee_type(?,?,?)}";
              break;
            //��˾��Ա 
            case DocType.ORG_PERSON:
              sp="{?=call ORGANIZATION.F_org_person(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;
            //��ɫ��Ϣ 
            case DocType.ORG_ROLES:
              sp="{?=call ORGANIZATION.f_org_roles_add(?,?,?)}";
              break;
           //���Ӳ�Ʒ              
            case DocType.PRODUCT:
              sp="{?=call product.f_product_add(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;
			     //Ŀ¼���
            case DocType.CATALOG_EDITION:
              sp="{?=call catalog.f_catalog_edition_add(?,?)}";
              break;
            //��Ա��
            case DocType.MEMBER_GROUP:
              sp="{?=call MEMBER.F_MEMBER_GROUP_ADD(?,?,?,?)}";
              break;
             //��Ա���
  	        case DocType.MEMBER_CATEGORIES: 
  	    	    sp="{?=call CONFIG.f_member_categories_add(?,?,?)}";
              break;
             //��Ա��ļ��������
  	        case DocType.RECRUITEMENT_CATEGORY: 
  	    	    sp="{?=call CONFIG.f_recruitment_category_add(?,?)}";
              break;
            //��Ա��ļ������ϸ����
  	        case DocType.RECRUITEMENT_TYPE: 
  	    	    sp="{?=call CONFIG.f_recruitment_type_add(?,?,?)}";
              break;
             //��Աѯ���¼�����
  	        case DocType.INQUIRY_TYPE: 
  	    	    sp="{?=call CONFIG.f_inquiry_type_add(?,?,?)}";
              break;
            //��ԱͶ�ߵȼ�
  	        case DocType.INQUIRY_LEVEL: 
  	    	    sp="{?=call CONFIG.f_inquiry_level_add(?,?,?)}";
              break;
            //��Ա������Ϊ����
  	        case DocType.MALPRACTICE_TYPE: 
  	    	    sp="{?=call CONFIG.f_malpractice_type_add(?,?)}";
              break;
            //������������
  	        case DocType.DELIVERY_TYPE: 
  	    	    sp="{?=call CONFIG.f_delivery_type_add(?,?,?)}";
              break;
            //��Ʒ����
  	        case DocType.ITEM_TYPE: 
  	    	    sp="{?=call CONFIG.f_item_type_add(?,?)}";
              break;
            //��Ʒ˰��
  	        case DocType.ITEM_TAX: 
  	    	    sp="{?=call CONFIG.f_item_tax_add(?,?,?,?)}";
              break;                  
            //��Ӧ���ͻ���ʽ
  	        case DocType.SUPPLIER_DELIVERY_TYPE: 
  	    	    sp="{?=call CONFIG.f_supplier_delivery_type_add(?,?)}";
              break;
            //��Ա����������
  	        case DocType.MBR_FREE_COMMITMENT: 
  	    	    sp="{?=call CONFIG.f_mbr_free_commitment_add(?,?,?)}";
              break;
            //��ԱeԪ��ñ���
  	        case DocType.MBR_EMONEY_RATES: 
  	    	    sp="{?=call CONFIG.f_mbr_emoney_rates_add(?,?,?,?)}";
              break; 
						//���Ӽ�Ŀ����
				    case DocType.PRICELIST_LINE:
					    sp= "{?=call catalog.F_PRICELIST_LINES_ADD(?,?,?,?,?,?,?)}";
				      break; 
						//����Ŀ¼����
				    case DocType.CATALOG_LINE:
					    sp= "{?=call catalog.F_CATALOG_LINES_ADD(?,?,?,?,?,?,?,?,?)}";
				      break; 
            //������Ʒ����
  	        case DocType.PRICELIST_GIFT: 
  	    	    sp="{?=call CATALOG.F_PRICELIST_LINE_GIFT_ADD(?,?,?,?,?,?,?)}";
              break;  
            //�������ͻ���
  	        case DocType.FREE_DELIVERY: 
  	    	    sp="{?=call CATALOG.F_PRD_FREE_DELIVERY_ADD(?,?,?)}";
              break; 
            //���Ӽ�Ŀ��
            case DocType.PRICELIST:
              sp="{?=call catalog.f_pricelist_add(?,?,?,?,?,?,?)}";
              break;
            //����Ŀ¼
            case DocType.CATALOG:
              sp="{?=call catalog.f_catalog_add(?,?,?,?,?,?,?,?,?)}";
              break;
            //�����ĵ�ģ��
            case DocType.DOCUMENT_TEMPLATE:
              sp="{?=call catalog.f_document_template_add(?,?,?,?)}";
              break;         
             //���Ӳ�Ʒ���
  	        case DocType.ITEM_CATEGORY: 
  	    	    sp="{?=call PRODUCT.F_ITEM_CATEGORY_ADD(?,?,?,?,?,?)}";
              break;    
            //���ӻ�Ա�˿��¼�
            case DocType.MEMBER_DRAWBACK:
              sp="{?=call member.F_MEMBER_DRAWBACK(?,?,?,?,?,?)}";
              break; 
            //���ӽ�����Ʒ
            case DocType.GET_MBR_GIFT:
              sp="{?=call product.f_get_mbr_gifts_add(?,?,?,?)}";
              break;
            //�����׼���Ʒ��ϸ
            case DocType.SET_PRODUCTS:
              sp="{?=call product.F_SET_ITEM_LINE_ADD(?,?,?,?,?)}";
              break;
             //���������Ա��ַ
            case DocType.ORGANIZATION_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_ADD(?,?,?,?,?)}";
              break;
            case DocType.MBR_GIFTS:
              sp="{?=call CATALOG.F_MBR_GIFTS_ADD(?,?,?,?,?)}";
              break;
            //���Ӵ�����Ŀ����
				    case DocType.PRICELIST_PROMOTION_LINE:
					    sp= "{?=call catalog.f_promotion_lines_add(?,?,?,?,?)}";
				      break;
            //���Ӵ�����Ʒ��
				    case DocType.PROMOTION_PRICELIST_GIFT:
				      sp= "{?=call catalog.f_promotion_line_gift_add(?,?,?,?,?,?)}";
				      break; 
            //���ӷ�������
	          case DocType.SERVICE_CONFIG:
              sp="{?=call CONFIG.F_BAS_JOBS_ADD(?,?,?,?,?,?,?,?,?,?)}";
              break;
            //�����ʱ��ַ
	          case DocType.MBR_POSTCODE:
              sp="{?=call CONFIG.F_POSTCODE_ADD(?,?,?,?)}";
              break;
            //������ȯ
	          case DocType.MBR_GIFT_CERTIFICATE:
              sp="{?=call member.f_member_gift_certificate_add(?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;  
            //���Ӳ�Ʒ��λ
	          case DocType.PRD_UOM:
              sp="{?=call config.v_item_uom_add(?,?)}";
              break;            
             /*  case DocType.MEMBER_RECRUITEMENT:
              sp="{?=call sp_name(?,?,?)}";
            break;
             */
        }
        Connection conn=null; 
        Statement stmt=null;
        ResultSet rs=null;
        CallableStatement cstmt=null;
        try
        {
        	conn=DBManager.getConnection();
             int para_index=1;
             cstmt = conn.prepareCall(sp); 
             //System.out.println(fds.length);
             for(int i=0;i<fds.length;i++)
             {           
             	//System.out.println("fds.length is "+fds.length);
                  if(fds[i].isNew())
                  {
                      para_index++;
                      String name=fds[i].getName();
                      //System.out.println("��������:"+name);
                      //System.out.println("����ֵ��"+request.getParameter(name));
                      //System.out.println("��������:"+fds[i].getDataType()+"\n");
                  
                      String para_value=StringUtil.cEmpty(request.getParameter(name)).trim();
                      if(fds[i].getInputType().equals("KEYSET"))
                      {
                          String key_value=StringUtil.cEmpty(request.getParameter(name+"_key"));
                          para_value="";
                          if(!key_value.equals(""))
                          {
                                  Lookup lu=LookupMap.getLookup(fds[i].getLookup());
                                  stmt=conn.createStatement();rs= stmt.executeQuery("select "+lu.getValueField()+" from "+lu.getDataSource()+" where "+lu.getKeyField()+"='"+key_value+"'");
                                try
                                {
                                    if(rs.next())
                                      para_value=rs.getString(1);
                                    else
                                    {
                                       message("δ���","�ֶ�\""+fds[i].getCaption()+"\"û��������Ч�Ĵ���.",request, response);
                                       return;
                                    }
                                }catch(Exception e)
                                {
                                  e.printStackTrace();
                                  System.out.println("error-"+e);
                                }
                          }
                      } 
                      if(fds[i].isRequired())
                      {
                          if(para_value.trim().equals(""))
                          {
                            message("δ���","��Ҫ���ֶ�\""+fds[i].getCaption()+"\"û������",request, response);
                            return;
                          }
                      }
                      if(fds[i].getDataType().equals("CHAR"))
                      {
                          //String value=para_value.trim();
                          String value=para_value.trim();
                          if(fds[i].getName().toUpperCase().equals("PWD"))
                          {
                              value=(new com.magic.utils.MD5()).getMD5ofStr(value);
                          }
                          cstmt.setString(para_index,value); 
                      }
                      if(fds[i].getDataType().equals("DATE"))
                      {
                          String value=para_value.trim();
                          if(!StringUtil.isDate(value)&&!value.equals(""))
                          {
                              message("δ���","�ֶ�\""+fds[i].getCaption()+"\"������Ч����������.",request, response);
                              return;
                          }
                          cstmt.setString(para_index,value);   
                      }
                      if(fds[i].getDataType().equals("INTEGER"))
                      {
                          if(!StringUtil.isNum(para_value) &&!para_value.equals(""))
                          {
                              message("δ���","�ֶ�\""+fds[i].getCaption()+"\"��������.",request, response);
                              return;
                          }
                          int value=0;
                          if(StringUtil.isNum(para_value))
                              value=Integer.parseInt(para_value);
                          cstmt.setInt(para_index,value);  
                      }
                      if(fds[i].getDataType().equals("FLOAT"))
                      {
                          if(!StringUtil.isNumEx(para_value)&&!para_value.equals(""))
                          {
                              message("δ���","�ֶ�\""+fds[i].getCaption()+"\"����������.",request, response);
                              return;
                          }
                          float value=0;
                          if(StringUtil.isNumEx(para_value))
                              value=Float.parseFloat(para_value);
                          else
                              if(fds[i].getInputType().equals("TEXT")) value=-1;
                          cstmt.setFloat(para_index,value);  
                      } 
                      if(fds[i].getDataType().equals("CLOB"))
                      {
                          // Clob clob = cstmt.getClob(4);
                         //Clob cb.getAsciiStream().read
                         // clob.setString(0,request.getParameter(name));
                          //clob.open (CLOB.MODE_READWRITE);
                          String value=para_value;
                        //  clob.putString(0,value);
                        //  clob.close();
                         // cstmt.setObject(para_index,clob);   
                         cstmt.setString(para_index,value); 
                      }         
                  } 
            }
         if(doc.isParentDoc())
            {
                para_index++;
                cstmt.setInt(para_index,Integer.parseInt(request.getParameter("parent_doc_id"))); 
                //System.out.println("��������:���ĵ�ID");
                //System.out.println("����ֵ��"+Integer.parseInt(request.getParameter("parent_doc_id")));
                //System.out.println("��������:INTEGER");
            }          
            if(!doc.is_global())  
            {
                para_index++;
                cstmt.setInt(para_index,sessionInfo.getCompanyID()); 
                //System.out.println("��������:���ֲ�ID");
                //System.out.println("����ֵ��"+sessionInfo.getCompanyID());
                //System.out.println("��������:INTEGER");
            }
           
            if(doc.isLogOperator())
            {
                 para_index++;
                cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
                //System.out.println("��������:����ԱID");
                //System.out.println("����ֵ��"+sessionInfo.getOperatorID());
                //System.out.println("��������:INTEGER");
            }

           
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getInt(1);           
            if(re<0)
            {
                   KException ke=new KException(re);
                   message("δ���","�������:"+ke.getErrorCode()+"<br>��������:"+ke.getMessage(),request, response);
            }
            else
            {
                Log log=new Log();
                log.audit(sessionInfo.getCompanyID(),sessionInfo.getOperatorID(),doc_type,log.EVENT_ADD,re);
                if(doc_type==doc.CATALOG||doc_type==doc.PRICELIST||doc_type==doc.MEMBER_PROMOTION)
                {
                    success("���","�ѳɹ����Ӵ�����¼�����Լ���<a href=\"../app/viewdetail?doc_type="+doc_type+"&doc_id="+re+"\">���ô˴����ľ�������</a>.",request, response);
                   return;
                }
                
                if(doc_type==doc.MEMBER_LOSECARD)
                {
                   success("���","�ѳɹ���Ա����ʧ�����ɿ����Ѳ���ʹ�ã��¿��ſ�ʼ��Ч��<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER+"&doc_id="+re+"\">�鿴�»�Ա������Ϣ</a>.",request, response);
                   return;
                } 
               if(doc_type==doc.PRODUCT)
				{	
				   stmt=conn.createStatement();
                   rs= stmt.executeQuery("select item_code from prd_items where item_id="+re);
				   try{
						if(rs.next())
						{	
						 String item_code=rs.getString("item_code");
						 success("���","�����Ӳ�Ʒ��Ϣ����Ʒ����Ϊ<a href=\"../app/viewdetail?doc_type=1500&doc_id="+re+"\">"+item_code+"</a>",request, response);
						 return;
						}
					  }
					catch(Exception e)
                      {
                         e.printStackTrace();
                         System.out.println("error-"+e);
                      }
				}
                if(doc_type==doc.MEMBER_RECRUITEMENT)
                {
                  stmt=conn.createStatement();rs= stmt.executeQuery("select card_id from mbr_members where id="+re);
				   try{
						if(rs.next())
						{	
						 String card_id=rs.getString("card_id");
						 success("���","�����ӻ�Ա��Ϣ����Ա����Ϊ<a href=\"../app/viewdetail?doc_type=1000&doc_id="+re+"\">"+card_id+"</a>����Ա��Ϊ��ͨ��Ա��������<a href=\"/member_order_add.jsp?doc_type=4000&card_id_key="+card_id+"\">��ᶩ��</a>",request, response);
						 return;
						}
				      }
					catch(Exception e)
                      {
                         System.out.println("error-"+e);
                      }
                }
                	
								if(doc_type==doc.PRICELIST_LINE || doc_type == doc.PRICELIST_PROMOTION_LINE)
                {
                  success("���","���������¼����.<br><a href=\"../app/viewdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
                }

								if(doc_type==doc.CATALOG_LINE || doc_type==doc.PRICELIST_GIFT || doc_type == doc.FREE_DELIVERY || doc_type==doc.PROMOTION_PRICELIST_GIFT)
                {
                  success("���","���������¼����.<br><a href=\"../app/viewdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
                }
                if(doc_type==doc.MEMBER_ADDRESS )
                {
                  success("���","���������¼����.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
                }
                if(doc_type==doc.ORGANIZATION_ADDRESS )
                {
                  success("���","���������¼����.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER_ORGANIZATION+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
                }								
								if(doc_type==doc.SET_PRODUCTS){
                  success("���","���������¼����.<br><a href=\"../app/viewdetail?doc_type=1500&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
								}
								if(doc_type==doc.MBR_GIFTS){
                  success("���","���������¼����.<br><a href=\"../app/viewdetail?doc_type="+DocType.CATALOG+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                   return;
								}
                if(doc_type==doc.ITEM_CATEGORY){
                 success("���","��ɼ�¼���Ӳ���.<br><a href=\"../app/viewconfig?doc_type="+doc.ITEM_CATEGORY + "&t_code=4300\">����.</a>",request, response);
                   return;                
                }  
                if(doc_type==doc.MBR_POSTCODE){
                 success("���","��ɼ�¼���Ӳ���.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_POSTCODE + "&t_code=9224\">����.</a>",request, response);
                   return;                
                }  
                if(doc_type==doc.MBR_GIFT_CERTIFICATE){
                 success("���","��ɼ�¼���Ӳ���.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_GIFT_CERTIFICATE + "&t_code=22600\">����.</a>",request, response);
                   return;                
                }   
                if(doc_type==doc.PRD_UOM){
                 success("���","��ɼ�¼���Ӳ���.<br><a href=\"../app/viewconfig?doc_type="+doc.PRD_UOM + "&t_code=9443\">����.</a>",request, response);
                   return;                
                }                 
                if(doc_type>10000)
                  success("���","��ɼ�¼���Ӳ���.<br>[<a href=\"../app/viewconfig?doc_type="+doc_type+"\">����.</a>]",request, response);
                else
                  success("���","��ɼ�¼���Ӳ���.",request, response);
                conn.close();
            }
        }catch(Exception e)
        {
          e.printStackTrace();
          message("δ���",e.getMessage(),request, response);
        }finally
      {

         try
          {
            if(cstmt!=null) cstmt.close();
            conn.close();
          }catch(Exception e)
          {
              e.printStackTrace();
          }
      }
    }     
}