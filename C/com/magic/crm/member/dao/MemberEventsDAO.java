/*
 * Created on 2005-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.member.entity.*;
import com.magic.crm.user.dao.*;
import com.magic.crm.util.KeyValue;


/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberEventsDAO {
	   public BigDecimal insert(Connection con, MemberEvents memberEvents,int addEvents ) throws SQLException {
        PreparedStatement pstmt = null;
        BigDecimal eventsId = null;
        ResultSet rs = null;
		try {
	           String eventuserl = "select seq_mbr_events_id.nextval from dual";
	           pstmt = con.prepareStatement(eventuserl);
	           rs = pstmt.executeQuery();
	           if(rs.next()) {
	           	eventsId = rs.getBigDecimal(1);
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
	           String sQuery = "INSERT INTO MBR_EVENTS(ID,member_id,EVENT_DATE,REF_DOC_ID,EVENT_TYPE,COMMENTS,OPERATOR_ID) VALUES(?, ?, ?, ?, ?,?,?)";
	           pstmt = con.prepareStatement(sQuery); 
	           
	           	pstmt.setBigDecimal(1, eventsId);
	  			pstmt.setInt(2,memberEvents.getMEMBER_ID());
	  			pstmt.setDate(3,Date.valueOf(memberEvents.getEVENT_DATE()));
	  			pstmt.setInt(4,memberEvents.getREF_DOC_ID());
	  			pstmt.setInt(5,memberEvents.getEVENT_TYPE());  	
	  			pstmt.setString(6,memberEvents.getCOMMENTS()); 
	  			pstmt.setInt(7,memberEvents.getOPERATOR_ID());
	           pstmt.execute();

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
        	

	         //	insert into MBR_GET_MBR	
           String sQuer = "INSERT INTO MBR_GET_MBR(EVENT_ID,member_id,status,RECOMMENDED_ID,GIFT_ID) VALUES(?,?, ?, ?, ?)";
           pstmt = con.prepareStatement(sQuer);          
           pstmt.setBigDecimal(1, eventsId);
  			pstmt.setInt(2,memberEvents.getMEMBER_ID());//推荐人
  			pstmt.setInt(3,10);
  			pstmt.setInt(4,memberEvents.getMemgetMemID());//被推荐人
  			pstmt.setInt(5,memberEvents.getGift_ID());
  			if(addEvents==1){
  				pstmt.execute();
  			}

         } catch (SQLException e) {
         	System.out.println("INSERT INTO MBR_GET_MBR(事件表) is error");
            throw e;
         } finally {
         	if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
            if (pstmt != null)
               try { pstmt.close(); } catch (Exception e) {}
         }
         return eventsId;
     }  
	   /* 所有事件类型 */
	   public static ArrayList getEventTypeList(Connection conn)throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList typelist=new ArrayList();
        Member member = new Member();
	   	try{
	   		String sql="select * from s_event_type ";
	   		pstmt=conn.prepareStatement(sql);
	   		rs=pstmt.executeQuery();
	   		while(rs.next()){
	   			KeyValue keyvalue = new KeyValue();
	   			keyvalue.setId(rs.getInt("id"));
	   			keyvalue.setName(rs.getString("name"));
	   			typelist.add(keyvalue);
	   		}
	   	}catch(SQLException e){
	   		e.printStackTrace();
	   	}
	   	return typelist;
	   }
	   /* 列出所有会员事件 */
	   public Collection QueryMemberEvents(Connection con,CommonPageUtil pageModel) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList memberCol = new ArrayList();
		pageModel.setPageSize(50);
		String card_id = (String) pageModel.getCondition().get("card_id");
		String name = (String) pageModel.getCondition().get("name");
		String event_type = (String) pageModel.getCondition().get("event_type");
		String begin_date = (String) pageModel.getCondition().get("begin_date");
		String end_date = (String) pageModel.getCondition().get("end_date");
		String condition="";
		try {
			if(card_id.length()>0){
				condition+=" and a.member_id ="+MemberDAO.getMemberID(con,card_id);
			}
			if(name.length()>0){
				condition+=" and b.name ='"+name+"'";
			}
			if(begin_date.length()>0){
				condition+=" and a.event_date  >=date '"+begin_date+"'";
			}
			if(end_date.length()>0){
				condition+=" and a.event_date  < date '"+end_date+"'+1";
			}			
			if(event_type.length()>0){
				condition+=" and a.event_type="+event_type;
			}	
			
			String countsql=" select count(*) from mbr_events a,mbr_members b  " +
							" where a.member_id=b.id   "+condition;
			pstmt = con.prepareStatement(countsql);
			//System.out.println(countsql);
			rs=pstmt.executeQuery();
			int rsCount = 0;
			if (rs.next()) {
				rsCount=rs.getInt(1);
			}
			pageModel.setRecordCount(rsCount);
			rs.close();
			pstmt.close();

			String sQuery = " select b.id ,b.name,b.card_id,a.id as event_id ,a.event_date,a.operator_id,a.comments,c.name as event_name " +
							" from mbr_events a,mbr_members b ,s_event_type c " +
							" where a.member_id=b.id and a.event_type=c.id  "+condition;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
			int recNo=0;
			while (rs.next()) {
				if (recNo >= pageModel.getFrom() && recNo <= pageModel.getTo()) {					
					Member member = new Member();
					/* 会员ID */
					member.setID(rs.getInt("id"));
					/* 事件编号 */
					member.setCATEGORY_ID(rs.getInt("event_id"));
					/* 会员号码 */
					member.setCARD_ID(rs.getString("card_id"));
					/* 会员姓名 */
					member.setNAME(rs.getString("name"));
					/* 事件类型 */
					member.setAddress1(rs.getString("event_name"));
					/* 事件日期 */
					member.setAddress(rs.getString("event_date").substring(0,10));
					/* 经办人员 */
					member.setADDRESS_ID(UserDAO.getPerson(con,rs.getInt("operator_id")));
					/* 备注 */
					member.setAddressDetail(rs.getString("comments"));
	
					memberCol.add(member);
				}else if (recNo > pageModel.getTo()) {
					break;
				}
				recNo++;				
			}
			pageModel.setModelList(memberCol);
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
		return memberCol;
	}	   
}
