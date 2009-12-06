/*
 * Created on 2005-1-28
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
import java.util.*;
import org.apache.log4j.Logger;
import com.magic.crm.common.pager.PagerForm;
import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.user.dao.S_AREADao;
import com.magic.crm.util.*;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebData;
import com.magic.crm.common.pager.CompSQL;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.promotion.dao.MbrGiftListDAO;
import com.magic.crm.member.entity.Exp;

/**
 * @author user1
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberDAO {

	private static Logger log = Logger.getLogger(MemberDAO.class);

	/**
	 * add by user 2006-06-12 : 15:15 得到会员的sequence
	 * 
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public int getMemberSEQ(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int memberId = 0;
		try {
			String sql = "select seq_MBR_MEMBERS_id.nextval from dual";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberId = rs.getInt(1);
			}
			return memberId;
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

	/**
	 * add by user 2006-06-12 : 15:15 得到会员地址的Sequence
	 * 
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public int getMemberAddressSEQ(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int addressesId = 0;
		try {
			String sql = "select seq_mbr_addresses_id.nextval from dual";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				addressesId = rs.getInt(1);
			}
			return addressesId;
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

	/**
	 * 插入会员注册礼券
	 * 
	 * @param con
	 * @param info
	 * @throws SQLException
	 */
	public void insertMbrGetMbrGift(Connection con, Member info)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			String gift_number = "";
			int keepDays = 0;
			String sQuery = "select gift_number,keep_days from MBR_GET_MBR_GIFT where is_valid=0 and sysdate>=begin_date and sysdate<end_date+1 order by id desc";
			pstmt = con.prepareStatement(sQuery);
			// pstmt.setString(1, info.getMSC_CODE());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				gift_number = rs.getString("gift_number");
				keepDays = rs.getInt("keep_days");
			}
			rs.close();
			pstmt.close();

			String sql = "insert into MBR_GET_MBR (EVENT_ID, MEMBER_ID, gift_number, "
					+ "RECOMMENDED_ID, KEEP_DAYS,  OPERATOR_ID) "
					+ "values ( SEQ_MBR_GET_MBR_ID.nextval, (select id from mbr_members where card_id=?),?, ?,?, 0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.getMbr_get_mbr());
			pstmt.setString(2, gift_number);
			pstmt.setLong(3, info.getID());
			pstmt.setLong(4, keepDays);
			pstmt.execute();

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}

	/**
	 * 增加会员信息
	 * 
	 * @param con
	 * @param info
	 * @throws SQLException
	 */
	public void insert(Connection con, Member info) throws SQLException {
		PreparedStatement pstmt = null;
		try {

			String sQuery = "INSERT INTO MBR_MEMBERS( ID,CARD_ID,CLUB_ID,NAME,BIRTHDAY,TELEPHONE,COMPANY_PHONE,FAMILY_PHONE,LEVEL_ID,LIGUIDATION,TIME_STATUS"
					+ " ,EXP,DELVIERY_TYPE,DEPOSIT,EMAIL,ADDRESS_ID,GENDER,CATEGORY_ID,IS_ORGANIZATION,EMONEY,CATALOG_TYPE"
					+ " ,CERTIFICATE_TYPE,CERTIFICATE_CODE,IS_CARD,FORZEN_CREDIT,FREE_COMMITMENT_PERIODS,AMOUNT_EXP,OLD_AMOUNT_EXP,OLD_LIGUIDATION"
					+ " ,OLD_FREE_COMMITMENT_PERIODS,OLD_LEVEL,JOIN_OTHER,MSC_CODE,CARD_TYPE,IS_MAKE_CARD,NETSHOP_ID,PURCHASE_COUNT,ANIMUS_COUNT,OLD_CARD_CODE,EFFECTIVE_STATUS,creator_id,address,postcode,comments,section,Taobaowang_id)"
					+ " VALUES(?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sQuery);

			pstmt.setInt(1, info.getID());
			pstmt.setString(2, info.getCARD_ID());
			pstmt.setInt(3, 1);
			pstmt.setString(4, info.getNAME());
			pstmt.setDate(5, Date.valueOf(info.getBIRTHDAY()));
			pstmt.setString(6, info.getTELEPHONE());
			pstmt.setString(7, info.getCOMPANY_PHONE());
			pstmt.setString(8, info.getFAMILY_PHONE());
			pstmt.setInt(9, 1);
			pstmt.setInt(10, info.getLIGUIDATION());
			pstmt.setInt(11, info.getTIME_STATUS());
			pstmt.setInt(12, info.getEXP());
			pstmt.setInt(13, info.getDELVIERY_TYPE());
			pstmt.setDouble(14, info.getDEPOSIT());
			pstmt.setString(15, info.getEMAIL());
			pstmt.setInt(16, Integer.parseInt(info.getADDRESS_ID()));
			pstmt.setString(17, info.getGENDER());
			// pstmt.setInt(18,info.getCATEGORY_ID());
			pstmt.setInt(18, 1);
			pstmt.setString(19, info.getIS_ORGANIZATION());
			// pstmt.setString(20,info.getCREATE_DATE());
			pstmt.setDouble(20, info.getEMONEY());
			pstmt.setInt(21, info.getCATALOG_TYPE());
			pstmt.setInt(22, info.getCERTIFICATE_TYPE());
			pstmt.setString(23, info.getCERTIFICATE_CODE());
			pstmt.setInt(24, info.getIS_CARD());
			pstmt.setDouble(25, info.getFORZEN_CREDIT());
			pstmt.setInt(26, info.getFREE_COMMITMENT_PERIODS());
			pstmt.setInt(27, 0);
			pstmt.setInt(28, info.getOLD_AMOUNT_EXP());
			pstmt.setInt(29, info.getOLD_LIGUIDATION());
			pstmt.setInt(30, info.getOLD_FREE_COMMITMENT_PERIODS());
			pstmt.setString(31, info.getOLD_LEVEL());
			pstmt.setInt(32, info.getJOIN_OTHER());
			pstmt.setString(33, info.getMSC_CODE());
			pstmt.setInt(34, info.getCARD_TYPE());
			// System.out.println("info.getCARD_TYPE() is
			// "+info.getCARD_TYPE());
			pstmt.setInt(35, info.getIS_MAKE_CARD());
			pstmt.setInt(36, info.getNETSHOP_ID());
			pstmt.setInt(37, info.getPURCHASE_COUNT());
			pstmt.setInt(38, info.getANIMUS_COUNT());
			pstmt.setString(39, info.getOLD_CARD_CODE());
			pstmt.setInt(40, 1);
			pstmt.setInt(41, info.getCreator_id());
			pstmt.setString(42, CheckStr.checkStr(info.getAddressDetail()));
			pstmt.setString(43, info.getPostcode());
			pstmt.setString(44, info.getCOMMENTS());
			pstmt.setString(45, info.getSection());
			pstmt.setString(46, info.getTaobaoWangId());
			pstmt.execute();

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}

	public void insertMscNumber(Connection con, Member info)
			throws Exception {
		PreparedStatement pstmt = null;
		try {
			String gift_number = "";
			String sQuery = "select gift_number from prd_pricelists where msc = ? and price_type_id=1 and status=100";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, info.getMSC_CODE());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				gift_number = rs.getString("gift_number");
			}
			rs.close();
			pstmt.close();

			// 如果存在礼券则插入礼券
			if (gift_number != null && !"".equals("gift_number")) {

				int type = MbrGiftListDAO.checkGiftNumber(con,gift_number);
			    if (type<0) {
			    	throw new Exception("礼券不存在");
			    } else if (type==2) {
			    	gift_number = MbrGiftListDAO.generateGiftNumber(con, gift_number);
			    } else if (type ==4) {
			    	throw new Exception("公有礼券不能作为入会礼券使用");
			    }
				
				String sql = "select t1.person_num,t1.amount,t1.end_date from mbr_gift_certificates t1 "
						+ " join mbr_gift_lists t2 on t2.gift_number=t1.gift_number "
						+ " where t2.gift_no = ? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, gift_number);
				ResultSet rs2 = pstmt.executeQuery();
				int total_num = 0;
				if (rs2.next()) {
					total_num = rs2.getInt("person_num");

				}
				rs2.close();
				pstmt.close();

				sql = "INSERT INTO mbr_gift_ticket_use(ID, mbrid, ticket_num, sell_type, total_num,num )"
						+ " VALUES (SEQ_MBR_GET_AWARD_ID.NEXTVAL, ?, ?, 17,?,0 )";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, info.getID());

				pstmt.setString(2, gift_number);
				
				pstmt.setInt(3, total_num);

				pstmt.executeUpdate();
				pstmt.close();
			}

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}

	/**
	 * add by user 2006-06-12 : 15:15 新增俱乐部
	 * 
	 * @param conn
	 * @param info
	 * @throws SQLException
	 */
	public void insertClub(Connection conn, Member info) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "insert into mbr_club(member_id, club_id)values(?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info.getID());
			pstmt.setInt(2, info.getCLUB_ID());
			pstmt.execute();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	/**
	 * add by user 2006-06-12 : 15:30 删除俱乐部
	 * 
	 * @param conn
	 * @param info
	 * @throws SQLException
	 */
	public void deleteClub(Connection conn, Member info) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "delete from mbr_club where member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info.getID());
			pstmt.execute();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	public void updateDefaultAddressById(Connection conn, Member info)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			String sql = "update mbr_members set address_id = ? ";
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	/*
	 * 修改会员基本信息
	 */
	public void updateDetail(Connection con, Member info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sQuery = "update MBR_MEMBERS set NAME=?,BIRTHDAY=?,TELEPHONE=?,COMPANY_PHONE=?,"
					+ "FAMILY_PHONE=?,CERTIFICATE_TYPE=?,CERTIFICATE_CODE=?,EMAIL=?,GENDER=?,JOIN_OTHER=?,CATALOG_TYPE=?,"
					+ " VALID_FLAG=?,modifier_id=?,address=?,postcode=?, modify_date = sysdate,comments = ?,section=?,Taobaowang_id=? where id=?";
			pstmt = con.prepareStatement(sQuery);
			con.setAutoCommit(false);
			pstmt.setString(1, info.getNAME());
			pstmt.setDate(2, Date.valueOf(info.getBIRTHDAY()));
			pstmt.setString(3, info.getTELEPHONE());
			pstmt.setString(4, info.getCOMPANY_PHONE());
			pstmt.setString(5, info.getFAMILY_PHONE());
			pstmt.setInt(6, info.getCERTIFICATE_TYPE());
			pstmt.setString(7, info.getCERTIFICATE_CODE());
			pstmt.setString(8, info.getEMAIL());
			pstmt.setString(9, info.getGENDER());
			pstmt.setInt(10, info.getJOIN_OTHER());
			pstmt.setInt(11, info.getCATALOG_TYPE());
			pstmt.setString(12, info.getVALID_FLAG());
			pstmt.setInt(13, info.getModifier_id());
			pstmt.setString(14, CheckStr.checkStr(info.getAddressDetail()));
			pstmt.setString(15, info.getPostcode());
			pstmt.setString(16, info.getCOMMENTS());
			pstmt.setString(17, info.getSection());
			pstmt.setString(18, info.getTaobaoWangId());
			pstmt.setInt(19, info.getID());
			pstmt.execute();
			/**
			 * String del_sql = "delete from mbr_club where member_id=?"; pstmt
			 * = con.prepareStatement(del_sql); pstmt.setInt(1, info.getID());
			 * pstmt.execute();
			 * 
			 * String in_club = "insert into
			 * mbr_club(member_id,club_id)values(?,?)"; pstmt =
			 * con.prepareStatement(in_club); pstmt.setInt(1, info.getID());
			 * pstmt.setInt(2, info.getCLUB_ID()); pstmt.execute();
			 */
			// update memberAddresses
			// /*
			// * memberAdd.setDelivery_address(info.getAddressDetail());
			// * memberAdd.setPostcode(info.getPostcode());
			// * memberAdd.setMember_ID(info.getID());
			// * memberAdd.setID(Integer.parseInt(info.getADDRESS_ID()));
			// * memberAdd.setRelation_person(info.getNAME());
			// * memberAdd.setTelephone(info.getTELEPHONE());
			// *
			// * MemberAddressDAO memberAddressDAO = new MemberAddressDAO();
			// */
			// memberAddressDAO.update(con, memberAdd);
			// con.commit();
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

	/*
	 * 修改会员信息
	 */
	/**
	 * public void update(Connection con, Member info, MemberAddresses
	 * memberAdd) throws SQLException { PreparedStatement pstmt = null;
	 * ResultSet rs = null; try { String sQuery = "update MBR_MEMBERS set
	 * id=?,CARD_ID=?,CLUB_ID=?,NAME=?,BIRTHDAY=?,TELEPHONE=?," +
	 * "COMPANY_PHONE=?,FAMILY_PHONE=?,LEVEL_ID=?,LIGUIDATION=?,TIME_STATUS=?,EXP=?,"
	 * +
	 * "DELVIERY_TYPE=?,DEPOSIT=?,EMAIL=?,GENDER=?,CATEGORY_ID=?,IS_ORGANIZATION=?,"
	 * +
	 * "EMONEY=?,CATALOG_TYPE=?,CERTIFICATE_TYPE=?,CERTIFICATE_CODE=?,IS_CARD=?,"
	 * +
	 * "FORZEN_CREDIT=?,FREE_COMMITMENT_PERIODS=?,AMOUNT_EXP=?,OLD_AMOUNT_EXP=?,"
	 * + "OLD_LIGUIDATION=?,OLD_FREE_COMMITMENT_PERIODS=?,JOIN_OTHER=?," +
	 * "IS_MAKE_CARD=?,NETSHOP_ID=?,PURCHASE_COUNT=?," +
	 * "ANIMUS_COUNT=?,OLD_CARD_CODE=? where id=?"; pstmt =
	 * con.prepareStatement(sQuery); con.setAutoCommit(false); pstmt.setInt(1,
	 * info.getID()); pstmt.setString(2, info.getCARD_ID()); pstmt.setInt(3, 1);
	 * pstmt.setString(4, info.getNAME()); pstmt.setDate(5,
	 * Date.valueOf(info.getBIRTHDAY())); pstmt.setString(6,
	 * info.getTELEPHONE()); pstmt.setString(7, info.getCOMPANY_PHONE());
	 * pstmt.setString(8, info.getFAMILY_PHONE()); pstmt.setInt(9,
	 * info.getLEVEL_ID()); pstmt.setInt(10, info.getLIGUIDATION());
	 * pstmt.setInt(11, info.getTIME_STATUS()); pstmt.setInt(12, info.getEXP());
	 * pstmt.setInt(13, info.getDELVIERY_TYPE()); pstmt.setDouble(14,
	 * info.getDEPOSIT()); pstmt.setString(15, info.getEMAIL());
	 * pstmt.setString(16, info.getGENDER()); pstmt.setInt(17, 1);
	 * pstmt.setString(18, info.getIS_ORGANIZATION()); pstmt.setInt(19,
	 * info.getEMONEY()); pstmt.setInt(20, info.getCATALOG_TYPE());
	 * pstmt.setInt(21, info.getCERTIFICATE_TYPE()); pstmt.setString(22,
	 * info.getCERTIFICATE_CODE()); pstmt.setInt(23, info.getIS_CARD());
	 * pstmt.setDouble(24, info.getFORZEN_CREDIT()); pstmt.setInt(25,
	 * info.getFREE_COMMITMENT_PERIODS()); pstmt.setInt(26,
	 * info.getAMOUNT_EXP()); pstmt.setInt(27, info.getOLD_AMOUNT_EXP());
	 * pstmt.setInt(28, info.getOLD_LIGUIDATION()); pstmt.setInt(29,
	 * info.getOLD_FREE_COMMITMENT_PERIODS());
	 * //pstmt.setString(30,info.getOLD_LEVEL()); pstmt.setInt(30,
	 * info.getJOIN_OTHER()); //pstmt.setString(32,info.getMSC_CODE());
	 * //pstmt.setInt(33,info.getCARD_TYPE()); pstmt.setInt(31,
	 * info.getIS_MAKE_CARD()); pstmt.setInt(32, info.getNETSHOP_ID());
	 * pstmt.setInt(33, info.getPURCHASE_COUNT()); pstmt.setInt(34,
	 * info.getANIMUS_COUNT()); pstmt.setString(35, info.getOLD_CARD_CODE());
	 * pstmt.setInt(36, info.getID()); pstmt.execute(); // update
	 * memberAddresses memberAdd.setDelivery_address(info.getAddressDetail());
	 * memberAdd.setPostcode(info.getPostcode());
	 * memberAdd.setMember_ID(info.getID());
	 * memberAdd.setID(Integer.parseInt(info.getADDRESS_ID()));
	 * memberAdd.setRelation_person(info.getNAME());
	 * memberAdd.setTelephone(info.getTELEPHONE());
	 * 
	 * MemberAddressDAO memberAddressDAO = new MemberAddressDAO();
	 * memberAddressDAO.update(con, memberAdd); con.commit(); } catch
	 * (SQLException e) { e.printStackTrace(); try { con.rollback(); } catch
	 * (Exception ex) { ex.printStackTrace(); } } finally { if (rs != null) try
	 * { rs.close(); } catch (Exception e) { } if (pstmt != null) try {
	 * pstmt.close(); } catch (Exception e) { } } }
	 */
	/*
	 * 修改会员MSC
	 */
	public void updateMSC(Connection con, String condition) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sQuery = "update MBR_MEMBERS " + condition;
			pstmt = con.prepareStatement(sQuery);

			pstmt.execute();
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

	}

	/**
	 * created by user 2008-03-17 更改MSC号
	 * 
	 * @param con
	 * @param mscCode
	 * @throws SQLException
	 */
	public void updateMSC(Connection conn, String mscCode, int memberId)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "update mbr_members set msc_code = ? where id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mscCode);
			pstmt.setInt(2, memberId);
			pstmt.execute();
		} catch (SQLException e) {
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
	 * created by user 2008-03-17 会员停发目录（批量处理）
	 * 
	 * @param conn
	 * @param memberIds
	 * @throws SQLException
	 */
	public static void stopSendCatalog(Connection conn, String memberIds)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "update mbr_members set catalog_type = 2 where id in ("
				+ memberIds + ") ";
		System.out.println(sql);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
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
	 * 会员充值修改DEPOSIT的值时,同步更新MBR_MONEY_HISTROY
	 */
	public static void updateMBR_MONEY_HISTROY(Connection con,
			MembeMoneyHistory info) throws SQLException {
		PreparedStatement pstmt = null;
		int memberid = 0;
		ResultSet rs = null;
		try {
			String memberidSql = "select seq_mbr_money_history.nextval from dual";
			pstmt = con.prepareStatement(memberidSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberid = rs.getInt(1);
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
		try {

			String sQuery = "INSERT INTO MBR_MONEY_HISTORY(ID,	MEMBER_ID,DEPOSIT,MONEY_UPDATE,"
					+ " OPERATOR_ID,EVENT_TYPE)VALUES(?, ?, ?, ?,  ?, ?) ";

			pstmt = con.prepareStatement(sQuery);

			pstmt.setInt(1, memberid);
			pstmt.setInt(2, info.getMEMBER_ID());
			pstmt.setDouble(3, info.getDEPOSIT());
			pstmt.setDouble(4, info.getMONEY_UPDATE());
			pstmt.setInt(5, info.getOPERATOR_ID());
			pstmt.setInt(6, info.getEVENT_TYPE());
			pstmt.execute();

		} catch (SQLException e) {
			// System.out.println("updateMBR_MONEY_HISTROY is error");
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

	}

	/*
	 * 分页查询
	 */
	public static void ListMembers(DBOperation db, MemberForm data,
			String condition) throws Exception {
		WebData wd = new WebData();
		// String sQuery = "SELECT * from mbr_members " + condition;
		// System.out.println(sQuery);
		db.setPageAttribute(data.getPageAttribute());
		db.queryDetailData(condition, wd, true);
		while (wd.next()) {
			MemberForm info = new MemberForm();
			info.setEMAIL(wd.getDetailString("email"));
			info.setID(wd.getDetailInt("id"));
			info.setCARD_ID(wd.getDetailString("CARD_ID"));
			info.setNAME(wd.getDetailString("NAME"));
			info.setTELEPHONE(wd.getDetailString("TELEPHONE"));
			info.setLEVEL_ID(wd.getDetailInt("LEVEL_ID"));
			info.setAddressDetail(wd.getDetailString("address"));
			info.setPostcode(wd.getDetailString("POSTCODE"));
			info.setTaobaoWangId(wd.getDetailString("taobaowang_id"));
			data.getItems().add(info);
		}
		wd = null;
	}

	public static ArrayList ListMembers(Connection conn, String sql)
			throws Exception {
		ArrayList ret = new ArrayList();
		// String sQuery = "SELECT * from mbr_members " + condition;
		// System.out.println(sQuery);
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			MemberForm info = new MemberForm();
			info.setEMAIL(rs.getString("email"));
			info.setID(rs.getInt("id"));
			info.setCARD_ID(rs.getString("CARD_ID"));
			info.setNAME(rs.getString("NAME"));
			info.setTELEPHONE(rs.getString("TELEPHONE"));
			info.setLEVEL_ID(rs.getInt("LEVEL_ID"));
			info.setAddressDetail(rs.getString("address"));
			info.setPostcode(rs.getString("POSTCODE"));
			info.setTaobaoWangId(rs.getString("taobaowang_id"));
			ret.add(info);
		}
		rs.close();
		ps.close();

		return ret;
	}

	/**
	 * 根据汇号查询money_input会员信息 modified by user 2008-04-02 remkark--->mbr_address
	 * 
	 * @param con
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Collection QueryMemberRefid(Connection con, String refId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "SELECT mb_code,mbr_address,postcode from mbr_money_input where id = ? ";

			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, refId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Member info = new Member();
				info.setCARD_ID(rs.getString("mb_code"));

				info.setAddressDetail(rs.getString("mbr_address"));
				info.setPostcode(rs.getString("postcode"));

				memberCol.add(info);
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
	 * 查询会员
	 */
	public Collection QueryMembers(Connection con, Member member)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "SELECT (select club_id from mbr_club where member_id=mbr_members.id) as CLUB_ID, "
					+ "id, card_id, name, birthday, telephone, "
					+ "level_id, address, postcode from mbr_members  where 1= 1 ";

			if (!"".equals(member.getNAME())) {
				sQuery += " and name = '" + member.getNAME() + "'";
			}
			if (!"".equals(member.getPostcode())) {
				sQuery += " and substr(postcode,1,4) = '"
						+ member.getPostcode() + "'";
			}
			// pstmt.setString(1,member.getNAME());
			// pstmt.setString(2,member.getPostcode());

			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Member info = new Member();

				info.setID(rs.getInt("id"));
				info.setCARD_ID(rs.getString("CARD_ID"));
				info.setCLUB_ID(rs.getInt("CLUB_ID"));
				info.setNAME(rs.getString("NAME"));
				// info.setBIRTHDAY(rs.getString("BIRTHDAY"));
				info.setTELEPHONE(rs.getString("TELEPHONE"));
				info.setLEVEL_ID(rs.getInt("LEVEL_ID"));
				// info.setLIGUIDATION(rs.getInt("LIGUIDATION"));
				// info.setEFFECTIVE_STATUS(rs.getInt("EFFECTIVE_STATUS"));
				// info.setTIME_STATUS(rs.getInt("TIME_STATUS"));
				// info.setLEAVING_STATUS(rs.getInt("LEAVING_STATUS"));
				// info.setEXP(rs.getInt("EXP"));
				// info.setDELVIERY_TYPE(rs.getInt("DELVIERY_TYPE"));
				// info.setDEPOSIT(rs.getDouble("DEPOSIT"));
				// info.setFAMILY_PHONE(rs.getString("FAMILY_PHONE"));

				info.setAddressDetail(rs.getString("address"));
				info.setPostcode(rs.getString("postcode"));

				memberCol.add(info);
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
	 * 根据姓名和邮编前4位匹配会员
	 * 
	 * @param conn
	 * @param name
	 * @param postcode
	 * @return
	 * @throws SQLException
	 */
	public Collection queryMemberBasic(Connection conn, String name,
			String postcode) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "SELECT id, card_id, name from mbr_members "
					+ "where name = ? and substr(postcode,1,4) = ? and valid_flag = 'Y' and is_organization = '0'";
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setString(1, name);
			pstmt.setString(2, postcode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member info = new Member();
				info.setID(rs.getInt("id"));
				info.setCARD_ID(rs.getString("CARD_ID"));
				info.setNAME(rs.getString("NAME"));
				memberCol.add(info);
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
	 * 会员基本信息息
	 */
	public Member getMemberInfo(Connection con, String card_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member info = new Member();
		try {
			String sQuery = "SELECT id, deposit, forzen_credit, name,card_id, level_id,birthday,gender from mbr_members where card_id= ?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, card_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setID(rs.getInt("id")); // 会员id
				info.setDEPOSIT(rs.getDouble("DEPOSIT"));// 帐户余额
				info.setFORZEN_CREDIT(rs.getDouble("forzen_credit"));// 冻结款
				info.setNAME(rs.getString("name"));// 姓名
				info.setCARD_ID(rs.getString("card_id"));// 卡号
				info.setLEVEL_ID(rs.getInt("level_id"));// 等级
				info.setBIRTHDAY(rs.getString("birthday"));
				info.setGENDER(rs.getString("gender"));

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
		return info;
	}

	/*
	 * 会员详细信息(包括地址信息)
	 */
	public Member DetailMembers(Connection con, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member info = new Member();
		// SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy/MM/dd");
		try {
			// String sQuery = "SELECT a.*,b.delivery_address,b.POSTCODE from
			// mbr_members a,MBR_ADDRESSES b where a.address_id=b.id and a.id="
			String sQuery = "SELECT  * from mbr_members   where  id="
					+ condition;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			String s = "";
			if (rs.next()) {

				info.setID(rs.getInt("id"));
				info.setCARD_ID(rs.getString("CARD_ID"));
				info.setCLUB_ID(rs.getInt(1));
				info.setNAME(rs.getString("NAME"));
				s = rs.getString("BIRTHDAY");
				info.setBIRTHDAY(s.substring(0, 4) + s.substring(5, 7)
						+ s.substring(8, 10));
				info.setTELEPHONE(rs.getString("TELEPHONE"));
				info.setLEVEL_ID(rs.getInt("LEVEL_ID"));
				info.setLIGUIDATION(rs.getInt("LIGUIDATION"));
				// info.setEFFECTIVE_STATUS(rs.getInt("EFFECTIVE_STATUS"));
				info.setTIME_STATUS(rs.getInt("TIME_STATUS"));
				// info.setLEAVING_STATUS(rs.getInt("LEAVING_STATUS"));
				info.setEXP(rs.getInt("EXP"));

				info.setDELVIERY_TYPE(rs.getInt("DELVIERY_TYPE"));
				info.setDEPOSIT(rs.getDouble("DEPOSIT"));
				info.setFAMILY_PHONE(rs.getString("FAMILY_PHONE"));
				info.setCOMPANY_PHONE(rs.getString("COMPANY_PHONE"));
				info.setEMAIL(rs.getString("EMAIL"));

				info.setGENDER(rs.getString("GENDER"));
				info.setCATEGORY_ID(rs.getInt("CATEGORY_ID"));

				info.setCREATE_DATE(rs.getString("CREATE_DATE"));
				info.setEMONEY(rs.getDouble("EMONEY"));
				info.setCATALOG_TYPE(rs.getInt("CATALOG_TYPE"));
				info.setCERTIFICATE_TYPE(rs.getInt("CERTIFICATE_TYPE"));
				info.setCERTIFICATE_CODE(rs.getString("CERTIFICATE_CODE"));
				info.setIS_CARD(rs.getInt("IS_CARD"));
				info.setFORZEN_CREDIT(rs.getDouble("FORZEN_CREDIT"));
				info.setFREE_COMMITMENT_PERIODS(rs
						.getInt("FREE_COMMITMENT_PERIODS"));
				info.setAMOUNT_EXP(rs.getInt("AMOUNT_EXP"));
				info.setOLD_AMOUNT_EXP(rs.getInt("OLD_AMOUNT_EXP"));
				info.setOLD_LIGUIDATION(rs.getInt("OLD_LIGUIDATION"));
				info.setOLD_FREE_COMMITMENT_PERIODS(rs
						.getInt("OLD_FREE_COMMITMENT_PERIODS"));
				info.setOLD_LEVEL(rs.getString("OLD_LEVEL"));
				info.setJOIN_OTHER(rs.getInt("JOIN_OTHER"));
				info.setMSC_CODE(rs.getString("MSC_CODE"));
				info.setCARD_TYPE(rs.getInt("CARD_TYPE"));
				info.setIS_MAKE_CARD(rs.getInt("IS_MAKE_CARD"));
				info.setNETSHOP_ID(rs.getInt("NETSHOP_ID"));
				info.setPURCHASE_COUNT(rs.getInt("PURCHASE_COUNT"));
				info.setANIMUS_COUNT(rs.getInt("ANIMUS_COUNT"));
				info.setOLD_CARD_CODE(rs.getString("OLD_CARD_CODE"));
				info.setVALID_FLAG(rs.getString("VALID_FLAG"));
				// info.setFROZEN_EMONEY(rs.getInt("FROZEN_EMONEY"));
				info.setADDRESS_ID(rs.getString("ADDRESS_ID"));
				info.setAddressDetail(rs.getString("address"));
				info.setPostcode(rs.getString("POSTCODE"));
				info.setIS_ORGANIZATION(rs.getString("IS_ORGANIZATION"));
				info.setCOMMENTS(rs.getString("comments"));
				info.setSection(rs.getString("section"));
				info.setSectionName(S_AREADao.getFullBySection(con,info.getSection()));
				info.setFROZEN_EMONEY(rs.getDouble("frozen_emoney"));
				info.setTaobaoWangId(rs.getString("TaobaoWang_Id"));
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
		return info;
	}

	public Member getCardInfo(Connection con, String card_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member info = new Member();
		// SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy/MM/dd");
		try {
			// String sQuery = "SELECT a.*,b.delivery_address,b.POSTCODE from
			// mbr_members a,MBR_ADDRESSES b where a.address_id=b.id and
			// a.card_id='"+card_id+"'";
			String sQuery = "SELECT  * from mbr_members where  card_id='"
					+ card_id + "'";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			String s = "";
			if (rs.next()) {

				info.setID(rs.getInt("id"));
				info.setCARD_ID(rs.getString("CARD_ID"));
				info.setCLUB_ID(rs.getInt(1));
				info.setNAME(rs.getString("NAME"));
				s = rs.getString("BIRTHDAY");
				info.setBIRTHDAY(s.substring(0, 4) + s.substring(5, 7)
						+ s.substring(8, 10));
				info.setTELEPHONE(rs.getString("TELEPHONE"));
				info.setLEVEL_ID(rs.getInt("LEVEL_ID"));
				info.setLIGUIDATION(rs.getInt("LIGUIDATION"));
				// info.setEFFECTIVE_STATUS(rs.getInt("EFFECTIVE_STATUS"));
				info.setTIME_STATUS(rs.getInt("TIME_STATUS"));
				// info.setLEAVING_STATUS(rs.getInt("LEAVING_STATUS"));
				info.setEXP(rs.getInt("EXP"));

				info.setDELVIERY_TYPE(rs.getInt("DELVIERY_TYPE"));
				info.setDEPOSIT(rs.getDouble("DEPOSIT"));
				info.setFAMILY_PHONE(rs.getString("FAMILY_PHONE"));
				info.setCOMPANY_PHONE(rs.getString("COMPANY_PHONE"));
				info.setEMAIL(rs.getString("EMAIL"));

				info.setGENDER(rs.getString("GENDER"));
				info.setCATEGORY_ID(rs.getInt("CATEGORY_ID"));

				info.setCREATE_DATE(rs.getString("CREATE_DATE")
						.substring(0, 10));
				//info.setEMONEY(rs.getInt("EMONEY"));
				info.setCATALOG_TYPE(rs.getInt("CATALOG_TYPE"));
				info.setCERTIFICATE_TYPE(rs.getInt("CERTIFICATE_TYPE"));
				info.setCERTIFICATE_CODE(rs.getString("CERTIFICATE_CODE"));
				info.setIS_CARD(rs.getInt("IS_CARD"));
				info.setFORZEN_CREDIT(rs.getDouble("FORZEN_CREDIT"));
				info.setFREE_COMMITMENT_PERIODS(rs
						.getInt("FREE_COMMITMENT_PERIODS"));
				info.setAMOUNT_EXP(rs.getInt("AMOUNT_EXP"));
				info.setOLD_AMOUNT_EXP(rs.getInt("OLD_AMOUNT_EXP"));
				info.setOLD_LIGUIDATION(rs.getInt("OLD_LIGUIDATION"));
				info.setOLD_FREE_COMMITMENT_PERIODS(rs
						.getInt("OLD_FREE_COMMITMENT_PERIODS"));
				info.setOLD_LEVEL(rs.getString("OLD_LEVEL"));
				info.setJOIN_OTHER(rs.getInt("JOIN_OTHER"));
				info.setMSC_CODE(rs.getString("MSC_CODE"));
				info.setCARD_TYPE(rs.getInt("CARD_TYPE"));
				info.setIS_MAKE_CARD(rs.getInt("IS_MAKE_CARD"));
				info.setNETSHOP_ID(rs.getInt("NETSHOP_ID"));
				info.setPURCHASE_COUNT(rs.getInt("PURCHASE_COUNT"));
				info.setANIMUS_COUNT(rs.getInt("ANIMUS_COUNT"));
				info.setOLD_CARD_CODE(rs.getString("OLD_CARD_CODE"));
				// info.setVALID_FLAG(rs.getString("VALID_FLAG"));
				// info.setFROZEN_EMONEY(rs.getInt("FROZEN_EMONEY"));
				info.setADDRESS_ID(rs.getString("ADDRESS_ID"));
				info.setAddressDetail(rs.getString("address"));
				info.setPostcode(rs.getString("POSTCODE"));
				info.setIS_ORGANIZATION(rs.getString("IS_ORGANIZATION"));
				info.setCOMMENTS(rs.getString("comments"));
				info.setEMONEY(rs.getDouble("emoney"));
				info.setFROZEN_EMONEY(rs.getDouble("frozen_emoney"));
				info.setTaobaoWangId(rs.getString("Taobaowang_id"));

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
		return info;
	}

	/*
	 * 自动生成会员号码
	 */
	public String getMBCardIDSEQ(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String cardidSeq = "";
		Member info = new Member();
		try {
			String ardidSql = "select seq_member_card_id.nextval from dual";
			pstmt = con.prepareStatement(ardidSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cardidSeq = "8" + String.valueOf(rs.getInt(1));
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
		return cardidSeq;
	}

	/**
	 * 会员号码是否存在 modified by user 2008-03-29 改成变量绑定的方式
	 * 
	 * @param con
	 * @param card_id
	 * @return
	 * @throws SQLException
	 */
	public boolean checkMBCardIDSEQ(Connection con, String card_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean cardidSeq = false;
		try {
			String ardidSql = "select card_id from mbr_members where card_id = ?";
			pstmt = con.prepareStatement(ardidSql);
			pstmt.setString(1, card_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cardidSeq = true;
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
		return cardidSeq;
	}

	/**
	 * 会员号码是否存在，如果有返回正式会员号 add by user 2008-03-29 改成变量绑定的方式
	 * 
	 * @param con
	 * @param card_id
	 * @return
	 * @throws SQLException
	 */
	public String checkMBCardIDSEQ2(Connection con, String card_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String cardidSeq = null;
		try {
			String ardidSql = "select card_id from mbr_members where (card_id = ? or old_card_code = ?)";
			pstmt = con.prepareStatement(ardidSql);
			pstmt.setString(1, card_id);
			pstmt.setString(2, card_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cardidSeq = rs.getString("card_id");
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
		return cardidSeq;
	}

	/*
	 * 判断会员是否重复 为了避免会员重复入会，设置会员重复判断条件：姓名，地址，电话，如果三者全相同
	 */
	public boolean checkMembers(Connection con, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ifcheck = false;
		Member info = new Member();
		try {
			String checkSql = "select a.id  from mbr_members a where 1=1 "
					+ condition;
			pstmt = con.prepareStatement(checkSql);
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

			String sql = "select count(*) from ord_headers t1, "
					+ "mbr_members t2, s_order_status t3, s_pr_type t4, "
					+ "s_order_category t5, org_persons t6, s_delivery_type t7 where "
					+ "t1.buyer_id = t2.id(+) and t1.status = t3.id(+) "
					+ "and t1.pr_type = t4.id(+) and t1.order_category = t5.id(+) "
					+ "and t1.creator_id = t6.id(+)"
					+ "and t1.delivery_type = t7.id(+) and t2.id = ? ";

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
	 * 得到当前会员的购买记录（分页）
	 * 
	 * @param conn
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public static Collection getOrdersByCurrentMember(Connection conn,
			long memberId, PagerForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Collection colMemberBuy = new ArrayList();
		try {

			String sql = "select * from (select t1.id, t1.so_number, t2.card_id, t2.name as mbname,t1.payed_emoney, "
					+ "t1.goods_fee, t1.order_sum, t1.payed_money,t1.status as statusid, t7.name as delivery_type_name, "
					+ "t3.name as statusname, t1.pr_type as prtypeid, "
					+ "t4.name as prtypename, t1.order_category as categoryid, "
					+ "t5.name as categoryname, t1.release_date, "
					+ "t6.name as creatorName, t1.order_type from ord_headers t1, "
					+ "mbr_members t2, s_order_status t3, s_pr_type t4, "
					+ "s_order_category t5, org_persons t6, s_delivery_type t7 where "
					+ "t1.buyer_id = t2.id(+) and t1.status = t3.id(+) "
					+ "and t1.pr_type = t4.id(+) and t1.order_category = t5.id(+) "
					+ "and t1.creator_id = t6.id(+)"
					+ "and t1.delivery_type = t7.id(+) " + "and t2.id = ? ";
			sql += " order by t1.release_date desc )";
			sql = CompSQL.getNewSql(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, param.getPager().getOffset()
					+ param.getPager().getLength());
			pstmt.setInt(3, param.getPager().getOffset());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderForm order = new OrderForm();
				order.setOrderId(rs.getInt("id"));
				order.setOrderNumber(rs.getString("so_number"));
				order.setCardId(rs.getString("card_id"));
				order.setMbName(rs.getString("mbname"));
				order.setPayable(rs.getDouble("order_sum"));
				order.setTotalMoney(rs.getDouble("goods_fee"));
				order.setMbPayable(rs.getDouble("order_sum")
						- rs.getDouble("payed_money") - rs.getDouble("payed_emoney"));
				order.setStatusName(rs.getString("statusname"));
				order.setStatusId(rs.getInt("statusid"));
				order.setPrTypeName(rs.getString("prtypename"));
				order.setCategoryName(rs.getString("categoryname"));
				order.setCreateDate(rs.getString("release_date"));
				order.setCreatorName(rs.getString("creatorName"));
				order.setDeliveryTypeName(rs.getString("delivery_type_name"));
				order.setOrderType(rs.getInt("order_type"));
				colMemberBuy.add(order);
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
		return colMemberBuy;
	}

	/**
	 * @deprecated 会员最近购买记录
	 * @param conn
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public Collection getMemberBuy(Connection conn, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Collection colMemberBuy = new ArrayList();
		try {
			/*
			 * 取出某个人最新的5条购买记录
			 */
			String buysql = "select * from (select t1.id, t1.so_number, t2.card_id, t2.name as mbname, "
					+ "t1.goods_fee, t1.order_sum, t1.status as statusid, t7.name as delivery_type_name, "
					+ "t3.name as statusname, t1.pr_type as prtypeid, "
					+ "t4.name as prtypename, t1.order_category as categoryid, "
					+ "t5.name as categoryname, t1.create_date, "
					+ "t6.name as creatorName from ord_headers t1, "
					+ "mbr_members t2, s_order_status t3, s_pr_type t4, "
					+ "s_order_category t5, org_persons t6, s_delivery_type t7 where "
					+ "t1.buyer_id = t2.id(+) and t1.status = t3.id(+) "
					+ "and t1.pr_type = t4.id(+) and t1.order_category = t5.id(+) "
					+ "and t1.creator_id = t6.id(+)"
					+ "and t1.delivery_type = t7.id(+) "
					+ "and t2.card_id="
					+ condition
					+ " order by t1.create_date desc ) where rownum <6";
			// System.out.println(buysql);
			pstmt = conn.prepareStatement(buysql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderForm order = new OrderForm();
				order.setOrderId(rs.getInt("id"));
				order.setOrderNumber(rs.getString("so_number"));
				order.setCardId(rs.getString("card_id"));
				order.setMbName(rs.getString("mbname"));
				order.setPayable(rs.getDouble("order_sum"));
				order.setTotalMoney(rs.getDouble("goods_fee"));
				order.setStatusName(rs.getString("statusname"));
				order.setStatusId(rs.getInt("statusid"));
				order.setPrTypeName(rs.getString("prtypename"));
				order.setCategoryName(rs.getString("categoryname"));
				order.setCreateDate(rs.getString("create_date"));
				order.setCreatorName(rs.getString("creatorName"));
				order.setDeliveryTypeName(rs.getString("delivery_type_name"));
				colMemberBuy.add(order);
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
		return colMemberBuy;
	}

	/**
	 * 得到会员基本信息 modified by user 2008-03-26 采用变量绑定方式
	 * 
	 * @param con
	 * @param memberId
	 * @return
	 * @throws SQLException
	 */
	public Member getMembers(Connection con, int memberId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member info = new Member();
		try {
			String sQuery = "SELECT name,CARD_ID,id from mbr_members  where  id = ? ";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, memberId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setID(rs.getInt("id"));
				info.setCARD_ID(rs.getString("CARD_ID"));
				info.setNAME(rs.getString("NAME"));
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
		return info;
	}

	/*
	 * 
	 * 列出会员MSC号 条件1:系统日期在开始日期和结束日期之间 条件2:price_type_id=1 条件3:status=100
	 */
	public Collection QueryMemberMSC(Connection con, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {

			String sQuery = "select msc,name,effect_date,expired_date from prd_pricelists where "
					+ "status=100 and price_type_id=1 and effect_date<sysdate+1 and expired_date>sysdate-1 "
					+ condition;

			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberPriceList info = new MemberPriceList();
				info.setMSC(rs.getString("msc"));
				info.setName(rs.getString("name"));
				memberCol.add(info);
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
	 * 
	 * 列出新会员招募活动
	 */
	public Collection QueryRecruit_Activity(Connection con, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {

			String sQuery = "select msc,name from prd_pricelists where price_type_id=1 and status=100 and effect_date<sysdate and expired_date>sysdate-1 "
					+ condition;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberPriceList info = new MemberPriceList();
				info.setMSC(rs.getString("msc"));
				info.setName(rs.getString("name"));
				memberCol.add(info);
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
	 * 
	 * 列出新会员招募活动
	 */
	public Collection QueryRecruit_Activity2(Connection con)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {

			String sQuery = "select * from Recruit_Activity order by id desc";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberPriceList info = new MemberPriceList();
				info.setMSC(rs.getString("msc"));
				info.setName(rs.getString("name"));
				memberCol.add(info);
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
	 * 判断输入的MSC是否存在
	 */
	public boolean checkMemberMSC(Connection con, String msc)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean checkMSC = false;

		try {

			String sQuery = "select msc,name,effect_date,expired_date from prd_pricelists where "
					+ "status=100 and price_type_id=1 and effect_date<=sysdate and expired_date>sysdate-1 and msc='"
					+ msc + "'";

			// String sQuery=
			// "select * from Recruit_Activity where status=1 and scope in (2,3) and startdate<sysdate+1 and enddate>sysdate-1 and msc='"
			// +msc+"'";

			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				checkMSC = true;
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
		return checkMSC;
	}

	/*
	 * 根据会员ID号判断输入的MSC是否和俱乐部匹配
	 */
	public int checkMemberMSCClub(Connection con, String msc_code)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean checkMSC = false;
		int clubid = 0;
		try {
			String sQuery = "select * from mbr_msc_gift where msc_code='"
					+ msc_code + "'";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				clubid = rs.getInt("clubid");
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
		return clubid;
	}

	/*
	 * 根据货号判断输入的MSC是否和俱乐部匹配
	 */
	public int checkMbrMSCClub(Connection con, String item_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean checkMSC = false;
		int clubid = 0;
		try {
			String sQuery = "select clubid from mbr_msc_gift  where   item_id='"
					+ item_id + "'";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			// System.out.println(sQuery);
			if (rs.next()) {
				clubid = rs.getInt("clubid");
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
		return clubid;
	}

	/*
	 * 判断礼品号是否存在
	 */
	public boolean checkMemberGift(Connection con, int gift_id2)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean checkGift = false;

		try {
			// String sQuery = "select id from PRD_GET_MBR_GIFTS where
			// IS_VALID=1 and id="
			String sQuery = "select item_id from mbr_get_mbr_gift  where IS_VALID=0 and item_id="
					+ gift_id2;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				checkGift = true;// 不存在
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
		return checkGift;
	}

	/*
	 * 判断推荐会员号是否存在
	 */
	public boolean checkMemberID(Connection con, String card_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean idSeq = false;
		Member info = new Member();
		try {
			String getIDsql = "select id from mbr_members where card_id='"
					+ card_id + "'";
			pstmt = con.prepareStatement(getIDsql);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				idSeq = true;// 不存在
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
		return idSeq;
	}

	/*
	 * 根据会员号得到会员ID
	 */
	public static int getMemberID(Connection con, String card_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean cardidSeq = false;
		int id = 0;
		try {
			String getIDsql = "select id from mbr_members where card_id='"
					+ card_id + "'";
			pstmt = con.prepareStatement(getIDsql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
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
		return id;
	}

	/*
	 * 根据会员ID得到会员号
	 */
	public static String getCard_ID(Connection con, int id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean cardidSeq = false;
		String card_id = "";
		try {
			String getIDsql = "select card_id from mbr_members where id=" + id;
			pstmt = con.prepareStatement(getIDsql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				card_id = rs.getString(1);
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
		return card_id;
	}

	/*
	 * 
	 * 获得推荐人信息
	 */
	public Collection MBR_GET_MBR_info(Connection con, String member_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "select a.id,a.card_id,a.name,a.birthday,a.LEVEL_id,a.gender ,b.RECOMMENDED_ID  "
					+ ",b.status,b.gift_number from "
					+ " mbr_members a ,MBR_GET_MBR b  "
					+ " where a.id=b.RECOMMENDED_ID(+) and b.member_id="
					+ member_id + "  order by a.id desc";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			// System.out.println(sQuery);
			while (rs.next()) {
				Member info = new Member();
				info.setID(rs.getInt("id"));
				info.setCARD_ID(rs.getString("card_id"));
				info.setNAME(rs.getString("name"));
				info.setLEVEL_ID(rs.getInt("LEVEL_id"));
				info.setGENDER(rs.getString("gender"));
				info.setBIRTHDAY(rs.getString("birthday"));
				info.setAddress1(rs.getString("gift_Number"));// 礼品名称
				info.setCATEGORY_ID(rs.getInt("RECOMMENDED_ID"));// 被推荐会员ID
				// info.setDEPOSIT(rs.getDouble(9));// 暂时存放礼品价格
				info.setTIME_STATUS(rs.getInt("status"));// mbr_get_mbr.status

				memberCol.add(info);
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
	 * 
	 * 获得被推荐人信息
	 */
	public String MBR_RECOMMENDED_ID(Connection con, String member_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String recommended_id = "999999999999";

		try {
			String sQuery = "select member_id from MBR_GET_MBR "
					+ " where  RECOMMENDED_ID=" + member_id;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				recommended_id = rs.getString("member_id");

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
		return recommended_id;
	}

	/*
	 * 判断会员是否已经有有效的订单
	 */
	public boolean checkMemberOrder(Connection conn, String member_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ifcheck = false;
		try {
			String checkSql = "select * from ord_headers where (status >0 or status=-6 or status=-8) and buyer_id="
					+ member_id;
			pstmt = conn.prepareStatement(checkSql);
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
	 * 修改推荐人的礼品
	 */
	public void updateMemberAward(Connection conn, String gift_id,
			String recommended_id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ifcheck = false;
		try {
			String Sql = "update mbr_get_mbr set gift_id=" + gift_id
					+ " where recommended_id=" + recommended_id;
			pstmt = conn.prepareStatement(Sql);
			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}

	/* 列出所有团体会员 */
	public Collection QueryOrgMember(Connection con, CommonPageUtil pageModel)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList memberCol = new ArrayList();
		pageModel.setPageSize(50);
		String card_id = (String) pageModel.getCondition().get("card_id");
		String name = (String) pageModel.getCondition().get("name");
		String condition = "";
		try {
			if (card_id.length() > 0) {
				condition += " and a.id ="
						+ MemberDAO.getMemberID(con, card_id);
			}
			if (name.length() > 0) {
				condition += " and a.name like '" + name + "%'";
			}

			String countsql = "select count(*) from mbr_members a,mbr_addresses b  where is_organization=1 and a.address_id=b.id "
					+ condition;

			pstmt = con.prepareStatement(countsql);
			rs = pstmt.executeQuery();
			int rsCount = 0;
			if (rs.next()) {
				rsCount = rs.getInt(1);
			}
			pageModel.setRecordCount(rsCount);
			rs.close();
			pstmt.close();

			String sQuery = "select a.id,a.card_id,a.name,a.telephone,a.family_phone,b.delivery_address,b.relation_person "
					+ ",b.postcode from mbr_members a,mbr_addresses b  where is_organization=1 and a.address_id=b.id "
					+ condition;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			int recNo = 0;
			while (rs.next()) {
				if (recNo >= pageModel.getFrom() && recNo <= pageModel.getTo()) {
					Member member = new Member();
					/* 会员ID */
					member.setID(rs.getInt("id"));
					/* 会员号码 */
					member.setCARD_ID(rs.getString("card_id"));
					/* 会员姓名 */
					member.setNAME(rs.getString("name"));
					/* 会员地址 */
					member.setAddressDetail(rs.getString("delivery_address"));
					/* 邮编 */
					member.setPostcode(rs.getString("postcode"));
					/* 联系电话 */
					member.setTELEPHONE(rs.getString("telephone"));
					/* 联系电话二 */
					member.setFAMILY_PHONE(rs.getString("family_phone"));
					/* 联系人 */
					member.setAddress1(rs.getString("relation_person"));

					memberCol.add(member);
				} else if (recNo > pageModel.getTo()) {
					break;
				}
				recNo++;
			}
			pageModel.setModelList(memberCol);
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
		return memberCol;
	}

	/**
	 * 根据邮局汇号判断会员是否重复充值
	 * 
	 * @param con
	 * @param ref_id
	 * @param memberID
	 * @return
	 * @throws SQLException
	 */
	public int checkPostNum(Connection con, String ref_id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check_result = 0;
		try {

			// 根据邮局汇号和状态判断是否已充值
			String checkref = "select * from mbr_money_input where status = 1 and ref_id = ? ";
			pstmt = con.prepareStatement(checkref);
			pstmt.setString(1, ref_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				check_result = 1;
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
		return check_result;
	}

	/*
	 * 充值失败 status=2
	 */
	public static void updateDepositFail(Connection conn,
			MemberaddMoney memberadd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sQuery = "update  MBR_MONEY_INPUT set STATUS=?,OPERATOR_ID=? where id=?";

			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, 2);
			pstmt.setInt(2, memberadd.getOPERATOR_ID());
			pstmt.setInt(3, memberadd.getID());
			pstmt.execute();

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
	}

	/**
	 * 更新充值信息 modified by user 2008-03-29
	 * 
	 * @param con
	 * @param memberadd
	 * @return
	 * @throws SQLException
	 */
	public int updateAddMomeyInfo(Connection con, MemberaddMoney memberadd)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sQuery = "";

		int updateRecod = 0;
		try {
			// con.setAutoCommit(false);
			sQuery = "update  MBR_MONEY_INPUT set "
					+ "MB_ID=?, ORDER_ID=?, STATUS=?, OPERATOR_ID=?, ORDER_CODE = ?, MB_CODE = ? "
					+ "where id=? and (status =0 or status = 2)";

			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, memberadd.getMB_ID());
			pstmt.setInt(2, memberadd.getORDER_ID());
			pstmt.setInt(3, 1);
			pstmt.setInt(4, memberadd.getOPERATOR_ID());
			pstmt.setString(5, memberadd.getORDER_CODE());
			pstmt.setString(6, memberadd.getMB_CODE());
			pstmt.setInt(7, memberadd.getID());
			updateRecod = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw e;

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return updateRecod;
	}

	/*
	 * 更新会员DEPOSIT的值
	 */
	public void updateDeposit(Connection con, MemberaddMoney memberadd)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sQuery = "";
		int memberid = 0;
		double vMoney = 0;
		try {
			sQuery = "select seq_mbr_money_history.nextval from dual";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberid = rs.getInt(1);
			}
		} catch (SQLException e) {
			// System.out.println("seq_mbr_money_history.nextval error");
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
		try {
			/*
			 * 得到用户原来帐户值
			 */
			sQuery = "select  DEPOSIT from MBR_MEMBERS  where id=?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, memberadd.getMB_ID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vMoney = rs.getDouble(1);
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

		try {
			con.setAutoCommit(false);
			sQuery = "update  MBR_MONEY_INPUT set MB_ID=?,ORDER_ID=?,STATUS=?,OPERATOR_ID=? where id=?";

			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, memberadd.getMB_ID());
			pstmt.setInt(2, memberadd.getORDER_ID());
			pstmt.setInt(3, 1);
			pstmt.setInt(4, memberadd.getOPERATOR_ID());
			pstmt.setInt(5, memberadd.getID());
			pstmt.execute();
			String sQuery1 = "update MBR_MEMBERS set DEPOSIT=deposit + ? where id=?";
			pstmt = con.prepareStatement(sQuery1);
			pstmt.setDouble(1, memberadd.getMONEY());
			pstmt.setInt(2, memberadd.getMB_ID());
			pstmt.execute();
			pstmt.close();

			String sQuery2 = "INSERT INTO MBR_MONEY_HISTORY(ID,MEMBER_ID,DEPOSIT,money_update,comments,OPERATOR_ID,EVENT_TYPE,pay_method)VALUES(?, ?,?, ?,?,?, ?,?) ";
			pstmt = con.prepareStatement(sQuery2);
			pstmt.setInt(1, memberid);
			pstmt.setInt(2, memberadd.getMB_ID());
			pstmt.setDouble(3, memberadd.getMONEY() + vMoney);
			pstmt.setDouble(4, memberadd.getMONEY());
			pstmt.setString(5, "充值：" + memberadd.getORDER_CODE());
			pstmt.setInt(6, memberadd.getOPERATOR_ID());
			pstmt.setString(7, memberadd.getTYPE());
			pstmt.setInt(8, memberadd.getPayMethod());
			pstmt.execute();
			pstmt.close();

			con.commit();

		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			try {

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}

	/*
	 * 老会员和新会员转俱乐部时，修改会员的俱乐部 @author Administrator(ysm) Created on 2005-12-14
	 */
	public void updateMbrClub(Connection con, Member info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sQuery = "";
		int memberid = 0;
		double vMoney = 0;

		try {

			sQuery = "update  mbr_member set clubid=? where id=?";

			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, info.getCLUB_ID());
			pstmt.setInt(2, info.getID());
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

	/*
	 * 老会员和新会员转俱乐部时，修改会员的俱乐部 @author Administrator(ysm) Created on 2005-12-14
	 */
	public boolean ifGetGift(Connection con, int id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sQuery = "";
		int memberid = 0;
		boolean ifgetGift = false;

		try {

			String check_sql = "select * from mbr_club where club_id=2 and member_id="
					+ id;
			pstmt = con.prepareStatement(check_sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ifgetGift = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return ifgetGift;

	}

	/*
	 * 更新会员DEPOSIT的值
	 */
	public void updateExp(Connection con, double exp, int memberID)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "";
		int memberid = 0;
		double vMoney = 0;
		try {
			sQuery = "update mbr_members set amount_exp = amount_exp + ? where id = ?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setDouble(1, exp);
			pstmt.setInt(2, memberID);
			pstmt.executeUpdate();

		} catch (SQLException e) {

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

	public static void updateFamilyPhone(Connection con, Member member)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "";

		try {
			sQuery = "update mbr_members set family_phone = ? where id = ?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, member.getFAMILY_PHONE());
			pstmt.setInt(2, member.getID());
			pstmt.executeUpdate();

		} catch (SQLException e) {
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

	public static void updateCompanyPhone(Connection con, Member member)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "";

		try {
			sQuery = "update mbr_members set company_phone = ? where id = ?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, member.getCOMPANY_PHONE());
			pstmt.setInt(2, member.getID());
			pstmt.executeUpdate();

		} catch (SQLException e) {
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

	public static void updateTelephone(Connection con, Member member)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "";

		try {
			sQuery = "update mbr_members set telephone = ? where id = ?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, member.getTELEPHONE());
			pstmt.setInt(2, member.getID());
			pstmt.executeUpdate();

		} catch (SQLException e) {
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

	public static void updateSysPostcode(Connection conn, SysPostcode data)
			throws Exception {
		String sql = "update s_postcode set province = ?,state=?,city = ? where postcode = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, data.getProvince());
		ps.setString(2, data.getProvince());
		ps.setString(3, data.getCity());
		ps.setString(4, data.getPostcode().substring(0, 4));
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * 新增邮编设置
	 * 
	 * @param con
	 * @param data
	 * @throws SQLException
	 */

	public static void insertSysPostcode(Connection con, SysPostcode data)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO jxc.sys_post(postcode,province,city) values "
				+ "(?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data.getPostcode());
			pstmt.setString(2, data.getProvince());
			pstmt.setString(3, data.getCity());
			pstmt.execute();

		} catch (SQLException e) {
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
	 * 检测jxc.sys_post是否已经有存在的邮编
	 * 
	 * @param con
	 * @param postcode
	 * @return
	 * @throws SQLException
	 */

	public static boolean checkSysPostcode(Connection con, SysPostcode param)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select postcode from jxc.sys_post where is_valid = 1 and postcode = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getPostcode());
			// pstmt.setString(2, param.getProvince());
			// pstmt.setString(3, param.getCity());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return false;
			}

		} catch (SQLException e) {
			throw e;

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return true;

	}

	public static boolean checkSysPostFee(Connection con, String postcode)
			throws SQLException {
		SysPostcode param = new SysPostcode();
		param.setPostcode(postcode);
		return checkSysPostFee(con, param);
	}

	/**
	 * 检测jxc.sys_pos_feet是否已经有存在的邮编
	 * 
	 * @param con
	 * @param postcode
	 * @return
	 * @throws SQLException
	 */

	public static boolean checkSysPostFee(Connection con, SysPostcode param)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select postcode from s_postcode where postcode = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getPostcode().substring(0, 4));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return false;

	}

	public static Exp getExpByCardId(Connection con, String cardId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id, card_id, name, exp, old_amount_exp, amount_exp from mbr_members where card_id = ? ";
		Exp exp = new Exp();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cardId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				exp.setExp(rs.getInt("exp"));
				exp.setOldAmountExp(rs.getInt("old_amount_exp"));
				exp.setAmountExp(rs.getInt("amount_exp"));
				exp.getMember().setID(rs.getInt("id"));
				exp.getMember().setCARD_ID(rs.getString("card_id"));
				exp.getMember().setNAME(rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return exp;

	}

	/**
	 * 判断是否elong卡
	 * 
	 * @param con
	 * @param cardId
	 * @return
	 * @throws SQLException
	 */
	public static boolean isElongCard(Connection con, String cardId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select *  from elong_card where card_id = ? ";
		boolean flag = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cardId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return flag;

	}

	/**
	 * 得到Email
	 * 
	 * @param con
	 * @param memberId
	 * @return email
	 * @throws SQLException
	 */
	public String getEmailById(Connection con, int memberId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select email from mbr_members where id = ? ";
		String email = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				email = rs.getString("email");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return email;

	}

	/**
	 * 判断Email是否被别的会员使用
	 * 
	 * @param con
	 * @param memberId
	 * @return email
	 * @throws SQLException
	 */
	public boolean isEmailUsed(Connection con, int memberId, String email)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id from mbr_members where id <> ? and email = ? ";
		boolean flag = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return flag;
	}
}