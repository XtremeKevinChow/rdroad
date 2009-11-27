/**
 * MemberaddMoneyInputAction.java
 * 此类用于线下现金回单录入（财务、客服公用）
 * 2008-3-26
 * 上午10:54:04
 * user
 * MemberaddMoneyInputAction
 */
package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.MemberaddMoneyForm;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

import java.util.Collection;

/**
 * @author user
 *
 */
public class MemberaddMoneyManageAction extends DispatchAction {
	
	/**
	 * 显示新增页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("add");
	}
	
	/**
	 * 新增单据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		
		
		//登陆人
		User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			// 检测会员号是否正确
			/*MemberDAO memberDao = new MemberDAO();
			Member member = memberDao.getMemberInfo(conn, myForm.getMB_CODE());
			if (member == null || member.getID() <= 0) {
				//Message.setErrorMsg(request,"会员号不存在!");
				//return mapping.findForward("success");
			} else {
				if (!member.getNAME().trim().equals(myForm.getMB_NAME())) {
					Message.setErrorMsg(request,"会员姓名和会员号不匹配，有可能已经更改了会员姓名!");
					return mapping.findForward("success");
				}
			}
			//拼接会员+姓名
			if (myForm.getMB_CODE() != null 
					&& myForm.getMB_CODE().trim().length() != 0) {
					myForm.setMB_CODE(myForm.getMB_CODE()+"+"+myForm.getMB_NAME());
			}*/
			MemberaddMoney money = new MemberaddMoney();
			money.setTYPE("3");//导入类型
			money.setOPERATOR_ID(Integer.parseInt(user.getId()));//登陆人
			copyForm2Entity(myForm, money);
			moneyDao.insert(conn, money);
			Message.setErrorMsg(request,"操作成功!");
		}catch(Exception e) {
			Message.setErrorMsg(request,"操作失败，可能您输入汇号系统已经存在了!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * 显示修改信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			MemberaddMoney data = moneyDao.findRecordByPK(conn, myForm.getID());
			copyEntity2Form(data, myForm);
		}catch(Exception e) {
			throw new ServletException();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward("modify");
	}
	
	/**
	 * 修改单据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		
		//登陆人
		User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			//检测会员号是否正确
			/*MemberDAO memberDao = new MemberDAO();
			Member member = memberDao.getMemberInfo(conn, myForm.getMB_CODE());
			if (member == null || member.getID() <= 0) {
				//Message.setErrorMsg(request,"会员号不存在!");
				//return mapping.findForward("success");
			} else {
				if (!member.getNAME().trim().equals(myForm.getMB_NAME())) {
					Message.setErrorMsg(request,"会员姓名和会员号不匹配，有可能已经更改了会员姓名!");
					return mapping.findForward("success");
				}
			}
			//拼接会员+姓名
			if (myForm.getMB_CODE() != null 
					&& myForm.getMB_CODE().trim().length() != 0) {
				myForm.setMB_CODE(myForm.getMB_CODE() + "+" + myForm.getMB_NAME()); //20008489+帅诗颀 这种形式
			}*/
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			MemberaddMoney money = new MemberaddMoney();
			
			copyForm2Entity(myForm, money);
			money.setOPERATOR_ID(Integer.parseInt(user.getId()));//登陆人
			moneyDao.update2(conn, money);
			Message.setMessage(request,"操作成功!");
		}catch(Exception e) {
			Message.setErrorMsg(request,"操作失败，可能您输入汇号系统已经存在了!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return mapping.findForward("query2");
	}
	
	/**
	 * 修改单据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			
			moneyDao.delete(conn, myForm);
			Message.setErrorMsg(request,"操作成功!");
		}catch(Exception e) {
			
			Message.setErrorMsg(request,"操作失败!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return mapping.findForward("query2");
	}
	/**
	 * 查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		myForm.setStatus(0);//新创建的单据
		myForm.setTYPE("3");//现金（回单）
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			Collection list = moneyDao.findRecordsByCondition(conn, myForm);
			request.setAttribute("list", list);
		}catch(Exception e) {
			throw new ServletException();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward("query");
	}
	
	
	/**
	 * 拷贝数据
	 * @param source
	 * @param dest
	 */
	private void copyForm2Entity (MemberaddMoneyForm source, MemberaddMoney dest) {
		dest.setID(source.getID());
		dest.setREF_ID(source.getREF_ID());
		dest.setMB_CODE(source.getMB_CODE());
		dest.setMB_NAME(source.getMB_NAME());
		dest.setORDER_CODE(source.getORDER_CODE());
		dest.setMONEY(source.getMONEY());
		dest.setBill_date(source.getBILL_DATE());
		dest.setREMARK(source.getREMARK());
		dest.setUSE_TYPE(source.getUSE_TYPE());
		dest.setPayMethod(source.getPayMethod());
	}
	private void copyEntity2Form(MemberaddMoney source, MemberaddMoneyForm dest) {
		dest.setREF_ID(source.getREF_ID());
		dest.setMB_CODE(source.getMB_CODE());
		dest.setORDER_CODE(source.getORDER_CODE());
		dest.setMONEY(source.getMONEY());
		dest.setBILL_DATE(source.getBill_date());
		dest.setREMARK(source.getREMARK());
		dest.setUSE_TYPE(source.getUSE_TYPE());
		dest.setMB_NAME(source.getMB_NAME());
		dest.setPayMethod(source.getPayMethod());
	}
}
