<%@ Page Language="C#" AutoEventWireup="true" CodeFile="LogisticsManager.aspx.cs" Inherits="system_LogisticsManager" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>配送商管理</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript">  
	  $(document).ready(function(){ 
	      bindTableBehavior('#data_list_table', '#chkSelectAll');	   
      });	   
	  
	  function checkSelect()
	  {
	    if(!hasSelect("#data_list_table"))
	    {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    return confirm("提示:确认删除所选配送商?");
	  }
	  
	  function Edit(id)
	  {
	        var url = "LogisticsEdit.aspx?mode=edit&id=" + id ;
	        var returnUrl = $("#hidReturnUrl").val();
	        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function AddNew()
	  {
	     var url = "LogisticsEdit.aspx?mode=new";
        var returnUrl = $("#hidReturnUrl").val();
        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  

    </script>

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
    </style>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="LogisticsManager.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="配送商管理" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:70px;">
                    公司简称&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtShortName" runat="server" CssClass="input" 
                        Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:70px;">
                    公司全称&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtFullName" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:35px;">
                    状态&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:CheckBoxList runat="server" ID="cklStatus" RepeatDirection="Horizontal" RepeatLayout="Flow">
                        <asp:ListItem Value="1">禁用</asp:ListItem>
                        <asp:ListItem Value="2" Selected="True">有效</asp:ListItem>
                    </asp:CheckBoxList>
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
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
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                Text="删除">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
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
                <tr>
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                  </th>
                    <th style="width:30px;">编辑</th>
                    <th style="width:140px;">简称</th>
                    <th>全称</th>
                    <th style="width:90px;">联系人</th>
                    <th style="width:110px;">电话</th>
                    <th style="width:60px;">结算周期</th>
                    <th style="width:130px;">配送范围</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptLogistics" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td style=" text-align:center;">
                                <input id="checkbox" value='<%# Eval("LogisticCompID") %>' type="checkbox" runat="server" />
                            </td>
                            <td>
                                <a href='javascript:Edit(<%# Eval("LogisticCompID") %>)'>编辑</a>
                            </td>
                            <td>
                            <%# Eval("ShortName")%>
                             </td>
                            <td>
                                 <%# Eval("FullName")%>
                            </td>
                            <td>
                               <%# Eval("Contact")%>
                            </td>
                            <td>
                              <%# Eval("Phone")%>
                            </td>
                            <td>
                                 <%# Eval("SettlementPeriod")%>
                            </td>
							 <td>
                                <%# Eval("LogisticsScope")%>
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
