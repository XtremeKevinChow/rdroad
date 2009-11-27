function query(name, index){
    if($.trim(name).length<=0) alert("Invalidate query name.");
    if(index!=null && index!=undefined && typeof index=="number" && index>=0){
        this.__index = index; //如果有指定index参数并且为合法的索引值，则将创建多实例，否则只使用单实例模式
        this.__multiInstance = true;
    }
    this.mfs_name=name;
}

query.prototype.mfs_name = undefined; //ajax action name
query.prototype.settings = undefined;
query.prototype.mfs_formId = undefined; //the id for the form that created for this popup
query.prototype.mfs_index = undefined; //trace current page index
query.prototype.params = undefined; //parameters for ajax call
query.prototype.mfs_count = undefined; //total page count

query.prototype.fnClose = function(){
    if(this.settings && typeof this.settings.on_close=="function")
        this.settings.on_close.call(this);
    $(this.mfs_formId).hide();
}

query.prototype.fnConfirm = function() {
    var r;
    try{
        if(eval("typeof (false || " + this.mfs_name + "_on_select)")=="function")
            r = eval("false || " + this.mfs_name + "_on_select").call(this, this.params);
    }catch(e) {}
    if(r==null || r==undefined) r=[];
    if(this.settings && typeof this.settings.on_select=="function")
        this.settings.on_select.call(this, r);
    this.fnClose();
}

query.prototype.fnSetPager = function(){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    theForm.find("#selectorPager").text(this.mfs_index + "/" + this.mfs_count);
    if(this.mfs_count>1 && this.mfs_index>1){ //the prev & first command
        if(!this.ms_prev){
            theForm.find("#sel_cmdFirst").removeAttr("disabled").attr("href", "#a").bind("click", function(){ thisRef.fnFist(); })
            .end().find("#sel_cmdPrev").removeAttr("disabled").attr("href", "#a").bind("click", function(){ thisRef.fnPrev(); });
            this.ms_prev=true;
        }
    }else{
        if(this.ms_prev){
            theForm.find("#sel_cmdFirst").removeAttr("href").attr("disabled", true).unbind("click");
            theForm.find("#sel_cmdPrev").removeAttr("href").attr("disabled", true).unbind("click");
            this.ms_prev=false;
        }
    }
    if(this.mfs_count>1 && this.mfs_index<this.mfs_count){ //the next & last command
        if(!this.ms_next){
            theForm.find("#sel_cmdLast").removeAttr("disabled").attr("href", "#a").bind("click", function(){ thisRef.fnLast(); })
                .end().find("#sel_cmdNext").removeAttr("disabled").attr("href", "#a").bind("click", function(){ thisRef.fnNext(); });
            this.ms_next=true;
            return; //firefox bugs
        }
    }else{
        if(this.ms_next){
            theForm.find("#sel_cmdLast").removeAttr("href").attr("disabled", true).unbind("click");
            theForm.find("#sel_cmdNext").removeAttr("href").attr("disabled", true).unbind("click");
            this.ms_next=false;
        }
    }
};

//type: 1 query command, 2 first command, 3 prev command, 4 next command, 5 last command
query.prototype.execQuery = function(index, type){
    //get the query parameters
    var param = this.params || {};
    try{
        if(this.settings.tooltip!=true && eval("typeof(false || "+this.mfs_name+"_on_query)")=="function"){
            eval("false || "+this.mfs_name+"_on_query").call(this, this.params);
        }
    }
    catch(e) { } 
    for(var p in this.settings.data)
        if(!param.hasOwnProperty(p))
            param[p] = this.settings.data[p];
    param.type="1002";
    param.action=this.mfs_name;
    param.pi = index;
    param.qt = type;
    if(!param.selected) { param.selected = []; }

    this.params=param;    
    this.mfs_index=index;
    var thisRef=this;
    
    //execute the query page and load result html
    $.ajax({
        async: true,
        dataType: "html",
        timeout: 10000,
        type: "POST",
        url: "../ajax.ashx",
        data: param,
        error: function(data, msg, e) { alert(e + " " + msg + " " + data); },
        
        success: function(data, status){
            var theForm = $(thisRef.mfs_formId);
            theForm.find(".userArea").html(data);
            try{
                if(thisRef.settings.tooltip!=true && eval("typeof(false || "+thisRef.mfs_name+"_on_load)")=="function"){
                    var cnt = eval("false || "+thisRef.mfs_name+"_on_load").call(thisRef, param);
                    cnt = parseInt(cnt);
                    if(!isNaN(cnt) && cnt>0) thisRef.mfs_count=cnt;
                }
            }catch(e) { alert("function "+thisRef.mfs_name+"_on_load not found"); }
            //command for query button
            if(thisRef.settings.tooltip!=true && theForm.find("#sel_cmdQuery").length>0) 
                theForm.find("#sel_cmdQuery").bind("click", function() { thisRef.fnQuery(); });
            //command for next, prev page button
            if(thisRef.settings.tooltip!=true) thisRef.fnSetPager();
        }//success
    });//$.ajax({
}

query.prototype.fnQuery = function() {
    this.execQuery(1, 1);
}
query.prototype.fnFist = function(){
    this.execQuery(1, 2)
}
query.prototype.fnPrev = function(){
    this.execQuery(this.mfs_index-1, 3)
}
query.prototype.fnNext = function(){
    this.execQuery(this.mfs_index+1, 4)
}
query.prototype.fnLast = function(){
    this.execQuery(this.mfs_count, 5)
}

query.prototype.fnPopup = function(settings){
    //validate and fix
    if(!settings) { alert("Invalidate settings for query" + this.mfs_name); return; }
    this.settings=settings;
    if(!settings.top || typeof settings.top!="number" || settings.top<=0) settings.top=50;
    if(!settings.left || typeof settings.left!="number" || settings.left<=0) settings.left=50;
    if(!settings.width || typeof settings.width!="number" || settings.width<=0) settings.width=400;
    if(!settings.height || typeof settings.height!="number" || settings.height<=0) settings.height=250;
    //remove the empty items
    if(this.settings.data && this.settings.data.selected && this.settings.data.selected.constructor==Array){
        this.settings.data.selected=$.grep(this.settings.data.selected, function(n, i){
            var isAllEmpty, propVal;
            isAllEmpty = true;
            for(var prop in n){
                propVal = n[prop];
                if(propVal!=undefined && propVal!=null && $.trim(propVal).length>0 && propVal!="0" && propVal!="-1"){
                    isAllEmpty = false;
                    break;
                }//if(propVal!=undefined && propVal!=null
            }//for(var prop in this.settings.data.selected[i])
            return !isAllEmpty;
        });//this.settings.data.selected
    }
    this.mfs_index=1; //current page index
    this.mfs_count=0; //total page count
    this.ms_prev=false; //is the prev & first command enabled?
    this.ms_next=false; //is the next & last command enabled?
    var thisRef=this; //a reference to this object
    
    //load the selector dialog
    $.ajax({
        async: true,
        dataType: "html",
        timeout: 5000,
        type: "POST",
        url: "../ajax.ashx",
        data: {
            type: "1002",
            action: "selector"
        },
        
        error: function(data, msg, e) { alert(e + " " + msg + " " + data); },
        success: function(data, status){
            var id="selector_"+thisRef.mfs_name;
            if(thisRef.__multiInstance){ id = id + thisRef.__index; }
            thisRef.mfs_formId="#"+id;
            var theForm;
            if($(thisRef.mfs_formId).length>0){ //clear to prevent memory leak
                theForm = $(thisRef.mfs_formId);
                if(theForm.length>0) {
                    if(theForm.DraggableDestroy && typeof theForm.DraggableDestroy=="function") 
                        theForm.DraggableDestroy();
                    theForm.remove();
                }
             }
             
             var contentDiv = $('<div id="'+id+'" class="magicSysForm"></div>').html(data);
             //BgFrame
             if ($.fn.bgIframe != undefined) 
            {
                 contentDiv = contentDiv.bgIframe();
            };
            document.body.appendChild(contentDiv[0]);
            theForm = $(thisRef.mfs_formId);
            theForm.css("top", settings.top).css("left", settings.left)
                .css("position","absolute")
                .find("#sel_cmdSysClose").bind("click", function() { thisRef.fnClose(); })
                .end().find("#sel_cmdClose").bind("click", function() { thisRef.fnClose(); })
            if(thisRef.settings.tooltip!=true) 
                theForm.find("#sel_cmdConfirm").bind("click", function() { thisRef.fnConfirm(); })
                    .end().find(".sysFormPager").css("width", settings.width+5)
                    .end().find(".commandArea").css("width", 2*65);
            else 
                theForm.find(".sysFormPager").hide()
                    .end().find("#sel_cmdConfirm").hide()
                    .end().find(".commandArea").css("width", 65);
            if(theForm.Draggable && typeof theForm.Draggable=="function") 
                theForm.Draggable({ handle: ".sysFormDragHandle" });
            if(settings.title){
                theForm.find("#magicTitle").text(settings.title);
            }

            theForm.find(".sysFormHead").css("width", settings.width+10) //header
            .end().find(".sysFormHeadMid").css("width", settings.width-3)
            .end().find(".sysFormBottom").css("width", settings.width+10) //bottom
            .end().find(".sysFormBottomMid").css("width", settings.width)
            .end().find(".sysFormDragHandle").css("width", settings.width-40)
            .end().find(".sysFormContent").css("width", settings.width).css("height", settings.height)
            .end().find("#userAreaContainer").css("width", settings.width-3).css("height", settings.height)
            .end().find(".userArea").css("width", settings.width-5);
            
            theForm.end().show();
            thisRef.fnQuery();
        }
    });
}