/*
 * Created on 2007-3-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;
import org.apache.struts.action.ActionForm;
/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mbr_ComplaintForm extends ActionForm{

	public int 	event_id=0;
	public String	cmpt_content="";
	public String	create_date="";
	public int 	cmpt_id=0;
	public int 	cmpt_type_id=0;
	public String 	cmpt_type_name="";
	public int 	cmpt_level=0;
	public int creator=0;
	public int mbr_id=0;
	public int cmpt_status=0;
	public String	last_deal_date="";
	public String	creator_name="";
	public int	dept_id=0;
	public int	type=1;//0投诉1咨询//默认为咨询
	public int	is_answer=0;//1需要恢复0不需要回去
	public String	card_id="";
	public String	parent_id="";
	
	/*
	 * get --- set 
	 */
	public String getParent_id(){
		return this.parent_id;
	}
	public void setParent_id(String parent_id){
		this.parent_id=parent_id;
	}
	public String getCard_id(){
		return this.card_id;
	}
	public void setCard_id(String card_id){
		this.card_id=card_id;
	}
	public int getIs_answer(){
		return this.is_answer;
	}
	public void setIs_answer(int is_answer){
		this.is_answer=is_answer;
	}		
	public int getType(){
		return this.type;
	}
	public void setType(int type){
		this.type=type;
	}	
	public int getEvent_id(){
		return this.event_id;
	}
	public void setEvent_id(int event_id){
		this.event_id=event_id;
	}
	
	public String getCmpt_content(){
		return this.cmpt_content;
	}
	public void setCmpt_content(String cmpt_content){
		this.cmpt_content=cmpt_content;
	}

	public String getCreate_date(){
		return this.create_date;
	}
	public void setCreate_date(String create_date){
		this.create_date=create_date;
	}
	public String getLast_deal_date(){
		return this.last_deal_date;
	}
	public void setLast_deal_date(String last_deal_date){
		this.last_deal_date=last_deal_date;
	}	
	public int getCmpt_id(){
		return this.cmpt_id;
	}
	public void setCmpt_id(int cmpt_id){
		this.cmpt_id=cmpt_id;
	}
	public int getCmpt_type_id(){
		return this.cmpt_type_id;
	}
	public void setCmpt_type_id(int cmpt_type_id){
		this.cmpt_type_id=cmpt_type_id;
	}
	public String getCmpt_type_name(){
		return this.cmpt_type_name;
	}
	public void setCmpt_type_name(String cmpt_type_name){
		this.cmpt_type_name=cmpt_type_name;
	}	
	public int getCmpt_level(){
		return this.cmpt_level;
	}
	public void setCmpt_level(int cmpt_level){
		this.cmpt_level=cmpt_level;
	}
	public int getCreator(){
		return this.creator;
	}
	public void setCreator(int creator){
		this.creator=creator;
	}
	public String getCreatorName(){
		return this.creator_name;
	}
	public void setCreatorName(String creator_name){
		this.creator_name=creator_name;
	}
	public int getDeptID(){
		return this.dept_id;
	}
	public void setDeptID(int dept_id){
		this.dept_id=dept_id;
	}
	public int getMbr_id(){
		return this.mbr_id;
	}
	public void setMbr_id(int mbr_id){
		this.mbr_id=mbr_id;
	}
	
	public int getCmpt_status(){
		return this.cmpt_status;
	}
	public void setCmpt_status(int cmpt_status){
		this.cmpt_status=cmpt_status;
	}	
	
}
