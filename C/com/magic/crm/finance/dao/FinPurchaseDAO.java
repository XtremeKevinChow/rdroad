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

import com.magic.crm.finance.form.FinPurchaseItemsForm;
import com.magic.crm.finance.form.FinPurchaseForm;
import com.magic.crm.finance.entity.FinPurchaseInvoiceItems;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(FinPurchaseDAO.class);

    /**
     * ��ѯĳ��������Ĳɹ�������
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findUncheckedPurchases(Connection con,
            FinPurchaseForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select a.*, b.pro_name, c.op_desc "
                    + "from fin_ps_mst a inner join providers b on a.pro_no = b.pro_no "
                    + "inner join FIN_OPERATION_MST c on a.operation_class = c.operation_class "
                    + "where a.status = '1' and a.purchasedate >= ? and a.purchasedate < ? + 1 order by a.res_no";
            pstmt = con.prepareStatement(sql);
            pstmt.setDate(1, param.getStartDate());
            pstmt.setDate(2, param.getEndDate());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinPurchaseForm data = new FinPurchaseForm();
                data.setPsID(rs.getInt("PS_ID"));
                data.setPsCode(rs.getString("PS_CODE"));
                data.setPurchaseDate(rs.getDate("PURCHASEDATE"));
                data.setResNO(rs.getString("RES_NO"));
                data.setOperationClass(rs.getString("OPERATION_CLASS"));
                data.setOperationClassName(rs.getString("OP_DESC"));
                data.setStockIOSign(rs.getString("STOCK_IOSIGN"));
                data.setOperator(rs.getString("OPERATIONER"));
                data.setProNO(rs.getString("PRO_NO"));
                data.setProName(rs.getString("PRO_NAME"));
                data.setPurType(rs.getString("PUR_TYPE"));
                data.setTax(rs.getDouble("TAX"));
                data.setCreator(rs.getString("CREATOR"));
                data.setCreateDate(rs.getDate("CREATEDATE"));
                data.setCheckPerson(rs.getString("CHECKPERSON"));
                data.setCheckDate(rs.getDate("CHECKDATE"));
                data.setStatus(rs.getString("STATUS"));
                data.setIsRed(rs.getString("IS_RED"));
                data.setIsTemp(rs.getString("IS_TEMP"));
                data.setStoNO(rs.getString("STO_NO"));
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
    
    public Collection searchPurchases(Connection con,
            FinPurchaseForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();
       
        try {

            sql = "select a.*, b.pro_name, c.op_desc ";
            sql+=" from fin_ps_mst a inner join providers b on a.pro_no = b.pro_no ";
            sql+=" inner join FIN_OPERATION_MST c on a.operation_class = c.operation_class";
            sql+=" where a.ps_id>0 ";
            if(param.getStartDate()!=null&&param.getEndDate()!=null){
            sql+=" and a.purchasedate >= date'"+param.getStartDate().toString().trim()+"'";
            sql+=" and a.purchasedate < date'"+param.getEndDate().toString().trim()+"'+1";
            }
            if(param.getPsCode().length()>0&&param.getPsCode()!=null){
                sql+=" and a.ps_code='"+param.getPsCode().trim()+"'";
            }
            if(param.getStatus().length()>0&&param.getStatus()!=null){
                sql+=" and a.status="+param.getStatus().trim();
            }
            if(param.getProNO().length()>0&&param.getProNO()!=null){
                sql+=" and a.pro_no='"+param.getProNO().trim()+"'";
            }            

            

            System.out.println(sql);
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinPurchaseForm data = new FinPurchaseForm();
                data.setPsID(rs.getInt("PS_ID"));
                data.setPsCode(rs.getString("PS_CODE"));
                data.setPurchaseDate(rs.getDate("PURCHASEDATE"));
                data.setResNO(rs.getString("RES_NO"));
                data.setOperationClass(rs.getString("OPERATION_CLASS"));
                data.setOperationClassName(rs.getString("OP_DESC"));
                data.setStockIOSign(rs.getString("STOCK_IOSIGN"));
                data.setOperator(rs.getString("OPERATIONER"));
                data.setProNO(rs.getString("PRO_NO"));
                data.setProName(rs.getString("PRO_NAME"));
                data.setPurType(rs.getString("PUR_TYPE"));
                data.setTax(rs.getDouble("TAX"));
                data.setCreator(rs.getString("CREATOR"));
                data.setCreateDate(rs.getDate("CREATEDATE"));
                data.setCheckPerson(rs.getString("CHECKPERSON"));
                data.setCheckDate(rs.getDate("CHECKDATE"));
                data.setStatus(rs.getString("STATUS"));
                data.setIsRed(rs.getString("IS_RED"));
                data.setIsTemp(rs.getString("IS_TEMP"));
                data.setStoNO(rs.getString("STO_NO"));
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
     * ��ѯ�ɹ�������
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    
    
    /**
     * ���ݹ�Ӧ�̻��Ʒ��ѯ����״̬Ϊ��2-��ˣ�3-���ֽ��� �ӱ�״̬Ϊ��1-δ���㣬2-���ֽ���ļ�¼ ����������Ʊʱ�ĵ���ҳ
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findCheckedPurchasesItems(Connection con,
            FinPurchaseForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select d.*, b.pro_no, b.pro_name, c.op_desc, e.item_code, e.name as item_name, "
                + "a.ps_code, purchasedate, res_no "
                + "from fin_ps_mst a inner join providers b on a.pro_no = b.pro_no "
                + "inner join fin_operation_mst c on a.operation_class = c.operation_class "
                + "inner join fin_ps_dtl d on a.ps_id = d.ps_id "
                + "inner join prd_items e on d.item_id = e.item_id "
                + "where a.status in ('2', '3') and d.status in('1', '2') ";
	        if (param.getProOrItem() == 1) { //��Ӧ������
	            sql += "and b.pro_name like '%" + param.getProOrItemCondition() + "%' ";
	        } else if (param.getProOrItem() == 2) {//��Ʒ����
	            sql += "and e.name like '%" + param.getProOrItemCondition() + "%' ";
	        } else if (param.getProOrItem() == 3) {//��Ӧ�̴���
	            sql += "and b.pro_no = '" + param.getProOrItemCondition() + "' ";
	        } else if (param.getProOrItem() == 4) {//��Ʒ����
	            sql += "and e.item_code = '" + param.getProOrItemCondition()+ "' ";
	        }
	        if (param.getIds() != null && !param.getIds().equals("")) {
	            sql += "and d.ps_dtl_id not in(" + param.getIds() + ")";
	        }
	        //sql += "order by item_name ";
	        if (param.getOrderByCondition1() != 0) {
	        	switch (param.getOrderByCondition1()) {
	        	case 1:
	        		sql += "order by purchasedate ";
	        		break;
	        	case 2:
	        		sql += "order by e.item_code ";
	        		break;
	        	case 3:
	        		sql += "order by e.name ";
	        		break;
	        	case 4:
	        		sql += "order by b.pro_no ";
	        		break;
	        	case 5:
	        		sql += "order by b.pro_name ";
	        		break;
	        	case 6:
	        		sql += "order by d.PUR_QTY ";
	        		break;
	        	case 7:
	        		sql += "order by d.PUR_PRICE ";
	        		break;
	        	case 8:
	        		sql += "order by d.USE_QTY ";
	        		break;
	        	case 9:
	        		sql += "order by d.FINISH_QTY ";
	        		break;
	        	case 10:
	        		sql += "order by a.ps_code ";
	        		break;
	        	case 11:
	        		sql += "order by res_no ";
	        		break;
	        	}
	        	if (param.getAscOrDesc1() == 0) {
	        		sql += "asc ";
	        	} else {
	        		sql += "desc ";
	        	}
	        }
	        if (param.getOrderByCondition2() != 0) {
	        	if (param.getOrderByCondition1() == 0) {
	        		switch (param.getOrderByCondition2()) {
		        	case 1:
		        		sql += "order by purchasedate ";
		        		break;
		        	case 2:
		        		sql += "order by e.item_code ";
		        		break;
		        	case 3:
		        		sql += "order by e.name ";
		        		break;
		        	case 4:
		        		sql += "order by b.pro_no ";
		        		break;
		        	case 5:
		        		sql += "order by b.pro_name ";
		        		break;
		        	case 6:
		        		sql += "order by d.PUR_QTY ";
		        		break;
		        	case 7:
		        		sql += "order by d.PUR_PRICE ";
		        		break;
		        	case 8:
		        		sql += "order by d.USE_QTY ";
		        		break;
		        	case 9:
		        		sql += "order by d.FINISH_QTY ";
		        		break;
		        	case 10:
		        		sql += "order by a.ps_code ";
		        		break;
		        	case 11:
		        		sql += "order by res_no ";
		        		break;
		        	}
		        	if (param.getAscOrDesc2() == 0) {
		        		sql += "asc ";
		        	} else {
		        		sql += "desc ";
		        	}
	        	} else {
	        		switch (param.getOrderByCondition2()) {
		        	case 1:
		        		sql += ",purchasedate ";
		        		break;
		        	case 2:
		        		sql += ",e.item_code ";
		        		break;
		        	case 3:
		        		sql += ",e.name ";
		        		break;
		        	case 4:
		        		sql += ",b.pro_no ";
		        		break;
		        	case 5:
		        		sql += ",b.pro_name ";
		        		break;
		        	case 6:
		        		sql += ",d.PUR_QTY ";
		        		break;
		        	case 7:
		        		sql += ",d.PUR_PRICE ";
		        		break;
		        	case 8:
		        		sql += ",d.USE_QTY ";
		        		break;
		        	case 9:
		        		sql += ",d.FINISH_QTY ";
		        		break;
		        	case 10:
		        		sql += ",a.ps_code ";
		        		break;
		        	case 11:
		        		sql += ",res_no ";
		        		break;
		        	}
		        	if (param.getAscOrDesc2() == 0) {
		        		sql += "asc ";
		        	} else {
		        		sql += "desc ";
		        	}
	        	}
	        	
	        }
	        if (param.getOrderByCondition3() != 0) {
	        	if (param.getOrderByCondition1() == 0 && param.getOrderByCondition2() == 0) {
	        		switch (param.getOrderByCondition3()) {
		        	case 1:
		        		sql += "order by purchasedate ";
		        		break;
		        	case 2:
		        		sql += "order by e.item_code ";
		        		break;
		        	case 3:
		        		sql += "order by e.name ";
		        		break;
		        	case 4:
		        		sql += "order by b.pro_no ";
		        		break;
		        	case 5:
		        		sql += "order by b.pro_name ";
		        		break;
		        	case 6:
		        		sql += "order by d.PUR_QTY ";
		        		break;
		        	case 7:
		        		sql += "order by d.PUR_PRICE ";
		        		break;
		        	case 8:
		        		sql += "order by d.USE_QTY ";
		        		break;
		        	case 9:
		        		sql += "order by d.FINISH_QTY ";
		        		break;
		        	case 10:
		        		sql += "order by a.ps_code ";
		        		break;
		        	case 11:
		        		sql += "order by res_no ";
		        		break;
		        	}
		        	if (param.getAscOrDesc3() == 0) {
		        		sql += "asc ";
		        	} else {
		        		sql += "desc ";
		        	}
	        	} else {
	        		switch (param.getOrderByCondition3()) {
		        	case 1:
		        		sql += ",purchasedate ";
		        		break;
		        	case 2:
		        		sql += ",e.item_code ";
		        		break;
		        	case 3:
		        		sql += ",e.name ";
		        		break;
		        	case 4:
		        		sql += ",b.pro_no ";
		        		break;
		        	case 5:
		        		sql += ",b.pro_name ";
		        		break;
		        	case 6:
		        		sql += ",d.PUR_QTY ";
		        		break;
		        	case 7:
		        		sql += ",d.PUR_PRICE ";
		        		break;
		        	case 8:
		        		sql += ",d.USE_QTY ";
		        		break;
		        	case 9:
		        		sql += ",d.FINISH_QTY ";
		        		break;
		        	case 10:
		        		sql += ",a.ps_code ";
		        		break;
		        	case 11:
		        		sql += ",res_no ";
		        		break;
		        	}
		        	if (param.getAscOrDesc3() == 0) {
		        		sql += "asc ";
		        	} else {
		        		sql += "desc ";
		        	}
	        	}
	        }
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinPurchaseItemsForm data = new FinPurchaseItemsForm();
                data.setPsDtlID(rs.getInt("PS_DTL_ID"));
                data.setPsID(rs.getInt("PS_ID"));
                data.setItemID(rs.getInt("ITEM_ID"));
                data.setItemCode(rs.getString("ITEM_CODE"));
                data.setItemName(rs.getString("ITEM_NAME"));
                data.setPurQty(rs.getDouble("PUR_QTY"));
                data.setPurPrice(rs.getDouble("PUR_PRICE"));
                data.setTaxPrice(rs.getDouble("TAX_PRICE"));
                data.setPurAmt(rs.getDouble("PUR_AMT"));
                data.setTaxAmt(rs.getDouble("TAX_AMT"));
                data.setAmt(rs.getDouble("AMT"));
                data.setTax(rs.getDouble("TAX"));
                data.setUseQty(rs.getDouble("USE_QTY"));
                data.setFinishQty(rs.getDouble("FINISH_QTY"));
                data.setFinishAmt(rs.getDouble("FINISH_AMT"));
                data.setReturnQty(rs.getDouble("RETURN_QTY"));
                data.setStatus(rs.getString("STATUS"));
                data.setProNO(rs.getString("PRO_NO"));
                data.setProName(rs.getString("PRO_NAME"));
                data.setResNO(rs.getString("RES_NO"));
                data.setPsCode(rs.getString("PS_CODE"));
                data.setPurchaseDate(rs.getDate("PURCHASEDATE"));
                coll.add(data);
            }
            System.out.println(sql);
        } catch (SQLException e) {
            logger
                    .error("query error: [" + param.getProOrItemCondition()
                            + "]");
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
     * ���ݷ�Ʊ��ϸ��һЩ������������ѯ��Ʊ��ϸ�� ���ڵ���ҳ�رշ�����ҳ��
     * 
     * @param con
     * @param PKs����ʽΪ�ö��ŷֿ����ַ�����ʽ���磺1001,1002,1003��
     * @return
     * @throws SQLException
     */
    public Collection findCheckedPurchasesItemsByPKs(Connection con, String PKs)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select d.*, b.pro_no, b.pro_name, c.op_desc, e.item_code, e.name as item_name "
                    + "from fin_ps_mst a inner join providers b on a.pro_no = b.pro_no "
                    + "inner join fin_operation_mst c on a.operation_class = c.operation_class "
                    + "inner join fin_ps_dtl d on a.ps_id = d.ps_id "
                    + "inner join prd_items e on d.item_id = e.item_id "
                    + "where a.status in ('2', '3') and d.status in('1', '2') "
                    + "and d.ps_dtl_id in (" + PKs + ")";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                FinPurchaseItemsForm data = new FinPurchaseItemsForm();
                data.setPsDtlID(rs.getInt("PS_DTL_ID"));
                data.setPsID(rs.getInt("PS_ID"));
                data.setItemID(rs.getInt("ITEM_ID"));
                data.setItemCode(rs.getString("ITEM_CODE"));
                data.setItemName(rs.getString("ITEM_NAME"));
                data.setPurQty(rs.getDouble("PUR_QTY"));
                data.setPurPrice(rs.getDouble("PUR_PRICE"));
                data.setTaxPrice(rs.getDouble("TAX_PRICE"));
                data.setPurAmt(rs.getDouble("PUR_AMT"));
                data.setTaxAmt(rs.getDouble("TAX_AMT"));
                data.setAmt(rs.getDouble("AMT"));
                data.setTax(rs.getDouble("TAX"));
                data.setUseQty(rs.getDouble("USE_QTY"));
                data.setFinishQty(rs.getDouble("FINISH_QTY"));
                data.setFinishAmt(rs.getDouble("FINISH_AMT"));
                data.setReturnQty(rs.getDouble("RETURN_QTY"));
                data.setStatus(rs.getString("STATUS"));
                data.setProNO(rs.getString("PRO_NO"));
                data.setProName(rs.getString("PRO_NAME"));
                coll.add(data);
            }

        } catch (SQLException e) {
            logger.error("query error: [" + PKs + "]");
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
     * ��������������Ӧ�ɹ�������
     * 
     * @param con
     * @param param
     * @return void
     * @throws SQLException
     */
    public void findPurchasesByPK(Connection con, FinPurchaseForm param)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        try {

            sql = "select a.*, b.pro_name, c.op_desc "
                    + "from fin_ps_mst a "
                    + "inner join providers b on a.pro_no = b.pro_no "
                    + "inner join fin_operation_mst c on a.operation_class = c.operation_class "
                    + "where ps_id = ? ";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getPsID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                param.setPsID(rs.getInt("PS_ID"));
                param.setPsCode(rs.getString("PS_CODE"));
                param.setPurchaseDate(rs.getDate("PURCHASEDATE"));
                param.setResNO(rs.getString("RES_NO"));
                param.setOperationClass(rs.getString("OPERATION_CLASS"));
                param.setOperationClassName(rs.getString("OP_DESC"));
                param.setStockIOSign(rs.getString("STOCK_IOSIGN"));
                param.setOperator(rs.getString("OPERATIONER"));
                param.setProNO(rs.getString("PRO_NO"));
                param.setProName(rs.getString("PRO_NAME"));
                param.setPurType(rs.getString("PUR_TYPE"));
                param.setTax(rs.getDouble("TAX"));
                param.setCreator(rs.getString("CREATOR"));
                param.setCreateDate(rs.getDate("CREATEDATE"));
                param.setCheckPerson(rs.getString("CHECKPERSON"));
                param.setCheckDate(rs.getDate("CHECKDATE"));
                param.setStatus(rs.getString("STATUS"));
                param.setIsRed(rs.getString("IS_RED"));
                param.setIsTemp(rs.getString("IS_TEMP"));
                param.setStoNO(rs.getString("STO_NO"));
                param.setRemark(rs.getString("REMARK"));

            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getPsID() + "]");
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
     * �����������Ҳɹ���������ϸ
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public FinPurchaseItemsForm findPurchasesItemsByPK(Connection con,
            int psDtlID) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        FinPurchaseItemsForm data = null;
        try {

            sql = "select a.*, b.item_code, b.name as item_name "
                    + "from fin_ps_dtl a inner join prd_items b on a.item_id = b.item_id "
                    + "where a.ps_dtl_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, psDtlID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                data = new FinPurchaseItemsForm();
                data.setPsDtlID(rs.getInt("PS_DTL_ID"));
                data.setPsID(rs.getInt("PS_ID"));
                data.setItemID(rs.getInt("ITEM_ID"));
                data.setItemCode(rs.getString("ITEM_CODE"));
                data.setItemName(rs.getString("ITEM_NAME"));
                data.setPurQty(rs.getDouble("PUR_QTY"));
                data.setPurPrice(rs.getDouble("PUR_PRICE"));
                data.setTaxPrice(rs.getDouble("TAX_PRICE"));
                data.setPurAmt(rs.getDouble("PUR_AMT"));
                data.setTaxAmt(rs.getDouble("TAX_AMT"));
                data.setAmt(rs.getDouble("AMT"));
                data.setTax(rs.getDouble("TAX"));
                data.setUseQty(rs.getDouble("USE_QTY"));
                data.setFinishQty(rs.getDouble("FINISH_QTY"));
                data.setFinishAmt(rs.getDouble("FINISH_AMT"));
                data.setReturnQty(rs.getDouble("RETURN_QTY"));
                data.setStatus(rs.getString("STATUS"));
            }

        } catch (SQLException e) {
            logger.error("query error: [" + psDtlID + "]");
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
     * �������������ID�����Ҳɹ���������ϸ
     * 
     * @param con
     * @param psID
     * @return
     * @throws SQLException
     */
    public Collection findPurchasesItemsByFK2(Connection con,
            int psID) throws SQLException {
        FinPurchaseForm param = new FinPurchaseForm();
        param.setPsID(psID);
        Collection coll = findPurchasesItemsByFK(con, param);
        return coll;
    }
    /**
     * �������������ID�����Ҳɹ���������ϸ
     * 
     * @param con
     * @param param
     * @return
     * @throws SQLException
     */
    public Collection findPurchasesItemsByFK(Connection con,
            FinPurchaseForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Collection coll = new ArrayList();

        try {

            sql = "select a.*, b.item_code, b.name as item_name "
                    + "from fin_ps_dtl a inner join prd_items b on a.item_id = b.item_id "
                    + "where a.ps_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, param.getPsID());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FinPurchaseItemsForm data = new FinPurchaseItemsForm();
                data.setPsDtlID(rs.getInt("PS_DTL_ID"));
                data.setPsID(rs.getInt("PS_ID"));
                data.setItemID(rs.getInt("ITEM_ID"));
                data.setItemCode(rs.getString("ITEM_CODE"));
                data.setItemName(rs.getString("ITEM_NAME"));
                data.setPurQty(rs.getDouble("PUR_QTY"));
                data.setPurPrice(rs.getDouble("PUR_PRICE"));
                data.setTaxPrice(rs.getDouble("TAX_PRICE"));
                data.setPurAmt(rs.getDouble("PUR_AMT"));
                data.setTaxAmt(rs.getDouble("TAX_AMT"));
                data.setAmt(rs.getDouble("AMT"));
                data.setTax(rs.getDouble("TAX"));
                data.setUseQty(rs.getDouble("USE_QTY"));
                data.setFinishQty(rs.getDouble("FINISH_QTY"));
                data.setFinishAmt(rs.getDouble("FINISH_AMT"));
                data.setReturnQty(rs.getDouble("RETURN_QTY"));
                data.setStatus(rs.getString("STATUS"));
                coll.add(data);
            }

        } catch (SQLException e) {
            logger.error("query error: [" + param.getPsID() + "]");
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
     * ��˲ɹ�������
     * 
     * @param conn
     * @param param
     * @throws SQLException
     */
    public void checkPurchase(Connection conn, FinPurchaseForm param)
            throws SQLException {
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.P_CheckPS(?, ?)}");
            cstmt.setInt(1, param.getPsID());
            cstmt.setInt(2, param.getOperatorID());
            cstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + param.getPsID() + "]");
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
     * ���²ɹ���������ϸ�е�δ��Ʊ����
     * 
     * @param conn
     * @param currentQty
     * @throws SQLException
     */
    public void updateItemsQtyByPK(Connection conn,
            FinPurchaseInvoiceItems invoiceItems) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            String sql = "update fin_ps_dtl set "
                + "use_qty = nvl(use_qty, 0) - ?  "
                + "where ps_dtl_id = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, invoiceItems.getQty());
            pstmt.setInt(2, invoiceItems.getPsDtlID());
            pstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + invoiceItems + "]");
            throw ex;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException ex) {

                }
        }
    }
    /**
     * �����ӱ���������ͽ�����
     * 
     * @param conn
     * @param currentQty
     * @throws SQLException
     */
    public void updateItemsQtyByPK(Connection conn,
            double qty, int psDtlID) throws SQLException {
        FinPurchaseInvoiceItems param = new FinPurchaseInvoiceItems();
        param.setQty(qty);
        param.setPsDtlID(psDtlID);
        updateItemsQtyByPK(conn, param);
    }
    /**
     * �����ӱ�״̬
     * @param conn
     * @param psDtlID
     * @param status
     * @throws SQLException
     */
    public void updateItemsStatusByPK(Connection conn,
            int psDtlID, String status) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "update fin_ps_dtl set status = ? where ps_dtl_id = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, psDtlID);
            pstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + psDtlID + "]");
            throw ex;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException ex) {

                }
        }
    }
    
    /**
     * ��������״̬
     * @param conn
     * @param psID
     * @param status
     * @throws SQLException
     */
    public void updateMasterStatus(Connection conn,
            int psID, String status) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "update fin_ps_mst set status = ? where ps_id = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, psID);
            pstmt.execute();
        } catch (SQLException ex) {
            logger.error("query error: [" + psID + "]");
            throw ex;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException ex) {

                }
        }
    }
   
}
