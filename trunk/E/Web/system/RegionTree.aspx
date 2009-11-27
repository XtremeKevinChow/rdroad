<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>省份、城市、区/县维护</title>
    <link href="../CSS/common.css" rel="stylesheet" type="text/css" />
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
        .divInfo { width: 200px; float: left; height: 100%; }
        .divInfo table { background-color: #F8F8F8; width:100%; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/jquery.contextmenu.r2.js" type="text/javascript"></script>
    <script src="../script/jquery.treeview.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>    
    <script src="../script/region.js" type="text/javascript"></script>

    <script type="text/javascript" language="javascript">
        function loadTreeWrap(){
            loadTree( { 
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadRegionTree" };  },
                data4Delete: function(t){ return { action: "DeleteRegion", id: $(t).attr("oi"), type: $(t).attr("ot") };  },
                data4Load: function(t){ return { action: "LoadRegion", id: $(t).attr("oi"), type: $(t).attr("ot") }; }
            });
        }
        function saveRegionWrap(){
            saveRegion({
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadRegionTree" };  },
                data4Delete: function(t){ return { action: "DeleteRegion", id: $(t).attr("oi"), type: $(t).attr("ot") };  },
                data4Load: function(t){ return { action: "LoadRegion", id: $(t).attr("oi"), type: $(t).attr("ot") }; }
            });
        }
        $(document).ready(function(){
            loadTreeWrap();
            $("#cmdSaveProvince").bind("click", saveRegionWrap);
            $("#cmdSaveCity").bind("click", saveRegionWrap);
            $("#cmdSaveDistrict").bind("click", saveRegionWrap);
        });//$(document).ready(function(){
    </script>
</head>
<body>
    <div style="display: none;" id="treeMenu">
        <ul>
            <li id="cmdInsertP"><img src="../images/b_addL.gif">&nbsp;&nbsp;&nbsp;添加省份</li>
            <li id="cmdInsertC"><img src="../images/b_addL.gif">&nbsp;&nbsp;&nbsp;添加城市</li>
            <li id="cmdDeleteP"><img src="../images/b_delete.gif">&nbsp;&nbsp;&nbsp;删除省份</li>
            <li id="cmdInsertD"><img src="../images/b_addL.gif">&nbsp;&nbsp;&nbsp;添加区域</li>
            <li id="cmdDeleteC"><img src="../images/b_delete.gif">&nbsp;&nbsp;&nbsp;删除城市</li>
            <li id="cmdDeleteD"><img src="../images/b_delete.gif">&nbsp;&nbsp;&nbsp;删除区域</li>
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
    <table border="0" cellspacing="1" cellpadding="0" class="pageNav mw750">
        <tr>
            <td class="f12b"><img src="../images/ico_pagenav.gif" alt=" " width="16" height="16" />省份、城市、区/县维护</td>
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
    <div id="divProvince" style="display:none;" class="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="2" style="font-weight: bold">省份信息</td>
            </tr>
            <tr>
                <td style="width:40px;">代码</td>
                <td>
                    <input id="txtPCode" type="text" maxlength="10" />*
                </td>
            </tr>
            <tr>
                <td>名称</td>
                <td><input id="txtPName" type="text" style="width: 100px;" maxlength="20"  />*</td>
            </tr>
            <tr>
                <td>简称</td>
                <td><input id="txtPAlias" type="text" style="width: 100px;" maxlength="20"  /></td>
            </tr>
        </table>
        <div style="height:8px; overflow:hidden;"></div>
        <div class="divCommand">
            <a href="#a" class="toolbutton" id="cmdSaveProvince" style="width:45px; float:left;"><img src="../images/b_save.gif" alt="" /> 保存</a> 
        </div>
    </div>
    
    <div id="divCity" style="display:none;" class="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="2" style="font-weight: bold">城市信息</td>
            </tr>
            <tr>
                <td style="width:40px;">代码</td>
                <td>
                    <input id="txtCCode" type="text" maxlength="10" style="width:100px;" />*
                </td>
            </tr>
            <tr>
                <td>名称</td>
                <td><input id="txtCName" type="text" style="width: 100px;" maxlength="20"  />*</td>
            </tr>
        </table>
        <div style="height:8px; overflow:hidden;"></div>
        <div class="divCommand">
            <a href="#a" class="toolbutton" id="cmdSaveCity" style="width:45px; float:left;"><img src="../images/b_save.gif" alt="" /> 保存</a> 
        </div>
    </div>
    
    <div id="divDistrict" style="display:none;" class="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="2" style="font-weight: bold">区/县信息</td>
            </tr>
            <tr>
                <td style="width:40px;">名称</td>
                <td>
                    <input id="txtDName" type="text" maxlength="18" style="width:100px;" />*
                </td>
            </tr>
            <tr>
                <td>邮编</td>
                <td><input id="txtDZipCode" type="text" style="width: 100px;" maxlength="10"  /></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input id="txtDShipment" type="checkbox" style="width:20px;" /><label for="txtDShipment">是否送货上门</label></td>
            </tr>
        </table>
        <div style="height:8px; overflow:hidden;"></div>
        <div class="divCommand">
            <a href="#a" class="toolbutton" id="cmdSaveDistrict" style="width:45px; float:left;"><img src="../images/b_save.gif" alt="" /> 保存</a> 
        </div>
    </div>
    </div>
</body>
</html>