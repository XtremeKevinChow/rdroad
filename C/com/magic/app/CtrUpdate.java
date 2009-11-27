package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * 系统处理修改时Servlet
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class CtrUpdate extends BaseServlet 
{
  public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        super.service(request,response);
        if(!assertSession(request))
        {
           response.sendRedirect("/relogin.html");
           return;
        }
      
       request.setCharacterEncoding("GBK");
       HttpSession session=request.getSession();
          SessionInfo sessionInfo = new SessionInfo(
            ((Integer)session.getAttribute("login_company_id")).intValue(), 
            ((Integer)session.getAttribute("login_operator_id")).intValue(), 
            (java.util.HashMap)session.getAttribute("powermap"),
            request.getParameterMap());
            
            
       int doc_type=0;
       int doc_id=0;
       String sp=null;
       super.service(request, response);
       try{
            doc_type=Integer.parseInt(request.getParameter("doc_type"));
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
        }catch(Exception e)
        {
             e.printStackTrace();
             message("未完成",e.getMessage(),request, response);
             return;
        }
				
     
        if(doc_type==1501){
          doc_type = 1500;
        }
        DocType doc=new DocType(doc_type);
        Field[] fds=doc.getFields();
        switch(doc_type){
               //修改会员类别
  	      case DocType.MEMBER_CATEGORIES:
  	    	    sp="{?=call CONFIG.f_member_categories_update(?,?,?,?)}";
              break;
            //修改会员招募渠道类型
  	      case DocType.RECRUITEMENT_CATEGORY:
  	    	    sp="{?=call CONFIG.f_recruitment_category_update(?,?,?)}";
              break;
            //修改会员招募渠道详细类型
  	      case DocType.RECRUITEMENT_TYPE:
  	    	    sp="{?=call CONFIG.f_recruitment_type_update(?,?,?,?)}";
              break;
            //修改会员询问事件类型
  	      case DocType.INQUIRY_TYPE:
  	    	    sp="{?=call CONFIG.f_inquiry_type_update(?,?,?,?)}";
              break;
             //修改会员投诉等级
  	      case DocType.INQUIRY_LEVEL: 
  	    	    sp="{?=call CONFIG.f_inquiry_level_update(?,?,?,?)}";
              break;
            //修改会员不良行为类型
  	      case DocType.MALPRACTICE_TYPE:
  	    	    sp="{?=call CONFIG.f_malpractice_type_update(?,?,?)}";
              break;
            //修改订单发送渠道
  	      case DocType.DELIVERY_TYPE:
  	    	    sp="{?=call CONFIG.f_delivery_type_update(?,?,?,?)}";
              break;
            //修改目录版块
  	      case DocType.CATALOG_EDITION:
  	    	    sp="{?=call CONFIG.f_catalog_edtion_update(?,?,?)}";
              break;
            //修改产品类型
  	      case DocType.ITEM_TYPE:
  	    	    sp="{?=call CONFIG.f_item_type_update(?,?,?)}";
              break;
            //修改产品税率
  	      case DocType.ITEM_TAX:
  	    	    sp="{?=call CONFIG.f_item_tax_update(?,?,?,?,?)}";
              break;                  
            //修改供应商送货方式
  	      case DocType.SUPPLIER_DELIVERY_TYPE:
  	    	    sp="{?=call CONFIG.f_supply_delivery_type_update(?,?,?)}";
              break;
            //修改会员免义务条件
  	      case DocType.MBR_FREE_COMMITMENT:
  	    	    sp="{?=call CONFIG.f_mbr_free_commitment_update(?,?,?,?)}";
              break;
            //修改会员e元获得比例
  	      case DocType.MBR_EMONEY_RATES:
  	    	    sp="{?=call CONFIG.f_mbr_emoney_rates_update(?,?,?,?,?)}";
              break;
                  
            //修改公司地址 
          case DocType.ORG_LOCATION:
              sp="{?=call ORGANIZATION.f_org_location_update(?,?,?,?,?,?,?,?,?,?,?)}";
              break;
            //修改公司部门类型 
          case DocType.DEPARTMENT_TYPE:
              sp="{?=call ORGANIZATION.f_department_type_update(?,?,?,?)}";
              break;
            //修改公司部门
          case DocType.ORG_DEPARTMENT:
              sp="{?=call ORGANIZATION.f_org_department_update(?,?,?,?,?,?,?)}";
              break;
            //修改人员类型 
          case DocType.EMPLOYEE_TYPE:
              sp="{?=call ORGANIZATION.f_employee_type_update(?,?,?,?)}";
              break;
            //修改公司人员 
          case DocType.ORG_PERSON:
              sp="{?=call ORGANIZATION.f_org_person_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;
            //修改角色信息 
          case DocType.ORG_ROLES:
              sp="{?=call ORGANIZATION.f_org_roles_update(?,?,?,?)}";
              break;
          case DocType.PRICELIST_LINE: 
  	    	    sp="{?=call catalog.f_pricelist_lines_update(?,?,?,?,?,?,?)}";
              break;
            //修改产品数据
          case DocType.PRODUCT:
              sp="{?=call product.f_product_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;
				   //目录修改
				  case DocType.CATALOG_LINE: 
					    sp="{?=call catalog.F_CATALOG_LINES_UPDATE(?,?,?,?,?,?,?,?,?)}";
              break;
          //修改礼品组行
  	      case DocType.PRICELIST_GIFT: 
  	    	    sp="{?=call CATALOG.F_PRICELIST_LINE_GIFT_UPDATE(?,?,?,?,?,?,?)}";
              break; 
            //修改免送货费
  	      case DocType.FREE_DELIVERY: 
  	    	    sp="{?=call CATALOG.F_PRD_FREE_DELIVERY_UPDATE(?,?,?)}";
              break;    
             //修改文档模版
          case DocType.DOCUMENT_TEMPLATE:
              sp="{?=call catalog.F_DOCUMENT_TEMPLATE_UPDATE(?,?,?,?,?)}";
              break;
          ///修改会员基本信息
          case DocType.MEMBER:
              sp="{?=call member.F_MEMBER_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;  
            
          //修改会员付款事件
          case DocType.MEMBER_DEPOSITS:
              sp="{?=call member.F_MEMBER_DEPOSITS_UPDATE(?,?,?,?,?,?,?)}";
              break;
          //修改会员升级事件
          case DocType.MEMBER_UPGRADE:
              sp="{?=call member.F_MEMBER_UPGRADE_UPDATE(?,?,?,?,?,?)}";
              break;
            //修改会员卡挂失事件
          case DocType.MEMBER_CARD:
              sp="{?=call member.F_MEMBER_CARD_UPDATE(?,?,?,?,?,?,?,?)}";
              break;
          //修改会员退会
          case DocType.MEMBER_CANCEL:
              sp="{?=call member.F_MEMBER_CANCEL_UPDATE(?,?,?,?,?)}";
              break;
          //修改会员询问事件
          case DocType.MEMBER_INQUIRY:
              sp="{?=call member.F_MEMBER_INQUIRY_UPDATE(?,?,?,?,?,?,?)}";
              break;              
          //修改团体会员 
          case DocType.MEMBER_ORGANIZATION:
              sp="{?=call member.F_ORGANIZATION_UPDATE(?,?,?,?,?,?,?,?)}";
              break;
          //修改会员地址
          case DocType.MEMBER_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_UPDATE(?,?,?,?,?)}";
              break;
          //增加产品类别
  	      case DocType.ITEM_CATEGORY: 
  	    	    sp="{?=call PRODUCT.F_ITEM_CATEGORY_UPDATE(?,?,?,?,?,?,?)}";
              break; 
          //修改会员退款事件
          case DocType.MEMBER_DRAWBACK:
              sp="{?=call member.F_MEMBER_DRAWBACK_UPDATE(?,?,?,?,?,?)}";
              break;  
          //修改会员等级透支金额
          case DocType.MEMBER_LEVEL:
              sp="{?=call config.F_MEMBER_LEVEL_UPDATE(?,?,?,?)}";
              break;
          //修改套件产品明细
          case DocType.SET_PRODUCTS:
              sp="{?=call product.F_SET_ITEM_LINE_UPDATE(?,?,?,?,?,?)}";
              break;
          //修改团体会员地址
          case DocType.ORGANIZATION_ADDRESS:
              sp="{?=call member.F_MEMBER_ADDRESS_UPDATE(?,?,?,?,?)}";
              break;
          //修改服务配置
	        case DocType.SERVICE_CONFIG:
              sp="{?=call CONFIG.F_BAS_JOBS_UPDATE(?,?,?,?,?,?,?,?,?,?,?)}";
              break;         
          case DocType.PERSON_PWD_UPDATE:
              sp="{?=call ORGANIZATION.F_ORG_PERSON_PWD_UPDATE(?,?)}";
              break;
            //促销价目表行
          case DocType.PRICELIST_PROMOTION_LINE:
              sp="{?=call catalog.f_promotion_lines_update(?,?,?,?,?)}";
              break;
            //促销礼品行
          case DocType.PROMOTION_PRICELIST_GIFT:
              sp="{?=call catalog.f_promotion_line_gift_update(?,?,?,?,?,?)}";
              break;
          //修改邮编地址
          case DocType.MBR_POSTCODE:
              sp="{?=call CONFIG.F_POSTCODE_UPDATE(?,?,?,?,?)}";
              break;
          //修改礼券
	        case DocType.MBR_GIFT_CERTIFICATE:
              sp="{?=call member.f_mbr_gift_certificate_update(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              break;   
          //修改产品单位
	        case DocType.PRD_UOM:
              sp="{?=call config.f_item_uom_update(?,?,?)}";
              break;
          //修改促销活动
	        case DocType.MEMBER_PROMOTION:
              sp="{?=call member.f_prd_pricelist_update(?,?,?,?,?,?,?,?,?)}";
              break;   
          //修改目录活动
	        case DocType.CATALOG:
              sp="{?=call catalog.F_CATALOG_UPDATE(?,?,?,?,?,?,?,?,?,?)}";
              break;                 
          //修改单页活动
	        case DocType.PRICELIST:
              sp="{?=call catalog.F_PRICELIST_UPDATE(?,?,?,?,?,?,?,?)}";
              break;  
          //修改套装产品主档
          case DocType.SETITEM:
            sp="{?=call product.f_setitem_update(?,?,?,?,?,?,?,?,?,?,?,?)}";
            break;

		  //修改单页活动
	      case DocType.MBR_GIFTS:
            sp="{?=call catalog.F_MBR_GIFTS_UPDATE(?,?,?,?,?)}";
            break; 
             //会员招募
            //case DocType.MEMBER_RECRUITEMENT:
              //sp="{?=call member.F_MEMBER_RECRUITMENTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
           // break;
        }
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        CallableStatement cstmt =null;
        try{
             int para_index=2;
             cstmt = dblink.prepareCall(sp); 
             cstmt.setInt(para_index,doc_id); 
             System.out.println("参数名称:doc_id");
             System.out.println("参数值："+doc_id);
             System.out.println("参数类型:INTEGER"); 
             System.out.println(fds.length);
             for(int i=0;i<fds.length;i++)
             {     
                  if(fds[i].isUpdate())
                  {	
                      para_index++;
                      String name=fds[i].getName();
                      System.out.println("参数名称:"+name);
                      System.out.println("参数值："+request.getParameter(name));
                      System.out.println("参数类型:"+fds[i].getDataType()+"\n");
                      if(fds[i].isRequired()&&fds[i].isUpdate())
                      {
                          if(request.getParameter(name)==null || request.getParameter(name).trim().equals(""))
                          {
                            message("未完成","必要的字段"+fds[i].getCaption()+"没有输入",request, response);
                            return;
                          }
                      }
                      String para_value=StringUtil.cEmpty(request.getParameter(name)).trim();
                      if(fds[i].getInputType().equals("KEYSET"))
                      {
                          String key_value=StringUtil.cEmpty(request.getParameter(name+"_key"));
                          para_value="";
                           if(!key_value.equals(""))
                          {
                          Lookup lu=LookupMap.getLookup(fds[i].getLookup());
                          Statement stmt_temp=null;
                          ResultSet rs_temp=null;
                        try
                        {
                            stmt_temp=dblink.createStatement();
                            rs_temp= stmt_temp.executeQuery("select "+lu.getValueField()+" from "+lu.getDataSource()+" where "+lu.getKeyField()+"='"+key_value+"'");
                            if(rs_temp.next())
                              para_value = rs_temp.getString(1);
                            else
                            {
                               rs_temp.close();
                               String old_value=StringUtil.cEmpty(request.getParameter(name));
                               if(!old_value.equals(""))
                               {
                                  rs_temp= stmt_temp.executeQuery("select "+lu.getValueField()+" from "+lu.getDataSource()+" where "+lu.getValueField()+"='"+old_value+"'");
                                  if(!rs_temp.next())
                                     para_value=old_value;
                                  else
                                  {
                                    message("未完成","字段\""+fds[i].getCaption()+"\"没有输入有效的代码.",request, response);
                                    return;
                                  }
                               }
                               else
                               {
                                 message("未完成","字段\""+fds[i].getCaption()+"\"没有输入有效的代码.",request, response);
                                 return;
                               }
                            }
                        }catch(Exception e)
                        {
                          e.printStackTrace();
                        }finally
                        {
                          try
                          {
                            if(rs_temp!=null) rs_temp.close();
                            if(stmt_temp!=null) stmt_temp.close();
                          }catch(Exception e)
                          {
                            e.printStackTrace();
                          }
                        }
                          }
                      } 
                      
                      if(fds[i].getDataType().equals("CHAR"))
                      {
                          //String value=request.getParameter(name).trim();
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
                          else
                            if(fds[i].getInputType().equals("TEXT")) 
                              value=-1;
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
                          String value=request.getParameter(name);
                        //  clob.putString(0,value);
                        //  clob.close();
                         // cstmt.setObject(para_index,clob);   
                         cstmt.setString(para_index,value); 
                      }      
                  } 
            }
             if(doc_type==doc.SET_PRODUCTS)
            {
                para_index++;
                cstmt.setInt(para_index,Integer.parseInt(request.getParameter("parent_doc_id"))); 
                System.out.println("参数名称:父文档ID");
                System.out.println("参数值："+Integer.parseInt(request.getParameter("parent_doc_id")));
                System.out.println("参数类型:INTEGER");
            }  
            if(!doc.is_global())
            {
                para_index++;
                cstmt.setInt(para_index,sessionInfo.getCompanyID()); 
                System.out.println("参数名称:俱乐部ID");
                System.out.println("参数值："+sessionInfo.getCompanyID());
                System.out.println("参数类型:INTEGER");
            }
            if(doc.isLogOperator())
            {
                 para_index++;
                cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
                System.out.println("参数名称:操作员ID");
                System.out.println("参数值："+sessionInfo.getOperatorID());
                System.out.println("参数类型:INTEGER");
            }
            System.out.println("参数个数" + para_index++);
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getShort(1);
            cstmt.close();
            if(re<0)
            {
                   KException ke=new KException(re);
                   message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
            }
            else
            {

              Log log=new Log();
              log.audit(sessionInfo.getCompanyID(),sessionInfo.getOperatorID(),doc_type,log.EVENT_UPDATE,doc_id);
								
                if(doc_type==doc.MEMBER_ADDRESS )
                {
                  success("完成","完成修改记录操作.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }

                if(doc_type==doc.ORGANIZATION_ADDRESS )
                {
                  success("完成","完成修改记录操作.<br><a href=\"../app/viewdetail?doc_type="+DocType.MEMBER_ORGANIZATION+"&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }
               	if( doc_type==doc.PRICELIST_LINE||doc_type==doc.CATALOG_LINE || doc_type==doc.PRICELIST_GIFT || 
									  doc_type == doc.FREE_DELIVERY || doc_type == doc.PRICELIST_PROMOTION_LINE || doc_type==doc.PROMOTION_PRICELIST_GIFT)
                {
                  success("完成","完成修改记录操作.<br><a href=\"../app/viewdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
                }
								if(doc_type==doc.SET_PRODUCTS){
                  success("完成","完成修改记录操作.<br><a href=\"../app/viewdetail?doc_type=1500&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                   return;
								}
								if(doc_type==doc.MEMBER_ORGANIZATION){
                  success("完成","完成修改记录操作.<br><a href=\"../app/viewdetail?doc_type=1710&doc_id="+Integer.parseInt(request.getParameter("doc_id"))+"\">返回.</a>",request, response);
                   return;
								}
                if(doc_type==doc.PERSON_PWD_UPDATE){
                 success("完成","完成更改记录操作.<br><a href=\"../app/viewdetail?doc_type="+doc.ORG_PERSON+"&doc_id="+re+"\">返回.</a>",request, response);
                   return;
								}
                if(doc_type==doc.ITEM_CATEGORY){
                 success("完成","完成更改记录操作.<br><a href=\"../app/viewconfig?doc_type="+doc.ITEM_CATEGORY + "&t_code=4300\">返回.</a>",request, response);
                   return;                
                }
                if(doc_type==doc.MBR_POSTCODE){
                 success("完成","完成更改记录操作.<br><a href=\"../app/viewlist?doc_type="+doc.MBR_POSTCODE + "&t_code=9224\">返回.</a>",request, response);
                   return;                
                }
                if(doc_type==doc.MEMBER){
                 success("完成","完成更改记录操作.<br><a href=\"../app/viewdetail?doc_type=1000&doc_id="+doc_id+"\">返回.</a>",request, response);
                   return;                                
                }
                if(doc_type==doc.PRODUCT || doc_type==doc.SETITEM){
                 //success("完成","完成更改记录操作.<br><a href=\"/app/viewdetail?doc_type="+ doc.PRODUCT +"&doc_id="+doc_id+"\">返回.</a>");
                 response.sendRedirect("../app/viewdetail?doc_type="+ doc.PRODUCT +"&doc_id="+doc_id);
                 return;                                                
                }
                if(doc_type==doc.MBR_GIFT_CERTIFICATE){
                 success("完成","完成更改记录操作.<br><a href=\"../app/viewdetail?doc_type="+ doc_type +"&doc_id="+doc_id+"\">返回.</a>",request, response);
                   return;                                                
                }   
                if(doc_type==doc.PRD_UOM){
                 success("完成","完成更改记录操作.<br><a href=\"../app/viewconfig?doc_type="+ doc_type +"&doc_id="+doc_id+"\">返回.</a>",request, response);
                   return;                                                
                }
                if(doc_type==doc.MEMBER_PROMOTION || doc_type==doc.PRICELIST || doc_type==doc.CATALOG){
                 success("完成","完成更改记录操作.<br><a href=\"../app/viewdetail?doc_type="+ doc_type +"&doc_id="+doc_id+"\">返回.</a>",request, response);
                   return;                                                
                }                  
              if(doc_type>10000)
                success("完成","完成记录更改操作.<br><a href=\"../app/viewconfig?doc_type="+doc_type+"\">返回.</a>",request, response); 
              else
                success("完成","完成记录更改操作.",request, response); 
              }
        }catch(Exception e)
        {
          System.out.println(e);
          message("未完成",e.getMessage(),request, response);
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