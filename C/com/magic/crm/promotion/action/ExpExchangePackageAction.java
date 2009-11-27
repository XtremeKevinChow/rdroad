package com.magic.crm.promotion.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import java.sql.Connection;
import java.util.Collection;

import com.magic.crm.promotion.form.ExpExchangePackageForm;
import com.magic.crm.product.form.ProductForm;

import com.magic.crm.common.pager.Pager;
import com.magic.crm.order.entity.OneTicket;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.promotion.dao.ExpExchangePackageDAO;
import com.magic.crm.order.dao.TicketDAO;
import com.magic.crm.promotion.entity.ExpExchangePackageMst;
import com.magic.crm.promotion.entity.ExpExchangePackageDtl;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * 礼包action类
 * 
 * @author user
 * 
 */
public class ExpExchangePackageAction extends DispatchAction {

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// 1 查询信息
			int size = ExpExchangePackageDAO.countRecords(conn, pageData);
			Pager page = new Pager(pageData.getOffset(), size);//生成page对象
            page.setLength(15);
            pageData.setPager(page);
			Collection ret = ExpExchangePackageDAO.findAll(conn, pageData);
			request.setAttribute("list", ret);

		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward("list");
	}
	
	public ActionForward showAddPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		//Connection conn = DBManager.getConnection();
		try {
			request.getSession().setAttribute("package", new ExpExchangePackageMst());

		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			//try {
				//conn.close();
			//} catch (Exception e) {
			//}
		}

		return mapping.findForward("add");
	}
	
	/**
	 * 检测输入数据
	 * @param request
	 * @param pageData
	 * @return
	 */
	private boolean checkInputData(HttpServletRequest request, ExpExchangePackageForm pageData, Connection conn) throws Exception {
		//主信息
		if (pageData.getPackageNo() == null || pageData.getPackageNo().length() == 0) {
			Message.setErrorMsg(request, "礼包号不能为空!");
			return false;
		}
		if (pageData.getDesc() == null || pageData.getDesc().length() == 0) {
			Message.setErrorMsg(request, "描述不能为空!");
			return false;
		}
		//明细信息
		if (pageData.getPackageType() == null || pageData.getPackageType().length == 0) {
			Message.setErrorMsg(request, "明细不能为空!");
			return false;
		}
	
		for (int i = 0; i < pageData.getPackageType().length; i ++) {
			if (pageData.getPackageType()[i] == null || pageData.getPackageType()[i].length() == 0) {
				Message.setErrorMsg(request, "礼包明细第"+ (i+1) +"行，类型不能为空!");
				return false;
			}
			if (pageData.getNo()[i] == null || pageData.getNo()[i].length() == 0) {
				
				Message.setErrorMsg(request, "礼包明细第"+ (i+1) +"行号码不能为空!");
				return false;
			} else {
				
				if (pageData.getPackageType()[i].equals("T")) {// 礼券
					OneTicket one = TicketDAO.getTicketByNumber2(conn, pageData.getNo()[i]);
					if (one.getGiftNo() == null) { //找不到这个礼券
						Message.setErrorMsg(request, "礼包明细第"+ (i+1) +"行，礼券号不存在!");
						return false;
					}
				} else if (pageData.getPackageType()[i].equals("G")) {//礼品
					ProductDAO productDao = new ProductDAO();
					ProductForm pf = productDao.findByCode(conn, pageData.getNo()[i]);
					
					if (pf.getItemCode() == null) { //产品不存在
						Message.setErrorMsg(request, "礼包明细第"+ (i+1) +"行，货号不存在!");
						return false;
					} else {
						if (pf.getIsSet().equals("1")) {//套装产品
							Message.setErrorMsg(request, "礼包明细第"+ (i+1) +"行，不支持套装产品!");
							return false;
						}
					}
				} else {
					Message.setErrorMsg(request, "礼包明细第"+ (i+1) +"行，类型不对!");
					return false;
				}
			}
			
			if (pageData.getQuantity()[i] == 0) {
				Message.setErrorMsg(request, "礼包明细第"+ (i+1) +"行数量不能为空!");
				return false;
			}
		}
		return true;
	}
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String foward = "query";
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			//check数据
			if (checkInputData(request, pageData, conn)) {
				//1 插入礼包
				ExpExchangePackageDAO.insertMst(conn, pageData);
				
				//2 插入明细
				ExpExchangePackageDtl dtl = new ExpExchangePackageDtl();
				for (int i = 0; i < pageData.getPackageType().length; i ++) {
					dtl.getMst().setPackageNo(pageData.getPackageNo());
					dtl.setPackageType(pageData.getPackageType()[i]);
					dtl.setNo(pageData.getNo()[i]);
					dtl.setQuantity(pageData.getQuantity()[i]);
					ExpExchangePackageDAO.insertDtl(conn, dtl);
				}
				conn.commit();
				Message.setMessage(request, "新增成功!");
				request.getSession().removeAttribute("package");
			} else {
				foward = "add";
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			log.error("exception:", e);
			foward = "add";
			Message.setErrorMsg(request, "数据库异常，可能是礼包号重复了!");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(foward);
	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String foward = "view";
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			//1 查询礼券
			ExpExchangePackageMst mst = ExpExchangePackageDAO.findByPk(conn, pageData);
			//2 查询礼券明细
			Collection ret = ExpExchangePackageDAO.findByFk(conn, pageData);
			mst.setDtlList(ret);
			request.setAttribute("package", mst);
			
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(foward);
	}
	
	public ActionForward showModifyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String foward = "modify";
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			//1 查询礼券
			ExpExchangePackageMst mst = ExpExchangePackageDAO.findByPk(conn, pageData);
			//2 查询礼券明细
			Collection ret = ExpExchangePackageDAO.findByFk(conn, pageData);
			mst.setDtlList(ret);
			
			//页面数据
			pageData.setPackageNo(mst.getPackageNo());
			pageData.setDesc(mst.getDesc());
			pageData.setStatus(mst.getStatus());
			pageData.setUrl(mst.getUrl());
			request.getSession().setAttribute("package", mst);
			//request.setAttribute("package", mst);
			
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(foward);
	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "view";
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			
			if (checkInputData(request, pageData, conn)) {
				//1 更新礼包
				ExpExchangePackageDAO.updateMst(conn, pageData);
				
				//2 删除老明细
				ExpExchangePackageDAO.removeDtlByFk(conn, pageData);
				//3 插入新明细
				ExpExchangePackageDtl dtl = new ExpExchangePackageDtl();
				for (int i = 0; i < pageData.getPackageType().length; i ++) {
					dtl.getMst().setPackageNo(pageData.getPackageNo());
					dtl.setPackageType(pageData.getPackageType()[i]);
					dtl.setNo(pageData.getNo()[i]);
					dtl.setQuantity(pageData.getQuantity()[i]);
					ExpExchangePackageDAO.insertDtl(conn, dtl);
				}
				
				//4 查询礼券
				ExpExchangePackageMst mst = ExpExchangePackageDAO.findByPk(conn, pageData);
				//5 查询礼券明细
				Collection ret = ExpExchangePackageDAO.findByFk(conn, pageData);
				mst.setDtlList(ret);
				request.setAttribute("package", mst);
				conn.commit();
				Message.setMessage(request, "修改成功!");
				request.getSession().removeAttribute("package");
			} else {
				//request.setAttribute("package", sessMst);
				forward = "modify";
			}
		} catch (Exception e) {
			conn.rollback();
			log.error("exception:", e);
			//request.setAttribute("package", sessMst);
			forward = "modify";
			Message.setErrorMsg(request, "修改失败!");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}
	
	/**
	 * 增加一行明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addDtlLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String forward = request.getParameter("forward");
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			ExpExchangePackageMst mst = (ExpExchangePackageMst)request.getSession().getAttribute("package");	
			mst.getDtlList().add(new ExpExchangePackageDtl());
			request.getSession().setAttribute("package", mst);
			//request.setAttribute("package", mst);
		
		} catch (Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}
	
	/**
	 * 删除一行明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteDtlLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = request.getParameter("forward");
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		int rowId = Integer.parseInt(request.getParameter("rowId"));
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ExpExchangePackageMst mst = (ExpExchangePackageMst)request.getSession().getAttribute("package");	
			ExpExchangePackageDtl dtl = (ExpExchangePackageDtl)((java.util.ArrayList)mst.getDtlList()).get(rowId);
			mst.getDtlList().remove(dtl);
			request.getSession().setAttribute("package", mst);
			//request.setAttribute("package", mst);
		
		} catch (Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request, "删除行出现异常");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String foward = "query";
		ExpExchangePackageForm pageData = (ExpExchangePackageForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			//1 更新礼包
			ExpExchangePackageDAO.deleteMstByPk(conn, pageData);
			ExpExchangePackageDAO.deleteDtlByFk(conn, pageData);
			conn.commit();
			Message.setMessage(request, "删除成功!");
		} catch (Exception e) {
			conn.rollback();
			log.error("exception:", e);
			Message.setMessage(request, "删除失败!");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(foward);
	}
}
