package com.magic.crm.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.magic.crm.product.entity.Provider;
import com.magic.crm.product.form.ProviderForm;
import com.magic.crm.common.pager.CompSQL;


/**
 * Jdbc Bean Class<br>
 * <br>
 * Autogenerated on 02/19/2005 04:39:38<br>
 * &nbsp;&nbsp;&nbsp; table = "PROVIDERS"
 * 
 * @author Generator
 */
public class ProviderDAO implements java.io.Serializable {

	private static Logger log = Logger.getLogger("ProviderDAO.class");

	public Collection query(Connection con, ProviderForm pf) throws Exception {

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		ArrayList list = new ArrayList();
		try {
			String providerName = pf.getProviderName();
			String providerConnecter = (String) pf.getConnecter();
			String sqlCondition = "";
			String sQuery = "SELECT pro_no, pro_name, pro_add, goods_Add, money_Days, pick_Up, pay_Days, cooperate, return_Policy, return_Bit, Pro_Manager, sto_Protect, pri_Protect, post_Add,PURCHASER,invoice_type, connecter, tax_Rate, province, city, pro_Zip, tel_Zip, tel, fax, email, pro_Title, pro_Category, ID, tel2, comments FROM PROVIDERS  where rownum <= 30";
			if (providerName != null && !(providerName.equals(""))) {
				sqlCondition = sqlCondition + " and pro_name like '%"
						+ providerName + "%'";
			}
			if (providerConnecter != null && !(providerConnecter.equals(""))) {
				sqlCondition = sqlCondition + " and connecter like '%"
						+ providerConnecter + "%'";
			}

			sQuery = sQuery + sqlCondition;

			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			int recNo = 0;

			while (rs.next()) {
				Provider info = new Provider();
				info.setID(rs.getString("id"));
				info.setPro_no(rs.getString("pro_no"));
				info.setProviderName(rs.getString("pro_name"));
				info.setConnecter(rs.getString("connecter"));
				info.setCity(rs.getString("city"));
				list.add(info);
			}
			// pageModel.setRecordCount

		} catch (SQLException e) {
			// System.out.println(e);
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
		return list;
	}

	/*
	 * 判断产品简称是否重复
	 */
	public boolean checkTitle(Connection con, String title) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean titleseq = false;
		Provider info = new Provider();
		try {
			String ardidSql = "select PRO_TITLE from providers where PRO_TITLE='"
					+ title + "'";
			pstmt = con.prepareStatement(ardidSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				titleseq = true;
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
		return titleseq;
	}

	public Collection queryProviders(Connection con, ProviderForm param, String condition)
			throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection allProviders = new ArrayList();
		try {
			String sQuery = "SELECT pro_no,pro_name,pro_add,province,city,pro_Zip,tax_Rate,cooperate,invoice_Type from providers where 1 = 1  "
					+ condition;
			sQuery = CompSQL.getNewSql(sQuery);
			
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, param.getPager().getOffset()
					+ param.getPager().getLength());
			pstmt.setInt(2, param.getPager().getOffset());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Provider info = new Provider();
				info.setPro_no(rs.getString("pro_no"));
				// log.info("sQuery is "+rs.getString("pro_no"));
				info.setProviderName(rs.getString("pro_name"));
				info.setProviderAddress(rs.getString("pro_add"));
				info.setProvince(rs.getString("province"));
				info.setCity(rs.getString("city"));
				info.setProviderZip(rs.getString("pro_Zip"));
				info.setTaxRate(rs.getFloat("tax_Rate"));
				info.setCooperate(rs.getString("cooperate"));
				info.setInvocieType(rs.getString("invoice_Type"));

				allProviders.add(info);
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
		return allProviders;
	}

	public int countProviders(Connection con, String condition) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		try {
			String sQuery = "SELECT count(1) from providers where 1 = 1 " + condition;

			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
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
		return cnt;
	}

	public com.magic.crm.product.entity.Provider findByPrimaryKey(
			Connection con, String primaryKey) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		com.magic.crm.product.entity.Provider info = new com.magic.crm.product.entity.Provider();

		try {
			String sQuery = "SELECT pro_no, pro_name, pro_add, goodsAdd, moneyDays, pickUp, payDays, cooperate, returnPolicy, returnBit, "
					+ "ProviderManager, stockProtect, priPtotect, postAdd, purchase, invocieType, connecter, taxRate, province, city, providerZip, telZip, telephone, fax, email, providerTitle, providerCategory, ID, telephone2, comments FROM PROVIDERS WHERE ";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				info.setPro_no(rs.getString("pro_no"));
				info.setProviderName(rs.getString("pro_name"));
				info.setProviderAddress(rs.getString("pro_add"));
				info.setMoneyDays(rs.getFloat("moneyDays"));
				info.setPayDays(rs.getFloat("payDays"));
				info.setReturnBit(rs.getFloat("returnBit"));
				info.setStockProtect(rs.getFloat("stockProtect"));
				info.setPriPtotect(rs.getFloat("priPtotect"));
				info.setPostAdd(rs.getString("postAdd"));
				info.setTaxRate(rs.getFloat("taxRate"));
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

	/*
	 * 根据电话区号得到供应商编号 编号（由输入的区号 ＋“－”＋ 三位数字【现在相同区号的最大值＋1，不足三位的左补零】）
	 */
	public String getProNO(Connection conn, String prono) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		prono = prono + "-";
		String newno = "";

		try {

			String sQuery = "SELECT max(pro_no) FROM PROVIDERS WHERE pro_no like '%"
					+ prono + "%'";
			pstmt = conn.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				newno = rs.getString(1);
				if (newno != null) {
					String a = newno.substring(newno.indexOf("-") + 1, newno
							.length());
					int b = Integer.parseInt(a) + 1;
					if (b > 100)
						newno = prono + b;
					if (b >= 100 && b < 1000)
						newno = prono + b;
					if (b >= 10 && b < 100)
						newno = prono + "0" + b;
					if (b > 0 && b < 10)
						newno = prono + "00" + b;
				} else {
					newno = prono + "001";
				}
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
		return newno;
	}

	public void insert(Connection con,
			com.magic.crm.product.entity.Provider info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int pid = 0;
		try {

			String proSql = "select SEQ_PROVIDERS_ID.nextval from dual";
			pstmt = con.prepareStatement(proSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pid = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		try {

			String sQuery = "INSERT INTO PROVIDERS(PRO_NO,PRO_NAME,PRO_ADD,GOODS_ADD,MONEY_DAYS,"
					+ "PICK_UP,PAY_DAYS,COOPERATE,RETURN_POLICY,RETURN_BIT,PRO_MANAGER,STO_PROTECT,"
					+ "PRI_PROTECT,POST_ADD,PURCHASER, INVOICE_TYPE,CONNECTER,TAX_RATE,PROVINCE,CITY,"
					+ "PRO_ZIP,TEL_ZIP,TEL,FAX,EMAIL,PRO_TITLE,PRO_CATEGORY,ID,TEL2,comments) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sQuery);

			pstmt.setString(1, this.getProNO(con, info.getTelZip()));
			pstmt.setString(2, info.getProviderName());
			pstmt.setString(3, info.getProviderAddress());
			pstmt.setString(4, info.getGoodsAdd());
			pstmt.setFloat(5, info.getMoneyDays());
			pstmt.setString(6, info.getPickUp());
			pstmt.setFloat(7, info.getPayDays());
			pstmt.setString(8, info.getCooperate());
			pstmt.setString(9, info.getReturnPolicy());
			pstmt.setFloat(10, info.getReturnBit());
			pstmt.setString(11, info.getProviderManager());
			pstmt.setFloat(12, info.getStockProtect());
			pstmt.setFloat(13, info.getPriPtotect());
			pstmt.setString(14, info.getPostAdd());
			pstmt.setString(15, info.getPurchase());
			pstmt.setString(16, info.getInvocieType());
			pstmt.setString(17, info.getConnecter());
			pstmt.setFloat(18, info.getTaxRate());
			pstmt.setString(19, info.getProvince());
			pstmt.setString(20, info.getCity());
			pstmt.setString(21, info.getProviderZip());
			pstmt.setString(22, info.getTelZip());
			pstmt.setString(23, info.getTelephone());
			pstmt.setString(24, info.getFax());
			pstmt.setString(25, info.getEmail());
			pstmt.setString(26, info.getProviderTitle());
			pstmt.setString(27, info.getProviderCategory());
			pstmt.setInt(28, pid);
			pstmt.setString(29, info.getTelephone2());
			pstmt.setString(30, info.getComments());

			pstmt.execute();

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

	public void update(Connection con,
			com.magic.crm.product.entity.Provider info) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String sQuery = "UPDATE PROVIDERS SET pro_no=?, pro_name=?, pro_add=?, goodsAdd=?, moneyDays=?, pickUp=?, payDays=?, cooperate=?, returnPolicy=?, returnBit=?, ProviderManager=?, stockProtect=?, priPtotect=?, postAdd=?, purchase=?, invocieType=?, connecter=?, taxRate=?, province=?, city=?, providerZip=?, telZip=?, telephone=?, fax=?, email=?, providerTitle=?, providerCategory=?, ID=?, telephone2=?, comments=? WHERE ";
			pstmt = con.prepareStatement(sQuery);

			pstmt.setString(1, info.getPro_no());
			pstmt.setString(2, info.getProviderName());
			pstmt.setString(3, info.getProviderAddress());
			pstmt.setFloat(5, info.getMoneyDays());
			pstmt.setFloat(7, info.getPayDays());
			pstmt.setFloat(10, info.getReturnBit());
			pstmt.setFloat(12, info.getStockProtect());
			pstmt.setFloat(13, info.getPriPtotect());
			pstmt.setString(14, info.getPostAdd());
			pstmt.setFloat(18, info.getTaxRate());

			pstmt.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	public void delete(Connection con,
			com.magic.crm.product.entity.Provider info) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String sQuery = "DELETE PROVIDERS WHERE ";
			pstmt = con.prepareStatement(sQuery);

			pstmt.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

}