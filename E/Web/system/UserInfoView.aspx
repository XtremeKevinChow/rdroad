<%@ Page Language="C#" AutoEventWireup="true" CodeFile="UserInfoView.aspx.cs" Inherits="UserInfoView" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>用户信息</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
	<script type="text/javascript">
	    function clear() {alert(000);
	        $("#txtOldPwd").val("");
	        $("#txtNewPwd2").val("");
	        $("#txtNewPwd").val("");
	    }
	</script>
</head>

<body>
<form runat="server" id="form1">
<div>
<table border="0" cellspacing="1" cellpadding="0" class="pageNav">
  <tr>
    <td class="f12b"><img src="../images/ico_pagenav.gif" alt=" " width="16" height="16" align="absmiddle" />用户信息</td>
  </tr>
</table>
<div class="contentBox">
  <table border="0" cellspacing="1" cellpadding="0" class="ManageInfo">
    <tr>
    <td></td>
      <td width="100" class="label">帐号名称：</td>
      <td>
        <input type="text" id="txtUserName" runat="server" readonly="readonly"  class="input"/>
      </td>
    </tr>
    <tr>
    <td></td>
      <td width="100" class="label">用户姓名：</td>
      <td><input type="text" id="txtFullName" runat="server"  readonly="readonly" class="input"/></td>
    </tr>
    <tr>
    <td></td>
      <td width="100" class="label">帐号创建时间：</td>
      <td><input type="text" id="txtCreateTime" runat="server" readonly="readonly" class="input"/></td>
    </tr>
    <tr>
    <td></td>
      <td width="100" class="label">上次登陆时间：</td>
      <td><input type="text" id="txtLoginTime" runat="server" readonly="readonly" class="input"/></td>
    </tr>    
   
  </table>
 
  <table border="0" cellspacing="1" cellpadding="0" class="pageNav">
  <tr>
    <td class="f12b"><img src="../images/ico_pagenav.gif" alt=" " width="16" height="16" align="absmiddle" />修改密码</td>
  </tr>
</table>
  <table border="0" cellspacing="1" cellpadding="0" class="ManageInfo">
  <tr>
  <td></td>
      <td  width="100" class="label">原密码：</td>
      <td>
        <input type="password" runat="server" id="txtOldPwd" class="input" />
      </td>
    </tr>
    <tr>
    <td></td>
      <td width="100"  class="label">新密码：</td>
      <td><input type="password"  runat="server" id="txtNewPwd"  class="input"/></td>
    </tr>
    <tr>
    <td></td>
      <td  width="100" class="label">重复新密码：</td>
      <td><input runat="server" type="password" id="txtNewPwd2" value=""  class="input"/></td>
    </tr>
    
    <tr>
       <td></td>
      <td colspan="4">
            <asp:Button ID="txtSubmit" runat="server" Text="提交" onclick="txtSubmit_Click" />
            <input type="button" value="取消 " class="button" onclick="clear();" />
      </td>
    </tr>
  </table>
</div>
</div>
</form>
</body>
</html>
