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
        //String remark[] = request.getParameterValues("ORDER_CODE1");//�����Ķ�����
        String order_code[] = request.getParameterValues("ORDER_CODE");
        String id[] = request.getParameterValues("ID");
        String[] payMethod = request.getParameterValues("payMethod");//���ʽ
        String[] flag1 = request.getParameterValues("flag1");//�Ƿ��ֵ
        String[] flag2 = request.getParameterValues("flag2");//�Ƿ񷵻ؽ��
        String[] flag3 = request.getParameterValues("flag3");//�Ƿ�����Ʒ
        String[] memberName = request.getParameterValues("MB_NAME");//��Ա����

        /**
         * �������ֵ����
         * 1. ȡ�ÿͻ����ύ�ļ�¼����ѡflag1�ļ�¼
         * 2. ����ж����ţ����ȸ��ݶ����ų�ֵ����������Ų���ȷ����¼״̬��Ϊ"��ֵʧ��"״̬
         * 3. ���û�ж����ţ����ݻ�Ա�źͻ�Ա������ֵ�������Ա�źͻ�Ա������ͬʱƥ�䣬��¼״̬��Ϊ"��ֵʧ��"״̬
         * 4. �ύ�����ݽ�����̨�洢����������
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

            //����ѭ����ֵ
            for (int j = 0; j < flag1.length; j++) {
                
                try {
                    //��������
                    conn.setAutoCommit(false);
                    
                    //û�й�ѡ "�Ƿ��ֵ" ������һѭ��
                    if (flag1[j] == null || "false".equals(flag1[j])) {
                        continue;
                    }
                     
                    //���ݻ����mbr_money_input�����жϸñʻ���Ƿ��ֵ�������ֵ������һѭ��
                    if (memberaddMoneyDAO.checkisRef(conn, id[j])) {
                        continue;
                    }

                    //��Ա�����Ƿ�ƥ��ı�ǣ����������Ϊ�մ˱��Ϊtrue
                    boolean flag = false;
                    String memberName2 = CharacterFormat
                            .separateString(memberName[j]);

                    /*
                     * ��ʼ��ֵ ��������Ų�Ϊ������ݶ����Ÿ���Ա��ֵ����������Ƿ����� ���������Ϊ������ݻ�Ա�Ÿ���Ա��ֵ
                     */
                    if ((order_code[j] != null && !order_code[j].equals(""))
                            || (mb_code[j] != null && !mb_code[j].equals(""))) {

                        if (order_code[j] != null && !order_code[j].equals("")) {
                        	order_code[j] = order_code[j].trim();//ȥ�ո�
                            
                            // add by user 2007-12-29
                            memberID = orderDAO.getMemberID(conn, order_code[j]);
                            
                            member = memberDAO.getMembers(conn, memberID);
                            flag = memberName2.equals(member.getNAME());
                            //�õ�����ID
                            orderid = orderDAO.getOrderID(conn, order_code[j]);
                        }

                        if (mb_code[j] != null && !mb_code[j].equals("")) {
                            mb_code[j] = mb_code[j].trim();//ȥ�ո�
                            //���ݻ�Ա���ŵõ���ԱID
                            member = new Member();
                            member = memberDAO.getMemberInfo(conn, mb_code[j]);
                            memberID = member.getID();
                            flag = memberName2.equals(member.getNAME());
                        }                     

                        //�ж���ʼ�¼�Ƿ��Ѿ���ֵ
                        if (memberDAO.checkPostNum(conn, ref_id[j]) == 0) {
                            
                            //���ó�ֵ����
                            successPrepay = successPrepay + 1;
                            totalMoney = totalMoney
                                    + Double.parseDouble(money[j]);//�ܽ��
                            memberaddMoney.setID(Integer.parseInt(id[j]));//ID
                            memberaddMoney.setMB_ID(memberID);//��ԱID
                            memberaddMoney.setStatus(1);//״̬
                            memberaddMoney.setOPERATOR_ID(Integer.parseInt(user
                                    .getId()));//������
                            memberaddMoney.setORDER_ID(orderid);//����ID
                            memberaddMoney.setORDER_CODE(order_code[j]);//������
                            memberaddMoney.setMB_CODE(mb_code[j]);//��Ա��
                            
                            cstmt.setString(2, order_code[j]);//������
                            cstmt.setString(3, mb_code[j]);//��Ա��
                            cstmt.setDouble(4, Double.parseDouble(money[j]));//��ֵ���
                            //cstmt.setInt(5, 6);
                            cstmt.setInt(5, Integer.parseInt(payMethod[j]));
                            cstmt.setNull(6, java.sql.Types.VARCHAR);
                            cstmt.setInt(7, Integer.parseInt(user.getId()));
                            cstmt.setString(8, ref_id[j]
                                    + "��"
                                    + (order_code[j].equals("") ? mb_code[j]
                                            : order_code[j]));

                            
                            //�Ƿ��Զ���������ҵ���߼��ɺ�˴洢���̾�����ǰ���ݲ�Ҫ����
                            cstmt.setInt(9, "true".equals(flag2[j]) ? 1 : 0);//�Ƿ�������Ĭ��������
                            cstmt.setInt(10, "true".equals(flag2[j]) ? 1 : 0);//�Ƿ��ͷ��ؽ�
                            cstmt.setInt(11, 0);//�Ƿ�������Ʒ
                            cstmt.setString(12,ref_id[j]);
                            cstmt.setNull(13, java.sql.Types.NUMERIC);
                            cstmt.setInt(14, orderid == 0 ? 3 : 4);
                            //��������޷�ƥ���ֵʧ��
                            if (!flag) {
                                MemberDAO.updateDepositFail(conn,memberaddMoney);
                                conn.commit();
                                System.out.println("������ƥ��");
                                continue;
                                
                            }
                            
                            cstmt.registerOutParameter(1,
                                    java.sql.Types.INTEGER);
                            
                            System.out.println("�����ţ�"+order_code[j]);
                            System.out.println("��Ա�ţ�"+mb_code[j]);
                            System.out.println("��ֵ��"+money[j]);
                            System.out.println("��ʽ��"+payMethod[j]);
                            System.out.println("���ڣ�");
                            System.out.println("��½��ID��"+user.getId());
                            System.out.println("��ע��"+ref_id[j]
                                                            + "��"
                                                            + (order_code[j].equals("") ? mb_code[j]
                                                                    : order_code[j]));
                            System.out.println("�Ƿ�������"+(flag2[j].equals("true") ? 1 : 0));
                            System.out.println("��ţ�"+ref_id[j]);
                            System.out.println("���ͣ�"+(orderid == 0 ? 3 : 4));
                            cstmt.execute();
                            int ret = cstmt.getInt(1);
                            
                            if (ret >= 0) { //��ȷ
                                int updateRecod = memberDAO.updateAddMomeyInfo(
                                        conn, memberaddMoney);
                                if (updateRecod != 1) {
                                    conn.rollback();
                                }

                            } else { //����
    							
    							System.out.println(user.getNAME()+"����"+(order_code[j].equals("")? mb_code[j]:order_code[j])+":��ֵʧ��"+ref_id[j]);
                                if (ret == -100) {//����������
                                    MemberDAO.updateDepositFail(conn,memberaddMoney);
                                    System.out.println("������: " + order_code[j]
                                            + " ������");
                                } else if (ret == -101) { //��Ա�Ų�����
                                    MemberDAO.updateDepositFail(conn,memberaddMoney);
                                    System.out.println("��Ա��: " + mb_code[j]
                                            + " ������");
                                } else { //��������
                                    conn.rollback();
                                    System.out.println("��������" + ret);
                                }
                            }

                        }
                    } else { //��������źͻ�Ա�Ŷ��ǿ�
                        continue;
                    }
                    //�ύ����
                    conn.commit();
                    
                    if (orderid > 0) { //�ж���id������һ�¶���
                    	cstmt1.registerOutParameter(1,
                                java.sql.Types.INTEGER);
                        cstmt1.setInt(2, orderid);
                        cstmt1.execute();
                    }
                    
                    
                } catch (SQLException e) {//ѭ�����쳣����
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