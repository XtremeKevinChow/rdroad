<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ApproveDefUser.aspx.cs" Inherits="Approve_ApproveDefUser" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>签核设定</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left;}
        .hidden { display:none;}
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script src="../script/selector.js" type="text/javascript"></script>
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
	    return confirm("确认将选择的用户删除？");
	  }
	  
	  function AddNew()
	  {
            var q = new query("User4ApproveDef");
            q.fnPopup({
                top:10, left:50, width:400, height:250, mode: "m", title: "选择签核人员",
                data: { ordType: $("#txtOrdType").val(), selected: [] },
                on_select: function(r) {
                    if(r.length>0){
                        var userList = "";
                        for(var i=0; i<r.length; i++){
                            userList=userList+r[i].userId+";";
                        }
                        $("#txtUserToAdd").val(userList);
                        $("#cmdAddUser").click();
                    }//if(r.length>0){
                }//on_select
            });//q.fnPopup
	  }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="txtUserToAdd" runat="server" value="" />
        <input type="hidden" id="txtOrdType" runat="server" value="" />
        <asp:Button ID="cmdAddUser" runat="server" Text="" onclick="cmdAddUser_Click" CssClass="hidden" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="单据签核设定" ExtInfo='设定单据的签核人员，多人签核时将按照设定的签核顺序进行。如果修改了签核顺序，请点击"保存"按钮进行保存' />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"  OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" CancelServerEvent="true" OnClientClick="AddNew()"
                                Text="添加签核人">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif"
                                Text="保存">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                Text="删除">
                            </mwu:MagicItem>
                            <mwu:MagicItem ItemCssClass="toolbutton" CommandName="Return" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" Text="返回" NavigateUrl="ApproveDef.aspx">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width:100px;">签核顺序</th>
                    <th style="width:100px;">
                        签核人
                    </th>
                    <th>
                        电话
                    </th>
                    <th>手机</th>
                    <th>电子邮箱</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeatControl" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td>
                                <input id="checkbox" type="checkbox" runat="server" value='<%# Eval("UserID")%>' />
                            </td>
                            <td style="text-align:center;">
                                <input type="text" runat="server" id="txtSeq" class="input" style="width:50px;" maxlength="3" value='<%# Eval("UserSequnce")%>' />
                            </td>
                            <td>
                                <%# Eval("UserName")%>
                            </td>
                            <td>
                                <%# Eval("Ext")%>
                            </td>
                            <td>
                                <%# Eval("Mobile")%>
                            </td>
                            <td>
                                <%# Eval("Email")%>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="toolbarBottom">
                        <Items>
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" CancelServerEvent="true" OnClientClick="AddNew()"
                                Text="添加签核人">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif"
                                Text="保存">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                Text="删除">
                            </mwu:MagicItem>
                            <mwu:MagicItem ItemCssClass="toolbutton" CommandName="Return" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" Text="返回" NavigateUrl="ApproveDef.aspx">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>