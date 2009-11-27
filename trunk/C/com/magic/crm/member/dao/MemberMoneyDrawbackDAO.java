package com.magic.crm.member.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.magic.crm.member.entity.MembeMoneyHistory;
import com.magic.crm.member.form.MemberMoneyDrawbackForm;

public class MemberMoneyDrawbackDAO {
    /**
     * 列出待审核的退款数据
     * @param conn
     * @param data
     * @throws SQLException
     */
    public static ArrayList listAudit(Connection conn ) throws SQLException {
        ArrayList ret = new ArrayList();
    	PreparedStatement ps = null;
        String sql = "select t1.*,t2.card_id,t2.name as mbr_name,t3.name as operator_name " +
        		" from mbr_money_drawback t1 join mbr_members t2 on t1.member_id = t2.id" +
        		" left join org_persons t3 on t1.creator_id = t3.id " +
        		" where t1.status=0 order by t1.create_date asc ";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	MemberMoneyDrawbackForm info = new MemberMoneyDrawbackForm();
            	info.setID(rs.getLong("id"));
            	info.setCardID(rs.getString("card_id"));
            	info.setName(rs.getString("mbr_name"));
            	info.setOperator_name(rs.getString("operator_name"));
            	info.setCreateDate(rs.getString("create_date"));
            	info.setStatus(rs.getInt("status"));
            	info.setDescription(rs.getString("comments"));
            	info.setAmount(rs.getDouble("money"));
            	ret.add(info);
            }
        	rs.close();
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException ex) {}
        }
        return ret;
    }
    
    
    public static int audit(Connection conn ,MemberMoneyDrawbackForm info ) throws SQLException {
        int ret = 0;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
            //step1 取出记录的信息
        	String sql ="select * from mbr_money_drawback where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, info.getID());
            rs = ps.executeQuery();
            if(rs.next()) {
            	info.setMemberID(rs.getLong("member_id"));
            	info.setAmount(rs.getDouble("money"));
            	info.setDescription(rs.getString("comments"));
            }
            rs.close();
            ps.close();
            
            //step2 更新记录的状态
            sql = "update mbr_money_drawback set status=1, auditor=?,audit_date=sysdate where id =?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, info.getAuditor());
            ps.setLong(2, info.getID());
            ps.executeUpdate();
            ps.close();
            
            //step3 更新会员信息
            sql ="UPDATE mbr_members SET deposit = deposit -?,forzen_credit = forzen_credit-? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, info.getAmount());
            ps.setDouble(2, info.getAmount());
            ps.setLong(3, info.getMemberID());
            ps.executeUpdate();
			ps.close();
			
			//step4  插入帐户历史表
			sql = "INSERT INTO mbr_money_history (ID, member_id, operator_id, modify_date, deposit, " +
					" money_update, comments, event_type, flush_type)"
			+ "VALUES (seq_mbr_money_history.NEXTVAL, ?, ?, SYSDATE, (select deposit from mbr_members where id =?), ?,"
            + "'会员退款' ||?, 2070, 13 )";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, info.getMemberID());
			ps.setLong(2, info.getAuditor());
			ps.setLong(3, info.getMemberID());
			ps.setDouble(4, -info.getAmount());
			ps.setString(5, info.getDescription());
			ps.executeUpdate();
			
		} catch (SQLException ex) {
            throw ex;
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException ex) {}
        }
        return ret;
    }
    
    public static int cancel(Connection conn ,MemberMoneyDrawbackForm info ) throws SQLException {
        int ret = 0;
    	PreparedStatement ps = null;
        String sql = "update mbr_money_drawback set status=-1, auditor=?,audit_date=sysdate where id =?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, info.getAuditor());
            ps.setLong(2, info.getID());
            ret = ps.executeUpdate();
            
            ps.close();
            ps = conn.prepareStatement("update mbr_members set forzen_credit = forzen_credit - (select money from mbr_money_drawback where id =?)"
            		+ " where id = (select member_id from mbr_money_drawback where id = ?)");
            ps.setLong(1, info.getID());
            ps.setLong(2, info.getID());
            ret = ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException ex) {}
        }
        return ret;
    }
}
