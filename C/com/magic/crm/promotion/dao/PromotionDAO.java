/*
 * @author Administrator(ysm)
 * Created on 2005-10-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.magic.crm.promotion.entity.*;
import com.magic.crm.promotion.form.*;

/**
 * @author Administrator(ysm)
 * Created on 2005-10-10
 */
public class PromotionDAO {

    /**
     * 新增促销主表
     * @param con
     * @param info
     * @throws SQLException
     */
    public static void insert(Connection con,Promotion info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			String sQuery = "INSERT INTO promotion( ID,name,putbasket,creatorid,"
					+ " description,flag,validflag,begindate,enddate,synch )"
					+ " VALUES(SEQ_promotion_ID.nextval,?,?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1,info.getName() );
			pstmt.setInt(2, info.getPutbasket());
			pstmt.setString(3, info.getCreatorID());
			pstmt.setString(4, info.getDescription());
			pstmt.setInt(5, info.getFlag());
			pstmt.setInt(6, info.getValidFlag());
			pstmt.setDate(7, Date.valueOf(info.getBeginDate()));
			pstmt.setDate(8, Date.valueOf(info.getEndDate()));
			pstmt.setInt(9, info.getSynch());
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
     * add by user 2005-12-14
     * 查询促销详情
     * @param con 数据库连接对象
     * @param info 促销bean
     * return 促销bean
     */
    public static Promotion showDetail (Connection con, Promotion info) {
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {

			StringBuffer sql = new StringBuffer();
			sql.append("select "); 
			sql.append("ID, "); 
			sql.append("name, ");
			sql.append("putbasket, ");
			sql.append("creatorid, ");
			sql.append("modifierid, ");
			sql.append("createdate, ");
			sql.append("modifydate, ");
			sql.append("description, ");
			sql.append("flag,");
			sql.append("validflag, ");
			sql.append("to_char(begindate, 'yyyy-mm-dd') as begindate, ");
			sql.append("to_char(enddate, 'yyyy-mm-dd') as enddate, ");
			sql.append("synch ");
			sql.append("from promotion ");
			sql.append("where id = ? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, info.getId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setId(rs.getInt("ID"));
				info.setName(rs.getString("name"));
				info.setPutbasket(rs.getInt("putbasket"));
				info.setCreatorID(rs.getString("creatorid"));
				info.setModifierID(rs.getString("modifierid"));
				info.setCreateDate(rs.getString("createdate"));
				info.setModifyDate(rs.getString("modifydate"));
				info.setDescription(rs.getString("description"));
				info.setFlag(rs.getInt("flag"));
				info.setValidFlag(rs.getInt("validflag"));
				info.setBeginDate(rs.getString("begindate"));
				info.setEndDate(rs.getString("enddate"));
				info.setSynch(rs.getInt("synch"));
				
			} else {
				return null;
			}
					
			
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
		return info;
    }
    
    /**
     * add by user 2005-12-14
     * 修改促销
     * @param con 数据库连接对象
     * @param info 促销bean
     * return
     */
    public static boolean modify (Connection con, Promotion info) {
    	PreparedStatement pstmt = null;
		
	
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("update promotion set ");
			sql.append("name = ?, ");
			sql.append("putbasket = ?, ");
			//sql.append("creatorid = ?, ");
			sql.append("modifierid = ?, ");
			//sql.append("createdate = ?, ");
			sql.append("modifydate = sysdate, ");
			sql.append("description = ?, ");
			sql.append("flag = ?, ");
			//sql.append("validflag = ?, ");
			sql.append("begindate = ?, ");
			sql.append("enddate = ? ");
			//sql.append("synch = ? ");
			sql.append("where id = ? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1,info.getName() );
			pstmt.setInt(2, info.getPutbasket());
			pstmt.setString(3, info.getModifierID());
			
			pstmt.setString(4, info.getDescription());
			pstmt.setInt(5, info.getFlag());
			pstmt.setDate(6, Date.valueOf(info.getBeginDate()));
			pstmt.setDate(7, Date.valueOf(info.getEndDate()));
			pstmt.setInt(8, info.getId());
			pstmt.execute();
						
		} catch (SQLException e) {
				e.printStackTrace();
				return false;
		} finally {
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return true;
    }
    /**
     * 新增促销产品表
     * @param con
     * @param info
     * @throws SQLException
     */
    public static void insertProm_Item(Connection con,Prom_Item info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
		    con.setAutoCommit(false);
			String sQuery = "INSERT INTO prom_item(ID,promotionid,itemcode,creatorid )VALUES(SEQ_PROM_ITEM_ID.nextval, ?, ?, ?)";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1,info.getPromotionID() );
			//pstmt.setString(2, info.getItemID());
			pstmt.setString(2, info.getItemcode());
			pstmt.setString(3, info.getCreatorID());

			pstmt.execute();
			
			String sql = "update  promotion set synch=?,modifydate=sysdate  where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, info.getPromotionID());
			pstmt.execute();
			con.commit();
		} catch (SQLException e) {
		    con.rollback();
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
     * 新增多少元选几件表
     * @param con
     * @param info
     * @throws SQLException
     */
    public static void insertProm_Money4qty(Connection con,Prom_money4qty info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
		    con.setAutoCommit(false);
			String sQuery = "INSERT INTO prom_money4qty(ID,promotionid,money,qty,creatorid ) VALUES(SEQ_PROM_ITEM_ID.nextval, ?,?, ?, ?)";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1,info.getPromotionID() );
			//pstmt.setString(2, info.getItemID());
			pstmt.setDouble(2, info.getMoney());
			pstmt.setInt(3, info.getQty());
			pstmt.setString(4, info.getCreatorID());

			pstmt.execute();
			
			String sql = "update  promotion set synch=?,modifydate=sysdate  where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, info.getPromotionID());
			pstmt.execute();
			con.commit();
		} catch (SQLException e) {
		    con.rollback();
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
     * 新增促销礼品表
     * @param con
     * @param info
     * @throws SQLException
     */
    public static void insertProm_Gift(Connection con,Prom_gift info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
		    con.setAutoCommit(false);
		    
		    //String c_sql="select * from promotion where begindate<=to_date('"+info.getBeginDate()+"','yyyy-mm-dd') and " +
		    //		" enddate >=to_date('"+info.getEndDate()+"','yyyy-mm-dd') and id="+info.getPromotionID();
			//pstmt = con.prepareStatement(c_sql);
			//rs = pstmt.executeQuery();	
			//if (rs.next()) {
						
			String sQuery = "INSERT INTO prom_gift( ID,promotionid,itemcode,creatorid,overx,addy," 
					+ " description)"
					+ " VALUES(SEQ_PROM_gift_ID.nextval,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1,info.getPromotionID() );
			//pstmt.setString(2, info.getItemID());
			pstmt.setString(2, info.getItemcode());
			pstmt.setString(3, info.getCreatorID());
			pstmt.setDouble(4, info.getOverx());
			pstmt.setDouble(5, info.getAddy());
			//pstmt.setDate(7, Date.valueOf(info.getBeginDate()));
			//pstmt.setDate(8, Date.valueOf(info.getEndDate()));
			//pstmt.setInt(9, info.getScope());
			pstmt.setString(6, info.getDescription());
			//pstmt.setString(11, info.getProm_url());
			pstmt.execute();
			
			String sql = "update  promotion set synch=?,modifydate=sysdate  where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, info.getPromotionID());
			pstmt.execute();
			
			
			con.commit();
		} catch (SQLException e) {
		    con.rollback();
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
     * add by user 2005-12-14
     * 显示促销礼品详情
     * @param con
     * @param info
     * @return
     */
    public static Prom_gift showPromGiftDetail(Connection con,Prom_gift info) throws SQLException {
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
		    
			StringBuffer sql = new StringBuffer();
			sql.append("select "); 
			sql.append("a.ID, "); 
			sql.append("a.PROMOTIONID, ");
			//sql.append("a.ITEMID, ");
			sql.append("a.ITEMCODE, ");
			sql.append("a.OVERX, ");
			sql.append("a.ADDY, ");
			//sql.append("to_char(a.BEGINDATE, 'yyyy-mm-dd') as BEGINDATE, ");
			//sql.append("to_char(a.ENDDATE, 'yyyy-mm-dd') as ENDDATE, ");
			sql.append("a.VALIDFLAG,");
			//sql.append("a.SCOPE, ");
			sql.append("a.CREATORID, ");
			sql.append("a.MODIFIERID, ");
			sql.append("a.CREATEDATE, ");
			sql.append("a.MODIFYDATE, ");
			sql.append("a.DESCRIPTION, ");
			//sql.append("a.PROM_URL, ");
			sql.append("b.NAME ");
			sql.append("from prom_gift a ");
			sql.append("inner join promotion b ");
			sql.append("on a.promotionid = b.id ");
			sql.append("where a.ID = ? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, info.getID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setID(rs.getInt("ID"));
				info.setPromotionID(rs.getInt("PROMOTIONID"));
				//info.setItemID(String.valueOf(rs.getInt("ITEMID")));
				info.setItemcode(rs.getString("ITEMCODE"));
				info.setOverx(rs.getInt("OVERX"));
				info.setAddy(rs.getInt("ADDY"));
				//info.setBeginDate(rs.getString("BEGINDATE"));
				//info.setEndDate(rs.getString("ENDDATE"));
				info.setValidflag(rs.getInt("VALIDFLAG"));
				//info.setScope(rs.getInt("SCOPE"));
				info.setCreatorID(String.valueOf(rs.getInt("CREATORID")));
				info.setModifierID(String.valueOf(rs.getInt("MODIFIERID")));
				info.setCreateDate(rs.getString("CREATEDATE"));
				info.setModifyDate(rs.getString("MODIFYDATE"));
				info.setDescription(rs.getString("DESCRIPTION"));
				//info.setProm_url(rs.getString("PROM_URL"));
				info.setPromotionName(rs.getString("NAME"));
			} else {
				return null;
			}

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
    	return info;
    }
    
    /**
     * add by user 2005-12-14
     * 修改促销礼品
     * @param con
     * @param info
     * @throws SQLException
     */
    public static boolean modifyPromGift(Connection con, Prom_gift info) throws SQLException {
    	
    	PreparedStatement pstmt = null;
	
		try {
		    
			StringBuffer sql = new StringBuffer();
			sql.append("update prom_gift set "); 
			//sql.append("ITEMID = ?, ");
			sql.append("ITEMCODE = ?, ");
			sql.append("OVERX = ?, ");
			sql.append("ADDY = ?, ");
			//sql.append("BEGINDATE = ?, ");
			//sql.append("ENDDATE = ?, ");
			//sql.append("VALIDFLAG = ?,");
			//sql.append("SCOPE = ?, ");
			//sql.append("CREATORID, ");
			sql.append("MODIFIERID = ?, ");
			//sql.append("CREATEDATE, ");
			sql.append("MODIFYDATE = sysdate, ");
			sql.append("DESCRIPTION = ? ");
			//sql.append("PROM_URL = ? ");
			sql.append("where ID = ? ");
			pstmt = con.prepareStatement(sql.toString());
			//pstmt.setInt(1, Integer.parseInt(info.getItemID()));
			pstmt.setString(1, info.getItemcode());
			pstmt.setDouble(2, info.getOverx());
			pstmt.setDouble(3, info.getAddy());
			//pstmt.setDate(5, Date.valueOf(info.getBeginDate()));
			//pstmt.setDate(6, Date.valueOf(info.getEndDate()));
			//pstmt.setInt(7, info.getScope());
			pstmt.setInt(4, Integer.parseInt(info.getModifierID()));
			//pstmt.setDate(9, Date.valueOf(info.getModifyDate()));
			pstmt.setString(5, info.getDescription());
			//pstmt.setString(10, info.getProm_url());
			pstmt.setInt(6, info.getID());
			pstmt.execute();
			
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
		return true;
    }
    /**
	 * 查询促销产品表
	 * @param con
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList queryPromo_Item(Connection con,int pid)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList prom_itemCol = new ArrayList();
	
	try {
		String sQuery = " SELECT t2.itm_name,t3.name,t1.* from prom_item t1 ,prd_item t2,promotion t3" +
				        " where t1.itemcode=t2.itm_code and t1.promotionid=t3.id and t1.promotionid<1000000" +
				        " and t1.promotionid=? order by t1.flag desc,t1.id  desc";
	
	    //System.out.println(sQuery);
		pstmt = con.prepareStatement(sQuery);
		pstmt.setInt(1, pid);
		rs = pstmt.executeQuery();	
		while (rs.next()) {
			Prom_Item info = new Prom_Item();
			info.setID(Integer.parseInt(rs.getString("id")));
			info.setPromotionName(rs.getString("name"));
			info.setPromotionID(pid);
			info.setItemcode(rs.getString("itemcode"));
			info.setItem_name(rs.getString("itm_name"));
		    info.setCreatorID(rs.getString("creatorid"));
		    info.setModifierID(rs.getString("modifierid"));
		    info.setCreateDate(rs.getString("createdate"));
		    info.setModifyDate(rs.getString("modifydate"));    
		    info.setFlag(rs.getInt("flag"));
		    prom_itemCol.add(info);
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
	return prom_itemCol;
	}

    /**
	 * 查询促销满多少元任选几件表
	 * @param con
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList queryPromo_Money4Qty(Connection con,int pid)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList prom_itemCol = new ArrayList();
	
	try {
		String sQuery = " SELECT t3.name,t1.* from prom_money4qty t1,promotion t3" +
				        " where t1.promotionid=t3.id " +
				        " and t1.promotionid=? order by t1.flag desc,t1.id  desc";
	
	    //System.out.println(sQuery);
		pstmt = con.prepareStatement(sQuery);
		pstmt.setInt(1, pid);
		rs = pstmt.executeQuery();	
		while (rs.next()) {
			Prom_money4qty info = new Prom_money4qty();
			info.setID(Integer.parseInt(rs.getString("id")));
			info.setPromotionName(rs.getString("name"));
			info.setPromotionID(pid);
			info.setMoney(rs.getDouble("money"));
			info.setQty(rs.getInt("qty"));
		    info.setCreatorID(rs.getString("creatorid"));
		    info.setModifierID(rs.getString("modifierid"));
		    info.setCreateDate(rs.getString("createdate"));
		    info.setModifyDate(rs.getString("modifydate"));    
		    info.setFlag(rs.getInt("flag"));
		    prom_itemCol.add(info);
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
	return prom_itemCol;
	}
	/**
     * 查询促销主表
     * @param con
     * @param condition
     * @return
     * @throws SQLException
     */
    public Collection queryPromotion(Connection con, String condition)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Collection promotionCol = new ArrayList();
	
	try {
		String sQuery = "SELECT * from promotion  "
				+ condition;
		sQuery+=" order by validflag desc,id  desc ";
		//System.out.println(sQuery);
		pstmt = con.prepareStatement(sQuery);
		rs = pstmt.executeQuery();	
		while (rs.next()) {
			Promotion info = new Promotion();
			info.setId(Integer.parseInt(rs.getString("id")));
		    info.setName(rs.getString("name"));
		    info.setPutbasket(Integer.parseInt(rs.getString("putbasket")));
		    info.setCreatorID(rs.getString("creatorid"));
		    info.setModifierID(rs.getString("modifierid"));
		    info.setCreateDate(rs.getString("createdate"));
		    info.setModifyDate(rs.getString("modifydate"));
		    info.setDescription(rs.getString("description"));
		    info.setFlag(Integer.parseInt(rs.getString("flag")));
		    info.setValidFlag(Integer.parseInt(rs.getString("validflag")));
		    info.setBeginDate(rs.getString("begindate").substring(0,10));
		    info.setEndDate(rs.getString("enddate").substring(0,10));
		    info.setSynch(Integer.parseInt(rs.getString("synch")));	        
			promotionCol.add(info);
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
	return promotionCol;
	}  
    /**
     * 查询促销礼品表
     * @param con
     * @param condition
     * @return
     * @throws SQLException
     */
    public Collection queryPromo_Gift(Connection con, int pid)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Collection prom_giftCol = new ArrayList();
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
	java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
	String str_date1 = formatter.format(currentTime); 
	try {
		String sQuery = " SELECT t2.itm_name,t1.* " +
				" from prom_gift t1 join prd_item t2 on t1.itemcode=t2.itm_code " +
        				" where t1.PROMOTIONID<1000000" +
        				" and t1.promotionid= ?" +
        				"  order by t1.validflag desc,t1.overx ";

		pstmt = con.prepareStatement(sQuery);
		pstmt.setInt(1, pid);
		rs = pstmt.executeQuery();	
		while (rs.next()) {
			Prom_gift info = new Prom_gift();
			info.setID(Integer.parseInt(rs.getString("id")));
			//info.setPromotionName(rs.getString(2));
			info.setPromotionID(pid);
			//info.setItemID(rs.getString(1));
			info.setItemcode(rs.getString("itemcode"));
			info.setItem_name(rs.getString("itm_name"));
			
		    info.setCreatorID(rs.getString("creatorid"));
		    info.setModifierID(rs.getString("modifierid"));
		    info.setCreateDate(rs.getString("createdate"));
		    info.setModifyDate(rs.getString("modifydate")); 
		    info.setOverx(rs.getDouble("overx"));
		    info.setAddy(rs.getDouble("addy"));
		    //info.setBeginDate(rs.getString("begindate").substring(0,10));
		    //info.setEndDate(rs.getString("enddate").substring(0,10));
		    //if(Date.valueOf(info.getEndDate()).before(Date.valueOf(str_date1))){		    	
		    //	info.setValidflag(0);
		    //}else{
		    info.setValidflag(rs.getInt("validflag"));
		    //}
		    //info.setScope(rs.getInt("scope"));
		    //info.setDescription(rs.getString("description"));
		    //info.setProm_url(rs.getString("prom_url"));
		    prom_giftCol.add(info);
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
	return prom_giftCol;
	}  
    /**
     * 更改促销状态
     * @param conn
     * @param info
     * @throws SQLException
     */
    public static void updateValidFlag(Connection conn,Promotion info)
    throws SQLException{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		String sQuery = "update  promotion set validflag=?,modifierid=?,synch=?,modifydate=sysdate  where id=?";

		pstmt = conn.prepareStatement(sQuery);
		pstmt.setInt(1, info.getValidFlag());
		pstmt.setString(2, info.getModifierID());
		pstmt.setInt(3, 1);
		pstmt.setInt(4, info.getId());

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
     * 更改促销产品状态
     * @param conn
     * @param info
     * @throws SQLException
     */
    public static void updateItemValidFlag(Connection conn,Prom_Item info)
    throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sQuery = "update  prom_item set flag=?,modifierid=?,modifydate=sysdate  where id=?";
	
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, info.getFlag());
			pstmt.setString(2, info.getModifierID());
			pstmt.setInt(3, info.getID());
			pstmt.execute();
			
			String sql = "update  promotion set synch=?,modifydate=sysdate  where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, info.getPromotionID());
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
     * 更改多少元任选几件规则
     * @param conn
     * @param info
     * @throws SQLException
     */
    public static void updateMoney4QtyValidFlag(Connection conn,Prom_money4qty info)
    throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sQuery = "update  prom_money4qty set flag=?,modifierid=?,modifydate=sysdate  where id=?";
	
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, info.getFlag());
			pstmt.setString(2, info.getModifierID());
			pstmt.setInt(3, info.getID());
			pstmt.execute();
			
			String sql = "update  promotion set synch=?,modifydate=sysdate  where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, info.getPromotionID());
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
     * 更改促销礼品状态
     * @param conn
     * @param info
     * @throws SQLException
     */
    public static void updateGiftValidFlag(Connection conn,Prom_Item info)
    throws SQLException{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		String sQuery = "update  prom_gift set validflag=?,modifierid=?,modifydate=sysdate  where id=?";

		pstmt = conn.prepareStatement(sQuery);
		pstmt.setInt(1, info.getFlag());
		pstmt.setString(2, info.getModifierID());
		pstmt.setInt(3, info.getID());
		pstmt.execute();
		
		String sql = "update  promotion set synch=?,modifydate=sysdate  where id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, 1);
		pstmt.setInt(2, info.getPromotionID());
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
     * 判断有效的促销名称是否重复
     * @author Administrator(ysm)
     * Created on 2005-10-10
     */
    public static int checkPromotion(Connection con, String name)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	
	try {
		String sQuery = "SELECT * from promotion where  name='"+name+"'";
		pstmt = con.prepareStatement(sQuery);
		rs = pstmt.executeQuery();	
		if (rs.next()) {
			checkp=1;
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
     * 判断促销产品是否重复
     * @author Administrator(ysm)
     * Created on 2005-10-10
     */
    public static int checkProm_Item(Connection con, Prom_Item info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	
	try {
		String sQuery = "SELECT * from prom_item where promotionid="+info.getPromotionID()+" and itemcode='"+info.getItemcode()+"'";
		pstmt = con.prepareStatement(sQuery);
		rs = pstmt.executeQuery();	
		if (rs.next()) {
			checkp=1;
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
     * 判断促销礼品是否重复
     * 一种礼品只能在一个促销里促销
     * @author Administrator(ysm)
     * Created on 2005-10-10
     */
    public static int checkProm_Gift(Connection con, Prom_gift info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	String sQuery = null;
	try {
		//String sQuery = "SELECT * from prom_gift where promotionid="+info.getPromotionID()+" and itemcode='"+info.getItemcode()+"'";
	    if (info.getID() == 0) {//新增
	    	sQuery = "SELECT * from prom_gift where   itemcode='"+info.getItemcode()+"' and scope in (2,4,6,7) and validflag = 1";
	    } else { //修改
	    	sQuery = "SELECT * from prom_gift where   itemcode='"+info.getItemcode()+"' and validflag = 1 and id <> " + info.getID() + " and promotionid < 1000000 ";
	    }
		
	    pstmt = con.prepareStatement(sQuery);
		rs = pstmt.executeQuery();	
		if (rs.next()) {
			checkp=1;
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
	 * add by user 2007-02-28
	 * @param con
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static Prom_gift findPromGiftByPK(Connection con, int id)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		String sQuery = "SELECT * from prom_gift where id = ?";
		pstmt = con.prepareStatement(sQuery);
		pstmt.setInt(1, id);
		rs = pstmt.executeQuery();	
		if (rs.next()) {
			Prom_gift data = new Prom_gift();
			data.setID(rs.getInt("id"));
			data.setPromotionID(rs.getInt("promotionid"));
			//data.setItemID(rs.getString("itemid"));
			data.setItemcode(rs.getString("itemcode"));
			data.setOverx(rs.getDouble("overx"));
			data.setAddy(rs.getDouble("addy"));
			data.setBeginDate(rs.getString("begindate"));
			data.setEndDate(rs.getString("enddate"));
			//省略了一些值
			return data;
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
	return null;
	}

	/**
     * 判断组促销时，是否先添加组产品后再添加组礼品
     * @author Administrator(ysm)
     * Created on 2005-10-27
     */
    public static int checkItem_Gift(Connection con, int pid)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int flag=0;
	
	try {
		String sQuery = "SELECT flag from promotion where id="+pid;
		pstmt = con.prepareStatement(sQuery);
		rs = pstmt.executeQuery();	
		if (rs.next()) {
		    flag=rs.getInt("flag");
		}	
		if(flag==3){
			String sql = "SELECT promotion.flag from prom_item,promotion  where " +
					" promotion.id=prom_item.promotionid and prom_item.FLAG=1 and  promotionid="+pid;
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();	
				if (rs.next()) {
				    flag=0;
				}else{
				    flag=3;
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
	return flag;
	}     
    
    public static int showCategory (Connection conn, PromotionForm fm) throws Exception {
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			String sql = "select t1.id,t1.name,nvl(t1.group_id,0) group_id " +
					"from promotion t1 " +
					"where t1.id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fm.getId());
			rs = pstmt.executeQuery();
			if(rs.next()){
				//fm.setCatalog_code(rs.getString("catalog_code"));
				//fm.setGroup_id(rs.getInt("group_id"));
				fm.setItem_category(rs.getInt("group_id"));
				fm.setId(rs.getInt("id"));
				fm.setName(rs.getString("name"));
			}
			
		} catch (SQLException e) {
				e.printStackTrace();
				throw e;
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
		return 0;
    }
    
    public static int saveCategory (Connection conn, PromotionForm fm) throws Exception {
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			String sql = "update promotion set group_id=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fm.getItem_category());
			pstmt.setInt(2, fm.getId());
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
		return 0;
    }
}
