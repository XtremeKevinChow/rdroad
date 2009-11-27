package com.magic.exchange;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Iterator;
import com.magic.crm.util.*;

import org.apache.log4j.*;

/**
 * 网站会员信息更新到CRM系统
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class Member2CRM {
	private static Logger log = Logger.getLogger(Member2CRM.class);

	/**
	 * 从网站中取得会员基本信息的方法 并调用isOpinion()方法去CRM系统中查询,看网站中的会员号,在CRM中是否存在
	 * 如果存在,则更新其记录,否则,插入新记录
	 */
	public void execute() {
		//从网站中获取所有会员信息的SQL语句
		Connection conOra = null;
		Connection conMs = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conOra = DBManager2.getConnection();
			conMs = DBManagerMS.getConnection();
		} catch (Exception e) {
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = conMs.prepareStatement("update b2cmbr set b2cmbr_modi= 0 where b2cmbr_id = ?");
			
			String strSql = "select count(1)"
					+ "from b2cmbr where b2cmbr_modi=1 and b2cmbr_ok=1  ";
			st = conMs.createStatement();
			rs = st.executeQuery(strSql);
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			//st.close();
			int failed_count = 0;
			int success_count = 0;
			log.info("网站会员更新到CRM开始，需要更新记录条数:" + count);

			strSql = "select b2cmbr_id,b2cmbr_cardid,b2cmbr_name,b2cmbr_sex,b2cmbr_birthday,b2cmbr_ophone,"
					+ "b2cmbr_hphone,b2cmbr_mphone,b2cmbr_uid,b2cmbr_certifiertype,b2cmbr_certifierno,"
					+ "b2cmbr_postcode,b2cmbr_haddr,b2cmbr_catalogtype,b2cmbr_cardtype, "
					+ "isnull(b2cmbr_GIFTID,0) as b2cmbr_giftid,isnull(b2cmbr_RECOMMENDER,0) as b2cmbr_recommender "+
					"from b2cmbr where b2cmbr_modi=1 and b2cmbr_ok=1 order by b2cmbr_id";
			rs = st.executeQuery(strSql);

			while (rs.next()) {
				
				MemberInfo member = new MemberInfo();
				try {
				//取得网站中的会员ID号,用于取网站中会员的地址表中的信息
				member.setTemp_b2cmbr_uid(Member2CRM.getAndTrim(rs.getString("b2cmbr_id")));
				//取得网站中会员与CRM系统中部分对应信息
				member.setCard_id(Member2CRM.getAndTrim(rs.getString("b2cmbr_cardid")));
				member.setName(Member2CRM.getAndTrim(rs.getString("b2cmbr_name")));
				//处理性别问题,网站中性别用INT表示,CRM中用CHAR表示
				String gender =null;
				if (rs.getInt("b2cmbr_sex") == 0) {
					gender = "M";
				} else {
					gender = "F";
				}
				member.setGender(gender);
				member.setBirthday(rs.getDate("b2cmbr_birthday"));
				member.setCompany_phone(Member2CRM.getAndTrim(rs
						.getString("b2cmbr_ophone")));
				member.setFamily_phone(Member2CRM.getAndTrim(rs
						.getString("b2cmbr_mphone")));
				member.setTelephone(Member2CRM.getAndTrim(rs
						.getString("b2cmbr_hphone")));
				//b2cmbr_uid 是网站中的用户电子邮件
				member.setEmail(Member2CRM.getAndTrim(rs
						.getString("b2cmbr_uid")));
				member.setCertificate_type(rs.getInt("b2cmbr_certifiertype"));
				member.setCertificate_code(Member2CRM.getAndTrim(rs
						.getString("b2cmbr_certifierno")));

				//会员新增时插入地址表中的内容
				member.setV_usephone("null");//rs.getString("b2cmbr_usephone"));
				member.setV_postcode(Member2CRM.getAndTrim(rs
						.getString("b2cmbr_postcode")));
				member.setV_address(Member2CRM.getAndTrim(rs
						.getString("b2cmbr_haddr")));

				//新增的字段
				member.setJoin_other(0);//rs.getInt("b2cmbr_joinother"));
				//新增 是否增收电子目录
				member.setCatalog_type(rs.getInt("b2cmbr_catalogtype"));
				//新增 卡类型 取值0，1 0为普通，1为联名
				member.setCard_type(rs.getInt("b2cmbr_cardtype"));
				
				//得到推荐人礼品号和推荐人网站id, 并将其转化为网下礼品id和推荐人id
				member.setGiftCode(rs.getString("b2cmbr_giftid"));
				member.setRecommenderNetshopId(rs.getInt("b2cmbr_recommender"));
				convertInfo(conOra,member);
				
				int ret = updateMember(conOra,member);
				if (ret ==0 ) {
					int mbr_id = insertMember(conOra,member);
					if (mbr_id <0) {
						throw new Exception("错误号：" + mbr_id);
					}
				} 
				
				//更新会员信息为已经更新过
				ps.setInt(1, Integer.parseInt(member.getTemp_b2cmbr_uid()
						.trim()));
				ps.executeUpdate();

				success_count++;
				log.info("网站会员更新到CRM,会员号" + member.getCard_id()
								+ "更新成功\t 成功:" + success_count + "\t失败:"
								+ failed_count);
				
				 } catch (Exception e) { 
				 	failed_count++;
				 	log.info("会员号" + member.getCard_id() + "更新失败\t成功:" + success_count + "\t失败:"+ failed_count,e);
				 }
			}
			rs.close();
			st.close();

		} catch (Exception e) {
			log.error("网站会员更新到CRM出现异常:",e);
		} finally {
			try {
				ps.close();
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

	/**
	 * 方法名：getAndTrim()
	 * 
	 * @param String
	 * @return String 此方法用于判断一个从数据库中取出的字符串值是否为空， 如果不为空，则除去其后的空格，为空则不处理
	 */
	private static final String getAndTrim(String str) {
		String returnStr = "";
		if (str == null || str.equals("")) {
			returnStr = "";
		} else {
			returnStr = str.trim();
		}
		return returnStr;
	}

	/**
	 * mothed updateMember()
	 * 
	 * @param p_opin
	 * @return
	 */
	private int updateMember(Connection conn,MemberInfo info) throws Exception {
		
		Statement st = conn.createStatement();
		String sql = "update mbr_members set name = '" + info.getName() 
		+ "',birthday = to_date('" + info.getBirthday() + "','yyyy-mm-dd')"
		+ ",gender = '" + info.getGender() + "'" + ",certificate_type =" + info.getCertificate_type()
		+ ",certificate_code ='" + info.getCertificate_code() + "',catalog_type=" + info.getCatalog_type()
		+ ",email ='" + info.getEmail() + "',company_phone = '" + info.getCompany_phone()
		+ "',family_phone = '" + info.getFamily_phone() + "' "
		+ "where netshop_id=" + info.getTemp_b2cmbr_uid();
		log.info(sql);
		int ret = st.executeUpdate(sql);
		if( ret > 0 ) {
			sql = "update mbr_addresses set delivery_address = '" + info.getV_address()
			+ "',telephone='" + info.getTelephone() + "', postcode = '" + info.getV_postcode()
			+ "' where id = (select address_id from mbr_members where netshop_id = " + info.getTemp_b2cmbr_uid() + ")";
			log.info(sql);
			st.executeUpdate(sql);
			
		}
		st.close();
		return ret;
	}

	/**
	 * 方法名:addNewMember() 此方法,用来插入一条新会员记录到MBR_MEMBERS表中 并且插入新的注册会员的地址表中的信息 参数
	 * Hashtable对象 返回 void
	 */
	private int insertMember(Connection conn,MemberInfo info) throws Exception {
		int v_msc = 1;
		int catalog_type = 1;
		Date date = new Date();
		SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
		String event_date = adf.format(date);
		System.out.println(event_date);
		int operator = 0;
		int recommend = 0;
		int gift_id = 0;
		String comments = "";
		int company_id = 1;
		//执行存储过程后返回的值
		int member_id = -1;
		int member_address_id = -1;
		String description = "";

		
		//调用存储过程
		conn.setAutoCommit(false);
		CallableStatement cst = conn.prepareCall("{?=call member.F_MEMBER_RECRUITMENTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cst.registerOutParameter(1, Types.INTEGER);
		cst.setString(2, info.getCard_id());
		// System.out.println("card_id....:"+this.getCard_id());
		cst.setString(3, info.getName());
		//  System.out.println("Name....:"+this.getName());
		cst.setString(4, info.getGender());
		//  System.out.println("gender....:"+this.getGender());
		cst.setString(5, "" + info.getBirthday());
		//  System.out.println("birthday....:"+this.getBirthday());
		cst.setInt(6, info.getCertificate_type());
		//  System.out.println("certificate_type....:"+this.getCertificate_type());
		cst.setString(7, info.getCertificate_code());
		//  System.out.println("certificate_code....:"+this.getCertificate_code());
		cst.setString(8, info.getTelephone());
		//   System.out.println("telephone....:"+this.getTelephone());
		cst.setString(9, info.getCompany_phone());
		//   System.out.println("company_phone....:"+this.getCompany_phone());
		cst.setString(10, info.getFamily_phone());
		//   System.out.println("family_phone....:"+this.getFamily_phone());

		//新加的字段 取值0，1 0为普通，1为联名
		cst.setInt(11, info.getCard_type());

		cst.setString(12, info.getV_postcode());
		//   System.out.println("postcode....:"+this.getV_postcode());
		cst.setString(13, info.getV_address());
		//   System.out.println("address....:"+this.getV_address());
		cst.setInt(14, v_msc);
		//  System.out.println("v_msc....:"+v_msc);
		cst.setInt(15, info.getCatalog_type());
		cst.setString(16, info.getEmail());
		//   System.out.println("catalog_type....:"+catalog_type);
		cst.setString(17, event_date);
		//System.out.println("event_date....:"+event_date);
		cst.setInt(18, operator);
		//  System.out.println("operator....:"+operator);
		cst.setInt(19, info.getRECOMMENDED_ID());
		//    System.out.println("recommend....:"+recommend);
		cst.setInt(20, info.getGIFT_ID());
		//    System.out.println("gift_id....:"+gift_id);
		//。。。新增字段的处理
		cst.setInt(21, info.getJoin_other());
		//    System.out.println("join_other....."+this.getJoin_other());
		cst.setString(22, comments);
		//   System.out.println("comments....:"+comments);
		cst.setInt(23, company_id);
		//   System.out.println("company_id....:"+company_id);

		cst.execute();

		member_id = cst.getInt(1);
		cst.close();
		info.setMember_id(member_id);
		Statement st = conn.createStatement();
		st.executeUpdate("update mbr_members set netshop_id =" + info.getTemp_b2cmbr_uid() + " where id =" + member_id );
		st.close();
		conn.commit();
		conn.setAutoCommit(true);
		return member_id;
	}

	/**
	 * 进行某些信息的转化
	 * @param conn
	 * @param info
	 * @throws Exception
	 */
	private void convertInfo(Connection conn,MemberInfo info) throws Exception {
		if (info.getGiftCode()== null ||
			info.getGiftCode().equals("")||
			info.getGiftCode().equals("0")||
			info.getRecommenderNetshopId() ==0) {
			info.setGIFT_ID(0);
			info.setRECOMMENDED_ID(0);
			return;
		}
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select id from prd_get_mbr_gifts where item_id = (select item_id from prd_items where item_code ='" + info.getGiftCode() + "')" );
		if(rs.next()) {
			info.setGIFT_ID(rs.getInt("id"));
		} 
		rs.close();
		rs = st.executeQuery("select id from mbr_members where netshop_id =" + info.getRecommenderNetshopId());
		if(rs.next()) {
			info.setRECOMMENDED_ID(rs.getInt("id"));
		}
		rs.close();
		st.close();
		
	}
}