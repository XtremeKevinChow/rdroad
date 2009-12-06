<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%@ page import="com.magic.crm.product.dao.*"%>
<%
DecimalFormat myformat = new DecimalFormat("#");
      String tag=request.getParameter("tag");
      tag=(tag==null)?"":tag;
      String pro_name=request.getParameter("pro_name");
      pro_name=(pro_name==null)?"":pro_name;
      String pro_code=request.getParameter("pro_code");
      pro_code=(pro_code==null)?"":pro_code;      
      String type=request.getParameter("type");
      type=(type==null)?"":type;      
      
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
		try{
		 conn = DBManager.getConnection();      
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--
function query_f() {



	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}

function getProvider(para){	
	document.forms[0].flag.value = para;
	openWin("providerQuery.do?show_pro_no=1","wins",600,400);
	
	
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">网站产品加印分析</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="96%" border="0"  cellpadding="1" cellspacing="0" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td>产品类型：</td>
		<td  bgcolor="#FFFFFF">
		<select name="type">
	        <option value="">请选择...</option>
	        <option value="1" >图书</option>
	        <option value="2" >影视</option>
	        <option value="3" >音乐</option>
	        <option value="4" >游戏/软件</option>
	        <option value="5" >礼品</option>
	        
	        
	        <option value="6" >其他</option>
	        </select>
	        
		</td>
		<td>供应商代码：</td>
		<td  bgcolor="#FFFFFF">
		<input id="supplierID" name="pro_code" value="<%=pro_code%>"  > 
		      <a href="javascript:getProvider('provider');">
		<img src="../images/icon_lookup.gif" width="22" height="23" border=0 align="top"></a> 
		<input type="button" name="btn_query" value=" 查   询 " onclick="query_f();">
		<input type="hidden" name="tag" value="1">
		<input name="flag" type="hidden" value="">		  
		</td>   		

		
	</tr>	
	
		
</table>
<%if(tag.equals("1")){%>
<br>
<table width="1380 align="center" border=0 cellspacing=1 cellpadding=2 class="OraTableRowHeader" noWrap >
	<tr>

      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle >货号</th>
      <th width="220"  bgcolor="#FFFFFF" noWrap align=middle>产品名称</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>供应商代码</th>
      <th width="140"  bgcolor="#FFFFFF" noWrap align=middle>供应商名称</th>
     <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>定价</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>采购成本</th>      
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle >库存</th>          
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>在途数量</th>
      <th width="100"  bgcolor="#FFFFFF" noWrap align=middle>最后到货日期</th>
       <th width="100"  bgcolor="#FFFFFF" noWrap align=middle>最后到货数量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>累计销量</th>
      <th width="100"  bgcolor="#FFFFFF" noWrap align=middle>退厂出库数量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>90天内销量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>30天内销量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>15天内销量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>7天内销量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>3天内销量</th>   
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>预计库存销售天数</th> 
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle>建议补货量</th>    
</tr>

<%

    
    
    
        sql=" select a.item_id,a.name,a.item_code as code,a.standard_price,a.purchasing_cost,b.available_quantity,";
        sql+=" b.afloat_quantity,d.pro_name,d.pro_no,e.qty3,e.qty7,e.qty15,e.qty30,e.qty90,f.use_qty,f.write_date,b.total_quantity,b.pro_rv_qty from prd_items a ";
        sql+=" inner join prd_sell_analyze1 b on a.item_id=b.item_id and b.release_date=trunc(sysdate)";
	//sql+=" inner join prd_sell_analyze1 b on a.item_id=b.item_id and b.release_date=date'2007-06-13'";
	sql+=" inner join providers d on a.supplier_id=d.id";
	sql+=" inner join (";
	sql+=" SELECT JXC.STO_RK_DTL.ITEM_ID,JXC.STO_RK_DTL.USE_QTY,JXC.STO_RK_MST.WRITE_DATE";
	sql+=" FROM JXC.STO_RK_DTL";
	sql+=" INNER JOIN JXC.STO_RK_MST ON JXC.STO_RK_MST.RK_NO=JXC.STO_RK_DTL.RK_NO";
	sql+=" INNER JOIN ";
	sql+=" (";
	sql+=" SELECT ITEM_ID,MAX(B.RK_NO)   AS RK_NO FROM ";
	sql+=" JXC.STO_RK_DTL A";
	sql+=" INNER JOIN JXC.STO_RK_MST B ON A.RK_NO=B.RK_NO "; 
	sql+=" WHERE B.RK_CALSS='P' ";
	sql+=" GROUP BY A.ITEM_ID";
	sql+=" ) AA ON JXC.STO_RK_DTL.RK_NO=AA.RK_NO AND AA.ITEM_ID=JXC.STO_RK_DTL.ITEM_ID  ";

	sql+=" ) f on f.item_id=a.item_id ";
	sql+=" inner join (select ord_lines.item_id,";
        sql+=" sum(case when sysdate-ord_headers.release_date<=3 then nvl(ord_lines.quantity,0) else 0 end) as qty3,";
        sql+=" sum(case when sysdate-ord_headers.release_date<=7 then nvl(ord_lines.quantity,0) else 0 end) as qty7,";
        sql+=" sum(case when sysdate-ord_headers.release_date<=15 then nvl(ord_lines.quantity,0) else 0 end) as qty15,   ";             
        sql+=" sum(case when sysdate-ord_headers.release_date<=30 then nvl(ord_lines.quantity,0) else 0 end) as qty30,";
        sql+=" sum(case when sysdate-ord_headers.release_date<=90 then nvl(ord_lines.quantity,0) else 0 end) as qty90 ";
        sql+=" from ord_headers,ord_lines ";
        sql+=" where ord_headers.id=ord_lines.order_id ";
        sql+=" and ord_headers.release_date>=sysdate-90 ";
        sql+=" and ord_headers.release_date<=sysdate ";
      	sql+=" group by ord_lines.item_id ";
     	sql+="  ) e on a.item_id=e.item_id ";
		sql+=" where a.item_id not in(select item_id FROM prd_pricelist_lines WHERE pricelist_id IN  ";
		sql+="(";
        sql+="SELECT ID from prd_pricelists ";
        sql+="where status=100 ";
        sql+="and price_type_id in (3,5) and sysdate>=effect_date and sysdate<expired_date+1))";
            
            
	if(type.length()>0){
	sql+="  and a.item_type ="+type;
	}	
	if(pro_code.length()>0){
	sql+="  and d.pro_no like '%"+pro_code+"%'"; 
	}

	sql+="  order by e.qty7 desc "; 
        //out.println(sql);
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	while(rs.next()){	
		
	String name=rs.getString("name");
	String item_code=rs.getString("code");
	int item_id=rs.getInt("item_id");
	String pro_no=rs.getString("pro_no");
	String rs_pro_name=rs.getString("pro_name");	
	String standard_price=rs.getString("standard_price");//定价
	String purchasing_cost=rs.getString("purchasing_cost");//采购成本
	int qty=rs.getInt("available_quantity");//库存数量
	//int all_in_qty=ProductDAO.getPru_Qty(conn,item_id);总进货量
	int afloat_quantity=rs.getInt("afloat_quantity");//在途数量
	int qty3=rs.getInt("qty3");
	int qty7=rs.getInt("qty7");
	int qty15=rs.getInt("qty15");
	int qty30=rs.getInt("qty30");
	int qty90=rs.getInt("qty90");
	String qty_sell="0";//预计库存销售天数=库存/7天平均	
        if(qty7>0){
	qty_sell=myformat.format(qty/qty7);
	}	
	int in_qty=qty7*30;//建议补货量=7天平均*30
	int use_qty=rs.getInt("use_qty");
	String write_date=rs.getString("write_date");
	String total_quantity=rs.getString("total_quantity");
	String pro_rv_qty=rs.getString("pro_rv_qty");
 
			

%>	
	<tr align=center>

		
		<td bgcolor="#FFFFFF" noWrap align="left"><%=item_code%></td>
		<td bgcolor="#FFFFFF" noWrap align="left"><%=name%></td>
		<td bgcolor="#FFFFFF" noWrap align="left"><%=pro_no%></td>
		<td bgcolor="#FFFFFF" noWrap align="left"><%=rs_pro_name%></td>		
		<td bgcolor="#FFFFFF" noWrap align="left"><%=standard_price%></td>
		<td bgcolor="#FFFFFF" noWrap align="left"><%=purchasing_cost%></td>	
		<td bgcolor="#FFFFFF" noWrap align="right"><%=qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=afloat_quantity%></td>
		
		<td bgcolor="#FFFFFF" noWrap align="left"><%=write_date%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=use_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=total_quantity%></td>
		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=pro_rv_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=qty90%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=qty30%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=qty15%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=qty7%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=qty3%></td>
		
		
				
		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=qty_sell%></td>		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=in_qty%></td>		
		

	</tr>
<%
}
%>

	<tr align=center>

		<td bgcolor="#FFFFFF" noWrap colspan="22"><a href="product_sell_analyze_net_execl.jsp?pro_code=<%=pro_code%>&pro_name=<%=pro_name%>&type=<%=type%>">生成execl文件</a></td>

	</tr>
</table>
<%}%>
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