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
   function modify_f(){
      if(document.forms[0].groupName.value==""){
        alert("组名必须填写");
        document.forms[0].groupName.focus();
        return;
      }

      if(document.forms[0].description.value==""){
        alert("描述必须填写");
        document.forms[0].description.focus();
        return;
      }
	  document.forms[0].submit();
      
  }

   function postset_list_f() {
		document.forms[0].action="findPostcodeSet.do?groupId="+document.forms[0].id.value;
		document.forms[0].submit();
   }
    //-->
    </script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>当前位置</b></font><font color="838383"> : </font><font color="838383">用户管理</font><font color="838383">
              -&gt; </font><font color="838383">修改邮编组</font>
          </td>
   </tr>
</table>
<br>
<html:form method="post" action="/editPostcodeGroup.do">
<html:hidden property="id"/>

<table width="90%" align="center" cellspacing="0" cellspacing="0" border="0" style="border: 1px" class="en" frame="box">

	<tr>
        <td align="right">
        
         <table width="100%" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
               <tr>
                  <td width="20%"  class="OraTableRowHeader" noWrap ><font color="red">*</font>组名称</td>
                  <td>
					<html:text property="groupName" />
                  </td>

              	<tr>
                  <td width="20%"  class="OraTableRowHeader" noWrap ><font color="red">*</font>组描述</td>
                  <td>
                  		<html:textarea property="description" cols="50" rows="3" />
                  </td>
                </tr>

                <tr  align="center">
								
										<td colspan="2">
												<input type="button" value="提交"  onclick="modify_f()">
										
												<input type="button" value="设置邮编" onclick="javascript:postset_list_f();"/>
												<input type="button" value="返回" onclick="javascript:window.history.go(-1)"/>
										</td>
								
								</tr>

        </table>

              </table>


</html:form>


</body>
</html>