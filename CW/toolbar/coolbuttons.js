document.onmouseover=doOver;
document.onmouseout=doOut;
document.onmousedown=doDown;
document.onmouseup=doUp;
function doOver()
{
	var toEl=getReal(window.event.toElement,"className","coolButton");
	var fromEl=getReal(window.event.fromElement,"className","coolButton");
	if(toEl==fromEl)
		return;
	var el=toEl;
	var cDisabled=el.cDisabled;
	cDisabled=(cDisabled!=null);
	if(el.className=="coolButton")
		el.onselectstart=new Function("return false");
	if((el.className=="coolButton")&&!cDisabled)
	{
		makeRaised(el);
		makeGray(el,false);
	}
}
function doOut()
{
	var toEl=getReal(window.event.toElement,"className","coolButton");
	var fromEl=getReal(window.event.fromElement,"className","coolButton");
	if(toEl==fromEl)
		return;
	var el=fromEl;
	var cDisabled=el.cDisabled;cDisabled=(cDisabled!=null);
	var cToggle=el.cToggle;
	toggle_disabled=(cToggle!=null);
	if(cToggle&&el.value)
	{
		makePressed(el);
		makeGray(el,true);
	}
	else 
	if((el.className=="coolButton")&&!cDisabled)
	{
		makeFlat(el);
		makeGray(el,true);
	}
}
function doDown()
{
	el=getReal(window.event.srcElement,"className","coolButton");
	var cDisabled=el.cDisabled;cDisabled=(cDisabled!=null);
	if((el.className=="coolButton")&&!cDisabled)
	{
		makePressed(el);
		if(el.id=="leftmenu")	mngMenu(el);
		if(el.id=="bottomright")	mngCopyright(el);
	}
}
function doUp()
{
	el=getReal(window.event.srcElement,"className","coolButton");
	var cDisabled=el.cDisabled;
	cDisabled=(cDisabled!=null);
	if((el.className=="coolButton")&&!cDisabled)
	{
		makeRaised(el);
	}
}
function getReal(el,type,value)
{
	temp=el;
	while((temp!=null)&&(temp.tagName!="BODY"))
	{
		if(eval("temp."+type)==value)
		{
			el=temp;return el;
		}
		temp=temp.parentElement;
	}
	return el;
}
function findChildren(el,type,value)
{
	var children=el.children;
	var tmp=new Array();
	var j=0;
	for(var i=0;i<children.length;i++)
	{
		if(eval("children[i]."+type+"==\""+value+"\""))
		{
			tmp[tmp.length]=children[i];
		}
		tmp=tmp.concat(findChildren(children[i],type,value));
	}
	return tmp;
}
function disable(el)
{
	if(document.readyState!="complete")
	{
		window.setTimeout("disable("+el.id+")",100);
		return;
	}
	var cDisabled=el.cDisabled;
	cDisabled=(cDisabled!=null);
	if(!cDisabled)
	{
		el.cDisabled=true;
		if(document.getElementsByTagName)
		{
			el.innerHTML="<span style='background: buttonshadow; filter: chroma(color=red) dropshadow(color=buttonhighlight, offx=1, offy=1); height: 100%;'>"+"<span style='filter: mask(color=red); height: 100%;'>"+el.innerHTML+"</span>"+"</span>";
		}
		else
		{
			el.innerHTML='<span style="background: buttonshadow; width: 100%; height: 100%; text-align: center;">'+'<span style="filter:Mask(Color=buttonface) DropShadow(Color=buttonhighlight, OffX=1, OffY=1, Positive=0); height: 100%; width: 100%%; text-align: center;">'+el.innerHTML+'</span>'+'</span>';
		}
		if(el.onclick!=null)
		{
			el.cDisabled_onclick=el.onclick;
			el.onclick=null;
		}
	}
}function enable(el)
{
	var cDisabled=el.cDisabled;cDisabled=(cDisabled!=null);
	if(cDisabled)
	{
		el.cDisabled=null;el.innerHTML=el.children[0].children[0].innerHTML;
		if(el.cDisabled_onclick!=null)
		{
			el.onclick=el.cDisabled_onclick;el.cDisabled_onclick=null;
		}
	}
}
function addToggle(el)
{
	var cDisabled=el.cDisabled;
	cDisabled=(cDisabled!=null);
	var cToggle=el.cToggle;cToggle=(cToggle!=null);
	if(!cToggle&&!cDisabled)
	{
		el.cToggle=true;
		if(el.value==null)
			el.value=0;
		if(el.onclick!=null)
			el.cToggle_onclick=el.onclick;
		else
			el.cToggle_onclick="";
		el.onclick=new Function("toggle("+el.id+"); "+el.id+".cToggle_onclick();");
	}
}function removeToggle(el)
{
	var cDisabled=el.cDisabled;cDisabled=(cDisabled!=null);
	var cToggle=el.cToggle;
	cToggle=(cToggle!=null);
	if(cToggle&&!cDisabled)
	{
		el.cToggle=null;
		if(el.value)
		{toggle(el);}
		makeFlat(el);
		if(el.cToggle_onclick!=null)
		{
			el.onclick=el.cToggle_onclick;
			el.cToggle_onclick=null;
		}
	}
}
function toggle(el)
{
	el.value=!el.value;
	if(el.value)
		el.style.background="URL(/images/tileback.gif)";
	else
		el.style.backgroundImage="";
}
function makeFlat(el)
{
	with(el.style)
	{
		background="";
		border="1px solid buttonface";
		padding="1px";
	}
}
function makeRaised(el)
{
	with(el.style)
	{
		borderLeft="1px solid buttonhighlight";
		borderRight="1px solid buttonshadow";
		borderTop="1px solid buttonhighlight";
		borderBottom="1px solid buttonshadow";
		padding="1px";
	}
}
function makePressed(el)
{
	with(el.style)
	{
		borderLeft="1px solid buttonshadow";
		borderRight="1px solid buttonhighlight";
		borderTop="1px solid buttonshadow";
		borderBottom="1px solid buttonhighlight";
		paddingTop="2px";
		paddingLeft="2px";
		paddingBottom="0px";
		paddingRight="0px";
	}
}
function makeGray(el,b)
{
	var filtval;
	if(b)
		filtval="gray()";
	else
		filtval="";
	var imgs=findChildren(el,"tagName","IMG");
	for(var i=0;i<imgs.length;i++)
	{
		imgs[i].style.filter=filtval;
	}
}
function mngMenu(el)
{
	if(document.all.hiddenright.value=="show")
	{	
		document.all.hiddenright.value="hide";
		document.all.leftmenu.title="��ʾ�˵�";
		window.parent.framemenu.cols="0,*";
	}
	else
	{
		document.all.hiddenright.value="show";
		document.all.leftmenu.title="���ز˵�";
		window.parent.framemenu.cols="120,*";
	}
			
		
}
function mngCopyright(el)
{
	if(document.all.hiddenright.value=="show")
	{	
		document.all.hiddenright.value="hide";
		document.all.bottomright.title="��ʾ��Ȩ";
		window.parent.mainframe.rows="30,*,0";
		//alert(window.parent.mainframe.rows);
	}
	else
	{
		document.all.hiddenright.value="show";
		document.all.bottomright.title="���ذ�Ȩ";
		window.parent.mainframe.rows="30,*,20";
	}
	
}
document.write("<style>");
document.write(".coolBar	{background: buttonface;border-top: 1px solid buttonhighlight;	border-left: 1px solid buttonhighlight;	border-bottom: 1px solid buttonshadow; border-right: 1px solid buttonshadow; padding: 2px; font: menu;}");
document.write(".coolButton {border: 1px solid buttonface; padding: 1px; text-align: center; cursor: default;}");
document.write(".coolButton IMG	{filter: gray();}");
document.write("</style>");