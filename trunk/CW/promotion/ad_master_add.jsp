<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/go_top.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript">function querySubmit() {document.fm_add.search.disabled = true;document.fm_add.submit();}</script></head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" >
<br>
<table width="750.0" border=0 cellspacing=1 cellpadding=5 >
<tr>
<td> <span class="OraHeader">增加&nbsp;促销活动</span>
<table width="100%" border=0 cellspacing=0 cellpadding=0 background="/../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
<tr background="/../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
<td height="1" width=100% background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
</tr>
</table>
</td>
</tr>
</table>
<table width="750.0" border=0 cellspacing=1 cellpadding=5   >
<form name="fm_update" method="post" action="../app/ctrnew">
<input type="hidden" name="doc_type" value="1510" >
<tr>
<td width="20%" align="right" ><font color=red>*</font>&nbsp;名称</td>
<td width="40%" align="left" >&nbsp;<input name="pricelist_name" value="">
</td><td width="40%" class=OraTipText align="left"></td></tr>
<tr>
<td width="20%" align="right" ><font color=red>*</font>&nbsp;招募MSC</td>
<td width="40%" align="left" >&nbsp;<input name="msc_code" value="">
</td><td width="40%" class=OraTipText align="left"></td></tr>
<tr>
<td width="20%" align="right" ><font color=red>*</font>&nbsp;招募渠道</td>
<td width="40%" align="left" >&nbsp;<input type="hidden" id="recruitment_type" name="recruitment_type" value=""> <input id="recruitment_type_key" name="recruitment_type_key" value=""  readonly onclick="javascript:select_item('recruitment_type',fm_update.recruitment_type,fm_update.recruitment_type_key,recruitment_type_display);">
<a href="javascript:select_item('recruitment_type',fm_update.recruitment_type,fm_update.recruitment_type_key,recruitment_type_display);"><img src="../crmjsp/images/icon_lookup.gif" border=0 align="top"><a>
&nbsp;<span style="display:none" id="recruitment_type_display" name="recruitment_type_display" ></span>
</td><td width="40%" class=OraTipText align="left"></td></tr>
<tr>
<td width="20%" align="right" ><font color=red>*</font>&nbsp;起始日期</td>
<td width="40%" align="left" >&nbsp;<input id="effect_date" name="effect_date" value=""> <a href="javascript:calendar(fm_update.effect_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(格式:YYYY-MM-DD)
</td><td width="40%" class=OraTipText align="left"></td></tr>
<tr>
<td width="20%" align="right" ><font color=red>*</font>&nbsp;终止日期</td>
<td width="40%" align="left" >&nbsp;<input id="expired_date" name="expired_date" value=""> <a href="javascript:calendar(fm_update.expired_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(格式:YYYY-MM-DD)
</td><td width="40%" class=OraTipText align="left"></td></tr>
<tr>
<td width="20%" align="right" >&nbsp;入会费</td>
<td width="40%" align="left" >&nbsp;<input name="entry_fee" value="">
</td><td width="40%" class=OraTipText align="left"></td></tr>
<tr>
<td width="20%" align="right" >&nbsp;描述</td>
<td width="40%" align="left" >&nbsp;<textarea cols=30 rows=4 name="description" ></textarea>
</td><td width="40%" class=OraTipText align="left"></td></tr>
<tr><td colspan=3>
<table width="750.0" border=0 cellspacing=0 cellpadding=0 background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT=1 WIDTH=1>
<tr background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT=1 WIDTH=1>
<td  height=1 width="750.0" background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT=1 WIDTH=1><img src="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT=1 WIDTH=1></td>
</tr></table></td></tr>
<tr>
<td align="right" colspan=3>
<input name = "submitButton" id="submitButton" type="button" class="button2" value="提交" onClick="submit();document.fm_update.submitButton.disabled=true;">&nbsp;
<input type="button" class="button2" value="取消" onClick="history.back();">
</tr>
</form>
</table>
</body>
</html>

