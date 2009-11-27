<%@ Page Language="C#" AutoEventWireup="true" CodeFile="InventoryCheckEdit.aspx.cs" Inherits="Inventory_InventoryCheckEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>查看盘点单据信息</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript">
function saveCheck(){
    if($.trim($("#drpLocation").val()).length<=0){
        alert("必须选择仓库才能开始盘点作业");
        return false;
    }
    return true;
}
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="查看盘点单据信息" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td class="label" style="width:30%;">盘点单据号码：</td>
                <td>
                    <asp:TextBox runat="server" ID="txtInvCheckNumber" Width="120px" CssClass="input readonly" ReadOnly="true" MaxLength="18"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">仓库：</td>
                <td>
                    <asp:DropDownList ID="drpLocation" runat="server" Width="120px" CssClass="select">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label">盘点方式：</td>
                <td>
                    <asp:DropDownList ID="drpCheckType" runat="server" Width="120px" CssClass="select">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:10%;">
                    备注说明&nbsp;
                </td>
                <td colspan="7">
                    <asp:TextBox ID="txtMemo" runat="server" CssClass="input" Width="350px" Height="50px" TextMode="MultiLine"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    单据状态：
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
                    <asp:TextBox ID="txtApproveNote" runat="server" CssClass="input readonly" TextMode="MultiLine" Width="350px" Height="50px" ReadOnly="true"></asp:TextBox>
                </td>
            </tr>
        </table>
        
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td style="width:40%"></td>
                <td align="center">
                     <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand"  runat="server" Layout="Div" ID="cmdSave" ToolbarStyle="valign:center;">     
                         <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton"  ItemType="ImageButton"
                            ImageUrl="../images/b_save.gif" Text="下一步" ToolTip="选择库位" CancelServerEvent="true" OnClientClick="saveCheck()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                     <mwu:MagicToolBar CssClass="toolbar" runat="server" Layout="Span" ID="cmdReturn" ToolbarStyle="valign:center;">     
                         <Items>
                             <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_stop.gif" >
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
