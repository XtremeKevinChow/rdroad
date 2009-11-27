/**
 * DiamondTimes.java
 * 2008-4-7
 * ����12:57:19
 * user
 * DiamondTimes
 */
package com.magic.crm.promotion.entity;

import java.sql.Date;
/**
 * @author user
 *
 */
public class DiamondTimes {
	//pk
	private int timeId;
	//fk
	private int actionId;
	//�һ�����
	private int times;
	//��Ա����
	private int mbrLevel;
	//ʱ������(1-��Ȼ�£�2-�ϴζһ���)
	private String timeType;
	//����
	private int days = 1;
	//������
	private int operator;
	//����ʱ��
	private Date opTime;
	//״̬
	private int status;
	//�Ƿ�ֻ��
	private boolean readOnly;
	//ɾ������
	public boolean isDeleteAble() { //�½�����˿�ɾ��
		return (status == 3 ? false : true);
	}
	//��˿���
	public boolean isCheckAble() { // �½������
		return (status == 1 && getTimeId() > 0) ? true : false;
	}
	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return (status == 1 ? false : true);
	}
	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	/**
	 * @return the actionId
	 */
	public int getActionId() {
		return actionId;
	}
	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}
	/**
	 * @param days the days to set
	 */
	public void setDays(int days) {
		this.days = days;
	}
	/**
	 * @return the mbrLevel
	 */
	public int getMbrLevel() {
		return mbrLevel;
	}
	/**
	 * @param mbrLevel the mbrLevel to set
	 */
	public void setMbrLevel(int mbrLevel) {
		this.mbrLevel = mbrLevel;
	}
	/**
	 * @return the operator
	 */
	public int getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(int operator) {
		this.operator = operator;
	}
	/**
	 * @return the opTime
	 */
	public Date getOpTime() {
		return opTime;
	}
	/**
	 * @param opTime the opTime to set
	 */
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the timeId
	 */
	public int getTimeId() {
		return timeId;
	}
	/**
	 * @param timeId the timeId to set
	 */
	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}
	/**
	 * @return the times
	 */
	public int getTimes() {
		return times;
	}
	/**
	 * @param times the times to set
	 */
	public void setTimes(int times) {
		this.times = times;
	}
	/**
	 * @return the timeType
	 */
	public String getTimeType() {
		return timeType;
	}
	/**
	 * @param timeType the timeType to set
	 */
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	
}
