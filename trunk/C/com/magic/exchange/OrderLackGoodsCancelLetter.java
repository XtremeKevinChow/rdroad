package com.magic.exchange;

import java.sql.ResultSet;
import java.sql.*;
import com.magic.crm.util.*;

/**
 * ����ȡ���Ķ���ȱ����Ʒ��HTML�ַ���
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class OrderLackGoodsCancelLetter {
	private Connection dblink = null;

	//����Ϣ
	private String line_name;//  ����������

	private int line_count;//  ������������

	private float line_price;//  �����е��ۣ�

	private float line_sum;//  ������С�᣺

	private float order_sum;//�ܼƣ�

	public OrderLackGoodsCancelLetter() {
	}

	/**
	 * ����һ��HTML���ַ���
	 * 
	 * @param ������
	 */
	public String getHTML(String order_number) {
		int order_id = 0;
		String html = "<table bgcolor=\"#ddeeee\" border=\"1\" cellspacing=\"0\" cellpadding=\"1\" width=\"60%\" align=\"center\">"
				+ "<tr bgcolor=\"#aaccff\"><td width=\"20%\" align=\"center\">"
				+ "<span class=\"OraHeader\">��Ʒ����</span></td><td width=\"10%\" align=\"center\">"
				+ "<span class=\"OraHeader\">����</span></td><td width=\"10%\" align=\"center\">"
				+ "<span class=\"OraHeader\">���(Ԫ)</span></td><td width=\"10%\" align=\"center\">"
				+ "<span class=\"OraHeader\">С��(Ԫ)</span></td></tr>";

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
	 * ȡ��������Ϣ,������һ��HTML�ַ��� ����һ��ƴ���˵��ַ���
	 */
	public String getOrderLineInfo(int order_id) {

		Statement stmt = null;
		ResultSet rs = null;
		String html_str = "";
		String sql = "select item_name,shipped_quantity,price,amount  from VW_ORDER_LINES where order_id="
				+ order_id + " and shipped_quantity>0";
		try {
			dblink = DBManager2.getConnection();
			int sum_line_count = 0;//��Ʒ����
			float sum_line_sum = 0;//��Ʒ�ܼ�ֵ
			stmt = dblink.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				line_name = rs.getString("item_name");
				line_count = rs.getInt("shipped_quantity");
				line_price = rs.getFloat("price");
				line_sum = rs.getFloat("amount");
				sum_line_count = sum_line_count + line_count;
				sum_line_sum = sum_line_sum + line_sum;
				//ƴ�ַ���
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
					+ "<tr><td width=\"20%\" ><span class=\"OraHeader\">�ܼ�</span></td>"
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