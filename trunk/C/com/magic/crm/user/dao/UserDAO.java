package com.magic.crm.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.math.*;
import com.magic.crm.user.entity.*;
import com.magic.crm.user.form.GradeForm;
import com.magic.crm.util.MD5;

/**
 * Jdbc Bean Class<br>
 * <br>
 * Autogenerated on 03/17/2003 10:36:03<br>
 * &nbsp;&nbsp;&nbsp; table = "users"
 * 
 * @author Generator
 */
public class UserDAO implements java.io.Serializable {

	public com.magic.crm.user.entity.User find(Connection con,
			com.magic.crm.user.entity.User info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sQuery = "SELECT ID,COMPANY_ID,NAME,PHONE,USERID,PWD,DEPARTMENT_ID,STATUS,EMAIL,TITLE,GENDER,EMPLOYEE_TYPE,MOBILE_PHONE,BP,PIC_URL,CREATE_DATE,LOCATION_ID,EMPLOYEE_NUMBER,MANAGER_ID,ACCESSRIGHT FROM org_persons WHERE "
					+ " id=?";
			pstmt = con.prepareStatement(sQuery);

			pstmt.setString(1, info.getId());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				info.setId(rs.getString("ID"));
				info.setCOMPANY_ID(rs.getInt("COMPANY_ID"));
				info.setNAME(rs.getString("NAME"));
				info.setPHONE(rs.getString("PHONE"));
				info.setUSERID(rs.getString("USERID"));
				info.setPWD(rs.getString("PWD"));
				info.setDEPARTMENT_ID(rs.getInt("DEPARTMENT_ID"));
				info.setSTATUS(rs.getInt("STATUS"));
				info.setEMAIL(rs.getString("EMAIL"));
				info.setTITLE(rs.getString("TITLE"));
				info.setGENDER(rs.getString("GENDER"));
				info.setEMPLOYEE_TYPE(rs.getInt("EMPLOYEE_TYPE"));
				info.setMOBILE_PHONE(rs.getString("MOBILE_PHONE"));
				info.setBP(rs.getString("BP"));
				info.setPIC_URL(rs.getString("PIC_URL"));
				info.setCREATE_DATE(rs.getDate("CREATE_DATE"));
				info.setLOCATION_ID(rs.getInt("LOCATION_ID"));
				info.setEMPLOYEE_NUMBER(rs.getString("EMPLOYEE_NUMBER"));
				info.setMANAGER_ID(rs.getInt("MANAGER_ID"));

			} else {
				return null;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return info;
	}

	public com.magic.crm.user.entity.User findByName(Connection con,
			com.magic.crm.user.entity.User info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sQuery = "SELECT ID,COMPANY_ID,NAME,PHONE,USERID,PWD,DEPARTMENT_ID,STATUS,EMAIL,TITLE,GENDER,EMPLOYEE_TYPE,MOBILE_PHONE,BP,PIC_URL,CREATE_DATE,LOCATION_ID,EMPLOYEE_NUMBER,MANAGER_ID,ACCESSRIGHT FROM org_persons  ";
			sQuery += " WHERE status=0 ";

			if (info.getLogID() > 0 && info.getTelno().length() > 0) {
				sQuery += " and department_id=2 and employee_number=? ";
			}
			if (info.getUSERID().length() > 0) {
				sQuery += " and USERID=? ";
			}
			// System.out.println(sQuery);
			pstmt = con.prepareStatement(sQuery);
			if (info.getUSERID().length() > 0) {
				pstmt.setString(1, info.getUSERID());
			}

			if (info.getLogID() > 0) {
				pstmt.setString(1, info.getLogID() + "");
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				info.setId(rs.getString("ID"));
				info.setCOMPANY_ID(rs.getInt("COMPANY_ID"));
				info.setNAME(rs.getString("NAME"));
				info.setPHONE(rs.getString("PHONE"));
				info.setUSERID(rs.getString("USERID"));
				info.setPWD(rs.getString("PWD"));
				info.setDEPARTMENT_ID(rs.getInt("DEPARTMENT_ID"));
				info.setSTATUS(rs.getInt("STATUS"));
				info.setEMAIL(rs.getString("EMAIL"));
				info.setTITLE(rs.getString("TITLE"));
				info.setGENDER(rs.getString("GENDER"));
				info.setEMPLOYEE_TYPE(rs.getInt("EMPLOYEE_TYPE"));
				info.setMOBILE_PHONE(rs.getString("MOBILE_PHONE"));
				info.setBP(rs.getString("BP"));
				info.setPIC_URL(rs.getString("PIC_URL"));
				info.setCREATE_DATE(rs.getDate("CREATE_DATE"));
				info.setLOCATION_ID(rs.getInt("LOCATION_ID"));
				info.setEMPLOYEE_NUMBER(rs.getString("EMPLOYEE_NUMBER"));
				info.setMANAGER_ID(rs.getInt("MANAGER_ID"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("sql error");

			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return info;
	}

	public boolean checkUserID(Connection con, User info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ifuserid = false;

		try {
			String sQuery = "SELECT USERID FROM org_persons WHERE  USERID=? and id<>?";
			pstmt = con.prepareStatement(sQuery);

			pstmt.setString(1, info.getUSERID());
			pstmt.setString(2, info.getId());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ifuserid = true;
			}
		} catch (SQLException e) {
			System.out.println("sql error");

			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return ifuserid;
	}

	/**
	 * 检测工号是否已经存在
	 * @param con
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public boolean checkEmployeeNumber(Connection con, User info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ifuserid = false;
		String sQuery = null;
		try {
			if (info.getId() == null) {//新增
				sQuery = "SELECT employee_number FROM org_persons WHERE status = 0 and department_id = 2 and employee_number=? ";
			} else { //修改
				sQuery = "SELECT employee_number FROM org_persons WHERE status = 0 and department_id =2 and employee_number=? and id<>?";
			}
			
			pstmt = con.prepareStatement(sQuery);

			pstmt.setString(1, info.getEMPLOYEE_NUMBER());
			if (info.getId() != null) {
				pstmt.setString(2, info.getId());
			}
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ifuserid = true;
			}
		} catch (SQLException e) {
			System.out.println("sql error");

			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return ifuserid;
	}

	public Collection findAllUsers2(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection userCol = new ArrayList();

		try {
			String sQuery = "SELECT ID,COMPANY_ID,NAME,PHONE,USERID,PWD,DEPARTMENT_ID,STATUS,EMAIL,TITLE,GENDER,EMPLOYEE_TYPE,MOBILE_PHONE,BP,PIC_URL,CREATE_DATE,LOCATION_ID,EMPLOYEE_NUMBER,MANAGER_ID,ACCESSRIGHT "
					+ "FROM org_persons where status=0 and DEPARTMENT_ID =3 order by name";
			pstmt = con.prepareStatement(sQuery);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				User info = new User();

				info.setId(rs.getString("ID"));
				info.setCOMPANY_ID(rs.getInt("COMPANY_ID"));
				info.setNAME(rs.getString("NAME"));
				info.setPHONE(rs.getString("PHONE"));
				info.setUSERID(rs.getString("USERID"));
				info.setPWD(rs.getString("PWD"));
				info.setDEPARTMENT_ID(rs.getInt("DEPARTMENT_ID"));
				info.setSTATUS(rs.getInt("STATUS"));
				info.setEMAIL(rs.getString("EMAIL"));
				info.setTITLE(rs.getString("TITLE"));
				info.setGENDER(rs.getString("GENDER"));
				info.setEMPLOYEE_TYPE(rs.getInt("EMPLOYEE_TYPE"));
				info.setMOBILE_PHONE(rs.getString("MOBILE_PHONE"));
				info.setBP(rs.getString("BP"));
				info.setPIC_URL(rs.getString("PIC_URL"));
				info.setCREATE_DATE(rs.getDate("CREATE_DATE"));
				info.setLOCATION_ID(rs.getInt("LOCATION_ID"));
				info.setEMPLOYEE_NUMBER(rs.getString("EMPLOYEE_NUMBER"));
				info.setMANAGER_ID(rs.getInt("MANAGER_ID"));

				userCol.add(info);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return userCol;
	}

	public Collection findAllUsers(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection userCol = new ArrayList();

		try {
			String sQuery = "SELECT ID,COMPANY_ID,NAME,PHONE,USERID,PWD,DEPARTMENT_ID,STATUS,EMAIL,TITLE,GENDER,EMPLOYEE_TYPE,MOBILE_PHONE,BP,PIC_URL,CREATE_DATE,"
					+ "LOCATION_ID,EMPLOYEE_NUMBER,MANAGER_ID,ACCESSRIGHT FROM org_persons where status=0 order by DEPARTMENT_ID,name";
			pstmt = con.prepareStatement(sQuery);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				User info = new User();

				info.setId(rs.getString("ID"));
				info.setCOMPANY_ID(rs.getInt("COMPANY_ID"));
				info.setNAME(rs.getString("NAME"));
				info.setPHONE(rs.getString("PHONE"));
				info.setUSERID(rs.getString("USERID"));
				info.setPWD(rs.getString("PWD"));
				info.setDEPARTMENT_ID(rs.getInt("DEPARTMENT_ID"));
				info.setSTATUS(rs.getInt("STATUS"));
				info.setEMAIL(rs.getString("EMAIL"));
				info.setTITLE(rs.getString("TITLE"));
				info.setGENDER(rs.getString("GENDER"));
				info.setEMPLOYEE_TYPE(rs.getInt("EMPLOYEE_TYPE"));
				info.setMOBILE_PHONE(rs.getString("MOBILE_PHONE"));
				info.setBP(rs.getString("BP"));
				info.setPIC_URL(rs.getString("PIC_URL"));
				info.setCREATE_DATE(rs.getDate("CREATE_DATE"));
				info.setLOCATION_ID(rs.getInt("LOCATION_ID"));
				info.setEMPLOYEE_NUMBER(rs.getString("EMPLOYEE_NUMBER"));
				info.setMANAGER_ID(rs.getInt("MANAGER_ID"));

				userCol.add(info);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return userCol;
	}

	/*
	 * 得到某部门的人
	 */
	public Collection findAllUsers(Connection con, int dept_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection userCol = new ArrayList();

		try {
			String sQuery = "SELECT ID,COMPANY_ID,NAME,PHONE,USERID,PWD,DEPARTMENT_ID,STATUS,EMAIL,TITLE,GENDER,EMPLOYEE_TYPE,MOBILE_PHONE,BP,PIC_URL,CREATE_DATE,LOCATION_ID,EMPLOYEE_NUMBER,"
					+ "MANAGER_ID,ACCESSRIGHT FROM org_persons where status=0 and DEPARTMENT_ID="
					+ dept_id + " order by name";
			pstmt = con.prepareStatement(sQuery);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				User info = new User();

				info.setId(rs.getString("ID"));
				info.setCOMPANY_ID(rs.getInt("COMPANY_ID"));
				info.setNAME(rs.getString("NAME"));
				info.setPHONE(rs.getString("PHONE"));
				info.setUSERID(rs.getString("USERID"));
				info.setPWD(rs.getString("PWD"));
				info.setDEPARTMENT_ID(rs.getInt("DEPARTMENT_ID"));
				info.setSTATUS(rs.getInt("STATUS"));
				info.setEMAIL(rs.getString("EMAIL"));
				info.setTITLE(rs.getString("TITLE"));
				info.setGENDER(rs.getString("GENDER"));
				info.setEMPLOYEE_TYPE(rs.getInt("EMPLOYEE_TYPE"));
				info.setMOBILE_PHONE(rs.getString("MOBILE_PHONE"));
				info.setBP(rs.getString("BP"));
				info.setPIC_URL(rs.getString("PIC_URL"));
				info.setCREATE_DATE(rs.getDate("CREATE_DATE"));
				info.setLOCATION_ID(rs.getInt("LOCATION_ID"));
				info.setEMPLOYEE_NUMBER(rs.getString("EMPLOYEE_NUMBER"));
				info.setMANAGER_ID(rs.getInt("MANAGER_ID"));

				userCol.add(info);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return userCol;
	}

	public BigDecimal insert(Connection con,
			com.magic.crm.user.entity.User info) throws SQLException {
		PreparedStatement pstmt = null;
		BigDecimal userId = null;
		MD5 m = new MD5();
		ResultSet rs = null;
		try {

			String userIdSql = "select seq_org_persons.nextval from dual";
			pstmt = con.prepareStatement(userIdSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userId = rs.getBigDecimal(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		try {

			String sQuery = "INSERT INTO org_persons(ID,COMPANY_ID,NAME,PHONE,USERID,PWD,DEPARTMENT_ID,STATUS,EMAIL,TITLE,GENDER,EMPLOYEE_TYPE,MOBILE_PHONE,BP,PIC_URL,LOCATION_ID,EMPLOYEE_NUMBER,MANAGER_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
			pstmt = con.prepareStatement(sQuery);

			pstmt.setBigDecimal(1, userId);
			pstmt.setInt(2, info.getCOMPANY_ID());
			pstmt.setString(3, info.getNAME());
			pstmt.setString(4, info.getPHONE());
			pstmt.setString(5, info.getUSERID());
			pstmt.setString(6, m.getMD5ofStr(info.getPWD()));
			pstmt.setInt(7, info.getDEPARTMENT_ID());
			pstmt.setInt(8, info.getSTATUS());
			pstmt.setString(9, info.getEMAIL());
			pstmt.setString(10, info.getTITLE());
			pstmt.setString(11, info.getGENDER());
			pstmt.setInt(12, info.getEMPLOYEE_TYPE());
			pstmt.setString(13, info.getMOBILE_PHONE());
			pstmt.setString(14, info.getBP());
			pstmt.setString(15, info.getPIC_URL());
			pstmt.setInt(16, info.getLOCATION_ID());
			pstmt.setString(17, info.getEMPLOYEE_NUMBER());
			pstmt.setInt(18, info.getMANAGER_ID());

			pstmt.execute();

		} catch (SQLException e) {

			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return userId;
	}

	public void update(Connection con, com.magic.crm.user.entity.User info,
			String ifModify) throws SQLException {
		PreparedStatement pstmt = null;
		MD5 m = new MD5();
		try {
			String sQuery = "UPDATE org_persons SET ID=?,COMPANY_ID=?,NAME=?,PHONE=?,USERID=?,PWD=?,DEPARTMENT_ID=?,STATUS=?,EMAIL=?,TITLE=?,GENDER=?,EMPLOYEE_TYPE=?,MOBILE_PHONE=?,BP=?,PIC_URL=?,LOCATION_ID=?,EMPLOYEE_NUMBER=?,MANAGER_ID=? WHERE id=?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, info.getId());
			pstmt.setInt(2, info.getCOMPANY_ID());
			pstmt.setString(3, info.getNAME());
			pstmt.setString(4, info.getPHONE());
			pstmt.setString(5, info.getUSERID());
			if (ifModify != null && ifModify.equals("1")) {
				pstmt.setString(6, m.getMD5ofStr(info.getPWD()));
			} else {
				pstmt.setString(6, info.getPWD());
			}
			pstmt.setInt(7, info.getDEPARTMENT_ID());
			pstmt.setInt(8, info.getSTATUS());
			pstmt.setString(9, info.getEMAIL());
			pstmt.setString(10, info.getTITLE());
			pstmt.setString(11, info.getGENDER());
			pstmt.setInt(12, info.getEMPLOYEE_TYPE());
			pstmt.setString(13, info.getMOBILE_PHONE());
			pstmt.setString(14, info.getBP());
			pstmt.setString(15, info.getPIC_URL());
			pstmt.setInt(16, info.getLOCATION_ID());
			pstmt.setString(17, info.getEMPLOYEE_NUMBER());
			pstmt.setInt(18, info.getMANAGER_ID());
			pstmt.setString(19, info.getId());

			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	/*
	 * 修改用户密码
	 * 
	 */
	public void modifyPWD(Connection con, User info) throws SQLException {
		PreparedStatement pstmt = null;
		MD5 m = new MD5();
		try {
			String sQuery = "UPDATE org_persons set PWD=? WHERE id=?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, m.getMD5ofStr(info.getPWD()));
			pstmt.setString(2, info.getId());
			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	public void delete(Connection con, com.magic.crm.user.entity.User info)
			throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String sQuery = "update  org_persons set status=3 WHERE id=?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, info.getId());

			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	public static String getPath(Connection con, int transaction_code)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String servletPath = "";
		try {
			String sQuery = "select link_addr from s_menu where menu_type=0 and id="
					+ transaction_code;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				servletPath = rs.getString(1);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return servletPath;
	}

	public String[] getDeptment() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] userCol = new String[11];
		userCol[0] = "（无）";
		userCol[1] = "市场部";
		userCol[2] = "客服部";
		userCol[3] = "编辑部";
		userCol[4] = "人事行政部";
		userCol[5] = "IT部";
		userCol[6] = "物流部";
		userCol[7] = "总经理室";
		userCol[8] = "财务部";
		userCol[9] = "网站";
		userCol[10] = "销售部";
		return userCol;

	}

	/*
	 * 根据人员ID得到会员姓名
	 */
	public static String getPerson(Connection con, int id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean cardidSeq = false;
		String name = "";
		try {
			String getIDsql = "select name from org_persons where id=" + id;
			pstmt = con.prepareStatement(getIDsql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				name = rs.getString(1);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return name;
	}
	
	/**
	 * 客服满意度统计
	 * @param conn
	 * @return
	 */
	public static Collection findPersonalValue(Connection conn, GradeForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	    Collection coll = new ArrayList();
	    String sql = "select b.userid, b.name, a.personid, count(*) as amount, "
	    	+ "sum(decode(a.level_id, 1, 1, 0)) as amount1, "
	    	+ "sum(decode(a.level_id, 2, 1, 0)) as amount2, "
	    	+ "sum(decode(a.level_id, 3, 1, 0)) as amount3, "
	    	+ "sum(decode(a.level_id, 4, 1, 0)) as amount4, "
	    	+ "sum(decode(a.level_id, 1, 1, 2, 1, 3, 1, 4, 1, 0)) as total "
	    	+ "from org_grade a inner join org_persons b "
	    	+ "on a.personid = b.employee_number where b.department_id = 2 "
	    	+ "and b.status = 0 and a.ivrdate >= ? and a.ivrdate < ? + 1 ";
	    if (param.getUserName() != null && param.getUserName().trim().length() > 0) {
	    	sql +=  "and b.name like ? ";
	    }
	    if (param.getPersonId() != null && param.getPersonId().trim().length() > 0) {
	    	sql += "and a.personid = ? ";
	    }
	    	
	    sql += "group by b.userid, b.name, a.personid ";
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, param.getBeginDate());
			pstmt.setDate(2, param.getEndDate());
			if (param.getUserName() != null && param.getUserName().trim().length() > 0) {
				pstmt.setString(3, param.getUserName());
				if (param.getPersonId() != null && param.getPersonId().trim().length() > 0) {
		    		pstmt.setString(4, param.getPersonId());
		    	}
		    } else {
		    	if (param.getPersonId() != null && param.getPersonId().trim().length() > 0) {
		    		pstmt.setString(3, param.getPersonId());
		    	}
		    	
		    }
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Grade grade = new Grade();
				grade.setUserId(rs.getString("userid"));
				grade.setUserName(rs.getString("name"));
				grade.setEmpNo(rs.getString("personid"));
				grade.setAmount1(rs.getInt("amount1"));
				grade.setAmount2(rs.getInt("amount2"));
				grade.setAmount3(rs.getInt("amount3"));
				grade.setAmount4(rs.getInt("amount4"));
				grade.setTotal(rs.getInt("total"));
				coll.add(grade);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return coll;
	}
}
