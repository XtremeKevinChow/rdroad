
package com.magic.crm.util;
import java.sql.*;

import javax.servlet.ServletException;

import com.magic.crm.user.dao.*;
import com.magic.crm.user.entity.*;



/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MailHtmlSource {
	public MailHtmlSource(){}
	/*
	 * 订单确认信 1
	 * 催款通知 2
	 * 订单缺货通知 3
	 * 订单缺款通知 4
	 * 订单发货通知 5
	 */
	public String getContent(Connection conn,int orderID,int type){
    String sql="";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
		String content="";	


		/*
		 *  得到会员的基本信息
		 */
       	String name="";
       	String email="";
       	String member_add="";
       	String post_code="";
       	String member_phone="";
       	String so_number="";
       	int sum_quantity=0;
       	double delivery_fee=0;//发送费
       	double deposit=0; //订单预付款
       	double append_fee=0;//礼券抵用
       	double order_sum=0;//订单总额
       	double member_deposit=0;//会员预付款
       	double emoney=0;//e元
       	int amount_exp=0;//年度积分
       	int exp=0;//积分
       	try{
       	sql="select a.delivery_fee,a.append_fee,a.phone,a.address,a.postcode,a.so_number,a.payed_money," +
       			"a.order_sum,b.name,b.email,b.AMOUNT_EXP,b.EXP,b.deposit,b.emoney from ord_headers a,mbr_members b where a.buyer_id=b.id and a.id="+orderID;
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			name=rs.getString("name");
			email=rs.getString("email");
			member_add=rs.getString("address");
			post_code=rs.getString("postcode");
			member_phone=rs.getString("phone");
			delivery_fee=rs.getDouble("delivery_fee");
			deposit=rs.getDouble("payed_money");
			append_fee=rs.getDouble("append_fee");
			so_number=rs.getString("so_number");
			order_sum=rs.getDouble("order_sum");
	       	member_deposit=rs.getDouble("deposit");
	       	emoney=rs.getDouble("emoney");
	       	amount_exp=rs.getInt("amount_exp");
	       	exp=rs.getInt("exp");
			
		}
       	}catch(SQLException e) {
       		e.printStackTrace();
       	}finally {
            if (rs != null)
                try { rs.close(); } catch (Exception e) {}
             if (pstmt != null)
                try { pstmt.close(); } catch (Exception e) {}
        }
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

            	    //******************************************************************
        	content+="<link href='http://www.99read.com/readstyle.css' rel='stylesheet' type='text/css'>";
        	content+="<table width='780' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>";
        	content+="<tr>";                       
        	content+="<td width='188' height='16'><img src='http://www.99read.com/images/top/99logo.jpg' width='188' height='64'></td>";
        	content+="<td width='592'>";
        	content+="<table width='592' height='61' border='0' cellpadding='0' cellspacing='0'>";
        	content+="<tr>"; 
        	content+="<td width='592' height='44' valign='top'>";
        	content+="<table width='100%' border='0' cellpadding='0' cellspacing='0'>";
        	content+="<tr>"; 
        	content+="<td width='57' align='right'><img src='http://www.99read.com/images/top/icon3.gif' width='27' height='27'></td>";
        	content+="<td width='68'><a href='http://www.99read.com/memb/membcf01.asp'><font color='#000000'>注册/登录</font></a></td>";
        	content+="<td width='27'><img src='http://www.99read.com/images/top/icon2.gif' width='27' height='27'></td>";
        	content+="<td width='60'><a href='http://www.99read.com/ebuy/gdspick.asp?action=1'><font color='#000000'>购物车</font></a></td>";
        	content+="<td width='27'><img src='http://www.99read.com/images/top/icon5.gif' width='27' height='27'></td>";
        	content+="<td width='68'><a href='http://www.99read.com/my99/my99.asp'><font color='#000000'>我的管家</font></a></td>";
        	content+="<td width='27'><img src='http://www.99read.com/images/top/icon4.gif' width='27' height='27'></td>";
        	content+="<td width='68'><a href='#' onClick='javascript:OpenWin2();'><font color='#000000'>购物流程</font></a></td>";
        	content+="<td width='27'><img src='http://www.99read.com/images/top/icon6.gif' width='27' height='27'></td>";
        	content+="<td width='68'><a href='http://www.99read.com/webhelp/helpindex.asp'><font color='#000000'>帮助中心</font></a></td>";
        	content+="<td width='27' height='40'><img src='http://www.99read.com/images/top/icon7.gif' width='27' height='27' border='0'></td>";
        	content+="<td><a href='javascript:void(window.open('http://www.biztong.com/guest/GuestLogin.aspx?managerID=459','_blank','width=328,height=300'));'><font color='#000000'>在线客服</font></a></td>";
        	content+="</tr></table></td></tr>";
        	content+="<tr>";       
        	content+="<td height='17'><table width='92%' border='0' align='center' cellpadding='0' cellspacing='0'>";
        	content+="<tr>";
        	content+="<td><font color='#666666'></font></td>";
        	content+="</tr>";
        	content+="</table></td>";
        	content+="</tr>";
        	content+="</table>";
        	content+="</td>";
        	content+="</tr>";
        	content+="</table>";
        	content+="<table width='780' border='0' cellspacing='0' cellpadding='0' align='center'>";
        	content+="<tr>"; 
        	content+="<td width='120' height='25' align='center'><img src='http://www.99read.com/images/renxian.gif' width='120' height='22' border='0'></td>";
        	content+="<td width='660'><table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        	content+="<tr>";
        	content+="<td width='57' align='right'><a href='http://www.99read.com/99index.asp'><img src='http://www.99read.com/images/top/sh1.gif' width='55' height='25' border='0'></a></td>";
        	content+="<td width='58'><a href='http://www.99read.com/book/book.asp'><img src='http://www.99read.com/images/top/sh2.gif' width='58' height='25' border='0'></a></td>";
        	content+="<td width='53'><a href='http://www.99read.com/music/music.asp'><img src='http://www.99read.com/images/top/music_1.gif' width='53' height='25' border='0'></a></td>";
        	content+="<td width='53'><a href='http://www.99read.com/video/video.asp'><img src='http://www.99read.com/images/top/movie_1.gif' width='53' height='25' border='0'></a></td>";
        	content+="<td width='53'><a href='http://www.99read.com/99gift/gift.asp'><img src='http://www.99read.com/images/top/gift.gif' width='53' height='25' border='0'></a></td>";
        	content+="<td width='92'><a href='http://www.99read.com/fiction/fiction.asp'><img src='http://www.99read.com/images/top/sh4.gif' width='92' height='25' border='0'></a></td>";
        	content+="<td width='78'><a href='http://www.99read.com/99yuan.asp?types=6'><img src='http://www.99read.com/images/top/sh5.gif' width='78' height='25' border='0'></a></td>";
        	content+="<td width='79'><a href='http://www.99read.com/wenxue/wxindex.asp'><img src='http://www.99read.com/images/top/sh6.gif' width='79' height='25' border='0'></a></td>";
        	content+="<td width='58'><a href='http://bbs.99read.com' target='_blank'><img src='http://www.99read.com/images/top/luntan.gif' width='58' height='25' border='0'></a></td>";
        	content+="<td width='79'><a href='http://99read.topstudy.com.cn/tongji/tongji.asp?url=http://99read.topstudy.com.cn/&id1=t105' target='_blank'><img src='http://www.99read.com/images/navigation2.gif' width='79' height='25' border='0'></a></td>";
        	content+="</tr>";
        	content+="</table></td>";
        	content+="</tr>";
        	content+="</table>";    
        	
            	content+="<table cellSpacing='0' cellPadding='0' width='780' border='0' align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td valign=top width=780 height=16>&nbsp;</td></tr>";
            /** 订单确认 **/	    
            if(type==1){
            	  content+="<tr>";
            	  content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>亲爱的会员</strong></font><strong>&nbsp;["+name+"]</strong>：</span></p>" +
            	    		"<p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
            	    		"<span class=biaotiwenzi>首先，非常感谢您在99网上书城购物！您可以随时在<a href='http://www.99read.com/my99/my99.asp' target='_blank'>" +
            	    		"<font color='#0000FF'>我的管家</font></a>中查询到您的订单状态和产品配送信息。</span><br></p></td></tr>";
            	  content+="<tr>";
            }
            /** 催款通知 **/
            if(type==2){
            	 content+="<tr>";
            	 content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>亲爱的会员</strong></font><strong>&nbsp;["+name+"]</strong>：</span></p><p>";
            	 content+="<span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>您好！";
            	 content+="非常感谢您订购九久的图书，由于您的支持才让我们能有今天的成绩。";
            	 content+="这里需要和您确认的是您的订单：["+so_number+"]尚有￥["+(order_sum-deposit)+"]未付款,";
            	 content+="总计应付款为￥["+order_sum+"]元。";            	 
            	 content+="</td>";
            	 content+=" </tr>";
          }     
            /** 订单缺货通知 **/
            if(type==3){
            	  content+="<tr>";
            	  content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>亲爱的会员</strong></font><strong>&nbsp;["+name+"]</strong>：</span></p><p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>您好！";
            	  content+="<br>非常感谢您在九久购物！您的订单号：["+so_number+"],但是您所订购的书（或者物品）由于比较畅销，导致缺货，所以您的如下订单已经被取消！";
            	  content+=" </td>";
            	  content+="</tr>";
          }        
            /** 订单缺款通知 **/
            if(type==4){
            	  content+="<tr>";
            	  content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>亲爱的会员</strong></font><strong>&nbsp;["+name+"]</strong>：</span></p><p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>您好！";
            	  content+="非常感谢您在九久购物！您的订单号：["+so_number+"],但是我们在您下订单开始的15天以内，未能收到您的全部购物款项，可能您已无意此笔订单，所以您的如下订单已经被取消！";
            	  content+="</td>";
            	  content+="</tr>";
          }   
            /** 订单发货通知 **/
            if(type==5){
            	    content+="<tr>";
            	    content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>亲爱的会员</strong></font><strong>&nbsp;["+name+"]</strong>：</span></p>" +
            	    		"<p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>您的订单已发货，请注意接收。<br>&nbsp;&nbsp;&nbsp;" +
            	    		"您可以随时在<a href='http://www.99read.com/my99/my99.asp' target='_blank'><font color='#0000FF'>我的管家</font></a>中查询到您的订单状态和产品配送信息。</span><br></p></td>";
            	    content+="</tr>";
          }             
            	    content+="<td height=19>&nbsp;</td> </tr></tbody></table>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center width=780 height=24><strong>您的详细订单信息:</strong></td></tr></tbody></table>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td valign=center bgColor=#993333 colspan=3 rowspan=2><span class=iconwenzi><font color=#ffffff><strong>(订单号：["+so_number+"]) </strong></font></span></td>";
            	    content+="<td colspan=2 height=18></td></tr>";
            	  content+="<tr>";
            	    content+="<td bgColor=#993333 colspan=2 height=2></td></tr>";
            	  content+="<tr>";
            	    content+="<td valign=top width=8 rowspan=3>&nbsp;</td>";
            	    content+="<td valign=center width=61 height=30><span class=biaotiwenzi>姓&nbsp;&nbsp;名:</span><br></td>";
            	    content+="<td class=biaotiwenzi valign=center  width=158>["+name+"]</td>";
            	    content+="<td class=biaotiwenzi valign=center width=61>E_mail:</td>";
            	    content+="<td class=biaotiwenzi valign=center width=492>["+email+"]</td></tr>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center height=30>邮&nbsp;&nbsp;编:</td>";
            	    content+="<td class=biaotiwenzi valign=center>["+post_code+"]</td>";
            	    content+="<td class=biaotiwenzi valign=center>地&nbsp;&nbsp;址:</td>";
            	    content+="<td class=biaotiwenzi valign=center>["+member_add+"]</td></tr>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center height=30>电&nbsp;&nbsp;话:</td>";
            	    content+="<td class=biaotiwenzi valign=center>["+member_phone+"]</td>";
            	    content+="<td></td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr valign=center>";
            	    content+="<td valign=center bgColor=#993333 colspan=3 height=20 rowspan=2><span class=iconwenzi><font color=#ffffff><strong>商品信息 </strong></font></span></td>";
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
            	      content+="<td valign=center width=161> <div align=center><strong>小计(元)</strong></div></td></tr>";
            	      int a=0;
            	      double b=0;
                      try{
                    	  sql="select a.quantity,a.price,b.name from ord_lines a,prd_items b where  a.item_id=b.item_id and a.order_id="+orderID;
          ;
                    	  pstmt=conn.prepareStatement(sql);
                    	  rs=pstmt.executeQuery();
                    	  
                    	  while(rs.next()){
                    	  	a+=rs.getInt("quantity");
                    	  	b+=rs.getInt("quantity")*rs.getDouble("price");
                    	    content+="<tr class=biaokekuangjia> ";
                  	      content+="<td width=10 height=20>&nbsp;</td>";
                  	      content+="<td width=382 valign=center><div class=iconwenzi align=left>"+rs.getString("name")+"</div></td>";
                  	      content+="<td valign=center width=66><div align=center>"+rs.getInt("quantity")+"</div></td>";
                  	      content+="<td valign=center width=161><div align=center> ";
                  	          content+="<p class=iconwenzi>"+rs.getDouble("price")+"</p></div></td>";
                  	      content+="<td valign=center width=161> <div align=center>"+rs.getInt("quantity")*rs.getDouble("price")+"(元)</div></td></tr>";
                  	      
                      	}
                    	  
                     	}catch(SQLException e) {
                     		e.printStackTrace();
                     	}finally {
                          if (rs != null)
                              try { rs.close(); } catch (Exception e) {}
                           if (pstmt != null)
                              try { pstmt.close(); } catch (Exception e) {}
                      }            	      
            	      content+="</tbody></table>";
            	content+="<table width=780 border=0 cellPadding=0 cellSpacing=0 class='biaokekuangjia' align='center'>";
            	  content+="<tbody>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>小计</div></td>";
            	      content+="<td valign='middle' width='66'><div align='center'>["+a+"]</div></td>";
            	      content+="<td valign='middle' width=161><div align='center'></div></td>";
            	      content+="<td valign='middle' width=161><div align='center'>["+b+"]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>发送费</div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'>["+delivery_fee+"]</div></td> </tr>";
            		 content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>使用预付款</div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'>["+deposit+"]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>礼券抵用</div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'>["+append_fee+"]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20 bgcolor='#CCCCCC'>&nbsp;</td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	      content+="<div align='left'>总计（应付款）</div></td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	        content+="<div align='center'></div></td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	      content+="<div align='center'></div></td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	        content+="<div align='center'>["+(b+append_fee-deposit+delivery_fee)+"]</div></td></tr>";
            	    content+="<tr> ";
            	      content+="<td height=6 colspan='4' valign='top' bgcolor='#993333'></td>";
            	      content+="<td valign='top' bgcolor='#993333'></td></tr></tbody></table>";

	      if(type!=1){
	      	content+="<br>";
	      	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
	      	  content+="<tbody>";
	      	  content+="<tr>";
	      	    content+="<td class=biaotiwenzi valign=center width=780  height=24><strong>您目前的帐户信息:</strong></td></tr></tbody></table>";
	      	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
	      	  content+="<tbody>";
	      	content+="<tr>";
	      	    content+="<td valign=top width=8 rowspan=3>&nbsp;</td>";
	      	    content+="<td valign=center width=80 height=30><span class=biaotiwenzi>预付款余额:</span><br></td>";
	      	    content+="<td class=biaotiwenzi valign=center  width=158>["+member_deposit+"]</td>";
	      	    content+="<td class=biaotiwenzi valign=center width=80>e元:</td>";
	      	    content+="<td class=biaotiwenzi valign=center width=158>["+emoney+"]</td></tr>";

	      	  content+="<tr>";
	      	    content+="<td class=biaotiwenzi valign=center height=30>积分:</td>";
	      	    content+="<td class=biaotiwenzi valign=center>["+exp+"]</td>";
	      	    content+="<td class=biaotiwenzi valign=center height=30>年度积分:</td>";
	      	    content+="<td class=biaotiwenzi valign=center>["+amount_exp+"]</td>";
	      	  content+="</tr>";
	      	  content+="</tbody>";
	      	content+="</table>";
	      }

	      	content+="<table cellSpacing='0' cellPadding='0' width='780' border='0' align='center'>";
	      	  content+="<tbody>";
	      	  content+="<tr>";
	      	    content+="<td valign=top width=780 height=16>&nbsp;</td>";
	      	  content+="</tr>";
		      /** 催款通知 **/
	 	     if(type==2){	      	  
		      	  content+="<tr>";
		      	    content+="<td valign=top ><span class=biaotiwenzi>";	
		      	    content+="<br>也许是您繁忙的工作或学习要处理而将此事遗忘，我们表示体谅。但同时希望您在收到此封信时，能抽出您的宝贵时间在二周内将未付款补齐。";		      	 
		      	    content+="<br> 您可采取以下方式：";
		      	    content+="<br>邮局汇款：收款人地址：03299（上海市南) &nbsp;&nbsp; 收款人姓名：九久 &nbsp;&nbsp; 邮编：200032&nbsp;&nbsp;请在汇款人附言栏中注明您的会员号";
		      	    content+="<br>网上支付：请参考网上支付方式,同时请您来电或以电子邮件的方式告知我们您的会员号码。";
			      	content+="<br>如果您在收到此信的同时已经汇款，可不必理会此信。对此信给您的打扰我们表示歉意。如果您觉得帐户有误以及有任何疑问，请速与我们联系，以便我们共同查清，为您提供更好的服务。";
			      	content+="</span></td>";
			      	content+="</tr>";
	 	    }
		     /** 订单缺货通知 **/
		     if(type==3){
		     	    content+="<tr>";
		     	    content+="<td valign=top height=52><p><span class=biaotiwenzi>";
			     	content+="以上产品不再发送，给您带来的不便，我们深感抱歉！麻烦您通过其他方式告知我们您新的需求，我们将最大限度地满足您的要求！　";
			     	content+="<br> <br>如果您的订单已使用邮局汇款的方式支付了，我们会将您的购物款项退还给您，但是邮局退款会扣除一定的手续费，给您造成不必要的损失，";
			     	content+="所以建议您将款项存入您的个人账户中。如果您的订单已使用信用卡的方式支付了，您的退款将被直接退还到您的信用卡帐户上。";
			     	content+="<br> 再次感谢您的支持，希望您有一个美好购物感受！ ";
			     	content+="<br> 我们也无比关注您的感受！";
			     	content+="<br>";
			     	content+="</td>";
			     	content+="</tr>";		     	
		     }	 
		     /** 订单缺款通知 **/
		     if(type==4){
		     	  content+="<tr>";
		     	    content+="<td valign=top ><span class=biaotiwenzi>";
		     	    content+="以上货品将不再发送";
		     	    content+="<br>";
			     	content+="如果您的订单已使用邮局汇款的方式支付了，我们会将您的购物款项退还给您，但是邮局退款会扣除一定的手续费，给您造成不必要的损失，所以建议您将款项存入您的个人帐户中。";
			     	content+="如果您的订单已使用信用卡的方式支付了，您的退款将被直接退还到您的信用卡帐户上。若您还需要再次定购，请您尽快与我们取得联系！";
			     	content+="<br>　再次感谢您的支持，希望您有一个美好的购物感受！";
			     	content+="<br>　我们也无比关注您的感受！";
			     	content+="<br>    阅读改变人生！非常感谢您对我们公司的关心和支持!祝您在九久购物愉快！ ";
			     	content+="<br>   欢迎您随时登陆我们的购物网站www.99read.com和读书论坛bbs.99read.com，关注我们公司的最新动态!";
			     	content+="<br>   如果您有任何意见或建议，欢迎您通过以下方式与我们取得联系。我们将以国际化标准竭诚为您服务。 ";
			     	content+="</span>	</td>";
			     	content+="</tr>";	     	
		     }			     
	      	content+="</table>";	      	
	    

	      
	      
            	content+="<table width='780' border='0' cellpadding='0' cellspacing='0' align='center'>";
            	  content+="<tr> ";
            	    content+="<td width='597' height='16' valign='top'>&nbsp;</td>";
            	    content+="<td width='183'>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='22' valign='top' class='biaotiwenzi'><strong>您诚挚的</strong><strong><font color='#a50000'>99网上书城 ";
            	       content+="客户服务部</font></strong></td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>如果您还有问题，请点击<a href='http://www.99read.com/webhelp/helpindex.asp' target='_blank'><font color='#0000FF'>帮助中心</font></a>查看常见问题的解答，或者与我们的客户服务中心联系</td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>E-mail:service@99read.com ";
            	     content+="  | 服务热线电话:021-34014699 | 服务中心传真:021-54960899</td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>来函地址: 上海市市南邮政032-99信箱，邮编: ";
            	      content+=" 200032</td>";
            	    content+="<td></td></tr></table></body></html>";
    			

        return content;
        
		}
	/*
	 *  根据orderID得到会员email
	 */
	public String getEmail(Connection con, int orderID)
	throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ifcheck = false;
		String mail="";
		try {
	       	String sql="select b.email from ord_headers a,mbr_members b where a.buyer_id=b.id and a.id="+orderID;
	       	pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mail= rs.getString("email");
				
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return mail;
		}
     
	}