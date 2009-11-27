package com.magic.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import com.magic.crm.util.Config;
/**
 * ���ݿ����ӳ���
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class DBConnectionPool
{

    private static Vector ConnectionPool = new Vector();      //���ӳ�����
    private int connNumb;                                      //���ӳ�����
    private static int maxConn = 30;                          //���������
    private static String dbDrv = "com.microsoft.jdbc.sqlserver.SQLServerDriver";  //���ݿ���������
    private static String dbURL = Config.getValue("crm_sql_server_url");           //����URL
    private static String dbPsd = Config.getValue("crm_sql_server_user_password"); //��¼�û�����
    private static String dbLogin = Config.getValue("crm_sql_server_user");        //��¼�û���
    private static DBConnectionPool instance;                                      //���ӳ�ʵ��

    public DBConnectionPool(){
    }
  /**
   * �õ����ݿ�����URL
   * @return ����URL�ַ���
   */
    public String getDbURL()
    {
        return dbURL;
    }
  
  /**
   * ���ݿ�����URL��ֵ
   * @param mUrl  ����URL
   */
  
    public void setDbURL(String mUrl){
        dbURL = mUrl;
    }
  /**
   * �õ���ǰ���ݿ���������
   * @return ���ݿ����������ַ���
   */
    public String getDbDrv(){
        return dbDrv;
    }
  /**
   * ���ݿ�����������ֵ
   * @param mDrv ���������ַ���
   */
    public void setDbDrv(String mDrv)
    {
        dbDrv = mDrv;
    }
  /**
   * �õ����ݿ���������
   * @return ����
   */
    public String getDbPsd()
    {
        return dbPsd;
    }
  /**
   * ���븳ֵ
   * @param mPsd ����
   */
    public void setDbPsd(String mPsd)
    {
        dbPsd = mPsd;
    }
  /**
   * ��õ�¼�û���
   * @return �û����ַ���
   */
    public String getDbLogin()
    {
        return dbLogin;
    }
  /**
   * ��ֵ��¼�û���
   * @param mLogin ��¼�û���
   */
    public void setDbLogin(String mLogin)
    {
        dbLogin = mLogin;
    }

    public String getMaxConn()
    {
        return Integer.toString(maxConn);
    }

    public void setMaxConn(String mMax)
    {
        if(Integer.parseInt(mMax) != 0)
            maxConn = Integer.parseInt(mMax);
    }
  /**
   * �õ����ݿ����ӳ�ʵ��
   * @return ���ݿ����ӳ�ʵ��
   */
    public static synchronized DBConnectionPool getInstance()
    {
        if(instance == null)
            instance = new DBConnectionPool();
        return instance;
    }
  /**
   * �ͷ�����
   * @param conn ���ݿ�����
   */
    public synchronized void freeConnection(Connection conn)
    {
        ConnectionPool.addElement(conn);
        connNumb--;
        System.out.println("���ʹ����������:"+connNumb);
    }
  /**
   * �ж��������û�йرգ��ȹر����ӣ�Ȼ������ӳ����ͷ�
   * @param conn      ���ݿ�����
   * @param isClose   �Ƿ�ر�
   */
    public synchronized void freeConnection(Connection conn, boolean isClose)
    {
        try
        {
            if(!conn.isClosed() && isClose)
            {
                conn.close();
                connNumb--;
            } else
            {
                freeConnection(conn);
            }
        }
        catch(SQLException e){
          e.printStackTrace();
        }
    }
  /**
   * �������
   * @return ���ݿ�����
   * @throws java.sql.SQLException
   */
    public synchronized Connection getConnection()
        throws SQLException
    {
         Connection conn = getSubConnection();

   if(conn == null){//���������˵�������ӿ���
      long startTime = (new java.util.Date()).getTime();
        for(conn = null; (conn = getSubConnection()) == null;)
        {
            try
            {
                wait(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            if((new java.util.Date()).getTime() - startTime >= 3000)
            {
                System.out.println("û�п��õ����ݿ�����,���ݿ����ӳ�ʱ30��");
                //printStatus();
                return null;
            }
        }   
   }
    //throw new SQLException("û�п��õ����ݿ�����");
  return conn;
       
    }
  /**
   * �����ݿ����ӳ��л��һ������
   * @return ���ݿ�����
   * @throws java.sql.SQLException
   */
    public synchronized Connection getSubConnection() throws SQLException{
        Connection conn = null;
        if(ConnectionPool.size() > 0)
        {
            conn = (Connection)ConnectionPool.firstElement();
            ConnectionPool.removeElementAt(0);
            if(conn==null || conn.isClosed()){
              conn = getConnection();
            }
        } else {
          if(maxConn == 0 || connNumb < 30){
            conn = newConnection();
          }else
          {
              
          }
        }
        if(conn != null){
            connNumb++;
        }
        return conn;
 }
  /**
   * �ȴ�ָ��ʱ�������ݿ��������ӳ��л��һ������
   * @param timeOut   �ȴ�ʱ��
   * @return ���ݿ�����
   * @throws java.sql.SQLException
   */
    public synchronized Connection getConnection(long timeOut)
        throws SQLException
    {
        long startTime = (new java.util.Date()).getTime();
        Connection conn;
        for(conn = null; (conn = getConnection()) == null;)
        {
            try
            {
                wait(timeOut);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            if((new java.util.Date()).getTime() - startTime >= timeOut)
            {
                return null;
            }
        }

        return conn;
    }
  /**
   * ����һ���µ����ݿ�����
   * @return ���ݿ�����
   */
    private Connection newConnection()
    {
        Connection conn = null;
        try
        {
            Class.forName(dbDrv);
            conn = DriverManager.getConnection(dbURL, dbLogin, dbPsd);
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
        return conn;
    }
    
    /**
     * �����ݿ����ӳ��е����������ͷ�
     */
    public synchronized void release()
    {
        for(Enumeration allConnections = ConnectionPool.elements(); allConnections.hasMoreElements();)
        {
            Connection conn = (Connection)allConnections.nextElement();
            try
            {
                if(!conn.isClosed())
                    conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        ConnectionPool.removeAllElements();
    }

    public void destroy()
    {
        release();
    }
}