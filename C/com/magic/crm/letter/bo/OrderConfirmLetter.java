package com.magic.crm.letter.bo;

import com.magic.utils.Arith;
import com.magic.crm.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 确认信邮件模版
 * 
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OrderConfirmLetter extends LetterTemplate {
    
    /** 本模版需要替换的变量(页面上定义的标签) * */
    private String[] label = new String[] { 
            "<MemberName>",
            "<DeliveryType>",
            "<PaymentMethod>",
            "<PaymentRemark>", 
            "<OrderNO>", 
            "<MemberEmail>",
            "<MemberPostcode>", 
            "<MemberAddress>",
            "<MemberTelephone>", 
            "<ProductList>" 
            };
    
    /** 银行map **/
    private static final Map bankMap = new HashMap();
    
    static {
        bankMap.put("4", "ICBC");
        bankMap.put("5", "CMB");
        bankMap.put("7", "IPS");
        bankMap.put("8", "CBC");
        bankMap.put("80", "SPDB");
    }

    public OrderConfirmLetter() {
        
    }
    
    /**
     * 替换title标签
     */
    public String replaceTitle() {
        return replace(getTitle(), label);
    }
    
    /**
     * 替换body标签
     */
    public String replaceBody() {
        return replace(getTemplate(), label);
    }
    
    
    /**
     * 设置标签对应的值(每个子模版的集中业务)
     */
    public void setValue(Connection con, int orderID) throws SQLException{
        
        LetterTemplate template = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        StringBuffer content = new StringBuffer();
        String name = null;
        String email = null;
        String member_address = null;
        String orderNO = null;
        String post_code = null;
        String member_phone = null;
        String paymentMethodName = null;
        int paymentMethod = 0;
        int deliveryType = 0;
        String paymentRemark = null;
        double delivery_fee = 0d;
        double deposit = 0d;
        double frozen_credit = 0d;
        double append_fee = 0d;
        String so_number = null;
        double order_sum = 0d;
        double member_deposit = 0d;
        double emoney = 0d;
        int amount_exp = 0;
        int exp = 0;
        try {
            sql = "select a.delivery_fee, a.append_fee, a.phone, a.address, a.postcode, a.so_number, a.payed_money, "
                    + "a.order_sum, b.name, b.email, b.AMOUNT_EXP, b.EXP, b.deposit, b.forzen_credit, b.emoney, a.remark, "
                    + "c.name as payment_method_name, a.payment_method, a.delivery_type "
                    + "from ord_headers a, mbr_members b, s_payment_method c "
                    + "where a.buyer_id = b.id and a.payment_method = c.id and a.id = ? ";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
                email = rs.getString("email");
                member_address = rs.getString("address");
                orderNO = rs.getString("so_number");
                post_code = rs.getString("postcode");
                member_phone = rs.getString("phone");
                paymentMethodName = rs.getString("payment_method_name");
                deliveryType = rs.getInt("delivery_type");
                paymentMethod = rs.getInt("payment_method");
                if (paymentMethod == 6) {
                    paymentRemark = "<tr><td>请在汇款单附言栏中注明您的订单号（如下图图示填写），收到汇款后我们将立即发货，再次感谢您的惠顾！</td></tr>";
                    paymentRemark += "<tr><td><img src='http://www.99read.com/image/ebuy/huikudan.jpg' border='0'></td></tr>";
                } else if (paymentMethod == 4 || paymentMethod == 5 || paymentMethod == 7 || paymentMethod == 8 || paymentMethod == 9) { //其他银行支付
                    paymentRemark = "<form name='submitForm' action='http://www.99read.com/payment/BankDeal.aspx' method='POST' target='_blank'>";
                    paymentRemark += "<input name=btn value='" + paymentMethodName + "' type='submit'>";
                    paymentRemark += "<input type='hidden' name='bank' value='" + bankMap.get(String.valueOf(paymentMethod)) + "'>";
                    paymentRemark += "<input type='hidden' name='order' value='" + orderNO + "'>";
                    paymentRemark += "<input type='hidden' name='date' value='" + DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss") + "'>";
                    
                } else if (paymentMethod == 80) { //浦发银行支付
                    paymentRemark = "<form name='submitForm' action='http://222.66.2.147/payment/BankDeal.asp' method='POST' target='_blank'>";
                    paymentRemark += "<input name=btn value='" + paymentMethodName + "' type='submit'>";
                    paymentRemark += "<input type='hidden' name='bank' value='" + bankMap.get(String.valueOf(paymentMethod)) + "'>";
                    paymentRemark += "<input type='hidden' name='order' value='" + orderNO + "'>";
                    paymentRemark += "<input type='hidden' name='date' value='" + DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss") + "'>";
                    
                }
                else {
                    paymentRemark = "";
                }
                delivery_fee = rs.getDouble("delivery_fee");
                deposit = rs.getDouble("payed_money");
                frozen_credit = rs.getDouble("forzen_credit");
                append_fee = rs.getDouble("append_fee");
                so_number = rs.getString("so_number");
                order_sum = rs.getDouble("order_sum");
                member_deposit = rs.getDouble("deposit");
                emoney = rs.getDouble("emoney");
                amount_exp = rs.getInt("amount_exp");
                exp = rs.getInt("exp");
                super.map.put("<MemberName>", name);
                map.put("<DeliveryType>", deliveryType == 1 ? "邮政递送" : "送货上门");
                map.put("<PaymentMethod>", paymentMethodName);
                map.put("<PaymentRemark>", paymentRemark);
                map.put("<OrderNO>", so_number);
                map.put("<MemberEmail>", email);
                map.put("<MemberPostcode>", post_code);
                map.put("<MemberAddress>", member_address);
                map.put("<MemberTelephone>", member_phone);

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

        try {
            sql = "select a.quantity,a.price,b.name from ord_lines a,prd_items b "
                    + "where a.item_id = b.item_id and a.order_id = ? ";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderID);
            rs = pstmt.executeQuery();
            content.append("<table cellSpacing=0 cellPadding=0 width=780 border=0 align='center'>");
            content.append("<tbody>");
            content.append("<tr class=biaokekuangjia>");
            content.append("<td width=10 height=20>&nbsp;</td>");
            content.append("<td width=382 valign=center><div class=iconwenzi align=left><strong>商品名称</strong></div></td>");
            content.append("<td valign=center width=66><div align=center><strong>数量</strong></div></td>");
            content.append("<td valign=center width=161><div align=center>");
            content.append("<p class=iconwenzi><strong>金额(元)</strong></p></div></td>");
            content.append("<td valign=center width=161> <div align=center><strong>小计(元)</strong></div></td></tr>");
            int a = 0;
            double b = 0;
            while (rs.next()) {
                a += rs.getInt("quantity");
                b += Arith.mul(rs.getInt("quantity"), rs.getDouble("price"));
                content.append("<tr class=biaokekuangjia>");
                content.append("<td width=10 height=20>&nbsp;</td>");
                content.append("<td width=382 valign=center><div class=iconwenzi align=left>"
                        + rs.getString("name") + "</div></td>");
                content.append("<td valign=center width=66><div align=center>"
                        + rs.getInt("quantity") + "</div></td>");
                content.append("<td valign=center width=161><div align=center>");
                content.append("<p class=iconwenzi>" + rs.getDouble("price")
                        + "</p></div></td>");
                content.append("<td valign=center width=161> <div align=center>"
                        + Arith.mul(rs.getInt("quantity"), rs.getDouble("price"))
                        + "(元)</div></td></tr>");

            }
            content.append("</tbody></table>");
            content.append("<table width=780 border=0 cellPadding=0 cellSpacing=0 class='biaokekuangjia' align='center'>");
            content.append("<tbody>");
            content.append("<tr class='biaokekuangjia'>");
            content.append("<td height=20>&nbsp;</td>");
            content.append("<td valign='middle'><div align='left'>小计</div></td>");
            content.append("<td valign='middle' width='66'><div align='center'>"
                    + a + "</div></td>");
            content.append("<td valign='middle' width=161><div align='center'></div></td>");
            content.append("<td valign='middle' width=161><div align='center'>"
                    + Arith.round(b, 2) + "</div></td></tr>");
            content.append("<tr class='biaokekuangjia'>");
            content.append("<td height=20>&nbsp;</td>");
            content.append("<td valign='middle'><div align='left'>发送费</div></td>");
            content.append("<td valign='middle'><div align='center'></div></td>");
            content.append("<td valign='middle'><div align='center'></div></td>");
            content.append("<td valign='middle'><div align='center'>"
                    + delivery_fee + "</div></td> </tr>");
            content.append("<tr class='biaokekuangjia'>");
            content.append("<td height=20>&nbsp;</td>");
            content.append("<td valign='middle'><div align='left'>使用预付款</div></td>");
            content.append("<td valign='middle'><div align='center'></div></td>");
            content.append("<td valign='middle'><div align='center'></div></td>");
            content.append("<td valign='middle'><div align='center'>" + deposit
                    + "</div></td></tr>");
            content.append("<tr class='biaokekuangjia'>");
            content.append("<td height=20>&nbsp;</td>");
            content.append("<td valign='middle'><div align='left'>礼券抵用</div></td>");
            content.append("<td valign='middle'><div align='center'></div></td>");
            content.append("<td valign='middle'><div align='center'></div></td>");
            content.append("<td valign='middle'><div align='center'>" + append_fee
                    + "</div></td></tr>");
            content.append("<tr class='biaokekuangjia'>");
            content.append("<td height=20>&nbsp;</td>");
            content.append("<td valign='middle'>");
            content.append("<div align='left'>总计（应付款）</div></td>");
            content.append("<td valign='middle'>");
            content.append("<div align='center'></div></td>");
            content.append("<td valign='middle'>");
            content.append("<div align='center'></div></td>");
            content.append("<td valign='middle'>");
            content.append("<div align='center'>"
                    + Arith.round((b + append_fee - deposit + delivery_fee), 2)
                    + "</div></td></tr>");
            content.append("<tr> ");
            content.append("<td height=1 colspan='4' valign='top' bgcolor='#993333'></td>");
            content.append("<td valign='top' bgcolor='#993333'></td></tr></tbody></table>");
            if (paymentMethod == 4 || paymentMethod == 5 || paymentMethod == 7 || paymentMethod == 8 || paymentMethod == 9 || paymentMethod == 80) {
                content.append("<input type='hidden' name='payment' value='" + Arith.round((b + append_fee - deposit + delivery_fee), 2) + "'>");
                content.append("</form>");
            }
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