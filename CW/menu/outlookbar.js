///��
function showitem(id,name)
{
	var itemStr = "<tr><td height=25px><A href='"+id+"' target=main><SPAN style='Margin-top:20px'>";
	itemStr+="<IMG height=16 src='images/menu_edit.gif' width=16 border=0 align='absmiddle'>";
	itemStr+=name;
	itemStr+="</SPAN></a></td></tr>";
	return itemStr;	
	//return ("<span style='LINE-HEIGHT: 25px'><a href='"+id+"' target=main></span><br>")
}

function switchoutlookBar(number)
{
	var i = outlookbar.opentitle;
	outlookbar.opentitle=number;
	var id1,id2,id1b,id2b
	if (number!=i && outlooksmoothstat==0){
		if (number!=-1)
		{
			if (i==-1){
				id2="blankdiv";
				id2b="blankdiv";
			}
			else{
				id2="outlookdiv"+i;
				id2b="outlookdivin"+i;
				//document.all("outlooktitle"+i).style.border="1px none navy";
				//document.all("outlooktitle"+i).style.background=outlookbar.maincolor;
				//document.all("outlooktitle"+i).style.color="#ffffff";
				//document.all("outlooktitle"+i).style.textalign="center";
			}
			id1="outlookdiv"+number
			id1b="outlookdivin"+number
			//document.all("outlooktitle"+number).style.border="1px none white";
			//document.all("outlooktitle"+number).style.background=outlookbar.maincolor; //title
			//document.all("outlooktitle"+number).style.color="#ffffff";
			//document.all("outlooktitle"+number).style.textalign="center";
			
			smoothout(id1,id2,id1b,id2b,0);
		}
		else
		{
		alert("aa");
			document.all("blankdiv").style.display="";
			document.all("blankdiv").sryle.height="100%";
			document.all("outlookdiv"+i).style.display="none";
			document.all("outlookdiv"+i).style.height="0%";
			document.all("outlooktitle"+i).style.border="1px none navy";
			document.all("outlooktitle"+i).style.background=outlookbar.maincolor;
			document.all("outlooktitle"+i).style.color="#ffffff";
			document.all("outlooktitle"+i).style.textalign="center";
		}
	}
}
function smoothout(id1,id2,id1b,id2b,stat)
{
	if(stat==0){
		tempinnertext1=document.all(id1b).innerHTML;
		tempinnertext2=document.all(id2b).innerHTML;
		document.all(id1b).innerHTML="";
		document.all(id2b).innerHTML="";
		outlooksmoothstat=1;
		document.all(id1b).style.overflow="hidden";
		document.all(id2b).style.overflow="hidden";
		document.all(id1).style.height="0%";
		document.all(id1).style.display="";
		setTimeout("smoothout('"+id1+"','"+id2+"','"+id1b+"','"+id2b+"',"+outlookbar.inc+")",outlookbar.timedalay);
	}
	else
	{
		stat+=outlookbar.inc;
		if (stat>100)
			stat=100;
		document.all(id1).style.height=stat+"%";
		document.all(id2).style.height=(100-stat)+"%";
		if (stat<100) 
			setTimeout("smoothout('"+id1+"','"+id2+"','"+id1b+"','"+id2b+"',"+stat+")",outlookbar.timedalay);
		else
		{
			document.all(id1b).innerHTML=tempinnertext1;
			document.all(id2b).innerHTML=tempinnertext2;
			outlooksmoothstat=0;
			document.all(id1b).style.overflow="auto";
			document.all(id2).style.display="none";
		}
	}
}
function getOutLine()
{
	outline="<table "+outlookbar.otherclass+">";
	for (i=0;i<(outlookbar.titlelist.length);i++)
	{
		outline+="<tr><td name=outlooktitle"+i+" id=outlooktitle"+i+" "; 
		if (i!=outlookbar.opentitle) 
			//outline+=" nowrap align=center style='cursor:hand;background-color:"+outlookbar.maincolor+";color:#ffffff;height:20;border:1 none navy' ";
			outline+=" nowrap align=center style='LINE-HEIGHT: 22px;CURSOR: hand; COLOR: #000000; TEXT-ALIGN: center' ";
		else
			outline+=" nowrap align=center style='LINE-HEIGHT: 22px;CURSOR: hand; COLOR: #000000; TEXT-ALIGN: center' ";
		outline+=outlookbar.titlelist[i].otherclass
		outline+=" onclick='switchoutlookBar("+i+")' background='images/outlookbar.gif'><span class=smallFont>";
		outline+=outlookbar.titlelist[i].title+"</span></td></tr>";
		outline+="<tr><td name=outlookdiv"+i+" valign=top align=left id=outlookdiv"+i+" style='width:100%"
		if (i!=outlookbar.opentitle) 
			outline+=";display:none;height:0%;";
		else
			outline+=";display:;height:100%;";
		outline+="'><div name=outlookdivin"+i+" id=outlookdivin"+i+" style='overflow:auto;width:100%;height:100%'><table>";
		for (j=0;j<outlookbar.itemlist[i].length;j++)
			outline+=showitem(outlookbar.itemlist[i][j].key,outlookbar.itemlist[i][j].title);
		outline+="</table></div></td></tr>"
	}
	outline+="</table>"
	return outline
}
function show()
{
	var outline;
	outline="<div id=outLookBarDiv name=outLookBarDiv style='width=100%;height:100%'>"
	outline+=outlookbar.getOutLine();
	outline+="</div>"
	document.write(outline);
}
///������
function theitem(intitle,instate,inkey)
{
	this.state=instate;
	this.otherclass=" nowrap ";
	this.key=inkey;
	this.title=intitle;
}
function addtitle(intitle)
{
	outlookbar.itemlist[outlookbar.titlelist.length]=new Array();
	outlookbar.titlelist[outlookbar.titlelist.length]=new theitem(intitle,1,0);
	return(outlookbar.titlelist.length-1);
}
function additem(intitle,parentid,inkey)
{
	if (parentid>=0 && parentid<=outlookbar.titlelist.length)
	{
		outlookbar.itemlist[parentid][outlookbar.itemlist[parentid].length]=new theitem(intitle,2,inkey);
		outlookbar.itemlist[parentid][outlookbar.itemlist[parentid].length-1].otherclass=" nowrap align=left style='height:5' ";
		return(outlookbar.itemlist[parentid].length-1);
	}
	else
		additem=-1;
}
function outlook()
{
	this.titlelist=new Array();
	this.itemlist=new Array();
	this.divstyle="style='height:100%;width:100%;overflow:auto' align=center";
	this.otherclass="border=0 cellspacing='0' cellpadding='0' style='height:100%;width:100%'valign=middle align=center ";
	this.addtitle=addtitle;
	this.additem=additem;
	this.starttitle=-1;
	this.show=show;
	this.getOutLine=getOutLine;
	this.opentitle=this.starttitle;
	this.reflesh=outreflesh;
	this.timedelay=50;
	this.inc=10;
	this.maincolor = "#336699"
}
function outreflesh()
{
	document.all("outLookBarDiv").innerHTML=outlookbar.getOutLine();
}
function locatefold(foldname)
{
	if (foldname=="")
		foldname = outlookbar.titlelist[0].title
	for (var i=0;i<outlookbar.titlelist.length;i++)
	{
		if(foldname==outlookbar.titlelist[i].title)
		{
			outlookbar.starttitle=i;
			outlookbar.opentitle=i;
		}
	}
}
var tempinnertext1,tempinnertext2,outlooksmoothstat
outlooksmoothstat = 0;
