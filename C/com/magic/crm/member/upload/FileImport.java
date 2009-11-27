/*
 * Created on 2005-11-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.upload;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.*;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileImport {
	
	private static Logger logger = Logger.getLogger(FileImport.class);	
	
	public FileImport() {
		
	}
	/**
	 * �˷�����servlet��dopost����
	 * @param request
	 * @param file
	 */
	public static void fileUpload(HttpServletRequest request, String file) {
		 String fileName = null;
		 DiskFileUpload fu = new DiskFileUpload();
	    try {
	        
	        // ��������ļ��ߴ磬������4MB
	        fu.setSizeMax(4194304);
	        // ���û�������С��������4kb
	        fu.setSizeThreshold(4096);
	        // �õ����е��ļ���
	        List fileItems = fu.parseRequest(request);
	        Iterator i = fileItems.iterator();
	        // ���δ���ÿһ���ļ���

	        while(i.hasNext()) {
	            FileItem fi = (FileItem)i.next();
	            // ����ļ���������ļ�������·����
	            fileName= fi.getName();
	            if(fileName!=null) {
	                // д���ļ�����Ҳ���Դ�fileName����ȡ�ļ�����
	            	fi.write(new File(file));      
	                logger.info("�ļ��ϴ��ɹ�!" + file);
	            }
	        }
	    }catch(Exception e) {
	       logger.error("�ļ��ϴ�ʧ��!" + file);
	       e.printStackTrace();
	    }	
	
	}
	
	/**
	 * 
	 * @param fullPath
	 * @param file
	 * @param request
	 * @throws Exception
	 */
	public static void fileUpload2(String fullPath, FormFile file, HttpServletRequest request) throws Exception {
		if ( fullPath == null || file == null) {
			throw new java.lang.IllegalArgumentException("�����ļ����ܿջ򲻲�����!");
		}
		InputStream stream = null;
		OutputStream bos = null;
		try {
		
			stream = file.getInputStream();//���ļ�����
			
			bos = new FileOutputStream(fullPath);//����һ���ϴ��ļ��������
			
			
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ( (bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);//���ļ�д�������
			}
			
		}catch(Exception e){
			System.err.print(e);
			throw e;
		} finally {
			bos.close();
			stream.close();
		}

	}
	
	
	
}
