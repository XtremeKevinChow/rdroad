<%@ Page Language="C#" AutoEventWireup="true" CodeFile="InterchangeLine.aspx.cs" Inherits="SaleDelivery_InterchangeLine" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>交接明细</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left;}
        .hidden { display:none;}
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/selector.js"></script>
<script type="text/javascript">
  $(document).ready(function(){ 
      bindTableBehavior('#data_list_table', '#chkSelectAll');	
  });//$(document).ready(function()
      
function checkSelect()
{
    if(!hasSelect("#data_list_table"))
    {
        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
        return false;
    }
    return true;
}
function onadd(){
    if($.trim($("#txtOrderNumber").val()).length<=0) return;
    var q = new query("Deliver4IC");
    q.fnPopup({
        top:10, left:20, width:800, height:350, mode: "m", title: "选择要交接的发货单",
        data: { selected: [] },
        on_select: function(r) {
            if(r.length>0){
                var skus = "";
                for(var i = 0; i<r.length; i++){
                    if(i>0) skus = skus+";";
                    skus = skus + r[i].ordNum;
                }
                $("#txtSkus").val(skus);
                $("#cmdAddLines").click();
            }//if(r.length>0){
            else{
                $("#txtSkus").val("");
                return;
            }
        }//on_select
    });//q.fnPopup
}
function ondelete(){
    if(!checkSelect()) return false;
    return confirm("确定要删除选择的发货单？");
}
function onrelease(){
    return confirm("确信发布交接单？");
}
function onclose(){
    return confirm("确信关闭交接单？");
}
function onprint(){
    if($.trim($("#txtOrderNumber").val()).length<=0) return;
    window.open("InterChangePrint.aspx?ordNum="+$("#txtOrderNumber").val(), "print", "width=990,left=10,top=10,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
}
function onaddline(){
    window.location.href="InterchangeLineScan.aspx?ordNumber="+$("#txtOrderNumber").val()+"&return=" + escape($("#txtReturnUrl").val());
}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="交接明细" ExtInfo="" />
        <input id="txtOrderNumber" type="text" runat="server" class="hidden" value="" />
        <input id="txtSkus" type="text" runat="server" class="hidden" value="" />
        <input id="txtReturnUrl" type="text" runat="server" class="hidden" value="" />
        <asp:Button ID="cmdAddLines" runat="server" Text="" CssClass="hidden" OnClick="cmdAddLines_Click" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit1" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Add" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="onaddline()"
                                Text="手工添加">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="ondelete()"
                                Text="删除发货单">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="onrelease()"
                                Text="发布">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="cmdPrint1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_print.gif" Text="打印" OnlyClient="true" OnClientClick="onprint()">
                            </mwu:MagicItem>
                        </Items>
                        <Items>
                            <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_download.jpg" Text="下载">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdClose1" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Close" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_finish.jpg" Text="完成" OnClientClick="onclose()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn1" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif"
                                Text="返回">
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
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width:40px;">行号</th>
                    <th style="width:80px;">发货单号码</th>
                    <th style="width:80px;">运单号码</th>
                    <th style="width:80px;">订单号码</th>
                    <th style="width:70px;">会员姓名</th>
                    <th style="width:70px;">联系人</th>
                    <th style="width:100px;">电话</th>
                    <th>省,&nbsp;市&nbsp;&nbsp;&nbsp;&nbsp;区,&nbsp;县</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeatControl" runat="server" onitemdatabound="repeatControl_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td style="width:20px;">
                                <input id="checkbox" value='<%# Eval("LineNumber") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width:40px;">
                                <%# RenderUtil.FormatString(Eval("LineNumber"))%>
                            </td>
                            <td style="width:80px;">
                                <%# RenderUtil.FormatString(Eval("SNNumber"))%>
                            </td>
                            <td style="width:80px;">
                                <%# RenderUtil.FormatString(Eval("ShippingNumber"))%>
                            </td>
                            <td style="width:80px;">
                                <%# RenderUtil.FormatString(Eval("SONumber"))%>
                            </td>
                            <td style="width:70px;">
                                <%# RenderUtil.FormatString(Eval("Name"))%>
                            </td>
                            <td style="width:70px;">
                                <%# RenderUtil.FormatString(Eval("Contact"))%>
                            </td>
                            <td style="width:100px;">
                                <%# RenderUtil.FormatString(Eval("Phone"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("Province")) + "&nbsp;" + RenderUtil.FormatString(Eval("City"))%>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit2" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Add" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="onaddline()"
                                Text="手工添加">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="ondelete()"
                                Text="删除发货单">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="onrelease()"
                                Text="发布">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="cmdPrint2" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_print.gif" Text="打印" OnlyClient="true" OnClientClick="onprint()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_download.jpg" Text="下载">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdClose2" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Close" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_finish.jpg" Text="完成" OnClientClick="onclose()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn2" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
    </div>
    <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </form>
</body>
</html>