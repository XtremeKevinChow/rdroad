/*
 * Created on 2004-7-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.exchange;

import org.apache.log4j.*;
import com.magic.utils.StringUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import com.magic.utils.Arith;
import com.magic.crm.util.*;

/**
 * ������վ��Ա��Ϣ
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class Member2Net {
	private static Logger log = Logger.getLogger(Member2Net.class);

	/**
	 * ����վϵͳ�ж�ȡ���е�ע���Ա�Ļ�Ա��,�����ݻ�Ա�Ŵ�CRMϵͳ���ҳ��û�Ա
	 * �ļ�¼��Ϣ,Ȼ��ȡ����Ա�ȼ�,��Ա���,��ԱeԱ,��Ա����,��Ա��Ȼ���,��Ա �����˻�����,����վ�ϵ����ݽ��и���.
	 */
	public void execute() {
		Connection conOra = null;
		Connection conMs = null;
		PreparedStatement psms = null;
		PreparedStatement ps2 = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conMs = DBManagerMS.getConnection();
			String sql = "update b2cmbr set b2cmbr_level= ?,b2cmbr_cardnumber=?,b2cmbr_deposit=?,"//b2cmbr_emoney=?,"
				+ "b2cmbr_exp=?,b2cmbr_yexp=?,b2cmbr_oexp=?,b2cmbr_cardid=?,b2cmbr_carduse=? where b2cmbr_id=?";
			psms = conMs.prepareStatement(sql);
			
			// �õ���Ҫ���»�Ա�Ļ�Աid --1��֮�ڷ������ĵ����ϻ�Ա
			conOra = DBManager2.getConnection();
			ps2 = conOra.prepareStatement("update mbr_members set mod_date = null where id= ?");
			
			stmt = conOra.createStatement();
			rs = stmt.executeQuery("select count(1) from mbr_members  where netshop_id>0 and mod_date is not null ");
			if(rs.next()) {
				log.info("-------------crm����վ���� " + rs.getInt(1) + "����Ա��Ϣ");
			}
			rs.close();
			
			String queryNetMemberSql = "select id,level_id,deposit,forzen_credit,emoney,exp,amount_exp,OLD_AMOUNT_EXP,card_type,netshop_id,card_id,old_card_code,is_make_card "
					+ " from mbr_members where netshop_id >0 and mod_date is not null ";
			rs = stmt.executeQuery(queryNetMemberSql);
			long suc_count = 0;
			long fail_count = 0;
			while (rs.next()) {
				//�����ϻ�Ա��������������վ��
				
				try {
					MemberInfo mb = new MemberInfo();
					//ȡ��CRM�е���վ��ԱID���������û�Ա��Ԥ������˿��¼
					mb.setMember_id(rs.getInt("id"));
					mb.setTemp_b2cmbr_uid(rs.getString("netshop_id"));
					mb.setCard_id(rs.getString("card_id"));
					//ȡ��CRM�ϻ�Ա��
					mb.setOld_card_number(StringUtil.cEmpty(rs
							.getString("old_card_code")));
					//mb.setMember_id(rs.getInt("id"));
					mb.setLevel_id(rs.getInt("level_id"));
					mb.setDeposit(rs.getFloat("deposit"));
					mb.setFrozen_credit(rs.getFloat("forzen_credit"));
					mb.setEmoney(rs.getFloat("emoney"));
					mb.setYear_exp(rs.getInt("amount_exp"));
					mb.setExp(rs.getInt("exp"));
					mb.setOld_year_exp(rs.getInt("OLD_AMOUNT_EXP"));
					mb.setIs_make_card(rs.getInt("is_make_card"));
					
					psms.setInt(1,mb.getLevel_id());
					psms.setString(2,mb.getOld_card_number());
					psms.setFloat(3,mb.getDeposit() + mb.getEmoney() - mb.getFrozen_credit());
					psms.setInt(4,mb.getExp());
					psms.setInt(5,mb.getYear_exp());
					psms.setInt(6,mb.getOld_year_exp());
					psms.setString(7,mb.getCard_id());
					psms.setInt(8,mb.getIs_make_card());
					psms.setString(9,mb.getTemp_b2cmbr_uid());
					psms.executeUpdate();
					
					ps2.setInt(1,mb.getMember_id());
					ps2.executeUpdate();
					suc_count++;
					
				} catch(Exception e) {
					fail_count++;
					log.error("��Ա���µ���վ����",e);
				}
				log.info("��Ա���µ���վ\t�ɹ�" + suc_count + "\tʧ��" + fail_count);
			}
			
		} catch (Exception e) {
			log.info("CRM������վ���»�Ա��Ϣ�쳣:", e);
		} finally {
			try {psms.close();} catch(Exception e){};
			try {stmt.close();} catch(Exception e){};
			try {conOra.close();} catch(Exception e){};
			try {conMs.close();} catch(Exception e){};
		}

	}

}