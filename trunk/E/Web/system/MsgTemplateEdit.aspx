<%@ Page Language="C#" AutoEventWireup="true" CodeFile="MsgTemplateEdit.aspx.cs" Inherits="MsgTemplateEditPage" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>编辑消息模板</title>
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
    <script type="text/javascript" src="../script/Navigator.js"></script>
    <script type="text/javascript">  
	 
	    $(document).ready(
	      function(){ 
	 
          //bind Org Select
          $("#txtNavName").bind("click",selNav);
        }
	  );	 
	  
	     function selNav(){
            mm.query( { 
                id:"nav",  target:$("#txtNavName"),  getter: new navSelect(), title: "选择导航入口", mode:"s",
                values: $("#txtNavId").val(), 
                callback: function(data, settings){
                    $("#txtNavId").val(data.id);
                    $("#txtNavName").val(data.desc);
                }
            });
        }
	    function ValidateData()
        {
            return true;
        }
        
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="编辑消息模板" />
       <!--store the primary keys in the hidden control--> 
        <input type="hidden" id="hidTmplCode" value="" runat="server" />
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
			    <td class="label">消息模板代码</td>
			    <td>
			        <asp:TextBox id="txtTmplCode" runat="server" ></asp:TextBox>
			    </td>
			</tr>     
			<tr>
                <td class="label">消息模板名称</td>
                <td><asp:TextBox runat="server" ID="txtName" MaxLength="50"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">消息的类型</td>
                <td><asp:DropDownList runat="server" ID="ddlMsgType"  ></asp:DropDownList></td>
			</tr>
			<tr>
                <td class="label">消息的可访问性</td>
                <td><asp:RadioButtonList runat="server" ID="rdlAccessibility"  RepeatDirection="Horizontal"  ></asp:RadioButtonList></td>
			</tr>
			<tr>
                <td class="label">过期期限(秒)</td>
                <td><asp:TextBox runat="server" ID="txtExpires"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">消息的来源</td>
                <td><asp:TextBox runat="server" ID="txtSource" MaxLength="50"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">消息查看地址</td>
                <td><asp:TextBox runat="server" ID="txtViewEntry" MaxLength="255"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">消息标题格式</td>
                <td><TextArea runat="server" id="txtTitleFormat" ></TextArea></td>
			</tr>
			<tr>
                <td class="label">消息内容格式</td>
                <td><TextArea runat="server" id="txtContentFormat"></TextArea></td>
			</tr>
			<tr>
                <td class="label">处理消息的功能入口</td>
                <td><asp:TextBox runat="server" ID="txtNavName"></asp:TextBox>
                <asp:HiddenField runat="server" ID="txtNavId" Value="" /></td>
			</tr>
			<tr>
                <td class="label">反应的快捷方式</td>
                <td><asp:TextBox runat="server" ID="txtResponseEntry" MaxLength="255"  ></asp:TextBox></td>
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
