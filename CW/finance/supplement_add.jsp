<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>

<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%@ page import ="java.text.SimpleDateFormat"%>

<%
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  String s1=sdf.format(new java.util.Date());
  String ship_no=request.getParameter("ship_no");
      ship_no=(ship_no==null)?"":ship_no;
  String tag=request.getParameter("tag");
      tag=(tag==null)?"":tag; 
  String redelivery_type=request.getParameter("redelivery_type");
      redelivery_type=(redelivery_type==null)?"1":redelivery_type;   

      
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
    
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>


<SCRIPT LANGUAGE="JavaScript">
<!--
function getOpenwinValue(ret){
	
	var frm = document.forms[0];
	var index = frm.whichLine.value;

	
	if (typeof(frm.new_item_code.length) == "undefined")
	{
		frm.new_item_code.value = ret[1];

		DataTable.rows[index].cells[6].innerText = ret[2];

	} else {

		frm.new_item_code[index - 1].value = ret[1];

		DataTable.rows[index].cells[6].innerText = ret[2];
	}
}

function select(obj,rowid){  
	
        if(obj.value=="2"){ 
	        if(typeof(document.forms[0].rs_item_code.length)== "undefined") {
	          
	        	document.form.new_item_code.value=document.form.rs_item_code.value; 
	        } else {
	        
	       		 document.form.new_item_code[rowid-1].value=document.form.rs_item_code[rowid-1].value;  
	       		 document.form.new_item_code[rowid-1].readOnly=true;
	       	}	
        }else{
        document.form.new_item_code[rowid-1].value="";  
        document.form.new_item_code[rowid-1].readOnly=false;
        
        }
	return true;
}
function query_f() {
	
	if (document.forms[0].ship_no.value == "")
	{
		alert("发货单号不能为空");
		document.forms[0].ship_no.focus();
		return;
	}	   
	document.form.action="";
	document.form.submit();
}
function add() {

	if (document.forms[0].require_date.value == "")
	{
		alert("申请日期不能为空");
		document.forms[0].require_date.focus();
		return;
	}
	if(document.forms[0].require_date.value != ""){		
		var r_date = document.forms[0].require_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(r_date==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].require_date.focus();
				return;
		 }		 
	}   

 		var isSelect = false;
		for(var i = 1; i < DataTable.rows.length; i ++) {
				if (DataTable.rows(i).cells(0).children(0)!=null) {
				if (DataTable.rows(i).cells(0).children(0).checked) {
					isSelect = true;
					
				}
			}
		}
	 
	 if(!isSelect) {
		alert("请选择记录");
		return false;
	 }
	document.form.action="supplement_add_ok.jsp";
	document.form.btn_add.disabled = "true";
	document.form.submit();
}

function check(obj,rowid) {
		if(isNaN(obj.value)||obj.value==""||parseInt(obj.value)<=0){
		alert('补发数量必须是大于0的数字!');
		document.form.item_num[rowid-1].select();
		return;
		}
		
}

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">销售管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">补货单新增</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="96%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td width="150">发货单号</td>
		<td  bgcolor="#FFFFFF">
		<input type=text name="ship_no" value="<%=ship_no%>">
		<input type="button" name="btn_query" value=" 查 询 " onclick="query_f();">
		<input type=hidden value="1" name="tag">		
		</td>		
							
	</tr>	
</table>
<p>
<%
if(tag.length()>0){
try{
 int status=0;
 conn = DBManager.getConnection();  
		sql="select a.*,b.id as member_id,b.card_id,b.name from ord_shippingnotices a ";
		sql+="inner join mbr_members b on a.member_id=b.id ";
		sql+="where a.barcode='"+ship_no+"' and a.status>=39 ";			

		pstmt=conn.prepareStatement(sql);

		rs=pstmt.executeQuery();
		String sn_id="";
		String card_id="";
		if(rs.next()){
		sn_id=rs.getString("id");
		card_id=rs.getString("card_id");
		status=rs.getInt("status");
		String name=rs.getString("name");
		String member_id=rs.getString("member_id");
		
				
	%>
	<table width="96%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
		
		<tr>	
			<td width="150">发货单号</td>
			<td  bgcolor="#FFFFFF">
			&nbsp;<font color=red><%=ship_no%><input type=hidden name="ship_id" value="<%=sn_id%>"></a>
			</td>
			<td width="150">会员姓名\会员号</td>
			<td  bgcolor="#FFFFFF">
			&nbsp;<font color=red><%=name%>\<%=card_id%><input type=hidden name="member_id" value="<%=member_id%>"></a>
			</td>			
			
			
								
		</tr>	
		<tr>	
			<td width="150">申请日期</td>
			<td  bgcolor="#FFFFFF">
				<input type="text" name="require_date" size="10"  value="<%=s1%>">
				
				<a href="javascript:calendar(document.forms[0].require_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
			
			</td>		
			<td width="150">原因</td>
			<td  bgcolor="#FFFFFF">
				<select name="redelivery_type1">
					<option value="1">错发</option>
					<option value="2">漏发</option>
					<option value="3">其他</option>
				</select>
			</td>		

		
								
		</tr>	
		<tr>
			<td width="150">是否要求退回相应产品</td>
			<td  bgcolor="#FFFFFF">
			<input type=radio name="is_return_orgin" value="1" checked >是
			<input type=radio name="is_return_orgin" value="0">否
	
	
			</td>			
			<td width="150">是否报销邮资</td>
			<td  bgcolor="#FFFFFF">
			<input type=radio name="is_postage" value="1" checked >是
			<input type=radio name="is_postage" value="0">否
				
			
			</td>	
											
		</tr>	
		<tr>
			<td width="" colspan="4" align="center" bgcolor="#FFFFFF">
			
			<%
		sql="select * from jxc.sto_supplement_mst where ship_id="+sn_id;
			

		pstmt=conn.prepareStatement(sql);

		rs=pstmt.executeQuery();
		if(rs.next()){
		
		%>
		<font color=red>此发货单已经有补货单，请核实。</font>
		<%}%>	&nbsp;<input type="button" name="btn_add" value=" 提 交 " onclick="add();">
			<input type="hidden" name="tag" value="1">
			</td>										
		</tr>			
	</table>
	<%}//if%>
	
	<br>
	<%if(card_id.length()>0){%>
	<table width="96%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap  id="DataTable">
		<tr bgcolor="#FFFFFF" align="center">	
			<td width="50">&nbsp;</td>
			<td width="60">货号</td>
			<td width="180">产品名称</td>
			<td width="60">原数量</td>		
			<td width="60">操作类型</td>
			<td width="100">新货号</td>
			<td width="180">新产品名称</td>
			<td width="">数量</td>
	
		</tr>
		<%
				sql="select a.*,b.item_code,b.name from ord_shippingnotice_lines a ";
				sql+="inner join prd_items b on a.item_id=b.item_id ";
				sql+=" where a.sn_id="+sn_id+" and a.status>=0";			
	 			
		//System.out.println(sql);
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				int i=0;
				while(rs.next()){
				String rs_id=rs.getString("id");
				
				String rs_item_id=rs.getString("item_id");
				String quantity=rs.getString("quantity");
				String rs_item_code=rs.getString("item_code");
				String rs_item_name=rs.getString("name");
	
		%>	
			<tr bgcolor="#FFFFFF">	
			<td><input type=checkbox name="row_num" value="<%=i%>"><input type=hidden name="id" value="<%=rs_id%>"></td>
			<td><%=rs_item_code%><input type=hidden name="rs_item_code" value="<%=rs_item_code%>"></td>
			<td><%=rs_item_name%></td>
			<td align="right"><%=quantity%><input type=hidden name="old_qty" value="<%=quantity%>"></td>
			<td  bgcolor="#FFFFFF">
				<select name="redelivery_type" onchange="select(this,this.parentElement.parentElement.rowIndex)">
					<option value="1">错发</option>
					<option value="2">漏发</option>
					<option value="3">其他</option>
				</select>			
			</td>
			<td>
				<input type=text name="new_item_code" size=8 value="0">
				<input name="query" type="button" value=".." onclick="openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);document.forms[0].whichLine.value=this.parentElement.parentElement.rowIndex;">
			</td>
			<td>
				
			</td>			
			<td>
			<input type=text name="item_num" size=4 value="1" onblur="check(this,this.parentElement.parentElement.rowIndex);">
			</td>

		</tr>	
		<%i++;}//while%>	
	</table>
	<%
	
	}else {//card_id
	//System.out.println(status);
	if(status!=40){
	out.println("发货单不是完成状态");
	}else{
	out.println("发货单不存在");
	
	}
	}%>
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
	
<%}//tag%>


<input type="hidden" name="whichLine">
</form>
</body>
</html>
