<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ApproveManage.aspx.cs" Inherits="Approve_ApproveManage" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>单据签核</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left;}
        a.dp-choose-date{ margin-top: 1px; }
        .hidden { display:none;}
        .appTooltip { margin-top:1px;margin-bottom:0;padding:0;border:0; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <script type="text/javascript" src="../script/selector.js"></script>
    <script type="text/javascript" src="../script/note.js"></script>
    <script type="text/javascript">
	  $(document).ready(function(){ 
	      $("#txtDateFrom").datePicker({startDate: '2008-01-01'});
	      $("#txtDateTo").datePicker({startDate: '2008-01-01'});
	      bindTableBehavior('#data_list_table', '#chkSelectAll');	
      });//$(document).ready(function()
	  
	  function checkSelect(type) {
	    if(!hasSelect("#data_list_table")) {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    noteForm(type, -1);
	    return false;
	  }
	  
	  function viewOrder(url){
	     window.open(
	        url, "viewpo"
	        , "height=550px,width=960px,left=20px,top=50px,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes");
	  }
	  
	  function noteForm(type, id){
	        if(type!=1 && type!=2) return;
	        $("#txtAppNote").val("");
	        $("#txtAppItemId").val(id);
            var q = new note("appnote");
            mm.mask();
            q.fnPopup({
                top: 100, left: 150, width: 300, height: 150, title: (type == 1 ? "签核备注" : "驳回原因"),
                on_select: function(msg) {
                    $("#txtAppNote").val(msg);
                    if (type == 1) $("#cmdApproveHid").click();
                    else if (type == 2) $("#cmdRejectHid").click();
                    $(document).attr("disabled", true);
                },
                on_close: function() { mm.maskClose(); }
            });   //q.fnPopup
	  }
	  
	  function showTips(cmd){
            var q = new query("ApproveHistoryTips");
            q.fnPopup({
                top:80, left:100, width:560, height:250, mode: "m", title: "签核记录",
                data: { ordNum: $(cmd).attr("ordNum") },
                tooltip: true
            });//q.fnPopup
            var evt = getEvent();
            if(evt!=null) {
                evt.returnValue = false;
                evt.cancelBubble = true;
                if(evt.preventDefault && typeof evt.preventDefault=="function") evt.preventDefault();
                if(evt.stopPropagation && typeof evt.stopPropagation=="function") evt.stopPropagation();
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
        <input type="hidden" id="txtAppNote" runat="server" value="" />
        <input type="hidden" id="txtAppItemId" runat="server" value="" />
        <asp:Button ID="cmdApproveHid" runat="server" Text="" onclick="cmdApprove_Click" CssClass="hidden" />
        <asp:Button ID="cmdRejectHid" runat="server" Text="" onclick="cmdReject_Click" CssClass="hidden" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="单据签核" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:40px;">
                    类型&nbsp;
                </td>
                <td class="label" style="width:110px;">
                    <asp:DropDownList ID="drpOrderType" runat="server" Width="100px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:45px;">
                    单据号&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtOrderNumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    日期&nbsp;
                </td>
                <td style="width:225px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td style="width:130px;">
                    <asp:RadioButton ID="rdoUnapprove" runat="server" GroupName="rdoStatus" Text="未签核" />&nbsp;
                    <asp:RadioButton ID="rdoApprove" runat="server" GroupName="rdoStatus" Text="已签核" />
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Pass" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="checkSelect(1)"
                                Text="同意">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Reject" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_reject.gif" CancelServerEvent="true" OnClientClick="checkSelect(2)"
                                Text="驳回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="19" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width:50px;">操作</th>
                    <th style="width:130px;">单据号码</th>
                    <th>单据类型</th>
                    <th>供应商</th>
                    <th style="width:45px;">状态</th>
                    <th style="width:110px;">签核时间</th>
                    <th style="width:52px;">创建者</th>
                    <th style="width:108px;">创建时间</th>
                    <th style="width:52px;">送签人</th>
                    <th style="width:108px;">送签时间</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeatControl" runat="server" 
                    onitemdatabound="repeatControl_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td class="">
                                <input id="checkbox" value='<%# Eval("ApproveResultID") %>' type="checkbox" runat="server" />
                            </td>
                            <td>
                                <asp:ImageButton ID="cmdPass" runat="server" ImageUrl="../images/b_confirm.gif" ToolTip="同意" Visible="false" />
                                <span>&nbsp;</span>
                                <asp:ImageButton ID="cmdReject" runat="server" ImageUrl="../images/b_reject.gif" ToolTip="驳回" Visible="false" />
                            </td>
                            <td>
                                <a href='#a' id="cmdView" runat="server" title="查看单据详细资料" class="left"><%# RenderUtil.FormatString(Eval("OrderNumber")) %></a>
                                <span class="left">&nbsp;</span>
                                <asp:ImageButton ID="cmdNote" runat="server" ImageUrl="../images/b_note.jpg" ToolTip="签核记录" CssClass="appTooltip left" />
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("TypeText"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("Vendor"))%>
                            </td>
                            <td>
                                <asp:Label ID="lblStatus" runat="server" Text=""></asp:Label>
                            </td>
                            <td>
                                <%# this.rdoUnapprove.Checked ? "" : RenderUtil.FormatDatetime(Eval("ApproveTime")) %>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("CreateUser"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatDatetime(Eval("CreateTime"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("SubmitUser"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatDatetime(Eval("SubmitTime"))%>
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
                        Layout="Div" ID="toolbarBottom">
                        <Items>
                            <mwu:MagicItem CommandName="Pass" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="checkSelect(1)"
                                Text="同意">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Reject" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_reject.gif" CancelServerEvent="true" OnClientClick="checkSelect(2)"
                                Text="驳回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="19" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
