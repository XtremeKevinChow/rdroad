/*
 * Created on 2005-8-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.exchange;

/*
 * Created on 2005-1-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.magic.exchange.Member2CRM;
import com.magic.exchange.MemberInfo;
import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebData;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.util.DBManager2;
import com.magic.crm.util.DBManagerMS;
import com.magic.crm.util.MD5;
import com.magic.crm.util.SendMail;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberMail {
	private static Logger log = Logger.getLogger(MemberMail.class);

     /*
      * ÿ���7��ǰ�µĶ�������Ŀǰ״̬��Ԥ�����Ķ���INSERT����MBR_EMAIL��
      */
		   public static void insertEmail(Connection con)
				throws SQLException {
			PreparedStatement pstmt = null;
			PreparedStatement pstmt1 = null;
			ResultSet rs = null;	
	
			String sql="";
			String insert_sql="";
            int orderid=0;
			
			try {
				sql="select ord_headers.buyer_id,ord_headers.id,mbr_members.email from ord_headers,mbr_members " +
						" where ord_headers.buyer_id=mbr_members.id and ord_headers.status=15 " +
						" and to_char(ord_headers.create_date,'yyyy-mm-dd') = to_char(sysdate-7,'yyyy-mm-dd')";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();	
				while(rs.next()){
					orderid=rs.getInt(2);
                    if(!checkOrderID(con,orderid)){
					insert_sql="insert into mbr_email(BUYER_ID,order_id,remind_num,email,status) values (?,?,?,?,?)";
					pstmt1 = con.prepareStatement(insert_sql);
					pstmt1.setInt(1, rs.getInt(1));
					pstmt1.setInt(2, rs.getInt(2));
					pstmt1.setInt(3, 0);
					pstmt1.setString(4, rs.getString(3));
					pstmt1.setInt(5, 15);
					pstmt1.execute();
					pstmt1.close();
                    }
				}

			 } catch (Exception e) { 
			 	log.info("������"+orderid+":����mbr_emailʱ������֪���Ĵ���",e);
			 } finally {
				if (rs != null)
					try {
						rs.close();
					}catch (Exception e){}
				
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (Exception e) {
					}
			}

		}		
		     /*
		      * ����EMAIL������Ԥ�����Ļ�Ա�����·��ʹ���
		      */	
		   public static void sendEmail(Connection con)
			throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;	
		String sql="";
		String insert_sql="";
		int orderid=0;
		try {
			/*
			 * ÿ�η���EMAILǰ����ʵ��ǰ�����Ƿ���Ԥ�����,���ҷ��ʹ���С��2
			 */
			sql="select mbr_email.email,mbr_email.order_id from mbr_email,ord_headers where " +
					"mbr_email.order_id=ord_headers.id and ord_headers.status=15 and mbr_email.remind_num<2 and mbr_email.email is not null";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();	
			while(rs.next()){
            /*
             * �����ʼ�
             */
				SendMail sml = new SendMail();
				orderid=rs.getInt(2);
				sml.send(con,orderid,2); 				
				insert_sql="update  mbr_email set remind_num=remind_num+1 where  order_id=?";
				pstmt1 = con.prepareStatement(insert_sql);
				pstmt1.setInt(1, orderid);
				pstmt1.execute();
				pstmt1.close();
			}
				

		 } catch (Exception e) { 
		 	log.info("������"+orderid+":����mbr_emailʱ������֪���Ĵ���",e);
		 } finally {
			if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}	
		    /*
		     * ִ���ռ�ȱ���������
		     */
			public void execute() {
				Connection conn = null;

				Statement stmt = null;
				ResultSet rs = null;
				PreparedStatement pstmt = null;
				
				try {
					conn = DBManager2.getConnection();					
                    insertEmail(conn);
                    System.out.println("ȱ����ռ���ϣ�");
				} catch (Exception e) {
					System.out.println("�ռ�Ƿ���Աʱ�����쳣:" + e + "\n");
					e.printStackTrace();
				} finally {
					try {
						pstmt.close();
					} catch (Exception e) {
					}
					try {
						conn.close();
					} catch (Exception e) {
					}
					
				}
			}
			/*
			 * ִ�з��ʹ߿��ŵ�����
			 */
			public void execute1() {
				//����վ�л�ȡ���л�Ա��Ϣ��SQL���
				Connection conn = null;

				Statement stmt = null;
				ResultSet rs = null;
				PreparedStatement pstmt = null;
				
				try {
					conn = DBManager2.getConnection();					
                    sendEmail(conn);
                    System.out.println("�ʼ�������ϣ�");
				} catch (Exception e) {
					System.out.println("���ʹ߿���ʱ�����쳣:" + e + "\n");
					e.printStackTrace();
				} finally {
					try {
						pstmt.close();
					} catch (Exception e) {
					}
					try {
						conn.close();
					} catch (Exception e) {
					}
					
				}
			}			
			/*
			 * �ж���ͬ��order_id�Ƿ��Ѿ�����
			 */
			public static boolean checkOrderID(Connection conn,int order_id)
		       throws SQLException{
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean ifcheck = false;
			String check_sql="";
			try {
				check_sql="select * from mbr_email where order_id="+order_id;
				pstmt = conn.prepareStatement(check_sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					ifcheck = true;
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
			return ifcheck;
		}	
			/*
			 * 2005-07-29
			 * ��ļ��Աѡ����ȯ����Ʒ
			 */
				
				
				public Collection get_MBR_MSC_GIFT_info(Connection con, String msc_code)
				throws SQLException {
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Collection memberGIFT = new ArrayList();
			
				try {
					String sQuery = "select mbr_msc_gift.item_id,prd_items.name from mbr_msc_gift,prd_items  where" +
							"  prd_items.item_id=mbr_msc_gift.item_id and mbr_msc_gift.msc_code='"+msc_code+"' order by id desc";
					pstmt = con.prepareStatement(sQuery);
					rs = pstmt.executeQuery();
			
					while (rs.next()) {
						MemberAWARD info = new MemberAWARD();
						//info.setItem_ID(rs.getInt(1));
						info.setDescription(rs.getString(2));
						
						memberGIFT.add(info);
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
				return memberGIFT;
			}			
}
