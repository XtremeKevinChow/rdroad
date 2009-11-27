<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ASSItemManage.aspx.cs" Inherits="numTwo_ASSItemManage" %>
<%@ Register Namespace="Magic.Web.UI" TagPrefix="mwu" Assembly="Magic.Web.UI"%>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Untitled Page</title>
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
	      bindTableBehavior('#sInfo', '#chkAll');	   
      });	
        function checkAll()
        {
            if(!hasSelect("#sInfo"))
	        {
	            ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	            return false;
	        }
	        var a=window.confirm("你确定要删除吗？");
	        if(a)
	        return true;
        }
        function add()
        {
            var url="ASSItemEdit.aspx?mode=add";
            var returnUrl = $("#hidReturnUrl").val();
            window.location.href=url+"&return="+escape(returnUrl);
        }
        function Edit(id)
        {
            var url="ASSItemEdit.aspx?mode=edit&id="+id;
            var returnUrl = $("#hidReturnUrl").val();
            window.location.href=url+"&return="+escape(returnUrl);
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="ASSItemManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="辅料维护" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    货号&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtItemCode" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    名称&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtItemName" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    规格&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtItemSpec" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:35px;">
                    状态&nbsp;
                </td>
                <td>
                    <asp:CheckBoxList runat="server" ID="chkItemStatus" RepeatDirection="Horizontal" RepeatLayout="Flow">
                        <asp:ListItem Value="1" Selected="True">有效</asp:ListItem>
                        <asp:ListItem Value="0">停用</asp:ListItem>

                    </asp:CheckBoxList>
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnSearch" ImageUrl="../Images/search.gif" OnClick="btnSearch_Click" CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="mboor_ItemCommand" runat="server"
                        Layout="Div" ID="mboor" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="javascript:add()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkAll()"
                                Text="删除">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="mpage" runat="server" OnPageChanged="mpage_PageChanged" 
                        ShowPageSizeBox="true" PageSize="20" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist" id="sInfo" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th>
                        <input name="" value="" id="chkAll" type="checkbox" title="全选" />
                    </th>
                    <th>
                        编辑
                    </th>
                    <th>
                        货号
                    </th>
                    <th>
                        状态
                    </th>
                    <th>
                        名称
                    </th>
                    <th>
                        规格
                    </th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rInfo" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="" style="width: 20px;">
                                <input type="checkbox" id="chkItem" value='<%# Eval("SKUID") %>' runat="server" />
                            </td>
                            <td style="width: 35px;">
                                <a href='javascript:Edit(<%# Eval("SKUID") %>)'>编辑</a>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemCode"))%>
                            </td>
                            <td>
                                <%# this.StatusText(Eval("Status"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemName")) %>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemDesc")) %>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="mboor_ItemCommand" runat="server"
                        Layout="Div" ID="MagicToolBar1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="javascript:add()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkAll()"
                                Text="删除">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="mPageSub" runat="server" OnPageChanged="mpage_PageChanged" 
                        ShowPageSizeBox="true" PageSize="20" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
