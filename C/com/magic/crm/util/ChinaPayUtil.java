package com.magic.crm.util;

import chinapay.PrivateKey;
import chinapay.SecureLink;

/**
 * this class is for 银联数字签名使用
 * 
 * @author zhuxiang
 * creat date 20080915
 */
public class ChinaPayUtil {

	private PrivateKey  key = null;
	private SecureLink t;
	
	/**
	 * 初始化数字签名的私钥
	 */
	public ChinaPayUtil() {
		boolean flag;
		key = new PrivateKey();
		flag=key.buildKey("808080090301003",0,"D:\\MerPrk.key");
		if (flag==false)
		{
		System.out.println("build key error!");
		return;
		}
		
		t=new chinapay.SecureLink (key);
	}
	
	/**
	 * 调用数字签名函数
	 * String MerId - 商户号，长度为15个字节的数字串，由ChinaPay或清算银行分配。
	 * String OrdId - 订单号，长度为16个字节的数字串，由商户系统生成，失败的订单号允许重复支付。
	 * String TransAmt - 交易金额，长度为12个字节的数字串，例如：数字串"000000001234"表示12.34元。
	 * String CuryId - 货币代码, 长度为3个字节的数字串，目前只支持人民币，取值为"156" 。
	 * String TransDate - 交易日期，长度为8个字节的数字串，表示格式为：YYYYMMDD。
	 * String TransType - 交易类型，长度为4个字节的数字串，取值范围为："0001"和"0002"，其中"0001"表示消费交易，"0002"表示退货交易
	 * return 检验字符串
	 * 
	 */
	public String signOrder(String MerId, String OrdId, String TransAmt, String CuryId, String TransDate, String TransType) {
		String ret = null;
		ret = t.signOrder(MerId, OrdId, TransAmt, CuryId, TransDate, TransType);
		return ret;
	}
	/**
	 * 
	 * String MerId - 商户号，长度为15个字节的数字串，由ChinaPay或清算银行分配。
	 * String OrdId - 订单号，长度为16个字节的数字串，由商户系统生成，失败的订单号允许重复支付。
	 * String TransAmt - 交易金额，长度为12个字节的数字串，例如：数字串"000000001234"表示12.34元。
	 * String CuryId - 货币代码, 长度为3个字节的数字串，目前只支持人民币，取值为"156" 。
	 * String TransDate - 交易日期，长度为8个字节的数字串，表示格式为： YYYYMMDD。
	 * String TransType - 交易类型，长度为4个字节的数字串，取值范围为："0001"和"0002"， 其中"0001"表示消费交易，"0002"表示退货交易。
	 * String OrderStatus - 交易状态，长度为4个字节的数字串。详见交易状态码说明。
	 * String CheckValue - 校验值，即ChinaPay对交易应答的数字签名，长度为256字节的字符串
	 * @return true - 表示成功，即该交易应答为ChinaPay所发送，商户根据“交易状态”进行后续处理；否则表示失败，即无效应答
	 */
	public boolean verifyTransResponse(String MerId, String OrdId, String TransAmt, String CuryId, String TransDate, String TransType, String OrderStatus, String CheckValue) {
		return t.verifyTransResponse(MerId, OrdId, TransAmt,CuryId, TransDate, TransType, OrderStatus, CheckValue);
	}
	
	/**
	 * 测试银联数字签名是否有效
	 * @param args
	 */
	public static void main(String[] args) {
		ChinaPayUtil util = new ChinaPayUtil();
		String checkVal = util.signOrder("808080090301003", "T10202021", "000000120450", "156", "20080101", "0001");
		System.out.println(checkVal);
		System.exit(0);
	}
}
