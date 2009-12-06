<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0063)http://irisserver:8080/punchmain/workflow/menus/sel/topmenu.htm -->
<HTML><HEAD>
<META content="text/html; charset=gb2312" http-equiv=Content-Type>
<script language="javascript" src="../../scripts/utilities.js"></script>
<SCRIPT language=JavaScript>
<!--

function relogin(strFlag)
{
	/*
	if(parent.parent.opener.window.closed)
	{
		parent.parent.window.open("./../default.asp","ETHICS","width=800,height:640");
		parent.parent.window.close();
		
	}
	else{
		parent.parent.window.close();
		parent.parent.opener.window.location.href="../../default.asp";
	}
	*/
	/*
	var iWidth=(screen.availWidth).toString();
	var iHeight=(screen.availHeight/1.118).toString();
	
	if(strFlag=="1")	{
		parent.parent.window.open("/default.asp","","left=0,top=0,width="+iWidth+",height="+iHeight+",toolbar=yes,location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,");

		parent.parent.window.close();
		
	}else{
		parent.parent.opener.window.closeWindow.mFlag=false;
		parent.parent.opener.window.moveTo(0,0);
		parent.parent.opener.window.resizeTo(iWidth,iHeight);
		//parent.parent.window.location.href="/default.asp";
		
		parent.parent.opener.window.location.href="/default.asp";
		
		parent.parent.window.close();
	}	
	*/
	parent.parent.window.location.href="../../default.asp";
}
function logout(strFlag)
{
	if(strFlag=="1") {
		parent.parent.opener.window.closeWindow.mFlag=false;

		if(!parent.parent.opener.window.closed)
		{
			parent.parent.window.opener.window.close();
			
		}
		parent.parent.window.close();
	}else{
		

		parent.parent.window.close()
	}

}

function over1() {    
    title.className = "iconover";	
}

function out1() {    
	title.className = "icon";	
}
function down1() {    
	title.className = "icondown";	
	//parent.parent.main.location.href="/punchmain/index.asp";	
}
function over2() {
    var currid;
    
    currid = document.all('b' + event.srcElement.id);
    currid.className = "iconover2";
}
function out2() {    
    currid = document.all('b' + event.srcElement.id);
    currid.className = "icon2";	
}
function over2() {
    var currid;
    
    currid = document.all('b' + event.srcElement.id);
    currid.className = "iconover2";
}

function nomove(){
	window.event.returnValue = false;
}

function openWin(para){
	//../../usr/Padduser.htm"
	
	//window.open(strURL,'chgapprovor','width=600,height=324,top='+eval(document.body.clientHeight/2)+',left='+eval(document.body.clientWidth/2-270)+',resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no')
	var strDiaArg = "2000";
	var strUrl = "";
	var varTemp="true";
	switch (para) {
	   case "adduser" :
	      strUrl="../../usr/index.asp?index=add";
	      var vReturnValue=showModalDialog(strUrl, strDiaArg, "dialogWidth:500px;dialogHeight:518px;status:false;center:true;help:no; scrolling=no;resizable:no;status:no;");
			
			if (vReturnValue == "yes"){
				parent.parent.main.location.href="../../usr/queryuser.asp";
			}
			else if(vReturnValue == "exsit"){
				alert("该用户已经存在。");
			}
			
			else if(vReturnValue == "timeout"){
				var strUrl="../../err/err_index.asp?act=err&errcode=1"
				var ReturnValue=showModelessDialog(strUrl, "2000", "dialogWidth:380px;dialogHeight:120px;status:false;center:true;help:no; scrolling=no;resizable:no;status:no;");
				//alert(ReturnValue);
			}
			
					 
			//alert(vReturnValue);
	      break;
	   case "chguser" :
	   		if(parent.parent.main.document.SPRPageType==null)
	   		{
	   			alert("请选中要编辑的用户。");
				parent.parent.main.location="../../usr/queryuser.asp";
	   			return false;
	   		}
	   		else if((parent.parent.main.document.SPRPageType!="PAGE_USER") || (parent.parent.main.document.InfoList_SelectedItems=="")){
	   			alert("请选中要编辑的用户。");
				if(parent.parent.main.document.SPRPageType!="PAGE_USER")
					parent.parent.main.location="../../usr/queryuser.asp";
	   			return false;
	   		}
	      strUrl="../../usr/index.asp?index=chg&userid="+GetASelectedItem(parent.parent.main.document.InfoList_SelectedItems);
	      var vReturnValue=showModalDialog(strUrl, strDiaArg, "dialogWidth:500px;dialogHeight:512px;status:false;center:true;help:no; scrolling=no;resizable:no;status:no;");
			if (vReturnValue == "yes"){
				//parent.parent.main.location.href="queryuser.asp";
				parent.parent.main.document.usrfrm.submit();
				//alert('The modal window is closed w/o using the buttons');
			}
			
	      break;
	   case "wkstation" :
	      strUrl="../../usr/usr_wkstation.htm";
	      var vReturnValue=showModalDialog(strUrl, strDiaArg, "dialogWidth:500px;dialogHeight:340px;status:false;center:true;help:no; scrolling=no;resizable:no;status:no;");
			if (vReturnValue == "yes"){
				alert('ok');
			}
			else{
				alert('no');
			} 
	      break;
	   case "usrinfo" :
	      strUrl="../../usr/index.asp?index=usr";
	      var vReturnValue=showModalDialog(strUrl, strDiaArg, "dialogWidth:500px;dialogHeight:482px;status:false;center:true;help:no; scrolling=no;resizable:no;status:no;");
			/*if (vReturnValue == "yes"){
				alert("个人信息修改成功。");
			}
			else if (vReturnValue == "no"){
				alert("个人信息修改出错。");
			}
			*/
	      break;
	   case "deluser" :
	   	
	   		if(parent.parent.main.document.SPRPageType==null)
	   		{
	   			alert("请选中要删除的用户。");
				parent.parent.main.location="../../usr/queryuser.asp";
	   			return false;
	   		}
	   		else if((parent.parent.main.document.SPRPageType!="PAGE_USER") || (parent.parent.main.document.InfoList_SelectedItems=="")){
	   			alert("请选中要删除的用户。");
				if(parent.parent.main.document.SPRPageType!="PAGE_USER")
					parent.parent.main.location="../../usr/queryuser.asp";
	   			return false;
	   		}
			userids = GetSelectedItems(parent.parent.main.document.InfoList_SelectedItems);
			if(userids.toUpperCase().indexOf("SYSADMIN") >= 0){
				alert("不能删除系统管理员。");
				return false;
			}
			
	   	  var confirm=window.confirm("确定要删除选中的用户吗？");
			
			if (confirm)
			{
				strURL="../../usr/usr_handle.asp?act=del&userid="+GetSelectedItems(parent.parent.main.document.InfoList_SelectedItems);
				parent.parent.main.location.href=strURL;
				
			}
	      //varTemp="false";
	      break;
	   default :
	      alert("ERROR");
	      
	}
	/*if(varTemp=="true"){
	 
		//window.open(strUrl,'chgapprovor','width=600,height=324,top='+eval(document.body.clientHeight/2)+',left='+eval(document.body.clientWidth/2-270)+',resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no')
		
	}*/
}
document.onmousemove = nomove;
//-->
</SCRIPT>

<META content="MSHTML 5.00.3315.2870" name=GENERATOR>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</HEAD>
<BODY bgColor=#FFFFFF style="MARGIN-LEFT: 0pt; MARGIN-RIGHT: 0pt; MARGIN-TOP: 0pt" leftmargin="0" topmargin="0" >
<DIV class=icon id=title onmousedown=down1() onmouseout=out1() 
onmouseover=over1() onmouseup=out1() 
style="LEFT: 0px; POSITION: absolute; TOP: 0px; WIDTH: 100%; Z-INDEX: 2">用户管理</DIV>
<DIV id=inFloder 
style="HEIGHT: 2000px; POSITION: absolute; TOP: 0px; WIDTH: 300px"><SPAN 
id=bicon10 style="LEFT: 2px; POSITION: absolute; TOP: 25px; WIDTH: 22px"><IMG 
border=0 src="images/menu1.gif" width="20" height="20"></SPAN><A 
href="../../usr/queryuser.asp" 
target=main><SPAN class=hand1 id=icon10 onmouseout=out2() onmouseover=over2() 
style="LEFT: 25px; POSITION: absolute; TOP: 30px; WIDTH: 200px">显示用户</SPAN> </A><SPAN id=bicon1 
style="LEFT: 2px; POSITION: absolute; TOP: 75px; WIDTH: 22px"><img 
border=0 src="images/menu_add.gif" width="20" height="20"></SPAN> <A href="javascript:void(0)" onclick="javascript:openWin('adduser')"> 
  <SPAN class=hand1 id=icon1 onmouseout=out2() onmouseover=over2() 
style="LEFT: 25px; POSITION: absolute; TOP: 80px; WIDTH: 200px">添加用户</SPAN> </A><SPAN id=bicon2 
style="LEFT: 2px; POSITION: absolute; TOP: 100px; WIDTH: 22px"><img 
border=0 src="images/menu_edit.gif" width="20" height="20"></SPAN> <A href="javascript:void(0)" onclick="javascript:openWin('chguser')"> 
  <SPAN class=hand1 id=icon2 onmouseout=out2() onmouseover=over2() 
style="LEFT: 26px; POSITION: absolute; TOP: 105px; WIDTH: 200px">编辑用户</SPAN> </A><SPAN id=bicon3 
style="LEFT: 2px; POSITION: absolute; TOP: 125px; WIDTH: 22px"><img 
border=0 src="images/menu_del.gif" width="20" height="20"></SPAN> <A href="javascript:void(0)" onclick="javascript:openWin('deluser')"> 
  <SPAN 
class=hand1 id=icon3 onmouseout=out2() onmouseover=over2() 
style="LEFT: 25px; POSITION: absolute; TOP: 130px; WIDTH: 200px">删除用户</SPAN> </A><SPAN id=bicon4 
style="LEFT: 1px; POSITION: absolute; TOP: 149px; WIDTH: 22px"><img 
border=0 src="images/menu_info.gif" width="20" height="20"></SPAN> <A href="../../usr/usr_wkstation.asp" target=main> 
  <SPAN 
class=hand1 id=icon5 onmouseout=out2() onmouseover=over2() 
style="LEFT: 25px; POSITION: absolute; TOP: 55px; WIDTH: 200px">主工作台</SPAN> </A><SPAN id=bicon5 
style="LEFT: 2px; POSITION: absolute; TOP: 50px; WIDTH: 22px"><img 
border=0 src="images/menu_desk.gif" width="20" height="20"></SPAN> <A href="javascript:void(0)" onclick="javascript:openWin('usrinfo')"> 
  <SPAN class=hand1 id=icon4 onmouseout=out2() onmouseover=over2() 
style="LEFT: 25px; POSITION: absolute; TOP: 155px; WIDTH: 200px">个人信息</SPAN> </A><SPAN id=bicon6 
style="LEFT: 2px; POSITION: absolute; TOP: 175px; WIDTH: 22px"><img 
border=0 src="images/menu_login.gif" width="20" height="20"></SPAN><A href="javascript:void(0)" onclick="javascript:relogin('<%=request("flag")%>')"> 
  <SPAN 
class=hand1 id=icon6 onmouseout=out2() onmouseover=over2() 
style="LEFT: 25px; POSITION: absolute; TOP: 180px; WIDTH: 200px">注销用户</SPAN> </A><SPAN id=bicon7 
style="LEFT: 2px; POSITION: absolute; TOP: 200px; WIDTH: 22px"><img 
border=0 src="images/menu_exit.gif" width="20" height="20"></SPAN><A href="javascript:void(0)" onclick="javascript:logout('<%=request("flag")%>')"> 
  <SPAN class=hand1 id=icon7 onmouseout=out2() onmouseover=over2() 
style="LEFT: 25px; POSITION: absolute; TOP: 205px; WIDTH: 200px">退出系统</SPAN> </A></DIV>
<IMG id=up2 onmousedown=miny() src="../images/up.gif" 
style="DISPLAY: none; POSITION: absolute; Z-INDEX: 1"> <IMG id=down2 
onmousedown=addy() src="../images/down.gif" 
style="DISPLAY: none; POSITION: absolute; Z-INDEX: 1">
<SCRIPT language=javascript>
var apk = 0;
window.onresize = doOnresize;
setTimeout('ini()',300);
function doOnresize(){
    ini();
}
function ini(){
    maxy =225
          if (maxy > document.body.offsetHeight - 20){
              down2.style.display = '';
          }
          else {
              down2.style.display = 'none';
          }
    up2.style.pixelLeft = document.body.offsetWidth - 17
    down2.style.pixelLeft = document.body.offsetWidth - 17
    up2.style.pixelTop = 25
    down2.style.pixelTop = document.body.offsetHeight - 18
}
function addy(){
   ik = apk;
    addygo();
}
function addygo(){
    k = apk;
    inFloder.style.clip = 'rect(' + (k + 25) + ' 100% ' + (k+500) + ' 0)';
    inFloder.style.top = (0 - k)
    if ((k < ik + 150) && k<(225-50)){
        apk = (k + 5);
        up2.style.display = '';
        setTimeout('addygo()',10);
    }
    if (apk>=225-100){
        down2.style.display = 'none';
    }
}
function miny(){
    ih = apk;
    minygo();
}
function minygo(){
    h = apk
   inFloder.style.clip = 'rect(' + (h + 25) + ' 100% ' + (h+500) + ' 0)';
    inFloder.style.top = (0 - h)
    if ((h > ih - 150) && ( h >= 0)){
        apk = (h - 5);
    down2.style.display = '';
        setTimeout('minygo()',10);
    }
    if (apk<=0){
        up2.style.display = 'none';
    }
}
</SCRIPT>

<SCRIPT language=VBScript>
sub document_onclick()
	window.Parent.frm1.rows ="*,23,23,23,23,23,23,23"
end sub	

sub title_OnMouseOver()
	title.style.cursor = "hand"
end sub	
</SCRIPT>
 </BODY></HTML>
