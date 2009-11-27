/*
 * Created on 2005-1-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.magic.crm.util.Function;
;

/**
 * @author zhux
 *
 * this class is for member gift exchange 
 */
public class MemberGiftExchanger {
	private static Logger log = Logger.getLogger(MemberGiftExchanger.class);
	Connection connOra = null;
	Connection connSql = null;
	
	/**
	 * ��ʼ������,����һ��oracle connection and a sqlserver connection
	 * @param conn1
	 * @param conn2
	 * @throws Exception
	 */
	public void initConnection(Connection conn1, Connection conn2 ) throws Exception {
		if( conn1 == null || conn1.isClosed()) { 
			throw new Exception(" Oracle Connection is null or closed ");
		}
		if( conn2 == null || conn2.isClosed()) { 
			throw new Exception(" sqlserver Connection is null or closed ");
		}
		connOra = conn1;
		connSql = conn2;
	}
	
	/**
	 * 
	 *
	 */
	public void execute() throws Exception {
		//ѡ����Ʒ���ж�Ӧ��վid�ļ�¼
		PreparedStatement ps = connOra.prepareStatement("select * from mbr_get_award where web_id = ? ");
		//ѡ����Ʒ���еĶ�Ӧ��Ա�Ļ��ֻ����¼
		PreparedStatement ps1 = connOra.prepareStatement("select a.*,c.item_code from mbr_get_award a, mbr_members b,prd_items c where b.netshop_id = ? and a.member_id = b.id and a.type = 0 and a.item_id = c.item_id ");
		//������Ʒ��һ���¼�¼
		PreparedStatement ps11 = connOra.prepareStatement(
				"insert into mbr_get_award (id,member_id,item_id,used_amount_exp,status,web_id,type )"
				+ " values (seq_mbr_get_award_id.nextval,(select id from mbr_members where netshop_id = ?),"
				+ " (select item_id from prd_items where item_code = ? ),?,?,?,0)");
		//���»�Ա����
		PreparedStatement ps12 = connOra.prepareStatement("update mbr_members set old_amount_exp = 0 where netshop_id = ? ");
		
		
		//������վ��Ʒ�����Ʒ״̬
		PreparedStatement ps2 = connSql.prepareStatement("update temp_shelf set changeflag = ? where id = ? ");
		PreparedStatement ps21 = connSql.prepareStatement("update temp_shelf set giftid = ?,point = ?,price=?,changeflag = ? where id = ? ");
		
		Statement st = connSql.createStatement();
		Statement st2 = connOra.createStatement();
		//����վ��Ʒ���е���Ʒ���в�ѯ����
		ResultSet rs = st.executeQuery(" select * from temp_shelf where typeflag = 0");
		while (rs.next()) {
			log.info("�����¼" + rs.getInt("id") + "\n");
			int id = rs.getInt("id");
			ps.setInt(1,id);
			//1 ����ü�¼�����´���, ��������ϼ�¼��״̬Ϊ���¼�¼��״̬
			ResultSet rs2 = ps.executeQuery();
			if( rs2.next()) {
				ps2.setInt(1,Function.sign(rs2.getInt("status")));
				ps2.setInt(2,id);
				ps2.executeUpdate();
			} else { // if the record does not exist
				try {
					//2 ��ѯ�û�Ա�Ƿ��������Ѿ��л��ֻ���Ʒ�ļ�¼
					ps1.setInt(1,rs.getInt("mbrid"));
					ResultSet rs3 = ps1.executeQuery();
					if( rs3.next()) {
						
						//3 �л��ֻ���Ʒ��¼�����µļ�¼���µ�����
						ps21.setString(1,rs3.getString("item_code"));
						ps21.setDouble(2,rs3.getDouble("used_amount_exp"));
						ps21.setDouble(3,rs3.getDouble("price"));
						ps21.setInt(4,Function.sign(rs3.getInt("status")));
						ps21.setInt(5,id);
						ps21.executeUpdate();
						
					// 4 ���������û��������һ����¼,ͬʱ���»�Ա������Ȼ���Ϊ0
					} else {
						ps11.setInt(1,rs.getInt("mbrid"));
						ps11.setString(2,rs.getString("giftid"));
						ps11.setInt(3,rs.getInt("point"));
						ps11.setInt(4,rs.getInt("typeflag"));
						ps11.setInt(5,id);
						ps11.executeUpdate();
						
						//
						ps12.setInt(1,rs.getInt("mbrid"));
						ps12.executeUpdate();
						
					}
					rs3.close();
				} catch(Exception e) {
					log.error("exception",e);
				}
				
			}
			rs2.close();
			
			
		}
		
	}
	
}
