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
 * ���action��
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
			// 1 ��ѯ��Ϣ
			int size = ExpExchangePackageDAO.countRecords(conn, pageData);
			Pager page = new Pager(pageData.getOffset(), size);//����page����
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
	 * �����������
	 * @param request
	 * @param pageData
	 * @return
	 */
	private boolean checkInputData(HttpServletRequest request, ExpExchangePackageForm pageData, Connection conn) throws Exception {
		//����Ϣ
		if (pageData.getPackageNo() == null || pageData.getPackageNo().length() == 0) {
			Message.setErrorMsg(request, "����Ų���Ϊ��!");
			return false;
		}
		if (pageData.getDesc() == null || pageData.getDesc().length() == 0) {
			Message.setErrorMsg(request, "��������Ϊ��!");
			return false;
		}
		//��ϸ��Ϣ
		if (pageData.getPackageType() == null || pageData.getPackageType().length == 0) {
			Message.setErrorMsg(request, "��ϸ����Ϊ��!");
			return false;
		}
	
		for (int i = 0; i < pageData.getPackageType().length; i ++) {
			if (pageData.getPackageType()[i] == null || pageData.getPackageType()[i].length() == 0) {
				Message.setErrorMsg(request, "�����ϸ��"+ (i+1) +"�У����Ͳ���Ϊ��!");
				return false;
			}
			if (pageData.getNo()[i] == null || pageData.getNo()[i].length() == 0) {
				
				Message.setErrorMsg(request, "�����ϸ��"+ (i+1) +"�к��벻��Ϊ��!");
				return false;
			} else {
				
				if (pageData.getPackageType()[i].equals("T")) {// ��ȯ
					OneTicket one = TicketDAO.getTicketByNumber2(conn, pageData.getNo()[i]);
					if (one.getGiftNo() == null) { //�Ҳ��������ȯ
						Message.setErrorMsg(request, "�����ϸ��"+ (i+1) +"�У���ȯ�Ų�����!");
						return false;
					}
				} else if (pageData.getPackageType()[i].equals("G")) {//��Ʒ
					ProductDAO productDao = new ProductDAO();
					ProductForm pf = productDao.findByCode(conn, pageData.getNo()[i]);
					
					if (pf.getItemCode() == null) { //��Ʒ������
						Message.setErrorMsg(request, "�����ϸ��"+ (i+1) +"�У����Ų�����!");
						return false;
					} else {
						if (pf.getIsSet().equals("1")) {//��װ��Ʒ
							Message.setErrorMsg(request, "�����ϸ��"+ (i+1) +"�У���֧����װ��Ʒ!");
							return false;
						}
					}
				} else {
					Message.setErrorMsg(request, "�����ϸ��"+ (i+1) +"�У����Ͳ���!");
					return false;
				}
			}
			
			if (pageData.getQuantity()[i] == 0) {
				Message.setErrorMsg(request, "�����ϸ��"+ (i+1) +"����������Ϊ��!");
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
			//check����
			if (checkInputData(request, pageData, conn)) {
				//1 �������
				ExpExchangePackageDAO.insertMst(conn, pageData);
				
				//2 ������ϸ
				ExpExchangePackageDtl dtl = new ExpExchangePackageDtl();
				for (int i = 0; i < pageData.getPackageType().length; i ++) {
					dtl.getMst().setPackageNo(pageData.getPackageNo());
					dtl.setPackageType(pageData.getPackageType()[i]);
					dtl.setNo(pageData.getNo()[i]);
					dtl.setQuantity(pageData.getQuantity()[i]);
					ExpExchangePackageDAO.insertDtl(conn, dtl);
				}
				conn.commit();
				Message.setMessage(request, "�����ɹ�!");
				request.getSession().removeAttribute("package");
			} else {
				foward = "add";
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			log.error("exception:", e);
			foward = "add";
			Message.setErrorMsg(request, "���ݿ��쳣��������������ظ���!");
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
			//1 ��ѯ��ȯ
			ExpExchangePackageMst mst = ExpExchangePackageDAO.findByPk(conn, pageData);
			//2 ��ѯ��ȯ��ϸ
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
			//1 ��ѯ��ȯ
			ExpExchangePackageMst mst = ExpExchangePackageDAO.findByPk(conn, pageData);
			//2 ��ѯ��ȯ��ϸ
			Collection ret = ExpExchangePackageDAO.findByFk(conn, pageData);
			mst.setDtlList(ret);
			
			//ҳ������
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
				//1 �������
				ExpExchangePackageDAO.updateMst(conn, pageData);
				
				//2 ɾ������ϸ
				ExpExchangePackageDAO.removeDtlByFk(conn, pageData);
				//3 ��������ϸ
				ExpExchangePackageDtl dtl = new ExpExchangePackageDtl();
				for (int i = 0; i < pageData.getPackageType().length; i ++) {
					dtl.getMst().setPackageNo(pageData.getPackageNo());
					dtl.setPackageType(pageData.getPackageType()[i]);
					dtl.setNo(pageData.getNo()[i]);
					dtl.setQuantity(pageData.getQuantity()[i]);
					ExpExchangePackageDAO.insertDtl(conn, dtl);
				}
				
				//4 ��ѯ��ȯ
				ExpExchangePackageMst mst = ExpExchangePackageDAO.findByPk(conn, pageData);
				//5 ��ѯ��ȯ��ϸ
				Collection ret = ExpExchangePackageDAO.findByFk(conn, pageData);
				mst.setDtlList(ret);
				request.setAttribute("package", mst);
				conn.commit();
				Message.setMessage(request, "�޸ĳɹ�!");
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
			Message.setErrorMsg(request, "�޸�ʧ��!");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}
	
	/**
	 * ����һ����ϸ
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
	 * ɾ��һ����ϸ
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
			Message.setErrorMsg(request, "ɾ���г����쳣");
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
			//1 �������
			ExpExchangePackageDAO.deleteMstByPk(conn, pageData);
			ExpExchangePackageDAO.deleteDtlByFk(conn, pageData);
			conn.commit();
			Message.setMessage(request, "ɾ���ɹ�!");
		} catch (Exception e) {
			conn.rollback();
			log.error("exception:", e);
			Message.setMessage(request, "ɾ��ʧ��!");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(foward);
	}
}
