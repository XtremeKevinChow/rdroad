/*
 * Created on 2006-9-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.common.pager;

import org.apache.struts.action.ActionForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PagerForm extends ActionForm {
    
    /** ∑÷“≥∂‘œÛ **/
    private Pager pager = new Pager();
    
    private int offset;
    
    /**
     * @return Returns the offset.
     */
    public int getOffset() {
        return offset;
    }
    /**
     * @param offset The offset to set.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
    /**
     * @return Returns the pager.
     */
    public Pager getPager() {
        return pager;
    }
    /**
     * @param pager The pager to set.
     */
    public void setPager(Pager pager) {
        this.pager = pager;
    }
    
}
