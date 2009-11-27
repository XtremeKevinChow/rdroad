<%@ Page Language="C#" AutoEventWireup="true" CodeFile="MessageManage.aspx.cs" Inherits="MessageManagePage" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>系统消息管理</title>
    <link href="../CSS/common.css" rel="stylesheet" type="text/css" />
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
	 
	  $(document).ready(
	      function(){ 
	      bindTableBehavior('#data_list_table', '#chkSelectAll');	   
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
	        if(!confirm("确认删除选择的系统消息？"))
	            return false;
	   }     
	    
	    return true;
	  }
	  
	  //Nav to Edit Page
	  function Edit(messageId)
	  {
	         var url = "MessageEdit.aspx?";
	        if(messageId)
	        {
	            url +="mode=edit";
	            url += "&messageId="+ messageId;
	        }
	        else
	        {
	            url +="mode=new" ;
	        }       
	        var returnUrl = $("#hidReturnUrl").val();
	        window.location.href = url + "&return="+escape(returnUrl);
	  }  
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div class="queryPage">
        <input type="hidden" id="hidReturnUrl" runat="server" value="MessageManage.aspx" /> 
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="系统消息管理" />
        <table class="queryArea" cellpadding="1" cellspacing="1">
		 <tr>
                <td class="label">消息类型ID</td>
                <td><asp:DropDownList runat="server" ID="ddlMsgTypeId"  ></asp:DropDownList></td>
                <td class="label">标题</td>
                <td><asp:TextBox runat="server" ID="txtTitle"  ></asp:TextBox></td>
                <td class="label">可访问性</td>
                <td><asp:CheckBoxList runat="server" ID="chkAccessibility"  RepeatDirection="Horizontal" ></asp:CheckBoxList></td>
		</tr>
		<tr>
                <td class="label">消息状态</td>
                <td><asp:CheckBoxList runat="server" ID="ddlStatus" RepeatDirection="Horizontal"  ></asp:CheckBoxList></td>
<td></td>
	     <td></td><td></td>
				<td class="queryButton">
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" />
                </td>	
            </tr>          
        </table>
        <div class="gridArea">
            <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                <tr>
                <td>
                        <mwu:MagicToolBar CssClass="toolbar"  OnItemCommand="MagicItemCommand"  runat="server" Layout="Div" ID="toolbarTop"  ToolbarStyle="">     
                             <Items>
                                <mwu:MagicItem ItemCssClass="toolbutton" ItemType="ImageButton"  CommandName="SendNewMessage"
                                    ImageUrl="../images/b_arrowUp.gif" Text="发送新消息" ></mwu:MagicItem>
                                <mwu:MagicItem ItemCssClass="toolbutton"  ItemType="Navigate"
                                    ImageUrl="../images/b_addL.gif" Text="新增" NavigateUrl="javascript:Edit()">
                                </mwu:MagicItem>
                                 <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                    ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()" Text="删除">
                                </mwu:MagicItem>
                             </Items>
                        </mwu:MagicToolBar>
                    </td>
                    <td align="right">
                        <mwu:MagicPager ID="magicPagerMain" ShowPageSizeBox="true" Layout="Div" 
                            PagingButtonType="Text" PagingButtonSpacing="2" FirstPageText="首页" PrevPageText="上一页"
                            NextPageText="下一页" LastPageText="尾页" runat="server" OnPageChanged="MagicPager_PageChanged"
                            PageSize="20" TextBeforeInputBox="转到第&amp;nbsp;" TextAfterInputBox="&amp;nbsp;页&amp;nbsp;"
                            SubmitButtonClass="input" InputBoxClass="input" ShowCustomInfoSection="Left" ShowPageIndex="false"
                            CustomInfoTextAlign="Right" AlwaysShow="True" MaxPageCount="20" ShowInputBox="Always">
                        </mwu:MagicPager>
                    </td>                    
                </tr>
            </table>
            <table class="datalist" id="data_list_table" border="0" cellpadding="0" cellspacing="1"
                style="background-color: #CCCCCC;">
                <tr>
                        <th style="width: 35px;">
                            <input name="" value="" id="chkSelectAll" type="checkbox" title="Select All" />
                        </th>
                        <th style="width: 35px;">编辑</th>
						<th>	消息模板代码</th>
						<th>	消息类型ID</th>
						<th>	标题</th>
						<th>	内容</th>
						<th>	可访问性</th>
						<th>	创建时间</th>
						<th>	发送时间</th>
						<th>	过期时间</th>
						<th>	消息状态</th>
						<th>	消息来源</th>
						<th>	消息目标</th>
                    </tr>     
                <asp:Repeater ID="rptMessage" runat="server">
                <ItemTemplate>
                     <tr>
                            <td>
                                <input id="checkbox"   messageId='<%# Eval("MessageId") %>'   type="checkbox" runat="server" />
                            </td>
                            <td>
                                <a href='javascript:Edit(<%# Eval("MessageId")%>)' >编辑</a>
                            </td>                            
							<td>	<%# Eval("TmplCode") %></td>
							<td>	<%# WebUtil.FindTextByValue(this.ddlMsgTypeId, Eval("MsgTypeId"),false) %></td>
							<td>	<%# Eval("Title") %></td>
							<td>	<%# Eval("Content") %></td>
							<td>	<%# EnumUtil.GetEnumTypeText<MessageAccessibility>(Eval("Accessibility")) %></td>
							<td>	<%# RenderUtil.FormatDatetime(Eval("CreateTime")) %></td>
							<td>	<%# RenderUtil.FormatDatetime(Eval("SendTime")) %></td>
							<td>	<%# RenderUtil.FormatDatetime(Eval("ExpireTime")) %></td>
							<td>	<%# WebUtil.FindTextByValue(this.ddlStatus, Eval("Status"), true) %></td>
							<td>	<%# Eval("Source") %></td>
							<td>	<%# Eval("Destination") %></td>
                        </tr>
                </ItemTemplate>
                </asp:Repeater>         
            </table>
            <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                <tr>
                    <td valign="baseline">
                        <mwu:MagicToolBar runat="server" Layout="Span" OnItemCommand="MagicItemCommand" ID="toolbarBottom" Width="60%" >
                             <Items>
                                <mwu:MagicItem ItemCssClass="toolbutton"  ItemType="Navigate"
                                    ImageUrl="../images/b_addL.gif" Text="新增" NavigateUrl="javascript:Edit()">
                                </mwu:MagicItem>
                                 <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                    ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()" Text="删除">
                                </mwu:MagicItem>
                             </Items>
                        </mwu:MagicToolBar>
                    </td>
                     <td align="right" >
                        <mwu:MagicPager ID="magicPagerSub" Layout="Span" PagingButtonType="Text"
                            PagingButtonSpacing="2" FirstPageText="首页" PrevPageText="上一页" NextPageText="下一页"
                            LastPageText="尾页" runat="server" OnPageChanged="MagicPager_PageChanged" PageSize="20"
                            TextBeforeInputBox="转到第&amp;nbsp;" TextAfterInputBox="&amp;nbsp;页&amp;nbsp;"  ShowPageIndex="false"
                            SubmitButtonClass="input" InputBoxClass="input" ShowCustomInfoSection="Never"
                             AlwaysShow="True" MaxPageCount="20" ShowInputBox="Always">
                        </mwu:MagicPager>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    </form>
</body>
</html>
