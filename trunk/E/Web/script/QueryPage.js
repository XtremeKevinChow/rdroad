// JavaScript Document
function showArea(obj,areaTarget){
	var allTabTitle = obj.parentNode.getElementsByTagName("LI");
	var allArea = obj.parentNode.parentNode.parentNode.getElementsByTagName("DIV");
	var timeout = setTimeout(function(){
		try{
			clearTimeout(switchToTodayInfo);
		}catch(exp){}
		i=0;
		while(i<=allTabTitle.length-1){
				allTabTitle[i].className="on2";
				i++;
		}
		obj.className="on";
		j=0;
		while(j<=allArea.length-1){
			if (allArea[j].id!=areaTarget){
				allArea[j].style.display="none";
			}else{
				allArea[j].style.display="block";
			}
			j++;
		}
	},200)
	obj.onmouseout = function(){
		clearTimeout(timeout);
		obj.onmouseout = null;
	}
}
function toggleMenu(b,obj){
	$(obj).toggle();
	var icon = b.parent().getElementsByTagName("b");
	if($(obj).visible()){
		$(icon[0]).className="h4b1";
	} else {
		$(icon[0]).className="h4b2";
	}
	return false;
}

function EventElement(event)
{
    return event.target || event.srcElement
}

/*绑定列表table的特效，相关函数*/
function observeTableTrOver(event){
	//caution:使用$可以防止某些浏览器中获取的对象没有被封装
	$(EventElement(event).parentNode).addClass('onclick');
}
function observeTableTrOut(event){
	$(EventElement(event).parentNode).removeClass('onclick');
}
function hasSelect(tableId, selAllId)
{
    var selectAll = selAllId && selAllId.length>0 ? selAllId : "chkSelectAll";
    if(selectAll.substr(0,1)=="#") selectAll = selectAll.substr(1);
    return $(tableId).find("tbody>tr").find("td:eq(0)>input[type='checkbox'][checked][id!='"+selectAll+"']").length>0;
}

function isAllSelect(tableId){
	all_s = true;
	trs =  $('TR',$(tableId)[0].tBodies[0]);
	trs.each(
	function(i){
	    var tr = $(this);
		if(!tr.is('.no_sel') && !tr.is('.collapse')){
			 var input = $('INPUT',$('TD',tr)[0])[0];
			if(input && !input.checked && input.checked != 'checked') {
				all_s = false;
			}
		}
	});
	//全选或者全未选时触发变化
	return all_s;
}
function bindTableBehavior(tableId, chkSelAllId){
	
	//将tableId的所有的行作为变量存储在它的SelAll的CheckBox上，
	//以保证多个表的行不发生窜动
	$(chkSelAllId)[0].tablerows= $('TR',$(tableId)[0].tBodies[0]);
	var trs = $(chkSelAllId)[0].tablerows;
	
	//if(trs.length==0) alert(trs);
	//全选
	$(chkSelAllId).bind('click',
		function(event){
			button = EventElement(event);
			var trs = $(chkSelAllId)[0].tablerows;
			
			trs.each(function(i){
				try{
				   
				    var tr = $(this);
				   
				    //alert("tr:"+tr.html());
				   var tds = $('TD',tr);
				   if(tds.length == 0) return;
				  
				    var input = $('INPUT',tds[0]);
				   // alert("input:" + input.html());
				    
				    if(input.is("input[@type='checkbox']"))
				        input.attr("checked",button.checked);
					
					if(button.checked){
					    tr.addClass('onselect');
						tr.unbind('mouseover', observeTableTrOver);
						tr.unbind('mouseout', observeTableTrOut);
						
					subTds = $('TD',tr);	
//						subTds.each(function(i){
//							td = $(this);
//							td.unbind('mouseover', observeTableTrOver);
//							td.unbind('mouseout', observeTableTrOut);
//						});
						tr.removeClass('onclick');
					} else {
						tr.removeClass('onselect');
						tr.bind('mouseover', observeTableTrOver);
						tr.bind('mouseout', observeTableTrOut);
//						subTds.each(function(i){
//							td = $(this);
//							td.bind('mouseover', observeTableTrOver);
//							td.bind('mouseout', observeTableTrOut);
//						});
					}
				}catch (e){}
			});
		}
	);
	//Grid可以按行选中，还有鼠标悬停的特效
	trs.each(
		function(i){
		    var tr = $(this);
			if(!tr.is('.no_sel') && !tr.is('.collapse')){
				tds = $('TD',tr);
				//初始化时检测input是否选中
				input =$('INPUT',$('TD',tr)[0])[0];
				if(input && input.checked){
					tr.addClass('onselect');
				}
				tds.each(function(i){
					//caution:使用$可以防止某些浏览器中获取的对象没有被封装
					var td = $(this);
					td.bind('click', function(event){
						td = $(EventElement(event));
						tr = $(td).parent();
						if(td.is("INPUT")){
							tr =$(tr).parent();
						}
						
						if(tr.is("TR")){
							tr.toggleClass('onselect');
							input = $('INPUT',$('TD',tr)[0]);
							if(input.is("input[@type='checkbox']"))
							    input.attr("checked",tr.is('.onselect') ? "checked" : "");
							subTds = $($('TD',tr));
							if(tr.is('.onselect')){
								subTds.each(function(i){
									td = $(this);
									td.unbind('mouseover', observeTableTrOver);
									td.unbind('mouseout', observeTableTrOut);
								});
								tr.removeClass('onclick');
							} else {
								subTds.each(function(i){
									td = $(this);
									td.bind('mouseover', observeTableTrOver);
									td.bind('mouseout', observeTableTrOut);
								});
							}
						}
						$(chkSelAllId).attr("checked",isAllSelect(tableId) ? "checked" : "");
					});
					td.bind('mouseover', observeTableTrOver);
					td.bind('mouseout', observeTableTrOut);
				});
			}
		}
	);
}

function ShowMsg(msgContent, msgTitle,masked)
{
    if(masked)
        mm.mask( { } ).msg({msg:msgContent, title:msgTitle, timeout:1, width:280, height:10, valign: "top-in", vmargin:100 });
     else
        mm.msg({msg:msgContent, title:msgTitle, timeout:1, width:280, height:10, valign: "top-in", vmargin:100 });
}