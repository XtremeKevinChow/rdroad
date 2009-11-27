/*
 * Created on 2006-7-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.dao;

import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import com.magic.crm.finance.form.FinSalesItemsForm;
import com.magic.crm.finance.form.FinSalesForm;
import com.magic.crm.finance.entity.FinSales;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.promotion.entity.Crush_Card_MST;

import java.text.*;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinSalesDAO {
    
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(FinSalesDAO.class);
    
    /**
     * 根据条件查询销售出库单
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findSalesByCondition(Connection con,
            FinSalesForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select a.*, b.cust_name, c.op_desc "
                    + "from fin_so_mst a inner join cust_mst b on a.custom_id = b.cust_no "
                    + "inner join FIN_OPERATION_MST c on a.operation_class = c.operation_class "
                    + "where 1 = 1 ";
            
            if (param.getStatus() != null && !"".equals(param.getStatus())) {
                sql += "and a.status = '" + param.getStatus() + "' ";
            }
            
            if (param.getSoNO() != null && !"".equals(param.getSoNO())) {
                sql += "and a.so_no = '" + param.getSoNO() + "' ";
            }
            
            if (param.getStartDate() != null && !"".equals(param.getStartDate())) {
                sql += "and a.so_date >= date'" + param.getStartDate() + "' ";
            }
            
            if (param.getEndDate() != null && !"".equals(param.getEndDate())) {
                sql += "and a.so_date < date'" + param.getEndDate() + "' + 1 ";
            }
            
            if (param.getCustomerID() != null && !param.getCustomerID().equals("")) {
                sql += "and a.custom_id = '" + param.getCustomerID() + "' ";
            }
            
            if (param.getCustomerName() != null && !param.getCustomerName().equals("")) {
                sql += "and b.cust_name like '%" + param.getCustomerName() + "%' ";
            }
            sql += " order by a.res_no";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinSalesForm data = new FinSalesForm();
                data.setSoID(rs.getInt("SO_ID"));
                data.setSoNO(rs.getString("SO_NO"));
                data.setSoDate(rs.getDate("SO_DATE"));
                data.setOperationClass(rs.getString("OPERATION_CLASS"));
                data.setOperationClassName(rs.getString("OP_DESC"));
                data.setCustomerID(rs.getString("CUSTOM_ID"));
                data.setCustomerName(rs.getString("CUST_NAME"));
                data.setSoType(rs.getString("SO_TYPE"));
                data.setTax(rs.getDouble("TAX"));
                data.setResNO(rs.getString("RES_NO"));
                data.setStockIOSign(rs.getString("STOCK_IOSIGN"));
                data.setStatus(rs.getString("STATUS"));
                data.setIsReturn(rs.getString("IS_RETURN"));
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
                data.setRemark(rs.getString("REMARK"));
                coll.add(data);
            }
            System.out.println(sql);
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
     * 根据主键查找相应销售出库单
     * 
     * @param con
     * @param param
     * @return void
     * @throws SQLException
     */
    public void findSalesByPK(Connection con, FinSalesForm param)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        try {

            sql = "select a.*, b.cust_name, c.op_desc "
                    + "from fin_so_mst a "
                    + "inner join cust_mst b on a.custom_id = b.cust_no "
                    + "inner join fin_operation_mst c on a.operation_class = c.operation_class "
                    + "where a.so_id = ? ";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getSoID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                param.setSoID(rs.getInt("SO_ID"));
                param.setSoNO(rs.getString("SO_NO"));
                param.setSoDate(rs.getDate("SO_DATE"));
                param.setOperationClass(rs.getString("OPERATION_CLASS"));
                param.setOperationClassName(rs.getString("OP_DESC"));
                param.setCustomerID(rs.getString("CUSTOM_ID"));
                param.setCustomerName(rs.getString("CUST_NAME"));
                param.setSoType(rs.getString("SO_TYPE"));
                param.setTax(rs.getDouble("TAX"));
                param.setResNO(rs.getString("RES_NO"));
                param.setStockIOSign(rs.getString("STOCK_IOSIGN"));
                param.setStatus(rs.getString("STATUS"));
                param.setIsReturn(rs.getString("IS_RETURN"));
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
                param.setRemark(rs.getString("REMARK"));
                param.setPackageAmt(rs.getDouble("package_amt"));// add by user 2008-05-21
                //System.out.println(sql+ param.getSoID());
            } else {
                param.reset();
            }
        } catch (SQLException e) {
            logger.error("query error: [" + param.getSoID() + "]");
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
     * 根据单号查找相应ID
     * 
     * @param con
     * @param param
     * @return int
     * @throws SQLException
     */
    public int findSoIdBySoNO(Connection con, FinSalesForm param)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        try {

            sql = "select so_id from fin_so_mst where so_no = ? ";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, param.getSoNO());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("SO_ID");
                
            } else {
                return 0;
            }
            
        } catch (SQLException e) {
            logger.error("query error: [" + param.getSoNO() + "]");
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
     * 根据外键（主表ID）查找销售出库单明细
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findSalesItemsByFK(Connection con,
            FinSalesForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select a.*, b.item_code, b.name as item_name "
                    + "from fin_so_dtl a inner join prd_items b on a.item_id = b.item_id "
                    + "where a.so_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getSoID());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinSalesItemsForm data = new FinSalesItemsForm();
                data.setSoDtlID(rs.getInt("SO_DTL_ID"));
                data.setSoID(rs.getInt("SO_ID"));
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
                data.setFinishQty(rs.getDouble("FINISH_QTY"));
                data.setFinishAmt(rs.getDouble("FINISH_AMT"));
                data.setRemark(rs.getString("REMARK"));
                coll.add(data);
            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getSoID() + "]");
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
     * 审核销售出库单
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void checkSales(Connection conn, FinSalesForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.P_ArOrderCheck(?, ?)}");
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
     * 弃审销售出库单
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void uncheckSales(Connection conn, FinSalesForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.P_ArOrderUnCheck(?, ?)}");
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
    /*
     * 成本结算产品类型报表
     */
    public Collection fin_stock_detail_list(Connection con, String startDate,String endDate)
	throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection finCol = new ArrayList();
		
		try {
		
				String sQuery=" SELECT d.ID,b.OPERATION_CLASS,SUM(a.OP_QTY) as OP_QTY,SUM(a.fact_AMT) as fact_AMT"; 
				sQuery+=" FROM FIN_STOCK_DETAIL a ";
				sQuery+=" INNER JOIN FIN_OPERATION_MST b ON a.OPERATION_CLASS=b.OPERATION_CLASS ";
				sQuery+=" INNER JOIN PRD_ITEMS c ON  a.ITEM_ID=c.ITEM_ID ";
				sQuery+=" INNER JOIN S_ITEM_TYPE d ON c.ITEM_TYPE=d.ID ";
				sQuery+=" WHERE DOC_TYPE <>'03' AND  OPERATION_DATE >=DATE '"+startDate+"' AND OPERATION_DATE <DATE '"+endDate+"'+1";
		
				sQuery+=" GROUP BY d.ID,b.OPERATION_CLASS";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
		//System.out.println(sQuery);
			while (rs.next()) {
				FinSales info = new FinSales();
		        info.setItem_type(rs.getString("id"));
		        info.setOp_class(rs.getString("OPERATION_CLASS"));
		        info.setOp_qty(rs.getInt("OP_QTY"));
		        info.setPre_amt(rs.getDouble("fact_AMT"));
		
		
		        finCol.add(info);
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
		
		return finCol;
		}
    
    public Collection fin_stock_item_list(Connection con, String item_type,String startDate,String endDate)
	throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection finCol = new ArrayList();
		
		try {
			
					String sQuery=" SELECT c.item_code,b.OPERATION_CLASS,SUM(a.OP_QTY) as OP_QTY,SUM(a.fact_AMT) as fact_AMT"; 
					sQuery+=" FROM FIN_STOCK_DETAIL a ";
					sQuery+=" INNER JOIN FIN_OPERATION_MST b ON a.OPERATION_CLASS=b.OPERATION_CLASS ";
					sQuery+=" INNER JOIN PRD_ITEMS c ON  a.ITEM_ID=c.ITEM_ID ";
					sQuery+=" WHERE DOC_TYPE <>'03' AND  OPERATION_DATE >=DATE '"+startDate+"' AND OPERATION_DATE <DATE '"+endDate+"'+1";
					sQuery+=" and c.item_type="+item_type;
					sQuery+=" GROUP BY c.item_code,b.OPERATION_CLASS";
				pstmt = con.prepareStatement(sQuery);
				rs = pstmt.executeQuery();
			//System.out.println(sQuery);
				while (rs.next()) {
					FinSales info = new FinSales();
			        info.setItem_type(rs.getString("item_code"));
			        info.setOp_class(rs.getString("OPERATION_CLASS"));
			        info.setOp_qty(rs.getInt("OP_QTY"));
			        info.setPre_amt(rs.getDouble("fact_AMT"));
			
			
			        finCol.add(info);
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
			
			return finCol;			
		}  
    public Collection fin_stock_item_list_qty(Connection con, String item_type,String startDate,String endDate)
	throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection finCol = new ArrayList();
		
		try {
			
					String sQuery=" SELECT c.item_code,b.OPERATION_CLASS,SUM(a.OP_QTY) as OP_QTY,SUM(a.fact_AMT) as fact_AMT"; 
					sQuery+=" FROM FIN_STOCK_DETAIL a ";
					sQuery+=" INNER JOIN FIN_OPERATION_MST b ON a.OPERATION_CLASS=b.OPERATION_CLASS ";
					sQuery+=" INNER JOIN PRD_ITEMS c ON  a.ITEM_ID=c.ITEM_ID ";
					sQuery+=" WHERE DOC_TYPE <>'03' AND  OPERATION_DATE >=DATE '"+startDate+"' AND OPERATION_DATE <DATE '"+endDate+"'+1";
					sQuery+=" and c.item_type="+item_type;
					sQuery+=" GROUP BY c.item_code,b.OPERATION_CLASS having (SUM(a.OP_QTY)+SUM(a.fact_AMT))>0";
				pstmt = con.prepareStatement(sQuery);
				rs = pstmt.executeQuery();
			System.out.println(sQuery);
				while (rs.next()) {
					FinSales info = new FinSales();
			        info.setItem_type(rs.getString("item_code"));
			        info.setOp_class(rs.getString("OPERATION_CLASS"));
			        info.setOp_qty(rs.getInt("OP_QTY"));
			        info.setPre_amt(rs.getDouble("fact_AMT"));
			
			
			        finCol.add(info);
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
			
			return finCol;			
		}      
    /*
     * 销售成本明细汇总（产品名称）分页
     * 
     */
    public static ArrayList fin_stock_page(Connection con, String sql,int from, int to)
	throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList finCol = new ArrayList();
			
			ArrayList ret = new ArrayList();
			String sql1 = " SELECT * FROM ( SELECT t.*, rownum rownum_ FROM ( "
					+ sql + " )t WHERE rownum <= ?) B WHERE rownum_ >? ";
			//System.out.println(sql1);
			PreparedStatement ps = con.prepareStatement(sql1);
			ps.setInt(1, to);
			ps.setInt(2, from);
			rs = ps.executeQuery();
			while (rs.next()) {
				FinSales info = new FinSales();
			    info.setItem_name(rs.getString("name"));
			    info.setItem_code(rs.getString("item_code"));
			
				ret.add(info);
			}
			rs.close();
			ps.close();
			return ret;

    	}   
		public static int queryListCount(Connection conn, String sql)
			throws Exception {
			int ret = 0;
			String sql1 = " SELECT COUNT(distinct c.item_code ) "
					+ sql.substring(sql.toUpperCase().indexOf(" FROM "));
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			if (rs.next()) {
				ret = rs.getInt(1);
			}
			rs.close();
			st.close();
			return ret;
		}    
	public  int getFin_qty(Collection c,String item_type,String op_class){

		FinSales info=new FinSales();
		int qty=0;
		   java.util.Iterator it=c.iterator();
			
		      while(it.hasNext()){
			   info=(FinSales)it.next();
	
			   if(info.getItem_type().equals(item_type)&&info.getOp_class().equals(op_class)){
			   	qty=info.getOp_qty();
			   }
		      }  		
		return qty;
	}
	public  double getFin_amt(Collection c,String item_type,String op_class){
		DecimalFormat myformat = new DecimalFormat("###,###.00");
		FinSales info=new FinSales();
		double amt=0;
		   java.util.Iterator it=c.iterator();
			
		      while(it.hasNext()){
			   info=(FinSales)it.next();
	
			   if(info.getItem_type().equals(item_type)&&info.getOp_class().equals(op_class)){
			   	amt=info.getPre_amt();
			   }
		      }  		
		return amt;
	}
	/*
     * 手工调整库存
     */
    public static int stockbyhand(Connection conn, String item_id,String operateDate,String qty,String uid
            ) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
   
        CallableStatement cstmt = null;
        String sp = null;
        int re = 0;
        try {
 
        	sp = "{?=call accounts.F_AdjustFinStock(?,?,?,?,?)}";
			cstmt = conn.prepareCall(sp);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setString(2,item_id);
			cstmt.setString(3,operateDate);
			cstmt.setString(4,qty);
			cstmt.setString(5,"70");
			cstmt.setString(6,uid);

	
			cstmt.execute();
			re = cstmt.getInt(1);
			cstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
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
      return re;
    }	
}
