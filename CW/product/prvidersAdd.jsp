<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>������Ա��ϵ����ϵͳ</title>
<script language="JavaScript">
function addSubmit() {
if(document.providerForm.providerName.value==""){
alert('���Ʋ���Ϊ��!');
document.providerForm.providerName.focus();
return false;
}
if(document.providerForm.providerTitle.value==""){
alert('��Ʋ���Ϊ��!');
document.providerForm.providerTitle.focus();
return false;
}
if(document.providerForm.providerTitle.value.length>30){
alert('�������̫��!');
document.providerForm.providerTitle.focus();
return false;
}
if(document.providerForm.providerAddress.value==""){
alert('��˾��ַ����Ϊ��!');
document.providerForm.providerAddress.focus();
return false;
}
if(document.providerForm.telZip.value==""||document.providerForm.telZip.value.length<3||isNaN(document.providerForm.telZip.value)){
alert('�绰���Ų���Ϊ��,���ұ���Ϊ���ȵ���3������');
document.providerForm.telZip.focus();
return false;
}
document.providerForm.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript" src="utils.js"></script>
<script src="calendar.js"></script>
<script src="go_top.js"></script>
<script language="JavaScript" src="common.js"></script>

<br>
<table width="750.0" border=0 cellspacing=1 cellpadding=5 >
  <tr>
    <td><span class="OraHeader">����&nbsp;��Ӧ��</span>
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        <tr background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
          <td height="1" width=100% background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="750.0" border=0 cellspacing=1 cellpadding=5   >
  <html:form  action="/prvidersAddok.do" method="post">
    <tr>
      <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;����:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="providerName" value="" size="50">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;���:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="providerTitle" value="" maxlength="30" size="50">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;��˾��ַ:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="providerAddress" value="" size="80">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" >&nbsp;�����ַ:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="goodsAdd" value="" size="80">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" >&nbsp;�ʼĵ�ַ:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="postAdd" value="" size="80">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;��Ӧ�̷���:</td>
      <td align="left" width="85%" nowarp cols="5">
			  <select name="providerCategory">
				  <option value="01" >������</option>
					<option value="02" selected>������</option>
				</select>
			</td>
		</tr>
    <tr>
      <td width="15%" align="right" >&nbsp;��������:</td>
      <td align="left" width="85%" nowarp cols="5">
			  <input type="text" name="moneyDays" value="0">&nbsp;&nbsp;��
			</td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;�����ʽ:</td>
      <td align="left" width="85%" nowarp cols="5">
				<select name="pickUp">
				  <option value="" selected>��ѡ��...</option>
				  <option value="����" >����</option>
					<option value="�ͻ�" >�ͻ�</option>
					<option value="����������" >����������</option>
				</select>
		  </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;��������:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="payDays" value="0">&nbsp;&nbsp;��
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;������ʽ:</td>
      <td align="left" width="85%" nowarp cols="5">
				<select name="cooperate">
				  <option value="" selected>��ѡ��...</option>
				  <option value="����" >����</option>
					<option value="����" >����</option>
					<option value="�ֽ�" >�ֽ�</option>
				</select>
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;�˻�����:</td>
      <td align="left" width="85%" nowarp cols="5">
				<select name="returnPolicy">
				<option value="" selected>��ѡ��...</option>
				  <option value="ȫ��" >ȫ��</option>
					<option value="������" >������</option>
					<option value="������" >������</option>
				</select>
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;�˻�����:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="returnBit" value="0.0">&nbsp;&nbsp;%
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;��汣����:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="stockProtect" value="0">&nbsp;&nbsp;��
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;�۸񱣻���:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="priPtotect" value="0">&nbsp;&nbsp;��
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;��Ʒ����:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="providerManager" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;��Ʊ����:</td>
      <td align="left" width="85%" nowarp cols="5">
				<select name="invocieType">
				  <option value="" selected>��ѡ��...</option>
					<option value="A" >��ͨ��Ʊ</option>
					<option value="B" >��Ʊ</option>
				</select>
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;��ϵ��:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="connecter" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;�ɹ�רԱ:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="purchase" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;ʡ��:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="province" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;����:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="city" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;�ʱ�:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="providerZip" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;�绰����:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="telZip" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;˰��:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="taxRate" value="0.0">&nbsp;&nbsp;%
			</td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;�绰1:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="telephone" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;�绰2:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="telephone2" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;����:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="fax" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;E-mail:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="email" value="" size="30">
      </td>
		</tr>
    <tr>
	    <td width="15%" align="right" >��ע</td>
	    <td align="left" width="85%" nowarp>
	      <textarea cols=30 rows=3 name="comments"></textarea>
      </td>
    </tr>
    <tr>
		  <td colspan=6>
        <table width="750.0" border=0 cellspacing=0 cellpadding=0 background="./images/headerlinepixel_onwhite.gif" HEIGHT=1 WIDTH=1>
          <tr background="./images/headerlinepixel_onwhite.gif" HEIGHT=1 WIDTH=1>
            <td  height=1 width="750.0" background="./images/headerlinepixel_onwhite.gif" HEIGHT=1 WIDTH=1><img src="./images/headerlinepixel_onwhite.gif" HEIGHT=1 WIDTH=1></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td align="right" colspan=6>
        <input type="button" class="button2" name="btn_submit" id="btn_submit" value="�ύ" onClick="addSubmit()">&nbsp;
        <input type="button" class="button2" value="ȡ��" onClick="history.back();">
			</td>
    </tr>
  </html:form>
</table>
</body>
</html>