<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>功能导航管理</title>
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
        #divNavTree { width: 330px; height: 100%; float: left;border-width: medium; border-color: Blue; }
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
    <script src="../script/Navigator.js" type="text/javascript"></script>

    <script type="text/javascript" language="javascript">
       
        function data4Save(){
            return { action: "SaveNavigator", 
                    opt: $("#txtOpt").val(),             
                    parent:  $("#txtParent").val(),
                    seq:$("#txtSeq").val(),
                    id: $("#txtId").val(),
                    name: $("#txtName").val(),
                    type: $("#txtType").val(),                
                    entry: $("#txtEntry").val()
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
        
        function clearView(opt, parentId, target) {
            var seq = 1;
            if(target)
            {
                var span = $(target);
                var ul = span.siblings('ul');                
                if(ul)
                {
                    
                    var lastIndex = ul.children().length;
                    if(lastIndex>0)
                     {  
                        lastIndex =lastIndex -1;
                        var last = $('li:last',ul)
                        seq = $(last).find("span").attr("seq");
                        if(!isNaN(seq))
                            seq = parseInt(seq,10) +1;
                        else
                            seq = 1;
                    }
                }
            }
            $("#txtSeq").val(seq);
            $("#txtOpt").val(opt);
            $("#txtId").val("-1");
            $("#txtParent").val(parentId);
            $("#txtType").val("");
            $("#txtName").val("");
            $("#txtEntry").val("");
            
        }
        function setView(data){
            $("#txtOpt").val("update");
            $("#txtId").val(data.id);
            $("#txtParent").val(data.parent);
            $("#txtType").val(data.type);
            $("#txtName").val(data.name);
            $("#txtEntry").val(data.entry);
            $("#txtSeq").val(data.seq);
        }
        function loadTreeWrap(){
            loadTree( { 
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadNavigatorTree", type:"Navigator" };  },
                data4Delete: function(t){ return { action: "DeleteNavigator", id: $(t).attr("oid"), type:"Navigator" };  },
                data4Load: function(id){ return { action: "LoadNavigator", id: id, type:"Navigator" }; }
            });
        }
        function saveOrgWrap(){
            saveOp({
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadNavigatorTree", type:"Navigator" };  },
                data4Delete: function(t){ return { action: "DeleteNavigator", id: $(t).attr("oid"), type:"Navigator" };  },
                data4Load: function(id){ return { action: "LoadNavigator", id: id, type:"Navigator" }; }
            });
        }
        $(document).ready(function(){
            loadTreeWrap();
            $("#cmdSave").bind("click", saveOrgWrap);
            $("#cmdRefresh").bind("click", loadTreeWrap);
        });
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
    <table border="0" cellspacing="1" cellpadding="0" class="pageNav mw750">
        <tr>
            <td class="f12b"><img src="../images/ico_pagenav.gif" alt=" " width="16" height="16" />功能导航维护</td>
        </tr>
    </table>
    <div style="height:1px; width: 742px; overflow: hidden;"></div>
    <div class="mw750">
    <div id="divNavTree">
    </div>
    <div id="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="4" style="font-weight: bold">操作</td>
            </tr>
            <tr>
                <td>序号</td>
                <td colspan="3"><input id="txtSeq" style="width: 100px;"  maxlength="25"></td>
            </tr>
            <tr>
                <td>名称</td>
                <td colspan="3">
                    <input type="hidden" id="txtId" />
                    <input type="hidden" id="txtParent" />
                    <input type="hidden" id="txtOpt" />
                    <input id="txtName" type="text" maxlength="50" style="width:250px;" /><span style="color:Red;">*</span>
                </td>
            </tr>
             <tr>
                <td>路径</td>
                <td colspan="3"><input id="txtEntry" style="width: 250px;"  maxlength="200"></td>
            </tr>
            <tr>
                <td>类型</td>
                <td colspan="3">
                    <select id="txtType"  style="width: 150px;">
                        <option value="Folder">组</option>
                        <option value="Entry">入口</option>
                    </select><span style="color:Red;">*</span></td>
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

