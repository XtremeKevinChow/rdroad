<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.magic.crm.member.entity.Member"%>
<%
Member member = (Member)request.getSession().getAttribute("currentMember");
if(member == null) {
	member = new Member();
}
%>
<HTML><HEAD>
<script type="text/javascript" src="outlookbar.js"></script>
<link rel="stylesheet" href="Style.css" type="text/css">
<script language="JavaScript">

	var outlookbar = new outlook();
	var t;
	
	/** 会员管理 **/
	t=outlookbar.addtitle('会员管理');
	outlookbar.additem('新增会员',t,'../../member/member_addToken.do');
	outlookbar.additem('会员查询',t,'../../member/query.do');
	outlookbar.additem('会员查询(高)',t,'../../member/aquery.do');
	outlookbar.additem('会员事件查询',t,'../../member/memberEventListinit.do');
	outlookbar.additem('新增团体会员	',t,'../../member/memberGroup.do?type=initAdd');
	outlookbar.additem('团体会员列表',t,'../../member/memberOrgListinit.do');
	outlookbar.additem('会员来电查询',t,'../../member/InquiryList2.do?isquery=2&type=0');
	outlookbar.additem('积分兑换礼品',t,'../../member/expExchange.do?type=queryValidGift');
	outlookbar.additem('待发礼品查询',t,'../../member/expExchange.do?type=queryExchangedGift');
	outlookbar.additem('积分历史查询',t,'../../member/expExchangeHis.do?type=query');
	outlookbar.additem('加入黑名单',t,'../../member/memberBlackList.do?type=showAddedPage');
	
	/** 订单管理 **/
	t=outlookbar.addtitle('订单管理');
	outlookbar.additem('新增订单',t,'../../order/orderAddFirst.do');
	outlookbar.additem('订单查询',t,'../../order/orderQuery.do');
	outlookbar.additem('订单查询(高)',t,'../../order/orderAQuery.do?type=init');
	outlookbar.additem('超期订单',t,'../../goto?t_code=3130');
	outlookbar.additem('挽留订单',t,'../../goto?t_code=3140');
	outlookbar.additem('新增团体订单',t,'../../order/groupOrderAdd.do?type=addFirst');
	outlookbar.additem('团体订单查询',t,'../../member/member_organization_query.jsp');
	outlookbar.additem('配货单查询',t,'../../goto?t_code=3160');
	outlookbar.additem('发货单查询',t,'../../order/snQry.do');
	
	/** 帐户管理 **/
	t=outlookbar.addtitle('帐户管理');
	outlookbar.additem('汇款导入',t,'../../member/member_addmoney_file.jsp');
	outlookbar.additem('会员充值',t,'../../member/memberqueryMoney.do');
	outlookbar.additem('帐户历史',t,'../../member/memberqueryFinanceMoney.do');
	outlookbar.additem('会员退款',t,'../../goto?t_code=3320');
	outlookbar.additem('手工充值',t,'../../order/orderPay.do?type=init&ref_dept=k');
	outlookbar.additem('书香卡充值',t,'../../promotion/saleCardDeposit.jsp');
	
	/** 市场促销 **/
	t=outlookbar.addtitle('市场促销');
	outlookbar.additem('新增招募活动',t,'../../goto?t_code=2110');
	outlookbar.additem('招募活动查询',t,'../../goto?t_code=2120');
	outlookbar.additem('新增单页',t,'../../goto?t_code=2220');
	outlookbar.additem('单页查询',t,'../../goto?t_code=2230');
	outlookbar.additem('新增目录',t,'../../goto?t_code=2420');
	outlookbar.additem('目录查询',t,'../../goto?t_code=2430');
	outlookbar.additem('乐透卡查询',t,'../../promotion/GGCardQuery.do');
	outlookbar.additem('乐透卡抽查',t,'../../promotion/gGCardCheck.do');
	outlookbar.additem('书香卡制作',t,'../../promotion/InitCrush_CardCreate.do?type=create');
	outlookbar.additem('书香卡查询',t,'../../promotion/InitCrush_CardQuery.do');
	outlookbar.additem('书香卡抽查',t,'../../promotion/InitCrush_CardCreate.do?type=check');
	outlookbar.additem('礼券设置',t,'../../goto?t_code=22600');
	outlookbar.additem('目录版块设置',t,'../../goto?t_code=9460');
	outlookbar.additem('招募促销设置',t,'../../member/gift_add.jsp');
	outlookbar.additem('购物促销设置',t,'../../promotion/promotionOperation.do?type=query');
	outlookbar.additem('预存款礼品设置',t,'../../member/memberAddMoneyGiftSetup.do?type=query');
	outlookbar.additem('推荐会员礼品设置',t,'../../member/mbr_get_mbr_gift_list.jsp');
	outlookbar.additem('积分兑换礼品设置',t,'../../member/memberExpExchangeSetup.do?type=query');
	
	/** 产品管理 **/
	t=outlookbar.addtitle('产品管理');
	outlookbar.additem('产品录入',t,'../../product/initProductAdd.do');
	outlookbar.additem('产品查询',t,'../../product/productQuery.do');
	outlookbar.additem('设置套装',t,'../../product/productSetAdd.do');
	outlookbar.additem('产品查询(高)',t,'../../product/productAQuery.do');
	outlookbar.additem('产品类型维护',t,'../../product/product_type_main.jsp');
	outlookbar.additem('供应商管理',t,'../../product/prvidersAdd.do?type=list');
	outlookbar.additem('采购单查询',t,'../../goto?t_code=4510');
	
	/** 审批管理 **/
	t=outlookbar.addtitle('审批管理');
	outlookbar.additem('采购单审批',t,'../../goto?t_code=4500');
	
	/** 财务查询 **/
	t=outlookbar.addtitle('财务查询');
	outlookbar.additem('入库上架差异查询',t,'../../finance/inbound2ShiftDiffQuery.do');

	/** 分析报表 **/
	t=outlookbar.addtitle('分析报表');
	outlookbar.additem('产品销售明细表',t,'../../report/prd_sale_detail.jsp');
	outlookbar.additem('每日产品扣单表',t,'../../crmjsp/pdf_order_waiting.jsp?doc_type=40202');
	outlookbar.additem('产品日销售分析(前台)',t,'../../crmjsp/pdf_prd_sell_analyze_foreground.jsp?doc_type=40109');
	outlookbar.additem('产品日销售分析(后台)',t,'../../crmjsp/pdf_prd_sell_analyze.jsp?doc_type=40101');
	outlookbar.additem('三无产品查询',t,'../../report/noitemsquery.jsp');
	outlookbar.additem('每日订单表',t,'../../crmjsp/pdf_ord_every_days.jsp?doc_type=40201');
	outlookbar.additem('客服订单统计',t,'../../report/orderStat.jsp');
	outlookbar.additem('销售地域分布',t,'../../report/mbrsalezone.jsp');
	outlookbar.additem('会员地域分布',t,'../../report/mbrzone.jsp');
	outlookbar.additem('按照年龄和招募渠道分析',t,'../../report/mbrmsczone.jsp');
	outlookbar.additem('会员来电报表',t,'../../report/reportInquiries.jsp');
	outlookbar.additem('客服操作日志',t,'../../report/customerLog.jsp');
	
	/** 系统管理 **/
	t=outlookbar.addtitle('系统管理');
	outlookbar.additem('增加用户',t,'../../initUserCreate.do');
	outlookbar.additem('用户查询',t,'../../listUser.do');
	outlookbar.additem('增加角色',t,'../../initRoleCreate.do');
	outlookbar.additem('角色浏览',t,'../../listRole.do');
	outlookbar.additem('路径设置',t,'../../pathadd.do');
	outlookbar.additem('邮编地址维护',t,'../../goto?t_code=9224');
	outlookbar.additem('会员免义务条件',t,'../../goto?t_code=9710');
	outlookbar.additem('业务参数设置',t,'../../goto?t_code=9740');
	outlookbar.additem('用户密码修改',t,'../../updatePWD.do');
	outlookbar.additem('设置代理人',t,'../../goto?t_code=9880');

</script>

</HEAD>
<BODY style="MARGIN-LEFT: 0pt; MARGIN-RIGHT: 0pt; MARGIN-TOP: 0pt;MARGIN-BOTTOM: 0pt">		
	<table width="100%" height="100%" cellspacing="0" cellpadding="0" align="center" border="0" >
		<tr  height="100%">
			<td valign="top" align="center" width="100%" height="95%">
				<script language=javascript>locatefold('');outlookbar.show();</script>
			</td>
		</tr>
	</table>
 </BODY>
 </HTML>
