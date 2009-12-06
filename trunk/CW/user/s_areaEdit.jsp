<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {
    if(document.forms[0].areaname.value=="") {
        alert("名称不能为空");
        return false;
    }
    /*if(document.forms[0].city.value=="") {
        alert("请选择省市");
        return false;
    }*/
    return true;
}

function queryCity()
{ 
    document.forms[0].action="s_area.do?type=view";
    document.forms[0].submit();
} 

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">基础数据</font><font color="838383"> 
      		-&gt; </font><font color="838383">直送区域维护</font>
      	</td>
   </tr>
</table>

<html:form  action="/s_area.do?type=update" method="post" onsubmit="return querySubmit();">
<table width="95%" align="center" cellspacing="1" border="0"  >
    <tr>
        <td  class="oraTableRowHeader" noWrap>对应省市</td>
        <td>&nbsp;<html:select property="province">
                <html:optionsCollection property="provs" />
            </html:select>&nbsp;
            <html:select property="city">
                <html:optionsCollection property="citys" />
            </html:select>
            <font color="red">*</font>
        </td>
        <html:hidden property="parentareacode"/>
        <html:hidden property="root"/>
        <html:hidden property="zone"/>
        
    </tr>
    <tr>
        <td width="200" class="oraTableRowHeader" noWrap>编号</td>
        <td>&nbsp;<html:text property="areacode" readonly="true" size="15"/><font color="red">&nbsp;*</font></td>
    </tr>
    <tr>
        <td  class="oraTableRowHeader" noWrap>名称</td>
        <td>&nbsp;<html:text property="areaname" size="15"/><font color="red">&nbsp;*</font></td>
    </tr>
    <tr>
        <td  class="oraTableRowHeader" noWrap>邮编前4位</td>
        <td>&nbsp;<html:text property="postcode" size="15" maxlength="4" /><font color="red">&nbsp;*</font></td>
    </tr>
    <tr>
        <td  class="oraTableRowHeader" noWrap>能否直送</td>
        <td>&nbsp;<html:select property="is_express">
                <html:option value="1">是</html:option>
                <html:option value="0">否</html:option>
            </html:select>
            <font color="red">*</font></td>
    </tr>
     <tr>
        <td colspan="2" align="center"><input type="submit" value=" 确 定 " >&nbsp;&nbsp;<input type="button" value=" 返 回 " onclick="history.back();"></td>
        
    </tr>
</table>
<TABLE align="center">
</TABLE>
</html:form>
</body>
</html>

