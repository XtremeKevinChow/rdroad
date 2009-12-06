/*
 * add by Floyd Jiang 2009.11.30
 */
package com.magic.crm.member.upload;

import com.magic.crm.util.Constants;
import com.magic.crm.util.Message;

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
import com.magic.crm.member.bo.MemberBO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberAddressDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberAddresses;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import java.util.HashMap;
import java.util.ArrayList;

public class TaobaoMemberUpload extends HttpServlet {
	/**
	 * ��ʽ:
	 * 
	 * �������	��һ�Ա��	���֧�����˺�	�ջ�������	�ջ���ַ 
	 * 
	 */
	
	public final static long serialVersionUID = 54960818L;
	
	private static Logger logger = Logger.getLogger(TaobaoMemberUpload.class);
	
	
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
	private static final String DEFAULT_IMPORT_PATH = "D:\\";

	/**
	 * default file import path
	 */
	private static final String DEFAULT_EXPORT_PATH = "D:\\";
	
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

		int id = 0;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

		String t = formatter.format(new Date());

		//String type = request.getParameter("type");
		
		String fileType = request.getParameter("filetype");
		
		Connection conn = null;

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
			sb.append("-&gt; </font><font color='838383'>������</font><font color='838383'>"); 
		    sb.append("</td> </tr></table>");
		    sb.append("<br><br>");
		    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
		    sb.append("<tr>");
			sb.append("<td align='left'>  Ŀǰ��֧�����ָ�ʽ�ļ����롣</td>");
			sb.append("<tr>");
			sb.append("<td><form action='/member/member_import_taobao.jsp'><input value='����' type='submit'></form></td>");
			sb.append("</tr></table>");
			sb.append("</html>");
			response.getWriter().println(sb);
			return;
			
		}
		//�ļ���ʽ:0_123456_2008-01-01
		fileName = "Taobao_"  + id + "" + t + "." + ReadFactory.FILE_MAP.get(fileType);
		
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
		 * ��ȡ�ļ�������Member����
		 */
		int j = 0, k = 0, m = 0;
		
		User user = new User();
		user = (User) session.getAttribute(Constants.USER_KEY);
		
		
		ArrayList list = new ArrayList();//error
		ArrayList list2 = new ArrayList();//sxk
		String[][] data = null;
		 StringBuffer strError = new StringBuffer();
			
		try {

			conn = DBManager.getConnection();

			if (readMap.get(ReadToolsIF.R_DATA) instanceof String[][]) {

				data = (String[][]) readMap.get(ReadToolsIF.R_DATA);//��map��ȡ����ά����
				if (data == null) {
					logger.error("null file ");
					return;
				}
				
				// step1  ����ʽ
				if (data[0].length < 7)
				{
					StringBuffer sb = new StringBuffer();
					sb.append("<html>");
					sb.append("<head>");
					sb.append("<link rel='stylesheet' href='css/style.css' type='text/css'>");
					sb.append("</head>");
					sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
				    sb.append("<tr>");
				    sb.append("<td width='21'>&nbsp;</td>");
				    sb.append("<td>");
					//sb.append("<b><font color='838383'>��ǰλ��</font></b><font color='838383'> : </font><font color='838383'>�ʻ�����</font><font color='838383'>"); 
					sb.append("-&gt; </font><font color='838383'>������</font><font color='838383'>"); 
				    sb.append("</td> </tr></table>");
				    sb.append("<br><br>");
				    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
				    sb.append("<tr>");
					sb.append("<td align='left'>��ע���ʽ���Ա��������ݸ�ʽ��</td>");
					sb.append("<tr>");
					sb.append("<td><form action='/member/member_import_taobao.jsp'><input value='����' type='submit'></form></td>");
					sb.append("</html>");
					response.getWriter().println(sb);
					
					java.io.File f3 = new java.io.File(importPath + fileName);
					
					boolean b=f3.delete();
					logger.info("delete file : " +b);
					return;
				}	
					
				
				
				/* step2 ���ݴ���
				 * �������ݵ�mbr_member
				 */
				String condition = "";
				MemberDAO memberDAO = new MemberDAO();
				Member member = new Member();
				 MemberBO memberBO = new MemberBO();//memberҵ��ʵ��
				 MemberAddresses memberAddr = new MemberAddresses();
				 int indexNickName = 1;
					int indexTaobaoId = 2;
					int indexName = 3;
					int indexAddress = 4;
					int indexTelephone = 5;
					int indexMobile = 6;
				for (int i = 1; i < data.length; i++) {
					
					try {
						//* �Ա�������,��Ա�������绰����ַ���ʱ�,email
						// �������	��һ�Ա��	���֧�����˺�	�ջ�������	�ջ���ַ ,��ϵ�绰 	��ϵ�ֻ�

						
						member.setTaobaoWangId(data[i][indexTaobaoId]);
						member.setNAME(data[i][indexName]);
						member.setTELEPHONE(data[i][indexMobile]);
						member.setFAMILY_PHONE(data[i][indexTelephone]);
						member.setAddress(data[i][indexAddress]);
						member.setAddressDetail(data[i][indexAddress]);
						member.setPostcode(" ");
						if(data[i][indexTaobaoId].indexOf("@")>0)
							member.setEMAIL(data[i][indexTaobaoId]);
						else
							member.setEMAIL(" ");
						member.setMSC_CODE("taobao");
						member.setBIRTHDAY("2000-01-01");
						member.setGENDER("0");
						member.setCOMMENTS(data[i][indexNickName]);
						
						memberAddr.setDelivery_address(data[i][indexAddress]);						
						memberAddr.setMember_ID(0);
						memberAddr.setTelephone(data[i][indexTelephone]);
						memberAddr.setTelephone2(data[i][indexMobile]);
						memberAddr.setRelation_person(data[i][indexName]);						
						memberAddr.setPostcode(" ");
						memberAddr.setSection("");
						
						
						 condition =  " and taobaowang_id='" + member.getTaobaoWangId() + "'";
						 		                    
		            if (memberDAO.checkMembers(conn, condition)) {
		            	logger.info("�ظ��Ա������ţ�"+ member.getTaobaoWangId());
		            	strError.append("�ظ��Ա������ţ�" + member.getTaobaoWangId());
		            	strError.append("<br/>");
		            	j++;
		            	continue;
		            }
		            //System.out.println("׼�����룬�Ա������ţ�"+ member.getTaobaoWangId());
					
		            
		            /*********/
		          //** �õ���Աseq **/
		            int memberId = memberDAO.getMemberSEQ(conn);

		            //** �õ���Ա��ַseq **/
		            int addressesId = memberDAO.getMemberAddressSEQ(conn);

		            String cardid = memberDAO.getMBCardIDSEQ(conn); 
		            
		            //** �����Ա�� **
		            member.setID(memberId);
		            member.setCARD_ID(cardid);
		            member.setADDRESS_ID(String.valueOf(addressesId));
		            member.setIS_ORGANIZATION("0");
		            
		            //System.out.println("����ǰ����ԱID��"+ member.getID());
		            memberDAO.insert(conn, member);
		            
		            //System.out.println("�ɹ����룬��ԱID��"+ member.getID());
					
		       		            
		            //** �����Ա��ַ���ʱൽ��Ա��ַ��MBR_ADDRESSES **/
		            memberAddr.setMember_ID(memberId);
		            memberAddr.setID(addressesId);
		            memberAddr.setRelation_person(member.getNAME());
		                     
		            
		            MemberAddressDAO memberAddressDAO = new MemberAddressDAO();
		            memberAddressDAO.insert(conn, memberAddr);
		            
		            //System.out.println("�ɹ����룬��Ա��ַ��"+ memberAddr.getID());
					strError.append("�ɹ����룬�Ա������ţ�"+ member.getTaobaoWangId());
					strError.append("<br/>");
		            /*********/

		            m++;		            
		     					
					} catch (Exception e) {
						list.add(data[i]);//�ռ����Ϸ�����
						strError.append("�Ա������ţ�"+ member.getTaobaoWangId()+"������ʧ��");
						strError.append("<br/>");
						System.out.println("�Ա�������:"+ member.getTaobaoWangId()+ "������"+e.toString() + e.getLocalizedMessage());
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
		//sb.append("<b><font color='838383'>��ǰλ��</font></b><font color='838383'> : </font><font color='838383'>�ʻ�����</font><font color='838383'>"); 
		sb.append("-&gt; </font><font color='838383'>������</font><font color='838383'>"); 
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
		sb.append("����ʧ��: " + j + "��<br>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>��ϸ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append(strError);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");		
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td><a href='member_import_taobao.jsp'>����</a>");
		sb.append("</td>");
		sb.append("</tr>");
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
	
	
	
}