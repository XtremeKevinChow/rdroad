/*
 * Created on 2006-4-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import javax.mail.SendFailedException;

import oracle.sql.CLOB;

import org.apache.log4j.Logger;

import com.magic.crm.letter.bo.*;
import com.magic.crm.util.DBManager2;
import com.magic.utils.StringUtil;

/**
 * @author magic
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MailGen {

	private static Logger log = Logger.getLogger("MailGen.class");

	/** 催款信最多发送次数 * */
	private static final int MAX_SEND_TIMES = 2;

	/** 催款信第一次发信等待天数 * */
	private static final int FIRST_SHORT_MONEY_WAIT_DAYS = 3;

	/** 催款信第二次发送（之后）等待天数 * */
	private static final int SECOND_SHORT_MONEY_WAIT_DAYS = 7;

	/** 缺货信发送等待天数 * */
	private static final int SHORT_GOODS_WAIT_DAYS = 3;

	/** 模版数据 * */
	private static Map letterMap = new HashMap();

	/** 本类初始化的时候加载信件模版到内存变量 * */
	static {

		log.info("加载信件模版开始");

		try {
			loadTemplate();
		} catch (Exception sx) {
			log.error("加载信件模版出错");
			System.exit(-2);

		}
		log.info("加载信件模版完毕");

	}

	/** 构造函数 * */
	public MailGen() {

	}

	/**
	 * 测试入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MailGen gen = new MailGen();
		gen.dealConfirmMail();
	}

	/**
	 * 加载信件模版
	 * 
	 * @throws SQLException
	 */
	private static void loadTemplate() throws SQLException, IOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		CLOB body = null;
		try {
			conn = DBManager2.getConnection();
			sql = "select id, title, body, description from letter_template";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				LetterTemplateBean temp = new LetterTemplateBean();
				temp.setId(rs.getLong("id"));
				temp.setTitle(rs.getString("title"));
				temp.setDescription(rs.getString("description"));
				body = (CLOB) rs.getClob("body");
				temp.setBody(readClob(body));
				// 将模版载入map中
				letterMap.put(String.valueOf(rs.getLong("id")), temp);

			}
		} catch (SQLException ex) {
			throw ex;
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
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}
		}
	}

	/**
	 * 读取CLOB字段
	 * 
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	private static String readClob(CLOB clob) throws IOException, SQLException {
		Reader is = null;
		BufferedReader br = null;
		String bodyStr = "";
		try {
			if (clob != null) {
				is = clob.getCharacterStream();
				br = new BufferedReader(is);
				String line = br.readLine();
				while (line != null) {
					bodyStr += line;
					line = br.readLine();
				}

			}
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (Exception ex) {

				}
			if (is != null)
				try {
					is.close();
				} catch (Exception ex) {

				}

		}
		return bodyStr;
	}

	/**
	 * 确认信、发货通知、缺款取消、缺货取消邮件处理入口
	 */
	public void dealConfirmMail() throws Exception {
		Connection conn = null;
		LetterTemplate template = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int type = 0;
		int orderID = 0;
		int times = 0;
		double days = 0;
		String email = null;

		try {
			conn = DBManager2.getConnection();
			sql = "select a.id, a.order_id, a.receive_date, a.send_date, a.times, "
					+ "a.status, a.mail_type, (sysdate - nvl(a.send_date, a.receive_date)) as days "
					+ "from user_letters a "
					+ "where a.status = 0 and a.mail_type in(1, 4, 6, 7) order by a.id asc ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs != null) {
				SendMail2 sm = new SendMail2();// 发邮件工具类
				while (rs.next()) {
					try {
						type = rs.getInt("mail_type");
						orderID = rs.getInt("order_id");
						times = rs.getInt("times");
						days = rs.getDouble("days");
						email = getEmail(conn, orderID);
						if (email != null && email.trim().length() > 0
								&& StringUtil.isEmailAddress(email)) { // 邮件地址合法
							// 静态工厂方法产生不同类型的邮件，如果邮件类型不正确抛出LetterTypeException异常
							template = UserLetterFactory.getInstance(type);

							// 设置邮件邮件主题，邮件内容，收件人
							LetterTemplateBean temp = (LetterTemplateBean) letterMap
									.get(String.valueOf(type));
							template.setTitle(temp.getTitle());
							template.setTemplate(temp.getBody());
							template.setEmail(email);

							// 模版方法调用子类的setValue,replace()方法
							template.buildTemplate(conn, orderID);

							// 邮件是否成功发送标记
							boolean isSend = false;

							isSend = sm.send(template);// 发送邮件

							// 如果邮件发送成功更新数据
							if (isSend) {
								System.out.println(new Date() + "\n " + orderID
										+ "邮件发送成功");
								if (update(conn, orderID, type, times)) {
									System.out.println(new Date() + "\n "
											+ orderID + "数据更新成功");
								} else {
									System.out.println(new Date() + "\n "
											+ orderID + "数据更新失败");
								}
							}
						}
					} catch (SendFailedException ex) { // 邮件发送不出去（地址不合法）
						if (updateFail(conn, orderID, type)) {
							System.out.println(new Date() + "\n " + orderID
									+ "无法到达指定地址，数据更新成功");
						} else {
							System.out.println(new Date() + "\n " + orderID
									+ "无法到达指定地址，数据更新失败");
						}
					} catch (Exception e) {// 循环内大异常
						e.printStackTrace();
						log.error((e.toString() + " 循环内大异常"));

					}
				}
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
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}

		}
	}

	/**
	 * 缺款，缺货(暂时未用)邮件处理入口
	 * 
	 * @throws Exception
	 */
	public void dealPaymentMail() throws Exception {
		Connection conn = null;
		LetterTemplate template = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int type = 0;
		int orderID = 0;
		int times = 0;
		double days = 0;
		String email = null;
		try {
			conn = DBManager2.getConnection();
			sql = "select a.id, a.order_id, a.receive_date, a.send_date, a.times, "
					+ "a.status, a.mail_type, (sysdate - nvl(a.send_date, a.receive_date)) as days "
					+ "from user_letters a "
					+ "where a.status = 0 and a.mail_type = 2 order by a.id asc ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs != null) {
				SendMail2 sm = new SendMail2();// 发邮件工具类
				while (rs.next()) {
					try {

						type = rs.getInt("mail_type");
						orderID = rs.getInt("order_id");
						times = rs.getInt("times");
						days = rs.getDouble("days");
						email = getEmail(conn, orderID);
						if (StringUtil.isEmailAddress(email)) { // 邮件地址合法

							// 静态工厂方法产生不同类型的邮件，如果邮件类型不正确抛出LetterTypeException异常
							template = UserLetterFactory.getInstance(type);

							// 设置邮件邮件主题，邮件内容，收件人
							LetterTemplateBean temp = (LetterTemplateBean) letterMap
									.get(String.valueOf(type));
							template.setTitle(temp.getTitle());
							template.setTemplate(temp.getBody());
							template.setEmail(email);

							// 模版方法调用子类的setValue,replace()方法
							template.buildTemplate(conn, orderID);

							// 邮件是否成功发送标记
							boolean isSend = false;

							// 如果是催款信，首次发送，离订单确认超过到3天,如果是催款信;不是首次发送，但是距离上一次发送未到7天;缺货间隔超过3天
							if ((type == 2 && times == 0 && days >= FIRST_SHORT_MONEY_WAIT_DAYS)
									|| (type == 2 && times >= 1 && days >= SECOND_SHORT_MONEY_WAIT_DAYS)
									|| (type == 3 && days >= SHORT_GOODS_WAIT_DAYS)) {

								isSend = sm.send(template);// 发送邮件
							}

							// 如果邮件发送成功更新数据
							if (isSend) {
								System.out.println(new Date() + "\n " + orderID
										+ "邮件发送成功");
								if (update(conn, orderID, type, times)) {
									System.out.println(new Date() + "\n "
											+ orderID + "数据更新成功");
								} else {
									System.out.println(new Date() + "\n "
											+ orderID + "数据更新失败");
								}
							}
						}
					} catch (OrderLetterException oex) {// 数据有问题(订单缺款金额出现负值)

						if (updateCancel(conn, orderID, type)) {
							System.out.println(new Date() + "\n " + orderID
									+ "缺款信有问题取消发送，数据更新成功");
						} else {
							System.out.println(new Date() + "\n " + orderID
									+ "缺款信有问题取消发送，数据更新失败");
						}
					} catch (SendFailedException ex) {
						if (updateFail(conn, orderID, type)) {
							System.out.println(new Date() + "\n " + orderID
									+ "无法到达指定地址，数据更新成功");
						} else {
							System.out.println(new Date() + "\n " + orderID
									+ "无法到达指定地址，数据更新失败");
						}
					} catch (Exception e) {// 循环内大异常
						e.printStackTrace();
						log.error((e.toString() + " 循环内大异常"));

					}
				}
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
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}

		}
	}

	/**
	 * 产品入库邮件处理入口
	 * 
	 * @throws Exception
	 */
	public void dealInboundMail() throws Exception {
		Connection conn = null;
		LetterTemplate template = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int type = 0;
		int orderID = 0;
		int cnt = 0;
		String email = null;
		try {
			conn = DBManager2.getConnection();
			cnt = ProductInboundLetter.countRecord(conn);
			System.out.println("inbound:" + cnt);
			if (cnt == 0) {// 没有记录
				return;
			}
			sql = "select a.id, a.user_name, a.mail_address, a.message_type "

			+ "from jxc.sto_mail_sets a where 1 = 1 and message_type = 2";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			SendMail2 sm = new SendMail2();// 发邮件工具类
			type = 101;
			StringBuffer email_list = new StringBuffer();;
			try {
				while (rs.next()) { //拼装eimail

					
					email = rs.getString("mail_address");
					

					if (StringUtil.isEmailAddress(email)) { // 邮件地址合法
						email_list.append(email);
						email_list.append(",");
						
					}

				}
				//静态工厂方法产生不同类型的邮件，如果邮件类型不正确抛出LetterTypeException异常
				template = UserLetterFactory.getInstance(type);

				// 设置邮件邮件主题，邮件内容，收件人
				LetterTemplateBean temp = (LetterTemplateBean) letterMap
						.get(String.valueOf(type));
				template.setTitle(temp.getTitle());
				template.setTemplate(temp.getBody());
				template.setEmail(email_list.toString());

				// 模版方法调用子类的setValue,replace()方法
				template.buildTemplate(conn, 0);

				// 邮件是否成功发送标记
				boolean isSend = false;

				isSend = sm.send(template);// 发送邮件

				// 如果邮件发送成功更新数据
				if (isSend) {
					System.out.println(new Date() + "\n " + orderID + "邮件发送成功");

				}
			} catch (SendFailedException ex) {
				if (updateFail(conn, orderID, type)) {
					System.out.println(new Date() + "\n " + orderID
							+ "无法到达指定地址，数据更新成功");
				} else {
					System.out.println(new Date() + "\n " + orderID
							+ "无法到达指定地址，数据更新失败");
				}
			} catch (Exception e) {// 循环内大异常
				e.printStackTrace();
				log.error((e.toString() + " 循环内大异常"));

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
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}

		}
	}

	/**
	 * 产品到货邮件处理入口
	 * 
	 * @throws Exception
	 */
	public void dealArrivalMail() throws Exception {
		Connection conn = null;
		LetterTemplate template = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int type = 0;
		int orderID = 0;
		int cnt = 0;
		String email = null;
		try {
			conn = DBManager2.getConnection();
			cnt = ProductArrivalLetter.countRecord(conn);
			System.out.println("arrival:" + cnt);
			// 没有邮件可以发送
			if (cnt == 0) {
				return;
			}
			sql = "select a.id, a.user_name, a.mail_address, a.message_type "

			+ "from jxc.sto_mail_sets a where 1 = 1 and message_type = 1";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs != null) {
				SendMail2 sm = new SendMail2();// 发邮件工具类
				while (rs.next()) {
					try {

						// 产品到货通知信
						type = 100;
						email = rs.getString("mail_address");

						if (StringUtil.isEmailAddress(email)) { // 邮件地址合法

							// 静态工厂方法产生不同类型的邮件，如果邮件类型不正确抛出LetterTypeException异常
							template = UserLetterFactory.getInstance(type);

							// 设置邮件邮件主题，邮件内容，收件人
							LetterTemplateBean temp = (LetterTemplateBean) letterMap
									.get(String.valueOf(type));
							template.setTitle(temp.getTitle());
							template.setTemplate(temp.getBody());
							template.setEmail(email);

							// 模版方法调用子类的setValue,replace()方法
							template.buildTemplate(conn, 0);

							// 邮件是否成功发送标记
							boolean isSend = false;

							isSend = sm.send(template);// 发送邮件

							// 如果邮件发送成功更新数据
							if (isSend) {
								System.out.println(new Date() + "\n " + orderID
										+ "邮件发送成功");

							}
						}
					} catch (SendFailedException ex) {
						if (updateFail(conn, orderID, type)) {
							System.out.println(new Date() + "\n " + orderID
									+ "无法到达指定地址，数据更新成功");
						} else {
							System.out.println(new Date() + "\n " + orderID
									+ "无法到达指定地址，数据更新失败");
						}
					} catch (Exception e) {// 循环内大异常
						e.printStackTrace();
						log.error((e.toString() + " 循环内大异常"));

					}
				}
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
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}

		}
	}

	/**
	 * 更新邮件数据
	 * 
	 * @param con
	 * @param orderID
	 * @return
	 * @throws SQLException
	 */
	public boolean update(Connection con, int orderID, int type, int times) {
		PreparedStatement pstmt = null;
		String sql = null;
		boolean rtv = false;
		try {
			if (type == 2) { // 催款信(隔2天发送第二封信)
				if (times + 1 < MAX_SEND_TIMES) { // 如果发送次数小于定义的最大发送次数
					sql = "update user_letters set times = times + 1, send_date = sysdate where status = 0 and order_id = ? and mail_type = ?";
				} else {
					sql = "update user_letters set times = times + 1, send_date = sysdate, status = 1 where status = 0 and order_id = ? and mail_type = ?";
				}
			} else {
				if (times <= 0) { // 没有发送过
					sql = "update user_letters set times = times + 1, send_date = sysdate, status = 1 where status = 0 and order_id = ? and mail_type = ?";
				} else {

				}
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderID);
			pstmt.setInt(2, type);
			pstmt.executeUpdate();
			return true;// 数据执行正确
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
			return false;// 数据出现异常

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {

				}
		}

	}

	/**
	 * 更新邮件数据(发送失败)
	 * 
	 * @param con
	 * @param orderID
	 * @return
	 * @throws SQLException
	 */
	public boolean updateFail(Connection con, int orderID, int type) {
		PreparedStatement pstmt = null;
		String sql = null;
		boolean rtv = false;
		try {

			sql = "update user_letters set send_date = sysdate, status = -2 where status = 0 and order_id = ? and mail_type = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderID);
			pstmt.setInt(2, type);
			pstmt.executeUpdate();
			return true;// 数据执行正确
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
			return false;// 数据出现异常

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {

				}
		}

	}

	/**
	 * 取消邮件
	 * 
	 * @param con
	 * @param orderID
	 * @return
	 * @throws SQLException
	 */
	public boolean updateCancel(Connection con, int orderID, int type) {
		PreparedStatement pstmt = null;
		String sql = null;
		boolean rtv = false;
		try {

			sql = "update user_letters set send_date = sysdate, status = -1 where status = 0 and order_id = ? and mail_type = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderID);
			pstmt.setInt(2, type);
			pstmt.executeUpdate();
			return true;// 数据执行正确
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
			return false;// 数据出现异常

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {

				}
		}

	}

	/**
	 * 根据orderID得到会员email
	 * 
	 * @param con
	 * @param orderID
	 * @return
	 * @throws SQLException
	 */
	public String getEmail(Connection con, int orderID) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String mail = null;
		try {
			String sql = "select b.email from ord_headers a,mbr_members b where a.buyer_id = b.id and a.id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mail = rs.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
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
