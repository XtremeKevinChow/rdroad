<%@ Page Language="C#" AutoEventWireup="true" CodeFile="oq.aspx.cs" Inherits="oq" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Untitled Page</title>
</head>
<body>
    <form id="form1" runat="server">
    <div>返回DataSet测试<br />
        <asp:Repeater ID="rpDs" runat="server">
            <HeaderTemplate>
                <table>
                    <thead>
                        <tr>
                            <th>
                                OrgId
                            </th>
                            <th>
                                OrgCode
                            </th>
                            <th>
                                OrgName
                            </th>
                            <th>
                                OrgType
                            </th>
                            <th>
                                CreateBy
                            </th>
                            <th>
                                CreateDate
                            </th>
                            <th>
                                ModifyBy
                            </th>
                            <th>
                                ModifyDate
                            </th>
                        </tr>
                    </thead>
            </HeaderTemplate>
            <ItemTemplate>
                <tr>
                    <td>
                        <%# Eval("Org_ID")%>
                    </td>
                    <td>
                        <%# Eval("Org_Code")%>
                    </td>
                    <td>
                        <%# Eval("Org_Name")%>
                    </td>
                    <td>
                        <%# Eval("Type")%>
                    </td>
                    <td>
                        <%# Eval("CreateBy")%>
                    </td>
                    <td>
                        <%# Eval("Create_Date")%>
                    </td>
                    <td>
                        <%# Eval("Modify_By")%>
                    </td>
                    <td>
                        <%# Eval("Modify_Date")%>
                    </td>
                </tr>
            </ItemTemplate>
            <FooterTemplate>
                </table>
            </FooterTemplate>
        </asp:Repeater>
    </div>
    <br />
    <div>返回List测试<br />
        <asp:Repeater ID="rpOrg" runat="server">
            <HeaderTemplate>
                <table>
                    <thead>
                        <tr>
                            <th>
                                OrgId
                            </th>
                            <th>
                                OrgCode
                            </th>
                            <th>
                                OrgName
                            </th>
                            <th>
                                OrgType
                            </th>
                        </tr>
                    </thead>
            </HeaderTemplate>
            <ItemTemplate>
                <tr>
                    <td>
                        <%# Eval("OrgId")%>
                    </td>
                    <td>
                        <%# Eval("OrgCode")%>
                    </td>
                    <td>
                        <%# Eval("OrgName")%>
                    </td>
                    <td>
                        <%# Eval("OrgType")%>
                    </td>
                </tr>
            </ItemTemplate>
            <FooterTemplate>
                </table>
            </FooterTemplate>
        </asp:Repeater>
    </div>
    <br />
    <div>返回List测试<br />
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
