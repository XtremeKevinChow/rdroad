<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*,com.magic.crm.user.entity.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�����˵�</title>
<style type="text/css">
<!--
*{border:0; font-size:12px;}
body {
margin:0 10px; padding:0;
font-family: arial, ����, serif;
font-size:12px;
background:url(images/nav_top_bg.gif) repeat-x right top;
}
a{ text-decoration:underline; cursor:hand;}
.toptable{width:100%; height:56px; margin:0; padding:0;}
.toptable .rightTd{width:300px; text-align:right;}
.toptable .midTd{vertical-align:top; text-align:right; padding-top:6px; line-height:20px;}
.toptable .leftTd{width:450px;}
.toptable .leftTd img {margin-left:1em;}
.fb12{font-size:12px; font-weight:bold;}
/*------------------һ���˵�-------------------------*/

#nav {
line-height: 24px; list-style-type: none; margin:0; padding:0;
}
#nav a {
display: block; text-align:left;
}

#nav a:link {
color:#fff; width:auto; white-space:nowrap; padding:0; text-decoration:none;font-weight:bold; text-align:center;
}
#nav a:visited {
color:#fff; width:auto; white-space:nowrap; padding:0; text-decoration:none;font-weight:bold; text-align:center;
}
#nav a:hover {
color:#FFF; width:auto; white-space:nowrap; padding:0; text-decoration:none;font-weight:bold; text-align:center;
}

#nav li {
float: left; width: 6em; height:30px; line-height:30px; margin:0; padding:0;
}
#nav .li5em {
float: left; width: 7em; height:30px; line-height:30px; margin:0; padding:0;
}
#nav .li6em {
float: left; width: 8.5em; height:30px; line-height:30px; margin:0; padding:0;
}
#nav .li8em {
float: left; width: 10em; height:30px; line-height:30px; margin:0; padding:0;
}
/*
#nav li a:hover{
width:6em;  height:30px; line-height:30px; background:url(images/nav_lihover.gif) no-repeat center bottom;
}

#nav .li5em a:hover{
width:7em;  height:30px; line-height:30px; background:url(images/nav_lihover5.gif) no-repeat center bottom;
}#nav .li6em a:hover{
width:8.5em; height:30px; line-height:30px; background:url(images/nav_lihover6.gif) no-repeat center bottom;
}#nav .li8em a:hover{
width:10em;  height:30px; line-height:30px; background:url(images/nav_lihover8.gif) no-repeat center bottom;
}
*/
#nav .on4em {
width:6em; color:#333333;  height:30px; line-height:30px; background:url(images/on4.gif) no-repeat center bottom;
}
#nav .on5em {
width:7em; color:#333333; height:30px; line-height:30px; background:url(images/on5.gif) no-repeat center bottom;
}#nav .on6em {
width:8.5em; color:#333333; height:30px; line-height:30px; background:url(images/on6.gif) no-repeat center bottom;
}#nav .on8em {
width:10em; color:#333333; height:30px; line-height:30px; background:url(images/on8.gif) no-repeat center bottom;
}

#nav .on4em a:link{
width:6em; color:#333333;  height:30px; line-height:30px; background:url(images/on4.gif) no-repeat center bottom;
}
#nav .on4em a:hover{
width:6em; color:#333333;  height:30px; line-height:30px; background:url(images/on4.gif) no-repeat center bottom;
}
#nav .on4em a:visited{
width:6em; color:#333333;  height:30px; line-height:30px; background:url(images/on4.gif) no-repeat center bottom;
}
#nav .on5em a:link{
width:7em; color:#333333; height:30px; line-height:30px; background:url(images/on5.gif) no-repeat center bottom;
}
#nav .on5em a:hover{
width:7em; color:#333333; height:30px; line-height:30px; background:url(images/on5.gif) no-repeat center bottom;
}
#nav .on5em a:visited{
width:7em; color:#333333; height:30px; line-height:30px; background:url(images/on5.gif) no-repeat center bottom;
}
#nav .on6em a:link{
width:8.5em; color:#333333; height:30px; line-height:30px; background:url(images/on6.gif) no-repeat center bottom;
}
#nav .on6em a:hover{
width:8.5em; color:#333333; height:30px; line-height:30px; background:url(images/on6.gif) no-repeat center bottom;
}
#nav .on6em a:visited{
width:8.5em; color:#333333; height:30px; line-height:30px; background:url(images/on6.gif) no-repeat center bottom;
}
#nav .on8em a:link{
width:10em; color:#333333; height:30px; line-height:30px; background:url(images/on8.gif) no-repeat center bottom;
}
#nav .on8em a:hover{
width:10em; color:#333333; height:30px; line-height:30px; background:url(images/on8.gif) no-repeat center bottom;
}
#nav .on8em a:visited{
width:10em; color:#333333; height:30px; line-height:30px; background:url(images/on8.gif) no-repeat center bottom;
}

#nav li a:hover{
width:6em;  height:30px; line-height:30px; background:url(images/nav_lihover.gif) no-repeat center bottom;
}
#nav .li5em a:hover{
width:7em;  height:30px; line-height:30px; background:url(images/nav_lihover5.gif) no-repeat center bottom;
}
#nav .li6em a:hover{
width:8.5em; height:30px; line-height:30px; background:url(images/nav_lihover6.gif) no-repeat center bottom;
}
#nav .li8em a:hover{
width:10em;  height:30px; line-height:30px; background:url(images/nav_lihover8.gif) no-repeat center bottom;
}

/*-------�����˵�----------*/
#nav li ul {
line-height: 20px; list-style-type: none;text-align:left; left: -900em; width: 960px; position:absolute; 
}
#nav li ul li{
float: left; line-height:20px; width:5em; text-align:center;
}
#nav li ul .li2em{
float: left; line-height:20px; width:3em; text-align:center;
}
#nav li ul .li4em{
float: left; line-height:20px; width:5em; text-align:center;
}
#nav li ul .li5em{
float: left; line-height:20px; width:6.2em; text-align:center;
}
#nav li ul .li6em{
float: left; line-height:20px; width:7em; text-align:center;
}
#nav li ul .li7em{
float: left; line-height:20px; width:9em; text-align:center;
}
#nav li ul .li8em{
float: left; line-height:20px; width:10em; text-align:center;
}

#nav li ul a{ 
 line-height:30px; text-decoration:none; width:5em; text-align:center;
}
#nav li ul li a{
display: block; line-height:30px; text-decoration:none; width:5em; text-align:center;
}
#nav li ul .li2em a{
display: block; line-height:30px; text-decoration:none; width:3em; text-align:center; background:none;
}
#nav li ul .li4em a{
display: block; line-height:30px; text-decoration:none; width:5em; text-align:center; background:none;
}
#nav li ul .li5em a{
display: block; line-height:30px; text-decoration:none; width:6.2em; text-align:center; background:none;
}
#nav li ul .li6em a{
display: block; line-height:30px; text-decoration:none; width:7em; text-align:center; background:none;
}
#nav li ul .li7em a{
display: block; line-height:30px; text-decoration:none; width:9em; text-align:center; background:none;
}
#nav li ul .li8em a{
display: block; line-height:30px; text-decoration:none; width:10em; text-align:center; background:none;
}


#nav li ul a:link {
color:#333; text-decoration:none; height:30px; line-height:30px;
}
#nav li ul a:visited {
color:#333;text-decoration:none; height:30px; line-height:30px;
}
#nav li ul  a:hover {color:#f60; height:30px; text-decoration:none; background:#; line-height:30px; width:5em; background:none;
}
#nav li ul .li2em a:hover {color:#f60; height:30px; text-decoration:none; background:#; line-height:30px; width:3em; background:none;
}
#nav li ul .li4em a:hover {color:#f60; height:30px; text-decoration:none; background:#; line-height:30px; width:5em; background:none;
}
#nav li ul .li5em a:hover {color:#f60; height:30px; text-decoration:none; background:#; line-height:30px; width:6.2em; background:none;
}
#nav li ul .li6em a:hover {color:#f60; height:30px; text-decoration:none; background:#; line-height:30px; width:7em; background:none;
}
#nav li ul .li7em a:hover {color:#f60; height:30px; text-decoration:none; background:#; line-height:30px; width:9em; background:none;
}
#nav li ul .li8em a:hover {color:#f60; height:30px; text-decoration:none; background:#; line-height:30px; width:10em; background:none;
}
/*
#nav li:hover ul {
left:-1.4em;
margin-left:0;
}*/
#nav li.sfhover ul {
height:20px;
line-height:20px;
left:2em;
margin:0; padding:0;
}
#content {
clear: left;
}
.table_topNav{font-weight:bold; margin:0; padding:0; width:100%; background:url(images/nav_bg.gif) repeat-x left top;}
.navDiv{width:1001px; height:0px; overflow:hidden;}
-->
</style>
<script type="text/javascript"><!--//--><![CDATA[//><!--
var currentShowLiId = "";
function menuFix()
 {
    //var sfEls = document.getElementById("nav").getElementsByTagName("li");
    var level1Menus = document.getElementById("nav").childNodes;
    
    for(var i=0;i<level1Menus.length; i++)
    {

    }
}

function ShowMenu(menuId)
{
     HideCurrent();
    if(currentShowLiId != menuId)
    {
        currentShowLiId =menuId;       
   }
  
   ShowCurrent();
     
}

function ShowCurrent()
{
      
        var currentShowLi = document.getElementById(currentShowLiId);
          Show(currentShowLi);
}

function HideCurrent()
{
    var currentShowLi = document.getElementById(currentShowLiId);
       Hide(currentShowLi);
}

function Show(element)
{
     if(element !=null)
        {
             var sfEls = element.getElementsByTagName("li");
            for(j=0;j<sfEls.length;j++)
            {
                sfEls[j].className+=(sfEls[j].className.length>0? " ": "") + "sfhover";
                
            }
            
             element.className+=(element.className.length>0? " ": "") + "sfhover";
             element.className = element.className.replace(new RegExp("li"),"on");
         } 
}

function Hide(element)
{
     if(element !=null)
        {
             var sfEls = element.getElementsByTagName("li");
            for(j=0;j<sfEls.length;j++)
            {
                sfEls[j].className=sfEls[j].className.replace(new RegExp("( ?|^)sfhover\\b"),"");
            }
            
            element.className = element.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
            element.className =  element.className.replace(new RegExp("on"),"li");
         } 
}

window.onload=menuFix;

//--><!]]></script>

<script type="text/javascript">
    function FunctionNavigate(url,funcId,sender)
    {
        if(typeof(sender) != "undefined" && sender == "topFrame")
            return;
                  
       
    }
    
    function PublishNavigate(url,funcId)
    {
          // navigate main frame to url
        if(window.parent != null && window.parent["mainFrame"] != null)
        {
         if(typeof(window.parent.FunctionNavigate) != "undefined")
            window.parent.FunctionNavigate(url,funcId,"topFrame");
        }
        else
        {
            window.location.href = url;
        }    
    }

    
    function getTimeNow() {
    var today = new Date();    
    var day = today.getDate();    
    var month = today.getMonth() + 1;    
    var year = today.getYear(); 
    var hour = today.getHours();
    var minute = today.getMinutes();
    var second = today.getSeconds();   
    var date = year + "��" + month + "��" + day + "�� " + hour +"ʱ" +minute + "��" ;  
    document.getElementById("showTime").innerHTML = date;
    //setTimeout("getTimeNow()",1000);
    }

    
</script>
</HEAD>
<%
User user=new User();
user = (User)session.getAttribute("user");
String LogID=request.getParameter("LogID");
LogID=(LogID==null)?"":LogID;
%>
<body onload="getTimeNow()">
<table cellpadding="0" cellspacing="0" class="toptable">
	<tr>
		<td class="leftTd"><img src="images/Logo.jpg" alt="Logo" width="436" ></td>
		<td align="right" class="midTd"><a href="./member/memberDetail.do?iscallcenter=2" target="mainFrame" >��һ��</a>&nbsp;&nbsp;
		<a href="./member/memberDetail.do?iscallcenter=1" target="mainFrame" >����̨</a>&nbsp;&nbsp;
		<span class="fb12"><a href="/userLogonOut.do" target=_top> ע��</a></span><br /><span><div id="showTime"></div></span></td>
		<td class="rightTd"><img src="images/kuser.gif" width="16" height="16" align="absmiddle"><span  class="fb12">��ӭ��,</span> <span class="fb12" id="txtLogonUser"><a onclick="javascript:PublishNavigate('system/UserInfo.htm','');"><%=user.getNAME()%> </a></span> 
		  </td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0" class="table_topNav">
	<tr>
		<td style="width:15px; height:62px; background:url(images/nav_left.gif) no-repeat left top; margin:0; padding:0;">
		</td>
		<td style="width:auto; height:62px; vertical-align:top; margin:0; padding:0;">
			<div style="margin:4px 0 0 0; padding:0; width:100%;">
				<ul id="nav">
					<li id="hygl" class="li5em"><a  href="javascript:ShowMenu('hygl')">��Ա����</a>
						<ul>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/member_addToken.do','template');">������Ա</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/query.do','card');">��Ա��ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/aquery.do','template');">�߼���ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/mbrGetAward.do?type=queryGift','card');">��Ʒ��ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/mbrGetAward.do?type=queryGiftNumber','card');">��ȯ��ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberAddGift.do?type=listAudit','card');">��Ʒ���</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/mbrGetAward.do?type=showExchangePage','card');">���ֶһ�</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/complaintQuery.do','');">��ѯ��ѯͶ��</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberGroup.do?type=initAdd','card');">�����Ź���Ա</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberOrgListinit.do','card');">�Ź���Ա��ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberBlackList.do?type=showAddedPage','card');">����������</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('s_area.do?type=queryPostCode','card');">�ʱ�����</a></li>
						</ul>
						
					</li>
					
					<li  id="ddgl"  class="li5em"><a href="javascript:ShowMenu('ddgl')">��������</a>
						<ul>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderAddFirst.do','cardorder');">��������</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderQuery.do','');">������ѯ</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderAQuery.do?type=init','');">�����߼���ѯ</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/groupOrderAdd.do?type=addFirst','');">�����Ź�����</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/member_organization_query.jsp','');">�Ź�������ѯ</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/snQry.do','');">��������ѯ</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('findPeronalUnfinishedOrders.do','');">δ��ɶ�����ѯ</a></li>
						</ul>
					</li>
					<!--<li  id="zxts"  class="li5em"><a href="javascript:ShowMenu('zxts')">��ѯͶ��</a>
						<ul>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/complaintQuery.do','');">��ѯ��ѯͶ��</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/complain_type_set.jsp','');">Ͷ����������</a></li>
							
						</ul>
					</li>-->
					
					<li  id="cpgl"  class="li5em"><a href="javascript:ShowMenu('cpgl')">��Ʒ����</a>
						<ul>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/initProductAdd.do','');">��������</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productQuery.do','');">���Ų�ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productSKU.do?type=addinit','');">����SKU</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productSKU.do?type=list','');">SKU��ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/product2Set.do?type=init','');">��װ����</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productSKU.do?type=addgiftinit','');">������Ʒ</a></li>
							<!--
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productCategory.do?type=list','');">��Ʒ����</a></li>
							-->
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/product_type_main.jsp','');">��������</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/product_color_list.jsp','cardorder');">��ɫ�ߴ�</a></li>	
						</ul>
					</li>
					<li  id="cxgl"  class="li5em"><a href="javascript:ShowMenu('cxgl')">��������</a>
						<ul>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/Catalog.do?type=init&price_type_id=1','cardorder');">������ļ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/member_active_list.jsp','');">��ļ��ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/mbr_gift_certificates_list.jsp','');">��ȯ����</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/ggcard_add.jsp','');">��ȯ����</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberAddMoneyGiftSetup.do?type=query','');">Ԥ�����ȯ</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/mbr_get_mbr_gift_list.jsp','');">�Ƽ���Ա��ȯ</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/expExchangeActivity.do?type=queryActivity','');">���ֻ����</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/promotionOperation.do?type=query','');">�����������</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/freeDeliveryFee.do?type=list','');">�����ⷢ�ͷ�����</a></li>
						</ul>
					</li>								
					<li  id="mlgl" class="li5em"><a  href="javascript:ShowMenu('mlgl')">Ŀ¼����</a>
						<ul>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/Catalog.do?type=init&price_type_id=3')">����Ŀ¼</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/CatalogList.jsp');">Ŀ¼�б�</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/s_catalog_edition.do?type=list');">Ŀ¼�������</a></li>
							
						</ul>
					</li>
					<li  id="czgl"  class="li5em"><a href="javascript:ShowMenu('czgl')">�ʻ�����</a>
						<ul>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/member_addmoney_file.jsp','cardorder');">����</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberqueryMoney.do','');">����ֵ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberqueryFinanceMoney.do','');">��ֵ��ѯ</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderPay.do?type=init&ref_dept=k','');">�ʻ��ֹ���ֵ</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderPay.do?type=initEmoney','');">����ֹ���ֵ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderPay.do?type=initCrush','');">��ֵ����ֵ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('crmjsp/member_drawback.jsp?doc_type=3320','');">��Ա�˿�</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberMoneyDrawback.do?type=listAudit','');">�˿����</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/crush_card_type_list.jsp','');">��ֵ����������</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/crush_card_lot_list.jsp','');">��ֵ������</a></li>
						</ul>
					</li>
					<li  id="sjfx" class="li5em"><a  href="javascript:ShowMenu('sjfx')">���ݷ���</a>
						<ul>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_prd_detail.jsp','');">��Ʒ������ϸ</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_sku_detail.jsp','');">sku������ϸ</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_prd_category_stats.jsp','');">�������ͳ��</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_sku_daily_stats.jsp','');">��Ʒ������ͳ��</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_goods_short_stats.jsp','');">��Ʒȱ������</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_total_stats1.jsp','');">��Ʒ�����ۻ���1</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_total_stats2.jsp','');">��Ʒ�����ۻ���2</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/msc_stats.jsp','');">MSCЧ��ͳ��</a></li>
						</ul>
					</li>
					<li  id="jcsj" class="li5em"><a  href="javascript:ShowMenu('jcsj')">��������</a>
						<ul>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('s_area.do?type=list','');">ֱ������ά��</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('/crmjsp/config_keys.jsp?doc_type=5060','');">ҵ���������</a></li>
							<!--<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('deliveryFeeSetting.do?type=showAdd','');">���ͷ�����</a></li>-->
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('deliveryFeeSetting.do?type=query&forward=list','');">���ⷢ�ͷ�����</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('defaultDeliveryFee.do?type=showDefaultModify','');">Ĭ�Ϸ��ͷ�����</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/complain_type_set.jsp','');">Ͷ����������</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('s_payment_method.do?type=list','');">���ʽ�ۿ�</a></li>
						</ul>
					</li>
					<li  id="xtgl" class="li5em"><a  href="javascript:ShowMenu('xtgl')">ϵͳ����</a>
						<ul>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('initUserCreate.do','');">�����û�</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('listUser.do','');">�û���ѯ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('initRoleCreate.do','');">���ӽ�ɫ</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('listRole.do','');">��ɫ���</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('pathadd.do','');">Ȩ��·��</a></li>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('updatePWD.do','');">�����޸�</a></li>
						</ul>
					</li>
					
					
				</ul>
			</div>
		</td>
		<td style="width:15px; height:62px; background:url(images/nav_right.gif) no-repeat right top;">
		</td>
	</tr>
</table>
</body>
</html>
