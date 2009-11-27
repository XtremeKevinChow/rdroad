package com.magic.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import com.magic.crm.util.Config;
/**
 * 数据库连接池类
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class DBConnectionPool
{

    private static Vector ConnectionPool = new Vector();      //连接池向量
    private int connNumb;                                      //连接池数量
    private static int maxConn = 30;                          //最大连接数
    private static String dbDrv = "com.microsoft.jdbc.sqlserver.SQLServerDriver";  //数据库连接驱动
    private static String dbURL = Config.getValue("crm_sql_server_url");           //连接URL
    private static String dbPsd = Config.getValue("crm_sql_server_user_password"); //登录用户密码
    private static String dbLogin = Config.getValue("crm_sql_server_user");        //登录用户名
    private static DBConnectionPool instance;                                      //连接池实例

    public DBConnectionPool(){
    }
  /**
   * 得到数据库连接URL
   * @return 连接URL字符串
   */
    public String getDbURL()
    {
        return dbURL;
    }
  
  /**
   * 数据库连接URL赋值
   * @param mUrl  输入URL
   */
  
    public void setDbURL(String mUrl){
        dbURL = mUrl;
    }
  /**
   * 得到当前数据库连接驱动
   * @return 数据库连接驱动字符串
   */
    public String getDbDrv(){
        return dbDrv;
    }
  /**
   * 数据库连接驱动赋值
   * @param mDrv 输入驱动字符串
   */
    public void setDbDrv(String mDrv)
    {
        dbDrv = mDrv;
    }
  /**
   * 得到数据库连接密码
   * @return 密码
   */
    public String getDbPsd()
    {
        return dbPsd;
    }
  /**
   * 密码赋值
   * @param mPsd 密码
   */
    public void setDbPsd(String mPsd)
    {
        dbPsd = mPsd;
    }
  /**
   * 获得登录用户名
   * @return 用户名字符串
   */
    public String getDbLogin()
    {
        return dbLogin;
    }
  /**
   * 赋值登录用户名
   * @param mLogin 登录用户名
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
   * 得到数据库连接池实例
   * @return 数据库连接池实例
   */
    public static synchronized DBConnectionPool getInstance()
    {
        if(instance == null)
            instance = new DBConnectionPool();
        return instance;
    }
  /**
   * 释放连接
   * @param conn 数据库连接
   */
    public synchronized void freeConnection(Connection conn)
    {
        ConnectionPool.addElement(conn);
        connNumb--;
        System.out.println("活动在使用连接数量:"+connNumb);
    }
  /**
   * 判断如果连接没有关闭，先关闭连接，然后从连接池中释放
   * @param conn      数据库连接
   * @param isClose   是否关闭
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
   * 获得连接
   * @return 数据库连接
   * @throws java.sql.SQLException
   */
    public synchronized Connection getConnection()
        throws SQLException
    {
         Connection conn = getSubConnection();

   if(conn == null){//如果不能则说明无连接可用
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
                System.out.println("没有可用的数据库连接,数据库连接超时30秒");
                //printStatus();
                return null;
            }
        }   
   }
    //throw new SQLException("没有可用的数据库连接");
  return conn;
       
    }
  /**
   * 从数据库连接池中获得一个连接
   * @return 数据库连接
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
   * 等待指定时间后从数据库连接连接池中获得一个连接
   * @param timeOut   等待时间
   * @return 数据库连接
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
   * 创建一个新的数据库连接
   * @return 数据库连接
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
     * 将数据库连接池中的所有连接释放
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