/*
 * Created on 2005-4-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.exchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.magic.crm.util.DBManager2;
import com.magic.crm.util.DBManagerMS;

/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class Gift2Net {
	private static Logger log = Logger.getLogger(Gift2Net.class);
	int suc_count =0, fail_count=0;
	public void execute() {
		Connection conOra = null;
		Connection conMs = null;
		try {
			conMs = DBManagerMS.getConnection();
			PreparedStatement ps1 = conMs.prepareStatement("update temp_shelf set changeflag=? where id=? ");
			PreparedStatement ps2 = conMs.prepareStatement("insert into temp_shelf(changeflag,mbrid,giftid,point,price,num,typeflag) values(?,?,?,?,?,?,?)");
			PreparedStatement ps3 = conMs.prepareStatement("select max(id) from temp_shelf where changeflag =? and mbrid=? and giftid=? and point=? and price=? and num=? and typeflag=?" );
			
			conOra = DBManager2.getConnection();
			PreparedStatement ps4 = conOra.prepareStatement("update mbr_get_award set web_id=? where id=?");
			Statement st = conOra.createStatement();
			//1 得到所有需要更新到网上的礼品信息.
			String sql = " select b.netshop_id,c.item_code,a.used_amount_exp,a.price,a.type,a.quantity,a.status,a.id,nvl(web_id,0) as web_id "
				+ " from mbr_get_award a, mbr_members b,prd_items c "
				+ " where a.mod_date > sysdate -2.1 and a.member_id=b.id and b.netshop_id >0 and a.item_id = c.item_id";
			ResultSet rs = st.executeQuery(sql);
			GiftInfo gift = new GiftInfo();
			while (rs.next()) {
				try {
					gift.setMember_web_id(rs.getInt("netshop_id"));
					gift.setItem_code(rs.getString("item_code"));
					gift.setExp(rs.getInt("used_amount_exp"));
					gift.setPrice(rs.getDouble("price"));
					gift.setType(rs.getInt("type"));
					gift.setQuantity(rs.getInt("quantity"));
					gift.setStatus(rs.getInt("status"));
					gift.setCrm_id(rs.getInt("id"));
					gift.setWeb_id(rs.getInt("web_id"));
					int ret =0;
					if( gift.getWeb_id()>0) {
						ps1.setInt(1,gift.getStatus());
						ps1.setInt(2,gift.getWeb_id());
						ret = ps1.executeUpdate();
					} else {
						ps2.setInt(1,gift.getStatus());
						ps2.setInt(2,gift.getMember_web_id());
						ps2.setString(3,gift.getItem_code());
						ps2.setInt(4,gift.getExp());
						ps2.setDouble(5,gift.getPrice());
						ps2.setInt(6,gift.getQuantity());
						ps2.setInt(7,gift.getType());
						ret = ps2.executeUpdate();
						if (ret >0 ) {
							ps3.setInt(1,gift.getStatus());
							ps3.setInt(2,gift.getMember_web_id());
							ps3.setString(3,gift.getItem_code());
							ps3.setInt(4,gift.getExp());
							ps3.setDouble(5,gift.getPrice());
							ps3.setInt(6,gift.getQuantity());
							ps3.setInt(7,gift.getType());
							ResultSet rs1 = ps3.executeQuery();
							int web_id = 0;
							if(rs1.next()) {
								web_id = rs1.getInt(1);
							}
							rs1.close();
							ps4.setInt(1,web_id);
							ps4.setInt(2,gift.getCrm_id());
							ps4.executeUpdate();
						}
					}
				
					if (ret <1) {
						fail_count++;
					} else {
						suc_count++;
					}
				
				} catch(Exception e) {
					fail_count++;
					log.error("--更新礼品到网站出现错误--",e);
				}
				log.info("更新礼品到网站当前记录 网站会员id,货号" + gift.getMember_web_id() + "," + gift.getItem_code() + "\t成功" + suc_count + " 失败 " + fail_count);
			}
			rs.close();
			st.close();
			ps1.close();
			ps2.close();
			ps3.close();
			ps4.close();
			
		} catch(Exception e) {
			log.error("--礼品更新到Net出错--",e);
		} finally {
			try { conOra.close(); } catch(Exception e) {}
			try { conMs.close(); } catch(Exception e) {}
		}
		
		
	}
	
	public static void main(String[] args) {
		try {
			new Gift2Net().execute();
		} catch(Exception e) {
			log.error(e);
		}
	}
}
