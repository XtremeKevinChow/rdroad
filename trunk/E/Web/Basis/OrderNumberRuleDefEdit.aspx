<%@ Page Language="C#" AutoEventWireup="true" CodeFile="OrderNumberRuleDefEdit.aspx.cs"
    Inherits="Basis_OrderNumberRuleDefEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>修改单据编码规则</title>
    <link href="../css/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/queryPage.js" type="text/javascript"></script>
    <script type="text/javascript">
        function ValidateData()
        {
            if($.trim($('#txtText').val()).length == 0)
            {
                ShowMsg("代码不能为空","警告");
                return false;
            }
            if($.trim($('#txtSerialLen').val()).length == 0)
            {
                ShowMsg("流水号长度不能为空","警告");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="txtRuleId" value="0" runat="server" />
        <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="修改单据编码规则" ExtInfo="系统支持的编码规则为：前缀字符 + 时间戳 + 流水号" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td class="label" style="width:30%;">
                    描述：
                </td>
                <td>
                    <asp:TextBox ID="txtText" runat="server" CssClass="input" Width="300px" MaxLength="40"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    是否使用前缀字符：
                </td>
                <td>
                    <asp:DropDownList ID="drpUsePrefix" runat="server" Width="60px" CssClass="select">
                        <asp:ListItem Text="否" Value="0"></asp:ListItem>
                        <asp:ListItem Text="是" Value="1"></asp:ListItem>
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label">
                    前缀字符：
                </td>
                <td>
                    <asp:TextBox ID="txtPrefix" runat="server" CssClass="input" Width="60px" MaxLength="4"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    是否使用时间戳：
                </td>
                <td>
                    <asp:DropDownList ID="drpUseTimeStamp" runat="server" Width="60px" CssClass="select">
                        <asp:ListItem Text="否" Value="0"></asp:ListItem>
                        <asp:ListItem Text="是" Value="1"></asp:ListItem>
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label">
                    时间戳精度：
                </td>
                <td>
                    <asp:DropDownList ID="drpTimeStampPrecision" runat="server" Width="60px" CssClass="select">
                    </asp:DropDownList>
                    <span class="tips">假设时间戳精度为"月"，则每个月单据的流水号都会重新从1开始自动增长</span>
                </td>
            </tr>
            <tr>
                <td class="label">&nbsp;</td>
                <td>
                    <span class="tips">时间戳格式中可以使用的字符为y（年份）、M（月份，必须为大写字符）、d（一月中的几号）</span><br />
                    <span class="tips">假设当前日期为2008年9月6号，示例如下：yyMMdd-&gt;080906; yyMd-&gt;0896; yyyy-MM-d-&gt;2008-09-6</span>
                </td>
            </tr>
            <tr>
                <td class="label">
                    时间戳格式：
                </td>
                <td>
                    <asp:TextBox ID="txtTimeStampPattern" runat="server" CssClass="input" Width="120px" MaxLength="10"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    最近单据的时间戳值：
                </td>
                <td>
                    <asp:Label ID="lblPrevTimeStampVal" runat="server" Text=""></asp:Label>
                    <span class="tips">该规则最近一次生成单据号码时使用的时间值</span>
                </td>
            </tr>
            <tr>
                <td class="label">
                    流水号长度：
                </td>
                <td>
                    <asp:TextBox ID="txtSerialLen" runat="server" CssClass="input" Width="20px" MaxLength="1"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    最近单据的流水号：
                </td>
                <td>
                    <asp:Label ID="lblPrevSerialVal" runat="server" Text=""></asp:Label>
                    <span class="tips">该规则最近一次生成单据号码时使用的流水号值</span>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarbottom" ToolbarStyle="valign:center;">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                OnClientClick="ValidateData()" CancelServerEvent="true" ImageUrl="../images/b_save.gif"
                                Text="保存">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true" NavigateUrl="OrderNumberRuleDefManager.aspx"
                                ImageUrl="../images/b_stop.gif">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
