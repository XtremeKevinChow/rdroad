package com.magic.crm.order.entity;

/**
 * 购物车其他信息
 * @author user
 *
 */
public class OtherInfo {
	
	/* 缺货处理方式(2:全部商品到齐发货；3:不等待，取消订单中缺货商品) */
	private int OOSPlan = 2;
	
	/* 是否需要发票 */
	private String needInvoice = "0";
	
	/* 发票抬头 */
	private String invoice_title;
	
	/* 发票号 */
	private String invoice_number;
	
	/* 信用卡号 */
	private String credit_card;
	/* 身份证号 */
	private String id_card;
	
	/*信用卡有效期 年*/
	private int ef_year;
	/*信用卡有效期 月*/
	private int ef_month;
	/*信用卡CV2号码*/
	private String ver_code;
	
	/* 付款折扣*/
	// 付款方式折扣和折扣金额
	private double paydiscount = 1;
	//private double discount_fee = 0;
	
	public double getPaydiscount() {
		return paydiscount;
	}
	public void setPaydiscount(double paydiscount) {
		this.paydiscount = paydiscount;
	}
	/*public double getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(double discount_fee) {
		this.discount_fee = discount_fee;
	}*/
	
	public String getInvoice_title() {
		return invoice_title;
	}

	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	/* 备注信息 */
	private String remark = null;
	
	/* 关联目录 */
	private String catalog = null;

	public String getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	public int getOOSPlan() {
		return OOSPlan;
	}

	public void setOOSPlan(int plan) {
		OOSPlan = plan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getCredit_card() {
		return credit_card;
	}
	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public int getEf_year() {
		return ef_year;
	}
	public void setEf_year(int ef_year) {
		this.ef_year = ef_year;
	}
	public int getEf_month() {
		return ef_month;
	}
	public void setEf_month(int ef_month) {
		this.ef_month = ef_month;
	}
	public String getVer_code() {
		return ver_code;
	}
	public void setVer_code(String ver_code) {
		this.ver_code = ver_code;
	}
	
	
}
