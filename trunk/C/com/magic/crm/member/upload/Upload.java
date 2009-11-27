/*
 * Created on 2005-11-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.upload;

import com.magic.crm.util.Constants;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import com.magic.utils.StringUtil;
import java.util.Date;
import java.util.Random;

import com.magic.utils.reader.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;

import org.apache.log4j.Logger;

import com.magic.crm.common.CharacterFormat;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Upload extends HttpServlet {
	/**
	 * ���۵������ܵ���ʽ:
	 * ���*     ���*	 ��Ա���� 	     ��ַ	 ��ע	   �ʱ�*	    ��������*
	 * 
	 */
	/**
	 * �ֽ𡢻ص���ʽ
	 * ���	    ���*	   ��Ա����	   ��ע	   ���ʽ*    ��������*

	 */
	public final static long serialVersionUID = 54960808L;
	
	private static Logger logger = Logger.getLogger(Upload.class);
	
	final static int COL_YJ_DZD = 7; //�ʾִ��۵�����
	
	final static int COL_YJ_JMD = 7; //�ʾּ��ܵ�����
	
	final static int COL_XJ = 6; //�����ֽ�(�ص�)����
	/**
	 * the file import path key, configured in the web.xml
	 */
	private static final String IMPORT_PATH_KEY = "import";
	
	/**
	 * the file export path key, configured in the web.xml
	 */
	private static final String EXPORT_PATH_KEY = "export";

	/**
	 * default file import path
	 */
	private static final String DEFAULT_IMPORT_PATH = "D:\\jboss-3.2.5\\server\\default\\deploy\\99CRM.war\\import\\";

	/**
	 * default file import path
	 */
	private static final String DEFAULT_EXPORT_PATH = "D:\\jboss-3.2.5\\server\\default\\deploy\\99CRM.war\\export\\";
	
	/**
	 * common file import path
	 */
	private String importPath = null;
	
	/**
	 * common file export path
	 */
	private String exportPath = null;
	
	public void init(ServletConfig conf) {

		this.importPath = conf.getInitParameter(IMPORT_PATH_KEY);
		
		this.exportPath = conf.getInitParameter(EXPORT_PATH_KEY);
		
		if (importPath == null) {
			
			importPath = DEFAULT_IMPORT_PATH;
		}
		
		if (exportPath == null) {
			
			exportPath = DEFAULT_EXPORT_PATH;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setCharacterEncoding("GBK");
		HttpSession session = request.getSession();
		
		HashMap errMap = null;
		
		HashMap readMap = null;
		
		HashMap sxkMap = null;
		
		String fileName = null;

		int id;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

		String t = formatter.format(new Date());

		String type = request.getParameter("type");
		
		String fileType = request.getParameter("filetype");
		
		Connection conn = null;

		try {

			conn = DBManager.getConnection();

			id = MemberaddMoneyDAO.getImportFileId(conn);

		} catch (SQLException se) {

			throw new ServletException(se);

		} finally {

			try {

				conn.close();

			} catch (SQLException sqe) {

				throw new ServletException(sqe);
			}

		}
		ReadToolsIF readtool = null;
		try {
			
			readtool = ReadFactory.factory(Integer.parseInt(fileType));//���������������ļ���ȡ��
			
		} catch (FileTypeException fte) {
			
			fte.getMessage();
			StringBuffer sb = new StringBuffer();
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<link rel='stylesheet' href='css/style.css' type='text/css'>");
			sb.append("</head>");
			sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		    sb.append("<tr>");
		    sb.append("<td width='21'>&nbsp;</td>");
		    sb.append("<td>");
			sb.append("<b><font color='838383'>��ǰλ��</font></b><font color='838383'> : </font><font color='838383'>�ʻ�����</font><font color='838383'>"); 
			sb.append("-&gt; </font><font color='838383'>������</font><font color='838383'>"); 
		    sb.append("</td> </tr></table>");
		    sb.append("<br><br>");
		    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
		    sb.append("<tr>");
			sb.append("<td align='left'>  Ŀǰ��֧�����ָ�ʽ�ļ����롣</td>");
			sb.append("<tr>");
			sb.append("<td><form action='/member/member_addmoney_file.jsp'><input value='����' type='submit'></form></td>");
			sb.append("</tr></table>");
			sb.append("</html>");
			response.getWriter().println(sb);
			return;
			
		}
		//�ļ���ʽ:0_123456_2008-01-01
		fileName = type + "_" + id + "" + t + "." + ReadFactory.FILE_MAP.get(fileType);
		
		java.io.File f = new java.io.File(importPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		java.io.File f2 = new java.io.File(exportPath);
		if (!f2.exists()) {
			f2.mkdirs();
		}
		FileImport.fileUpload(request, importPath + fileName);//�ļ��ϴ�
		
		readtool.setFile(importPath + fileName);

		readtool.read();

		readMap = readtool.getData();//�õ�����excel����

		/*
		 * ��ȡ�ļ����������ݿ�MBR_MONEY_INPUT
		 */
		int j = 0, k = 0, m = 0;
		
		User user = new User();
		user = (User) session.getAttribute(Constants.USER_KEY);
		MemberaddMoneyDAO memberaddMoneyDAO = new MemberaddMoneyDAO();
		ArrayList list = new ArrayList();//error
		ArrayList list2 = new ArrayList();//sxk
		String[][] data = null;
		try {

			conn = DBManager.getConnection();

			if (readMap.get(ReadToolsIF.R_DATA) instanceof String[][]) {

				data = (String[][]) readMap.get(ReadToolsIF.R_DATA);//��map��ȡ����ά����
				if (data == null) {
					logger.error("null file ");
					return;
				}
				
				// step1  ����ʽ
				
				if (  ( type.equals("0") && data[0].length != COL_YJ_DZD ) //���۵�
						|| (type.equals("2") && data[0].length != COL_YJ_JMD) //���ܵ�
						|| (type.equals("3") && data[0].length != COL_XJ) //�ֽ�
						) {
					
					StringBuffer sb = new StringBuffer();
					sb.append("<html>");
					sb.append("<head>");
					sb.append("<link rel='stylesheet' href='css/style.css' type='text/css'>");
					sb.append("</head>");
					sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
				    sb.append("<tr>");
				    sb.append("<td width='21'>&nbsp;</td>");
				    sb.append("<td>");
					sb.append("<b><font color='838383'>��ǰλ��</font></b><font color='838383'> : </font><font color='838383'>�ʻ�����</font><font color='838383'>"); 
					sb.append("-&gt; </font><font color='838383'>������</font><font color='838383'>"); 
				    sb.append("</td> </tr></table>");
				    sb.append("<br><br>");
				    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
				    sb.append("<tr>");
					sb.append("<td align='left'>��ע���ʽ�����۵���"+COL_YJ_DZD+"�У����ܵ���"+COL_YJ_JMD+"�У��ֽ���"+COL_XJ+"�С�</td>");
					sb.append("<tr>");
					sb.append("<td><form action='/member/member_addmoney_file.jsp'><input value='����' type='submit'></form></td>");
					sb.append("</html>");
					response.getWriter().println(sb);
					
					java.io.File f3 = new java.io.File(importPath + fileName);
					
					boolean b=f3.delete();
					logger.info("delete file : " +b);
					return;
				}
				
				/* step2 ����ظ���
				 * ����Ǵ��۵������ܵ��ڲ�������֮ǰ��map���������5����������⿴�Ƿ����ļ��ظ�����
				 */
				if (type.equals("0") || type.equals("2")) {
					int num = 0;
					boolean isdup = false;
					for (int i = 0; i < 5; i ++) {
						if (data.length > 1) {
							num = this.numRandom(data.length - 1);
						}
						
						if (data[num][0] == null || data[num][0].trim().length() == 0) { //���۵������ܵ�����Ų�����
							i --;
							continue;
						}
						System.out.println("��ţ�" + data[num][0]);
						isdup = memberaddMoneyDAO.checkIsDupImport(conn, data[num][0] == null? null : data[num][0].trim());
						if (isdup) {
							logger.info("�ļ��ظ�����");
							StringBuffer sb = new StringBuffer();
							sb.append("<html>");
							sb.append("<head>");
							sb.append("<link rel='stylesheet' href='css/style.css' type='text/css'>");
							sb.append("</head>");
							sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
						    sb.append("<tr>");
						    sb.append("<td width='21'>&nbsp;</td>");
						    sb.append("<td>");
							sb.append("<b><font color='838383'>��ǰλ��</font></b><font color='838383'> : </font><font color='838383'>�ʻ�����</font><font color='838383'>"); 
							sb.append("-&gt; </font><font color='838383'>������</font><font color='838383'>"); 
						    sb.append("</td> </tr></table>");
						    sb.append("<br><br>");
						    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
						    sb.append("<tr>");
							sb.append("<td align='left'>������������Ѿ�����ϵͳ�������ٴε��롣</td>");
							sb.append("<tr>");
							sb.append("<td><form action='/member/member_addmoney_file.jsp'><input value='����' type='submit'></form></td>");
							sb.append("</html>");
							response.getWriter().println(sb);
							
							java.io.File f3 = new java.io.File(importPath + fileName);
							
							boolean b=f3.delete();
							logger.info("delete file : " +b);
							return;
						}
					}
				}
				
				
				/* step3 ���ݴ���
				 * �������ݵ�mbr_money_input
				 */
				MemberaddMoney memberaddMoney = new MemberaddMoney();
				for (int i = 1; i < data.length; i++) {
					
					try {
						
						/*
						 * pay_method�����ʽ����ȷ��
						 *
						 */
						if (type.equals("0") || type.equals("2")) { //���۵������ܵ�
							if (data[i][0] == null 
									|| data[i][0].trim().length() == 0 
									|| !StringUtil.isNum(data[i][0].trim())) {//û�л�Ż��߻�Ų������ֵļ�¼�����κδ���
								list.add(data[i]);
								continue;
							}
							memberaddMoney.setREF_ID(data[i][0]==null?null:data[i][0].trim()); //�ʾֻ�ţ�Ψһ��
							memberaddMoney.setMONEY(Double.parseDouble(data[i][1]==null?null:data[i][1].trim())); //�����
							memberaddMoney.setMB_NAME(data[i][2]==null?null:data[i][2].trim());//����
							memberaddMoney.setADDRESS(data[i][3]==null?null:data[i][3].trim());//��ַ
							memberaddMoney.setREMARK(data[i][4]==null?null:data[i][4].trim());//��ע
							memberaddMoney.setPostCode(data[i][5]==null?null:data[i][5].trim());//�ʱ�
							memberaddMoney.setBill_date(data[i][6]==null?null:data[i][6].trim());//��������
							memberaddMoney.setPayMethod(6);//֧����ʽ
						}
						
						else if (type.equals("3")) {// �ֽ�ص���ʽ
							memberaddMoney.setREF_ID(data[i][0]==null?null:data[i][0].trim());//���ݺţ�����Ϊ�գ�
							memberaddMoney.setMONEY(Double.parseDouble(data[i][1]==null?null:data[i][1].trim()));//�����
							memberaddMoney.setMB_NAME(data[i][2]==null?null:data[i][2].trim());//����
							memberaddMoney.setREMARK(data[i][3]==null?null:data[i][3].trim());//��ע
							// modify by zhux 20080727 change paymethod to �ֽ�֧��
							// memberaddMoney.setPayMethod(Integer.parseInt(data[i][4]==null?null:data[i][4].trim()));//֧����ʽ
							memberaddMoney.setPayMethod(14);
							
							memberaddMoney.setBill_date(data[i][5]==null?null:data[i][5].trim());//��������
							
						}
						memberaddMoney.setMB_ID(0);
						memberaddMoney.setORDER_ID(0);
						memberaddMoney.setStatus(0);
						memberaddMoney.setOPERATOR_ID(Integer.parseInt(user
								.getId()));
						memberaddMoney.setTYPE(type);
						
						//step 4 -----------������ȡ��Ա�š�������--------------------------------------------
						setMbCodeAndOrderCode(memberaddMoney, conn);
						//-------------------------------------------------------------------------
						
					
						/* step5 ���ݲ���
						 * use_type�ֶ�����Ϊ0��Ԥ��
						 */
						memberaddMoney.setUSE_TYPE("0");
						memberaddMoneyDAO.insert(conn, memberaddMoney);
						m ++;
					
						
					} catch (Exception e) {
						list.add(data[i]);//�ռ����Ϸ�����
						System.out.println("���:"+memberaddMoney.getREF_ID()+e.getMessage());
						j ++;
					}
				}
			} else {
				logger.error("type change error");
			}
			errMap = new HashMap();
			errMap.put(ReadToolsIF.W_DATA, list);
			if (list.size() > 0) {
				readtool.setErrorFile(exportPath + "error_"
						+ fileName);
				readtool.write(errMap, data[0].length);//���ɲ��Ϸ�xls�ļ�
			}
			sxkMap = new HashMap();
			sxkMap.put(ReadToolsIF.W_DATA, list2);
			if (list2.size() > 0) {
				readtool.setErrorFile(exportPath + "sxk_" + fileName);
				readtool.write(sxkMap, data[0].length);//���㿨��xls�ļ�
			}
		} catch (SQLException e) {
			
			logger.error("database connect error");
			
		}finally {
			try {
				if ( conn!= null )
					conn.close();
			}catch(SQLException se){
				logger.error("database colose error");
			}
		}
		logger.info("write error data into excel succeed!");
		//if(list.size() > 0 ) { //���쳣����
			//response.sendRedirect(request.getContextPath() + "/upload/" + ReadFactory.FILE_MAP.get(fileType) + "/"+ "error_"
					//+ fileName);
		//}else {
		String errUrl = request.getContextPath() + "/upload/export/"+ "error_" + fileName;
		String sxkUrl = request.getContextPath() + "/upload/export/"+ "sxk_" + fileName;
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<link rel='stylesheet' href='css/style.css' type='text/css'>");
		sb.append("</head>");
		sb.append("<table width='100%' border='0' cellspacing='2' cellpadding='2'>");
	    sb.append("<tr>");
	    sb.append("<td width='21'>&nbsp;</td>");
	    sb.append("<td>");
		sb.append("<b><font color='838383'>��ǰλ��</font></b><font color='838383'> : </font><font color='838383'>�ʻ�����</font><font color='838383'>"); 
		sb.append("-&gt; </font><font color='838383'>������</font><font color='838383'>"); 
	    sb.append("</td> </tr></table>");
	    sb.append("<br><br>");
	    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
	    sb.append("<tr>");
		sb.append("<td align='left'>�ļ�����ɹ�</td>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("����ɹ�: " + m + "��<br>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<a href='"+errUrl+"'>����ʧ��: " + j + "��</a><br>");
		sb.append("</td>");
		sb.append("</tr>");
		/*sb.append("<tr>");
		sb.append("<td>");
		sb.append("<a href='"+sxkUrl+"'>���㿨: " + k + "��</a><br>");
		sb.append("</td>");
		sb.append("</tr>");*/
		sb.append("</table>");
		sb.append("</html>");
		
		response.getWriter().println(sb);
			//response.sendRedirect("../member/memberqueryMoney.do?filename="+fileName+"&type="+type);
		//}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		this.doPost(request, response);
	}

	public void destory() {

		super.destroy();

	}
	
	/**
	 * ����һ��С���������������
	 * ������ڱ�ʾ�Ѿ�������ˣ����û�У���ʾû�е������
	 * @param max
	 * @return
	 */
	private int numRandom(int max) {
		Random rd = new Random();
		
		return rd.nextInt(max);
	}
	
	
	/**
	 * ������ȡ��Ա�š�������
	 * @param addmoney
	 * @param conn
	 * @throws Exception
	 */
	private void setMbCodeAndOrderCode(MemberaddMoney addmoney, Connection conn) throws Exception {
		
		MemberDAO mbdao = new MemberDAO();
		OrderDAO orderdao = new OrderDAO();
		
		String excelMbName = addmoney.getMB_NAME();
		String excelRemark = addmoney.getREMARK();
		String excelPostcode = addmoney.getPostCode();
		
		/* step1 ��ȡ������*/
		String orderCode = CharacterFormat.analyseNumber(excelRemark, 12); // ��"��ע"����ȡ������(1��ĸ+12����)
		if (orderCode != null && !orderCode.equals("")) {
			orderCode = CharacterFormat.analyseOrderType(excelRemark.toUpperCase(), orderCode);
			if (!orderdao.checkOrderCode(conn, orderCode)) { //�����Ų�����
				orderCode = null;//���������ÿ�
			}
		} 
		
		//��ĸO�滻������0
		if (orderCode == null ) {
			orderCode = CharacterFormat.analyseNumber(excelRemark, 11);
			if (orderCode != null && !orderCode.equals("")) {//�ҵ�11λ
				orderCode = CharacterFormat.analyseOrderType(excelRemark.toUpperCase(), "O" + orderCode);
				if (orderCode != null && !orderCode.equals("")) {
					orderCode = orderCode.substring(0,1) + "0" +orderCode.substring(2);
				}
				if (!orderdao.checkOrderCode(conn, orderCode)) { //�����Ų�����
					orderCode = null;//���������ÿ�
				}
			}
		}
		//��ȡ7+12λ
		if (orderCode == null) {
			orderCode = CharacterFormat.analyseNumber(excelRemark, 13);
			if (orderCode != null && !orderCode.equals("")) {
				if (orderCode.startsWith("7")) {
					orderCode = "T" + orderCode.substring(1);
					if (!orderdao.checkOrderCode(conn, orderCode)) {
						orderCode = null;//���������ÿ�
					}
				}
				
			}
		}
		//ȥ���пո���ȡ12λ
		if (orderCode == null) {
			String trimedExcelRemark = excelRemark.replaceAll(" ", "");
			orderCode = CharacterFormat.analyseNumber(trimedExcelRemark, 12);
			if (orderCode != null && !orderCode.equals("")) {
				orderCode = CharacterFormat.analyseOrderType(trimedExcelRemark.toUpperCase(), orderCode);
				if (!orderdao.checkOrderCode(conn, orderCode)) { //�����Ų�����
					orderCode = null;//���������ÿ�
				}
			} 
		}
		
		if (orderCode != null && !orderCode.equals("")) {
			if (!orderdao.checkOrderCode(conn, orderCode)) { //�����Ų�����
				orderCode = null;//���������ÿ�
			}
		}
		
		
		/* step2 ��ȡ��Ա��*/
		String mbCode = CharacterFormat.analyseNumber(excelMbName, 8); // ��"���������"��ȡ��Ա��(8λ)
		if (mbCode != null && !mbCode.equals("")) {
			mbCode = mbdao.checkMBCardIDSEQ2(conn, mbCode);
		}
		//���"���������"�ֶ�����ȡ������Ա�ţ�����֮ǰ������û��ȡ������"��Ա����"��ȡ��Ա��
		if (mbCode == null && orderCode == null) {
			mbCode = CharacterFormat.analyseNumber(excelRemark, 8); // �ӻ��Ա����"��ȡ��Ա��(8λ)
			if (mbCode != null && !mbCode.equals("")) {
				mbCode = mbdao.checkMBCardIDSEQ2(conn, mbCode); //���ϵͳ���Ǵ��ڸ�8λ���ִ��Ļ�Ա�Ż�����ʱ��Ա�ţ�û�оͷ���null
			}
		}
		
		/* step3  ��������źͻ�Ա�Ŷ���ȡ��������Ҫ���ݻ��������(���ʱ�ǰ4λ)��ƥ���Ա�� */
		if ((orderCode == null || orderCode.trim().equals(""))
				&& (mbCode == null || mbCode.trim().equals(""))) { // �����Ż�Ա�Ŷ��ǿ�
			//String queryName = getMemberName(excelMbName == null ? "" : excelMbName.trim());
			String queryName = CharacterFormat.separateString(excelMbName == null ? "" : excelMbName.trim());
			String queryPostcode = "";
			// ��ȡ�ʱ�ǰ4λ�������ʱ�Ϊ��
			if (excelPostcode != null && excelPostcode.length() >= 4) {
				queryPostcode = excelPostcode.substring(0, 4);
			}
			
			//ͨ����ȡ���������ʱ�4λ���һ�Ա��Ϣ(�ظ���ᣬ��Ч��ԱӦ�ù��˵�??????????????????????????)
			ArrayList list = (ArrayList)mbdao.queryMemberBasic(conn, queryName, queryPostcode);
			
			if (list.size() == 0) {// û��ƥ��

			} else if (list.size() == 1) {// ƥ�䵽1��

				Member member = (Member) list.get(0);
				mbCode = member.getCARD_ID(); //�õ���Ա��

			} else {// ƥ�䵽���

			}

		}
		addmoney.setMB_CODE(mbCode);
		addmoney.setORDER_CODE(orderCode);
	}
}