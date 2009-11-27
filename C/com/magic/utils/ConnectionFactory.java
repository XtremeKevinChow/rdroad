package com.magic.utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.sql.DataSource;

/**
 * ���ӳ��೧�����ೣ��������������Դ���ƺ����ݿ����ӳض�Ӧ�Ĺ�ϣ
 * @author Wave Li
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class ConnectionFactory {
 private static ConnectionFactory m_instance = null;
 //��ʹ�õ����ӳ�
 private LinkedHashSet ConnectionPool = null;
 //�������ӳ�
 private LinkedHashSet FreeConnectionPool = null;
 //���������
 private int MaxConnectionCount = 5;
 //��С������
 private int MinConnectionCount = 1;
 //��ǰ������
 private int current_conn_count = 0;
 //���Ӳ���
 private ConnectionParam connparam = null;
 //�Ƿ񴴽������ı�־
 private boolean isflag = false;
 //�Ƿ�֧������
 private boolean supportTransaction = false;
 //����������
 private int ManageType = 0;
 private ConnectionFactory() {
  ConnectionPool = new LinkedHashSet();
  FreeConnectionPool = new LinkedHashSet();
 }
 /**
  * ʹ��ָ���Ĳ�������һ�����ӳ�
  */
 public ConnectionFactory(ConnectionParam param, FactoryParam fparam)
  throws SQLException 
 {
  //���������Ϊ��
  if ((param == null)||(fparam == null))
   throw new SQLException("ConnectionParam��FactoryParam����Ϊ��");
  if (m_instance == null)
  {
   synchronized(ConnectionFactory.class){
    if (m_instance == null)
    {
     //new instance
     //��������
     m_instance = new ConnectionFactory();
     m_instance.connparam = param;
     m_instance.MaxConnectionCount = fparam.getMaxConn();
     m_instance.MinConnectionCount = fparam.getMinConn();
     m_instance.ManageType = fparam.getType();
     m_instance.isflag = true;
     //��ʼ��������MinConnectionCount������
     System.out.println("connection factory ������");
     try{
      for (int i=0; i < m_instance.MinConnectionCount; i++)
      {
       ConnectionImpl _conn = ConnectionImpl.getConnection(m_instance, m_instance.connparam);
       if (_conn == null) 
       {
            continue;
       }
       System.out.println("connection����");
       m_instance.FreeConnectionPool.add(_conn);//����������ӳ�
       m_instance.current_conn_count ++;
       //��־�Ƿ�֧������
       m_instance.supportTransaction = _conn.isSupportTransaction();    
      }
     }
     catch(Exception e)
     {
      e.printStackTrace();
     }
     //���ݲ����ж��Ƿ���Ҫ��ѯ
     if (m_instance.ManageType != 0)
     {
      Thread t = new Thread(new FactoryMangeThread(m_instance));
      t.start();
     } 
    }
   }
  }
 }
/**
  * ��־�����Ƿ��Ѿ�����
  * @return boolean
  */ 
 public boolean isCreate()
 {
  return m_instance.isflag;
 }
 
 /**
  * �����ӳ���ȡһ�����е�����
  * @return Connection
  * @throws SQLException
  */
 public synchronized Connection getFreeConnection() 
  throws SQLException
 {
  Connection conn = getSubFreeConnection();

   if(conn == null){//���������˵�������ӿ���
      long startTime = (new java.util.Date()).getTime();
        for(conn = null; (conn = getSubFreeConnection()) == null;)
        {
            try
            {
                wait(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            if((new java.util.Date()).getTime() - startTime >= 30000)
            {
                System.out.println("û�п��õ����ݿ�����,���ݿ����ӳ�ʱ");
                //printStatus();
                return null;
            }
        }   
   }
    //throw new SQLException("û�п��õ����ݿ�����");
  return conn;
 }
 
 private Connection getSubFreeConnection() throws SQLException
 {
  Connection conn = null;
  
  //��ȡ��������
  Iterator iter = m_instance.FreeConnectionPool.iterator();
  while(iter.hasNext()){
   ConnectionImpl _conn = (ConnectionImpl)iter.next();
   //�ҵ�δ������
   if(!_conn.isFree()){
    conn = _conn.getFreeConnection();
    _conn.setIsFree(true);
    //�Ƴ�������
    m_instance.FreeConnectionPool.remove(_conn);
    //�������ӳ� 
    m_instance.ConnectionPool.add(_conn);   
     break;
   }
  }
  //�����г��Ƿ�Ϊ��
  if (m_instance.FreeConnectionPool.isEmpty())
  {
   //�ټ���Ƿ��ܹ�����
   if (m_instance.current_conn_count < m_instance.MaxConnectionCount)
   {
   //�½����ӵ��������ӳ�
    int newcount = 0 ;
    //ȡ��Ҫ��������Ŀ
    if (m_instance.MaxConnectionCount - m_instance.current_conn_count >=m_instance.MinConnectionCount)
    {
     newcount = m_instance.MinConnectionCount;
    }
    else
    {
     newcount = m_instance.MaxConnectionCount - m_instance.current_conn_count;
    }
    //��������
    for (int i=0;i <newcount; i++)
    {
     ConnectionImpl _conn = ConnectionImpl.getConnection(m_instance, m_instance.connparam);
     
     _conn.setIsFree(false);
    
     m_instance.FreeConnectionPool.add(_conn);
     m_instance.current_conn_count ++;
    }
   } else {//��������½�������Ƿ����Ѿ��黹������
    iter = m_instance.ConnectionPool.iterator();
    while(iter.hasNext()){
     ConnectionImpl _conn = (ConnectionImpl)iter.next();
     if(!_conn.isFree()){
      //conn = _conn.getFreeConnection();
      _conn.setIsFree(false);
      m_instance.ConnectionPool.remove(_conn); 
      m_instance.FreeConnectionPool.add(_conn);   
      break;
     }
    }    
   }
  }
  
    if(conn!=null) return conn;
    
  //if (FreeConnectionPool.isEmpty())
 //�ٴμ���Ƿ��ܷ�������
  if(conn == null){
   iter = m_instance.FreeConnectionPool.iterator();
   while(iter.hasNext()){
    ConnectionImpl _conn = (ConnectionImpl)iter.next();
    if(!_conn.isFree()){
     conn = _conn.getFreeConnection();
     _conn.setIsFree(true);
     m_instance.FreeConnectionPool.remove(_conn); 
     m_instance.ConnectionPool.add(_conn);   
     break;
    }
   }
  }
  
  return conn;
 }

 /**
  * �رո����ӳ��е��������ݿ�����
  * @throws SQLException
  */
 public synchronized void close() throws SQLException
 {
  this.isflag = false;
  SQLException excp = null;
  //�رտ��г�
  Iterator iter = m_instance.FreeConnectionPool.iterator();
  while(iter.hasNext()){
   try{
    ((ConnectionImpl)iter.next()).close();
    System.out.println("close connection:free");
    m_instance.current_conn_count --;
   }catch(Exception e){
    if(e instanceof SQLException)
     excp = (SQLException)e;
   }
  }
  //�ر���ʹ�õ����ӳ�
  iter = m_instance.ConnectionPool.iterator();
  while(iter.hasNext()){
   try{
    ((ConnectionImpl)iter.next()).close();
    System.out.println("close connection:inused");
    m_instance.current_conn_count --;
   }catch(Exception e){
    if(e instanceof SQLException)
     excp = (SQLException)e;
   }
  }  
  if(excp != null)
   throw excp;
 } 
 
 /**
  * �����Ƿ�֧������
  * @return boolean
  */
 public boolean isSupportTransaction() {
  return m_instance.supportTransaction;
 }  
 /**
  * ���ӳص��ȹ���
  *
  */
 public void schedule(){
  Connection conn = null;
  //�ټ���Ƿ��ܹ�����
  Iterator iter = null;
  //����Ƿ����Ѿ��黹������
  {
   iter = m_instance.ConnectionPool.iterator();
   while(iter.hasNext()){
    ConnectionImpl _conn = (ConnectionImpl)iter.next();
    if(!_conn.isFree()){
     conn = _conn.getFreeConnection();
     _conn.setIsFree(false);
     m_instance.ConnectionPool.remove(_conn); 
     m_instance.FreeConnectionPool.add(_conn);   
     break;
    }
   }    
  }
  if (m_instance.current_conn_count < m_instance.MaxConnectionCount)
  {
   //�½����ӵ��������ӳ�
   int newcount = 0 ;
   //ȡ��Ҫ��������Ŀ
   if (m_instance.MaxConnectionCount - m_instance.current_conn_count >=m_instance.MinConnectionCount)
   {
    newcount = m_instance.MinConnectionCount;
   }
   else
   {
    newcount = m_instance.MaxConnectionCount - m_instance.current_conn_count;
   }
   //��������
   for (int i=0;i <newcount; i++)
   {
    ConnectionImpl _conn = ConnectionImpl.getConnection(m_instance, m_instance.connparam);
    m_instance.FreeConnectionPool.add(_conn);
    m_instance.current_conn_count ++;
   }
  }
 } 
  public void printStatus()
 {

  Iterator iter = m_instance.ConnectionPool.iterator();
  int count=0;
  while(iter.hasNext()){
    ConnectionImpl _conn = (ConnectionImpl)iter.next();
    count++;;
    System.out.println("ConnectionPool["+count+"]"+_conn.isFree());
  } 
  iter = m_instance.FreeConnectionPool.iterator();
  count=0;
  while(iter.hasNext()){
    ConnectionImpl _conn = (ConnectionImpl)iter.next();
    count++;;
   // System.out.println("FreeConnectionPool["+count+"]"+_conn.isFree());
  }  
 }
}