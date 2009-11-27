/*
 * Created on 2006-2-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.award.bo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.magic.crm.member.form.MbrGetAwardForm;
import com.magic.crm.util.DateUtil;
import com.magic.crm.award.form.AwardForm;



/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AwardBO implements Serializable {

	
	
	/**
	 * ��Ʒȡ��
	 * modified by user 2007-12-19
	 * @param con
	 * @param param
	 * @return 0 - �ɹ�, -2 - ���ݿ��쳣
	 * @throws SQLException
	 */
	public int cancel(Connection con, AwardForm award)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		int newID = 0;
		
		try {
		    /** step 0: �ж�״̬ **/
		    int status = -100;
			pstmt = con.prepareStatement("select status from mbr_get_award where id = ?");
			pstmt.setInt(1, award.getId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
			    status = rs.getInt(1);
			    if (status != 0) {
			        return -2;
			    }
			}
			/*if (award.getSellType() == 6) { //������۷�ʽ�Ǽ��ֶһ�
			    
				*//** step 1: ���»�Ա�ʻ�����(����) **//*
				sql = "UPDATE MBR_MEMBERS SET AMOUNT_EXP = AMOUNT_EXP - ? WHERE ID = ? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setDouble(1, -award.getAmountExp());
				pstmt.setInt(2, award.getMemberID());
				count = pstmt.executeUpdate();
				pstmt.close();

				*//** step 2: ���������ʷ��(������) **//*
				sql = "SELECT SEQ_MBR_EXP_EXCHANGE_HIS.nextval FROM DUAL ";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					newID = rs.getInt(1);
				}
				pstmt.close();

				sql = "INSERT INTO MBR_EXP_EXCHANGE_HIS ( ID, OP_TYPE, ISVALID, OPERATOR_NAME, EXP, CREATE_DATE, MEMBER_ID ) "
						+ "VALUES( ?, ?, ?, ?, ?, sysdate, ? )";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, newID);
				pstmt.setInt(2, ExpExchangeForm.EXP_CANCEL);//����ȡ��
				pstmt.setInt(3, 1);
				pstmt.setString(4, award.getOperatorName());
				pstmt.setDouble(5, award.getAmountExp());//���Ļ���
				pstmt.setInt(6, award.getMemberID());
				count = pstmt.executeUpdate();
				pstmt.close();
			}
			*/
			/** step 3: ���ݴ���ϵ���Ʒ��Ϊ��Ч **/
			sql = "UPDATE MBR_GET_AWARD SET STATUS = -1 WHERE ID = ? AND STATUS = 0 ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, award.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
			if ( count <= 0 ) {
			    return -2;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {

				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {

				}
			}
		}
		return 0;
	}

	/**
	 * ����ȡ��(����1���Զһ�)
	 * 
	 * @param con
	 * @param param
	 * @return 0 - �ɹ�, -2 - ���ݿ��쳣
	 * @throws SQLException
	 */
	public int cancel2(Connection con, AwardForm award)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		int newID = 0;
		
		try {
		    /** step 0: �ж�״̬ **/
		    int status = -100;
			pstmt = con.prepareStatement("select status from mbr_get_award where id = ?");
			pstmt.setInt(1, award.getId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
			    status = rs.getInt(1);
			    if (status != 0) {
			        return -2;
			    }
			}
			/*if (award.getSellType() == 6) { //������۷�ʽ�Ǽ��ֶһ�
				Date now = DateUtil.getSqlDate();
		        if (now.after(DateUtil.getSqlDate(DateUtil.getDate("2007-03-01", "yyyy-MM-dd"))) || now.before(DateUtil.getSqlDate(DateUtil.getDate("2007-01-01", "yyyy-MM-dd")))  ) {
		    		return -3;
		    	}
				*//** step 1: ���»�Ա�ʻ�����(����) **//*
				sql = "UPDATE MBR_MEMBERS SET OLD_AMOUNT_EXP = OLD_AMOUNT_EXP - ? WHERE ID = ? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setDouble(1, -award.getAmountExp());
				pstmt.setInt(2, award.getMemberID());
				count = pstmt.executeUpdate();
				pstmt.close();

				*//** step 2: ���������ʷ��(������) **//*
				sql = "SELECT SEQ_MBR_EXP_EXCHANGE_HIS.nextval FROM DUAL ";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					newID = rs.getInt(1);
				}
				pstmt.close();

				sql = "INSERT INTO MBR_EXP_EXCHANGE_HIS ( ID, OP_TYPE, ISVALID, OPERATOR_NAME, EXP, CREATE_DATE, MEMBER_ID ) "
						+ "VALUES( ?, ?, ?, ?, ?, sysdate, ? )";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, newID);
				pstmt.setInt(2, ExpExchangeForm.EXP_CANCEL);//����ȡ��
				pstmt.setInt(3, 1);
				pstmt.setString(4, "һ���Զһ�");
				pstmt.setDouble(5, award.getAmountExp());//���Ļ���
				pstmt.setInt(6, award.getMemberID());
				count = pstmt.executeUpdate();
				pstmt.close();
			}
			*/
			/** step 3: ���ݴ���ϵ���Ʒ��Ϊ��Ч **/
			sql = "UPDATE MBR_GET_AWARD SET STATUS = -1 WHERE ID = ? AND STATUS = 0 ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, award.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
			if ( count <= 0 ) {
			    return -2;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {

				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {

				}
			}
		}
		return 0;
	}
}