/*
 * Created on 2005-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;
import java.sql.*;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StringDataSource {
	public String getContent(Connection conn,int orderID,int type){
		String content="";
        switch(type)
        { 
        	case 1:
            content="<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.0 Transitional//EN'>";

            	content+="<html>";
            	content+="<head>";
            	content+="<title>[letter_title]</title>";
            	content+="<meta http-equiv=Content-Type content='text/html; charset=GBK'>";
            	content+="<style type='text/css'>";
            	content+=".iconwenzi {background-position: center 50%; font-size: 12px}</style>";
            	content+="<style type='text/css'>.biaotiwenzi {font-size: 14px}</style>";
            	content+="<style type=text/css>";
            	 content+=".biaokekuangjia {border-right: 1px #000000; border-top: 1px solid #000000; font-size: 12px; border-left: 1px #000000; border-bottom: 1px none #000000}";
            	 content+="a:link {color:#000000;text-decoration: none;}";
            	 content+="a:visited {color:#000000;text-decoration: none;}";
            	 content+="a:active {color:#000000;text-decoration: none;}";
            	 content+="a:hover {color:#000000;text-decoration: underline;}</style>";
            	content+="<meta content='MSHTML 6.00.2800.1458' name=GENERATOR></head>";
            	content+="<body topmargin='0' leftmargin='0'>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td valign=top width=198 rowspan=6><img border=0 src='c:/template/images/99logo.jpg'></td>";
            	    content+="<td valign=top width=17 rowspan=7>&nbsp;</td>";
            	    content+="<td width=27 height=6></td>";
            	    content+="<td width=28></td>";
            	    content+="<td width=46></td>";
            	    content+="<td width=12></td>";
            	    content+="<td width=15></td>";
            	    content+="<td width=64></td>";
            	    content+="<td width=10></td>";
            	    content+="<td width=27></td>";
            	    content+="<td width=55></td>";
            	    content+="<td width=19></td>";
            	    content+="<td width=27></td>";
            	    content+="<td width=32></td>";
            	    content+="<td width=42></td>";
            	    content+="<td width=27></td>";
            	    content+="<td width=10></td>";
            	    content+="<td width=64></td>";
            	    content+="<td width=60></td></tr>";
            	  content+="<tr>";
            	    content+="<td valign=top rowspan=2><img border=0 src='c:/template/images/icon2.gif'></td>";
            	    content+="<td height=2></td>";
            	    content+="<td></td>";
            	    content+="<td valign=top colspan=2 rowspan=2><img border=0 src='c:/template/images/icon3.gif'></td>";
            	    content+="<td></td>";
            	    content+="<td></td>";
            	    content+="<td valign=top rowspan=2><img border=0 src='c:/template/images/icon4.gif'></td>";
            	    content+="<td></td>";
            	    content+="<td></td>";
            	    content+="<td valign=top rowspan=2><img border=0 src='c:/template/images/icon5.gif'></td>";
            	    content+="<td></td>";
            	    content+="<td></td>";
            	    content+="<td valign=top rowspan=2><img border=0 src='c:/template/images/icon6.gif'></td>";
            	    content+="<td></td>";
            	    content+="<td></td>";
            	    content+="<td></td></tr>";
            	  content+="<tr>";
            	    content+="<td class=iconwenzi valign=center colspan=2 rowspan=2><a href='http://www.99read.com/memb/membcf01.asp' target='_blank'>注册/登录</a></td>";
            	    content+="<td class=iconwenzi valign=center colspan=2 rowspan=2><a href='http://www.99read.com/ebuy/gdspick.asp?action=1' target='_blank'>购物车</a></td>";
            	    content+="<td class=iconwenzi valign=center colspan=2 rowspan=2><a href='http://www.99read.com/my99/my99.asp' target='_blank'>我的管家</a></td>";
            	    content+="<td class=iconwenzi valign=center colspan=2 rowspan=2><a href='http://www.99read.com/demo/demobuy.asp' target='_blank'>购物流程</a></td>";
            	    content+="<td class=iconwenzi valign=center colspan=2 rowspan=2><a href='http://www.99read.com/webhelp/helpindex.asp' target='_blank'>帮助中心</a></td>";
            	    content+="<td height=25>&nbsp;</td></tr>";
            	  content+="<tr>";
            	    content+="<td height=2></td>";
            	    content+="<td></td>";
            	    content+="<td></td>";
            	    content+="<td></td>";
            	    content+="<td></td>";
            	    content+="<td></td>";
            	    content+="<td></td></tr>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center colspan=17 height=25><font color=#ff0000>[letter_title]</font></td> </tr>";
            	  content+="<tr>";
            	    content+="<td valign=top colspan=2 rowspan=2><a href='http://www.99read.com' target='_blank'><img border=0 src='c:/template/images/sh1.gif'></a></td>";
            	    content+="<td valign=top colspan=2 rowspan=2><a href='http://www.99read.com/book/book.asp' target='_blank'><img border=0 src='c:/template/images/sh2.gif'></a></td>";
            	    content+="<td valign=top colspan=2 rowspan=2><a href='http://www.99read.com/videogame/video.asp' target='_blank'><img border=0 src='c:/template/images/sh3.gif'></a></td>";
            	    content+="<td valign=top colspan=3 rowspan=2><a href='http://www.99read.com/fiction.asp' target='_blank'><img border=0 src='c:/template/images/sh4.gif'></a></td>";
            	    content+="<td valign=top colspan=3 rowspan=2><a href='http://www.99read.com/pop/nine.asp?types=6' target='_blank'><img border=0 src='c:/template/images/sh5.gif'></a></td>";
            	    content+="<td valign=top colspan=3 rowspan=2><a href='http://www.99read.com/wenxue/wxindex.asp' target='_blank'><img border=0 src='c:/template/images/sh6.gif'></a></td>";
            	    content+="<td height=4></td>";
            	    content+="<td></td></tr>";
            	  content+="<tr>";
            	    content+="<td valign=top height=22><img border=0 src='c:/template/images/renxian.gif'></td>";
            	    content+="<td>&nbsp;</td>";
            	    content+="<td></td></tr> </tbody></table>";
            	content+="<table height=5 cellSpacing='0' cellPadding='0' width='780' border='0' align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td width='780' bgColor='#ffcc33' height='1'></td></tr></tbody></table>";
            	content+="<table cellSpacing='0' cellPadding='0' width='780' border='0' align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td valign=top width=780 height=16>&nbsp;</td></tr>";
            	  content+="<tr>";
            	    content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>亲爱的会员</strong></font><strong>&nbsp;[member_name]</strong>：</span></p><p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>首先，非常感谢您在99网上书城购物！您可以随时在<a href='http://www.99read.com/my99/my99.asp' target='_blank'><font color='#0000FF'>我的管家</font></a>中查询到您的订单状态和产品配送信息。</span><br></p></td></tr>";
            	  content+="<tr>";
            	    content+="<td height=19>&nbsp;</td> </tr></tbody></table>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center width=780 height=24><strong>您的详细订单信息:</strong></td></tr></tbody></table>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td valign=center bgColor=#993333 colspan=3 rowspan=2><span color=#ffffff><strong>&nbsp;&nbsp;(订单号：[order_code]) </strong></font></span></td>";
            	    content+="<td colspan=2 height=18></td></tr>";
            	  content+="<tr>";
            	    content+="<td bgColor=#993333 colspan=2 height=2></td></tr>";
            	  content+="<tr>";
            	    content+="<td valign=top width=8 rowspan=3>&nbsp;</td>";
            	    content+="<td valign=center width=61 height=30><span class=biaotiwenzi>姓&nbsp;&nbsp;名:</span><br></td>";
            	    content+="<td class=biaotiwenzi valign=center  width=158><strong>[member_name]</strong></td>";
            	    content+="<td class=biaotiwenzi valign=center width=61>E_mail:</td>";
            	    content+="<td class=biaotiwenzi valign=center width=492>[member_email]</td></tr>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center height=30>邮&nbsp;&nbsp;编:</td>";
            	    content+="<td class=biaotiwenzi valign=center>[post_code]</td>";
            	    content+="<td class=biaotiwenzi valign=center>地&nbsp;&nbsp;址:</td>";
            	    content+="<td class=biaotiwenzi valign=center>[member_addr]</td></tr>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center height=30>电&nbsp;&nbsp;话:</td>";
            	    content+="<td class=biaotiwenzi valign=center>[member_phone]</td>";
            	    content+="<td></td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr valign=center>";
            	    content+="<td valign=center bgColor=#993333 colspan=3 height=20 rowspan=2><span class=iconwenzi><fonr color=#ffffff><strong>&nbsp;&nbsp;商品信息 </strong></font></span></td>";
            	    content+="<td valign=top colspan=2 height=18></td></tr>";
            	  content+="<tr valign=center>";
            	    content+="<td valign=top bgColor=#993333 colspan=2 height=2></td></tr></tbody></table>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	    content+="<tr class=biaokekuangjia> ";
            	      content+="<td width=10 height=20>&nbsp;</td>";
            	      content+="<td width=382 valign=center><div class=iconwenzi align=left><strong>商品名称</strong></div></td>";
            	      content+="<td valign=center width=66><div align=center><strong>数量</strong></div></td>";
            	      content+="<td valign=center width=161><div align=center> ";
            	          content+="<p class=iconwenzi><strong>金额(元)</strong></p></div></td>";
            	      content+="<td valign=center width=161> <div align=center><strong>小计(元)</strong></div></td></tr></tbody></table>";
            	content+="<table width=780 border=0 cellPadding=0 cellSpacing=0 class='biaokekuangjia' align='center'>";
            	  content+="<tbody>";
            	     content+="[order_detail]";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>小计</div></td>";
            	      content+="<td valign='middle'><div align='center'>[sum_quantity]</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>[amount]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>发送费</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>[delivery_fee]</div></td> </tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>会员卡工本费</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>[card_fee]</div></td></tr>";
            		 content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>使用预付款</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>[deposit]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>礼券抵用</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>---</div></td>";
            	      content+="<td valign='middle'><div align='center'>[append_fee]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20 bgcolor='#CCCCCC'>&nbsp;</td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	      content+="<div align='left'>总计（应付款）</div></td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	        content+="<div align='center'>---</div></td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	      content+="<div align='center'>---</div></td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	        content+="<div align='center'>[total]</div></td></tr>";
            	    content+="<tr> ";
            	      content+="<td height=6 colspan='4' valign='top' bgcolor='#993333'></td>";
            	      content+="<td valign='top' bgcolor='#993333'></td></tr></tbody></table>";

            	content+="<table width='780' border='0' cellpadding='0' cellspacing='0' align='center'>";
            	  content+="<tr> ";
            	    content+="<td width='597' height='16' valign='top'>&nbsp;</td>";
            	    content+="<td width='183'>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='22' valign='top' class='biaotiwenzi'><strong>您诚挚的</strong><strong><font color='#a50000'>99网上书城 ";
            	       content+="客户服务部</font></strong></td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>如果您还有问题，请点击<a href='http://www.99read.com/webhelp/helpindex.asp' target='_blank'><fonr color='#0000FF'>帮助中心</font></a>查看常见问题的解答，或者与我们的客户服务中心联系</td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>E-mail:service@99read.com ";
            	     content+="  | 服务热线电话:021-34014699 | 服务中心传真:021-54960899</td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>来函地址: 上海市市南邮政032-99信箱，邮编: ";
            	      content+=" 200032</td>";
            	    content+="<td></td></tr></table></body></html>";

            break;
        }
		return content;
	}
} 

