/*
 * Treeview 1.4pre - jQuery plugin to hide and show branches of a tree
 * 
 * http://bassistance.de/jquery-plugins/jquery-plugin-treeview/
 *
 * Copyright (c) 2007 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id: jquery.treeview.js 4362 2008-01-07 17:08:59Z joern.zaefferer $
 *
 */
 /**
 * Modified by Richie
 * 1. January 29, 2008 (line 69, 220, 262), add a "selected" class in treeview items(disabled the expand / collapse when cliking on treeitems, expand / collapse only through hitarea)
 * 2. February 9, 2008 (line 244), apply the hover class, selected class and click event when add treeview items
 */

;(function($) {

	$.extend($.fn, {
		swapClass: function(c1, c2) { //将c1, c2所在的element中c1和c2这两个class交替变换，返回的是原对象
			var c1Elements = this.filter('.' + c1);
			this.filter('.' + c2).removeClass(c2).addClass(c1);
			c1Elements.removeClass(c1).addClass(c2);
			return this;
		},
		replaceClass: function(c1, c2) { //将c1所在的element中的class c1替换为c2，返回的是c1所在的element集合
			return this.filter('.' + c1).removeClass(c1).addClass(c2);
		},
		hoverClass: function(className) {
			className = className || "hover";
			return this.hover(function() {
				$(this).addClass(className);
			}, function() {
				$(this).removeClass(className);
			});
		},
		heightToggle: function(animated, callback) { //展开树节点函数
			animated ?
				this.animate({ height: "toggle" }, animated, callback) :
				this.each(function(){
					jQuery(this)[ jQuery(this).is(":hidden") ? "show" : "hide" ]();
					if(callback)
						callback.apply(this, arguments);
				});
		},
		heightHide: function(animated, callback) { //收缩树节点函数
			if (animated) {
				this.animate({ height: "hide" }, animated, callback);
			} else {
				this.hide();
				if (callback)
					this.each(callback);				
			}
		},
		showItem: function(){ //必须是span节点
		    if($(this).hasClass(CLASSES.expandable) || $(this).hasClass(CLASSES.lastExpandable)) return;
            $(this)
	            .parent()
	            // swap classes for hitarea
	            .find(">.hitarea")
		            .swapClass( CLASSES.collapsableHitarea, CLASSES.expandableHitarea )
		            .swapClass( CLASSES.lastCollapsableHitarea, CLASSES.lastExpandableHitarea )
	            .end()
	            // swap classes for parent li
	            .swapClass( CLASSES.collapsable, CLASSES.expandable )
	            .swapClass( CLASSES.lastCollapsable, CLASSES.lastExpandable )
	            // find child lists
	            .find( ">ul" )
	            // toggle them
	            .heightToggle();
		},
		
		prepareBranches: function(settings) { //标记树节点每一层的最后一个节点，以及初始显示时收缩/展开控制
			if (!settings.prerendered) {
				// mark last tree items
				this.filter(":last-child:not(ul)").addClass(CLASSES.last);
				// collapse whole tree, or only those marked as closed, anyway except those marked as open
				this.filter((settings.collapsed ? "" : "." + CLASSES.closed) + ":not(." + CLASSES.open + ")").find(">ul").hide();
			}
			// return all items with sublists
			return this.filter(":has(>ul)");
		},//prepareBranches
		
		applyClasses: function(settings, toggler) {
			if (!settings.prerendered) {
				// handle closed ones first
				this.filter(":has(>ul:hidden)")
						.addClass(CLASSES.expandable)
						.replaceClass(CLASSES.last, CLASSES.lastExpandable);						
				// handle open ones
				this.not(":has(>ul:hidden)")
						.addClass(CLASSES.collapsable)
						.replaceClass(CLASSES.last, CLASSES.lastCollapsable);						
	            // create hitarea
	            //执行完prepareBranches之后再执行这个函数，所以此时this是包含ul的li节点（即有子节点）
	            if(this.find(">div."+ CLASSES.hitarea).length<=0){//没有hitarea才添加
				    this.prepend("<div class=\"" + CLASSES.hitarea + "\"/>").find("div." + CLASSES.hitarea).each(function() {
					    var classes = "";
					    $.each($(this).parent().attr("class").split(" "), function() {
						    classes += this + "-hitarea ";
					    });
					    $(this).addClass( classes );
				    });
				}
			}//if (!settings.prerendered)			
			// apply event to hitarea
			this.find("div." + CLASSES.hitarea).click( toggler );
		},//applyClasses
		
		treeview: function(settings) {			
			settings = $.extend( { cookieId: "treeview" }, settings);			
			if (settings.add) { return this.trigger("add", [settings.add]); }
			//added by Richie, add the remove event
			//the branches must be a li node
			if (settings.remove) { return this.trigger("remove", [settings.remove]); }
			if ( settings.toggle ) {
				var callback = settings.toggle;
				settings.toggle = function() { return callback.apply($(this).parent()[0], arguments); };
			}
			function treeController(tree, control) {
				function handler(filter) {
					return function() {
						// reuse toggle event handler, applying the elements to toggle
						// start searching for all hitareas
						toggler.apply( $("div." + CLASSES.hitarea, tree).filter(function() {
							// for plain toggle, no filter is provided, otherwise we need to check the parent element
							return filter ? $(this).parent("." + filter).length : true;
						}) );
						return false;
					};
				}
				// click on first element to collapse tree
				$("a:eq(0)", control).click( handler(CLASSES.collapsable) );
				// click on second to expand tree
				$("a:eq(1)", control).click( handler(CLASSES.expandable) );
				// click on third to toggle tree
				$("a:eq(2)", control).click( handler() ); 
			}
		
			// handle toggle event
			function toggler() {
				$(this)
					.parent()
					// swap classes for hitarea
					.find(">.hitarea")
						.swapClass( CLASSES.collapsableHitarea, CLASSES.expandableHitarea )
						.swapClass( CLASSES.lastCollapsableHitarea, CLASSES.lastExpandableHitarea )
					.end()
					// swap classes for parent li
					.swapClass( CLASSES.collapsable, CLASSES.expandable )
					.swapClass( CLASSES.lastCollapsable, CLASSES.lastExpandable )
					// find child lists
					.find( ">ul" )
					// toggle them
					.heightToggle( settings.animated, settings.toggle );
				if ( settings.unique ) {
					$(this).parent()
						.siblings()
						.replaceClass( CLASSES.collapsable, CLASSES.expandable )
						.replaceClass( CLASSES.lastCollapsable, CLASSES.lastExpandable )
						.find( ">ul" )
						.heightHide( settings.animated, settings.toggle );
				}
			}
			
			function serialize() {
				function binary(arg) {
					return arg ? 1 : 0;
				}
				var data = [];
				branches.each(function(i, e) {
					data[i] = $(e).is(":has(>ul:visible)") ? 1 : 0;
				});
				$.cookie(settings.cookieId, data.join("") );
			}
			
			function deserialize() {
				var stored = $.cookie(settings.cookieId);
				if ( stored ) {
					var data = stored.split("");
					branches.each(function(i, e) {
						$(e).find(">ul")[ parseInt(data[i]) ? "show" : "hide" ]();
					});
				}
			}
			
			// add treeview class to activate styles
			this.addClass("treeview");
			
			// prepare branches and find all tree items with child lists
			var branches = this.find("li").prepareBranches(settings);
			
			switch(settings.persist) {
			case "cookie":
				var toggleCallback = settings.toggle;
				settings.toggle = function() {
					serialize();
					if (toggleCallback) {
						toggleCallback.apply(this, arguments);
					}
				};
				deserialize();
				break;
			case "location":
				var current = this.find("a").filter(function() { return this.href.toLowerCase() == location.href.toLowerCase(); });
				if ( current.length ) {
					current.addClass("selected").parents("ul, li").add( current.next() ).show();
				}
				break;
			}
			
			branches.applyClasses(settings, toggler);
				
			// if control option is set, create the treecontroller and show it
			if ( settings.control ) {
				treeController(this, settings.control);
				$(settings.control).show();
			}
			
			//added by Richie
			var treeitems = this.find("li");
			//apply the hover class
			treeitems.filter(":not(:has(>a))").find(">span").add( $("a", this) ).hoverClass();
			//apply the selected class
			treeitems.find(">span").bind("click", function(){
		        if(!(settings.selected==false)){
		            if($(this).hasClass(CLASSES.selected)) return; //if it has already in selected state, just do nothing
                    $(".treeview span."+CLASSES.selected).removeClass(CLASSES.selected);
                    $(this).addClass(CLASSES.selected);
                }
	            //apply the click event
	            if(settings.onclick && typeof settings.onclick=="function")
	                settings.onclick($(this));
			});
			
			this.bind("add", function(event, branches) {
				$(branches).prev()
					.removeClass(CLASSES.last)
					.removeClass(CLASSES.lastCollapsable)
					.removeClass(CLASSES.lastExpandable)
				.find(">.hitarea")
					.removeClass(CLASSES.lastCollapsableHitarea)
					.removeClass(CLASSES.lastExpandableHitarea);
				$(branches).parent().find("li").andSelf().prepareBranches(settings).applyClasses(settings, toggler);
				//added by Richie
				//apply the hover class, selected class and click event when add treeview items
				$(branches).filter(":not(:has(>a))").find("span").add( $("a", this) ).hoverClass();
			    $(branches).find("span").bind("click", function(){
			        if(!(settings.selected==false)){
			            if($(this).hasClass(CLASSES.selected)) return; //if it has already in selected state, just do nothing
	                    $(".treeview span."+CLASSES.selected).removeClass(CLASSES.selected);
	                    $(this).addClass(CLASSES.selected);
	                }
	                //apply the click event
	                if(settings.onclick && typeof settings.onclick=="function")
	                    settings.onclick($(this));
	            });
			});
			
			//added by Richie, add the remove event
			//the branches must be a single li node, and the tree html must be compliant with the W3C
			this.bind("remove", function(event, branches){
                var toRemove=$(branches);
                var top;
                if(toRemove.parent().find(">li").length==1){
                    //是唯一的节点
                    top=toRemove.parent().parent();
                    toRemove.parent().remove(); //删除节点
                    if(top.find(">div."+CLASSES.hitarea).length>0) top.find(">div."+CLASSES.hitarea).remove(); //删除hitarea
                    top.removeClass(CLASSES.expandable) //样式恢复
                        .removeClass(CLASSES.collapsable);
                    if(top.hasClass(CLASSES.lastExpandable)) top.removeClass(CLASSES.lastExpandable).addClass(CLASSES.last);
                    if(top.hasClass(CLASSES.lastCollapsable)) top.removeClass(CLASSES.lastCollapsable).addClass(CLASSES.last);
                }else if( toRemove.nextAll("li").length>0 ){
                    //不是最末尾的节点，直接删除就OK
                    toRemove.remove();
                }else{
                    //是最后一个节点，它前面至少还有另外一个兄弟节点
                    top=toRemove.prevAll("li").filter(":last");
                    toRemove.remove();
                    top.addClass(CLASSES.last);
                    if(top.hasClass(CLASSES.expandable)) top.removeClass(CLASSES.expandable).addClass(CLASSES.lastExpandable);
                    if(top.hasClass(CLASSES.collapsable)) top.removeClass(CLASSES.collapsable).addClass(CLASSES.lastCollapsable);
                    var hitarea=top.find(">div."+CLASSES.hitarea);
                    if(hitarea.length>0){
                        if(hitarea.hasClass(CLASSES.collapsableHitarea))
                            hitarea.removeClass(CLASSES.collapsableHitarea).addClass(CLASSES.lastCollapsableHitarea);
                        if(hitarea.hasClass(CLASSES.expandableHitarea))
                            hitarea.removeClass(CLASSES.expandableHitarea).addClass(CLASSES.lastExpandableHitarea);
                    }
                }
			});
			return this;
		}
	});
	
	// classes used by the plugin
	// need to be styled via external stylesheet, see first example
	var CLASSES = $.fn.treeview.classes = {
		open: "open",
		closed: "closed",
		expandable: "expandable",
		expandableHitarea: "expandable-hitarea",
		lastExpandableHitarea: "lastExpandable-hitarea",
		collapsable: "collapsable",
		collapsableHitarea: "collapsable-hitarea",
		lastCollapsableHitarea: "lastCollapsable-hitarea",
		lastCollapsable: "lastCollapsable",
		lastExpandable: "lastExpandable",
		last: "last",
		hitarea: "hitarea",
		selected: "selected" //added by Richie
	};
	
	// provide backwards compability
	$.fn.Treeview = $.fn.treeview;
	
})(jQuery);