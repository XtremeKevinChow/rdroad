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
	 * 礼品取消
	 * modified by user 2007-12-19
	 * @param con
	 * @param param
	 * @return 0 - 成功, -2 - 数据库异常
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
		    /** step 0: 判断状态 **/
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
			/*if (award.getSellType() == 6) { //如果销售方式是几分兑换
			    
				*//** step 1: 更新会员帐户积分(增加) **//*
				sql = "UPDATE MBR_MEMBERS SET AMOUNT_EXP = AMOUNT_EXP - ? WHERE ID = ? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setDouble(1, -award.getAmountExp());
				pstmt.setInt(2, award.getMemberID());
				count = pstmt.executeUpdate();
				pstmt.close();

				*//** step 2: 插入积分历史表(正积分) **//*
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
				pstmt.setInt(2, ExpExchangeForm.EXP_CANCEL);//积分取消
				pstmt.setInt(3, 1);
				pstmt.setString(4, award.getOperatorName());
				pstmt.setDouble(5, award.getAmountExp());//正的积分
				pstmt.setInt(6, award.getMemberID());
				count = pstmt.executeUpdate();
				pstmt.close();
			}
			*/
			/** step 3: 将暂存架上的礼品置为无效 **/
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
	 * 积分取消(用于1次性兑换)
	 * 
	 * @param con
	 * @param param
	 * @return 0 - 成功, -2 - 数据库异常
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
		    /** step 0: 判断状态 **/
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
			/*if (award.getSellType() == 6) { //如果销售方式是几分兑换
				Date now = DateUtil.getSqlDate();
		        if (now.after(DateUtil.getSqlDate(DateUtil.getDate("2007-03-01", "yyyy-MM-dd"))) || now.before(DateUtil.getSqlDate(DateUtil.getDate("2007-01-01", "yyyy-MM-dd")))  ) {
		    		return -3;
		    	}
				*//** step 1: 更新会员帐户积分(增加) **//*
				sql = "UPDATE MBR_MEMBERS SET OLD_AMOUNT_EXP = OLD_AMOUNT_EXP - ? WHERE ID = ? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setDouble(1, -award.getAmountExp());
				pstmt.setInt(2, award.getMemberID());
				count = pstmt.executeUpdate();
				pstmt.close();

				*//** step 2: 插入积分历史表(正积分) **//*
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
				pstmt.setInt(2, ExpExchangeForm.EXP_CANCEL);//积分取消
				pstmt.setInt(3, 1);
				pstmt.setString(4, "一次性兑换");
				pstmt.setDouble(5, award.getAmountExp());//正的积分
				pstmt.setInt(6, award.getMemberID());
				count = pstmt.executeUpdate();
				pstmt.close();
			}
			*/
			/** step 3: 将暂存架上的礼品置为无效 **/
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