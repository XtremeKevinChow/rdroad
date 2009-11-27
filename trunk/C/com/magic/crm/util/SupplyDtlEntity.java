/*
 * Created on 2005-8-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

import java.io.Serializable;

/**
 * @author zhux
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SupplyDtlEntity implements Serializable {
	private long supply_dtl_id;        
    private String supply_no;         
    private String correspond_no;
    private int correspond_qty;
	private long item_id;
	private String item_code;
	private String item_name;
    private String target_region;
    private String target_shelf;      
    private int target_qty;    
    private String source_region;     
    private String source_shelf;      
    private int source_qty;        
    private int qty;              

	/**
	 * @return Returns the correspond_no.
	 */
	public String getCorrespond_no() {
		return correspond_no;
	}
	/**
	 * @param correspond_no The correspond_no to set.
	 */
	public void setCorrespond_no(String correspond_no) {
		this.correspond_no = correspond_no;
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
	 * @return Returns the source_qty.
	 */
	public int getSource_qty() {
		return source_qty;
	}
	/**
	 * @param source_qty The source_qty to set.
	 */
	public void setSource_qty(int source_qty) {
		this.source_qty = source_qty;
	}
	/**
	 * @return Returns the source_region.
	 */
	public String getSource_region() {
		return source_region;
	}
	/**
	 * @param source_region The source_region to set.
	 */
	public void setSource_region(String source_region) {
		this.source_region = source_region;
	}
	/**
	 * @return Returns the source_shelf.
	 */
	public String getSource_shelf() {
		return source_shelf;
	}
	/**
	 * @param source_shelf The source_shelf to set.
	 */
	public void setSource_shelf(String source_shelf) {
		this.source_shelf = source_shelf;
	}
	/**
	 * @return Returns the supply_dtl_id.
	 */
	public long getSupply_dtl_id() {
		return supply_dtl_id;
	}
	/**
	 * @param supply_dtl_id The supply_dtl_id to set.
	 */
	public void setSupply_dtl_id(long supply_dtl_id) {
		this.supply_dtl_id = supply_dtl_id;
	}
	/**
	 * @return Returns the supply_no.
	 */
	public String getSupply_no() {
		return supply_no;
	}
	/**
	 * @param supply_no The supply_no to set.
	 */
	public void setSupply_no(String supply_no) {
		this.supply_no = supply_no;
	}
	/**
	 * @return Returns the target_qty.
	 */
	public int getTarget_qty() {
		return target_qty;
	}
	/**
	 * @param target_qty The target_qty to set.
	 */
	public void setTarget_qty(int target_qty) {
		this.target_qty = target_qty;
	}
	/**
	 * @return Returns the target_region.
	 */
	public String getTarget_region() {
		return target_region;
	}
	/**
	 * @param target_region The target_region to set.
	 */
	public void setTarget_region(String target_region) {
		this.target_region = target_region;
	}
	/**
	 * @return Returns the target_shelf.
	 */
	public String getTarget_shelf() {
		return target_shelf;
	}
	/**
	 * @param target_shelf The target_shelf to set.
	 */
	public void setTarget_shelf(String target_shelf) {
		this.target_shelf = target_shelf;
	}
	
	/**
	 * @return Returns the correspond_qty.
	 */
	public int getCorrespond_qty() {
		return correspond_qty;
	}
	/**
	 * @param correspond_qty The correspond_qty to set.
	 */
	public void setCorrespond_qty(int correspond_qty) {
		this.correspond_qty = correspond_qty;
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
}
