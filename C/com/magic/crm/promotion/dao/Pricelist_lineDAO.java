/*
 * Created on 2006-11-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.magic.crm.promotion.entity.*;
import com.magic.crm.promotion.form.*;
import com.magic.crm.product.dao.*;
import com.magic.crm.product.form.*;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Pricelist_lineDAO {
	public Pricelist_line findByPrimaryKey(Connection con, int id)
	throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Pricelist_line info=null;
	try {		

		 String sql="select a.*,b.name,c.color_code,c.size_code from prd_pricelist_lines a,s_catalog_edition b,prd_item_sku c  where  a.sku_id=c.sku_id and a.CATALOG_EDITION=b.id(+) and a.id=?  ";
		  //System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,id);
			
			rs = pstmt.executeQuery();
		
				if (rs.next()) {
					info=new Pricelist_line();
					info.setID(rs.getInt("id"));
					info.setPricelist_id(rs.getInt("PRICELIST_ID"));
					info.setSku_id(rs.getInt("sku_id"));
					info.setItem_code(rs.getString("item_code"));
					info.setPage(rs.getInt("PAGE"));
					
					info.setSell_type(rs.getInt("SELL_TYPE"));
					info.setStatus(rs.getInt("STATUS"));
					info.setCatalog_editon(rs.getInt("CATALOG_EDITION"));
					info.setCatalog_editon_name(rs.getString("name"));
					info.setOperator_id(rs.getInt("OPERATOR_ID"));
					info.setVip_price(rs.getDouble("VIP_PRICE"));
					info.setSale_price(rs.getDouble("SALE_PRICE"));
					info.setModify_date(rs.getString("MODIFY_DATE"));
					info.setColor_code(rs.getString("color_code"));
					info.setSize_code(rs.getString("size_code"));
															  								
				}
				
		} catch (SQLException e) {
			//System.out.println(e);
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
/*****************************/	
	public String insert(Connection con,
			Pricelist_lineForm info) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			/*sp = "{?=call catalog.F_CATALOG_LINES_ADD(?,?,?,?,?,?,?,?,?,?)}";
			cstmt = con.prepareCall(sp);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setInt(2, info.getItem_id());
			cstmt.setInt(3, info.getSell_type());
			cstmt.setInt(4, info.getCatalog_editon());
			cstmt.setInt(5, info.getCommitment());
			cstmt.setDouble(6, info.getCommon_price());
			cstmt.setDouble(7, info.getCard_price());
			cstmt.setInt(8, info.getPage());
			cstmt.setInt(9, info.getPricelist_id());
			cstmt.setInt(10, info.getOperator_id());
			cstmt.setFloat(11,info.getPlatina_Price());
			cstmt.execute();
			re = cstmt.getInt(1);
			cstmt.close();*/
			
			String sql = " INSERT INTO PRD_PRICELIST_LINES(ID,sku_id,item_code,SELL_TYPE,CATALOG_EDITION," +
			           " sale_PRICE,vip_PRICE,PAGE,PRICELIST_ID,OPERATOR_ID) " +
			           " (select SEQ_PRD_PRICELIST_LINES_ID.NEXTVAL,sku_id,itm_code,?,?," +
	                   " ?,?,?,?,? from prd_item_sku where itm_code=?) ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,info.getSell_type());
			pstmt.setInt(2, info.getCatalog_editon());
			pstmt.setDouble(3, info.getSale_price());
			pstmt.setDouble(4, info.getVip_price());
			pstmt.setInt(5, info.getPage());
			pstmt.setInt(6, info.getPricelist_id());
			pstmt.setInt(7, info.getOperator_id());
			
			pstmt.setString(8, info.getItem_code());
			
			re = pstmt.executeUpdate();
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			
				try {
					cstmt.close();
				} catch (Exception e) {
				}
		}
		return String.valueOf(re);
	}
	/*****************************/	
	public void update(Connection con,
			CatalogForm info) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;


		try {
			con.setAutoCommit(false);
			sql="update prd_pricelists set name=?,effect_date=to_date(?,'YYYY-MM-DD'),expired_date=to_date(?,'YYYY-MM-DD'),description=? where status=0 and id=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,info.getName());
			pstmt.setString(2,info.getEffect_date());
			pstmt.setString(3,info.getExpirped_date());
			pstmt.setString(4,info.getDescription());
			pstmt.setInt(5,info.getID());
			pstmt.executeUpdate();
		    
			String sql1="update prd_catalogs_basic set periodical_id=?,member_category_id=?,operator_id=?,catalogs_name=?,msc=?,operate_time=sysdate where pricelist_id=?";
			pstmt = con.prepareStatement(sql1);
			pstmt.setString(1,info.getPeriodical_id());
			pstmt.setInt(2,info.getMember_category_id());
			pstmt.setInt(3,info.getOperator_id());
			pstmt.setString(4,info.getName());
			pstmt.setInt(5,info.getMscID());
			pstmt.setInt(6,info.getID());
			pstmt.executeUpdate();			
			con.commit();
		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
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
	/*****************************/		
	/*
	 * 目录发布
	 */
	public void CatalogRelease(Connection con,
			String id) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			sp = "{?=call catalog.f_pricelist_release(?)}";
			cstmt = con.prepareCall(sp);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setInt(2,Integer.parseInt(id));
	
			cstmt.execute();
			re = cstmt.getInt(1);
			cstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			e.printStackTrace();
			throw e;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (Exception e) {
				}
		}
	}	
	/*****************************/		
	/*
	 * 目录中止
	 */
	public void CatalogPause(Connection con,
			String id) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			sp = "{?=call catalog.f_pricelist_pause(?)}";
			cstmt = con.prepareCall(sp);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setInt(2,Integer.parseInt(id));
	
			cstmt.execute();
			re = cstmt.getInt(1);
			cstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			e.printStackTrace();
			throw e;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (Exception e) {
				}
		}
	}		
	/*****************************/		
	/*
	 * 删除目录
	 */
	public void CatalogDel(Connection con,
			String id,String uid) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			sp = "{?=call catalog.f_pricelist_delete(?,?)}";
			cstmt = con.prepareCall(sp);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setInt(2,Integer.parseInt(id));
			cstmt.setInt(3,Integer.parseInt(uid));
	
			cstmt.execute();
			re = cstmt.getInt(1);
			cstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			e.printStackTrace();
			throw e;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (Exception e) {
				}
		}
	}	
	/*
	 * 删除目录行
	 */
	public void CatalogLineDel(Connection con,
			String id,String uid) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			//sp = "{?=call catalog.F_CATALOG_LINES_DEL(?,?)}";
			String sql="UPDATE PRD_PRICELIST_LINES SET status = -1,OPERATOR_ID =? WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(uid));
			pstmt.setInt(2, Integer.parseInt(id));
			
			re = pstmt.executeUpdate();
			
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			e.printStackTrace();
			throw e;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (Exception e) {
				}
		}
	}		
	/*
	 * 修改目录行
	 */
	/**
	 *    FUNCTION F_CATALOG_LINES_UPDATE(V_DOC_ID NUMBER,V_ITEM_ID NUMBER,V_SELL_TYPE NUMBER,V_CATALOG_EDITION NUMBER,
	       V_COMMITMENT NUMBER,V_COMMON_PRICE VARCHAR2,V_CARD_PRICE VARCHAR2,V_PAGE NUMBER,V_OPERATOR_ID NUMBER)
	   
	 */ 
	public static int CatalogLineUpdate(Connection con,
			Pricelist_lineForm info) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			//sp = "{?=call catalog.F_CATALOG_LINES_UPDATE(?,?,?,?,?,?,?,?,?,?)}";
			String sql = " UPDATE PRD_PRICELIST_LINES SET SELL_TYPE = ?,CATALOG_EDITION = ?, " +
	          " sale_PRICE=?,vip_PRICE=?,PAGE=?,OPERATOR_ID=?, " +
	          " sku_id=? ,item_code=(select itm_code from prd_item_set where sku_id= ?)" +
			  " WHERE ID=? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, info.getSell_type());
			pstmt.setInt(2,info.getCatalog_editon());
			pstmt.setDouble(3, info.getSale_price());
			pstmt.setDouble(4, info.getVip_price());
			pstmt.setInt(5, info.getPage());
			pstmt.setInt(6, info.getOperator_id());
			pstmt.setInt(7, info.getSku_id());
			pstmt.setInt(8, info.getSku_id());
			pstmt.setInt(9, info.getId());
			
			re =pstmt.executeUpdate();
		    
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pstmt.close();
				} catch (Exception e) {
				}
		}
		return re;
	}		
	public static int updateLinesByItemCode(Connection con,
			Pricelist_lineForm info) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			//sp = "{?=call catalog.F_CATALOG_LINES_UPDATE(?,?,?,?,?,?,?,?,?,?)}";
			String sql = " UPDATE PRD_PRICELIST_LINES SET SELL_TYPE = ?,CATALOG_EDITION = ?, " +
	          " sale_PRICE=?,vip_PRICE=?,PAGE=?,OPERATOR_ID=? " +
	          " WHERE pricelist_id=? and item_code =? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, info.getSell_type());
			pstmt.setInt(2,info.getCatalog_editon());
			pstmt.setDouble(3, info.getSale_price());
			pstmt.setDouble(4, info.getVip_price());
			pstmt.setInt(5, info.getPage());
			pstmt.setInt(6, info.getOperator_id());
			pstmt.setInt(7, info.getPricelist_id());
			pstmt.setString(8, info.getItem_code());
			
			re =pstmt.executeUpdate();
		    
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pstmt.close();
				} catch (Exception e) {
				}
		}
		return re;
	}		
	/*****************************/	
	public int checkInsert(Connection con,
			CatalogForm info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql = null;
        int checkid=0;

		try {

			sql="select * from prd_catalogs_basic where catalogs_name=? and periodical_id=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,info.getName());
			pstmt.setString(2,info.getPeriodical_id());
			rs=pstmt.executeQuery();
		    if(rs.next()){
		    	checkid=1;
		    }

		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
  return checkid;
	}		
	/*************检查目录行产品是否在这期目录已存在***************/	
	public int checkLineInsert(Connection con,
			Pricelist_lineForm info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql = null;
        int checkid=0;

		try {

			sql="select 1 from prd_pricelist_lines where status=0 and item_code=? and pricelist_id=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,info.getItem_code());
			pstmt.setInt(2,info.getPricelist_id());
			rs=pstmt.executeQuery();
		    if(rs.next()){
		    	checkid=1;
		    }
		    rs.close();

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
  return checkid;
	}		
/******************************************/
}
