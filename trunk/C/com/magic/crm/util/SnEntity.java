package com.magic.crm.util;
import java.io.Serializable;

import com.lowagie.text.Cell;
import com.lowagie.text.Image;
/*
 * Created on 2005-7-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SnEntity implements Serializable {
	int doc_id = 0;
	
	float delivery_fee = 0.0f; //送货费用
	String comments = ""; //备注内容
	Cell cell = null; //统一的Cell
	Image jpeg = null; //条形码图片
	
	//会员附加信息变量定义
	float OLD_MEMBER_DEPOSIT = 0.0f; //本单前会员的帐户余额
	float MEMBER_DEPOSIT = 0.0f;//现在的帐户余额
	double payed_money = 0.0f; //本单使用的帐户金额
	
	
	int OLD_MEMBER_EXP = 0; //本单前会员的累计积分
	int created_MEMBER_EXP = 0; //本次产生的累计积分
	int OLD_YEAR_EXP = 0; //本单前会员的年度积分
	int created_year_exp = 0; //本次产生的年度积分
	
	//float OLD_EMONEY = 0; //本单前会员的E元
	float THIS_USED_EMONEY = 0.0f; //本次使用的E元
	//float created_emoney = 0.0f; //本次产生的E元
	//float eMoney = 0; // 现在e元
	int member_exp = 0;
	int MEMBER_YEAR_EXP =0;
	        
	float SHIPPING_SUM = 0; //应付款
	float APPEND_FEE = 0; //其他费用
	float SHIPPING_TOTAL = 0; //发货单总额
	float SHIPPTINGTOTICES_MONEY = 0; //发货单货款
	float entry_fee = 0; //制卡费
	int is_voice = 0; //是否需要发票
	String invoice_title = "";
	String is_comments = ""; //缺货信息
	int comments_type = 0; //备注标志
	int use_number = 0; //礼券剩余次数
	float use_money = 0.0f; //礼券剩余总金额   
	int delivery_type = 0; //送货方式
	float package_fee = 0; //包装费
	float MEMBER_ARREARAGE = 0; //会员欠费
	float goods_fee = 0.0f;//购物金额
	double discount_fee = 0;
	
	String barcode = "";
	String lot = "";
	String contact = "";
	String orderNumber = "";
	String card_id = "";
	String mb_name = "";
	String family_phone = "";
	String post_code = "";
	String company_phone = "";
	String delivery_type_name = "";
	String address = "";
	String city = "";
	String phone = "";
	String remark = "";
	String phone1 = "";
	String payment = "";
	
}
