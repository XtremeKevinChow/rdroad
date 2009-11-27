function note(name){
    if($.trim(name).length<=0) alert("Invalidate form name.");
    this.mfs_name=name;
}

note.prototype.mfs_name = undefined; //ajax action name
note.prototype.settings = undefined;
note.prototype.mfs_formId = undefined; //the id for the form that created for this popup

note.prototype.fnClose = function(){
    if(this.settings && typeof this.settings.on_close=="function")
        this.settings.on_close.call(this);
    $(this.mfs_formId).hide();
}

note.prototype.fnConfirm = function() {
    if(this.settings && typeof this.settings.on_select=="function")
        this.settings.on_select.call(this, $("#note_txt_area").val());
    this.fnClose();
}

note.prototype.fnPopup = function(settings){
    //validate and fix
    if(!settings) { alert("Invalidate settings for query" + this.mfs_name); return; }
    this.settings=settings;
    if(!settings.top || typeof settings.top!="number" || settings.top<=0) settings.top=50;
    if(!settings.left || typeof settings.left!="number" || settings.left<=0) settings.left=50;
    if(!settings.width || typeof settings.width!="number" || settings.width<=0) settings.width=400;
    if(!settings.height || typeof settings.height!="number" || settings.height<=0) settings.height=250;
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
            action: "note"
        },
        
        error: function(data, msg, e) { alert(e + " " + msg + " " + data); },
        success: function(data, status){
            var id="selector_"+thisRef.mfs_name;
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
                 contentDiv = contentDiv.bgIframe();
            document.body.appendChild(contentDiv[0]);
            theForm = $(thisRef.mfs_formId);
            theForm.css("top", settings.top).css("left", settings.left)
                .css("position","absolute")
                .find("#sel_cmdSysClose").bind("click", function() { thisRef.fnClose(); })
                .end().find("#sel_cmdClose").bind("click", function() { thisRef.fnClose(); })
                .end().find("#sel_cmdConfirm").bind("click", function() { thisRef.fnConfirm(); })
                .end().find(".commandArea").css("width", 2*65);
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
            .end().find(".userArea").css("width", settings.width-5)
            .end().find("#note_txt_area").css("height", settings.height-10).css("width", settings.width-10);
            
            theForm.show();
            theForm.find("#note_txt_area")[0].select();
        }
    });
}