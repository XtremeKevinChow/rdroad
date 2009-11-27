<%@ Page Language="C#" AutoEventWireup="true" CodeFile="OpLogEdit.aspx.cs" Inherits="OpLogEditPage" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>编辑系统日志</title>
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
            return true;
        }
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="编辑系统日志" />
       <!--store the primary keys in the hidden control--> 
        <input type="hidden" id="hidLogId"  value="" runat="server" />
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
                <td class="label">操作对象类型</td>
                <td><asp:DropDownList runat="server" ID="ddlOpObjectType"  ></asp:DropDownList></td>
			</tr>
			<tr>
                <td class="label">操作对象标识</td>
                <td><asp:TextBox runat="server" ID="txtOpObjectId" MaxLength="40"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">操作内容</td>
                <td><asp:TextBox runat="server" ID="txtOpContent" MaxLength="250"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">操作类型</td>
                <td><asp:DropDownList runat="server" ID="ddlOpType"  ></asp:DropDownList></td>
			</tr>
			<tr>
                <td class="label">操作时间</td>
                <td><asp:TextBox runat="server" ID="txtOpTime"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">操作者姓名</td>
                <td><asp:TextBox runat="server" ID="txtOperatorName" MaxLength="40"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">操作者类型</td>
                <td><asp:DropDownList runat="server" ID="ddlOperatorType"  ></asp:DropDownList></td>
			</tr>
			<tr>
                <td class="label">机器IP</td>
                <td><asp:TextBox runat="server" ID="txtIP" MaxLength="15"  ></asp:TextBox></td>
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
