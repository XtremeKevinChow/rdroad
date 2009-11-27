/*
 * Created on 2006-1-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.util.LabelValueBean;

import com.magic.crm.member.entity.MemberAddMoneyGiftSetup;
import com.magic.crm.member.form.MemberAddMoneyGiftSetupForm;

/**
 * @author 蟋蟀
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAddMoneyGiftSetupDAO implements Serializable {
	
	public MemberAddMoneyGiftSetupDAO() {
		
	}
	
	/** 新增记录 **/
	public void insert(Connection con, MemberAddMoneyGiftSetup data) throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		int newID = 0;
		try {
			String sql = "SELECT NVL(MAX(ID), 0) + 1 AS CURR_ID FROM MBR_MONEY_GIFT ";
			pstmt2 = con.prepareStatement(sql);
			rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				newID = rs2.getInt(1);
			}
		} catch (SQLException e) {

		} finally {
			if (rs2 != null)
				try {
					rs2.close();
				} catch (Exception e) {

				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (Exception e) {

				}
		}
		try {
			String insertSql = null;
			insertSql = "INSERT INTO MBR_MONEY_GIFT (ID,  MONEY, gift_number,keep_days, STATUS, CREATE_DATE, OPERATOR_ID) VALUES (?, ?, ?, ?, 0, sysdate, ?) ";
			pstmt = con.prepareStatement(insertSql.toString());
			pstmt.setInt(1, newID);
			pstmt.setDouble(2, data.getMoney());
			pstmt.setString(3, data.getGift_number());
			pstmt.setInt(4, data.getKeepDays());
			pstmt.setInt(5, data.getOperatorID());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if (pstmt != null)
				pstmt.close();

		}
	}
	
	/** 显示详情 **/
	public MemberAddMoneyGiftSetup showDetail(Connection con, MemberAddMoneyGiftSetupForm form)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberAddMoneyGiftSetup data = null;
		try {
			String sql = "SELECT a.ID, a.ITEM_ID, b.ITEM_CODE, a.MONEY, a.PRICE, a.STATUS, a.CREATE_DATE, a.OPERATOR_ID, c.NAME " +
			"FROM MBR_MONEY_GIFT a INNER JOIN PRD_ITEMS b ON a.ITEM_ID = b.ITEM_ID INNER JOIN ORG_PERSONS c ON a.OPERATOR_ID = c.ID " +
			"WHERE a.ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, form.getId());
			rs = pstmt.executeQuery();
			if (rs != null) {
				data = new MemberAddMoneyGiftSetup();
				while (rs.next()) {
					data.setId(rs.getInt("ID"));
					data.setItemID(rs.getInt("ITEM_ID"));
					data.setItemCode(rs.getString("ITEM_CODE"));
					data.setMoney(rs.getDouble("MONEY"));
					data.setPrice(rs.getDouble("PRICE"));
					data.setStatus(rs.getInt("STATUS"));
					data.setCreateDate(rs.getString("CREATE_DATE"));
					data.setOperatorID(rs.getInt("OPERATOR_ID"));
					data.setOperatorName(rs.getString("NAME"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return data;
	}
	
	
	/** 得到到保留天数 **/
	public static int getKeepDays(Connection con)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int rtn = 0;
		try {
			String sql = "SELECT VALUE FROM S_CONFIG_KEYS WHERE KEY = 'PREPAY_MONEY_GIFT_KEEP_DAY' AND COMPANY_ID = 1";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					rtn = rs.getInt("VALUE");
					
				}
				
			} else {
				return 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		
		return rtn;
	}
	
	/** 修改记录 **/
	public void update(Connection con, MemberAddMoneyGiftSetup data) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String updateSql = "UPDATE MBR_MONEY_GIFT SET ITEM_ID = ?, MONEY = ?, PRICE = ?, OPERATOR_ID = ? WHERE ID=? ";
			pstmt = con.prepareStatement(updateSql);
			pstmt.setInt(1, data.getItemID());
			pstmt.setDouble(2, data.getMoney());
			pstmt.setDouble(3, data.getPrice());
			pstmt.setInt(4, data.getOperatorID());
			pstmt.setInt(5, data.getId());
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					
				}
		}
	}

	/** 设置记录为无效 **/
	public void delete(Connection con, MemberAddMoneyGiftSetupForm form) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String delSql = "UPDATE MBR_MONEY_GIFT SET STATUS = -1 WHERE ID = ?";
			pstmt = con.prepareStatement(delSql);
			pstmt.setInt(1, form.getId());
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
	
	/** 查询所有记录(不分页) **/
	public Collection getList(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection gift = new ArrayList();

		try {
			int days = getKeepDays(con);
			String sQuery = "SELECT a.ID, a.MONEY, a.gift_number,a.keep_days,a.STATUS,b.gift_money,b.start_date,b.end_date FROM MBR_MONEY_GIFT a "+
			"INNER JOIN mbr_gift_certificates b ON a.gift_number = b.gift_number ORDER BY a.STATUS DESC, a.MONEY ASC";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				MemberAddMoneyGiftSetup info = new MemberAddMoneyGiftSetup();
				info.setId(rs.getInt("ID"));
				//info.setItemID(rs.getInt("ITEM_ID"));
				//info.setItemCode(rs.getString("ITEM_CODE"));
				//info.setItemName(rs.getString("NAME"));
				info.setMoney(rs.getDouble("MONEY"));
				//info.setPrice(rs.getDouble("PRICE"));
				info.setStatus(rs.getInt("STATUS"));
				info.setKeepDays(rs.getInt("keep_days"));
				info.setGift_number(rs.getString("gift_number"));
				info.setGift_money(rs.getDouble("gift_money"));
				info.setGift_start_date(rs.getString("start_date").substring(0,10));
				info.setGift_end_date(rs.getString("end_date").substring(0,10));
				
				gift.add(info);
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return gift;
	}
	
	/** 判断产品是否存在 **/
	public boolean hasItemID(Connection con, MemberAddMoneyGiftSetup form) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT ID FROM MBR_MONEY_GIFT WHERE ITEM_ID = ? AND ID <> ? AND STATUS = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, form.getItemID());
			pstmt.setInt(2, form.getId());
			rs = pstmt.executeQuery();
			if(rs.next()) {//有记录
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				}catch(Exception e){
					
				}
			if (pstmt != null)
				
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return false;
	}
	/** 判断金额已经设置 **/
	public boolean hasMoney(Connection con, MemberAddMoneyGiftSetup form) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT ID FROM MBR_MONEY_GIFT WHERE MONEY = ? AND ID <>? AND STATUS = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, form.getMoney());
			pstmt.setInt(2, form.getId());
			rs = pstmt.executeQuery();
			if(rs.next()) {//有记录
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				}catch(Exception e){
					
				}
			if (pstmt != null)
				
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return false;
	}
	
	public static ArrayList listAvailabeGiftNumber(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList gifts= new ArrayList();

		try {
			String sQuery = " select gift_number from mbr_gift_certificates " +
					" where gift_type=5 and start_date <= trunc(sysdate) and end_date> = trunc(sysdate)" +
					" order by gift_number desc ";
			
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				LabelValueBean gift = new LabelValueBean();
				gift.setLabel(rs.getString("gift_number"));
				gift.setValue(rs.getString("gift_number"));
				gifts.add(gift);
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return gifts;
	}
}
