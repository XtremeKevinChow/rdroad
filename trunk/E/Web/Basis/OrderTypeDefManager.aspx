<%@ Page Language="C#" AutoEventWireup="true" CodeFile="OrderTypeDefManager.aspx.cs" Inherits="Basis_OrderTypeDefManager" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>单据配置</title>
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
    if(!confirm("确实要删除单据类型？")){
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
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="单据配置" ExtInfo="各种单据类型的配置信息" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="OrderTypeDefEdit.aspx" OnClientClick="true">
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
                    <th style="width:45px;">操作</th>
                    <th>类型代码</th>
                    <th>单据描述</th>
                    <th>支持签核</th>
                    <th>是否签核</th>
                    <th>编码规则</th>
                    <th>查看地址</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeatControl" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td style="width:45px; text-align:center;">
                                <a href='OrderTypeDefEdit.aspx?type=<%# Eval("OrderTypeCode") %>'>编辑&nbsp;</a>
                                <!--安全起见，禁用删除功能-->
                                <asp:LinkButton ID="cmdDelete" Visible="false" OnClick="cmdDelete_Click" OnClientClick="onDelete();" typeCode='<%# Eval("OrderTypeCode") %>' runat="server">删除&nbsp;</asp:LinkButton>
                            </td>
                            <td><%# Eval("OrderTypeCode")%></td>
                            <td><%# Eval("TypeText")%></td>
                            <td><%# RenderUtil.FormatBool(Eval("SupportApprove"), "是", "否")%></td>
                            <td><%# RenderUtil.FormatBool(Eval("NeedApprove"), "是", "否")%></td>
                            <td><%# Eval("RuleDefineText")%></td>
                            <td><%# Eval("ViewURL")%></td>
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
                                Text="新增" NavigateUrl="OrderTypeDefEdit.aspx">
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
