/*
 * Created on 2005-5-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.shippingnotice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.shippingnotice.entity.SnGifts;
import com.magic.crm.shippingnotice.entity.ShippingNoticeDtl;
import com.magic.crm.shippingnotice.entity.ShippingNoticeMst;
import com.magic.crm.shippingnotice.form.ShippingNoticeForm;

/**
 * @author Administrator
 * 
 * TODO 99read
 */
public class ShippingNoticeDAO {

    public static Collection list(Connection conn, ShippingNoticeForm form)
            throws Exception {
        Collection ret = new ArrayList();
        String sql = "select a.id,a.ref_order_id,a.member_id,a.create_date,a.barcode,a.order_number,a.lot," +
        		" a.status,a.print_date,a.contact,a.postcode,a.check_date,a.check_person, "
                + " b.card_id,b.name as member_name,c.name as status_name,d.name as delivery_type_name  "
                + " from ord_shippingnotices a, mbr_members b, s_shippingnotice_status c, s_delivery_type d "
                + " where a.member_id = b.id and a.status = c.id and a.delivery_type = d.id  ";
        if (!form.getStrQrySnCode().trim().equals("")) {
            sql += " and a.barcode = '" + form.getStrQrySnCode().trim() + "'";
        }
        if (!form.getStrQryShippingNumber().trim().equals("")) {
            sql += " and a.shipping_number = '" + form.getStrQryShippingNumber().trim() + "'";
        }
        if (!form.getStrQryOrdCode().trim().equals("")) {
            sql += " and a.order_number = '" + form.getStrQryOrdCode().trim()
                    + "'";
        }
        if (!form.getStrQryMbrCode().trim().equals("")) {
            sql += " and b.card_id = '" + form.getStrQryMbrCode().trim() + "'";
        }
        if (!form.getStrQryMbrName().trim().equals("")) {
            sql += " and b.name = '" + form.getStrQryMbrName().trim() + "'";
        }
        if (!form.getStrQryTelephone().trim().equals("")) {
            sql += " and b.telephone = '" + form.getStrQryMbrName().trim() + "'";
        }
        sql += " order by a.create_date desc ";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
       
        while (rs.next()) {

            ShippingNoticeMst mst = new ShippingNoticeMst();
            mst.setSn_id(rs.getLong("id"));
            mst.setSn_code(rs.getString("barcode"));
            //mst.setRef_order(rs.getLong("ref_order_id"));
           // mst.setOrder_number(rs.getString("order_number"));
            //mst.setMb_id(rs.getLong("member_id"));
            //mst.setMb_code(rs.getString("card_id"));
            //mst.setContactor(rs.getString("contact"));
            //mst.setPostcode(rs.getString("postcode"));
            //mst.setDelivery_type_name(rs.getString("delivery_type_name"));
            mst.getOrder().setOrderId(rs.getInt("ref_order_id"));
            mst.getOrder().setOrderNumber(rs.getString("order_number"));
            mst.getOrder().getMember().setID(rs.getInt("member_id"));
            mst.getOrder().getMember().setCARD_ID(rs.getString("card_id"));
            mst.getOrder().getDeliveryInfo().setReceiptor(rs.getString("contact"));
            mst.getOrder().getDeliveryInfo().setPostCode(rs.getString("postcode"));
            mst.getOrder().getDeliveryInfo().setDeliveryType(rs.getString("delivery_type_name"));
            
            mst.setLot(rs.getString("lot"));
            mst.setCreate_date(rs.getString("create_date"));
            mst.setPrint_date(rs.getString("print_date"));
            mst.setStatus(rs.getInt("status"));
            mst.setStatus_name(rs.getString("status_name"));
            mst.setCheckDate(rs.getString("check_date"));
            mst.setCheckPersonName(rs.getString("check_person"));
            ret.add(mst);
        }
        rs.close();
        st.close();

        return ret;
    }

    /**
     * 得到发货单的主要信息
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception
     */
    public static ShippingNoticeMst getShippingNoticeByPK(Connection conn,
            long id) throws Exception {

        ShippingNoticeMst mst = new ShippingNoticeMst();
        String sql = " select a.*,b.card_id,b.name as mb_name,c.name as delivery_type_name,d.name as status_name,e.LC_SHORT_NAME "
                + " from ord_shippingnotices a, mbr_members b,s_delivery_type c,s_shippingnotice_status d,bas_lc_comp e "
                + " where a.member_id = b.id and a.delivery_type = c.id and a.status = d.id and a.LOGISTIC_ID = e.LC_ID(+) "
                + " and a.id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                mst.setSn_id(rs.getLong("id"));
                mst.setSn_code(rs.getString("barcode"));
                //mst.setRef_order(rs.getLong("ref_order_id"));
                //mst.setOrder_number(rs.getString("order_number"));
                //mst.setMb_id(rs.getLong("member_id"));
                //mst.setMb_code(rs.getString("card_id"));
                //mst.setMb_name(rs.getString("mb_name"));
                //mst.setContactor(rs.getString("contact"));
                //mst.setPostcode(rs.getString("postcode"));
                //mst.setDelivery_type_name(rs.getString("delivery_type_name"));
                //mst.setAddress(rs.getString("address"));
                //mst.setTelephone(rs.getString("phone"));
                //mst.setDelivery_fee(rs.getDouble("delivery_fee"));
                mst.getOrder().setOrderId(rs.getInt("ref_order_id"));
                mst.getOrder().setOrderNumber(rs.getString("order_number"));
                mst.getOrder().getMember().setID(rs.getInt("member_id"));
                mst.getOrder().getMember().setCARD_ID(rs.getString("card_id"));
                mst.getOrder().getMember().setNAME(rs.getString("mb_name"));
                mst.getOrder().getDeliveryInfo().setReceiptor(rs.getString("contact"));
                mst.getOrder().getDeliveryInfo().setPostCode(rs.getString("postcode"));
                mst.getOrder().getDeliveryInfo().setDeliveryType(rs.getString("delivery_type_name"));
                mst.getOrder().getDeliveryInfo().setAddress(rs.getString("address"));
                mst.getOrder().getDeliveryInfo().setPhone(rs.getString("phone")+" "+rs.getString("phone1"));
                mst.getOrder().getDeliveryInfo().setDeliveryFee(rs.getDouble("delivery_fee"));
                
                mst.setLot(rs.getString("lot"));
                mst.setCreate_date(rs.getString("create_date"));
                mst.setPrint_date(rs.getString("print_date"));
                mst.setStatus(rs.getInt("status"));
                mst.setStatus_name(rs.getString("status_name"));
                //mst.setGift_number(rs.getString("gift_number"));
                mst.setAppend_fee(rs.getDouble("append_fee"));
                mst.setGoods_fee(rs.getDouble("goods_fee"));
                mst.setPackageFee(rs.getDouble("package_fee"));
                mst.setDiscount_fee(rs.getDouble("discount_fee"));
                
                mst.setPayed_money(rs.getDouble("payed_money"));
                mst.setPayed_emoney(rs.getDouble("payed_emoney"));
                mst.setShipping_sum(rs.getDouble("shipping_sum"));
                mst.setDeliveryDate(rs.getString("delivery_date"));
                mst.setCheckDate(rs.getString("check_date"));
                mst.setComments(rs.getString("comments"));
                mst.setRemark(rs.getString("remark"));
                mst.setPackageCategory(rs.getInt("package_category"));
                
                mst.setInvoice_number(rs.getString("invoice_number"));
                mst.setInvoice_title(rs.getString("invoice_title"));
                mst.setShipping_number(rs.getString("SHIPPING_NUMBER"));
                mst.setLogistic_name(rs.getString("LC_SHORT_NAME"));
                
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (Exception e) {
                }
        }
        return mst;
    }

    /**
     * 根据订单号得到发货单的主要信息
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception
     * @author kevin
     * @Date 2006-08-16
     */
    /**
    public static ShippingNoticeMst getShippingNoticeByOrderNumber(
            Connection conn, String orderNumber) throws Exception {

        ShippingNoticeMst mst = new ShippingNoticeMst();
        String sql = " select a.*,b.card_id,b.name as mb_name,c.name as delivery_type_name,d.name as status_name "
                + " from ord_shippingnotices a, mbr_members b,s_delivery_type c,s_shippingnotice_status d "
                + " where a.member_id = b.id and a.delivery_type = c.id and a.status = d.id "
                + " and a.order_number = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, orderNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                mst.setSn_id(rs.getLong("id"));
                mst.setSn_code(rs.getString("barcode"));
                mst.setRef_order(rs.getLong("ref_order_id"));
                mst.setOrder_number(rs.getString("order_number"));
                mst.setMb_id(rs.getLong("member_id"));
                mst.setMb_code(rs.getString("card_id"));
                mst.setMb_name(rs.getString("mb_name"));
                mst.setContactor(rs.getString("contact"));
                mst.setPostcode(rs.getString("postcode"));
                mst.setDelivery_type_name(rs.getString("delivery_type_name"));
                mst.setLot(rs.getString("lot"));
                mst.setCreate_date(rs.getString("create_date"));
                mst.setPrint_date(rs.getString("print_date"));
                mst.setStatus(rs.getInt("status"));
                mst.setStatus_name(rs.getString("status_name"));
                //mst.setGift_number(rs.getString("gift_number"));
                mst.setAppend_fee(rs.getDouble("append_fee"));
                mst.setGoods_fee(rs.getDouble("goods_fee"));
                mst.setDelivery_fee(rs.getDouble("delivery_fee"));
                mst.setAddress(rs.getString("address"));
                mst.setTelephone(rs.getString("phone"));
                mst.setPayed_money(rs.getDouble("payed_money"));
                mst.setShipping_sum(rs.getDouble("shipping_sum"));
                mst.setDeliveryDate(rs.getString("delivery_date"));
                mst.setCheckDate(rs.getString("check_date"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (Exception e) {
                }
        }

        return mst;
    }
*/
    /**
     * 根据订单号获得对应的发货单数量
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception
     * @author kevin
     */
    //add date 2006-08-16
    public static int getCountOfShippingNotice(Connection conn,
            String orderNumber) throws Exception {

        ShippingNoticeMst mst = new ShippingNoticeMst();
        String sql = "select count(*) from ord_shippingnotices where order_number =? ";

        PreparedStatement ps = null;
        ResultSet rs = null;
        int ret = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, orderNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (ps != null)
                try {
                    rs.close();
                    ps.close();
                } catch (Exception e) {
                }
        }

        return ret;
    }

    /**
     * 根据发货单号得到发货单的主要信息
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception
     */
    public static ShippingNoticeMst findShippingNoticeByNO(Connection conn,
            String docNumber) throws Exception {

        ShippingNoticeMst mst = new ShippingNoticeMst();
        String sql = " select a.*,b.card_id,b.name as mb_name,c.name as delivery_type_name,d.name as status_name "
                + " from ord_shippingnotices a, mbr_members b,s_delivery_type c,s_shippingnotice_status d "
                + " where a.member_id = b.id and a.delivery_type = c.id and a.status = d.id "
                + " and a.barcode = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, docNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                mst.setSn_id(rs.getLong("id"));
                mst.setSn_code(rs.getString("barcode"));
                //mst.setRef_order(rs.getLong("ref_order_id"));
                //mst.setOrder_number(rs.getString("order_number"));
                //mst.setMb_id(rs.getLong("member_id"));
                //mst.setMb_code(rs.getString("card_id"));
                //mst.setMb_name(rs.getString("mb_name"));
                //mst.setContactor(rs.getString("contact"));
                //mst.setPostcode(rs.getString("postcode"));
                //mst.setDelivery_type_name(rs.getString("delivery_type_name"));
                //mst.setAddress(rs.getString("address"));
                //mst.setTelephone(rs.getString("phone"));
                //mst.setDelivery_fee(rs.getDouble("delivery_fee"));
                //mst.setOrder_pr_type(rs.getInt("order_pr_type"));//add by user 2007-01-09
                mst.getOrder().setOrderId(rs.getInt("ref_order_id"));
                mst.getOrder().setOrderNumber(rs.getString("order_number"));
                mst.getOrder().setPrTypeId(rs.getInt("order_pr_type"));
                mst.getOrder().getMember().setID(rs.getInt("member_id"));
                mst.getOrder().getMember().setCARD_ID(rs.getString("card_id"));
                mst.getOrder().getMember().setNAME(rs.getString("mb_name"));
                mst.getOrder().getDeliveryInfo().setReceiptor(rs.getString("contact"));
                mst.getOrder().getDeliveryInfo().setPostCode(rs.getString("postcode"));
                mst.getOrder().getDeliveryInfo().setDeliveryType(rs.getString("delivery_type_name"));
                mst.getOrder().getDeliveryInfo().setAddress(rs.getString("address"));
                mst.getOrder().getDeliveryInfo().setPhone(rs.getString("phone")+" "+rs.getString("phone1"));
                mst.getOrder().getDeliveryInfo().setDeliveryFee(rs.getDouble("delivery_fee"));
                mst.setPackageFee(rs.getDouble("package_fee"));// add by user 2008-05-20
                
                mst.setLot(rs.getString("lot"));
                mst.setCreate_date(rs.getString("create_date"));
                mst.setPrint_date(rs.getString("print_date"));
                mst.setStatus(rs.getInt("status"));
                mst.setStatus_name(rs.getString("status_name"));
                //mst.setGift_number(rs.getString("gift_number"));
                mst.setAppend_fee(rs.getDouble("append_fee"));
                mst.setGoods_fee(rs.getDouble("goods_fee"));
                mst.setDiscount_fee(rs.getDouble("discount_fee"));
                
                mst.setPayed_money(rs.getDouble("payed_money"));
                mst.setShipping_sum(rs.getDouble("shipping_sum"));
                mst.setShipCategory(rs.getInt("SHIPPINGNOTICES_CATEGORY"));
                mst.setState(rs.getString("STATE"));
                mst.setDeliveryDate(rs.getString("delivery_date"));
                mst.setCheckDate(rs.getString("check_date"));
                mst.setPackageCategory(rs.getInt("package_category"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (Exception e) {
                }
        }

        return mst;
    }

    /**
     * 根据发货单明细id得到1条明细记录
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception
     */
    public static ShippingNoticeDtl findShippingNoticeDtlByPK(Connection conn,
            int id) throws Exception {

        ShippingNoticeDtl dtl = null;
        String sql = "select /*+index(a pk_ord_shippingnotice_lines) */ a.*,b.item_code, b.name as item_name,c.name as status_name "
                + " from ord_shippingnotice_lines a,prd_items b,s_shippingnotice_line_status c "
                + " where a.id = ? and a.item_id = b.item_id(+) "
                + " and a.status = c.id(+)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                dtl = new ShippingNoticeDtl();
                dtl.setId(rs.getLong("id"));
                dtl.setItem_id(rs.getLong("item_id"));
                dtl.setItem_code(rs.getString("item_code"));
                dtl.setItem_name(rs.getString("item_name"));
                dtl.setStatus_name(rs.getString("status_name"));
                dtl.setQty(rs.getInt("quantity"));
                dtl.setPrice(rs.getDouble("price"));
                dtl.setComments(rs.getString("comments"));
                dtl.setRef_sn_id(rs.getLong("sn_id"));
                dtl.setRefOrderLineId(rs.getLong("ref_order_line_id"));
                dtl.setTotal(dtl.getPrice() * dtl.getQty());
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (Exception e) {
                }
        }

        return dtl;
    }

    /**
     * 得到一条发货单所有明细
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception
     */
    public static ArrayList getDetailByMst(Connection conn,
            ShippingNoticeMst mst) throws Exception {
        ArrayList dtls = new ArrayList();
        String sql = "select a.*,b.itm_code, c.itm_name as item_name,d.name as status_name ,f.name as color_name,b.size_code as size_name "
                + " from ord_shippingnotice_lines a,prd_item_sku b,prd_item c,s_shippingnotice_line_status d," +
                		"prd_item_color f"
                + " where a.sn_id = " + mst.getSn_id()
                + " and a.sku_id = b.sku_id and a.status = d.id(+) and b.itm_code=c.itm_code "
                + " and b.color_code = f.code(+) ";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ShippingNoticeDtl dtl = new ShippingNoticeDtl();
            dtl.setId(rs.getLong("id"));
            dtl.setItem_id(rs.getLong("sku_id"));
            dtl.setItem_code(rs.getString("itm_code"));
            dtl.setItem_name(rs.getString("item_name"));
            dtl.setColor_name(rs.getString("color_name"));
            dtl.setSize_name(rs.getString("size_name"));
            dtl.setStatus_name(rs.getString("status_name"));
            dtl.setQty(rs.getInt("quantity"));
            dtl.setPrice(rs.getDouble("price"));
            dtl.setComments(rs.getString("comments"));
            dtl.setRef_sn_id(rs.getLong("sn_id"));
            dtl.setRefOrderLineId(rs.getLong("ref_order_line_id"));
            dtl.setTotal(dtl.getPrice() * dtl.getQty());
            dtls.add(dtl);
        }
        rs.close();
        st.close();
        return dtls;
    }
    /**
     * 得到一条发货单所有明细(合并发货单的明细，子单的明细)
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception
     */
    public static ArrayList getDetailByMst2(Connection conn,
            ShippingNoticeMst mst) throws Exception {
        ArrayList dtls = new ArrayList();
        String sql = "select a.*,b.item_code, b.name as item_name,c.name as status_name "
                + " from ord_shippingnotice_lines a,prd_items b,s_shippingnotice_line_status c "
                + " where a.sn_id in ( select child_ship_id from ord_ship_sets where parent_ship_id = ?) "
                + " and a.item_id = b.item_id(+) " + " and a.status = c.id(+)";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setLong(1, mst.getSn_id());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            ShippingNoticeDtl dtl = new ShippingNoticeDtl();
            dtl.setId(rs.getLong("id"));
            dtl.setItem_id(rs.getLong("item_id"));
            dtl.setItem_code(rs.getString("item_code"));
            dtl.setItem_name(rs.getString("item_name"));
            dtl.setStatus_name(rs.getString("status_name"));
            dtl.setQty(rs.getInt("quantity"));
            dtl.setPrice(rs.getDouble("price"));
            dtl.setComments(rs.getString("comments"));
            dtl.setRef_sn_id(rs.getLong("sn_id"));
            dtl.setRefOrderLineId(rs.getLong("ref_order_line_id"));
            dtl.setTotal(dtl.getPrice() * dtl.getQty());
            dtls.add(dtl);
        }
        rs.close();
        st.close();
        return dtls;
    }
    /**
     * 根据发运单号查找明细记录条数
     * 
     * @param conn
     * @param mst
     * @return
     * @throws Exception
     */
    public static int getDetailCntByFK(Connection conn, String docNumber)
            throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from ord_shippingnotice_lines where sn_id = "
                + "(select id from ord_shippingnotices where barcode = ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, docNumber);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception ex) {
            throw ex;
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
     * 根据发运单号查找明细记录条数(合并发货单)
     * 
     * @param conn
     * @param mst
     * @return
     * @throws Exception
     */
    public static int getDetailCntByFK2(Connection conn, String docNumber)
            throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from ord_shippingnotice_lines where sn_id in "
                + "(select child_ship_id from ord_ship_sets a inner join ord_shippingnotices b on b.id = a.parent_ship_id where b.barcode = ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, docNumber);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception ex) {
            throw ex;
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
     * 删除发货单
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception
     */
    public static int deleteShippingNotice(Connection conn, long id, int operator)
            throws Exception {

        CallableStatement cst = conn
                .prepareCall("{?=call orders.f_shippingnotice_delete(?, ?)}");
        cst.setLong(2, id);
        cst.setInt(3, operator);
        cst.registerOutParameter(1, java.sql.Types.INTEGER);
        cst.execute();

        int ret = cst.getInt(1);
        cst.close();
        return ret;
    }

    /**
     * 得到进销存配货单状态
     * 
     * @param conn
     * @param shipID
     * @return
     * @throws Exception
     */
    public static String getSNStatus(Connection conn, long shipID)
            throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select status from jxc.sto_SHIPPINGNOTICES_mst where lot = ( select lot from ord_shippingnotices where id = ? )";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, shipID);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException ex) {

                }
        }
    }

    /**
     * 得到订单list
     * 
     * @param conn
     * @param order
     * @throws Exception
     */
    public static ArrayList querySnList(Connection conn, String sql, int from,
            int to) throws Exception {
        ArrayList ret = new ArrayList();
        String sql1 = " SELECT * FROM ( SELECT t.*, rownum rownum_ FROM ( "
                + sql + " )t WHERE rownum <= ?) B WHERE rownum_ >? ";
        System.out.println(sql1);
        PreparedStatement ps = conn.prepareStatement(sql1);
        ps.setInt(1, to);
        ps.setInt(2, from);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ShippingNoticeMst mst = new ShippingNoticeMst();
            mst.setSn_id(rs.getLong("id"));
            mst.setSn_code(rs.getString("barcode"));
            //mst.setRef_order(rs.getLong("ref_order_id"));
            //mst.setOrder_number(rs.getString("order_number"));
            //mst.setMb_id(rs.getLong("member_id"));
            //mst.setMb_code(rs.getString("card_id"));
            //mst.setContactor(rs.getString("contact"));
            //mst.setPostcode(rs.getString("postcode"));
            //mst.setDelivery_type_name(rs.getString("delivery_type_name"));
            mst.getOrder().setOrderId(rs.getInt("ref_order_id"));
            mst.getOrder().setOrderNumber(rs.getString("order_number"));
            mst.getOrder().getMember().setID(rs.getInt("member_id"));
            mst.getOrder().getMember().setCARD_ID(rs.getString("card_id"));
            mst.getOrder().getDeliveryInfo().setReceiptor(rs.getString("contact"));
            mst.getOrder().getDeliveryInfo().setPostCode(rs.getString("postcode"));
            mst.getOrder().getDeliveryInfo().setDeliveryType(rs.getString("delivery_type_name"));
            
            mst.setLot(rs.getString("lot"));
            mst.setCreate_date(rs.getString("create_date"));
            mst.setPrint_date(rs.getString("print_date"));
            mst.setStatus_name(rs.getString("status_name"));
            ret.add(mst);
        }
        rs.close();
        ps.close();
        return ret;
    }

    /**
     * 得到发货单list的总条数
     * 
     * @param conn
     * @param order
     * @throws Exception
     */
    public static int querySnListCount(Connection conn, String sql)
            throws Exception {
        int ret = 0;
        String sql1 = " SELECT COUNT(1) "
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

    /**
     * 得到发货单主键 add by user 2006-08-15 16:54
     * 
     * @param conn
     * @return
     * @throws SQLException
     */
    public static long generateSnId(Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long rtn = 0L;
        String sql = "SELECT seq_sn_id.NEXTVAL from dual";

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rtn = rs.getLong(1);
            }
            return rtn;
        } catch (SQLException ex) {

            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * 插入发货单主表
     * 
     * @param conn
     * @param data
     * @return
     * @throws SQLException
     */
    public static void insertSnMst(Connection conn, ShippingNoticeMst data)
            throws SQLException {

        PreparedStatement pstmt = null;
        String sql = "insert into ord_shippingnotices "
                + "(id, doc_number, ref_order_id, member_year_exp, member_exp, "
                + "member_id, company_id, create_date, comments, creator_id, "
                + "delivery_date, postcode, address, state, phone, contact, "
                + "delivery_fee, delivery_type, print_date, operator_id, lot, "
                + "status, barcode, country, order_category, payment_method, "
                + "shipping_sum, activity_description, old_member_deposit, "
                + "old_member_exp, old_year_exp, created_member_exp, "
                + "created_year_exp, shipping_total, "
                + "shipptingtotices_money, shippingnotices_category, city, "
                + "entry_fee, order_number, source_id, payed_money, goods_fee, append_fee ) "
                + "values " 
                + "( " 
                + "?, ?, ?, 0, 0, "
                + "?, 1, sysdate, '市场销售', 29, " 
                + "sysdate, ?, ?, ?, ?, ?, "
                + "?, ?, sysdate, 29, 0, " 
                + "30, TO_CHAR (?), 1, ?, ?, "
                + "?, 'user', 0, " 
                + "0, 0, 0, " 
                + "0, ?, " 
                + "?, 0, ?, "
                + "0, ?, ?, ?, ?, ? " 
                + ") ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, data.getSn_id());
            pstmt.setString(2, data.getSn_code());
            pstmt.setLong(3, data.getRef_order());

            pstmt.setLong(4, data.getMb_id());

            pstmt.setString(5, data.getPostcode());
            pstmt.setString(6, data.getAddress());
            pstmt.setString(7, data.getState());
            pstmt.setString(8, data.getTelephone());
            pstmt.setString(9, data.getContactor());
           
            pstmt.setDouble(10, data.getDelivery_fee());
            pstmt.setInt(11, data.getDelivery_type());

            pstmt.setString(12, data.getSn_code());
            pstmt.setInt(13, data.getOrderCategory());
            pstmt.setInt(14, data.getPay_type());
            pstmt.setDouble(15, data.getShipping_sum());

            pstmt.setDouble(16, data.getShippint_total());
            pstmt.setDouble(17, data.getShippingnoticesMoney());
            pstmt.setString(18, data.getCity());
            
            pstmt.setString(19, data.getOrder_number());
            pstmt.setString(20, data.getSourceID());
            pstmt.setDouble(21, data.getPayed_money());
            pstmt.setDouble(22, data.getGoods_fee());
            pstmt.setDouble(23, data.getAppend_fee());

            pstmt.execute();

        } catch (SQLException ex) {
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
     * 更新发货单主表
     * 
     * @param conn
     * @param data
     * @return
     * @throws SQLException
     */
    public static void updateSnMst(Connection conn, ShippingNoticeMst data)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "update ord_shippingnotices set goods_fee = ?, payed_money = ?, shipptingtotices_money = ?, shipping_sum = ?, shipping_total = ? where id = ?";
                
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, data.getGoods_fee());
            pstmt.setDouble(2, data.getPayed_money());
            pstmt.setDouble(3, data.getShippingnoticesMoney());
            pstmt.setDouble(4, data.getShipping_sum());
            pstmt.setDouble(5, data.getShippint_total());
            pstmt.setLong(6, data.getSn_id());
            pstmt.execute();

        } catch (SQLException ex) {
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
     * 插入发货单明细行
     * 
     * @param conn
     * @param data
     * @return
     * @throws SQLException
     */
    public static void insertSnDtl(Connection conn, ShippingNoticeDtl data)
            throws SQLException {

        PreparedStatement pstmt = null;
        String sql = "insert into ord_shippingnotice_lines " 
            	+ "("
                + "id, sn_id, ref_order_line_id, item_id, quantity, "
                + "price, status, comments, set_item_id, operator_id " 
                + ") "
                + "values " 
                + "(" 
                + "SEQ_SN_LINE_ID.nextval, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ? " 
                + ") ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, data.getRef_sn_id());
            pstmt.setLong(2, data.getRefOrderLineId());
            pstmt.setLong(3, data.getItem_id());
            pstmt.setDouble(4, data.getQty());
            pstmt.setDouble(5, data.getPrice());
            pstmt.setInt(6, data.getStatus());
            pstmt.setString(7, data.getComments());
            pstmt.setString(8, data.getSetItemID());
            pstmt.setInt(9, data.getOperatorID());

            pstmt.execute();

        } catch (SQLException ex) {
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
     * 根据订单Id产生相应的发货单
     * 
     * @param conn
     * @param orderId
     * @return 1-成功；2-失败
     * @throws SQLException
     */
    public static int createSnByOrderId(Connection conn, int orderId)
            throws SQLException {
        CallableStatement cstmt = null;
        String sql = "{ ? = call service.create_shippingnotice(?) }";
        try {
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(2, orderId);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(1);
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (cstmt != null) {
                cstmt.close();
            }
        }
    }

    /**
     * 更新发货单状态
     * 
     * @param conn
     * @param orderId
     * @return
     * @throws SQLException
     */
    public static void updateSnStatusByPk(Connection conn, int status, int snId)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "update ord_shippingnotices set status = ? where id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, status);
            pstmt.setInt(2, snId);
            pstmt.execute();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
    /**
     * 更新明细状态
     * 
     * @param conn
     * @param orderId
     * @return
     * @throws SQLException
     */
    public static void updateSnLineStatusByPk(Connection conn, int status, int snId)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "update ord_shippingnotice_lines set status = ? where sn_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, status);
            pstmt.setInt(2, snId);
            pstmt.execute();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
    
    /**
	 * 通过发货单查找礼券使用列表
	 * 
	 * @param conn
	 * @param snId
	 * @return
	 * @throws SQLException
	 */
	public static Collection getGiftsBySnId(Connection conn, long snId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = " select * from shippingnotice_gifts where sn_id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, snId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SnGifts sn_gifts = new SnGifts();
				sn_gifts.setId(rs.getLong("gs_id"));
				sn_gifts.setSnId(rs.getInt("sn_id"));
				sn_gifts.setGiftNumber(rs.getString("gift_number"));
				sn_gifts.setDisAmt(rs.getDouble("dis_amt"));
				coll.add(sn_gifts);

			}
			return coll;
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}

	}
	/**
	 * 通过合并发货单得到子发货单
	 * @param conn
	 * @param mst
	 * @throws SQLException
	 */
	public static void getChildrenSnByParent(Connection conn, ShippingNoticeMst mst)
		throws SQLException {
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		String sql = null;
		try {
			//子单
			sql = "select b.id, b.doc_number, b.order_number, b.ref_order_id, b.lot, b.status, d.name as status_name, b.shippingnotices_category "
					+ "from ord_ship_sets a inner join ord_shippingnotices b on a.child_ship_id = b.id "
					+ "inner join s_shippingnotice_status d on d.id = b.status "
					+ "where a.parent_ship_id = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, mst.getSn_id());
			rs2 = pstmt2.executeQuery();
			Collection childrenList = new ArrayList();
			while (rs2.next()) {
				ShippingNoticeMst childMst = new ShippingNoticeMst();
				childMst.setSn_id(rs2.getLong("id"));
				childMst.setSn_code(rs2.getString("doc_number"));
				childMst.setOrder_number(rs2.getString("order_number"));
				childMst.setRef_order(rs2.getInt("ref_order_id"));
				childMst.setLot(rs2.getString("lot"));
				childMst.setStatus(rs2.getInt("status"));
				childMst.setStatus_name(rs2.getString("status_name"));
				childMst.setShipCategory(rs2.getInt("shippingnotices_category"));
				childrenList.add(childMst);
			}
			mst.setChildrenList(childrenList);
			
		} catch (SQLException ex) {
			throw ex;
		} finally {
			
			if (rs2 != null)
				rs2.close();
			
			if (pstmt2 != null)
				pstmt2.close();
		}
		
	}
	/**
	 * 通过子发货单得到合并发货单
	 * @param conn
	 * @param child
	 * @throws SQLException
	 */
	public static void getParentSnByChild(Connection conn, ShippingNoticeMst child)
		throws SQLException {
		ShippingNoticeMst parentMst = new ShippingNoticeMst();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			//子单
			sql = "select b.id, b.doc_number, b.order_number, b.ref_order_id, b.lot, b.status, d.name as status_name, b.shippingnotices_category "
					+ "from ord_ship_sets a inner join ord_shippingnotices b on a.parent_ship_id = b.id "
					+ "inner join s_shippingnotice_status d on d.id = b.status "
					+ "where a.child_ship_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, child.getSn_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				parentMst.setSn_id(rs.getLong("id"));
				parentMst.setSn_code(rs.getString("doc_number"));
				parentMst.setOrder_number(rs.getString("order_number"));
				parentMst.setRef_order(rs.getInt("ref_order_id"));
				parentMst.setLot(rs.getString("lot"));
				parentMst.setStatus(rs.getInt("status"));
				parentMst.setStatus_name(rs.getString("status_name"));
				parentMst.setShipCategory(rs.getInt("shippingnotices_category"));
				
			}
			child.setParent(parentMst);
			
		} catch (SQLException ex) {
			throw ex;
		} finally {
			
			if (rs != null)
				rs.close();
			
			if (pstmt != null)
				pstmt.close();
		}
		
	}
	/**
	 * 通过合并发货单号查找子发货单
	 * 
	 * @param conn
	 * @param queryCondition
	 * @return
	 * @throws SQLException
	 */
	public static Collection getPackageSnByLot(Connection conn, ShippingNoticeForm queryCondition)
			throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Collection coll = new ArrayList();
		String sql = null;
		String str = null;
		sql = " select a.id, a.doc_number, a.order_number, a.ref_order_id, c.card_id, c.name as member_name, a.lot, "
			+ "a.status, d.name as status_name, a.postcode, a.address, a.phone, a.contact "
			+ "from ord_shippingnotices a  "
			+ "inner join mbr_members c on a.member_id = c.id "
			+ "inner join s_shippingnotice_status d on d.id = a.status "
			+ "where a.package_category = 1 ";
		if (queryCondition.getOrder_number() != null && queryCondition.getOrder_number().trim().length()>0) {
			sql += "and a.id = (select parent_ship_id from ord_ship_sets where child_ship_id = (select id from ord_shippingnotices where order_number = ?)) ";
			str = queryCondition.getOrder_number().trim();
		} else {
			if (queryCondition.getSn_code() != null && queryCondition.getSn_code().trim().length()>0) {
				sql += "and a.barcode = ? ";
				str = queryCondition.getSn_code().trim();
			} else {
				
				sql += "and a.lot = ? ";
				str = queryCondition.getLot().trim();
				
			}
		}
		
		
		
			
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, str);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ShippingNoticeMst mst = new ShippingNoticeMst();
				mst.setSn_id(rs.getLong("id"));
				mst.setSn_code(rs.getString("doc_number"));
				mst.setOrder_number(rs.getString("order_number"));
				mst.setRef_order(rs.getLong("ref_order_id"));
				mst.setLot(rs.getString("lot"));
				mst.setStatus(rs.getInt("status"));
				mst.setStatus_name(rs.getString("status_name"));
				mst.setStatus_name(rs.getString("status_name"));
				mst.setMb_code(rs.getString("card_id"));
				mst.setMb_name(rs.getString("member_name"));
				mst.setAddress(rs.getString("address"));
				mst.setPostcode(rs.getString("postcode"));
				mst.setTelephone(rs.getString("phone"));
				mst.setContactor(rs.getString("contact"));
				//子单
				sql = "select b.id, b.doc_number, b.order_number, b.ref_order_id, b.lot, b.status, d.name as status_name "
					+ "from ord_ship_sets a inner join ord_shippingnotices b on a.child_ship_id = b.id "
					+ "inner join s_shippingnotice_status d on d.id = b.status "
					+ "where a.parent_ship_id = ?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setLong(1, mst.getSn_id());
				rs2 = pstmt2.executeQuery();
				Collection childrenList = new ArrayList();
				while (rs2.next()) {
					ShippingNoticeMst childMst = new ShippingNoticeMst();
					childMst.setSn_id(rs2.getLong("id"));
					childMst.setSn_code(rs2.getString("doc_number"));
					childMst.setOrder_number(rs2.getString("order_number"));
					childMst.setRef_order(rs2.getInt("ref_order_id"));
					childMst.setLot(rs2.getString("lot"));
					childMst.setStatus(rs2.getInt("status"));
					childMst.setStatus_name(rs2.getString("status_name"));
					childrenList.add(childMst);
				}
				mst.setChildrenList(childrenList);
				coll.add(mst);
				rs2.close();
				pstmt2.close();
			}
			return coll;
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (rs2 != null)
				rs2.close();
			if (pstmt != null)
				pstmt.close();
			if (pstmt2 != null)
				pstmt2.close();
		}

	}
	
}

