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
	//״̬ 0-�½�; 1-���; 2-�ر�; -1;��Ч
	private int status;
	private Date beginDate;
	private Date endDate;
	private int createPerson;
	private Date createDate;
	private int checkPerson;
	private Date checkDate;
	// �һ���ʽ A-һ���Զһ�(��Ӧ�ʻ�old_amount_exp);B-ʵʱ�һ�(��Ӧ�ʻ� amount_exp)
	private String exchangeType;
	// �ʻ�����ʽ A-�ʻ����� B-�ʻ��ɶ�ζһ�
	private String dealType;
	//��Ʒ�ݴ�����������
	private Date giftLastDate;
	private String headHtml;
	//���� 1-���ֶһ�;2-���ܻ�Ա�һ� add by user 2008-04-03
	private int activityType = -1;
	
	//���ֵ���<ExpExchangeStepMst>
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
	 * ��ʼ����������ϸ
	 * @param count
	 */
	public void init(int count) {
		for (int i = 0; i < count; i ++) {
			this.getMstList().add(new ExpExchangeStepMst());
		}
	}
	/**
	 *ɾ����ϸ
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
	 * ɾ������״̬�ĵ�����ϸ����
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
	 * ���������õ����ֵ���
	 * @param rowIndex
	 * @return
	 */
	public ExpExchangeStepMst getStepByRow(int rowIndex) {
		List list = (List)this.mstList;
		return (ExpExchangeStepMst)list.get(rowIndex);
	}
	
	/**
	 * ɾ�����ֵ���
	 * @param rowIndex
	 */
	public void removeStepByIndex(int rowIndex) {
		List list = (List)this.mstList;
		list.remove(rowIndex);
	}
	
	/**
	 * ͨ���кŵõ�����
	 * @param rowIndex
	 * @return
	 */
	public ExpExchangeStepMst getStepMstByIndex(int rowIndex) {
		List list = (List)this.mstList;
		return (ExpExchangeStepMst)list.get(rowIndex);
	}
	
	/**
	 * ɾ�����ֵ���
	 * @param rowIndex
	 */
	public void removeStep(ExpExchangeStepMst mst) {
		
		this.getMstList().remove(mst);
	}
	
	/**
	 * �������ֵ���
	 *
	 */
	public void createStep() {
		ExpExchangeStepMst mst = new ExpExchangeStepMst();
		this.mstList.add(mst);
	}
	
	/**
	 * �õ����ֵ�����ϸ
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
	 * ɾ�����ֵ�����ϸ
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
	 * ɾ�����ֵ�����ϸ
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
	 * ɾ�����ֵ�����ϸ
	 * @param param
	 */
	public void removeStepGift(ExpExchangeStepDtl dtl) {
		dtl.getStepMst().getDtlList().remove(dtl);
	}
	
	/**
	 * �������ֵ�����ϸ
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
