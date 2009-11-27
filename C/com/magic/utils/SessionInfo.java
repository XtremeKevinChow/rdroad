package com.magic.utils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.*;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
/**
 * �����¼ Session��Ϣ
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class SessionInfo 
{
    private int companyID;
    private int operatorID;
		private int operatorPower;
		private HashMap powerMap;
    private HashMap requestMap; 

    public SessionInfo(int company_id,int operator_id)
    {
        this.companyID=company_id;
        this.operatorID=operator_id;
    }

		public SessionInfo(int company_id,int operator_id,HashMap map,Map request_map){
        this.companyID=company_id;
        this.operatorID=operator_id;
		this.powerMap = new HashMap();
        powerMap.putAll(map);
        this.requestMap=new HashMap();
        requestMap.putAll(request_map);
		}

    public int getCompanyID()
    {
        return companyID;
    }



    public int getOperatorID()
    {
       
        return operatorID;
    }

		public int getOperatorPower(int doc_type){
			
			if(powerMap!=null && powerMap.containsKey(new Integer(doc_type))){
				return ((Integer)powerMap.get(new Integer(doc_type))).intValue();
			} else {
				return 0;
			}
		}

		public HashMap getPowerMap(){
			return powerMap;
		}


  public void setRequest(Map request_map)
  {
    //this.requestMap = request_map;
  }
  
  public String getParameter(String parameter_name)
  {
    String[] values=(String[])requestMap.get(parameter_name);
    if(values==null) 
        return null;
    else
        return values[0];
  }
  public Vector getParameterValues(String Parameter_name)
  {
   /* java.util.Iterator it= requestMap.values().iterator(); 
    while(it.hasNext ()) {
      java.util.Map.Entry entry = (java.util.Map.Entry) it.next ();
      System.out.println(entry.getKey);
      System.out.println(entry.getValue()); %
    }*/
    Vector vector=new Vector();
    String values[]=(String[])requestMap.get(Parameter_name);
    if(values==null) return null;
    if(values!=null)
    {
      System.out.println("parameter name:"+Parameter_name);
      for(int i=0;i<values.length;i++)
      { 
        System.out.println("value "+i+":"+values[i]);
        vector.add(i,values[i]);
      }
    }
    System.out.println(vector.size());
    return vector;
  }
  public HashMap getRequestMap()
  {
    return requestMap;
  }
  
  /**
	 * ��ȡ�ַ���������
   * 
	 */
  public String getStrParameter(String parameter_name)
  {
    if(getParameter(parameter_name)==null)
      return "";
    else
      return getParameter(parameter_name);
  }
  
  /**
	 * ��ȡ��������
   * 
	 */
  public int getIntParameter(String parameter_name) throws KException
  {
    String v=getParameter(parameter_name);
    if(v==null)
    {
        KException e=new KException("û������ֵ");
        System.out.println("û������ֵ:"+parameter_name);
        throw e;
    }
    if(!StringUtil.isNum(v))
    {
       KException e=new KException("������������Ͳ�����������.");
       throw e;
    }
    return Integer.parseInt(v);
  }
  
   /**
	 * ��ȡ����������
   * 
	 */
  public float getFloatParameter(String parameter_name) throws KException
  {
    String v=getParameter(parameter_name);
    if(v==null)
    {
        KException e=new KException("û������ֵ");
        System.out.println("û������ֵ:"+parameter_name);
        throw e;
    }
    if(!StringUtil.isNumEx(v))
    {
       KException e=new KException("������������Ͳ�����������.");
       throw e;
    }
    return Float.parseFloat(v);
  }
  
  /**
	 * ��ȡ�ʼ���ַ����
   * 
	 */
    public String getEmailParameter(String parameter_name) throws KException
  {
    String v=getParameter(parameter_name);
    if(v==null)
    {
       return "";
    }
    if(!StringUtil.isEmailAddress(v))
    {
       KException e=new KException("������������Ͳ����ʼ���ַ����.");
       throw e;
    }
    return v;
  }
  
  /**
	 * ��ȡ��������
   * 
	 */
    public String getDateParameter(String parameter_name) throws KException
  {
    String v=getParameter(parameter_name);
  
    return v;
  }
  
  /**
	 * ��ȡʱ������
   * 
	 */
    public String getTimeParameter(String parameter_name) throws KException
  {
    String v=getParameter(parameter_name);
    
    return v;
  }
  
  /**
	 * ��ȡ����ʱ������
   * 
	 */
    public String getDateTimeParameter(String parameter_name) throws KException
  {
    String v=getParameter(parameter_name);
 
    return v;
  }
}