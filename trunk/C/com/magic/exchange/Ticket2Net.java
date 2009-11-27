/*
 * Created on 2005-4-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.exchange;

import java.sql.*;

import org.apache.log4j.Logger;

import com.magic.crm.util.*;

/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class Ticket2Net {
	private static Logger log = Logger.getLogger(Ticket2Net.class);
	private int suc_count = 0;
	private int fail_count = 0;
	
	public void execute() throws Exception {
		Connection connMs = null;
		Connection connOra = null;
		try {
			connMs = DBManagerMS.getConnection();
		} catch(Exception e) {
			log.error("",e);
			throw e;
		}
		try {
			connOra = DBManager2.getConnection();
		} catch(Exception e) {
			log.error("",e);
			throw e;
		}
		
		try {
			// 1. 更新礼券使用情况,包括普通礼券和乐透卡礼券
			PreparedStatement ps1 = connMs.prepareStatement(" update new_memb_use set num = ? where mbrid=? and ticketid=? ");
			PreparedStatement ps2 = connMs.prepareStatement(" insert into new_memb_use (num,mbrid,ticketid) values(?,?,?)");
			String sql=" select b.netshop_id,a.ticket_num,a.num from mbr_gift_ticket_use a,mbr_members b where a.mod_date>sysdate-3.1 and a.mbrid = b.id and b.netshop_id >0";
			Statement st = connOra.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				try {
					long mbid_web = rs.getLong("netshop_id");
					String ticket_num = rs.getString("ticket_num");
					int num = rs.getInt("num");
					ps1.setInt(1,num);
					ps1.setLong(2,mbid_web);
					ps1.setString(3,ticket_num);
					int ret = ps1.executeUpdate();
					if( ret < 1) {
						ps2.setInt(1,num);
						ps2.setLong(2,mbid_web);
						ps2.setString(3,ticket_num);
						ret = ps2.executeUpdate();
						if ( ret < 1) {
							fail_count++;
						} else {
							suc_count++;
						}
					} else {
						suc_count++;
					}
				} catch(Exception e) {
					fail_count++;
					log.error("礼券使用情况更新出现错误",e);
				}
				log.info("礼券使用情况" + "\t共成功更新" + suc_count + ",失败" + fail_count);
				
			}
			rs.close();
			
			
			// 2. 更新乐透卡礼券表
			ps1.close();
			ps2.close();
			suc_count =0;
			fail_count =0;
			ps1 = connMs.prepareStatement("update ggcard_mst set is_used=?,month_hit=?,year_hit=?,used_date=? where card_num=?");
			rs = st.executeQuery("select card_num,is_used,month_hit,year_hit,to_char(used_date,'yyyy-mm-dd') as used_date from ggcard_mst where mod_date >=sysdate-1.1");
			TicketInfo ticket = new TicketInfo();
			while(rs.next()) {
				try {
					ticket.setCard_num(rs.getString("card_num"));
					//ticket.setPass(rs.getString("pass"));
					//ticket.setCard_type(rs.getString("card_type");
					ticket.setIs_used(rs.getString("is_used"));
					ticket.setMonth_hit(rs.getString("month_hit"));
					ticket.setYear_hit(rs.getString("year_hit"));
					ticket.setUsed_date(rs.getString("used_date"));
					
					ps1.setString(1,ticket.getIs_used());
					ps1.setString(2,ticket.getMonth_hit());
					ps1.setString(3,ticket.getYear_hit());
					ps1.setString(4,ticket.getUsed_date());
					ps1.setString(5,ticket.getCard_num());
					int ret = ps1.executeUpdate();
					if (ret <1) {
						fail_count++;
						log.error("该乐透卡在网站系统不存在" + ticket.getCard_num());
					} else {
						suc_count++;
					}
					
				} catch(Exception e) {
					fail_count++;
					log.error("乐透卡信息更新出现错误",e);
				}
				log.info("乐透卡更新记录 卡号" + ticket.getCard_num() +  "\t共成功" + suc_count + ",失败" + fail_count);
			
			}
			rs.close();
			ps1.close();
			st.close();
		
		} finally {
			try {connMs.close();} catch(Exception e) {}
			try {connOra.close();} catch(Exception e) {}
		}
	}
	
	public static void main(String[] args) {
		try {
			new Ticket2Net().execute();
		} catch(Exception e) {
			log.error(e);
		}
	}
}