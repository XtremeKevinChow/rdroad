package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Order2CRM;
import com.magic.crm.io.MemberGiftExchanger;
import com.magic.crm.util.DBManagerMS;
import com.magic.crm.util.DBManager2;
import java.sql.*;

/**
 * *************************************************************
 * (�����Ѿ�����,ʹ��jobNet2Crm����)
 * *************************************************************
 * ������Ϣ����CRMϵͳ
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class JobOrder2CRM implements Job {
	private static boolean isRun = false;
	private static Logger log = Logger.getLogger(JobOrder2CRM.class);
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				System.out.println("����:JobOrderToCRM�����Ѿ���ִ�У�ȡ���˴�����!");
				return;
			}
			isRun = true;

			/////////////////////////// add zhuxiang 2005-01-13
			// �ڶ�������ϵͳǰ�ȸ��»�Ա������Ʒ��
			/*
			 * System.out.println("-------before update member
			 * gift------------"); Connection conOra =
			 * DBManager2.getConnection(); DBLinkSQLServer dbsql = new
			 * DBLinkSQLServer(); try { MemberGiftExchanger mge = new
			 * MemberGiftExchanger();
			 * mge.initConnection(conOra,dbsql.getConnection()); mge.execute();
			 *  } catch(Exception e) { e.printStackTrace(); } finally {
			 * if(conOra != null) {conOra.close();} if(dbsql != null)
			 * {dbsql.close();} } System.out.println("-------after update member
			 * gift--------------");
			 */
			/////////////////////////////////////end add zhux
			new Order2CRM().execute();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("--execute order to crm error---",e);
		} finally {
			isRun = false;
		}
	}
}