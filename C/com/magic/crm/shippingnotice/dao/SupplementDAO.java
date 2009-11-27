/*
 * Created on 2007-3-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.shippingnotice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.magic.crm.shippingnotice.entity.*;
import com.magic.crm.shippingnotice.form.*;

import com.magic.crm.member.dao.*;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SupplementDAO {
	
	public static void insertSupplementMst(Connection conn, Supplement info)
    throws SQLException {

		PreparedStatement pstmt = null;
		String sql = "insert into jxc.STO_SUPPLEMENT_MST " 
		    	+ "("
		        + "ID, MEMBER_ID, REQUIRE_DATE, SHIP_ID, REDELIVERY_TYPE, "
		        + "IS_RETURN_ORGIN, IS_POSTAGE, WRITER, WRITE_DATE, OPERATOR,OP_TIME,STATUS " 
		        + ") "
		        + "values " 
		        + "(" 
		        + "jxc.seq_sto_supplement_mst_id.nextval, ?, to_date('"+info.getRequire_date()+"','yyyy-mm-dd'), ?, ?, "
		        + "?, ?, ?, sysdate, ? ,sysdate,?" 
		        + ") ";
			try {
			    pstmt = conn.prepareStatement(sql);
			    pstmt.setInt(1, info.getMember_id());
			    pstmt.setLong(2, info.getShip_id());
			    pstmt.setInt(3, info.getRedelivery_type());
			    pstmt.setInt(4, info.getIs_return_orgin());
			    pstmt.setInt(5, info.getIs_postage());
			    pstmt.setInt(6, info.getWriter());
			    pstmt.setInt(7, info.getOperator());
			    pstmt.setInt(8, 1);
			
			    pstmt.execute();
			
			} catch (SQLException ex) {
			    throw ex;
			} finally {
			    if (pstmt != null)
			        try {
			            pstmt.close();
			        } catch (SQLException ex) {
			        }
			}
	}
	public static void insertSupplementDtl(Connection conn, Supplement info)
    throws SQLException {

		PreparedStatement pstmt = null;
		String sql = "insert into JXC.STO_SUPPLEMENT_DTL " 
		    	+ "("
		        + "SD_ID, SS_ID, ITEM_ID, QTY, PRICE, "
		        + "REMARK, SELL_TYPE, DTL_TYPE, ORGIN_DTL_ID " 
		        + ") "
		        + "values " 
		        + "(" 
		        + "jxc.seq_sto_supplement_dtl_id.nextval, (select max(id) from jxc.STO_SUPPLEMENT_MST), ?, ?, ?, "
		        + "?, ?, ?, ?" 
		        + ") ";
			try {
			    pstmt = conn.prepareStatement(sql);
			    
			    pstmt.setInt(1, info.getItem_id());
			    pstmt.setInt(2, info.getQty());
			    pstmt.setDouble(3, info.getPrice());
			    pstmt.setString(4, info.getRemark());
			    pstmt.setInt(5, info.getSell_type());
			    pstmt.setInt(6, info.getDtl_type());
			    pstmt.setInt(7, info.getOrgin_dtl_id());

			
			    pstmt.execute();
			
			} catch (SQLException ex) {
			    throw ex;
			} finally {
			    if (pstmt != null)
			        try {
			            pstmt.close();
			        } catch (SQLException ex) {
			        }
			}
	}	
	public static int createSupply(Connection conn, int id,int uid
    ) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	CallableStatement cstmt = null;
	String sp = null;
	int re = 0;
	int ship_id=0;
	try {
	/*
	        cstmt = conn.prepareCall("{call magic.orders.p_return_for_inner(?, ?)}");
            cstmt.setInt(1, param.getPurID());
            cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
            cstmt.execute();
            int ret = cstmt.getInt(2);
            return ret;

	 */
		cstmt = conn.prepareCall("{call orders.p_supply_shippingnotices(?,?,?,?)}");		
		//sp = "{call orders.p_supply_shippingnotices(?,?,?)}";
		//cstmt = conn.prepareCall(sp);
	  
		cstmt.setInt(1,id);
		cstmt.setInt(2,uid);
		cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
		cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
	
		cstmt.execute();
		re = cstmt.getInt(3);
		ship_id=cstmt.getInt(4);
		//System.out.println(ship_id);
		cstmt.close();
		if (re < 0) {
			System.out.println("re is " + re);
			if(re==-5){
				ship_id=-5;
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
	return ship_id;
	}	
}
