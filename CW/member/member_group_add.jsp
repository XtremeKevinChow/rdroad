<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/Ajax.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">

function initFocus() {
	document.forms[0].compayName.focus();
}
function check_f() {

	if(document.forms[0].compayName.value==""){
		alert('��˾���Ʋ���Ϊ��!');
		document.forms[0].compayName.focus();
		return false;
	}

	if(document.forms[0].telephone.value==""){
		alert('��ϵ�绰����Ϊ��!');
		document.forms[0].telephone.focus();
		return false;
	}
	if(document.forms[0].telephone2.value==""){
		alert('��ϵ�绰������Ϊ��!');
		document.forms[0].telephone2.focus();
		return false;
	}

	if(document.forms[0].postcode.value==""||document.forms[0].postcode.value.length!=6||isNaN(document.forms[0].postcode.value)){
		alert('�ʱ಻��Ϊ�ղ��ҳ���Ϊ6λ����!');
		document.forms[0].postcode.focus();
		return false;
	}
	if(document.forms[0].relationPerson.value==""){
		alert('��ϵ�˲���Ϊ��!');
		document.forms[0].relationPerson.focus();
		return false;
	}
	if(document.forms[0].address.value==""){
		alert('��Ա��ַ����Ϊ��!');
		document.forms[0].address.focus();
		return false;
	}
}

function listCity() {
    if(document.forms[0].province.value=="") {
        alert("��ѡ��ʡ��");
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
        alert("��ѡ�����");
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

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="initFocus()">
<html:form method="post" action="/memberGroup.do?type=add" onsubmit="return check_f();">
<table width="100%" border="0" cellspacing="1" cellpadding="0">
    <tr>
      	
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font>
			<font color="838383">��></font><font color="838383">�����Ա����</font>
			<font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<table  border=0 cellspacing=1 cellpadding=1  width="700" >
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�����Ա����</td>
		<td align="left">
			<html:text property="groupCode" readonly="true"/><font color=red>(ϵͳ����)</font>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;��˾����</td>
		<td  align="left" >
			<html:text property="compayName"/>
		</td>
	</tr>
	
	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��ϵ�绰</td>
		<td  align="left" >
			<html:text property="telephone"/>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;��ϵ�绰��</td>
		<td  align="left" >
			<html:text property="telephone2"/>
		</td>
	</tr>
	

	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;�ʱ�</td>
		<td align="left" >
			<html:text property="postcode"/>
		</td>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��ϵ��</td>
		<td align="left">
			<html:text property="relationPerson"/>
		</td>
	</tr>
	<tr>
	    <td align="right" class="OraTableRowHeader" noWrap  >ʡ������</td>
		<td colspan="3" width="*%" align="left">
		<html:select property="province" onchange="listCity();">
		    <option value="">��ѡ��</option>
			<html:optionsCollection property="provs" />
		</html:select>&nbsp;&nbsp;
		<html:select property="city" onchange="listSection();">
		    <option value="">��ѡ��</option>
			<html:optionsCollection property="citys" />
		</html:select>&nbsp;&nbsp;
		<html:select property="section">
		    <option value="">��ѡ��</option>
			<html:optionsCollection property="sects" />
		</html:select>&nbsp;<font color=red>*</font>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��˾��ַ</td>
		<td align="left" colspan="3">
			<html:textarea property="address" cols="69" rows="3"/>
		</td>
	</tr>
	
	<tr height="40">
		<td align="center" colspan=4>
		<input name = "submitButton" id="submitButton" type="submit" class="button2" value=" ȷ�� ">&nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
	</tr>
</table>
</html:form>

</body>
</html>
