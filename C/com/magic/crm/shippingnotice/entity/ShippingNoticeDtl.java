/*
 * Created on 2005-5-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.shippingnotice.entity;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class ShippingNoticeDtl implements Serializable {
    
    private double goodQty = 0d;
    private double badQty = 0d;
    private String checkStatus = "";
    private String color_name = "";
    private String size_name = "";
	
    public String getColor_name() {
		return color_name;
	}
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}
	public String getSize_name() {
		return size_name;
	}
	public void setSize_name(String size_name) {
		this.size_name = size_name;
	}
	/**
     * @return Returns the checkStatus.
     */
    public String getCheckStatus() {
        return checkStatus;
    }
    /**
     * @param checkStatus The checkStatus to set.
     */
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
    /**
     * @return Returns the badQty.
     */
    public double getBadQty() {
        return badQty;
    }
    /**
     * @param badQty The badQty to set.
     */
    public void setBadQty(double badQty) {
        this.badQty = badQty;
    }
    /**
     * @return Returns the goodQty.
     */
    public double getGoodQty() {
        return goodQty;
    }
    /**
     * @param goodQty The goodQty to set.
     */
    public void setGoodQty(double goodQty) {
        this.goodQty = goodQty;
    }
	long id; 
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the item_code.
	 */
	public String getItem_code() {
		return item_code;
	}
	/**
	 * @param item_code The item_code to set.
	 */
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	/**
	 * @return Returns the item_id.
	 */
	public long getItem_id() {
		return item_id;
	}
	/**
	 * @param item_id The item_id to set.
	 */
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	/**
	 * @return Returns the item_name.
	 */
	public String getItem_name() {
		return item_name;
	}
	/**
	 * @param item_name The item_name to set.
	 */
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return Returns the qty.
	 */
	public int getQty() {
		return qty;
	}
	/**
	 * @param qty The qty to set.
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
	/**
	 * @return Returns the ref_sn_id.
	 */
	public long getRef_sn_id() {
		return ref_sn_id;
	}
	/**
	 * @param ref_sn_id The ref_sn_id to set.
	 */
	public void setRef_sn_id(long ref_sn_id) {
		this.ref_sn_id = ref_sn_id;
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
	 * @return Returns the total.
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * @param total The total to set.
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	long ref_sn_id;
	long item_id;
	String item_code;
	String item_name;
	int qty;
	double price;
	double total;
	int status;
	String status_name;
	String comments;
	private long refOrderLineId = 0;
	private String setItemID = null;
	private int operatorID = 0;
	
    /**
     * @return Returns the operatorID.
     */
    public int getOperatorID() {
        return operatorID;
    }
    /**
     * @param operatorID The operatorID to set.
     */
    public void setOperatorID(int operatorID) {
        this.operatorID = operatorID;
    }
    /**
     * @return Returns the setItemID.
     */
    public String getSetItemID() {
        return setItemID;
    }
    /**
     * @param setItemID The setItemID to set.
     */
    public void setSetItemID(String setItemID) {
        this.setItemID = setItemID;
    }
    /**
     * @return Returns the refOrderLineId.
     */
    public long getRefOrderLineId() {
        return refOrderLineId;
    }
    /**
     * @param refOrderLineId The refOrderLineId to set.
     */
    public void setRefOrderLineId(long refOrderLineId) {
        this.refOrderLineId = refOrderLineId;
    }
	/**
	 * @return Returns the comments.
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
