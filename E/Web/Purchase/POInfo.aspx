<%@ Page Language="C#" AutoEventWireup="true" CodeFile="POInfo.aspx.cs" Inherits="Purchase_POLook" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购明细管理</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
     <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
     
     <style type="text/css">
        .hidden { display:none; }
    </style>
   
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/selector.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    
     <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
 

    <style type="text/css">
        .style1
        {
            text-align: right;
            width: 82px;
        }
        .style2
        {
            text-align: right;
            width: 92px;
        }
        .style3
        {
            width: 131px;
        }
        .style4
        {
            width: 134px;
        }
        .style5
        {
            text-align: right;
            width: 83px;
        }
    </style>

</head>
<body>
    <form id="form1" runat="server">
    <div>
      
 
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购明细管理" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr height="20px">
              <td class="style1">采购单号：</td>
              <td class="style3">
                  <asp:Label ID="LabOrderNumber" runat="server"></asp:Label>
                    </td>
              <td class="style2">供 应 商<span class="style1">：</span></td>
              <td class="style4">
                  <asp:Label ID="LabVendorID" runat="server"></asp:Label>
                    </td>
              <td class="style5">采购组<span class="style1">：</span></td>
              <td>
                  <asp:Label ID="LabPurchGroupCode" runat="server"></asp:Label>
                    </td>
              <td>&nbsp;</td>
            </tr>
            <tr height="20px">
                <td class="style1">
                                        开始日期：                </td>
                <td class="style3">
                    <asp:Label ID="Label4" runat="server"></asp:Label>
                </td>
                <td class="style2">结束日期：</td>
                <td class="style4">
                    <asp:Label ID="Label6" runat="server"></asp:Label>
                </td>
                <td class="style5">
                    &nbsp;                </td>
                <td>
                    &nbsp;</td>
                <td>
                    &nbsp;</td>
            </tr>
            <tr height="20px">
                <td class="style1">
                                        含税：</td>
                <td class="style3">
                    <asp:Label ID="LabTaxInclusiveAmt" runat="server"></asp:Label>
                </td>
                <td class="style2">税额：</td>
                <td class="style4">
                    <asp:Label ID="LabTaxAmt" runat="server"></asp:Label>
                </td>
                <td class="style5">
                    不含税额：</td>
                <td>
                    <asp:Label ID="LabTaxExclusiveAmt" runat="server"></asp:Label>
                </td>
                <td>
                    &nbsp;</td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="20" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr height="20">
                    <th>行状态</th>
                    <th>货号</th>
                    <th>商品名称</th>
                    <th>颜色</th>
                    <th>尺码</th>
                    <th>采购数量</th>
                    <th>送货日期</th>
                    <th>价格</th>
                    <th>含税额</th>
                    <th>税率</th>
                    <th>税额</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptPL" runat="server" >
                    <ItemTemplate>
                        <tr height="20">
                            <td style="width: 70px;"  align="center">
<%# Magic.ERP.Orders.POLine.POLineStatusText(Magic.Framework.Utils.Cast.Enum<Magic.ERP.POLineStatus>(Eval("LineStatus")))%>
                            </td>
                            <td style="width: 35px;" align="center">
                           <%# Eval("ItemCode")%>
                          </td>
                            <td  align="center">
                           <%# Eval("ItemName")%>
                            </td>
                            <td  align="center">
                           <%# Eval("ColorCode")%>
                            </td>
                            <td  align="center">
                            <%# Eval("SizeCode")%>
                            </td>
                            <td  align="center">
                            <%# Eval("PurchaseQty")%>
                            </td>
							 <td align="center">
                              <%# Magic.Framework.Utils.Cast.DateTime(Eval("PlanDate")).ToShortDateString()%>
                            </td>
                             <td  align="center">
                              <%# Eval("Price")%>
                            </td>
                             <td  align="center">
                             <%# Eval("TaxInclusiveAmt")%>
                            </td>
                              <td  align="center">
                              <%# Eval("TaxValue")%>
                            </td>
                              <td  align="center">
                              <%# Eval("TaxAmt")%>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="20" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
   
    </form>
</body>
</html>
