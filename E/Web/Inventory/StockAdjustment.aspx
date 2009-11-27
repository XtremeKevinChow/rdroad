<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StockAdjustment.aspx.cs" Inherits="StockAdjustment" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>库存调整单编辑</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/selector.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript">
         $(document).ready(function(){
         //选择产品
            $("#btnPopup").bind("click", function(){  
                var q = new query("Item");
                q.fnPopup({
                    top:10, left:50, width:560, height:250, mode: "s", title: "选择产品",
                    data: { selected: [{ itemId: "", skuId: "", itemCode: "", itemName:"", colorCode: "", sizeCode: "" }] },
                    on_select: function(r) { 
                        if(r.length>0){
                            $("#hidItemId").val(r[0].itemId);
                            $("#hidSkuId").val(r[0].skuId);
                            $("#hidItemCode").val(r[0].itemCode);
                            $("#txtItemName").val(r[0].itemName);
                            $("#txtItemColor").val(r[0].colorCode);
                            $("#txtItemSize").val(r[0].sizeCode);
                            document.getElementById("txtItemName").value = r[0].itemName ;
                        }//if(r.length>0){
                        else{
                            $("#hidItemId").val("");
                            $("#hidSkuId").val("");
                            $("#hidItemCode").val("");
                            $("#txtItemName").val("");
                            $("#txtItemColor").val("");
                            $("#txtItemSize").val("");
                        }
                    }//on_select
                });//q.fnPopup
            });//$("#cmdSelectItem").bind
         }) ;
         
         function fnCancel()
         {
            document.getElementById("tblAddItem").style.display ="none";
            document.getElementById("txtItemName").value = "" ;
            document.getElementById("txtItemColor").value = "" ;
            document.getElementById("txtItemSize").value = "" ;
            document.getElementById("txtOldQuantity").value = "" ;
         }
         
         function AddNew()
         {
            document.getElementById("btnPopup").click() ;
            document.getElementById("tblAddItem").style.display ="block";
         }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="button" style="display:none" id="btnPopup" />
        <input type="hidden" id="hidSize" runat="server" value="" />
        <input type="hidden" id="hidColor" runat="server" value="" />
        <input type="hidden" id="hidItemName" runat="server" value="" />
        <input type="hidden" id="hidItemCode" runat="server" value="" />
        <input type="hidden" id="hidItemId" runat="server" value="" />
        <input type="hidden" id="hidSkuId" runat="server" value="" />
        <input type="hidden" id="hidReturnUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="库存调整单编辑" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:10%;">
                    调整单编号&nbsp;
                </td>
                <td style="width:15%;">
                    <asp:TextBox ID="txtInvCheckNumber" runat="server" CssClass="input" Width="95%"></asp:TextBox>
                </td>
                <td class="label" style="width:10%;">
                    创建者&nbsp;
                </td>
                <td style="width:15%;">
                   <asp:TextBox ID="txtUser" runat="server" CssClass="input" Width="95%"></asp:TextBox>
                </td>
                <td class="label" style="width:10%;">
                    状态&nbsp;
                </td>
                <td class="label" style="width:15%;">
                    <asp:TextBox ID="txtStatus" runat="server" CssClass="input" Width="95%"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:10%;">
                    备注&nbsp;
                </td>
                <td colspan="7">
                    <asp:TextBox ID="txtMemo" runat="server" CssClass="input" Width="99%"></asp:TextBox>
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr style="width:100%">
                <td style="width:100%" colspan="2"><br />调整物料列表
                </td>
            </tr>
            <tr style="height:1px;">
                <td colspan="2" style="background-color:#eeeeee"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table border="0" style="width:100%">
                        <tr style="width:100%">
                            <td style="width:100%">
                                <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td>
                                            <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                                                Layout="Div" ID="toolbarTop" ToolbarStyle="">
                                                <Items>
                                                    <mwu:MagicItem ItemCssClass="toolbutton" ItemType="ImageButton" ImageUrl="../images/b_addL.gif"
                                                        Text="添加物料" OnClientClick="AddNew()" CancelServerEvent="true">
                                                    </mwu:MagicItem>
                                                </Items>
                                            </mwu:MagicToolBar>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                         <tr style="width:100%">
                            <td style="width:90%">
                                <table id="tblAddItem" class="queryArea" runat="server" cellpadding="0" cellspacing="0">
                                    <tr>
                                         <td class="label" style="width:10%;">物料名称&nbsp;</td>
                                         <td class="label" style="width:23%;"><input type="text" class="input" runat="server" ID="txtItemName" readonly style="width:100%"></input></td>
                                         <td class="label" style="width:10%;">颜色&nbsp;</td>
                                         <td class="label" style="width:23%;"><input type="text" class="input" runat="server" ID="txtItemColor" readonly style="width:100%"></input></td>
                                         <td class="label" style="width:10%;">尺码&nbsp;</td>
                                         <td class="label" style="width:23%;"><input type="text" class="input" runat="server" ID="txtItemSize" readonly style="width:100%"></input></td>
                                    </tr>
                                    <tr>
                                         <td class="label" style="width:10%;">库房&nbsp;</td>
                                         <td class="label" style="width:23%;"><asp:DropDownList ID="ddlLocation" CssClass="input" AutoPostBack="true" OnSelectedIndexChanged="ddlLocation_SelectedIndexChanged" runat="server" DataValueField="" DataTextField="" Width="100%"></asp:DropDownList></td>
                                         <td class="label" style="width:10%;">区域&nbsp;</td>
                                         <td class="label" style="width:23%;"><asp:DropDownList ID="ddlArea" CssClass="input" AutoPostBack="true" OnSelectedIndexChanged="ddlArea_SelectedIndexChanged" runat="server" DataValueField="" DataTextField="" Width="100%"></asp:DropDownList></td>
                                         <td class="label" style="width:10%;">货架&nbsp;</td>
                                         <td class="label" style="width:23%;"><asp:DropDownList ID="ddlSection" CssClass="input" AutoPostBack="true" OnSelectedIndexChanged="ddlSection_SelectedIndexChanged" runat="server" DataValueField="" DataTextField="" Width="100%"></asp:DropDownList></td>
                                    </tr>
                                    <tr>
                                         <td class="label" style="width:10%;">当前数量&nbsp;</td>
                                         <td class="label" style="width:23%;"><input type="text" class="input" runat="server" readonly ID="txtOldQuantity" style="width:100%"></input></td>
                                         <td class="label" style="width:10%;">调整后数量&nbsp;</td>
                                         <td class="label" style="width:23%;"><asp:TextBox runat="server" CssClass="input" Text="" Enabled="true" ID="txtNewQuantity" style="text-align:right" Width="100%"></asp:TextBox></td>
                                         <td class="label" style="width:10%;" colspan="2"></td>
                                    </tr>
                                    <tr>
                                        <td align="center" colspan="6">
                                             <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                                                <tr>
                                                    <td style="width:30%"></td>
                                                    <td align="center">
                                                         <mwu:MagicToolBar ID="MagicToolBar1" CssClass="toolbar" OnItemCommand="MagicItemCommand"  runat="server" Layout="Span"  ToolbarStyle="valign:center;">     
                                                             <Items>
                                                                    <mwu:MagicItem ItemCssClass="toolbutton" CommandName="Insert" ItemType="ImageButton" ImageUrl="../images/b_addL.gif"
                                                                        Text="新增库存" ItemAlign="Center">
                                                                    </mwu:MagicItem>
                                                                    <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                                                        ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="fnCancel()"
                                                                        Text="取消新增" ItemAlign="Center">
                                                                    </mwu:MagicItem>
                                                            </Items>
                                                        </mwu:MagicToolBar>
                                                    </td>
                                                </tr>      
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <br />
                            </td>
                        </tr>
                         <tr style="width:100%">
                            <td style="width:100%">
                                <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>
                                                仓库
                                            </th>
                                            <th>
                                                区域
                                            </th>
                                            <th>
                                                货架
                                            </th>
                                            <th>
                                                物料名称
                                            </th>
                                            <th>
                                                尺寸
                                            </th>
                                            <th>
                                                颜色
                                            </th>
                                            <th style="width:10%">
                                                当前数量
                                            </th>
                                            <th style="width:10%">
                                                实际数量
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <asp:Repeater ID="rptInvCheck" runat="server">
                                            <ItemTemplate>
                                                <tr>
                                                    <td>
                                                        <%#  RenderUtil.FormatString(Eval("LOCATIONNAME"))%>
                                                    </td>
                                                    <td>
                                                        <%# RenderUtil.FormatString(Eval("AreaName"))%>
                                                    </td>
                                                    <td>
                                                        <%# RenderUtil.FormatString(Eval("SectionCode"))%>
                                                    </td>
                                                    <td>
                                                        <%# RenderUtil.FormatString(Eval("ItemName"))%>
                                                    </td>
                                                    <td>
                                                        <%# RenderUtil.FormatString(Eval("SizeText"))%>
                                                    </td>
                                                    <td>
                                                        <%# RenderUtil.FormatString(Eval("ColorText"))%>
                                                    </td>
                                                    <td>
                                                        <%# RenderUtil.FormatString(Eval("BeforeQty"))%>
                                                    </td>
                                                    <td>
                                                        <input type="hidden" id="hidSKUID" runat="server" value='<%# RenderUtil.FormatString(Eval("SKUID"))%>' />
                                                        <asp:TextBox ID="txtCurrentQuantity" style="text-align:right" Text='<%# RenderUtil.FormatString(Eval("CurrentQty"))%>' runat="server" Width="70px" class="input"></asp:TextBox>
                                                    </td>
                                                </tr>
                                            </ItemTemplate>
                                        </asp:Repeater>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td style="width:40%"></td>
                <td align="center">
                     <mwu:MagicToolBar ID="MagicToolSave" CssClass="toolbar" OnItemCommand="MagicItemCommand"  runat="server" Layout="Span"  ToolbarStyle="valign:center;">     
                         <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton"  ItemType="ImageButton"
                            ImageUrl="../images/b_save.gif" Text="保存" >
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Close" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_finish.jpg"
                                Text="关闭">
                            </mwu:MagicItem>
                             <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="ImageButton"
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
