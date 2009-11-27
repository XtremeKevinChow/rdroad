<%@ Page Language="C#" AutoEventWireup="true" CodeFile="OpLogManage.aspx.cs" Inherits="OpLogManagePage" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle"   TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>系统日志管理</title>
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
    <script type="text/javascript">  
	 
	  $(document).ready(
	      function(){ 
	      bindTableBehavior('#data_list_table', '#chkSelectAll');	   
        }
	  );	 
	  
	   function checkSelect()
	  {
	    if(!hasSelect("#data_list_table"))
	    {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！", "警告");
	        return false;
	    }
	   else
	   {
	        if(!confirm("确认删除选择的系统日志？"))
	            return false;
	   }     
	    
	    return true;
	  }
	  
	  //Nav to Edit Page
	  function Edit(logId)
	  {
	         var url = "OpLogEdit.aspx?";
	        if(logId)
	        {
	            url +="mode=edit";
	            url += "&logId="+ logId;
	        }
	        else
	        {
	            url +="mode=new" ;
	        }       
	        var returnUrl = $("#hidReturnUrl").val();
	        window.location.href = url + "&return="+escape(returnUrl);
	  }  
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div class="queryPage">
        <input type="hidden" id="hidReturnUrl" runat="server" value="OpLogManage.aspx" /> 
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="系统日志管理" />
        <table class="queryArea" cellpadding="1" cellspacing="1">
		 <tr>
                <td class="label">操作对象类型</td>
                <td><asp:DropDownList runat="server" ID="ddlOpObjectType"  ></asp:DropDownList></td>
                <td class="label">操作类型</td>
                <td><asp:DropDownList runat="server" ID="ddlOpType"  ></asp:DropDownList></td>
                <td class="label">操作时间</td>
                <td><asp:TextBox runat="server" ID="txtOpTime"  ></asp:TextBox></td>
		</tr>
		<tr>
                <td class="label">操作者姓名</td>
                <td><asp:TextBox runat="server" ID="txtOperatorName"  ></asp:TextBox></td>
                <td class="label">操作者类型</td>
                <td><asp:DropDownList runat="server" ID="ddlOperatorType"  ></asp:DropDownList></td>
<td></td>
				<td class="queryButton">
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" />
                </td>	
            </tr>          
        </table>
        <div class="gridArea">
            <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                <tr>
                <td>
                       
                    </td>
                    <td align="right">
                        <mwu:MagicPager ID="magicPagerMain" ShowPageSizeBox="true" Layout="Div" 
                            PagingButtonType="Text" PagingButtonSpacing="2" FirstPageText="首页" PrevPageText="上一页"
                            NextPageText="下一页" LastPageText="尾页" runat="server" OnPageChanged="MagicPager_PageChanged"
                            PageSize="20" TextBeforeInputBox="转到第&amp;nbsp;" TextAfterInputBox="&amp;nbsp;页&amp;nbsp;"
                            SubmitButtonClass="input" InputBoxClass="input" ShowCustomInfoSection="Left" ShowPageIndex="false"
                            CustomInfoTextAlign="Right" AlwaysShow="True" MaxPageCount="20" ShowInputBox="Always">
                        </mwu:MagicPager>
                    </td>                    
                </tr>
            </table>
            <table class="datalist" id="data_list_table" border="0" cellpadding="0" cellspacing="1"
                style="background-color: #CCCCCC;">
                <tr>
                        <th style="width: 35px;">
                            <input name="" value="" id="chkSelectAll" type="checkbox" title="Select All" />
                        </th>
                        <th style="width: 35px;">编辑</th>
						<th>	操作对象类型</th>
						<th>	操作对象标识</th>
						<th>	操作内容</th>
						<th>	操作类型</th>
						<th>	操作时间</th>
						<th>	操作者姓名</th>
						<th>	操作者类型</th>
						<th>	机器IP</th>
                    </tr>     
                <asp:Repeater ID="rptOpLog" runat="server">
                <ItemTemplate>
                     <tr>
                            <td>
                                <input id="checkbox"   logId='<%# Eval("LogId") %>'   type="checkbox" runat="server" />
                            </td>
                            <td>
                                <a href="javascript:Edit('<%# Eval("LogId")%>')" >编辑</a>
                            </td>                            
							<td>	<%# Eval("OpObjectType") %></td>
							<td>	<%# Eval("OpObjectId") %></td>
							<td>	<%# Eval("OpContent") %></td>
							<td>	<%# Eval("OpType") %></td>
							<td>	<%# RenderUtil.FormatDatetime(Eval("OpTime")) %></td>
							<td>	<%# Eval("OperatorName") %></td>
							<td>	<%# Eval("OperatorType") %></td>
							<td>	<%# Eval("IP") %></td>
                        </tr>
                </ItemTemplate>
                </asp:Repeater>         
            </table>
            <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
                <tr>
                    <td valign="baseline">
                        
                    </td>
                     <td align="right" >
                        <mwu:MagicPager ID="magicPagerSub" Layout="Span" PagingButtonType="Text"
                            PagingButtonSpacing="2" FirstPageText="首页" PrevPageText="上一页" NextPageText="下一页"
                            LastPageText="尾页" runat="server" OnPageChanged="MagicPager_PageChanged" PageSize="20"
                            TextBeforeInputBox="转到第&amp;nbsp;" TextAfterInputBox="&amp;nbsp;页&amp;nbsp;"  ShowPageIndex="false"
                            SubmitButtonClass="input" InputBoxClass="input" ShowCustomInfoSection="Never"
                             AlwaysShow="True" MaxPageCount="20" ShowInputBox="Always">
                        </mwu:MagicPager>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    </form>
</body>
</html>
