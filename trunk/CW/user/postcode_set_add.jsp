<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title></title>
<script language=javascript>
      function add_f(){
      if(document.forms[0].postcode.value==""){
        alert("邮编必须填写");
        document.forms[0].postcode.focus();
        return false;
      }

      if(document.forms[0].postFee.value==""){
        alert("邮费必须填写");
        document.forms[0].postFee.focus();
        return false;
      }
      
}
    //-->
    </script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0" onload="document.forms[0].postcode.focus();">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>当前位置</b></font><font color="838383"> : </font><font color="838383">用户管理</font><font color="838383">
              -&gt; </font><font color="838383">增加邮编设置</font>
          </td>
   </tr>
</table>
<br>
<html:form method="post" action="/createPostcodeSet.do">
<html:hidden property="groupId"/>
<table width="90%" align="center" cellspacing="0" cellspacing="0" border="0" style="border: 1px" class="en" frame="box">

	<tr>
        <td align="right">
        
         <table width="100%" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
               <tr>
                  <td width="20%"  class="OraTableRowHeader" noWrap ><font color="red">*</font>邮编</td>
                  <td>
					<html:text property="postcode" />
                  </td>

              	<tr>
                  <td width="20%"  class="OraTableRowHeader" noWrap ><font color="red">*</font>邮费</td>
                  <td>
                  		<html:text property="postFee" />
                  </td>
                </tr>
                <tr  align="center">
								
					<td colspan="2">
						<input type="submit" value="提交"  onclick="add_f()">
										
						<input type="button" value="返回" onclick="javascript:window.history.go(-1)"/>
					</td>
								
				</tr>

        </table>

 </table>
</html:form>
</body>
</html>