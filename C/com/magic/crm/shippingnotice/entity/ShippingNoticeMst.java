/*
 * Created on 2005-5-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.shippingnotice.entity;

import java.io.Serializable;
import com.magic.crm.order.entity.Order;
import java.util.Collection;
import java.util.ArrayList;
/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class ShippingNoticeMst implements Serializable {
	
	private static final long serialVersionUID = -2007032000000000001L;
	// 子发货单（合并发货单的子集）
	private Collection childrenList = new ArrayList();
	// 父发货单
	private ShippingNoticeMst parent = null;
	
	//相关联的订单
	private Order Order = new Order();
	//相关发货单明
	private Collection items = new ArrayList();
	long sn_id =0 ;//发货单id
	String sn_code = "";//发货单号
	long ref_order = 0;//订单id
	String order_number = "";//订单号
	String mb_code = "";//会员号
	long mb_id = 0;//会员id
	String mb_name;
	int status = -100;
	String status_name = "";//状态名称
	String lot = "";//批号
	int order_pr_type = -1;
	String create_date = "";
	String print_date = "";
	String gift_number = "";
	double delivery_fee = 0;
	double append_fee = 0;
	double goods_fee = 0;
	double discount_fee = 0;
	
	double payed_money = 0;
	double payed_emoney =0;
	public double getPayed_emoney() {
		return payed_emoney;
	}

	public void setPayed_emoney(double payed_emoney) {
		this.payed_emoney = payed_emoney;
	}
	double shipping_sum = 0;
	double shippint_total = 0;
	private double shippingnoticesMoney = 0d;
	//会员备注
	private String remark = "";
	//系统备注
	private String comments = null;
	private int shipCategory = -1;  
	private String sourceID = null;
	private int orderCategory = 0;
	//核货人
	private String checkPersonName;
	
	//合并发货单标记
	private int packageCategory = 0;
	private double packageFee = 0;
	
	
	private String invoice_number = "";
	private String invoice_title = "";
	private String shipping_number = "";
	private long logistic_id = 0;
	private String logistic_name = "";
	
	
	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getInvoice_title() {
		return invoice_title;
	}

	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}

	public String getShipping_number() {
		return shipping_number;
	}

	public void setShipping_number(String shipping_number) {
		this.shipping_number = shipping_number;
	}

	public long getLogistic_id() {
		return logistic_id;
	}

	public void setLogistic_id(long logistic_id) {
		this.logistic_id = logistic_id;
	}

	public String getLogistic_name() {
		return logistic_name;
	}

	public void setLogistic_name(String logistic_name) {
		this.logistic_name = logistic_name;
	}

	/**
	 * @return the packageFee
	 */
	public double getPackageFee() {
		return packageFee;
	}

	/**
	 * @param packageFee the packageFee to set
	 */
	public void setPackageFee(double packageFee) {
		this.packageFee = packageFee;
	}

	//发货单欠款（add by user 2008-01-07）
	public double getSnOwe() {
		return (this.packageFee + this.goods_fee + this.getOrder().getDeliveryInfo().getDeliveryFee() + this.append_fee + this.getDiscount_fee() - this.payed_money - this.payed_emoney);
	}
	
    public ShippingNoticeMst getParent() {
		return parent;
	}

	public void setParent(ShippingNoticeMst parent) {
		this.parent = parent;
	}

	public Collection getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(Collection childrenList) {
		this.childrenList = childrenList;
	}
	public int getPackageCategory() {
		return packageCategory;
	}
	public void setPackageCategory(int packageCategory) {
		this.packageCategory = packageCategory;
	}
	public String getCheckPersonName() {
		return checkPersonName;
	}
	public void setCheckPersonName(String checkPersonName) {
		this.checkPersonName = checkPersonName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getOrder_pr_type() {
		return order_pr_type;
	}
	public void setOrder_pr_type(int order_pr_type) {
		this.order_pr_type = order_pr_type;
	}
	/**
     * @return Returns the shippint_total.
     */
    public double getShippint_total() {
        return shippint_total;
    }
    /**
     * @param shippint_total The shippint_total to set.
     */
    public void setShippint_total(double shippint_total) {
        this.shippint_total = shippint_total;
    }
    /**
     * @return Returns the orderCategory.
     */
    public int getOrderCategory() {
        return orderCategory;
    }
    /**
     * @param orderCategory The orderCategory to set.
     */
    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }
	/* 送货信息 */
	String postcode = "";
	String contactor = "";
	String address = "";
	String telephone = "";
	private String city = null;
	int delivery_type = -100;
	String delivery_type_name = "";
	int pay_type = -100;
	String pay_type_name = "";
	
	private String state = null;
	private String deliveryDate = null;
	private String checkDate = null;
	
	/**
	 * 发货单在核货以前可以取消
	 * @return
	 */
	public boolean isCancelable() {
		int status = getStatus();
		return status >=0 && status < 20 ;
	}
    /**
     * @return Returns the sourceID.
     */
    public String getSourceID() {
        return sourceID;
    }
    /**
     * @param sourceID The sourceID to set.
     */
    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }
    /**
     * @return Returns the city.
     */
    public String getCity() {
        return city;
    }
    /**
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return Returns the append_fee.
	 */
	public double getAppend_fee() {
		return append_fee;
	}
	/**
	 * @param append_fee The append_fee to set.
	 */
	public void setAppend_fee(double append_fee) {
		this.append_fee = append_fee;
	}
	/**
	 * @return Returns the contactor.
	 */
	public String getContactor() {
		return contactor;
	}
	/**
	 * @param contactor The contactor to set.
	 */
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	/**
	 * @return Returns the create_date.
	 */
	public String getCreate_date() {
		return create_date;
	}
	/**
	 * @param create_date The create_date to set.
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	/**
	 * @return Returns the delivery_fee.
	 */
	public double getDelivery_fee() {
		return delivery_fee;
	}
	/**
	 * @param delivery_fee The delivery_fee to set.
	 */
	public void setDelivery_fee(double delivery_fee) {
		this.delivery_fee = delivery_fee;
	}
	/**
	 * @return Returns the delivery_type.
	 */
	public int getDelivery_type() {
		return delivery_type;
	}
	/**
	 * @param delivery_type The delivery_type to set.
	 */
	public void setDelivery_type(int delivery_type) {
		this.delivery_type = delivery_type;
	}
	/**
	 * @return Returns the delivery_type_name.
	 */
	public String getDelivery_type_name() {
		return delivery_type_name;
	}
	/**
	 * @param delivery_type_name The delivery_type_name to set.
	 */
	public void setDelivery_type_name(String delivery_type_name) {
		this.delivery_type_name = delivery_type_name;
	}
	/**
	 * @return Returns the goods_fee.
	 */
	public double getGoods_fee() {
		return goods_fee;
	}
	/**
	 * @param goods_fee The goods_fee to set.
	 */
	public void setGoods_fee(double goods_fee) {
		this.goods_fee = goods_fee;
	}
	/**
	 * @return Returns the lot.
	 */
	public String getLot() {
		return lot;
	}
	/**
	 * @param lot The lot to set.
	 */
	public void setLot(String lot) {
		this.lot = lot;
	}
	/**
	 * @return Returns the mb_code.
	 */
	public String getMb_code() {
		return mb_code;
	}
	/**
	 * @param mb_code The mb_code to set.
	 */
	public void setMb_code(String mb_code) {
		this.mb_code = mb_code;
	}
	/**
	 * @return Returns the mb_id.
	 */
	public long getMb_id() {
		return mb_id;
	}
	/**
	 * @param mb_id The mb_id to set.
	 */
	public void setMb_id(long mb_id) {
		this.mb_id = mb_id;
	}
	/**
	 * @return Returns the order_number.
	 */
	public String getOrder_number() {
		return order_number;
	}
	/**
	 * @param order_number The order_number to set.
	 */
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	/**
	 * @return Returns the pay_type.
	 */
	public int getPay_type() {
		return pay_type;
	}
	/**
	 * @param pay_type The pay_type to set.
	 */
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}
	/**
	 * @return Returns the pay_type_name.
	 */
	public String getPay_type_name() {
		return pay_type_name;
	}
	/**
	 * @param pay_type_name The pay_type_name to set.
	 */
	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}
	/**
	 * @return Returns the postcode.
	 */
	public String getPostcode() {
		return postcode;
	}
	/**
	 * @param postcode The postcode to set.
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/**
	 * @return Returns the print_date.
	 */
	public String getPrint_date() {
		return print_date;
	}
	/**
	 * @param print_date The print_date to set.
	 */
	public void setPrint_date(String print_date) {
		this.print_date = print_date;
	}
	/**
	 * @return Returns the ref_order.
	 */
	public long getRef_order() {
		return ref_order;
	}
	/**
	 * @param ref_order The ref_order to set.
	 */
	public void setRef_order(long ref_order) {
		this.ref_order = ref_order;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the sn_code.
	 */
	public String getSn_code() {
		return sn_code;
	}
	/**
	 * @param sn_code The sn_code to set.
	 */
	public void setSn_code(String sn_code) {
		this.sn_code = sn_code;
	}
	/**
	 * @return Returns the sn_id.
	 */
	public long getSn_id() {
		return sn_id;
	}
	/**
	 * @param sn_id The sn_id to set.
	 */
	public void setSn_id(long sn_id) {
		this.sn_id = sn_id;
	}
	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return Returns the status_name.
	 */
	public String getStatus_name() {
		return status_name;
	}
	/**
	 * @param status_name The status_name to set.
	 */
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	/**
	 * @return Returns the telephone.
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone The telephone to set.
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return Returns the mb_name.
	 */
	public String getMb_name() {
		return mb_name;
	}
	/**
	 * @param mb_name The mb_name to set.
	 */
	public void setMb_name(String mb_name) {
		this.mb_name = mb_name;
	}
	
	/**
	 * @return Returns the gift_number.
	 */
	public String getGift_number() {
		return gift_number;
	}
	/**
	 * @param gift_number The gift_number to set.
	 */
	public void setGift_number(String gift_number) {
		this.gift_number = gift_number;
	}
	
	/**
	 * @return Returns the payed_money.
	 */
	public double getPayed_money() {
		return payed_money;
	}
	/**
	 * @param payed_money The payed_money to set.
	 */
	public void setPayed_money(double payed_money) {
		this.payed_money = payed_money;
	}
	/**
	 * @return Returns the shipping_sum.
	 */
	public double getShipping_sum() {
		return shipping_sum;
	}
	/**
	 * @param shipping_sum The shipping_sum to set.
	 */
	public void setShipping_sum(double shipping_sum) {
		this.shipping_sum = shipping_sum;
	}
	
    
    /**
     * @return Returns the shipCategory.
     */
    public int getShipCategory() {
        return shipCategory;
    }
    /**
     * @param shipCategory The shipCategory to set.
     */
    public void setShipCategory(int shipCategory) {
        this.shipCategory = shipCategory;
    }
    
    /**
     * @return Returns the state.
     */
    public String getState() {
        return state;
    }
    /**
     * @param state The state to set.
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * @return Returns the deliveryDate.
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }
    /**
     * @param deliveryDate The deliveryDate to set.
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    /**
     * @return Returns the checkDate.
     */
    public String getCheckDate() {
        return checkDate;

    }
    /**
     * @param checkDate The checkDate to set.
     */
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
    
    /**
     * @return Returns the shippingnoticesMoney.
     */
    public double getShippingnoticesMoney() {
        return shippingnoticesMoney;
    }
    /**
     * @param shippingnoticesMoney The shippingnoticesMoney to set.
     */
    public void setShippingnoticesMoney(double shippingnoticesMoney) {
        this.shippingnoticesMoney = shippingnoticesMoney;
    }
    
	public Order getOrder() {
		return Order;
	}
	public void setOrder(Order order) {
		Order = order;
	}
	public Collection getItems() {
		return items;
	}
	public void setItems(Collection items) {
		this.items = items;
	}
	
	public double getDiscount_fee() {
		return discount_fee;
	}

	public void setDiscount_fee(double discount_fee) {
		this.discount_fee = discount_fee;
	}
    
}
