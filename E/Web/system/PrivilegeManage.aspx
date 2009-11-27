<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>权限管理</title>
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
        /*avoid the layout changed when resize window*/input
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
        #divGroupTree
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

    <script type="text/javascript" language="javascript">      
       
        function loadTreeWrap(){
            loadTree( { 
                type: "privilege" ,
                data4LoadTree: function() { return { action: "LoadUserGroupTree", type:"operation" };  }
            });
        }
    
        function loadTree(settings)
        {
            $("#loadingText").html("正在加载...");
            mm.mask( { } )
                .__popup( { pop_src: "#ajaxStatus", pop_target: "#divGroupTree", sysFrame: false });
            $.ajax({
                async: true,
                success: function(data) {
                    if(data._error) alert(data._error_msg);
                    else {
                        settings.resultid="#"+data.id;
                        $("#divGroupTree").html(data.html);
                        setupTree(settings);
                        
                    }
                    mm.__close("#ajaxStatus").maskClose();
                },
                error: function(data, msg, e) {
                    alert(e + " " + msg + " " + data);
                    mm.__close("#ajaxStatus").maskClose();
                },
                data: settings.data4LoadTree(),
                dataType: "json",
                timeout: 15000,
                type: "POST",
                url: "../ajax.ashx"
            });
        }
        
        function setupTree(settings)
        {           
             $(settings.resultid).treeview({
                onclick: function(em){ selectItemClick(em, settings); },
                selected: false
            });
          
        }
        
        function selectItemClick(em, settings)
        {
            var node=$(em);

            var uid = parseInt($(node[0]).attr("oid"));
            
            if(uid>-1)
            {
                $("#Privilege").attr("src","AssignPermission.aspx?type=2&uid="+uid);
            }
//            if(node.hasClass("selected")) {
//                node.removeClass("selected").prevAll("input").filter(":last").removeAttr("checked");
//                return;
//            }
//            if(settings && settings.mode=="s")
//                $(settings.resultid).find("span.selected").removeClass("selected").prevAll("input").filter(":last").removeAttr("checked");
//            node.addClass("selected").prevAll("input").filter(":last").attr("checked", true);
        }
        
        $(document).ready(function(){
            loadTreeWrap();           
        });
    </script>

</head>
<body>
    <div id="ajaxStatus">
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td id="loadingText">
                </td>
                <td>
                    <img alt="loading..." src="../images/loading.gif" />
                </td>
            </tr>
        </table>
    </div>
    <table border="0" cellspacing="0" cellpadding="0" class="pageNav" 
        style="height:26px; border:1px solid #ccc; border-collapse:collapse; background:#fff; margin-bottom:4px;">
        <tr>
            <td style="font-size:12px; font-weight:bold;">
                <img src="../images/ico_pagenav.gif" alt=" " align="absmiddle" style="border:0;margin-left:6px; margin-right:6px;" />
                <span id="lblPageTitle">权限维护</span>
            </td>
        </tr>
    </table>
    <div style="height: 1px; width: 742px; overflow: hidden;">
    </div>
    <div class="mw750">
        <div id="divGroupTree">
        </div>
        <div id="divInfo">
            <iframe id="Privilege" src="AssignPermission.aspx" frameborder="0" scrolling="auto"
                width="430px" height="460px" marginheight="0" marginwidth="0"></iframe>
        </div>
    </div>
</body>
</html>
