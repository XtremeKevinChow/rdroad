package com.magic.crm.order.entity;

/**
 * ���ﳵ������Ϣ
 * @author user
 *
 */
public class OtherInfo {
	
	/* ȱ������ʽ(2:ȫ����Ʒ���뷢����3:���ȴ���ȡ��������ȱ����Ʒ) */
	private int OOSPlan = 2;
	
	/* �Ƿ���Ҫ��Ʊ */
	private String needInvoice = "0";
	
	/* ��Ʊ̧ͷ */
	private String invoice_title;
	
	/* ��Ʊ�� */
	private String invoice_number;
	
	/* ���ÿ��� */
	private String credit_card;
	/* ���֤�� */
	private String id_card;
	
	/*���ÿ���Ч�� ��*/
	private int ef_year;
	/*���ÿ���Ч�� ��*/
	private int ef_month;
	/*���ÿ�CV2����*/
	private String ver_code;
	
	/* �����ۿ�*/
	// ���ʽ�ۿۺ��ۿ۽��
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

	/* ��ע��Ϣ */
	private String remark = null;
	
	/* ����Ŀ¼ */
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
