package com.magic.exchange;

import java.sql.*;
import java.sql.SQLException;
import java.util.*;
import com.magic.utils.*;
import com.magic.crm.util.*;

import org.apache.log4j.*;

/**
 * ��վ����ͬ����CRMϵͳ
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class Order2CRM {
	private static Logger log = Logger.getLogger(Order2CRM.class);
	private int success_count = 0;
	private int failed_count = 0;
	
	//mapping
	private HashMap deliveryType_mapping = new HashMap();
	private HashMap paymentType_mapping = new HashMap();
	
	public void initMapping() {
		Connection conOra = null;
		try {
			conOra = DBManager2.getConnection();
			Statement st = conOra.createStatement();
			deliveryType_mapping.clear();
			ResultSet rs = st.executeQuery("select * from nsp_delivery_type ");
			while (rs.next()) {
				Integer crm_id = new Integer(rs.getInt("crm_id"));
				Integer net_id = new Integer(rs.getInt("nsp_id"));
				deliveryType_mapping.put(net_id,crm_id);
			}
			rs.close();
			paymentType_mapping.clear();
			rs = st.executeQuery("select * from nsp_payment_method ");
			while (rs.next()) {
				Integer crm_id = new Integer(rs.getInt("crm_id"));
				Integer net_id = new Integer(rs.getInt("nsp_id"));
				paymentType_mapping.put(net_id,crm_id);
			}
			rs.close();
			st.close();
			
		} catch(Exception e) {
			log.error("exception",e);
		} finally {
			try { conOra.close();}catch(Exception e){}
			
		}
		
	}
	
	public void execute() {
		initMapping();
		Connection conOra = null;
		Connection  conMs = null;
		try {
			conOra = DBManager2.getConnection();
			conMs = DBManagerMS.getConnection();
			Statement st = conMs.createStatement();
			PreparedStatement ps = conMs.prepareStatement("update odrmst set odrmst_change= 0 where odrmst_change=1 and rtrim(Ltrim(odrmst_odrid)) =?");
			String querysql_1 = "SELECT  odrmst.OdrMst_odrid, b2cmbr.b2cmbr_cardid, "
					+ "odrmst.OdrMst_mbrid, odrmst.OdrMst_confirmdate, "
					+ "odrmst.OdrMst_raddress, odrmst.OdrMst_rzipcode, "
					+ "odrmst.OdrMst_rname, odrmst.OdrMst_rphone, odrmst.OdrMst_shipid, "
					+ "odrmst.OdrMst_shipfee, odrmst.OdrMst_PayID,"
					+ "odrmst.OdrMst_InvoiceFlag, odrmst.OdrMst_gdsmoney, "
					+ "odrmst.OdrMst_liquanmoney, odrmst.OdrMst_ordermoney, "
					+ "odrmst.OdrMst_orderstatus, odrmst.OdrMst_membmemo, "
					+ "odrmst.OdrMst_IfWait, odrmst.OdrMst_createdate, "
					+ "odrmst.OdrMst_ticketid ticketid,odrmst_prepaymoney, "
					+ "odrmst.OdrMst_cardtype,odrmst.odrmst_bankpayment "
					+ "FROM odrmst INNER JOIN "
					+ "b2cmbr ON "
					+ "odrmst.OdrMst_mbrid = b2cmbr.b2cmbr_id "
					+ "WHERE (odrmst.OdrMst_orderstatus = 0) AND (odrmst.odrmst_change = 1)"
					+ "ORDER BY odrmst.OdrMst_createdate ";

			ResultSet rs = st.executeQuery(querysql_1);
			while (rs.next()) {
				try {

					OrderInfo orderInfo = new OrderInfo();
					String order_id = rs.getString("odrmst_odrid");//������
					String net_id = rs.getString("odrmst_mbrid");
					orderInfo.setSo_number(order_id);
					orderInfo.setBuyer_id(rs.getInt("odrmst_mbrid"));
					
					getCrmMemberId(conOra,orderInfo);
					
					log.info("������Ա����:" + rs.getString("b2cmbr_cardid"));
					//orderInfo.setRelease_date(rs.getDate("odrmst_confirmdate"));//ȷ������
					orderInfo.setRelease_date(rs.getDate("OdrMst_createdate")
							+ " " + rs.getTime("OdrMst_createdate"));//�¶�������
					orderInfo.setAddress(rs.getString("OdrMst_raddress"));//������ַ
					orderInfo.setPostcode(rs.getString("odrmst_rzipcode")
							.trim());//�ʱ�
					orderInfo.setContact(rs.getString("odrmst_rname"));//����������
					orderInfo.setPhone(rs.getString("odrmst_rphone"));//��ϵ�绰
					
					orderInfo.setDelivery_type(
							((Integer)deliveryType_mapping.get(new Integer(rs.getInt("odrmst_shipid")))).intValue()
							);//�������ͻ���ʽ
					orderInfo.setDelivery_fee(rs.getFloat("odrmst_shipfee"));//���ͷ�

					//���÷������д���
					orderInfo.setPayment_method(
							((Integer)paymentType_mapping.get(new Integer(rs.getInt("odrmst_payid")))).intValue()
							);//�����ĸ��ʽ

					//�����жϣ�����վ��odrmst_ifwaitΪ��ʱ��Oos_disposeΪ��
					//��������������վ��Odrmst_ifwaitΪ��ʱ��Oos_disposeΪ��
					int ifwait = rs.getInt("odrmst_ifwait");
					if (ifwait == 0) {
						orderInfo.setOos_dispose(3);
					} else {
						orderInfo.setOos_dispose(2);
					}
					orderInfo.setOos_dispose(orderInfo.getOos_dispose());

					orderInfo.setIs_invoice(rs.getInt("OdrMst_InvoiceFlag"));//�Ƿ�Ҫ��Ʊ
					orderInfo.setProduct_value(rs.getFloat("odrmst_gdsmoney"));//�������ܾ���
					//���붩��������Ϊ�����������ڶ����ĸ��ӷ�����
					orderInfo.setAppendfee(-1
							* rs.getFloat("odrmst_liquanmoney"));//�����
					orderInfo.setOrder_value(rs.getFloat("odrmst_ordermoney"));//����ʵ���ܾ���
					orderInfo.setOrder_status(rs.getInt("odrmst_orderstatus"));//����״̬

					orderInfo.setOrder_memo(rs.getString("OdrMst_membmemo"));//������ע
					orderInfo.setPrepayMoney(rs.getFloat("odrmst_prepaymoney"));
					orderInfo.setBank_pay(rs.getDouble("odrmst_bankpayment"));
					//ͨ��getGiftTicketId()����ȡ�ö�����Ӧ������
					orderInfo.setGift_number(StringUtil.cEmpty(rs
							.getString("ticketid")));

					//����һ������,ȡ�ö�����Ӧ�Ĳ�Ʒ����Ϣ
					getOrderProductInfo(conMs,conOra,orderInfo);

					if (!(orderInfo.getItem_id().equals("")
							|| orderInfo.getQuantity().equals("")
							|| orderInfo.getPrice().equals("")
							|| orderInfo.getIs_commitment().equals("") || orderInfo
							.getSell_type().equals(""))) {
						//���÷�������������Ϣǰ���Ƚ����жϣ���������������κ�һ��ֵΪ�յĻ���������ȥ
						//���÷����������Ա��Card_type�����£�,����������������վ�еĶ���ID����վ��վ��ԱID
						conOra.setAutoCommit(false);
						updateMemberInfo(conOra,orderInfo);
						transactOrderInfo(conOra,orderInfo);
						
						ps.setString(1,orderInfo.getSo_number());
						ps.executeUpdate();
						
						success_count++;
						log.info("����վ����ͬ����CRMϵͳ,\t" + orderInfo.getSo_number()
								+ "����ɹ�,\t�ɹ�:" + success_count + "\tʧ��:"
								+ failed_count);
						conOra.commit();
						try {
							new SendMail().send(conOra,orderInfo.getOrder_id(),1);
						} catch(Throwable e) {
							log.error("���Ͷ���ȷ���ų���" + orderInfo.getOrder_id() ,e);
						}
					
					} else {

						log.error("������������" + "\t������Ʒ�д��ڿ�ֵ:" + "\t�����ţ�"
								+ orderInfo.getSo_number());
						failed_count++;
						log.info("����վ����ͬ����CRMϵͳ,\t" + orderInfo.getSo_number()
								+ "����ʧ��,\t�ɹ�:" + success_count + "\tʧ��:"
								+ failed_count);
					}
					
				} catch (Exception e) {
					conOra.rollback();
					log.error("������������",e);
					failed_count++;
					log.info("����վ����ͬ����CRMϵͳ,\t" + rs.getString("odrmst_odrid")
							+ "����ʧ��,\t�ɹ�:" + success_count + "\tʧ��:"
							+ failed_count);
				}
			}
			rs.close();
			st.close();
			ps.close();
			
		} catch (SQLException se) {
			log.error("exception",se);
		} finally {
			try {conOra.close();} catch(Exception e) {}
			try {conMs.close();} catch(Exception e) {}
		}
		
	}
	
	/**
	 * �������еĲ�Ʒ��Ϣ
	 * 
	 * @param OrderInfo
	 */
	public void getOrderProductInfo(Connection conn,Connection conOra,OrderInfo orderInfo) throws Exception {
		Statement st = conn.createStatement();
		String s = orderInfo.getSo_number().trim();
		String line_id = "",item_id = "", quantity = "", price = "", is_commitment = "", sell_type = "",prom_line_id = "";
		String querysql = "select odrdtl_subodrid,odrdtl_saletype, odrdtl_gdsid,odrdtl_memberprice, odrdtl_gdscount,odrdtl_dutyflag,isnull(promotion_gift_id,0)as prom_line_id from odrdtl  where odrdtl_odrid='"
				+ s + "'";

		ResultSet rs = st.executeQuery(querysql);
		while (rs.next()) {
			//ȡ����Ϣ��������µ�String
			//����queryProductId()��CRMϵͳ��prd_item�����ҳ���Ʒ��Ӧ��ID,�����ַ���
			int i_str = this.queryProductId(conOra,(rs.getString("odrdtl_gdsid").trim()));
			if (i_str != -1) {
				item_id = item_id + i_str + ",";
				line_id = line_id + rs.getFloat("odrdtl_subodrid") + ",";
				price = price + rs.getFloat("odrdtl_memberprice") + ",";
				quantity = quantity + rs.getInt("odrdtl_gdscount") + ",";

				sell_type = sell_type + rs.getInt("odrdtl_saletype") + ",";
				is_commitment = is_commitment + rs.getInt("odrdtl_dutyflag")
						+ ",";
				prom_line_id = prom_line_id + rs.getInt("prom_line_id") + ",";
			} else {

				log.error("������������" + "\t������Ʒ��CRMϵͳ�в�����" + "\t�����ţ�" + s
						+ "\t���ţ�" + item_id);
				throw new Exception("������Ʒ��CRMϵͳ�в�����,�����ţ�" + s + "\t���ţ�"
						+ item_id);
			}
		}
		rs.close();
		st.close();
		orderInfo.setWeb_line_id(line_id);
		orderInfo.setItem_id(item_id);
		orderInfo.setQuantity(quantity);
		orderInfo.setPrice(price);
		orderInfo.setIs_commitment(is_commitment);
		orderInfo.setSell_type(sell_type);
		orderInfo.setPro_line_id(prom_line_id);
	}
	
	/**
	 * ������վϵͳ�ж������еĶ�����ȥ������Ӧ�Ĳ�Ʒ����ȡ�û��ţ�����Ψһ�� �ٸ��ݻ�����CRMϵͳ���ҳ���Ӧ�Ĳ�ƷID
	 * 
	 * @param ss
	 *            ��Ʒ����
	 */
	public int queryProductId(Connection conn,String ss) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		int result = -1;
		ss = ss.trim();
		String querysql = "select item_id from prd_items where item_code='"
				+ ss + "'";
		rs = stmt.executeQuery(querysql);
		if (rs.next())
			result = rs.getInt("item_id");
		rs.close();
		stmt.close();
		return result;
	}
	
	/**
	 * �ڵ��붩����Ϣ֮ǰ ���»�Ա����Ϣ,������ֵ�ͻ�Ա������
	 */
	public int updateMemberInfo(Connection conn,OrderInfo order)
			throws Exception {
		int ret=0; 
		//ֻ��1 ʱ�Ÿ���CRMϵͳ�л�Ա��Card_type����CRMϵͳ��Ĭ��ֵΪ0������Ϊ0ʱ���ظ��£�����ֵ���������²���
		/*if (order.getBank_pay() > 0 ) {
			//step1 ����Ա��ֵ
			String sql1 = "update mbr_members set deposit = deposit + ? where id = ?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setDouble(1, order.getBank_pay());
			ps1.setLong(2, order.getMbr_id());
			ps1.executeUpdate();
			ps1.close();

			// step2 ��¼��ֵ��ʷ
			String sql2 = "insert into mbr_money_history(ID,MEMBER_ID,OPERATOR_ID,DEPOSIT,MONEY_UPDATE,COMMENTS,MODIFY_DATE,EVENT_TYPE,PAY_METHOD) "
					+ " values(seq_mbr_money_history.nextval,?,?,(select deposit from mbr_members where id = ? ),?,?,sysdate,2020,?)";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setLong(1, order.getMbr_id());
			ps2.setInt(2, 0);
			ps2.setLong(3, order.getMbr_id());
			ps2.setDouble(4, order.getBank_pay());
			ps2.setString(5, order.getSo_number());
			ps2.setInt(6,order.getPayment_method());
			ps2.executeUpdate();
			ps2.close();
		}*/ 
		
		if (order.getCard_type() == 1) {
			String updatesql = "update mbr_members set card_type=" + order.getCard_type()
					+ "  where netshop_id=" + order.getBuyer_id();
			Statement st = conn.createStatement();
			int i = st.executeUpdate(updatesql);
			if (i == 0) {
				log.error("�����������" + "\t�û�Ա�����²�����" + "\t��Ա��վID��"
						+ order.getBuyer_id());
				throw new Exception("������������û�Ա�����²�����,��Ա��վID��"
						+ order.getBuyer_id());
			}
			st.close();
		}
		
		return ret;
	}
	
	/**
	 * ���ô洢���̣������¶���(��վ������CRMϵͳ)
	 * 
	 * @param orderInfo
	 *            ������Ϣ
	 * @param card_type
	 *            ������
	 * @throws java.lang.Exception
	 */
	public void transactOrderInfo(Connection conn, OrderInfo orderInfo)
			throws Exception {
		
		
		String release_date = orderInfo.getRelease_date();
		CallableStatement cst = conn
				.prepareCall("{?=call WEB_INTERFACE.F_GET_WEB_ORDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cst.registerOutParameter(1, Types.INTEGER);
		cst.setString(2, orderInfo.getSo_number().trim());
		//	    System.out.println("�����ţ�"+orderInfo.getSo_number());
		cst.setLong(3, orderInfo.getMbr_id());
		//System.out.println("CRM�л�ԱID��"+queryCardId(orderInfo.getBuyer_id()));
		cst.setString(4, release_date);
		//	    System.out.println("�������ڣ�"+orderInfo.getRelease_date());
		cst.setString(5, orderInfo.getAddress().trim());
		//	    System.out.println("��ַ��"+orderInfo.getAddress());
		cst.setString(6, orderInfo.getPostcode() + "");
		//	    System.out.println("�ʱࣺ"+orderInfo.getPostcode());
		cst.setString(7, orderInfo.getContact());
		//	    System.out.println("��ϵ�ˣ�"+orderInfo.getContact());
		cst.setString(8, orderInfo.getPhone());
		//		System.out.println("�绰��"+orderInfo.getPhone());
		cst.setInt(9, orderInfo.getDelivery_type());
		//	    System.out.println("������ʽ��"+orderInfo.getDelivery_type());
		cst.setFloat(10, orderInfo.getDelivery_fee());
		//	    System.out.println("�������ã�"+orderInfo.getDelivery_fee());
		cst.setInt(11, orderInfo.getPayment_method());
		//	    System.out.println("���ʽ��"+orderInfo.getPayment_method());
		cst.setInt(12, orderInfo.getIs_invoice());
		//	    System.out.println("�Ƿ�Ҫ��Ʊ��"+orderInfo.getIs_invoice());
		cst.setFloat(13, orderInfo.getProduct_value());
		//	    System.out.println("��Ʒ��"+orderInfo.getProduct_value());
		cst.setFloat(14, orderInfo.getAppendfee());
		//				    System.out.println("׷�ӷ��ã�"+orderInfo.getAppendfee());
		cst.setFloat(15, orderInfo.getOrder_value());
		//    System.out.println("�����ܽ�"+orderInfo.getOrder_value());
		cst.setInt(16, orderInfo.getOrder_status());
		//	    System.out.println("����״̬��"+orderInfo.getOrder_status());
		cst.setInt(17, orderInfo.getOos_dispose());
		//	    System.out.println("oos_dispose:"+orderInfo.getOos_dispose());
		cst.setString(18, orderInfo.getWeb_line_id());
		cst.setString(19, orderInfo.getItem_id());
		//	    System.out.println("��ƷID��"+orderInfo.getItem_id());
		cst.setString(20, orderInfo.getQuantity());
		//	    System.out.println("����������"+orderInfo.getQuantity());
		cst.setString(21, orderInfo.getPrice());
		//	    System.out.println("��Ʒ�۸�"+orderInfo.getPrice());
		cst.setString(22, orderInfo.getIs_commitment());
		//	   System.out.println("�Ƿ������飺"+orderInfo.getIs_commitment());
		cst.setString(23, orderInfo.getSell_type());
		//	    System.out.println("sell_type��"+orderInfo.getSell_type());
		cst.setString(24, orderInfo.getGift_number());
		//������ע��Ϣ
		// System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~������ע��Ϣ:"+orderInfo.getOrder_memo());
		cst.setString(25, orderInfo.getOrder_memo());
		cst.setInt(26, orderInfo.getCard_type());
		cst.setString(27,orderInfo.getPro_line_id());
		cst.execute();
		int order_id = cst.getInt(1);
		String description = "";
		if (order_id < 0) {
			if (order_id == -9) {
				log.error("��������" + "������" + orderInfo.getSo_number()
						+ "�Ѵ��ڣ�����Ҫ���µ��롣");
			} else {
				throw new Exception(String.valueOf(order_id));
			}
		} else {
			orderInfo.setOrder_id(order_id);
		}
		cst.close();
	}
	
	private void getCrmMemberId(Connection conn,OrderInfo order) throws Exception
	{
		long crm_member_id = -1;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select id from mbr_members where netshop_id=" + order.getBuyer_id());
		if (rs.next()) {
			crm_member_id = rs.getLong("id");
			order.setMbr_id(crm_member_id);
		}
		if(crm_member_id <0 ) {
			throw new Exception("��վidΪ" + rs.getInt("id")+ "��Ա������");
		}
		rs.close();
		st.close();
	}
}