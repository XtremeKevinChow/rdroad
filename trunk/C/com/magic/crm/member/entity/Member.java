/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.entity;

import com.magic.utils.Arith;
/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Member {
	
	protected int	ID= 0;
	protected String	CARD_ID= "";
	protected int	CLUB_ID= 0;
	protected String	NAME= "";
	protected String	BIRTHDAY= "";
	protected String	TELEPHONE= "";
	protected int	LEVEL_ID= 0;
	protected int	LIGUIDATION= 0;
	protected int	EFFECTIVE_STATUS= 0;
	protected int	TIME_STATUS= 0;
	protected int	LEAVING_STATUS= 0;
	protected int	EXP= 0;
	protected int	DELVIERY_TYPE= 0;
	protected double	DEPOSIT= 0;
	protected String	FAMILY_PHONE= "";
	protected String	COMPANY_PHONE= "";
	protected String	EMAIL= "";
	protected String	ADDRESS_ID= "";
	protected String	GENDER= "";
	protected int	CATEGORY_ID= 0;
	protected String	IS_ORGANIZATION= "";
	protected String	CREATE_DATE= "";
	protected double	EMONEY= 0;
	protected int	CATALOG_TYPE= 0;
	protected int	CERTIFICATE_TYPE= 0;
	protected String	CERTIFICATE_CODE="";
	protected int	IS_CARD= 0;
	protected double	FORZEN_CREDIT= 0;
	protected int	FREE_COMMITMENT_PERIODS= 0;
	protected int	AMOUNT_EXP= 0;
	protected int	OLD_AMOUNT_EXP= 0;
	protected int	OLD_LIGUIDATION= 0;
	protected int	OLD_FREE_COMMITMENT_PERIODS= 0;
	protected String	OLD_LEVEL= "";
	protected int	JOIN_OTHER= 0;
	protected String	MSC_CODE= "";
	protected int	CARD_TYPE= 0;
	protected int	IS_MAKE_CARD= 0;
	protected int	NETSHOP_ID= 0;
	protected int	PURCHASE_COUNT= 0;
	protected int	ANIMUS_COUNT= 0;
	protected String	OLD_CARD_CODE= "";
	protected String	VALID_FLAG= "";
	protected double	FROZEN_EMONEY= 0;
	protected String Postcode="";
	protected String AddressDetail="";
	protected String Mbr_get_mbr="";
	protected String gift_id="";
	protected String COMMENTS="";
	protected String address="";//省
	protected String address1="";//市
	protected int	creator_id= 0;
	protected int	modifier_id= 0;
	protected String	modify_date= "";
	protected boolean isBlacklistMember = false;
	protected String blackRemark;//拉入黑名单原因
	private boolean oldMember = false;
	private String realPostcode;
	private String section = "";

public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	private String sectionName = "";
	
	
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
	protected int gift_num = 0;
	
	
    public int getGift_num() {
		return gift_num;
	}

	public void setGift_num(int gift_num) {
		this.gift_num = gift_num;
	}

	/**
	 * @return the blackRemark
	 */
	public String getBlackRemark() {
		return blackRemark;
	}
	
	/**
	 * @param blackRemark the blackRemark to set
	 */
	public void setBlackRemark(String blackRemark) {
		this.blackRemark = blackRemark;
	}

	public String getRealPostcode() {
		return realPostcode == null ? "" : realPostcode;
	}
	public void setRealPostcode(String realPostcode) {
		this.realPostcode = realPostcode;
	}
	public boolean isOldMember() {
		return oldMember;
	}
	public void setOldMember(boolean oldMember) {
		this.oldMember = oldMember;
	}
	/**
     * @return Returns the isBlacklistMember.
     */
    public boolean isBlacklistMember() {
        return isBlacklistMember;
    }
    /**
     * @param isBlacklistMember The isBlacklistMember to set.
     */
    public void setBlacklistMember(boolean isBlacklistMember) {
        this.isBlacklistMember = isBlacklistMember;
    }
    
    
       public Member(){
       	
       }
       public String	getCOMMENTS(){
	   	  return this.COMMENTS;
	   }
	   public void	setCOMMENTS(String iCOMMENTS){
	   	   this.COMMENTS=iCOMMENTS;
	   }  
       public String	getGift_id(){
	   	  return gift_id == null ? "" : gift_id;
	   }
	   public void	setGift_id(String igift_id){
	   	   this.gift_id=igift_id;
	   } 	   
       public String	getPostcode(){
	     	  return this.Postcode;
	     }
	   public void	setPostcode(String iPostcode){
	   	   this.Postcode=iPostcode;
	   }  
       public String	getMbr_get_mbr(){
	   	  return this.Mbr_get_mbr;
	   }
	   public void	setMbr_get_mbr(String iMbr_get_mbr){
	   	   this.Mbr_get_mbr=iMbr_get_mbr;
	   }  
       public String	getAddress(){
	   	  return this.address ;
	   }
	   public void	setAddress(String iAddress){
	   	   this.address=iAddress;
	   }		   
       public String	getAddressDetail(){
	   	  return this.AddressDetail;
	   }
       public void	setAddressDetail(String iAddressDetail){
	   	   this.AddressDetail=iAddressDetail;
	   }	
       public String	getAddress1(){
	   	  return this.address1;
	   }
	   public void	setAddress1(String iAddress1){
	   	   this.address1=iAddress1;
	   }   
	      
       public int	getID(){
       	  return this.ID;
       }
	   public String	getCARD_ID(){
	   	  return this.CARD_ID;
	   }
	   public int	getCLUB_ID(){
	   	return this.CLUB_ID  ;	   	
	   }
	   public String	getNAME(){
	   	return this.NAME;	
	   }
	   public String	getBIRTHDAY(){
	   	return this.BIRTHDAY ;
	   }
	   public String	getTELEPHONE(){
	   	return this.TELEPHONE ;
	   }
	   public int	getLEVEL_ID(){
	   	return this.LEVEL_ID ;
	   }
	   public int	getLIGUIDATION(){
	   	return this.LIGUIDATION ;
	   }
	   public int	getEFFECTIVE_STATUS(){
	   	return this.EFFECTIVE_STATUS ;
	   }
	   public int	getTIME_STATUS(){
	   	return this.TIME_STATUS ;
	   }
	   public int	getLEAVING_STATUS(){
	   	return this.LEAVING_STATUS ;
	   }
	   public int	getEXP(){
	   	return this.EXP ;
	   }
	   public int	getDELVIERY_TYPE(){
	   	return this.DELVIERY_TYPE ;
	   }
	   public double	getDEPOSIT(){
	   	return this.DEPOSIT ;
	   }
	   public String	getFAMILY_PHONE(){
	   	return this.FAMILY_PHONE ;
	   }
	   public String	getCOMPANY_PHONE(){
	   	return this.COMPANY_PHONE ;
	   }
	   public String	getEMAIL(){
	   	return this.EMAIL ;
	   }
	   public String	getADDRESS_ID(){
	   	return this.ADDRESS_ID ;
	   }
	   public String	getGENDER(){
	   	return this.GENDER ;
	   }
	   
	   public int	getCATEGORY_ID(){
	   	return this.CATEGORY_ID;
	   }
	   public String	getIS_ORGANIZATION(){
	   	return this.IS_ORGANIZATION;
	   }
	   public String	getCREATE_DATE(){
	   	return this.CREATE_DATE;
	   }
	   public double	getEMONEY(){
	   	return this.EMONEY;
	   }
	   public int	getCATALOG_TYPE(){
	   	return this.CATALOG_TYPE;
	   }
	   public int	getCERTIFICATE_TYPE(){
	   	return this.CERTIFICATE_TYPE;
	   }
	   public String	getCERTIFICATE_CODE(){
	   	return this.CERTIFICATE_CODE;
	   }
	   public int	getIS_CARD(){
	   	return this.IS_CARD;
	   }
	   public double	getFORZEN_CREDIT(){
	   	return this.FORZEN_CREDIT;
	   }
	   public int	getFREE_COMMITMENT_PERIODS(){
	   	return this.FREE_COMMITMENT_PERIODS;
	   }
	   public int	getAMOUNT_EXP(){
	   	return this.AMOUNT_EXP;
	   }
	   public int	getOLD_AMOUNT_EXP(){
	   	return this.OLD_AMOUNT_EXP;
	   }
	   public int	getOLD_LIGUIDATION(){
	   	return this.OLD_LIGUIDATION;
	   }
	   public int	getOLD_FREE_COMMITMENT_PERIODS(){
	   	return this.OLD_FREE_COMMITMENT_PERIODS;
	   }
	   public String	getOLD_LEVEL(){
	   	return this.OLD_LEVEL;
	   }
	   public int	getJOIN_OTHER(){
	   	return this.JOIN_OTHER;
	   }
	   public String	getMSC_CODE(){
	   	return this.MSC_CODE ;
	   }
	   public int	getCARD_TYPE(){
	   	return this.CARD_TYPE;
	   }
	   public int	getIS_MAKE_CARD(){
	   	return this.IS_MAKE_CARD;
	   }
	   public int	getNETSHOP_ID(){
	   	return this.NETSHOP_ID ;
	   }
	   public int	getPURCHASE_COUNT(){
	   	return this.PURCHASE_COUNT ;
	   }
	   public int	getANIMUS_COUNT(){
	   	return this.ANIMUS_COUNT ;
	   }
	   public String	getOLD_CARD_CODE(){
	   	return this.OLD_CARD_CODE;
	   }
	   public String	getVALID_FLAG(){
	   	return this.VALID_FLAG ;
	   }
	   public double	getFROZEN_EMONEY(){
	   	return this.FROZEN_EMONEY ;
	   }
	   public int	getCreator_id(){
	   	return this.creator_id ;
	   }
	   public int	getModifier_id(){
	   	return this.modifier_id ;
	   }
	   public String	getModify_date(){
	   	return this.modify_date ;
	   }
       //******************** set *************************
	   
	   //******************** set *************************
	   public void setCreator_id(int icreator_id){
	   	this.creator_id =icreator_id;
	   }
	   public void setModifier_id(int imodifier_id){
	   	this.modifier_id =imodifier_id;
	   }	   
	   public void setID(int id){
	   	this.ID =id;
	   }
	   public void setCARD_ID(String card_id){
	   	this.CARD_ID =card_id;
	   }
	   public void setCLUB_ID(int club_id){
	   	this.CLUB_ID =club_id;
	   }
	   public void setNAME(String name){
	   	this.NAME =name;
	   }
	   public void setBIRTHDAY(String birthday){
	   	this.BIRTHDAY =birthday;
	   }
	   public void setTELEPHONE(String telephone){
	   	this.TELEPHONE =telephone;
	   }
	   public void setLEVEL_ID(int level_id){
	   	this.LEVEL_ID =level_id;
	   }
	   public void setLIGUIDATION(int liguidation){
	   	this.LIGUIDATION =liguidation;
	   }
	   public void setEFFECTIVE_STATUS(int effective_id){
	   	this.EFFECTIVE_STATUS =effective_id;
	   }
	   public void setTIME_STATUS(int time_status){
	   	this.TIME_STATUS =time_status;
	   }
	   public void setLEAVING_STATUS(int leaving_status){
	   	this.LEAVING_STATUS =leaving_status;
	   }
	   public void setEXP(int exp){
	   	this.EXP =exp;
	   }
	   public void setDELVIERY_TYPE(int delviery){
	   	this.DELVIERY_TYPE =delviery;
	   }
	   public void setDEPOSIT(double  deposit){
	   	this.DEPOSIT =deposit;
	   }
	   public void setFAMILY_PHONE(String family_phone){
	   	this.FAMILY_PHONE =family_phone;
	   }
	   public void setCOMPANY_PHONE(String company_phone){
	   	this.COMPANY_PHONE =company_phone;
	   }
	   public void setEMAIL(String email){
	   	this.EMAIL =email;
	   }
	   public void setADDRESS_ID(String addressid){
	   	this.ADDRESS_ID =addressid;
	   }
	   public void setGENDER(String gender){
	   	this.GENDER =gender;
	   }
	   public void setCATEGORY_ID(int category){
	   	this.CATEGORY_ID =category;
	   }
	   public void setIS_ORGANIZATION(String is_organization){
	   	this.IS_ORGANIZATION =is_organization;
	   }
	   public void setCREATE_DATE(String create_date){
	   	this.CREATE_DATE =create_date;
	   }
	   public void setEMONEY(double emoney){
	   	this.EMONEY =emoney;
	   }
	   public void setCATALOG_TYPE(int catalog_type){
	   	this.CATALOG_TYPE =catalog_type;
	   }
	   public void setCERTIFICATE_TYPE(int certificate_type){
	   	this.CERTIFICATE_TYPE =certificate_type;
	   }
	   public void setCERTIFICATE_CODE(String certificate_code){
	   	this.CERTIFICATE_CODE =certificate_code;
	   }
	   public void setIS_CARD(int is_card){
	   	this.IS_CARD =is_card;
	   }
	   public void setFORZEN_CREDIT(double forzen_credit){
	   	this.FORZEN_CREDIT =forzen_credit;
	   }
	   public void setFREE_COMMITMENT_PERIODS(int free_commitment_periods){
	   	this.FREE_COMMITMENT_PERIODS =free_commitment_periods;
	   }
	   public void setAMOUNT_EXP(int amount_exp){
	   	this.AMOUNT_EXP =amount_exp;
	   }
	   public void setOLD_AMOUNT_EXP(int old_amount_exp){
	   	this.OLD_AMOUNT_EXP =old_amount_exp;
	   }
	   public void setOLD_LIGUIDATION(int old_liguidation){
	   	this.OLD_LIGUIDATION =old_liguidation;
	   }
	   public void setOLD_FREE_COMMITMENT_PERIODS(int old_free_commitment_periods){
	   	this.OLD_FREE_COMMITMENT_PERIODS =old_free_commitment_periods;
	   }
	   public void setOLD_LEVEL(String old_level){
	   	this.OLD_LEVEL =old_level;
	   }
	   public void setJOIN_OTHER(int join_other){
	   	this.JOIN_OTHER =join_other;
	   }
	   public void setMSC_CODE(String msc_code){
	   	this.MSC_CODE =msc_code;
	   }
	   public void setCARD_TYPE(int card_type){
	   	this.CARD_TYPE =card_type;
	   }
	   public void setIS_MAKE_CARD(int is_make_card){
	   	this.IS_MAKE_CARD =is_make_card;
	   }
	   public void setNETSHOP_ID(int netshop_id){
	   	this.NETSHOP_ID =netshop_id;
	   }
	   public void setPURCHASE_COUNT(int purchase_count){
	   	this.PURCHASE_COUNT =purchase_count;
	   }
	   public void setANIMUS_COUNT(int animus_count){
	   	this.ANIMUS_COUNT =animus_count;
	   }
	   public void setOLD_CARD_CODE(String old_card_code){
	   	this.OLD_CARD_CODE =old_card_code;
	   }
	   public void setVALID_FLAG(String valid_flag){
	   	this.VALID_FLAG =valid_flag;
	   }
	   public void setFROZEN_EMONEY(double frozen_emoney){
	   	this.FROZEN_EMONEY =frozen_emoney;
	   }
	   public double getUsefulMoney () {
		   return Arith.round(this.DEPOSIT - this.FORZEN_CREDIT + this.EMONEY - this.FROZEN_EMONEY , 2);
	   }
}
