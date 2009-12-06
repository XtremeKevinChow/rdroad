<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function getOpenwinValue(ret){
	
	var frm = document.forms[0];
	var index = frm.whichLine.value;

	
	if (typeof(frm.itemCode.length) == "undefined")
	{
		frm.itemCode.value = ret[1];
		//frm.itemName.value = ret[2];

		detailTable.rows[index].cells[1].innerText = ret[2];

	} else {
		
		frm.itemCode[index - 1].value = ret[1];
		//frm.itemName[index - 1].value = ret[2];
		detailTable.rows[index].cells[1].innerText = ret[2];
	}
}

function submit_f() {
	
	var frm = document.forms[0];

	if (document.forms[0].postDate.value !="" && !checkDate_f(document.forms[0].postDate))
	{
		alert("日期格式不正确");
		document.forms[0].postDate.focus();
		return;
	}

	if (frm.itemCode != null)
	{
		if (typeof(frm.itemCode.length) == "undefined")
		{
			if (frm.itemCode.value == null || frm.itemCode.value == "")
			{
				alert("产品代码不能为空");
				frm.itemCode.focus();
				return;
			}
			if (frm.goodQty.value == "" && frm.badQty.value == "")
			{
				alert("请输入数量");
				frm.goodQty.focus();
				return;
			}
			if (frm.goodQty.value != null && frm.goodQty.value != "" && isNaN(frm.goodQty.value))
			{
				alert("入库数量(完好)必须为数字");
				frm.goodQty.focus();
				return;
			}
			if (frm.badQty.value != null && frm.badQty.value != "" && isNaN(frm.badQty.value))
			{
				alert("入库数量(破损)必须为数字");
				frm.badQty.focus();
				return;
			}

		} else {
			
			for ( var i = 0 ; i < frm.itemCode.length ; i ++ )
			{
				if (frm.itemCode[i].value == null || frm.itemCode[i].value == "")
				{
					alert("产品代码不能为空");
					frm.itemCode[i].focus();
					return;
				}
				//判断产品代码是否有重复
				for ( var j = i + 1 ; j < frm.itemCode.length ; j ++ )
				{
					if (frm.itemCode[i].value == frm.itemCode[j].value)
					{
						alert("第"+(parseInt(i)+1)+"行和第"+(parseInt(j)+1)+"行的产品代码重复了");
						frm.itemCode[j].select();
						return;
					}
				}
				if (frm.goodQty[i].value == "" && frm.badQty[i].value == "")
				{
					alert("请输入数量");
					frm.goodQty[i].focus();
					return;
				}
				if (frm.goodQty[i].value != null && frm.goodQty[i].value != "" && isNaN(frm.goodQty[i].value))
				{
					alert("入库数量(完好)必须为数字");
					frm.goodQty[i].focus();
					return;
				}
				if (frm.badQty[i].value != null && frm.badQty[i].value != "" && isNaN(frm.badQty[i].value))
				{
					alert("入库数量(破损)必须为数字");
					frm.badQty[i].focus();
					return;
				}
				
			}
		}
	}
	

	document.forms[0].submit();
}

function checkDate_f(obj) {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(obj.value);
}

function delete_f(row) {

	if (detailTable.rows.length <= 2)
	{
		alert("至少要留一条明细在页面上");
		return;
	}
	detailTable.deleteRow(row);
}

function additem_f() {

	//添加一行
	var newTr = detailTable.insertRow();

	//添加5列
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	var newTd2 = newTr.insertCell();
	var newTd3 = newTr.insertCell();
	var newTd4 = newTr.insertCell();

	//设置列内容和属性 

	newTd0.innerHTML = "<input name='itemCode' size='8'/><input name=\"query\" type=\"button\" value=\"查询\" onclick=\"openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);document.forms[0].whichLine.value=this.parentElement.parentElement.rowIndex;\">"; 
	newTd1.innerHTML = ""; 
	newTd2.innerHTML = "<input name='goodQty' size='8'/>"; 
	newTd3.innerHTML = "<input name='badQty' size='8'/>"; 
	newTd4.innerHTML= "<input type='button' value='删除' onclick='delete_f(this.parentElement.parentElement.rowIndex);'>";

	newTd0.bgColor = "#f7f7e7";
	newTd1.bgColor = "#f7f7e7";
	newTd2.bgColor = "#f7f7e7";
	newTd3.bgColor = "#f7f7e7";
	newTd4.bgColor = "#f7f7e7";

	newTd0.setAttribute("align", "middle");
	newTd1.setAttribute("align", "left");
	newTd2.setAttribute("align", "middle");
	newTd3.setAttribute("align", "middle");
	newTd4.setAttribute("align", "middle");
}


function addItem(obj) {

	if(trim(obj.value) == "") {
		alert("请选择商品！");
		obj.focus();
		return;
	}
	
	
	document.forms[0].action = "../order/orderAddSecond.do?type=addItem";
	document.forms[0].actionType.value = "addItem";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/nsInbound?type=modify" method="post">
<html:hidden property="rkNO" />
<html:hidden property="writeDate" />
<input type="hidden" name="whichLine">
<table width="750.0" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	<td> 
		<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">库存管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">三无单修改</font><font color="838383">
		<table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<tr background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<td height="1" width=100% background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
			</tr>
		</table>
	</td>
	</tr>
</table>

<table  border=0 cellspacing=1 cellpadding=1  width="600">
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;邮编</td>
		<td width="30%" align="left" >
			<html:text property="postcode" size="10"/>&nbsp;
		</td>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;客户姓名</td>
		<td align="left" width="30%">
			<html:text property="memberName"  size="10"/>&nbsp;
		</td>
	</tr>
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;地址</td>
		<td  align="left" colspan="3">
			<html:text property="address" size="60"/>&nbsp;
		</td>
		
	</tr>
	
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;退货原因</td>
		<td  align="left" >
			<html:select property="returnReason" style="width:160">
			<option value="">-- 请选择 --</option>
				<html:optionsCollection name="nsInboundForm" property="rrList" value="code" label="name"/> 
		</html:select>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;邮戳日期</td>
		<td align="left" >
			<html:text property="postDate"  size="10"/><a href="javascript:calendar(document.forms[0].postDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;包裹号</td>
		<td bgcolor="#FFFFFF">
			<html:text property="postNum" size="10"/>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;邮资</td>
		<td align="left" >
			<html:text property="postage"  size="10"/>
		</td>
	</tr>

	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;其他特征</td>
		<td align="left" colspan="3">
			<html:text property="otherSpecial" size="60"/>&nbsp;
		</td>
		
	</tr>
	<tr height="25">
		
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;备注</td>
		<td align="left" colspan="3">
			<html:textarea property="remark" cols="50" rows="3"/>&nbsp;
		</td>
	</tr>
	
</table>
<br>
<bean:define id="list" name="nsInboundForm" property="items"/>
<table id="detailTable" style="display:block" width="95%" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</th>
		<th width="40%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>可用数量</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>破损数量</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>操作</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<input name="itemCode" size="8" value="<bean:write name="list" property="itemCode"/>"><input name="query" type="button" value="查询" onclick="openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);document.forms[0].whichLine.value=this.parentElement.parentElement.rowIndex;">
		</td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="itemName"/></td>
		<td class=OraTableCellText noWrap align=middle ><input name="goodQty" size="8" value="<bean:write name="list" property="useQty"/>"></td>
		<td class=OraTableCellText noWrap align=middle ><input  name="badQty" size="8" value="<bean:write name="list" property="disQty"/>"></td>
		<td class=OraTableCellText noWrap align=middle ><input type="button" value="删除" onclick="delete_f(this.parentElement.parentElement.rowIndex);"></td>
	</tr>
	</logic:iterate>
</table>
<TABLE align="center">
	<tr height="40">
		<td align="center" colspan=4>
			<input type="button" class="button2" value="新增明细" onclick="additem_f()">&nbsp;
			<input type="button" class="button2" value=" 确定 " onclick="submit_f()">&nbsp;
			<input type="button" class="button2" value=" 返回 " onclick="history.back()">&nbsp;
		</td>
	</tr>
</TABLE>
</html:form>
</body>
</html>
