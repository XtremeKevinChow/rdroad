package com.magic.crm.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.product.entity.ProductSKU;
import com.magic.crm.product.form.Product2Form;
import com.magic.crm.product.form.ProductSKUForm;

public class ProductSKUDAO implements java.io.Serializable {
	private static Logger log = Logger.getLogger("ProductSKUDAO.class");

	/**
	 * 显示同一个货号下的所有sku
	 * @param con 连接
	 * @param item_code 货号
	 * @return 包含对应sku的arraylist
	 * @throws Exception
	 */
	public static ArrayList list(Connection con, String item_code)
			throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList list = new ArrayList();
		try {
			String sql = "select t1.*,t2.name as color_name,t3.name size_name,t4.itm_type,t4.saleflag"
					+ " from prd_item_sku t1 left join prd_item_color t2 on t1.color_code = t2.code"
					+ " join prd_item t4 on t1.itm_code=t4.itm_code"
					+ " left join prd_item_size t3 on t1.size_code = t3.code and t3.type_id=t4.category_id"
					+ " where  t1.itm_code= ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, item_code);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSKUForm info = new ProductSKUForm();
				info.setSku_id(rs.getInt("sku_id"));
				// info.setItem_id(rs.getInt("itm_id"));
				//log.debug(1);
				info.setItem_code(rs.getString("itm_code"));
				info.setColor_code(rs.getString("color_code"));
				info.setColor_name(rs.getString("color_name")+ "("+ rs.getString("color_code")+ ")");
				info.setSize_code(rs.getString("size_code"));
				info.setSize_name(rs.getString("size_name"));
				info.setBarcode(rs.getString("itm_barcode"));
				//log.debug(2);
				info.setMax_count(rs.getInt("max_count"));
				info.setStandard_price(rs.getDouble("standard_price"));
				info.setItem_cost(rs.getDouble("itm_cost"));
				info.setSale_price(rs.getDouble("sale_price"));
				info.setVip_price(rs.getDouble("vip_price"));
				info.setEnable_os(rs.getInt("enable_os"));
				info.setOs_qty(rs.getInt("os_qty"));	
				info.setItem_type(rs.getInt("itm_type"));
				info.setSaleflag(rs.getString("saleflag"));
				info.setWeb_price(rs.getDouble("web_price"));
				//log.debug(4);
				list.add(info);

			}

		} catch (SQLException e) {
			System.out.println(e);
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
		return list;
	}

	/**
	 * 显示同一个barcode的所有sku,应该只有一个
	 * @param con 连接
	 * @param item_code 货号
	 * @return 包含对应sku的arraylist
	 * @throws Exception
	 */
	public static ArrayList listByBarcode(Connection con, String barcode)
			throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList list = new ArrayList();
		try {
			String sql = "select t1.*,t2.name as color_name,t3.name size_name"
					+ " from prd_item_sku t1 left join prd_item_color t2 on t1.color_code = t2.code"
					+ " join prd_item t4 on t1.itm_code=t4.itm_code"
					+ " left join prd_item_size t3 on t1.size_code = t3.code and t3.type_id=t4.category_id"
					+ " where  t1.itm_barcode=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, barcode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSKUForm info = new ProductSKUForm();
				info.setSku_id(rs.getInt("sku_id"));
				// info.setItem_id(rs.getInt("itm_id"));
				//log.debug(1);
				info.setItem_code(rs.getString("itm_code"));
				info.setColor_code(rs.getString("color_code"));
				info.setColor_name(rs.getString("color_name")+ "("+ rs.getString("color_code")+ ")");
				info.setSize_code(rs.getString("size_code"));
				info.setSize_name(rs.getString("size_name"));
				info.setBarcode(rs.getString("itm_barcode"));
				//log.debug(2);
				info.setMax_count(rs.getInt("max_count"));
				info.setStandard_price(rs.getDouble("standard_price"));
				info.setItem_cost(rs.getDouble("itm_cost"));
				info.setSale_price(rs.getDouble("sale_price"));
				info.setVip_price(rs.getDouble("vip_price"));
				info.setEnable_os(rs.getInt("enable_os"));
				info.setOs_qty(rs.getInt("os_qty"));
				info.setWeb_price(rs.getDouble("web_price"));
				//log.debug(4);
				list.add(info);

			}

		} catch (SQLException e) {
			System.out.println(e);
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
		return list;
	}
	
	/**
	 * 根据sku_id查找sku具体信息
	 * @param con
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public static int findByPK(Connection con, ProductSKUForm info)
			throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int ret = -1;
		try {
			String sql = "select * from prd_item_sku where sku_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, info.getSku_id());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = 0;
				info.setItem_code(rs.getString("itm_code"));
				info.setColor_code(rs.getString("color_code"));
				//info.setColor_name(rs.getString("color_name"));
				info.setSize_code(rs.getString("size_code"));
				//info.setSize_name(rs.getString("size_name"));
				info.setBarcode(rs.getString("itm_barcode"));
				//log.debug(2);
				info.setMax_count(rs.getInt("max_count"));
				info.setStandard_price(rs.getDouble("standard_price"));
				info.setItem_cost(rs.getDouble("itm_cost"));
				info.setSale_price(rs.getDouble("sale_price"));
				info.setVip_price(rs.getDouble("vip_price"));
				info.setEnable_os(rs.getInt("enable_os"));
				info.setOs_qty(rs.getInt("os_qty"));
				info.setWeb_price(rs.getDouble("web_price"));
				//log.debug(4);
				

			}

		} catch (SQLException e) {
			System.out.println(e);
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
		return ret;
	}

	public static int insertByItem(Connection conn, Product2Form info,
			String[] colors, String[] sizes) throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String sql = "insert into prd_item_sku(sku_id,itm_code,color_code,size_code,"
					+ "itm_barcode,standard_price, sale_price,vip_price,itm_cost," +
					"enable_os,os_qty,max_count,itm_unit,itm_id)"
					+ " (select seq_prd_item_sku_id.nextval,itm_code,?,?,itm_barcode,"
					+ " standard_price,sale_price,vip_price,itm_cost,enable_os,os_qty,max_count,itm_unit,itm_id" +
							" from prd_item "
					+ " where itm_code = ?)";
			ps = conn.prepareStatement(sql);

			String temp1;
			String temp2;
			for (int i = 0; i < colors.length; i++) {
				for (int j = 0; j < sizes.length; j++) {

					temp1 =  colors[i];
					if ("null".equals(temp1)) {
						ps.setNull(1, Types.VARCHAR);
					} else {
						ps.setString(1, temp1.toUpperCase());
					}
					temp2 = (String) sizes[j];
					if ("null".equals(temp2)) {
						ps.setNull(2, Types.VARCHAR);
					} else {
						ps.setString(2, temp2.toUpperCase());
					}
					ps.setString(3, info.getItem_code());
					//log.info(sql + ':' + temp1 + ":" + temp2 + ":"
					//		+ info.getItem_code());
					ret = ps.executeUpdate();
				}
			}
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}

		}

		return ret;
	}

	public static int delete(Connection conn, ProductSKUForm info)
			throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String sql = "delete from prd_item_sku where sku_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, info.getSku_id());

			ret = ps.executeUpdate();

		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}

		}

		return ret;
	}
	
	public static int insert(Connection conn, ProductSKUForm info ) throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String sql = "insert into prd_item_sku(sku_id,itm_code,color_code,size_code,"
					+ "itm_barcode, standard_price,itm_cost,sale_price,vip_price,"
					+ "enable_os,os_qty,max_count,itm_unit,itm_id,web_price) "
					+ " values(seq_prd_item_sku_id.nextval,?,?,?,"
					+ " ?,?,?,?,?,?,?,?,?,(select itm_id from prd_item where itm_code = ?),?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, info.getItem_code());
			ps.setString(2, info.getColor_code());
			ps.setString(3, info.getSize_code());
			ps.setString(4, info.getBarcode());
			ps.setDouble(5, info.getStandard_price());
			ps.setDouble(6, info.getItem_cost());
			ps.setDouble(7, info.getSale_price());
			ps.setDouble(8, info.getVip_price());
			ps.setInt(9, info.getEnable_os());
			ps.setInt(10, info.getOs_qty());
			ps.setInt(11, info.getMax_count());
			ps.setInt(12, info.getItem_unit());
			ps.setString(13, info.getItem_code());
			if(info.getWeb_price()>0 ) {
				ps.setDouble(14, info.getWeb_price());
			} else {
				ps.setNull(14,Types.DOUBLE);
			}
			ret = ps.executeUpdate();
			
			
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}

		}

		return ret;
	}
	
	public static int insertGift(Connection conn, ProductSKUForm info ) throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String sql = "select count(*) from prd_item where itm_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,info.getItem_code());
			rs = ps.executeQuery();
			if(rs.next()) {
				ret = rs.getInt(1);
				if(ret >0 ) {
					throw new Exception("货号已经存在,请重新输入");
				}
			}
			rs.close();
			ps.close();	
			
			sql = "insert into prd_item(itm_code,itm_name,itm_name_en,"
					+ " itm_type,category_id,itm_cost,standard_price,"
					+ " sale_price,vip_price,max_count,itm_title,itm_fabric,"
					+ " itm_lining,itm_origin,itm_desc,enable_os,os_qty,itm_barcode,"
					+ " itm_unit,itm_bottom,itm_side,itm_other,main_category,itm_id,saleflag ) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "(select catalog_id from prd_item_category "
					+ "	where parent_id=0 "
					+ "	start with catalog_id= ? "
					+ "	connect by catalog_id =prior parent_id),seq_prd_item_id.nextval,'N')";
			ps = conn.prepareStatement(sql);
			ps.setString(1, info.getItem_code());
			ps.setString(2, info.getItem_name());
			ps.setString(3, info.getItem_name_en());
			ps.setInt(4, 1);
			ps.setInt(5, info.getItem_category());
			ps.setDouble(6, info.getItem_cost());
			ps.setDouble(7, info.getStandard_price());
			ps.setDouble(8, info.getSale_price());
			ps.setDouble(9, info.getVip_price());
			ps.setInt(10, 4);
			ps.setString(11, info.getItem_title());
			ps.setString(12, info.getItem_fabric());
			ps.setString(13, info.getItem_lining());
			ps.setString(14, info.getItem_origin());
			ps.setString(15, info.getItem_desc());
			ps.setInt(16, info.getEnable_os());
			ps.setInt(17, info.getOs_qty());
			ps.setString(18, info.getBarcode());
			ps.setInt(19, info.getItem_unit());
			ps.setString(20, info.getItem_bottom());
			ps.setString(21, info.getItem_side());
			ps.setString(22, info.getItem_other());
			ps.setInt(23, info.getItem_category());

			ret = ps.executeUpdate();
			ps.close();

			sql = "insert into prd_item_sku(sku_id,itm_code,color_code,size_code,standard_price,"
					+ " sale_price,vip_price,max_count, enable_os,os_qty,itm_barcode,itm_unit,itm_id ) "
					+ " (select seq_prd_item_sku_id.nextval,itm_code,0,0,standard_price," +
					" sale_price,vip_price,max_count,enable_os,os_qty,itm_barcode,itm_unit,itm_id from prd_item where itm_code = ? )";
			ps = conn.prepareStatement(sql);
			ps.setString(1, info.getItem_code());
			ret = ps.executeUpdate();
			
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}

		}

		return ret;
	}
	
	public static int modify(Connection conn, ProductSKUForm info ) throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String sql = "update prd_item_sku set itm_code =?,color_code =?," +
					"size_code=?,itm_barcode=?,standard_price=?,itm_cost=?," +
					"sale_price=?,vip_price=?,enable_os=?,os_qty=?,max_count=?,web_price=? "
					+ " where sku_id= ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, info.getItem_code());
			ps.setString(2, info.getColor_code());
			ps.setString(3, info.getSize_code());
			ps.setString(4, info.getBarcode());
			ps.setDouble(5, info.getStandard_price());
			ps.setDouble(6, info.getItem_cost());
			ps.setDouble(7, info.getSale_price());
			ps.setDouble(8, info.getVip_price());
			ps.setInt(9, info.getEnable_os());
			ps.setInt(10, info.getOs_qty());
			ps.setInt(11, info.getMax_count());
			
			if(info.getWeb_price()>0 ) {
				ps.setDouble(12, info.getWeb_price());
			} else {
				ps.setNull(12,Types.DOUBLE);
			}
			ps.setInt(13, info.getSku_id());
			
			ret = ps.executeUpdate();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}
	
	public static int updateAll(Connection conn, ProductSKUForm info ) throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String sql = "update prd_item_sku set standard_price=?," +
					"sale_price=?,vip_price=?,enable_os=?,os_qty=?,max_count=?,web_price=? "
					+ " where itm_code = (select itm_code from prd_item_sku where sku_id = ?)";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, info.getStandard_price());
			ps.setDouble(2, info.getSale_price());
			ps.setDouble(3, info.getVip_price());
			ps.setInt(4, info.getEnable_os());
			ps.setInt(5, info.getOs_qty());
			ps.setInt(6, info.getMax_count());
			
			if(info.getWeb_price()>0 ) {
				ps.setDouble(7, info.getWeb_price());
			} else {
				ps.setNull(7,Types.DOUBLE);
			}
			ps.setInt(8, info.getSku_id());
			
			ret = ps.executeUpdate();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}
}
