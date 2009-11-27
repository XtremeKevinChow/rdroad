package com.magic.exchange;

import java.io.Serializable;
import java.sql.Date;
import java.util.*;

/**
 * 网站订单信息Bean
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class OrderInfo implements Serializable {



	Collection lines = new Vector();

	public Collection getLines() {
		return lines;
	}

	public void setLines(Collection col) {
		lines = col;
	}

	private int shipcount;

	private int shipstatus;

	private float ordermoney;

	private String so_number;

	private int buyer_id;
	private long mbr_id;

	/**
	 * @return Returns the mbr_id.
	 */
	public long getMbr_id() {
		return mbr_id;
	}
	/**
	 * @param mbr_id The mbr_id to set.
	 */
	public void setMbr_id(long mbr_id) {
		this.mbr_id = mbr_id;
	}
	private String release_date;

	private String address;

	private String postcode;

	private String contact;

	private String phone;

	private int delivery_type;

	private float delivery_fee;

	private int payment_method;

	private int is_invoice;

	private float product_value;

	private float appendfee;

	private float order_value;

	private int order_status;

	private int oos_dispose;

	private String web_line_id;
	
	/**
	 * @return Returns the web_line_id.
	 */
	public String getWeb_line_id() {
		return web_line_id;
	}
	/**
	 * @param web_line_id The web_line_id to set.
	 */
	public void setWeb_line_id(String web_line_id) {
		this.web_line_id = web_line_id;
	}
	private String item_id;

	private String quantity;

	private String price;

	private String is_commitment;

	private String sell_type;

	private String itemName;

	private float memberPrice;

	private int gdscount;

	//订单ID
	private int order_id;

	//礼卷号
	private String gift_number;

	//订单备注
	private String order_memo;

	private float salesPrice;

	private float totalmoney;

	private int saletype;

	private int finishcount;

	private float prepayMoney;

	private int card_type;
	
	private double bank_pay;
	
	/**
	 * @return Returns the card_type.
	 */
	public int getCard_type() {
		return card_type;
	}
	/**
	 * @param card_type The card_type to set.
	 */
	public void setCard_type(int card_type) {
		this.card_type = card_type;
	}
	/**
	 * @return Returns the gdscount.
	 */
	public int getGdscount() {
		return gdscount;
	}
	/**
	 * @param gdscount The gdscount to set.
	 */
	public void setGdscount(int gdscount) {
		this.gdscount = gdscount;
	}
	public int getFinishcount() {
		return finishcount;
	}

	public void setFinishcount(int finishcount) {
		this.finishcount = finishcount;
	}

	public int getSaletype() {
		return saletype;
	}

	public void setSaletype(int saletype) {
		this.saletype = saletype;
	}

	public float getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(float totalmoney) {
		this.totalmoney = totalmoney;
	}

	public int getGbscount() {
		return gdscount;
	}

	public void setGbscount(int gdscount) {
		this.gdscount = gdscount;
	}

	public float getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(float salesPrice) {
		this.salesPrice = salesPrice;
	}

	public float getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(float memberPrice) {
		this.memberPrice = memberPrice;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            The address to set.
	 */
	public void setAddress(String address) {
		this.address = address.trim();
	}

	/**
	 * @return Returns the appendfee.
	 */
	public float getAppendfee() {
		return appendfee;
	}

	/**
	 * @param appendfee
	 *            The appendfee to set.
	 */
	public void setAppendfee(float appendfee) {
		this.appendfee = appendfee;
	}

	/**
	 * @return Returns the buyer_id.
	 */
	public int getBuyer_id() {
		return buyer_id;
	}

	/**
	 * @param buyer_id
	 *            The buyer_id to set.
	 */
	public void setBuyer_id(int buyer_id) {
		this.buyer_id = buyer_id;
	}

	/**
	 * @return Returns the contact.
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            The contact to set.
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return Returns the delivery_fee.
	 */
	public float getDelivery_fee() {
		return delivery_fee;
	}

	/**
	 * @param delivery_fee
	 *            The delivery_fee to set.
	 */
	public void setDelivery_fee(float delivery_fee) {
		this.delivery_fee = delivery_fee;
	}

	/**
	 * @return Returns the delivery_type.
	 */
	public int getDelivery_type() {
		return delivery_type;
	}

	/**
	 * @param delivery_type
	 *            The delivery_type to set.
	 */
	public void setDelivery_type(int delivery_type) {
		this.delivery_type = delivery_type;
	}

	/**
	 * @return Returns the is_invoice.
	 */
	public int getIs_invoice() {
		return is_invoice;
	}

	/**
	 * @param is_invoice
	 *            The is_invoice to set.
	 */
	public void setIs_invoice(int is_invoice) {
		this.is_invoice = is_invoice;
	}

	/**
	 * @return Returns the item_id.
	 */
	public String getItem_id() {
		return item_id;
	}

	/**
	 * @param item_id
	 *            The item_id to set.
	 */
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	/**
	 * @return Returns the order_status.
	 */
	public int getOrder_status() {
		return order_status;
	}

	/**
	 * @param order_status
	 *            The order_status to set.
	 */
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	/**
	 * @return Returns the order_value.
	 */
	public float getOrder_value() {
		return order_value;
	}

	/**
	 * @param order_value
	 *            The order_value to set.
	 */
	public void setOrder_value(float order_value) {
		this.order_value = order_value;
	}

	/**
	 * @return Returns the payment_method.
	 */
	public int getPayment_method() {
		return payment_method;
	}

	/**
	 * @param payment_method
	 *            The payment_method to set.
	 */
	public void setPayment_method(int payment_method) {
		this.payment_method = payment_method;
	}

	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return Returns the postcode.
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode
	 *            The postcode to set.
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return Returns the price.
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            The price to set.
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return Returns the product_value.
	 */
	public float getProduct_value() {
		return product_value;
	}

	/**
	 * @param product_value
	 *            The product_value to set.
	 */
	public void setProduct_value(float product_value) {
		this.product_value = product_value;
	}

	/**
	 * @return Returns the quantity.
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            The quantity to set.
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return Returns the release_date.
	 */
	public String getRelease_date() {
		return release_date;
	}

	/**
	 * @param release_date
	 *            The release_date to set.
	 */
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	/**
	 * @return Returns the so_number.
	 */
	public String getSo_number() {
		return so_number;
	}

	/**
	 * @param so_number
	 *            The so_number to set.
	 */
	public void setSo_number(String so_number) {
		this.so_number = so_number;
	}

	/**
	 * @return Returns the is_commitment.
	 */
	public String getIs_commitment() {
		return is_commitment;
	}

	/**
	 * @param is_commitment
	 *            The is_commitment to set.
	 */
	public void setIs_commitment(String is_commitment) {
		this.is_commitment = is_commitment;
	}

	/**
	 * @return Returns the oos_dispose.
	 */
	public int getOos_dispose() {
		return oos_dispose;
	}

	/**
	 * @param oos_dispose
	 *            The oos_dispose to set.
	 */
	public void setOos_dispose(int oos_dispose) {
		this.oos_dispose = oos_dispose;
	}

	/**
	 * @return Returns the sell_type.
	 */
	public String getSell_type() {
		return sell_type;
	}

	/**
	 * @param sell_type
	 *            The sell_type to set.
	 */
	public void setSell_type(String sell_type) {
		this.sell_type = sell_type;
	}

	/**
	 * @return Returns the ordermoney.
	 */
	public float getOrdermoney() {
		return ordermoney;
	}

	/**
	 * @param ordermoney
	 *            The ordermoney to set.
	 */
	public void setOrdermoney(float ordermoney) {
		this.ordermoney = ordermoney;
	}

	/**
	 * @return Returns the shipcount.
	 */
	public int getShipcount() {
		return shipcount;
	}

	/**
	 * @param shipcount
	 *            The shipcount to set.
	 */
	public void setShipcount(int shipcount) {
		this.shipcount = shipcount;
	}

	/**
	 * @return Returns the shipstatus.
	 */
	public int getShipstatus() {
		return shipstatus;
	}

	/**
	 * @param shipstatus
	 *            The shipstatus to set.
	 */
	public void setShipstatus(int shipstatus) {
		this.shipstatus = shipstatus;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setGift_number(String gift_number) {
		this.gift_number = gift_number;
	}

	public String getGift_number() {
		return gift_number;
	}

	public void setOrder_memo(String order_memo) {
		this.order_memo = order_memo;
	}

	public String getOrder_memo() {
		return order_memo;
	}

	public float getPrepayMoney() {
		return prepayMoney;
	}

	public void setPrepayMoney(float prepayMoney) {
		this.prepayMoney = prepayMoney;
	}

	/**
	 * @return Returns the bank_pay.
	 */
	public double getBank_pay() {
		return bank_pay;
	}
	/**
	 * @param bank_pay The bank_pay to set.
	 */
	public void setBank_pay(double bank_pay) {
		this.bank_pay = bank_pay;
	}
	
	private String pro_line_id;
	
	/**
	 * @return Returns the pro_line_id.
	 */
	public String getPro_line_id() {
		return pro_line_id;
	}
	/**
	 * @param pro_line_id The pro_line_id to set.
	 */
	public void setPro_line_id(String pro_line_id) {
		this.pro_line_id = pro_line_id;
	}
}