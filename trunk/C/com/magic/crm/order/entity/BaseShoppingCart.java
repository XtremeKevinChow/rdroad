/*
 * Created on 2007-03-02
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

/**
 * ���ﳵ�����ӿ�
 * @author user
 *
 */
public interface BaseShoppingCart {
	
	/**
	 * ���Ӳ�Ʒ
	 * @param obj
	 * @throws Exception
	 */
	public void addItem(Object obj);
	
	/**
	 * ���²�Ʒ
	 * @param obj
	 * @throws Exception
	 */
	public void updateItem(Object obj);
	
	/**
	 * ɾ����Ʒ
	 * @param nIndex
	 * @throws Exception
	 */
	public void removeItem(int nIndex);
	
	/**
	 * ��չ��ﳵ
	 * @throws Exception
	 */
	public void clearShoppingCart();
	
	/**
	 * �����ܶ�
	 * @return
	 * @throws Exception
	 */
	public double getTotalMoney();
	
	/**
	 * �����ܼ���
	 * @return
	 * @throws Exception
	 */
	public long getTotalQty();
}
