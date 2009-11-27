package com.magic.utils;

/**
 * 文件上传异常处理
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class FileUploadException extends Exception
{
	public FileUploadException(String desc)
	{
		super(desc);
	}
}
