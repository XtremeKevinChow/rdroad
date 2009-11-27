<%@ Page Language="C#" AutoEventWireup="true" CodeFile="POEdit.aspx.cs" Inherits="Purchase_POEdit" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购订单资料</title>
    <link href="../css/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        a.dp-choose-date{ margin-top: 1px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/queryPage.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            if ($("#hidStatus").val() == "New")
                $("#txtDemandDate").datePicker({ startDate: '2008-01-01' });
        });
        function ValidateData()
        {
            if($.trim($('#drpPurchGroupCode').val()).length == 0)
            {
                ShowMsg("请选择采购组!","数据不完整");
                return false;
            }
            if($.trim($('#drpLocationCode').val()).length == 0)
            {
                ShowMsg("请选择仓库!","数据不完整");
                return false;
            }
            if($.trim($('#drpVendorID').val()).length == 0)
            {
                ShowMsg("请选择供应商!","数据不完整");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" runat="server" id="hidOrderNumber" value="" />
        <input type="hidden" runat="server" id="hidStatus" value="" />
        <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="采购订单资料" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td width="30%" height="20" class="label">
                    采购订单：
                </td>
                <td width="70%" height="20">
                    <asp:TextBox ID="txtOrderNumber" runat="server" CssClass="input readonly" ReadOnly="true" Width="130px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td width="30%" height="20" class="label">
                    采购组：
                </td>
                <td width="70%" height="20">
                    <asp:DropDownList ID="drpPurchGroupCode" runat="server" Width="130px" CssClass="select">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td width="30%" height="20" class="label">
                    仓库：
                </td>
                <td width="70%" height="20">
                    <asp:DropDownList ID="drpLocationCode" runat="server" Width="130px" CssClass="select" OnSelectedIndexChanged="drpLocationCode_SelectedIndexChanged"
                        AutoPostBack="True">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td width="30%" height="20" class="label">
                    供应商：
                </td>
                <td width="70%" height="20">
                    <asp:DropDownList ID="drpVendorID" runat="server" Width="130px" CssClass="select">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td width="30%" height="20" align="right">
                    送货地址：
                </td>
                <td width="70%" height="20">
                    <asp:TextBox ID="txtShippingAddress" runat="server" CssClass="input" MaxLength="60" Width="350px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td width="30%" height="20" align="right">
                    需求日期：
                </td>
                <td width="70%" height="20">
                    <div style="width:2px;float:left;height:10px; overflow:hidden;"></div>
                    <asp:TextBox ID="txtDemandDate" runat="server" CssClass="input" MaxLength="10" Width="75px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td width="30%" height="20" align="right">
                    说明：
                </td>
                <td width="70%" height="20">
                    <asp:TextBox ID="txtNote" TextMode="MultiLine" CssClass="input" runat="server" Height="66px"
                        Width="350px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td width="30%" height="20">
                </td>
                <td width="70%" height="20">
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Span" ID="cmdDetail" ToolbarStyle="valign:center;">
                        <Items>
                            <mwu:MagicItem CommandName="Detail" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_note.jpg"
                                Text="明细">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Span" ID="cmdSave" ToolbarStyle="valign:center;">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                OnClientClick="ValidateData()" CancelServerEvent="true" ImageUrl="../images/b_save.gif"
                                Text="保存">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" Layout="Span" ID="cmdReturn"
                        ToolbarStyle="valign:center;">
                        <Items>
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_stop.gif">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
            </tr>
            <tr>
                <td width="30%" height="20">
                    &nbsp;
                </td>
                <td width="70%" height="20">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
