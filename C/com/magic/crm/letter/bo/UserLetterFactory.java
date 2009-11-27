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
     * 信件模版工厂类,以后增加模版的时候可以在这里增加就行了
     * @param letterType
     * 这个值是表USER_LETTERS中的MAIL_TYPE对应的值
     * LETTER_TEMPLATE中定义的模版
     * 1-确认信, 
     * 2-催款信, 
     * 3-缺货通知信, 
     * 4-订单发货通知, 
     * 5-会员取消信(暂时未用), 
     * 6-缺货取消信, 
     * 7-缺款取消信
     * 100-仓库产品到货通知
     * 101-仓库产品入库通知
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
        	    throw new LetterTypeException("未定义的信件模版:" + letterType);
        		
        }
        return template;
    }
}
