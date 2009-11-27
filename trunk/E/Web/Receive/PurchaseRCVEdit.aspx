<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PurchaseRCVEdit.aspx.cs" Inherits="Receive_PurchaseRCVEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>采购收货编辑</title>
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <style type="text/css" media="screen">
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/jquery.treeview.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/selector.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#cmdSelectPO").bind("click", function(){  
                var q = new query("PO");
                q.fnPopup({
                    top:10, left:50, width:670, height:400, mode: "s", title: "选择采购订单",
                    data: { selected: [{ po: $("#txtPONumber").val() }] },
                    on_select: function(r) { 
                        if(r.length>0){
                            $("#txtPONumber").val(r[0].po);
                        }//if(r.length>0){
                        else{
                            $("#txtPONumber").val("");
                        }
                    }//on_select
                });//q.fnPopup
            });//$("#cmdSelectItem").bind
        });
	      
        function saveCheck(){
            if($.trim($("#txtPONumber").val()).length<=0) {
                alert("您没有选择采购订单"); return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <form id="form1" runat="server" style="width:100%;margin:0;padding:0; overflow: hidden;">
    <input type="text" style="display:none;" id="txtAction" runat="server" value="" />
    <input type="text" style="display:none;" id="txtId" runat="server" value="" />
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购收货单编辑" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td class="label" style="width:30%;">收货单号码：</td>
                <td>
                    <asp:TextBox runat="server" ID="txtOrderNumber" Width="120px" CssClass="input readonly" ReadOnly="true" MaxLength="18"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    采购订单号：
                </td>
                <td>
                    <input id="txtPONumber" type="text" maxlength="18" class="input" style="width:120px;float:left;margin-right:5px;" runat="server" />
                    <div runat="server" id="cmdSelectPO" style="float:left; background-image:url(../images/b_search.png); background-position:center; background-repeat:no-repeat; width:20px;height:19px;margin-top:auto;margin-bottom:auto; cursor:pointer;border:0;"></div>
                </td>
            </tr>
            <tr>
                <td class="label">供应商：</td>
                <td>
                    <asp:Label ID="lblVendor" runat="server" Text=""></asp:Label></td>
            </tr>
            <tr>
                <td class="label">
                    备注：
                </td>
                <td>
                    <asp:TextBox ID="txtNote" runat="server" CssClass="input" TextMode="MultiLine" Width="350px" Height="60px"></asp:TextBox>
                </td>
            </tr>
            <tr runat="server" id="trAutoCreate">
                <td class="label">&nbsp;</td>
                <td>
                    <asp:RadioButton ID="rdoAutoCreateLines" runat="server" Text="自动生成收货明细" GroupName="LineOption" />
                    <asp:RadioButton ID="rdoManualAddLines" runat="server" Checked="true" Text="手工添加收货明细" GroupName="LineOption" />
                </td>
            </tr>
            <tr>
                <td class="label">
                    状态：
                </td>
                <td>
                    <asp:Label ID="lblStatus" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    创建人：
                </td>
                <td>
                    <asp:Label ID="lblUser" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    创建时间：
                </td>
                <td>
                    <asp:Label ID="lblCreateTime" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    签核状态：
                </td>
                <td>
                    <asp:Label ID="lblApproveResult" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    签核人：
                </td>
                <td>
                    <asp:Label ID="lblApproveUser" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    签核时间：
                </td>
                <td>
                    <asp:Label ID="lblApproveTime" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="label">
                    签核备注：
                </td>
                <td>
                    <asp:TextBox ID="txtApproveNote" runat="server" CssClass="input readonly" TextMode="MultiLine" Width="350px" Height="60px" ReadOnly="true"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdDetail">
                        <Items>
                            <mwu:MagicItem CommandName="Detail" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_note.jpg" OnlyClient="true"
                                Text="明细">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdEdit">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton" ImageUrl="../images/b_save.gif"
                                 OnClientClick="saveCheck()"
                                Text="保存">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdReturn">
                        <Items>
                            <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" OnlyClient="true"
                                Text="返回">
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