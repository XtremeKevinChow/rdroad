package com.magic.crm.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.utils.reader.ReadFactory;
import com.magic.utils.reader.ReadToolsIF;
import com.magic.crm.member.form.PostCodeFileForm;

import com.magic.crm.member.upload.FileImport;
import com.magic.crm.member.entity.PostCodeFile;
import com.magic.crm.member.dao.PostCodeFileDAO;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

public class ImportPostcodeAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(ImportPostcodeAction.class);

	public static final String POSTCODE_FILE_PATH = "upload\\postcode\\";

	public ActionForward importFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// response.setCharacterEncoding("GBK");
		Connection conn = null;
		PostCodeFileForm myForm = (PostCodeFileForm) form;
		try {
			StringBuffer fullPath = new StringBuffer();
			fullPath.append(this.getServlet().getServletContext().getRealPath(
					"/"));
			fullPath.append(POSTCODE_FILE_PATH);

			File f = new File(fullPath.toString());
			if (!f.exists()) {
				f.mkdirs();
			}
			fullPath.append(myForm.getFile().getFileName());
			if (!myForm.getFile().getContentType().equals(
					"application/vnd.ms-excel")) {
				throw new Exception("必须是excel格式文件!");
			}
			FileImport.fileUpload2(fullPath.toString(), myForm.getFile(),
					request);

			ReadToolsIF readtool = ReadFactory.factory(1);

			readtool.setFile(fullPath.toString());
			readtool.read();

			HashMap readMap = readtool.getData();
			String[][] data = null;

			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			data = (String[][]) readMap.get(ReadToolsIF.R_DATA);// 从map中取出二维矩阵
			if (data == null) {
				logger.error("null file ");
				throw new Exception("文件不能空!");
			}
			PostCodeFile item = new PostCodeFile();
			for (int i = 1; i < data.length; i++) {
				item.setOrdNumber(data[i][0]);
				
				item.setOrdPostcode(data[i][1]);
				item.setOrdAddress(data[i][2]);
				item.setCardId(data[i][3]);
				item.setMbrPostcode(data[i][4]);
				item.setNewPostcode(data[i][5]);
				PostCodeFileDAO.insert(conn, item);
			}
			conn.commit();
			Message.setMessage(request, "导入成功!");
		} catch (Exception ex) {
			if (conn != null) {
				conn.rollback();
			}
			
			Message.setErrorMsg(request, ex.toString());
			 
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward("success");
	}

	public ActionForward showImportPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("init");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		// PostCodeFileForm myForm = (PostCodeFileForm) form;
		try {
			conn = DBManager.getConnection();
			Collection coll = PostCodeFileDAO.findAll(conn);
			request.setAttribute("list", coll);
		} catch (Exception ex) {
			Message.setMessage(request, "查询错误!");
			logger.error(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return mapping.findForward("query");
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		// PostCodeFileForm myForm = (PostCodeFileForm) form;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			Collection coll = PostCodeFileDAO.findAll(conn);
			Iterator it = coll.iterator();
			while (it.hasNext()) {
				PostCodeFile item = (PostCodeFile) it.next();
				if (!item.getNewPostcode().equals(item.getOrdPostcode())) {
					PostCodeFileDAO.updateOrderPostcode(conn, item);
					if (item.getOrdPostcode().equals(item.getMbrPostcode())) {
						PostCodeFileDAO.updateMemberPostcode(conn, item);
					}
				}

			}
			PostCodeFileDAO.deletePostCodeFile(conn);
			Message.setMessage(request, "更新成功!");
			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			Message.setMessage(request, "更新错误!");
			logger.error(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return mapping.findForward("success");
	}
}
