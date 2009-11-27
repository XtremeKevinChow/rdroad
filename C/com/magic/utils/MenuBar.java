package com.magic.utils;

import java.sql.*;
import java.net.*;
import java.io.*;
/**
 * 系统菜单
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class MenuBar {
	int menu_id=0;
	static String str="";

	public MenuBar()
	{
	}
	public  String get_menu() 
	{
     String s="<SCRIPT language=\"JavaScript\" src=\"/menu.js\" ></SCRIPT>\n";
     return s;		
//    DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
//    if(str.equals(""))
//		{
//			String ret="";
//			ret=ret+"stm_bm(['uueoehr',400,'','/images/blank.gif',0,'','',0,0,0,0,0,1,0,0]);\n";
//			ret=ret+"stm_bp('p0',[0,4,0,0,2,2,0,0,100,'',-2,'',-2,90,0,0,'#336699','transparent','',3,0,0,'#336699']);\n";
//			ret=ret+"stm_ai('p0i0',[0,'|','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#f1f2ee',1,'#cccccc',1,'','',3,3,0,0,'#336699','#336699','#336699','#336699','12px 宋体','12px 宋体',0,0]);\n";
//			int i=1;
//      try
//      {
//        	stmt=dblink.createStatement();rs= stmt.executeQuery("select * from s_menu where parent_id=0 order by id");
//          while(rs.next())
//          {
//            ret=ret+get_menu_str(rs.getInt("id"),i++,1);
//            ret=ret+"stm_aix('p0i2','p0i0',[0,'|','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#f1f2ee',1,'#cccccc',1,'','',3,3,0,0,'#336699','#ffffff','#ffffff','#ffffff','12px 宋体','12px 宋体',0,0]);\n";
//          }
//      }
//      catch(Exception e)
//      {
//        System.out.println(e);
//      }
//		
//			ret=ret+"stm_ep();\n";
//			ret=ret+"stm_em();\n";
//			str=ret;
//		}
//    dblink.close();
//		return str;
	}


	 String  get_menu_str(int id,int item_index, int layer ) throws Exception
	{
		DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
    String ret="";
		stmt=dblink.createStatement();
        rs= stmt.executeQuery("select * from s_menu where id="+id);
		rs.next();
		String name=rs.getString("name");
		String link="/goto?t_code="+rs.getInt("id");
		String description=rs.getString("description")+"&nbsp;("+rs.getInt("id")+")";
		int menu_type=rs.getInt("menu_type");
		if(menu_type==0)
		{
			ret="stm_aix('p"+menu_id+"i"+item_index+"','p"+(menu_id-1)+"i0',[0,'"+name+"','','',-1,-1,0,'"+link+"','_self','"+link+"','"+description+"','','',0,0,0,'','',0,0,0,0,1,'#f1f2ee',1,'#6699cc',0,'','',3,3,0,0,'#336699','#ffffff','#ffffff','#ffffff','12px 宋体']);\n";
		}
		else
		{
			int parent_menu_id=0;
			if(layer==1)
					ret="stm_aix('p"+menu_id+"i"+item_index+"','p"+ parent_menu_id+"i0',[0,'"+name+"','','',-1,-1,0,'','_self','','"+description+"','','',0,0,0,'','',0,0,0,0,1,'#f1f2ee',1,'#cccccc',1,'','',3,3,0,0,'#336699','#ff0000','#ffffff','#ff6600','12px 宋体','12px 宋体']);\n";
			else
			{
				parent_menu_id=menu_id-1;
				ret="stm_aix('p"+menu_id+"i"+item_index+"','p"+ parent_menu_id+"i0',[0,'"+name+"','','',-1,-1,0,'','_self','','"+description+"','','',6,0,0,'/images/arrow_r.gif','/images/arrow_w.gif',7,7,0,0,1,'#336699',0,'#6699cc',0,'','',3,3,0,0,'#336699','#ffffff','#ffffff','#000000','12px 宋体'])\n;";
			}
			menu_id=menu_id+1;
			if(layer==1)
				ret=ret+"stm_bpx('p"+menu_id+"','p"+ (menu_id-1)+"',[1,4,0,0,2,3,6,7,100,'progid:DXImageTransform.Microsoft.Fade(overlap=.5,enabled=0,Duration=0.43)',-2,'',-2,67,2,3,'#999999','#336699','',3,1,1,'#aca899']);\n";
			else
				ret=ret+"stm_bpx('p"+menu_id+"','p"+ (menu_id-1)+"',[1,2,-2,-3,2,3,0]);\n";
			int i=0;
			Statement stmt1=dblink.createStatement();rs=stmt1.executeQuery("select id from s_menu where parent_id="+id+" order by id");
			while(rs.next())
			{
				ret=ret+get_menu_str(rs.getInt("id"),i++,layer+1);
			}
			ret=ret+"stm_ep();\n";
		}
    dblink.close();
		return ret;
	}
    public static void main(String args[]) throws Exception
    {
		MenuBar mb=new MenuBar();
		System.out.println(mb.get_menu());
    }
}