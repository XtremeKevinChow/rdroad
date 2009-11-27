/*
 * Created on 2005-5-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.shippingnotice.form;

import java.util.ArrayList;

import com.magic.crm.common.WebForm;
import com.magic.crm.shippingnotice.entity.ShippingNoticeMst;
import java.util.Collection;
/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class ShippingNoticeForm extends WebForm {
	
	
	ShippingNoticeMst mst = new ShippingNoticeMst();
	ArrayList dtls = new ArrayList();
	
	
	ArrayList msts = new ArrayList();
	
	ArrayList statusList = new ArrayList();
	ArrayList deliveryList = new ArrayList();
	ArrayList payList = new ArrayList();
	
	/* 发货单礼券对应聚集 */
	Collection snGifts = new ArrayList();
	
	public Collection getSnGifts() {
		return snGifts;
	}
	public void setSnGifts(Collection snGifts) {
		this.snGifts = snGifts;
	}
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return mst.getAddress();
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		mst.setAddress(address);
	}
	/**
	 * @return Returns the append_fee.
	 */
	public double getAppend_fee() {
		return mst.getAppend_fee();
	}
	/**
	 * @param append_fee The append_fee to set.
	 */
	public void setAppend_fee(double append_fee) {
		mst.setAppend_fee(append_fee);
	}
	/**
	 * @return Returns the contactor.
	 */
	public String getContactor() {
		return mst.getContactor();
	}
	/**
	 * @param contactor The contactor to set.
	 */
	public void setContactor(String contactor) {
		mst.setContactor(contactor);
	}
	/**
	 * @return Returns the create_date.
	 */
	public String getCreate_date() {
		return mst.getCreate_date();
	}
	/**
	 * @param create_date The create_date to set.
	 */
	public void setCreate_date(String create_date) {
		mst.setCreate_date(create_date);
	}
	/**
	 * @return Returns the delivery_fee.
	 */
	public double getDelivery_fee() {
		return mst.getDelivery_fee();
	}
	/**
	 * @param delivery_fee The delivery_fee to set.
	 */
	public void setDelivery_fee(double delivery_fee) {
		mst.setDelivery_fee(delivery_fee);
	}
	/**
	 * @return Returns the delivery_type.
	 */
	public int getDelivery_type() {
		return mst.getDelivery_type();
	}
	/**
	 * @param delivery_type The delivery_type to set.
	 */
	public void setDelivery_type(int delivery_type) {
		mst.setDelivery_type(delivery_type);
	}
	/**
	 * @return Returns the delivery_type_name.
	 */
	public String getDelivery_type_name() {
		return mst.getDelivery_type_name();
	}
	/**
	 * @param delivery_type_name The delivery_type_name to set.
	 */
	public void setDelivery_type_name(String delivery_type_name) {
		mst.setDelivery_type_name(delivery_type_name);
	}
	/**
	 * @return Returns the goods_fee.
	 */
	public double getGoods_fee() {
		return mst.getGoods_fee();
	}
	/**
	 * @param goods_fee The goods_fee to set.
	 */
	public void setGoods_fee(double goods_fee) {
		mst.setGoods_fee(goods_fee);
	}
	/**
	 * @return Returns the lot.
	 */
	public String getLot() {
		return mst.getLot();
	}
	/**
	 * @param lot The lot to set.
	 */
	public void setLot(String lot) {
		mst.setLot(lot);
	}
	/**
	 * @return Returns the mb_code.
	 */
	public String getMb_code() {
		return mst.getMb_code();
	}
	/**
	 * @param mb_code The mb_code to set.
	 */
	public void setMb_code(String mb_code) {
		setMb_code(mb_code);
	}
	/**
	 * @return Returns the mb_id.
	 */
	public long getMb_id() {
		return mst.getMb_id();
	}
	/**
	 * @param mb_id The mb_id to set.
	 */
	public void setMb_id(long mb_id) {
		setMb_id(mb_id);
	}
	/**
	 * @return Returns the order_number.
	 */
	public String getOrder_number() {
		return mst.getOrder_number();
	}
	/**
	 * @param order_number The order_number to set.
	 */
	public void setOrder_number(String order_number) {
		mst.setOrder_number(order_number);
	}
	/**
	 * @return Returns the pay_type.
	 */
	public int getPay_type() {
		return mst.getPay_type();
	}
	/**
	 * @param pay_type The pay_type to set.
	 */
	public void setPay_type(int pay_type) {
		mst.setPay_type(pay_type);
	}
	/**
	 * @return Returns the pay_type_name.
	 */
	public String getPay_type_name() {
		return mst.getPay_type_name();
	}
	/**
	 * @param pay_type_name The pay_type_name to set.
	 */
	public void setPay_type_name(String pay_type_name) {
		mst.setPay_type_name (pay_type_name);
	}
	/**
	 * @return Returns the postcode.
	 */
	public String getPostcode() {
		return mst.getPostcode();
	}
	/**
	 * @param postcode The postcode to set.
	 */
	public void setPostcode(String postcode) {
		mst.setPostcode(postcode);
	}
	/**
	 * @return Returns the print_date.
	 */
	public String getPrint_date() {
		return mst.getPrint_date();
	}
	/**
	 * @param print_date The print_date to set.
	 */
	public void setPrint_date(String print_date) {
		mst.setPrint_date(print_date);
	}
	/**
	 * @return Returns the ref_order.
	 */
	public long getRef_order() {
		return mst.getRef_order();
	}
	/**
	 * @param ref_order The ref_order to set.
	 */
	public void setRef_order(long ref_order) {
		mst.setRef_order(ref_order);
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return mst.getRemark();
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		mst.setRemark(remark);
	}
	/**
	 * @return Returns the sn_code.
	 */
	public String getSn_code() {
		return mst.getSn_code();
	}
	/**
	 * @param sn_code The sn_code to set.
	 */
	public void setSn_code(String sn_code) {
		mst.setSn_code(sn_code);
	}
	/**
	 * @return Returns the sn_id.
	 */
	public long getSn_id() {
		return mst.getSn_id();
	}
	/**
	 * @param sn_id The sn_id to set.
	 */
	public void setSn_id(long sn_id) {
		mst.setSn_id(sn_id);
	}
	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return mst.getStatus();
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		mst.setStatus(status);
	}
	/**
	 * @return Returns the status_name.
	 */
	public String getStatus_name() {
		return mst.getStatus_name();
	}
	/**
	 * @param status_name The status_name to set.
	 */
	public void setStatus_name(String status_name) {
		mst.setStatus_name(status_name);
	}
	/**
	 * @return Returns the telephone.
	 */
	public String getTelephone() {
		return mst.getTelephone();
	}
	/**
	 * @param telephone The telephone to set.
	 */
	public void setTelephone(String telephone) {
		mst.setTelephone(telephone);
	}
	
	String strQrySnCode = "";
	String strQryOrdCode = "";
	String strQryMbrCode = "";
	String strQryMbrName = "";
	String strQryTelephone = "";
	String strQryShippingNumber = "";
	
	/**
	 * @return Returns the dtls.
	 */
	public ArrayList getDtls() {
		return dtls;
	}
	/**
	 * @param dtls The dtls to set.
	 */
	public void setDtls(ArrayList dtls) {
		this.dtls = dtls;
	}
	/**
	 * @return Returns the mst.
	 */
	public ShippingNoticeMst getMst() {
		return mst;
	}
	/**
	 * @param mst The mst to set.
	 */
	public void setMst(ShippingNoticeMst mst) {
		this.mst = mst;
	}
	/**
	 * @return Returns the msts.
	 */
	public ArrayList getMsts() {
		return msts;
	}
	/**
	 * @param msts The msts to set.
	 */
	public void setMsts(ArrayList msts) {
		this.msts = msts;
	}
	/**
	 * @return Returns the strQryMbrCode.
	 */
	public String getStrQryMbrCode() {
		return strQryMbrCode;
	}
	/**
	 * @param strQryMbrCode The strQryMbrCode to set.
	 */
	public void setStrQryMbrCode(String strQryMbrCode) {
		this.strQryMbrCode = strQryMbrCode;
	}
	/**
	 * @return Returns the strQryMbrName.
	 */
	public String getStrQryMbrName() {
		return strQryMbrName;
	}
	/**
	 * @param strQryMbrName The strQryMbrName to set.
	 */
	public void setStrQryMbrName(String strQryMbrName) {
		this.strQryMbrName = strQryMbrName;
	}
	/**
	 * @return Returns the strQryOrdCode.
	 */
	public String getStrQryOrdCode() {
		return strQryOrdCode;
	}
	/**
	 * @param strQryOrdCode The strQryOrdCode to set.
	 */
	public void setStrQryOrdCode(String strQryOrdCode) {
		this.strQryOrdCode = strQryOrdCode;
	}
	/**
	 * @return Returns the strQrySnCode.
	 */
	public String getStrQrySnCode() {
		return strQrySnCode;
	}
	/**
	 * @param strQrySnCode The strQrySnCode to set.
	 */
	public void setStrQrySnCode(String strQrySnCode) {
		this.strQrySnCode = strQrySnCode;
	}
	
	/**
	 * @return Returns the strQrySnCode.
	 */
	public String getMb_name() {
		return mst.getMb_name();
	}
	/**
	 * @param strQrySnCode The strQrySnCode to set.
	 */
	public void setMb_name(String mb_name) {
		mst.setMb_name(mb_name);
	}
	
	/**
	 * 发货单在核货以前可以取消
	 * @return
	 */
	public boolean isCancelable() {
		int status = mst.getStatus();
		return status >=0 && status < 20;
	}
	/**
	 * @return Returns the deliveryList.
	 */
	public ArrayList getDeliveryList() {
		return deliveryList;
	}
	/**
	 * @param deliveryList The deliveryList to set.
	 */
	public void setDeliveryList(ArrayList deliveryList) {
		this.deliveryList = deliveryList;
	}
	/**
	 * @return Returns the payList.
	 */
	public ArrayList getPayList() {
		return payList;
	}
	/**
	 * @param payList The payList to set.
	 */
	public void setPayList(ArrayList payList) {
		this.payList = payList;
	}
	/**
	 * @return Returns the statusList.
	 */
	public ArrayList getStatusList() {
		return statusList;
	}
	/**
	 * @param statusList The statusList to set.
	 */
	public void setStatusList(ArrayList statusList) {
		this.statusList = statusList;
	}
	
	/**
	 * @return Returns the payed_money.
	 */
	public double getPayed_money() {
		return mst.getPayed_money();
	}
	/**
	 * @param payed_money The payed_money to set.
	 */
	public void setPayed_money(double payed_money) {
		mst.setPayed_money(payed_money);
	}
	/**
	 * @return Returns the shipping_sum.
	 */
	public double getShipping_sum() {
		return mst.getShipping_sum();
	}
	/**
	 * @param shipping_sum The shipping_sum to set.
	 */
	public void setShipping_sum(double shipping_sum) {
		setShipping_sum(shipping_sum);
	}
	public String getStrQryTelephone() {
		return strQryTelephone;
	}
	public void setStrQryTelephone(String strQryTelephone) {
		this.strQryTelephone = strQryTelephone;
	}
	public String getStrQryShippingNumber() {
		return strQryShippingNumber;
	}
	public void setStrQryShippingNumber(String strQryShippingNumber) {
		this.strQryShippingNumber = strQryShippingNumber;
	}
}
