﻿<%@ Page Language="C#" AutoEventWireup="true" CodeFile="LogisReturnEdit.aspx.cs" Inherits="Receive_LogisReturnEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>编辑退货单</title>
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
<script type="text/javascript">
function saveCheck(){
    if($("#drpLocation").val()=="") {
        alert("必须选择仓库"); return false;
    }
    if ($("#txtSNNumber").val() == "") {
        alert("必须填写发货单号码"); return false;
    }
    return true;
}
</script>
</head>
<body>
    <form id="form1" runat="server" style="width:100%;margin:0;padding:0; overflow: hidden;">
    <input type="text" style="display:none;" id="txtAction" runat="server" value="" />
    <input type="text" style="display:none;" id="txtId" runat="server" value="" />
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="编辑退货单" ExtInfo="退货包裹的主信息" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td class="label" style="width:30%;">退货单号码：</td>
                <td>
                    <asp:TextBox runat="server" ID="txtOrderNumber" Width="120px" CssClass="input readonly" ReadOnly="true" MaxLength="18"></asp:TextBox>
                </td>
            </tr>
            
            <tr>
                <td class="label">仓库：</td>
                <td>
                    <asp:DropDownList ID="drpLocation" runat="server" Width="120px" CssClass="select">
                    </asp:DropDownList>
                    <span class="tips">接收退货包裹的仓库</span>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:30%;">发货单号码：</td>
                <td>
                    <asp:TextBox runat="server" ID="txtSNNumber" Width="120px" CssClass="input" MaxLength="18"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">退货原因：</td>
                <td>
                    <asp:DropDownList ID="drpReason" runat="server" Width="250px" CssClass="select">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label">&nbsp;</td>
                <td>
                    <asp:CheckBox ID="chkIsMalicious" runat="server" Text="是否恶意退货" />
                </td>
            </tr>
            <tr>
                <td class="label">&nbsp;</td>
                <td>
                    <asp:CheckBox ID="chkHasTransported" runat="server" Checked="true" Text="物流公司是否发运配送过" />
                </td>
            </tr>
            <tr>
                <td class="label">
                    备注：
                </td>
                <td>
                    <asp:TextBox ID="txtNote" runat="server" CssClass="input" TextMode="MultiLine" Width="350px" Height="60px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    状态：
                </td>
                <td>
                    <asp:Label ID="lblStatus" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    创建人：
                </td>
                <td>
                    <asp:Label ID="lblUser" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    创建时间：
                </td>
                <td>
                    <asp:Label ID="lblCreateTime" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    签核状态：
                </td>
                <td>
                    <asp:Label ID="lblApproveResult" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    签核人：
                </td>
                <td>
                    <asp:Label ID="lblApproveUser" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    签核时间：
                </td>
                <td>
                    <asp:Label ID="lblApproveTime" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    签核备注：
                </td>
                <td>
                    <asp:TextBox ID="txtApproveNote" runat="server" CssClass="input readonly" TextMode="MultiLine" Width="350px" Height="60px" ReadOnly="true"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdEdit">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton" ImageUrl="../images/b_save.gif" CancelServerEvent="true" OnClientClick="saveCheck()"
                                Text="保存">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdReturn">
                        <Items>
                            <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" OnlyClient="true"
                                Text="返回">
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