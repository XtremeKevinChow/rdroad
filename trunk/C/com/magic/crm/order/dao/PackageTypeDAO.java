package com.magic.crm.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.magic.crm.order.entity.PackageType;
import com.magic.crm.util.KeyValue;

public class PackageTypeDAO {

	public static ArrayList listPackages(Connection conn)throws Exception {
		ArrayList ret = new ArrayList();
		PreparedStatement ps = conn.prepareStatement(
				" select id,name,description,price from package_type ");
		try {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PackageType pack = new PackageType();
				pack.setId(rs.getInt("id"));
				pack.setName(rs.getString("name"));
				pack.setDescription(rs.getString("description"));
				pack.setPrice(rs.getDouble("price"));
				
				ret.add(pack);
			}
			rs.close();
		} finally {
			try {ps.close();} catch(Exception e) {}
		}
		return ret;
	}
}
