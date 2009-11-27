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
	 * 此方法在servlet中dopost调用
	 * @param request
	 * @param file
	 */
	public static void fileUpload(HttpServletRequest request, String file) {
		 String fileName = null;
		 DiskFileUpload fu = new DiskFileUpload();
	    try {
	        
	        // 设置最大文件尺寸，这里是4MB
	        fu.setSizeMax(4194304);
	        // 设置缓冲区大小，这里是4kb
	        fu.setSizeThreshold(4096);
	        // 得到所有的文件：
	        List fileItems = fu.parseRequest(request);
	        Iterator i = fileItems.iterator();
	        // 依次处理每一个文件：

	        while(i.hasNext()) {
	            FileItem fi = (FileItem)i.next();
	            // 获得文件名，这个文件名包括路径：
	            fileName= fi.getName();
	            if(fileName!=null) {
	                // 写入文件，你也可以从fileName中提取文件名：
	            	fi.write(new File(file));      
	                logger.info("文件上传成功!" + file);
	            }
	        }
	    }catch(Exception e) {
	       logger.error("文件上传失败!" + file);
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
			throw new java.lang.IllegalArgumentException("导入文件不能空或不才能在!");
		}
		InputStream stream = null;
		OutputStream bos = null;
		try {
		
			stream = file.getInputStream();//把文件读入
			
			bos = new FileOutputStream(fullPath);//建立一个上传文件的输出流
			
			
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ( (bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);//将文件写入服务器
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
