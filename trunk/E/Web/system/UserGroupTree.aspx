<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户组和用户</title>
    <link href="../CSS/queryPage.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/treeview.css" rel="stylesheet" type="text/css" />
    <!--override the default treeview class-->
    <style type="text/css">
        .treeview li
        {
            background-image: url(../images/tree-gray-line.gif);
        }
        .treeview .hitarea, .treeview li.lastCollapsable, .treeview li.lastExpandable
        {
            background-image: url(../images/tree-gray.gif);
        }
        .mw750
        {
            min-width: 750px;
            _width: 750px;
        }
        /*avoid the layout changed when resize window*/
        input
        {
            width: 100px;
        }
        #ajaxStatus
        {
            z-index: 10010;
            position: absolute;
            width: 100px;
            height: 32px;
            display: none;
        }
        #ajaxStatus table
        {
            width: 100%;
            height: 32px;
        }
        #loadingText
        {
            padding-top: 8px;
            padding-bottom: 8px;
            padding-left: 0;
            padding-right: 0;
            width: 70px;
        }
        #divTree
        {
            width: 330px;
            height: 100%;
            float: left;
            border-width: medium;
            border-color: Blue;
        }
        #divCommand
        {
            margin-left: auto;
            margin-right: auto;
            width: 130px;
        }
        #divInfo
        {
            width: 410px;
            float: left;
            height: 100%;
        }
        #divInfo table
        {
            background-color: #F8F8F8;
        }
    </style>

    <script src="../script/jquery.js" type="text/javascript"></script>

    <script src="../script/jquery.cookie.js" type="text/javascript"></script>

    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>

    <script src="../script/jquery.contextmenu.r2.js" type="text/javascript"></script>

    <script src="../script/jquery.treeview.js" type="text/javascript"></script>

    <script src="../script/interface.fix.js" type="text/javascript"></script>

    <script src="../script/magic.js" type="text/javascript"></script>

    <script src="../script/UserGroupTree.js" type="text/javascript"></script>

    <script type="text/javascript" language="javascript">
       
       function data4Save(){
            return { action: "SaveGroup", 
                    opt: $("#txtOpt").val(),             
                    type: "operation",
                    parent:  $("#txtParent").val(),
                    id: $("#txtId").val(),
                    name: $("#txtName").val(),
                    type: $("#txtType").val(),                
                    desc: $("#txtDesc").val()
            };
        }
        
        function saveHandler(){
            if($("#txtOpt").val()!="update" && $("#txtOpt").val()!="create") return false;
            if($("#txtName").val()==null || $("#txtName").val().length<=0){
                mm.msg({msg:"操作名称必须填写", title:"资料不完整", timeout:2, width:180, valign: "top-in", vmargin:100 });
                return false;
            }
            return true;
        }
        
        function clearView(opt, parentId) {
            $("#txtOpt").val(opt);
            $("#txtId").val("-1");
            $("#txtParent").val(parentId);
            $("#txtType").val("");
            $("#txtName").val("");
            $("#txtDesc").val("");
        }
        function setView(data){
            $("#txtOpt").val("update");
            $("#txtId").val(data.id);
            $("#txtParent").val(data.parent);
            $("#txtType").val(data.type);
            $("#txtName").val(data.name);
            $("#txtDesc").val(data.desc);
        }
        function loadTreeWrap(){
            loadTree( { 
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadUserGroupTree", type:"operation" };  },
                data4Delete: function(t){ return { action: "DeleteGroup", id: $(t).attr("oid"), type:"operation" };  },
                data4Load: function(id){ return { action: "LoadGroup", id: id, type:"operation" }; }
            });
        }
        function saveCmmdWrap(){
            saveGroup({
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadUserGroupTree", type:"operation" };  },
                data4Delete: function(t){ return { action: "DeleteGroup", id: $(t).attr("oid"), type:"operation" };  },
                data4Load: function(id){ return { action: "LoadGroup", id: id, type:"operation" }; }
            });
        }
        $(document).ready(function(){
            loadTreeWrap();
            $("#cmdSave").bind("click", saveCmmdWrap);
            $("#cmdRefresh").bind("click", loadTreeWrap);
        });
    </script>

</head>
<body>
    <input type="hidden" id="txtId" />
    <input type="hidden" id="txtParent" />
    <input type="hidden" id="txtOpt" />
    <div style="display: none;" id="treeMenu">
        <ul>
            <li id="cmdInsert">
                <img alt="Add" src="../images/b_addL.gif">&nbsp;&nbsp;&nbsp;新增</li>
            <li id="cmdDelete">
                <img alt="delte" src="../images/b_delete.gif">&nbsp;&nbsp;&nbsp;删除</li>
        </ul>
    </div>
    <div id="ajaxStatus">
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td id="loadingText">
                </td>
                <td>
                    <img src="../images/loading.gif" />
                </td>
            </tr>
        </table>
    </div>
    <table border="0" cellspacing="0" cellpadding="0" class="pageNav" 
        style="height:26px; border:1px solid #ccc; border-collapse:collapse; background:#fff; margin-bottom:4px;">
        <tr>
            <td style="font-size:12px; font-weight:bold;">
                <img src="../images/ico_pagenav.gif" alt=" " align="absmiddle" style="border:0;margin-left:6px; margin-right:6px;" />
                <span id="lblPageTitle">用户组维护</span>
            </td>
        </tr>
    </table>
    <div style="height: 1px; width: 742px; overflow: hidden;">
    </div>
    <div class="mw750">
        <div id="divTree">
        </div>
        <div id="divInfo">
            <table cellpadding="1" cellspacing="2">
                <tr>
                    <td colspan="4" style="font-weight: bold">
                        用户组
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        名称：
                    </td>
                    <td colspan="3">
                        <input id="txtName" type="text" maxlength="50" style="width: 250px;" class="input" /><span style="color: Red;">*</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        描述：
                    </td>
                    <td colspan="3">
                        <input id="txtDesc" style="width: 250px;" maxlength="25" class="input">
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        类型：
                    </td>
                    <td colspan="3">
                        <select id="txtType" style="width: 150px;">
                            <option value="Administrative">系统管理</option>
                            <option value="System">系统内建</option>
                            <option value="Users">用户</option>
                        </select><span style="color: Red;">*</span>
                    </td>
                </tr>
            </table>
            <div style="height: 8px; overflow: hidden;">
            </div>
            <div id="divCommand">
                <a href="#a" class="toolbutton" id="cmdSave" style="width: 45px; float: left;">
                    <img src="../images/b_save.gif" alt="" />
                    保存</a> <a href="#a" class="toolbutton" id="cmdRefresh" style="width: 45px; float: right;">
                        <img src="../images/b_refresh.gif" alt="" />
                        刷新</a>
            </div>
        </div>
    </div>
</body>
</html>
