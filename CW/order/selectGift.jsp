<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.member.dao.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
int sellTypeId=Integer.parseInt(request.getParameter("sellTypeId"));
String item_id=request.getParameter("item_id");
int awardId=Integer.parseInt(request.getParameter("awardId"));
String type1=request.getParameter("type1");
String orderId=request.getParameter("orderId");
       orderId=(orderId==null)?"0":orderId.trim();
String ord_require=request.getParameter("ord_require");
       ord_require=(ord_require==null)?"0":ord_require.trim();
       //System.out.println("orderId is "+orderId);
// 17-入会送礼跟msc挂钩
String msc_code = request.getParameter("msc_code");
msc_code = (msc_code==null) ? "":msc_code;
%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>更换缺货礼品::佰明会员关系管理系统</title>
<script language="JavaScript">
function changeGift(item_id,sellTypeId,awardId, new_awardId) {
		//opener.location.href="orderAddSecond.do?type=changeGift&&type1=<%=type1%>&&item_id="+item_id+"&&sellTypeId="+sellTypeId+"&&awardId="+awardId+"&&orderId=<%=orderId%>&old_item_id=<%=item_id%>&new_awardId="+new_awardId;
		var url = "../order/orderAddSecond.do?type=changeGift&&type1=<%=type1%>&&item_id="+item_id+"&&sellTypeId="+sellTypeId+"&&awardId="+awardId+"&&orderId=<%=orderId%>&old_item_id=<%=item_id%>&new_awardId="+new_awardId;
		//ajaxpage(url, "ajaxcontentarea","str");
		opener.getOpenwinValue2(url);
		window.close();
		//opener.window.focus();
		

}


</script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<html:form action="/orderAddSecond.do"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">订单管理</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt;新增订单 -&gt;</font><font color="838383">更换缺货礼品</font> 
      	</td>
   </tr>
</table>
<br>

<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="600"  cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
<%if(sellTypeId != 6){%>
	<tr>   
		<td width="100" class="OraTableRowHeader">货号</td>
		<td width="*" class="OraTableRowHeader">产品名称</td>
		<td width="*" class="OraTableRowHeader">满足条件</td>
		<td width="*" class="OraTableRowHeader">市场价</td>
		<td width="*" class="OraTableRowHeader">促销价</td>
		<td class="OraTableRowHeader">操作</td>
	</tr>
<%}else{%>
	<tr>   
		<td width="100" class="OraTableRowHeader">类型</td>
		<td width="*" class="OraTableRowHeader">号码</td>
		<td width="*" class="OraTableRowHeader">积分</td>
		<td width="*" class="OraTableRowHeader">订单最小金额</td>
		<td width="*" class="OraTableRowHeader">额外金额</td>
		<td class="OraTableRowHeader">操作</td>
	</tr>
	<tr><td>暂时未开通！！</td></tr>
<%}%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      double addmoney=0;
      double standardPrice = 0;
		try{
		 conn = DBManager.getConnection();
		   //1:网站适用；2：CRM适用；3：门店适用；4：网站、CRM适用；5：网站、门店适用；6：CRM、门店适用
			//  注册送礼=7
			//  积分换礼=6
			//  预存款=15
			//  介绍人赠品=5
			//  礼品赠品=4
			//  入会送礼=17
		    String sell_name="";
		    //out.println(sellTypeId);
		    if(sellTypeId==4||sellTypeId==12){
	        
		        sql=" select b.item_id,b.item_code,b.name,a.addy,a.overx, a.id  from prom_gift a,prd_items b,jxc.sto_stock d ";
                   
		        sql+=" where a.itemid=b.item_id and b.item_id=d.item_id and d.sto_no='000' and (d.use_qty-d.frozen_qty)>0 "; 
		        sql+=" and a.scope in (2,4,6,7) and a.ValidFlag=1 and  a.itemid <> "+item_id;
		        sql+=" and a.overx<="+ord_require+" order by a.overx desc ";		        
			
		        //out.println(sql);
		    }
		    if(sellTypeId==5){
		        sql="select b.item_id,b.item_code,b.name,b.standard_price,price addy from mbr_get_mbr_gift a,prd_items b,jxc.sto_stock c";
			sql+=" where a.item_id=b.item_id and b.item_id=c.item_id and c.sto_no='000' and (c.use_qty-c.frozen_qty)>0";
			sql+=" and  sysdate<=a.end_date and a.is_valid=0";
	
		    }
		    if(sellTypeId==6){ //积分兑换
			

			//sql=" select b.item_id,b.item_code,b.name,b.standard_price,a.exchange_price addy,";
			//sql+=" a.exp_start,a.exp_end from mbr_exp_exchange_mst m,mbr_exp_exchange_dtl a,prd_items b,jxc.sto_stock c ";			
			//sql+=" where m.id = a.pid and m.valid_flag ='Y' and sysdate >= m.start_date and sysdate <= m.end_date + 1 and a.item_id=b.item_id and b.item_id=c.item_id and c.sto_no='000' and (c.use_qty-c.frozen_qty)>0 and a.valid_flag='Y' and m.id=5";
			//sql+=" and a.item_id<>"+item_id+" and a.exp_start<="+ord_require+" order by a.exp_start desc";
		    		    
		    sql = " select b.id, b.step_id, b.type, b.no, b.order_require, b.add_money, a.begin_exp from ";
			sql += " exp_exchange_step_dtl b inner join exp_exchange_step_mst a on b.step_id=a.id ";
			sql += " inner join exp_exchange_activity c on a.activity_no = c.activity_no " ;
			sql += " where sysdate >= b.begin_date and sysdate < = b.end_date + 1 and c.status = 1 ";
			sql += " and a.status = 'Y' and b.status = 'Y' and a.begin_exp <= " + ord_require; 
			sql += " order by a.begin_exp desc";
		    }
		    if(sellTypeId==7){

		        sql="select distinct b.item_id,b.item_code,b.name,b.standard_price,a.addmoney addy ,a.order_require overx from mbr_msc_gift a,prd_items b,jxc.sto_stock c  ";			
			sql+=" where a.item_id=b.item_code and b.item_id=c.item_id and c.sto_no='000' and (c.use_qty-c.frozen_qty)>0";
			sql+=" and a.type=1 and b.item_id<>"+item_id+"  and a.status=0 and a.order_require="+ord_require;
                     
			//out.println(sql);
		    }
		    if(sellTypeId==15){

		        sql="select a.price addy,b.item_id,b.item_code,b.name,b.standard_price from mbr_money_gift a,prd_items b,jxc.sto_stock c ";
			sql+=" where a.item_id=b.item_id and b.item_id=c.item_id and c.sto_no='000' and a.status=0 and (c.use_qty-c.frozen_qty)>0";
			sql+=" and b.item_id<>"+item_id+" and a.money<="+ord_require;		
			//out.println(sql);
		    }
		    if(sellTypeId==17){

		    sql="select a.id, a.price addy, b.item_id, b.item_code, "
			+"b.name, b.standard_price, a.overx "
			+ "from recruit_activity_pricelist a, prd_items b, "
			+ "jxc.sto_stock c, recruit_activity_section d, recruit_activity e "
			+ "where a.itemid=b.item_id "
			+ "and b.item_id=c.item_id "
			+ "and a.sectionid = d.id and d.msc = e.msc "
			+ "and c.sto_no='000' and (c.use_qty - c.frozen_qty) > 0 "
			+ "and e.startdate <= sysdate and e.enddate >= sysdate + 1 "
			+ "and e.status = 1 and e.scope in(2, 3) "
			+ "and a.selltype = 17 and a.startdate <= sysdate and a.enddate >= sysdate + 1 "
			+ "and a.status=1 and b.item_id <> "+item_id
		
			+ "and d.type in('A','B','C') ";		
			//System.out.println(sql);
		    }		    
		    //out.println(sql);
		 pstmt=conn.prepareStatement(sql);						
		 rs=pstmt.executeQuery();
		 while(rs.next()){ 
		 	int new_awardId = 0;
			String rs_item_id = "";
			String overx="";//满足条件
			String item_code="";
			String name="";
			//以下积分
			long step_dtl_id = -1;
			String type = null;
			String type_name = "";
			String no = "";
			int begin_exp =0;
			double order_require = 0;
			double add_money = 0;

			if (sellTypeId != 6) { // 不是积分兑换

				if(sellTypeId==4||sellTypeId==12||sellTypeId==17){
					new_awardId=rs.getInt("id");
				}
				rs_item_id=rs.getString("item_id");
				
				
				if(sellTypeId==4||sellTypeId==7||sellTypeId==12||sellTypeId==17){
					overx=rs.getString("overx")+"元";
				}
				item_code=rs.getString("item_code");
				name=rs.getString("name");
				if(sellTypeId==15||sellTypeId==5||sellTypeId==7||sellTypeId==17){
					standardPrice = rs.getDouble("standard_price");
				}else{
					standardPrice = 0;
				}
				
				if(sellTypeId==15){
				  addmoney=0;
				}else{
				  addmoney=rs.getDouble("addy");
				}
			} else { //积分
				step_dtl_id = rs.getLong("id");
				type = rs.getString("type");
				type_name = "";
				if (rs.getString("type").equals("P")) {
					type_name = "礼包";
				} else if (rs.getString("type").equals("G")) {
					type_name = "礼品";
				} else if (rs.getString("type").equals("T")) {
					type_name = "礼券";
				}
				no = rs.getString("no");
				begin_exp = rs.getInt("begin_exp");
				order_require = rs.getDouble("order_require");
				add_money = rs.getDouble("add_money");
			}
%>
<% if (sellTypeId != 6) { %>
	<tr>
		<td class=OraTableCellText><%=item_code%></td>
		<td class=OraTableCellText><%=name%></td>
		<td class=OraTableCellText align=right><%=overx%></td>
		<td class=OraTableCellText align=right><%=standardPrice%></td>
		
		<td class=OraTableCellText align=right><%=addmoney%></td>
		<td class=OraTableCellText align=center><input type="button" value=" 选择 " onClick="changeGift(<%=rs_item_id%>,<%=sellTypeId%>,<%=awardId%>,<%=new_awardId%>);"></td>
	</tr>
<%} else { %>
	<!-- <tr>
		<td class=OraTableCellText><%=type_name%></td>
		<td class=OraTableCellText><%=no%></td>
		<td class=OraTableCellText align=right><%=begin_exp%></td>
		<td class=OraTableCellText align=right><%=order_require%></td>
		
		<td class=OraTableCellText align=right><%=add_money%></td>
		<td class=OraTableCellText align=center><input type="button" value=" 选择 " onClick="changeGift(-1,<%=sellTypeId%>,<%=awardId%>,<%=step_dtl_id%>);"></td>
	</tr> -->
	
<% } %>


<%					
	
	
	} 
	rs.close();
	pstmt.close();

%>
	
</table>
<%
} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {}			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {}
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
 }

%>
</table>
  </td></tr>
</table>  
</html:form>     
</body>

</html>