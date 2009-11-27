package com.magic.crm.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.magic.crm.product.entity.ProductSKU;
import com.magic.crm.product.form.Product2Form;
import com.magic.crm.product.form.Product2SetForm;
import com.magic.crm.product.form.ProductSKUForm;

public class Product2SetDtlDAO implements java.io.Serializable {
	private static Logger log = Logger.getLogger(Product2DAO.class);

	public static int insert(Connection conn, Product2SetForm info)
			throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//step1 插入套件设置表
			String sql = "insert into prd_item_set(id,set_item_code,part_item_code,"
					+ " color_code,standard_price,itm_cost,sale_price,vip_price) "
					+ " select seq_prd_item_sets_id.nextval,?,itm_code,?,standard_price,"
					+ " itm_cost,sale_price,vip_price "
					+ " from prd_item where itm_code=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, info.getSet_item_code());

			ps.setString(2, info.getColor_code());
			// ps.setDouble(3, info.getSale_price());
			// ps.setDouble(4, info.getVip_price());

			ps.setString(3, info.getPart_item_code());
			ret = ps.executeUpdate();
			//ps.close();

			// step2 更改套装产品属性
			resetSetItem(conn,info.getSet_item_code());
			

		} catch (SQLException e) {
			log.debug(e);
			throw e;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

		return ret;
	}

	public static int update(Connection conn, Product2SetForm info)
			throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "update prd_item_set set part_item_code = ?,color_code =?,"
					+ " sale_price=?,vip_price=?,is_optional=?"
					+ " where id =? ";
			ps = conn.prepareStatement(sql);

			ps.setString(1, info.getPart_item_code());
			ps.setString(2, info.getColor_code());
			ps.setDouble(3, info.getSale_price());
			ps.setDouble(4, info.getVip_price());
			ps.setInt(5, info.getIs_optional());
			ps.setInt(6, info.getSet_id());

			ret = ps.executeUpdate();

		} catch (SQLException e) {
			log.debug(e);
			throw e;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

		return ret;
	}

	public static int resetSetItem(Connection conn, String set_item_code) 
	throws Exception {
		int ret=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = " update prd_item t1 set (standard_price,itm_cost,sale_price,vip_price,web_price)= "
					+ " (select sum(standard_price),sum(itm_cost),sum(sale_price),sum(vip_price),sum(web_price) from prd_item_set t2 where t1.itm_code=t2.set_item_code)"
					+ " where t1.itm_code= ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,set_item_code);
			ret = ps.executeUpdate();
			ps.close();

			sql = " update prd_item_sku t1 set (standard_price,itm_cost,sale_price,vip_price,web_price) = "
					+ " (select sum(standard_price),sum(itm_cost),sum(sale_price),sum(vip_price),sum(web_price) from prd_item_set t2 where t1.itm_code=t2.set_item_code) "
					+ " where t1.itm_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,set_item_code);
			ret = ps.executeUpdate();

		} catch (SQLException e) {
			log.debug(e);
			throw e;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}
	public static int updatePartPrice(Connection conn, Product2SetForm info)
			throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "update prd_item_set set "
					+ " standard_price=?, sale_price=?,vip_price=?,web_price=? where id =? ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, info.getStandard_price());
			ps.setDouble(2, info.getSale_price());
			ps.setDouble(3, info.getVip_price());
			if(info.getWeb_price()>0) {
				ps.setDouble(4, info.getWeb_price());
			} else {
				ps.setNull(4, Types.DOUBLE);
			}
			ps.setInt(5, info.getSet_id());
			ret = ps.executeUpdate();
			//ps.close();
			
			resetSetItem(conn,info.getSet_item_code());

			
		} catch (SQLException e) {
			log.debug(e);
			throw e;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

		return ret;
	}

	public static int delete(Connection conn, int set_id) throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String set_code = null;
		try {
			
			String sql = "select set_item_code from prd_item_set where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, set_id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				set_code = rs.getString("set_item_code");
			}
			rs.close();
			ps.close();
			
			sql = "delete from prd_item_set where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, set_id);
			ret = ps.executeUpdate();
			ps.close();
			
			resetSetItem(conn,set_code);

		} catch (SQLException e) {
			log.debug(e);
			throw e;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

		return ret;
	}

	public static int findByPK(Connection con, Product2Form info)
			throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int ret = -1;
		try {
			String sql = "select * from prd_item where itm_code = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.getItem_code());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = 0;
				info.setItem_code(rs.getString("itm_code"));
				info.setItem_name(rs.getString("itm_name"));
				info.setItem_name_en(rs.getString("itm_name_en"));
				info.setItem_type(rs.getInt("itm_type"));
				// info.setColor_code(rs.getString("color_code"));
				// info.setColor_name(rs.getString("color_name"));
				// info.setSize_code(rs.getString("size_code"));
				// info.setSize_name(rs.getString("size_name"));
				info.setBarcode(rs.getString("itm_barcode"));
				// log.debug(2);
				info.setMax_count(rs.getInt("max_count"));
				info.setStandard_price(rs.getDouble("standard_price"));
				info.setItem_cost(rs.getDouble("itm_cost"));
				info.setSale_price(rs.getDouble("sale_price"));
				info.setVip_price(rs.getDouble("vip_price"));
				info.setItem_category(rs.getInt("category_id"));
				info.setItem_fabric(rs.getString("itm_fabric"));
				info.setItem_lining(rs.getString("itm_lining"));
				info.setItem_origin(rs.getString("itm_origin"));
				info.setEnable_os(rs.getInt("enable_os"));
				info.setOs_qty(rs.getInt("os_qty"));
				info.setItem_desc(rs.getString("itm_desc"));
				info.setItem_unit(rs.getInt("itm_unit"));
				// log.debug(4);

			}

		} catch (SQLException e) {
			log.debug(e);
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

	public static ArrayList listItemPart(Connection con, Product2Form info)
			throws Exception {

		ArrayList ret = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = " select t1.*,t2.itm_code,t2.itm_name,t3.name as color_name "
					+ " from prd_item_set t1 join prd_item t2 on t1.part_item_code = t2.itm_code"
					+ " left join prd_item_color t3 on t1.color_code = t3.code "
					+ " where t1.set_item_code = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.getItem_code());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product2SetForm pf = new Product2SetForm();

				pf.setSet_id(rs.getInt("id"));
				pf.setPart_item_code(rs.getString("itm_code"));
				pf.setPart_item_name(rs.getString("itm_name"));
				pf.setColor_name(rs.getString("color_name"));
				pf.setIs_optional(rs.getInt("is_optional"));
				pf.setSale_price(rs.getDouble("sale_price"));
				pf.setVip_price(rs.getDouble("vip_price"));

				ret.add(pf);
			}
			rs.close();

		} catch (SQLException e) {
			log.debug(e);
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}
}
