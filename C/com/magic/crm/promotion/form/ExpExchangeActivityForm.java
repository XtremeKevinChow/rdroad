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
	
	//��id
	private int rowId;
	//���ֵ���
	private int queryExp;
	// ��������id
	private long[] stepMstId;
	
	//���ֵ�������
	private int[] beginExp;
	//������ϸid����
	private long[] stepDtlId;
	
	//������������
	private String[] stepType;
	//��������
	private String[] no;
	//������С�������
	private double[] orderRequire;
	//����������
	private double[] addMoney;
	//��Ч��������
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
	 * session���ݺ�form����֮��ıȽ������
	 * @param dest
	 * @param type
	 */
	public void updateStep(ExpExchangeActivity dest, int type) {
		List list = (List)dest.getMstList();
		for (int k = 0; k < list.size(); k ++) {
			ExpExchangeStepMst stepMst = (ExpExchangeStepMst)list.get(k);
			if (this.getBeginExp() != null && this.getBeginExp().length > 0) {
				for (int i = 0; i < this.getBeginExp().length; i ++) {
					if (stepMst.getBeginExp() == this.getBeginExp()[i]) { //��ƥ���¼
						break;
						
					} else { //�Ҳ���ƥ�����ݣ�ɾ�����ΰ���������ϸ
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
		if (type == 0) {//��ҳ�����ݱ��ݵ�ʵ��
			if (this.beginExp == null || this.beginExp.length == 0) {
				return;
			}
			//����Session�е��ϵ�mst����
			Collection oldList = new ArrayList();
			oldList.addAll(dest.getMstList());
			
			
			for (int i = 0; i < this.beginExp.length; i ++) { //������
				
				if (this.beginExp[i] > 0 ) { //����δ�־û������ݽ��д���
					
					if (dest.getStatus() == -2) { //����״̬
						if (stepMstId[i] == 0) {
							ExpExchangeStepMst stepMst = new ExpExchangeStepMst();
							stepMst.setActivity(dest);
							stepMst.setBeginExp(beginExp[i]);
							stepMst.setId(this.stepMstId[i]);
							//��ԭsession�еĵ����������ݵ��µ�����
							Iterator it = dest.getMstList().iterator();
							while (it.hasNext()) {
								ExpExchangeStepMst oldStepMst = (ExpExchangeStepMst)it.next();
								if (stepMst.getBeginExp() == oldStepMst.getBeginExp() && oldStepMst.getId() == 0) {
									stepMst.setDtlList(oldStepMst.getDtlList()); //�����ϸ����
								}
								
							}
							dest.getMstList().add(stepMst);
						}
					} else if (dest.getStatus() == 0) {//�½�״̬
						ExpExchangeStepMst stepMst = new ExpExchangeStepMst();
						stepMst.setActivity(dest);
						stepMst.setBeginExp(beginExp[i]);
						stepMst.setId(this.stepMstId[i]);
						//��ԭsession�еĵ����������ݵ��µ�����
						Iterator it = dest.getMstList().iterator();
						while (it.hasNext()) {
							ExpExchangeStepMst oldStepMst = (ExpExchangeStepMst)it.next();
							if (stepMst.getBeginExp() == oldStepMst.getBeginExp()) {
								stepMst.setDtlList(oldStepMst.getDtlList()); //�����ϸ����
							}
						}
						dest.getMstList().add(stepMst);
					} 
					
				}
			}
			
			if (dest.getStatus() == -2) {
				//ɾ������״̬������
				Iterator it2 = oldList.iterator();
				while (it2.hasNext()) {
					ExpExchangeStepMst mst = (ExpExchangeStepMst)it2.next();
					if (mst.getId() == 0) {
						dest.getMstList().remove(mst);
					}
				}
			} else if (dest.getStatus() == 0) {
				//ɾ����������
				Iterator it2 = oldList.iterator();
				while (it2.hasNext()) {
					ExpExchangeStepMst mst = (ExpExchangeStepMst)it2.next();
					dest.getMstList().remove(mst);
					
				}
			}
		} 
		else if (type == 1) {//��ʵ�����ݱ��ݵ�ҳ��
			
		}
		
	}
	
	/**
	 * �½�״̬�����ڴ������еĵ�����ϸɾ�����������ϵĵ�����ϸ��ӽ������������������ݻ��ǳ־û������ݣ�
	 * ����״̬�����ڴ�������ĵ�����ϸɾ�����������ϵĵ�����ϸ��ӽ�����ֻ���������ݣ�
	 * @param dest
	 * @param type
	 */
	public void copyStepGift(ExpExchangeActivity dest, int type) {
		if (type == 0) {//��ҳ�����ݱ��ݵ�ʵ��
			if (this.beginExp == null || this.beginExp.length == 0) {
				return;
			}
			
			//����������
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
					
					if (dest.getStatus() == -2) { //����ֻ���������ݴ���
						if (this.stepDtlId[i] == 0 && stepMst.getBeginExp() == this.beginExp[i]) {//ֻ���������ݴ���ͬһ������
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
						if (stepMst.getBeginExp() == this.beginExp[i]) {//�½�״̬�����м�¼����
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
				//ɾ������״̬������
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
				//ɾ����������
				Iterator it2 = oldList.iterator();
				while (it2.hasNext()) {
					ExpExchangeStepMst mst = (ExpExchangeStepMst)it2.next();
					mst.getDtlList().clear();
					
				}
			}*/
		} 
		else if (type == 1) {//��ʵ�����ݱ��ݵ�ҳ��
			
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
