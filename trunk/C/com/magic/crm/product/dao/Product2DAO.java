package com.magic.crm.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.magic.crm.product.entity.ProductSKU;
import com.magic.crm.product.form.Product2Form;
import com.magic.crm.product.form.Product2SetForm;
import com.magic.crm.product.form.ProductSKUForm;

public class Product2DAO implements java.io.Serializable {
	private static Logger log = Logger.getLogger(Product2DAO.class);

	public static int insert(Connection conn, Product2Form info)
			throws Exception {
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
					+ " itm_unit,itm_bottom,itm_side,itm_other,main_category,itm_id ,sale_price2) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "(select catalog_id from prd_item_category "
					+ "	where parent_id=0 "
					+ "	start with catalog_id= ? "
					+ "	connect by catalog_id =prior parent_id),seq_prd_item_id.nextval,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, info.getItem_code());
			ps.setString(2, info.getItem_name());
			ps.setString(3, info.getItem_name_en());
			ps.setInt(4, info.getItem_type());
			ps.setInt(5, info.getItem_category());
			ps.setDouble(6, info.getItem_cost());
			ps.setDouble(7, info.getStandard_price());
			ps.setDouble(8, info.getSale_price());
			ps.setDouble(9, info.getVip_price());
			ps.setInt(10, info.getMax_count());
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
			ps.setDouble(24, info.getRetail_price());

			ret = ps.executeUpdate();
			ps.close();

			if (info.getItem_type() == 2 || info.getItem_type() == 3) {
				sql = "insert into prd_item_sku(sku_id,itm_code,"
						+ " color_code,size_code,"
						+ " sale_price,vip_price,max_count,"
						+ " enable_os,os_qty,itm_barcode,itm_unit,itm_id ) "
						+ " values(seq_prd_item_sku_id.nextval,?,0,0,0,0,?,?,?,?,?,(select itm_id from prd_item where itm_code = ? ))";
				ps = conn.prepareStatement(sql);
				ps.setString(1, info.getItem_code());
				ps.setInt(2, info.getMax_count());
				ps.setInt(3, info.getEnable_os());
				ps.setInt(4, info.getOs_qty());
				ps.setString(5, info.getBarcode());
				ps.setInt(6, info.getItem_unit());
				ps.setString(7, info.getItem_code());
				ret = ps.executeUpdate();
				
			}

		} catch (SQLException e) {
			log.debug(e);
			throw e;
		} finally {
			try {ps.close();} catch(Exception e) {};
		}

		return ret;
	}

	public static int update(Connection conn, Product2Form info)
			throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "update prd_item set itm_name = ?,itm_name_en =?,"
					+ " itm_type=?,category_id=?,itm_cost=?,standard_price=?,"
					+ " sale_price=?,vip_price=?,max_count=?,itm_title=?,itm_fabric=?,"
					+ " itm_lining=?,itm_origin=?,itm_desc=?,enable_os=?,os_qty=?,itm_barcode=?, "
					+ " itm_bottom=?,itm_side=?,sale_price2=? " + " where itm_code=? ";
			ps = conn.prepareStatement(sql);

			ps.setString(1, info.getItem_name());
			ps.setString(2, info.getItem_name_en());
			ps.setInt(3, info.getItem_type());
			ps.setInt(4, info.getItem_category());
			ps.setDouble(5, info.getItem_cost());
			ps.setDouble(6, info.getStandard_price());
			ps.setDouble(7, info.getSale_price());
			ps.setDouble(8, info.getVip_price());
			ps.setInt(9, info.getMax_count());
			ps.setString(10, info.getItem_title());
			ps.setString(11, info.getItem_fabric());
			ps.setString(12, info.getItem_lining());
			ps.setString(13, info.getItem_origin());
			ps.setString(14, info.getItem_desc());
			ps.setInt(15, info.getEnable_os());
			ps.setInt(16, info.getOs_qty());
			ps.setString(17, info.getBarcode());
			ps.setString(18, info.getItem_bottom());
			ps.setString(19, info.getItem_side());
			ps.setDouble(20, info.getRetail_price());

			ps.setString(21, info.getItem_code());
			ret = ps.executeUpdate();
			ps.close();
			
			//更新对应的套装品的市场价
			sql = "update prd_item_set set standard_price = ? where part_item_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, info.getStandard_price());
			ps.setString(2, info.getItem_code());
			ps.executeUpdate();
			ps.close();
			
			sql = "select set_item_code from prd_item_set where part_item_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,info.getItem_code());
			rs = ps.executeQuery();
			while(rs.next()){
				String set_code = rs.getString("set_item_code");
				Product2SetDtlDAO.resetSetItem(conn, set_code);
			}
			rs.close();
			

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

	public static String generateCode(Connection conn) throws Exception {
		String ret = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select to_char(sysdate,'yy')||lpad(nvl(max(to_number(substr(itm_code,3,4)))+1,'1'),4,'0')"
					+ " from prd_item where substr(itm_code,1,2)=to_char(sysdate,'yy')";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			}
			rs.close();

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

	public static String generateSetCode(Connection conn) throws Exception {
		String ret = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select 'T'||to_char(sysdate,'yy')||lpad(nvl(max(to_number(substr(itm_code,4)))+1,'1'),4,'0')"
					+ " from prd_item where substr(itm_code,2,2)=to_char(sysdate,'yy')";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			}
			rs.close();

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

	public static int delete(Connection conn, String itemcode) throws Exception {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String sql = "delete from prd_item_sku where itm_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemcode);
			ret = ps.executeUpdate();
			ps.close();

			sql = "delete from prd_item where itm_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemcode);
			ret = ps.executeUpdate();
			ps.close();

			sql = "delete from prd_item_set where set_item_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemcode);
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

	public static int findByItemCode(Connection con, Product2Form info)
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
				info.setItem_bottom(rs.getString("itm_bottom"));
				info.setItem_side(rs.getString("itm_side"));
				info.setWeb_price(rs.getDouble("web_price"));
				info.setRetail_price(rs.getDouble("sale_price2"));
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
			String sql = " select t1.*,t2.itm_code,t2.itm_name,t2.standard_price,t3.name as color_name "
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
				pf.setStandard_price(rs.getDouble("standard_price"));
				pf.setIs_optional(rs.getInt("is_optional"));
				pf.setSale_price(rs.getDouble("sale_price"));
				pf.setVip_price(rs.getDouble("vip_price"));
				pf.setWeb_price(rs.getDouble("web_price"));

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

	public static ArrayList query(Connection con, Product2Form info)
			throws Exception {

		ArrayList ret = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select t1.itm_code,t1.itm_name,t1.saleflag,"
					+ "decode(itm_type,1,'普通商品',2,'系列商品',3,'套装商品',0,'辅料','无') itm_type_name,"
					+ "t2.catalog_name ,"
					+ "STANDARD_PRICE,SALE_PRICE,VIP_PRICE,sale_price2 "
					+ " from prd_item t1 "
					+ "left join prd_item_category t2 on t1.category_id=t2.catalog_id where t1.itm_type>0 ";
			if (info.getQry_item_code() != null
					&& !info.getQry_item_code().equals("")) {
				sql = sql + " and t1.itm_code like '" + info.getQry_item_code()
						+ "%' ";
			}
			if (info.getQry_item_name() != null
					&& !info.getQry_item_name().equals("")) {
				sql = sql + " and t1.itm_name like '%"
						+ info.getQry_item_name() + "%' ";
			}
			if (info.getQry_item_category() != null
					&& !info.getQry_item_category().equals("0")) {
				sql = sql + " and t1.category_id = "
						+ info.getQry_item_category();
			}

			sql += " and rownum<50";

			pstmt = con.prepareStatement(sql);
			// pstmt.setString(1, info.getItem_code());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product2Form pf = new Product2Form();
				pf.setItem_code(rs.getString("itm_code"));
				pf.setItem_name(rs.getString("itm_name"));
				pf.setItem_type_name(rs.getString("itm_type_name"));
				pf.setItem_category_name(rs.getString("catalog_name"));
				pf.setStandard_price(rs.getDouble("standard_price"));
				pf.setSale_price(rs.getDouble("sale_price"));
				pf.setVip_price(rs.getDouble("vip_price"));
				pf.setSaleflag(rs.getString("saleflag"));
				pf.setRetail_price(rs.getDouble("sale_price2"));
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

	public static ArrayList queryStock(Connection con, String item_code )
			throws Exception {

		ArrayList ret = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = " select t1.itm_code,t1.itm_name,t3.standard_price,t3.sale_price,"
				    + " t3.vip_price,t3.color_code,t4.name as color_name,t3.size_code,t5.name as size_name,"
				    + " t3.os_qty + t6.use_qty-t6.frozen_qty as availqty "
					+ " from prd_item t1 join prd_item_sku t3 on t1.itm_code =t3.itm_code "
					+ " left join prd_item_color t4 on t3.color_code = t4.code " +
					 " left join prd_item_size t5 on t3.size_code = t5.code " +
					 " left join sto_stock t6 on t3.sku_id = t6.sku_id where t1.itm_code = ? ";
					
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, item_code);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSKUForm pf = new ProductSKUForm();
				pf.setItem_code(rs.getString("itm_code"));
				pf.setItem_name(rs.getString("itm_name"));
				//pf.setItem_type_name(rs.getString("itm_type_name"));
				//pf.setItem_category_name(rs.getString("catalog_name"));
				pf.setStandard_price(rs.getDouble("standard_price"));
				pf.setSale_price(rs.getDouble("sale_price"));
				pf.setVip_price(rs.getDouble("vip_price"));
				pf.setColor_code(rs.getString("color_code"));
				pf.setColor_name(rs.getString("color_name"));
				pf.setSize_code(rs.getString("size_code"));
				pf.setSize_name(rs.getString("size_name"));
				pf.setAvailQty(rs.getInt("availqty"));

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

	public static ArrayList listItemColor(Connection conn, String item_code)
			throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement(" select distinct t2.code,t2.name from prd_item_sku t1 join "
							+ " prd_item_color t2 on t1.color_code = t2.code"
							+ " where t1.itm_code = ? order by code");
			ps.setString(1, item_code);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LabelValueBean info = new LabelValueBean();
				info.setValue(rs.getString("code"));
				info.setLabel(rs.getString("name") + "(" + rs.getString("code")
						+ ")");

				ret.add(info);
			}
			rs.close();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}

	public static ArrayList listItemSize(Connection conn, String item_code)
			throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement(" select distinct t2.code,t2.name from prd_item_sku t1 join "
							+ " prd_item_size t2 on t1.size_code = t2.code"
							+ " where t1.itm_code = ? order by code");
			ps.setString(1, item_code);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LabelValueBean info = new LabelValueBean();
				info.setValue(rs.getString("code"));
				info.setLabel(rs.getString("name"));

				ret.add(info);
			}
			rs.close();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}

}
