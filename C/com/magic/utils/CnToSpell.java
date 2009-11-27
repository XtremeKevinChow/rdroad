package com.magic.utils;
import java.util.*;
import java.io.*;
/**
 * ����ת����ƴ��
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class CnToSpell
{
	private String sPropertyFile;

	private static Properties settings=null;
	static {
		settings = new Properties();
		try{
			FileInputStream in = new FileInputStream("dictionary.properties");
			settings.load(in);
		}
		catch(IOException ioe){
			System.err.println("ERROR: Open dictionary.properties error, please check the file.");
			System.exit(1);
		}

	}
       /**
        * ͨ���������Ƶõ�����ֵ
        * @param sKey
        * @return ����ֵ
        */
	public static final String getValue(String sKey)
	{
		if(null == sKey || "".equals(sKey.trim())){
		      return sKey;
        }
        StringBuffer retuBuf = new StringBuffer();
        for(int i=0;i<sKey.length();i++){
			String temp="";
			try{
				temp=new String(sKey.substring(i,i+1).getBytes("GBK"),"ISO-8859-1");
				String sValue = settings.getProperty(temp);
				if(sValue!=null){
				   retuBuf.append(sValue+" ");
			    }
			    else{
					retuBuf.append(sKey.substring(i,i+1)+" ");
				}
			}catch(java.io.UnsupportedEncodingException use){
				System.out.println(use);
			}catch(java.lang.NullPointerException e){
				System.out.println(e);
			}
		}
		return retuBuf.toString();
	}

	public static void main(String args[])
	{
		System.out.println(getValue("����......,,<,,,jj�𣿻���"));

	}
}