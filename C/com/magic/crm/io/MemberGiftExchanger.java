/*
 * Created on 2005-1-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.magic.crm.util.Function;
;

/**
 * @author zhux
 *
 * this class is for member gift exchange 
 */
public class MemberGiftExchanger {
	private static Logger log = Logger.getLogger(MemberGiftExchanger.class);
	Connection connOra = null;
	Connection connSql = null;
	
	/**
	 * 初始化连接,包括一个oracle connection and a sqlserver connection
	 * @param conn1
	 * @param conn2
	 * @throws Exception
	 */
	public void initConnection(Connection conn1, Connection conn2 ) throws Exception {
		if( conn1 == null || conn1.isClosed()) { 
			throw new Exception(" Oracle Connection is null or closed ");
		}
		if( conn2 == null || conn2.isClosed()) { 
			throw new Exception(" sqlserver Connection is null or closed ");
		}
		connOra = conn1;
		connSql = conn2;
	}
	
	/**
	 * 
	 *
	 */
	public void execute() throws Exception {
		//选择礼品表中对应网站id的记录
		PreparedStatement ps = connOra.prepareStatement("select * from mbr_get_award where web_id = ? ");
		//选择礼品表中的对应会员的积分换礼记录
		PreparedStatement ps1 = connOra.prepareStatement("select a.*,c.item_code from mbr_get_award a, mbr_members b,prd_items c where b.netshop_id = ? and a.member_id = b.id and a.type = 0 and a.item_id = c.item_id ");
		//插入礼品表一条新记录
		PreparedStatement ps11 = connOra.prepareStatement(
				"insert into mbr_get_award (id,member_id,item_id,used_amount_exp,status,web_id,type )"
				+ " values (seq_mbr_get_award_id.nextval,(select id from mbr_members where netshop_id = ?),"
				+ " (select item_id from prd_items where item_code = ? ),?,?,?,0)");
		//更新会员积分
		PreparedStatement ps12 = connOra.prepareStatement("update mbr_members set old_amount_exp = 0 where netshop_id = ? ");
		
		
		//更新网站礼品表的礼品状态
		PreparedStatement ps2 = connSql.prepareStatement("update temp_shelf set changeflag = ? where id = ? ");
		PreparedStatement ps21 = connSql.prepareStatement("update temp_shelf set giftid = ?,point = ?,price=?,changeflag = ? where id = ? ");
		
		Statement st = connSql.createStatement();
		Statement st2 = connOra.createStatement();
		//对网站礼品表中的礼品进行查询处理
		ResultSet rs = st.executeQuery(" select * from temp_shelf where typeflag = 0");
		while (rs.next()) {
			log.info("处理记录" + rs.getInt("id") + "\n");
			int id = rs.getInt("id");
			ps.setInt(1,id);
			//1 如果该记录在网下存在, 则更新网上记录的状态为网下记录的状态
			ResultSet rs2 = ps.executeQuery();
			if( rs2.next()) {
				ps2.setInt(1,Function.sign(rs2.getInt("status")));
				ps2.setInt(2,id);
				ps2.executeUpdate();
			} else { // if the record does not exist
				try {
					//2 查询该会员是否在网下已经有积分换礼品的记录
					ps1.setInt(1,rs.getInt("mbrid"));
					ResultSet rs3 = ps1.executeQuery();
					if( rs3.next()) {
						
						//3 有积分换礼品记录则将网下的记录更新到网上
						ps21.setString(1,rs3.getString("item_code"));
						ps21.setDouble(2,rs3.getDouble("used_amount_exp"));
						ps21.setDouble(3,rs3.getDouble("price"));
						ps21.setInt(4,Function.sign(rs3.getInt("status")));
						ps21.setInt(5,id);
						ps21.executeUpdate();
						
					// 4 如果在网下没有则新增一条记录,同时更新会员的上年度积分为0
					} else {
						ps11.setInt(1,rs.getInt("mbrid"));
						ps11.setString(2,rs.getString("giftid"));
						ps11.setInt(3,rs.getInt("point"));
						ps11.setInt(4,rs.getInt("typeflag"));
						ps11.setInt(5,id);
						ps11.executeUpdate();
						
						//
						ps12.setInt(1,rs.getInt("mbrid"));
						ps12.executeUpdate();
						
					}
					rs3.close();
				} catch(Exception e) {
					log.error("exception",e);
				}
				
			}
			rs2.close();
			
			
		}
		
	}
	
}
