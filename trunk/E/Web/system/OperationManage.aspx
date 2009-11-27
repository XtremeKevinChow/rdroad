<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>操作管理</title>
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
        #divOpTree { width: 330px; height: 100%; float: left;border-width: medium; border-color: Blue; }
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
    <script src="../script/jquery.bgiframe.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/operation.js" type="text/javascript"></script>
    
    <script type="text/javascript" language="javascript">
       
        function data4Save(){
       // alert($("input[name='rdlStatus'][checked]").val());
            return { action: "SaveOperation", 
                    opt: $("#txtOpt").val(),             
                    type: "operation",
                parent:  $("#txtParent").val(),
                id: $("#txtId").val(),
                name: $("#txtName").val(),
                type: $("#txtType").val(),                
                desc: $("#txtDesc").val(),
                entry:$("#txtEntry").val(),
                image:$("#txtImage").val(),
                seq:$("#txtSeqNo").val(),
                status:$("input[name='rdlStatus'][checked]").val()
            };
        }
        
        function saveHandler(){
            if($("#txtOpt").val()!="update" && $("#txtOpt").val()!="create") return false;
            if($.trim($("#txtDesc").val()).length<=0){
                mm.msg({msg:"功能描述必须填写", title:"资料不完整", timeout:2, width:180,height:20, valign: "top-in", vmargin:100 });
                return false;
            }
            return true;
        }
        
        function clearView(opt, parentId,target) {
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
            $("#txtOpt").val(opt);
            $("#txtId").val("-1");
            $("#txtParent").val(parentId);
            $("#txtType").val("");
            $("#txtName").val("");
            $("#txtDesc").val("");
            $("#txtEntry").val("");
            $("#txtImage").val("");
            $("#txtSeqNo").val(seq);
            $("#status_active").attr("checked","checked");
        }
        function setView(data){
            $("#txtOpt").val("update");
            $("#txtId").val(data.id);
            $("#txtParent").val(data.parent);
            $("#txtType").val(data.type);
            $("#txtName").val(data.name);
            $("#txtDesc").val(data.desc);
            $("#txtEntry").val(data.entry);
            $("#txtImage").val(data.image);
            $("#txtSeqNo").val(data.seq);
           $("input[name='rdlStatus'][value='"+data.status+"']").attr("checked","checked");
        }
        function loadTreeWrap(){
            loadTree( { 
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadOperationTree", type:"operation" };  },
                data4Delete: function(t){ return { action: "DeleteOperation", id: $(t).attr("oid"), type:"operation" };  },
                data4Load: function(id){ return { action: "LoadOperation", id: id, type:"operation" }; }
            });
        }
        function saveOrgWrap(){
            saveOp({
                type: "mgmt" ,
                data4LoadTree: function() { return { action: "LoadOperationTree", type:"operation" };  },
                data4Delete: function(t){ return { action: "DeleteOperation", id: $(t).attr("oid"), type:"operation" };  },
                data4Load: function(id){ return { action: "LoadOperation", id: id, type:"operation" }; }
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
    <input type="hidden" id="txtId" />
    <input type="hidden" id="txtParent" />
    <input type="hidden" id="txtOpt" />
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
                <span id="lblPageTitle">功能维护</span>
            </td>
        </tr>
    </table>
    <div style="height:1px; width: 742px; overflow: hidden;"></div>
    <div class="mw750">
    <div id="divOpTree">
    </div>
    <div id="divInfo">
        <table cellpadding="1" cellspacing="2">
            <tr>
                <td colspan="2" style="font-weight: bold; text-align:left;" class="label">功能设置：</td>
            </tr>
             <tr>
                <td class="label">功能描述：</td>
                <td>
                    <input id="txtDesc" style="width: 250px;"  maxlength="25" class="input" />
                    <span style="color:Red;">*</span>
                </td>
            </tr>
             <tr>
                <td class="label">序号：</td>
                <td><input id="txtSeqNo" maxlength="3" class="input" style="width:40px;" /></td>
            </tr>
            <tr>
                <td class="label">类型：</td>
                <td>
                    <select id="txtType"  style="width: 150px;">
                        <option value="Module">系统模块</option>
                        <option value="Feature" selected="selected">系统功能</option>
                    </select></td>
            </tr>
             <tr>
                <td class="label">URL地址：</td>
                <td><input id="txtEntry" style="width: 250px;"  maxlength="250" class="input" /></td>
            </tr>
           <tr>
                <td class="label">状态：</td>
                  <td><input id="status_active" name="rdlStatus" type="radio" value="Active" checked="checked"/>启用<input id="status_deactive" name="rdlStatus" type="radio" value="Deactive"/>禁用</td>
           </tr>
             <tr style="display:none;">
                <td>图标</td>
                <td><input id="txtImage" style="width: 250px;"  maxlength="250" /></td>
            </tr>
            <tr style="display:none;">
                <td class="label">功能名称：</td>
                <td>
                    <input id="txtName" type="text" maxlength="50" style="width:250px;" class="input" />
                    <span style="color:Red;">*</span>
                </td>
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