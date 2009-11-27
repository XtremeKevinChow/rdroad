package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MbrGiftListDAO {

	/**
	 * ����˽����ȯ��ʵ��ȯ��
	 * @param conn
	 * @param gift_lot
	 * @return
	 * @throws Exception
	 */
	public static String generateGiftNumber(Connection conn, String gift_lot) throws Exception {
		String ret = "";
		PreparedStatement ps = null;
		String sql ="select round(dbms_random.value(10000000, 99999999)) from dual";
		String sql1 = " insert into mbr_gift_lists(id,GIFT_NO,gift_number,isneedpass,create_person,create_date)" +
				" values(sql_MBR_GIFT_LISTS_id.Nextval,?,?,0,0,sysdate) ";
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ret = rs.getString(1);
			}
			rs.close();
			ps.close();
			
			ret = gift_lot + ret;
			ps = conn.prepareStatement(sql1);
			ps.setString(1,ret);
			ps.setString(2, gift_lot);
			int r =ps.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {ps.close();} catch(Exception e){}
		}
		
		return ret;
	}
	
	/**
	 * �����ȯ��˽����ȯ���ǹ�����ȯ
	 * @param conn
	 * @param gift_number ��Ҫ������ȯ��
	 * @return int -1,��ȯ������ 1,��ȯ�ӱ����,2,��ȯ���ڵ��ӱ�����,4,������ȯ,5,˽����ȯ,6,˽�в�����ȯ
	 * @throws Exception
	 */
	public static int checkGiftNumber(Connection conn, String gift_number) throws Exception {
		int ret1 = 0,ret2 = 0;
		PreparedStatement ps = null;
		String sql ="select count(*) from mbr_gift_lists where gift_no =?";
		String sql1 = "select count(*) from MBR_GIFT_CERTIFICATES where gift_number =? ";
		String sql2 = "select gift_type from mbr_gift_certificates where gift_number = ( "
			+ " select gift_number from mbr_gift_lists where gift_no = ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, gift_number);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ret1 = rs.getInt(1);
			}
			rs.close();
			ps.close();
			if(ret1>0 ) {
				ps = conn.prepareStatement(sql2);
				ps.setString(1, gift_number);
				rs = ps.executeQuery();
				if(rs.next()) {
					ret1 = rs.getInt("gift_type");
				}
				rs.close();
				ps.close();
				
				return ret1;

			}
			
			ps = conn.prepareStatement(sql1);
			ps.setString(1,gift_number);
			rs = ps.executeQuery();
			if(rs.next()) {
				ret2 = rs.getInt(1);
			}
			rs.close();
			ps.close();
			if(ret2>0 ) {
				return 2;
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		
		return -1;
	}
}
