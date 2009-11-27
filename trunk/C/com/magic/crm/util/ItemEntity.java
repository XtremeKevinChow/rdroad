package com.magic.crm.util;
import java.io.Serializable;
/*
 * Created on 2005-7-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ItemEntity implements Serializable {
	int sku_id =0;
	String item_code = "";
	String item_name = "";
	int quantiy = 0;
	double std_price =0;
	double price = 0;
	String item_type ="";
	String item_pos = "";
	String comments = "";
	String shelf_no = "";
	String color_name = "";
	String size_name = "";
	String barcode = "";
	int sellType = 0;
	long orderLineId =0 ;
	
}
