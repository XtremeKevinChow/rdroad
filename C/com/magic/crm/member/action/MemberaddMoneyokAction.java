/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

import com.magic.crm.common.CharacterFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.member.entity.Member;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberaddMoneyokAction extends Action {

    private static Logger log = Logger.getLogger(MemberaddMoneyokAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        User user = new User();
        user = (User) session.getAttribute("user");
        Member member = null;
        Connection conn = null;
        CallableStatement cstmt = null;
        CallableStatement cstmt1 = null;
        MemberaddMoneyDAO memberaddMoneyDAO = new MemberaddMoneyDAO();
        MemberaddMoney memberaddMoney = new MemberaddMoney();
        MemberDAO memberDAO = new MemberDAO();
        OrderDAO orderDAO = new OrderDAO();
        String ref_id[] = request.getParameterValues("REF_ID");
        //String mb_code[] = request.getParameterValues("MB_CODE1");
        String mb_code[] = request.getParameterValues("MB_CODE");
        String money[] = request.getParameterValues("MONEY");
        //String remark[] = request.getParameterValues("ORDER_CODE1");//处理后的订单号
        String order_code[] = request.getParameterValues("ORDER_CODE");
        String id[] = request.getParameterValues("ID");
        String[] payMethod = request.getParameterValues("payMethod");//付款方式
        String[] flag1 = request.getParameterValues("flag1");//是否充值
        String[] flag2 = request.getParameterValues("flag2");//是否返回金额
        String[] flag3 = request.getParameterValues("flag3");//是否送礼品
        String[] memberName = request.getParameterValues("MB_NAME");//会员姓名

        /**
         * 批处理充值流程
         * 1. 取得客户端提交的记录，钩选flag1的记录
         * 2. 如果有订单号，优先根据订单号充值，如果订单号不正确，记录状态置为"充值失败"状态
         * 3. 如果没有订单号，根据会员号和会员姓名充值，如果会员号和会员姓名不同时匹配，记录状态置为"充值失败"状态
         * 4. 提交的数据交给后台存储过程来处理
         */
        try {
            conn = DBManager.getConnection();
            cstmt1 = conn.prepareCall("{? = call service.f_order_run(?, 0) }");
            cstmt = conn
                    .prepareCall("{? = call MEMBER.f_member_add_money(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            double totalMoney = 0;
            int successPrepay = 0;
            int memberID = 0;
            int orderid = 0;

            //批量循环充值
            for (int j = 0; j < flag1.length; j++) {
                
                try {
                    //设置事务
                    conn.setAutoCommit(false);
                    
                    //没有勾选 "是否充值" 继续下一循环
                    if (flag1[j] == null || "false".equals(flag1[j])) {
                        continue;
                    }
                     
                    //根据汇款导入表mbr_money_input主键判断该笔汇款是否充值，如果充值继续下一循环
                    if (memberaddMoneyDAO.checkisRef(conn, id[j])) {
                        continue;
                    }

                    //会员姓名是否匹配的标记，如果订单不为空此标记为true
                    boolean flag = false;
                    String memberName2 = CharacterFormat
                            .separateString(memberName[j]);

                    /*
                     * 开始冲值 如果订单号不为空则根据订单号给会员充值，并检查款货是否满足 如果订单号为空则根据会员号给会员充值
                     */
                    if ((order_code[j] != null && !order_code[j].equals(""))
                            || (mb_code[j] != null && !mb_code[j].equals(""))) {

                        if (order_code[j] != null && !order_code[j].equals("")) {
                        	order_code[j] = order_code[j].trim();//去空格
                            
                            // add by user 2007-12-29
                            memberID = orderDAO.getMemberID(conn, order_code[j]);
                            
                            member = memberDAO.getMembers(conn, memberID);
                            flag = memberName2.equals(member.getNAME());
                            //得到订单ID
                            orderid = orderDAO.getOrderID(conn, order_code[j]);
                        }

                        if (mb_code[j] != null && !mb_code[j].equals("")) {
                            mb_code[j] = mb_code[j].trim();//去空格
                            //根据会员卡号得到会员ID
                            member = new Member();
                            member = memberDAO.getMemberInfo(conn, mb_code[j]);
                            memberID = member.getID();
                            flag = memberName2.equals(member.getNAME());
                        }                     

                        //判断这笔记录是否已经充值
                        if (memberDAO.checkPostNum(conn, ref_id[j]) == 0) {
                            
                            //设置充值数据
                            successPrepay = successPrepay + 1;
                            totalMoney = totalMoney
                                    + Double.parseDouble(money[j]);//总金额
                            memberaddMoney.setID(Integer.parseInt(id[j]));//ID
                            memberaddMoney.setMB_ID(memberID);//会员ID
                            memberaddMoney.setStatus(1);//状态
                            memberaddMoney.setOPERATOR_ID(Integer.parseInt(user
                                    .getId()));//操作人
                            memberaddMoney.setORDER_ID(orderid);//订单ID
                            memberaddMoney.setORDER_CODE(order_code[j]);//订单号
                            memberaddMoney.setMB_CODE(mb_code[j]);//会员号
                            
                            cstmt.setString(2, order_code[j]);//订单号
                            cstmt.setString(3, mb_code[j]);//会员号
                            cstmt.setDouble(4, Double.parseDouble(money[j]));//充值金额
                            //cstmt.setInt(5, 6);
                            cstmt.setInt(5, Integer.parseInt(payMethod[j]));
                            cstmt.setNull(6, java.sql.Types.VARCHAR);
                            cstmt.setInt(7, Integer.parseInt(user.getId()));
                            cstmt.setString(8, ref_id[j]
                                    + "："
                                    + (order_code[j].equals("") ? mb_code[j]
                                            : order_code[j]));

                            
                            //是否自动升级，本业务逻辑由后端存储过程决定，前端暂不要求处理
                            cstmt.setInt(9, "true".equals(flag2[j]) ? 1 : 0);//是否升级（默认升级）
                            cstmt.setInt(10, "true".equals(flag2[j]) ? 1 : 0);//是否送返回金
                            cstmt.setInt(11, 0);//是否赠送礼品
                            cstmt.setString(12,ref_id[j]);
                            cstmt.setNull(13, java.sql.Types.NUMERIC);
                            cstmt.setInt(14, orderid == 0 ? 3 : 4);
                            //如果姓名无法匹配充值失败
                            if (!flag) {
                                MemberDAO.updateDepositFail(conn,memberaddMoney);
                                conn.commit();
                                System.out.println("姓名不匹配");
                                continue;
                                
                            }
                            
                            cstmt.registerOutParameter(1,
                                    java.sql.Types.INTEGER);
                            
                            System.out.println("订单号："+order_code[j]);
                            System.out.println("会员号："+mb_code[j]);
                            System.out.println("充值金额："+money[j]);
                            System.out.println("方式："+payMethod[j]);
                            System.out.println("日期：");
                            System.out.println("登陆人ID："+user.getId());
                            System.out.println("备注："+ref_id[j]
                                                            + "："
                                                            + (order_code[j].equals("") ? mb_code[j]
                                                                    : order_code[j]));
                            System.out.println("是否升级："+(flag2[j].equals("true") ? 1 : 0));
                            System.out.println("汇号："+ref_id[j]);
                            System.out.println("类型："+(orderid == 0 ? 3 : 4));
                            cstmt.execute();
                            int ret = cstmt.getInt(1);
                            
                            if (ret >= 0) { //正确
                                int updateRecod = memberDAO.updateAddMomeyInfo(
                                        conn, memberaddMoney);
                                if (updateRecod != 1) {
                                    conn.rollback();
                                }

                            } else { //错误
    							
    							System.out.println(user.getNAME()+"根据"+(order_code[j].equals("")? mb_code[j]:order_code[j])+":充值失败"+ref_id[j]);
                                if (ret == -100) {//订单不存在
                                    MemberDAO.updateDepositFail(conn,memberaddMoney);
                                    System.out.println("订单号: " + order_code[j]
                                            + " 不存在");
                                } else if (ret == -101) { //会员号不存在
                                    MemberDAO.updateDepositFail(conn,memberaddMoney);
                                    System.out.println("会员号: " + mb_code[j]
                                            + " 不存在");
                                } else { //其他错误
                                    conn.rollback();
                                    System.out.println("其他错误" + ret);
                                }
                            }

                        }
                    } else { //如果订单号和会员号都是空
                        continue;
                    }
                    //提交事务
                    conn.commit();
                    
                    if (orderid > 0) { //有订单id就运行一下订单
                    	cstmt1.registerOutParameter(1,
                                java.sql.Types.INTEGER);
                        cstmt1.setInt(2, orderid);
                        cstmt1.execute();
                    }
                    
                    
                } catch (SQLException e) {//循环内异常处理
                    conn.rollback();
                    log.error(e.getMessage());
                }
            }//end for

            return mapping.findForward("success");

        } catch (Exception e) {
            conn.rollback();
            log.error(e.getMessage());
            throw new ServletException(e);

        } finally {

            try {
                cstmt.close();
                cstmt1.close();
                conn.close();

            } catch (SQLException sqe) {

                throw new ServletException(sqe);

            }

        }
    }

}