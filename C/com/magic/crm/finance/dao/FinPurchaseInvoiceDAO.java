/*
 * Created on 2006-7-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.magic.crm.finance.entity.Period;
import com.magic.crm.finance.entity.FinPurchaseInvoice;
import com.magic.crm.finance.entity.FinPurchaseInvoiceItems;
import com.magic.crm.finance.form.FinPurchaseInvoiceForm;
import com.magic.crm.finance.form.FinPurchaseInvoiceItemsForm;


/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseInvoiceDAO {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(FinPurchaseInvoiceDAO.class);

    /**
     * 判断实际发票号是否已经存在
     * 
     * @param con
     * @return
     * @throws SQLException
     */
    public boolean checkFactApCode(Connection con, FinPurchaseInvoice param)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        try {
            sql = "select * from fin_ap_mst where fact_ap_code = ? and ap_id <> ? ";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, param.getFactAPCode());
            pstmt.setInt(2, param.getApID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
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
    }

    /**
     * 得到采购发票主键
     * 
     * @param con
     * @return
     * @throws SQLException
     */
    public int generateMasterID(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        try {
            sql = "select S_FIN_AP_MST_ID.nextval from dual ";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
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
    }

    /**
     * 插入发票主表
     * 
     * @param con
     * @param data
     * @throws SQLException
     */
    public void insertMaster(Connection con, FinPurchaseInvoice data)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = null;
        try {

            sql = "insert into fin_ap_mst " 
                	+ "("
                    + "AP_ID, SYS_AP_CODE, FACT_AP_CODE, "
                    + "CREATEDATE, CHECKDATE, PRO_NO, TAX, CREATOR, "
                    + "CHECKPERSON, TALLIER, TALLY_DATE, AMT, STATUS, "
                    + "REMARK, AP_TYPE, INVOICE_DATE" 
                    + ") " 
                    + "values " 
                    + "(" 
                    + " ?, ?, ?, "
                    + " sysdate, ?, ?, ?, ?, " 
                    + " ?, ?, ?, ?, '1', "
                    + " ?, ?, ? " 
                    + ")";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, data.getApID());
            pstmt.setString(2, "P" + data.getApID());
            pstmt.setString(3, data.getFactAPCode());
            pstmt.setDate(4, data.getCheckDate());
            pstmt.setString(5, data.getProNO());
            pstmt.setDouble(6, data.getTax());
            pstmt.setString(7, data.getCreator());
            pstmt.setString(8, data.getCheckPerson());
            pstmt.setString(9, data.getTallier());
            pstmt.setDate(10, data.getTallyDate());
            pstmt.setDouble(11, data.getAmt());
            pstmt.setString(12, data.getRemark());
            pstmt.setString(13, data.getApType());
            pstmt.setDate(14, data.getInvoiceDate());
            pstmt.execute();

        } catch (SQLException e) {
            logger.error(sql);
            throw e;
        } finally {

            if (pstmt != null)
                pstmt.close();

        }
    }

    /**
     * 根据主键删除主表记录
     * 
     * @param con
     * @param param
     * @throws SQLException
     */
    public void deleteMaster(Connection con, FinPurchaseInvoiceForm param)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "delete from fin_ap_mst where ap_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getApID());

            pstmt.execute();

        } catch (SQLException e) {
            logger.error("[" + param.getApID() + "]");
            throw e;
        } finally {

            if (pstmt != null)
                pstmt.close();

        }
    }

    /**
     * 根据主键删除主表记录（修改）
     * 
     * @param con
     * @param param
     * @throws SQLException
     */
    public void deleteMasterByPK(Connection con, int apID) throws SQLException {
        FinPurchaseInvoiceForm param = new FinPurchaseInvoiceForm();
        param.setApID(apID);
        deleteMaster(con, param);
    }

    /**
     * 插入发票明细
     * 
     * @param con
     * @param data
     * @throws SQLException
     */
    public void insertItems(Connection con, FinPurchaseInvoiceItems data)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = null;
        try {

            sql = "insert into fin_ap_dtl " 
                	+ "("
                	+ "AP_DTL_ID, AP_ID, ITEM_ID, QTY, PUR_PRICE, "
                    + "AP_PRICE, AMT, TOTAL_AMT, TAX, PS_DTL_ID, "
                    + "DIS_AMT, SHOULD_PAY" 
                    + ") " 
                    + "values ("
                    + " S_FIN_AP_DTL_ID.nextval, ?, ?, ?, ?, "
                    + " ?, ?, ?, ?, ?, " 
                    + " ?, ? " 
                    + ")";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, data.getApID());
            pstmt.setInt(2, data.getItemID());
            pstmt.setDouble(3, data.getQty());
            pstmt.setDouble(4, data.getPurPrice());
            pstmt.setDouble(5, data.getApPrice());
            pstmt.setDouble(6, data.getAmt());
            pstmt.setDouble(7, data.getTotalAmt());
            pstmt.setDouble(8, data.getTax());
            pstmt.setInt(9, data.getPsDtlID());
            pstmt.setDouble(10, data.getDisAmt());
            pstmt.setDouble(11, data.getShouldPay());
            pstmt.execute();

        } catch (SQLException e) {
            logger.error(data.toString());
            throw e;
        } finally {

            if (pstmt != null)
                pstmt.close();

        }
    }

    /**
     * 根据主表主键删除明细记录
     * 
     * @param con
     * @param param
     * @throws SQLException
     */
    public void deleteItemsByFK(Connection con, FinPurchaseInvoiceForm param)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "delete from fin_ap_dtl where ap_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getApID());
            pstmt.execute();

        } catch (SQLException e) {
            logger.error("[" + param.getApID() + "]");
            throw e;
        } finally {

            if (pstmt != null)
                pstmt.close();

        }
    }

    /**
     * 根据主键删除明细记录
     * 
     * @param con
     * @param param
     * @throws SQLException
     */
    public void deleteItemsByPK(Connection con,
            FinPurchaseInvoiceItemsForm param) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "delete from fin_ap_dtl where ap_dtl_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getApDtlID());
            pstmt.execute();

        } catch (SQLException e) {
            logger.error("[" + param.getApDtlID() + "]");
            throw e;
        } finally {

            if (pstmt != null)
                pstmt.close();

        }
    }

    /**
     * 根据外键（主表主键）删除明细记录
     * 
     * @param con
     * @param apDtlID
     * @throws SQLException
     */
    public void deleteItemsByFK(Connection con, int apID) throws SQLException {
        FinPurchaseInvoiceForm param = new FinPurchaseInvoiceForm();
        param.setApID(apID);
        deleteItemsByFK(con, param);
    }

    /**
     * 根据供应商查询采购发票
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findInvoicesByCondition(Connection con,
            FinPurchaseInvoiceForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select a.*, b.pro_name "
                    + "from fin_ap_mst a inner join providers b on a.pro_no = b.pro_no "
                    + "where 1 = 1 ";
            if (param.getStatus() != null && !"".equals(param.getStatus())) {
                sql += " and a.status = '" + param.getStatus() + "'";
            }

            if (param.getProNO() != null && !"".equals(param.getProNO())) {
                sql += " and a.pro_no = '" + param.getProNO().trim() + "'";

            }
            
            /*if (param.getInvoiceDate() != null && !"".equals(param.getInvoiceDate())) {
                sql += "  and a.invoice_date >= date'" + param.getInvoiceDate()+"' ";
                sql+="  and a.invoice_date < date'" + param.getInvoiceDate()+"'+1 ";
            }*/
            if (param.getCreator() != null && !"".equals(param.getCreator())) {
                sql += " and a.ap_id in(select x.ap_id from fin_ap_dtl x inner join prd_items z on x.item_id = z.item_id and z.item_code = '" + param.getCreator().trim() + "')";

            }
            /*if (param.getStartDate() != null && !"".equals(param.getStartDate())) {
                sql += "  and a.invoice_date >= date'" + param.getStartDate()+"' ";
            }
            
            if (param.getEndDate() != null && !"".equals(param.getEndDate())) {
                sql += "  and a.invoice_date < date'" + param.getEndDate()+"' + 1 ";
            }
   */
            if (param.getFactAPCode() != null && !"".equals(param.getFactAPCode())) {
                sql += " and a.FACT_AP_CODE = '" + param.getFactAPCode().trim() + "'";

            }
            if (param.getProName() != null && !"".equals(param.getProName())) {
                sql += " and b.pro_name like '%" + param.getProName().trim() + "%'";

            }            
            if (param.getStartDate() != null
                    && !"".equals(param.getStartDate())) {
                sql += " and a.createdate >= date'" + param.getStartDate()
                        + "'";
            }

            if (param.getEndDate() != null && !"".equals(param.getEndDate())) {
                sql += " and a.createdate < date'" + param.getEndDate()
                        + "' + 1 ";
            }
            
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
         
            while (rs.next()) {
                FinPurchaseInvoiceForm data = new FinPurchaseInvoiceForm();
                data.setApID(rs.getInt("AP_ID"));
                data.setSysAPCode(rs.getString("SYS_AP_CODE"));
                data.setFactAPCode(rs.getString("FACT_AP_CODE"));
                data.setApType(rs.getString("AP_TYPE"));
                data.setCreateDate(rs.getDate("CREATEDATE"));
                data.setCheckDate(rs.getDate("CHECKDATE"));
                data.setProNO(rs.getString("PRO_NO"));
                data.setProName(rs.getString("PRO_NAME"));
                data.setTax(rs.getDouble("TAX"));
                data.setCreator(rs.getString("CREATOR"));
                data.setCheckPerson(rs.getString("CHECKPERSON"));
                data.setTallier(rs.getString("TALLIER"));
                data.setTallyDate(rs.getDate("TALLY_DATE"));
                data.setAmt(rs.getDouble("AMT"));
                data.setStatus(rs.getString("STATUS"));
                data.setIsFirst(rs.getString("IS_FIRST"));
                data.setRemark(rs.getString("REMARK"));
                data.setInvoiceDate(rs.getDate("invoice_Date"));
                coll.add(data);
            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getStartDate() + "], ["
                    + param.getEndDate() + "]");
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
     * 根据主键查找相应采购发票
     * 
     * @param con
     * @param param
     * @return void
     * @throws SQLException
     */
    public void findInvoicesByPK(Connection con, FinPurchaseInvoiceForm param)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        try {

            sql = "select a.*, b.pro_name " + "from fin_ap_mst a "
                    + "inner join providers b on a.pro_no = b.pro_no "
                    + "where ap_id = ? ";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getApID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                param.setApID(rs.getInt("AP_ID"));
                param.setSysAPCode(rs.getString("SYS_AP_CODE"));
                param.setFactAPCode(rs.getString("FACT_AP_CODE"));
                param.setApType(rs.getString("AP_TYPE"));
                param.setCreateDate(rs.getDate("CREATEDATE"));
                param.setCheckDate(rs.getDate("CHECKDATE"));
                param.setProNO(rs.getString("PRO_NO"));
                param.setProName(rs.getString("PRO_NAME"));
                param.setTax(rs.getDouble("TAX"));
                param.setCreator(rs.getString("CREATOR"));
                param.setCheckPerson(rs.getString("CHECKPERSON"));
                param.setTallier(rs.getString("TALLIER"));
                param.setTallyDate(rs.getDate("TALLY_DATE"));
                param.setAmt(rs.getDouble("AMT"));
                param.setStatus(rs.getString("STATUS"));
                param.setIsFirst(rs.getString("IS_FIRST"));
                param.setRemark(rs.getString("REMARK"));
                param.setInvoiceDate(rs.getDate("INVOICE_DATE"));

            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getApID() + "]");
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
     * 根据外键（主表主键）查找采购到货单明细
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findInvoicesItemsByFK(Connection con,
            FinPurchaseInvoiceForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select a.*, b.item_code, b.name as item_name, (a. qty + c.use_qty) as use_qty "
                    + "from fin_ap_dtl a inner join prd_items b on a.item_id = b.item_id "
                    + "inner join fin_ps_dtl c on a.ps_dtl_id = c.ps_dtl_id "
                    + "where a.ap_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getApID());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinPurchaseInvoiceItemsForm data = new FinPurchaseInvoiceItemsForm();
                data.setApDtlID(rs.getInt("AP_DTL_ID"));
                data.setApID(rs.getInt("AP_ID"));
                data.setItemID(rs.getInt("ITEM_ID"));
                data.setItemCode(rs.getString("ITEM_CODE"));
                data.setItemName(rs.getString("ITEM_NAME"));
                data.setQty(rs.getDouble("QTY"));
                data.setUseQty(rs.getDouble("USE_QTY"));//可用数量
                data.setPurPrice(rs.getDouble("PUR_PRICE"));
                data.setApPrice(rs.getDouble("AP_PRICE"));
                data.setAmt(rs.getDouble("AMT"));
                data.setTotalAmt(rs.getDouble("TOTAL_AMT"));
                data.setTax(rs.getDouble("TAX"));
                data.setPsDtlID(rs.getInt("PS_DTL_ID"));
                data.setDisAmt(rs.getDouble("DIS_AMT"));
                data.setShouldPay(rs.getDouble("SHOULD_PAY"));
                data.setBudget_Cost(data.getQty()*data.getPurPrice());
 
                coll.add(data);
            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getApID() + "]");
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
     * 根据主键查找采购发票明细
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public FinPurchaseInvoiceItemsForm findInvoicesItemsByPK(Connection con,
            int apDtlID) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        FinPurchaseInvoiceItemsForm data = null;
        try {

            sql = "select a.*, b.item_code, b.name as item_name, (a. qty + c.use_qty) as use_qty "
                    + "from fin_ap_dtl a inner join prd_items b on a.item_id = b.item_id "
                    + "inner join fin_ps_dtl c on a.ps_dtl_id = c.ps_dtl_id "
                    + "where a.ap_dtl_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, apDtlID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                data = new FinPurchaseInvoiceItemsForm();
                data.setApDtlID(rs.getInt("AP_DTL_ID"));
                data.setApID(rs.getInt("AP_ID"));
                data.setItemID(rs.getInt("ITEM_ID"));
                data.setItemCode(rs.getString("ITEM_CODE"));
                data.setItemName(rs.getString("ITEM_NAME"));
                data.setQty(rs.getDouble("QTY"));
                data.setUseQty(rs.getDouble("USE_QTY"));//可用数量
                data.setPurPrice(rs.getDouble("PUR_PRICE"));
                data.setApPrice(rs.getDouble("AP_PRICE"));
                data.setAmt(rs.getDouble("AMT"));
                data.setTotalAmt(rs.getDouble("TOTAL_AMT"));
                data.setTax(rs.getDouble("TAX"));
                data.setPsDtlID(rs.getInt("PS_DTL_ID"));
                data.setDisAmt(rs.getDouble("DIS_AMT"));
                data.setShouldPay(rs.getDouble("SHOULD_PAY"));

            }

        } catch (SQLException e) {
            logger.error("query error: [" + apDtlID + "]");
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

        return data;
    }

    /**
     * 审核采购发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void checkInvoice(Connection conn, FinPurchaseInvoiceForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.P_ApInvoiceCheck(?, ?)}");
            cstmt.setInt(1, param.getApID());
            cstmt.setInt(2, param.getOperatorID());
            cstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + param.getApID() + "]");
            throw ex;
        } finally {
            if (cstmt != null)
                try {
                    cstmt.close();
                } catch (SQLException ex) {

                }
        }
    }

    /**
     * 审核采购发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void checkInvoice(Connection conn, int apID, int logID)
            throws SQLException {

        FinPurchaseInvoiceForm param = new FinPurchaseInvoiceForm();
        param.setApID(apID);
        param.setOperatorID(logID);
        checkInvoice(conn, param);

    }

    /**
     * 弃审采购发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void uncheckInvoice(Connection conn, FinPurchaseInvoiceForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn
                    .prepareCall("{call accounts.P_ApInvoiceUnCheck(?, ?)}");
            cstmt.setInt(1, param.getApID());
            cstmt.setInt(2, param.getOperatorID());
            cstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + param.getApID() + "]");
            throw ex;
        } finally {
            if (cstmt != null)
                try {
                    cstmt.close();
                } catch (SQLException ex) {

                }
        }
    }

    /**
     * 弃审采购发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void uncheckInvoice(Connection conn, int apID, int logID)
            throws SQLException {

        FinPurchaseInvoiceForm param = new FinPurchaseInvoiceForm();
        param.setApID(apID);
        param.setOperatorID(logID);
        uncheckInvoice(conn, param);

    }

    /**
     * 采购发票记帐
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public int balanceInvoice(Connection conn, FinPurchaseInvoiceForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        int ret = 0;
        try {
            cstmt = conn.prepareCall("{call accounts.P_ApInvoiceInDS(?, ?, ?)}");
            cstmt.setInt(1, param.getApID());
            cstmt.setInt(2, param.getOperatorID());
            cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
            cstmt.execute();
            ret = cstmt.getInt(3);
            return ret;
        } catch (SQLException ex) {
            logger.error("query error: [" + param.getApID() + "]");
            throw ex;
        } finally {
            if (cstmt != null)
                try {
                    cstmt.close();
                } catch (SQLException ex) {

                }
        }
    }

    /**
     * 采购发票记帐
     * 
     * @param conn
     * @param apID
     * @param logID
     * @throws SQLException
     */
    public int balanceInvoice(Connection conn, int apID, int logID)
            throws SQLException {

        FinPurchaseInvoiceForm param = new FinPurchaseInvoiceForm();
        param.setApID(apID);
        param.setOperatorID(logID);
        return balanceInvoice(conn, param);

    }
    
    /**
     * 采购发票取消
     * 
     * @param conn
     * @param apID
     * @throws SQLException
     */
    public void cancelInvoice(Connection conn, int apID)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.P_CancelApInvoice(?)}");
            cstmt.setInt(1, apID);
            cstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + apID + "]");
            throw ex;
        } finally {
            if (cstmt != null)
                try {
                    cstmt.close();
                } catch (SQLException ex) {

                }
        }
    }
    
    /**
     * 判断会计期内生成的采购发票是否都已经记帐
     * @param conn
     * @param period
     * @return
     * @throws SQLException
     */
    public static boolean checkInvoiceStatusByPeriod(Connection conn, Period period) throws SQLException {
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		    
			String sql = "select d.* from fin_ps_mst a " 
			    + "inner join fin_ps_dtl b on a.ps_id = b.ps_id "
			    + "inner join fin_ap_dtl c on b.ps_dtl_id = c.ps_dtl_id "
			    + "inner join fin_ap_mst d on c.ap_id = d.ap_id  "
			    + "where a.purchasedate >= ? and a.purchasedate < ? + 1 "
			    + "and d.status <> 3  ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, period.getBeginDate());
			pstmt.setDate(2, period.getEndDate());
			rs = pstmt.executeQuery();
			if (rs.next()) {
			    return false;
			} else {
			    return true;
			}
		} catch (SQLException e) {
		    logger.error("[" + period.getBeginDate() + "], [" + period.getEndDate() + "]");
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {

				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {

				}
			}
		}
    }

}
