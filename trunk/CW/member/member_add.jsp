<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<script language="JavaScript">

function listCity() {
    if(document.forms[0].province.value=="") {
        alert("请选择省份");
        document.forms[0].province.focus();
        return;
    }
    var ajax = new Ajax("/magicAjax.do?type=getCitysByProvince&province=" + document.forms[0].province.value,"",this.showCity);
	ajax.postRequest();
}

function showCity(response) {
    //alert(response);
    clearOption(document.forms[0].city);
    
    var city;
    var arr = response.split(",");
    for(var i=0; i < arr.length; i++){
        var arrTemp = arr[i].split("-");
		addOption(document.forms[0].city, arrTemp[0], arrTemp[1]);
		if(i==0) {city = arrTemp[0]};
	}
	
	var ajax = new Ajax("/magicAjax.do?type=getSectionByCity&city=" + city,"",this.showSection);
	ajax.postRequest();
}
function listSection() {
    if(document.forms[0].city.value=="") {
        alert("请选择城市");
        document.forms[0].city.focus();
        return;
    }
    var ajax = new Ajax("/magicAjax.do?type=getSectionByCity&city=" + document.forms[0].city.value,"",this.showSection);
	ajax.postRequest();
}

function showSection(response) {
    //alert(response);
    clearOption(document.forms[0].section);
    
    var arr = response.split(",");
    for(var i=0; i < arr.length; i++){
		var arrTemp = arr[i].split("-");
		addOption(document.forms[0].section, arrTemp[0], arrTemp[1]);
	}
}

function selectCity(obj) {
	document.forms[0].Delivery_address.value = document.forms[0].address.value + obj.value;
}
function addPostcode() {
	var obj = document.forms[0].postcode;
	var obj2 =  document.forms[0].realPostcode;
	if (obj.value == "" || obj.value.length != 6)

	{
			document.forms[0].addPostBtn.disabled = true;
			alert("邮编必须是6位!");
			
			return;
	}
	if (obj2.value == obj.value)
	{
		document.forms[0].addPostBtn.disabled = true;
		alert("此邮编无需添加!");
			
		return;
	}
	document.forms[0].action="/member/memberAddPostcode.do";
	document.forms[0].addPostBtn.disabled = true;
	document.forms[0].submit();
}
//异步函数
function callAjax() {
	var obj = document.forms[0].postcode;
	//if(event.keyCode == 13) {
		if (obj.value == "" || obj.value.length != 6)

		{
			//document.forms[0].addPostBtn.disabled = true;
			alert("邮编必须是6位!");
			document.forms[0].postcode.select();
			return;
		}
		
		var ajax = new Ajax("/magicAjax.do?type=getAddressByPostcode&postcode=" + escape(obj.value),"",this.updatePage);
		ajax.postRequest();
	//}
	
}
//回调函数
function updatePage(response) {
	if (response == "error") { //error
	
		//document.getElementById("msg").innerText = "邮编不存在!";
		//document.forms[0].addPostBtn.disabled = true;
	} else {
		document.forms[0].Delivery_address.value = response;
			
	}
   	document.forms[0].Delivery_address.focus();
}

function winFocus(){
	document.forms[0].NAME.focus();
	return true;
	
}

function getItem() {
	if(document.forms[0].MSC_CODE.value==""){
	alert('msc号不能为空!!');
	document.forms[0].MSC_CODE.select();
	return false;
	}
	window.open("memberQueryRecruitProduct.do?msc_code="+document.forms[0].MSC_CODE.value,"选择入会礼品","toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=650,top=0");
}
function addSubmit() {
	if(document.forms[0].CARD_ID.value!=""){
		if(isNaN(document.forms[0].CARD_ID.value)){
		alert('会员号码只能为数字!');
		document.forms[0].CARD_ID.select();
		return;
		}
		
	}
	if(document.forms[0].NAME.value==""){
		alert('会员姓名不能为空!');
		document.forms[0].NAME.select();
		return;
	}

	if (!document.forms[0].GENDER[0].checked && !document.forms[0].GENDER[1].checked)
	{
		alert('会员性别不能为空!');
		return;
	}

	if(document.forms[0].BIRTHDAY.value==""||document.forms[0].BIRTHDAY.value.length!=8||isNaN(document.forms[0].BIRTHDAY.value)){
		alert('出生日期不能为空并且长度为8位数字!!');
		document.forms[0].BIRTHDAY.select();
		return;
	}

	if(document.forms[0].BIRTHDAY.value.substring(4,6)<1||document.forms[0].BIRTHDAY.value.substring(4,6)>12){
		alert('出生日期的月份必须介于1-12之间!');
		document.forms[0].BIRTHDAY.select();
		return;
	}

	if(document.forms[0].BIRTHDAY.value.substring(6,8)<1||document.forms[0].BIRTHDAY.value.substring(6,8)>31){
		alert('出生日期的日子必须介于1-31之间!');
		document.forms[0].BIRTHDAY.select();
		return;
	}

	if(document.forms[0].TELEPHONE.value==""){
		alert('主要电话不能为空!');
		document.forms[0].TELEPHONE.select();
		return;
	}

	if(document.forms[0].postcode.value==""||document.forms[0].postcode.value.length!=6||isNaN(document.forms[0].postcode.value)){
		alert('邮编不能为空并且长度为6位数字!');
		document.forms[0].postcode.select();
		return;
	}
	
	if(document.forms[0].section.value==""){
		alert('会员区域不能为空!');
		document.forms[0].section.focus();
		return;
	}

	if(document.forms[0].delivery_address.value==""){
		alert('会员地址不能为空!');
		document.forms[0].delivery_address.select();
		return;
	}

	if(document.forms[0].MSC_CODE.value==""){
		alert('会员MSC不能为空!');
		document.forms[0].MSC_CODE.select();
		return ;
	}		
									
    /*
	if(document.forms[0].EVENT_DATE.value==""){
		alert('事件日期不能为空!');
		document.forms[0].EVENT_DATE.select();
		return;
	}
	 var edate = document.forms[0].EVENT_DATE.value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	 if(edate==null){
		alert('请按格式填写事件日期!');
		document.forms[0].EVENT_DATE.select();
		return ;
	 }*/
	 
	document.forms[0].submitBtn.disabled=true;
	document.forms[0].submit();
}

function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
function setItemInfo(str) {
	document.forms[0].GIFT_ID.value = str;
}

//将光标定位在文字末尾
function rolltoEnd() 
{ 
	 var e = event.srcElement; 
	 var r =e.createTextRange(); 
	 r.moveStart('character',e.value.length); 
	 r.collapse(true); 
	 r.select(); 
} 


function getOpenwinMemberValue(ret) {
     document.forms[0].memgetmemID.value = ret;
}

function opentaobao()
{
	 window.location.href = "member_import_taobao.jsp";
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" STYLE="margin:0,10,10,10">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">新增会员</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<br>
<html:form action="/member_add.do" method="post" >

<table  border=0 cellspacing=1 cellpadding=1  width="800" align="center" >
	<tr>
		<td noWrap  class="inputLabel">会员号：</td>
		<td width="*%" align="left" colspan="3">
			<html:text property="CARD_ID"  maxlength="10" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td  noWrap  class="inputLabel">会员姓名：</td>
		<td  align="left"  colspan="3">
			<html:text property="NAME" maxlength="40" />&nbsp;<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;
		    性别：
		    <html:radio property="GENDER" value="M"/>男&nbsp;
		    <html:radio property="GENDER" value="F"/>女&nbsp;
			<font color=red>*</font>&nbsp;
		</td>
	</tr>
	<tr>
		<td noWrap   class="inputLabel">出生日期：</td>
		<td width="*%" align="left" colspan="3">
			<html:text property="BIRTHDAY" maxlength="8"/>&nbsp;<font color=red>*</font>&nbsp;(日期格式:YYYYMMDD 示例：20050101)
			</td>
		
	</tr>
	
	<tr>
	    <td noWrap  class="inputLabel" >邮政编码：</td>
		<td width="*%" align="left">
		
			<html:text property="postcode" maxlength="6" />&nbsp;<font color=red>*</font>&nbsp;&nbsp;
		</td>
		<td noWrap  class="inputLabel" >省市区：</td>
		<td width="*%" align="left">
		<html:select property="province" onchange="listCity();">
		    <option value="">请选择</option>
			<html:optionsCollection property="provs" />
		</html:select>&nbsp;&nbsp;
		<html:select property="city" onchange="listSection();">
		    <option value="">请选择</option>
			<html:optionsCollection property="citys" />
		</html:select>&nbsp;&nbsp;
		<html:select property="section">
		    <option value="">请选择</option>
			<html:optionsCollection property="sects" />
		</html:select>&nbsp;<font color=red>*</font>
		</td>
	</tr>
    <tr>
		<td noWrap  class="inputLabel" >联系地址：</td>
		<td width="*%" align="left" colspan="3">
		    <html:text property="delivery_address" size="68" onfocus="rolltoEnd()"  />&nbsp;<font color=red>*</font>
		</td>
	</tr>
		
	<tr>
		<td noWrap  class="inputLabel" >联系电话：</td>
		<td  align="left" colspan="3">
		     <html:text property="TELEPHONE" maxlength="50" />(M)&nbsp;<font color=red>*</font>
			 <html:text property="FAMILY_PHONE" maxlength="50" />(H)&nbsp;
			 <html:text property="COMPANY_PHONE" maxlength="50" />(O)&nbsp;（至少输入一个联系电话）
		</td>
		
	</tr>
	<tr>
		<td noWrap  class="inputLabel" >招募MSC：</td>
		<td  align="left">
			<html:text property="MSC_CODE" readonly="true" />&nbsp;<font color=red>*</font> 
			<input type="button" onClick="javascript:winopen('queryActiveList.do?tag=crmuse','选择MSC号')" value="选择MSC" > 
		<td  noWrap  class="inputLabel" >接收目录类型：</td>
			<td  align="left" >
			<html:select property="CATALOG_TYPE" > 
				<html:option value="2" >不需要</html:option> 
				<html:option value="0" >纸面版</html:option> 
				<html:option value="1" >电子版</html:option>
				<html:option value="3" >两者都要</html:option> 
			</html:select>
		</td>		
	</tr>
	<tr>
		<td noWrap  class="inputLabel" >介绍人会员号：</td>
		<td  align="left">
			<html:text property="memgetmemID" maxlength="10" /> &nbsp;
			<input type="button" onClick="javascript:winopen('queryList.do','选择介绍人会员号')" value=" 选 择 "> 	
		<td  noWrap  class="inputLabel" >电子邮箱：</td>
		<td  align="left" >
			<html:text property="EMAIL" maxlength="100" />
		</td>		
	</tr>
	<!--
	<tr>
      	<td noWrap  class="inputLabel" >经办人员：</td>
			
      	<td align="left" nowarp>
      		<bean write name="memberForm" property="creator_name"/>
        </td>
        <td noWrap  class="inputLabel" >事件日期：</td>
		<td align="left" >
			<bean:write name="memberForm" property="event_date" /> (格式:YYYY-MM-DD)
		</td>
	</tr>-->	
	<tr>
		<td noWrap  class="inputLabel" >备注：</td>
		<td align="left" colspan="3">
			<html:textarea cols="90" rows="3" property="COMMENTS" />
		</td>
	</tr>
	<tr>
		<td noWrap  class="inputLabel" >淘宝旺旺号：</td>
		<td align="left" colspan="3">
			<html:text  property="taobaoWangId" />
		</td>
</tr>
	<tr height="60" valign="middle">
		<td align="center" colspan="4"><br>
		<input type="button"  value=" 确定 " name="submitBtn" onclick="addSubmit();"> &nbsp;
		<input type="reset" class="button2" value=" 取消 " >&nbsp;
		<input type="button" value="淘宝会员导入" onclick='opentaobao();' />
	</tr>
</table>
<htm:hidden property="IS_ORGANIZATION" value="0"/>

</html:form>

</body>
</html>
