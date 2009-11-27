package com.magic.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;

import com.magic.crm.util.Config;
/**
 * Oracle数据库连接处理 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class DBLink  {
  public Connection conn = null;
  private static OracleConnectionPoolDataSource ocpds=null;
  private static String applicationType="";
  private static String dsName="";
  Vector vst = new Vector();
  private PooledConnection dbpool; 
  private static OracleDataSource ods=null;
  private static DataSource ds=null;
  private static boolean init=false;
  private static int connecton_count=0;
	private void loadProperty(){
    if(!init)
    {
    	
      try
        {
        applicationType="CONSOLE";//
        //applicationType=Config.getValue("application_type");
        // 初始化数据源实例     
        if(applicationType.equals("CONSOLE"))
        {
              // 初始化数据源实例       
            ods= new OracleDataSource();
            ods.setDriverType("thin");
            ods.setServerName(Config.getValue("database_host"));
            ods.setNetworkProtocol("tcp");
            ods.setDatabaseName(Config.getValue("database_name"));
            ods.setPortNumber(Integer.parseInt(Config.getValue("database_port")));
            ods.setUser(Config.getValue("database_user"));
            ods.setPassword(Config.getValue("database_user_password"));
        
        }
        else
        {
             dsName=Config.getValue("data_source_name");
              InitialContext ctx =new InitialContext();
             ds=(DataSource)ctx.lookup(dsName);
        }
        }
        catch(Exception e)
        {
               e.printStackTrace();
      }
        init=true;
        //System.out.println("*******the load property");
    }
	}


	public DBLink(){
        loadProperty();
	
    }

	/**
	 * 初始化连接
	 *
	 */
	private void initConnection() 
  {
    try
    {
            
            if(applicationType.equals("CONSOLE"))
            {
               this.conn=ods.getConnection(); 
            }else
            {
              this.conn = ds.getConnection();
            }
            
            connecton_count++;
            System.out.println("++++++"+connecton_count);
               
         }catch(Exception e)
         {
          e.printStackTrace();
         }    
  }
        
	public synchronized java.sql.Connection getConnectionex(){
		//if (conn == null){
		//  conn = createConnection();
    //}
		if(conn == null) {
			initConnection();
		}
		return conn;
	}

	/*private  Connection createConnection(){
    try{
			if (conn == null){

				Class.forName(driverClass);
				System.out.println("Database Connection to '"+jdbcURL+"' using '"+driverClass+"' by '"+username+":"+password+"'");
				conn = java.sql.DriverManager.getConnection(jdbcURL, username, password);
				System.out.println("Database Connection to '"+jdbcURL+"' using '"+driverClass+"' by '"+username+":"+password+"'");
			}
		}catch(Exception e){
			System.err.println("Database Connection Created Error: " + e);
			System.out.println("Database Connection Created Error: " + e);
		}
		return conn;
	}*/

//	// perform a query with records returned
//	public ResultSet executeQuery(String sqlstr){
//    System.out.println("SQL <<<< "+sqlstr);
//		ResultSet rs1 = null;
//		try{
//			 if(applicationType.equals("CONSOLE"))
//       {
//          //clearParameters();
//          rs1 = stmt.executeQuery(sqlstr);
//       }
//       else
//       {
//          Statement stmt1 = conn.createStatement();
//          rs1 = stmt1.executeQuery(sqlstr);
//          vst.add(stmt1);
//       }
//		}
//		catch (SQLException e){
//			System.out.print("Query:"+e.getMessage());
//		}
//		return rs1;
//	}
//
	public boolean executeUpdate(String sqlstr) {
		System.out.println("SQL <<<< "+sqlstr);
		if(conn==null) 	initConnection();
        try
		{
			Statement stmt = conn.createStatement();
			//vst.add(stmt1);
			stmt.executeUpdate(sqlstr);
            stmt.close();
			return true;
		}
		catch(SQLException e)
		{
			System.out.print("Update:"+e.getMessage());
			return false;
		}
	}

	//perform insert
	public boolean executeInsert(String sqlstr) {
    if(conn==null) 	initConnection();
		System.out.println("SQL <<<< "+sqlstr);
		try
		{
			
			Statement stmt = conn.createStatement();
		//	vst.add(stmt1);
			
			stmt.executeUpdate(sqlstr);
			stmt.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.print("Insert:"+e.getMessage());
			return false;
		}
	}


	//perform delete
	public boolean executeDelete(String sqlstr) {
    if(conn==null) 	initConnection();
		System.out.println("SQL <<<< "+sqlstr);
		try
		{
			
            Statement stmt = conn.createStatement();
		//	vst.add(stmt1);
			
			stmt.executeUpdate(sqlstr);
            stmt.close();
			return true;
		}
		catch (Exception e)
		{
			System.out.print("Delete:"+e.getMessage());
			return false;
		}
	}
//
//	// return the num of columns
//	/*public int getColumns(){
//		int columns = 0;
//		try
//		{
//			this.resultsMeta = this.rs.getMetaData();
//			columns = this.resultsMeta.getColumnCount();
//		}
//		catch (SQLException e)  {}
//		return columns;
//	}*/
//
//
//
//
//
//
      public void beginTran()
{
    if(conn==null) 	initConnection();
 try{
          	this.conn.setAutoCommit(false);
          }catch(Exception e){
            System.out.println(e.toString());
          }
}
	public void commitTran()
{
if(conn==null) 	initConnection();
 try{
          	this.conn.commit();
	this.conn.setAutoCommit(true);
          }catch(Exception e){
            System.out.println(e.toString());
          }

}
	public CallableStatement prepareCall(String sql)
	{
    if(conn==null) 	initConnection();
		System.out.println("SQL <<<< "+sql);
		try {
			return this.conn.prepareCall(sql);
		}
		catch ( Exception e ) {
				System.out.println( "Error: " + e.toString() );
				return null;
			}
	}

	public void rollbackTran()
{
if(conn==null) 	initConnection();
 try{
          this.conn.rollback();
	this.conn.setAutoCommit(true);
          }catch(Exception e){
            System.out.println(e.toString());
          }

} 
//  
//	
//	protected void finalize()
//	{
//        close();
//	}
// //public void clearParameters()throws SQLException{
// //  if(pstmt != null)
// //    pstmt.clearParameters();
// //}  
// 
 /**
  * 释放 dbLink的连接
  *
  */
 public void close(){

      if(conn==null ) return;

    try
    {
       
        // stmt.close();
         if(!conn.isClosed())
        {            
         connecton_count--;
         System.out.println("-----"+connecton_count);
         if(!conn.isClosed())
            conn.close();
            //System.out.println("**********"+connecton_count);
        }
    }catch(Exception e)
    {
        e.printStackTrace();
    }
   
 }
 
 public Connection getConnection()
 {
     if(conn==null) 	initConnection();
     return conn;
 }
 
 public Statement createStatement() throws SQLException
 {
    if(conn==null){ 	
      initConnection();
    }
    return conn.createStatement();
 } 	
 public static void main(String args[])
    {
    }
}

