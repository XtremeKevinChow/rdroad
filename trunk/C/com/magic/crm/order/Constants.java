/*
 * Created on 2006-10-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order;

import com.magic.crm.util.CodeName;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Constants {

    /** 邮局手工充款原因显示列表（静态构建数据） **/
	public static java.util.Collection reasonColl = new java.util.ArrayList();
	static {
	    reasonColl.add(new CodeName("0", "-- 请选择 --"));
	    reasonColl.add(new CodeName("1", "客服下错单"));
	    //reasonColl.add(new CodeName("2", "书香卡特殊处理"));
	    reasonColl.add(new CodeName("3", "报销邮费"));
	    reasonColl.add(new CodeName("4", "物流操作问题"));
	    reasonColl.add(new CodeName("5", "系统问题"));
	    reasonColl.add(new CodeName("6", "转帐"));
	    reasonColl.add(new CodeName("7", "礼品质量问题退换货"));
	    reasonColl.add(new CodeName("8", "产品质量问题退换货"));
	    reasonColl.add(new CodeName("9", "包裹遗失"));
	    reasonColl.add(new CodeName("10", "开单查询未果"));
	    reasonColl.add(new CodeName("11", "会员特殊要求"));
	    reasonColl.add(new CodeName("12", "其他"));
	}
    
}
