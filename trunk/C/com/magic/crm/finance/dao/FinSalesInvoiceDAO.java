/*
 * Created on 2006-7-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.dao;

import org.apache.log4j.Logger;

import com.magic.crm.finance.entity.Period;
import com.magic.crm.finance.form.FinSalesInvoiceItemsForm;
import com.magic.crm.finance.form.FinSalesInvoiceForm;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FinSalesInvoiceDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(FinSalesInvoiceDAO.class);

    /**
     * 根据销售出库单生成销售发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void newArInvoice(Connection conn, FinSalesInvoiceForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.P_NewArInvoice(?, ?)}");
            cstmt.setInt(1, param.getSoID());
            cstmt.setInt(2, param.getOperatorID());
            cstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + param.getSoID() + "]");
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
     * 根据销售出库单生成销售发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void newArInvoice(Connection conn, int arID, int logID)
            throws SQLException {
        FinSalesInvoiceForm param = new FinSalesInvoiceForm();
        param.setSoID(arID);
        param.setOperatorID(logID);
        newArInvoice(conn, param);
    }

    /**
     * 根据客户查询销售发票
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findInvoicesByCondition(Connection con,
            FinSalesInvoiceForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select a.*, b.cust_name, c.op_desc, d.so_no "
                    + "from fin_ar_mst a "
                    + "inner join fin_so_mst d on a.so_id = d.so_id "
                    + "inner join cust_mst b on a.custom_id = b.cust_no "
                    + "inner join fin_operation_mst c on a.operation_class = c.operation_class "
                    + "where 1 = 1 ";

            
            
            if (param.getStatus() != null && !"".equals(param.getStatus())) {
                sql += " and a.status = '" + param.getStatus() + "'";
            }
            
            if (param.getSoNO() != null && !"".equals(param.getSoNO())) {
                sql += " and d.so_no = '" + param.getSoNO() + "'";
            }
            
            if (param.getArNO() != null && !"".equals(param.getArNO())) {
                sql += " and a.ar_no = '" + param.getArNO() + "'";
            }
            
            if (param.getCustomerID() != null
                    && !"".equals(param.getCustomerID())) {
                sql += " and a.custom_id = '" + param.getCustomerID() + "'";

            }

            if (param.getCustomerName() != null
                    && !"".equals(param.getCustomerName())) {
                sql += " and b.cust_name like '%" + param.getCustomerName() + "%'";
            }

            if (param.getStartDate() != null
                    && !"".equals(param.getStartDate())) {
                sql += " and a.so_date >= date'" + param.getStartDate()
                        + "'";
            }

            if (param.getEndDate() != null && !"".equals(param.getEndDate())) {
                sql += " and a.so_date < date'" + param.getEndDate()
                        + "' + 1";
            }

            pstmt = con.prepareStatement(sql);
            System.out.println(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinSalesInvoiceForm data = new FinSalesInvoiceForm();
                data.setArID(rs.getInt("AR_ID"));
                data.setArNO(rs.getString("AR_NO"));
                data.setSoID(rs.getInt("SO_ID"));
                data.setSoNO(rs.getString("SO_NO"));
                data.setSoDate(rs.getDate("SO_DATE"));
                data.setOperationClass(rs.getString("OPERATION_CLASS"));
                data.setOperationClassName(rs.getString("OP_DESC"));
                data.setOperator(rs.getString("OPERATOR"));
                data.setCustomerID(rs.getString("CUSTOM_ID"));
                data.setCustomerName(rs.getString("CUST_NAME"));
                data.setSoType(rs.getString("SO_TYPE"));
                data.setTax(rs.getDouble("TAX"));
                data.setStatus(rs.getString("STATUS"));
                data.setStoIOSign(rs.getString("STOCK_IOSIGN"));
                data.setSoAmt(rs.getDouble("SO_AMT"));
                data.setArAmt(rs.getDouble("AR_AMT"));
                data.setGiftAmt(rs.getDouble("GIFT_AMT"));
                data.setCardAmt(rs.getDouble("CARD_AMT"));
                data.setDeliverAmt(rs.getDouble("DELIVER_AMT"));
                data.setPayedAmt(rs.getDouble("PAYED_AMT"));
                data.setCreator(rs.getString("CREATOR"));
                data.setCreateDate(rs.getDate("CREATEDATE"));
                data.setCheckPerson(rs.getString("CHECKPERSON"));
                data.setCheckDate(rs.getDate("CHECKDATE"));
                data.setTallier(rs.getString("TALLIER"));
                data.setTallyDate(rs.getDate("TALLY_DATE"));
                data.setIsFirst(rs.getString("IS_FIRST"));
                data.setRemark(rs.getString("REMARK"));
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
     * 根据主键查找相应销售发票
     * 
     * @param con
     * @param param
     * @return void
     * @throws SQLException
     */
    public void findInvoicesByPK(Connection con, FinSalesInvoiceForm param)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        try {

            sql = "select a.*, b.cust_name, c.op_desc, d.res_no " 
                	+ "from fin_ar_mst a "
                	+ "inner join fin_so_mst d on a.so_id = d.so_id "
                    + "inner join cust_mst b on a.custom_id = b.cust_no "
                    + "inner join fin_operation_mst c on a.operation_class = c.operation_class "
                    + "where ar_id = ? ";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getArID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                param.setArID(rs.getInt("AR_ID"));
                param.setArNO(rs.getString("AR_NO"));
                param.setSoID(rs.getInt("SO_ID"));
                param.setSoDate(rs.getDate("SO_DATE"));
                param.setOperationClass(rs.getString("OPERATION_CLASS"));
                param.setOperationClassName(rs.getString("OP_DESC"));
                param.setOperator(rs.getString("OPERATOR"));
                param.setCustomerID(rs.getString("CUSTOM_ID"));
                param.setCustomerName(rs.getString("CUST_NAME"));
                param.setSoType(rs.getString("SO_TYPE"));
                param.setTax(rs.getDouble("TAX"));
                param.setStatus(rs.getString("STATUS"));
                param.setStoIOSign(rs.getString("STOCK_IOSIGN"));
                param.setSoAmt(rs.getDouble("SO_AMT"));
                param.setArAmt(rs.getDouble("AR_AMT"));
                param.setGiftAmt(rs.getDouble("GIFT_AMT"));
                param.setCardAmt(rs.getDouble("CARD_AMT"));
                param.setDeliverAmt(rs.getDouble("DELIVER_AMT"));
                param.setPayedAmt(rs.getDouble("PAYED_AMT"));
                param.setCreator(rs.getString("CREATOR"));
                param.setCreateDate(rs.getDate("CREATEDATE"));
                param.setCheckPerson(rs.getString("CHECKPERSON"));
                param.setCheckDate(rs.getDate("CHECKDATE"));
                param.setTallier(rs.getString("TALLIER"));
                param.setTallyDate(rs.getDate("TALLY_DATE"));
                param.setIsFirst(rs.getString("IS_FIRST"));
                param.setRemark(rs.getString("REMARK"));
                param.setResNO(rs.getString("RES_NO"));
                param.setPackageAmt(rs.getDouble("package_amt"));
            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getArID() + "]");
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
     * 根据主键查找相应销售发票的礼券抵用明细总额
     * 
     * @param con
     * @param param
     * @return double
     * @throws SQLException
     */
    public double sumArGiftFeeByPK(Connection con, FinSalesInvoiceForm param)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        double gifAmt = 0d;
        try {
        	sql =
        		"select " 
        		+ "SUM(dis_amt) AS dis_amt "
        		+ "from "
        		+ "( "
        		+ "    select " 
        		+ "    SUM(c.dis_amt) AS dis_amt "
        		+ "    from magic.fin_ar_mst " 
        		+ "    INNER JOIN magic.fin_so_mst ON "
        		+ "    magic.fin_ar_mst.so_id=magic.fin_so_mst.so_id "
        		+ "    inner join jxc.sto_connect_dtl a on "
        		+ "    a.connect_no=magic.fin_so_mst.res_no "
        		+ "    inner join ship_mst b on b.id=a.id "
        		+ "    INNER JOIN magic.shippingnotice_gifts c ON a.id=c.sn_id "
        		+ "    where  magic.fin_ar_mst.ar_id = ? and "
        		+ "	   magic.fin_ar_mst.operation_class='11' "
        		+ "    and b.package_category=0 "
        		+ "    UNION ALL "
        		+ "    SELECT " 
        		+ "    SUM(nvl(d.dis_amt,0)) AS dis_amt "
        		+ "    from " 
        		+ "    magic.fin_ar_mst " 
        		+ "    INNER JOIN magic.fin_so_mst ON "
        		+ "    magic.fin_ar_mst.so_id=magic.fin_so_mst.so_id "
        		+ "    inner join jxc.sto_connect_dtl a on "
        		+ "    a.connect_no=magic.fin_so_mst.res_no "
        		+ "    inner join magic.ord_ship_sets c on a.id=c.parent_ship_id "
        		+ "    inner join ship_mst b on b.id=c.child_ship_id "
        		+ "    INNER JOIN magic.shippingnotice_gifts d ON b.id=d.sn_id "
        		+ "    where  magic.fin_ar_mst.ar_id = ? and "
        		+ "    magic.fin_ar_mst.operation_class='11' "
        		+ "    and b.package_category=-1 "
        		+ ") ";
        	/*
            sql = "select sum(nvl(e.dis_amt, 0)) as gift_amt " 
                	+ "from fin_ar_mst a "
                	+ "inner join fin_so_mst d on a.so_id = d.so_id "
                    + "inner join jxc.sto_connect_dtl b on b.connect_no = d.res_no "
                    + "inner join shippingnotice_gifts e on e.sn_id = to_number(b.id) "
                    + "where ar_id = ? ";*/
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getArID());
            pstmt.setInt(2, param.getArID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
            	gifAmt = rs.getDouble(1);
            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getArID() + "]");
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
        return gifAmt;
    }

    
    /**
     * 根据外键（主表主键）查找销售发票明细
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findInvoicesItemsByFK(Connection con,
            FinSalesInvoiceForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select a.*, b.item_code, b.name as item_name "
                    + "from fin_ar_dtl a "
                    + "inner join prd_items b on a.item_id = b.item_id "
                    + "where a.ar_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getArID());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinSalesInvoiceItemsForm data = new FinSalesInvoiceItemsForm();
                data.setArDtlID(rs.getInt("AR_DTL_ID"));
                data.setArID(rs.getInt("AR_ID"));
                data.setStoNO(rs.getString("STO_NO"));
                data.setItemID(rs.getInt("ITEM_ID"));
                data.setItemCode(rs.getString("ITEM_CODE"));
                data.setItemName(rs.getString("ITEM_NAME"));
                data.setSoQty(rs.getDouble("SO_QTY"));
                data.setSellType(rs.getString("SELL_TYPE"));
                data.setSoPrice(rs.getDouble("SO_PRICE"));
                data.setTax(rs.getDouble("TAX"));
                data.setAmt(rs.getDouble("AMT"));
                data.setTaxAmt(rs.getDouble("TAX_AMT"));
                data.setTotalAmt(rs.getDouble("TOTAL_AMT"));
                data.setRemark(rs.getString("REMARK"));
                coll.add(data);
            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getArID() + "]");
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
     * 弃审销售发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void uncheckInvoice(Connection conn, FinSalesInvoiceForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn
                    .prepareCall("{call accounts.P_ArInvoiceUNCheck(?, ?)}");
            cstmt.setInt(1, param.getArID());
            cstmt.setInt(2, param.getOperatorID());
            cstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + param.getArID() + "]");
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
     * 弃审销售发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void uncheckInvoice(Connection conn, int arID, int logID)
            throws SQLException {
        FinSalesInvoiceForm param = new FinSalesInvoiceForm();
        param.setArID(arID);
        param.setOperatorID(logID);
        uncheckInvoice(conn, param);
    }

    /**
     * 审核销售发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void checkInvoice(Connection conn, FinSalesInvoiceForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.P_ArInvoiceCheck(?, ?)}");
            cstmt.setInt(1, param.getArID());
            cstmt.setInt(2, param.getOperatorID());
            cstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + param.getArID() + "]");
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
     * 审核销售发票
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void checkInvoice(Connection conn, int arID, int logID)
            throws SQLException {
        FinSalesInvoiceForm param = new FinSalesInvoiceForm();
        param.setArID(arID);
        param.setOperatorID(logID);
        checkInvoice(conn, param);
    }

    /**
     * 销售发票记帐
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void balanceInvoice(Connection conn, FinSalesInvoiceForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.P_ArInovoiceInDS(?, ?)}");
            cstmt.setInt(1, param.getArID());
            cstmt.setInt(2, param.getOperatorID());
            cstmt.execute();
            System.out.println(param.getArID());
        } catch (SQLException ex) {
            logger.error("query error: [" + param.getArID() + "]");
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
     * 销售发票记帐
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void balanceInvoice(Connection conn, int arID, int logID)
            throws SQLException {
        FinSalesInvoiceForm param = new FinSalesInvoiceForm();
        param.setArID(arID);
        param.setOperatorID(logID);
        balanceInvoice(conn, param);
    }
    
    /**
     * 判断会计期内生成的销售发票是否都已经记帐
     * @param conn
     * @param period
     * @return
     * @throws SQLException
     */
    public static boolean checkInvoiceStatusByPeriod(Connection conn, Period period) throws SQLException {
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		    
			String sql = "select b.* from fin_so_mst a " 
			    + "inner join fin_ar_mst b on a.so_id = b.so_id "
			    + "where a.so_date >= ? and a.so_date < ? + 1 "
			    + "and b.status <> 3";
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
