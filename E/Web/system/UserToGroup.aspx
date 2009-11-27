<%@ Page Language="C#" AutoEventWireup="true" CodeFile="UserToGroup.aspx.cs" Inherits="system_UserToGroup" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register src="../Controls/FunctionTitle.ascx" tagname="FunctionTitle" tagprefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>用户和用户组组维护</title>
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
	      bindTableBehavior('#UserToGroup', '#chkSelectAllUserTo');	
	      bindTableBehavior('#AllUserGroup','#chkSelectAllGroup');  
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
    <input type="hidden" id="txtUserId"  value="" runat="server" />
    <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="所属用户组" />
      <span>当前用户：</span>
      <asp:Label ID="lblUserName" runat="server" Text=""></asp:Label>
        <div style="vertical-align:top; float:left; text-align:left;width:330px;">
            <span>已经选择的用户组：</span>   
            <table  id="UserToGroup" border="0" cellpadding="0" cellspacing="0" class="datalist">
                <tr>     
                    <th style="width: 20px;">
                        <input name="" value="" id="chkSelectAllUserTo" type="checkbox" title="Select All" />
                    </th>                                        
                    <th style="width:120px;">	用户群组名称</th>
                    <th>用户群组	描述</th>
                </tr>
                <asp:Repeater ID="rptUserToGroup" runat="server">
                <ItemTemplate>
                    <tr> 
                        <td>
                            <input id="checkbox"  value='<%# Eval("GroupId") %>' type="checkbox" runat="server" />
                        </td>                                                                    
                        <td><%# Eval("Name") %></td>
                        <td><%# Eval("Description") %></td>							
                    </tr>
                </ItemTemplate>                
                </asp:Repeater>  
            </table>
        </div>
        <div style="width:35px;text-align:center; vertical-align:middle;float:left;" >
            <ul style="list-style-type:none;margin:0;padding:0; ">
                <li><span>&nbsp;</span></li>
                <li><span>&nbsp;</span></li>
                <li><span>&nbsp;</span></li>
                <li><asp:ImageButton  runat="server" id="btnRemoveFromUser" ImageUrl="../images/arrow-right.gif" 
                        Text="" onclick="btnRemoveFromUser_Click" /></li>
                <li><span>&nbsp;</span></li>                    
                <li><asp:ImageButton runat="server" ImageUrl="../images/arrow-left.gif" id="btnAddToUser" 
                        Text="" onclick="btnAddToUser_Click" /></li>
            </ul>              
        </div>
        <div style="vertical-align:top; text-align:left;width:330px; float:left;">
            <span>可选的用户组：</span>
            <table  id="AllUserGroup" border="0" cellpadding="0" cellspacing="0" class="datalist">
                <tr>  
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAllGroup" type="checkbox" title="Select All" />
                    </th>                                             
                    <th style="width:120px;">	用户群组名称</th>
                    <th>	用户群组描述</th>
                </tr> 
                <asp:Repeater ID="rptAllGroup" runat="server">
                <ItemTemplate>
                    <tr>            
                        <td align="center">
                            <input id="checkbox"  value='<%# Eval("GroupId") %>' type="checkbox" runat="server" />
                        </td>                                                         
                        <td>	<%# Eval("Name") %></td>
                        <td>	<%# Eval("Description") %></td>							
                    </tr>
                </ItemTemplate>                
                </asp:Repeater>         
            </table>
        </div> 
    </div>
    <div style="width:700px; height:5px; overflow:hidden;"></div>
    <div style="width:140px; margin-left:auto; margin-right:auto;">
        <mwu:MagicToolBar CssClass="toolbar" runat="server" Layout="Div" ID="toolbarbottom" >     
        <Items>                      
        <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
            ImageUrl="../images/b_stop.gif" >
        </mwu:MagicItem>
        </Items>
        </mwu:MagicToolBar>
    </div>
    </form>
</body>
</html>
