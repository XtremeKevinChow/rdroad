/*
 * Created on 2005-1-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import java.util.ArrayList;
import java.util.List;
import com.magic.crm.member.entity.Member;
import com.magic.crm.common.WebForm;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberForm extends WebForm {
	/**
	 * @return Returns the nPageCount.
	 */
	public String getPageNavigator() {

		StringBuffer buff = new StringBuffer();
		buff
				.append("\t\t  <table width=\"100%\" border=\"0\" align=\"center\">\n");
		buff.append("\t\t    <tr>\n");
		buff.append("\t\t      <td nowrap>共");
		buff.append(pa.getRecordCount());
		buff.append("条记录<input type=\"hidden\" name=\"recordCount\" value=\"");
		buff.append(pa.getRecordCount());
		buff.append("\">&nbsp;分");
		buff.append(pa.getPageCount());
		buff.append("页显示<input type=\"hidden\" name=\"pageCount\" value=\"");
		buff.append(pa.getPageCount());
		buff.append("\">&nbsp;\t\t当前第");
		buff.append(pa.getPageNo());
		buff.append("页<input type=\"hidden\" name=\"pageNo\" value=\"");
		buff.append(pa.getPageNo());
		buff.append("\"></td>\n");
		buff.append("\t\t<td nowrap align=\"right\">\n");
		// buff.append("\t\t\t <ww:if test=\"pageNo > 1\">\n");
		// 1,2,3页
		if (pa.getPageCount() > 1) {
			buff.append("\t\t\t\t <a href=\"javascript:toPage(1)\">第一页&nbsp;</a>\n");

		}
		if (pa.getPageCount() >= 2) {
			buff.append("\t\t\t\t <a href=\"javascript:toPage(2)\">第二页&nbsp;</a>\n");

		}
		if (pa.getPageCount() >= 3) {
			buff.append("\t\t\t\t <a href=\"javascript:toPage(3)\">第三页&nbsp;</a>\n");
		}

		buff.append("\t\t      </td>\n");
		buff.append("\t\t    </tr>\n");
		buff.append("\t\t  </table>\n");

		return buff.toString();
	}

	protected ArrayList provs = new ArrayList();
	protected ArrayList citys = new ArrayList();
	protected ArrayList sects = new ArrayList();
	
	protected String province = "";
	protected String city = "";
	protected String section = "";
	protected String taobaowangid= "";
	
	public String getTaobaoWangId()
	{
		return taobaowangid;
	}
	public void setTaobaoWangId(String value)
	{
		taobaowangid = value;
	}
	
	protected String MemgetmemID = "";
	
	public String getMemgetmemID() {
		return MemgetmemID;
	}

	public void setMemgetmemID(String memgetmemID) {
		MemgetmemID = memgetmemID;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public ArrayList getProvs() {
		return provs;
	}

	public void setProvs(ArrayList provs) {
		this.provs = provs;
	}

	public ArrayList getCitys() {
		return citys;
	}

	public void setCitys(ArrayList citys) {
		this.citys = citys;
	}

	public ArrayList getSects() {
		return sects;
	}

	public void setSects(ArrayList sects) {
		this.sects = sects;
	}

	/** 会员id * */
	public int ID = 0;

	/** 会员卡号 * */
	public String CARD_ID = "";

	/** 俱乐部id * */
	public int CLUB_ID = 0;

	/** 会员姓名 * */
	public String NAME = "";

	/** 生日 * */
	public String BIRTHDAY = "";

	/** 手机 * */
	public String TELEPHONE = "";

	/** 会员等级id * */
	public int LEVEL_ID = -1;

	/** 清算状态* */
	public int LIGUIDATION = 0;

	/** 活动状态* */
	public int EFFECTIVE_STATUS = 0;

	/** 阶段状态* */
	public int TIME_STATUS = 0;

	public int LEAVING_STATUS = 0;

	/** 累计积分 * */
	public int EXP = 0;

	/** 送货方式 * */
	public int DELVIERY_TYPE = 0;

	/** 帐户 * */
	public double DEPOSIT = 0;

	/** 家庭电话 * */
	public String FAMILY_PHONE = "";

	/** 单位电话 * */
	public String COMPANY_PHONE = "";

	/** 电子邮件 * */
	public String EMAIL = "";

	/** 送货地址id * */
	public String ADDRESS_ID = "";

	/** 性别 * */
	public String GENDER = "M";

	public int CATEGORY_ID = 0;

	/** 是否团购会员(0普通；1团购) * */
	public String IS_ORGANIZATION = "";

	/** 入会日期 * */
	public String CREATE_DATE = "";

	/** e元 * */
	public int EMONEY = 0;

	/** 接收目录类型 * */
	public int CATALOG_TYPE = 0;

	/** 证件类型 * */
	public int CERTIFICATE_TYPE = 0;

	/** 证件号 * */
	public String CERTIFICATE_CODE = "";

	/** 是否制卡 * */
	public int IS_CARD = 0;

	/** 冻结款 * */
	public double FORZEN_CREDIT = 0;

	/** 员免义务周期数 * */
	public int FREE_COMMITMENT_PERIODS = 0;

	/** 本年度积分 * */
	public int AMOUNT_EXP = 0;

	/** 上年度积分 * */
	public int OLD_AMOUNT_EXP = 0;

	/** 上期清算状态 * */
	public int OLD_LIGUIDATION = 0;

	/** 上周期会员免义务周期 * */
	public int OLD_FREE_COMMITMENT_PERIODS = 0;

	/** 上周期会员等级 * */
	public String OLD_LEVEL = "";

	/** 是否参加过其他俱乐部 * */
	public int JOIN_OTHER = 0;

	/** MSC代码 * */
	public String MSC_CODE = "";

	/** 发卡类型 * */
	public int CARD_TYPE = 0;

	/** 是否需要制卡 * */
	public int IS_MAKE_CARD = 0;

	/** 对应的网上ID * */
	public int NETSHOP_ID = 0;

	/** 会员购买次数 * */
	public int PURCHASE_COUNT = 0;

	/** 会员恶意退货次数 * */
	public int ANIMUS_COUNT = 0;

	/** 原会员号 * */
	public String OLD_CARD_CODE = "";

	/** 是否有效 * */
	public String VALID_FLAG = "";

	/** ??? * */
	public int FROZEN_EMONEY = 0;

	public String postcode = "";

	protected String AddressDetail = "";
	protected String delivery_address = "";
	protected String event_date = "";
	

	public String getEvent_date() {
		return event_date;
	}

	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}

	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	/** 创建人id * */
	protected int creator_id = 0;
	protected String creator_name = "";

	public String getCreator_name() {
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	/** 修改人id * */
	protected int modifier_id = 0;

	/** 修改时间 * */
	protected String modify_date = "";
	
	/** 会员备注 */
	protected String COMMENTS = "";
	
	public String getCOMMENTS() {
		return COMMENTS;
	}

	public void setCOMMENTS(String comments) {
		COMMENTS = comments;
	}

	/*
	 * 会员事件查询 event_type_list,所有事件类型
	 */
	protected ArrayList TypeList = new ArrayList();

	public ArrayList getTypeList() {
		return TypeList;
	}

	public void setTypeList(ArrayList iTypeList) {
		this.TypeList = iTypeList;
	}

	protected int event_type = 0;

	public int getevent_type() {
		return this.event_type;
	}

	public void setevent_type(int ievent_type) {
		this.event_type = ievent_type;
	}

	/** ************************************ */
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String iPostcode) {
		this.postcode = iPostcode;
	}

	public String getAddressDetail() {
		return this.AddressDetail;
	}

	public void setAddressDetail(String iAddressDetail) {
		this.AddressDetail = iAddressDetail;
	}

	public int getID() {
		return this.ID;
	}

	public String getCARD_ID() {
		return this.CARD_ID;
	}

	public int getCLUB_ID() {
		return this.CLUB_ID;
	}

	public String getNAME() {
		return this.NAME;
	}

	public String getBIRTHDAY() {
		return this.BIRTHDAY;
	}

	public String getTELEPHONE() {
		return this.TELEPHONE;
	}

	public int getLEVEL_ID() {
		return this.LEVEL_ID;
	}

	public int getLIGUIDATION() {
		return this.LIGUIDATION;
	}

	public int getEFFECTIVE_STATUS() {
		return this.EFFECTIVE_STATUS;
	}

	public int getTIME_STATUS() {
		return this.TIME_STATUS;
	}

	public int getLEAVING_STATUS() {
		return this.LEAVING_STATUS;
	}

	public int getEXP() {
		return this.EXP;
	}

	public int getDELVIERY_TYPE() {
		return this.DELVIERY_TYPE;
	}

	public double getDEPOSIT() {
		return this.DEPOSIT;
	}

	public String getFAMILY_PHONE() {
		return this.FAMILY_PHONE;
	}

	public String getCOMPANY_PHONE() {
		return this.COMPANY_PHONE;
	}

	public String getEMAIL() {
		return this.EMAIL;
	}

	public String getADDRESS_ID() {
		return this.ADDRESS_ID;
	}

	public String getGENDER() {
		return this.GENDER;
	}

	public int getCATEGORY_ID() {
		return this.CATEGORY_ID;
	}

	public String getIS_ORGANIZATION() {
		return this.IS_ORGANIZATION;
	}

	public String getCREATE_DATE() {
		return this.CREATE_DATE;
	}

	public int getEMONEY() {
		return this.EMONEY;
	}

	public int getCATALOG_TYPE() {
		return this.CATALOG_TYPE;
	}

	public int getCERTIFICATE_TYPE() {
		return this.CERTIFICATE_TYPE;
	}

	public String getCERTIFICATE_CODE() {
		return this.CERTIFICATE_CODE;
	}

	public int getIS_CARD() {
		return this.IS_CARD;
	}

	public double getFORZEN_CREDIT() {
		return this.FORZEN_CREDIT;
	}

	public int getFREE_COMMITMENT_PERIODS() {
		return this.FREE_COMMITMENT_PERIODS;
	}

	public int getAMOUNT_EXP() {
		return this.AMOUNT_EXP;
	}

	public int getOLD_AMOUNT_EXP() {
		return this.OLD_AMOUNT_EXP;
	}

	public int getOLD_LIGUIDATION() {
		return this.OLD_LIGUIDATION;
	}

	public int getOLD_FREE_COMMITMENT_PERIODS() {
		return this.OLD_FREE_COMMITMENT_PERIODS;
	}

	public String getOLD_LEVEL() {
		return this.OLD_LEVEL;
	}

	public int getJOIN_OTHER() {
		return this.JOIN_OTHER;
	}

	public String getMSC_CODE() {
		return this.MSC_CODE;
	}

	public int getCARD_TYPE() {
		return this.CARD_TYPE;
	}

	public int getIS_MAKE_CARD() {
		return this.IS_MAKE_CARD;
	}

	public int getNETSHOP_ID() {
		return this.NETSHOP_ID;
	}

	public int getPURCHASE_COUNT() {
		return this.PURCHASE_COUNT;
	}

	public int getANIMUS_COUNT() {
		return this.ANIMUS_COUNT;
	}

	public String getOLD_CARD_CODE() {
		return this.OLD_CARD_CODE;
	}

	public String getVALID_FLAG() {
		return this.VALID_FLAG;
	}

	public int getFROZEN_EMONEY() {
		return this.FROZEN_EMONEY;
	}

	public int getCreator_id() {
		return this.creator_id;
	}

	public int getModifier_id() {
		return this.modifier_id;
	}

	public String getModify_date() {
		return this.modify_date;
	}

	// ******************** set *************************

	// ******************** set *************************
	public void setCreator_id(int icreator_id) {
		this.creator_id = icreator_id;
	}

	public void setModifier_id(int imodifier_id) {
		this.modifier_id = imodifier_id;
	}

	public void setID(int id) {
		this.ID = id;
	}

	public void setCARD_ID(String card_id) {
		this.CARD_ID = card_id;
	}

	public void setCLUB_ID(int club_id) {
		this.CLUB_ID = club_id;
	}

	public void setNAME(String name) {
		this.NAME = name;
	}

	public void setBIRTHDAY(String birthday) {
		this.BIRTHDAY = birthday;
	}

	public void setTELEPHONE(String telephone) {
		this.TELEPHONE = telephone;
	}

	public void setLEVEL_ID(int level_id) {
		this.LEVEL_ID = level_id;
	}

	public void setLIGUIDATION(int liguidation) {
		this.LIGUIDATION = liguidation;
	}

	public void setEFFECTIVE_STATUS(int effective_id) {
		this.EFFECTIVE_STATUS = effective_id;
	}

	public void setTIME_STATUS(int time_status) {
		this.TIME_STATUS = time_status;
	}

	public void setLEAVING_STATUS(int leaving_status) {
		this.LEAVING_STATUS = leaving_status;
	}

	public void setEXP(int exp) {
		this.EXP = exp;
	}

	public void setDELVIERY_TYPE(int delviery) {
		this.DELVIERY_TYPE = delviery;
	}

	public void setDEPOSIT(double deposit) {
		this.DEPOSIT = deposit;
	}

	public void setFAMILY_PHONE(String family_phone) {
		this.FAMILY_PHONE = family_phone;
	}

	public void setCOMPANY_PHONE(String company_phone) {
		this.COMPANY_PHONE = company_phone;
	}

	public void setEMAIL(String email) {
		this.EMAIL = email;
	}

	public void setADDRESS_ID(String addressid) {
		this.ADDRESS_ID = addressid;
	}

	public void setGENDER(String gender) {
		this.GENDER = gender;
	}

	public void setCATEGORY_ID(int category) {
		this.CATEGORY_ID = category;
	}

	public void setIS_ORGANIZATION(String is_organization) {
		this.IS_ORGANIZATION = is_organization;
	}

	public void setCREATE_DATE(String create_date) {
		this.CREATE_DATE = create_date;
	}

	public void setEMONEY(int emoney) {
		this.EMONEY = emoney;
	}

	public void setCATALOG_TYPE(int catalog_type) {
		this.CATALOG_TYPE = catalog_type;
	}

	public void setCERTIFICATE_TYPE(int certificate_type) {
		this.CERTIFICATE_TYPE = certificate_type;
	}

	public void setCERTIFICATE_CODE(String certificate_code) {
		this.CERTIFICATE_CODE = certificate_code;
	}

	public void setIS_CARD(int is_card) {
		this.IS_CARD = is_card;
	}

	public void setFORZEN_CREDIT(double forzen_credit) {
		this.FORZEN_CREDIT = forzen_credit;
	}

	public void setFREE_COMMITMENT_PERIODS(int free_commitment_periods) {
		this.FREE_COMMITMENT_PERIODS = free_commitment_periods;
	}

	public void setAMOUNT_EXP(int amount_exp) {
		this.AMOUNT_EXP = amount_exp;
	}

	public void setOLD_AMOUNT_EXP(int old_amount_exp) {
		this.OLD_AMOUNT_EXP = old_amount_exp;
	}

	public void setOLD_LIGUIDATION(int old_liguidation) {
		this.OLD_LIGUIDATION = old_liguidation;
	}

	public void setOLD_FREE_COMMITMENT_PERIODS(int old_free_commitment_periods) {
		this.OLD_FREE_COMMITMENT_PERIODS = old_free_commitment_periods;
	}

	public void setOLD_LEVEL(String old_level) {
		this.OLD_LEVEL = old_level;
	}

	public void setJOIN_OTHER(int join_other) {
		this.JOIN_OTHER = join_other;
	}

	public void setMSC_CODE(String msc_code) {
		this.MSC_CODE = msc_code;
	}

	public void setCARD_TYPE(int card_type) {
		this.CARD_TYPE = card_type;
	}

	public void setIS_MAKE_CARD(int is_make_card) {
		this.IS_MAKE_CARD = is_make_card;
	}

	public void setNETSHOP_ID(int netshop_id) {
		this.NETSHOP_ID = netshop_id;
	}

	public void setPURCHASE_COUNT(int purchase_count) {
		this.PURCHASE_COUNT = purchase_count;
	}

	public void setANIMUS_COUNT(int animus_count) {
		this.ANIMUS_COUNT = animus_count;
	}

	public void setOLD_CARD_CODE(String old_card_code) {
		this.OLD_CARD_CODE = old_card_code;
	}

	public void setVALID_FLAG(String valid_flag) {
		this.VALID_FLAG = valid_flag;
	}

	public void setFROZEN_EMONEY(int frozen_emoney) {
		this.FROZEN_EMONEY = frozen_emoney;
	}

	/*
	 * 分页
	 */
	private List items = new ArrayList();

	public List getItems() {
		return items;
	}

	public void reset(org.apache.struts.action.ActionMapping mapping,
			javax.servlet.http.HttpServletRequest request) {
		ID = 0;
		AddressDetail = "";
		postcode = "";
		CARD_ID = "";
		CLUB_ID = 0;
		NAME = "";
		BIRTHDAY = "";
		TELEPHONE = "";
		LEVEL_ID = -1;
		LIGUIDATION = 0;
		EFFECTIVE_STATUS = 0;
		TIME_STATUS = 0;
		LEAVING_STATUS = 0;
		EXP = 0;
		DELVIERY_TYPE = 0;
		DEPOSIT = 0;
		FAMILY_PHONE = "";
		COMPANY_PHONE = "";
		EMAIL = null;
		ADDRESS_ID = "";
		GENDER = "M";
		CATEGORY_ID = 0;
		IS_ORGANIZATION = "";
		CREATE_DATE = "";
		EMONEY = 0;
		CATALOG_TYPE = 0;
		CERTIFICATE_TYPE = 0;
		CERTIFICATE_CODE = "";
		IS_CARD = 0;
		FORZEN_CREDIT = 0;
		FREE_COMMITMENT_PERIODS = 0;
		AMOUNT_EXP = 0;
		OLD_AMOUNT_EXP = 0;
		OLD_LIGUIDATION = 0;
		OLD_FREE_COMMITMENT_PERIODS = 0;
		OLD_LEVEL = "";
		JOIN_OTHER = 0;
		MSC_CODE = "";
		CARD_TYPE = 0;
		IS_MAKE_CARD = 0;
		NETSHOP_ID = 0;
		PURCHASE_COUNT = 0;
		ANIMUS_COUNT = 0;
		OLD_CARD_CODE = "";
		VALID_FLAG = "";
		FROZEN_EMONEY = 0;
		taobaowangid = "";
	}

	public void copy(Member target) {
		target.setTaobaoWangId(this.taobaowangid);
		target.setID(this.ID);
		target.setAddressDetail(this.AddressDetail);
		target.setPostcode(this.getPostcode());
		target.setCARD_ID(this.CARD_ID);
		target.setCLUB_ID(this.getCLUB_ID());
		target.setNAME(this.NAME);
		target.setBIRTHDAY(this.BIRTHDAY);
		target.setTELEPHONE(this.TELEPHONE);
		target.setLEVEL_ID(this.LEVEL_ID);
		target.setLIGUIDATION(this.LIGUIDATION);
		target.setEFFECTIVE_STATUS(EFFECTIVE_STATUS);
		target.setTIME_STATUS(this.TIME_STATUS);
		target.setLEAVING_STATUS(this.LEAVING_STATUS);
		target.setEXP(this.EXP);
		target.setDELVIERY_TYPE(this.DELVIERY_TYPE);
		target.setDEPOSIT(DEPOSIT);
		target.setFAMILY_PHONE(FAMILY_PHONE);
		target.setCOMPANY_PHONE(COMPANY_PHONE);
		target.setEMAIL(EMAIL);
		target.setADDRESS_ID(ADDRESS_ID);
		target.setGENDER(GENDER);
		target.setCATEGORY_ID(CATEGORY_ID);
		target.setIS_ORGANIZATION(IS_ORGANIZATION);
		target.setCREATE_DATE(CREATE_DATE);
		target.setEMONEY(EMONEY);
		target.setCATALOG_TYPE(CATALOG_TYPE);
		target.setCERTIFICATE_TYPE(CERTIFICATE_TYPE);
		target.setCERTIFICATE_CODE(CERTIFICATE_CODE);
		target.setIS_CARD(IS_CARD);
		target.setFORZEN_CREDIT(FORZEN_CREDIT);
		target.setFREE_COMMITMENT_PERIODS(FREE_COMMITMENT_PERIODS);
		target.setAMOUNT_EXP(AMOUNT_EXP);
		target.setOLD_AMOUNT_EXP(OLD_AMOUNT_EXP);
		target.setOLD_LIGUIDATION(OLD_LIGUIDATION);
		target.setOLD_FREE_COMMITMENT_PERIODS(OLD_FREE_COMMITMENT_PERIODS);
		target.setOLD_LEVEL(OLD_LEVEL);
		target.setJOIN_OTHER(JOIN_OTHER);
		target.setMSC_CODE(MSC_CODE);
		target.setCARD_TYPE(CARD_TYPE);
		target.setIS_MAKE_CARD(IS_MAKE_CARD);
		target.setNETSHOP_ID(NETSHOP_ID);
		target.setPURCHASE_COUNT(PURCHASE_COUNT);
		target.setANIMUS_COUNT(ANIMUS_COUNT);
		target.setOLD_CARD_CODE(OLD_CARD_CODE);
		target.setVALID_FLAG(VALID_FLAG);
		target.setFROZEN_EMONEY(FROZEN_EMONEY);
	}
}
