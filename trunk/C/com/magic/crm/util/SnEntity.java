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
	
	float delivery_fee = 0.0f; //�ͻ�����
	String comments = ""; //��ע����
	Cell cell = null; //ͳһ��Cell
	Image jpeg = null; //������ͼƬ
	
	//��Ա������Ϣ��������
	float OLD_MEMBER_DEPOSIT = 0.0f; //����ǰ��Ա���ʻ����
	float MEMBER_DEPOSIT = 0.0f;//���ڵ��ʻ����
	double payed_money = 0.0f; //����ʹ�õ��ʻ����
	
	
	int OLD_MEMBER_EXP = 0; //����ǰ��Ա���ۼƻ���
	int created_MEMBER_EXP = 0; //���β������ۼƻ���
	int OLD_YEAR_EXP = 0; //����ǰ��Ա����Ȼ���
	int created_year_exp = 0; //���β�������Ȼ���
	
	//float OLD_EMONEY = 0; //����ǰ��Ա��EԪ
	float THIS_USED_EMONEY = 0.0f; //����ʹ�õ�EԪ
	//float created_emoney = 0.0f; //���β�����EԪ
	//float eMoney = 0; // ����eԪ
	int member_exp = 0;
	int MEMBER_YEAR_EXP =0;
	        
	float SHIPPING_SUM = 0; //Ӧ����
	float APPEND_FEE = 0; //��������
	float SHIPPING_TOTAL = 0; //�������ܶ�
	float SHIPPTINGTOTICES_MONEY = 0; //����������
	float entry_fee = 0; //�ƿ���
	int is_voice = 0; //�Ƿ���Ҫ��Ʊ
	String invoice_title = "";
	String is_comments = ""; //ȱ����Ϣ
	int comments_type = 0; //��ע��־
	int use_number = 0; //��ȯʣ�����
	float use_money = 0.0f; //��ȯʣ���ܽ��   
	int delivery_type = 0; //�ͻ���ʽ
	float package_fee = 0; //��װ��
	float MEMBER_ARREARAGE = 0; //��ԱǷ��
	float goods_fee = 0.0f;//������
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
