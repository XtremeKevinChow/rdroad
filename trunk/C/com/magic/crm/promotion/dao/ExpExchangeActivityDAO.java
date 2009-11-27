package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.magic.crm.promotion.entity.ExpExchangeActivity;
import com.magic.crm.promotion.entity.ExpExchangeStepMst;
import com.magic.crm.promotion.entity.ExpExchangeStepDtl;
import com.magic.crm.promotion.entity.ExpExchangePackageMst;
import com.magic.crm.promotion.entity.ExpExchangePackageDtl;
import com.magic.crm.promotion.form.ExpExchangePackageForm;
import com.magic.crm.promotion.form.ExpExchangeActivityForm;
import com.magic.crm.promotion.dao.ExpExchangePackageDAO;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.promotion.entity.ExpExchangePackageDtl;
import com.magic.crm.product.entity.Product;

/**
 * 积分活动DAO
 * 
 * @author user
 * 
 */
public class ExpExchangeActivityDAO {

	private static Logger log = Logger.getLogger(ExpExchangeActivityDAO.class);

	/**
	 * 查找所有活动
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static Collection findAll(Connection con) throws Exception {

		PreparedStatement pstmt = null;

		ResultSet rs = null;
		Collection list = new ArrayList();
		try {

			String sql = "select * from exp_exchange_activity where 1 = 1 ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ExpExchangeActivity data = new ExpExchangeActivity();
				data.setActivityNo(rs.getString("activity_no"));
				data.setActivityDesc(rs.getString("activity_desc"));
				data.setStatus(rs.getInt("status"));
				data.setBeginDate(rs.getDate("begin_date"));
				data.setEndDate(rs.getDate("end_date"));
				data.setCreatePerson(rs.getInt("create_person"));
				data.setCreateDate(rs.getDate("create_date"));
				data.setCheckPerson(rs.getInt("check_person"));
				data.setCheckDate(rs.getDate("check_date"));
				data.setExchangeType(rs.getString("exchange_type"));
				data.setDealType(rs.getString("deal_type"));
				data.setGiftLastDate(rs.getDate("gift_last_date"));
				data.setActivityType(rs.getInt("type"));//add by user 2008-04-03
				list.add(data);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		return list;
	}
	
	/**
	 * 更新活动状态
	 * -1-删除；0-新建；1-审核；2-关闭
	 * @param con
	 * @param param
	 * @return i
	 * @throws Exception
	 */
	public static int updateActivityStatus(Connection con,
			ExpExchangeActivityForm param) throws Exception {
		PreparedStatement pstmt = null;
		int i = 0;
		try {

			String sql = "update exp_exchange_activity set status = ? where activity_no = ? and status = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getStatus());
			pstmt.setString(2, param.getActivityNo());
			pstmt.setInt(3, param.getOldStatus());
			i = pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return i;
	}
	
	/**
	 * 新增积分活动
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void insertActivity(Connection con,
			ExpExchangeActivity param) throws Exception {
		PreparedStatement pstmt = null;
		String sql = null;
		sql = "insert into exp_exchange_activity ("
			+ "activity_no, activity_desc, status, begin_date, end_date, "
			+ "create_person, create_date, exchange_type, deal_type, gift_last_date, "
			+ "headhtml, type) "
			+ "values (?, ?, ?, ?, ?, "
			+ "?, sysdate, ?, ?, ?, "
			+ "empty_clob(), ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getActivityNo());
			pstmt.setString(2, param.getActivityDesc());
			pstmt.setInt(3, 0);
			pstmt.setDate(4, param.getBeginDate());
			pstmt.setDate(5, param.getEndDate());
			pstmt.setInt(6, param.getCreatePerson());
			//pstmt.setDate(7, param.getCreateDate());
			pstmt.setString(7, param.getExchangeType());
			pstmt.setString(8,param.getDealType());
			pstmt.setDate(9, param.getGiftLastDate());
			pstmt.setInt(10, param.getActivityType());//add by user 2008-04-03
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 更新大型字段
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void updateActivityHTML(Connection con,
			ExpExchangeActivity param) throws Exception {
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		oracle.sql.CLOB clob=null;
		sql="select headhtml from exp_exchange_activity where activity_no = ? for update";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, param.getActivityNo());
        	rs = pstmt.executeQuery();
        	if(rs.next()){
                clob = (oracle.sql.CLOB)rs.getClob(1);//得到记录
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		clob.putChars(1,param.getHeadHtml().toCharArray());
		
		
		sql = "update exp_exchange_activity set headhtml = ? where activity_no = ? ";
		try {
			pstmt = con.prepareStatement(sql);
            pstmt.setClob(1,clob);
            pstmt.setString(2,param.getActivityNo());
            pstmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 修改积分活动
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void updateActivity(Connection con,
			ExpExchangeActivity param) throws Exception {
		PreparedStatement pstmt = null;
		String sql = null;
		sql = "update exp_exchange_activity "
			+ "set "
			+ "activity_desc = ?, "
			+ "begin_date = ?, "
			+ "end_date = ?, "
			+ "create_person = ?, " 
			+ "create_date = sysdate, "
			+ "exchange_type = ?, "
			+ "deal_type = ?, "
			+ "gift_last_date = ?, "
			+ "headhtml = empty_clob() "
			+ "where activity_no = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getActivityDesc());
			pstmt.setDate(2, param.getBeginDate());
			pstmt.setDate(3, param.getEndDate());
			pstmt.setInt(4, param.getCreatePerson());
			pstmt.setString(5, param.getExchangeType());
			pstmt.setString(6,param.getDealType());
			pstmt.setDate(7, param.getGiftLastDate());
			pstmt.setString(8, param.getActivityNo());
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		
	}
	
	/**
	 * 新增积分档次
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static long insertStepMst(Connection con,
			ExpExchangeStepMst param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long mstId = 0;
		try {
			String sql = "select SEQ_EXP_EXCHANGE_STEP_MST_ID.nextval from dual";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mstId = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null) 
				pstmt.close();
		}
		try {

			String sql = "insert into exp_exchange_step_mst (id, "
				+ "activity_no, begin_exp, create_person, create_date, status) "
				+ "values (?, "
				+ "?, ?, ?, sysdate, 'Y') ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, mstId);
			pstmt.setString(2, param.getActivity().getActivityNo());
			pstmt.setInt(3, param.getBeginExp());
			pstmt.setInt(4, param.getCreatePerson());
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return mstId;
	}
	
	/**
	 * 更新积分档次
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void updateStepMst(Connection con,
			ExpExchangeStepMst param) throws Exception {
		PreparedStatement pstmt = null;
		String sql = null;
		sql = "update exp_exchange_step_mst ("
			+ "set "
			+ "beg_in_exp = ?, "
			+ "create_person = ?, "
			+ "create_date = ? "
			+ "where id = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getBeginExp());
			pstmt.setInt(2, param.getCreatePerson());
			pstmt.setDate(3, param.getCreateDate());
			pstmt.setLong(4, param.getId());
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		
	}
	/**
	 * 删除一个活动下所有的积分档次
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void removeStepMstByActivityNo(Connection con,
			String activityNo) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from exp_exchange_step_mst where activity_no = ? and status = 'Y'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, activityNo);
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	
	/**
	 * 新增积分档次明细
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void insertStepDtl(Connection con,
			ExpExchangeStepDtl param) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into exp_exchange_step_dtl (id, "
				+ "step_id, type, no, begin_date, end_date, "
				+ "order_require, add_money, status) "
				+ "values (SEQ_EXP_EXCHANGE_STEP_DTL_ID.nextval, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?) ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, param.getStepMst().getId());
			pstmt.setString(2, param.getStepType());
			pstmt.setString(3, param.getNo());
			pstmt.setDate(4, param.getBeginDate());
			pstmt.setDate(5, param.getEndDate());
			pstmt.setDouble(6, param.getOrderRequire());
			pstmt.setDouble(7, param.getAddMoney());
			pstmt.setString(8, "Y");
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 删除档次明细(status = 'N')
	 * @param con
	 * @param dtl
	 * @throws Exception
	 */
	public static void deleteStepDtl(Connection con,
			ExpExchangeStepDtl dtl) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "update exp_exchange_step_dtl set status = 'N' where id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, dtl.getId());
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 删除档次(status = 'N')
	 * @param con
	 * @param dtl
	 * @throws Exception
	 */
	public static void deleteStepMst(Connection con,
			ExpExchangeStepMst mst) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "update exp_exchange_step_mst set status = 'N' where id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, mst.getId());
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	/**
	 * 删除一个活动下所有的积分档次明细
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void removeStepDtlByActivityNo(Connection con,
			String activityNo) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from exp_exchange_step_dtl where step_id in "
				+ "(select id from exp_exchange_step_mst where activity_no = ?) ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, activityNo);
			pstmt.execute();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 根据积分档次明细id查找积分档次明细
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static ExpExchangeStepDtl findByStepDtlPk(Connection con,
			long stepDtlId) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ExpExchangeStepDtl data = new ExpExchangeStepDtl();
		try {
			String sql = "select * from exp_exchange_step_dtl where status = 'Y' and id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, stepDtlId);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				data.setId(rs.getLong("id"));
				data.getStepMst().setId((rs.getLong("step_id")));
				data.setStepType(rs.getString("type"));
				data.setNo(rs.getString("no"));
				data.setBeginDate(rs.getDate("begin_date"));
				data.setEndDate(rs.getDate("end_date"));
				data.setOrderRequire(rs.getDouble("order_require"));
				data.setAddMoney(rs.getDouble("add_money"));
				data.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		return data;
	}

	/**
	 * 根据积分档次id查找积分档次
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static ExpExchangeStepMst findByStepMstPk(Connection con,
			long stepMstId) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ExpExchangeStepMst data = new ExpExchangeStepMst();
		try {
			String sql = "select * from exp_exchange_step_mst where status = 'Y' and id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, stepMstId);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				data.setId(rs.getLong("id"));
				data.getActivity().setActivityNo(rs.getString("activity_no"));
				data.setBeginExp(rs.getInt("begin_exp"));
				data.setCreatePerson(rs.getInt("create_person"));
				data.setCreateDate(rs.getDate("create_date"));
				data.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		return data;
	}

	/**
	 * 根据积分档次id查找积分档次
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static ExpExchangeActivity findByActivityPk(Connection con,
			String activityNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ExpExchangeActivity data = new ExpExchangeActivity();
		try {
			String sql = "select * from exp_exchange_activity where activity_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, activityNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				data.setActivityNo(rs.getString("activity_no"));
				data.setActivityDesc(rs.getString("activity_desc"));
				data.setStatus(rs.getInt("status"));
				data.setBeginDate(rs.getDate("begin_date"));
				data.setEndDate(rs.getDate("end_date"));
				data.setCreatePerson(rs.getInt("create_person"));
				data.setCreateDate(rs.getDate("create_date"));
				data.setCheckPerson(rs.getInt("check_person"));
				data.setCheckDate(rs.getDate("check_date"));
				data.setExchangeType(rs.getString("exchange_type"));
				data.setDealType(rs.getString("deal_type"));
				data.setGiftLastDate(rs.getDate("gift_last_date"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		return data;
	}

	/**
	 * 加载一个活动，用于积分兑换 如果同时有多个积分活动有效，只能加载最近的一个 created by user
	 * 
	 * @param con
	 * @throws Exception
	 */
	public static ExpExchangeActivity loadCurrentActivity(Connection con)
			throws Exception {

		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ExpExchangeActivity activity = new ExpExchangeActivity();

		try {
			// 审核的，未过期的活动根据审核时间倒序
			String sql = "select * from exp_exchange_activity where status = 1 and type = 1 "
					+ "and sysdate >= begin_date and sysdate < end_date + 1 "
					+ "order by check_date desc ";
			pstmt1 = con.prepareStatement(sql);
			rs1 = pstmt1.executeQuery();
			if (rs1.next()) {
				Collection stepMstList = new ArrayList();
				activity.setActivityNo(rs1.getString("activity_no"));
				activity.setActivityDesc(rs1.getString("activity_desc"));
				activity.setStatus(rs1.getInt("status"));
				activity.setBeginDate(rs1.getDate("begin_date"));
				activity.setEndDate(rs1.getDate("end_date"));
				activity.setCreatePerson(rs1.getInt("create_person"));
				activity.setCreateDate(rs1.getDate("create_date"));
				activity.setCheckPerson(rs1.getInt("check_person"));
				activity.setCheckDate(rs1.getDate("check_date"));
				activity.setExchangeType(rs1.getString("exchange_type"));
				activity.setDealType(rs1.getString("deal_type"));
				activity.setGiftLastDate(rs1.getDate("gift_last_date"));
				
				// 根据活动号得到所有有效的积分档次
				sql = "select * from exp_exchange_step_mst where activity_no = ? and status = 'Y' order by begin_exp ";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setString(1, activity.getActivityNo());
				rs2 = pstmt2.executeQuery();
				while (rs2.next()) {
					Collection stepDtlList = new ArrayList();
					ExpExchangeStepMst stepMst = new ExpExchangeStepMst();
					stepMst.setActivity(activity);
					stepMst.setBeginExp(rs2.getInt("begin_exp"));
					stepMst.setId(rs2.getLong("id"));
					stepMst.setCreatePerson(rs2.getInt("create_person"));
					stepMst.setCreateDate(rs2.getDate("create_date"));
					stepMst.setStatus(rs2.getString("status"));
					// 根据档次的到所有有效明细
					sql = "select * from exp_exchange_step_dtl where step_id = ? and status = 'Y' and sysdate >= begin_date and sysdate < end_date + 1 ";
					pstmt3 = con.prepareStatement(sql);
					pstmt3.setLong(1, stepMst.getId());
					rs3 = pstmt3.executeQuery();
					while (rs3.next()) {
						ExpExchangeStepDtl stepDtl = new ExpExchangeStepDtl();
						stepDtl.setId(rs3.getLong("id"));
						stepDtl.setStepType(rs3.getString("type"));
						stepDtl.setNo(rs3.getString("no"));
						stepDtl.setBeginDate(rs3.getDate("begin_date"));
						stepDtl.setEndDate(rs3.getDate("end_date"));
						stepDtl.setOrderRequire(rs3.getDouble("order_require"));
						stepDtl.setAddMoney(rs3.getDouble("add_money"));
						stepDtl.setStatus(rs3.getString("status"));
						stepDtl.setStepMst(stepMst);
						// 如果类型是P-礼包，加载礼包明细
						ExpExchangePackageMst packageMst = null;
						/*if (stepDtl.getStepType().equals("P")) {
							Collection packageList = new ArrayList();
							sql = "select * from exp_exchange_package_dtl where package_no = ? and status = 'Y' ";
							pstmt4 = con.prepareStatement(sql);
							pstmt4.setString(1, stepDtl.getNo());
							rs4 = pstmt4.executeQuery();
							packageMst = new ExpExchangePackageMst();
							while (rs4.next()) {
								ExpExchangePackageDtl packageDtl = new ExpExchangePackageDtl();
								packageDtl.setId(rs4.getLong("id"));
								packageDtl
										.setPackageType(rs4.getString("type"));
								packageDtl.setNo(rs4.getString("no"));
								packageDtl.setQuantity(rs4.getInt("quantity"));
								packageDtl.setStatus(rs4.getString("status"));
								if (packageDtl.getPackageType().equals("G")) { // 礼品
									Product product = ProductDAO
											.findBasicByItemCode(con,
													packageDtl.getNo());
									if (product.getItemID() != null) {
										//StockDAO stockDao =new StockDAO();
										//Stock stock = stockDao.loadRecord(con, "000", Integer.parseInt(product.getItemID()));
										//product.setStock(stock);
									}
									packageDtl.setGift(product);
								}
								packageDtl.setMst(packageMst);
								packageList.add(packageDtl);
							}
							rs4.close();
							pstmt4.close();
							packageMst.setDtlList(packageList);
						}*/
						if (stepDtl.getStepType().equals("G")) { // 礼品
							Product product = ProductDAO.findBasicByItemCode(
									con, stepDtl.getNo());
							//if (product.getItemCode() != null) {
								//StockDAO stockDao =new StockDAO();
								//Stock stock = stockDao.loadRecord(con, "000", Integer.parseInt(product.getItemID()));
								//product.setStock(stock);
							//}
							stepDtl.setGift(product);
						}
						stepDtl.setPackMst(packageMst);
						stepDtlList.add(stepDtl);
					}
					rs3.close();
					pstmt3.close();
					stepMst.setDtlList(stepDtlList);
					stepMstList.add(stepMst);
				}
				rs2.close();
				pstmt2.close();
				activity.setMstList(stepMstList);
			}
			rs1.close();
			pstmt1.close();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			if (rs1 != null)
				try {
					rs1.close();
				} catch (Exception e) {
				}
			if (rs2 != null)
				try {
					rs2.close();
				} catch (Exception e) {
				}
			if (rs3 != null)
				try {
					rs3.close();
				} catch (Exception e) {
				}
			if (rs4 != null)
				try {
					rs4.close();
				} catch (Exception e) {
				}
			if (pstmt1 != null)
				try {
					pstmt1.close();
				} catch (Exception e) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (Exception e) {
				}
			if (pstmt3 != null)
				try {
					pstmt3.close();
				} catch (Exception e) {
				}
			if (pstmt4 != null)
				try {
					pstmt4.close();
				} catch (Exception e) {
				}
		}
		return activity;
	}
	
	/**
	 * 查看一个积分活动（包括档次设置）
	 * 
	 * @param con
	 * @throws Exception
	 */
	public static ExpExchangeActivity view(Connection con, ExpExchangeActivityForm param)
			throws Exception {

		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		ExpExchangeActivity activity = new ExpExchangeActivity();

		try {
			String sql = "select * from exp_exchange_activity where activity_no = ?";
			pstmt1 = con.prepareStatement(sql);
			pstmt1.setString(1, param.getActivityNo());
			rs1 = pstmt1.executeQuery();
			if (rs1.next()) {
				Collection stepMstList = new ArrayList();
				activity.setActivityNo(rs1.getString("activity_no"));
				activity.setActivityDesc(rs1.getString("activity_desc"));
				activity.setStatus(rs1.getInt("status"));
				activity.setBeginDate(rs1.getDate("begin_date"));
				activity.setEndDate(rs1.getDate("end_date"));
				activity.setCreatePerson(rs1.getInt("create_person"));
				activity.setCreateDate(rs1.getDate("create_date"));
				activity.setCheckPerson(rs1.getInt("check_person"));
				activity.setCheckDate(rs1.getDate("check_date"));
				activity.setExchangeType(rs1.getString("exchange_type"));
				activity.setDealType(rs1.getString("deal_type"));
				activity.setGiftLastDate(rs1.getDate("gift_last_date"));
				activity.setActivityType(rs1.getInt("type"));// add by user 2008-04-03
				// 根据活动号得到所有有效的积分档次
				sql = "select * from exp_exchange_step_mst where activity_no = ? and status = 'Y' order by begin_exp ";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setString(1, activity.getActivityNo());
				rs2 = pstmt2.executeQuery();
				while (rs2.next()) {
					Collection stepDtlList = new ArrayList();
					ExpExchangeStepMst stepMst = new ExpExchangeStepMst();
					stepMst.setActivity(activity);
					stepMst.setBeginExp(rs2.getInt("begin_exp"));
					stepMst.setId(rs2.getLong("id"));
					if (activity.getStatus() == -2) {
						stepMst.setEnabled(false);
					}
					stepMst.setCreatePerson(rs2.getInt("create_person"));
					stepMst.setCreateDate(rs2.getDate("create_date"));
					stepMst.setStatus(rs2.getString("status"));
					// 根据档次的到所有有效明细
					sql = "select * from exp_exchange_step_dtl where step_id = ? and status = 'Y' ";
					pstmt3 = con.prepareStatement(sql);
					pstmt3.setLong(1, stepMst.getId());
					rs3 = pstmt3.executeQuery();
					while (rs3.next()) {
						ExpExchangeStepDtl stepDtl = new ExpExchangeStepDtl();
						stepDtl.setId(rs3.getLong("id"));
						stepDtl.setStepType(rs3.getString("type"));
						stepDtl.setNo(rs3.getString("no"));
						stepDtl.setBeginDate(rs3.getDate("begin_date"));
						stepDtl.setEndDate(rs3.getDate("end_date"));
						stepDtl.setOrderRequire(rs3.getDouble("order_require"));
						stepDtl.setAddMoney(rs3.getDouble("add_money"));
						stepDtl.setStatus(rs3.getString("status"));
						if ( activity.getStatus() == 0 ) {
							stepDtl.setEnabled(true);
						}
						stepDtl.setStepMst(stepMst);
						
						if (stepDtl.getStepType().equals("G")) { // 礼品
							Product product = ProductDAO.findBasicByItemCode(
									con, stepDtl.getNo());
							stepDtl.setGift(product);
						}
						
						stepDtlList.add(stepDtl);
					}
					rs3.close();
					pstmt3.close();
					stepMst.setDtlList(stepDtlList);
					stepMstList.add(stepMst);
				}
				rs2.close();
				pstmt2.close();
				activity.setMstList(stepMstList);
			}
			rs1.close();
			pstmt1.close();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			if (rs1 != null)
				try {
					rs1.close();
				} catch (Exception e) {
				}
			if (rs2 != null)
				try {
					rs2.close();
				} catch (Exception e) {
				}
			if (rs3 != null)
				try {
					rs3.close();
				} catch (Exception e) {
				}
			if (pstmt1 != null)
				try {
					pstmt1.close();
				} catch (Exception e) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (Exception e) {
				}
			if (pstmt3 != null)
				try {
					pstmt3.close();
				} catch (Exception e) {
				}
			
		}
		return activity;
	}
	
	/**
	 * 查看一个积分活动
	 * 
	 * @param con
	 * @throws Exception
	 */
	public static ExpExchangeActivity viewActivity(Connection con, ExpExchangeActivityForm param)
			throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ExpExchangeActivity activity = new ExpExchangeActivity();

		try {
			String sql = "select * from exp_exchange_activity where activity_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getActivityNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				activity.setActivityNo(rs.getString("activity_no"));
				activity.setActivityDesc(rs.getString("activity_desc"));
				activity.setStatus(rs.getInt("status"));
				activity.setBeginDate(rs.getDate("begin_date"));
				activity.setEndDate(rs.getDate("end_date"));
				activity.setCreatePerson(rs.getInt("create_person"));
				activity.setCreateDate(rs.getDate("create_date"));
				activity.setCheckPerson(rs.getInt("check_person"));
				activity.setCheckDate(rs.getDate("check_date"));
				activity.setExchangeType(rs.getString("exchange_type"));
				activity.setDealType(rs.getString("deal_type"));
				activity.setGiftLastDate(rs.getDate("gift_last_date"));
				activity.setHeadHtml(rs.getString("headhtml"));
				activity.setActivityType(rs.getInt("type"));//add by user 2008-04-03
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
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
		return activity;
	}
}
