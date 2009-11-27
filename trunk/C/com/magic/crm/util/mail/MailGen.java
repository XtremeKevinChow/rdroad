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

	/** �߿�����෢�ʹ��� * */
	private static final int MAX_SEND_TIMES = 2;

	/** �߿��ŵ�һ�η��ŵȴ����� * */
	private static final int FIRST_SHORT_MONEY_WAIT_DAYS = 3;

	/** �߿��ŵڶ��η��ͣ�֮�󣩵ȴ����� * */
	private static final int SECOND_SHORT_MONEY_WAIT_DAYS = 7;

	/** ȱ���ŷ��͵ȴ����� * */
	private static final int SHORT_GOODS_WAIT_DAYS = 3;

	/** ģ������ * */
	private static Map letterMap = new HashMap();

	/** �����ʼ����ʱ������ż�ģ�浽�ڴ���� * */
	static {

		log.info("�����ż�ģ�濪ʼ");

		try {
			loadTemplate();
		} catch (Exception sx) {
			log.error("�����ż�ģ�����");
			System.exit(-2);

		}
		log.info("�����ż�ģ�����");

	}

	/** ���캯�� * */
	public MailGen() {

	}

	/**
	 * �������
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MailGen gen = new MailGen();
		gen.dealConfirmMail();
	}

	/**
	 * �����ż�ģ��
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
				// ��ģ������map��
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
	 * ��ȡCLOB�ֶ�
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
	 * ȷ���š�����֪ͨ��ȱ��ȡ����ȱ��ȡ���ʼ��������
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
				SendMail2 sm = new SendMail2();// ���ʼ�������
				while (rs.next()) {
					try {
						type = rs.getInt("mail_type");
						orderID = rs.getInt("order_id");
						times = rs.getInt("times");
						days = rs.getDouble("days");
						email = getEmail(conn, orderID);
						if (email != null && email.trim().length() > 0
								&& StringUtil.isEmailAddress(email)) { // �ʼ���ַ�Ϸ�
							// ��̬��������������ͬ���͵��ʼ�������ʼ����Ͳ���ȷ�׳�LetterTypeException�쳣
							template = UserLetterFactory.getInstance(type);

							// �����ʼ��ʼ����⣬�ʼ����ݣ��ռ���
							LetterTemplateBean temp = (LetterTemplateBean) letterMap
									.get(String.valueOf(type));
							template.setTitle(temp.getTitle());
							template.setTemplate(temp.getBody());
							template.setEmail(email);

							// ģ�淽�����������setValue,replace()����
							template.buildTemplate(conn, orderID);

							// �ʼ��Ƿ�ɹ����ͱ��
							boolean isSend = false;

							isSend = sm.send(template);// �����ʼ�

							// ����ʼ����ͳɹ���������
							if (isSend) {
								System.out.println(new Date() + "\n " + orderID
										+ "�ʼ����ͳɹ�");
								if (update(conn, orderID, type, times)) {
									System.out.println(new Date() + "\n "
											+ orderID + "���ݸ��³ɹ�");
								} else {
									System.out.println(new Date() + "\n "
											+ orderID + "���ݸ���ʧ��");
								}
							}
						}
					} catch (SendFailedException ex) { // �ʼ����Ͳ���ȥ����ַ���Ϸ���
						if (updateFail(conn, orderID, type)) {
							System.out.println(new Date() + "\n " + orderID
									+ "�޷�����ָ����ַ�����ݸ��³ɹ�");
						} else {
							System.out.println(new Date() + "\n " + orderID
									+ "�޷�����ָ����ַ�����ݸ���ʧ��");
						}
					} catch (Exception e) {// ѭ���ڴ��쳣
						e.printStackTrace();
						log.error((e.toString() + " ѭ���ڴ��쳣"));

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
	 * ȱ�ȱ��(��ʱδ��)�ʼ��������
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
				SendMail2 sm = new SendMail2();// ���ʼ�������
				while (rs.next()) {
					try {

						type = rs.getInt("mail_type");
						orderID = rs.getInt("order_id");
						times = rs.getInt("times");
						days = rs.getDouble("days");
						email = getEmail(conn, orderID);
						if (StringUtil.isEmailAddress(email)) { // �ʼ���ַ�Ϸ�

							// ��̬��������������ͬ���͵��ʼ�������ʼ����Ͳ���ȷ�׳�LetterTypeException�쳣
							template = UserLetterFactory.getInstance(type);

							// �����ʼ��ʼ����⣬�ʼ����ݣ��ռ���
							LetterTemplateBean temp = (LetterTemplateBean) letterMap
									.get(String.valueOf(type));
							template.setTitle(temp.getTitle());
							template.setTemplate(temp.getBody());
							template.setEmail(email);

							// ģ�淽�����������setValue,replace()����
							template.buildTemplate(conn, orderID);

							// �ʼ��Ƿ�ɹ����ͱ��
							boolean isSend = false;

							// ����Ǵ߿��ţ��״η��ͣ��붩��ȷ�ϳ�����3��,����Ǵ߿���;�����״η��ͣ����Ǿ�����һ�η���δ��7��;ȱ���������3��
							if ((type == 2 && times == 0 && days >= FIRST_SHORT_MONEY_WAIT_DAYS)
									|| (type == 2 && times >= 1 && days >= SECOND_SHORT_MONEY_WAIT_DAYS)
									|| (type == 3 && days >= SHORT_GOODS_WAIT_DAYS)) {

								isSend = sm.send(template);// �����ʼ�
							}

							// ����ʼ����ͳɹ���������
							if (isSend) {
								System.out.println(new Date() + "\n " + orderID
										+ "�ʼ����ͳɹ�");
								if (update(conn, orderID, type, times)) {
									System.out.println(new Date() + "\n "
											+ orderID + "���ݸ��³ɹ�");
								} else {
									System.out.println(new Date() + "\n "
											+ orderID + "���ݸ���ʧ��");
								}
							}
						}
					} catch (OrderLetterException oex) {// ����������(����ȱ������ָ�ֵ)

						if (updateCancel(conn, orderID, type)) {
							System.out.println(new Date() + "\n " + orderID
									+ "ȱ����������ȡ�����ͣ����ݸ��³ɹ�");
						} else {
							System.out.println(new Date() + "\n " + orderID
									+ "ȱ����������ȡ�����ͣ����ݸ���ʧ��");
						}
					} catch (SendFailedException ex) {
						if (updateFail(conn, orderID, type)) {
							System.out.println(new Date() + "\n " + orderID
									+ "�޷�����ָ����ַ�����ݸ��³ɹ�");
						} else {
							System.out.println(new Date() + "\n " + orderID
									+ "�޷�����ָ����ַ�����ݸ���ʧ��");
						}
					} catch (Exception e) {// ѭ���ڴ��쳣
						e.printStackTrace();
						log.error((e.toString() + " ѭ���ڴ��쳣"));

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
	 * ��Ʒ����ʼ��������
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
			if (cnt == 0) {// û�м�¼
				return;
			}
			sql = "select a.id, a.user_name, a.mail_address, a.message_type "

			+ "from jxc.sto_mail_sets a where 1 = 1 and message_type = 2";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			SendMail2 sm = new SendMail2();// ���ʼ�������
			type = 101;
			StringBuffer email_list = new StringBuffer();;
			try {
				while (rs.next()) { //ƴװeimail

					
					email = rs.getString("mail_address");
					

					if (StringUtil.isEmailAddress(email)) { // �ʼ���ַ�Ϸ�
						email_list.append(email);
						email_list.append(",");
						
					}

				}
				//��̬��������������ͬ���͵��ʼ�������ʼ����Ͳ���ȷ�׳�LetterTypeException�쳣
				template = UserLetterFactory.getInstance(type);

				// �����ʼ��ʼ����⣬�ʼ����ݣ��ռ���
				LetterTemplateBean temp = (LetterTemplateBean) letterMap
						.get(String.valueOf(type));
				template.setTitle(temp.getTitle());
				template.setTemplate(temp.getBody());
				template.setEmail(email_list.toString());

				// ģ�淽�����������setValue,replace()����
				template.buildTemplate(conn, 0);

				// �ʼ��Ƿ�ɹ����ͱ��
				boolean isSend = false;

				isSend = sm.send(template);// �����ʼ�

				// ����ʼ����ͳɹ���������
				if (isSend) {
					System.out.println(new Date() + "\n " + orderID + "�ʼ����ͳɹ�");

				}
			} catch (SendFailedException ex) {
				if (updateFail(conn, orderID, type)) {
					System.out.println(new Date() + "\n " + orderID
							+ "�޷�����ָ����ַ�����ݸ��³ɹ�");
				} else {
					System.out.println(new Date() + "\n " + orderID
							+ "�޷�����ָ����ַ�����ݸ���ʧ��");
				}
			} catch (Exception e) {// ѭ���ڴ��쳣
				e.printStackTrace();
				log.error((e.toString() + " ѭ���ڴ��쳣"));

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
	 * ��Ʒ�����ʼ��������
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
			// û���ʼ����Է���
			if (cnt == 0) {
				return;
			}
			sql = "select a.id, a.user_name, a.mail_address, a.message_type "

			+ "from jxc.sto_mail_sets a where 1 = 1 and message_type = 1";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs != null) {
				SendMail2 sm = new SendMail2();// ���ʼ�������
				while (rs.next()) {
					try {

						// ��Ʒ����֪ͨ��
						type = 100;
						email = rs.getString("mail_address");

						if (StringUtil.isEmailAddress(email)) { // �ʼ���ַ�Ϸ�

							// ��̬��������������ͬ���͵��ʼ�������ʼ����Ͳ���ȷ�׳�LetterTypeException�쳣
							template = UserLetterFactory.getInstance(type);

							// �����ʼ��ʼ����⣬�ʼ����ݣ��ռ���
							LetterTemplateBean temp = (LetterTemplateBean) letterMap
									.get(String.valueOf(type));
							template.setTitle(temp.getTitle());
							template.setTemplate(temp.getBody());
							template.setEmail(email);

							// ģ�淽�����������setValue,replace()����
							template.buildTemplate(conn, 0);

							// �ʼ��Ƿ�ɹ����ͱ��
							boolean isSend = false;

							isSend = sm.send(template);// �����ʼ�

							// ����ʼ����ͳɹ���������
							if (isSend) {
								System.out.println(new Date() + "\n " + orderID
										+ "�ʼ����ͳɹ�");

							}
						}
					} catch (SendFailedException ex) {
						if (updateFail(conn, orderID, type)) {
							System.out.println(new Date() + "\n " + orderID
									+ "�޷�����ָ����ַ�����ݸ��³ɹ�");
						} else {
							System.out.println(new Date() + "\n " + orderID
									+ "�޷�����ָ����ַ�����ݸ���ʧ��");
						}
					} catch (Exception e) {// ѭ���ڴ��쳣
						e.printStackTrace();
						log.error((e.toString() + " ѭ���ڴ��쳣"));

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
	 * �����ʼ�����
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
			if (type == 2) { // �߿���(��2�췢�͵ڶ�����)
				if (times + 1 < MAX_SEND_TIMES) { // ������ʹ���С�ڶ��������ʹ���
					sql = "update user_letters set times = times + 1, send_date = sysdate where status = 0 and order_id = ? and mail_type = ?";
				} else {
					sql = "update user_letters set times = times + 1, send_date = sysdate, status = 1 where status = 0 and order_id = ? and mail_type = ?";
				}
			} else {
				if (times <= 0) { // û�з��͹�
					sql = "update user_letters set times = times + 1, send_date = sysdate, status = 1 where status = 0 and order_id = ? and mail_type = ?";
				} else {

				}
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderID);
			pstmt.setInt(2, type);
			pstmt.executeUpdate();
			return true;// ����ִ����ȷ
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
			return false;// ���ݳ����쳣

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {

				}
		}

	}

	/**
	 * �����ʼ�����(����ʧ��)
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
			return true;// ����ִ����ȷ
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
			return false;// ���ݳ����쳣

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {

				}
		}

	}

	/**
	 * ȡ���ʼ�
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
			return true;// ����ִ����ȷ
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
			return false;// ���ݳ����쳣

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {

				}
		}

	}

	/**
	 * ����orderID�õ���Աemail
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
