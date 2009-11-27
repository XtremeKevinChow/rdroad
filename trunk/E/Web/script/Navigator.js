function loadTree(settings){
    $("#loadingText").html("正在加载...");
    mm.mask( { } )
        .__popup( { pop_src: "#ajaxStatus", pop_target: "#divNavTree", sysFrame: false });
    $.ajax({
        async: true,
        success: function(data) {
            if(data._error) alert(data._error_msg);
            else {
                settings.resultid="#"+data.id;
                $("#divNavTree").html(data.html);
                setupTree(settings);
                if(settings.type=="mgmt")
                    clearView("", "-1");
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
        });
        
        $(settings.resultid).find('span').contextMenu("treeMenu", {
            onContextMenu: function(e) { return contextMenu(e, settings); },
            onShowMenu: function(e, menu){
                if($(e.target).attr("root")=="1") $("#cmdDelete", menu).remove();
                return menu;
            },
            bindings: { "cmdInsert": function(t) { clearView("create", $(t).attr("oid"), $(t)); },  "cmdDelete": function(t) { cmdDelete(t, settings);} }
        });
    }
     else if(settings.type == "viewpermission")
    {
         $(settings.resultid).treeview({
            onclick: function(em){ selectItemClick(em, settings); },
            selected: false
        });
    }
    else if(settings.type=="select"){
        $(settings.resultid).treeview({
            onclick: function(em){ selectItemClick(em, settings); },
            selected: false
        });
    }
}

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
        msg:"您确实要删除"+$(t).text()+"?", title:"删除确认", width:220, pop_target: t, halign:"right-out", hmargin:10,
        callback:function(){
            $("#loadingText").html("正在删除...");
            mm.__popup({ pop_src: "#ajaxStatus", sysFrame: false, pop_target: $(t), halign:"right-out", hmargin:5 });
            $.ajax({
                async: true,
                success: function(data) {
                    if(data._error) alert(data._error_msg);
                    else {
                        $(settings.resultid).treeview({remove:$(t).parent()})
                        clearView("", "-1");
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
    var orgId=elm.attr("oid");
    if(orgId==null || orgId.length<=0 || isNaN(parseInt(orgId))) return;
    orgId=parseInt(orgId);

    $("#loadingText").html("正在加载...");
    mm.mask( { target: "#divNavTree" } )
        .__popup( { pop_src: "#ajaxStatus", pop_target: elm, halign: "right-out", hmargin:4, sysFrame: false });

    $.ajax({
        async: true,
        success: function(data) {
            if(data._error) alert(data._error_msg);
            else afterSave(data, false, false, settings);
            mm.__close("#ajaxStatus").maskClose();
        }, //success: function(retValue)
        error: function(request, msg, e){
            alert(e + " " + msg + " " + request);
            mm.__close("#ajaxStatus").maskClose();
        },//error: function(httpRequest, msg, e
        data: settings.data4Load(orgId),
        dataType: "json",
        timeout: 5000,
        type: "POST",
        url: "../ajax.ashx"
    });//$.ajax({
}//function treeItemClick(elm){

function saveOp(settings){
    if(!saveHandler()) return;
    settings.resultid="#navTree";
    $("#loadingText").html("正在保存...");
    mm.mask({}).__popup( { pop_src: "#ajaxStatus", sysFrame: false, valign: "top-in", vmargin:100 });
    $.ajax({
        async: true,
        success: function(data) {
            var isUpdate=true, isInsert=false;
            if($("#txtOpt").val()=="create") { isUpdate=false; isInsert=true; }
            if(data._error) alert(data._error_msg);
            else afterSave(data, isUpdate, isInsert, settings); 
            mm.__close("#ajaxStatus");
            mm.msg( {msg:data.name+" 保存成功", title:"操作成功", timeout: 0.6, width: 180, valign: "top-in", vmargin:100 } );
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
}//function saveOrg

function afterSave(data, isUpdate, isInsert, settings){
    setView(data);
    if(isUpdate)
        $(settings.resultid).find('span[@oid='+data.id+']').text(data.name);
    if(isInsert){
        var selectedItem=$(settings.resultid).find("span.selected");
        if(selectedItem.length<=0) { alert("Parent node not found"); return; }
        var tagName=selectedItem[0].parentNode.lastChild.tagName;
        if(tagName!="UL" && tagName!="SPAN") { alert("Unknown treeitem node"); return; }                
        var itemToAdd;
        if(tagName=="UL"){
            itemToAdd = $('<li><span oid="'+data.id+'" seq="' + data.seq +'" >'+data.name+'</span></li>')
                .appendTo(selectedItem[0].parentNode.lastChild);
        }else{
            itemToAdd = $('<ul><li><span oid="'+data.id+'" seq="' + data.seq +'">'+data.name+'</span></li></ul>')
                .appendTo(selectedItem[0].parentNode);
        }                
        $(settings.resultid).treeview({add:itemToAdd});
        
        itemToAdd.find('span').contextMenu("treeMenu", {
            onContextMenu: function(e) { return contextMenu(e, settings); },
            onShowMenu: function(e, menu){
                if($(e.target).attr("root")=="1") $("#cmdDelete", menu).remove();
                return menu;
            },
            bindings: { "cmdInsert": function(t) { clearView("create", $(t).attr("oid"),$(t)); },  "cmdDelete": function(t) { cmdDelete(t, settings);} }
        });
        
        selectedItem.removeClass("selected");
        itemToAdd.find("span").addClass("selected");
    }//if(isInsert)
} //function afterSave(data)

//Select Navigator Tree
function navSelect(){}
navSelect.prototype.exec=function(callback, settings){
    //mm.mask({});
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
                if(settings.values!=null && (typeof(settings.values)=="string" || settings.values.constructor==Array)){
                    var stringValues="";
                    if(typeof settings.values=="string") stringValues = ";" + settings.values + ";";
                    else if(stringValues.constructor==Array) stringValues=";"+stringValues.join(";")+";";
                    result.find("span").each(function(){
                        if(stringValues.indexOf(";"+$(this).attr("oid")+";")>=0) {
                            $(this).trigger("click");
                        }
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
        data: { type: settings.query, action:"SelectNavigator" },
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

window.nav_close=window.partner_close=function(settings){
    var selected=$(settings.resultid).find("input[checked]");
    if(selected.length<=0) return { list: [], id:"", desc:"" };
    var r=[], id=[], desc=[];
    selected.each(function(){
        id.push($(this).nextAll("span").filter(":first").attr("oid"));
        desc.push($(this).nextAll("span").filter(":first").text());    
        r.push({
            id: id[id.length-1],
            desc: desc[desc.length-1] 
        });
    });
    return { list: r, id: id.join(";"), desc: desc.join("; ") };
}

