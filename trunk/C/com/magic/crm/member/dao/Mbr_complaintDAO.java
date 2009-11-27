/*
 * Created on 2007-3-1
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
import com.magic.crm.member.form.*;
import com.magic.crm.user.entity.User;
import com.magic.crm.user.dao.UserDAO;
/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mbr_complaintDAO {
	public Mbr_complaintDAO(){
		
	}
	public static void insert(Connection con, Mbr_ComplaintForm mf ) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        try {


           String sQuery = "INSERT INTO mbr_complaint(cmpt_id,event_id,mbr_id,cmpt_type_id,cmpt_content,"
           	+"cmpt_level,cmpt_status,creator,create_date,last_deal_date,is_answer)"
            +"VALUES(seq_mbr_complaint_id.nextval, seq_MBR_events_id.nextval, ?, ?, ?, ?,?,?,sysdate,sysdate,?)";
           pstmt = con.prepareStatement(sQuery);  
           	pstmt.setInt(1,mf.getMbr_id());
           	if(mf.getCmpt_type_id()==0){
  			pstmt.setString(2,mf.getParent_id());
           	}else{
           		pstmt.setInt(2,mf.getCmpt_type_id());	
           	}
  			pstmt.setString(3,mf.getCmpt_content());
  			pstmt.setInt(4,2);//这个客服已经要求不需要，又不能改数据库结构， 所以把默认值2插到数据库里。
  			if(mf.getIs_answer()==0){//不需要回复
  				pstmt.setInt(5,1);
  			}else{
  				pstmt.setInt(5,mf.getCmpt_status());	  				
  			}
  			pstmt.setInt(6,mf.getCreator());
  			pstmt.setInt(7,mf.getIs_answer());
  		
  		
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
         } 
	public static void insertDeal(Connection con, Mbr_ComplaintForm mf ) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        try {

           con.setAutoCommit(false);
           String sQuery = "INSERT INTO mbr_complaint_deal(id,cmpt_id,deal_content,creator,create_date)"
            +"VALUES(seq_mbr_complaint_deal_id.nextval, ?,?, ?,sysdate)";
           pstmt = con.prepareStatement(sQuery); 
           pstmt.setInt(1,mf.getEvent_id());
           	pstmt.setString(2,mf.getCmpt_content());
  			pstmt.setInt(3,mf.getCreator());
  			pstmt.execute();
  			pstmt.close();
            String update_sql="update mbr_complaint set cmpt_status=?,last_deal_date=sysdate where event_id="+mf.getEvent_id();
            pstmt = con.prepareStatement(update_sql); 

           	pstmt.setInt(1,mf.getCmpt_status());
  			pstmt.execute();        
  		    con.commit();
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
         } 
	  /*
	   * 列出所有投诉类型
	   */
	  public Collection getCmpt_type(Connection con,int type) throws SQLException {
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;	
	       Collection complaintType = new ArrayList();
	       String sQuery="";
	       try {
	           
		       if(type==1){
		       	sQuery="select * from complaint_type where type=1 and status=0 start with parent_id=0 connect by parent_id=prior cmpt_type_id";
 			}else{
 				sQuery="select a.cmpt_type_name,a.parent_id,a.Cmpt_type_id from complaint_type a, dept_complaint b where a.type=0 and a.status=0";
 				sQuery+=" and a.cmpt_type_id=b.cmpt_type_id";
 				sQuery+=" start with parent_id=0 connect by parent_id=prior a.cmpt_type_id";			
 			}	          
	          
	          
	          pstmt = con.prepareStatement(sQuery);
	          rs = pstmt.executeQuery(sQuery);
	          //System.out.println();
	          while (rs.next()) {
	          	Mbr_ComplaintForm  info = new Mbr_ComplaintForm();
	          	if(rs.getInt("parent_id")==0){
	          		info.setCmpt_type_name("--------"+rs.getString("Cmpt_type_name"));
	          	}else{
	          		info.setCmpt_type_name(rs.getString("Cmpt_type_name"));	          	
	          	}
	           	info.setCmpt_type_id(rs.getInt("Cmpt_type_id"));
	           	
	           	complaintType.add(info);
	          }
	       } catch (SQLException e) {
	         throw e;
	       } finally {
	          if (rs != null)
	             try { rs.close(); } catch (Exception e) {}
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	       return complaintType;
	   }
	  /*
	   * 列出所有投诉
	   */
	  public Collection ListComplaint(Connection con,String condition,String type) throws SQLException {
	  	UserDAO userDAO=new UserDAO();
	  	User user=new User();
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;	
	       Collection complaint = new ArrayList();
	       String sQuery ="";
	       try {
	       	if(type.equals("0")){
	       		
	       		sQuery="SELECT a.*, b.dept_id,c.cmpt_type_name,c.type from mbr_complaint a,Dept_Complaint b,complaint_type c "
	          	+" where a.cmpt_type_id=b.cmpt_type_id and  c.cmpt_type_id=b.cmpt_type_id "+condition;
	       	}else{
	       		sQuery="SELECT a.*, c.cmpt_type_name,c.type from mbr_complaint a, complaint_type c "
		          	+" where a.cmpt_type_id=c.cmpt_type_id  "+condition;	       		
	       	}
	          pstmt = con.prepareStatement(sQuery);
	          //System.out.println(sQuery);
	          rs = pstmt.executeQuery();
	          while (rs.next()) {
	          	Mbr_Complaint  info = new Mbr_Complaint();
	          	

	          	info.setEvent_id(rs.getInt("event_id"));
	          	info.setCmpt_content(rs.getString("cmpt_content"));
	          	user.setId(rs.getString("creator"));
	          	info.setCreatorName(userDAO.find(con,user).getNAME());
	          	if(type.equals("0")){
	          	info.setDeptID(rs.getInt("dept_id"));
	          	}else{
	          		info.setDeptID(0);	
	          	}
	          	info.setCmpt_type_name(rs.getString("cmpt_type_name"));
	          	info.setCmpt_type_id(rs.getInt("cmpt_type_id"));
	          	info.setCmpt_level(rs.getInt("Cmpt_level"));
	          	info.setCreator(rs.getInt("Creator"));
	          	info.setMbr_id(rs.getInt("Mbr_id"));
		        info.setCmpt_status(rs.getInt("Cmpt_status"));
	          	info.setCreate_date(rs.getString("create_date").substring(0,10));
	          	info.setLast_deal_date(rs.getString("Last_deal_date"));
	          	info.setCmpt_id(rs.getInt("Cmpt_id"));
	          	info.setIs_answer(rs.getInt("is_answer"));
	          	info.setType(rs.getInt("type"));
	          	
	          	complaint.add(info);
	          }

	       } catch (SQLException e) {
	         throw e;
	       } finally {
	          if (rs != null)
	             try { rs.close(); } catch (Exception e) {}
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	       return complaint;
	   }	 
	    
	  /*
	   * 列出所有投诉解决记录
	   */
	  public Collection ListComplaintDeal(Connection con,int cmpt_id) throws SQLException {
	  	UserDAO userDAO=new UserDAO();
	  	User user=new User();
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;	
	       Collection complaint = new ArrayList();
	       try {
	          String sQuery = "SELECT * from mbr_complaint_deal where cmpt_id="+cmpt_id;
	          //System.out.println(sQuery);
	          pstmt = con.prepareStatement(sQuery);
	          rs = pstmt.executeQuery();
	          while (rs.next()) {
	          	Mbr_Complaint  info = new Mbr_Complaint();	
	  
	          	info.setCmpt_content(rs.getString("deal_content"));
	          	user.setId(rs.getString("creator"));
	          	info.setCreatorName(userDAO.find(con,user).getNAME());
	          	info.setCreator(rs.getInt("Creator"));
	          	info.setCreate_date(rs.getString("create_date"));	     
	          	info.setCmpt_id(rs.getInt("Cmpt_id"));	
	          	
	          	complaint.add(info);
	          }

	       } catch (SQLException e) {
	         throw e;
	       } finally {
	          if (rs != null)
	             try { rs.close(); } catch (Exception e) {}
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	       return complaint;
	   }	  
	  /*
	   * 列出所有投诉分页数据
	   */
	  public Collection ListComplaintFY(Connection con,CommonPageUtil pageModel) throws SQLException {
	  	UserDAO userDAO=new UserDAO();
	  	MemberDAO memberDAO=new MemberDAO();
	  	User user=new User();
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;	
	       ResultSet rec = null;

	       ArrayList complaint = new ArrayList();
	       pageModel.setPageSize(50);
	       String card_id = (String) pageModel.getCondition().get("card_id");
	       String type = (String) pageModel.getCondition().get("type");
	       String cmpt_type_id = (String) pageModel.getCondition().get("cmpt_type_id");
	       String parent_id = (String) pageModel.getCondition().get("parent_id");
	       String solve_date = (String) pageModel.getCondition().get("solve_date");
	       String solve_date2 = (String) pageModel.getCondition().get("solve_date2");
	       String creator = (String) pageModel.getCondition().get("creator");

	       String status = (String) pageModel.getCondition().get("status");
	       String dept_id = (String) pageModel.getCondition().get("dept_id");//判断是否客服部门
		   //String is_answer = (String) pageModel.getCondition().get("is_answer");
			String condition="";
			/*
			 * 1 客服人员可以看全部投诉和咨询
			 * 2 其他部门只能看各自部门的投诉和咨询
			 */

			if(card_id.length()>0){
				condition+="and a.mbr_id="+card_id+"";//这里的card_id已经转换成ID
			}

			if(creator.length()>0&&Integer.parseInt(creator)>0){
				condition+=" and a.creator="+creator;
			}
			if(solve_date.length()>0){
				condition+=" and a.create_date > to_date('"+solve_date+"','yyyy-mm-dd')";
			}
			if(solve_date2.length()>0){
				condition+=" and a.create_date < (to_date('"+solve_date2+"','yyyy-mm-dd')+1)";
			}		
			//condition+=" and a.is_answer = " + is_answer;
			//if(Integer.parseInt(dept_id)!=2&&type.equals("0")){
			//condition+=" and b.dept_id="+dept_id;
			//}
			if(status.length()>0){
				if(Integer.parseInt(status)==0){
					condition+=" and a.cmpt_status in (0,2)" ;
				}else{
					condition+=" and a.cmpt_status=" +status;
				}
			}
			condition+=" and c.type = " + type;
			if(parent_id.length()>0 &&!parent_id.equals("10000")){
				if(cmpt_type_id.length()>0&&Integer.parseInt(cmpt_type_id)>0){
					condition+=" and a.cmpt_type_id = "+ cmpt_type_id;
				}else{
					condition+=" and a.cmpt_type_id = "+ parent_id;	
				}
			}			
			condition+="  order by a.cmpt_id desc";			
			 //System.out.println(condition);
	       /*************************************************************/
			String countsQuery ="";
			String sQuery="";
	       try {
	       	//if(type.equals("0")){
	       	//   countsQuery = "SELECT /*+index(a IX_MBR_COMPLAINT_CREATEDATE) */ count(*) from mbr_complaint a,Dept_Complaint b,complaint_type c "
	        //  	+" where a.cmpt_type_id=b.cmpt_type_id and  c.cmpt_type_id=b.cmpt_type_id "+condition;
	       //	}else{
		       countsQuery = "SELECT /*+index(a IX_MBR_COMPLAINT_CREATEDATE) */ count(*) from mbr_complaint a,complaint_type c "
		        +" where a.cmpt_type_id=c.cmpt_type_id "+condition;
	       //	}
				pstmt = con.prepareStatement(countsQuery);
				rec = pstmt.executeQuery();
				int recordCount = 0;
				if (rec.next()) {
					recordCount = rec.getInt(1);
				}
				pageModel.setRecordCount(recordCount);
				rec.close();
				pstmt.close();
				//if(type.equals("0")){
	       		//sQuery="SELECT /*+index(a IX_MBR_COMPLAINT_CREATEDATE) */ a.*, b.dept_id,c.cmpt_type_name,c.type from mbr_complaint a,Dept_Complaint b,complaint_type c "
		        //  	+" where a.cmpt_type_id=b.cmpt_type_id and  c.cmpt_type_id=b.cmpt_type_id "+condition;
		       	//}else{
		       	sQuery="SELECT /*+index(a IX_MBR_COMPLAINT_CREATEDATE) */ a.*,c.cmpt_type_name,c.type from mbr_complaint a,complaint_type c "
			         +" where a.cmpt_type_id=c.cmpt_type_id "+condition;
			   //}	       		
	          pstmt = con.prepareStatement(sQuery);
	          //System.out.println(sQuery);
	          
	          rs = pstmt.executeQuery();
	          int recNo=0;
	          while (rs.next()) {
	          	if (recNo >= pageModel.getFrom() && recNo <= pageModel.getTo()) {	
		          	Mbr_Complaint  info = new Mbr_Complaint();
		          	
		          	info.setEvent_id(rs.getInt("event_id"));
		          	info.setCmpt_content(rs.getString("cmpt_content"));
		          	user.setId(rs.getString("creator"));
		          	if(userDAO.find(con,user)!=null){
		          	info.setCreatorName(userDAO.find(con,user).getNAME());		          	
		          	}else{
		          		info.setCreatorName("");
		          	}
		          	//if(type.equals("0")){
			          	//info.setDeptID(rs.getInt("dept_id"));
			          	//}else{
			         info.setDeptID(0);	
			         //}		          	
		          	
		          	info.setCmpt_type_name(rs.getString("cmpt_type_name"));
		          	info.setCmpt_type_id(rs.getInt("cmpt_type_id"));
		          	info.setCmpt_level(rs.getInt("Cmpt_level"));
		          	info.setCreator(rs.getInt("Creator"));
		          	info.setMbr_id(rs.getInt("Mbr_id"));
			        info.setCmpt_status(rs.getInt("Cmpt_status"));
		          	info.setCreate_date(rs.getString("create_date"));
		          	info.setLast_deal_date(rs.getString("Last_deal_date"));
		          	
		          	info.setCmpt_id(rs.getInt("Cmpt_id"));
		          	info.setIs_answer(rs.getInt("is_answer"));
		          	info.setType(rs.getInt("type"));
		          	info.setCard_id(memberDAO.getCard_ID(con,rs.getInt("Mbr_id")));
		          	complaint.add(info);
	          	}else if (recNo > pageModel.getTo()) {
					break;
				}
				recNo++;
	          }
	          pageModel.setModelList(complaint);
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
	       return complaint;
	   }
	  public static int getDeptID(Connection con, String cmpt_type_id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        int dept_id=0;
        try {

           con.setAutoCommit(false);
           String sQuery = "select dept_id from dept_complaint where cmpt_type_id="+cmpt_type_id;
           pstmt = con.prepareStatement(sQuery); 
	          rs = pstmt.executeQuery();
	          if (rs.next()) {	
	          	dept_id=rs.getInt("dept_id");
	          }
           return dept_id;
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
         } 	 
	  
	  /*
	   * 列出所有大类类型
	   */
	  public Collection getSupperClass(Connection con,int type) throws SQLException {
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;	
	       Collection complaintType = new ArrayList();
	       String sQuery="";
	       try {
	           
	       	
			    if(type==1){//咨询大类类型
			       	sQuery="select * from complaint_type where type=1 and status=0 and parent_id=0 order by cmpt_type_id";
	 			}else{
	 				sQuery="select a.*,b.dept_id from complaint_type a left join dept_complaint b on  a.cmpt_type_id=b.cmpt_type_id "; 
	 					sQuery+=" where a.type=0 and a.status=0 and a.parent_id=0 order by b.sort_order,a.cmpt_type_id";
	 			}	
	          
	          pstmt = con.prepareStatement(sQuery);
	          rs = pstmt.executeQuery();
	          //System.out.println();
	          while (rs.next()) {
	          	Mbr_ComplaintForm  info = new Mbr_ComplaintForm();

	          	info.setCmpt_type_name(rs.getString("Cmpt_type_name"));	          	
	          
	           	info.setCmpt_type_id(rs.getInt("Cmpt_type_id"));
	           	
	           	complaintType.add(info);
	          }
	       } catch (SQLException e) {
	         throw e;
	       } finally {
	          if (rs != null)
	             try { rs.close(); } catch (Exception e) {}
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	       return complaintType;
	   }	  
	  /*
	   * 列出所有小类类型
	   */
	  public Collection getSunClass(Connection con,int parent_id) throws SQLException {
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;	
	       Collection complaintType = new ArrayList();
	       String sQuery="";
	       try {
	           
	 		 sQuery="select * from complaint_type where  status=0 and parent_id = ?";
	 			
	          
	          pstmt = con.prepareStatement(sQuery);
	          pstmt.setInt(1, parent_id);
	          rs = pstmt.executeQuery();
	          //System.out.println();
	          while (rs.next()) {
	          	Mbr_ComplaintForm  info = new Mbr_ComplaintForm();

	          	info.setCmpt_type_name(rs.getString("Cmpt_type_name"));	          	
	          
	           	info.setCmpt_type_id(rs.getInt("Cmpt_type_id"));
	           	
	           	complaintType.add(info);
	          }
	       } catch (SQLException e) {
	         throw e;
	       } finally {
	          if (rs != null)
	             try { rs.close(); } catch (Exception e) {}
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	       return complaintType;
	   }		  
}
