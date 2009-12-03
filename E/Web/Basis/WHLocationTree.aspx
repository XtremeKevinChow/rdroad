<%@ Page Language="C#" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Sys" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>仓库维护</title>
    <link href="../CSS/queryPage.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/treeview.css" rel="stylesheet" type="text/css" />
    <!--override the default treeview class-->
    <style type="text/css">
        .treeview li { background-image: url(../images/tree-gray-line.gif); }
        .treeview .hitarea, .treeview li.lastCollapsable, .treeview li.lastExpandable { background-image: url(../images/tree-gray.gif); }
        .mw750{ min-width:750px; _width:750px; } /*avoid the layout changed when resize window*/
        
        input { width:100px; }        
        #ajaxStatus { z-index:10010; position:absolute; width:100px; height:32px; display:none; }
        #ajaxStatus table { width: 100%; height: 32px; }
        #loadingText { padding-top: 8px; padding-bottom: 8px; padding-left: 0; padding-right: 0; width: 70px; }
        #divOrgTree { width: 270px; height: 100%; float: left;border-width: medium; border-color: Blue; }
        .divCommand { margin-left:auto; margin-right:auto; width:70px;height:25px; }
        .divInfo { width: 460px; float: left; height: 100%; }
        .divInfo table { background-color: #F8F8F8; width:100%; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/jquery.contextmenu.r2.js" type="text/javascript"></script>
    <script src="../script/jquery.treeview.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>    
    <script src="../script/whlocation.js" type="text/javascript"></script>

    <script type="text/javascript" language="javascript">
        function loadTreeWrap(){
            loadTree( { 
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadWHLocTree" };  },
                data4Delete: function(t){ return { action: "DeleteWHLoc", code: $(t).attr("oi"), type: $(t).attr("ot"), parent: $(t).attr("pi") };  },
                data4Load: function(t){ return { action: "LoadWHLoc", code: $(t).attr("oi"), type: $(t).attr("ot"), parent: $(t).attr("pi") }; }
            });
        }
        function saveWHLocWrap(){
            saveLocation({
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadWHLocTree" };  },
                data4Delete: function(t){ return { action: "DeleteWHLoc", code: $(t).attr("oi"), type: $(t).attr("ot"), parent: $(t).attr("pi") };  },
                data4Load: function(t){ return { action: "LoadWHLoc", code: $(t).attr("oi"), type: $(t).attr("ot"), parent: $(t).attr("pi") }; }
            });
        }
        $(document).ready(function(){
            loadTreeWrap();
            $("#cmdSaveLocation").bind("click", saveWHLocWrap);
            $("#cmdSaveArea").bind("click", saveWHLocWrap);
            $("#cmdSaveSection").bind("click", saveWHLocWrap);
        });//$(document).ready(function(){
    </script>
    <script runat="server" language="C#">
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                using (ISession session = new Session())
                {
                    Org org = Org.Get(session, OrgType.Own);
                    this.drpOrg.Items.Clear();
                    this.drpOrg.Items.Add(new ListItem(org.OrgName, org.OrgId.ToString()));
                }
            }
        }
    </script>
</head>
<body>
<form runat="server" id="form1">
    <div style="display: none;" id="treeMenu">
        <ul>
            <li id="cmdInsertL"><img src="../images/b_addL.gif" />&nbsp;&nbsp;&nbsp;添加仓储地</li>
            <li id="cmdInsertA"><img src="../images/b_addL.gif" />&nbsp;&nbsp;&nbsp;添加仓库区域</li>
            <li id="cmdDeleteL"><img src="../images/b_delete.gif" />&nbsp;&nbsp;&nbsp;删除仓储地</li>
            <li id="cmdInsertS"><img src="../images/b_addL.gif" />&nbsp;&nbsp;&nbsp;添加货架</li>
            <li id="cmdDeleteA"><img src="../images/b_delete.gif" />&nbsp;&nbsp;&nbsp;删除仓库区域</li>
            <li id="cmdDeleteS"><img src="../images/b_delete.gif" />&nbsp;&nbsp;&nbsp;删除货架</li>
            <li id="cmdInfo">该仓库区域配置为不可执行操作</li>
        </ul>
    </div>
    <div id="ajaxStatus">
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td id="loadingText"></td>
                <td><img src="../images/loading.gif" /></td>
            </tr>
        </table>
    </div>
    <table border="0" cellspacing="0" cellpadding="0" class="pageNav" 
        style="height:26px; border:1px solid #ccc; border-collapse:collapse; background:#fff; margin-bottom:4px;">
        <tr>
            <td style="font-size:12px; font-weight:bold;">
                <img src="../images/ico_pagenav.gif" alt=" " align="absmiddle" style="border:0;margin-left:6px; margin-right:6px;" />
                <span id="lblPageTitle">仓库维护</span>
            </td>
        </tr>
    </table>
    <div style="height:1px; width: 742px; overflow: hidden;"></div>
    <div class="mw750">
    <div id="divOrgTree">
    </div>
    <input type="text" style="display:none;" id="txtId" />
    <input type="text" style="display:none;" id="txtParent" />
    <input type="text" style="display:none;" id="txtOpt" />
    <input type="text" style="display:none;" id="txtType" />
    <input type="text" style="display:none;" id="txtParentType" />
    <input type="text" style="display:none;" id="txtAllowDelete" />
    <input type="text" style="display:none;" id="txtAllowChild" />
    
    <div id="divLocation" style="display:none;" class="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="2" style="font-weight: bold">仓库</td>
            </tr>
            <tr>
                <td class="label" style="width:100px;">公司：</td>
                <td>
                    <asp:DropDownList ID="drpOrg" runat="server" Width="200px" CssClass="select">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:100px;">状态：</td>
                <td>
                    <select id="drpLStatus" style="width:80px;" class="select">
                        <option value="1">禁用</option>
                        <option value="2">启用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="label">代码：</td>
                <td>
                    <input id="txtLCode" type="text" maxlength="8" class="input" style="width:80px;" />*
                </td>
            </tr>
            <tr>
                <td class="label">名称：</td>
                <td>
                    <input id="txtLName" type="text" style="width: 200px;" maxlength="15"  class="input" />*
                </td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td><textarea id="txtLDesc" style="width: 350px;height:35px;" class="input" rows="2" onkeypress="return exceedMaxlen(this, 40);"></textarea></td>
            </tr>
            <tr>
                <td class="label">地址：</td>
                <td><textarea id="txtLAddr" style="width: 350px;height:35px;" class="input" rows="2" onkeypress="return exceedMaxlen(this, 70);"></textarea></td>
            </tr>
            <tr>
                <td class="label">邮编：</td>
                <td><input id="txtLZipCode" type="text" style="width: 80px;" maxlength="10" class="input" /></td>
            </tr>
            <tr>
                <td class="label">联系人：</td>
                <td><input id="txtLContact" type="text" style="width: 120px;" maxlength="20" class="input" /></td>
            </tr>
            <tr>
                <td class="label">电话：</td>
                <td><input id="txtLPhone" type="text" style="width: 120px;" maxlength="16" class="input" /></td>
            </tr>
            <tr>
                <td class="label">传真：</td>
                <td><input id="txtLFax" type="text" style="width: 120px;" maxlength="16" class="input" /></td>
            </tr>
        </table>
        <div style="height:8px; overflow:hidden;"></div>
        <div class="divCommand">
            <a href="#a" class="toolbutton" id="cmdSaveLocation" style="width:45px; float:left;"><img src="../images/b_save.gif" alt="" /> 保存</a> 
        </div>
    </div>
    
    <div id="divArea" style="display:none;" class="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="2" style="font-weight: bold">库位</td>
            </tr>
            <tr>
                <td class="label">状态：</td>
                <td>
                    <select id="drpAStatus" style="width:80px;">
                        <option value="1">禁用</option>
                        <option value="2">启用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:100px;" class="label">代码：</td>
                <td>
                    <input id="txtACode" type="text" maxlength="8" style="width:80px;" class="input" />*
                </td>
            </tr>
            <tr>
                <td class="label">名称：</td>
                <td><input id="txtAName" type="text" style="width: 200px;" maxlength="15"  class="input" />*</td>
            </tr>
            <tr>
                <td class="label">存储容量：</td>
                <td>
                    <input id="txtACap" type="text" maxlength="8" style="width:80px;" class="input" />
                </td>
            </tr>
            <tr>
                <td class="label">是否设置货架：</td>
                <td align="left"><input type="checkbox" id="chkHasSec" style="width:15px; margin-left:0px;" /></td>
            </tr>
            <tr>
                <td class="label">是否质检仓：</td>
                <td align="left"><input type="checkbox" id="chkIsQC" style="width:15px; margin-left:0px;" disabled="true" /></td>
            </tr>
            <tr>
                <td class="label">是否废品仓：</td>
                <td align="left"><input type="checkbox" id="chkIsScrap" style="width:15px; margin-left:0px;" disabled="true" /></td>
            </tr>
            <tr>
                <td class="label">是否非正式仓：</td>
                <td align="left"><input type="checkbox" id="chkIsNonFormal" style="width:15px; margin-left:0px;" disabled="true" /></td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td><textarea id="txtADesc" style="width: 350px;height:35px;" class="input" rows="2" onkeypress="return exceedMaxlen(this, 40);"></textarea></td>
            </tr>
        </table>
        <div style="height:8px; overflow:hidden;"></div>
        <div class="divCommand">
            <a href="#a" class="toolbutton" id="cmdSaveArea" style="width:45px; float:left;"><img src="../images/b_save.gif" alt="" /> 保存</a> 
        </div>
    </div>
    
    <div id="divSection" style="display:none;" class="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="2" style="font-weight: bold">货架</td>
            </tr>
            <tr>
                <td class="label" style="width:100px;">状态：</td>
                <td>
                    <select id="drpSStatus" style="width:80px;">
                        <option value="1">禁用</option>
                        <option value="2">启用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="label">代码：</td>
                <td>
                    <input id="txtSCode" type="text" maxlength="10" style="width:80px;" class="input" />*
                </td>
            </tr>
            <tr>
                <td class="label">存储容量：</td>
                <td><input id="txtSCap" type="text" style="width: 80px;" maxlength="10" class="input" /></td>
            </tr>
            <tr>
                <td class="label">备注： </td>
                <td><textarea id="txtSDesc" style="width: 350px;height:35px;" class="input" rows="2" onkeypress="return exceedMaxlen(this, 40);"></textarea></td>
            </tr>
        </table>
        <div style="height:8px; overflow:hidden;"></div>
        <div class="divCommand">
            <a href="#a" class="toolbutton" id="cmdSaveSection" style="width:45px; float:left;"><img src="../images/b_save.gif" alt="" /> 保存</a> 
        </div>
    </div>
    </div>
</form>
</body>
</html>