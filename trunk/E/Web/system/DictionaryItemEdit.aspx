<%@ Page Language="C#" AutoEventWireup="true" CodeFile="DictionaryItemEdit.aspx.cs" Inherits="DictionaryItemEditPage" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>编辑系统数据字典项</title>
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
	 
	    $(document).ready(function(){
	       var element= $("#rdlItemType");
	       element.bind("change",onItemTypeChange);
	        onItemTypeChange(element[0]);
	        
	    });
	    function ValidateData()
        {
            if($.trim($('#txtCode').val()).length == 0)
            {
                ShowMsg("代码不能为空","警告");
                return false;
            }
            return true;
        }
        
        function onItemTypeChange(element)
        {
            var itemtype = $(element).val();
            switch(itemtype)
            {
                case "String":
                  $("#trItemValue").show();
                $("#txtStringValue").show();
                $("#ddlBoolValue").hide();
                $("#txtNumberValue").hide();
                break;
                case "Numric":
                  $("#trItemValue").show();
                 $("#txtStringValue").hide();
                $("#ddlBoolValue").hide();
                $("#txtNumberValue").show();
                break;
                case "Boolean":
                 $("#trItemValue").show();
                 $("#txtStringValue").hide();
                $("#ddlBoolValue").show();
                $("#txtNumberValue").hide();
                break;
                default:
                $("#trItemValue").hide();
                 $("#txtStringValue").hide();
                $("#ddlBoolValue").hide();
                $("#txtNumberValue").hide();
                break;
            }
        }
        
        function Renew()
        {
            var url = window.location.href;
            window.location.href = url;
        }
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="编辑系统数据字典项" />
       <!--store the primary keys in the hidden control--> 
        <input type="hidden" id="hidItemCode"  value="" runat="server" />
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
                         <mwu:MagicItem CommandName="Add" ItemCssClass="toolbutton"  ItemType="ImageButton"
                         OnClientClick="Renew()" OnlyClient="true"
                        ImageUrl="../images/b_addL.gif" Text="继续新增" >
                        </mwu:MagicItem>
                         <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                            ImageUrl="../images/b_stop.gif" >
                        </mwu:MagicItem>
                    </Items>
                </mwu:MagicToolBar>
				</td>
			</tr>  			    
			<tr runat="server" id="trGroupCode">
                <td class="label">组代码</td>
                <td><asp:TextBox runat="server" ID="txtGroupCode" ReadOnly="true" MaxLength="30"  Width="200px"  ></asp:TextBox>
                    <asp:TextBox runat="server" ID="txtGroupName" ReadOnly="true" ></asp:TextBox>
                </td>
			</tr>
			<tr>
			    <td class="label">数据代码</td>
                <td><asp:TextBox runat="server" ID="txtCode" MaxLength="50"    Width="200px"></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">数据名称</td>
                <td><asp:TextBox runat="server" ID="txtName" MaxLength="50"    Width="200px"></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">数据类型</td>
                <td><asp:DropDownList runat="server" ID="rdlItemType"  RepeatDirection="Horizontal" ></asp:DropDownList></td>
			</tr>
			<tr id="trItemValue">
                <td class="label">数据值</td>
                <td>
                <TextArea runat="server" id="txtStringValue"></TextArea>
                <asp:DropDownList ID="ddlBoolValue" runat="server" Width="60px" >
                    <asp:ListItem Text="true" Value="true"></asp:ListItem>
                    <asp:ListItem Text="false" Value="false"></asp:ListItem>
                </asp:DropDownList>
                <asp:TextBox ID="txtNumberValue" runat="server" MaxLength="19" Width="200px" ></asp:TextBox>
                </td>
			</tr>			
			<tr>
                <td class="label">备注</td>
                <td><TextArea runat="server" id="txtNote"   ></TextArea></td>
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
                         <mwu:MagicItem CommandName="Add" ItemCssClass="toolbutton"  ItemType="ImageButton"
                         OnClientClick="Renew()" OnlyClient="true"
                        ImageUrl="../images/b_addL.gif" Text="继续新增" >
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
