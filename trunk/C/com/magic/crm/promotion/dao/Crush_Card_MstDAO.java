/*
 * Created on 2005-9-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import com.magic.crm.config.dao.SConfigKeysDAO;
import com.magic.crm.promotion.entity.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import com.magic.crm.util.CodeName;

import com.magic.crm.util.MD5;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Crush_Card_MstDAO {

    public void insert(Connection conn, Crush_Card_MST ccm) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        try {

            MD5 m = new MD5();
            sql = "insert into CRUSH_CARD_MST (CARD_NUM,PASS,CRUSH_PERSON,card_type)values(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ccm.getCard_num());
            pstmt.setString(2, m.getMD5ofStr(ccm.getPass()));
            pstmt.setInt(3, ccm.getCreate_person());
            pstmt.setString(4, ccm.getCard_type());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }

    }

    /**
     * 销售销售卡 把status=1的销售卡设置成status=2
     * 
     * @param conn
     * @param ccm
     * @throws Exception
     */
    public void update(Connection conn, Crush_Card_MST ccm) throws Exception {
        PreparedStatement pstmt = null;

        try {
            String sql = "update CRUSH_CARD_MST set sale_person="
                    + ccm.getSale_person() + ","
                    + "status=2,sale_date=sysdate,crush_person="
                    + ccm.getCrush_person() + ",crush_date=sysdate "
                    + " where status=1 and card_num=" + ccm.getCard_num();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }

    }

    /*
     * 销售卡给会员帐户充值
     */
    public void updateDeposit(Connection conn, Crush_Card_MST ccm,
            String card_id) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double ccm_money = 0;
        double v_money = 0;
        int mb_id = 0;
        int levelID = 0;
        CallableStatement cstmt = null;
        String sp = null;
        int re = 0;
        try {
 
        	sp = "{?=call member.f_member_add_money(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			cstmt = conn.prepareCall(sp);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setString(2,"");
			cstmt.setString(3,card_id);
			cstmt.setDouble(4,ccm.getMoney());
			cstmt.setInt(5,16);
			cstmt.setString(6,"");
			cstmt.setInt(7,ccm.getCrush_person());
			cstmt.setString(8,"书香卡充值卡号:" + ccm.getCard_num());
			cstmt.setInt(9,1);
			cstmt.setInt(10,0);
			cstmt.setInt(11,0);
			cstmt.setString(12,ccm.getCard_num());
			cstmt.setInt(13,0);
			cstmt.setInt(14,2);
	
			cstmt.execute();
			re = cstmt.getInt(1);
			cstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
			}	
			/*
           if(re>=0){
            String sql = "update CRUSH_CARD_MST set crush_person="
                    + ccm.getCrush_person() + ",status=3,crush_date=sysdate "
                    + " where  status = 2 and card_num=" + ccm.getCard_num();
            pstmt = conn.prepareStatement(sql);	
            pstmt.executeUpdate();
           }
           */
        	/*
            String getMoney_sQuery = "select  DEPOSIT,id,level_id from MBR_MEMBERS  where card_id=?";
            pstmt = conn.prepareStatement(getMoney_sQuery);
            pstmt.setString(1, card_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                v_money = rs.getDouble(1);
                mb_id = rs.getInt(2);
                levelID = rs.getInt(3);
            }

            conn.setAutoCommit(false);
            String sql = "update CRUSH_CARD_MST set crush_person="
                    + ccm.getCrush_person() + ",status=3,crush_date=sysdate "
                    + " where  status = 2 and card_num=" + ccm.getCard_num();
            pstmt = conn.prepareStatement(sql);

            int count = pstmt.executeUpdate();
            if (count == 1) {
                ccm_money = ccm.getMoney();
   
                //     		modify by qbzhou 2006-03-15
                // 如果书香卡充值超过table[s_config_keys]中key[PREPAY_TO_GOLD_MEMB]对应的值后自动升为金卡会员
                String value = SConfigKeysDAO.getValueByKey(conn,
                        "PREPAY_TO_GOLD_MEMB");
                String sQuery1 = "update MBR_MEMBERS set DEPOSIT=deposit + ? where card_id=?";
                if (value != null) {
                    if (ccm_money >= Double.parseDouble(value) && levelID < 3) {
                        sQuery1 = "update MBR_MEMBERS set level_id = 3,DEPOSIT=deposit + ? where  card_id=?";
                    }
                }
                pstmt = conn.prepareStatement(sQuery1);
                pstmt.setDouble(1, ccm_money);
                pstmt.setString(2, card_id);
                pstmt.executeUpdate();

                String sQuery2 = "INSERT INTO MBR_MONEY_HISTORY(ID,MEMBER_ID,DEPOSIT,money_update,comments,OPERATOR_ID,EVENT_TYPE,pay_method)VALUES"
                        + "(seq_mbr_money_history.nextval,?,?,?,?,?,?,?) ";
                pstmt = conn.prepareStatement(sQuery2);

                pstmt.setInt(1, mb_id);
                pstmt.setDouble(2, ccm_money + v_money);
                pstmt.setDouble(3, ccm_money);
                pstmt.setString(4, "卡号:" + ccm.getCard_num());
                pstmt.setInt(5, ccm.getCrush_person());
                pstmt.setInt(6, 2020);
                pstmt.setInt(7, 16);
                pstmt.executeUpdate();
            }
            */
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
     * 检查卡号是否正确
     * 
     * @param conn
     * @param card_num
     * @return
     * @throws SQLException
     */
    public int checkCardNum(Connection conn, String card_num)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        int checkCardNum = 0;
        try {

            sql = "select card_num from CRUSH_CARD_MST where card_num='"
                    + card_num + "'";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                checkCardNum = 1;

            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
        return checkCardNum;
    }

    /**
     * 得到销售卡当前信息 '1、创建2、销售3、充值4、作废';
     * 
     * @param conn
     * @param ccm
     * @return
     * @throws SQLException
     */
    public Crush_Card_MST getCardInfo(Connection conn, String card_num)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        int status = 0;
        Crush_Card_MST ccm = new Crush_Card_MST();
        try {
            sql = "select a.card_num, a.pass, a.card_type, a.status, a.begin_date, a.end_date, "
                    + "a.create_date, a.sale_person, a.crush_person, a.crush_date, b.money from CRUSH_CARD_MST a "
                    + "inner join CRUSH_CARD_VALUE b on a.card_type = b.code "
                    + "where a.card_num = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, card_num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ccm.setCard_num(rs.getString("card_num"));
                ccm.setPass(rs.getString("pass"));
                ccm.setCard_type(rs.getString("card_type"));
                ccm.setStatus(rs.getString("status"));
                ccm.setBegin_date(rs.getString("begin_date"));
                ccm.setEnd_date(rs.getString("end_date"));
                ccm.setCreate_date(rs.getString("create_date"));
                ccm.setSale_person(rs.getInt("sale_person"));
                ccm.setCrush_person(rs.getInt("crush_person"));
                ccm.setCreate_date(rs.getString("crush_date"));
                ccm.setMoney(rs.getDouble("money"));

            }

        } catch (SQLException e) {

            throw e;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
        return ccm;
    }

    /*
     * 检查已销售(status=2)的充值卡密码是否正确
     */
    public int checkPass(Connection conn, Crush_Card_MST ccm)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        int checkpass = 0;
        MD5 m = new MD5();
        try {

            sql = "select card_num from CRUSH_CARD_MST where card_num='"
                    + ccm.getCard_num() + "' " + " and status=2 and pass='"
                    + m.getMD5ofStr(ccm.getPass()).toLowerCase() + "'";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                checkpass = 1;

            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
        return checkpass;
    }

    /*
     * 列出所有充值卡信息
     */
    public Collection ListCCM(Connection con, String condition)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Collection ccmCol = new ArrayList();

        try {
            String sQuery = "select a.card_num, a.card_type, a.create_date, a.crush_person, a.sale_person, "
                    + "a.crush_date, a.sale_date, a.status, "
                    + " (select name from org_persons where id=sale_person) sale_person_name,"
                    + " (select name from org_persons where id=crush_person) crush_person_name, "
                    + " b.money "
                    + " from CRUSH_CARD_MST a inner join CRUSH_CARD_VALUE b on a.card_type = b.code " 
                    + condition;

            System.out.println(sQuery);
            pstmt = con.prepareStatement(sQuery);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Crush_Card_MST ccm = new Crush_Card_MST();
                ccm.setCard_num(rs.getString("card_num"));
                ccm.setCard_type(rs.getString("card_type"));
                ccm.setCreate_date(rs.getString("create_date"));
                //ccm.setCreate_person(rs.getInt("crush_person"));
                ccm.setCrush_date(rs.getString("crush_date"));
                ccm.setCreate_person(rs.getInt("crush_person"));
                ccm.setSale_date(rs.getString("sale_date"));
                //ccm.setSale_person(rs.getInt("sale_person"));
                ccm.setStatus(rs.getString("status"));
                ccm.setSale_person_name(rs.getString("sale_person_name"));
                ccm.setCrush_person_name(rs.getString("crush_person_name"));
                ccm.setMoney(rs.getDouble("money"));
                ccmCol.add(ccm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return ccmCol;
    }

    /**
     * 检查刚创建的书香卡和密码是否匹配 status=1
     * 
     * @author Administrator(ysm) Created on 2005-10-14
     */
    public int checkCardPass(Connection conn, Crush_Card_MST ccm)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        int c_pass = 0;
        MD5 m = new MD5();
        try {

            sql = "select status from CRUSH_CARD_MST where card_num='"
                    + ccm.getCard_num() + "' " + " and pass='"
                    + m.getMD5ofStr(ccm.getPass()).toLowerCase() + "'";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                c_pass = rs.getInt("status");

            }
            if (ccm.getStatus().equals("4")) {
                String up_sql = "update CRUSH_CARD_MST set status=4 where card_num='"
                        + ccm.getCard_num() + "' ";
                pstmt = conn.prepareStatement(up_sql);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
        return c_pass;
    }
    
    
    /*
     * 列出所有书香卡类型
     */
    public static Collection findAllCrushTypes(Connection conn)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Collection crushTypes = new ArrayList();

        try {
            String sQuery = "select * from crush_card_value  " ;

            pstmt = conn.prepareStatement(sQuery);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	int id = rs.getInt("id");
            	String code = rs.getString("code");
            	double money = rs.getDouble("money");
            	double fact_money = rs.getDouble("fact_money");
            	StringBuffer name = new StringBuffer();
            	name.append(code);
            	name.append("-");
            	name.append(money);
            	name.append("-");
            	name.append(fact_money);
            	CodeName codeName = new CodeName(String.valueOf(id), name.toString());
                
            	crushTypes.add(codeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return crushTypes;
    }
}
