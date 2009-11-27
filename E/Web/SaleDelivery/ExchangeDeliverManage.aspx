<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ExchangeDeliverManage.aspx.cs" Inherits="ExchangeDeliverManage" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>换货发货管理</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left; }
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
	      $('#txtBeginDate').datePicker({startDate:'1996-01-01'}) ;
	      $('#txtEndDate').datePicker({startDate:'1996-01-01'}) ;
      });	   
	  
	  function checkSelect()
	  {
	    if(!hasSelect("#data_list_table"))
	    {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    return true;
	  }
	  
	  function Edit(id)
	  {
	        var url = "ExchangeDeliverEdit.aspx?SDNumber=" + id ;
	        var returnUrl = $("#hidReturnUrl").val();
	        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function AddNew()
	  {
	     var url = "UserEdit.aspx?mode=new";
        var returnUrl = $("#hidReturnUrl").val();
        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function UserGroup(id)
	  {
	     var url = "UserToGroup.aspx?uid=" + id ;
        var returnUrl = $("#hidReturnUrl").val();
        window.location.href = url + "&return="+escape(returnUrl);
	  }
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="换货发货管理" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:50px;">
                    订单号&nbsp;
                </td>
                <td style="width:108px;">
                    <asp:TextBox ID="txtSONumber" runat="server" CssClass="input" Width="100px" MaxLength="16"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    发货单号&nbsp;
                </td>
                <td style="width:108px;">
                   <asp:TextBox ID="txtSDNumber" runat="server" CssClass="input" Width="100px" MaxLength="16"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    交接单号&nbsp;
                </td>
                <td style="width:108px;">
                    <asp:TextBox ID="txtICNumber" runat="server" CssClass="input" Width="100px" MaxLength="16"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    状态&nbsp;
                </td>
                <td class="label" style="text-align:left;">
                    <asp:CheckBoxList runat="server" ID="cklStatus" RepeatDirection="Horizontal" RepeatLayout="Flow">
                        <asp:ListItem Text="新建" Value="1" Selected="True"></asp:ListItem>
                        <asp:ListItem Text="发布" Value="2" Selected="True"></asp:ListItem>
                        <asp:ListItem Text="待发货" Value="3" Selected="True"></asp:ListItem>
                        <asp:ListItem Text="完成" Value="4"></asp:ListItem>
                        <asp:ListItem Text="取消" Value="0"></asp:ListItem>
                    </asp:CheckBoxList>
                </td>
            </tr>
            <tr>
                <td class="label">
                    日期&nbsp;
                </td>
                <td colspan="3">
                    <div class="left" style="width:3px; height:5px;"></div>
                    <asp:TextBox ID="txtBeginDate" runat="server" onKeyPress="return false;" CssClass="input left" Width="75px" MaxLength="10"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtEndDate" runat="server" onKeyPress="return false;"  CssClass="input left" Width="75px" MaxLength="10"></asp:TextBox>
                </td>
                <td class="label">
                    处理人&nbsp;
                </td>
                <td>
                    <asp:TextBox ID="txtUser" runat="server" CssClass="input" Width="100px" MaxLength="15"></asp:TextBox>
                </td>
                <td></td>
                <td>
                     <asp:ImageButton runat="server" ID="ImageButton1" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_print.gif"
                                Text="下载打印" NavigateUrl="javascript:AddNew()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Approve" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_edit.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                Text="发布">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                Text="删除">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="20" ShowCustomInfoSection="Never" MaxPageCount="20">
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
                        发货单号
                    </th>
                    <th>
                        状态
                    </th>
                    <th>
                        收货单编号
                    </th>
                    <th>
                        交接单号
                    </th>
                    <th>
                        发货日期
                    </th>
                    <th>
                        处理人
                    </th>
                    <th>
                        处理时间
                    </th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptSDHead" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="" style="width: 20px;">
                                <input id="checkbox" value='<%# Eval("SDNumber") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width: 35px;">
                                <a href="javascript:Edit('<%# Eval("SDNumber") %>');">编辑</a>
                            </td>
                            <td>
                                <%#  RenderUtil.FormatString(Eval("SDNumber"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("StatusText"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ERNumber"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ICNumber"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatDate(Eval("DeliverDate"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("UserName"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatDatetime(Eval("CreateDate"))%>
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
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_print.gif"
                                Text="下载打印" NavigateUrl="javascript:AddNew()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Approve" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_edit.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                Text="发布">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" Text="删除" CancelServerEvent="true" OnClientClick="checkSelect()">
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
</body>
</html>
