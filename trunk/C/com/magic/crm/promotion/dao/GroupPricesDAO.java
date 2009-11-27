/**
 * GroupPricesDAO.java
 * 2008-5-7
 * 下午07:19:54
 * user
 * GroupPricesDAO
 */
package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.ArrayList;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.magic.crm.promotion.entity.GroupPrices;
import com.magic.crm.promotion.entity.Recruit_Activity_PriceList;
import com.magic.crm.promotion.entity.Recruit_Activity_Section;
import com.magic.crm.promotion.entity.Recruit_Activity;
import com.magic.crm.promotion.form.GroupPricesForm;
import com.magic.crm.util.CodeName;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.common.DBOperation;

/**
 * @author user
 */
public class GroupPricesDAO {

	private static Logger logger = Logger.getLogger(GroupPricesDAO.class);

	/**
	 * 查询列表
	 * 
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public static Collection findAll(Connection conn, GroupPricesForm param)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select a.*, b.name, b.msc from RECRUIT_ACTIVITY_GROUP_PRICES a "
				+ "inner join recruit_activity_section b on a.sectionid = b.id "
				+ "where a.sectionid in(select id from RECRUIT_ACTIVITY_SECTION where msc = ?) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param.getMsc());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GroupPrices data = new GroupPrices();
				data.setGpId(rs.getInt("gp_id"));
				data.setSectionId(rs.getInt("sectionid"));
				data.setSaleQty(rs.getDouble("sale_qty"));
				data.setSaleAmt(rs.getDouble("sale_amt"));
				data.setIsGift(rs.getInt("is_gift"));
				data.setBeginDate(rs.getDate("startdate"));
				data.setEndDate(rs.getDate("enddate"));
				data.setStatus(rs.getInt("status"));
				data.setSectionName(rs.getString("name") + "("
						+ rs.getString("msc") + ")");
				coll.add(data);
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}

	/**
	 * 新增
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public static void insert(Connection conn, GroupPricesForm data)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "insert into RECRUIT_ACTIVITY_GROUP_PRICES "
				+ "(gp_id, sectionid, sale_qty, sale_amt, is_gift, "
				+ "startdate, enddate, createdate, creatorid, status) "
				+ "values (SEQ_RECRUIT_GROUP_PRICES_ID.nextval, ?, ?, ?, ?, "
				+ "?, ?, sysdate, ?, 0)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, data.getSectionId());
			pstmt.setDouble(2, data.getSaleQty());
			pstmt.setDouble(3, data.getSaleAmt());
			pstmt.setInt(4, data.getIsGift());
			pstmt.setDate(5, data.getBeginDate());
			pstmt.setDate(6, data.getEndDate());
			pstmt.setInt(7, data.getCreatorId());
			pstmt.execute();

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * 修改
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public static void update(Connection conn, GroupPricesForm data)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "update RECRUIT_ACTIVITY_GROUP_PRICES "
				+ "set sectionid = ?, sale_qty = ?, sale_amt = ?, is_gift = ?, "
				+ "startdate = ?, enddate = ?, lastmodidate = sysdate, lastmodifierid = ? "
				+ "where gp_id = ? and status = 0";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, data.getSectionId());
			pstmt.setDouble(2, data.getSaleQty());
			pstmt.setDouble(3, data.getSaleAmt());
			pstmt.setInt(4, data.getIsGift());
			pstmt.setDate(5, data.getBeginDate());
			pstmt.setDate(6, data.getEndDate());
			pstmt.setInt(7, data.getLastModifierId());
			pstmt.setInt(8, data.getGpId());
			pstmt.execute();

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	public static GroupPrices view(Connection conn, int gpId)
			throws SQLException {
		GroupPricesForm param = new GroupPricesForm();
		param.setGpId(gpId);
		return view(conn, param);
	}

	/**
	 * 查看详情
	 * 
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public static GroupPrices view(Connection conn, GroupPricesForm param)
			throws SQLException {
		GroupPrices data = new GroupPrices();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.*, b.maxgoods, b.mingoods, b.msc, b.type, b.id, b.name "
				+ "from RECRUIT_ACTIVITY_GROUP_PRICES a "
				+ "inner join recruit_activity_section b on a.sectionid = b.id "
				+ "where a.gp_id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, param.getGpId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				data.setGpId(rs.getInt("gp_id"));
				data.setSectionId(rs.getInt("sectionid"));
				data.setSaleQty(rs.getDouble("sale_qty"));
				data.setSaleAmt(rs.getDouble("sale_amt"));
				data.setIsGift(rs.getInt("is_gift"));
				data.setBeginDate(rs.getDate("startdate"));
				data.setEndDate(rs.getDate("enddate"));
				data.setStatus(rs.getInt("status"));
				data.getSection().setId(rs.getInt("id"));
				data.getSection().setName(rs.getString("name"));
				data.getSection().setMaxGoods(rs.getInt("maxgoods"));
				data.getSection().setMinGoods(rs.getInt("mingoods"));
				data.getSection().setMsc_Code(rs.getString("msc"));
				data.getSection().setType(rs.getString("type"));
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return data;
	}

	/**
	 * 更新状态
	 * 
	 * @param conn
	 * @param param
	 * @throws SQLException
	 */
	public static void updateStatus(Connection conn, GroupPricesForm param)
			throws SQLException {

		PreparedStatement pstmt = null;

		String sql = "update RECRUIT_ACTIVITY_GROUP_PRICES set status = ? where gp_id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, param.getStatus());
			pstmt.setInt(2, param.getGpId());
			pstmt.execute();

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * 销售区列表
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static Collection getAllSectionList(Connection conn)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select * from RECRUIT_ACTIVITY_section order by id desc";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			coll.add(new CodeName("", "请选择..."));
			while (rs.next()) {
				CodeName data = new CodeName(rs.getString("id"), rs
						.getString("name")
						+ "(" + rs.getString("msc") + ")");
				coll.add(data);
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}

	/**
	 * 新招募MSC列表
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static Collection getAllActivityList(Connection conn)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select * from RECRUIT_ACTIVITY order by id desc";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			coll.add(new CodeName("", "请选择..."));
			while (rs.next()) {
				CodeName data = new CodeName(rs.getString("msc"), rs
						.getString("msc")
						+ "(" + rs.getString("name") + ")");
				coll.add(data);
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}

	/**
	 * 根据会员msc加载打套产品组
	 * 
	 * @param conn
	 * @param mscCode
	 * @return
	 * @throws SQLException
	 */
	public static Collection loadActiveGroupsByMsc(Connection conn,
			String mscCode, OrderForm pageData) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select * from recruit_activity_group_prices a "
				+ "inner join recruit_activity_section b on a.sectionid = b.id "
				+ "inner join recruit_activity c on b.msc = c.msc "
				+ "where b.msc = ? "
				+ "and c.startdate <= sysdate and c.enddate >= sysdate + 1 "
				+ "and c.status = 1 " 
				+ "and c.scope in(2, 3) "
				+ "order by a.sale_amt ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mscCode);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				GroupPrices data = new GroupPrices();
				data.setGpId(rs.getInt("gp_id"));
				data.setSectionId(rs.getInt("sectionid"));
				data.setSaleQty(rs.getDouble("sale_qty"));
				data.setSaleAmt(rs.getDouble("sale_amt"));
				data.setIsGift(rs.getInt("is_gift"));
				data.setBeginDate(rs.getDate("startdate"));
				data.setEndDate(rs.getDate("enddate"));
				data.setStatus(rs.getInt("status"));
				if (data.getIsGift() == 1) { // 可选择赠品
					Collection sectionList = Recruit_Activity_SectionDAO
							.findProductByMsc(conn, rs.getString("msc"), pageData);
					data.setGiftSection(sectionList);
				}
				data.setProduct(loadGfitsByMsc(conn, mscCode, "D",pageData));// 打套产品
				coll.add(data);
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}

	/**
	 * 得到老会员享受的促销MSC
	 * 
	 * @param conn
	 * @param pageData
	 * @return
	 * @throws SQLException
	 */
	public static Collection getOldMemberEnjoyRecruits(Connection conn,
			OrderForm pageData) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select distinct b.msc, b.name from recruit_members a "
				+ "inner join recruit_activity b on a.msc = b.msc "
				+ "left join recruit_activity_section c on b.msc=c.msc "
				+ "where 1 = 1 and c.type in('D') and a.mbr_id = ? "
				+ "and b.startdate <= sysdate and b.enddate >= sysdate + 1 "
				+ "and b.status = 1 and b.scope in(2,3) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pageData.getCart().getMember().getID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Recruit_Activity activity = new Recruit_Activity();
				activity.setMsc_Code(rs.getString("msc"));
				activity.setName(rs.getString("name"));
				coll.add(activity);
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}

	/**
	 * 得到新会员对应的msc活动
	 * 
	 * @param conn
	 * @param pageData
	 * @return
	 * @throws SQLException
	 */
	public static Collection getNewMemberEnjoyRecruits(Connection conn,
			OrderForm pageData) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select distinct b.msc, b.name from mbr_members a "
				+ "inner join recruit_activity b on a.msc_code = b.msc "
				+ "left join recruit_activity_section c on b.msc = c.msc "
				
				+ "where 1=1 and b.msc = ? and a.id = ? and a.IS_ORGANIZATION = '0' "
				+ "and b.startdate <= sysdate and b.enddate >= sysdate + 1 "
				+ "and b.status = 1 and b.scope in(2,3) and c.type in('D') ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pageData.getCart().getMember().getMSC_CODE());
			pstmt.setInt(2, pageData.getCart().getMember().getID());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Recruit_Activity activity = new Recruit_Activity();
				activity.setMsc_Code(rs.getString("msc"));
				activity.setName(rs.getString("name"));
				coll.add(activity);
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}

	/**
	 * @deprecated 根据会员msc加载打套产品
	 * @param conn
	 * @param mscCode
	 * @return
	 * @throws SQLException
	 */
	public static Collection loadSetProductsByMsc(Connection conn,
			String mscCode) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select a.*, d.name as item_name "
				+ "from recruit_activity_pricelist a "
				+ "inner join recruit_activity_section b on a.sectionid = b.id "
				+ "inner join recruit_activity c on b.msc = c.msc "
				+ "inner join prd_items d on a.item_id = d.item_id "
				+ "where b.msc = ? " + "and b.type = 'D' "
				+ "and a.startdate <= sysdate and a.enddate >= sysdate + 1 "
				+ "and c.startdate <= sysdate and c.enddate >= sysdate + 1 "
				+ "and c.status = 1 " + "and c.scope in(2, 3) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mscCode);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Recruit_Activity_PriceList data = new Recruit_Activity_PriceList();
				data.setId(rs.getInt("id"));
				data.setSectionId(rs.getInt("sectionid"));
				data.setItemId(rs.getInt("itemid"));
				data.setItemCode(rs.getString("itemcode"));
				data.setItemName(rs.getString("item_name"));
				data.setSellType(rs.getInt("selltype"));
				data.setPrice(rs.getDouble("price"));
				data.setStartDate(rs.getString("startdate"));
				data.setEndDate(rs.getString("enddate"));
				data.setStatus(rs.getInt("status"));
				data.setOverx(rs.getDouble("overx"));
				coll.add(data);
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}

	/**
	 * 根据会员msc加载组赠品
	 * 
	 * @param conn
	 * @param mscCode
	 * @param sectionType
	 * @return
	 * @throws SQLException
	 */
	public static Collection loadGfitsByMsc(Connection conn, String mscCode,
			String sectionType, OrderForm pageData) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		/*String sql = "select a.*, d.name as item_name, d.standard_price, d.is_last_sell, b.type as section_type "
				+ "from recruit_activity_pricelist a "
				+ "inner join recruit_activity_section b on a.sectionid = b.id "
				+ "inner join recruit_activity c on b.msc = c.msc "
				+ "inner join prd_items d on a.itemid = d.item_id "
				+ "where b.msc = ? " + "and b.type = ? "
				+ "and a.startdate <= sysdate and a.enddate >= sysdate + 1 "
				+ "and c.startdate <= sysdate and c.enddate >= sysdate + 1 "
				+ "and c.status = 1 " + "and c.scope in(2, 3) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mscCode);
			pstmt.setString(2, sectionType);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Recruit_Activity_PriceList data = new Recruit_Activity_PriceList();
				data.setId(rs.getInt("id"));
				data.setSectionId(rs.getInt("sectionid"));
				data.setItemId(rs.getInt("itemid"));
				data.setItemCode(rs.getString("itemcode"));
				data.setItemName(rs.getString("item_name"));
				data.setSellType(rs.getInt("selltype"));
				data.setPrice(rs.getDouble("price"));
				data.setStartDate(rs.getString("startdate"));
				data.setEndDate(rs.getString("enddate"));
				data.setStatus(rs.getInt("status"));
				data.setOverx(rs.getDouble("overx"));
				data.setStandardPrice(rs.getDouble("standard_price"));
				data.setIsLastSell(rs.getInt("is_last_sell"));
				data.setSectionType(rs.getString("section_type"));
				ItemInfo item = new ItemInfo();
				item.setItemId(data.getItemId());
				item.setLastSell(rs.getInt("is_last_sell") == 1 ? true:false);
				
				int nAvailableQty = OrderDAO.getAvailableStockQty(new DBOperation(conn),
						item, pageData);
				if (nAvailableQty <= 1) { // 库存不足
					data.setStockStatusId(1);
					if (item.isLastSell())
						data.setStockStatusName("永久缺货");
					else
						data.setStockStatusName("暂时缺货");
				} else {
					data.setStockStatusId(0);
					if (nAvailableQty - 1 < 10) {
						data.setStockStatusName("即将缺货");
					} else {
						data.setStockStatusName("库存正常");
					}

				}
				coll.add(data);
			}

		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}*/
		return coll;
	}
}
