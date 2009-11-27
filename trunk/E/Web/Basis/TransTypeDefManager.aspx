<%@ Page Language="C#" AutoEventWireup="true" CodeFile="TransTypeDefManager.aspx.cs"
    Inherits="Basis_TransTypeDefManager" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>交易类型定义</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript">
function onDelete(){
    if(!confirm("确实要删除交易类型？")){
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
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="交易类型定义" ExtInfo="对各种出入库事件进行分类、参数设置，并维护它们可以使用哪些库位" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="TransTypeDefEdit.aspx" OnClientClick="true">
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
                    <th style="width:70px;">操作</th>
                    <th  style="width:70px;">代码</th>
                    <th>描述</th>
                    <th style="width:100px;">价格来源</th>
                    <th style="width:100px;">是否成本交易</th>
                    <th style="width:70px;">交易属性</th>
                    <th>备注</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeaterControl" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td>
                                <a href='TransTypeDefEdit.aspx?type=<%# Eval("TransTypeCode") %>'>编辑&nbsp;</a>
                                <!--安全起见，删除交易类型功能屏蔽掉-->
                                <asp:LinkButton ID="cmdDelete" Visible="false" OnClick="cmdDelete_Click" OnClientClick="onDelete();" typeCode='<%# Eval("TransTypeCode") %>' runat="server">删除&nbsp;</asp:LinkButton>
                                <a href='TransTypeDefWHArea.aspx?type=<%# Eval("TransTypeCode") %>'>库位</a>
                            </td>
                            <td><%# Eval("TransTypeCode")%></td>
                            <td><%# Eval("TransDefText")%></td>
                            <td>
                                 <%# Magic.ERP.Core.TransTypeDef.PriceSourceTypeText(Eval("PriceSourceType"))%>
                            </td>
                            <td>
                               <%# RenderUtil.FormatBool(Eval("IsCostTrans"), "是", "否")%>
                            </td>
                            <td>
                               <%# this.TransPropertyText(Magic.Framework.Utils.Cast.Enum<Magic.ERP.TransProperty>(Eval("TransProperty")))%>
                            </td>
                            <td><%# Eval("TransDefDesc")%></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="toolbarBottom">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="TransTypeDefEdit.aspx">
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
