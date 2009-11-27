package com.magic.app;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.magic.utils.*;
/**
 * 新增界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
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
            message("未完成",e.getMessage(),request, response);
            
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
           
          //会员招募
          case DocType.MEMBER:
              response.sendRedirect("../crmjsp/member_new.jsp?doc_type="+DocType.MEMBER_RECRUITEMENT);
              break;

          case DocType.MEMBER_GROUP:
              response.sendRedirect("../app/memberquery");
              break;
					//供应商新增
					case DocType.PROVIDERS:
						   response.sendRedirect("../crmjsp/providers_edit.jsp?doc_type="+doc_type);
					     break;
					//会员付款
					case DocType.MEMBER_DEPOSITS:
						   response.sendRedirect("../crmjsp/member_deposits.jsp?doc_type="+doc_type + "&member_id_key="+StringUtil.cEmpty(request.getParameter("member_id_key")));
					     break;

					//套装配置
					case DocType.SETITEM:
						   response.sendRedirect("../crmjsp/set_item.jsp?doc_type="+doc_type);
					     break;
					//会员卡挂失
					case DocType.MEMBER_CARD:
						   response.sendRedirect("../crmjsp/member_card_loss.jsp?doc_type="+doc_type + "&old_card_id=" + StringUtil.cEmpty(request.getParameter("member_id_key")));
					     break;
					//会员退款
					case DocType.MEMBER_DRAWBACK:
						   response.sendRedirect("../crmjsp/member_drawback.jsp?doc_type="+doc_type);
					     break;
					//新增团体订单
					case DocType.GROUP_ORDER:
						   response.sendRedirect("../crmjsp/member_group_order_add.jsp?doc_type="+doc_type);
					     break;
					//汇款导入
					case 5080:   //要修改
						   response.sendRedirect("../crmjsp/member_deposits_upload.jsp?doc_type="+doc_type);
					     break;
          //信件模板内容
          case DocType.DOCUMENT_TEMPLATE:
               response.sendRedirect("../crmjsp/document_template.jsp?doc_type="+doc_type + "&act=addSave");
               break;
          //会员询问
					case DocType.MBR_INQUIRY:
						   response.sendRedirect("../crmjsp/member_inquiry.jsp?doc_type="+doc_type + "&member_id_key="+StringUtil.cEmpty(request.getParameter("member_id_key")));
					     break;
          //报表
          //产品销售明细表
					case DocType.PDF_SELL_ALL:
						   response.sendRedirect("../crmjsp/pdf_prd_sell_all.jsp?doc_type="+doc_type);
					     break;

          //产品类型分析报表
					case DocType.PDF_SELL_TYPE:
						   response.sendRedirect("../crmjsp/pdf_prd_type_sell_analyze.jsp?doc_type="+doc_type);
					     break;
          //根据订单来源分析
					case DocType.PDF_SELL_PR:
						   response.sendRedirect("../crmjsp/pdf_prd_pr_type_analyze.jsp?doc_type="+doc_type);
					     break;
          //根据采购编辑分析
					case DocType.PDF_SELL_OWENER:
						   response.sendRedirect("../crmjsp/pdf_prd_owener_analyze.jsp?doc_type="+doc_type);
					     break;
          //根据产品类别分析
					case DocType.PDF_SELL_CATELOG:
						   response.sendRedirect("../crmjsp/pdf_prd_catelog_analyze.jsp?doc_type="+doc_type);
					     break;
          //根据产品成本价分析
					case DocType.PDF_SELL_MONEY:
						   response.sendRedirect("../crmjsp/pdf_prd_money.jsp?doc_type="+doc_type);
					     break;
          //根据供应商分析
					case DocType.PDF_SELL_PROVIDER:
						   response.sendRedirect("../crmjsp/pdf_prd_provider_analyze.jsp?doc_type="+doc_type);
					     break;
          //根据销售渠道分析
					case DocType.PDF_SELL_DELIVERY:
						   response.sendRedirect("../crmjsp/pdf_prd_delivery_analyze.jsp?doc_type="+doc_type);
					     break;
          //每日订单
					case DocType.PDF_ORDER_EVERYDAY:
						   response.sendRedirect("../crmjsp/pdf_ord_every_days.jsp?doc_type="+doc_type);
					     break;
          //每日产品扣单表
					case DocType.PDF_ORDER_WAITING:
						   response.sendRedirect("../crmjsp/pdf_order_waiting.jsp?doc_type="+doc_type);
					     break;
          //扣单分析表
					case DocType.PDF_ORDER_SHORTAGE:
						   response.sendRedirect("../crmjsp/pdf_order_shortage_goods.jsp?doc_type="+doc_type);
					     break;
           //订单分析表
					case DocType.PDF_ORDER_ANALYZE:
						   response.sendRedirect("../crmjsp/pdf_order_analyze.jsp?doc_type="+doc_type);
					     break;
          //订单地域分析表
					case DocType.PDF_ORDER_ZONE:
						   response.sendRedirect("../crmjsp/pdf_order_zone.jsp?doc_type="+doc_type);
					     break;
          //订单年龄分析表
					case DocType.PDF_ORDER_AGE:
						   response.sendRedirect("../crmjsp/pdf_order_age.jsp?doc_type="+doc_type);
					     break;
           //产品日后台销售报表
					case DocType.PDF_SELL_ANALYZE:
						   response.sendRedirect("../crmjsp/pdf_prd_sell_analyze.jsp?doc_type="+doc_type);
					     break;
          //产品日前台销售报表
					case DocType.PDF_SELL_ANALYZE_FOREGROUND:
						   response.sendRedirect("../crmjsp/pdf_prd_sell_analyze_foreground.jsp?doc_type="+doc_type);
                         break;
          //会员购书总体分布
					case DocType.PDF_CUSTOM_LEVEL:
						   response.sendRedirect("../crmjsp/pdf_ord_level.jsp?doc_type="+doc_type);
					     break;
          //会员购书金额排行
					case DocType.PDF_CUSTOM_MONEY:
						   response.sendRedirect("../crmjsp/pdf_ord_money.jsp?doc_type="+doc_type);
					     break;
          //购书会员列表
					case DocType.PDF_CUSTOM_LIST:
						   response.sendRedirect("../crmjsp/pdf_prd_custom_lists.jsp?doc_type="+doc_type);
					     break;
          //会员销售地域分布
					case DocType.PDF_CUSTOM_POSTCODE:
						   response.sendRedirect("../crmjsp/pdf_ord_postcode.jsp?doc_type="+doc_type);
					     break;
          //按照地域分析
					case DocType.PDF_CUSTOM_CITY:
						   response.sendRedirect("../crmjsp/pdf_mbr_city.jsp?doc_type="+doc_type);
					     break;
          //会员销售年龄分布
					case DocType.PDF_CUSTOM_AGE:
						   response.sendRedirect("../crmjsp/pdf_ord_age.jsp?doc_type="+doc_type);
					     break;
          //招募渠道分析
					case DocType.PDF_CUSTOM_MSC:
						   response.sendRedirect("../crmjsp/pdf_mbr_msc.jsp?doc_type="+doc_type);
					     break;
          //按照年龄和招募渠道分析
					case DocType.PDF_CUSTOM_MSC_AGE:
						   response.sendRedirect("../crmjsp/pdf_mbr_msc_age.jsp?doc_type="+doc_type);
					     break;
          //member get member 礼品销售分析
					case DocType.PDF_CUSTOM_GET:
						   response.sendRedirect("../crmjsp/pdf_prd_gifts.jsp?doc_type="+doc_type);
					     break;
          //目录概要
					case DocType.PDF_PRICELIST_TOTAL:
						   response.sendRedirect("../crmjsp/pdf_pricelist_total.jsp?doc_type="+doc_type);
					     break;
          //销售数量top20
					case DocType.PDF_PRICELIST_QUANTITY:
						   response.sendRedirect("../crmjsp/pdf_pricelist_quantity.jsp?doc_type="+doc_type);
					     break;
          //销售金额top20
					case DocType.PDF_PRICELIST_MONEY:
						   response.sendRedirect("../crmjsp/pdf_pricelist_money.jsp?doc_type="+doc_type);
					     break;
          //页面销售top10
					case DocType.PDF_PRICELIST_PAGE:
						   response.sendRedirect("../crmjsp/pdf_pricelist_page.jsp?doc_type="+doc_type);
					     break;
          //根据产品大类
					case DocType.PDF_PRICELIST_CATEGORY:
						   response.sendRedirect("../crmjsp/pdf_pricelist_category.jsp?doc_type="+doc_type);
					     break;
          //根据小类分
					case DocType.PDF_PRICELIST_SMALL:
						   response.sendRedirect("../crmjsp/pdf_pricelist_small.jsp?doc_type="+doc_type);
					     break;
          //页面表现
					case DocType.PDF_PRICELIST_EDITION:
						   response.sendRedirect("../crmjsp/pdf_pricelist_edition.jsp?doc_type="+doc_type);
					     break;
          //正常销售
					case DocType.PDF_PRICELIST_COMMON:
						   response.sendRedirect("../crmjsp/pdf_selltype_common.jsp?doc_type="+doc_type);
					     break;
          //打折销售
					case DocType.PDF_PRICELIST_DISCOUNT:
						   response.sendRedirect("../crmjsp/pdf_selltype_discount.jsp?doc_type="+doc_type);
					     break;
          //礼品赠品
					case DocType.PDF_PRICELIST_PRICE:
						   response.sendRedirect("../crmjsp/pdf_selltype_price.jsp?doc_type="+doc_type);
					     break;
          //介绍人赠品
					case DocType.PDF_PRICELIST_INTRODUCE:
						   response.sendRedirect("../crmjsp/pdf_selltype_introduce.jsp?doc_type="+doc_type);
					     break;
          //目录促销方式
					case DocType.PDF_PRICELIST_PROMOTION:
						   response.sendRedirect("../crmjsp/pdf_pricelist_promotion.jsp?doc_type="+doc_type);
					     break;
          //单页促销
					case DocType.PDF_PRICELIST_MSC:
						   response.sendRedirect("../crmjsp/pdf_pricelist_analyze.jsp?doc_type="+doc_type);
					     break;
          //客服订单统计
					case DocType.PDF_PERSON_ORDER:
						   response.sendRedirect("../crmjsp/pdf_order_receive.jsp?doc_type="+doc_type);
					     break;
          //会员事件分析表
					case DocType.PDF_PERSON_EVENTS:
						   response.sendRedirect("../crmjsp/pdf_mbr_events.jsp?doc_type="+doc_type);
					     break;
          //库存产品类型分析
					case DocType.PDF_STOCK_TYPE:
						   response.sendRedirect("../crmjsp/pdf_stock_type.jsp?doc_type="+doc_type);
					     break;
          //库存年龄分析
					case DocType.PDF_STOCK_AGE:
						   response.sendRedirect("../crmjsp/pdf_stock_time.jsp?doc_type="+doc_type);
					     break;
          //采购单分析
					case DocType.PDF_STOCK_PUR:
						   response.sendRedirect("../crmjsp/pdf_stock_pur.jsp?doc_type="+doc_type);
					     break;
          //会员充值记录
					case DocType.PDF_MEMBER_DEPOSIT:
						   response.sendRedirect("../crmjsp/pdf_member_deposit.jsp?doc_type="+doc_type);
					     break;
          //会员卡费收支统计表
					case DocType.PDF_MEMBER_CARD:
						   response.sendRedirect("../crmjsp/pdf_card_analyze.jsp?doc_type="+doc_type);
					     break;
          default:
              HTMLView hv=new HTMLView();
              hv.setSessionInfo(sessionInfo);
              hv.setWidth(750);
              hv.setSubject("增加&nbsp;"+doc.getDocName());
              hv.addNewView(doc_type,parent_doc_id);

              response.setContentType(CONTENT_TYPE);
              PrintWriter out = response.getWriter();

              out.println(hv.getHTML());
              out.close();
        }
    }
}