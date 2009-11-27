<%@ Page Language="C#" AutoEventWireup="true" CodeFile="InventoryCheckWH.aspx.cs"
    Inherits="Inventory_InventoryCheckWH" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>盘点的库位</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript">
function onconfirm(){
    return confirm("确认开始本次盘点作业？");
}
function onadd(){
    if($.trim($("#drpArea").val()).length<=0) return false;
    return true;
}
function onDelete(){
    if(!confirm("确认不使用该仓库？")){
        var evt = getEvent();
        if(evt!=null) {
            evt.returnValue = false;
            evt.cancelBubble = true;
            if(evt.preventDefault && typeof evt.preventDefault=="function") evt.preventDefault();
            if(evt.stopPropagation && typeof evt.stopPropagation=="function") evt.stopPropagation();
        }
    }
}
function getEvent(){
     if(window.event)    {return window.event;}
     func=getEvent.caller;            
     while(func!=null){    
         var arg0=func.arguments[0];
         if(arg0){
             if((arg0.constructor==Event || arg0.constructor ==MouseEvent)
                 || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)){    
                 return arg0;
             }
         }
         func=func.caller;
     }
     return null;
} 
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="盘点的库位" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;" runat="server" id="sectionAdd">
            <tr>
                <td class="label" style="width:140px;">
                    请添加盘点作业的库位：
                </td>
                <td style="width:130px;">
                    <asp:DropDownList ID="drpArea" runat="server" Width="120px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label">
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Add" ItemCssClass="toolbutton" ItemType="ImageButton" ImageUrl="../images/b_addL.gif"
                                CancelServerEvent="true" OnClientClick="onadd()" Text="添加">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:35px;">操作</th>
                    <th  style="width:70px;">代码</th>
                    <th>名称</th>
                    <th>备注</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeaterControl" runat="server" 
                    onitemdatabound="repeaterControl_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td style="width:35px;">
                                <asp:LinkButton ID="cmdDelete" OnClick="cmdDelete_Click" OnClientClick="onDelete();" area='<%# Eval("AreaCode") %>' runat="server">删除</asp:LinkButton>
                            </td>
                            <td  style="width:70px;"><%# Eval("AreaCode")%></td>
                            <td><%# Eval("Name")%></td>
                            <td><%# Eval("Text")%></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <div style="color:red; margin-top:8px;" runat="server" id="sectionAlert">盘点作业期间（执行"开始盘点作业"操作之后）相关库位会被锁定，无法进行出入库操作；盘点作业完成之后这些库位会解除锁定</div>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1" width="100%">
            <tr>
                <td width="30%"></td>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="cmdConfirm">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" CommandName="Confirm" ItemType="ImageButton" ImageUrl="../images/b_confirm.gif"
                                CancelServerEvent="true" OnClientClick="onconfirm()" Text="开始盘点作业">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdViewDetail">
                        <Items>
                            <mwu:MagicItem CommandName="Detail" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true" ImageUrl="../images/b_detail.gif"
                                Text="查看盘点明细">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn">
                        <Items>
                            <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true" ImageUrl="../images/b_back.gif"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td width="30%">
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
