<%@ Page Language="C#" AutoEventWireup="true" CodeFile="POManager.aspx.cs" Inherits="Purchase_POManager" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购管理</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
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
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <script type="text/javascript">  
	  $(document).ready(function(){ 
	      bindTableBehavior('#data_list_table', '#chkSelectAll');	
	      $("#txtStartDate").datePicker({startDate: '2008-01-01'}); 
	      $("#txtEndDate").datePicker({startDate: '2008-01-01'});    
      });	   
	  
	  function checkSelect(select)
	  {
	    if(!hasSelect("#data_list_table"))
	    {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    if(select == 1)
	        return confirm("提示:确认删除所选订单?");
	    else if(select == 2)
	        return confirm("提示:确认发布所选订单?");
	     else if(select == 3)
	        return confirm("提示:确认完成所选订单?");
	  }
	  
	  function Edit(OrderNum)
	  {
	        var url = "POEdit.aspx?mode=edit&OrderNum="+OrderNum;
	        var returnUrl = $("#hidReturnUrl").val();
	        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function AddNew()
	  {
	     var url = "POEdit.aspx?mode=new";
         var returnUrl = $("#hidReturnUrl").val();
         window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function Detail(OrderNum,Status,CurrentLineNumber)
	  {
	     var url = "POLineManage.aspx?OrderNum="+OrderNum;
         var returnUrl = $("#hidReturnUrl").val();
         window.location.href = url + "&return="+escape(returnUrl);
	  }
	  function viewpo(orderNumber){
	    if($.trim($("#hidViewUrl").val()).length<=0) return;
	     window.open(
	        $.trim($("#hidViewUrl").val())+"?ordNum="+orderNumber
	        , "viewpo"
	        , "height=550px,width=960px,left=20px,top=50px,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes");
	  }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="POManager.aspx" />
        <input type="hidden" id="hidViewUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购管理" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width: 75px;">
                    采购单号：
                </td>
                <td style="width:120px;">
                    <asp:TextBox ID="txtOrderNum" runat="server" CssClass="input" Width="110px"></asp:TextBox>
                </td>
                <td class="label" style="width:70px;">
                    供 应 商：
                </td>
                <td style="width:110px;">
                    <asp:DropDownList ID="drpVendor" runat="server">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width: 35px;">
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td class="label">
                    日期：
                </td>
                <td colspan="3">
                    <div style="float:left; width:3px;height:10px; overflow:hidden;"></div>
                    <input id="txtStartDate" type="text" runat="server" maxlength="10" 
                        class="input" style="float: left; margin-right: 4px; width: 80px;" />
                    <span style="float:left;">&nbsp;到&nbsp;</span>
                    <input id="txtEndDate" type="text" runat="server" maxlength="10" class="input" 
                       style="float: left; margin-right: 4px; width: 80px;" />
                </td>
                <td class="label">
                    状态&nbsp;
                </td>
                <td>
                    <asp:CheckBoxList ID="cklStatus" RepeatDirection="Horizontal" RepeatLayout="Flow"
                        runat="server">
                        <asp:ListItem Value="1" Selected="True">新建</asp:ListItem>
                        <asp:ListItem Value="2" Selected="True">发布</asp:ListItem>
                        <asp:ListItem Value="3">已完成</asp:ListItem>
                    </asp:CheckBoxList>
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
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="javascript:AddNew()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect(1)"
                                Text="删除">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged"
                        ShowPageSizeBox="true" PageSize="20" MaxPageCount="20" ShowCustomInfoSection="Left"
                        Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th>
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th>
                        编辑
                    </th>
                    <th>
                        采购单号
                    </th>
                    <th>
                        状态
                    </th>
                    <th>
                        签核状态
                    </th>
                    <th>
                        采购组
                    </th>
                    <th>
                        仓库
                    </th>
                    <th>
                        供应商
                    </th>
                    <th>
                        总金额
                    </th>
                    <th>
                        创建者
                    </th>
                    <th>
                        创建时间
                    </th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptPO" runat="server" onitemdatabound="rptPO_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td class="" style="width: 20px;" align="center">
                                <input id="checkbox" value='<%# Eval("OrderNumber") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width: 70px;" align="center">
                                <a href='javascript:Edit("<%# Eval("OrderNumber") %>")'>编辑</a> <a href='javascript:Detail("<%# Eval("OrderNumber") %>","<%# Eval("Status") %>","<%# Eval("CurrentLineNumber") %>")'>
                                    明细</a>
                            </td>
                            <td style="width: 35px;" align="center">
                                <a href='javascript:viewpo("<%# Eval("OrderNumber")%>");'><%# Eval("OrderNumber")%></a>
                            </td>
                            <td align="center">
                                <input type="hidden" id="hidStatus" value='<%# Magic.Framework.Utils.Cast.Enum<Magic.ERP.POStatus>(Eval("Status")).ToString() %>' runat="server" />
                                <input type="hidden" id="hidApproveStatus" value='<%# Magic.ERP.Orders.POHead.ApproveStatusText(Magic.Framework.Utils.Cast.Enum<Magic.ERP.ApproveStatus>(Eval("ApproveResult"))).ToString() %>' runat="server" />
                                <%# Magic.ERP.Orders.POHead.POStatusText(Magic.Framework.Utils.Cast.Enum<Magic.ERP.POStatus>(Eval("Status")))%>
                            </td>
                            <td align="center">
                                <%# Magic.ERP.Orders.POHead.ApproveStatusText(Magic.Framework.Utils.Cast.Enum<Magic.ERP.ApproveStatus>(Eval("ApproveResult")))%>
                            </td>
                            <td align="center">
                                <%# Eval("PurchGroupCode")%>
                            </td>
                            <td>
                                <%# Eval("Name")%>
                            </td>
                            <td>
                                <%# Eval("FullName")%>
                            </td>
                            <td align="right">
                                <%# RenderUtil.FormatNumber(Eval("TaxInclusiveAmt"), "#0.#0", "")%>
                            </td>
                            <td>
                                <%# Eval("uFullName")%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatDate(Eval("CreateTime"))%>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarBottom">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="javascript:AddNew()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect(1)"
                                Text="删除">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
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
    <!--mwu--:--MagicItem CommandName="Complete" ItemCssClass="toolbutton" ItemType="ImageButton"
        ImageUrl="../images/left_icon1.gif" CancelServerEvent="true" OnClientClick="checkSelect(3)"
        Text="完成">
    </mwu--:--MagicItem>
    <mwu--:--MagicItem CommandName="Publish" ItemCssClass="toolbutton" ItemType="ImageButton"
        ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="checkSelect(2)"
        Text="发布">
    </mwu--:--MagicItem-->
</body>
</html>
