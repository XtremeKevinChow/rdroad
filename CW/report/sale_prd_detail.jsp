<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.util.Constants"%>
<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	String today=sdf.format(new Date()).toString().trim();	
	
String begin_date = request.getParameter("begin_date");
       begin_date=(begin_date==null)?today:begin_date;	
String end_date = request.getParameter("end_date");
       end_date=(end_date==null)?today:end_date;
String item_code = request.getParameter("queryItemCode");
       item_code=(item_code==null)?"":item_code;	
String item_type = request.getParameter("item_type");
       item_type=(item_type==null)?"":item_type;	
String category_id = request.getParameter("categoryID");
       category_id=(category_id==null)?"":category_id;	
String pr_type = request.getParameter("pr_type");
       pr_type=(pr_type==null)?"":pr_type;	
String delivery_type = request.getParameter("delivery_type");
       delivery_type=(delivery_type==null)?"":delivery_type;	
                                          
String tag=request.getParameter("tag");
tag=(tag==null)?"":tag;
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
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">
function getCategory(){
	
	//openWin("/mrm/product/product_category_query.jsp","2005",600,400);
	openWin("../product/prdCatQuery.do","2005",600,400);

}
function getReturnCate(selectedid){
	document.forms[0].categoryID.value= selectedid;
	
}

function f_export_excel() {
    if(document.getElementById("data") ==null) {
        alert("没有查询数据不能导出");
        return;
    }
    document.forms[0].action="excel_export.jsp";
    document.target="_blank";
	document.forms[0].submit();
}
function querySubmit() {

	if(document.form.begin_date.value==""
	&&document.form.end_date.value==""
	&&document.form.itemCode.value==""
	&&document.form.item_type.value==""
	&&document.form.categoryID.value==""
	&&document.form.product_owner_id.value==""
	&&document.form.pr_type.value==""
	&&document.form.delivery_type.value==""	
	&&document.form.supplierID.value==""	
	){
		alert('查询条件不能为空!');
		return false;;
	}else{
		var bdate = document.form.begin_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		if(document.form.begin_date.value!=""){
			 if(bdate==null){
					alert('请按格式填写起始日期,并且注意你的日期是否正确!');
					document.form.begin_date.focus();
					return false;
			 }
		 }	
		var edate = document.form.end_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		if(document.form.end_date.value!=""){
			 if(edate==null){
					alert('请按格式填写结束日期,并且注意你的日期是否正确!');
					document.form.end_date.focus();
					return false;
			 }
		}		 
	}
	
	document.form.search.disabled = true;
	document.form.submit();
}


function getProduct(para){
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
}

function getOpenwinValue(ret){
    document.forms[0].queryItemCode.value = ret;
}

function initFocus(){
	document.form.begin_date.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">数据分析</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品销售明细表</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="sale_prd_detail.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td width="10%">起始日期</td>
		<td width="20%"><input type="text" name="begin_date" value="<%=begin_date%>">
		<a href="javascript:show_calendar(document.forms[0].begin_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		<td width="10%">结束日期</td>
		<td width="20%"><input type="text" name="end_date" value="<%=end_date%>">
		<a href="javascript:show_calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		<td>&nbsp;</td>	
	</tr>	
	<tr>
	    <td >货号</td><td><input type="text" name="queryItemCode" value="<%=item_code%>" >
		<a href="javascript:getProduct();">
		<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
		</a></td>	
	    
		<td>产品类别</td><td>
	           <input type="text" name="categoryID" value="<%=category_id%>"> 
	           <a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a>
	        </td>		
	</tr>
	<tr>
	        <td colspan="4">产品类型&nbsp;&nbsp;
		    <select name="item_type">
		      <option value="">--全部--</option>
		      <option value="0" >辅料</option>
              <option value="1" >普通商品</option>
              <option value="2" >系列商品</option>
              <option value="3" >套装商品</option>
		    </select>
		&nbsp;&nbsp;&nbsp;&nbsp;订单方式&nbsp;&nbsp;
		    <select name="pr_type">
		      <option value="">--全部--</option>
		      <%
		        sql="select * from s_pr_type ";
		        pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			String pr_id=rs.getString("id");
			String pr_name=rs.getString("name");
		      %>
		      <option value="<%=pr_id%>" <%if(pr_type.indexOf(pr_id)==0){%>selected<%}%>><%=pr_name%></option>
		      <%
		      }
		      rs.close();
		      pstmt.close();
		      %>
		    </select>&nbsp;&nbsp;&nbsp;&nbsp;
		发送方式&nbsp;&nbsp;
		    <select name="delivery_type">
		      <option value="">--全部--</option>
		      <option value="1"<%if(delivery_type.equals("1")){%>selected<%}%>>邮寄</option>
		      <option value="3"<%if(delivery_type.equals("3")){%>selected<%}%>>款到发货快递</option>
		      <option value="4"<%if(delivery_type.equals("4")){%>selected<%}%>>邮政EMS</option>
		      <option value="5"<%if(delivery_type.equals("5")){%>selected<%}%>>货到付款快递</option>
		    </select>
		</td>
		<td ><input type="submit" name="search" value=" 查询 ">
		</td>			
	</tr>
	
<input name="flag" type="hidden" value="">	
<input type="hidden" name="tag" value="1">
</table>

</form>
<% 
if(tag.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap id="data">
	<tr>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>产品类型</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>货号</th>		
		<th width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>销售价格</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>销售数量</th>	
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>销售金额</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>成本</th>	
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>毛利</th>			
	</tr>
<%


			if(begin_date.length()>0){
			   condition+=" and a.release_date >= date '"+begin_date+"'";
			}
			if(end_date.length()>0){
			   condition+=" and a.release_date < date '"+end_date+"'+1";
			}
			if(item_code.length()>0){
			   condition+=" and c.itm_code ='"+item_code+"'";
			}
			if(item_type.length()>0){
			   condition+=" and d.itm_type ="+item_type;
			}
			if(category_id.length()>0){
			   condition+="  and d.category_id in  (select catalog_id from prd_item_category connect by parent_id = prior catalog_id start with catalog_id="+category_id+")";
			}
			if(pr_type.length()>0){
			   condition+=" and a.pr_type ="+pr_type;
			}
			if(delivery_type.length()>0){
			   condition+=" and a.delivery_type ="+delivery_type;
			}
																					
		 		 
		String count_sql="";			

		count_sql+=" select decode(d.itm_type,0,'辅料',1,'普通产品',2,'系列产品',3,'套装产品','其他') as itm_type,";
		count_sql+=" c.itm_code,d.itm_name,b.price,sum(b.quantity) sum_qty,sum(b.quantity*b.price) sum_price,sum(c.itm_cost*b.quantity) sum_cost, ";
		count_sql+="  sum(b.quantity*b.price) - sum(c.itm_cost*b.quantity) sum_profit ";
		count_sql+=" from ord_headers a,ord_lines b ,prd_item_sku c,prd_item d";
		count_sql+=" where a.id=b.order_id and b.sku_id=c.sku_id and c.itm_code=d.itm_code ";
		count_sql+=" and a.status>0 and b.status>0 "+condition;
		count_sql+=" group by d.itm_type,c.itm_code,d.itm_name,b.price ";

        request.getSession().setAttribute("excel_name","产品销售明细");
        request.getSession().setAttribute("excel_title","产品类型,货号,产品名称,销售价格,销售数量,销售金额,成本,毛利");
		request.getSession().setAttribute("excel_sql",count_sql);

		//out.println(count_sql);
			pstmt=conn.prepareStatement(count_sql);
			rs=pstmt.executeQuery();
			
			//销售金额 成本 毛利 
			double sum_price=0;
			double sum_unpurchasing_cost=0;

				while(rs.next()){ 
				String rs_item_type_name=rs.getString("itm_type");
				String rs_item_code=rs.getString("itm_code");
				String rs_item_name=rs.getString("itm_name");
				double rs_price=rs.getDouble("price");
				int rs_quantity=rs.getInt("sum_qty");
				double rs_unpurchasing_cost=rs.getDouble("sum_cost");
				sum_price=sum_price+rs_price*rs_quantity;
				sum_unpurchasing_cost=sum_unpurchasing_cost+rs_unpurchasing_cost;
%>
	<tr>
		<td bgcolor="#FFFFFF"><%=rs_item_type_name%></td>
		<td bgcolor="#FFFFFF"><%=rs_item_code%></td>
		<td bgcolor="#FFFFFF" align="left"><%=rs_item_name%></td>
			
		<td bgcolor="#FFFFFF"><%=rs_price%></td>
		<td bgcolor="#FFFFFF"><%=rs_quantity%></td>		
		<td bgcolor="#FFFFFF"><%=Math.round(rs_price*rs_quantity*100.0)/100.0%></td>
		<td bgcolor="#FFFFFF"><%=rs_unpurchasing_cost%></td>	
		<td bgcolor="#FFFFFF">
		<%
		if(Math.round((rs_price*rs_quantity-rs_unpurchasing_cost)*100.0)/100.0<0){
		%>
		<font color="red"><%=Math.round((rs_price*rs_quantity-rs_unpurchasing_cost)*100.0)/100.0%></font>
		<%}else{%>
		<%=Math.round((rs_price*rs_quantity-rs_unpurchasing_cost)*100.0)/100.0%>
		<%}%>
		</td>		

	</tr>
	
<%					
	
	
	} 

%>
	<tr align="center">
		<td colspan="5" bgcolor="#FFFFFF"></td>
		
		<td bgcolor="#FFFFFF"><%=String.valueOf(Math.round(sum_price*100.0)/100.0)%></td>
		<td bgcolor="#FFFFFF"><%=String.valueOf(Math.round(sum_unpurchasing_cost*100.0)/100.0)%></td>	
		<td bgcolor="#FFFFFF"><%=String.valueOf(Math.round((sum_price-sum_unpurchasing_cost)*100.0)/100.0)%></td>		

	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td  align="center">
		
		<a href="excel_export.jsp" target=_blank>
		导出记录</a>
		
	</td>
</tr>
</table>

<%
}//if(tag)
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
