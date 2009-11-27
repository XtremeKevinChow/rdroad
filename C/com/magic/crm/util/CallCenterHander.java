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
 * CallCenterHander��չ��HttpSession
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallCenterHander {
    
    HttpSession currentMember = null;
    
    public CallCenterHander (HttpSession session) {
        this.currentMember = session;
    }
    
    /**
     * �Ƿ��л�Ա���ڷ�����
     * @return
     */
    public boolean isOnService () {
        return currentMember.getAttribute(Constants.CURRENT_MEMBER_KEY) == null ? false : true;
    }
    
    /**
     * �˳�����
     */
    public void exitService() {
        currentMember.removeAttribute(Constants.CURRENT_MEMBER_KEY);
    }
    
    /**
     * �õ���ǰ����Ļ�Ա
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
     * ���÷����Ա����
     * @param member
     */
    public void setPreServiceMember(Member member) {
        currentMember.setAttribute(Constants.PREVIOUS_MEMBER_KEY, member);
    }
    
    /**
     * �õ���ǰ����Ļ�Ա
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
     * ���÷����Ա����
     * @param member
     */
    public void setServiceMember(Member member) {
        currentMember.setAttribute(Constants.CURRENT_MEMBER_KEY, member);
    }
}
