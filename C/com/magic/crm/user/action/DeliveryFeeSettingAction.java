/**
 * DeliveryFeeSetting.java
 * 2008-4-9
 * 下午05:15:35
 * user
 * DeliveryFeeSetting
 */
package com.magic.crm.user.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.common.pager.Pager;
import com.magic.crm.user.dao.DeliveryFeeDAO;
import com.magic.crm.user.entity.DeliveryFee;
import com.magic.crm.user.form.DeliveryFeeForm;
import com.magic.crm.user.form.DefaultDeliveryFeeForm;
import com.magic.crm.user.entity.DefaultDeliveryFee;
import com.magic.crm.util.DBManager;
//import com.magic.crm.util.DBManager3;
import com.magic.crm.util.Message;
import com.magic.crm.io.Sync;
import com.magic.crm.io.DefaultDeliveryFeeSync;
import java.util.Collection;

/**
 * @author user
 *
 */
public class DeliveryFeeSettingAction extends DispatchAction {
	
	/**
	 * 显示新增页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "add";
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			pageData.setProvinceList(fee_dao.getProvinceList(conn));
			pageData.setDeliveryTypes( fee_dao.getDeliveryTypeList(conn));
			pageData.setMbrLevels(fee_dao.getMbrLevels(conn));

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 选择省
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectProvince(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = request.getParameter("forward");
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			pageData.setProvinceList(fee_dao.getProvinceList(conn));
			pageData.setCityList(fee_dao.getCityList(conn, pageData));
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 选择城市
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "add";
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			pageData.setProvinceList(fee_dao.getProvinceList(conn));
			pageData.setCityList(fee_dao.getCityList(conn, pageData));
			pageData.setDetailList(fee_dao.findByProvinceAndCity(conn, pageData));
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 新增
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "message";
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			if (checkAddInput(request, pageData) ) {
				conn = DBManager.getConnection();
				conn.setAutoCommit(false);
				DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
				//if (pageData.getPostcode() != null && pageData.getPostcode().length > 0) {
					DeliveryFee data = new DeliveryFee();
					int deliveryType = pageData.getDeliveryTypeM();
					data.setDeliveryType(deliveryType);
					int levelId = pageData.getLevelId();
					data.setLevelId(levelId);
					data.setFees(pageData.getFeeM());
					data.setRegionCode(pageData.getSearchProvince());
					//data.setBeginDate(pageData.getBeginDateM());
					//data.setEndDate(pageData.getEndDateM());
					//data.setRequireAmt(pageData.getRequireAmtM());
					//data.setRemark(pageData.getRemarkM());
					
					//for (int i = 0; i < pageData.getPostcode().length; i ++) {
					//	data.setPostcode(pageData.getPostcode()[i]);
						if (deliveryType == -1) { //所有发送方式
							for (int j = 1; j <= 5; j ++) {
								//if (j == 2) {
								//	continue;
								//}
								data.setDeliveryType(j);//邮寄
								if (levelId == -1) { //所有会员等级
									for (int k = 1; k <= 3; k ++) {
										data.setLevelId(k);
										fee_dao.insert(conn, data);
									}
								} else {
									fee_dao.insert(conn, data);
								}
							}
						} else { //不是所有发送方式
							if (levelId == -1) {
								for (int k = 1; k <= 3; k ++) {
									data.setLevelId(k);
									fee_dao.insert(conn, data);
								}
							} else {
								fee_dao.insert(conn, data);
							}
						}
						
					}
				//}
				conn.commit();
				Message.setErrorMsg(request, "新增成功!");
			//} 
		} catch (Exception e) {
			conn.rollback();
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 根据条件查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = request.getParameter("forward");
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			pageData.setProvinceList(fee_dao.getProvinceList(conn));
			//pageData.setCityList(fee_dao.getCityList(conn, pageData));
			int size = fee_dao.countRecords(conn, pageData);
			Pager page = new Pager(pageData.getOffset(), size);//生成page对象
            page.setLength(15);
            pageData.setPager(page);
			request.setAttribute("list", fee_dao.findByCondition(conn, pageData));
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 查看详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward view(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "view";
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			request.setAttribute("deliveryFee", fee_dao.findByPk(conn, pageData));
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 显示修改页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "modify";
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			DeliveryFee fee = fee_dao.findByPk(conn, pageData);
			pageData.copy(fee, 1);
			request.setAttribute("deliveryFee", fee);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 修改记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "message";
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			DeliveryFee data = new DeliveryFee();
			pageData.copy(data, 0);
			if (checkModifyInput(request, pageData)) {
				fee_dao.update(conn, data);
			} else {
				forward = "modify";
			}
			
			Message.setErrorMsg(request, "修改成功!");
			//request.setAttribute("deliveryFee", data);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 修改记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "list";
		DeliveryFeeForm pageData = (DeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String id = request.getParameter("searchId");
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			fee_dao.delete(conn, Integer.parseInt(id));
			pageData.setProvinceList(fee_dao.getProvinceList(conn));
			//pageData.setCityList(fee_dao.getCityList(conn, pageData));
			int size = fee_dao.countRecords(conn, pageData);
			Pager page = new Pager(pageData.getOffset(), size);//生成page对象
            page.setLength(15);
            pageData.setPager(page);
			request.setAttribute("list", fee_dao.findByCondition(conn, pageData));
			
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	/**
	 * 显示默认发送费修改页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showDefaultModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "default_modify";
		DefaultDeliveryFeeForm pageData = (DefaultDeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			Collection feeList = fee_dao.listDefaultDeliveryFee(conn);
			
			request.setAttribute("feeList", feeList);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 更新默认发送费修改页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateDefault(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "list";
		String id = request.getParameter("updateId");
		DefaultDeliveryFeeForm pageData = (DefaultDeliveryFeeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeDAO fee_dao = new DeliveryFeeDAO();
			if (pageData.getDeliveryFee() != null) {
				for (int i = 0; i < pageData.getId().length; i ++) {
					if (pageData.getId()[i] == Integer.parseInt(id)) {
						DefaultDeliveryFee fee = new DefaultDeliveryFee();
						fee.setId(pageData.getId()[i]);
						fee.setDeliveryFee(pageData.getDeliveryFee()[i]);
						//fee.setPackageFee(pageData.getPackageFee()[i]);
						fee_dao.updateDeliveryFeeByPk(conn, fee);
						break;
					}
				}
			}
			
			Message.setMessage(request, "更新成功!");
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 默认发送费同步到网站数据库
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward syncDefault(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "list";
		Connection conn1 = null;
		Connection conn2 = null;
		try {
			conn1 = DBManager.getConnection();
			conn2 = null;//DBManager3.getConnection();

			conn1.setAutoCommit(false);
			conn2.setAutoCommit(false);
			Sync sync = new DefaultDeliveryFeeSync();
			sync.execute(conn1, conn2);
			conn1.commit();
			conn2.commit();
			Message.setMessage(request, "同步成功!");
			
		} catch (Exception e) {
			if (!conn1.getAutoCommit()) {
				conn1.rollback();
			}
			if (!conn2.getAutoCommit()) {
				conn2.rollback();
			}
			
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn1 != null) {
				conn1.close();
			}
			if (conn2 != null) {
				conn2.close();
			}
		}
		return mapping.findForward(forward);
	}
	/**
	 * 新增数据检测
	 * @param request
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	private boolean checkAddInput(HttpServletRequest request,
			DeliveryFeeForm pageData) throws Exception {
		
		//头部检查
		if (pageData.getSearchProvince().equals("-1")) {
			Message.setErrorMsg(request, "请选择省份!");
			return false;
		}
		/*if (pageData.getBeginDateM() == null || pageData.getBeginDateM().equals("")) {
			Message.setErrorMsg(request, "开始日期为空或不合法!");
			return false;
		}
		if (pageData.getEndDateM() == null || pageData.getEndDateM().equals("")) {
			Message.setErrorMsg(request, "结束日期为空或不合法!");
			return false;
		}
		if (pageData.getBeginDateM().after(pageData.getEndDateM())) {
			Message.setErrorMsg(request, "开始日期不能大于结束日期!");
			return false;
		}*/
		if (pageData.getFeeM() < 0) {
			Message.setErrorMsg(request, "发送费不能小于0!");
			return false;
		}
		/*if (pageData.getRequireAmtM() < 0) {
			Message.setErrorMsg(request, "金额要求不能小于0!");
			return false;
		}
		
		if (pageData.getPostcode() == null
				|| pageData.getPostcode().length == 0) {
			Message.setErrorMsg(request, "没有选择要添加的纪录!");
			return false;
		}
		int len = pageData.getPostcode().length;
		
		for (int i = 0; i < len; i++) {
			
			if (pageData.getPostcode()[i] == null || pageData.getPostcode()[i].equals("")) {
				Message.setErrorMsg(request, "明细第" + (i + 1) + "行，邮编不能为空!");
				return false;
			}
		}*/
		return true;
	}
	
	/**
	 * 修改数据检测
	 * @param request
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	private boolean checkModifyInput(HttpServletRequest request,
			DeliveryFeeForm pageData) throws Exception {
		
		
		if (pageData.getFeeM() < 0) {
			Message.setErrorMsg(request, "发送费不能小于0!");
			return false;
		}
		
		if (pageData.getBeginDateM() == null || pageData.getBeginDateM().equals("")) {
			Message.setErrorMsg(request, "开始日期不正确!");
			return false;
		}
		
		if (pageData.getEndDateM() == null || pageData.getEndDateM().equals("")) {
			Message.setErrorMsg(request, "结束日期不正确!");
			return false;
		}
		
		if (pageData.getBeginDateM().after(pageData.getEndDateM())) {
			Message.setErrorMsg(request, "开始日期不能大于结束日期!");
			return false;
		}
		
		if (pageData.getRequireAmtM() < 0) {
			Message.setErrorMsg(request, "要求金额不能小于0!");
			return false;
		}
		
		
		return true;
	}
}
