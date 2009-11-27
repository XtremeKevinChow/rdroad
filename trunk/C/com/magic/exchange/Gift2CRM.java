/*
 * Created on 2005-4-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.exchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.magic.crm.util.DBManager2;
import com.magic.crm.util.DBManagerMS;

/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class Gift2CRM {
	private static Logger log = Logger.getLogger(Gift2CRM.class);
	int suc_count =0, fail_count=0;
	private void execute() {
		Connection conOra = null;
		Connection conMs = null;
		try {
			conOra = DBManager2.getConnection();
			PreparedStatement psOraUpdate = conOra.prepareStatement("update mbr_get_award set status=? where web_id = ?");
			PreparedStatement psOraInsert = conOra.prepareStatement(
					"insert into mbr_get_award (id,member_id,item_id,used_amount_exp,status,web_id,type,quantity,price )"
					+ " values (seq_mbr_get_award_id.nextval,(select id from mbr_members where netshop_id = ?),"
					+ " (select item_id from prd_items where item_code = ? ),?,?,?,?,?,?)");
			conMs = DBManagerMS.getConnection();
			PreparedStatement ps = conMs.prepareStatement("update temp_shelf set modiflag =0 where id = ?");
			Statement st = conMs.createStatement();
			//1 �õ�������Ҫ���µ����µ���Ʒ��Ϣ.
			ResultSet rs = st.executeQuery(" select * from temp_shelf where modiflag = 1");
			while (rs.next()) {
				GiftInfo gift = new GiftInfo();
				gift.setWeb_id(rs.getInt("id"));
				gift.setMember_web_id(rs.getInt("mbrid"));
				gift.setItem_code(rs.getString("giftid"));
				gift.setExp(rs.getInt("point"));
				gift.setPrice(rs.getDouble("price"));
				gift.setType(rs.getInt("typeflag"));
				gift.setQuantity(1);
				gift.setStatus(rs.getInt("changeflag"));
				
				try {
					//2 ���µ�����,�������Ϊ��,�����
					psOraUpdate.setInt(1,gift.getStatus());
					psOraUpdate.setInt(2,gift.getWeb_id());
					if (psOraUpdate.executeUpdate() <1) {
						psOraInsert.setInt(1,gift.getMember_web_id());
						psOraInsert.setString(2,gift.getItem_code());
						psOraInsert.setInt(3,gift.getExp());
						psOraInsert.setInt(4,gift.getStatus());
						psOraInsert.setInt(5,gift.getWeb_id());
						psOraInsert.setInt(6,gift.getType());
						psOraInsert.setInt(7,gift.getQuantity());
						psOraInsert.setDouble(8,gift.getPrice());
						psOraInsert.executeUpdate();
					}
					
					ps.setInt(1,rs.getInt("id"));
					ps.executeUpdate();
					suc_count++;
					log.info("--������Ʒ��crm\t�ɹ�" + suc_count + "\tʧ��"+ fail_count);
				} catch(Exception e) {
					fail_count++;
					log.error("--������Ʒ��CRM����--\tidΪ"+ rs.getInt("id"));
					log.error("--����ԭ��--",e);
				}
			}
			rs.close();
			st.close();
			ps.close();
			psOraUpdate.close();
			psOraInsert.close();
		} catch(Exception e) {
			log.error("--��Ʒ���µ�CRM����--",e);
		} finally {
			try { conOra.close(); } catch(Exception e) {}
			try { conMs.close(); } catch(Exception e) {}
		}
		
		
	}
}
