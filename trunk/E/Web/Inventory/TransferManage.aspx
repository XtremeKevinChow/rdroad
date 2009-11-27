<%@ Page Language="C#" AutoEventWireup="true" CodeFile="TransferManage.aspx.cs" Inherits="Inventory_TransferManage" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>移库管理</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left;}
        a.dp-choose-date{ margin-top: 1px; }
        .hidden { display:none;}
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
      
      function Edit(ordNumber){
          var url = "TransferEdit.aspx";
          if(ordNumber!=null && $.trim(ordNumber).length>0)
            url = url + "?mode=edit&ordNumber=" + ordNumber;
          else url = url + "?mode=new";
          url = url + "&return=" + escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
      function viewDetail(ordNumber){
          var url = "TransferLine.aspx?ordNum="+ordNumber+"&return="+escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
	  
	  function checkSelect(type) {
	    if(!hasSelect("#data_list_table")) {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    noteForm(type, -1);
	    return false;
	  }
	  function ondelete(){
	    if(!hasSelect("#data_list_table")) {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    return confirm("确实要删除选择的收货单？");
	  }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="移库管理" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    移库单号&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtOrderNumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    日期&nbsp;
                </td>
                <td style="width:270px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    状态&nbsp;
                </td>
                <td>
                    <asp:CheckBox ID="chkNew" runat="server" Text="" />&nbsp;
                    <asp:CheckBox ID="chkRelease" runat="server" Text="" />&nbsp;
                    <asp:CheckBox ID="chkClose" runat="server" Text="" />&nbsp;
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="Edit()"
                                Text="新增">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="ondelete()"
                                Text="删除">
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
                    <th style="width:70px;">操作</th>
                    <th style="width:130px;">移库单编号</th>
                    <th style="width:45px;">状态</th>
                    <th style="width:110px;">签核结果</th>
                    <th>来源仓库</th>
                    <th>目标仓库</th>
                    <th style="width:52px;">创建者</th>
                    <th style="width:120px;">创建时间</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeatControl" runat="server" onitemdatabound="repeatControl_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td style="width:20px;">
                                <input id="checkbox" value='<%# Eval("OrderNumber") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width:70px;">
                                <a href='javascript:Edit("<%# Eval("OrderNumber") %>");'>查看&nbsp;</a>
                                <a href='javascript:viewDetail("<%# Eval("OrderNumber") %>");'>明细</a>
                                <!--asp--:--LinkButton ID="cmdDelete" OnClick="On_Delete" OnClientClick="return confirm('确实要删除收货单？');" >删除< / asp--:--LinkButton-->
                            </td>
                            <td style="width:130px;">
                                <%# RenderUtil.FormatString(Eval("OrderNumber"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("StatusText"))%>
                            </td>
                            <td>
                                <asp:Label ID="lblApproveResult" runat="server" Text=""></asp:Label>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("FromLocation"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ToLocation"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("UserName"))%>
                            </td>
                            <td style="width:120px;">
                                <%# RenderUtil.FormatDatetime(Eval("CreateTime"))%>
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
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="Edit()"
                                Text="新增">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="ondelete()"
                                Text="删除">
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
<!--
<mwu--:--MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
    ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="onrelease()"
    Text="发布" >
</mwu--:--MagicItem>
<mwu--:--MagicItem CommandName="Close" ItemCssClass="toolbutton" ItemType="ImageButton"
    ImageUrl="../images/b_finish.jpg" CancelServerEvent="true" OnClientClick="onclose()"
    Text="完成">
</mwu--:--MagicItem>
-->
</body>
</html>