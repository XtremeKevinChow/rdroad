package com.magic.exchange;

import java.io.Serializable;

import com.magic.crm.util.DBManagerMS;

/**
 * 产品信息Bean
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class ProductInfo implements Serializable {
	private String item_code;

	private String item_name;

	private String barcode;

	private String ISBN;

	private String category_id;

	private int item_type_id;

	private String publishing_house;

	private String author = null;

	private float standard_price;

	private int is_commitment;

	private String item_size;

	private int inventory_status;

	private float common_price;

	private float card_price;

	private float web_price;

	private String catalog_code;

	private int Inventory_quantity;

	private String comments;

	private DBManagerMS linkserver;

	private int is_last_sale;
	
	private int club_id;

	/**
	 * @return Returns the club_id.
	 */
	public int getClub_id() {
		return club_id;
	}
	/**
	 * @param club_id The club_id to set.
	 */
	public void setClub_id(int club_id) {
		this.club_id = club_id;
	}
	public String getCatalog_code() {
		return catalog_code;
	}

	public void setCatalog_code(String catalog_code) {
		this.catalog_code = catalog_code;
	}

	public ProductInfo() {
	}

	/**
	 * @return Returns the author.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            The author to set.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return Returns the barcode.
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode
	 *            The barcode to set.
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return Returns the card_price.
	 */
	public float getCard_price() {
		return card_price;
	}

	/**
	 * @param card_price
	 *            The card_price to set.
	 */
	public void setCard_price(float card_price) {
		this.card_price = card_price;
	}

	/**
	 * @return Returns the common_price.
	 */
	public float getCommon_price() {
		return common_price;
	}

	/**
	 * @param common_price
	 *            The common_price to set.
	 */
	public void setCommon_price(float common_price) {
		this.common_price = common_price;
	}

	/**
	 * @return Returns the inventory_status.
	 */
	public int getInventory_status() {
		return inventory_status;
	}

	/**
	 * @param inventory_status
	 *            The inventory_status to set.
	 */
	public void setInventory_status(int inventory_status) {
		this.inventory_status = inventory_status;
	}

	/**
	 * @return Returns the is_commitment.
	 */
	public int getIs_commitment() {
		return is_commitment;
	}

	/**
	 * @param is_commitment
	 *            The is_commitment to set.
	 */
	public void setIs_commitment(int is_commitment) {
		this.is_commitment = is_commitment;
	}

	/**
	 * @return Returns the iSBN.
	 */
	public String getISBN() {
		return ISBN;
	}

	/**
	 * @param isbn
	 *            The iSBN to set.
	 */
	public void setISBN(String isbn) {
		ISBN = isbn;
	}

	/**
	 * @return Returns the item_code.
	 */
	public String getItem_code() {
		return item_code;
	}

	/**
	 * @param item_code
	 *            The item_code to set.
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
	 * @param item_name
	 *            The item_name to set.
	 */
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	/**
	 * @return Returns the item_size.
	 */
	public String getItem_size() {
		return item_size;
	}

	/**
	 * @param item_size
	 *            The item_size to set.
	 */
	public void setItem_size(String item_size) {
		this.item_size = item_size;
	}

	/**
	 * @return Returns the item_type_id.
	 */
	public int getItem_type_id() {
		return item_type_id;
	}

	/**
	 * @param item_type_id
	 *            The item_type_id to set.
	 */
	public void setItem_type_id(int item_type_id) {
		this.item_type_id = item_type_id;
	}

	/**
	 * @return Returns the publishing_house.
	 */
	public String getPublishing_house() {
		return publishing_house;
	}

	/**
	 * @param publishing_house
	 *            The publishing_house to set.
	 */
	public void setPublishing_house(String publishing_house) {
		this.publishing_house = publishing_house;
	}

	/**
	 * @return Returns the standard_price.
	 */
	public float getStandard_price() {
		return standard_price;
	}

	/**
	 * @param standard_price
	 *            The standard_price to set.
	 */
	public void setStandard_price(float standard_price) {
		this.standard_price = standard_price;
	}

	/**
	 * @return Returns the web_price.
	 */
	public float getWeb_price() {
		return web_price;
	}

	/**
	 * @param web_price
	 *            The web_price to set.
	 */
	public void setWeb_price(float web_price) {
		this.web_price = web_price;
	}

	/**
	 * @return Returns the category_id.
	 */
	public String getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id
	 *            The category_id to set.
	 */
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public void set_Inventory_quantity(int Inventory_quantity) {
		this.Inventory_quantity = Inventory_quantity;
	}

	public int get_Inventory_quantity() {
		return Inventory_quantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public DBManagerMS getLinkserver() {
		return linkserver;
	}

	public void setLinkserver(DBManagerMS linkserver) {
		this.linkserver = linkserver;
	}

	/**
	 * @return Returns the is_last_sale.
	 */
	public int getIs_last_sale() {
		return is_last_sale;
	}

	/**
	 * @param is_last_sale
	 *            The is_last_sale to set.
	 */
	public void setIs_last_sale(int is_last_sale) {
		this.is_last_sale = is_last_sale;
	}
}