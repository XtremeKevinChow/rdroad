package com.magic.crm.letter.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;

/**
 * �ֿ��Ʒ����֪ͨģ��
 * 
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProductArrivalLetter extends LetterTemplate {

    
	/** ��ģ����Ҫ�滻�ı���(ҳ���϶���ı�ǩ) * */
    private String[] label = new String[] { 
          //  "<PurNO>",
           // "<ItemName>",
          //  "<ItemCode>",
          //  "<Qty>",
            "<ProductList>" 
            };
    
  
  
    /**
     * �滻title��ǩ
     */
    public String replaceTitle() {
        return replace(getTitle(), label);
    }
    
    /**
     * �滻body��ǩ
     */
    public String replaceBody() {
        return replace(getTemplate(), label);
    }
    
    public static int countRecord(Connection con) throws SQLException {
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) as total from jxc.sto_item_arrival a inner join prd_items b "
 	    	 + "on a.item_id = b.item_id where a.is_send = 0 ";
        int cnt = 0;
        
        Calendar cld1 = Calendar.getInstance();
        Calendar cld2 = Calendar.getInstance();
        cld2.add(Calendar.DATE, -1);//����
        
        int year = cld1.get(Calendar.YEAR);
        
        String month = "0"+ (cld1.get(Calendar.MONTH) + 1);
        month = month.substring(month.length() - 2);
        
        String day = "0" + cld1.get(Calendar.DATE);
        day = day.substring(day.length() - 2);
        
        int hour = cld1.get(Calendar.HOUR_OF_DAY);
        StringBuffer beginDate = new StringBuffer();
        StringBuffer endDate = new StringBuffer();
        
        if (hour >= 9 && hour < 11) { // 9���ӷ�������㵽����9����ʼ�
        	beginDate.append(cld2.get(Calendar.YEAR));
        	beginDate.append("-");
        	String tempMonth = "0" + (cld2.get(Calendar.MONTH) + 1);	
        	beginDate.append(tempMonth.substring(tempMonth.length()-2));
        	beginDate.append("-");
        	String tempDate = "0" + cld2.get(Calendar.DATE);
        	
        	beginDate.append(tempDate.substring(tempDate.length()-2));
        	beginDate.append(" ");
        	beginDate.append("17");
        	
        	endDate.append(year);
        	endDate.append("-");
        	endDate.append(month);
        	endDate.append("-");
        	endDate.append(day);
        	endDate.append(" ");
        	endDate.append("9");
        } else {
        	beginDate.append(year);
        	beginDate.append("-");
        	beginDate.append(month);
        	beginDate.append("-");
        	beginDate.append(day);
        	beginDate.append(" ");
        	
        	endDate.append(year);
        	endDate.append("-");
        	endDate.append(month);
        	endDate.append("-");
        	endDate.append(day);
        	endDate.append(" ");
        	if (hour >= 11 && hour < 14) {
            	
            	beginDate.append("9");
            	endDate.append("11");
            	
            } else if (hour >= 14 && hour < 17) {
            	beginDate.append("11");
            	endDate.append("14");
            	
            } else if (hour >= 17) {
            	beginDate.append("14");
            	endDate.append("17");
            	
            }
        }
        
        sql += " and a.write_date >= to_date('" + beginDate.toString() +"', 'yyyy-mm-dd hh24') and a.write_date < to_date('" + endDate.toString() +"','yyyy-mm-dd hh24')";
        System.out.println(sql);
        try {
        	
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
            	cnt = rs.getInt("total");
            	System.out.println("222222222222"+cnt);
            }
           
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
        return cnt;
    }
    
    /**
     * ���ñ�ǩ��Ӧ��ֵ(ÿ����ģ��ļ���ҵ��)
     */
    public void setValue(Connection con, int orderID) throws SQLException{
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select a.*, b.item_code, b.name as item_name from jxc.sto_item_arrival a inner join prd_items b "
 	    	 + "on a.item_id = b.item_id where a.is_send = 0 ";
        StringBuffer content = new StringBuffer();
        
        Calendar cld1 = Calendar.getInstance();
        Calendar cld2 = Calendar.getInstance();
        cld2.add(Calendar.DATE, -1);//����
        
        int year = cld1.get(Calendar.YEAR);
        
        String month = "0"+ (cld1.get(Calendar.MONTH) + 1);
        month = month.substring(month.length() - 2);
        
        String day = "0" + cld1.get(Calendar.DATE);
        day = day.substring(day.length() - 2);
        
        int hour = cld1.get(Calendar.HOUR_OF_DAY);
        StringBuffer beginDate = new StringBuffer();
        StringBuffer endDate = new StringBuffer();
        
        if (hour >= 9 && hour < 11) { // 9���ӷ�������㵽����9����ʼ�
        	beginDate.append(cld2.get(Calendar.YEAR));
        	beginDate.append("-");
        	String tempMonth = "0" + (cld2.get(Calendar.MONTH) + 1);	
        	beginDate.append(tempMonth.substring(tempMonth.length()-2));
        	beginDate.append("-");
        	String tempDate = "0" + cld2.get(Calendar.DATE);
        	
        	beginDate.append(tempDate.substring(tempDate.length()-2));
        	beginDate.append(" ");
        	beginDate.append("17");
        	
        	endDate.append(year);
        	endDate.append("-");
        	endDate.append(month);
        	endDate.append("-");
        	endDate.append(day);
        	endDate.append(" ");
        	endDate.append("9");
        } else {
        	beginDate.append(year);
        	beginDate.append("-");
        	beginDate.append(month);
        	beginDate.append("-");
        	beginDate.append(day);
        	beginDate.append(" ");
        	
        	endDate.append(year);
        	endDate.append("-");
        	endDate.append(month);
        	endDate.append("-");
        	endDate.append(day);
        	endDate.append(" ");
        	if (hour >= 11 && hour < 14) {
            	
            	beginDate.append("9");
            	endDate.append("11");
            	
            } else if (hour >= 14 && hour < 17) {
            	beginDate.append("11");
            	endDate.append("14");
            	
            } else if (hour >= 17) {
            	beginDate.append("14");
            	endDate.append("17");
            	
            }
        }
        
        
        sql += " and a.write_date >= to_date('" + beginDate.toString() +"', 'yyyy-mm-dd hh24') and a.write_date < to_date('" + endDate.toString() +"','yyyy-mm-dd hh24')";
        System.out.println(sql);
        try {
        	
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            content.append("<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>");
            content.append("<tbody>");
            content.append("<tr class=biaokekuangjia>");
            content.append("<td width=10 height=20>&nbsp;</td>");
            content.append("<td width=66 valign=center><div class=iconwenzi align=left><strong>��Ʒ����</strong></div></td>");
            content.append("<td valign=center width=382><div align=center><strong>��Ʒ����</strong></div></td>");
            content.append("<td valign=center width=161><div align=center><p class=iconwenzi><strong>����</strong></p></div></td>");
            content.append("<td valign=center width=161><div align=center><p class=iconwenzi><strong>��Ӧ����</strong></p></div></td>");
            content.append("</tr>");
            while (rs.next()) {
            	//String purNO = rs.getString("pur_no");
	        	String itemCode = rs.getString("item_code");
	        	String itemName = rs.getString("item_name");
	        	String purNO = rs.getString("pur_no");
	        	int qty = rs.getInt("qty");
                content.append("<tr class=biaokekuangjia>");
                content.append("<td width=10 height=20>&nbsp;</td>");
                content.append("<td width=66 valign=center><div class=iconwenzi align=left>"
                        + itemCode + "</div></td>");
                content.append("<td valign=center width=382><div align=center>"
                        + itemName + "</div></td>");
                content.append("<td valign=center width=161><div align=center>");
                content.append("<p class=iconwenzi>" + qty
                        + "</p></div></td>");
                content.append("<td valign=center width=161><div align=center><p class=iconwenzi>" + purNO
                        + "</p></div></td>");
                content.append("</tr>");

            }
            content.append("</tbody></table>");
            
            map.put("<ProductList>", content.toString());
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
        
    }
}
