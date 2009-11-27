<%@ Page Language="C#" AutoEventWireup="true" CodeFile="MsgSubscriberManage.aspx.cs"
    Inherits="MsgSubscriberManagePage" %>

<%@ Import Namespace="Magic.Sys" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>消息订阅用户管理</title>
    <link href="../CSS/common.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
<link href="../CSS/treeview.css" rel="Stylesheet" type="text/css" />
 <script src="../script/jquery.js" type="text/javascript"></script>    
     <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
        <script src="../script/jquery.treeview.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/UserGroupTree.js"></script>
    <script type="text/javascript" src="../script/user.js"></script>

    <script type="text/javascript">  
	 
	  $(document).ready(
	      function(){ 
	      bindTableBehavior('#data_user_table', '#chkAllUser');	   
	      bindTableBehavior('#data_group_table','#chkAllGroup');
        }
	  );	 
	  
	   function checkSelect()
	  {
	    if(!hasSelect("#data_list_table"))
	    {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！", "警告");
	        return false;
	    }
	   else
	   {
	        if(!confirm("确认删除选择的消息订阅用户？"))
	            return false;
	   }     
	    
	    return true;
	  }
	  
	  function onSelectUserGroup()
	  {
	     mm.query( { 
                id:"userGroup",    getter: new msgSubscriberUserGroupSelect(), title: "选择用户组", mode:"s",
                msgtmpl:$("#txtTmplCode").val(),
                callback: function(data, settings){
                    $("#txtUserGroupId").val(data.id);   
                    document.getElementById("btnAddUserGroup").click();                 
                }
            });      
	  }
	  
	  function onSelectUser()
	  {
	    mm.query({
	         id:"user",    getter: new userSelect(), title: "选择用户", mode:"s",
	            username:"a",
	            fullname:"",
	            orgid:"",
                msgtmpl:$("#txtTmplCode").val(),
                callback: function(data, settings){
                    $("#txtUserId").val(data.id);   
                    document.getElementById("btnAddUser").click();                 
                }
            });      
	  }

	  function Add(isGroup)
	  {
	    $("#txtUserGroupId").val("");
	    $("#txtUserId").val("");
	    if(isGroup)
	        onSelectUserGroup();
	    else
	        onSelectUser();
	  }  
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div class="queryPage">
        <input type="hidden" id="hidReturnUrl" runat="server" value="MsgSubscriberManage.aspx" />
        <input type="hidden" id="txtUserGroupId" runat="server" value="" />
        <input type="hidden" id="txtUserId" runat="server" value="" />
        
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="消息订阅用户管理" />       
        
        <table class="queryArea" cellpadding="1" cellspacing="1" style="width:100%">
          
            <tr>
                <td style="width:80px;"> 
                    消息模板代码
                </td>
                <td style="width:100px;">
                    <asp:TextBox runat="server" ID="txtTmplCode" ReadOnly="true"></asp:TextBox>
                </td>
                <td style="width:80px">
                    消息模板名称
                </td>
                <td style="width:20%">
                    <asp:TextBox style="width:100px;" runat="server" ID="txtTmplName" ReadOnly="true"></asp:TextBox>
                </td>
                <td style="width:100px;">
                    消息类型
                </td>
                <td style="width:20%"><asp:TextBox runat="server" ID="txtMsgTypeName"></asp:TextBox>
                    <input runat="server" id="txtMsgTypeId" type="hidden" />
                </td>                
                <td style="width:20%"></td>
            </tr>
        </table>
        <div style="width:5px; height:200px; float:left;"></div>
        <div class="gridArea" style="width:45%; float:left;">
        <div style="width:100%; padding-top:5px;"><span style=" font-weight:bolder; ">订阅的用户组</span></div>
            <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                <tr>
                    <td>
                        <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                            Layout="Div" ID="toolbarTop" ToolbarStyle="">
                            <Items>
                                <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                    Text="新增" NavigateUrl="javascript:Add(true)">
                                </mwu:MagicItem>
                                <mwu:MagicItem CommandName="DeleteGroup" ItemCssClass="toolbutton" ItemType="ImageButton"
                                    ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                    Text="删除">
                                </mwu:MagicItem>
                            </Items>
                        </mwu:MagicToolBar>
                    </td>
                    
                </tr>
            </table>
            <table class="datalist" id="data_group_table"   border="0" cellpadding="0" cellspacing="1"
                style="background-color: #CCCCCC;">
                <tr>
                    <th style="width: 35px;">
                        <input name="" value="" id="chkAllGroup" type="checkbox" title="Select All" />
                    </th>
                    <th>
                        用户组
                    </th>                    
                    <th>
                        订阅时间
                    </th>
                </tr>
                <asp:Repeater ID="rptSubscribeGroup" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td>
                                <input id="checkbox" subscriberid='<%# Eval("SubscriberId") %>' type="checkbox" runat="server" />
                            </td>                     
                            <td>                                
                                <%# Eval("Description") %>(<%# Eval("GroupName") %>)
                                <%# Eval("GroupId") %>
                            </td>                         
                            <td>
                                <%# RenderUtil.FormatDatetime(Eval("SubscribeTime")) %>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </table>    
            <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                <tr>
                    <td align="right">
                        <mwu:MagicPager ID="magicPagerGroup" ShowPageSizeBox="true" Layout="Div" PagingButtonType="Text"
                            PagingButtonSpacing="2" FirstPageText="首页" PrevPageText="上一页" NextPageText="下一页"
                            LastPageText="尾页" runat="server" OnPageChanged="MagicPagerGroup_PageChanged" PageSize="20"
                            TextBeforeInputBox="转到第&amp;nbsp;" TextAfterInputBox="&amp;nbsp;页&amp;nbsp;"
                            SubmitButtonClass="input" InputBoxClass="input" ShowCustomInfoSection="Left"
                            ShowPageIndex="false" CustomInfoTextAlign="Right" AlwaysShow="True" MaxPageCount="20"
                            ShowInputBox="Always">
                        </mwu:MagicPager>
                    </td>
                </tr>
            </table>        
        </div>
        <div style="width:15px; height:200px;float:left;"></div>
        <div class="gridArea" style="width:45%; float:left;">
        <div style="width:100%; padding-top:5px;"><span style=" font-weight:bolder; ">订阅的用户</span></div>
            <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                <tr>
                    <td>
                        <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                            Layout="Div" ID="MagicToolBar1" ToolbarStyle="">
                            <Items>
                                <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                    Text="新增" NavigateUrl="javascript:Add(false)">
                                </mwu:MagicItem>
                                <mwu:MagicItem CommandName="DeleteUser" ItemCssClass="toolbutton" ItemType="ImageButton"
                                    ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                    Text="删除">
                                </mwu:MagicItem>
                            </Items>
                        </mwu:MagicToolBar>
                    </td>
                   
                </tr>
            </table>
            <table class="datalist" id="data_user_table" border="0" cellpadding="0" cellspacing="1"
                style="background-color: #CCCCCC;">
                <tr>
                    <th style="width: 35px;">
                        <input name="" value="" id="chkAllUser" type="checkbox" title="Select All" />
                    </th>               
                    <th>
                        用户
                    </th>                    
                    <th>
                        订阅时间
                    </th>
                </tr>
                <asp:Repeater ID="rptSubscribeUser" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td>
                                <input id="checkbox" subscriberid='<%# Eval("SubscriberId") %>' type="checkbox" runat="server" />
                            </td>
                            <td>
                                <%# Eval("FullName")%>(<%# Eval("UserName") %>)
                                <%# Eval("UserId") %>
                            </td>                           
                            <td>
                                <%# RenderUtil.FormatDatetime(Eval("SubscribeTime")) %>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </table>
            <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                <tr>
                    <td align="right">
                        <mwu:MagicPager ID="magicPagerUser" ShowPageSizeBox="true" Layout="Div" PagingButtonType="Text"
                            PagingButtonSpacing="2" FirstPageText="首页" PrevPageText="上一页" NextPageText="下一页"
                            LastPageText="尾页" runat="server" OnPageChanged="MagicPagerUser_PageChanged" PageSize="20"
                            TextBeforeInputBox="转到第&amp;nbsp;" TextAfterInputBox="&amp;nbsp;页&amp;nbsp;"
                            SubmitButtonClass="input" InputBoxClass="input" ShowCustomInfoSection="Left"
                            ShowPageIndex="false" CustomInfoTextAlign="Right" AlwaysShow="True" MaxPageCount="20"
                            ShowInputBox="Always">
                        </mwu:MagicPager>
                    </td>
                </tr>
            </table>   
        </div>
        <div style=" clear:both;"></div>
        <div style="width:100%; text-align:center;">
               <mwu:MagicButton CssClass="toolbutton" runat="server" ID="btnReturn" ItemType="Navigate" ImageUrl="../images/b_back.png" Text="返回" NavigateUrl="MsgTemplateEdit.aspx" />
        </div>     
        <div style="display:none">
        <asp:Button ID="btnAddUserGroup"  runat="server" onclick="btnAddUserGroup_Click"/>
        <asp:Button ID="btnAddUser" runat="server" OnClick="btnAddUser_Click" />
    </div>  
    </div>
    
    </form>
</body>
</html>
