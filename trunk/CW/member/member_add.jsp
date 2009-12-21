<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<script language="JavaScript">

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
function addPostcode() {
	var obj = document.forms[0].postcode;
	var obj2 =  document.forms[0].realPostcode;
	if (obj.value == "" || obj.value.length != 6)

	{
			document.forms[0].addPostBtn.disabled = true;
			alert("�ʱ������6λ!");
			
			return;
	}
	if (obj2.value == obj.value)
	{
		document.forms[0].addPostBtn.disabled = true;
		alert("���ʱ���������!");
			
		return;
	}
	document.forms[0].action="/member/memberAddPostcode.do";
	document.forms[0].addPostBtn.disabled = true;
	document.forms[0].submit();
}
//�첽����
function callAjax() {
	var obj = document.forms[0].postcode;
	//if(event.keyCode == 13) {
		if (obj.value == "" || obj.value.length != 6)

		{
			//document.forms[0].addPostBtn.disabled = true;
			alert("�ʱ������6λ!");
			document.forms[0].postcode.select();
			return;
		}
		
		var ajax = new Ajax("/magicAjax.do?type=getAddressByPostcode&postcode=" + escape(obj.value),"",this.updatePage);
		ajax.postRequest();
	//}
	
}
//�ص�����
function updatePage(response) {
	if (response == "error") { //error
	
		//document.getElementById("msg").innerText = "�ʱ಻����!";
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
	alert('msc�Ų���Ϊ��!!');
	document.forms[0].MSC_CODE.select();
	return false;
	}
	window.open("memberQueryRecruitProduct.do?msc_code="+document.forms[0].MSC_CODE.value,"ѡ�������Ʒ","toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=650,top=0");
}
function addSubmit() {
	if(document.forms[0].CARD_ID.value!=""){
		if(isNaN(document.forms[0].CARD_ID.value)){
		alert('��Ա����ֻ��Ϊ����!');
		document.forms[0].CARD_ID.select();
		return;
		}
		
	}
	if(document.forms[0].NAME.value==""){
		alert('��Ա��������Ϊ��!');
		document.forms[0].NAME.select();
		return;
	}

	if (!document.forms[0].GENDER[0].checked && !document.forms[0].GENDER[1].checked)
	{
		alert('��Ա�Ա���Ϊ��!');
		return;
	}

	if(document.forms[0].BIRTHDAY.value==""||document.forms[0].BIRTHDAY.value.length!=8||isNaN(document.forms[0].BIRTHDAY.value)){
		alert('�������ڲ���Ϊ�ղ��ҳ���Ϊ8λ����!!');
		document.forms[0].BIRTHDAY.select();
		return;
	}

	if(document.forms[0].BIRTHDAY.value.substring(4,6)<1||document.forms[0].BIRTHDAY.value.substring(4,6)>12){
		alert('�������ڵ��·ݱ������1-12֮��!');
		document.forms[0].BIRTHDAY.select();
		return;
	}

	if(document.forms[0].BIRTHDAY.value.substring(6,8)<1||document.forms[0].BIRTHDAY.value.substring(6,8)>31){
		alert('�������ڵ����ӱ������1-31֮��!');
		document.forms[0].BIRTHDAY.select();
		return;
	}

	if(document.forms[0].TELEPHONE.value==""){
		alert('��Ҫ�绰����Ϊ��!');
		document.forms[0].TELEPHONE.select();
		return;
	}

	if(document.forms[0].postcode.value==""||document.forms[0].postcode.value.length!=6||isNaN(document.forms[0].postcode.value)){
		alert('�ʱ಻��Ϊ�ղ��ҳ���Ϊ6λ����!');
		document.forms[0].postcode.select();
		return;
	}
	
	if(document.forms[0].section.value==""){
		alert('��Ա������Ϊ��!');
		document.forms[0].section.focus();
		return;
	}

	if(document.forms[0].delivery_address.value==""){
		alert('��Ա��ַ����Ϊ��!');
		document.forms[0].delivery_address.select();
		return;
	}

	if(document.forms[0].MSC_CODE.value==""){
		alert('��ԱMSC����Ϊ��!');
		document.forms[0].MSC_CODE.select();
		return ;
	}		
									
    /*
	if(document.forms[0].EVENT_DATE.value==""){
		alert('�¼����ڲ���Ϊ��!');
		document.forms[0].EVENT_DATE.select();
		return;
	}
	 var edate = document.forms[0].EVENT_DATE.value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	 if(edate==null){
		alert('�밴��ʽ��д�¼�����!');
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

//����궨λ������ĩβ
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">������Ա</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<br>
<html:form action="/member_add.do" method="post" >

<table  border=0 cellspacing=1 cellpadding=1  width="800" align="center" >
	<tr>
		<td noWrap  class="inputLabel">��Ա�ţ�</td>
		<td width="*%" align="left" colspan="3">
			<html:text property="CARD_ID"  maxlength="10" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td  noWrap  class="inputLabel">��Ա������</td>
		<td  align="left"  colspan="3">
			<html:text property="NAME" maxlength="40" />&nbsp;<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;
		    �Ա�
		    <html:radio property="GENDER" value="M"/>��&nbsp;
		    <html:radio property="GENDER" value="F"/>Ů&nbsp;
			<font color=red>*</font>&nbsp;
		</td>
	</tr>
	<tr>
		<td noWrap   class="inputLabel">�������ڣ�</td>
		<td width="*%" align="left" colspan="3">
			<html:text property="BIRTHDAY" maxlength="8"/>&nbsp;<font color=red>*</font>&nbsp;(���ڸ�ʽ:YYYYMMDD ʾ����20050101)
			</td>
		
	</tr>
	
	<tr>
	    <td noWrap  class="inputLabel" >�������룺</td>
		<td width="*%" align="left">
		
			<html:text property="postcode" maxlength="6" />&nbsp;<font color=red>*</font>&nbsp;&nbsp;
		</td>
		<td noWrap  class="inputLabel" >ʡ������</td>
		<td width="*%" align="left">
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
		<td noWrap  class="inputLabel" >��ϵ��ַ��</td>
		<td width="*%" align="left" colspan="3">
		    <html:text property="delivery_address" size="68" onfocus="rolltoEnd()"  />&nbsp;<font color=red>*</font>
		</td>
	</tr>
		
	<tr>
		<td noWrap  class="inputLabel" >��ϵ�绰��</td>
		<td  align="left" colspan="3">
		     <html:text property="TELEPHONE" maxlength="50" />(M)&nbsp;<font color=red>*</font>
			 <html:text property="FAMILY_PHONE" maxlength="50" />(H)&nbsp;
			 <html:text property="COMPANY_PHONE" maxlength="50" />(O)&nbsp;����������һ����ϵ�绰��
		</td>
		
	</tr>
	<tr>
		<td noWrap  class="inputLabel" >��ļMSC��</td>
		<td  align="left">
			<html:text property="MSC_CODE" readonly="true" />&nbsp;<font color=red>*</font> 
			<input type="button" onClick="javascript:winopen('queryActiveList.do?tag=crmuse','ѡ��MSC��')" value="ѡ��MSC" > 
		<td  noWrap  class="inputLabel" >����Ŀ¼���ͣ�</td>
			<td  align="left" >
			<html:select property="CATALOG_TYPE" > 
				<html:option value="2" >����Ҫ</html:option> 
				<html:option value="0" >ֽ���</html:option> 
				<html:option value="1" >���Ӱ�</html:option>
				<html:option value="3" >���߶�Ҫ</html:option> 
			</html:select>
		</td>		
	</tr>
	<tr>
		<td noWrap  class="inputLabel" >�����˻�Ա�ţ�</td>
		<td  align="left">
			<html:text property="memgetmemID" maxlength="10" /> &nbsp;
			<input type="button" onClick="javascript:winopen('queryList.do','ѡ������˻�Ա��')" value=" ѡ �� "> 	
		<td  noWrap  class="inputLabel" >�������䣺</td>
		<td  align="left" >
			<html:text property="EMAIL" maxlength="100" />
		</td>		
	</tr>
	<!--
	<tr>
      	<td noWrap  class="inputLabel" >������Ա��</td>
			
      	<td align="left" nowarp>
      		<bean write name="memberForm" property="creator_name"/>
        </td>
        <td noWrap  class="inputLabel" >�¼����ڣ�</td>
		<td align="left" >
			<bean:write name="memberForm" property="event_date" /> (��ʽ:YYYY-MM-DD)
		</td>
	</tr>-->	
	<tr>
		<td noWrap  class="inputLabel" >��ע��</td>
		<td align="left" colspan="3">
			<html:textarea cols="90" rows="3" property="COMMENTS" />
		</td>
	</tr>
	<tr>
		<td noWrap  class="inputLabel" >�Ա������ţ�</td>
		<td align="left" colspan="3">
			<html:text  property="taobaoWangId" />
		</td>
</tr>
	<tr height="60" valign="middle">
		<td align="center" colspan="4"><br>
		<input type="button"  value=" ȷ�� " name="submitBtn" onclick="addSubmit();"> &nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >&nbsp;
		<input type="button" value="�Ա���Ա����" onclick='opentaobao();' />
	</tr>
</table>
<htm:hidden property="IS_ORGANIZATION" value="0"/>

</html:form>

</body>
</html>