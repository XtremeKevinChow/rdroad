<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*,com.magic.crm.user.entity.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>导航菜单</title>
<style type="text/css">
<!--
*{border:0; font-size:12px;}
body {
margin:0 10px; padding:0;
font-family: arial, 宋体, serif;
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
/*------------------一级菜单-------------------------*/

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

/*-------二级菜单----------*/
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
    var date = year + "年" + month + "月" + day + "日 " + hour +"时" +minute + "分" ;  
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
		<td align="right" class="midTd"><a href="./member/memberDetail.do?iscallcenter=2" target="mainFrame" >上一个</a>&nbsp;&nbsp;
		<a href="./member/memberDetail.do?iscallcenter=1" target="mainFrame" >工作台</a>&nbsp;&nbsp;
		<span class="fb12"><a href="/userLogonOut.do" target=_top> 注销</a></span><br /><span><div id="showTime"></div></span></td>
		<td class="rightTd"><img src="images/kuser.gif" width="16" height="16" align="absmiddle"><span  class="fb12">欢迎您,</span> <span class="fb12" id="txtLogonUser"><a onclick="javascript:PublishNavigate('system/UserInfo.htm','');"><%=user.getNAME()%> </a></span> 
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
					<li id="hygl" class="li5em"><a  href="javascript:ShowMenu('hygl')">会员管理</a>
						<ul>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/member_addToken.do','template');">新增会员</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/query.do','card');">会员查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/aquery.do','template');">高级查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/mbrGetAward.do?type=queryGift','card');">礼品查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/mbrGetAward.do?type=queryGiftNumber','card');">礼券查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberAddGift.do?type=listAudit','card');">礼品审核</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/mbrGetAward.do?type=showExchangePage','card');">积分兑换</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/complaintQuery.do','');">查询咨询投诉</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberGroup.do?type=initAdd','card');">新增团购会员</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberOrgListinit.do','card');">团购会员查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberBlackList.do?type=showAddedPage','card');">黑名单管理</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('s_area.do?type=queryPostCode','card');">邮编查地区</a></li>
						</ul>
						
					</li>
					
					<li  id="ddgl"  class="li5em"><a href="javascript:ShowMenu('ddgl')">订单管理</a>
						<ul>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderAddFirst.do','cardorder');">新增订单</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderQuery.do','');">订单查询</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderAQuery.do?type=init','');">订单高级查询</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/groupOrderAdd.do?type=addFirst','');">新增团购订单</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/member_organization_query.jsp','');">团购订单查询</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/snQry.do','');">发货单查询</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('findPeronalUnfinishedOrders.do','');">未完成订单查询</a></li>
						</ul>
					</li>
					<!--<li  id="zxts"  class="li5em"><a href="javascript:ShowMenu('zxts')">咨询投诉</a>
						<ul>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/complaintQuery.do','');">查询咨询投诉</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/complain_type_set.jsp','');">投诉类型设置</a></li>
							
						</ul>
					</li>-->
					
					<li  id="cpgl"  class="li5em"><a href="javascript:ShowMenu('cpgl')">产品管理</a>
						<ul>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/initProductAdd.do','');">新增货号</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productQuery.do','');">货号查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productSKU.do?type=addinit','');">新增SKU</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productSKU.do?type=list','');">SKU查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/product2Set.do?type=init','');">套装设置</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productSKU.do?type=addgiftinit','');">新增赠品</a></li>
							<!--
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/productCategory.do?type=list','');">产品分类</a></li>
							-->
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/product_type_main.jsp','');">分类设置</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('product/product_color_list.jsp','cardorder');">颜色尺寸</a></li>	
						</ul>
					</li>
					<li  id="cxgl"  class="li5em"><a href="javascript:ShowMenu('cxgl')">促销管理</a>
						<ul>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/Catalog.do?type=init&price_type_id=1','cardorder');">新增招募</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/member_active_list.jsp','');">招募查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/mbr_gift_certificates_list.jsp','');">礼券设置</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/ggcard_add.jsp','');">礼券制作</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberAddMoneyGiftSetup.do?type=query','');">预存款礼券</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/mbr_get_mbr_gift_list.jsp','');">推荐会员礼券</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/expExchangeActivity.do?type=queryActivity','');">积分活动设置</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/promotionOperation.do?type=query','');">购物促销设置</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/freeDeliveryFee.do?type=list','');">购物免发送费设置</a></li>
						</ul>
					</li>								
					<li  id="mlgl" class="li5em"><a  href="javascript:ShowMenu('mlgl')">目录管理</a>
						<ul>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/Catalog.do?type=init&price_type_id=3')">新增目录</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/CatalogList.jsp');">目录列表</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/s_catalog_edition.do?type=list');">目录版块设置</a></li>
							
						</ul>
					</li>
					<li  id="czgl"  class="li5em"><a href="javascript:ShowMenu('czgl')">帐户管理</a>
						<ul>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/member_addmoney_file.jsp','cardorder');">汇款导入</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberqueryMoney.do','');">汇款充值</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberqueryFinanceMoney.do','');">充值查询</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderPay.do?type=init&ref_dept=k','');">帐户手工充值</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderPay.do?type=initEmoney','');">礼金手工充值</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('order/orderPay.do?type=initCrush','');">充值卡充值</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('crmjsp/member_drawback.jsp?doc_type=3320','');">会员退款</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/memberMoneyDrawback.do?type=listAudit','');">退款审核</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/crush_card_type_list.jsp','');">充值卡类型设置</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('promotion/crush_card_lot_list.jsp','');">充值卡制作</a></li>
						</ul>
					</li>
					<li  id="sjfx" class="li5em"><a  href="javascript:ShowMenu('sjfx')">数据分析</a>
						<ul>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_prd_detail.jsp','');">产品销售明细</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_sku_detail.jsp','');">sku销售明细</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_prd_category_stats.jsp','');">类别销售统计</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_sku_daily_stats.jsp','');">产品日销售统计</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_goods_short_stats.jsp','');">商品缺货排行</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_total_stats1.jsp','');">产品日销售汇总1</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/sale_total_stats2.jsp','');">产品日销售汇总2</a></li>
						<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('report/msc_stats.jsp','');">MSC效果统计</a></li>
						</ul>
					</li>
					<li  id="jcsj" class="li5em"><a  href="javascript:ShowMenu('jcsj')">基础数据</a>
						<ul>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('s_area.do?type=list','');">直送区域维护</a></li>
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('/crmjsp/config_keys.jsp?doc_type=5060','');">业务参数设置</a></li>
							<!--<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('deliveryFeeSetting.do?type=showAdd','');">发送费新增</a></li>-->
							<li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('deliveryFeeSetting.do?type=query&forward=list','');">特殊发送费配置</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('defaultDeliveryFee.do?type=showDefaultModify','');">默认发送费配置</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('member/complain_type_set.jsp','');">投诉类型设置</a></li>
						    <li class="li7em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('s_payment_method.do?type=list','');">付款方式折扣</a></li>
						</ul>
					</li>
					<li  id="xtgl" class="li5em"><a  href="javascript:ShowMenu('xtgl')">系统管理</a>
						<ul>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('initUserCreate.do','');">增加用户</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('listUser.do','');">用户查询</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('initRoleCreate.do','');">增加角色</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('listRole.do','');">角色浏览</a></li>
							<li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('pathadd.do','');">权限路径</a></li>
						    <li class="li5em" onmouseover="this.style.color='#f60';" onmouseout="this.style.color='#333';"><a  onclick="javascript:PublishNavigate('updatePWD.do','');">密码修改</a></li>
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
