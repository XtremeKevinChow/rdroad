<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*,com.magic.utils.Arith"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String barcode = "";
String queryBy = request.getParameter("queryBy");


barcode=request.getParameter("barcode");
barcode=(barcode==null)?"":barcode;

%>
	 
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function doSearch() {

	
	if(document.form.barcode.value == "") {
		// 没有查询条件
		alert("请输入发货单号！");
		return false;
	} 

}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 订单管理 -&gt; 部分退货发货单查询</font></td>
  </tr>
</table>
<form name="form" action="" onsubmit="return doSearch();" method="post">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		<td>
			发货单号：<input type="text" name="barcode" size="12" value=<%=barcode%>>
			<input name="queryBy" value="shippingnotice" type="hidden">
			<input name="BtnQuery" type="submit" value=" 查询 ">
      </td>		
	</tr>
</table>
<%
//queryBy表示是通过发货单号（发货单条码）还是通过订单号来查询，queryBy="order"时barcode的值为订单号，queryBy="shippingnotice"时barcode的值为发货单号或者发货单条码
if(queryBy != null && (queryBy.equals("shippingnotice") || queryBy.equals("order"))){
%>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5>
	<tr align="center"  class="OraTableRowHeader" noWrap >
		<th class="OraTableRowHeader" noWrap  width="9%">货号</th>
		<th class="OraTableRowHeader" noWrap  noWrap width="">产品名称</th>
		<th class="OraTableRowHeader" noWrap  noWrap width="9%">产品类别</th>
		<th class="OraTableRowHeader" noWrap  noWrap width="9%">原价格</th>
		<th class="OraTableRowHeader" noWrap  noWrap width="9%">数量</th>
		
		<th class="OraTableRowHeader" noWrap  width="9%">退货价格</th>
		<th class="OraTableRowHeader" noWrap  noWrap width="9%">订单数量</th>
		<th class="OraTableRowHeader" noWrap  noWrap width="9%">状态</th>
	  
	</tr>	
<%
	Connection conn=null;
    ResultSet rs=null;
	PreparedStatement pstmt=null;
	String condition="";
			String sql="";
		try{
		 conn = DBManager.getConnection();	
			String first_id="";
			sql="select b.goods_fee as ship_goods_fee,b.delivery_fee as ship_delivery_fee,  b.append_fee as ship_append_fee,";
			sql+="b.goods_fee+b.delivery_fee+b.append_fee+b.package_fee as ship_should_amt,a.item_id,e.name,a.quantity as s_quantity,a.price as s_price,h.name as s_status,";
			sql+="c.quantity as o_quantity,c.price as o_price,c.item_id as tao_item_id,g.name as o_status,c.status ,i.name as item_type_name,d.so_number,d.goods_fee as order_goods_fee,";
			sql+=" d.delivery_fee as order_delivery_fee,c.frozen_item,";
			sql+="d.append_fee as order_append_fee,d.goods_fee+d.delivery_fee+d.append_fee+d.package_fee as order_should_amt";
			sql+=" from ship_dtl a inner join ship_mst  b on a.sn_id=b.id inner join ord_lines c on a.ref_order_line_id=c.id";
			sql+=" inner join ord_headers d on b.ref_order_id=d.id inner join prd_items e on a.item_id=e.item_id ";
			sql+=" inner join s_sell_type f on c.sell_type=f.id ";
			sql+=" inner join s_order_line_status g on c.status=g.id ";
			sql+=" inner join s_ship_dtl_status h on a.status=h.id ";
			sql+=" inner join s_item_type i on e.item_type=i.id ";
			if(queryBy.equals("order")){
				sql+=" where b.order_number=?";
			}else{					
				sql+=" where b.barcode=?";
			}
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,barcode);
			rs=pstmt.executeQuery();
			int i=1;
			while(rs.next()){ 
			String so_number=rs.getString("so_number");
			String ship_goods_fee=rs.getString("ship_goods_fee");
			String ship_delivery_fee=rs.getString("ship_delivery_fee");
			String ship_append_fee=rs.getString("ship_append_fee");
			String ship_should_amt=rs.getString("ship_should_amt");
			String order_goods_fee=rs.getString("order_goods_fee");
			String order_delivery_fee=rs.getString("order_delivery_fee");
			String order_append_fee=rs.getString("order_append_fee");
			String order_should_amt=rs.getString("order_should_amt");		
			String item_id=rs.getString("item_id");
			String name=rs.getString("name");		
			String status=rs.getString("status");
			String s_status=rs.getString("s_status");
			String o_status=rs.getString("o_status");		
			String s_quantity=rs.getString("s_quantity");
			String o_quantity=rs.getString("o_quantity");	
			String s_price=rs.getString("s_price");
			String o_price=rs.getString("o_price");	
			
			String color="#000000";
			if(Double.parseDouble(s_price)!=Double.parseDouble(o_price)){
			   color="red";
			}
			if(status.equals("-8")){
			   color="blue";
			}			
				
			
			String item_type_name=rs.getString("item_type_name");		
			String tao_item_id=rs.getString("tao_item_id");	
			String frozen_item=rs.getString("frozen_item");	
			       frozen_item=(frozen_item==null)?"":frozen_item;					
%>

<tr align="center"  class="OraTableRowHeader" noWrap  >
		<td  noWrap class=OraTableCellText><font color="<%=color%>"><%=item_id%></font></td>
		<td align="left" class=OraTableCellText><font color="<%=color%>"><%=name%></font></td>
		<td align="left" class=OraTableCellText><font color="<%=color%>"><%=item_type_name%></font></td>
		<td align="right" class=OraTableCellText><font color="<%=color%>"><%=s_price%></font></td>
		<td align="right" class=OraTableCellText><font color="<%=color%>"><%=s_quantity%></font></td>
		
		<%
		if(frozen_item.length()>10){
		
		%>			
		<td align="right" class=OraTableCellText><font color="<%=color%>">
		<%
		   if(i==1){
       out.println(o_price);
       }
	  %>
		</font</td>
		<td align="right" class=OraTableCellText><font color="<%=color%>">
		<%
		   if(i==1){
       out.println(o_quantity);
       }
	  %>		
		</font</td>
		<td align="left" class=OraTableCellText><font color="<%=color%>">
		<%
		   if(i==1){
       out.println(o_status);
       }
	  %>			
		</font</td>		
	<%
	i++;
	}else{	
	%>			
		
    <td align="right" class=OraTableCellText><font color="<%=color%>"><%=o_price%></font></td>
	<td align="right" class=OraTableCellText><font color="<%=color%>"><%=o_quantity%></font></td>
	<td align="left" class=OraTableCellText><font color="<%=color%>"><%=s_status%></font></td>	
		<%}%>
	</tr>		
	
		
<%
	}

%>
</table>


<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5>
		
<%
	
			sql="select b.goods_fee as ship_goods_fee,b.delivery_fee as ship_delivery_fee,  b.append_fee as ship_append_fee,";
			sql+="b.goods_fee+b.delivery_fee+b.append_fee+b.package_fee as ship_should_amt,";
			sql+="d.so_number,d.goods_fee as order_goods_fee,";
			sql+=" d.delivery_fee as order_delivery_fee,";
			sql+="d.append_fee as order_append_fee,d.goods_fee+d.delivery_fee+d.append_fee+d.package_fee as order_should_amt, b.package_fee as ship_package_fee, d.package_fee as ord_package_fee ";
			sql+=" from ship_dtl a inner join ship_mst  b on a.sn_id=b.id inner join ord_lines c on a.ref_order_line_id=c.id";
			sql+=" inner join ord_headers d on b.ref_order_id=d.id inner join prd_items e on a.item_id=e.item_id ";
			sql+=" inner join s_sell_type f on c.sell_type=f.id ";	
			if(queryBy.equals("order")){
				sql+=" where b.order_number=?";
			}else{					
				sql+=" where b.barcode=?";
			}
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,barcode);
			rs=pstmt.executeQuery();
			if(rs.next()){ 
			String so_number=rs.getString("so_number");
			String ship_goods_fee=rs.getString("ship_goods_fee");
			String ship_delivery_fee=rs.getString("ship_delivery_fee");
			String ship_append_fee=rs.getString("ship_append_fee");
			String ship_should_amt=rs.getString("ship_should_amt");
			String order_goods_fee=rs.getString("order_goods_fee");
			String order_delivery_fee=rs.getString("order_delivery_fee");
			String order_append_fee=rs.getString("order_append_fee");
			String order_should_amt=rs.getString("order_should_amt");	
			double banlance=Double.parseDouble(ship_should_amt)-Double.parseDouble(order_should_amt);
			double ship_package_fee = rs.getDouble("ship_package_fee");
			double ord_package_fee = rs.getDouble("ord_package_fee");
%>
	<tr>
		<th width="50%" align="left" colspan="4">订单部分退货订单重算后的差异信息。订单号：【<%=so_number%>】  发货单号：【<a href="/order/snView.do?queryKey=findBySNNum&sn_id=<%=barcode%>"><%=barcode%></a>】</th>
		
	</tr>
	<tr align="left">
		<td class="OraTableRowHeader" noWrap  noWrap width="15%" align="center">明细</td>
		<td class="OraTableRowHeader" noWrap  noWrap width="15%" colspan=1 align="center">退货之前订单金额</td>
		<td class="OraTableRowHeader" noWrap  noWrap width="15%" colspan=1 align="center">部分退货重算后订单金额</td>
	</tr>
	<tr align="left">
		<td class="OraTableRowHeader" noWrap  noWrap width="15%">购物金额</td>
		<td  noWrap width="35%" class=OraTableCellText align="right"><%=ship_goods_fee%></td>
		<td  noWrap width="35%" class=OraTableCellText align="right">
		<%
		  if(Double.parseDouble(ship_goods_fee)==Double.parseDouble(order_goods_fee)){
		  out.println(order_goods_fee);
		  }else{
		  %>
		  <font color="red"><%=order_goods_fee%></font>
		  <%}%>
		</td>
	</tr>	
	<tr align="left">
		<td class="OraTableRowHeader" noWrap  noWrap width="15%">发送费</td>
		<td noWrap width="35%" class=OraTableCellText  align="right"><%=ship_delivery_fee%></td>
		<td noWrap width="35%" class=OraTableCellText align="right">
		<%
		  if(Double.parseDouble(ship_delivery_fee)==Double.parseDouble(order_delivery_fee)){
		  out.println(Double.parseDouble(order_delivery_fee));
		  }else{
		  %>
		  <font color="red"><%=Double.parseDouble(order_delivery_fee)%></font>
		  <%}%>		

		</td>
	</tr>		
	<tr align="left">
		<td class="OraTableRowHeader" noWrap  noWrap width="15%">包装费</td>
		<td noWrap width="35%" class=OraTableCellText  align="right"><%=ship_package_fee%></td>
		<td noWrap width="35%" class=OraTableCellText align="right">
		<%
		  if(ship_package_fee==ord_package_fee){
		  out.println(ord_package_fee);
		  }else{
		  %>
		  <font color="red"><%=ord_package_fee%></font>
		  <%}%>		

		</td>
	</tr>		
	<tr align="left">
		<td class="OraTableRowHeader" noWrap  noWrap width="15%">礼金</td>
		<td noWrap width="35%" class=OraTableCellText align="right"><%=ship_append_fee%></td>
		<td noWrap width="35%" class=OraTableCellText align="right">
		<%
		  if(Double.parseDouble(ship_append_fee)==Double.parseDouble(order_append_fee)){
		  out.println(order_append_fee);
		  }else{
		  %>
		  <font color="red"><%=order_append_fee%></font>
		  <%}%>				
		</td>
	</tr>	
	<tr align="left">
		<td class="OraTableRowHeader" noWrap  noWrap width="15%">总计</td>
		<td  width="35%" class=OraTableCellText align="right"><%=ship_should_amt%></td>
		<td width="35%" class=OraTableCellText align="right">
		<%
		  if(Double.parseDouble(ship_should_amt)==Double.parseDouble(order_should_amt)){
		  out.println(order_should_amt);
		  }else{
		  %>
		  <font color="red"><%=order_should_amt%></font>
		  <%}%>			
		</td>
	</tr>	
	<tr align="left">
		<td class="OraTableRowHeader" noWrap   width="15%">退款金额</td ><td noWrap width="35%" colspan="2" class=OraTableCellText align="right">
		<%=Arith.round(banlance,2)%></td>
	</tr>		
	<%}%>
	<tr align="left">
		<td colspan="3"></td>
	</tr>		
	</table>
	<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5>
		<tr>
			<td align="left"><font color="red">
				注：1.产品列表中红色部分表示因部分退货后价格发生改变，蓝色部分表示该产品退货。<br>
				    2.退款金额为正表示退入会员帐户，为负表示从会员帐户扣除。
			</td>
			
		</tr>
	</table>
	<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5>
		<tr>
			<td align=center><input type=button value=" 返回 " onclick="javascript:history.go(-1);"></td>
		</tr>
	</table>
</form>

</body>
</html>
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
<%}%>
