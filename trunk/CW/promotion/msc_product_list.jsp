<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@page import="com.magic.crm.promotion.entity.*,com.magic.crm.promotion.dao.*"%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      DecimalFormat myformat = new DecimalFormat("###,###.00");      
      String pricelist_id=request.getParameter("pricelist_id");
      
      String item_code=request.getParameter("item_code");
             item_code=(item_code==null)?"":item_code;             
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">

<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../crmjsp/go_top.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">
function operation(typename) {
	var flag = false;
	var len = DataTable.rows.length;
	var idvalue;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
                                idvalue=row.getElementsByTagName("INPUT")(0).value;
				flag = true;
				break;
			}
		}
		
		
	}
	if (flag == true)
	{
		if (confirm("确定要进行这一步操作?"))
		{
		
			document.form1.action = "Pricelist_line.do?type=delete2&typename="+typename+"&id="+idvalue;
			document.form1.submit();
		}
		
	} else {
		alert("请选择记录!");
	}			        	         
}
function modify() {
	var flag = false;
	var len = DataTable.rows.length;
	var idvalue;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
                                idvalue=row.getElementsByTagName("INPUT")(0).value;
				flag = true;
				break;
			}
		}
		
		
	}
	if (flag == true)
	{
		if (confirm("该功能将修改对应货号的所有产品，确定要进行这一步操作?"))
		{
	
			document.form1.action = "Pricelist_line.do?type=init2&id="+idvalue;
			document.form1.submit();
		}
		
	} else {
		alert("请选择记录!");
	}			        	         
}
function upload() {		
	document.form1.action = "data_upload.jsp?doc_type=1680&t_code=2430&parent_doc_id=<%=pricelist_id%>";
	document.form1.submit();
			        	         
}
function add() {	
		document.form1.action = "Pricelist_line.do?type=init2";
		document.form1.submit();			        	         
}
function msc_gift_list() {	
		document.form1.action = "prd_pricelist_gift.do?type=list&pricelist_id=<%=pricelist_id%>";
		document.form1.submit();			        	         
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
    	<b><font color="838383">当前位置</font></b><font color="838383"> : </font>
    	<font color="838383">促销管理</font><font color="838383">-&gt; </font>
    	<font color="838383">促销行管理</font><font color="838383">&nbsp; </font>
    	</td>
      	
   </tr>
</table>
<%
try{
  conn = DBManager.getConnection();  
%>
<form method="post" name="form1">
<table  border=0 cellspacing=1 cellpadding=1  width="800" align="center"  class="OraTableRowHeader" noWrap >
	<tr  bgcolor="#FFFFFF">
	<td align="left" width="100">货号:	
	</td>
	<td align="left">
		<input type=text name="item_code" value="<%=item_code%>">
		&nbsp;<input type=submit   value="查询">	
	</td>
	</tr>
</table>
<input type=hidden name="pricelist_id" value="<%=pricelist_id%>">
</form>

<table  border=0 cellspacing=1 cellpadding=1  width="800"  align="center"  id="DataTable">
	<tr >
	<th class="OraTableRowHeader"   width="30"></th>
	    <th class="OraTableRowHeader" width="60">sku</th>
		<th class="OraTableRowHeader" width="100">货号</th>
		<th class="OraTableRowHeader" width="200">产品名称</th>
		<th class="OraTableRowHeader" width="100">颜色</th>
		<th class="OraTableRowHeader" width="70">尺寸</th>
		<th class="OraTableRowHeader"   width="100" >销售方式</th>
		<th class="OraTableRowHeader"   width="70">会员价</th>
		<th class="OraTableRowHeader"   width="70">vip价</th>	
			
	</tr>
<%

		  sql=" select a.id,a.sku_id,a.page,a.sale_price,a.vip_price,b.itm_name,a.item_code,";
		  sql+= " c.color_code,d.name as color_name,e.name as size_name, ";
		  sql+=" (select name from s_sell_type where id=a.sell_type) as sell_type_name ";
		  sql+=" from PRD_PRICELIST_LINES a inner join prd_item b on a.item_code=b.itm_code ";
			sql+=" join prd_item_sku c on a.sku_id=c.sku_id ";
			sql+=" join prd_item_color d on c.color_code=d.code";
			sql+=" join prd_item_size e on c.size_code=e.code";
		  sql+=" where a.status=0 and a.pricelist_id="+pricelist_id;
		  
		  if(item_code.length()>0){
		  
		  sql+=" and b.itm_code='"+item_code+"'";
		  }
	  	  
	  	  //out.println(sql);
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){		  
		  String rs_item_code=rs.getString("item_code");
		  String sku_id=rs.getString("sku_id");
		  String name=rs.getString("itm_name");
		  String color = rs.getString("color_name")+ "("+ rs.getString("color_name") + ")";
		  String size = rs.getString("size_name");
		  String sell_type_name=rs.getString("sell_type_name");
		  
		  double sale_price=rs.getDouble("sale_price");
		  double vip_price=rs.getDouble("vip_price");
		  String id=rs.getString("id");
%>      
	<tr bgcolor="#FFFFFF">
	        <td class="OraTableCellText" noWrap align="center"><input type="radio" name="id" value="<%=id%>"></td>
		<td class="OraTableCellText" noWrap align="center""><%=sku_id%></a></td>
		<td class="OraTableCellText" noWrap align="center">&nbsp;<%=rs_item_code%></td>
		<td class="OraTableCellText" noWrap align="center">&nbsp;<%=name%></td>
		<td class="OraTableCellText" noWrap align="center">&nbsp;<%=color%></td>
		<td class="OraTableCellText" noWrap align="center">&nbsp;<%=size%></td>
		<td class="OraTableCellText" noWrap align="center">&nbsp;<%=sell_type_name%></td>
		<td class="OraTableCellText" noWrap align="right">&nbsp;<%=myformat.format(sale_price)%></td>
		<td class="OraTableCellText" noWrap align="right">&nbsp;<%=myformat.format(vip_price)%></td>	
	</tr>
	<%}//while%>
<tr bgcolor="#FFFFFF">
		<td class="OraTableCellText" noWrap align="center" colspan="11">
		<input type=button onclick="javascript:add()" value="新增产品">&nbsp;
		<input type=button onclick="javascript:operation('del')" value="删除产品">&nbsp;
		<input type=button onclick="javascript:modify()" value="修改产品">&nbsp;
		<input type=button onclick="msc_gift_list()" value="招募礼品设置">&nbsp;
		</td>	
	</tr>	
	
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
</body>
</html>
