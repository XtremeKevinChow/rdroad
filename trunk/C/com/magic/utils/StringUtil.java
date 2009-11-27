package com.magic.utils;

import java.util.*;
import java.text.*;

/**
 * 字符串处理函数集合
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class StringUtil
{
	final static String[] SBC = {
		"，",
		"。",
		"；",
		"“",
		"”",
		"？",
		"！",
		"（",
		"）",
		"：",
		"——",
		"、"
	};

	final static String[] DBC = {
		",",
		".",
		";",
		"\"",
		"\"",
		"?",
		"!",
		"(",
		")",
		":",
		"_",
		","
	};

	/**
	 * 去除字符串两端空格
	 * 如果字符串是空的返加null
	 * @Param String
	 * @return String
	 */
   
	public static String trim(String str)
	{
		if (str == null)
			return null;

		str = str.trim();
		if (str.length() == 0)
			return null;
		return str;
	}
  
	/**
	 * 设置字符串首字母为大写
	 */
	public static String cap(String str)
	{
		if (str == null)
			return null;

		StringBuffer sb = new StringBuffer();
		sb.append(Character.toUpperCase(str.charAt(0)));
		sb.append(str.substring(1).toLowerCase());
		return sb.toString();
	}

	/**
	 * 判断字符串是否是包含a-z, A-Z, 0-9, _(下划线)
	 */
	public static boolean isWord(String str)
	{
		if (str == null)
			return false;

		char []ch = str.toCharArray();
		int i;
		for (i=0; i<str.length(); i++)
		{
			if ((!Character.isLetterOrDigit(ch[i])) && (ch[i] != '_'))
				return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否数字
	 */
	public static boolean isNum(String str)
	{
		if (str == null ||str.length() <= 0)
			return false;

		char [] ch = str.toCharArray();
    if(!(Character.isDigit(ch[0]) || ch[0]=='-')) return false;
		for(int i=1; i<str.length(); i++)
			if(!(Character.isDigit(ch[i]) ))
				return false;

		return true;
	}

	/**
	 * 判断字符串是否为实数
	 */
	public static boolean isNumEx(String str)
	{
		if (str == null ||str.length() <= 0  || str.length()>=10)
			return false;
//
//		char [] ch = str.toCharArray();
//   // if(!(Character.isDigit(ch[0]) || ch[0]=='-')) return false;
//		for (int i=0, comcount=0; i<str.length(); i++)
//		{
//			if (!Character.isDigit(ch[i]))
//			{
//        if(ch[i] != '.' && ch[i]!='-'){
//					return false;
//        }else if (i==0 || i==str.length()-1){
//					return false; // .12122 or 423423. is not a real number
//        }else if ( ++comcount > 1 ){
//					return false;  // 12.322.23 is not a real number
//        }
//			}
//		}
//		return true;
     try {
       double number = Double.parseDouble(str);
       return true;
     } catch (Exception ex) {
       return false;
     }
	}

	/**
	 * examples: String str = (String)StringUtil.getStringStr("jfas12",1);
	 */
	public static Object getStringStr (String str ,int index)
	{
		Vector reStr = new Vector();
		Object obj = getStringNumber(str,0);

		if (obj == null)
		{
			if (index > 1)
				return null;
			return str;
		}
		else
		{
			for(int i=0; i<((Vector)obj).size(); i++)
			{
				int indexOfString = str.indexOf( (String) (((Vector)obj).elementAt(i)) );
				if (indexOfString != 0)
					reStr.addElement(str.substring(0,indexOfString));
				str = str.substring(indexOfString + ((String)(((Vector)obj).elementAt(i))).length());
			}
			if (str.length() != 0)
				reStr.addElement(str);
		}

		if (index <= 0)
			return reStr;
		if (index > reStr.size())
			return null;
		return reStr.elementAt(index-1);
	}

	/**
	 * 得到字符串中的第index个数字字符串
	 * example: int i = Integer.parseInt((String)StringUtil.getStringNumber("asjfdkla3.asfa4",1));
	* return 1
	 */
	public static Object getStringNumber(String str, int index)
	{
		if (str == null)
			return null;

		char []ch = str.toCharArray();
		int i;
		String tempStr = "";
		Vector returnNumber = new Vector();
	
		for (i=0; i<str.length(); i++)
		{
			if (Character.isDigit(ch[i])) 
				tempStr += ch[i];
			else
			{
				if (!tempStr.equals(""))
					returnNumber.addElement(tempStr);
				tempStr = "";
			}
		}

		if ( ! tempStr.equals("") ) 
			returnNumber.addElement(tempStr);

		if (returnNumber.isEmpty() || (index > returnNumber.size()))
			return null;
		else
		{
			if (index <= 0) 
				return returnNumber;
			else 
				return returnNumber.elementAt(index-1);
		}
	}

	 /**
	 * 替换字符串，sOld sNew的大小必须相同
	 */
	public static String replaceStrEq(String sReplace,String sOld,String sNew)
	{
		if (sReplace==null || sOld==null || sNew==null)
			return null;

		int iLen = sReplace.length();
		int iLenOldStr = sOld.length();
		int iLenNewStr = sNew.length();

		if (iLen<=0 || iLenOldStr<=0 || iLenNewStr<=0)
			return sReplace;

		if (iLenOldStr != iLenNewStr)
			return sReplace;

		int[] iIndex = new int[iLen];
		iIndex[0] = sReplace.indexOf(sOld,0);
		if (iIndex[0]==-1)
			return sReplace;

		int iIndexNum=1;
		while (true)
		{
			iIndex[iIndexNum] = sReplace.indexOf(sOld,iIndex[iIndexNum-1]+1);
			if (iIndex[iIndexNum] == -1)
				break;
			iIndexNum++;
		}

		char[] caReplace = sReplace.toCharArray();
		char[] caNewStr = sNew.toCharArray();

		for(int i=0; i<iIndexNum; i++)
		{
			for(int j=0; j<iLenOldStr; j++)
			{
				caReplace[j+iIndex[i]] = caNewStr[j];
			}
		}
		return new String(caReplace);
	}

	 /**
	 * 替换字符串
	 */
	public static String replaceStrEx(String sReplace,String sOld,String sNew)
	{
		if (sReplace==null || sOld==null || sNew==null)
			return null;

		int iLen = sReplace.length();
		int iLenOldStr = sOld.length();
		int iLenNewStr = sNew.length();

		if(iLen<=0 || iLenOldStr<=0 || iLenNewStr<0)
			return sReplace;

		int[] iIndex = new int[iLen];
		iIndex[0] = sReplace.indexOf(sOld, 0);
		if (iIndex[0]== -1)
			return sReplace;

		int iIndexNum=1;
		while(true)
		{
			iIndex[iIndexNum] = sReplace.indexOf(sOld, iIndex[iIndexNum-1]+1);
			if (iIndex[iIndexNum] == -1)
				break;
			iIndexNum++;
		}

		Vector vStore = new Vector();
		String sub = sReplace.substring(0, iIndex[0]);
		if(sub != null)
			vStore.add(sub);

		int i=1;
		for(i=1; i<iIndexNum; i++)
		{
			vStore.add(sReplace.substring(iIndex[i-1]+iLenOldStr, iIndex[i]));
		}
		vStore.add(sReplace.substring(iIndex[i-1]+iLenOldStr, iLen));

		StringBuffer sbReplaced = new StringBuffer("");
		for(i=0; i<iIndexNum; i++)
		{
			sbReplaced.append(vStore.get(i)+sNew);
		}
		sbReplaced.append(vStore.get(i));

		return sbReplaced.toString();
	}

	 /**
	 * 分隔字符串
	 */
	public static String[] splitStr(String sStr,String sSplitter)
	{
		if (sStr==null || sStr.length()<=0 || sSplitter==null || sSplitter.length()<=0)
			return null;

		String[] saRet=null;

		int[] iIndex = new int[sStr.length()]; 
		iIndex[0] = sStr.indexOf(sSplitter, 0);
		if (iIndex[0]== -1)
		{
			saRet = new String[1];
			saRet[0]=sStr;
			return saRet;
		}

		int iIndexNum=1;
		while(true)
		{
			iIndex[iIndexNum] = sStr.indexOf(sSplitter,iIndex[iIndexNum-1]+1);
			if (iIndex[iIndexNum] == -1)
				break;
			iIndexNum++;
		}

		Vector vStore = new Vector();
		int iLen = sSplitter.length();
		int i =0;
		String sub = null;

		for(i=0; i<iIndexNum+1; i++)
		{
			if(i==0)
				sub = sStr.substring(0,iIndex[0]);
			else if (i==iIndexNum)
				sub = sStr.substring(iIndex[i-1]+iLen,sStr.length());
			else sub = sStr.substring(iIndex[i-1]+iLen,iIndex[i]);

			if(sub != null && sub.length()>0)
				vStore.add(sub);
		}

		if (vStore.size() <= 0)
			return null;
		saRet = new String[vStore.size()];
		Enumeration e = vStore.elements();

		for(i=0; e.hasMoreElements(); i++)
			saRet[i] = (String)e.nextElement();
		return saRet;
	}

	/**
	 * 以sContacter为分隔符连接字符串数组saStr
	 */
	public static String contactStr(String[] saStr,String sContacter)
	{
		if(saStr==null || saStr.length<=0 || sContacter==null || sContacter.length()<=0)
			return null;

		StringBuffer sRet = new StringBuffer("");
		for (int i=0; i<saStr.length; i++)
		{
			if (i==saStr.length-1)
				sRet.append(saStr[i]);
			else
				sRet.append(saStr[i]+sContacter);
		}
		return sRet.toString();
	}

	/**
	 * 转换整型数组为字符串
	 */
	public static String contactStr(int[] saStr,String sContacter)
	{
		if (saStr==null || saStr.length<=0 || sContacter==null || sContacter.length()<=0)
			return null;

		StringBuffer sRet = new StringBuffer("");
		for(int i=0; i<saStr.length; i++)
		{
			if (i==saStr.length-1)
				sRet.append(new Integer(saStr[i]));
			else
				sRet.append(new Integer(saStr[i]) + sContacter);
		}
		return sRet.toString();
	}

	 /**
	 * 排序字符串数组
	 */
	public static String[] sortByLength(String[] saSource, boolean bAsc)
	{
		if(saSource == null || saSource.length<=0)
			return null;

		int iLength = saSource.length;
		String[] saDest = new String[iLength];

		for(int i=0; i<iLength; i++)
			saDest[i] = saSource[i]; 

		String sTemp = "";
		int j=0,k=0;

		for (j = 0; j < iLength; j++)
			for (k = 0; k<iLength-j-1; k++)
			{
				if(saDest[k].length() > saDest[k+1].length() && bAsc)
				{
					sTemp = saDest[k];
					saDest[k] = saDest[k+1];
					saDest[k+1] = sTemp;
				}
				else if (saDest[k].length() < saDest[k+1].length() && !bAsc)
				{
					sTemp = saDest[k];
					saDest[k] = saDest[k+1];
					saDest[k+1] = sTemp;
				}
			}
		return saDest;
	}

	public static String compactStr(String str)
	{
		if (str == null)
			return null;

		if (str.length() <= 0)
			return "";

		String sDes = new String(str);
		int iBlanksAtStart = 0;
		int iLen = str.length();

		while (sDes.charAt(iBlanksAtStart) == ' ')
			if (++iBlanksAtStart >= iLen)
				break;

		String[] saDes = splitStr(sDes.trim()," ");
		if(saDes == null)
			return null;

		int i = 0;
		for(i = 0; i<saDes.length; i++)
		{
			saDes[i] = saDes[i].trim();
		}

		sDes = contactStr(saDes," ");
		StringBuffer sBlank = new StringBuffer("");
		for(i = 0; i<iBlanksAtStart; i++)
			sBlank.append(" ");

		return sBlank.toString() + sDes;
	}

	/**
	 *转换sbctodbc
	 */
	public static String symbolSBCToDBC(String sSource)
	{
		if (sSource==null || sSource.length()<=0)
			return sSource;

		int iLen = SBC.length < DBC.length ? SBC.length : DBC.length;
		for (int i = 0; i < iLen; i++)
			sSource = replaceStrEx(sSource,SBC[i],DBC[i]);
		return sSource;
	}

	/**
	 *转换dbctosbc
	 */
	public static String symbolDBCToSBC(String sSource)
	{
		if (sSource==null || sSource.length()<=0)
			return sSource;

		int iLen = SBC.length < DBC.length ? SBC.length : DBC.length;
		for (int i = 0; i < iLen; i++)
			sSource = replaceStrEx(sSource, DBC[i], SBC[i]);
		return sSource;
	}

	/**
	 * 判断是否email地址
	*/
	public static boolean isEmailUrl(String str)
	{
		if ((str == null) || (str.length() == 0))
			return false;

		if ((str.indexOf('@') > 0) && (str.indexOf('@') == str.lastIndexOf('@')))
		{
			if ((str.indexOf('.') > 0) && (str.lastIndexOf('.') > str.indexOf('@')))
			{
				return true;
			}
		}
		return false;
	}
  
  public static  boolean isDate(String s)
	{
		    if(s.length()==10)
        {
          System.out.println(s.charAt(4));
           System.out.println(s.charAt(7));
            System.out.println(s.substring(5,7));
          if(s.charAt(4)=='-' && s.charAt(7)=='-' && isNum(s.substring(0,3)) && isNum(s.substring(5,7)) && isNum(s.substring(8,10)))
            return true;
          else
            return false;
        }
         return false;

	}

	/**
	 * 判断是否email地址
	*/
	public static boolean isEmailAddress(String str)
	{
		if (str==null || str.length()<=0)
			return false;

		int iCommonCount=0;
		int iAltCount=0; 
		char[] chEmail = str.trim().toCharArray();

		for (int i = 0; i<chEmail.length; i++)
		{
			if (chEmail[i]==' ')
				return false;

			else if (chEmail[i]=='.')
			{
				if(i==0 || (i==chEmail.length - 1) )
					return false;
			}

			else if (chEmail[i]=='@')
			{
				if( ++iAltCount > 1 || i==0 || i==chEmail.length-1 )
					return false;
			}
		}

//		if (str.indexOf('.') < str.indexOf('@'))
//			return false;
		return true;
	}

	/**
	 * 格式化日期
	 * @param java.util.Date date
	 * @param String newFormat
	 * @return String
	 * example formatDate(date, "MMMM dd, yyyy") = July 20, 2000
	 */
	public static String formatDate(Date date, String newFormat)
	{
		if (date == null || newFormat == null)
			return null;

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(newFormat);
		return formatter.format(date);
	}

	/**
	 * 去除字符串中的特殊字符和两端空格
	 * 如果字符串是空的返回null
	 * @Param String
	 * @return String
	 */
	public static String cleanShow(String s)
	{
		String t;
		if(s==null) return "";
		else
			t=s;
    t=replaceStrEx(t,"&","&amp;");
    t=replaceStrEx(t," ","&nbsp;");
    t=replaceStrEx(t,"<","&lt;");
    t=replaceStrEx(t,">","&gt;");
    t=replaceStrEx(t,"\n","<br>");
 
    
		t= t.trim();
		return t;
	}
  
  /**
	 * 去除字符串中的特殊字符和两端空格
	 * 如果字符串是空的返回null
	 * @Param String
	 * @return String
	 */
	public static String cleanValue(String s)
	{
		String t;
		if(s==null) return "";
		else
			t=s;
		t=replaceStrEx(t,"\"","“");
		t= t.trim();
		return t;
	}
/**
*将Null的String转换为空字符串
*/
	public static String cEmpty(String s)
	{
		
    if (s==null) return "";
    String t=s;
   
		return t.trim();
	}
/**
*将空字符串转换为Null
*/
	public static String cNull(String s)
	{
		if (s==null) return "";
		if (s.trim().length()==0) return "";
    String t=s;
	

    return t;
	}
/**
*如果s为空或Null, 则返回"NUll", 否则给s两边加上单引号返回。用在写数据库的时候。
*/
	public static String nullString(String s)
	{
		if (s==null) return "Null";
		if (s.trim().length() == 0) return "Null";
		return "'" + s.trim() + "'";
	}

	public static String filterString(String s,String t)
	{
		String a=s;
		int i,j;
		j=t.length();
		while((i=a.indexOf(t))!=-1)
		{
			a=a.substring(0,i-1)+a.substring(i+j);
		}
		return a;
	}
  
	/**
	 *将字符串按指定字符分开，然后首字母大写组成新的字符串
   * ABC_abc   ----->AbcAbc
	 */
	public static String swapCase(String str,String splitStr){
		if (str == null){
			return null;
    }
		StringBuffer sb = new StringBuffer();
    for(int i=0;i<str.split(splitStr).length;i++){
		  sb.append(Character.toUpperCase(str.split(splitStr)[i].charAt(0)));
		  sb.append(str.split(splitStr)[i].substring(1).toLowerCase());
    }
		return sb.toString();
	}  
	
	public static void main(String[] args) {
	    System.out.println(isEmailAddress("first.lady@whitehouse.gov"));
	}
	
	/**
	 * 合成如同 1,2,3,4格式的字符串
	 * @param IDs
	 * @return
	 */
	public static String composePKs(String[] IDs) {
	    String rtn = "";
	    if (IDs == null || IDs.length == 0)
	        return "";
	    
	    for (int i = 0; i < IDs.length; i ++) {
	        
	        rtn += IDs[i] + ",";
	    }
	    return rtn.substring(0, rtn.length() - 1);
	}
}
