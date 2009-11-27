package com.magic.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * �������ݿ����ӵĴ�����
 * @author Wave Li
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class ConnectionImpl implements InvocationHandler {
 //��������
 private Connection conn = null;
 //���������Ӵ��������
 private Statement statRef = null;
 private PreparedStatement prestatRef = null;
 //�Ƿ�֧�������־
 private boolean supportTransaction = false;
 //���ݿ��æ״̬
 private boolean isFree = false;
 //���һ�η���ʱ��
 long lastAccessTime = 0;
 //����Ҫ�ӹܵĺ���������
 String CREATESTATE = "createStatement";
 String CLOSE = "close";
 String PREPARESTATEMENT = "prepareStatement";
 String COMMIT = "commit";
 String ROLLBACK = "rollback";
/**
  * ���캯��������˽�У���ֹ��ֱ�Ӵ���
  * @param param ���Ӳ���
  */
 private ConnectionImpl(ConnectionParam param) {
  //��¼����
  
  try{
   //��������
   Class.forName(param.getDriver()).newInstance();
   System.out.println(param.getUrl());
   
   conn = DriverManager.getConnection(param.getUrl(),param.getUser(), param.getPassword());   
   if(conn==null)
   {
       System.out.println("û�гɹ�����Connection");
   }
   DatabaseMetaData dm = null;
   dm = conn.getMetaData();
   //�ж��Ƿ�֧������
   supportTransaction = dm.supportsTransactions();
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
 }
public Object invoke(Object proxy, Method method, Object[] args)
  throws Throwable {
   Object obj = null;
   //�ж��Ƿ������close�ķ������������close�������������Ϊ����״̬
   if(CLOSE.equals(method.getName()))
   {
    //���ò�ʹ�ñ�־
    setIsFree(false);
    //System.out.println("set false");
    //����Ƿ��к������������������������Դ
    if (statRef != null)
     statRef.close();
    if (prestatRef != null)
     prestatRef.close();
    return null;
   }
   //�ж���ʹ����createStatement���
   if (CREATESTATE.equals(method.getName()))
   {
    obj = method.invoke(conn, args);
    statRef = (Statement)obj;//��¼���
    return obj;
   }
   //�ж���ʹ����prepareStatement���
   if (PREPARESTATEMENT.equals(method.getName()))
   {
    obj = method.invoke(conn, args);
    prestatRef = (PreparedStatement)obj;
    return obj;
   }
   //�����֧�����񣬾Ͳ�ִ�и�����Ĵ���
   if ((COMMIT.equals(method.getName())||ROLLBACK.equals(method.getName())) && (!isSupportTransaction()))
    return null;   
   obj = method.invoke(conn, args); 
   //�������һ�η���ʱ�䣬�Ա㼰ʱ�����ʱ������
   lastAccessTime = System.currentTimeMillis();
   return obj;
 }
/**
  * �������ӵĹ�����ֻ���ù�������
  * @param factory Ҫ���ù���������һ������ȷ��ʼ��
  * @param param ���Ӳ���
  * @return ����
  */
 static public ConnectionImpl getConnection(ConnectionFactory factory, ConnectionParam param)
 {
  if (factory.isCreate())//�ж��Ƿ���ȷ��ʼ���Ĺ���
  {
   ConnectionImpl _conn = new ConnectionImpl(param);
   return _conn;
  }
  else
   return null;
 }
 
 public Connection getFreeConnection() {
  //�������ݿ�����conn�Ľӹ��࣬�Ա��סclose����
  Connection conn2 = (Connection)Proxy.newProxyInstance(
   conn.getClass().getClassLoader(),
   conn.getClass().getInterfaces(),this);
  return conn2;
 }
/**
  * �÷��������Ĺر������ݿ������
  * @throws SQLException
  */
 void close() throws SQLException{
  //����������conn��û�б��ӹܵ����ӣ����һ������close�������ֱ�ӹر�����
  //setIsFree(false);
  conn.close();
 }
   
 public void setIsFree(boolean value)
 {
  isFree = value;
 }
 
 public boolean isFree() {
  return isFree;
 } 
 /**
  * �ж��Ƿ�֧������
  * @return boolean
  */
 public boolean isSupportTransaction() {
  return supportTransaction;
 }  
}