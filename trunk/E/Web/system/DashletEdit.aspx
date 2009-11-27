<%@ Page Language="C#" AutoEventWireup="true" CodeFile="DashletEdit.aspx.cs" Inherits="DashletEditPage" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>编辑Dashlet</title>
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
    <script type="text/javascript" src="../script/selector.js"></script>
    <script type="text/javascript" src="../script/jquery.bgiframe.js"></script>
    <script type="text/javascript">  
	 
	    $(document).ready(function(){
	    
	     $("#txtIcon").bind("click", function(){  
               
                var q = new query("IconSelector"); //实现了Dashlet选择页面的服务器端控件名字
                q.fnPopup({
                    top:50, left:50, width:400, height:250, //选择框的位置信息
                    mode: "s", //s单选，m多选
                    title:"选择图标",
                    data: {
                        selected: [ {key: ""} ], //当前选择的                       
                        pi: 1 //当前页数
                    },
                    on_select: function(r) { //点击确认按钮的回调函数，参数r为返回结果（数组）
                        var str="";
                        $(r).each(function(i, e){ //返回了Dashlet的选择的数据
						$("#txtIcon").val(e.name);
						$("#txtIcon").attr("key",e.key);
                        });
                        
                    }
                });
            });
	    
	    });
	    function ValidateData()
        {
            if($.trim($('#txtTitle').val()).length == 0)
            {
                ShowMsg("标题不能为空","警告");
                return false;
            }
            if($.trim($('#txtInstanceMethod').val()).length == 0)
            {
                ShowMsg("创建方式不能为空","警告");
                return false;
            }
            return true;
        }
        
       
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="编辑Dashlet" />
       <!--store the primary keys in the hidden control--> 
        <input type="hidden" id="hidDashletId"  value="" runat="server" />
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
                <td class="label">分类</td>
                <td><asp:DropDownList runat="server" ID="ddlCategory"  ></asp:DropDownList></td>
			</tr>
			<tr>
                <td class="label">标题</td>
                <td><asp:TextBox runat="server" ID="txtTitle" MaxLength="250"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">链接</td>
                <td><asp:TextBox runat="server" ID="txtLink" MaxLength="255"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">图标</td>
                <td><asp:TextBox runat="server" ID="txtIcon" MaxLength="50"  key=""  ReadOnly="true" ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">描述</td>
                <td><asp:TextBox runat="server" ID="txtDescription" MaxLength="250" TextMode="MultiLine"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">创建方式</td>
                <td><asp:TextBox runat="server" ID="txtInstanceMethod" MaxLength="20"  ></asp:TextBox></td>
			</tr>
			<tr>
                <td class="label">创建参数</td>
                <td><asp:TextBox runat="server" ID="txtInstanceParameter" MaxLength="1000"  ></asp:TextBox></td>
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
