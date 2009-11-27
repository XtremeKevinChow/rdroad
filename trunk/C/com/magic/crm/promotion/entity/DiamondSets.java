/**
 * DiamondSets.java
 * 2008-4-7
 * 下午12:42:38
 * user
 * DiamondSets
 */
package com.magic.crm.promotion.entity;

import java.sql.Date;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * @author user
 *
 */
public class DiamondSets {
	//PK
	private int actionId;
	//描述
	private String actionDesc;
	//开始日期
	private Date beginDate;
	//结束日期
	private Date endDate;
	//每月得钻数
	private int setCount;
	//保留天数
	private int keepDays;
	//状态
	private int status;
	//操作人
	private int operator;
	//操作时间
	private Date opTime;
	//能否修改
	private boolean modifyAble;
	
	
	private Collection requireList = new ArrayList();
	
	private Collection exchangeList = new ArrayList();
	
	private Collection timesList = new ArrayList();
	
	
	/**
	 * @return the exchangeList
	 */
	public Collection getExchangeList() {
		return exchangeList;
	}
	/**
	 * @param exchangeList the exchangeList to set
	 */
	public void setExchangeList(Collection exchangeList) {
		this.exchangeList = exchangeList;
	}
	/**
	 * @return the requireList
	 */
	public Collection getRequireList() {
		return requireList;
	}
	/**
	 * @param requireList the requireList to set
	 */
	public void setRequireList(Collection requireList) {
		this.requireList = requireList;
	}
	/**
	 * @return the timesList
	 */
	public Collection getTimesList() {
		return timesList;
	}
	/**
	 * @param timesList the timesList to set
	 */
	public void setTimesList(Collection timesList) {
		this.timesList = timesList;
	}
	/**
	 * @return the actionDesc
	 */
	public String getActionDesc() {
		return actionDesc;
	}
	/**
	 * @param actionDesc the actionDesc to set
	 */
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
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
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * @return the setCount
	 */
	public int getSetCount() {
		return setCount;
	}
	/**
	 * @param setCount the setCount to set
	 */
	public void setSetCount(int setCount) {
		this.setCount = setCount;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the keepDays
	 */
	public int getKeepDays() {
		return keepDays;
	}
	/**
	 * @param keepDays the keepDays to set
	 */
	public void setKeepDays(int keepDays) {
		this.keepDays = keepDays;
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
	
	public void clearAllRequire() {
		this.requireList.clear();
	}
	public void clearAllExchange() {
		this.exchangeList.clear();
	}
	public void clearAllTime() {
		this.timesList.clear();
	}
	
	/**
	 * 初始化若干条明细
	 * @param count
	 */
	public void init(int count) {
		for (int i = 0; i < count; i ++) {
			this.getRequireList().add(new DiamondRequires());
			this.getTimesList().add(new DiamondTimes());
			this.getExchangeList().add(new DiamondExchange());
		}
	}
	
	/**
	 * 删除指定的DiamondRequires
	 * @param reqId
	 */
	public void removeRequire(int reqId) {
		Iterator it = this.getRequireList().iterator();
		while (it.hasNext()) {
			DiamondRequires require = (DiamondRequires) it.next();
			if (require.getReqId() == reqId) {
				this.getRequireList().remove(require);
				break;
			}
		}
	}
	/**
	 * 审核指定的DiamondRequires
	 * @param reqId
	 */
	public void checkRequire(int reqId) {
		Iterator it = this.getRequireList().iterator();
		while (it.hasNext()) {
			DiamondRequires require = (DiamondRequires) it.next();
			if (require.getReqId() == reqId) {
				require.setStatus(2);
				break;
			}
		}
	}
	/**
	 * 删除指定的DiamondTimes
	 * @param timeId
	 */
	public void removeTime(int timeId) {
		Iterator it = this.getTimesList().iterator();
		while (it.hasNext()) {
			DiamondTimes time = (DiamondTimes) it.next();
			if (time.getTimeId() == timeId) {
				this.getTimesList().remove(time);
				break;
			}
		}
	}
	/**
	 * 审核指定的DiamondTimes
	 * @param timeId
	 */
	public void checkTime(int timeId) {
		Iterator it = this.getTimesList().iterator();
		while (it.hasNext()) {
			DiamondTimes time = (DiamondTimes) it.next();
			if (time.getTimeId() == timeId) {
				time.setStatus(2);
				break;
			}
		}
	}
	/**
	 * 删除指定的DiamondExchange
	 * @param excId
	 */
	public void removeExchange(int excId) {
		Iterator it = this.getExchangeList().iterator();
		while (it.hasNext()) {
			DiamondExchange exchange = (DiamondExchange) it.next();
			if (exchange.getExcId() == excId) {
				this.getExchangeList().remove(exchange);
				break;
			}
		}
	}
	/**
	 * 审核指定的DiamondExchange
	 * @param excId
	 */
	public void checkExchange(int excId) {
		Iterator it = this.getExchangeList().iterator();
		while (it.hasNext()) {
			DiamondExchange exchange = (DiamondExchange) it.next();
			if (exchange.getExcId() == excId) {
				exchange.setStatus(2);
				break;
			}
		}
	}
	/**
	 * @return the modifyAble
	 */
	public boolean isModifyAble() {
		return (status == 3 ? false : true);
	}
}
