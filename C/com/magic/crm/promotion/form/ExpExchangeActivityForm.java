package com.magic.crm.promotion.form;

import java.sql.Date;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.struts.action.ActionForm;

import com.magic.crm.promotion.entity.ExpExchangeActivity;
import com.magic.crm.promotion.entity.ExpExchangeStepMst;
import com.magic.crm.promotion.entity.ExpExchangeStepDtl;

public class ExpExchangeActivityForm extends ActionForm {
	private String activityNo;
	private String activityDesc;
	//状态 0-新建; 1-审核; 2-关闭; -1;无效
	private int status;
	private Date beginDate;
	private Date endDate;
	private int createPerson;
	private Date createDate;
	private int checkPerson;
	private Date checkDate;
	// 兑换方式 A-一次性兑换(对应帐户old_amount_exp);B-实时兑换(对应帐户 amount_exp)
	private String exchangeType;
	// 帐户处理方式 A-帐户清零 B-帐户可多次兑换
	private String dealType;
	//礼品暂存架最后保留日期
	private Date giftLastDate;
	private String headHtml;
	//分类 1-积分兑换;2-介绍会员兑换 add by user 2008-04-03
	private int activityType = -1;
	
	//行id
	private int rowId;
	//积分档次
	private int queryExp;
	// 档次数组id
	private long[] stepMstId;
	
	//积分档次数组
	private int[] beginExp;
	//档次明细id数组
	private long[] stepDtlId;
	
	//赠送类型数组
	private String[] stepType;
	//号码数组
	private String[] no;
	//订单最小金额数组
	private double[] orderRequire;
	//额外金额数组
	private double[] addMoney;
	//有效日期数组
	private Date[] stepBeginDate;
	private Date[] stepEndDate;
	private int oldStatus;
	
	public int getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(int oldStatus) {
		this.oldStatus = oldStatus;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public int getCheckPerson() {
		return checkPerson;
	}
	public void setCheckPerson(int checkPerson) {
		this.checkPerson = checkPerson;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(int createPerson) {
		this.createPerson = createPerson;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public Date getGiftLastDate() {
		return giftLastDate;
	}
	public void setGiftLastDate(Date giftLastDate) {
		this.giftLastDate = giftLastDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getHeadHtml() {
		return headHtml;
	}
	public void setHeadHtml(String headHtml) {
		this.headHtml = headHtml;
	}
	public double[] getAddMoney() {
		return addMoney;
	}
	public void setAddMoney(double[] addMoney) {
		this.addMoney = addMoney;
	}
	public int[] getBeginExp() {
		return beginExp;
	}
	public void setBeginExp(int[] beginExp) {
		this.beginExp = beginExp;
	}
	public String[] getNo() {
		return no;
	}
	public void setNo(String[] no) {
		this.no = no;
	}
	public double[] getOrderRequire() {
		return orderRequire;
	}
	public void setOrderRequire(double[] orderRequire) {
		this.orderRequire = orderRequire;
	}
	public Date[] getStepBeginDate() {
		return stepBeginDate;
	}
	public void setStepBeginDate(Date[] stepBeginDate) {
		this.stepBeginDate = stepBeginDate;
	}
	public Date[] getStepEndDate() {
		return stepEndDate;
	}
	public void setStepEndDate(Date[] stepEndDate) {
		this.stepEndDate = stepEndDate;
	}
	public String[] getStepType() {
		return stepType;
	}
	public void setStepType(String[] stepType) {
		this.stepType = stepType;
	}
	
	/**
	 * session数据和form数据之间的比较与更新
	 * @param dest
	 * @param type
	 */
	public void updateStep(ExpExchangeActivity dest, int type) {
		List list = (List)dest.getMstList();
		for (int k = 0; k < list.size(); k ++) {
			ExpExchangeStepMst stepMst = (ExpExchangeStepMst)list.get(k);
			if (this.getBeginExp() != null && this.getBeginExp().length > 0) {
				for (int i = 0; i < this.getBeginExp().length; i ++) {
					if (stepMst.getBeginExp() == this.getBeginExp()[i]) { //有匹配记录
						break;
						
					} else { //找不到匹配数据，删除档次包括档次明细
						if (i == this.getBeginExp().length - 1) {
							list.remove(stepMst);
							break;
						}
						
					}
				}
			}
		}
	}
	
	public void copyActivity(ExpExchangeActivity dest, int type) {
		if (type == 0) {
		
			dest.setActivityNo(this.getActivityNo());
			dest.setActivityDesc(this.getActivityDesc());
			dest.setBeginDate(this.getBeginDate());
			dest.setEndDate(this.getEndDate());
			dest.setGiftLastDate(this.getGiftLastDate());
			dest.setExchangeType(this.getExchangeType());
			dest.setDealType(this.getDealType());
			dest.setHeadHtml(this.getHeadHtml());
			dest.setActivityType(this.activityType);// add by user 2008-04-03
		} 
		else if (type == 1) {
		
			this.setActivityNo(dest.getActivityNo());
			this.setActivityDesc(dest.getActivityDesc());
			this.setBeginDate(dest.getBeginDate());
			this.setEndDate(dest.getEndDate());
			this.setGiftLastDate(dest.getGiftLastDate());
			this.setExchangeType(dest.getExchangeType());
			this.setDealType(dest.getDealType());
			this.setHeadHtml(dest.getHeadHtml());
			this.setActivityType(dest.getActivityType());// add by user 2008-04-03
		}
		
	}
	
	public void copyStep(ExpExchangeActivity dest, int type) {
		if (type == 0) {//将页面数据备份到实体
			if (this.beginExp == null || this.beginExp.length == 0) {
				return;
			}
			//备份Session中的老的mst数据
			Collection oldList = new ArrayList();
			oldList.addAll(dest.getMstList());
			
			
			for (int i = 0; i < this.beginExp.length; i ++) { //表单数据
				
				if (this.beginExp[i] > 0 ) { //对于未持久化的数据进行处理
					
					if (dest.getStatus() == -2) { //弃审状态
						if (stepMstId[i] == 0) {
							ExpExchangeStepMst stepMst = new ExpExchangeStepMst();
							stepMst.setActivity(dest);
							stepMst.setBeginExp(beginExp[i]);
							stepMst.setId(this.stepMstId[i]);
							//将原session中的档次明拷贝份到新的数据
							Iterator it = dest.getMstList().iterator();
							while (it.hasNext()) {
								ExpExchangeStepMst oldStepMst = (ExpExchangeStepMst)it.next();
								if (stepMst.getBeginExp() == oldStepMst.getBeginExp() && oldStepMst.getId() == 0) {
									stepMst.setDtlList(oldStepMst.getDtlList()); //完成明细备份
								}
								
							}
							dest.getMstList().add(stepMst);
						}
					} else if (dest.getStatus() == 0) {//新建状态
						ExpExchangeStepMst stepMst = new ExpExchangeStepMst();
						stepMst.setActivity(dest);
						stepMst.setBeginExp(beginExp[i]);
						stepMst.setId(this.stepMstId[i]);
						//将原session中的档次明拷贝份到新的数据
						Iterator it = dest.getMstList().iterator();
						while (it.hasNext()) {
							ExpExchangeStepMst oldStepMst = (ExpExchangeStepMst)it.next();
							if (stepMst.getBeginExp() == oldStepMst.getBeginExp()) {
								stepMst.setDtlList(oldStepMst.getDtlList()); //完成明细备份
							}
						}
						dest.getMstList().add(stepMst);
					} 
					
				}
			}
			
			if (dest.getStatus() == -2) {
				//删除游离状态的数据
				Iterator it2 = oldList.iterator();
				while (it2.hasNext()) {
					ExpExchangeStepMst mst = (ExpExchangeStepMst)it2.next();
					if (mst.getId() == 0) {
						dest.getMstList().remove(mst);
					}
				}
			} else if (dest.getStatus() == 0) {
				//删除所有数据
				Iterator it2 = oldList.iterator();
				while (it2.hasNext()) {
					ExpExchangeStepMst mst = (ExpExchangeStepMst)it2.next();
					dest.getMstList().remove(mst);
					
				}
			}
		} 
		else if (type == 1) {//将实体数据备份到页面
			
		}
		
	}
	
	/**
	 * 新建状态：将内存中所有的档次明细删除，将界面上的档次明细添加进来（不论是游离数据还是持久化的数据）
	 * 弃审状态：将内存中游离的档次明细删除，将界面上的档次明细添加进来（只是游离数据）
	 * @param dest
	 * @param type
	 */
	public void copyStepGift(ExpExchangeActivity dest, int type) {
		if (type == 0) {//将页面数据备份到实体
			if (this.beginExp == null || this.beginExp.length == 0) {
				return;
			}
			
			//备份老数据
			if (dest.getStatus() == -2) {
				dest.removeNewStepGift();
			} else if (dest.getStatus() == 0) {
				dest.removeAllStepGift();
			}
			
			
			Collection stepMstList = dest.getMstList();
			Iterator it = stepMstList.iterator();
			while (it.hasNext()) {
				ExpExchangeStepMst stepMst = (ExpExchangeStepMst)it.next();
				for (int i = 0; i < this.beginExp.length; i ++) {
					
					if (dest.getStatus() == -2) { //弃审只对游离数据处理
						if (this.stepDtlId[i] == 0 && stepMst.getBeginExp() == this.beginExp[i]) {//只对游离数据处理，同一个档次
							ExpExchangeStepDtl stepDtl = new ExpExchangeStepDtl();
							stepDtl.setId(this.stepDtlId[i]);
							stepDtl.setStepType(this.stepType[i]);
							stepDtl.setNo(this.no[i]);
							stepDtl.setOrderRequire(0);
							stepDtl.setEnabled(true);
							//stepDtl.setAddMoney(this.addMoney[i]);
							stepDtl.setAddMoney(0);
							stepDtl.setBeginDate(this.stepBeginDate[i]);
							stepDtl.setEndDate(this.stepEndDate[i]);
							stepDtl.setStepMst(stepMst);
							stepMst.getDtlList().add(stepDtl);
							
						}
					} else if (dest.getStatus() == 0) {
						if (stepMst.getBeginExp() == this.beginExp[i]) {//新建状态对所有记录处理
							ExpExchangeStepDtl stepDtl = new ExpExchangeStepDtl();
							stepDtl.setId(this.stepDtlId[i]);
							stepDtl.setStepType(this.stepType[i]);
							stepDtl.setNo(this.no[i]);
							stepDtl.setOrderRequire(0);
							stepDtl.setEnabled(true);
							stepDtl.setAddMoney(this.addMoney[i]);
							stepDtl.setBeginDate(this.stepBeginDate[i]);
							stepDtl.setEndDate(this.stepEndDate[i]);
							stepDtl.setStepMst(stepMst);
							stepMst.getDtlList().add(stepDtl);
							
						}
					}
					
				}
			}
			/*if (dest.getStatus() == -2) {
				//删除游离状态的数据
				List list = (List)oldList;
				for (int i = 0; i < list.size(); i ++) {
					ExpExchangeStepMst mst = (ExpExchangeStepMst)list.get(i);
					List list2 = (List)mst.getDtlList();
					for (int j = 0; j < list2.size(); j ++) {
						ExpExchangeStepDtl dtl = (ExpExchangeStepDtl)list2.get(j);
						if (dtl.getId() == 0) {
							list2.remove(j--);
						}
					}
				}
			} else if (dest.getStatus() == 0) {
				//删除所有数据
				Iterator it2 = oldList.iterator();
				while (it2.hasNext()) {
					ExpExchangeStepMst mst = (ExpExchangeStepMst)it2.next();
					mst.getDtlList().clear();
					
				}
			}*/
		} 
		else if (type == 1) {//将实体数据备份到页面
			
		}
		
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public int getQueryExp() {
		return queryExp;
	}
	public void setQueryExp(int queryExp) {
		this.queryExp = queryExp;
	}
	public long[] getStepMstId() {
		return stepMstId;
	}
	public void setStepMstId(long[] stepMstId) {
		this.stepMstId = stepMstId;
	}
	public long[] getStepDtlId() {
		return stepDtlId;
	}
	public void setStepDtlId(long[] stepDtlId) {
		this.stepDtlId = stepDtlId;
	}
	/**
	 * @return the activityType
	 */
	public int getActivityType() {
		return activityType;
	}
	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	
}
