function loadTree(settings){
    $("#loadingText").html("正在加载...");
    mm.mask( { } )
        .__popup( { pop_src: "#ajaxStatus", pop_target: "#divOrgTree", sysFrame: false });
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

function clearView(opt, parentId, type) {
    $("#txtOpt").val(opt);
    $("#txtId").val("-1");
    $("#txtParent").val(parentId);
    $("#txtType").val(type);
    $("#txtPCode").val(""); //Province
    $("#txtPName").val("");
    $("#txtPAlias").val("");
    $("#txtCCode").val(""); //City
    $("#txtCName").val("");
    $("#txtDName").val(""); //District
    $("#txtDZipCode").val("");
    $("#txtDShipment").removeAttr("checked");
    
    $("#divProvince").hide();
    $("#divCity").hide();
    $("#divDistrict").hide();
    if(type=="p") $("#divProvince").show();
    else if(type=="c") $("#divCity").show();
    else if(type=="d") $("#divDistrict").show();
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
            mm.__popup({ pop_src: "#ajaxStatus", sysFrame: false, pop_target: $(t), halign:"right-out", hmargin:5 });
            $.ajax({
                async: true,
                success: function(data) {
                    if(data._error) alert(data._error_msg);
                    else {
                        $(settings.resultid).treeview({remove:$(t).parent()})
                        clearView("", "","");
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
    if(orgId==null || orgId.length<=0 || isNaN(parseInt(orgId))) return;
    orgId=parseInt(orgId);
    var type=elm.attr("ot");
    if(type!="p" && type!="c" && type!="d") return;

    $("#loadingText").html("正在加载...");
    mm.__popup( { pop_src: "#ajaxStatus", pop_target: elm, halign: "right-out", hmargin:4, sysFrame: false });

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
    if(type!="p" && type!="c" && type!="d") return false;
    if(type=="p" && ($("#txtPCode").val().length<=0 || $("#txtPName").val().length<=0)){
        mm.msg({msg:"省份代码或名称必须填写", title:"资料不完整", timeout:2, width:180, height: 16, valign: "top-in", vmargin:100 });
        return false;
    }
    if(type=="c" && ($("#txtCCode").val().length<=0 || $("#txtCName").val().length<=0)){
        mm.msg({msg:"城市代码或名称必须填写", title:"资料不完整", timeout:2, width:180, height: 16, valign: "top-in", vmargin:100 });
        return false;
    }
    if(type=="d" && $("#txtDName").val().length<=0){
        mm.msg({msg:"区域代码或名称必须填写", title:"资料不完整", timeout:2, width:180, height: 16, valign: "top-in", vmargin:100 });
        return false;
    }
    return true;
}

function saveRegion(settings){
    if(!saveHandler()) return;
    settings.resultid="#regionTree";
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
            mm.msg( {msg:data.desc+" 保存成功", title:"操作成功", timeout: 0.6, width: 180, height: 16, valign: "top-in", vmargin:100 } );
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
}//function saveRegion

function orgLoaded(orgInfo, isUpdate, isInsert, settings){
    setView(orgInfo);
    if(isUpdate)
        $(settings.resultid).find('span[@oi='+orgInfo.id+']').text(orgInfo.desc);
    if(isInsert){
        var selectedItem=$(settings.resultid).find("span.selected");
        if(selectedItem.length<=0) { alert("Parent node not found"); return; }
        var tagName=selectedItem[0].parentNode.lastChild.tagName;
        if(tagName!="UL" && tagName!="SPAN") { alert("Unknown treeitem node"); return; }                
        var itemToAdd;
        if(tagName=="UL"){
            itemToAdd = $('<li><span ot="'+orgInfo.type+'" oi="'+orgInfo.id+'" pi="'+orgInfo.parent+'">'+orgInfo.desc+'</span></li>')
                .appendTo(selectedItem[0].parentNode.lastChild);
        }else{
            itemToAdd = $('<ul><li><span ot="'+orgInfo.type+'" oi="'+orgInfo.id+'" pi="'+orgInfo.parent+'">'+orgInfo.desc+'</span></li></ul>')
                .appendTo(selectedItem[0].parentNode);
        }                
        $(settings.resultid).treeview({add:itemToAdd});
        
        itemToAdd.find('span').contextMenu("treeMenu", {
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
                "cmdInsertP": function(t) { clearView("create", "","p"); },  
                "cmdInsertC": function(t) { clearView("create", $(t).attr("oi"),"c"); },  
                "cmdDeleteP": function(t) { cmdDelete(t, settings);},
                "cmdInsertD": function(t) { clearView("create", $(t).attr("oi"),"d"); },  
                "cmdDeleteC": function(t) { cmdDelete(t, settings);},
                "cmdDeleteD": function(t) { cmdDelete(t, settings);}
            };
}

function showMenu(e, menu){
{
    if($(e.target).attr("ot")=="r") {
        $("#cmdInsertC", menu).remove();
        $("#cmdDeleteP", menu).remove();
        $("#cmdInsertD", menu).remove();
        $("#cmdDeleteC", menu).remove();
        $("#cmdDeleteD", menu).remove();
    }else if($(e.target).attr("ot")=="p"){
        $("#cmdInsertP", menu).remove();
        $("#cmdInsertD", menu).remove();
        $("#cmdDeleteC", menu).remove();
        $("#cmdDeleteD", menu).remove();
    }else if($(e.target).attr("ot")=="c"){
        $("#cmdInsertP", menu).remove();
        $("#cmdInsertC", menu).remove();
        $("#cmdDeleteP", menu).remove();
        $("#cmdDeleteD", menu).remove();
    }else if($(e.target).attr("ot")=="d"){
        $("#cmdInsertP", menu).remove();
        $("#cmdInsertC", menu).remove();
        $("#cmdDeleteP", menu).remove();
        $("#cmdInsertD", menu).remove();
        $("#cmdDeleteC", menu).remove();
    }else{
        return undefined;
    }
    return menu;
    }
}

function setView(data){
    $("#txtOpt").val("update");
    $("#txtId").val(data.id);
    $("#txtParent").val(data.parent);
    $("#txtType").val(data.type);
    $("#divProvince").hide();
    $("#divCity").hide();
    $("#divDistrict").hide();
    if(data.type=="p"){
        $("#txtPCode").val(data.code);
        $("#txtPName").val(data.name);
        $("#txtPAlias").val(data.alias);
        $("#divProvince").show();
    } else if(data.type=="c"){
        $("#txtCCode").val(data.code);
        $("#txtCName").val(data.name);
        $("#divCity").show();
    }else if(data.type=="d"){
        $("#txtDName").val(data.name);
        $("#txtDZipCode").val(data.zip);
        $("#txtDShipment").attr("checked", data.ship);
        $("#divDistrict").show();
    }
}

function data4Save(){
    var type=$("#txtType").val();
    var r={ action: "SaveRegion", opt: $("#txtOpt").val(), type: type, parent:  $("#txtParent").val(), id: $("#txtId").val() };
    if(type=="p"){
        r.code=$("#txtPCode").val();
        r.name=$("#txtPName").val();
        r.alias=$("#txtPAlias").val();
    }else if(type=="c"){
        r.code=$("#txtCCode").val();
        r.name=$("#txtCName").val();
    }else if(type=="d"){
        r.name=$("#txtDName").val();
        r.zip=$("#txtDZipCode").val();
        r.ship=$("#txtDShipment").attr("checked")?"1":"0";
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