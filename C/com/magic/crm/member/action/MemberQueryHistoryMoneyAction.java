/*
 * Created on 2005-3-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.form.MemberaddMoneyForm;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.entity.MembeMoneyHistory;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.common.CommonPageUtil;
import java.util.HashMap;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberQueryHistoryMoneyAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
	    MemberaddMoneyForm mf = (MemberaddMoneyForm)form;
		HttpSession session=request.getSession();
		User user=new User();
		user = (User)session.getAttribute("user");
		Connection conn = null;
		MemberaddMoneyDAO memberaddMoneyDAO=new MemberaddMoneyDAO();
		MemberaddMoney memberaddMoney=new MemberaddMoney();
		MembeMoneyHistory info=new MembeMoneyHistory();
		CommonPageUtil pageModel = new CommonPageUtil();
		pageModel.setPageSize(20);
		Collection paymethod = new ArrayList();
		Collection memberMoneyExecl = new ArrayList();

			/*
			 * ȡ������ֵ
			 */
		String tag=request.getParameter("tag");
		if(tag==null){
			tag="0";
		}
		if (request.getMethod().equals("GET")&&tag.equals("0")) {
			request.setAttribute("memberPageModel", pageModel);
			return mapping.findForward("success");
		}		
		String mb_code=request.getParameter("MB_CODE");
		String modify_date=request.getParameter("CREATE_DATE");
		String payid=request.getParameter("payMethod");
		String isquery=request.getParameter("isquery");

		/** �����callcenterϵͳ����sessin��ȡ����Ա��Ϣ�����session��û�л�Ա��Ϣ������ʾ��¼������� **/
        String iscallcenter = request.getParameter("iscallcenter");
        if (iscallcenter != null && iscallcenter.equals("1")) {
            CallCenterHander hander = new CallCenterHander(request.getSession());
            if (hander.isOnService()) {
                Member mb = hander.getServicedMember();
                mb_code = String.valueOf(mb.getCARD_ID());
                mf.setMB_CODE(mb_code);
            } else {
                ControlledError ctlErr = new ControlledError();
                ctlErr.setErrorTitle("��������");
                ctlErr.setErrorBody("û�з��������������");
                request.setAttribute(Constants.ERROR_KEY, ctlErr);
                return mapping.findForward("controlledError");

            }
        }
		/*
		 * ����ͻ����ύ��ʽΪ"POST"�����ύ�����������ݿ���ѡ�����������Ļ�Ա��ֵ
		 */

		String s_pageNum = request.getParameter("pageNo");
		int pageNum = 1;
		if (s_pageNum != null && !"".equals(s_pageNum)) {
			pageNum = Integer.parseInt(s_pageNum);
		}
		pageModel.setPageNo(pageNum);
		HashMap hashmap = new HashMap();
		hashmap.put("payid", payid);
		hashmap.put("modify_date", modify_date);
		hashmap.put("mb_code", mb_code);
	
		pageModel.setCondition(hashmap);
		Collection historyMoney=new ArrayList(); 

		String condition="";
		try{
			conn = DBManager.getConnection();
			if(isquery!=null&&isquery.equals("1")){

				memberaddMoneyDAO.QueryMemberHistoryMoney(conn,pageModel);	
				
		        	if(mb_code!=null&&mb_code.length()>0){
		        	condition+=" and a.card_id='"+mb_code+"'";
			        }
			        if(modify_date!=null&&modify_date.length()>0){
			        	condition+=" and b.modify_date like to_date('"+modify_date+"','yyyy-mm-dd')";
			        }
					if(payid!=null&&payid.length()>0){
						condition+= " and e.id="+payid;
					}
					/*
					 * ��ʾ�ܽ��
					 */
					if(s_pageNum==null||s_pageNum.equals("1")){
					double summoney=memberaddMoneyDAO.getSumMoney(conn,condition);
					request.setAttribute("summoney", String.valueOf(summoney));		
					}
					/*
					 * ���ɱ���
					 */
			}
			
			request.setAttribute("memberPageModel", pageModel);

		 	return mapping.findForward("success");	
		
		
		} catch(SQLException se) {

		  	throw new ServletException(se);
	
		 } finally {
	
			 try {
	
				 conn.close();
	
			  } catch(SQLException sqe) {
	
				  throw new ServletException(sqe);
	
			  }
	
		 }
		 
	}
	
}
