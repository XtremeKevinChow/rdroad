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
	
	/** ��Ա���� **/
	t=outlookbar.addtitle('��Ա����');
	outlookbar.additem('������Ա',t,'../../member/member_addToken.do');
	outlookbar.additem('��Ա��ѯ',t,'../../member/query.do');
	outlookbar.additem('��Ա��ѯ(��)',t,'../../member/aquery.do');
	outlookbar.additem('��Ա�¼���ѯ',t,'../../member/memberEventListinit.do');
	outlookbar.additem('���������Ա	',t,'../../member/memberGroup.do?type=initAdd');
	outlookbar.additem('�����Ա�б�',t,'../../member/memberOrgListinit.do');
	outlookbar.additem('��Ա�����ѯ',t,'../../member/InquiryList2.do?isquery=2&type=0');
	outlookbar.additem('���ֶһ���Ʒ',t,'../../member/expExchange.do?type=queryValidGift');
	outlookbar.additem('������Ʒ��ѯ',t,'../../member/expExchange.do?type=queryExchangedGift');
	outlookbar.additem('������ʷ��ѯ',t,'../../member/expExchangeHis.do?type=query');
	outlookbar.additem('���������',t,'../../member/memberBlackList.do?type=showAddedPage');
	
	/** �������� **/
	t=outlookbar.addtitle('��������');
	outlookbar.additem('��������',t,'../../order/orderAddFirst.do');
	outlookbar.additem('������ѯ',t,'../../order/orderQuery.do');
	outlookbar.additem('������ѯ(��)',t,'../../order/orderAQuery.do?type=init');
	outlookbar.additem('���ڶ���',t,'../../goto?t_code=3130');
	outlookbar.additem('��������',t,'../../goto?t_code=3140');
	outlookbar.additem('�������嶩��',t,'../../order/groupOrderAdd.do?type=addFirst');
	outlookbar.additem('���嶩����ѯ',t,'../../member/member_organization_query.jsp');
	outlookbar.additem('�������ѯ',t,'../../goto?t_code=3160');
	outlookbar.additem('��������ѯ',t,'../../order/snQry.do');
	
	/** �ʻ����� **/
	t=outlookbar.addtitle('�ʻ�����');
	outlookbar.additem('����',t,'../../member/member_addmoney_file.jsp');
	outlookbar.additem('��Ա��ֵ',t,'../../member/memberqueryMoney.do');
	outlookbar.additem('�ʻ���ʷ',t,'../../member/memberqueryFinanceMoney.do');
	outlookbar.additem('��Ա�˿�',t,'../../goto?t_code=3320');
	outlookbar.additem('�ֹ���ֵ',t,'../../order/orderPay.do?type=init&ref_dept=k');
	outlookbar.additem('���㿨��ֵ',t,'../../promotion/saleCardDeposit.jsp');
	
	/** �г����� **/
	t=outlookbar.addtitle('�г�����');
	outlookbar.additem('������ļ�',t,'../../goto?t_code=2110');
	outlookbar.additem('��ļ���ѯ',t,'../../goto?t_code=2120');
	outlookbar.additem('������ҳ',t,'../../goto?t_code=2220');
	outlookbar.additem('��ҳ��ѯ',t,'../../goto?t_code=2230');
	outlookbar.additem('����Ŀ¼',t,'../../goto?t_code=2420');
	outlookbar.additem('Ŀ¼��ѯ',t,'../../goto?t_code=2430');
	outlookbar.additem('��͸����ѯ',t,'../../promotion/GGCardQuery.do');
	outlookbar.additem('��͸�����',t,'../../promotion/gGCardCheck.do');
	outlookbar.additem('���㿨����',t,'../../promotion/InitCrush_CardCreate.do?type=create');
	outlookbar.additem('���㿨��ѯ',t,'../../promotion/InitCrush_CardQuery.do');
	outlookbar.additem('���㿨���',t,'../../promotion/InitCrush_CardCreate.do?type=check');
	outlookbar.additem('��ȯ����',t,'../../goto?t_code=22600');
	outlookbar.additem('Ŀ¼�������',t,'../../goto?t_code=9460');
	outlookbar.additem('��ļ��������',t,'../../member/gift_add.jsp');
	outlookbar.additem('�����������',t,'../../promotion/promotionOperation.do?type=query');
	outlookbar.additem('Ԥ�����Ʒ����',t,'../../member/memberAddMoneyGiftSetup.do?type=query');
	outlookbar.additem('�Ƽ���Ա��Ʒ����',t,'../../member/mbr_get_mbr_gift_list.jsp');
	outlookbar.additem('���ֶһ���Ʒ����',t,'../../member/memberExpExchangeSetup.do?type=query');
	
	/** ��Ʒ���� **/
	t=outlookbar.addtitle('��Ʒ����');
	outlookbar.additem('��Ʒ¼��',t,'../../product/initProductAdd.do');
	outlookbar.additem('��Ʒ��ѯ',t,'../../product/productQuery.do');
	outlookbar.additem('������װ',t,'../../product/productSetAdd.do');
	outlookbar.additem('��Ʒ��ѯ(��)',t,'../../product/productAQuery.do');
	outlookbar.additem('��Ʒ����ά��',t,'../../product/product_type_main.jsp');
	outlookbar.additem('��Ӧ�̹���',t,'../../product/prvidersAdd.do?type=list');
	outlookbar.additem('�ɹ�����ѯ',t,'../../goto?t_code=4510');
	
	/** �������� **/
	t=outlookbar.addtitle('��������');
	outlookbar.additem('�ɹ�������',t,'../../goto?t_code=4500');
	
	/** �����ѯ **/
	t=outlookbar.addtitle('�����ѯ');
	outlookbar.additem('����ϼܲ����ѯ',t,'../../finance/inbound2ShiftDiffQuery.do');

	/** �������� **/
	t=outlookbar.addtitle('��������');
	outlookbar.additem('��Ʒ������ϸ��',t,'../../report/prd_sale_detail.jsp');
	outlookbar.additem('ÿ�ղ�Ʒ�۵���',t,'../../crmjsp/pdf_order_waiting.jsp?doc_type=40202');
	outlookbar.additem('��Ʒ�����۷���(ǰ̨)',t,'../../crmjsp/pdf_prd_sell_analyze_foreground.jsp?doc_type=40109');
	outlookbar.additem('��Ʒ�����۷���(��̨)',t,'../../crmjsp/pdf_prd_sell_analyze.jsp?doc_type=40101');
	outlookbar.additem('���޲�Ʒ��ѯ',t,'../../report/noitemsquery.jsp');
	outlookbar.additem('ÿ�ն�����',t,'../../crmjsp/pdf_ord_every_days.jsp?doc_type=40201');
	outlookbar.additem('�ͷ�����ͳ��',t,'../../report/orderStat.jsp');
	outlookbar.additem('���۵���ֲ�',t,'../../report/mbrsalezone.jsp');
	outlookbar.additem('��Ա����ֲ�',t,'../../report/mbrzone.jsp');
	outlookbar.additem('�����������ļ��������',t,'../../report/mbrmsczone.jsp');
	outlookbar.additem('��Ա���籨��',t,'../../report/reportInquiries.jsp');
	outlookbar.additem('�ͷ�������־',t,'../../report/customerLog.jsp');
	
	/** ϵͳ���� **/
	t=outlookbar.addtitle('ϵͳ����');
	outlookbar.additem('�����û�',t,'../../initUserCreate.do');
	outlookbar.additem('�û���ѯ',t,'../../listUser.do');
	outlookbar.additem('���ӽ�ɫ',t,'../../initRoleCreate.do');
	outlookbar.additem('��ɫ���',t,'../../listRole.do');
	outlookbar.additem('·������',t,'../../pathadd.do');
	outlookbar.additem('�ʱ��ַά��',t,'../../goto?t_code=9224');
	outlookbar.additem('��Ա����������',t,'../../goto?t_code=9710');
	outlookbar.additem('ҵ���������',t,'../../goto?t_code=9740');
	outlookbar.additem('�û������޸�',t,'../../updatePWD.do');
	outlookbar.additem('���ô�����',t,'../../goto?t_code=9880');

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
