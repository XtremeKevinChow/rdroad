/*
 * Created on 2006-10-31
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
import java.sql.Statement;
import java.util.ArrayList;

import com.magic.crm.finance.entity.FinSales;
import com.magic.crm.promotion.form.*;
import com.magic.crm.promotion.entity.*;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CatalogDAO {
	public Catalog findByPrimaryKey(Connection con, int id)
	throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Catalog info=null;
	try {		


		 String sql="select a.id,a.name,a.effect_date,a.expired_date,a.status, a.description, ";
		  sql+=" a.msc as msccode  ";
		  sql+=" from prd_pricelists a  ";
		  sql+=" where a.id=? ";
		  //System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,id);
			
			rs = pstmt.executeQuery();
		
				if (rs.next()) {
					info=new Catalog();
					info.setID(rs.getInt("id"));
					//info.setPeriodical_id(rs.getString("periodical_id"));
					//info.setMember_category_id(rs.getInt("member_category_id"));
					info.setMsc(rs.getString("msccode"));	
					//info.setMscID(rs.getInt("msc"));					
					info.setName(rs.getString("name"));
					//info.setPrice_type_id(rs.getInt("price_type_id"));
					info.setDescription(rs.getString("description"));
					info.setEffect_date(rs.getString("effect_date").substring(0,10));
					info.setExpirped_date(rs.getString("expired_date").substring(0,10));
					
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
	//招募活动
	public Catalog findByKey(Connection con, int id)
	throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Catalog info=null;
	try {		

		 String sql="select a.*,b.name as recruitment_name  from prd_pricelists a,s_recruitment_type b where a.recruitment_type=b.id and a.price_type_id=1 and a.id=? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,id);
			
			rs = pstmt.executeQuery();
		
				if (rs.next()) {
					info=new Catalog();
					info.setID(rs.getInt("id"));
					info.setMsc(rs.getString("msc"));						
					info.setName(rs.getString("name"));
					info.setGift_number(rs.getString("gift_number"));
					info.setPrice_type_id(rs.getInt("price_type_id"));
					info.setDescription(rs.getString("description"));
					info.setEffect_date(rs.getString("effect_date").substring(0,10));
					info.setExpirped_date(rs.getString("expired_date").substring(0,10));	
					info.setRecruitment_type(rs.getInt("recruitment_type"));
					info.setRecruitmentName(rs.getString("recruitment_name"));
					info.setEntry_fee(rs.getDouble("Entry_fee"));
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
			CatalogForm info,String price_type_id) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			
			/*
			 * price_type_id=3 目录新增
			 */
			
				/*String sql="select id from prd_pricelists where msc=?";

				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,info.getMsc());
				rs=pstmt.executeQuery();
				String mscid="";
			    if(rs.next()){
			    	mscid=rs.getString(1);
			    }	
			    rs.close();
				pstmt.close();*/
				
				//sp = "{?=call catalog.f_catalog_add(?,?,?,?,?,?,?,?,?)}";
				String sql = "INSERT INTO PRD_PRICELISTS(ID,EFFECT_DATE,EXPIRED_DATE, " +
					" NAME,DESCRIPTION,RECRUITMENT_TYPE,COMPANY_ID,PRICE_TYPE_ID,MSC,gift_number) " +
					" VALUES(SEQ_PRD_PRICELISTS_ID.NEXTVAL,to_date(?,'yyyy-mm-dd')," +
					" to_date(?,'yyyy-mm-dd'),?,?,101,1,?,?,?)" ;
				pstmt = con.prepareStatement(sql);
				
				//cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
				//pstmt.setString(2,info.getPeriodical_id());
				
				//pstmt.setString(4, mscid);
				//pstmt.setInt(5, info.getMember_category_id());
				pstmt.setString(1, info.getEffect_date());
				pstmt.setString(2, info.getExpirped_date());
				pstmt.setString(3, info.getCatalogs_name());
				pstmt.setString(4, info.getDescription());
				if(price_type_id.equals("1")){
					pstmt.setInt(5, 1);
					pstmt.setString(7, info.getGift_number());
				} else if (price_type_id.equals("3")) {
					pstmt.setInt(5, 3);
					pstmt.setNull(7,java.sql.Types.VARCHAR);
				} else if (price_type_id.equals("5")) {
					pstmt.setInt(5, 5);
					pstmt.setNull(7,java.sql.Types.VARCHAR);
				} else {
					pstmt.setInt(5, 100);
					pstmt.setNull(7,java.sql.Types.VARCHAR);
				}
				pstmt.setString(6, info.getMsc());
				
				//pstmt.setInt(9,info.getCompany_id());
				//pstmt.setInt(10,info.getOperator_id());

				re = pstmt.executeUpdate();
				
				//cstmt.close();
				if (re < 0) {
					System.out.println("re is " + re);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
					pstmt.close();
				} catch (Exception e) {
				}
				
		}
		return String.valueOf(re);
	}
	/*****************************/	
	public void CatalogUpdate(Connection con,
			CatalogForm info) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;

        
		try {
			
			sql="update prd_pricelists set name=?,effect_date=to_date(?,'YYYY-MM-DD'),expired_date=to_date(?,'YYYY-MM-DD'),description=?,msc=? where status=0 and id=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,info.getName());
			pstmt.setString(2,info.getEffect_date());
			pstmt.setString(3,info.getExpirped_date());
			pstmt.setString(4,info.getDescription());
			pstmt.setString(5, info.getMsc());
			pstmt.setInt(6,info.getID());
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
	/*****************************/		
	/*
	 * 招募活动修改
	 */
	public void update(Connection con,CatalogForm info
			) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			//sp = "{?=call member.F_PRD_PRICELIST_UPDATE(?,?,?,?,?,?,?,?,?)}";
			String sql = " UPDATE prd_pricelists SET NAME = ?, " +
             " effect_date = to_date(?,'yyyy-mm-dd'),expired_date = to_date(?,'yyyy-mm-dd'), " +
             " description = ?,msc = ?, " +
             " recruitment_type = ?,entry_fee = ?,gift_number=? " +
             " WHERE ID = ?";
			ps = con.prepareStatement(sql);
			
			//cstmt = con.prepareCall(sp);
			//cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			//cstmt.setInt(2,info.getID());
			ps.setString(1,info.getName());
			ps.setString(2,info.getEffect_date());
			ps.setString(3,info.getExpirped_date());
			ps.setString(4,info.getDescription());
			ps.setString(5,info.getMsc());
			ps.setInt(6,info.getRecruitment_type());
			ps.setDouble(7,info.getEntry_fee());
			
			ps.setString(8, info.getGift_number());
	
			ps.setInt(9, info.getID());
			re = ps.executeUpdate();
			
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
					ps.close();
				} catch (Exception e) {
				}
		}
	}	
	/*****************************/			
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
			//sp = "{?=call catalog.f_pricelist_release(?)}";
			//cstmt = con.prepareCall(sp);
			//cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			//cstmt.setInt(2,Integer.parseInt(id));
			String sql ="update prd_pricelists set status=100 where id= ?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			re = pstmt.executeUpdate();
			//re = cstmt.getInt(1);
			pstmt.close();

			if (re < 0) {
				System.out.println("re is " + re);
			}else{
				String sQuery = "insert into magic.prd_items_temp (itm_id) "
					+ " select distinct a.itm_id from prd_item a inner join prd_pricelist_lines b on "
					+ " a.itm_code=b.item_code where b.pricelist_id=? and a.itm_id not in (select itm_id from prd_items_temp) ";
			//System.out.println(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, Integer.parseInt(id));

			pstmt.execute();				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
			throw e;
		} finally {
			
				try {
					pstmt.close();
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
			//sp = "{?=call catalog.f_pricelist_pause(?)}";
			//cstmt = con.prepareCall(sp);
			//cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			//cstmt.setInt(2,Integer.parseInt(id));
			String sql ="update prd_pricelists set status=-1 where id= ?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			re = pstmt.executeUpdate();
			//re = cstmt.getInt(1);
			pstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
			}else{
				String sQuery = "insert into magic.prd_items_temp (itm_id) "
					+ " select distinct a.itm_code from prd_item a inner join prd_pricelist_lines b on "
					+ " a.itm_code=b.item_code where b.pricelist_id=? and a.itm_id not in (select itm_id from prd_items_temp) ";
			//System.out.println(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, Integer.parseInt(id));

			pstmt.execute();				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
			throw e;
		} finally {
			try {
					pstmt.close();
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
			//sp = "{?=call catalog.f_pricelist_delete(?,?)}";
			//cstmt = con.prepareCall(sp);
			//cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			//cstmt.setInt(2,Integer.parseInt(id));
			String sql ="update prd_pricelists set status=-10 where id= ?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			re = pstmt.executeUpdate();
			pstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
			throw e;
		} finally {
			try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
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
	/*
     * 招募活动分页
     * 
     */
    public static ArrayList DataToPages(Connection con, String sql,int from, int to)
	throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList finCol = new ArrayList();
			
			ArrayList ret = new ArrayList();
			String sql1 = " SELECT * FROM ( SELECT t.*, rownum rownum_ FROM ( "
					+ sql + " )t WHERE rownum <= ?) B WHERE rownum_ >? ";
			
			PreparedStatement ps = con.prepareStatement(sql1);
			ps.setInt(1, to);
			ps.setInt(2, from);
			rs = ps.executeQuery();
			while (rs.next()) {
				
				Catalog info = new Catalog();
				info.setID(rs.getInt("id"));
				info.setMsc(rs.getString("msc"));	
				info.setName(rs.getString("name"));
				info.setEffect_date(rs.getString("effect_date"));
				info.setExpirped_date(rs.getString("expired_date"));
			    info.setEntry_fee(rs.getDouble("entry_fee"));
			    info.setStatus(rs.getInt("status"));
			    info.setIs_valid(rs.getInt("Is_valid"));
			    info.setGift_number(rs.getString("gift_number"));
			    if(info.getGift_number() == null) {
			    	info.setGift_number("");
			    }
				ret.add(info);
			}
			rs.close();
			ps.close();
			return ret;

    	}   
		public static int queryListCount(Connection conn, String sql)
			throws Exception {
			int ret = 0;
			String sql1 = " SELECT COUNT(*) "
					+ sql.substring(sql.toUpperCase().indexOf(" FROM "));
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			if (rs.next()) {
				ret = rs.getInt(1);
			}
			rs.close();
			st.close();
			return ret;
		}    
/******************************************/
}
