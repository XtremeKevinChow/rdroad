/*
 * Created on 2007-03-02
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

/**
 * 购物车基本接口
 * @author user
 *
 */
public interface BaseShoppingCart {
	
	/**
	 * 增加产品
	 * @param obj
	 * @throws Exception
	 */
	public void addItem(Object obj);
	
	/**
	 * 更新产品
	 * @param obj
	 * @throws Exception
	 */
	public void updateItem(Object obj);
	
	/**
	 * 删除产品
	 * @param nIndex
	 * @throws Exception
	 */
	public void removeItem(int nIndex);
	
	/**
	 * 清空购物车
	 * @throws Exception
	 */
	public void clearShoppingCart();
	
	/**
	 * 购物总额
	 * @return
	 * @throws Exception
	 */
	public double getTotalMoney();
	
	/**
	 * 购物总件数
	 * @return
	 * @throws Exception
	 */
	public long getTotalQty();
}
