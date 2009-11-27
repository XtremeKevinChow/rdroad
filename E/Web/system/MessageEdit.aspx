<%@ Page Language="C#" AutoEventWireup="true" CodeFile="MessageEdit.aspx.cs" Inherits="MessageEditPage" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>编辑系统消息</title>
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
	 
	  
	    function ValidateData()
        {
            if($.trim($('#ddlTmplCode').val()).length == 0)
            {
                ShowMsg("消息模板代码不能为空","警告");
                return false;
            }
            if($.trim($('#ddlMsgTypeId').val()).length == 0)
            {
                ShowMsg("消息类型ID不能为空","警告");
                return false;
            }
            if($.trim($('#txtCreateBy').val()).length == 0)
            {
                ShowMsg("创建人不能为空","警告");
                return false;
            }
            return true;
        }
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="编辑系统消息" />
       <!--store the primary keys in the hidden control--> 
        <input type="hidden" id="txtMessageId"  value="" runat="server" />
        <table id="editTable" width="100%" border="0" cellspacing="1" cellpadding="0" class="margin_top12px">
			<tr>
				<td></td>
				<td>
                 <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand"  runat="server" Layout="Span" ID="toolbarup">     
                     <Items>
                        <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton"  ItemType="ImageButton"
                         OnClientClick="ValidateData()" CancelServerEvent="true"
                        ImageUrl="../images/b_save.gif" Text="保存" >
                        </mwu:MagicItem>
                         <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                            ImageUrl="../images/b_stop.gif" >
                        </mwu:MagicItem>
                    </Items>
                </mwu:MagicToolBar>
				</td>
			</tr>      
			<tr>
                <td class="label">消息类型</td>
                <td><asp:DropDownList runat="server" ID="ddlMsgTypeId"  AutoPostBack="true" 
                        onselectedindexchanged="ddlMsgTypeId_SelectedIndexChanged" ></asp:DropDownList></td>
			</tr>
			<tr>
                <td class="label">消息模板代码</td>
                <td><asp:DropDownList runat="server" ID="ddlTmplCode"  ></asp:DropDownList></td>
			</tr>
			<tr>
                <td class="label">标题</td>
                <td><asp:TextBox runat="server" ID="txtTitle" MaxLength="250"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">内容</td>
                <td><TextArea runat="server" id="txtContent" ></TextArea></td>
			</tr>
			<tr>
                <td class="label">可访问性</td>
                <td><asp:RadioButtonList runat="server" ID="rdlAccessibility"  RepeatDirection="Horizontal"  ></asp:RadioButtonList></td>
			</tr>
			<tr>
                <td class="label">创建时间</td>
                <td><asp:TextBox runat="server" ID="txtCreateTime"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">查看地址</td>
                <td><asp:TextBox runat="server" ID="txtViewEntry" MaxLength="255"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">发送时间</td>
                <td><asp:TextBox runat="server" ID="txtSendTime"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">过期时间</td>
                <td><asp:TextBox runat="server" ID="txtExpireTime"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">创建人</td>
                <td><asp:TextBox runat="server" ID="txtCreateBy" MaxLength="20"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">最近响应时间</td>
                <td><asp:TextBox runat="server" ID="txtLastResponseTime"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">消息状态</td>
                <td><asp:RadioButtonList runat="server" ID="rdlStatus"  RepeatDirection="Horizontal"  ></asp:RadioButtonList></td>
			</tr>
			<tr>
                <td class="label">反应的快捷地址</td>
                <td><asp:TextBox runat="server" ID="txtResponseEntry" MaxLength="255"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">消息来源</td>
                <td><asp:TextBox runat="server" ID="txtSource" MaxLength="50"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">消息目标</td>
                <td><asp:TextBox runat="server" ID="txtDestination" MaxLength="50"  ></asp:TextBox></td>
			</tr>
			 <tr>
				<td></td>
				<td></td>
			</tr>    
			<tr>
				<td></td>
				<td>
                 <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand"  runat="server" Layout="Span" ID="toolbarbottom">     
                     <Items>
                        <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton"  ItemType="ImageButton"
                         OnClientClick="ValidateData()" CancelServerEvent="true"
                        ImageUrl="../images/b_save.gif" Text="保存" >
                        </mwu:MagicItem>
                         <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
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
