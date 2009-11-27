/*
 * Created on 2006-6-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

import javax.servlet.http.HttpSession;
import com.magic.crm.member.entity.Member;
/**
 * @author magic
 * CallCenterHander扩展了HttpSession
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallCenterHander {
    
    HttpSession currentMember = null;
    
    public CallCenterHander (HttpSession session) {
        this.currentMember = session;
    }
    
    /**
     * 是否有会员正在服务中
     * @return
     */
    public boolean isOnService () {
        return currentMember.getAttribute(Constants.CURRENT_MEMBER_KEY) == null ? false : true;
    }
    
    /**
     * 退出服务
     */
    public void exitService() {
        currentMember.removeAttribute(Constants.CURRENT_MEMBER_KEY);
    }
    
    /**
     * 得到当前服务的会员
     * @return
     */
    public Member getServicedMember () {
        Object obj = null;
        obj = currentMember.getAttribute(Constants.CURRENT_MEMBER_KEY);
        if ( obj instanceof Member ) {
            return (Member)obj;
        } else {
            return null;
        }
       
    }
    
    /**
     * 设置服务会员对象
     * @param member
     */
    public void setPreServiceMember(Member member) {
        currentMember.setAttribute(Constants.PREVIOUS_MEMBER_KEY, member);
    }
    
    /**
     * 得到当前服务的会员
     * @return
     */
    public Member getPreServicedMember () {
        Object obj = null;
        obj = currentMember.getAttribute(Constants.PREVIOUS_MEMBER_KEY);
        if ( obj instanceof Member ) {
            return (Member)obj;
        } else {
            return null;
        }
       
    }
    
    /**
     * 设置服务会员对象
     * @param member
     */
    public void setServiceMember(Member member) {
        currentMember.setAttribute(Constants.CURRENT_MEMBER_KEY, member);
    }
}
