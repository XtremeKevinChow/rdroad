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

    /** �ʾ��ֹ����ԭ����ʾ�б���̬�������ݣ� **/
	public static java.util.Collection reasonColl = new java.util.ArrayList();
	static {
	    reasonColl.add(new CodeName("0", "-- ��ѡ�� --"));
	    reasonColl.add(new CodeName("1", "�ͷ��´�"));
	    //reasonColl.add(new CodeName("2", "���㿨���⴦��"));
	    reasonColl.add(new CodeName("3", "�����ʷ�"));
	    reasonColl.add(new CodeName("4", "������������"));
	    reasonColl.add(new CodeName("5", "ϵͳ����"));
	    reasonColl.add(new CodeName("6", "ת��"));
	    reasonColl.add(new CodeName("7", "��Ʒ���������˻���"));
	    reasonColl.add(new CodeName("8", "��Ʒ���������˻���"));
	    reasonColl.add(new CodeName("9", "������ʧ"));
	    reasonColl.add(new CodeName("10", "������ѯδ��"));
	    reasonColl.add(new CodeName("11", "��Ա����Ҫ��"));
	    reasonColl.add(new CodeName("12", "����"));
	}
    
}
