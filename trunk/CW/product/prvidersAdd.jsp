<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>佰明会员关系管理系统</title>
<script language="JavaScript">
function addSubmit() {
if(document.providerForm.providerName.value==""){
alert('名称不能为空!');
document.providerForm.providerName.focus();
return false;
}
if(document.providerForm.providerTitle.value==""){
alert('简称不能为空!');
document.providerForm.providerTitle.focus();
return false;
}
if(document.providerForm.providerTitle.value.length>30){
alert('简称字数太多!');
document.providerForm.providerTitle.focus();
return false;
}
if(document.providerForm.providerAddress.value==""){
alert('公司地址不能为空!');
document.providerForm.providerAddress.focus();
return false;
}
if(document.providerForm.telZip.value==""||document.providerForm.telZip.value.length<3||isNaN(document.providerForm.telZip.value)){
alert('电话区号不能为空,并且必须为长度等于3的数字');
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
    <td><span class="OraHeader">增加&nbsp;供应商</span>
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
      <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;名称:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="providerName" value="" size="50">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;简称:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="providerTitle" value="" maxlength="30" size="50">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;公司地址:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="providerAddress" value="" size="80">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" >&nbsp;提货地址:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="goodsAdd" value="" size="80">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" >&nbsp;邮寄地址:</td>
      <td align="left" width="85%" nowarp cols="5">
        <input type="text" name="postAdd" value="" size="80">
      </td>
		</tr>
    <tr>
      <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;供应商分类:</td>
      <td align="left" width="85%" nowarp cols="5">
			  <select name="providerCategory">
				  <option value="01" >出版社</option>
					<option value="02" selected>代理商</option>
				</select>
			</td>
		</tr>
    <tr>
      <td width="15%" align="right" >&nbsp;结算周期:</td>
      <td align="left" width="85%" nowarp cols="5">
			  <input type="text" name="moneyDays" value="0">&nbsp;&nbsp;天
			</td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;提货方式:</td>
      <td align="left" width="85%" nowarp cols="5">
				<select name="pickUp">
				  <option value="" selected>请选择...</option>
				  <option value="自提" >自提</option>
					<option value="送货" >送货</option>
					<option value="第三方配送" >第三方配送</option>
				</select>
		  </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;付款周期:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="payDays" value="0">&nbsp;&nbsp;天
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;合作方式:</td>
      <td align="left" width="85%" nowarp cols="5">
				<select name="cooperate">
				  <option value="" selected>请选择...</option>
				  <option value="代销" >代销</option>
					<option value="经销" >经销</option>
					<option value="现结" >现结</option>
				</select>
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;退货政策:</td>
      <td align="left" width="85%" nowarp cols="5">
				<select name="returnPolicy">
				<option value="" selected>请选择...</option>
				  <option value="全退" >全退</option>
					<option value="部分退" >部分退</option>
					<option value="不能退" >不能退</option>
				</select>
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;退货比例:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="returnBit" value="0.0">&nbsp;&nbsp;%
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;库存保护期:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="stockProtect" value="0">&nbsp;&nbsp;天
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;价格保护期:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="priPtotect" value="0">&nbsp;&nbsp;天
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;产品经理:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="providerManager" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;发票类型:</td>
      <td align="left" width="85%" nowarp cols="5">
				<select name="invocieType">
				  <option value="" selected>请选择...</option>
					<option value="A" >普通发票</option>
					<option value="B" >增票</option>
				</select>
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;联系人:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="connecter" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;采购专员:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="purchase" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;省份:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="province" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;城市:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="city" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;邮编:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="providerZip" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" ><font color="#FF3300">*</font>&nbsp;电话区号:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="telZip" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;税率:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="taxRate" value="0.0">&nbsp;&nbsp;%
			</td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;电话1:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="telephone" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;电话2:</td>
      <td align="left" width="85%" nowarp cols="5">
				<input type="text" name="telephone2" value="">
      </td>
		</tr>
		<tr>
		  <td width="15%" align="right" >&nbsp;传真:</td>
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
	    <td width="15%" align="right" >备注</td>
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
        <input type="button" class="button2" name="btn_submit" id="btn_submit" value="提交" onClick="addSubmit()">&nbsp;
        <input type="button" class="button2" value="取消" onClick="history.back();">
			</td>
    </tr>
  </html:form>
</table>
</body>
</html>