/*
 * Created on 2005-2-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.member.entity.*;
import com.magic.crm.user.entity.User;
import com.magic.crm.user.dao.UserDAO;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberInquiryDAO {
		public MemberInquiryDAO(){
			
		}
	   public int insertInquiry(Connection con, MemberInquiry memberInquiry,boolean is_solve ) throws SQLException {
        PreparedStatement pstmt = null;
        int InquieryId = 0;
        ResultSet rs=null;
		try {

	           String addresuserl = "select seq_MBR_INQUIRIES_id.nextval from dual";
	           pstmt = con.prepareStatement(addresuserl);
	           rs = pstmt.executeQuery();
	           if(rs.next()) {
	           	InquieryId = rs.getInt(1);
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


           String sQuery = "INSERT INTO MBR_INQUIRIES(EVENT_ID,SOLVE_METHOD,SOLVE_PERSON,ref_department," +
           				   "IS_SOLVE,INQUIRY_TYPE,INQUIRY_LEVEL,CREATEID,MEMBERID,ROOTID,STATUS,IS_ANSWER) " +
           				   "VALUES(?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";
           pstmt = con.prepareStatement(sQuery);  
           	pstmt.setInt(1, InquieryId);
  			pstmt.setString(2,memberInquiry.getSOLVE_METHOD());
  			pstmt.setString(3,memberInquiry.getSOLVE_PERSON());
  			pstmt.setInt(4,this.getRefDept(con,memberInquiry.getINQUIRY_TYPE()));
  			pstmt.setInt(5,memberInquiry.getIS_SOLVE());
  			pstmt.setInt(6,memberInquiry.getINQUIRY_TYPE());
  			pstmt.setInt(7,memberInquiry.getINQUIRY_LEVEL());
  			if(is_solve){
  			pstmt.setInt(8,memberInquiry.getCREATEID());
  			}else{
  			pstmt.setInt(8,InquieryId);	
  			}
  			pstmt.setString(9,memberInquiry.getMEMBERID());
  			pstmt.setInt(10,memberInquiry.getROOTID());  
  			pstmt.setInt(11,memberInquiry.getStatus());
  			pstmt.setInt(12,memberInquiry.getIS_ANSWER());
  			pstmt.execute();

	         } catch (SQLException e) {
	         	System.out.println("sql is error");
	            throw e;
	         } finally {
	         	if (rs != null)
					try {
						rs.close();
					}catch (Exception e){}
	            if (pstmt != null)
	               try { pstmt.close(); } catch (Exception e) {}
	         }
         		return InquieryId;
         } 
		  /*
		   * 列出所有投诉
		   */
		  public Collection ListInquiry(Connection con,String condition) throws SQLException {
		  	UserDAO userDAO=new UserDAO();
		  	User user=new User();
		       PreparedStatement pstmt = null;
		       ResultSet rs = null;	
		       Collection Inquiry = new ArrayList();
		       try {
		          String sQuery = "SELECT a.*, b.name from MBR_INQUIRIES a,s_inquiry_type b where a.INQUIRY_TYPE=b.id "+condition;
		          pstmt = con.prepareStatement(sQuery);
		          rs = pstmt.executeQuery();
		          while (rs.next()) {
		          	MemberInquiry  info = new MemberInquiry();
		          	
		          	info.setEVENT_ID(rs.getInt("event_id"));
		          	info.setSOLVE_METHOD(rs.getString("SOLVE_METHOD"));
		          	user.setId(rs.getString("SOLVE_PERSON"));
		          	info.setSOLVE_PERSON(userDAO.find(con,user).getUSERID());
		          	info.setref_department(rs.getInt("ref_department"));		          	
		          	info.setSOLVE_DATE(rs.getString("SOLVE_DATE"));
		          	info.setIS_SOLVE(rs.getInt("IS_SOLVE"));
		          	info.setINQUIRY_TYPE(rs.getInt("INQUIRY_TYPE"));
		          	info.setINQUIRY_LEVEL(rs.getInt("INQUIRY_LEVEL"));
		          	info.setCREATEID(rs.getInt("CREATEID"));
		          	info.setMEMBERID(rs.getString("MEMBERID"));
		          	info.setROOTID(rs.getInt("ROOTID"));
		          	info.setStatus(rs.getInt("status"));
		          	info.setInquiryName(rs.getString("name"));
		          	info.setIS_ANSWER(rs.getInt("is_answer"));
		          	
		           	Inquiry.add(info);
		          }

		       } catch (SQLException e) {
		         throw e;
		       } finally {
		          if (rs != null)
		             try { rs.close(); } catch (Exception e) {}
		          if (pstmt != null)
		             try { pstmt.close(); } catch (Exception e) {}
		       }
		       return Inquiry;
		   }	   
		  /*
		   * 列出所有投诉分页数据
		   */
		  public Collection ListInquiry2(Connection con,CommonPageUtil pageModel) throws SQLException {
		  	UserDAO userDAO=new UserDAO();
		  	MemberDAO memberDAO=new MemberDAO();
		  	User user=new User();
		       PreparedStatement pstmt = null;
		       ResultSet rs = null;	
		       ResultSet rec = null;
		       ArrayList Inquiry = new ArrayList();
		       pageModel.setPageSize(50);
		       String card_id = (String) pageModel.getCondition().get("card_id");
		       String type = (String) pageModel.getCondition().get("type");
		       String INQUIRY_TYPE = (String) pageModel.getCondition().get("INQUIRY_TYPE");
		       String solve_date = (String) pageModel.getCondition().get("solve_date");
		       String solve_date2 = (String) pageModel.getCondition().get("solve_date2");
		       String SOLVE_PERSON = (String) pageModel.getCondition().get("SOLVE_PERSON");
		       String deptid = (String) pageModel.getCondition().get("deptid");
		       String IS_SOLVE = (String) pageModel.getCondition().get("IS_SOLVE");
		       String departmentID=(String) pageModel.getCondition().get("departmentID");
			   String IS_ANSWER = (String) pageModel.getCondition().get("IS_ANSWER");
				String condition="";
				/*
				 * 1 客服人员可以看全部投诉和咨询
				 * 2 其他部门只能看各自部门的投诉,看不到咨询(物流部除外)
				 * 3 只显示所有需要回复的投诉和咨询 is_answer=1				 
				 */
				condition=" and b.id=a.INQUIRY_TYPE  and a.rootid=0 " ;
				if(card_id.length()>0){
					condition+="and a.memberid='"+card_id+"'";
				}
				/* 最新规则:所有部门可以查看所有部门和咨询 */
				/* 物流和客服能查询所有投诉和咨询 */
				/*
				if(!departmentID.equals("2")&&!departmentID.equals("6")){
					condition+="and a.REF_DEPARTMENT="+departmentID;	
				}		
				*/		
				/*
				if(IS_SOLVE.equals("1")){
					if(!departmentID.equals("2")){ 
						condition+="and a.REF_DEPARTMENT="+departmentID;	
					}
				}else{
					if(!departmentID.equals("2")&&!departmentID.equals("6")){
						condition+="and a.REF_DEPARTMENT=100";	
					}
				}
				*/
				if(INQUIRY_TYPE.length()>0){
					condition+=" and a.INQUIRY_TYPE="+INQUIRY_TYPE;
				}else{
					/* 按部门查询 */
					if(deptid.length()>0){
						condition+=" and a.REF_DEPARTMENT="+deptid;
					}
				}
				if(SOLVE_PERSON.length()>0){
					condition+=" and a.SOLVE_PERSON="+SOLVE_PERSON;
				}
				if(solve_date.length()>0){
					condition+=" and a.solve_date > to_date('"+solve_date+"','yyyy-mm-dd')";
				}
				if(solve_date2.length()>0){
					condition+=" and a.solve_date < (to_date('"+solve_date2+"','yyyy-mm-dd')+1)";
				}		
				condition+=" and a.is_answer = " + IS_ANSWER;
				condition+="  and a.status=" +type+" and a.is_answer="+IS_SOLVE+" and a.IS_SOLVE = "+IS_SOLVE+" order by a.event_id desc";			
		       
		       /*************************************************************/
		       try {
		       	  String countsQuery = "SELECT count(*) from MBR_INQUIRIES a,s_inquiry_type b where a.INQUIRY_TYPE=b.id "+condition;
					pstmt = con.prepareStatement(countsQuery);
					rec = pstmt.executeQuery();
					int recordCount = 0;
					if (rec.next()) {
						recordCount = rec.getInt(1);
					}
					pageModel.setRecordCount(recordCount);
					rec.close();
					pstmt.close();
		       	  String sQuery = "SELECT a.*, b.name from MBR_INQUIRIES a,s_inquiry_type b where a.INQUIRY_TYPE=b.id "+condition;
		          pstmt = con.prepareStatement(sQuery);
		          //System.out.println(sQuery);
		          rs = pstmt.executeQuery();
		          int recNo=0;
		          while (rs.next()) {
		          	if (recNo >= pageModel.getFrom() && recNo <= pageModel.getTo()) {	
				          	MemberInquiry  info = new MemberInquiry();
				          	
				          	info.setEVENT_ID(rs.getInt("event_id"));
				          	info.setSOLVE_METHOD(rs.getString("SOLVE_METHOD"));
				          	user.setId(rs.getString("SOLVE_PERSON"));
				          	info.setSOLVE_PERSON(userDAO.find(con,user).getNAME());
				          	info.setref_department(rs.getInt("ref_department"));		          	
				          	info.setSOLVE_DATE(rs.getString("SOLVE_DATE"));
				          	info.setIS_SOLVE(rs.getInt("IS_SOLVE"));
				          	info.setINQUIRY_TYPE(rs.getInt("INQUIRY_TYPE"));
				          	info.setINQUIRY_LEVEL(rs.getInt("INQUIRY_LEVEL"));
				          	info.setCREATEID(rs.getInt("CREATEID"));
				          	info.setMEMBERID(memberDAO.getMembers(con,Integer.parseInt(rs.getString("MEMBERID"))).getCARD_ID() );
				          	info.setROOTID(rs.getInt("ROOTID"));
				          	info.setStatus(rs.getInt("status"));
				          	info.setInquiryName(rs.getString("name"));
				          	info.setIS_ANSWER(rs.getInt("is_answer"));
				          	
				           	Inquiry.add(info);
		          	}else if (recNo > pageModel.getTo()) {
						break;
					}
					recNo++;
		          }
		          pageModel.setModelList(Inquiry);
		       } catch (SQLException e) {
		         throw e;
		       } finally {
		          if (rs != null)
		             try { rs.close(); } catch (Exception e) {}
						if (rec != null)
							try {
								rec.close();
							}catch (Exception e){}			             
		          if (pstmt != null)
		             try { pstmt.close(); } catch (Exception e) {}
		       }
		       return Inquiry;
		   }
		  /*
		   * 列出所有投诉类型
		   */
		  public Collection ListInquiryType(Connection con) throws SQLException {
		       PreparedStatement pstmt = null;
		       ResultSet rs = null;	
		       Collection InquiryType = new ArrayList();
		       try {
		          String sQuery = "SELECT * from S_INQUIRY_TYPE order by name desc";
		          pstmt = con.prepareStatement(sQuery);
		          rs = pstmt.executeQuery();
		          while (rs.next()) {
		          	MemberInquiry  info = new MemberInquiry();
		           	info.setInquiryID(rs.getInt("id"));
		           	info.setInquiryName(rs.getString("name"));
					InquiryType.add(info);
		          }
		       } catch (SQLException e) {
		         throw e;
		       } finally {
		          if (rs != null)
		             try { rs.close(); } catch (Exception e) {}
		          if (pstmt != null)
		             try { pstmt.close(); } catch (Exception e) {}
		       }
		       return InquiryType;
		   }
		  
		  /*
		   * 根据投诉类型返回转到部门
		   */
		  public int getRefDept(Connection con,int id) throws SQLException {
		       PreparedStatement pstmt = null;
		       ResultSet rs = null;	
		       int deptID=0;
		       try {
		          String sQuery = "SELECT REF_DEPARTMENT from S_INQUIRY_TYPE where id="+id;
		          pstmt = con.prepareStatement(sQuery);
		          rs = pstmt.executeQuery();
		          if (rs.next()) {
		          	deptID=rs.getInt(1);
					
		          }
		       } catch (SQLException e) {
		         throw e;
		       } finally {
		          if (rs != null)
		             try { rs.close(); } catch (Exception e) {}
		          if (pstmt != null)
		             try { pstmt.close(); } catch (Exception e) {}
		       }
		       return deptID;
		   }	
		  public void updateStatus(Connection con, int status,String createid ) throws SQLException {
	        PreparedStatement pstmt = null;
	        int addressesId = 0;
	        
	        try {
	           String sQuery = "update  MBR_INQUIRIES set status="+status+" where createid="+createid;
	           pstmt = con.prepareStatement(sQuery);          
	           pstmt.execute();
	         } catch (SQLException e) {
	         	System.out.println("sql is error");
	            throw e;
	         } finally {
	            if (pstmt != null)
	               try { pstmt.close(); } catch (Exception e) {}
	         }

	     }  
		  /*
		   * 得到某部门的投诉\咨询类型
		   * status=1投诉
		   * status=0咨询
		   */
		  public Collection getInquiryType(Connection con,String deptid,String status) throws SQLException {
		       PreparedStatement pstmt = null;
		       ResultSet rs = null;	
		       Collection InquiryType = new ArrayList();
		       try {
		          String sQuery = "SELECT * from S_INQUIRY_TYPE where id>0 ";
		          if(status.length()>0){
		          	sQuery+=" and status="+status ;
		          }
		          if(deptid.length()>0){
		          	sQuery+=" and ref_department="+deptid;
		          }
		          sQuery+=" order by RECORD_POSITION asc";
		          pstmt = con.prepareStatement(sQuery);
		          rs = pstmt.executeQuery();
		          while (rs.next()) {
		          	MemberInquiry  info = new MemberInquiry();
		           	info.setInquiryID(rs.getInt("id"));
		           	info.setInquiryName(rs.getString("name"));
					InquiryType.add(info);
		          }
		       } catch (SQLException e) {
		         throw e;
		       } finally {
		          if (rs != null)
		             try { rs.close(); } catch (Exception e) {}
		          if (pstmt != null)
		             try { pstmt.close(); } catch (Exception e) {}
		       }
		       return InquiryType;
		   }		  

		  
}
