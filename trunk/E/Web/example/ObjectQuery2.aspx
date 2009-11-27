<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ObjectQuery2.aspx.cs" Inherits="ObjectQuery2" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Untitled Page</title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        返回List测试<br />
        <asp:Repeater ID="rpUser" runat="server">
            <HeaderTemplate>
                <table>
                    <thead>
                        <tr>
                            <th>
                                ID
                            </th>
                            <th>
                                UserName
                            </th>
                            <th>
                                FullName
                            </th>
                        </tr>
                    </thead>
            </HeaderTemplate>
            <ItemTemplate>
                <tr>
                    <td>
                        <%# Eval("Id")%>
                    </td>
                    <td>
                        <%# Eval("UserName")%>
                    </td>
                    <td>
                        <%# Eval("FullName")%>
                    </td>
                </tr>
            </ItemTemplate>
            <FooterTemplate>
                </table>
            </FooterTemplate>
        </asp:Repeater>
    </div>
    </form>
</body>
</html>
