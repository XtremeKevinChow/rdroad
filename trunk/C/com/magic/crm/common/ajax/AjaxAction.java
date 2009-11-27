package com.magic.crm.common.ajax;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.common.Constants;
import com.magic.crm.common.DBOperation;
import com.magic.crm.user.dao.S_AREADao;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.order.form.OrderForm;
import java.util.Collection;
import java.util.Iterator;
public class AjaxAction extends DispatchAction {
	public ActionForward getItemNameByCode(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
		String itemCode = request.getParameter("itemCode");
		ProductDAO productDao = new ProductDAO();
		java.sql.Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ProductForm ProductForm = productDao.findByCode(conn, itemCode);
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			
			if (ProductForm == null || ProductForm.getItemID()==null) {
				response.getWriter().print("书号："+itemCode + "不存在");
			} else {
				response.getWriter().print(ProductForm.getName());
			}
			
		} catch(Exception ex) {
			throw new ServletException();
        }finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }
        }
		
		return null;
	}
	/**
	 * 检查数量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkItemQty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String itemCode = request.getParameter("itemCode");
		String qty = request.getParameter("qty");
		java.sql.Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ProductDAO productDao = new ProductDAO();
			ProductForm ProductForm = productDao.findByCode(conn, itemCode);
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			if (Integer.parseInt(qty) > ProductForm.getMaxsalenum()) {
				response.getWriter().print("不能超过" + ProductForm.getMaxsalenum()+"本");
				return null;
			}
			
			
		} catch(Exception ex) {
			throw new ServletException();
        }finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }
        }
        return null;
	}
	/**
	 * 通过邮编得到省市地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward getAddressByPostcode(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String postcode = request.getParameter("postcode");
		java.sql.Connection conn = null;
		try {
			conn = DBManager.getConnection();
			Collection coll = BaseDataDAO.getCityByPostcode(conn, postcode);
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			if (postcode.length() < 6) {
				response.getWriter().print("error");
				return null;
			}
			
			String rtnMsg = showMsg(coll);
			if (rtnMsg.equals("error")) {
				postcode = postcode.substring(0,4) + "00";
				coll = BaseDataDAO.getCityByPostcode(conn, postcode);
				rtnMsg = showMsg(coll);
			}
		
			response.getWriter().print(rtnMsg);
		} catch(Exception ex) {
			throw new ServletException();
        }finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }
        }
        return null;
	}*/
	/**
	 * 通过卡号得到名字
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getNameByCardId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String cardId = request.getParameter("cardId");
		java.sql.Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberDAO memberDao = new MemberDAO();
			Member member = memberDao.getMemberInfo(conn, cardId);
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			response.getWriter().print(member.getNAME());
		} catch(Exception ex) {
			throw new ServletException();
        }finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }
        }
        return null;
	}
	private String showMsg( Collection coll) throws Exception {
		if (coll.size() == 0) {
			return "error";
		} else {
			StringBuffer str = new StringBuffer();
			Iterator it = coll.iterator();
			
			if (coll.size() == 1) {
				if (it.hasNext()) {
					String[] post = (String[])it.next();
					//str.append("<input type='text' size='6' readonly name='realPostcode' value=");
					//str.append(post[2]);
					//str.append(">&nbsp;&nbsp;");
					//str.append("<input type=\"text\"  name=\"address\" size=\"10\" value=");
					str.append(post[0]);
					//str.append(">省（市）");
					//str.append("<input type=\"text\" name=\"address1\" size=\"10\" value=");
					str.append(post[1]);
					//str.append(">市（区、县）");
					
				}
				
			} else {
				int i = 1;
				while (it.hasNext()) {
					String[] post = (String[])it.next();
					if (i == 1) {
						//str.append("<input type=text size='6' readonly name='realPostcode' value=");
						//str.append(post[2]);
						//str.append(">&nbsp;&nbsp;");
						//str.append("<input type=\"text\" readonly name=\"address\" size=\"10\" value=");
						str.append(post[0]);
						//str.append(">省（市）");
						//str.append("<select type=\"text\" name=\"address1\" onchange=\"selectCity(this);\" style=\"width:76\">");
					}
					//str.append("<option value=");
					//str.append(post[1]);
					//str.append(">");
					//str.append(post[1]);
					//str.append("</option>");
					i ++;
				}
				//str.append("</select>市（区、县）");
				
			}
			return str.toString();
		}
	}
	/**
	 * 检查输入产品的库存状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkStockStatusByItemCode(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		OrderForm orderForm  = new OrderForm();
		OrderForm sessData = 
			(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
		OrderForm.copyData(sessData,orderForm);
		String itemCode = request.getParameter("itemCode");
		String qty = request.getParameter("qty");
		
		orderForm.setQueryItemCode(itemCode);
		
		User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
		
		java.sql.Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DBOperation db = new DBOperation(conn);
			ItemInfo item = OrderDAO.findItem(conn, orderForm.getQueryItemCode());
			int nAvailableQty = OrderDAO.getAvailableStockQty(db, item, orderForm);
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			
			if (item == null || item.getItemCode()==null) {
				response.getWriter().print("书号："+itemCode + "不存在");
			} else {
				ProductDAO productDao = new ProductDAO();
				ProductForm ProductForm = productDao.findByCode(conn, itemCode);
				if (Integer.parseInt(qty) > ProductForm.getMaxsalenum()) {
					response.getWriter().print("不能超过" + ProductForm.getMaxsalenum()+"本");
					return null;
				}
				
				if (nAvailableQty < Integer.parseInt(qty)) { // 库存不足
					item.setStockStatusId(1);
					if (item.getIs_pre_sell() == 1) {
						
						response.getWriter().print("预售缺货");
					}
					else if (item.isLastSell()) {
						OrderDAO.registerOOS(conn, Integer.parseInt(ProductForm.getItemID()), Integer.parseInt(user.getId()));
						response.getWriter().print("永久缺货");
					}
					else {
						OrderDAO.registerOOS(conn, Integer.parseInt(ProductForm.getItemID()), Integer.parseInt(user.getId()));
						response.getWriter().print("暂时缺货");
					}
				} else {
	
					response.getWriter().print("库存正常");
				}
			}
			
		} catch(Exception ex) {
			throw new ServletException();
        }finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }
        }
		
		return null;
	}
	
	/**
	 * @deprecated
	 * 查询会员最近购买记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryMemberPurchaseRecords(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		OrderForm orderForm  = new OrderForm();
		StringBuffer htmlStr = new StringBuffer();
		java.sql.Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberDAO memberDAO = new MemberDAO();
			String cardId = request.getParameter("cardId");
			Collection coll = memberDAO.getMemberBuy(conn, "'"+cardId+"'");
			Iterator it = coll.iterator();
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			htmlStr.append("<table width=\"95%\" align=\"center\" border=0 cellspacing=1 cellpadding=2 >\n");
			htmlStr.append("<TBODY>\n");
			htmlStr.append("<TR>\n");
			htmlStr.append("<TH width=\"10%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >订单号</TH>\n");
			htmlStr.append("<TH width=\"6%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >会员号码</TH>\n");
			htmlStr.append("<TH width=\"6%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >会员姓名</TH>\n");
			htmlStr.append("<TH width=\"6%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >订单金额</TH>\n");
			htmlStr.append("<TH width=\"6%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >应付金额</TH>\n");
			htmlStr.append("<TH width=\"6%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >订单状态</TH>\n");
			htmlStr.append("<TH width=\"6%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >发送方式</TH>\n");
			htmlStr.append("<TH width=\"6%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >订单类型</TH>\n");
			htmlStr.append("<TH width=\"13%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >下单日期</TH>\n");
			htmlStr.append("<TH width=\"6%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >经办人员</TH>\n");
			htmlStr.append("<TH width=\"13%\"  bgcolor=\"#ECECD1\" noWrap align=middle  >操作</TH>\n");
			htmlStr.append("</TR>\n");
			while (it.hasNext()) {
				OrderForm order = (OrderForm)it.next();
				htmlStr.append("<TR>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=middle>\n");
				htmlStr.append("<A href=\"../order/orderView.do?orderId="+order.getOrderId()+"target=\"_blank\">"+order.getOrderNumber()+"</A></TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=left >"+order.getCardId()+"</TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=left >"+order.getMbName()+"</TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=right >"+order.getTotalMoney()+"</TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=right >"+order.getPayable()+"</TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=left >"+order.getStatusName()+"</TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=left >"+order.getDeliveryTypeName()+"</TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=left >"+order.getCategoryName()+"</TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=left >"+order.getCreateDate()+"</TD>\n");
				htmlStr.append("<TD class=OraTableCellText noWrap align=left>");
				if(order.getCreatorName().equals("")) {
					htmlStr.append("无");
				}else {
					htmlStr.append(order.getCreatorName());
				}
				htmlStr.append("</TD>\n");
				
				htmlStr.append("<TD class=OraTableCellText noWrap align=\"center\">");
				if (order.getStatusId() >= 25 && order.getStatusId() <= 100) {
					htmlStr.append("<input name=\"btn\" type=\"button\" value=\"发货单\" onclick=queryFahuo('/order/snQry.do','"+order.getOrderNumber()+"') >");
					
				} else if(order.getStatusId() == -8) {
					htmlStr.append("<input name=\"btn\" type=\"button\" value=\"发货单\" onclick=queryFahuo('/order/snQry.do','"+order.getOrderNumber()+"') >");
				}
				htmlStr.append("</TD>\n");
				htmlStr.append("</TR>\n");
				
			}
			htmlStr.append("</TBODY>\n");
			htmlStr.append("</TABLE>");
			response.getWriter().print(htmlStr.toString());
			
		} catch(Exception ex) {
			throw new ServletException();
        }finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }
        }
		
		return null;
	}
	
	
	public ActionForward getCitysByProvince(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String province = request.getParameter("province");
		java.sql.Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String citys = S_AREADao.listCityStr(conn, province);
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			response.getWriter().print(citys);
		} catch(Exception ex) {
			throw new ServletException();
        }finally {
            try {
                conn.close();
            } catch (SQLException sqe) {

            }
        }
        return null;
	}
	
	public ActionForward getSectionByCity(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String city = request.getParameter("city");
		java.sql.Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String citys = S_AREADao.listSectionStr(conn, city);
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			response.getWriter().print(citys);
		} catch(Exception ex) {
			throw new ServletException();
        }finally {
            try {
                conn.close();
            } catch (SQLException sqe) {

            }
        }
        return null;
	}	
}
