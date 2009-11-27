/*
 * Created on 2006-2-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.member.dao.MbrGetAwardDAO2;
import com.magic.crm.member.dao.MemberExpExchangeDAO;
import com.magic.crm.member.form.MemberExpExchangeForm;
import com.magic.crm.member.form.MemberExpExchangePopForm;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author 蟋蟀
 * 积分礼品设置
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberExpExchangeSetupAction extends DispatchAction {
	
	private static Logger log = Logger.getLogger("MemberExpExchangeSetupAction.class");
	
	
	/**
	 * 显示新增页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	    MemberExpExchangeForm data = (MemberExpExchangeForm)form;
	    Connection conn = DBManager.getConnection();
		try {
		    int days = MbrGetAwardDAO2.getExchangeGiftKeepDay(conn);
		    data.setValidDay(days);//积分兑换礼品保留天数
			
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} 

		return mapping.findForward("add");
	}

	/**
	 * 添加记录
	 * 
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
		
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		Connection conn = DBManager.getConnection();
		MemberExpExchangeForm data = (MemberExpExchangeForm)form;
		
		try {
			int itemID = ProductDAO.getItemID(conn, data.getItemCode());
			if ( itemID == 0 ) {
				Message.setMessage(request, "该产品不存在", "返 回",null);
				return mapping.findForward("message");
			}
			data.setItemID(itemID);
			
			//假如表中有这个产品或者有这个金额
			if (ptDao.hasItemID(conn, data)) {
				Message.setMessage(request, "该产品已经设置","返 回",null);
				return mapping.findForward("message");
			}
			
			MemberExpExchangePopForm popForm = ptDao.showMainDetail(conn, data.getParentID());
			if (parseDateFloat(data.getStartDate()) <  parseDateFloat( popForm.getStartDate() )) {
				Message.setMessage(request, "开始日期不能小于" + popForm.getStartDate(),"返 回",null);
				return mapping.findForward("message");
			}
			if (parseDateFloat(data.getEndDate()) >  parseDateFloat( popForm.getEndDate())) {
				Message.setMessage(request, "结束日期不能大于" + popForm.getEndDate(),"返 回",null);
				return mapping.findForward("message");
			}
			ptDao.insert(conn, data);

		} catch (Exception e) {
			Message.setMessage(request, "新增错误");
			log.error("exception:", e);
			return mapping.findForward("message");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query1");
	}
	/**
	 * 日期转化
	 * @param date
	 * @return
	 */
	private float parseDateFloat (String date) {
		String[] arr = date.split("-");
		StringBuffer buf = new StringBuffer();
		buf.append(arr[0]);
		buf.append(arr[1]);
		buf.append(arr[2]);
		return Float.parseFloat( buf.toString());
	}
	/**
	 * 显示修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberExpExchangeForm pageData = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		Connection conn = DBManager.getConnection();
		try {
			String id = request.getParameter("id");
			pageData.setID(Integer.parseInt(id));
			
			MemberExpExchangeForm data = ptDao.showDetail(conn, pageData);
			
			String name = ptDao.getNameByID(conn, data.getParentID());
			data.setParentName(name);
			
			int days = MbrGetAwardDAO2.getExchangeGiftKeepDay(conn);
		    data.setValidDay(days);//积分兑换礼品保留天数
			request.setAttribute("memberExpExchangeForm", data);
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("modify");
	}

	/**
	 * 修改记录
	 * 
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
		MemberExpExchangeForm data = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		
		Connection conn = DBManager.getConnection();
		try {
			int itemID = ProductDAO.getItemID(conn, data.getItemCode());
			if ( itemID == 0 ) {
				Message.setMessage(request, "该产品不存在","返 回","member/memberExpExchangeSetup.do?type=modInit&id=" + data.getID());
				return mapping.findForward("message");
			}
			data.setItemID(itemID);
			//假如表中有这个产品
			if (ptDao.hasItemID(conn, data)) {
				Message.setMessage(request, "货号已经存在","返 回","member/memberExpExchangeSetup.do?type=modInit&id=" + data.getID());
				return mapping.findForward("message");
			}
			MemberExpExchangePopForm popForm = ptDao.showMainDetail(conn, data.getParentID());
			if (parseDateFloat(data.getStartDate()) <  parseDateFloat( popForm.getStartDate() )) {
				Message.setMessage(request, "开始日期不能小于" + popForm.getStartDate(),"返 回",null);
				return mapping.findForward("message");
			}
			if (parseDateFloat(data.getEndDate()) >  parseDateFloat( popForm.getEndDate())) {
				Message.setMessage(request, "结束日期不能大于" + popForm.getEndDate(),"返 回",null);
				return mapping.findForward("message");
			}
			ptDao.update(conn, data);

		} catch (Exception e) {
			Message.setMessage(request, "修改错误");
			log.error("exception:", e);
			return mapping.findForward("message");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query1");
	}

	/**
	 * 删除记录
	 * 
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
		MemberExpExchangeForm pageData = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		String[] delID = request.getParameterValues("delID");
		Connection conn = DBManager.getConnection();

		try {
			if (delID == null || delID.length == 0) {
				Message.setMessage(request, "请选择记录");
			} 
			for (int i = 0; i < delID.length; i ++) {
				pageData.setID(Long.parseLong(delID[i]));
				ptDao.delete(conn, pageData);
			}
			
			Message.setMessage(request, "禁用成功","返 回",null);
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("message");
	}

	/**
	 * 查询所有记录
	 * 
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
		MemberExpExchangeForm data = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		Connection conn = DBManager.getConnection();

		try {
			
			//1 查询信息
			Collection ret = ptDao.getList(conn, data);
			
			if(request.getMethod().equals("GET")) {
				
				ret = new ArrayList();
			}
			request.setAttribute("list", ret);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setMessage(request, "查询出错");
			throw e;
			
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query");
	}
	
	/**
	 * 选择主记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberExpExchangeForm data = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		Connection conn = DBManager.getConnection();
	
		try {

			//1 查询信息
			Collection ret = ptDao.getMstList(conn, data);
			
//			if(request.getMethod().equals("GET")) {
//				
//				ret = new ArrayList();
//			}
			request.setAttribute("expList", ret);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setMessage(request, "查询出错");
			throw e;
			
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("select");
	}
}
