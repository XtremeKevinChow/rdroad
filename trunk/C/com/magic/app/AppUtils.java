package com.magic.app;

import com.magic.utils.*;
import java.sql.*;
/**
 * Ӧ�ó��򹤾߼�
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class AppUtils 
{
  public AppUtils()
  {
  
  }
  /**
   * ���ݻ�Ա���ŵõ���ԱID
   * @param card_number     ��Ա����
   * @return ��ԱID
   */
  public static int getCardID(String card_number)
  {
      DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
      try
      {
        stmt=dblink.createStatement();
        rs=stmt.executeQuery("select id from mbr_members where card_id='"+card_number+"'");
        if(!rs.next()) 
                return 0;
          else
                return rs.getInt("id");
      }
      catch(Exception e)
      {
        return 0;
      }finally
      {
          try{
                if (rs != null ) rs.close();
                if (stmt != null ) stmt.close();
                if(dblink!=null) dblink.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
      }
      
     
  }
}