/*
 * Created on 2006-5-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import java.sql.Date;

import com.magic.crm.member.entity.MemberGetMember;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMemberForm extends ActionForm implements Serializable {
    
    /** 事件ID **/
    private Long eventId = null;
    
    /** 会员ID **/
    private Long memberId = null;
    
    /** 会员卡号 **/
    private String cardId = null;
    
    /** 状态 **/
    private Integer status = null;
    
    /** 礼品ID **/
    private Long giftId = null;
    
    /** 礼品代码 **/
    private String itemCode = null;
    
    /** 被推荐会员ID **/
    private Long recommendedId = null;
    
    /** 被推荐会员卡号 **/
    private String recommendedCardId = null;
    
    /** 礼品保留天数 **/
    private Integer keepDays = null;
    
    /** 礼品价格 **/
    private Double price = null;
    
    /** 操作人ID **/
    private Long operatorId = null;
    
    /** 创建日期 **/
    private Date createDate = null;
    
    /** 礼券号 **/
    private String giftNumber = null;
    /** 礼券金额 */
    private double giftMoney = 0;
    /** 订单满足金额条件 */
    private double orderMoney = 0;
    /** 订单截止时间 */
    private String endDate= null;
    
    protected String taobaowangid= "";
	
	public String getTaobaoWangId()
	{
		return taobaowangid;
	}
	public void setTaobaoWangId(String value)
	{
		taobaowangid = value;
	}
    
    public double getGiftMoney() {
		return giftMoney;
	}
	public void setGiftMoney(double giftMoney) {
		this.giftMoney = giftMoney;
	}
	public double getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(double orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

    
    
    public String getGiftNumber() {
		return giftNumber;
	}
	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber;
	}
	/**
     * @return Returns the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate The createDate to set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * @return Returns the eventId.
     */
    public Long getEventId() {
        return eventId;
    }
    /**
     * @param eventId The eventId to set.
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    /**
     * @return Returns the giftId.
     */
    public Long getGiftId() {
        return giftId;
    }
    /**
     * @param giftId The giftId to set.
     */
    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }
    /**
     * @return Returns the keepDays.
     */
    public Integer getKeepDays() {
        return keepDays;
    }
    /**
     * @param keepDays The keepDays to set.
     */
    public void setKeepDays(Integer keepDays) {
        this.keepDays = keepDays;
    }
    /**
     * @return Returns the memberId.
     */
    public Long getMemberId() {
        return memberId;
    }
    /**
     * @param memberId The memberId to set.
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    
    /**
     * @return Returns the cardId.
     */
    public String getCardId() {
        return cardId;
    }
    /**
     * @param cardId The cardId to set.
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    /**
     * @return Returns the operatorId.
     */
    public Long getOperatorId() {
        return operatorId;
    }
    /**
     * @param operatorId The operatorId to set.
     */
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
    /**
     * @return Returns the price.
     */
    public Double getPrice() {
        return price;
    }
    /**
     * @param price The price to set.
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    /**
     * @return Returns the recommendedId.
     */
    public Long getRecommendedId() {
        return recommendedId;
    }
    /**
     * @param recommendedId The recommendedId to set.
     */
    public void setRecommendedId(Long recommendedId) {
        this.recommendedId = recommendedId;
    }
    /**
     * @return Returns the status.
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * @return Returns the recommendedCardId.
     */
    public String getRecommendedCardId() {
        return recommendedCardId;
    }
    /**
     * @param recommendedCardId The recommendedCardId to set.
     */
    public void setRecommendedCardId(String recommendedCardId) {
        this.recommendedCardId = recommendedCardId;
    }
    /**
     * @return Returns the itemCode.
     */
    public String getItemCode() {
        return itemCode;
    }
    /**
     * @param itemCode The itemCode to set.
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.eventId = null;
        this.memberId = null;
        this.status = null;
        this.giftId = null;
        this.recommendedId = null;
        this.keepDays = null;
        this.price = null;
        this.operatorId = null;
        this.createDate = null;
    }
    
    public void copy ( MemberGetMember target ) {
        
        target.setMemberId(this.getMemberId());
        target.setStatus(this.getStatus());
        target.setGiftId(this.getGiftId());
        target.setRecommendedId(this.getRecommendedId());
        target.setKeepDays(this.getKeepDays());
        target.setPrice(this.getPrice());
        target.setOperatorId(this.getOperatorId());
        
    }
}
