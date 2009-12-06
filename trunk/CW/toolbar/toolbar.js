var passwordError="Password is missing.";
window.onload=initToolBarPrime;
preload(new Array("bold","expandthread","folder","folderlistbutton","help","home","hr","indent","italic","justifycenter","justifyleft","justifyright","message2","minusicon","newmessage","nextmessage","nextthread","outdent","paletteicon","plusicon","postmessage","previewposition","refreshicon","replytoauthor","replytogroup","separator","treeback.gif","underline","webfxfolder","tileback"),"images/",".gif");
var href="";
var subject="";
function openSettings()
{
	adminWin=window.open(adminPath+"adminSettings.html","settingsWindow","height=420, width=505, toolbar=0,location=0,directories=0,status=0,menuBar=0,scrollBars=0,resizable=0");
	adminWin.focus();
}
function newMessage()
{
	win=window.open("editMessage.html?new","","height=400, width=500, toolbar=0,location=0,directories=0,status=0,menuBar=0,scrollBars=0,resizable=1");
	win.focus();
}
function reply()
{
	if(subject.indexOf("re:")== -1)
		subject="Re:"+subject;
	window.location=(href+"?subject="+subject);
}
function followUp()
{
	win=window.open("editMessage.html?reply","","height=400, width=500, toolbar=0,location=0,directories=0,status=0,menuBar=0,scrollBars=0,resizable=1");
	win.focus();
}
function initToolBarPrime()
{
	composeButton.onclick=new Function("newMessage()");
	followUpButton.onclick=new Function("followUp()");
	replyToAuthorButton.onclick=new Function("reply()");
	refreshButton.onclick=new Function("refresh()");
	toggleExpandCollapseButton.onclick=new Function("if (parent.trLoaded) parent.frames['treeframe'].expandCollapse();");
	nextMessageButton.onclick=new Function("if (parent.trLoaded) parent.frames['treeframe'].nextMessage();");
	nextThreadButton.onclick=new Function("if (parent.trLoaded) parent.frames['treeframe'].nextThread();");
	viewGroupsButton.onclick=new Function("parent.switchLayout()");
	toggleMessageListButton.onclick=new Function("toggleMessageTree()");
	aboutButton.onclick=new Function("showSplash()");
	if(adminMode)
	{
		adminEditButton.onclick=new Function("adminEditMessage()");
		adminSettingsButton.onclick=new Function("openSettings()");
		deleteButton.onclick=new Function("deleteMessages()");
	}
	disable(followUpButton);
	disable(replyToAuthorButton);
	if(adminMode)
	{
		disable(adminEditButton);
		disable(deleteButton);
	}
	disable(nextMessageButton);
	disable(nextThreadButton);
	addToggle(toggleExpandCollapseButton);
	addToggle(toggleMessageListButton);
	addToggle(viewGroupsButton);
	toggle(toggleMessageListButton);
	makePressed(toggleMessageListButton);
	toggle(viewGroupsButton);
	makePressed(viewGroupsButton);
	if(parent==null)return;
	parent.toolbarLoaded();
	document.body.style.cursor="default";
}
function toggleMessageTree()
{
	parent.toggleTree();
}
function checkMessage()
{
	var mLoaded=parent.mLoaded;
	if(!mLoaded)
	{
		window.setTimeout("checkMessage()",100);
	}
	else
	{
		var d=parent.frames("messageFrame").document;
		var msgFrom=d.all("email").innerText;
		var msgSubject=d.all("subject").innerText;
		if(msgSubject!=" ")
		{
			enable(followUpButton);
			if(adminMode)
				enable(adminEditButton);
			enable(nextMessageButton);
			enable(nextThreadButton);
		}
		if(d.all("email").children.length>0)
		{
			enable(replyToAuthorButton);
			href=d.all("email").children[0].href;
			subject=msgSubject;
		}
		else
			disable(replyToAuthorButton);
	}
}
var idList=new Array();
function deleteMessages()
{
	var str="id=";
	var first=true;
	idList=idList.sort();
	for(var i in idList)
	{
		if(idList[i])
		{
			if(!first)
				str+=",";
			else
				first=false;str+=i.substring(1,i.length);
		}
	}
	var pwd=parent.treeframe.document.body.password;
	var groupName=parent.treeframe.document.body.group;
	if(pwd==null)
		alert(passwordError);
	else
		parent.treeframe.location=cgiFile+"?action=delete&group="+groupName+"&"+str+"&pwd="+pwd;
	makeFlat(deleteButton);
	disable(deleteButton);
}
function checkDeleteButtonStatus()
{
	var none=true;
	for(var i in idList)
	{
		if(idList[i])
		{
			none=false;
			break;
		}
	}
	if(none)
		disable(deleteButton);
	else
		enable(deleteButton);
}
function adminEditMessage()
{
	win=window.open("editMessage.html?edit","","height=400, width=500, toolbar=0,location=0,directories=0,status=0,menuBar=0,scrollBars=0,resizable=1");
	win.focus();
}
function refresh()
{
	parent.frames["treeframe"].location.reload();
}