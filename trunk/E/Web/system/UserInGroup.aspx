<%@ Page Language="C#" AutoEventWireup="true" CodeFile="UserInGroup.aspx.cs" Inherits="system_UserInGroup" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register src="../Controls/FunctionTitle.ascx" tagname="FunctionTitle" tagprefix="uc1" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>用户组的成员维护</title>
     <link href="../CSS/common.css" rel="stylesheet" type="text/css" />
    <link href="../css/queryPage.css" rel="Stylesheet" type="text/css" />
     <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
     <script src="../script/jquery.js" type="text/javascript"></script>    
      <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/queryPage.js" type="text/javascript"></script>
    <script type="text/javascript">
          $(document).ready(
	      function(){ 
	      bindTableBehavior('#UserInGroup', '#chkSelectAllUserIn');	
	      bindTableBehavior('#AllUser','#chkSelectAllUser');  
        }
	  );	   
	  
	  function checkSelect()
	  {
	    if(!hasSelect("#data_list_table"))
	    {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    return true;
	  }

    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    <input type="hidden" id="txtGroupId"  value="" runat="server" />
      <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="用户组的成员" />
    <div>
      <span>当前用户组：<asp:TextBox ID="txtGroupName" runat="server" ReadOnly="true"></asp:TextBox></span>
      <div style="width:100%; text-align:center;">     
            <div style="vertical-align:top; float:left;" align="left">
                <span>已经选择的用户组：</span>   
                <table  id="UserInGroup" border="1" cellpadding="2" cellspacing="1" style="border-style:outset;">
                <thead>
                <tr>     
                         <th>
                            <input name="" value="" id="chkSelectAllUserIn" type="checkbox" title="Select All" />
                        </th>                                        
						<th>	用户ID</th>
						<th>	账号</th>
						<th>	姓名</th>
                    </tr>
                    </thead>  
                    <tbody>
                <asp:Repeater ID="rptUserInGroup" runat="server">
                <ItemTemplate>
                     <tr> 
                             <td class="" style="width: 35px;">
                                <input id="checkbox"  value='<%# Eval("UserId") %>' type="checkbox" runat="server" />
                            </td>                                                                    
							<td>	<%# Eval("UserId") %></td>
							<td>	<%# Eval("UserName") %></td>
							<td>	<%# Eval("FullName") %></td>							
                        </tr>
                </ItemTemplate>                
                </asp:Repeater>  
                </tbody>       
            </table>
            </div>
            <div style="width:35px; height:100%; float:left; text-align:center; vertical-align:middle;" >
                <ul style="list-style-type:none; margin-left:0; margin-bottom:0; ">
                      <li>&nbsp;</li>
                    <li>&nbsp;</li>
                      <li>&nbsp;</li>
                    <li>&nbsp;</li>
                    <li><asp:ImageButton ImageUrl="../images/arrow-right.gif" runat="server" id="btnAddToGroup" Text="&lt;--" 
                            onclick="btnAddToGroup_Click" /></li>
                    <li>&nbsp;</li>
                    <li>&nbsp;</li>                    
                    <li><asp:ImageButton  runat="server" ImageUrl="../images/arrow-left.gif" id="btnRemoveFromGroup" Text="--&gt;" 
                            onclick="btnRemoveFromGroup_Click" /></li>
                </ul>              
            </div>
            <div style="vertical-align:top;" align="left">
                <span>可选的用户：</span>
                <table  id="AllUser" border="1" cellpadding="2" cellspacing="1">
                <thead>
                <tr>  
                         <th>
                            <input name="" value="" id="chkSelectAllUser" type="checkbox" title="Select All" />
                        </th>                                             
						<th>	用户ID</th>
						<th>	账号</th>
						<th>	姓名</th>
                    </tr> 
                  </thead>
                    <tbody>
                <asp:Repeater ID="rptAllGroup" runat="server">
                <ItemTemplate>
                     <tr>            
                             <td class="" align="center">
                                <input id="checkbox"  value='<%# Eval("UserId") %>' type="checkbox" runat="server" />
                            </td>                                                         
							<td>	<%# Eval("UserId") %></td>
							<td>	<%# Eval("UserName") %></td>
							<td>	<%# Eval("FullName") %></td>							
                        </tr>
                </ItemTemplate>                
                </asp:Repeater>         
                </tbody>    
            </table>
            </div>            
        </tr>
      </div>    
     <mwu:MagicToolBar CssClass="toolbar" runat="server" Layout="Div" ID="toolbarbottom" >     
                     <Items>                      
                         <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                            ImageUrl="../images/b_stop.gif" >
                        </mwu:MagicItem>
                    </Items>
                </mwu:MagicToolBar>
   </div>
    </div>
    </form>
</body>
</html>
