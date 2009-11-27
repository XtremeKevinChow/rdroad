/*
 * Created on 2006-3-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.letter.bo;


/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserLetterFactory {
    /**
     * �ż�ģ�湤����,�Ժ�����ģ���ʱ��������������Ӿ�����
     * @param letterType
     * ���ֵ�Ǳ�USER_LETTERS�е�MAIL_TYPE��Ӧ��ֵ
     * LETTER_TEMPLATE�ж����ģ��
     * 1-ȷ����, 
     * 2-�߿���, 
     * 3-ȱ��֪ͨ��, 
     * 4-��������֪ͨ, 
     * 5-��Աȡ����(��ʱδ��), 
     * 6-ȱ��ȡ����, 
     * 7-ȱ��ȡ����
     * 100-�ֿ��Ʒ����֪ͨ
     * 101-�ֿ��Ʒ���֪ͨ
     * @return
     */
    public static LetterTemplate getInstance(int letterType) throws LetterTypeException {
        LetterTemplate template = null;
        switch (letterType){
        	case 1:
        	    template = new OrderConfirmLetter();
        	    break;
        	case 2:
        	    template = new OrderPaymentLetter();
        	    break;
        	case 3:
        	    template = new OrderOOSLetter();
        	    break;
        	case 4:
        	    template = new OrderSendofLetter();
        	    break;
        	case 6:
        	    template = new OrderPaymentCancelLetter();
        	    break;
        	case 7:
        	    template = new OrderOOSCancelLetter();
        	    break;
        	case 100:
        		template = new ProductArrivalLetter();
        		break;
        	case 101:
        		template = new ProductInboundLetter();
        		break;
        	default:
        	    throw new LetterTypeException("δ������ż�ģ��:" + letterType);
        		
        }
        return template;
    }
}
