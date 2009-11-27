/*
 * Created on 2006-12-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import com.magic.crm.promotion.entity.*;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mbr_gift_money_by_orderDAO {
	public static void insert(Connection con,Mbr_gift_money_by_order info)
	throws SQLException {
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
	String sQuery = "INSERT INTO MBR_GIFT_MONEY_BY_ORDER( ID,GIFT_NUMBER,LEVEL_ID,ORDER_REQUIRE,DIS_AMT,STATUS, is_discount, dis_type,begin_date,end_date,product_group_id)"
			+ " VALUES(SEQ_MBR_GIFT_MONEY_BY_ORDER_ID.nextval,?,?, ?,?,?,?,?,?,?,?)";
	pstmt = con.prepareStatement(sQuery);
	pstmt.setString(1,info.getGift_number() );
	pstmt.setInt(2, info.getLevel_id());
	pstmt.setDouble(3, info.getOrder_require());
	pstmt.setDouble(4, info.getDis_amt());
	pstmt.setInt(5, 0);
	pstmt.setString(6, info.getIs_discount());
	pstmt.setInt(7, info.getDis_type());
	pstmt.setDate(8, info.getBegin_date());
	pstmt.setDate(9, info.getEnd_date());
	pstmt.setInt(10, info.getItem_group_id());
	pstmt.execute();
				
} catch (SQLException e) {
		e.printStackTrace();
} finally {
	if (rs != null)
		try {
			rs.close();
		}catch (Exception e){}
	
	if (pstmt != null)
		try {
			pstmt.close();
		} catch (Exception e) {
		}
}
}
	/**
	* 删除或启用礼券礼品抵用明细表
	* @param con
	* @param info
	* @throws SQLException
	*/    
	public static void delete(Connection con,Mbr_gift_money_by_order info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		String sQuery = "update  MBR_GIFT_MONEY_BY_ORDER set status=? where id=? and status<>-1";
		pstmt = con.prepareStatement(sQuery);
		pstmt.setInt(1,info.getStatust() );
		pstmt.setInt(2,info.getID() );
		pstmt.execute();
					
	} catch (SQLException e) {
			e.printStackTrace();
	} finally {
		if (rs != null)
			try {
				rs.close();
			}catch (Exception e){}
		
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (Exception e) {
			}
	}
	}  
	/**
     * 修改礼券礼品抵用明细表
     * @param con
     * @param info
     * @throws SQLException
     */    
    public static void update(Connection con,Mbr_gift_money_by_order info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

		try {
			String sQuery = "update MBR_GIFT_MONEY_BY_ORDER set GIFT_NUMBER=?,LEVEL_ID=?,ORDER_REQUIRE=?,DIS_AMT=?, is_discount = ?, dis_type = ?, begin_date=?, end_date=?,product_group_id=? where id=? ";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1,info.getGift_number() );
			pstmt.setInt(2,info.getLevel_id() );
			pstmt.setDouble(3,info.getOrder_require() );
			pstmt.setDouble(4,info.getDis_amt() );
			pstmt.setString(5, info.getIs_discount());
			pstmt.setInt(6, info.getDis_type());
			pstmt.setDate(7, info.getBegin_date());
			pstmt.setDate(8, info.getEnd_date());
			pstmt.setInt(9, info.getItem_group_id());
			pstmt.setInt(10,info.getID() );
			pstmt.execute();
						
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}     	
	public Mbr_gift_money_by_order findByPrimaryKey(
			Connection con, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Mbr_gift_money_by_order info = new Mbr_gift_money_by_order();

		try {
			String sQuery = "SELECT * from MBR_GIFT_MONEY_BY_ORDER where id="+id;
				
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				info.setID(rs.getInt("id"));
				info.setGift_number(rs.getString("gift_number"));
				info.setLevel_id(rs.getInt("Level_id"));
				info.setStatus(rs.getInt("status"));
				info.setOrder_require(rs.getDouble("Order_require"));
				info.setDis_amt(rs.getDouble("Dis_amt"));
				info.setIs_discount(rs.getString("is_discount"));
				info.setDis_type(rs.getInt("dis_type"));
				info.setBegin_date(rs.getDate("begin_date"));
				info.setEnd_date(rs.getDate("end_date"));

			} else {
				return null;
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
	
	/**
	 * 通过礼券号查找抵用级别
	 * @param con
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Collection findByGiftNumber(
			Connection con, String number) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Collection coll = new ArrayList();

		try {
			String sQuery = "SELECT * from MBR_GIFT_MONEY_BY_ORDER where gift_number = ? and level_id = -1 and status = 1 order by order_require desc, dis_amt desc  ";
				
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, number);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Mbr_gift_money_by_order info = new Mbr_gift_money_by_order();
				info.setID(rs.getInt("id"));
				info.setGift_number(rs.getString("gift_number"));
				info.setLevel_id(rs.getInt("Level_id"));
				info.setStatus(rs.getInt("status"));
				info.setOrder_require(rs.getDouble("Order_require"));
				info.setDis_amt(rs.getDouble("Dis_amt"));
				info.setIs_discount(rs.getString("is_discount"));
				info.setDis_type(rs.getInt("dis_type"));
				info.setBegin_date(rs.getDate("begin_date"));
				info.setEnd_date(rs.getDate("end_date"));
				info.setItem_group_id(rs.getInt("product_group_id"));
				coll.add(info);
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
		return coll;
	}
	
	/**
	 * 通过礼券号和会员等级查找抵用级别
	 * @param con
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Collection findByGiftNumberLevel(
			Connection con, String number, int level) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Collection coll = new ArrayList();

		try {
			String sQuery = "SELECT * from MBR_GIFT_MONEY_BY_ORDER where gift_number = ? and (level_id = -1 or level_id <= ?)  "
				+ "and status = 1 order by order_require desc, dis_amt desc  ";
				
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, number);
			pstmt.setInt(2, level);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Mbr_gift_money_by_order info = new Mbr_gift_money_by_order();
				info.setID(rs.getInt("id"));
				info.setGift_number(rs.getString("gift_number"));
				info.setLevel_id(rs.getInt("Level_id"));
				info.setStatus(rs.getInt("status"));
				info.setOrder_require(rs.getDouble("Order_require"));
				info.setDis_amt(rs.getDouble("Dis_amt"));
				info.setIs_discount(rs.getString("is_discount"));
				info.setDis_type(rs.getInt("dis_type"));
				info.setBegin_date(rs.getDate("begin_date"));
				info.setEnd_date(rs.getDate("end_date"));

				coll.add(info);
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
		return coll;
	}
	/**
     * 有效的抵用级别产品组不能重复
     * @param con
     * @param info
     * @throws SQLException
     */
    public static int checkGiftItemGroupID(Connection con, Mbr_gift_money_by_order info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	
	try {
		String sQuery = "SELECT count(*) from mbr_gift_money_by_order where  status<>-1 ";
		sQuery+=" and gift_number='"+info.getGift_number()+"'  and product_group_id<>"+info.getItem_group_id();
System.out.println(sQuery);
		pstmt = con.prepareStatement(sQuery);
		rs = pstmt.executeQuery();	
	
		if (rs.next()) {
			checkp=rs.getInt(1);
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
	return checkp;
	}  
    /**
     * 同一张礼券的抵用级别，如果是单一订单，以后的设置必须也是单一订单。
     * @param con
     * @param info
     * @throws SQLException
     */
    public static int checkGiftItemDisType(Connection con, Mbr_gift_money_by_order info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	
	try {
		String sQuery = "SELECT count(*) from mbr_gift_money_by_order where  status<>-1 ";
		sQuery+=" and gift_number='"+info.getGift_number()+"' and dis_type<>'"+info.getDis_type()+"'";
		//System.out.println("******* is "+sQuery);
		pstmt = con.prepareStatement(sQuery);
		
		rs = pstmt.executeQuery();	
	
		if (rs.next()) {
			checkp=rs.getInt(1);
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
	return checkp;
	}      
    /**
     * 同一张礼券的抵用级别，折扣是相同的，这张礼券的抵用级别不能一个有折扣，另一个又没有折扣。
     * @param con
     * @param info
     * @throws SQLException
     */
    public static int checkGiftItemDisCount(Connection con, Mbr_gift_money_by_order info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	
	try {
		String sQuery = "SELECT count(*) from mbr_gift_money_by_order where  status<>-1 ";
		sQuery+=" and gift_number='"+info.getGift_number()+"' and is_discount<>'"+info.getIs_discount()+"'";
		//System.out.println("******* is "+sQuery);
		pstmt = con.prepareStatement(sQuery);
		
		rs = pstmt.executeQuery();	
	
		if (rs.next()) {
			checkp=rs.getInt(1);
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
	return checkp;
	}       
	/**
     * 有效的抵用级别金额不能重复
     * @param con
     * @param info
     * @throws SQLException
     */
    public static int checkGiftMoney(Connection con, Mbr_gift_money_by_order info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	
	try {
		String sQuery = "SELECT count(*) from mbr_gift_money_by_order where  status<>-1 and level_id="+info.getLevel_id();
		sQuery+=" and gift_number='"+info.getGift_number()+"' and ORDER_REQUIRE="+info.getOrder_require();
		sQuery+=" and DIS_AMT="+info.getDis_amt();
		pstmt = con.prepareStatement(sQuery);
		rs = pstmt.executeQuery();	
	
		if (rs.next()) {
			checkp=rs.getInt(1);
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
	return checkp;
	}      	
}
