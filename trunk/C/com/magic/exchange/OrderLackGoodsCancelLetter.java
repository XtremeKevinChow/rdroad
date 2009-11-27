package com.magic.exchange;

import java.sql.ResultSet;
import java.sql.*;
import com.magic.crm.util.*;

/**
 * 产生取消的订单缺货商品的HTML字符串
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class OrderLackGoodsCancelLetter {
	private Connection dblink = null;

	//行信息
	private String line_name;//  订单行名：

	private int line_count;//  订单行数量：

	private float line_price;//  订单行单价：

	private float line_sum;//  订单行小结：

	private float order_sum;//总计：

	public OrderLackGoodsCancelLetter() {
	}

	/**
	 * 返回一个HTML的字符串
	 * 
	 * @param 订单号
	 */
	public String getHTML(String order_number) {
		int order_id = 0;
		String html = "<table bgcolor=\"#ddeeee\" border=\"1\" cellspacing=\"0\" cellpadding=\"1\" width=\"60%\" align=\"center\">"
				+ "<tr bgcolor=\"#aaccff\"><td width=\"20%\" align=\"center\">"
				+ "<span class=\"OraHeader\">商品名称</span></td><td width=\"10%\" align=\"center\">"
				+ "<span class=\"OraHeader\">数量</span></td><td width=\"10%\" align=\"center\">"
				+ "<span class=\"OraHeader\">金额(元)</span></td><td width=\"10%\" align=\"center\">"
				+ "<span class=\"OraHeader\">小计(元)</span></td></tr>";

		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select order_id from VW_ORDERS where order_number='"
				+ order_number + "'";
		try {
			dblink = DBManager2.getConnection();
			stmt = dblink.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				order_id = rs.getInt("order_id");
			}
			String temp_str = this.getOrderLineInfo(order_id);
			html = html + temp_str;
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (dblink != null)
					dblink.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return html;
	}

	/**
	 * 取订单行信息,并构成一个HTML字符串 返回一个拼好了的字符串
	 */
	public String getOrderLineInfo(int order_id) {

		Statement stmt = null;
		ResultSet rs = null;
		String html_str = "";
		String sql = "select item_name,shipped_quantity,price,amount  from VW_ORDER_LINES where order_id="
				+ order_id + " and shipped_quantity>0";
		try {
			dblink = DBManager2.getConnection();
			int sum_line_count = 0;//产品总数
			float sum_line_sum = 0;//产品总价值
			stmt = dblink.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				line_name = rs.getString("item_name");
				line_count = rs.getInt("shipped_quantity");
				line_price = rs.getFloat("price");
				line_sum = rs.getFloat("amount");
				sum_line_count = sum_line_count + line_count;
				sum_line_sum = sum_line_sum + line_sum;
				//拼字符串
				html_str = html_str
						+ "<tr><td width=\"20%\" ><span class=\"OraHeader\">"
						+ line_name
						+ "</span></td>"
						+ "<td width=\"10%\" align=\"center\"><span class=\"OraHeader\">"
						+ line_count
						+ "</span></td>"
						+ "<td width=\"10%\" align=\"center\"><span class=\"OraHeader\">"
						+ line_price
						+ "</span></td>"
						+ "<td width=\"10%\" align=\"center\"><span class=\"OraHeader\">"
						+ line_sum + "</span></td></tr>";
			}
			html_str = html_str
					+ "<tr><td width=\"20%\" ><span class=\"OraHeader\">总计</span></td>"
					+ "<td width=\"10%\" align=\"center\"><span class=\"OraHeader\">"
					+ sum_line_count
					+ "</span></td>"
					+ "<td width=\"10%\" align=\"center\"><span class=\"OraHeader\">---</span></td>"
					+ "<td width=\"10%\" align=\"center\"><span class=\"OraHeader\">"
					+ sum_line_sum + "</span></td></tr>";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (dblink != null)
					dblink.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return html_str;
	}

	public static void main(String[] args) {
		OrderLackGoodsCancelLetter orderLackGoodsCancelLetter = new OrderLackGoodsCancelLetter();
		String ss = orderLackGoodsCancelLetter.getHTML("W040905000228");
		System.out.println(ss);
	}
}