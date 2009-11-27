<%@ Page Language="C#" AutoEventWireup="true" CodeFile="MbrAccountBalance.aspx.cs"
    Inherits="Report_MbrAccountBalance" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>帐户余额</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left
        {
            float: left;
        }
        a.dp-choose-date
        {
            margin-top: 1px;
        }
    </style>

    <script src="../script/jquery.js" type="text/javascript"></script>

    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>

    <script src="../script/interface.fix.js" type="text/javascript"></script>

    <script src="../script/magic.js" type="text/javascript"></script>

    <script type="text/javascript" src="../script/QueryPage.js"></script>

    <script type="text/javascript" src="../script/date.js"></script>

    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $("#txtDateFrom").datePicker({ startDate: '2008-01-01' });
            $("#txtDateTo").datePicker({ startDate: '2008-01-01' });
        }); //$(document).ready(function()
        function viewTrans(mbrNum) {
            window.location.href = "MbrAccountDetail.aspx?mode=fix&pd=" + $("#drpPeriod").val() + "&mbr=" + mbrNum
                + "&return=" + escape("MbrAccountBalance.aspx?pd=" + $("#drpPeriod").val() + "&mnum=" + $("#txtMbrID").val() + "&mname=" + escape($("#txtMbrName").val())+"&return=" + escape($("#hidReturnUrl").val()));
        }
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <input type="text" runat="server" id="hidReturnUrl" value="" style="display: none;" />
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="帐户余额" ExtInfo="每一个会员号可以点击，查看该会员的帐户变动详细情况" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width: 60px;">
                    库存期间&nbsp;
                </td>
                <td style="width: 95px;">
                    <asp:DropDownList ID="drpPeriod" runat="server" Width="90px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width: 60px;">
                    会员号&nbsp;
                </td>
                <td>
                    <asp:TextBox ID="txtMbrID" runat="server" CssClass="input" Width="90px"></asp:TextBox>
                    姓名
                    <asp:TextBox ID="txtMbrName" runat="server" CssClass="input" Width="60px"></asp:TextBox>
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click"
                        CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="cmdDownload">
                        <Items>
                            <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_download.jpg" Text="导出">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" Layout="Div" ID="cmdReturn1">
                        <Items>
                            <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" OnlyClient="true" Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged"
                        ShowPageSizeBox="true" PageSize="25" MaxPageCount="20" ShowCustomInfoSection="Left"
                        Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0" style="width: 400px;">
            <tr>
                <td style="width: 75px;" class="h">
                    会员号
                </td>
                <td style="width: 80px;" class="h">
                    会员姓名
                </td>
                <td style="width: 95px;" class="h">
                    期初余额
                </td>
                <td style="width: 65px;" class="h">
                    期末余额
                </td>
            </tr>
            <asp:Repeater ID="repeater" runat="server">
                <ItemTemplate>
                    <tr>
                        <td>
                            <a href='javascript:viewTrans("<%# Eval("MemberNumber") %>");'>
                                <%# Eval("MemberNumber")%></a>
                        </td>
                        <td>
                            <%# Eval("MemberName")%>
                        </td>
                        <td align="right">
                            <%# RenderUtil.FormatNumber(Eval("BeginAmt"), "#,##0.#0", "0.00")%>&nbsp;
                        </td>
                        <td align="right">
                            <%# RenderUtil.FormatNumber(Eval("EndAmt"), "#,##0.#0", "0.00")%>&nbsp;
                        </td>
                    </tr>
                </ItemTemplate>
            </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="cmdDownload2">
                        <Items>
                            <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_download.jpg" Text="导出">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" Layout="Div" ID="cmdReturn2">
                        <Items>
                            <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" OnlyClient="true" Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged"
                        ShowPageSizeBox="true" PageSize="25" MaxPageCount="20" ShowCustomInfoSection="Left"
                        Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    <iframe id="frameDownload" runat="server" style="width: 1px; height: 1px; overflow: auto;
        margin: 0; padding: 0; border: 0;" src=""></iframe>
    </form>
</body>
</html>
