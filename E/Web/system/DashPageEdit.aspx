<%@ Page Language="C#" AutoEventWireup="true" CodeFile="DashPageEdit.aspx.cs" Inherits="DashPageEditPage" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>编辑DashPage</title>
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
            if($.trim($('#txtTitle').val()).length == 0)
            {
                ShowMsg("标题不能为空","警告");
                return false;
            }
            return true;
        }
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="编辑DashPage" />
       <!--store the primary keys in the hidden control--> 
        <input type="hidden" id="hidDashpageId"  value="" runat="server" />
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
                <td class="label">标题</td>
                <td><asp:TextBox runat="server" ID="txtTitle" MaxLength="250"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">布局</td>
                <td><asp:RadioButtonList runat="server" ID="rdlLayout"  RepeatDirection="Horizontal"  ></asp:RadioButtonList></td>
			</tr>
			<tr>
                <td class="label">类型</td>
                <td><asp:RadioButtonList runat="server" ID="rdlType"  RepeatDirection="Horizontal"  ></asp:RadioButtonList></td>
			</tr>
			<tr>
                <td class="label">帮助链接</td>
                <td><asp:TextBox runat="server" ID="txtHelpLink" MaxLength="255"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">描述</td>
                <td><asp:TextBox runat="server" ID="txtDescription" MaxLength="250" 
                        TextMode="MultiLine"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">状态</td>
                <td><asp:RadioButtonList runat="server" ID="rdlStatus"  RepeatDirection="Horizontal"  ></asp:RadioButtonList></td>
			</tr>
			<tr>
                <td class="label">创建时间</td>
                <td><asp:TextBox runat="server" ID="txtCreateTime" ReadOnly="True"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">创建人</td>
                <td><asp:TextBox runat="server" ID="txtCreateBy" ReadOnly="True"  ></asp:TextBox></td>
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
