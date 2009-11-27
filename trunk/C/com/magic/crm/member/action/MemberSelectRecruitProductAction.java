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
 * 选择入会礼品
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
				//得到共几个组
				MemberSessionRecruitGifts sessionRecruit = (MemberSessionRecruitGifts)request.getSession(true).getAttribute("RECRUIT ACTIVITY");
				if (sessionRecruit != null) {
					Iterator it = sessionRecruit.getAllRecruitGifts().getSectionsList().iterator();//所有礼品区
					int len = 0;
					int i=0;
					while (it.hasNext()) {
						Recruit_Activity_Section section = (Recruit_Activity_Section)it.next();
						String[] priceListId = request.getParameterValues("product_" + section.getId());//product_1
						len = priceListId == null ? 0 : priceListId.length;
						
						for (i = 0; i < len; i ++) {
							
							Recruit_Activity_PriceList product = section.getProduct(Integer.parseInt(priceListId[i]));//allgifts中
							
							//Recruit_Activity_PriceList newObj = new Recruit_Activity_PriceList();//新增一个对象，值和allgifts中的一样
							
							section.resetTemp(product);//将allgifts中对应的这条记录设置状态
							
							//PropertyUtils.copyProperties(newObj, product);//复制数据
							
							sessionRecruit.addGift(product);//加入已选礼品
						}
							
						
					}
					int rtn = sessionRecruit.checkAllSelectedGifts();
					System.out.println("checkAllSelectedGifts****"+rtn);
					if ( rtn < 0) {
						sessionRecruit.removeTemp();
						if (rtn == -1) {
							Message.setMessage(request, "选礼品太多");
						}else if (rtn == -2) {
							Message.setMessage(request, "选礼品太少");
						}
					} else {
						sessionRecruit.changeTempStatus();//设置allgifts中礼品状态
						sessionRecruit.resetAllGiftsStatus();
					}
					
				}
				if (sessionRecruit == null) {
					sessionRecruit = new MemberSessionRecruitGifts();
				}
				request.getSession(true).setAttribute("RECRUIT ACTIVITY", sessionRecruit);//将列表保存到session
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
