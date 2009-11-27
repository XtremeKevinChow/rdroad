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
 * 系统处理新增时Servlet
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
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
             message("未完成",e.getMessage(),request, response);
             return;
        }
         


        DocType doc=new DocType(doc_type);
        Field[] fds=doc.getFields();
        switch(doc_type)
        {
             //会员招募
            case DocType.MEMBER_RECRUITEMENT:
              sp="{?=call member.F_MEMBER_RECRUITMENTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;
            //增加会员促销数据 
            case DocType.MEMBER_PROMOTION:
              sp="{?=call member.F_PRD_PRICELIST_ADD(?,?,?,?,?,?,?,?)}";
              break;
            //会员付款事件
            case DocType.MEMBER_DEPOSITS:
              sp="{?=call member.F_MEMBER_DEPOSITS(?,?,?,?,?,?,?,?)}";
              break;
            //增加会员升级事件
            case DocType.MEMBER_UPGRADE:
              sp="{?=call member.F_MEMBER_UPGRADE(?,?,?,?,?,?)}";
              break;
            //增加会员卡挂失事件
            case DocType.MEMBER_CARD:
              sp="{?=call member.F_MEMBER_CARD(?,?,?,?,?,?,?,?)}";
              break;
            //会员退会
            case DocType.MEMBER_CANCEL:
              sp="{?=call member.F_MEMBER_CANCEL(?,?,?,?,?)}";
              break;
            //增加会员询问事件
            case DocType.MEMBER_INQUIRY:
              sp="{?=call member.F_MEMBER_INQUIRY(?,?,?,?,?,?,?)}";
              break;
           
            //增加团体会员 
            case DocType.MEMBER_ORGANIZATION:
              sp="{?=call member.F_ORGANIZATION(?,?,?,?,?,?,?,?)}";
              break;
             //增加会员地址
            case DocType.MEMBER_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_ADD(?,?,?,?,?)}";
              break;
            //公司地址 
            case DocType.ORG_LOCATION:
              sp="{?=call ORGANIZATION.F_org_location(?,?,?,?,?,?,?,?,?,?)}";
              break;
            //公司部门类型 
            case DocType.DEPARTMENT_TYPE:
              sp="{?=call ORGANIZATION.F_department_type(?,?,?)}";
              break;
            //公司部门
            case DocType.ORG_DEPARTMENT:
              sp="{?=call ORGANIZATION.F_org_department(?,?,?,?,?,?)}";
              break;
            //人员类型 
            case DocType.EMPLOYEE_TYPE:
              sp="{?=call ORGANIZATION.f_employee_type(?,?,?)}";
              break;
            //公司人员 
            case DocType.ORG_PERSON:
              sp="{?=call ORGANIZATION.F_org_person(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;
            //角色信息 
            case DocType.ORG_ROLES:
              sp="{?=call ORGANIZATION.f_org_roles_add(?,?,?)}";
              break;
           //增加产品              
            case DocType.PRODUCT:
              sp="{?=call product.f_product_add(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;
			     //目录版块
            case DocType.CATALOG_EDITION:
              sp="{?=call catalog.f_catalog_edition_add(?,?)}";
              break;
            //会员组
            case DocType.MEMBER_GROUP:
              sp="{?=call MEMBER.F_MEMBER_GROUP_ADD(?,?,?,?)}";
              break;
             //会员类别
  	        case DocType.MEMBER_CATEGORIES: 
  	    	    sp="{?=call CONFIG.f_member_categories_add(?,?,?)}";
              break;
             //会员招募渠道类型
  	        case DocType.RECRUITEMENT_CATEGORY: 
  	    	    sp="{?=call CONFIG.f_recruitment_category_add(?,?)}";
              break;
            //会员招募渠道详细类型
  	        case DocType.RECRUITEMENT_TYPE: 
  	    	    sp="{?=call CONFIG.f_recruitment_type_add(?,?,?)}";
              break;
             //会员询问事件类型
  	        case DocType.INQUIRY_TYPE: 
  	    	    sp="{?=call CONFIG.f_inquiry_type_add(?,?,?)}";
              break;
            //会员投诉等级
  	        case DocType.INQUIRY_LEVEL: 
  	    	    sp="{?=call CONFIG.f_inquiry_level_add(?,?,?)}";
              break;
            //会员不良行为类型
  	        case DocType.MALPRACTICE_TYPE: 
  	    	    sp="{?=call CONFIG.f_malpractice_type_add(?,?)}";
              break;
            //订单发送渠道
  	        case DocType.DELIVERY_TYPE: 
  	    	    sp="{?=call CONFIG.f_delivery_type_add(?,?,?)}";
              break;
            //产品类型
  	        case DocType.ITEM_TYPE: 
  	    	    sp="{?=call CONFIG.f_item_type_add(?,?)}";
              break;
            //产品税率
  	        case DocType.ITEM_TAX: 
  	    	    sp="{?=call CONFIG.f_item_tax_add(?,?,?,?)}";
              break;                  
            //供应商送货方式
  	        case DocType.SUPPLIER_DELIVERY_TYPE: 
  	    	    sp="{?=call CONFIG.f_supplier_delivery_type_add(?,?)}";
              break;
            //会员免义务条件
  	        case DocType.MBR_FREE_COMMITMENT: 
  	    	    sp="{?=call CONFIG.f_mbr_free_commitment_add(?,?,?)}";
              break;
            //会员e元获得比例
  	        case DocType.MBR_EMONEY_RATES: 
  	    	    sp="{?=call CONFIG.f_mbr_emoney_rates_add(?,?,?,?)}";
              break; 
						//增加价目表行
				    case DocType.PRICELIST_LINE:
					    sp= "{?=call catalog.F_PRICELIST_LINES_ADD(?,?,?,?,?,?,?)}";
				      break; 
						//增加目录表行
				    case DocType.CATALOG_LINE:
					    sp= "{?=call catalog.F_CATALOG_LINES_ADD(?,?,?,?,?,?,?,?,?)}";
				      break; 
            //增加礼品组行
  	        case DocType.PRICELIST_GIFT: 
  	    	    sp="{?=call CATALOG.F_PRICELIST_LINE_GIFT_ADD(?,?,?,?,?,?,?)}";
              break;  
            //增加免送货费
  	        case DocType.FREE_DELIVERY: 
  	    	    sp="{?=call CATALOG.F_PRD_FREE_DELIVERY_ADD(?,?,?)}";
              break; 
            //增加价目表
            case DocType.PRICELIST:
              sp="{?=call catalog.f_pricelist_add(?,?,?,?,?,?,?)}";
              break;
            //增加目录
            case DocType.CATALOG:
              sp="{?=call catalog.f_catalog_add(?,?,?,?,?,?,?,?,?)}";
              break;
            //增加文档模版
            case DocType.DOCUMENT_TEMPLATE:
              sp="{?=call catalog.f_document_template_add(?,?,?,?)}";
              break;         
             //增加产品类别
  	        case DocType.ITEM_CATEGORY: 
  	    	    sp="{?=call PRODUCT.F_ITEM_CATEGORY_ADD(?,?,?,?,?,?)}";
              break;    
            //增加会员退款事件
            case DocType.MEMBER_DRAWBACK:
              sp="{?=call member.F_MEMBER_DRAWBACK(?,?,?,?,?,?)}";
              break; 
            //增加介绍赠品
            case DocType.GET_MBR_GIFT:
              sp="{?=call product.f_get_mbr_gifts_add(?,?,?,?)}";
              break;
            //增加套件产品明细
            case DocType.SET_PRODUCTS:
              sp="{?=call product.F_SET_ITEM_LINE_ADD(?,?,?,?,?)}";
              break;
             //增加团体会员地址
            case DocType.ORGANIZATION_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_ADD(?,?,?,?,?)}";
              break;
            case DocType.MBR_GIFTS:
              sp="{?=call CATALOG.F_MBR_GIFTS_ADD(?,?,?,?,?)}";
              break;
            //增加促销价目表行
				    case DocType.PRICELIST_PROMOTION_LINE:
					    sp= "{?=call catalog.f_promotion_lines_add(?,?,?,?,?)}";
				      break;
            //增加促销礼品组
				    case DocType.PROMOTION_PRICELIST_GIFT:
				      sp= "{?=call catalog.f_promotion_line_gift_add(?,?,?,?,?,?)}";
				      break; 
            //增加服务配置
	          case DocType.SERVICE_CONFIG:
              sp="{?=call CONFIG.F_BAS_JOBS_ADD(?,?,?,?,?,?,?,?,?,?)}";
              break;
            //增加邮编地址
	          case DocType.MBR_POSTCODE:
              sp="{?=call CONFIG.F_POSTCODE_ADD(?,?,?,?)}";
              break;
            //增加礼券
	          case DocType.MBR_GIFT_CERTIFICATE:
              sp="{?=call member.f_member_gift_certificate_add(?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;  
            //增加产品单位
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
                      //System.out.println("参数名称:"+name);
                      //System.out.println("参数值："+request.getParameter(name));
                      //System.out.println("参数类型:"+fds[i].getDataType()+"\n");
                  
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
                                       message("未完成","字段\""+fds[i].getCaption()+"\"没有输入有效的代码.",request, response);
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
                            message("未完成","必要的字段\""+fds[i].getCaption()+"\"没有输入",request, response);
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
                              message("未完成","字段\""+fds[i].getCaption()+"\"不是有效的日期类型.",request, response);
                              return;
                          }
                          cstmt.setString(para_index,value);   
                      }
                      if(fds[i].getDataType().equals("INTEGER"))
                      {
                          if(!StringUtil.isNum(para_value) &&!para_value.equals(""))
                          {
                              message("未完成","字段\""+fds[i].getCaption()+"\"不是整型.",request, response);
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
                              message("未完成","字段\""+fds[i].getCaption()+"\"不是数字型.",request, response);
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
                //System.out.println("参数名称:父文档ID");
                //System.out.println("参数值："+Integer.parseInt(request.getParameter("parent_doc_id")));
                //System.out.println("参数类型:INTEGER");
            }          
            if(!doc.is_global())  
            {
                para_index++;
                cstmt.setInt(para_index,sessionInfo.getCompanyID()); 
                //System.out.println("参数名称:俱乐部ID");
                //System.out.println("参数值："+sessionInfo.getCompanyID());
                //System.out.println("参数类型:INTEGER");
            }
           
            if(doc.isLogOperator())
            {
                 para_index++;
                cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
                //System.out.println("参数名称:操作员ID");
                //System.out.println("参数值："+sessionInfo.getOperatorID());
                //System.out.println("参数类型:INTEGER");
            }

           
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getInt(1);           
            if(re<0)
            {
                   KException ke=new KException(re);
                   message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
            }
            else
            {
                Log log=new Log();
                log.audit(sessionInfo.getCompanyID(),sessionInfo.getOperatorID(),doc_type,log.EVENT_ADD,re);
                if(doc_type==doc.CATALOG||doc_type==doc.PRICELIST||doc_type==doc.MEMBER_PROMOTION)
                {
                    success("完成","已成功增加促销记录，可以继续<a href=\"../app/viewdetail?doc_type="+doc_type+"&doc_id="+re+"\">设置此促销的具体内容</a>.",request, response);
                   return;
                }
                
                if(doc_type==doc.MEMBER_LOSECARD)
                {
                   success("完成","已成功会员卡挂失处理，旧卡号已不能使用，新卡号开始生效。<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER+"&doc_id="+re+"\">查看新会员卡号信息</a>.",request, response);
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
						 success("完成","已增加产品信息，产品编码为<a href=\"../app/viewdetail?doc_type=1500&doc_id="+re+"\">"+item_code+"</a>",request, response);
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
						 success("完成","已增加会员信息，会员号码为<a href=\"../app/viewdetail?doc_type=1000&doc_id="+re+"\">"+card_id+"</a>，会员成为普通会员必须输入<a href=\"/member_order_add.jsp?doc_type=4000&card_id_key="+card_id+"\">入会订单</a>",request, response);
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
                  success("完成","完成新增记录操作.<br><a href=\"../app/viewdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }

								if(doc_type==doc.CATALOG_LINE || doc_type==doc.PRICELIST_GIFT || doc_type == doc.FREE_DELIVERY || doc_type==doc.PROMOTION_PRICELIST_GIFT)
                {
                  success("完成","完成新增记录操作.<br><a href=\"../app/viewdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }
                if(doc_type==doc.MEMBER_ADDRESS )
                {
                  success("完成","完成新增记录操作.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }
                if(doc_type==doc.ORGANIZATION_ADDRESS )
                {
                  success("完成","完成新增记录操作.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER_ORGANIZATION+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }								
								if(doc_type==doc.SET_PRODUCTS){
                  success("完成","完成新增记录操作.<br><a href=\"../app/viewdetail?doc_type=1500&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
								}
								if(doc_type==doc.MBR_GIFTS){
                  success("完成","完成新增记录操作.<br><a href=\"../app/viewdetail?doc_type="+DocType.CATALOG+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
								}
                if(doc_type==doc.ITEM_CATEGORY){
                 success("完成","完成记录增加操作.<br><a href=\"../app/viewconfig?doc_type="+doc.ITEM_CATEGORY + "&t_code=4300\">返回.</a>",request, response);
                   return;                
                }  
                if(doc_type==doc.MBR_POSTCODE){
                 success("完成","完成记录增加操作.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_POSTCODE + "&t_code=9224\">返回.</a>",request, response);
                   return;                
                }  
                if(doc_type==doc.MBR_GIFT_CERTIFICATE){
                 success("完成","完成记录增加操作.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_GIFT_CERTIFICATE + "&t_code=22600\">返回.</a>",request, response);
                   return;                
                }   
                if(doc_type==doc.PRD_UOM){
                 success("完成","完成记录增加操作.<br><a href=\"../app/viewconfig?doc_type="+doc.PRD_UOM + "&t_code=9443\">返回.</a>",request, response);
                   return;                
                }                 
                if(doc_type>10000)
                  success("完成","完成记录增加操作.<br>[<a href=\"../app/viewconfig?doc_type="+doc_type+"\">返回.</a>]",request, response);
                else
                  success("完成","完成记录增加操作.",request, response);
                conn.close();
            }
        }catch(Exception e)
        {
          e.printStackTrace();
          message("未完成",e.getMessage(),request, response);
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