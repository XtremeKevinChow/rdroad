
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
	 * ����ȷ���� 1
	 * �߿�֪ͨ 2
	 * ����ȱ��֪ͨ 3
	 * ����ȱ��֪ͨ 4
	 * ��������֪ͨ 5
	 */
	public String getContent(Connection conn,int orderID,int type){
    String sql="";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
		String content="";	


		/*
		 *  �õ���Ա�Ļ�����Ϣ
		 */
       	String name="";
       	String email="";
       	String member_add="";
       	String post_code="";
       	String member_phone="";
       	String so_number="";
       	int sum_quantity=0;
       	double delivery_fee=0;//���ͷ�
       	double deposit=0; //����Ԥ����
       	double append_fee=0;//��ȯ����
       	double order_sum=0;//�����ܶ�
       	double member_deposit=0;//��ԱԤ����
       	double emoney=0;//eԪ
       	int amount_exp=0;//��Ȼ���
       	int exp=0;//����
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
        	content+="<td width='68'><a href='http://www.99read.com/memb/membcf01.asp'><font color='#000000'>ע��/��¼</font></a></td>";
        	content+="<td width='27'><img src='http://www.99read.com/images/top/icon2.gif' width='27' height='27'></td>";
        	content+="<td width='60'><a href='http://www.99read.com/ebuy/gdspick.asp?action=1'><font color='#000000'>���ﳵ</font></a></td>";
        	content+="<td width='27'><img src='http://www.99read.com/images/top/icon5.gif' width='27' height='27'></td>";
        	content+="<td width='68'><a href='http://www.99read.com/my99/my99.asp'><font color='#000000'>�ҵĹܼ�</font></a></td>";
        	content+="<td width='27'><img src='http://www.99read.com/images/top/icon4.gif' width='27' height='27'></td>";
        	content+="<td width='68'><a href='#' onClick='javascript:OpenWin2();'><font color='#000000'>��������</font></a></td>";
        	content+="<td width='27'><img src='http://www.99read.com/images/top/icon6.gif' width='27' height='27'></td>";
        	content+="<td width='68'><a href='http://www.99read.com/webhelp/helpindex.asp'><font color='#000000'>��������</font></a></td>";
        	content+="<td width='27' height='40'><img src='http://www.99read.com/images/top/icon7.gif' width='27' height='27' border='0'></td>";
        	content+="<td><a href='javascript:void(window.open('http://www.biztong.com/guest/GuestLogin.aspx?managerID=459','_blank','width=328,height=300'));'><font color='#000000'>���߿ͷ�</font></a></td>";
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
            /** ����ȷ�� **/	    
            if(type==1){
            	  content+="<tr>";
            	  content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>�װ��Ļ�Ա</strong></font><strong>&nbsp;["+name+"]</strong>��</span></p>" +
            	    		"<p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
            	    		"<span class=biaotiwenzi>���ȣ��ǳ���л����99������ǹ����������ʱ��<a href='http://www.99read.com/my99/my99.asp' target='_blank'>" +
            	    		"<font color='#0000FF'>�ҵĹܼ�</font></a>�в�ѯ�����Ķ���״̬�Ͳ�Ʒ������Ϣ��</span><br></p></td></tr>";
            	  content+="<tr>";
            }
            /** �߿�֪ͨ **/
            if(type==2){
            	 content+="<tr>";
            	 content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>�װ��Ļ�Ա</strong></font><strong>&nbsp;["+name+"]</strong>��</span></p><p>";
            	 content+="<span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>���ã�";
            	 content+="�ǳ���л�������žõ�ͼ�飬��������֧�ֲ����������н���ĳɼ���";
            	 content+="������Ҫ����ȷ�ϵ������Ķ�����["+so_number+"]���У�["+(order_sum-deposit)+"]δ����,";
            	 content+="�ܼ�Ӧ����Ϊ��["+order_sum+"]Ԫ��";            	 
            	 content+="</td>";
            	 content+=" </tr>";
          }     
            /** ����ȱ��֪ͨ **/
            if(type==3){
            	  content+="<tr>";
            	  content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>�װ��Ļ�Ա</strong></font><strong>&nbsp;["+name+"]</strong>��</span></p><p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>���ã�";
            	  content+="<br>�ǳ���л���ھžù�����Ķ����ţ�["+so_number+"],���������������飨������Ʒ�����ڱȽϳ���������ȱ���������������¶����Ѿ���ȡ����";
            	  content+=" </td>";
            	  content+="</tr>";
          }        
            /** ����ȱ��֪ͨ **/
            if(type==4){
            	  content+="<tr>";
            	  content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>�װ��Ļ�Ա</strong></font><strong>&nbsp;["+name+"]</strong>��</span></p><p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>���ã�";
            	  content+="�ǳ���л���ھžù�����Ķ����ţ�["+so_number+"],�������������¶�����ʼ��15�����ڣ�δ���յ�����ȫ��������������������˱ʶ����������������¶����Ѿ���ȡ����";
            	  content+="</td>";
            	  content+="</tr>";
          }   
            /** ��������֪ͨ **/
            if(type==5){
            	    content+="<tr>";
            	    content+="<td valign=top height=52><p><span class=biaotiwenzi><font color=#993333><strong>�װ��Ļ�Ա</strong></font><strong>&nbsp;["+name+"]</strong>��</span></p>" +
            	    		"<p><span class=iconwenzi>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class=biaotiwenzi>���Ķ����ѷ�������ע����ա�<br>&nbsp;&nbsp;&nbsp;" +
            	    		"��������ʱ��<a href='http://www.99read.com/my99/my99.asp' target='_blank'><font color='#0000FF'>�ҵĹܼ�</font></a>�в�ѯ�����Ķ���״̬�Ͳ�Ʒ������Ϣ��</span><br></p></td>";
            	    content+="</tr>";
          }             
            	    content+="<td height=19>&nbsp;</td> </tr></tbody></table>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center width=780 height=24><strong>������ϸ������Ϣ:</strong></td></tr></tbody></table>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	  content+="<tr>";
            	    content+="<td valign=center bgColor=#993333 colspan=3 rowspan=2><span class=iconwenzi><font color=#ffffff><strong>(�����ţ�["+so_number+"]) </strong></font></span></td>";
            	    content+="<td colspan=2 height=18></td></tr>";
            	  content+="<tr>";
            	    content+="<td bgColor=#993333 colspan=2 height=2></td></tr>";
            	  content+="<tr>";
            	    content+="<td valign=top width=8 rowspan=3>&nbsp;</td>";
            	    content+="<td valign=center width=61 height=30><span class=biaotiwenzi>��&nbsp;&nbsp;��:</span><br></td>";
            	    content+="<td class=biaotiwenzi valign=center  width=158>["+name+"]</td>";
            	    content+="<td class=biaotiwenzi valign=center width=61>E_mail:</td>";
            	    content+="<td class=biaotiwenzi valign=center width=492>["+email+"]</td></tr>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center height=30>��&nbsp;&nbsp;��:</td>";
            	    content+="<td class=biaotiwenzi valign=center>["+post_code+"]</td>";
            	    content+="<td class=biaotiwenzi valign=center>��&nbsp;&nbsp;ַ:</td>";
            	    content+="<td class=biaotiwenzi valign=center>["+member_add+"]</td></tr>";
            	  content+="<tr>";
            	    content+="<td class=biaotiwenzi valign=center height=30>��&nbsp;&nbsp;��:</td>";
            	    content+="<td class=biaotiwenzi valign=center>["+member_phone+"]</td>";
            	    content+="<td></td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr valign=center>";
            	    content+="<td valign=center bgColor=#993333 colspan=3 height=20 rowspan=2><span class=iconwenzi><font color=#ffffff><strong>��Ʒ��Ϣ </strong></font></span></td>";
            	    content+="<td valign=top colspan=2 height=18></td></tr>";
            	  content+="<tr valign=center>";
            	    content+="<td valign=top bgColor=#993333 colspan=2 height=2></td></tr></tbody></table>";
            	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
            	  content+="<tbody>";
            	    content+="<tr class=biaokekuangjia> ";
            	      content+="<td width=10 height=20>&nbsp;</td>";
            	      content+="<td width=382 valign=center><div class=iconwenzi align=left><strong>��Ʒ����</strong></div></td>";
            	      content+="<td valign=center width=66><div align=center><strong>����</strong></div></td>";
            	      content+="<td valign=center width=161><div align=center> ";
            	          content+="<p class=iconwenzi><strong>���(Ԫ)</strong></p></div></td>";
            	      content+="<td valign=center width=161> <div align=center><strong>С��(Ԫ)</strong></div></td></tr>";
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
                  	      content+="<td valign=center width=161> <div align=center>"+rs.getInt("quantity")*rs.getDouble("price")+"(Ԫ)</div></td></tr>";
                  	      
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
            	      content+="<td valign='middle'><div align='left'>С��</div></td>";
            	      content+="<td valign='middle' width='66'><div align='center'>["+a+"]</div></td>";
            	      content+="<td valign='middle' width=161><div align='center'></div></td>";
            	      content+="<td valign='middle' width=161><div align='center'>["+b+"]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>���ͷ�</div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'>["+delivery_fee+"]</div></td> </tr>";
            		 content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>ʹ��Ԥ����</div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'>["+deposit+"]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20>&nbsp;</td>";
            	      content+="<td valign='middle'><div align='left'>��ȯ����</div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'></div></td>";
            	      content+="<td valign='middle'><div align='center'>["+append_fee+"]</div></td></tr>";
            	    content+="<tr class='biaokekuangjia'> ";
            	      content+="<td height=20 bgcolor='#CCCCCC'>&nbsp;</td>";
            	      content+="<td valign='middle' bgcolor='#CCCCCC'>";
            	      content+="<div align='left'>�ܼƣ�Ӧ���</div></td>";
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
	      	    content+="<td class=biaotiwenzi valign=center width=780  height=24><strong>��Ŀǰ���ʻ���Ϣ:</strong></td></tr></tbody></table>";
	      	content+="<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>";
	      	  content+="<tbody>";
	      	content+="<tr>";
	      	    content+="<td valign=top width=8 rowspan=3>&nbsp;</td>";
	      	    content+="<td valign=center width=80 height=30><span class=biaotiwenzi>Ԥ�������:</span><br></td>";
	      	    content+="<td class=biaotiwenzi valign=center  width=158>["+member_deposit+"]</td>";
	      	    content+="<td class=biaotiwenzi valign=center width=80>eԪ:</td>";
	      	    content+="<td class=biaotiwenzi valign=center width=158>["+emoney+"]</td></tr>";

	      	  content+="<tr>";
	      	    content+="<td class=biaotiwenzi valign=center height=30>����:</td>";
	      	    content+="<td class=biaotiwenzi valign=center>["+exp+"]</td>";
	      	    content+="<td class=biaotiwenzi valign=center height=30>��Ȼ���:</td>";
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
		      /** �߿�֪ͨ **/
	 	     if(type==2){	      	  
		      	  content+="<tr>";
		      	    content+="<td valign=top ><span class=biaotiwenzi>";	
		      	    content+="<br>Ҳ��������æ�Ĺ�����ѧϰҪ��������������������Ǳ�ʾ���¡���ͬʱϣ�������յ��˷���ʱ���ܳ�����ı���ʱ���ڶ����ڽ�δ����롣";		      	 
		      	    content+="<br> ���ɲ�ȡ���·�ʽ��";
		      	    content+="<br>�ʾֻ��տ��˵�ַ��03299���Ϻ�����) &nbsp;&nbsp; �տ����������ž� &nbsp;&nbsp; �ʱࣺ200032&nbsp;&nbsp;���ڻ���˸�������ע�����Ļ�Ա��";
		      	    content+="<br>����֧������ο�����֧����ʽ,ͬʱ����������Ե����ʼ��ķ�ʽ��֪�������Ļ�Ա���롣";
			      	content+="<br>��������յ����ŵ�ͬʱ�Ѿ����ɲ��������š��Դ��Ÿ����Ĵ������Ǳ�ʾǸ�⡣����������ʻ������Լ����κ����ʣ�������������ϵ���Ա����ǹ�ͬ���壬Ϊ���ṩ���õķ���";
			      	content+="</span></td>";
			      	content+="</tr>";
	 	    }
		     /** ����ȱ��֪ͨ **/
		     if(type==3){
		     	    content+="<tr>";
		     	    content+="<td valign=top height=52><p><span class=biaotiwenzi>";
			     	content+="���ϲ�Ʒ���ٷ��ͣ����������Ĳ��㣬������б�Ǹ���鷳��ͨ��������ʽ��֪�������µ��������ǽ�����޶ȵ���������Ҫ�󣡡�";
			     	content+="<br> <br>������Ķ�����ʹ���ʾֻ��ķ�ʽ֧���ˣ����ǻὫ���Ĺ�������˻������������ʾ��˿��۳�һ���������ѣ�������ɲ���Ҫ����ʧ��";
			     	content+="���Խ�����������������ĸ����˻��С�������Ķ�����ʹ�����ÿ��ķ�ʽ֧���ˣ������˿��ֱ���˻����������ÿ��ʻ��ϡ�";
			     	content+="<br> �ٴθ�л����֧�֣�ϣ������һ�����ù�����ܣ� ";
			     	content+="<br> ����Ҳ�ޱȹ�ע���ĸ��ܣ�";
			     	content+="<br>";
			     	content+="</td>";
			     	content+="</tr>";		     	
		     }	 
		     /** ����ȱ��֪ͨ **/
		     if(type==4){
		     	  content+="<tr>";
		     	    content+="<td valign=top ><span class=biaotiwenzi>";
		     	    content+="���ϻ�Ʒ�����ٷ���";
		     	    content+="<br>";
			     	content+="������Ķ�����ʹ���ʾֻ��ķ�ʽ֧���ˣ����ǻὫ���Ĺ�������˻������������ʾ��˿��۳�һ���������ѣ�������ɲ���Ҫ����ʧ�����Խ�����������������ĸ����ʻ��С�";
			     	content+="������Ķ�����ʹ�����ÿ��ķ�ʽ֧���ˣ������˿��ֱ���˻����������ÿ��ʻ��ϡ���������Ҫ�ٴζ�������������������ȡ����ϵ��";
			     	content+="<br>���ٴθ�л����֧�֣�ϣ������һ�����õĹ�����ܣ�";
			     	content+="<br>������Ҳ�ޱȹ�ע���ĸ��ܣ�";
			     	content+="<br>    �Ķ��ı��������ǳ���л�������ǹ�˾�Ĺ��ĺ�֧��!ף���ھžù�����죡 ";
			     	content+="<br>   ��ӭ����ʱ��½���ǵĹ�����վwww.99read.com�Ͷ�����̳bbs.99read.com����ע���ǹ�˾�����¶�̬!";
			     	content+="<br>   ��������κ�������飬��ӭ��ͨ�����·�ʽ������ȡ����ϵ�����ǽ��Թ��ʻ���׼�߳�Ϊ������ ";
			     	content+="</span>	</td>";
			     	content+="</tr>";	     	
		     }			     
	      	content+="</table>";	      	
	    

	      
	      
            	content+="<table width='780' border='0' cellpadding='0' cellspacing='0' align='center'>";
            	  content+="<tr> ";
            	    content+="<td width='597' height='16' valign='top'>&nbsp;</td>";
            	    content+="<td width='183'>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='22' valign='top' class='biaotiwenzi'><strong>����ֿ��</strong><strong><font color='#a50000'>99������� ";
            	       content+="�ͻ�����</font></strong></td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>������������⣬����<a href='http://www.99read.com/webhelp/helpindex.asp' target='_blank'><font color='#0000FF'>��������</font></a>�鿴��������Ľ�𣬻��������ǵĿͻ�����������ϵ</td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>E-mail:service@99read.com ";
            	     content+="  | �������ߵ绰:021-34014699 | �������Ĵ���:021-54960899</td>";
            	    content+="<td>&nbsp;</td></tr>";
            	  content+="<tr> ";
            	    content+="<td height='20' valign='top' class='biaotiwenzi'>������ַ: �Ϻ�����������032-99���䣬�ʱ�: ";
            	      content+=" 200032</td>";
            	    content+="<td></td></tr></table></body></html>";
    			

        return content;
        
		}
	/*
	 *  ����orderID�õ���Աemail
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