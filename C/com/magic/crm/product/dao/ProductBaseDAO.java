package com.magic.crm.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.magic.crm.product.entity.ProductSKU;
import com.magic.crm.product.form.Product2Form;
import org.apache.struts.util.LabelValueBean;


public class ProductBaseDAO implements java.io.Serializable {
	private static Logger log = Logger.getLogger(ProductBaseDAO.class);

	public static int insertColor(Connection conn,String color_code,String color_name)throws Exception {
		int ret=0;
		PreparedStatement ps = conn.prepareStatement(
				"insert into prd_item_color(code,name) values(?,?)");
		try {
			ps.setString(1, color_code.toUpperCase());
			ps.setString(2, color_name);
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static int updateColor(Connection conn,String color_code,String color_name,String old_color_code)throws Exception {
		int ret=0;
		PreparedStatement ps = conn.prepareStatement(
				"update prd_item_color set code=?,name=? where code = ? ");
		try {
			ps.setString(1, color_code.toUpperCase());
			ps.setString(2, color_name);
			ps.setString(3, old_color_code);
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static int deleteColor(Connection conn,String color_code)throws Exception {
		int ret=0;
		PreparedStatement ps = null;
		try {
			
			ps = conn.prepareStatement("select count(*) from prd_item_sku where color_code = ?");
			ps.setString(1, color_code);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				if (rs.getInt(1)>0) {
					throw new Exception("已存在sku的颜色不能删除");
				}
			}
			rs.close();
			ps.close();
			
			ps = conn.prepareStatement("delete prd_item_color where code=?");
			ps.setString(1, color_code);
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	public static int insertSize(Connection conn,String size_code,int type_id)throws Exception {
		int ret=0;
		PreparedStatement ps = conn.prepareStatement(
				"insert into prd_item_size(code,name,type_id) values(?,?,?)");
		try {
			ps.setString(1, size_code.toUpperCase());
			ps.setString(2, size_code.toUpperCase());
			ps.setInt(3, type_id);
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static int updateSize(Connection conn,String size_code,int type_id,String old_size_code, int old_type_id)throws Exception {
		int ret=0;
		PreparedStatement ps = conn.prepareStatement(
				"update prd_item_size set code=?,type_id=? where code = ? and type_id=? ");
		try {
			ps.setString(1, size_code.toUpperCase());
			ps.setInt(2, type_id);
			ps.setString(3, old_size_code);
			ps.setInt(4, old_type_id);
			
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static int deleteSize(Connection conn,String size_code,int type_id)throws Exception {
		int ret=0;
		PreparedStatement ps = null;
		
		try {
			ps= conn.prepareStatement("select count(*) from prd_item_sku where size_code = ?");
			ps.setString(1, size_code);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				if (rs.getInt(1)>0) {
					throw new Exception("已存在sku的尺寸不能删除");
				}
			}
			rs.close();
			ps.close();
			
			ps = conn.prepareStatement("delete prd_item_size where code=? and type_id=?");
			ps.setString(1, size_code);
			ps.setInt(2, type_id);
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static int insertSize2(Connection conn,String size_code,int type_id)throws Exception {
		int ret=0;
		PreparedStatement ps = conn.prepareStatement(
				"insert into prd_item_size2(code,type_id) values(?,?)");
		try {
			ps.setString(1, size_code.toUpperCase());
			ps.setInt(2, type_id);
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static int updateSize2(Connection conn,String size_code,int type_id,String old_size_code, int old_type_id)throws Exception {
		int ret=0;
		PreparedStatement ps = conn.prepareStatement(
				"update prd_item_size2 set code=?,type_id=? where code = ? and type_id=? ");
		try {
			ps.setString(1, size_code.toUpperCase());
			ps.setInt(2, type_id);
			ps.setString(3, old_size_code);
			ps.setInt(4, old_type_id);
			
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static int deleteSize2(Connection conn,String size_code,int type_id)throws Exception {
		int ret=0;
		PreparedStatement ps = conn.prepareStatement(
				"delete prd_item_size2 where code=? and type_id=?");
		try {
			ps.setString(1, size_code);
			ps.setInt(2, type_id);
			ret = ps.executeUpdate();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static ArrayList listColorCode(Connection conn)throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = conn.prepareStatement(
				"select code from prd_item_color order by code");
		try {
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ret.add(rs.getString("code"));
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static ArrayList listColor(Connection conn)throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = conn.prepareStatement(
				"select code,name from prd_item_color where code !='0' order by code");
		try {
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LabelValueBean info = new LabelValueBean();
				info.setValue(rs.getString("code"));
				info.setLabel(rs.getString("name")+"(" + rs.getString("code")+")");
				
				ret.add(info);
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	/**
	 * 检查输入的颜色是否存在
	 * @param conn
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static boolean existsColor(Connection conn,String code)throws Exception {
		boolean ret = false;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("select count(1) from prd_item_color where code =? ");
			ps.setString(1, code.toUpperCase());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ret = (rs.getInt(1) > 0);
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	/**
	 * 检查输入的尺寸是否存在
	 * @param conn
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static boolean existsSize(Connection conn,String code)throws Exception {
		boolean ret = false;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("select count(1) from prd_item_size where code =? ");
			ps.setString(1, code.toUpperCase());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ret = (rs.getInt(1) > 0);
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static ArrayList listSize(Connection conn,int category)throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = conn.prepareStatement(
				"select code,name from prd_item_size where type_id=? order by code");
		try {
			ps.setInt(1, category);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LabelValueBean info = new LabelValueBean();
				info.setValue(rs.getString("code"));
				info.setLabel(rs.getString("name"));
				
				ret.add(info);
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	public static ArrayList listSize(Connection conn,String item_code)throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = conn.prepareStatement(
				"select code,name from prd_item_size where type_id=" +
				" (select catalog_id from prd_item_category where parent_id =100 " +
				" connect by prior parent_id =catalog_id " +
				" start with catalog_id=(select category_id from prd_item where itm_code = ? ))" +
				" order by code");
		try {
			ps.setString(1,item_code);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LabelValueBean info = new LabelValueBean();
				info.setValue(rs.getString("code"));
				info.setLabel(rs.getString("name"));
				
				ret.add(info);
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
	
	
	public static ArrayList listSizeCodeByType(Connection conn,int type_id)throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = conn.prepareStatement(
				"select code from prd_item_size where type_id=? order by code");
		try {
			ps.setInt(1,type_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ret.add(rs.getString("code"));
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}	
	
	public static ArrayList listSize2CodeByType(Connection conn,int type_id)throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = conn.prepareStatement(
				"select code from prd_item_size2 where type_id=? order by code");
		try {
			ps.setInt(1,type_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ret.add(rs.getString("code"));
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}	
	
}
