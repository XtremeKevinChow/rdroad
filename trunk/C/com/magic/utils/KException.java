package com.magic.utils;
import java.sql.*;

public class KException extends Exception 
{
  private Exception exception = null;
	private String message = "";
	private String errcode = "";


	public KException(String message)
	{
		super(message);
		this.errcode = "";
		this.message = message;
		this.exception = null;
	}


	public KException(String errcode, String message)
	{
		super(message);
		this.errcode = errcode;
		this.message = message;
		this.exception = null;
	}
  
  public KException(int errcode)
	{
      DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
    try
    {
    
      stmt=dblink.createStatement();rs= stmt.executeQuery("select id,description from s_error_code where id="+errcode);
      if(rs.next())
        this.message="Î´ÃèÊöµÄ´íÎó´úÂë";
      else
        this.message=rs.getString("description");
      this.errcode = errcode+"";
      this.exception = new Exception(rs.getString("description"));
    }catch(Exception e)
    {
      System.out.println(e);
    }finally
    {
        try
            {
                 if(rs!=null) rs.close();
                 if(stmt!=null) stmt.close();
                 dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
    }
	}


	public KException(Exception e)
	{
		super();
		this.exception = e;
	}


	public KException(String message, Exception e)
	{
		super(message);
		this.exception = e;
	}


	public String getErrorCode()
	{
		return errcode;
	}


	public String getMessage()
	{
		String message = super.getMessage();
		if (message == null && exception != null)
		{
			return exception.getMessage();
		}
		else
		{
			return message;
		}
	}


	public Exception getException()
	{
		return exception;
	}


	public String toString()
	{
		if (exception != null)
		{
			return exception.toString();
		}
		else
		{
			return super.toString();
		}
	}
}