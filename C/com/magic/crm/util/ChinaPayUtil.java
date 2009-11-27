package com.magic.crm.util;

import chinapay.PrivateKey;
import chinapay.SecureLink;

/**
 * this class is for ��������ǩ��ʹ��
 * 
 * @author zhuxiang
 * creat date 20080915
 */
public class ChinaPayUtil {

	private PrivateKey  key = null;
	private SecureLink t;
	
	/**
	 * ��ʼ������ǩ����˽Կ
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
	 * ��������ǩ������
	 * String MerId - �̻��ţ�����Ϊ15���ֽڵ����ִ�����ChinaPay���������з��䡣
	 * String OrdId - �����ţ�����Ϊ16���ֽڵ����ִ������̻�ϵͳ���ɣ�ʧ�ܵĶ����������ظ�֧����
	 * String TransAmt - ���׽�����Ϊ12���ֽڵ����ִ������磺���ִ�"000000001234"��ʾ12.34Ԫ��
	 * String CuryId - ���Ҵ���, ����Ϊ3���ֽڵ����ִ���Ŀǰֻ֧������ң�ȡֵΪ"156" ��
	 * String TransDate - �������ڣ�����Ϊ8���ֽڵ����ִ�����ʾ��ʽΪ��YYYYMMDD��
	 * String TransType - �������ͣ�����Ϊ4���ֽڵ����ִ���ȡֵ��ΧΪ��"0001"��"0002"������"0001"��ʾ���ѽ��ף�"0002"��ʾ�˻�����
	 * return �����ַ���
	 * 
	 */
	public String signOrder(String MerId, String OrdId, String TransAmt, String CuryId, String TransDate, String TransType) {
		String ret = null;
		ret = t.signOrder(MerId, OrdId, TransAmt, CuryId, TransDate, TransType);
		return ret;
	}
	/**
	 * 
	 * String MerId - �̻��ţ�����Ϊ15���ֽڵ����ִ�����ChinaPay���������з��䡣
	 * String OrdId - �����ţ�����Ϊ16���ֽڵ����ִ������̻�ϵͳ���ɣ�ʧ�ܵĶ����������ظ�֧����
	 * String TransAmt - ���׽�����Ϊ12���ֽڵ����ִ������磺���ִ�"000000001234"��ʾ12.34Ԫ��
	 * String CuryId - ���Ҵ���, ����Ϊ3���ֽڵ����ִ���Ŀǰֻ֧������ң�ȡֵΪ"156" ��
	 * String TransDate - �������ڣ�����Ϊ8���ֽڵ����ִ�����ʾ��ʽΪ�� YYYYMMDD��
	 * String TransType - �������ͣ�����Ϊ4���ֽڵ����ִ���ȡֵ��ΧΪ��"0001"��"0002"�� ����"0001"��ʾ���ѽ��ף�"0002"��ʾ�˻����ס�
	 * String OrderStatus - ����״̬������Ϊ4���ֽڵ����ִ����������״̬��˵����
	 * String CheckValue - У��ֵ����ChinaPay�Խ���Ӧ�������ǩ��������Ϊ256�ֽڵ��ַ���
	 * @return true - ��ʾ�ɹ������ý���Ӧ��ΪChinaPay�����ͣ��̻����ݡ�����״̬�����к������������ʾʧ�ܣ�����ЧӦ��
	 */
	public boolean verifyTransResponse(String MerId, String OrdId, String TransAmt, String CuryId, String TransDate, String TransType, String OrderStatus, String CheckValue) {
		return t.verifyTransResponse(MerId, OrdId, TransAmt,CuryId, TransDate, TransType, OrderStatus, CheckValue);
	}
	
	/**
	 * ������������ǩ���Ƿ���Ч
	 * @param args
	 */
	public static void main(String[] args) {
		ChinaPayUtil util = new ChinaPayUtil();
		String checkVal = util.signOrder("808080090301003", "T10202021", "000000120450", "156", "20080101", "0001");
		System.out.println(checkVal);
		System.exit(0);
	}
}
