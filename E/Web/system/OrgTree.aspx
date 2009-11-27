<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>企业组织结构</title>
    <link href="../CSS/queryPage.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/treeview.css" rel="stylesheet" type="text/css" />
    <!--override the default treeview class-->
    <style type="text/css">
        .treeview li { background-image: url(../images/tree-gray-line.gif); }
        .treeview .hitarea, .treeview li.lastCollapsable, .treeview li.lastExpandable { background-image: url(../images/tree-gray.gif); }
        .mw750{ min-width:750px; _width:750px; } /*avoid the layout changed when resize window*/
        
        #ajaxStatus { z-index:10010; position:absolute; width:100px; height:32px; display:none; }
        #ajaxStatus table { width: 100%; height: 32px; }
        #loadingText { padding-top: 8px; padding-bottom: 8px; padding-left: 0; padding-right: 0; width: 70px; }
        #divOrgTree { width: 330px; height: 100%; float: left;border-width: medium; border-color: Blue; }
        #divCommand { margin-left:auto; margin-right:auto; width:130px; }
        #divInfo { width: 410px; float: left; height: 100%; }
        #divInfo table { background-color: #F8F8F8; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/jquery.contextmenu.r2.js" type="text/javascript"></script>
    <script src="../script/jquery.treeview.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>    
    <script src="../script/org.js" type="text/javascript"></script>
    <script src="../script/selector.js" type="text/javascript"></script>

    <script type="text/javascript" language="javascript">
        function data4Save(){
            return {
                action: "SaveOrg",
                opt: $("#txtOpt").val(),
                type: "org",
                parent:  $("#txtParent").val(),
                id: $("#txtId").val(),
                code: $("#txtCode").val(),
                name: $("#txtName").val(),
                seq: $("#txtSeq").val(),
                managerId: $("#txtManagerId").val(),
                remark: $("#txtRemark").val()
            };
        }
        function saveHandler(){
            if($("#txtOpt").val()!="update" && $("#txtOpt").val()!="create") return false;
            if($("#txtCode").val()==null || $("#txtCode").val().length<=0 || $("#txtName").val()==null || $("#txtName").val().length<=0){
                mm.msg({msg:"部门代码或名称必须填写", title:"资料不完整", timeout:2, width:180, valign: "top-in", vmargin:100 });
                return false;
            }
            return true;
        }
        function clearView(opt, parentId) {
            $("#txtOpt").val(opt);
            $("#txtId").val("-1");
            $("#txtParent").val(parentId);
            $("#txtCode").val("");
            $("#txtName").val("");
            $("#txtSeq").val("");
            $("#txtManager").val("");
            $("#txtManagerId").val("-1");
            $("#txtCreateBy").val("");
            $("#txtCreateTime").val("");
            $("#txtModifyBy").val("");
            $("#txtModifyTime").val("");
            $("#txtRemark").val("");
        }//function clearOrgInfo(){
        function setView(data){
            $("#txtOpt").val("update");
            $("#txtId").val(data.id);
            $("#txtParent").val(data.parent);
            $("#txtCode").val(data.code);
            $("#txtName").val(data.name);
            $("#txtSeq").val(data.seq);
            $("#txtManagerId").val(data.managerId);
            $("#txtManager").val(data.manager);
            $("#txtCreateBy").val(data.createBy);
            $("#txtCreateTime").val(data.createTime);
            $("#txtModifyBy").val(data.modifyBy);
            $("#txtModifyTime").val(data.modifyTime);
            $("#txtRemark").val(data.remark);
        }
        function loadTreeWrap(){
            loadTree( { 
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadOrgTree", type:"org" };  },
                data4Delete: function(t){ return { action: "DeleteOrg", id: $(t).attr("oid"), type:"org" };  },
                data4Load: function(id){ return { action: "LoadOrg", id: id, type:"org" }; }
            });
        }
        function saveOrgWrap(){
            saveOrg({
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadOrgTree", type:"org" };  },
                data4Delete: function(t){ return { action: "DeleteOrg", id: $(t).attr("oid"), type:"org" };  },
                data4Load: function(id){ return { action: "LoadOrg", id: id, type:"org" }; }
            });
        }
        $(document).ready(function(){
            loadTreeWrap();
            $("#cmdSave").bind("click", saveOrgWrap);
            $("#cmdRefresh").bind("click", loadTreeWrap);
            
            $("#cmdSelectUser").bind("click", function(){
                var opt = $("#txtOpt").val();
                if(opt!="create" && opt !="update") return;
                var q = new query("User");
                q.fnPopup({
                    top:10, left:50, width:400, height:250, mode: "s", title: "选择负责人",
                    data: { selected: [{ userId: $("#txtManagerId").val(), fullName: $("#txtManager").val() }], orgId: $("#txtId").val() },
                    on_select: function(r) {
                        if(r.length>0){
                            $("#txtManagerId").val(r[0].userId);
                            $("#txtManager").val(r[0].fullName);
                        }//if(r.length>0){
                        else{
                            $("#txtManagerId").val("");
                            $("#txtManager").val("");
                        }
                    }//on_select
                });//q.fnPopup
            });
        });//$(document).ready(function(){
    </script>
</head>
<body>
    <div style="display: none;" id="treeMenu">
        <ul>
            <li id="cmdInsert"><img src="../images/b_addL.gif">&nbsp;&nbsp;&nbsp;新增</li>
            <li id="cmdDelete"><img src="../images/b_delete.gif">&nbsp;&nbsp;&nbsp;删除</li>
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
                <span id="lblPageTitle">组织结构维护</span>
            </td>
        </tr>
    </table>
    <div style="height:1px; width: 742px; overflow: hidden;"></div>
    <div class="mw750">
    <div id="divOrgTree">
    </div>
    <div id="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="4" style="font-weight: bold">部门信息</td>
            </tr>
            <tr>
                <td class="label">部门代码：</td>
                <td colspan="3">
                    <input type="hidden" id="txtId" />
                    <input type="hidden" id="txtParent" />
                    <input type="hidden" id="txtOpt" />
                    <input id="txtCode" type="text" maxlength="15" class="input" />*
                </td>
            </tr>
            <tr>
                <td class="label">部门名称：</td>
                <td colspan="3"><input id="txtName" type="text" style="width: 250px;" maxlength="20" class="input"  />*</td>
            </tr>
            <tr>
                <td class="label">排列顺序：</td>
                <td colspan="3"><input id="txtSeq" type="text" style="width: 30px;" maxlength="4" class="input"  /></td>
            </tr>
            <tr>
                <td class="label">负责人：</td>
                <td colspan="3">
                    <input id="txtManager" type="text" maxlength="20" readonly="readonly" class="readonly input" style="float:left;margin-right:5px;" />
                    <input id="txtManagerId" type="text" style="display:none;" />
                    <div id="cmdSelectUser" style="float:left; background-image:url(../images/cuser.gif); background-position:center; background-repeat:no-repeat; width:20px;height:19px;margin-top:auto;margin-bottom:auto; cursor:pointer;border:0;"></div>
                </td>
            </tr>
            <tr>
                <td class="label">创建用户：</td>
                <td><input id="txtCreateBy" type="text"  readonly="readonly" class="readonly input" style="width:100px;"  /></td>
                <td style="text-align:right;" class="label">建档时间：</td>
                <td><input id="txtCreateTime" type="text" readonly="readonly" class="readonly input" style="width:100px;"  /></td>
            </tr>
            <tr>
                <td style="width:105px;" class="label">更新用户：</td>
                <td style="width:120px;"><input id="txtModifyBy" type="text" readonly="readonly input" class="readonly input" style="width:100px;"  /></td>
                <td style="width:65px; text-align:right;" class="label">更新时间：</td>
                <td style="width:120px;"><input id="txtModifyTime" type="text" readonly="readonly input" class="readonly input" style="width:100px;"  /></td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td colspan="3"><textarea id="txtRemark" cols="1" rows="1" style="width: 310px; height: 40px; font-size:12px;" class="input"></textarea></td>
            </tr>
        </table>
        <div style="height:8px; overflow:hidden;"></div>
        <div id="divCommand">
            <a href="#a" class="toolbutton" id="cmdSave" style="width:45px; float:left;"><img src="../images/b_save.gif" alt="" /> 保存</a> 
            <a href="#a" class="toolbutton" id="cmdRefresh" style="width:45px; float:right;"><img src="../images/b_refresh.gif" alt="" /> 刷新</a> 
        </div>
    </div></div>
</body>
</html>