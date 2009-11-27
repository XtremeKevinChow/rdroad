/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.utils.Arith;
import com.magic.crm.member.entity.MembeMoneyHistory;
import com.magic.crm.common.CharacterFormat;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.member.form.MemberaddMoneyForm;
import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.common.pager.CompSQL;
import com.magic.crm.common.pager.PagerForm;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.common.SequenceManager;
import com.magic.crm.util.CodeName;

/**
 * @author user1 TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberaddMoneyDAO {
	public MemberaddMoneyDAO() {

	}
	/**
	 * 新增汇款记录
	 * @param conn
	 * @param memberaddMoney
	 * @throws SQLException
	 */
	public void insert(Connection conn, MemberaddMoney memberaddMoney)
			throws SQLException {
		PreparedStatement pstmt = null;
		
		try {
			String sQuery = "INSERT INTO MBR_MONEY_INPUT"
					+ "(ID, MB_ID, MB_CODE, ORDER_CODE, ORDER_ID, "
					+ "REMARK, MONEY, REF_ID, TYPE, STATUS, "
					+ "OPERATOR_ID, POSTCODE, USE_TYPE, BILL_DATE, PAY_METHOD, "
					+ "MBR_NAME, MBR_ADDRESS)"
					+ " VALUES(seq_MBR_MONEY_INPUT_ID.nextval, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, "
					+ "?, ?, ?, to_date(?, 'yyyy-mm-dd'), ?, "
					+ "?, ?)";
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, memberaddMoney.getMB_ID());
			pstmt.setString(2, memberaddMoney.getMB_CODE());
			pstmt.setString(3, memberaddMoney.getORDER_CODE());
			pstmt.setInt(4, memberaddMoney.getORDER_ID());
			pstmt.setString(5, memberaddMoney.getREMARK());
			pstmt.setDouble(6, Arith
					.round((memberaddMoney.getMONEY() * 100), 0));
			if (memberaddMoney.getREF_ID() == null
					|| memberaddMoney.getREF_ID().trim().length() == 0) {
				int seq = SequenceManager.getNextVal(conn,
						"SEQ_MBR_MONEY_INPUT_REFID");
				pstmt.setString(7, String.valueOf(seq));
			} else {
				pstmt.setString(7, memberaddMoney.getREF_ID().trim());
			}

			pstmt.setString(8, memberaddMoney.getTYPE());
			pstmt.setInt(9, memberaddMoney.getStatus());
			pstmt.setInt(10, memberaddMoney.getOPERATOR_ID());
			//字段"post_date"实际上已废弃，现在使用"bill_date"
			pstmt.setString(11,
					(memberaddMoney.getPostCode() == null || memberaddMoney
							.getPostCode().length() > 6) ? "000000"
							: memberaddMoney.getPostCode());
			pstmt.setString(12, memberaddMoney.getUSE_TYPE());
			pstmt.setString(13, memberaddMoney.getBill_date());
			pstmt.setInt(14, memberaddMoney.getPayMethod());
			pstmt.setString(15, memberaddMoney.getMB_NAME());
			pstmt.setString(16, memberaddMoney.getADDRESS());
			pstmt.execute();

		} catch (SQLException e) {
			System.out.println("memberaddMoneyDAO.insert 出错");
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	/**
	 * 根据主键查找单据
	 * 
	 * @param conn
	 * @return
	 */
	public MemberaddMoney findRecordByPK(Connection conn, int id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberaddMoney data = null;
		String sql = "select * from mbr_money_input where id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				data = new MemberaddMoney();
				data.setID(rs.getInt("id"));
				data.setMB_ID(rs.getInt("mb_id"));
				
				/*String mbCode = rs.getString("mb_code");
				if ( mbCode != null && mbCode.trim().length() > 0 ) {
					if (mbCode.trim().indexOf("+") != -1) {
						int pos = mbCode.trim().indexOf("+");
						data.setMB_NAME(mbCode.substring(pos + 1));
						data.setMB_CODE(mbCode.substring(0, pos));
					}
					
				}*/
				data.setMB_CODE(rs.getString("mb_code"));
				data.setMB_NAME(rs.getString("mbr_name"));
				data.setORDER_CODE(rs.getString("order_code"));
				data.setORDER_ID(rs.getInt("order_id"));
				data.setADDRESS(rs.getString("mbr_address"));
				data.setCREATE_DATE(rs.getString("create_date"));
				data.setREMARK(rs.getString("remark"));
				data.setMONEY(rs.getDouble("money")/100);
				data.setStatus(rs.getInt("status"));
				data.setREF_ID(rs.getString("ref_id"));
				data.setTYPE(rs.getString("type"));
				data.setOPERATOR_ID(rs.getInt("operator_id"));
				data.setPostCode(rs.getString("postcode"));
				data.setUSE_TYPE(rs.getString("use_type"));
				data.setPayMethod(rs.getInt("pay_method"));
				
				if (rs.getString("bill_date") != null && rs.getString("bill_date").length() >= 10) {
					data.setBill_date(rs.getString("bill_date").substring(0,10));
				}
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
		return data;
	}
	/**
	 * 根据条件查询
	 * 
	 * @param conn
	 * @return coll
	 */
	public Collection findRecordsByCondition (Connection conn, MemberaddMoneyForm param)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select * from mbr_money_input where 1 = 1 "
			+ "and create_date >= to_date(?, 'yyyy-mm-dd') "
			+ "and create_date < to_date(?, 'yyyy-mm-dd') + 1 "
			+ "and status = ? and type = ?  ";
		if (param.getSearchRefId() != null && param.getSearchRefId().trim().length() > 0) {
			sql += "and ref_id = '" + param.getSearchRefId() + "' ";
		}
		if (param.getSearchMbName() != null && param.getSearchMbName().trim().length() > 0) {
			sql += "and mbr_name = '" + param.getSearchMbName() + "' ";
		}
		if (param.getSearchUseType() != null && param.getSearchUseType().trim().length() > 0) {
			sql += "and use_type = '" + param.getSearchUseType() + "' ";
		}
		if (param.getSearchPayMethod() != -1) {
			sql += "and pay_method = " + param.getSearchPayMethod();
		}
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param.getCREATE_DATE());
			pstmt.setString(2, param.getCREATE_DATE());
			pstmt.setString(3, String.valueOf(param.getStatus()));
			pstmt.setString(4, param.getTYPE());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberaddMoney data = new MemberaddMoney();
				data.setID(rs.getInt("ID"));
				data.setMB_ID(rs.getInt("mb_id"));
				/*String mbCode = rs.getString("mb_code");
				if ( mbCode != null && mbCode.trim().length() > 0 ) {
					if (mbCode.trim().indexOf("+") != -1) {
						int pos = mbCode.trim().indexOf("+");
						data.setMB_NAME(mbCode.substring(pos + 1));
						data.setMB_CODE(mbCode.substring(0, pos));
					}
					
				}*/
				data.setMB_CODE(rs.getString("mb_code"));
				data.setMB_NAME(rs.getString("mbr_name"));
				data.setORDER_CODE(rs.getString("order_code"));
				data.setORDER_ID(rs.getInt("order_id"));
				data.setADDRESS(rs.getString("mbr_address"));
				data.setCREATE_DATE(rs.getString("create_date"));
				data.setREMARK(rs.getString("remark"));
				data.setMONEY(rs.getDouble("money")/100);
				data.setStatus(rs.getInt("status"));
				data.setREF_ID(rs.getString("ref_id"));
				data.setTYPE(rs.getString("type"));
				data.setOPERATOR_ID(rs.getInt("operator_id"));
				data.setPostCode(rs.getString("postcode"));
				data.setUSE_TYPE(rs.getString("use_type"));
				data.setPayMethod(rs.getInt("pay_method"));
				if (rs.getString("bill_date") != null && rs.getString("bill_date").length() >= 10) {
					data.setBill_date(rs.getString("bill_date").substring(0,10));
				}
				coll.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return coll;
	}
	
	/**
	 * 查询书香卡记录
	 * 
	 * @param conn
	 * @return coll
	 */
	public Collection findSxkRecordsByCondition (Connection conn, MemberaddMoneyForm param)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select * from mbr_money_input where 1 = 1 "
			+ "and create_date >= to_date(?, 'yyyy-mm-dd') "
			+ "and create_date < to_date(?, 'yyyy-mm-dd') + 1 "
			+ "and use_type = '1' ";
		if (param.getSearchRefId() != null && param.getSearchRefId().trim().length() > 0) {
			sql += "and ref_id = '" + param.getSearchRefId() + "' ";
		}
		if (param.getSearchMbName() != null && param.getSearchMbName().trim().length() > 0) {
			sql += "and mbr_name = '" + param.getSearchMbName() + "' ";
		}
		
		if (param.getSearchPayMethod() != -1) {
			sql += "and pay_method = " + param.getSearchPayMethod();
		}
		if (param.getStatus() != -1) {
			sql += "and status = '" + param.getStatus() + "' ";
		}
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param.getBeginDate());
			pstmt.setString(2, param.getEndDate());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberaddMoney data = new MemberaddMoney();
				data.setID(rs.getInt("ID"));
				data.setMB_ID(rs.getInt("mb_id"));
				data.setMB_CODE(rs.getString("mb_code"));
				data.setMB_NAME(rs.getString("mbr_name"));
				data.setORDER_CODE(rs.getString("order_code"));
				data.setORDER_ID(rs.getInt("order_id"));
				data.setADDRESS(rs.getString("mbr_address"));
				data.setCREATE_DATE(rs.getString("create_date"));
				data.setREMARK(rs.getString("remark"));
				data.setMONEY(rs.getDouble("money")/100);
				data.setStatus(rs.getInt("status"));
				data.setREF_ID(rs.getString("ref_id"));
				data.setTYPE(rs.getString("type"));
				data.setOPERATOR_ID(rs.getInt("operator_id"));
				data.setPostCode(rs.getString("postcode"));
				data.setUSE_TYPE(rs.getString("use_type"));
				data.setPayMethod(rs.getInt("pay_method"));
				if (rs.getString("bill_date") != null && rs.getString("bill_date").length() >= 10) {
					data.setBill_date(rs.getString("bill_date").substring(0,10));
				}
				coll.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return coll;
	}
	/**
	 * 更新单据(线下现金、回单)
	 * @param conn
	 * @param memberaddMoney
	 * @throws SQLException
	 */
	public void update2(Connection conn, MemberaddMoney memberaddMoney)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "update  MBR_MONEY_INPUT "
			+ "set mb_code = ?, order_code = ?, OPERATOR_ID = ?, "
			+ "money = ?, ref_id = ?, bill_date = to_date(?,'yyyy-mm-dd'), "
			+ "remark = ?, use_type = ?, pay_method = ?, mbr_name = ? "
			+ "where id = ? ";
		try {
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setString(1, memberaddMoney.getMB_CODE());
			pstmt.setString(2, memberaddMoney.getORDER_CODE());
			pstmt.setInt(3, memberaddMoney.getOPERATOR_ID());
			pstmt.setDouble(4, memberaddMoney.getMONEY()*100);
			if (memberaddMoney.getREF_ID() == null
					|| memberaddMoney.getREF_ID().trim().length() == 0) {
				int seq = SequenceManager.getNextVal(conn,
						"SEQ_MBR_MONEY_INPUT_REFID");
				pstmt.setString(5, String.valueOf(seq));
			} else {
				pstmt.setString(5, memberaddMoney.getREF_ID().trim());
			}
			//pstmt.setString(5, memberaddMoney.getREF_ID());
			pstmt.setString(6, memberaddMoney.getBill_date());
			pstmt.setString(7, memberaddMoney.getREMARK());
			pstmt.setString(8, memberaddMoney.getUSE_TYPE());
			pstmt.setInt(9, memberaddMoney.getPayMethod());
			pstmt.setString(10, memberaddMoney.getMB_NAME());
			pstmt.setInt(11, memberaddMoney.getID());
			pstmt.execute();

		} catch (SQLException e) {
			System.out.println("sql is error");
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
	/**
	 * 删除单据(线下现金、回单)
	 * @param conn
	 * @param memberaddMoney
	 * @throws SQLException
	 */
	public void delete(Connection conn, MemberaddMoneyForm param)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "delete from mbr_money_input where id = ? and status = 0 ";
		try {
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, param.getID());
			pstmt.execute();

		} catch (SQLException e) {
			System.out.println("sql is error");
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
	/*
	 * 入库后,要更新MBR_MONEY_INPUT的MB_ID,和OR_ID,status
	 */
	public static void update(Connection conn, MemberaddMoney memberaddMoney)
			throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String sQuery = "update  MBR_MONEY_INPUT set MB_ID=?,ORDER_ID=?,STATUS=?,OPERATOR_ID=? where id=?";

			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, memberaddMoney.getMB_ID());
			pstmt.setInt(2, memberaddMoney.getORDER_ID());
			pstmt.setInt(3, memberaddMoney.getStatus());
			pstmt.setInt(4, memberaddMoney.getOPERATOR_ID());
			pstmt.setInt(5, memberaddMoney.getID());

			pstmt.execute();

		} catch (SQLException e) {
			System.out.println("sql is error");
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	/*
	 * 查出ID,组成上传文件名
	 */
	public static int getImportFileId(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int fileId = 0;
		try {
			/**
			 * modified user 2008-03-25 新建一个汇款文件序列号采用：SEQ_MBR_MONEY_FILE_ID
			 */
			// String addMoneySql = "select seq_MBR_MONEY_INPUT_ID.nextval from
			// dual";
			String addMoneySql = "select SEQ_MBR_MONEY_FILE_ID.nextval from dual";
			pstmt = conn.prepareStatement(addMoneySql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				fileId = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("sql is error");
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
		return fileId;
	}

	/**
	 * 查询会员充值MBR_MONEY_INPUT
	 * @deprecated
	 * @param con
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public Collection QueryMemberAddMoney(Connection con, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "SELECT * from mbr_money_input " + condition;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberaddMoney addmoney = new MemberaddMoney();

				addmoney.setID(rs.getInt("id"));
				addmoney.setMB_ID(rs.getInt("MB_ID"));
				//addmoney.setMB_CODE(rs.getString("MB_CODE"));
				addmoney.setMB_NAME(rs.getString("MB_NAME"));
				addmoney.setMB_CODE(CharacterFormat.analyseNumber(rs
						.getString("MB_CODE"), 8));
				//addmoney.setORDER_CODE(rs.getString("ORDER_CODE"));
				addmoney.setORDER_CODE(CharacterFormat.analyseNumber(rs
						.getString("ORDER_CODE"), 12));
				addmoney.setORDER_ID(rs.getInt("ORDER_ID"));
				addmoney.setCREATE_DATE(rs.getString("CREATE_DATE"));
				addmoney.setREMARK(rs.getString("REMARK"));

				addmoney.setMONEY(rs.getDouble("MONEY") / 100);
				addmoney.setREF_ID(rs.getString("REF_ID"));
				addmoney.setTYPE(rs.getString("TYPE"));
				addmoney.setOPERATOR_ID(rs.getInt("OPERATOR_ID"));
				addmoney.setStatus(rs.getInt("STATUS"));
				addmoney.setPostDate(rs.getString("PostDate"));

				memberCol.add(addmoney);
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
		return memberCol;
	}

	/**
	 * 充值查询函数 截取会员号和订单号的说明： 1.根据查询条件查找相应数据
	 * 2.同时从MB_CODE字段中提取8位会员号，从ORDER_CODE中提取13位订单号(1字母+12数字)
	 * 3.如果会员号订单号都不能成功提取，从MB_CODE中提取中文姓名，从POSTCODE中提取邮编前4位
	 * 4.用提取的中文姓名和邮编前4位查找会员信息 5.如果找到一条，将会员号填充在文本框中，找不到或找到2条以上不做处理。
	 * 
	 * @param con
	 * @param pageModel
	 * @return
	 * @throws SQLException
	 */
	public Collection QueryMemberPrepay(Connection con, CommonPageUtil pageModel)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet rec = null;
		ArrayList memberCol = new ArrayList();
		pageModel.setPageSize(50);

		try {
			String sDate = (String) pageModel.getCondition().get("createDate"); // 充值日期
			String searchMbName = (String) pageModel.getCondition().get("searchMbName"); // 会员姓名
			String sref_id = (String) pageModel.getCondition().get("ref_id"); // 邮局汇号
			String import_type = (String) pageModel.getCondition().get(
					"import_type"); // 导入类型
			String status = (String) pageModel.getCondition().get("status"); // 状态
			String filter1 = (String)pageModel.getCondition().get("filter1");
			
			String sQuery = 
				"select id, mb_id, mb_code, order_code, order_id, "
					+ "create_date, remark, money, status, ref_id, "
					+ "type, operator_id, postdate, postcode, use_type, "
					+ "pay_method, trunc(bill_date) as b_date, mbr_name, mbr_address "
					+ "from mbr_money_input "
					+ "where create_date >= to_date(?,'yyyy-mm-dd') "
					+ "and create_date < to_date(?,'yyyy-mm-dd') + 1 "
					+ "and status = ? and use_type='0' "
					+ "and type in('0','2','3')"; //目前是三种格式
			String sqlCount = "select count(*), sum(MONEY) from mbr_money_input "
				+ "where create_date >= to_date(?,'yyyy-mm-dd') "
				+ "and create_date < to_date(?,'yyyy-mm-dd') + 1  "
				+ "and status = ? and use_type='0' and type in('0','2','3')";
			// String sqlamount = "select sum(MONEY) from mbr_money_input where
			// create_date >= to_date(?,'yyyy-mm-dd') and create_date <
			// to_date(?,'yyyy-mm-dd') + 1 and status = ? and use_type='0'";
			if (searchMbName != null && searchMbName.length() > 0) { // 会员姓名

				sQuery = sQuery + " and mbr_name like ? ";
				sqlCount = sqlCount + " and mbr_name like ? ";
				// sqlamount = sqlamount + " and mb_code like ?";
			}
			if (sref_id != null && sref_id.length() > 0) {// 邮局汇号

				sQuery = sQuery + " and ref_id = '" + sref_id + "' ";
				sqlCount = sqlCount + " and ref_id = '" + sref_id + "' ";
				// sqlamount = sqlamount + " and ref_id = '"+sref_id+"' ";
			}
			/**
			 * add by user 2008-03-25
			 */
			if (import_type != null && import_type.length() > 0) { // 导入类型
				sQuery = sQuery + " and type = '" + import_type + "' ";
				sqlCount = sqlCount + " and type = '" + import_type + "' ";
				// sqlamount = sqlamount + " and type = '"+import_type+"' ";
			}
			if (filter1 != null && filter1.length() > 0) {
				if (filter1.equals("0")) {
					
				}
				else if (filter1.equals("1")) {
					sQuery = sQuery + " and (mb_code is not null or order_code is not null) ";
					sqlCount = sqlCount + " and (mb_code is not null or order_code is not null) ";
				} else if (filter1.equals("2")) {
					sQuery = sQuery + " and mb_code is null and order_code is null ";
					sqlCount = sqlCount + " and mb_code is null and order_code is null ";
				}
			}
			 sQuery = sQuery + " order by order_code asc ";
			/**
			 * 获得满足查询条件的汇款总笔数
			 */
			pstmt = con.prepareStatement(sqlCount);
			pstmt.setString(1, sDate);
			pstmt.setString(2, sDate);
			pstmt.setString(3, status);
			if (searchMbName != null && searchMbName.length() > 0) {
				pstmt.setString(4, searchMbName);
			}

			rec = pstmt.executeQuery();
			int recordCount = 0;
			if (rec.next()) {
				recordCount = rec.getInt(1);
			}
			pageModel.setRecordCount(recordCount);// 记录总数
			pageModel.setAmount(rec.getInt(2) / 100);// 总金额
			rec.close();
			pstmt.close();
			/**
			 * 获得满足查询条件的汇款总金额
			 */
			/*
			 * pstmt = con.prepareStatement(sqlamount); pstmt.setString(1,
			 * sDate); pstmt.setString(2, sDate); pstmt.setString(3, status); if
			 * (smb_code != null && smb_code.length() > 0) { pstmt.setString(4,
			 * "%" + smb_code + "%"); } rec = pstmt.executeQuery(); float amount =
			 * 0; if (rec.next()) { amount = rec.getInt(1); }
			 * pageModel.setAmount(amount / 100); rec.close(); pstmt.close();
			 */
			/**
			 * 获得满足查询条件的所有汇款明细
			 */
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, sDate);
			pstmt.setString(2, sDate);
			pstmt.setString(3, status);
			if (searchMbName != null && searchMbName.length() > 0) {
				pstmt.setString(4, searchMbName);
			}
			rs = pstmt.executeQuery();
			int recNo = 0;
			//MemberDAO mbdao = new MemberDAO();
			//Member member = new Member();

			while (rs.next()) {
				if (recNo >= pageModel.getFrom() && recNo <= pageModel.getTo()) {
					MemberaddMoney addmoney = new MemberaddMoney();

					/*String mbCode1 = CharacterFormat.isNum(rs
							.getString("MB_CODE"), 8); // 从MB_CODE字段提取会员号(8位)
					String orderCode1 = CharacterFormat.isNum(rs
							.getString("ORDER_CODE"), 12); // 从ORDER_CODE字段中提取订单号(12位)
					String pcode = "";

					// 如果订单号和会员号提取不到，就要根据姓名(和邮编)来匹配
					Collection data = null;
					ArrayList list = null;
					Member mb = null;
					if ((orderCode1 == null || "".equals(orderCode1.trim()))
							&& (mbCode1 == null || "".equals(mbCode1.trim()))) { // 订单号会员号都是空

						String mbName = "";
						if (rs.getString("MB_CODE") != null) {
							mbName = rs.getString("MB_CODE").trim(); // 默认是MB_CODE
						}
						// 截取中文姓名
						String subName = mbName;

						if (mbName != null && !"".equals(mbName)) {
							int len = mbName.length();
							for (int i = 0; i < len; i++) {
								char c = mbName.charAt(i);
								if (c <= 128 || c == '＋' || c == '　'
										|| c == '［' || c == '（' || c == '＊'
										|| c == '｛') { // 中文字的ASCII码大于128
									subName = mbName.substring(0, i);
									break;
								}
							}

							if ("".equals(subName)) {
								for (int i = len - 1; i >= 0; i--) {
									char c = mbName.charAt(i);
									if (c <= 128 || c == '＋' || c == '　'
											|| c == '［' || c == '（' || c == '＊'
											|| c == '｛') {
										subName = mbName.substring(i + 1);
										break;
									}

								}
							}

						}

						member.setNAME("".equals(subName) ? "[=NULL=]"
								: subName);
						pcode = rs.getString("postcode");
						// pcode=(pcode==null)?"":pcode.substring(0,4);
						// 提取邮编前4位，否则邮编为空
						if (pcode == null) {
							pcode = "";
						} else {
							if (pcode.length() >= 4) {
								pcode = pcode.substring(0, 4);
							} else {
								pcode = "";
							}
						}
						member.setPostcode(pcode); // 取消了根据邮编匹配，原因是邮局提供的邮编不正确
						*//**
						 * modified by user 2005-11-14 14:15
						 *//*
						data = mbdao.QueryMembers(con, member);// 通过截取的姓名和邮编4位查找会员信息
						list = (ArrayList) data;

						if (list.size() == 0) {// 没有匹配

						} else if (list.size() == 1) {// 匹配到1个

							mb = (Member) list.get(0);
							mbCode1 = mb.getCARD_ID();
							addmoney.setMB_CODE(mbCode1);// 的到会员号（在输入框内填充）

						} else {// 匹配到多个就根据姓名和地址前4个中文字联合匹配

						}

					}
*/
					addmoney.setID(rs.getInt("ID"));
					addmoney.setMB_ID(rs.getInt("MB_ID"));
					addmoney.setMB_CODE(rs.getString("MB_CODE"));
					addmoney.setMB_NAME(rs.getString("MBR_NAME"));
					addmoney.setORDER_CODE(rs.getString("ORDER_CODE"));
					addmoney.setORDER_ID(rs.getInt("ORDER_ID"));
					addmoney.setCREATE_DATE(rs.getString("CREATE_DATE"));
					addmoney.setREMARK(rs.getString("REMARK"));
					addmoney.setMONEY(rs.getDouble("MONEY") / 100);
					addmoney.setStatus(rs.getInt("STATUS"));
					addmoney.setREF_ID(rs.getString("REF_ID"));
					addmoney.setTYPE(rs.getString("TYPE"));
					addmoney.setOPERATOR_ID(rs.getInt("OPERATOR_ID"));
					String postcode = rs.getString("POSTCODE");
					if (postcode != null && postcode.length()>=4) {
						postcode = postcode.substring(0, 4);
					}
					addmoney.setPostCode(postcode); // 邮编前4位用于在充款的时候点会员汇款姓名旁边的小按钮时查找会员信息
					addmoney.setUSE_TYPE(rs.getString("use_type"));
					addmoney.setPayMethod(rs.getInt("pay_method"));
					addmoney.setBill_date(rs.getDate("b_date")==null?null:rs.getDate("b_date").toString());
					addmoney.setADDRESS(rs.getString("mbr_address"));
					memberCol.add(addmoney);
				} else if (recNo > pageModel.getTo()) {
					break;
				}
				recNo++;
			}
			pageModel.setModelList(memberCol);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (rec != null)
				try {
					rec.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

		return memberCol;
	}

	/*
	 * 所有支付方式
	 */
	public Collection QueryPayMethod(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "select * from s_payment_method order by name ";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MembeMoneyHistory addmoney = new MembeMoneyHistory();
				addmoney.setPayMethod(rs.getInt("id"));
				addmoney.setPayMethodName(rs.getString("name"));
				memberCol.add(addmoney);
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
		return memberCol;
	}

	/*
	 * 会员帐户查询分页
	 */
	public Collection QueryMemberHistoryMoney(Connection con,
			CommonPageUtil pageModel) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet rec = null;
		ArrayList memberCol = new ArrayList();

		try {
			String modify_date = (String) pageModel.getCondition().get(
					"modify_date");
			String end_date = (String) pageModel.getCondition().get("end_date");
			
			String mb_code = (String) pageModel.getCondition().get("mb_code");
			// Date createDate = DateUtil.getSqlDate(DateUtil.getDate(sDate+"
			// 00:00 A.M.","yyyymmdd HH:MI A.M."));
			String payid = (String) pageModel.getCondition().get("payid");
			/*
			 *  
			 */
			String sqlCount = "select count(*) from mbr_members a,mbr_money_history b ,s_event_type c,org_persons d,s_payment_method e"
					+ " where a.id=b.member_id and b.event_type=c.id and d.id=b.operator_id and b.pay_method = e.id(+) ";
			if (mb_code != null && mb_code.length() > 0) {
				sqlCount += " and a.card_id='" + mb_code + "'";
			}
			if (modify_date != null && modify_date.length() > 0) {
				sqlCount += " and b.modify_date >= date'" + modify_date + "'";
				
			}
			if (end_date != null && end_date.length() > 0) {
				sqlCount += " and b.modify_date < date'" + end_date + "' + 1";
			}
			if (payid != null && Integer.parseInt(payid) > -1) {
				sqlCount += " and e.id=" + payid;
			}
			pstmt = con.prepareStatement(sqlCount);
			rec = pstmt.executeQuery();
			int recordCount = 0;
			if (rec.next()) {
				recordCount = rec.getInt(1);
			}
			pageModel.setRecordCount(recordCount);
			rec.close();
			pstmt.close();

			String sQuery = "select b.id,a.card_id,a.name,b.modify_date,"
					+ "b.money_update,b.deposit ,b.COMMENTS,b.member_id,"
					+ "b.event_type,b.pay_method,c.name,d.name,e.name, b.credence,(select name from s_flushbalance_source where id=b.flush_type) as flush_name "
					+ " from mbr_members a,mbr_money_history b ,s_event_type c,org_persons d,s_payment_method e"
					+ " where a.id=b.member_id and b.event_type=c.id and d.id=b.operator_id and b.pay_method = e.id(+) ";
			if (mb_code != null && mb_code.length() > 0) {
				sQuery += " and a.card_id='" + mb_code + "'";
			}
			if (modify_date != null && modify_date.length() > 0) {
				sQuery += " and b.modify_date >= date'" + modify_date + "'";
				
			}
			if (end_date != null && end_date.length() > 0) {
				sQuery += " and b.modify_date < date'" + end_date + "' + 1";
			}
			if (payid != null && Integer.parseInt(payid) > -1) {
				sQuery += " and e.id=" + payid;
			}
			sQuery += " order by b.id desc";
			pstmt = con.prepareStatement(sQuery);
			// System.out.println("sQuery is "+sQuery);
			rs = pstmt.executeQuery();
			int recNo = 0;
			while (rs.next()) {
				if (recNo >= pageModel.getFrom() && recNo <= pageModel.getTo()) {
					MembeMoneyHistory addmoney = new MembeMoneyHistory();

					addmoney.setID(rs.getInt(1));
					addmoney.setCARD_ID(rs.getString("card_id"));
					addmoney.setCARD_NAME(rs.getString(3));
					addmoney.setMODIFY_DATE(rs.getString("modify_date")
							.substring(0, 16));
					addmoney.setMONEY_UPDATE(rs.getDouble("money_update"));
					addmoney.setDEPOSIT(rs.getDouble("deposit"));
					addmoney.setCOMMENTS(rs.getString("COMMENTS"));
					addmoney.setMEMBER_ID(rs.getInt("member_id"));
					addmoney.setEVENT_TYPE(rs.getInt("event_type"));
					addmoney.setPayMethod(rs.getInt("pay_method"));

					if (Date
							.valueOf(addmoney.getMODIFY_DATE().substring(0, 10))
							.before(Date.valueOf("2006-12-21"))) {
						addmoney.setEVENT_TYPE_NAME(rs.getString(11));
					} else {
						addmoney.setEVENT_TYPE_NAME(rs.getString("flush_name"));
					}

					addmoney.setOPERATOR_NAME(rs.getString(12));
					addmoney.setPayMethodName(rs.getString(13));
					addmoney.setCredence(rs.getString("credence"));
					memberCol.add(addmoney);

				} else if (recNo > pageModel.getTo()) {
					break;
				}
				recNo++;
			}
			pageModel.setModelList(memberCol);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (rec != null)
				try {
					rec.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return memberCol;
	}

	/**
	 * 查找记录总数
	 * 
	 * @param conn
	 * @param stoNO
	 * @param itemID
	 * @throws SQLException
	 */
	public static int countRecordsByCondition(Connection conn, long memberId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;

		try {

			String sql = "select count(*) from mbr_money_history where member_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				cnt = rs.getInt(1);
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
		return cnt;
	}
	/**
	 * 查找记录总数
	 * 
	 * @param conn
	 * @param stoNO
	 * @param itemID
	 * @throws SQLException
	 */
	public static int countEmoneyRec(Connection conn, long memberId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;

		try {

			String sql = "select count(*) from mbr_emoney_history where member_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				cnt = rs.getInt(1);
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
		return cnt;
	}
	/**
	 * 查询当前会员帐户历史（分页）
	 * 
	 * @param con
	 * @param memberId
	 * @return
	 * @throws SQLException
	 */
	public static Collection getDepostHistoryByMember(Connection con,
			int memberId, PagerForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet rec = null;
		ArrayList memberCol = new ArrayList();

		try {

			String sQuery = "select b.id, a.card_id, a.name as member_name, b.modify_date,"
					+ " b.money_update,b.deposit ,b.COMMENTS, b.member_id,"
					+ " b.event_type, b.pay_method, c.name as even_type_name, d.name as operate_name, "
					+ " e.name as method_name, b.credence, "
					+ "(select name from s_flushbalance_source where id=b.flush_type) as flush_name "
					+ " from mbr_members a,mbr_money_history b ,s_event_type c,org_persons d,s_payment_method e"
					+ " where a.id=b.member_id and b.event_type=c.id and d.id(+)=b.operator_id and b.pay_method = e.id(+) "
					+ " and a.id = ? order by b.id desc ";

			sQuery = CompSQL.getNewSql(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, param.getPager().getOffset()
					+ param.getPager().getLength());
			pstmt.setInt(3, param.getPager().getOffset());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				MembeMoneyHistory addmoney = new MembeMoneyHistory();

				addmoney.setID(rs.getInt(1));
				addmoney.setCARD_ID(rs.getString("card_id"));
				addmoney.setCARD_NAME(rs.getString(3));
				addmoney.setMODIFY_DATE(rs.getString("modify_date").substring(
						0, 16));
				addmoney.setMONEY_UPDATE(rs.getDouble("money_update"));
				addmoney.setDEPOSIT(rs.getDouble("deposit"));
				addmoney.setCOMMENTS(rs.getString("COMMENTS"));
				addmoney.setMEMBER_ID(rs.getInt("member_id"));
				addmoney.setEVENT_TYPE(rs.getInt("event_type"));
				addmoney.setPayMethod(rs.getInt("pay_method"));

				if (Date.valueOf(addmoney.getMODIFY_DATE().substring(0, 10))
						.before(Date.valueOf("2006-12-21"))) {
					addmoney.setEVENT_TYPE_NAME(rs.getString(11));
				} else {
					addmoney.setEVENT_TYPE_NAME(rs.getString("flush_name"));
				}

				addmoney.setOPERATOR_NAME(rs.getString(12));
				addmoney.setPayMethodName(rs.getString(13));
				addmoney.setCredence(rs.getString("credence"));
				memberCol.add(addmoney);

			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (rec != null)
				try {
					rec.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return memberCol;
	}

	/**
	 * 查询当前会员礼金帐户历史（分页）
	 * 
	 * @param con
	 * @param memberId
	 * @return
	 * @throws SQLException
	 */
	public static Collection getEmoneyHistoryByMember(Connection con,
			int memberId, PagerForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet rec = null;
		ArrayList memberCol = new ArrayList();

		try {

			String sQuery = "select b.id, a.card_id, a.name as member_name, b.modify_date,"
					+ " b.money_update,b.deposit ,b.COMMENTS, b.member_id,"
					+ " b.event_type, b.pay_method, c.name as even_type_name, d.name as operate_name, "
					+ " e.name as method_name, b.credence, "
					+ "(select name from s_flushbalance_source where id=b.flush_type) as flush_name "
					+ " from mbr_members a,mbr_emoney_history b ,s_event_type c,org_persons d,s_payment_method e"
					+ " where a.id=b.member_id and b.event_type=c.id and d.id(+)=b.operator_id and b.pay_method = e.id(+) "
					+ " and a.id = ? order by b.id desc ";

			sQuery = CompSQL.getNewSql(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, param.getPager().getOffset()
					+ param.getPager().getLength());
			pstmt.setInt(3, param.getPager().getOffset());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				MembeMoneyHistory addmoney = new MembeMoneyHistory();

				addmoney.setID(rs.getInt(1));
				addmoney.setCARD_ID(rs.getString("card_id"));
				addmoney.setCARD_NAME(rs.getString(3));
				addmoney.setMODIFY_DATE(rs.getString("modify_date").substring(
						0, 16));
				addmoney.setMONEY_UPDATE(rs.getDouble("money_update"));
				addmoney.setDEPOSIT(rs.getDouble("deposit"));
				addmoney.setCOMMENTS(rs.getString("COMMENTS"));
				addmoney.setMEMBER_ID(rs.getInt("member_id"));
				addmoney.setEVENT_TYPE(rs.getInt("event_type"));
				addmoney.setPayMethod(rs.getInt("pay_method"));

				if (Date.valueOf(addmoney.getMODIFY_DATE().substring(0, 10))
						.before(Date.valueOf("2006-12-21"))) {
					addmoney.setEVENT_TYPE_NAME(rs.getString(11));
				} else {
					addmoney.setEVENT_TYPE_NAME(rs.getString("flush_name"));
				}

				addmoney.setOPERATOR_NAME(rs.getString(12));
				addmoney.setPayMethodName(rs.getString(13));
				addmoney.setCredence(rs.getString("credence"));
				memberCol.add(addmoney);

			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (rec != null)
				try {
					rec.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return memberCol;
	}
	/*
	 * 计算会员某天某个支付方式的总金额
	 */
	public double getSumMoney(Connection con, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double summoney = 0;
		try {

			String sQuery = "select sum(b.money_update) from mbr_members a,mbr_money_history b ,s_event_type c,org_persons d,s_payment_method e"
					+ " where a.id=b.member_id and b.event_type=c.id and d.id=b.operator_id and b.pay_method = e.id(+) "
					+ condition;
			sQuery += " order by b.id desc";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			int recNo = 0;
			if (rs.next()) {
				summoney = rs.getDouble(1);
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
		return summoney;
	}

	/*
	 * 会员帐户报表
	 */
	public Collection getMemberMoneyExcel(Connection con, String conndition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList memberCol = new ArrayList();
		try {

			String sQuery = "select b.id,a.card_id,a.name,b.modify_date,b.money_update,b.deposit ,b.COMMENTS,b.member_id,b.event_type,b.pay_method,c.name,d.name,e.name,(select name from s_flushbalance_source where id=b.flush_type) as flush_name "
					+ " from mbr_members a,mbr_money_history b ,s_event_type c,org_persons d,s_payment_method e"
					+ " where a.id=b.member_id and b.event_type=c.id and d.id=b.operator_id and b.pay_method = e.id(+) "
					+ conndition;
			sQuery += " order by b.id desc";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MembeMoneyHistory addmoney = new MembeMoneyHistory();

				addmoney.setID(rs.getInt(1));
				addmoney.setCARD_ID(rs.getString("card_id"));
				addmoney.setCARD_NAME(rs.getString(3));
				addmoney.setMODIFY_DATE(rs.getString("modify_date").substring(
						0, 10));
				addmoney.setMONEY_UPDATE(rs.getDouble("money_update"));
				addmoney.setDEPOSIT(rs.getDouble("deposit"));
				addmoney.setCOMMENTS(rs.getString("COMMENTS"));
				addmoney.setMEMBER_ID(rs.getInt("member_id"));
				addmoney.setEVENT_TYPE(rs.getInt("event_type"));
				addmoney.setPayMethod(rs.getInt("pay_method"));

				if (Date.valueOf(addmoney.getMODIFY_DATE().substring(0, 10))
						.before(Date.valueOf("2006-12-21"))) {
					addmoney.setEVENT_TYPE_NAME(rs.getString(11));
				} else {
					addmoney.setEVENT_TYPE_NAME(rs.getString("flush_name"));
				}
				addmoney.setOPERATOR_NAME(rs.getString(12));
				addmoney.setPayMethodName(rs.getString(13));
				memberCol.add(addmoney);
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
		return memberCol;
	}

	/*
	 * 判断客服是否再刷新而造成重复充款
	 */
	public boolean checkisRef(Connection con, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isRef = false;
		try {

			String sQuery = "select id from mbr_money_input where status=1 and id = ? ";
			/**
			 * modified by user 2008-03-21 SQL使用绑定变量的方式
			 */
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				isRef = true;
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
		return isRef;
	}

	/**
	 * 判断汇款文件是否重复导入
	 * 
	 * @param refId --
	 *            邮局汇号(唯一索引)
	 * @return boolean
	 */
	public boolean checkIsDupImport(Connection con, String refId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT REF_ID FROM MBR_MONEY_INPUT WHERE REF_ID = ?";
			/**
			 * modified by user 2008-03-21 SQL使用绑定变量的方式
			 */
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, refId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * 得到所有汇款导入类型 add by user 2008-03-25
	 * 
	 * @param conn
	 * @param isUse
	 *            0-线下；1-网站
	 * @return coll
	 * @throws SQLException
	 */
	public static Collection getImportTypeList(Connection conn, int isUse)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from s_mbr_money_input_type where is_use = ? ";
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, isUse);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CodeName codeName = new CodeName(String
						.valueOf(rs.getInt("id")), rs.getString("type_name"));
				coll.add(codeName);
			}
		} catch (SQLException e) {
			throw e;
		}
		return coll;
	}
}