/*
 * Created on 2006-2-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.member.form.MbrGetAwardForm;
import com.magic.crm.member.form.ExpExchangeHisForm;
import com.magic.crm.member.form.MemberMoneyDrawbackForm;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.promotion.dao.ExpExchangeActivityDAO;
import com.magic.crm.promotion.dao.ExpExchangePackageDAO;
import com.magic.crm.promotion.dao.MbrGiftListDAO;
import com.magic.crm.promotion.entity.ExpExchangeActivity;
import com.magic.crm.promotion.entity.ExpExchangePackageDtl;
import com.magic.crm.promotion.entity.ExpExchangeStepDtl;
import com.magic.crm.promotion.entity.ExpExchangeStepMst;
import com.magic.crm.util.DateUtil;
import com.magic.crm.util.Message;
import com.magic.crm.common.pager.CompSQL;

/**
 * @author user
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MbrGetAwardDAO2 implements Serializable {

	/** 通过会员卡号取得会员积分帐户信息 * */
	public MbrGetAwardForm getMemberInfoByCardID(Connection con,
			MbrGetAwardForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MbrGetAwardForm data = null;
		try {
			String sql = "SELECT ID, CARD_ID, NAME, AMOUNT_EXP FROM MBR_MEMBERS WHERE CARD_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getCardID());
			rs = pstmt.executeQuery();
			if (rs != null) {

				while (rs.next()) {
					data = new MbrGetAwardForm();
					data.setMemberID(rs.getLong("ID"));
					data.setCardID(rs.getString("CARD_ID"));
					data.setMemberName(rs.getString("NAME"));
					data.setAmountExp(rs.getInt("AMOUNT_EXP"));
				}
			} else {
				return null;
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

	/** 通过会员卡号取得会员积分帐户信息 * */
	public MbrGetAwardForm getMemberInfoByCardID2(Connection con,
			MbrGetAwardForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MbrGetAwardForm data = null;
		try {
			String sql = "SELECT ID, CARD_ID, NAME, OLD_AMOUNT_EXP FROM MBR_MEMBERS WHERE CARD_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getCardID());
			rs = pstmt.executeQuery();
			if (rs != null) {

				while (rs.next()) {
					data = new MbrGetAwardForm();
					data.setMemberID(rs.getLong("ID"));
					data.setCardID(rs.getString("CARD_ID"));
					data.setMemberName(rs.getString("NAME"));
					data.setAmountExp(rs.getInt("OLD_AMOUNT_EXP"));
				}
			} else {
				return null;
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

	/**
	 * 积分兑换
	 * 
	 * @param con
	 * @param param
	 * @return 暂存架ID - 成功, -1 - 帐户余额不足, -2 - 数据库异常
	 * @throws SQLException
	 */
	public int expChange_bak(Connection con, MbrGetAwardForm param)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		int newID = 0;
		int awardID = 0;
		try {

			/** step 1: 更新会员帐户积分(减少) * */
			String sql = "UPDATE MBR_MEMBERS SET AMOUNT_EXP = AMOUNT_EXP - ? WHERE CARD_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getExpExchange());
			pstmt.setString(2, param.getCardID());
			count = pstmt.executeUpdate();
			pstmt.close();

			/** step 2: 插入积分历史表(负积分) * */
			sql = "SELECT SEQ_MBR_EXP_EXCHANGE_HIS.nextval FROM DUAL ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				newID = rs.getInt(1);
			}
			pstmt.close();

			sql = "INSERT INTO MBR_EXP_EXCHANGE_HIS ( ID, OP_TYPE, ISVALID, OPERATOR_NAME, EXP, CREATE_DATE, MEMBER_ID ) "
					+ "VALUES( ?, ?, ?, ?, ?, sysdate ,? )";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newID);
			pstmt.setInt(2, MbrGetAwardForm.EXP_CHANGE);// 积分兑换
			pstmt.setInt(3, 1);
			pstmt.setString(4, param.getOperatorName());
			pstmt.setInt(5, -param.getExpExchange());// 负的积分
			pstmt.setLong(6, param.getMemberID());
			count = pstmt.executeUpdate();
			pstmt.close();

			/** step 3.0: 得到暂存架ID * */
			sql = "SELECT SEQ_MBR_GET_AWARD_ID.nextval FROM DUAL ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				awardID = rs.getInt(1);
			}
			pstmt.close();

			/** step 3: 将礼品插入暂存架 * */
			sql = "INSERT INTO MBR_GET_AWARD ( ID, MEMBER_ID, gift_number, PRICE, QUANTITY, USED_AMOUNT_EXP, OPERATOR_ID, TYPE ,CREATE_DATE, LAST_DATE ) "
					+ "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ? )";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, awardID);
			pstmt.setLong(2, param.getMemberID());
			pstmt.setString(3, param.getGift_number());
			pstmt.setDouble(4, param.getExchangePrice());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, param.getExpExchange());
			pstmt.setInt(7, param.getOperatorID());
			pstmt.setInt(8, 6); // 销售方式
			pstmt.setDate(9, param.getLastDate());
			count = pstmt.executeUpdate();
			pstmt.close();

			/** step4: 判断积分帐户是否为负数 * */
			sql = "SELECT AMOUNT_EXP FROM MBR_MEMBERS WHERE CARD_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getCardID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) < 0) {

					// System.out.println("积分帐户余额不足, 兑换后的余额为：" + rs.getInt(1));
					return -1;
				}
			}
			if (pstmt != null) {
				pstmt.close();
			}

		} catch (SQLException e) {
			// con.rollback();
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
		return awardID;
	}

	/**
	 * 一次性兑换积分取消 modified by user 2007-12-19
	 * 
	 * @param con
	 * @param param
	 * @return 0 - 成功, 2 - 数据库异常
	 * @throws SQLException
	 */
	public int expCancel2(Connection con, MbrGetAwardForm param)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		int newID = 0;

		try {

			/** step 0: 判断状态 * */
			int status = -100;
			pstmt = con
					.prepareStatement("select status from mbr_get_award where id = ?");
			pstmt.setInt(1, param.getAwardID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				status = rs.getInt(1);
				if (status != 0) {
					return 2;
				}
			}
			/*
			 * if (param.getSellType() == 6) { //如果销售方式是几分兑换 Date now =
			 * DateUtil.getSqlDate(); if
			 * (now.after(DateUtil.getSqlDate(DateUtil.getDate("2007-03-01",
			 * "yyyy-MM-dd"))) ||
			 * now.before(DateUtil.getSqlDate(DateUtil.getDate("2007-01-01",
			 * "yyyy-MM-dd"))) ) { return 3; }
			 *//** step 1: 更新会员帐户积分(增加) * */
			/*
			 * sql =
			 * "UPDATE MBR_MEMBERS SET OLD_AMOUNT_EXP = OLD_AMOUNT_EXP - ? WHERE CARD_ID = ? "
			 * ; pstmt = con.prepareStatement(sql); pstmt.setInt(1,
			 * -param.getExpExchange()); pstmt.setString(2, param.getCardID());
			 * count = pstmt.executeUpdate(); pstmt.close();
			 *//** step 2: 插入积分历史表(正积分) * */
			/*
			 * sql = "SELECT SEQ_MBR_EXP_EXCHANGE_HIS.nextval FROM DUAL "; pstmt
			 * = con.prepareStatement(sql); rs = pstmt.executeQuery(); if
			 * (rs.next()) { newID = rs.getInt(1); } pstmt.close();
			 * 
			 * sql =
			 * "INSERT INTO MBR_EXP_EXCHANGE_HIS ( ID, OP_TYPE, ISVALID, OPERATOR_NAME, EXP, CREATE_DATE, MEMBER_ID ) "
			 * + "VALUES( ?, ?, ?, ?, ?, sysdate, ? )"; pstmt =
			 * con.prepareStatement(sql); pstmt.setInt(1, newID);
			 * pstmt.setInt(2, ExpExchangeForm.EXP_CANCEL);//积分取消
			 * pstmt.setInt(3, 1); pstmt.setString(4, "一次性兑换"); pstmt.setInt(5,
			 * param.getExpExchange());//正的积分 pstmt.setLong(6,
			 * param.getMemberID()); count = pstmt.executeUpdate();
			 * pstmt.close(); }
			 */
			/** step 3: 将暂存架上的礼品置为无效 * */
			sql = "UPDATE MBR_GET_AWARD SET STATUS = -1, operator_id = ?  WHERE ID = ? AND STATUS = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getOperatorID());
			pstmt.setInt(2, param.getAwardID());
			count = pstmt.executeUpdate();
			pstmt.close();
			if (count <= 0) {
				throw new SQLException("状态不对");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
			return 2;
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
	 * 积分取消 modified by user 2007-12-19
	 * 
	 * @param con
	 * @param param
	 * @return 0 - 成功, 2 - 数据库异常
	 * @throws SQLException
	 */
	public int expCancel(Connection con, MbrGetAwardForm param)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		int newID = 0;

		try {
			/** step 0: 判断状态 * */
			int status = -100;
			pstmt = con
					.prepareStatement("select status from mbr_get_award where id = ?");
			pstmt.setInt(1, param.getAwardID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				status = rs.getInt(1);
				if (status != 0) {
					return 2;
				}
			}
			/*
			 * if (param.getSellType() == 6) { //如果销售方式是几分兑换
			 *//** step 1: 更新会员帐户积分(增加) * */
			/*
			 * sql =
			 * "UPDATE MBR_MEMBERS SET AMOUNT_EXP = AMOUNT_EXP - ? WHERE CARD_ID = ? "
			 * ; pstmt = con.prepareStatement(sql); pstmt.setInt(1,
			 * -param.getExpExchange()); pstmt.setString(2, param.getCardID());
			 * count = pstmt.executeUpdate(); pstmt.close();
			 *//** step 2: 插入积分历史表(正积分) * */
			/*
			 * sql = "SELECT SEQ_MBR_EXP_EXCHANGE_HIS.nextval FROM DUAL "; pstmt
			 * = con.prepareStatement(sql); rs = pstmt.executeQuery(); if
			 * (rs.next()) { newID = rs.getInt(1); } pstmt.close();
			 * 
			 * sql =
			 * "INSERT INTO MBR_EXP_EXCHANGE_HIS ( ID, OP_TYPE, ISVALID, OPERATOR_NAME, EXP, CREATE_DATE, MEMBER_ID ) "
			 * + "VALUES( ?, ?, ?, ?, ?, sysdate, ? )"; pstmt =
			 * con.prepareStatement(sql); pstmt.setInt(1, newID);
			 * pstmt.setInt(2, ExpExchangeForm.EXP_CANCEL);//积分取消
			 * pstmt.setInt(3, 1); pstmt.setString(4, param.getOperatorName());
			 * pstmt.setInt(5, param.getExpExchange());//正的积分 pstmt.setLong(6,
			 * param.getMemberID()); count = pstmt.executeUpdate();
			 * pstmt.close(); }
			 */

			/** step 3: 将暂存架上的礼品置为无效 * */
			sql = "UPDATE MBR_GET_AWARD SET STATUS = -1 WHERE ID = ? AND STATUS = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getAwardID());
			count = pstmt.executeUpdate();
			pstmt.close();
			if (count <= 0) {
				throw new SQLException("状态不对");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
			return 2;
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
	 * 取消礼券
	 * 
	 * @param con
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public int cancelGiftNumber(Connection con, MbrGetAwardForm param)
			throws SQLException {

		PreparedStatement pstmt = null;
		String sql = null;
		int count = 0;
		

		try {

			/** step 3: 将暂存架上的礼券置为无效 * */
			sql = "UPDATE mbr_gift_ticket_use SET STATUS = -1 WHERE ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getAwardID());
			count = pstmt.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
			return 2;
		} finally {
			
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
	 * 更新礼券使用次数
	 * @param con
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public static int useGiftNumber(Connection con, MemberAWARD info)
			throws SQLException {

		PreparedStatement pstmt = null;
		
		String sql = null;
		int count = 0;
		try {

			sql = "UPDATE mbr_gift_ticket_use SET num = num + 1 WHERE ID = ? and status=0 and num < total_num";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, info.getID());
			count = pstmt.executeUpdate();
			pstmt.close();
			
			sql = "update mbr_gift_ticket_use set status=1 where id = ? and num = total_num ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, info.getID());
			count = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
			return 2;
		} finally {
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
	 * 返回总的记录数
	 * 
	 * @param conn
	 *            Connection
	 * @throws SQLException
	 * @return int
	 */
	public int getRsTotalCount(Connection con, ExpExchangeHisForm form)
			throws SQLException {
		if (form.getMemberID() <= 0) {
			form.setMemberID(MemberDAO.getMemberID(con, form.getCardID()));
		}
		int rsCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		/*
		 * if (memberID == 0) {//没找到会员 return 0; }
		 */
		StringBuffer sQuery = new StringBuffer();
		sQuery.append("SELECT COUNT(*) as total ");
		sQuery.append("FROM MBR_EXP_EXCHANGE_HIS WHERE 1 = 1 ");
		if (form.getMemberID() != 0) {
			sQuery.append("AND MEMBER_ID = " + form.getMemberID());
		}
		if (form.getStartDate() != null && !form.getStartDate().equals("")) {
			sQuery.append(" AND CREATE_DATE >= date'" + form.getStartDate()
					+ "'");
		}
		if (form.getEndDate() != null && !form.getEndDate().equals("")) {
			sQuery.append(" AND CREATE_DATE <= date'" + form.getEndDate()
					+ "' + 1");
		}
		if (form.getOpType() != 0) {
			sQuery.append(" AND OP_TYPE = " + form.getOpType());
		}
		if (form.getIsvalid() != -1) {
			sQuery.append(" AND ISVALID = " + form.getIsvalid());
		}
		// sQuery.append(" ORDER BY CREATE_DATE DESC ");
		try {
			pstmt = con.prepareStatement(sQuery.toString());
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				rsCount = rs.getInt("total");
			}
			return rsCount;

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
		}
	}

	/** 查询所有积分历史记录(分页),积分兑换列表查询的时候也调用这个方法 * */
	public Collection getHisList(Connection con, ExpExchangeHisForm form)
			throws SQLException {
		if (form.getMemberID() <= 0) {
			form.setMemberID(MemberDAO.getMemberID(con, form.getCardID()));
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection gift = new ArrayList();
		/*
		 * if (memberID == 0) {//没找到会员 return gift; }
		 */
		StringBuffer sQuery = new StringBuffer();
		try {
			sQuery
					.append("SELECT ID, DOC_NUMBER, OP_TYPE, ISVALID, OPERATOR_NAME, EXP, TOTAL_EXP, MEMBER_ID, CREATE_DATE ");
			sQuery.append("FROM MBR_EXP_EXCHANGE_HIS WHERE 1 = 1 ");
			if (form.getMemberID() != 0) {
				sQuery.append("AND MEMBER_ID = " + form.getMemberID());
			}
			if (form.getStartDate() != null && !form.getStartDate().equals("")) {
				sQuery.append(" AND CREATE_DATE >= date'" + form.getStartDate()
						+ "'");// DateUtil.getDate(form.getCreateDate(),
				// "yyyy-MM-dd")
			}
			if (form.getEndDate() != null && !form.getEndDate().equals("")) {
				sQuery.append(" AND CREATE_DATE <= date'" + form.getEndDate()
						+ "' + 1");// DateUtil.getDate(form.getEndDate(),
				// "yyyy-MM-dd")
			}
			if (form.getOpType() != 0) {
				sQuery.append(" AND OP_TYPE = " + form.getOpType());
			}
			if (form.getIsvalid() != -1) {
				sQuery.append(" AND ISVALID = " + form.getIsvalid());
			}
			sQuery.append(" ORDER BY CREATE_DATE DESC ");

			String sql = CompSQL.getNewSql(sQuery.toString());
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, form.getPager().getOffset()
					+ form.getPager().getLength());
			pstmt.setInt(2, form.getPager().getOffset());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				ExpExchangeHisForm info = new ExpExchangeHisForm();
				info.setID(rs.getInt("ID"));
				info.setDocNumber(rs.getString("DOC_NUMBER"));
				info.setOpType(rs.getInt("OP_TYPE"));
				info.setIsvalid(rs.getInt("ISVALID"));
				info.setOperatorName(rs.getString("OPERATOR_NAME"));
				info.setExp(rs.getInt("EXP"));
				info.setTotalExp(rs.getInt("TOTAL_EXP"));
				info.setMemberID(rs.getLong("MEMBER_ID"));
				info.setCreateDate(rs.getString("CREATE_DATE"));
				String cardID = MemberDAO.getCard_ID(con, (int) info
						.getMemberID());
				info.setCardID(cardID);

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

	/** 新增会员礼品 * */
	public int insertGift(Connection con, MbrGetAwardForm data)
			throws SQLException {
		int ret = 0;
		PreparedStatement pstmt = null;

		ResultSet rs2 = null;
		int newID = 0;
		try {
			String sql = "";// "SELECT SEQ_MBR_GET_AWARD_ID.NEXTVAL FROM DUAL";
			if (!"".equals(data.getColor_code())
					&& !"".equals(data.getSize_code())
					&& !"".equals(data.getItemCode())) {
				sql = " select sku_id from prd_item_sku where itm_code=? and color_code=? and size_code =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, data.getItemCode());
				pstmt.setString(2, data.getColor_code());
				pstmt.setString(3, data.getSize_code());
				rs2 = pstmt.executeQuery();
				if (rs2.next()) {
					data.setSku_id(rs2.getInt(1));
				} else {
					ret = -1;
				}
				rs2.close();
				pstmt.close();

				if (ret == -1) {
					return ret;
				}
			}

			String insertSql = "INSERT INTO MBR_GET_AWARD(ID, MEMBER_ID,sku_id,item_code,PRICE, "
					+ " color_code,OPERATOR_ID,  TYPE, LAST_DATE ,DESCRIPTION )"
					+ " VALUES (SEQ_MBR_GET_AWARD_ID.NEXTVAL,?, ?, ?, ?, ?,?, 13, sysdate + ? ,? )";
			pstmt = con.prepareStatement(insertSql);
			pstmt.setLong(1, data.getMemberID());
			pstmt.setLong(2, data.getSku_id());
			pstmt.setString(3, data.getItemCode());
			pstmt.setDouble(4, data.getExchangePrice());
			pstmt.setString(5, data.getColor_code());
			// pstmt.setString(6, data.getSize_code());
			pstmt.setInt(6, data.getOperatorID());
			pstmt.setInt(7, data.getAvailDay());
			pstmt.setString(8, data.getDescription());
			ret = pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ret;
	}

	/** 新增会员礼品 * */
	public int insertUnauditGift(Connection con, MbrGetAwardForm data)
			throws SQLException {
		int ret = 0;
		PreparedStatement pstmt = null;

		ResultSet rs2 = null;
		int newID = 0;
		try {
			String sql = "";// "SELECT SEQ_MBR_GET_AWARD_ID.NEXTVAL FROM DUAL";
			if (!"".equals(data.getColor_code())
					&& !"".equals(data.getSize_code())
					&& !"".equals(data.getItemCode())) {
				sql = " select sku_id from prd_item_sku where itm_code=? and color_code=? and size_code =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, data.getItemCode());
				pstmt.setString(2, data.getColor_code());
				pstmt.setString(3, data.getSize_code());
				rs2 = pstmt.executeQuery();
				if (rs2.next()) {
					data.setSku_id(rs2.getInt(1));
				} else {
					ret = -1;
				}
				rs2.close();
				pstmt.close();

				if (ret == -1) {
					return ret;
				}
			}

			String insertSql = "INSERT INTO MBR_GET_AWARD_audit(id,MEMBER_ID,sku_id,item_code,PRICE,quantity, "
					+ " color_code,creator, LAST_DATE ,status,DESCRIPTION )"
					+ " VALUES (seq_mbr_get_award_audit_id.nextval,?, ?, ?, ?,?,?,?, sysdate + ? ,0,? )";
			pstmt = con.prepareStatement(insertSql);
			pstmt.setLong(1, data.getMemberID());
			pstmt.setLong(2, data.getSku_id());
			pstmt.setString(3, data.getItemCode());
			pstmt.setDouble(4, data.getExchangePrice());
			pstmt.setInt(5,data.getTotal_num());
			pstmt.setString(6, data.getColor_code());
			pstmt.setInt(7, data.getOperatorID());
			pstmt.setInt(8, data.getAvailDay());
			pstmt.setString(9, data.getDescription());
			ret = pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ret;
	}
	
	/** 新增会员礼券 * */
	public void insertGiftNumber(Connection con, MbrGetAwardForm data)
			throws Exception {
		PreparedStatement pstmt = null;

		ResultSet rs2 = null;
		int newID = 0;
		int total_num;
		try {

			String sql = "select t1.person_num,t1.amount,t1.end_date from mbr_gift_certificates t1 "
					+ " join mbr_gift_lists t2 on t2.gift_number=t1.gift_number "
					+ " where t2.gift_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data.getGift_number());
			rs2 = pstmt.executeQuery();
			if (rs2.next()) {
				total_num = rs2.getInt("person_num");
				data.setTotal_num(total_num);

			}
			rs2.close();
			pstmt.close();

			String insertSql = "INSERT INTO mbr_gift_ticket_use(ID, mbrid, ticket_num,"
					+ " OPERATOR_ID,  sell_TYPE, DESCRIPTION,total_num,num )"
					+ " VALUES (SEQ_MBR_GET_AWARD_ID.NEXTVAL, ?, ?, ?, 13, ? ,?,0)";
			pstmt = con.prepareStatement(insertSql);
			pstmt.setLong(1, data.getMemberID());
			// pstmt.setLong(2, data.getSku_id());
			pstmt.setString(2, data.getGift_number());
			pstmt.setInt(3, data.getOperatorID());
			
			pstmt.setString(4, data.getDescription());
			pstmt.setInt(5, data.getTotal_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

			if (pstmt != null)
				pstmt.close();

		}
	}

	/** 新增会员礼券 * */
	/*public void insertMscNumber(Connection con, MbrGetAwardForm data)
			throws Exception {
		PreparedStatement pstmt = null;

		ResultSet rs2 = null;
		int newID = 0;
		try {
			String sql = "select nvl(max(gift_type),0) from mbr_gift_certificates where gift_number=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data.getGift_number());
			rs2 = pstmt.executeQuery();
			if (rs2.next()) {
				int r = rs2.getInt(1);
				if (r == 5) {
					data.setGift_number(MbrGiftListDAO.generateGiftNumber(con,
							data.getGift_number()));
				} else if (r == 0) {
					throw new Exception("no exists gift number");
				}
			}
			rs2.close();
			pstmt.close();

			String insertSql = "INSERT INTO MBR_GET_AWARD(ID, MEMBER_ID, gift_number,"
					+ " OPERATOR_ID,  TYPE, LAST_DATE ,DESCRIPTION )"
					+ " VALUES (SEQ_MBR_GET_AWARD_ID.NEXTVAL, ?, ?, ?, 13, sysdate + ? ,? )";
			pstmt = con.prepareStatement(insertSql);
			pstmt.setLong(1, data.getMemberID());
			// pstmt.setLong(2, data.getSku_id());
			pstmt.setString(2, data.getGift_number());
			pstmt.setInt(3, data.getOperatorID());
			pstmt.setInt(4, data.getAvailDay());
			pstmt.setString(5, data.getDescription());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

			if (pstmt != null)
				pstmt.close();

		}
	}*/

	/**
	 * 得到积分兑换礼品保留天数
	 * 
	 * @param con
	 * @return days
	 * @throws SQLException
	 */
	public static int getExchangeGiftKeepDay(Connection con)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int days = 0;
		try {
			String sql = "SELECT VALUE FROM S_CONFIG_KEYS WHERE KEY = 'EXCHANGE_GIFT_KEEP_DAY'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				days = Integer.parseInt(rs.getString(1));
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
		return days;
	}

	/**
	 * 2006年度积分一次性兑换
	 * 
	 * @param conn
	 * @param param
	 * @throws SQLException
	 */
	public static int expChange2(Connection con, MbrGetAwardForm param)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		int newID = 0;
		int awardID = 0;
		int yearExp = 0;

		try {
			/** step 0: 取出积分 **/
			String sql = "select old_amount_exp from mbr_members WHERE CARD_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getCardID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				yearExp = rs.getInt("old_amount_exp");
			}
			rs.close();
			pstmt.close();
			if (yearExp <= 0) {// 没有了积分
				return -1;
			}

			/** step 1: 更新会员帐户积分 * */
			sql = "UPDATE MBR_MEMBERS SET OLD_AMOUNT_EXP = 0 WHERE CARD_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getCardID());
			count = pstmt.executeUpdate();
			pstmt.close();

			/** step 2: 插入积分历史表(负积分) * */

			sql = "SELECT SEQ_MBR_EXP_EXCHANGE_HIS.nextval FROM DUAL ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				newID = rs.getInt(1);
			}
			pstmt.close();

			sql = "INSERT INTO MBR_EXP_EXCHANGE_HIS ( ID, OP_TYPE, ISVALID, OPERATOR_NAME, EXP, CREATE_DATE, MEMBER_ID ) "
					+ "VALUES( ?, ?, ?, ?, ?, sysdate ,? )";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newID);
			pstmt.setInt(2, MbrGetAwardForm.EXP_CHANGE);// 积分兑换
			pstmt.setInt(3, 1);
			pstmt.setString(4, "一次性兑换");
			pstmt.setInt(5, -yearExp);// 负的积分
			pstmt.setLong(6, param.getMemberID());
			count = pstmt.executeUpdate();
			pstmt.close();

			/** step 3.0: 得到暂存架ID * */
			sql = "SELECT SEQ_MBR_GET_AWARD_ID.nextval FROM DUAL ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				awardID = rs.getInt(1);
			}
			pstmt.close();

			/** step 3: 将礼品插入暂存架 * */
			sql = "INSERT INTO MBR_GET_AWARD ( ID, MEMBER_ID, ITEM_ID, PRICE, QUANTITY, USED_AMOUNT_EXP, OPERATOR_ID, TYPE ,CREATE_DATE, LAST_DATE ) "
					+ "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ? )";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, awardID);
			pstmt.setLong(2, param.getMemberID());
			// pstmt.setInt(3, param.getItemID());
			pstmt.setDouble(4, param.getExchangePrice());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, yearExp);
			pstmt.setInt(7, param.getOperatorID());
			pstmt.setInt(8, 6); // 销售方式
			pstmt.setDate(9, param.getLastDate());
			count = pstmt.executeUpdate();
			pstmt.close();

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
		return awardID;
	}

	/**
	 * 2007-12-18 created by user 积分兑换
	 * 
	 * @param conn
	 * @param param
	 * @throws SQLException
	 */
	public static int expChange(Connection con, MbrGetAwardForm param)
			throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer awardsBuff = new StringBuffer();
		int newID = 0;
		int awardID = 0;
		int usedExp = 0;
		String sql = null;
		try {
			if (param.getMemberID() <= 0) {
				throw new Exception("内存中的会员主键丢失，可能本次会话出了问题");
			}
			/* 得到积分活动 */
			ExpExchangeStepDtl stepDtl = ExpExchangeActivityDAO
					.findByStepDtlPk(con, param.getStepDtlId());
			ExpExchangeStepMst stepMst = ExpExchangeActivityDAO
					.findByStepMstPk(con, stepDtl.getStepMst().getId());
			ExpExchangeActivity activity = ExpExchangeActivityDAO
					.findByActivityPk(con, stepMst.getActivity()
							.getActivityNo());
			String exchangeType = activity.getExchangeType();// 兑换方式
			String dealType = activity.getDealType();// 积分处理方式
			int exp = stepMst.getBeginExp();

			/* 取出积分 */
			sql = "select old_amount_exp, amount_exp from mbr_members WHERE CARD_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getCardID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (exchangeType.equals("A")) {// 一次性
					usedExp = rs.getInt("old_amount_exp");
				} else if (exchangeType.equals("B")) {// 实时
					usedExp = rs.getInt("amount_exp");
				} else {
					return -2;
				}

			}
			rs.close();
			pstmt.close();
			if (usedExp <= 0) {// 没有了积分
				return -1;
			}

			/* 更新会员帐户积分 */
			if (exchangeType.equals("A")) {// 一次性
				if (dealType.equals("A")) {// 清零
					sql = "UPDATE MBR_MEMBERS SET OLD_AMOUNT_EXP = 0 WHERE CARD_ID = ? ";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, param.getCardID());
					pstmt.execute();
					pstmt.close();
				} else if (dealType.equals("B")) { // 可多次兑换
					if (usedExp < exp) { // 不够
						return -1;
					}
					sql = "UPDATE MBR_MEMBERS SET OLD_AMOUNT_EXP = old_amount_exp - ? WHERE CARD_ID = ? ";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, exp);
					pstmt.setString(2, param.getCardID());
					pstmt.execute();
					pstmt.close();
					usedExp = exp;
				} else {
					return -2;
				}

			} else if (exchangeType.equals("B")) {// 实时
				if (usedExp < exp) { // 不够
					return -1;
				}
				sql = "UPDATE MBR_MEMBERS SET AMOUNT_EXP = AMOUNT_EXP - ? WHERE CARD_ID = ? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, exp);
				pstmt.setString(2, param.getCardID());
				pstmt.execute();
				pstmt.close();
				usedExp = exp;
			} else {
				return -2;
			}

			/* 插入积分历史表(负积分) */
			sql = "INSERT INTO MBR_EXP_EXCHANGE_HIS ( ID, OP_TYPE, ISVALID, OPERATOR_NAME, EXP, CREATE_DATE, MEMBER_ID, step_dtl_id ) "
					+ "VALUES( SEQ_MBR_EXP_EXCHANGE_HIS.nextval, ?, ?, ?, ?, sysdate ,?, ? )";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newID);
			pstmt.setInt(1, 1);// 积分兑换
			pstmt.setInt(2, 1);
			pstmt.setInt(3, param.getOperatorID());// 操作人
			pstmt.setInt(4, -usedExp);// 负的积分（一次性积分，实时积分）
			pstmt.setLong(5, param.getMemberID());
			pstmt.setLong(6, param.getStepDtlId()); // 记录积分档次id
			pstmt.execute();
			pstmt.close();

			/* 将礼品、礼券插入暂存架 */
			if (stepDtl.getStepType().equals("G")) {// 礼品
				sql = "INSERT INTO MBR_GET_AWARD ( ID, MEMBER_ID, item_code, PRICE, QUANTITY, "
					+ "USED_AMOUNT_EXP, OPERATOR_ID, TYPE ,CREATE_DATE, LAST_DATE) "
					+ "VALUES( SEQ_MBR_GET_AWARD_ID.nextval, ?, ?, ?, ?, ?, ?, ?, sysdate, ? )";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, param.getMemberID());
				//int itemId = ProductDAO.getItemID(con, stepDtl.getNo());
				pstmt.setString(2,stepDtl.getNo());
				pstmt.setDouble(3, stepDtl.getAddMoney());
				pstmt.setInt(4, 1);// ??????????????是否加一个数量字段
				pstmt.setInt(5, usedExp);
				pstmt.setInt(6, param.getOperatorID());
				pstmt.setInt(7, 6); // 销售方式
				pstmt.setDate(8, activity.getGiftLastDate());// 终止日期
				pstmt.executeUpdate();
				pstmt.close();
			} else if (stepDtl.getStepType().equals("T")) {// 礼券
				sql = "insert into mbr_gift_ticket_use(id,mbrid,ticket_num,num,sell_type,used_amount_exp,operator_id) "
					+ "values(SEQ_MBR_GET_AWARD_ID.nextval,?,?,0,?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, param.getMemberID());
				//pstmt.setString(2, stepDtl.getNo());
				pstmt.setInt(4, usedExp);
				pstmt.setInt(5, param.getOperatorID());
				pstmt.setInt(3, 6); // 销售方式
				
				//根据礼券批号产生对应的礼券号
				String gift_no = MbrGiftListDAO.generateGiftNumber(con, stepDtl.getNo());
				pstmt.setString(2, gift_no);// 礼券号
				
				pstmt.executeUpdate();
				pstmt.close();
				
			} else {
				return -2;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return -3;
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
	 * 得到暂存架id
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	/*public static int getAwardId(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int awardId = 0;
		try {
			// 得到暂存架ID 
			String sql = "SELECT SEQ_MBR_GET_AWARD_ID.nextval FROM DUAL ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				awardId = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}
		return awardId;
	}*/
	
    public static ArrayList listAudit(Connection conn ) throws SQLException {
        ArrayList ret = new ArrayList();
    	PreparedStatement ps = null;
        String sql = "select t1.*,t2.card_id,t2.name as mbr_name,t3.name as operator_name,t4.itm_name " +
        		" from mbr_get_award_audit t1 join mbr_members t2 on t1.member_id = t2.id" +
        		" left join org_persons t3 on t1.creator = t3.id " +
        		" left join prd_item t4 on t1.item_code = t4.itm_code " + 
        		" where t1.status=0 order by t1.create_date asc ";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	MbrGetAwardForm info = new MbrGetAwardForm();
            	info.setAwardID(rs.getInt("id"));
            	info.setCardID(rs.getString("card_id"));
            	info.setMemberName(rs.getString("mbr_name"));
            	info.setOperatorName(rs.getString("operator_name"));
            	info.setCreate_date(rs.getString("create_date"));
            	//info.setStatus(rs.getInt("status"));
            	info.setDescription(rs.getString("description"));
            	info.setItemCode(rs.getString("item_code"));
            	info.setItemName(rs.getString("itm_name"));
            	info.setExchangePrice(rs.getDouble("price"));
            	info.setTotal_num(rs.getInt("quantity"));
            	ret.add(info);
            }
        	rs.close();
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException ex) {}
        }
        return ret;
    }

    public static int audit(Connection conn ,MbrGetAwardForm info ) throws SQLException {
        int ret = 0;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
            //step1 插入暂存架
    		String sql = "insert into mbr_get_award(id,member_id,sku_id,item_code,color_code," +
    				" price,quantity,create_date,operator_id,last_date,type,description) " +
    				" select seq_mbr_get_award_id.nextval,member_id,sku_id,item_code,color_code," +
    				" price,quantity,create_date,creator,last_date,13,description from mbr_get_award_audit where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, info.getAwardID());
            ps.executeUpdate();
            ps.close();
    		
            //step2 更新记录的状态
            sql = "update mbr_get_award_audit set status=1, auditor=?,audit_date=sysdate where id =?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, info.getOperatorID());
            ps.setLong(2, info.getAwardID());
            ps.executeUpdate();
            ps.close();
            ps = null;
            
 		} catch (SQLException ex) {
 			conn.rollback();
            throw ex;
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException ex) {}
        }
        return ret;
    }
    
    public static int cancelAudit(Connection conn ,MbrGetAwardForm info ) throws SQLException {
        int ret = 0;
    	PreparedStatement ps = null;
        String sql = "update mbr_get_award_audit set status=-1, auditor=?,audit_date=sysdate where id =?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, info.getOperatorID());
            ps.setLong(2, info.getAwardID());
            ret = ps.executeUpdate();
            
            ps.close();
            ps = null;
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException ex) {}
        }
        return ret;
    }
}