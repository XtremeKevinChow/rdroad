package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 系统处理删除Servlet
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
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
             message("未完成",e.getMessage(),request, response);
             return;
        }


         
        DocType doc=new DocType(doc_type);
        Field[] fds=doc.getFields();
        switch(doc_type)
        {
        	
            //删除会员类别
  	    case DocType.MEMBER_CATEGORIES: 
  	    	    sp="{?=call CONFIG.f_member_categories_delete(?)}";
                  break;
            //删除会员招募渠道类型
  	    case DocType.RECRUITEMENT_CATEGORY: 
  	    	    sp="{?=call CONFIG.f_recruitment_category_delete(?)}";
                  break;
            //删除会员招募渠道详细类型
  	    case DocType.RECRUITEMENT_TYPE: 
  	    	    sp="{?=call CONFIG.f_recruitment_type_delete(?)}";
                  break;
            //删除会员询问事件类型
  	    case DocType.INQUIRY_TYPE: 
  	    	    sp="{?=call CONFIG.f_inquiry_type_delete(?)}";
                  break;

         //删除会员投诉等级
  	    case DocType.INQUIRY_LEVEL: 
  	    	    sp="{?=call CONFIG.f_inquiry_level_delete(?)}";
                  break;
            //删除会员不良行为类型
  	    case DocType.MALPRACTICE_TYPE: 
  	    	    sp="{?=call CONFIG.f_malpractice_type_delete(?)}";
                  break;
            //删除订单发送渠道
  	    case DocType.DELIVERY_TYPE: 
  	    	    sp="{?=call CONFIG.f_delivery_type_delete(?)}";
                  break;
            //删除目录版块
  	    case DocType.CATALOG_EDITION: 
  	    	    sp="{?=call CONFIG.f_catalog_edtion_delete(?)}";
                  break;
            //删除产品类型
  	    case DocType.ITEM_TYPE: 
  	    	    sp="{?=call CONFIG.f_item_type_delete(?)}";
                  break;
            //删除产品税率
  	    case DocType.ITEM_TAX: 
  	    	    sp="{?=call CONFIG.f_item_tax_delete(?)}";
                  break;                  
            //删除供应商送货方式
  	    case DocType.SUPPLIER_DELIVERY_TYPE: 
  	    	    sp="{?=call CONFIG.f_supply_delivery_type_delete(?)}";
                  break;
            //删除会员免义务条件
  	    case DocType.MBR_FREE_COMMITMENT: 
  	    	    sp="{?=call CONFIG.f_mbr_free_commitment_delete(?)}";
                  break;
            //删除会员e元获得比例
  	    case DocType.MBR_EMONEY_RATES: 
  	    	    sp="{?=call CONFIG.f_mbr_emoney_rates_delete(?)}";
                  break;


	    //删除公司地址 
            case DocType.ORG_LOCATION:
              sp="{?=call ORGANIZATION.f_org_location_delete(?)}";
            break;
            //删除公司部门类型 
            case DocType.DEPARTMENT_TYPE:
              sp="{?=call ORGANIZATION.f_department_type_delete(?)}";
            break;
            //删除公司部门
            case DocType.ORG_DEPARTMENT:
              sp="{?=call ORGANIZATION.f_org_department_delete(?)}";
            break;
            //删除人员类型 
            case DocType.EMPLOYEE_TYPE:
              sp="{?=call ORGANIZATION.f_employee_type_delete(?)}";
            break;
            //删除公司人员 
            case DocType.ORG_PERSON:
              sp="{?=call ORGANIZATION.f_org_person_delete(?)}";
            break;
            //删除角色信息 
            case DocType.ORG_ROLES:
              sp="{?=call ORGANIZATION.f_org_roles_delete(?)}";
            break;
         
        case DocType.DOCUMENT_TEMPLATE:
              sp="{?=call catalog.f_document_template_delete(?)}";
              break;   
          //删除产品数据
            case DocType.PRODUCT:
             sp="{?=call product.f_product_delete(?)}";
            break;
                        
            //删除会员付款事件
            case DocType.MEMBER_DEPOSITS:
              sp="{?=call member.F_MEMBER_DEPOSITS_DELETE(?)}";
            break;
            //删除会员卡挂失事件
            case DocType.MEMBER_CARD:
              sp="{?=call member.F_MEMBER_CARD_DELETE(?)}";
            break;
            //删除会员询问事件
            case DocType.MEMBER_INQUIRY:
              sp="{?=call member.F_MEMBER_INQUIRY_DELETE(?)}";
            break;              
             //删除团体会员 
            case DocType.MEMBER_ORGANIZATION:
              sp="{?=call member.F_ORGANIZATION_DELETE(?)}";
            break;
            //删除会员地址
            case DocType.MEMBER_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_DELETE(?)}";
            break;
            //删除产品类别
  	    case DocType.ITEM_CATEGORY: 
  	    	sp="{?=call PRODUCT.F_ITEM_CATEGORY_DELETE(?)}";
            break;   
            
				 //删除价目表
				 case DocType.PRICELIST_LINE:
  	        sp="{?=call catalog.f_pricelist_lines_del(?,?)}";
                  break;
         //删除目录表详细
				 case DocType.CATALOG_LINE:
  	        sp="{?=call catalog.F_CATALOG_LINES_DEL(?,?)}";
                  break;
            
            //删除礼品组行
  	    case DocType.PRICELIST_GIFT: 
  	    	    sp="{?=call CATALOG.F_PRICELIST_LINE_GIFT_DEL(?,?)}";
                  break; 
            //删除免送货费
  	    case DocType.FREE_DELIVERY: 
  	    	    sp="{?=call CATALOG.F_PRD_FREE_DELIVERY_DEL(?)}";
                  break; 
         //删除会员退款事件
            case DocType.MEMBER_DRAWBACK:
              sp="{?=call member.F_MEMBER_DRAWBACK_DELETE(?)}";
            break; 
          //删除介绍人赠品
             case DocType.GET_MBR_GIFT:
              sp="{?=call product.f_get_mbr_gifts_delete(?)}";
            break;
         //删除套件产品明细
         case DocType.SET_PRODUCTS:
              sp="{?=call product.F_SET_ITEM_LINE_DEL(?,?)}";
            break;
         //团体会员地址删除
         case DocType.ORGANIZATION_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_DELETE(?)}";
            break;
         case DocType.MBR_GIFTS:
              sp="{?=call CATALOG.F_MBR_GIFTS_DEL(?)}";
            break;
					//删除目录
         case DocType.CATALOG:
              sp= "{?=call catalog.f_pricelist_delete(?,?)}";
            break;

					//删除促销价目表行
         case DocType.PRICELIST_PROMOTION_LINE:
              sp="{?=call catalog.f_promotion_lines_del(?,?)}";
              break;

				 case 10100:
					    sp="{?=call config.f_bas_saved_queries_del(?)}";
				      break;
  	    case DocType.PROMOTION_PRICELIST_GIFT: 
  	    	    sp="{?=call CATALOG.F_PRICELIST_LINE_GIFT_DEL(?,?)}";
                  break; 
          //删除服务配置
	       case DocType.SERVICE_CONFIG:
              sp="{?=call CONFIG.F_BAS_JOBS_DELETE(?)}";
            break;
          //删除邮编地址
	       case DocType.MBR_POSTCODE:
              sp="{?=call CONFIG.F_POSTCODE_DELETE(?)}";
            break;
          //删除礼券
	       case DocType.MBR_GIFT_CERTIFICATE:
           sp="{?=call member.f_gift_certificate_delete(?)}";
           break;   
          //删除产品单位
	       case DocType.PRD_UOM:
           sp="{?=call config.f_item_uom_delete(?)}";
           break;             
          //会员招募
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
             //System.out.println("参数名称:doc_id");
            // System.out.println("参数值："+doc_id);
             //System.out.println("参数类型:INTEGER"); 
             //System.out.println(fds.length);
             
             
            if(doc_type==doc.CATALOG || doc_type==doc.MEMBER_GROUP || doc_type == doc.PRICELIST_LINE || 
							 doc_type == doc.CATALOG_LINE || doc_type==doc.PRICELIST_GIFT  || doc_type==doc.MEMBER_DRAWBACK || 
							 doc_type == doc.PRICELIST_PROMOTION_LINE || doc_type==doc.PROMOTION_PRICELIST_GIFT)
            {
                 para_index++;
                cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
                //System.out.println("参数名称:操作员ID");
                //System.out.println("参数值："+sessionInfo.getOperatorID());
                //System.out.println("参数类型:INTEGER");
            }

						if(doc_type==doc.SET_PRODUCTS){
                para_index++;
                cstmt.setInt(para_index,Integer.parseInt(request.getParameter("parent_doc_id"))); 
                //System.out.println("参数名称:父文档ID");
                //System.out.println("参数值："+Integer.parseInt(request.getParameter("parent_doc_id")));
                //System.out.println("参数类型:INTEGER");
						}
           
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getShort(1);
            if(re<0)
            {
                   KException ke=new KException(re);
                   message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
                   return;
            }
            else
            {
                Log log=new Log();
                log.audit(sessionInfo.getCompanyID(),sessionInfo.getOperatorID(),doc_type,log.EVENT_DELETE,doc_id);
                  if(doc_type==doc.PRICELIST_LINE || doc_type == doc.PRICELIST_PROMOTION_LINE)
              {
                  success("完成","完成删除记录操作.<br><a href=\"../app/viewdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
              }
              if(doc_type==doc.MEMBER || doc_type==doc.PRODUCT)
              {
                 success("完成","完成记录删除操作.<br>[<a href=\"../app/viewlist?doc_type="+doc_type+"\">返回</a>]",request, response);   
              }
							if(doc_type==doc.CATALOG_LINE || doc_type==doc.PRICELIST_GIFT || doc_type == doc.FREE_DELIVERY || doc_type==doc.PROMOTION_PRICELIST_GIFT){
                  success("完成","完成删除记录操作.<br><a href=\"../app/viewdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
							}
                if(doc_type==doc.ORGANIZATION_ADDRESS )
                {
                  success("完成","完成删除记录操作.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER_ORGANIZATION+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }	
								if(doc_type==doc.SET_PRODUCTS){
                  success("完成","完成删除记录操作.<br><a href=\"../app/viewdetail?doc_type=1500&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
								}
								if(doc_type==doc.MEMBER_ADDRESS )
                {
                  success("完成","完成删除记录操作.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }
								if(doc_type==doc.MBR_GIFTS){
                  success("完成","完成删除记录操作.<br><a href=\"../app/viewdetail?doc_type="+DocType.CATALOG+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
								}
                if(doc_type==doc.PRICELIST||doc_type==doc.CATALOG||doc_type==doc.MEMBER_PROMOTION){
                  success("完成","完成删除记录操作.<br><a href=\"../app/viewdetail?doc_type="+doc_type+">返回.</a>",request, response);
                   return;
								}
                if(doc_type==doc.MBR_POSTCODE){
                 success("完成","完成记录删除操作.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_POSTCODE + "&t_code=9224\">返回.</a>",request, response);
                   return;                
                }
                if(doc_type==doc.MBR_GIFT_CERTIFICATE){
                 success("完成","完成记录删除操作.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_GIFT_CERTIFICATE + "&t_code=22600\">返回.</a>",request, response);
                   return;                
                }     
                if(doc_type==doc.PRD_UOM){
                 success("完成","完成记录删除操作.<br><a href=\"../app/viewconfig?doc_type="+doc.PRD_UOM + "&t_code=9443\">返回.</a>",request, response);
                   return;                
                }                  
                success("完成","完成记录删除操作.<br>[<a href=\"../app/viewconfig?doc_type="+doc_type+"\">返回</a>]",request, response);   
                
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