//Richie, Junary 27, 2008
//Copyright @2008, ThoughtSoft Co,. Ltd.
//See the dependencies before each function definition, make sure the associated files are imported in the page
//This component should be improved and as a jQuery plugin later

//common functions
function exceedMaxlen(obj, maxlen){
    if(obj==null || obj.value==null) return true;
    return obj.value.length<=maxlen;
}

//magic control
(function(){
    //has been defined, just return
    if(window.magic && window.magic._identifier=="magic.framework.js") return window.magic;
    //save the conflict object
    
    if(window.magic) var _magic=window.magic;
    
    var magic=window.magic=function(){
        return new magic.prototype.init();
    };
    magic.prototype.__container=[];
    magic.prototype.init=function() { return this; };
    magic.prototype.init.prototype=magic.prototype;    
    magic.prototype._identifier="magic.framework.js";
    var mm=window.mm=new magic();
    
    var __masker=window.__masker=null;
    var __ismasking=window.__ismasking=false;
    
    function __getMasker(){
        if(__masker==null){
            __masker=window.__masker=$(document.createElement("div"));
            __masker.css("position", "absolute").css("z-index", "10000").css("background-color", "#fff").css("opacity", 0.8); //default effects
        }
        return __masker;
    };
    function __borderWidth(target, direction, docAsTarget){
        if(docAsTarget || (direction!="left" && direction!="right" && direction!="top" && direction!="bottom")) return 0;
        
        var width=$.css($(target)[0], "border-"+direction+"-width");
        if(width!=null && !isNaN(parseInt(width))) return parseInt(width);
        return 0;
    };
    function __offset(target, docAsTarget){
        if(docAsTarget)
            return {top:$(document).scrollTop(), left:$(document).scrollLeft()};
        else
            return target.offset( );
    };
    function __width(target, docAsTarget){
        return docAsTarget ? $(window).width() : target.width();
    };
    function __height(target, docAsTarget){
        return docAsTarget ? $(window).height() : target.height();
    };
    
    //dependencies: jquery.js, jquery.dimensions.min.js
    //target / mask_target: the object that will be masked
    //bg-color / mask_bgcolor:
    //z-index / mask_zindex:
    //opacity / mask_opacity:
    magic.prototype.mask=function(settings) {
        if(settings==null) settings={};
        if(!settings.name) settings.name="_defaultMask";
        if(__ismasking) return this;
        var docat=false;
        var target;
        if(settings==null || (!settings.mask_target && !settings.target)){
            docat=true;
            target=$(document.body);
        }else{
            target=$(settings.mask_target || settings.target);
        }
        var masker=__getMasker();
        
        var offset= __offset(target, docat);
        offset.left += __borderWidth(target, "left", docat);
        offset.top += __borderWidth(target, "top", docat);
        
        masker.css("left", offset.left).css("top", offset.top)
            .css("width", __width(target, docat))
            .css("height", __height(target, docat));
        if(settings.mask_bgcolor || settings.bgcolor)
            masker.css("background-color", settings.mask_bgcolor || settings.bgcolor);
        if(settings.mask_zindex || settings.zindex)
            masker.css("z-index", settings.mask_zindex || settings.zindex);
        if(settings.mask_opacity || settings.opacity)
            masker.css("opacity", settings.mask_opacity || settings.opacity);
        
        document.body.appendChild(__masker[0]);
        masker.show();
        __ismasking=window.__ismasking=true;
        
        return this;
    };
    
    magic.prototype.maskClose=function(){
        if(__ismasking && __masker) {
            __masker.hide();
            __ismasking=window.__ismasking=false;
        }
        
        return this;
    };
    
    // dependencies: jquery.js, jquery.dimensions.min.js
    // ***************************
    // *******parameters*******
    // src / pop_src: the source element used to display in popup window
    //      type: jQuery object, DOMElement, or string like "#elementID"
    // url / pop_url: the url of the document whose content used to display in popup window
    //      type: string
    // msg: the message to be shown 
    //      type: string, html is allowed
    //====CAUSION====, src, url and msg are three approaches used to get source content that display in popup window, so only one of them is picked up for each invoke
    // sysframe: whether to use the default form wrapper
    //      type: boolean
    // buttons: the buttons shown in the bottom of popup window, this is only useful when sysframe is true
    //      type: json object, { 
    //              img: the src of button img
    //              text: the button text
    //              callback: the callback function for this button
    //          }
    // id: id for the popup window
    //      type: string
    // target / pop_target: the target container in which the popup window displays, if not provided then use the document as default
    //      type: jQuery object, DOMElement, or string like "#elementID"
    // valign: vertical align, it can be one of "top", "center", "bottom", if not provided then use "center" as default
    // vmargin: vertical margin
    //      type: integer
    // halign: horizonal align, it can be one of "left", "center", "right", if not provided then use "center" as default
    // hmargin: horizonal margin
    //      type: integer
    // width, height: the width and height of the popup window
    //      type: integer, the value must be great than 100
    // isMagicQuery: 
    //      type: boolean
    // query: the name of the query, used to build the function names, for example if the query is "user", the function names are "user_init()", "user_close()"
    //             this parameter is useful only when the url is provided
    //      type: string
    // data:
    magic.prototype.__popup=function(settings){
        if(settings==null) return this;
        if(settings.mask) this.mask(settings); //it is not re-enterable
        
        if((!settings.width || typeof(settings.width)!="number" || settings.width<=0) && settings.pop_src!="#ajaxStatus") settings.width=290;
        if((!settings.height || typeof(settings.height)!="number" || settings.height<=0) && settings.pop_src!="#ajaxStatus") settings.height=200;
        
        //get the source element
        var srcFound=false;
        if(settings.src || settings.pop_src) { settings.src=settings.pop_src ? $(settings.pop_src) : $(settings.src); srcFound=true; } //source element is privoded by client
        if(settings.url || settings.pop_url) {
            //use ajax to load the content html, is it better that load the content after the form displays?
            var hasErrorOnAjax=false;
            $.ajax({
                async: false,
                success: function(request, status){
                    settings.src = $(document.createElement("div")).html(request);
                    srcFound=true;
                },
                error: function(request, status, e){ //exception handling
                    alert(status + " " + request.responseText);
                    hasErrorOnAjax=true;
                },
                dataType: "html",
                timeout: 8000,
                type: "GET",
                url: settings.url || settings.pop_url
            });
            if(hasErrorOnAjax) return this;
        }else if(settings.getter && settings.getter.exec!=null && typeof(settings.getter.exec)=="function"){
            settings.getter.exec(function(result){
                settings.src=$(document.createElement("div"))
                    .css("width", settings.width-10).css("margin-left","auto").css("margin-right","auto")
                    .append(result);
                if(settings.height) settings.src.css("overflow", "auto").css("height", settings.height-10)
                srcFound=true;
                return settings.src;
            }, settings);
        }
        
        var useSysFrame = true ;
        if(settings.sysframe==false || settings.sysFrame==false) useSysFrame=false;
        if(!srcFound && !useSysFrame) return this; //no content used to popup found, just return
        
        //initialize the SysForm
        if(useSysFrame){
            var id=settings.popid || settings.popId || settings.id || "magicJsFrame" + (new Date()).getTime(); //try to get id from the arguments
            var sysFrame=document.getElementById(id);
            if(sysFrame) {
                sysFrame=$(sysFrame);
                //clear the previous content
                sysFrame.find("#magicTitle").empty()
                    .end().find("sysFormDragHandle").DraggableDestroy()
                    .end().find("div.sysFormContent").empty();
            }
            else{ //not found, just create a new instance
                sysFrame=$(document.createElement("div")) 
                    .attr("id", id).addClass("magicSysForm")
                    .html('\
<div class="sysFormHead">\
    <div class="sysFormHeadLeft"></div>\
    <div class="sysFormHeadMid">\
        <h6 id="magicTitle" class="sysFormDragHandle"></h6>\
        <span class="sysFormClose" id="sel_cmdSysClose"><a href="#a">关闭</a></span>\
    </div>\
    <div class="sysFormHeadRight"></div>\
</div>\
<div class="sysFormContent"></div>\
<div class="sysFormBottom">\
    <div class="sysFormBottomLeft"></div>\
    <div class="sysFormBottomMid"></div>\
    <div class="sysFormBottomRight"></div>\
</div>\
')
                    .Draggable({ handle: ".sysFormDragHandle" });
                sysFrame.find("span.sysFormClose").bind("click", function() { mm.__close(sysFrame).maskClose(); } );
                 if ($.fn.bgIframe != undefined) 
                {
	                 sysFrame = sysFrame.bgIframe();
	            };
                document.body.appendChild(sysFrame[0]); //append the message window to document
            }
            //set content, current css and etc. to SysForm
            //content and title
            if(srcFound) sysFrame.find("div.sysFormContent").append(settings.src);
            else if(settings.msg) sysFrame.find("div.sysFormContent").html(settings.msg);
            sysFrame.find("#magicTitle").text(settings.title || "System Message");
            sysFrame.find(".sysFormHead").css("width", settings.width+10)
                .end().find(".sysFormHeadMid").css("width", settings.width-3)
                .end().find(".sysFormBottom").css("width", settings.width+10)
                .end().find(".sysFormBottomMid").css("width", settings.width)
                .end().find(".sysFormDragHandle").css("width", settings.width-40) 
                .end().find(".sysFormContent").css("width", settings.width).css("height", settings.height);
             //buttons
            if(settings.buttons && settings.buttons.length>0){
                var buttonHtml='<div style="width:'+(56*settings.buttons.length)+'px;" class="commandArea">';
                for(var i=0; i<settings.buttons.length; i++){
                    var command=settings.buttons[i];
                    buttonHtml=buttonHtml+'<a href="#a" id="cmd'+i+'"><img src="'+command.img+'" /><span>'+command.text+'</span></a>';
                }
                buttonHtml=buttonHtml+'</div>';
                sysFrame.find("div.sysFormBottomMid").html(buttonHtml);
                //bind callbacks to buttons
                for(var i=0; i<settings.buttons.length; i++){
                    var command=settings.buttons[i];
                    if(command.callback && (typeof command.callback=="function"))
                        sysFrame.find("#cmd"+i).bind("click", command.callback);
                }
            }else{
                sysFrame.find("div.sysFormBottomMid").html("&nbsp;");
            }
            
            settings.src=sysFrame;
            srcFound=true;
        }//if(useSysFrame)
        
        if(settings.timeout) //automatically fade out the message window when the timeout is provided
            window.setTimeout(function(){
                settings.src.fadeOut(500, function() { mm.maskClose(); } );
            }, settings.timeout * 1000);
        
        //all the following code handle the position and offset of the pop window
        var docat=false;
        var target;
        if(settings==null || (!settings.target && !settings.pop_target)){
            docat=true;
            target=$(document.body);
        }else{
            target=settings.pop_target ? $(settings.pop_target) : $(settings.target);
        }

        var offset = __offset(target, docat);
        switch(settings.valign){
            case "top-in": 
                offset.top += __borderWidth(target, "top", docat) + (settings.vmargin>0?settings.vmargin:0);
                settings.src.css("top", offset.top);
                break;
            case "top-out": 
                offset.top -= __borderWidth(settings.src, "top", docat) + __borderWidth(settings.src, "bottom", docat);
                offset.top -= (settings.vmargin>0?settings.vmargin:0);
                offset.top -= (settings.height>0 ? settings.height : settings.src.height());
                settings.src.css("top", offset.top);
                break;
            case "bottom-in": 
                offset.top += __borderWidth(target, "top", docat) + __height(target, docat);
                offset.top -= __borderWidth(settings.src, "top", docat)+__borderWidth(settings.src, "bottom", docat);
                offset.top -= (settings.height>0 ? settings.height : settings.src.height());
                offset.top -= (settings.vmargin>0?settings.vmargin:0);
                settings.src.css("top", offset.top);
                break;
            case "bottom-out":
                offset.top += __borderWidth(target, "top", docat) + __borderWidth(target, "bottom", docat);
                offset.top += __height(target, docat);
                offset.top += (settings.vmargin>0?settings.vmargin:0);
                settings.src.css("top", offset.top);
                break;
            default:  //center
                offset.top += __borderWidth(target, "top", docat);
                offset.top += (__height(target, docat)-(settings.height>0 ? settings.height : settings.src.height()))/2;
                settings.src.css("top", offset.top);
                break;
        }//switch(settings.valign)
        
        switch(settings.halign){
            case "left-out": 
                offset.left -= (settings.width>0 ? settings.width : settings.src.width());
                offset.left -= (settings.hmargin>0 ? settings.hmargin : 0);
                offset.left -= (__borderWidth(settings.src, "left", docat) + __borderWidth(settings.src, "right", docat));
                settings.src.css("left", offset.left);
                break;
            case "left-in": 
                offset.left += __borderWidth(target, "left", docat);
                offset.left += (settings.hmargin>0 ? settings.hmargin : 0);
                settings.src.css("left", offset.left);
                break;
            case "right-out": 
                offset.left += __width(target, docat)+__borderWidth(target, "right", docat)+__borderWidth(target, "left", docat);
                offset.left += (settings.hmargin>0 ? settings.hmargin : 0);
                settings.src.css("left", offset.left);
                break;
            case "right-in": 
                offset.left += __width(target, docat)+__borderWidth(target, "left", docat);
                offset.left -= (settings.width>0 ? settings.width : settings.src.width());
                offset.left -= __borderWidth(settings.src, "left", docat)+__borderWidth(settings.src, "right", docat);
                offset.left -= (settings.hmargin>0 ? settings.hmargin : 0);
                settings.src.css("left", offset.left);
                break;
            default:  //center
                offset.left += __borderWidth(target, "left", docat);
                offset.left += (__width(target, docat)-(settings.width>0 ? settings.width : settings.src.width() ) )/2;
                settings.src.css("left", offset.left);
                break;
        }//switch(settings.halign)
        
        settings.src.show();
        
        //try to execute the init function defined in the loaded document
        var execInit = settings.isMagicQuery && (settings.query!=null && typeof settings.query=="string" && settings.query.length>0);
        if(execInit){
            if(eval("typeof " + settings.query+"_init")=="function")
                eval(settings.query+"_init")(settings.data==null ? {} : settings.data, settings);
        }
        return this;
    }; //magic.prototype.__popup=function(settings)
    
    magic.prototype.__close=function(source){
        $(source).hide();
        return this;
    };//magic.prototype.__close=function(source)
    
    //dependencies: jquery.js, jquery.dimensions.min.js, interface.js
    //must include popups.css
    //msg: the message that to display
    //title: the message title
    //timeout: in seconds, after the timeout the message window will fade out automatically
    //headbg, bottombg, rightbg: the background image for the message window, this is useful when the page is not in the first level subdirectory of the web site
    //other parameters see the __popup() and mask().
    magic.prototype.msg=function(settings){
        if(arguments.length<=0 && !settings.msg) return this;
        if(!settings.msg) {
            //the following call is available: 
            //1. mm.popmsg("your message"); 
            //2. mm.popmsg( { msg: "your message" } ); 
            settings={ msg: arguments[0] }; //this line transforms the calling style 1. to 2.
        }
        settings.popId="magicJsFrameMessage";
        if(!settings.height || typeof(settings.height)!="number" || settings.height<=0)
            settings.height = 60;
        
        return this.__popup(settings);
    };//magic.prototype.msg=function(settings)
    
    magic.prototype.confirm=function(settings){
        if(arguments.length<=0 && !settings.msg) return this;        
        if(!settings.msg) {
            //the following call is available: 
            //1. mm.popmsg("your message"); 
            //2. mm.popmsg( { msg: "your message" } ); 
            settings={ msg: arguments[0] }; //this line transforms the calling style 1. to 2.
        }
        settings.popId="magicJsFrameConfirm";
        if(!settings.height || typeof(settings.height)!="number" || settings.height<=0)
            settings.height = 60;
        if(settings.buttons==null || settings.buttons.length<=0) { //build the default buttons
            settings.buttons=[
                {  img: "../images/b_confirm.gif", text: "确认", 
                    callback: function() { 
                        mm.__close("#"+settings.popId);
                        if(settings.callback!=null && typeof settings.callback=="function") settings.callback(settings); 
                     } 
                 },
                { img: "../images/b_delete.gif", text: "取消", callback: function(){ mm.__close("#"+settings.popId).maskClose(); } }
            ];
        }else{ //wrap the button callbacks
            for(var i=0; i<settings.buttons.length; i++){
                settings.buttons[i].callback=function(){
                    mm.__close("#"+settings.popId);
                    if(settings.buttons[i].callback!=null && typeof settings.buttons[i].callback=="function")
                        settings.buttons[i].callback(settings);
                }
            }
        }//if(!settings.buttons || settings.buttons.length<=0)
        if(!settings.title) settings.title="操作确认";
        
        return this.__popup(settings);
    };//magic.prototype.confirm=function(settings)
    
    //url: 
    //getter:
    //id: 
    //buttons:
    magic.prototype.query=function(settings){
        if(settings==null || settings.id==null || (settings.url==null && settings.getter==null)) {
            alert('Insufficient arguments in query, The required parameter { id: "", url: "" } is missing');
            return this;
        }
        settings.isMagicQuery=true;
        settings.query=settings.id;
        settings.id="magicJsFrame" + settings.id;
        if(!settings.valign) settings.valign="top-in";
        if(!settings.vmargin) settings.vmargin=40;
        //build the buttons and callbacks
        var data;
        if(settings.buttons==null || settings.buttons.length<=0) { //build the default buttons
            settings.buttons=[
                {  img: "../images/b_confirm.gif", text: "确认", 
                    callback: function() { 
                        mm.__close("#"+settings.id);
                        var fnClose=eval(settings.query+"_close"); 
                        if(typeof fnClose=="function")
                            data = fnClose(settings); //execute the function to get return value
                        if(data==null || data==undefined) data={};
                        if(settings.callback!=null && typeof settings.callback=="function") settings.callback(data, settings); 
                     } 
                 },
                { img: "../images/b_delete.gif", text: "关闭", callback: function(){ mm.__close("#"+settings.id).maskClose(); } }
            ];
        }else{ //wrap the button callbacks
            for(var i=0; i<settings.buttons.length; i++){
                settings.buttons[i].callback=function(){
                    mm.__close("#"+settings.id);
                    var fnClose=eval(settings.query+"_close"); 
                    if(typeof fnClose=="function")
                        data = fnClose(settings); //execute the function to get return value
                    if(data==null || data==undefined) data={};
                    if(settings.callback!=null && typeof settings.callback=="function") settings.callback(data, settings); 
                    if(settings.buttons[i].callback!=null && typeof settings.buttons[i].callback=="function")
                        settings.buttons[i].callback(data==null?{}:data, settings);
                }
            }
        }//if(!settings.buttons || settings.buttons.length<=0)
        return this.__popup(settings);
    };
    
    magic.prototype.close=function(formID){
        $("#magicJsFrame"+formID).hide();
        return this;
    };
    
    magic.prototype.ajax=function(settings){
        return this;
    };
     
     magic.prototype.request=function(item)
    {
        var svalue = document.location.search.match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)","i"));
        return svalue ? svalue[1]: "";
    }
})();