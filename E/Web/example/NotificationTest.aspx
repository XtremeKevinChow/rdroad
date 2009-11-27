<%@ Page Language="C#" AutoEventWireup="true" CodeFile="NotificationTest.aspx.cs" Inherits="NotificationTest" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <asp:Button ID="cmdAdd" runat="server" Text="添加" onclick="cmdAdd_Click" /><asp:TextBox ID="txtID"
            runat="server"></asp:TextBox>
            <br />
        <asp:Button ID="cmdSend" runat="server" Text="发送" onclick="cmdSend_Click" />
        
        <br /><br />
        <div runat="server" id="divContainer"></div>
    </div>
    </form>
</body>
</html>
