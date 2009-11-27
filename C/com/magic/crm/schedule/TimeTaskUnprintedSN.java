/*
 * Created on 2005-8-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.schedule;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimerTask;

import org.apache.log4j.Logger;


import com.magic.crm.util.DBManager2;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TimeTaskUnprintedSN extends TimerTask{
	private Logger log = Logger.getLogger(TimeTaskUnprintedSN.class);
	/* 需要操作的对象 */
	private SNPrinter2 owner = null;
	/* 传入的连接 */
	private java.sql.Connection conn = null;
	TimeTaskUnprintedSN(SNPrinter2 sn) {
		owner = sn;
		try {
			conn = DBManager2.getConnection();
		} catch(Exception e) {
			log.error("fatal error ",e);
		}
	}

	/**
	 * get information
	 * */
	int qty1=0,qty2=0;
	public void run() {
		Statement st = null;
		try {
		 String sql = "select count(*) as qty,delivery_type "
		 	+ " from ord_shippingnotices "
			+ " where status=0 "
			+ " group by delivery_type" ;
		 st = conn.createStatement();
		 ResultSet rs = st.executeQuery(sql);
		 while(rs.next()) {
		 	int dt = rs.getInt("delivery_type");
		 	int qty = rs.getInt("qty");
		 	if (dt ==1) {
		 		qty1 = qty;
		 	} else if (dt ==3)  {
		 		qty2 = qty;
		 	}
		 }
		 rs.close();
		 //owner.display.asyncExec(this);
		 owner.setDeliveryQry(qty1,qty2);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("",e);
		} finally {
			try { st.close();}catch(Exception e){};
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see java.util.TimerTask#cancel()
	 */
	public boolean cancel() {
		try {conn.close();} catch(Exception e){};
		return super.cancel();

	}

}
