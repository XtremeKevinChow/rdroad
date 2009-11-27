package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.promotion.entity.Recruit_Activity_Section;
import com.magic.crm.promotion.entity.Recruit_Activity_PriceList;

import com.magic.crm.util.Message;
import com.magic.crm.member.entity.MemberSessionRecruitGifts;
import java.util.Iterator;

/**
 * ѡ�������Ʒ
 * @author user
 *
 */
public class MemberSelectRecruitProductAction extends Action {
	
		public ActionForward execute(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
			//Recruit_ActivityForm pageForm = (Recruit_ActivityForm)form;
			String nextUrl = "success";
			Connection conn = null;
			try {
				//�õ���������
				MemberSessionRecruitGifts sessionRecruit = (MemberSessionRecruitGifts)request.getSession(true).getAttribute("RECRUIT ACTIVITY");
				if (sessionRecruit != null) {
					Iterator it = sessionRecruit.getAllRecruitGifts().getSectionsList().iterator();//������Ʒ��
					int len = 0;
					int i=0;
					while (it.hasNext()) {
						Recruit_Activity_Section section = (Recruit_Activity_Section)it.next();
						String[] priceListId = request.getParameterValues("product_" + section.getId());//product_1
						len = priceListId == null ? 0 : priceListId.length;
						
						for (i = 0; i < len; i ++) {
							
							Recruit_Activity_PriceList product = section.getProduct(Integer.parseInt(priceListId[i]));//allgifts��
							
							//Recruit_Activity_PriceList newObj = new Recruit_Activity_PriceList();//����һ������ֵ��allgifts�е�һ��
							
							section.resetTemp(product);//��allgifts�ж�Ӧ��������¼����״̬
							
							//PropertyUtils.copyProperties(newObj, product);//��������
							
							sessionRecruit.addGift(product);//������ѡ��Ʒ
						}
							
						
					}
					int rtn = sessionRecruit.checkAllSelectedGifts();
					System.out.println("checkAllSelectedGifts****"+rtn);
					if ( rtn < 0) {
						sessionRecruit.removeTemp();
						if (rtn == -1) {
							Message.setMessage(request, "ѡ��Ʒ̫��");
						}else if (rtn == -2) {
							Message.setMessage(request, "ѡ��Ʒ̫��");
						}
					} else {
						sessionRecruit.changeTempStatus();//����allgifts����Ʒ״̬
						sessionRecruit.resetAllGiftsStatus();
					}
					
				}
				if (sessionRecruit == null) {
					sessionRecruit = new MemberSessionRecruitGifts();
				}
				request.getSession(true).setAttribute("RECRUIT ACTIVITY", sessionRecruit);//���б��浽session
				request.setAttribute("recruit", sessionRecruit);
			} catch(Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
			return mapping.findForward(nextUrl);
		}
}
