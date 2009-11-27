package com.magic.exchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.magic.utils.StringUtil;
import com.magic.crm.util.*;

/**
 * 产品信息同步到网站
 *
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 *
 * refacted by zhux 20050405
 *
 */
public class Product2Net {
	private static Logger log = Logger.getLogger(ProductInfo.class);

	public Product2Net() {

	}

	public void execute() {
		Connection conOra = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection conMs = null;

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		try {
			///////////////////////////////////////// add zhux 2005-01-26 得到替代品的库存
			conOra = DBManager2.getConnection();
			ps = conOra.prepareStatement("select a.use_qty- a.frozen_qty as qty from jxc.sto_stock a, prd_items b where a.sto_no='000' and b.item_id = ? and a.item_id = b.replace_item_id ");

			conMs = DBManagerMS.getConnection();
			String sql = " update gdsmst set gdsmst_updatedate=getdate(),gdsmst_lackflag=?,gdsmst_vipprice=?,"
				+ "gdsmst_memberprice=?,gdsmst_webprice=?,gdsmst_saleprice=?,"
				+ "type_code=?,gdsmst_islastsale=? where gdsmst_gdsid=?";
			ps1 = conMs.prepareStatement(sql);
			ps2 = conMs.prepareStatement(" update gdsmst1 set gdsmst_storageaccount=? where gdsmst_gdsid=? ");
			ps3 = conMs.prepareStatement(" select count(*) from gdsmst where gdsmst_gdsid=?");
			
			stmt = conOra.createStatement();
			rs = stmt.executeQuery("select * from vw_web_item");
			while (rs.next()) {
				ProductInfo info = new ProductInfo();

				String str = rs.getString("item_code");
				info.setItem_code(rs.getString("item_code"));
				info.setItem_name(StringUtil.replaceStrEx(rs
						.getString("item_name"), "'", "''"));
				info.setBarcode(rs.getString("barcode"));
				String testISBN = StringUtil.cEmpty(rs.getString("ISBN"));
				info.setISBN(testISBN);

				//调整
				info.setCategory_id(StringUtil.cEmpty(rs
						.getString("CATALOG_CODE")));
				info.setItem_type_id(rs.getInt("item_type"));

				String testPublishing_house = StringUtil.cEmpty(rs
						.getString("publishing_house_name"));
				info.setPublishing_house(testPublishing_house);

				String testAuthor = StringUtil.replaceStrEx(StringUtil
						.cEmpty(rs.getString("author")), "'", "''");
				info.setAuthor(testAuthor);
				info.setComments(StringUtil.replaceStrEx(StringUtil.cEmpty(rs
						.getString("comments")), "'", "''"));
				info.setStandard_price(rs.getFloat("standard_price"));
				info.setIs_commitment(rs.getInt("is_commitment"));
				info.setItem_size(rs.getString("item_size"));
				info.setCommon_price(rs.getFloat("common_price"));
				info.setCard_price(rs.getFloat("card_price"));
				info.setWeb_price(rs.getFloat("web_price"));
				info.setIs_last_sale(rs.getInt("is_last_sale"));
				info.setClub_id(rs.getInt("clubid"));

				//产品数量和库存状态之间的关系在这里进行判断和设置
				//现库存数量
				int temp_quantity = rs.getInt("inventory_quantity");

				////////////////////////////////////add zhux 2005-01-26
				// 如果现有库存数量<=50,而存在替代品的,使用替代品库存来顶替原有库存
				// (全是实际库存,不考虑冻结库存和采购单库存)
				if (temp_quantity <= 50) {
					long item_id = (long) rs.getLong("item_id");
					ps.setLong(1, item_id);
					ResultSet rst = ps.executeQuery();
					if (rst.next()) {
						temp_quantity = rst.getInt("qty") + temp_quantity;
					}
					rst.close();
				}
				///////////////////////////////////end add zhux 2005-01-26

				//即将到货数量
				//int temp_comming_quantity=rs.getInt("comming_quantity");
				int temp_comming_quantity = 0;
				if ((temp_quantity + temp_comming_quantity) > 0) {
					//设置传入网站中的库存数量和状态
					info.set_Inventory_quantity(temp_quantity
							+ temp_comming_quantity);
					info.setInventory_status(0);//0表示正常
				} else {
					//设置传入网站中的库存数量和状态
					info.set_Inventory_quantity(temp_quantity
							+ temp_comming_quantity);
					info.setInventory_status(1);//1表示缺货
				}

				String catalog_code_str = StringUtil.cEmpty(rs
						.getString("catalog_code"));
				info.setCatalog_code(catalog_code_str);

				ps3.setString(1,info.getItem_code());
				ResultSet rs1= ps3.executeQuery();
				if(rs1.next()) {
					if (rs1.getInt(1)>0) {
						updateNetProduct(ps1,ps2,info);
						log.info("更新产品成功 \t货号" + info.getItem_code() + "\n");
					} else {
						insertNetProduct(conMs,info);
						log.info("插入产品成功 \t货号"+ info.getItem_code() + "\n");
					}
				}
				rs1.close();
				
			}
			rs.close();
			stmt.close();

		} catch (SQLException se) {
			log.error("",se);
		} finally {

			try {
				ps.close();
				ps1.close();
				ps2.close();
				ps3.close();
			} catch (Exception e) {
			}
			try {
				conOra.close();
			} catch (Exception e) {
			}
			try {
				conMs.close();
			} catch (Exception e) {
			}



		}

	}

	public static void main(String args[]) {
		new Product2Net().execute();
	}

	/**
	 * method updateNetProduct()
	 *
	 * @param void
	 * @return void
	 */
	public int updateNetProduct(PreparedStatement ps,PreparedStatement ps1,ProductInfo info) {
		int ret = -1;
		try {
			ps.setInt(1,info.getInventory_status());
			ps.setDouble(2,info.getCard_price());
			ps.setDouble(3,info.getCommon_price());
			ps.setDouble(4,info.getWeb_price());
			//ps.setInt(5,info.get_Inventory_quantity());
			ps.setDouble(5,info.getStandard_price());
			ps.setString(6,String.valueOf(info.getItem_type_id()));
			ps.setInt(7,info.getIs_last_sale());
			ps.setString(8,info.getItem_code());
			ret = ps.executeUpdate();
			//if (ret >1) {
				ps1.setInt(1,info.get_Inventory_quantity());
				ps1.setString(2,info.getItem_code());
				ret = ps1.executeUpdate();
			//}

		} catch (Exception se) {
			se.printStackTrace();
			log.error("更新网上产品出错：\t货号：" + info.getItem_code(), se);
		}
		return ret;
	}

	public int insertNetProduct(Connection conn,ProductInfo info) {
		int ret = -1;
		try {
			String temp="";
			String sql2 = "insert into gdsmst(gdsmst_gdsname,gdsmst_barcode,gdsmst_isbn,gdsmst_crmrackcode,type_code,"
					+ "gdsmst_manufacture,gdsmst_stdvalue1,gdsmst_saleprice,gdsmst_dutyflag,gdsmst_lackflag,"
					+ "gdsmst_vipprice,gdsmst_memberprice,gdsmst_webprice,gdsmst_gdsid,"
					+ "gdsmst_inprice,gdsmst_taxrate,"
					+ "gdsmst_updatedate,gdsmst_createdate,"
					+ "gdsmst_rackcode,gdsmst_islastsale,gdsmst_clubid) "
					+ "values('"
					+ info.getItem_name()
					+ "','"
					+ info.getBarcode()
					+ "','"
					+ info.getISBN()
					+ "','"
					+ info.getCategory_id()
					+ "',"
					+ "'"
					+ info.getItem_type_id()
					+ "','"
					+ info.getPublishing_house()
					+ "','"
					+ info.getAuthor()
					+ "',"
					+ info.getStandard_price()
					+ ","
					+ info.getIs_commitment()
					+ ","
					+ ""
					+ info.getInventory_status()
					+ ","
					+ info.getCard_price()
					+ ","
					+ info.getCommon_price()
					+ ","
					+ ""
					+ info.getWeb_price()
					+ ",'"
					+ info.getItem_code()
					+ "',"
					+ info.getStandard_price()
					+ ",'"
					+ temp
					+ "',getdate(),getdate(),'"
					+ info.getCatalog_code()
					+ "',"
					+ info.getIs_last_sale() 
					+ ","
					+ info.getClub_id()
					+ ")";
			log.debug(sql2);
			Statement st = conn.createStatement();
			ret = st.executeUpdate(sql2);
			sql2 = " insert into gdsmst1 (gdsmst_gdsid,gdsmst_storageaccount)"
				 + " values ('" + info.getItem_code() + "'," + info.get_Inventory_quantity() + ")";
			ret = st.executeUpdate(sql2);
			st.close();
		} catch(Exception e) {
			e.printStackTrace();
			log.error("插入网站产品信息出错 \t货号：" + info.getItem_code(),e);
		}
		return ret;
	}

}
