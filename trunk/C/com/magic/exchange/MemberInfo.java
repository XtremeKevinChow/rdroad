package com.magic.exchange;

import java.io.Serializable;
import java.util.Date;


/**
 * 会员信息更新实现
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class MemberInfo implements Serializable {
	
	private String name;

	private String card_id;
	private String old_card_number;

	/**
	 * @return Returns the old_card_number.
	 */
	public String getOld_card_number() {
		return old_card_number;
	}
	/**
	 * @param old_card_number The old_card_number to set.
	 */
	public void setOld_card_number(String old_card_number) {
		this.old_card_number = old_card_number;
	}
	
	private int is_make_card = 1;
	
	private String gender;

	private Date birthday;

	private String company_phone;

	private String family_phone;

	private String telephone;

	private String email;

	private int certificate_type;

	private String certificate_code;

	private String v_usephone;

	private String v_postcode;

	private String v_address;
	//会员的网下id
	private int member_id;

	private String giftCode = "";
	private int recommenderNetshopId = 0;
	private int RECOMMENDED_ID = 0;
	private int GIFT_ID = 0;

	//网站会员网站id
	private String temp_b2cmbr_uid;

	private int join_other;

	//是否接收电子目录
	private int catalog_type;

	//新加的字段 取值0，1 0为普通，1为联名
	private int card_type;
	
	private int level_id;
	private float deposit;
	private float emoney;
	private float frozen_credit;
	private int exp;
	private int year_exp;
	private int old_year_exp;


	public void setRECOMMENDED_ID(int iRECOMMENDED_ID) {
		this.RECOMMENDED_ID = iRECOMMENDED_ID;
	}

	public int getRECOMMENDED_ID() {
		return RECOMMENDED_ID;
	}

	public void setGIFT_ID(int iGIFT_ID) {
		this.GIFT_ID = iGIFT_ID;
	}

	public int getGIFT_ID() {
		return GIFT_ID;
	}

	public String getTemp_b2cmbr_uid() {
		return temp_b2cmbr_uid;
	}

	public void setTemp_b2cmbr_uid(String temp_b2cmbr_uid) {
		this.temp_b2cmbr_uid = temp_b2cmbr_uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public String getCertificate_code() {
		return certificate_code;
	}

	public void setCertificate_code(String certificate_code) {
		this.certificate_code = certificate_code;
	}

	public int getCertificate_type() {
		return certificate_type;
	}

	public void setCertificate_type(int certificate_type) {
		this.certificate_type = certificate_type;
	}

	public String getCompany_phone() {
		return company_phone;
	}

	public void setCompany_phone(String company_phone) {
		this.company_phone = company_phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFamily_phone() {
		return family_phone;
	}

	public void setFamily_phone(String family_phone) {
		this.family_phone = family_phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getV_address() {
		return v_address;
	}

	public void setV_address(String v_address) {
		this.v_address = v_address;
	}

	public String getV_postcode() {
		return v_postcode;
	}

	public void setV_postcode(String v_postcode) {
		this.v_postcode = v_postcode;
	}

	public String getV_usephone() {
		return v_usephone;
	}

	public void setV_usephone(String v_usephone) {
		this.v_usephone = v_usephone;
	}

	public MemberInfo() {
		
	}

	

	

	public void setJoin_other(int join_other) {
		this.join_other = join_other;
	}

	public int getJoin_other() {
		return join_other;
	}

	public void set_join_other(int join_other) {
		this.join_other = join_other;
	}

	public int get_join_other() {
		return join_other;
	}

	public void setCatalog_type(int catalog_type) {
		this.catalog_type = catalog_type;
	}

	public int getCatalog_type() {
		return catalog_type;
	}

	public void set_catalog_type(int catalog_type) {
		this.catalog_type = catalog_type;
	}

	public int get_catalog_type() {
		return catalog_type;
	}

	public void setCard_type(int card_type) {
		this.card_type = card_type;
	}

	public int getCard_type() {
		return card_type;
	}

	/**
	 * @return Returns the deposit.
	 */
	public float getDeposit() {
		return deposit;
	}
	/**
	 * @param deposit The deposit to set.
	 */
	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}
	/**
	 * @return Returns the emoney.
	 */
	public float getEmoney() {
		return emoney;
	}
	/**
	 * @param emoney The emoney to set.
	 */
	public void setEmoney(float emoney) {
		this.emoney = emoney;
	}
	/**
	 * @return Returns the exp.
	 */
	public int getExp() {
		return exp;
	}
	/**
	 * @param exp The exp to set.
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}
	/**
	 * @return Returns the frozen_credit.
	 */
	public float getFrozen_credit() {
		return frozen_credit;
	}
	/**
	 * @param frozen_credit The frozen_credit to set.
	 */
	public void setFrozen_credit(float frozen_credit) {
		this.frozen_credit = frozen_credit;
	}
	/**
	 * @return Returns the level_id.
	 */
	public int getLevel_id() {
		return level_id;
	}
	/**
	 * @param level_id The level_id to set.
	 */
	public void setLevel_id(int level_id) {
		this.level_id = level_id;
	}
	/**
	 * @return Returns the old_year_exp.
	 */
	public int getOld_year_exp() {
		return old_year_exp;
	}
	/**
	 * @param old_year_exp The old_year_exp to set.
	 */
	public void setOld_year_exp(int old_year_exp) {
		this.old_year_exp = old_year_exp;
	}
	/**
	 * @return Returns the year_exp.
	 */
	public int getYear_exp() {
		return year_exp;
	}
	/**
	 * @param year_exp The year_exp to set.
	 */
	public void setYear_exp(int year_exp) {
		this.year_exp = year_exp;
	}
	/**
	 * @return Returns the is_make_card.
	 */
	public int getIs_make_card() {
		return is_make_card;
	}
	/**
	 * @param is_make_card The is_make_card to set.
	 */
	public void setIs_make_card(int is_make_card) {
		this.is_make_card = is_make_card;
	}
	/**
	 * @return Returns the giftCode.
	 */
	public String getGiftCode() {
		return giftCode;
	}
	/**
	 * @param giftCode The giftCode to set.
	 */
	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
	/**
	 * @return Returns the recommenderNetshopId.
	 */
	public int getRecommenderNetshopId() {
		return recommenderNetshopId;
	}
	/**
	 * @param recommenderNetshopId The recommenderNetshopId to set.
	 */
	public void setRecommenderNetshopId(int recommenderNetshopId) {
		this.recommenderNetshopId = recommenderNetshopId;
	}
}