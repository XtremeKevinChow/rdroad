<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PackageModify.aspx.cs" Inherits="SaleDelivery_PackageModify" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<%@ Register Src="../Controls/SNInfo.ascx" TagName="sn" TagPrefix="sn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>包装调整</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left;}
        .top2px { margin-top:2px; }
        .warn { color:Red; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function() { $("#txtSNNumber").select(); });
        function onprint() {
            var logisId = parseInt($("#drpLogis").val());
            logisId = isNaN(logisId) ? 0 : logisId;
            if (logisId <= 0) {
                alert("请选择物流公司并保存，再执行打印功能");
                return false;
            }
            document.getElementById("frameDownload").src = "DeliverDownload.aspx?ordNum=" + $("#hidSnNumber").val() + "&tid=" + $("#drpTemplate").val();
            return false;
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
        <asp:HiddenField ID="hidSnNumber" runat="server" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="包装调整" ExtInfo="对那些包装错误的发货单进行调整" />
        <div style="height:20px;width:90%;margin-bottom:4px;margin-top:10px;">
            <span class="left top2px">发货单号：</span>
            <asp:TextBox ID="txtSNNumber" runat="server" CssClass="input left top2px" Width="100px"></asp:TextBox>
            <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                Layout="Div" ID="cmdConfirm" ToolbarStyle="float:left;margin-left:3px;">
                <Items>
                    <mwu:MagicItem CommandName="Confirm" ItemCssClass="toolbutton" ItemType="ImageButton"
                        ImageUrl="../images/b_confirm.gif" Text="确定">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
            <span class="left top2px warn" runat="server" id="lblInfo"></span>
        </div>
        <sn:sn ID="snView" runat="server" /><br />
        <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
            Layout="Div" ID="cmdSave" ToolbarStyle="float:left;margin-left:3px;">
            <Items>
                <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                    ImageUrl="../images/b_save.gif" Text="保存">
                </mwu:MagicItem>
            </Items>
        </mwu:MagicToolBar>
        <asp:DropDownList ID="drpTemplate" runat="server" CssClass="select left pad20" Width="160px">
        </asp:DropDownList>
        <mwu:MagicToolBar CssClass="toolbar" runat="server"
            Layout="Div" ID="cmdPrint" ToolbarStyle="float:left;margin-left:3px;">
            <Items>
                <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="ImageButton"
                    ImageUrl="../images/b_print.gif" Text="下载打印" OnlyClient="true" OnClientClick="onprint()">
                </mwu:MagicItem>
            </Items>
        </mwu:MagicToolBar>
        <table class="queryArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label" style="width:62px;">物流公司&nbsp;</td>
                <td style="width:125px;">
                    <asp:DropDownList ID="drpLogis" runat="server" Width="115px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:55px;">运单号&nbsp;</td>
                <td style="width:125px;">
                    <input type="text" runat="server" id="txtShippingNumber" class="input" style="width:115px;" maxlength="13" />
                </td>
                <td class="label" style="width:55px;">发票号&nbsp;</td>
                <td style="width:125px;">
                    <input type="text" runat="server" id="txtInvoice" class="input" style="width:115px;" maxlength="20" />
                </td>
                <td id="tdTitleWeight" class="label" style="width:65px;">包裹重量&nbsp;</td>
                <td style="width:75px;">
                    <input type="text" runat="server" id="txtPackageWeight" class="input" style="width:70px;" maxlength="10" />
                </td>
                <td class="label" style="width:65px;">包裹数量&nbsp;</td>
                <td style="width:38px;">
                    <input type="text" runat="server" id="txtPackageCount" class="input" style="width:30px;" maxlength="2" />
                </td>
                <td></td>
            </tr>
        </table>
        <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </form>
</body>
</html>