<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<HTML>
<HEAD>
<TITLE>法登会员关系管理系统</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<script language="javascript" >
function submitform(){
	var form = document.forms[0];

	if (form.logID.value == ""){
		alert("工号不能为空！");
		return false;
	}

}
function initForm(){
	var form = document.forms[0];
	form.logID.focus();
}
</script>
</HEAD>
<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 onload="javascript:initForm()">

<html:form action="/logonnow.do" onsubmit="return submitform();">
<center>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30px;"></td>
  </tr>
</table>
<TABLE WIDTH=780 BORDER=0 CELLPADDING=0 CELLSPACING=0>
	<TR>
		<TD>
			<IMG SRC="images/LOGIN_UI_1.gif" WIDTH=93 HEIGHT=86 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_2.gif" WIDTH=119 HEIGHT=86 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_3.gif" WIDTH=132 HEIGHT=86 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_4.gif" WIDTH=176 HEIGHT=86 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_5.gif" WIDTH=147 HEIGHT=86 ALT=""></TD>
		<TD COLSPAN=2>
			<IMG SRC="images/LOGIN_UI_6.gif" WIDTH=113 HEIGHT=86 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/LOGIN_UI_7.gif" WIDTH=93 HEIGHT=107 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_8.gif" WIDTH=119 HEIGHT=107 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_9.gif" WIDTH=132 HEIGHT=107 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_10.gif" WIDTH=176 HEIGHT=107 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_11.gif" WIDTH=147 HEIGHT=107 ALT=""></TD>
		<TD COLSPAN=2>
			<IMG SRC="images/LOGIN_UI_12.gif" WIDTH=113 HEIGHT=107 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/LOGIN_UI_13.gif" WIDTH=93 HEIGHT=72 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_14.gif" WIDTH=119 HEIGHT=72 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_15.gif" WIDTH=132 HEIGHT=72 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_16.gif" WIDTH=176 HEIGHT=72 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_17.gif" WIDTH=147 HEIGHT=72 ALT=""></TD>
		<TD COLSPAN=2>
			<IMG SRC="images/LOGIN_UI_18.gif" WIDTH=113 HEIGHT=72 ALT=""></TD>
	</TR>
	
	<TR>
	
		<TD>
			<IMG SRC="images/LOGIN_UI_19.gif" WIDTH=93 HEIGHT=59 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_20.gif" WIDTH=119 HEIGHT=59 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_21.gif" WIDTH=132 HEIGHT=59 ALT=""></TD>
    <TD><IMG height=59 alt="" src="images/new_LOGIN_UI_22.gif" width=176></TD>
		
      <TD background="images/LOGIN_UI_23.gif"> <input type="text" name="logID"  size="15" tabindex=1><br><input type="text" name="telno"  size="15" tabindex=2>      
    </TD>

      <TD> <input type="image"  SRC="images/LOGIN_UI_24.gif" ALT="" WIDTH=65 HEIGHT=59 border="0" tabindex=4></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_25.gif" WIDTH=48 HEIGHT=59 ALT=""></TD>
	
	</TR>
	
	<TR>
		<TD>
			<IMG SRC="images/LOGIN_UI_26.gif" WIDTH=93 HEIGHT=77 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_27.gif" WIDTH=119 HEIGHT=77 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_28.gif" WIDTH=132 HEIGHT=77 ALT=""></TD>
		<TD>
			<IMG SRC="images/new_LOGIN_UI_29.gif" WIDTH=176 HEIGHT=77 ALT=""></TD>
          <TD width=147 valign="top" background="images/LOGIN_UI_30.gif">
<html:password property="password"  size="15" tabindex="3" />
      <html:hidden property="tag"  value="2" />
          </TD>
		<TD COLSPAN=2>
			<IMG SRC="images/LOGIN_UI_31.gif" WIDTH=113 HEIGHT=77 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/LOGIN_UI_32.gif" WIDTH=93 HEIGHT=99 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_33.gif" WIDTH=119 HEIGHT=99 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_34.gif" WIDTH=132 HEIGHT=99 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_35.gif" WIDTH=176 HEIGHT=99 ALT=""></TD>
		<TD>
			<IMG SRC="images/LOGIN_UI_36.gif" WIDTH=147 HEIGHT=99 ALT=""></TD>
		<TD COLSPAN=2>
			<IMG SRC="images/LOGIN_UI_37.gif" WIDTH=113 HEIGHT=99 ALT=""></TD>
	</TR>
</TABLE>
</center>

</html:form>


</BODY>
</HTML>