/*
 * Created on 2005-2-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.*;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.user.dao.S_AREADao;
import com.magic.crm.util.Constants;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberInitModifyAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	MemberForm fm = (MemberForm) form;
        Connection conn = null;
        //会员ID
        String id = request.getParameter("id");
        
        /** 如果是callcenter系统，从sessin中取出会员信息，如果没有提示登录服务对象 **/
        String iscallcenter = request.getParameter("iscallcenter");
        if (iscallcenter != null && iscallcenter.equals("1")) {
            CallCenterHander hander = new CallCenterHander(request.getSession());
            if (hander.isOnService()) {
                Member mb = hander.getServicedMember();
                id = String.valueOf(mb.getID());
            } else {
                ControlledError ctlErr = new ControlledError();
                ctlErr.setErrorTitle("操作错误");
                ctlErr.setErrorBody("没有服务对象，请接入服务");
                request.setAttribute(Constants.ERROR_KEY, ctlErr);
                return mapping.findForward("controlledError");

            }
        }
        //type=0、初始化更改会员基本信息
        //type=1、初始化修改会员送货地址
        //type=2、删除会员送货地址
        //type=3、初始化增加会员送货地址
        //type=4、修改会员送货地址
        String type = request.getParameter("type");//业务操作标记
        //地址ID
        String address_id = request.getParameter("address_id");
        Member member = new Member();
        MemberDAO memberDAO = new MemberDAO();
        try {
            conn = DBManager.getConnection();
            
            member = memberDAO.DetailMembers(conn, id);
            fm.setSection(S_AREADao.getFullBySection(conn, member.getSection()));
            
            fm.setSection(member.getSection());
        	fm.setCity(S_AREADao.getParent(conn, member.getSection()));
        	fm.setProvince(S_AREADao.getParent(conn, fm.getCity()));
        	ArrayList provs = S_AREADao.listProvince(conn);
    		fm.setProvs(provs);
    		fm.setCitys(S_AREADao.listCity(conn, fm.getProvince()));
    		fm.setSects(S_AREADao.listSection(conn, fm.getCity()));
    		
            request.setAttribute("member", member);
            //memberAddresses
            //更改会员基本信息
            MemberAddressDAO memberAddDAO = new MemberAddressDAO();

            if (type.equals("2")) {//删除会员送货地址
                if (!address_id.equals(member.getADDRESS_ID())) {
                    memberAddDAO.delete(conn, " and id=" + address_id);

                } else {
                    ControlledError ctlErr = new ControlledError();

                    ctlErr.setErrorTitle("操作错误");

                    ctlErr.setErrorBody("用户主地址不能删除");

                    request.setAttribute(Constants.ERROR_KEY, ctlErr);
                    return mapping.findForward("controlledError");

                }
            }
            Collection memberAddCol = memberAddDAO.QueryMemberAddresses(conn,
                    " and member_id=" + id);
            request.setAttribute("memberAddCol", memberAddCol);
            if (type.equals("1")) {
                Collection memberAddDetail = memberAddDAO.QueryMemberAddresses(
                        conn, " and id=" + address_id);
                Iterator it = memberAddDetail.iterator();
                if(it.hasNext()) {
                	MemberAddresses address = (MemberAddresses) it.next();
                    fm.setSection(address.getSection());
                	fm.setCity(S_AREADao.getParent(conn, address.getSection()));
                	fm.setProvince(S_AREADao.getParent(conn, fm.getCity()));
                	//ArrayList provs1 = S_AREADao.listProvince(conn);
            		//fm.setProvs(provs1);
            		fm.setCitys(S_AREADao.listCity(conn, fm.getProvince()));
            		fm.setSects(S_AREADao.listSection(conn, fm.getCity()));
            		
                }
                
                
                
                request.setAttribute("memberAddDetail", memberAddDetail);
                return mapping.findForward("modify");
            } else if (type.equals("3")) {
            	//ArrayList provs = S_AREADao.listProvince(conn);
        		//fm.setProvs(provs);
                return mapping.findForward("add");
            } else {
                return mapping.findForward("success");

            }
        } catch (SQLException se) {

            throw new ServletException(se);

        } finally {

            try {

                conn.close();

            } catch (SQLException sqe) {

                throw new ServletException(sqe);

            }

        }
    }
}
