function loadTree(settings){
    $("#loadingText").html("正在加载...");
    mm.mask( { } )
        .__popup( { pop_src: "#ajaxStatus", sysFrame: false });
    $.ajax({
        async: true,
        success: function(data) {
            if(data._error) alert(data._error_msg);
            else {
                settings.resultid="#"+data.id;
                $("#divOrgTree").html(data.html);
                setupTree(settings);
                if(settings.type=="mgmt")
                    clearView("", "","");
            }
            mm.__close("#ajaxStatus").maskClose();
        },
        error: function(data, msg, e) {
            alert(e + " " + msg + " " + data);
            mm.__close("#ajaxStatus").maskClose();
        },
        data: settings.data4LoadTree(),
        dataType: "json",
        timeout: 5000,
        type: "POST",
        url: "../ajax.ashx"
    });//$.ajax({
}//function loadTree(){

function setupTree(settings){
    if(settings.type=="mgmt"){
        $(settings.resultid).treeview({
            onclick: function(em){ treeItemClick(em, settings); }
        });//$("#orgTree").treeview
        
        $(settings.resultid).find('span').contextMenu("treeMenu", {
            menuStyle: { width: "130px" },
            onContextMenu: function(e) { return contextMenu(e, settings); },
            onShowMenu: showMenu,
            bindings: menuBiddings(settings)
        });//$("#orgTree").find(">span").contextMenu
    }else if(settings.type=="select"){
        $(settings.resultid).treeview({
            onclick: function(em){ selectItemClick(em, settings); },
            selected: false
        });//$("#orgTree").treeview
    }
}

function clearView(opt, parentId, type, parentType, allowDelete, allowChild) {
    $("#txtOpt").val(opt);
    $("#txtId").val("");
    $("#txtParent").val(parentId);
    $("#txtType").val(type);
    $("#txtParentType").val(parentType);
    $("#txtAllowDelete").val(allowDelete);
    $("#txtAllowChild").val(allowChild);
    $("#txtLCode").val("").removeAttr("readonly").removeClass("readonly"); //Location
    $("#drpLStatus").val("2");
    $("#txtLName").val("");
    $("#txtLDesc").val("");
    $("#txtLAddr").val("");
    $("#txtLZipCode").val("");
    $("#txtLContact").val("");
    $("#txtLPhone").val("");
    $("#txtLFax").val("");
    $("#txtACode").val("").removeAttr("readonly").removeClass("readonly"); //Area
    $("#drpAStatus").val("2");
    $("#txtAName").val("");
    $("#txtADesc").val("");
    $("#txtACap").val("");
    $("#chkHasSec").removeAttr("checked");
    $("#chkIsQC").removeAttr("checked");
    $("#chkIsScrap").removeAttr("checked");
    $("#txtSCode").val("").removeAttr("readonly").removeClass("readonly"); //Section
    $("#drpSStatus").val("2");
    $("#txtSCap").val("");
    $("#txtSDesc").val("");
    
    $("#divLocation").hide();
    $("#divArea").hide();
    $("#divSection").hide();
    if(type=="l") $("#divLocation").show();
    else if(type=="a") $("#divArea").show();
    else if(type=="s") $("#divSection").show();
}//function clearOrgInfo(){

function contextMenu(e, settings){
    var target=$(e.target);
    if(target.hasClass("selected")) return true;
    treeItemClick(e.target, settings);
    $(settings.resultid).find("span.selected").removeClass("selected");
    target.addClass("selected");
    return true; 
}//function contextMenu(e){

function cmdDelete(t, settings){
    mm.mask({}).confirm({
        msg:"您确实要删除"+$(t).text()+"?", title:"删除确认", width:220, height: 16, pop_target: t, halign:"right-out", hmargin:10,
        callback:function(){
            $("#loadingText").html("正在删除...");
            mm.__popup({ pop_src: "#ajaxStatus", sysFrame: false, pop_target: $(t), halign:"right-out", hmargin:5, valign:"center" });
            $.ajax({
                async: true,
                success: function(data) {
                    if(data._error) alert(data._error_msg);
                    else {
                        $(settings.resultid).treeview({remove:$(t).parent()})
                        clearView("", "","", "", "", "");
                    }//else
                    mm.__close("#ajaxStatus").maskClose();
                }, //success: function(retValue)
                error: function(request, msg, e){
                    alert(e + " " + msg + " " + request);
                    mm.__close("#ajaxStatus").maskClose();
                },//error: function(httpRequest, msg, e
                data: settings.data4Delete(t),
                dataType: "json",
                timeout: 5000,
                type: "POST",
                url: "../ajax.ashx"
            });//$.ajax({
        }//callback:function(){
    });//mm.mask({}).confirm({
}//function cmdDelete(t){

function treeItemClick(elm, settings){ //elm为span节点
    elm=$(elm);
    if(elm.parent().parent().attr("virtual")=="1") return;
    var orgId=elm.attr("oi");
    if(orgId==null || $.trim(orgId).length<=0) return;
    var type=elm.attr("ot");
    if(type!="l" && type!="a" && type!="s") return;

    $("#loadingText").html("正在加载...");
    mm.__popup( { pop_src: "#ajaxStatus", pop_target: elm, halign: "right-out", hmargin:4, valign:"center", sysFrame: false });

    $.ajax({
        async: true,
        success: function(data) {
            if(data._error) alert(data._error_msg);
            else orgLoaded(data, false, false, settings);
            mm.__close("#ajaxStatus");
        }, //success: function(retValue)
        error: function(request, msg, e){
            alert(e + " " + msg + " " + request);
            mm.__close("#ajaxStatus");
        },//error: function(httpRequest, msg, e
        data: settings.data4Load(elm),
        dataType: "json",
        timeout: 5000,
        type: "POST",
        url: "../ajax.ashx"
    });//$.ajax({
}//function treeItemClick(elm){

function saveHandler(){
    if($("#txtOpt").val()!="update" && $("#txtOpt").val()!="create") return false;
    var type=$("#txtType").val();
    if(type!="l" && type!="a" && type!="s") return false;
    if(type=="l" && ($.trim($("#txtLCode").val()).length<=0 || $.trim($("#txtLName").val()).length<=0)){
        mm.msg({msg:"仓储地代码和名称必须填写", title:"资料不完整", timeout:2, width:180, height: 16, valign: "top-in", vmargin:100 });
        return false;
    }
    if(type=="a" && ($.trim($("#txtACode").val()).length<=0 || $.trim($("#txtAName").val()).length<=0)){
        mm.msg({msg:"仓库区域代码和名称必须填写", title:"资料不完整", timeout:2, width:180, height: 16, valign: "top-in", vmargin:100 });
        return false;
    }
    if(type=="s" && $.trim($("#txtSCode").val()).length<=0){
        mm.msg({msg:"货架代码必须填写", title:"资料不完整", timeout:2, width:180, height: 16, valign: "top-in", vmargin:100 });
        return false;
    }
    if((type=="a" && $.trim($("#txtACap").val()).length>0) || (type=="s" && $.trim($("#txtSCap").val()).length>0)) {
        var fCapacity = 0;
        if(type=="a") fCapacity = parseInt($.trim($("#txtACap").val()));
        else if(type=="s") fCapacity = parseInt($.trim($("#txtSCap").val()));
        if(isNaN(fCapacity) || fCapacity<=0){
            mm.msg({msg:"存储容量不是有效数字", title:"资料错误", timeout:2, width:180, height: 16, valign: "top-in", vmargin:100 });
            if(type=="a") document.getElementById("txtACap").focus();
            else document.getElementById("txtSCap").focus();
            return false;
        }
        if(type=="a") $("#txtACap").val(fCapacity);
        else $("#txtSCap").val(fCapacity);
    }
    return true;
}

function saveLocation(settings){
    if(!saveHandler()) return;
    settings.resultid="#whlocathonTree";
    $("#loadingText").html("正在保存...");
    mm.mask({}).__popup( { pop_src: "#ajaxStatus", sysFrame: false, valign: "top-in", vmargin:100 });
    $.ajax({
        async: true,
        success: function(data) {
            var isUpdate=true, isInsert=false;
            if($("#txtOpt").val()=="create") { isUpdate=false; isInsert=true; }
            if(data._error) alert(data._error_msg);
            else orgLoaded(data, isUpdate, isInsert, settings); 
            mm.__close("#ajaxStatus");
            mm.msg( {msg:data.text+" 保存成功", title:"操作成功", timeout: 0.6, width: 180, height: 16, valign: "top-in", vmargin:100 } );
        },
        error: function(data, msg, e) {
            alert(e + " " + msg + " " + data);
            mm.__close("#ajaxStatus").maskClose();
        },
        data: data4Save(),
        dataType: "json",
        timeout: 5000,
        type: "POST",
        url: "../ajax.ashx"
    });//$.ajax({
}//function saveLocation

function orgLoaded(orgInfo, isUpdate, isInsert, settings){
    setView(orgInfo);
    if(isUpdate) {
        var targetObject = $(settings.resultid).find('span[@oi='+orgInfo.code+']');
        targetObject.text(orgInfo.text);
        if(orgInfo.type=="a") targetObject.attr("a_sec", orgInfo.hassec==true?"1":"0");
    }
    if(isInsert){
        var selectedItem=$(settings.resultid).find("span.selected");
        if(selectedItem.length<=0) { alert("Parent node not found"); return; }
        var tagName=selectedItem[0].parentNode.lastChild.tagName;
        if(tagName!="UL" && tagName!="SPAN") { alert("Unknown treeitem node"); return; }                
        var itemToAdd;
        if(tagName=="UL"){
            itemToAdd = $('<li><span ot="'+orgInfo.type+'" oi="'+orgInfo.code+'" pi="'+orgInfo.parent+'">'+orgInfo.text+'</span></li>')
                .appendTo(selectedItem[0].parentNode.lastChild);
        }else{
            itemToAdd = $('<ul><li><span ot="'+orgInfo.type+'" oi="'+orgInfo.code+'" pi="'+orgInfo.parent+'">'+orgInfo.text+'</span></li></ul>')
                .appendTo(selectedItem[0].parentNode);
        }                
        $(settings.resultid).treeview({add:itemToAdd});
        
        itemToAdd.find('span').contextMenu("treeMenu", {
            menuStyle: { width: "130px" },
            onContextMenu: function(e) { return contextMenu(e, settings); },
            onShowMenu: showMenu,
            bindings: menuBiddings(settings)
        });//$("#orgTree").find(">span").contextMenu
        
        selectedItem.removeClass("selected");
        itemToAdd.find("span").addClass("selected");
    }//if(isInsert)
} //function orgLoaded(orgInfo)

function menuBiddings(settings){
    return { 
                "cmdInsertL": function(t) { clearView("create", "","l", "", "1", "1"); },  
                "cmdInsertA": function(t) { clearView("create", $(t).attr("oi"),"a", $(t).attr("ot"), "1", "1"); },  
                "cmdDeleteL": function(t) { cmdDelete(t, settings);},
                "cmdInsertS": function(t) { clearView("create", $(t).attr("oi"),"s", "a", "1", "0"); },  
                "cmdDeleteA": function(t) { cmdDelete(t, settings);},
                "cmdDeleteS": function(t) { cmdDelete(t, settings);}
            };
}

function showMenu(e, menu){
{
    if($(e.target).attr("ot")=="r") {
        $("#cmdInsertA", menu).remove();
        $("#cmdDeleteL", menu).remove();
        $("#cmdInsertS", menu).remove();
        $("#cmdDeleteA", menu).remove();
        $("#cmdDeleteS", menu).remove();
        $("#cmdInfo", menu).remove();
    }else if($(e.target).attr("ot")=="l"){
        $("#cmdInsertL", menu).remove();
        $("#cmdInsertS", menu).remove();
        $("#cmdDeleteA", menu).remove();
        $("#cmdDeleteS", menu).remove();
        $("#cmdInfo", menu).remove();
    }else if($(e.target).attr("ot")=="a"){
        $("#cmdInsertL", menu).remove();
        $("#cmdInsertA", menu).remove();
        $("#cmdDeleteL", menu).remove();
        $("#cmdDeleteS", menu).remove();
        var menuCount = 2;
        if($(e.target).attr("a_sec")=="0") { $("#cmdInsertS", menu).remove(); menuCount--; }
        if($(e.target).attr("a_del")=="0") { $("#cmdDeleteA", menu).remove(); menuCount--; }
        if(menuCount>0) $("#cmdInfo", menu).remove();
    }else if($(e.target).attr("ot")=="s"){
        $("#cmdInsertL", menu).remove();
        $("#cmdInsertA", menu).remove();
        $("#cmdDeleteL", menu).remove();
        $("#cmdInsertS", menu).remove();
        $("#cmdDeleteA", menu).remove();
        $("#cmdInfo", menu).remove();
    }else{
        return undefined;
    }
    return menu;
    }
}

function setView(data){
    $("#txtOpt").val("update");
    $("#txtId").val(data.code);
    $("#txtParent").val(data.parent);
    $("#txtType").val(data.type);
    if(data.parentType!=undefined) $("#txtParentType").val(data.parentType);
    else $("#txtParentType").val("");
    if(data.allowdelete!=undefined) $("#txtAllowDelete").val(data.allowdelete);
    else $("#txtAllowDelete").val("0");
    if(data.allowchild!=undefined) $("#txtAllowChild").val(data.allowchild);
    else $("#txtAllowChild").val("0");
    $("#divLocation").hide();
    $("#divArea").hide();
    $("#divSection").hide();
    if(data.type=="l"){
        $("#drpLStatus").val(data.status);
        $("#txtLCode").attr("readonly", "readonly");
        if(!$("#txtLCode").hasClass("readonly")) $("#txtLCode").addClass("readonly");
        $("#txtLCode").val(data.code);
        $("#txtLName").val(data.name);
        $("#txtLDesc").val(data.desc);
        $("#txtLAddr").val(data.addr);
        $("#txtLZipCode").val(data.zipcode);
        $("#txtLContact").val(data.contact);
        $("#txtLPhone").val(data.phone);
        $("#txtLFax").val(data.fax);
        if(data.comp>0) $("#drpOrg").val(data.comp);
        $("#divLocation").show();
    } else if(data.type=="a"){
        $("#drpAStatus").val(data.status);
        $("#txtACode").attr("readonly", "readonly");
        if(!$("#txtACode").hasClass("readonly")) $("#txtACode").addClass("readonly");
        $("#txtACode").val(data.code);
        $("#txtAName").val(data.name);
        $("#txtADesc").val(data.desc);
        $("#txtACap").val(data.cap);
        $("#chkHasSec").attr("checked", data.hassec);
        $("#chkIsQC").attr("checked", data.isqc);
        $("#chkIsScrap").attr("checked", data.isscrap);
        $("#divArea").show();
    }else if(data.type=="s"){
        $("#drpSStatus").val(data.status);
        $("#txtSCode").attr("readonly", "readonly");
        if(!$("#txtSCode").hasClass("readonly")) $("#txtSCode").addClass("readonly");
        $("#txtSCode").val(data.code);
        $("#txtSDesc").val(data.desc);
        $("#txtSCap").val(data.cap);
        $("#divSection").show();
    }
}

function data4Save(){
    var type=$("#txtType").val();
    var r={ action: "SaveWHLoc", opt: $("#txtOpt").val(), type: type, parent:  $("#txtParent").val() };
    if(type=="l"){
        r.code=$("#txtLCode").val();
        r.name=$("#txtLName").val();
        r.status=$("#drpLStatus").val();
        r.desc=$("#txtLDesc").val();
        r.addr=$("#txtLAddr").val();
        r.zipcode=$("#txtLZipCode").val();
        r.contact=$("#txtLContact").val();
        r.phone=$("#txtLPhone").val();
        r.fax=$("#txtLFax").val();
        r.comp = $("#drpOrg").val();
    }else if(type=="a"){
        r.parentType = $("#txtParentType").val();
        r.code=$("#txtACode").val();
        r.status=$("#drpAStatus").val();
        r.name=$("#txtAName").val();
        r.desc=$("#txtADesc").val();
        r.cap=$("#txtACap").val();
        r.hassec = $("#chkHasSec").attr("checked") ? "1" : "0";
        r.isqc = $("#chkIsQC").attr("checked") ? "1" : "0";
        r.isscrap = $("#chkIsScrap").attr("checked") ? "1" : "0";
    }else if(type=="s"){
        r.code=$("#txtSCode").val();
        r.status=$("#drpSStatus").val();
        r.cap=$("#txtSCap").val();
        r.desc=$("#txtSDesc").val() ; //$("#txtDShipment").attr("checked")?"1":"0";
    }
    return r;
}

function regionSelect(){}
regionSelect.prototype.exec=function(callback, settings){
    $.ajax({
        async: false,
        success: function(data) {
            if(data._error) alert(data._error_msg);
            else {
                var result=$(data.html);
                settings.resultid="#"+data.id;
                result.treeview({
                    onclick: function(elm){ selectItemClick(elm, settings); },
                    selected: false
                });
                result.find("input").bind("click", function(){
                    if(settings && settings.mode=="s")
                        $(settings.resultid).find("span.selected").removeClass("selected").prevAll("input").filter(":last").removeAttr("checked");
                    if($(this).attr("checked")) $(this).nextAll("span").filter(":first").addClass("selected");
                    else $(this).nextAll("span").filter(":first").removeClass("selected");
                });
                if(settings.values!=null && settings.values.constructor==Array && settings.values.length>0){
                    $.each(settings.values, function(){
                        if(this.d && this.d.constructor==Number && this.d>0) 
                            result.find("span[oi="+this.d+"][ot='d']").trigger("click")  //click事件将勾选checkbox、设置为选择状态
                                .parent().parent().parent().find(">span").showItem() //将城市的节点展开显示
                                .parent().parent().parent().find(">span").showItem(); //将城市的节点展开显示
                        else if(this.c && this.c.constructor==Number && this.c>0) 
                            result.find("span[oi="+this.c+"][ot='c']").trigger("click") //click事件将勾选checkbox、设置为选择状态
                                .parent().parent().parent().find(">span").showItem(); //将省份的节点展开显示
                        else if(this.p && this.p.constructor==Number && this.p>0) result.find("span[oi="+this.p+"][ot='p']").trigger("click");
                    });
                }
                if(this.callback!=null && typeof this.callback=="function")
                    this.callback(result, settings);
                if(callback!=null && typeof callback=="function")
                    callback(result, settings);
            }
        },
        error: function(data, msg, e) {
            alert(e + " " + msg + " " + data);
        },
        data: { action:"SelectRegion" },
        dataType: "json",
        timeout: 5000,
        type: "POST",
        url: "../ajax.ashx"
    });//$.ajax({
}

function selectItemClick(em, settings){
    var node=$(em);
    if(node.parent().parent().attr("virtual")=="1") return;
    if(node.hasClass("selected")) {
        node.removeClass("selected").prevAll("input").filter(":last").removeAttr("checked");
        return;
    }
    if(settings && settings.mode=="s")
        $(settings.resultid).find("span.selected").removeClass("selected").prevAll("input").filter(":last").removeAttr("checked");
    node.addClass("selected").prevAll("input").filter(":last").attr("checked", true);
}

window.region_close=function(settings){ //只允许单选
    function trim(s){
        if(s==undefined || s==null) return "";
        if(s.indexOf("(")<0) return s;
        return s.substr(0, s.indexOf("(")-1);
    }

    var result = { p: {id:"", desc:"", select: false}, c: {id:"", desc:"", select: false}, d: {id:"", desc:"", select: false}, desc: "" };
    var selected=$(settings.resultid).find("input[checked]");
    if(selected.length<=0) return result;
    
    selected=$(selected[0]).next();
    if(selected.attr("ot")=="d"){
        result.d.select=true;
        result.d.id=selected.attr("oi");
        result.d.desc=trim(selected.text());
        selected=selected.parent().parent().parent().find("span[oi="+selected.attr("pi")+"]");
    }
    if(selected.attr("ot")=="c"){
        result.c.select=true;
        result.c.id=selected.attr("oi");
        result.c.desc=trim(selected.text());
        selected=selected.parent().parent().parent().find("span[oi="+selected.attr("pi")+"]");
    }
    if(selected.attr("ot")=="p"){
        result.p.select=true;
        result.p.id=selected.attr("oi");
        result.p.desc=trim(selected.text());
    }
    result.desc=result.p.desc;
    if(result.c.select) result.desc=result.desc+" - "+result.c.desc;
    if(result.d.select) result.desc=result.desc+" - "+result.d.desc;
    return result;
}