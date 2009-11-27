package com.magic.crm.promotion.entity;

import java.sql.Date;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import com.magic.crm.promotion.form.ExpExchangeActivityForm;

public class ExpExchangeActivity {
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
	
	//积分档次<ExpExchangeStepMst>
	Collection mstList = new ArrayList();
	
	Collection deletedMstList = new ArrayList();
	
	Collection deletedDtlList = new ArrayList();
	
	public Collection getDeletedDtlList() {
		return deletedDtlList;
	}
	public void setDeletedDtlList(Collection deletedDtlList) {
		this.deletedDtlList = deletedDtlList;
	}
	public Collection getDeletedMstList() {
		return deletedMstList;
	}
	public void setDeletedMstList(Collection deletedMstList) {
		this.deletedMstList = deletedMstList;
	}
	public Collection getMstList() {
		return mstList;
	}
	public void setMstList(Collection mstList) {
		this.mstList = mstList;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getGiftLastDate() {
		return giftLastDate;
	}
	public void setGiftLastDate(Date giftLastDate) {
		this.giftLastDate = giftLastDate;
	}
	public String getHeadHtml() {
		return headHtml;
	}
	public void setHeadHtml(String headHtml) {
		this.headHtml = headHtml;
	}
	/**
	 * 初始化若干条明细
	 * @param count
	 */
	public void init(int count) {
		for (int i = 0; i < count; i ++) {
			this.getMstList().add(new ExpExchangeStepMst());
		}
	}
	/**
	 *删除明细
	 * @param count
	 */
	public void removeAllStep() {
		
		this.getMstList().clear();
		
	}
	
	public void removeAllStepGift() {
		Iterator it = this.getMstList().iterator();
		while (it.hasNext()) {
			ExpExchangeStepMst mst = (ExpExchangeStepMst)it.next();
			mst.getDtlList().clear();
		}
	}
	
	/**
	 * 删除游离状态的档次明细数据
	 *
	 */
	public void removeNewStepGift() {
		List list = (List)this.getMstList();
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
		
	}
	
	/**
	 * 根据索引得到积分档次
	 * @param rowIndex
	 * @return
	 */
	public ExpExchangeStepMst getStepByRow(int rowIndex) {
		List list = (List)this.mstList;
		return (ExpExchangeStepMst)list.get(rowIndex);
	}
	
	/**
	 * 删除积分档次
	 * @param rowIndex
	 */
	public void removeStepByIndex(int rowIndex) {
		List list = (List)this.mstList;
		list.remove(rowIndex);
	}
	
	/**
	 * 通过行号得到档次
	 * @param rowIndex
	 * @return
	 */
	public ExpExchangeStepMst getStepMstByIndex(int rowIndex) {
		List list = (List)this.mstList;
		return (ExpExchangeStepMst)list.get(rowIndex);
	}
	
	/**
	 * 删除积分档次
	 * @param rowIndex
	 */
	public void removeStep(ExpExchangeStepMst mst) {
		
		this.getMstList().remove(mst);
	}
	
	/**
	 * 新增积分档次
	 *
	 */
	public void createStep() {
		ExpExchangeStepMst mst = new ExpExchangeStepMst();
		this.mstList.add(mst);
	}
	
	/**
	 * 得到积分档次明细
	 * @param param
	 * @return
	 */
	public ExpExchangeStepDtl getStepDtlByRow(ExpExchangeActivityForm param) {
		Iterator it = this.getMstList().iterator();
		while (it.hasNext()) {
			ExpExchangeStepMst mst = (ExpExchangeStepMst)it.next();
			if (mst.getBeginExp() == param.getQueryExp()) {
				List list = (List)mst.getDtlList();
				return (ExpExchangeStepDtl)list.get(param.getRowId());
			}
		}
		return null;
	}
	
	/**
	 * 删除积分档次明细
	 * @param beginExp
	 * @param rowIndex
	 */
	public void removeStepGiftByIndex(int beginExp, int rowIndex) {
		Iterator it = this.getMstList().iterator();
		while (it.hasNext()) {
			ExpExchangeStepMst mst = (ExpExchangeStepMst)it.next();
			if (mst.getBeginExp() == beginExp) {
				List list = (List)mst.getDtlList();
				list.remove(rowIndex);
			}
		}
	}
	
	/**
	 * 删除积分档次明细
	 * @param param
	 */
	public void removeStepGiftByIndex(ExpExchangeActivityForm param) {
		Iterator it = this.getMstList().iterator();
		while (it.hasNext()) {
			ExpExchangeStepMst mst = (ExpExchangeStepMst)it.next();
			if (mst.getBeginExp() == param.getQueryExp()) {
				List list = (List)mst.getDtlList();
				list.remove(param.getRowId());
			}
		}
	}
	
	/**
	 * 删除积分档次明细
	 * @param param
	 */
	public void removeStepGift(ExpExchangeStepDtl dtl) {
		dtl.getStepMst().getDtlList().remove(dtl);
	}
	
	/**
	 * 新增积分档次明细
	 * @param beginExp
	 */
	public void createStepGiftByExp(int beginExp) {
		ExpExchangeStepDtl dtl = new ExpExchangeStepDtl();
		dtl.setEnabled(true);
		
		dtl.setBeginDate(this.beginDate);
		dtl.setEndDate(this.endDate);
		Iterator it = this.getMstList().iterator();
		while (it.hasNext()) {
			ExpExchangeStepMst mst = (ExpExchangeStepMst)it.next();
			if (mst.getBeginExp() == beginExp) {
				mst.getDtlList().add(dtl);
			}
		}
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
