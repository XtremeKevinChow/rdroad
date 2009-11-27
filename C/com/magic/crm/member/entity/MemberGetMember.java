/*
 * Created on 2006-5-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.entity;

import java.sql.Date;
import java.io.Serializable;

/**
 * @author user
 * 这个类和数据表MBR_GET_MBR映射
 * &nbsp;&nbsp;&nbsp; table = "MBR_GET_MBR"
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMember implements Serializable {
    
    /** 事件ID **/
    private Long eventId = null;
    
    /** 会员ID **/
    private Long memberId = null;
    
    /** 状态 **/
    private Integer status = null;
    
    /** 礼品ID **/
    private Long giftId = null;
    
    /** 被推荐会员ID **/
    private Long recommendedId = null;
    
    /** 礼品保留天数 **/
    private Integer keepDays = null;
    
    /** 礼品价格 **/
    private Double price = null;
    
    /** 操作人ID **/
    private Long operatorId = null;
    
    /** 创建日期 **/
    private Date createDate = null;
    
    
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
}
