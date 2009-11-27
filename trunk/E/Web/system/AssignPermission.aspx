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
        .mw750{ min-width:425px; _width:425px; } /*avoid the layout changed when resize window*/
        
             
        #ajaxStatus { z-index:10010; position:absolute; width:100px; height:32px; display:none; }
        #ajaxStatus table { width: 100%; height: 32px; }
        #loadingText { padding-top: 8px; padding-bottom: 8px; padding-left: 0; padding-right: 0; width: 70px; }
        #divOpTree { width: 100%; height: 100%; float: left;border-width: medium; border-color: Blue; list-style-type:none;}
        #divCommand { margin-left:auto; margin-right:auto; width:130px; }
        #divInfo { width: 100% ; float: left; height: 100%; }
        #divInfo table { background-color: #F8F8F8; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/jquery.contextmenu.r2.js" type="text/javascript"></script>
    <script src="../script/jquery.treeview.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
 
    <script type="text/javascript" language="javascript">
       
        function data4Save(){
            var prms = GetPrms();
            return { action: "SavePrmsTree",
                type: $("#txtType").val(),
                uid: $("#txtPrivilegerId").val(),
                assigned: prms.assigned,
                removed: prms.removed
            };
        }
        
        //before save
        function saveHandler()
        {
           return true;
        }      
        
        function GetPrms()
        {
             var prmTree = $("#assignedPrms");
             var assignedPrms = prmTree.find("input[assigned='false'][checked]");
             var removedPrms = prmTree.find("input[assigned='true']").filter(":not(:checked)");
             var assignedOids = "", removedOids = "";
             for(i=0;i<assignedPrms.length;i++)
             {
                assignedOids += $(assignedPrms[i]).attr("opid") +",";
             }
             for (i = 0; i < removedPrms.length; i++)
             {
                 removedOids += $(removedPrms[i]).attr("opid") + ",";
            }
            return { assigned: assignedOids, removed: removedOids };
        }  

        function loadTreeWrap(){
            loadTree({
                data4LoadTree: function() { return { action: "LoadPrmsTree", uid: $("#txtPrivilegerId").val(), type: $("#txtType").val() }; }
            });
        }
        function savePrmsWrap(){
            savePrms({
                data4LoadTree: function() { return { action: "LoadPrmsTree", uid: $("#txtPrivilegerId").val(), type: $("#txtType").val() }; }
            });
        }
        
        function loadTree(settings)
        {
            $("#loadingText").html("正在加载...");
            mm.mask( { } )
                .__popup( { pop_src: "#ajaxStatus", pop_target: "#divOpTree", sysFrame: false });
            $.ajax({
                async: true,
                success: function(data) {
                    if (data._error) alert(data._error_msg);
                    else {
                        settings.resultid = "#" + data.id;
                        $("#txtName").text(data.name);
                        $("#divOpTree").html(data.html);
                        buildTree(settings);

                    }
                    mm.__close("#ajaxStatus").maskClose();
                },
                error: function(data, msg, e) {
                    alert(e + " " + msg + " " + data);
                    mm.__close("#ajaxStatus").maskClose();
                },
                data: settings.data4LoadTree(),
                dataType: "json",
                timeout: 30000,
                type: "POST",
                url: "../ajax.ashx"
            });
        }
        
        //构造树
        function buildTree(settings)
        {
            $(settings.resultid).treeview({
                onclick: function(em) { },
                selected: false
            });
        }

        //保存权限 Assign Permissions
        function savePrms(settings)
        {
             if(!saveHandler()) return;
            settings.resultid="#opTree";
            $("#loadingText").html("正在保存...");
            mm.mask({}).__popup( { pop_src: "#ajaxStatus", sysFrame: false, valign: "top-in", vmargin:100 });
            $.ajax({
                async: true,
                success: function(data) {
                    if (data._error)
                        alert(data._error_msg);
                    else
                        loadTreeWrap();
                    mm.__close("#ajaxStatus");
                    mm.msg({ msg: data.desc, title: "提示", timeout: 0.6, width: 180, valign: "top-in", vmargin: 100 });
                },
                error: function(data, msg, e) {
                    alert(e + " " + msg + " " + data);
                    mm.__close("#ajaxStatus").maskClose();
                },
                data: data4Save(),
                dataType: "json",
                timeout: 30000,
                type: "POST",
                url: "../ajax.ashx"
            });
        }

        $(document).ready(function() {
            //获取参数
            var uid = mm.request("uid");
            var type = mm.request("type");
            $("#txtPrivilegerId").val(uid);
            $("#txtType").val(type);
            if (uid != "") {
                loadTreeWrap();
                $("#cmdSave").bind("click", savePrmsWrap);
                $("#cmdRefresh").bind("click", loadTreeWrap);
            }
        });
    </script>
</head>
<body>    
    <div id="ajaxStatus">
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td id="loadingText"></td>
                <td><img src="../images/loading.gif" /></td>
            </tr>
        </table>
    </div>
    <div style="height:1px; width: 425px; overflow: hidden;"></div>
    <div class="mw750">
        <div id="divInfo">
        <table cellpadding="1" cellspacing="2" style="width:100%;">
            <tr>
                <td  style="font-weight: bold">当前用户群组:&nbsp;&nbsp;<span id="txtName"></span></td>
                <td><input type="hidden" id="txtPrivilegerId" value="" /><input type="hidden" id="txtType" value="" /></td>                
            </tr>
            <tr>
                <td colspan="4" align="center">
                 <a href="#a" class="toolbutton" id="cmdSave" style="width:45px;"><img src="../images/b_save.gif" alt="Save"  align="middle"/> 保存</a> 
                <a href="#a" class="toolbutton" id="cmdRefresh" style="width:45px; "><img src="../images/b_refresh.gif" alt="Refresh" align="middle" /> 刷新</a> </td>
            </tr>
          </table>
        
        <div id="divCommand">
           
        </div>
    </div>
    <div id="divOpTree">
    </div>
    
    </div>
</body>
</html>