package com.magic.crm.schedule;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import oracle.xml.parser.v2.DOMParser;

import org.apache.log4j.PropertyConfigurator;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.magic.utils.StringUtil;

//import org.jdom.*;
/**
 * ��̨�������̨Java
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */

public class MainFrame extends JFrame implements ActionListener,
		ListSelectionListener {

	private static int job_id = 0;

	private static Scheduler sched;

	// �������
	/**
	 * main ����
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create Frame class`s Object
		MainFrame mainframe = new MainFrame("������̹������");
		//���ô����ʱ��������رմ����¼���
		mainframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowevent) {
				// �����˳� Java �����
				System.exit(0);
			}
		});
		// ��ʾ������***********************
		mainframe.pack();
		mainframe.show();
	}

	JPanel add_button = new JPanel();

	// Creat Panel
	JPanel add_label = new JPanel();

	JPanel add_textarea = new JPanel();

	//Creat ScheduleService Object
	//ScheduleService service;
	// Create Label
	JLabel display_service = new JLabel("���з��������Ϣ��", Label.LEFT);

	final String GROUP_NAME = "magic";

	private Vector jobs = new Vector();

	JButton no_service = new JButton("��ֹ����");

	//JButton see_service = new JButton("�鿴��־");
	JButton now_service = new JButton("����һ��");

	JButton plash_service = new JButton("�������");

	final int ROW_COUNT = 40;

	JButton run_service = new JButton("���з���");

	String strTableData[][];

	// Create Table Display All Schedule Servise
	String strTableTitle[] = { "����ID", "��������", "��������", "����״̬", "�ϴ�����ʱ��",
			"�´�����ʱ��" };

	// Creat Servise Control Button
	JButton suspension_service = new JButton("��ͣ����");

	JTable table;

	JPanel table_panel = new JPanel();

	JTextArea textarea = new JTextArea(" >>> ����" + "\n" + " ������̹���ϵͳ��������...",
			13, 30);

	public MainFrame() {

	}

	//���̷������

	// Construct the Frame
	/**
	 * ����Frame
	 * 
	 * @param setFrameTitle
	 */
	public MainFrame(String setFrameTitle) {

		super(setFrameTitle);
		loadProperties();
		display_service.setText("���з��������Ϣ");

		// �ڹ��캯������� JTable �Ķ���
		strTableData = new String[ROW_COUNT][6];
		table = new JTable(strTableData, strTableTitle);
		DefaultTableModel dtm;
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(false);
		//�����������.������ȥ ��addComponent -->JPanel -->JFrame
		add_label.add(display_service); // ��� ��ʾ���з�����Ϣ ��ǩ
		add_button.add(suspension_service); // ��� ��ͣ���� ��ť
		add_button.add(run_service); // ��� ���з��� ��ť
		//add_button.add(no_service); // ��� ��ֹ���� ��ť
		//add_button.add(plash_service); // ��� ������� ��ť
		//add_button.add(see_service); // ��� �鿴���������־ ��ť
		add_button.add(now_service); // ��� ���ĵ��� ��ť

		// �ڴ���������ϰ����˱�ǩ�����Ͱ�ť��Щ�����������add_label��add_table��add_button
		this.getContentPane().add(add_label, BorderLayout.NORTH); // 						 
		this.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER); // 
		//this.getContentPane().add(new
		// JScrollPane(textarea),BorderLayout.WEST); //
		this.getContentPane().add(add_button, BorderLayout.SOUTH);

		//��Ӽ���
		suspension_service.addActionListener(this);
		run_service.addActionListener(this);
		//no_service.addActionListener(this);
		//plash_service.addActionListener(this);
		//see_service.addActionListener(this);
		now_service.addActionListener(this);
		//table.addListSelectionListener(this); // ListSelectionListener

		setSize(800, 400);
		setVisible(true);
		setResizable(true);

		/*
		 * �ڴ˼ӽ� ScheduleMain.java �еĴ���
		 */

		initTable();
		try {
			loadServices();
		} catch (Exception e) {
			System.out.println(e);
		}
		int delay = 1000; //milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				refreshTable();
			}
		};
		new javax.swing.Timer(delay, taskPerformer).start();
		/*
		 * TimerTask task = new TimerTask() { public void run() {
		 * refreshTableRow(); } };
		 * 
		 * java.util.Timer timer = new java.util.Timer(); timer.schedule(task,
		 * 0, 1500);
		 */
		//timer.cancel();
	}

	// ����ť�¼�
	/**
	 * ����ť�¼�
	 * 
	 * @param actionevent
	 */
	public void actionPerformed(ActionEvent actionevent) {
		// ������ ����ͣ���̷��� ��ť�ϵ��¼�����
		if (actionevent.getSource() == suspension_service) {
			if (getSelectedJobID().equals(""))
				return;
			int i = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ��ͣ���������",
					"��ͣ�������", JOptionPane.INFORMATION_MESSAGE);
			if (i == 0) {
				try {
					sched.pauseJob(getSelectedJobID(), GROUP_NAME);
					updateTableRowStatus(getSelectedJobID(), "��ͣ");
					//System.out.println(getSelectedJobID());
				} catch (Exception e) {
				    
					e.printStackTrace();
				}
			}
		}
		//������ �����н��̷��� ��ť�ϵ��¼�����
		if (actionevent.getSource() == run_service) {
			if (getSelectedJobID().equals(""))
				return;
			int i = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ���и��������",
					"���з������", JOptionPane.INFORMATION_MESSAGE);
			if (i == 0) {
				try {
					sched.resumeJob(getSelectedJobID(), GROUP_NAME);
					updateTableRowStatus(getSelectedJobID(), "��Ч");
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		//������ ����ֹ���̷��� ��ť�ϵ��¼�����
		//        if(actionevent.getSource() == no_service)
		//		{
		//			 String id=getSelectedJobID();
		//       if(id.equals("")) return;
		//     	 int
		// i=JOptionPane.showConfirmDialog(null,"��ȷ��Ҫ��ֹ���������","��ֹ�������",JOptionPane.INFORMATION_MESSAGE);
		//       if(i==0 )
		//       {
		//         try
		//         {
		//           dblink.executeUpdate("UPDATE BAS_JOBS SET STATUS = -1 where
		// id="+getSelectedJobID());
		//           sched.unscheduleJob(id, GROUP_NAME);
		//           sched.deleteJob(id, GROUP_NAME);
		//           updateTableRowStatus(getSelectedJobID(),"��Ч");
		//         }catch(Exception e)
		//         {
		//           e.printStackTrace();
		//         }
		//       }
		//      
		//		}
		//		 //������ ��������̷��� ��ť�ϵ��¼�����plash_servise
		//    if(actionevent.getSource() == plash_service)
		//		{
		//			 String id=getSelectedJobID();
		//       if(id.equals("")) return;
		//     	 int
		// i=JOptionPane.showConfirmDialog(null,"��ȷ��Ҫ������������","����������",JOptionPane.INFORMATION_MESSAGE);
		//       if(i==0 )
		//       {
		//         try
		//         {
		//           dblink.executeUpdate("UPDATE BAS_JOBS SET STATUS = 0 where
		// id="+getSelectedJobID());
		//           stmt=dblink.createStatement();rs= stmt.executeQuery("SElECT * FROM
		// BAS_JOBS WHERE id="+getSelectedJobID());
		//           rs.next();
		//				  String description=rs.getString("DESCRIPTION");
		//				  String execute_month=rs.getString("EXECUTE_MONTH");
		//				  String execute_day=rs.getString("EXECUTE_DAY");
		//				  int execute_week=rs.getInt("EXECUTE_WEEK");
		//				  int execute_hour=rs.getInt("EXECUTE_HOUR");
		//				  int execute_miniute=rs.getInt("EXECUTE_MINIUTE");
		//				  int execute_interval_seconds=rs.getInt("EXECUTE_INTERVAL_SECONDS");
		//				  int status=rs.getInt("STATUS");
		//				  String class_name=rs.getString("CLASS_NAME");
		//				  String log_file_name=rs.getString("LOG_FILE_NAME");
		//				  String name=rs.getString("NAME");
		//				  int schedule_type=rs.getInt("SCHEDULE_TYPE");
		//        
		//				//**************************
		//
		//         JobDetail job = new JobDetail(id+"", GROUP_NAME,
		// Class.forName(class_name));
		//				 Trigger trigger =null;
		//				// �жϷ�����̵������ͣ�
		//			    // ���ֶ� SCHEDULE_TYPE==0��Ϊ�򵥵������ͣ���magic_SCHEDULE_TYPE==1����Ϊ���ӵ�������
		//				 if(schedule_type==0)
		//				 {
		//					 trigger = new SimpleTrigger(id+"", //
		//															   GROUP_NAME, //
		//															   new Date(),// �����������ʱ��
		//															   null, //��ֹ������̵�ʱ��Ϊ��
		//															   SimpleTrigger.REPEAT_INDEFINITELY,//
		//															   execute_interval_seconds*1000L);//���ÿ�����ظ�ִ��һ��
		//				}
		//				else
		//				{
		//					// ���ܵ���
		//					if(execute_week!=0)
		//					{
		//						// �жϰ�����ʱ������������̣�
		//						// ��ֵ�� 0--7 ֮�䣬��Ϊ 0 ���� execute_month==0 �� magic_execute_day==0
		//						String s="0 "+execute_miniute+" "+execute_hour+" * * "+execute_week;
		//						trigger = new CronTrigger(""+id,GROUP_NAME,s);
		//					}
		//					else
		//					{
		//						if(execute_day.equals("0")) execute_day="*";
		//						if(execute_month.equals("0")) execute_month="*";
		//						String s="0 "+execute_miniute+" "+execute_hour+" "+execute_day+"
		// "+execute_month+" ?";
		//						trigger = new CronTrigger(""+id,GROUP_NAME,s);
		//					}
		//				}
		//				   sched.scheduleJob(job, trigger);
		//           updateTableRowStatus(getSelectedJobID(),"��Ч");
		//           refreshTable();
		//         }catch(Exception e)
		//         {
		//           System.out.println(e);
		//         }
		//       }
		//		}
		// ������ ���鿴������־�� ��ť�ϵ��¼�����
		/*
		 * if(actionevent.getSource() == see_service) { // �������� JTable
		 * ��������ı���������ʾ��������̵ĵ���
		 * JOptionPane.showMessageDialog(null,"�鿴���������־..","�鿴���������־",JOptionPane.INFORMATION_MESSAGE); }
		 */
		// ������ ���������С� ��ť�ϵ��¼�����
		if (actionevent.getSource() == now_service) {
			String id = getSelectedJobID();
			if (id.equals(""))
				return;
			int i = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�������з��������",
					"�������з������", JOptionPane.INFORMATION_MESSAGE);
			if (i == 0) {
				try {

					JobElement je = (JobElement) jobs.get(Integer.parseInt(id));
					JobDetail job = new JobDetail("" + (job_id++), GROUP_NAME
							+ "_NOW", Class.forName(je.getClassName()));
					Trigger trigger = new SimpleTrigger("" + (job_id++), //
							GROUP_NAME + "_NOW", //
							new Date(),// �����������ʱ��
							new Date(), //��ֹ������̵�ʱ��Ϊ��
							SimpleTrigger.REPEAT_INDEFINITELY,//
							1000L);//���ÿ�����ظ�ִ��һ��
					sched.scheduleJob(job, trigger);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			//JOptionPane.showMessageDialog(null,"��ȷ��Ҫ���Ľ��̵�����","���Ľ��̵���",JOptionPane.INFORMATION_MESSAGE);
			//ScheduleService service = new ScheduleService("������̵��ȶ���");
			//service.pack();
		}

	}

	private String getSelectedJobID() {
		ListSelectionModel rowSM = table.getSelectionModel();
		if (rowSM == null)
			return "";
		System.out.println(strTableData[rowSM.getMaxSelectionIndex()][0]);
		return (String) strTableData[rowSM.getMaxSelectionIndex()][0];
	}

	private String getTableRowStatus(String id) {
		for (int i = 0; i <= ROW_COUNT; i++) {
			//System.out.println(strTableData[i][1]);
			if (StringUtil.cEmpty(strTableData[i][0]).equals(id))
				return strTableData[i][3];
		}
		return "";
	}

	/**
	 * ��ʼ���������
	 */
	private void initTable() {

		for (int i = 0; i < jobs.size(); i++) {
			JobElement je = (JobElement) jobs.get(i);
			strTableData[i][0] = i + "";
			strTableData[i][1] = je.getName();
			strTableData[i][2] = je.getDescription();
			if (je.getStatus() >= 0)
				strTableData[i][3] = "��Ч";
			else
				strTableData[i][3] = "��Ч";
			strTableData[i][4] = "";
			strTableData[i][5] = "";
		}

	}

	/**
	 * ��XML�ж�ȡ��������
	 */
	private void loadProperties() {
		PropertyConfigurator
				.configure("E:\\crm_service\\service\\schedule\\log4j.properties");

		org.w3c.dom.Document doc = null;
		try {
			DOMParser parser = new DOMParser();
			parser.parse(new FileReader("schedule.xml"));
			doc = parser.getDocument();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Sorry, an error occurred: " + e);
		}

		if (doc != null) {
			Node node = doc.getLastChild();
			NodeList list = doc.getElementsByTagName("job");
			int len = list.getLength();
			//System.out.println(len);
			for (int i = 0; i < len; i++) {

				org.w3c.dom.Element de = (org.w3c.dom.Element) (list.item(i));
				String jobName = de.getElementsByTagName("job_name").item(0)
						.getFirstChild().getNodeValue();
				String jobDescription = de.getElementsByTagName(
						"job_description").item(0).getFirstChild()
						.getNodeValue();
				String scheduleType = de.getElementsByTagName("schedule_type")
						.item(0).getFirstChild().getNodeValue();
				String scheduleRule = de.getElementsByTagName("schedule_rule")
						.item(0).getFirstChild().getNodeValue();
				String calssName = de.getElementsByTagName("job_class").item(0)
						.getFirstChild().getNodeValue();
				String status = de.getElementsByTagName("status").item(0)
						.getFirstChild().getNodeValue();

				JobElement je = new JobElement();
				je.setName(jobName);
				je.setDescription(jobDescription);
				je.setScheduleType(scheduleType);
				je.setScheduleRule(scheduleRule);
				je.setClassName(calssName);
				if (status.toUpperCase().equals("ENABLE"))
					je.setStatus(1);
				else
					je.setStatus(-1);
				jobs.add(je);
			}
		}
	}

	/**
	 * ������̨����
	 * 
	 * @throws java.lang.Exception
	 */
	private void loadServices() throws Exception {
		textarea.setText(" ����������... " + "\n");
		SchedulerFactory sf = new org.quartz.impl.StdSchedulerFactory();
		this.sched = sf.getScheduler();
		Trigger trigger = null;
		JobDetail job = null;

		textarea.append(" ע���ϴ����е�����... " + "\n");

		String[] names = sched.getTriggerNames(GROUP_NAME);
		for (int j = 0; j < names.length; j++) {
			textarea.append(names[j]);
			sched.unscheduleJob(names[j], GROUP_NAME);
		}

		names = sched.getJobNames(GROUP_NAME);
		for (int j = 0; j < names.length; j++) {
			sched.deleteJob(names[j], GROUP_NAME);
		}

		textarea.append(" ע�����..." + "\n");
		textarea.append(" �ɹ�����..." + "\n");
		textarea.append(" ��ʼ��������ƻ�..." + "\n");

		// jobs can be scheduled before start() has been called

		// long ts = TriggerUtils.getNextGivenSecondDate(null, 15).getTime(); .

		for (int i = 0; i < jobs.size(); i++) {
			JobElement je = (JobElement) jobs.get(i);
			if (je.getStatus() == 1) {
				job = new JobDetail(i + "", GROUP_NAME, Class.forName(je
						.getClassName()));

				//  �жϷ�����̵������ͣ�
				//  ���ֶ�
				// SCHEDULE_TYPE==0��Ϊ�򵥵������ͣ���magic_SCHEDULE_TYPE==1����Ϊ���ӵ�������
				if (je.getScheduleType().toUpperCase().equals("SIMPLE")) {
					trigger = new SimpleTrigger(i + "", //
							GROUP_NAME, //
							new Date(),// �����������ʱ��
							null, //��ֹ������̵�ʱ��Ϊ��
							SimpleTrigger.REPEAT_INDEFINITELY,//
							Long.parseLong(je.getScheduleRule()) * 1000L);//���ÿ�����ظ�ִ��һ��
				} else {

					trigger = new CronTrigger("" + i, GROUP_NAME, je
							.getScheduleRule());
				}
				Date ft = sched.scheduleJob(job, trigger);
				System.out.println("------------------------" + "\n");
				System.out.println("�ɹ���������:" + je.getName() + "\n");
				System.out.println("��Ҫ��������:" + je.getDescription() + "\n");
				System.out
						.println("�´�����ʱ��=" + trigger.getNextFireTime() + "\n");
				System.out.println("�ϴ�����ʱ��=" + trigger.getPreviousFireTime()
						+ "\n");
				System.out.println("------------------------" + "\n");
			}
		}
		sched.start();

	}

	private void refresh() {
		this.paint(this.getGraphics());
	}

	/**
	 * ˢ�±��
	 */
	private void refreshTable() {
		try {
			//System.out.println(sched.getSchedulerName());
			String[] names = sched.getTriggerNames(GROUP_NAME);
			//System.out.println("��Ч�������:"+names.length);
			for (int j = 0; j < names.length; j++) {
				//System.out.println("��Ч�������:"+j);
				Trigger trigger = sched.getTrigger(names[j], GROUP_NAME);
				// System.out.println("refresh
				// row:"+names[j]+"|"+trigger.getPreviousFireTime()+"|"+trigger.getNextFireTime());
				refreshTableRow(names[j], trigger.getPreviousFireTime(),
						trigger.getNextFireTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}

	/**
	 * ˢ�±������Ϣ
	 * 
	 * @param id
	 *            ���id
	 * @param pre_date
	 *            ��һ��ִ������
	 * @param next_date
	 *            ��һ��ִ������
	 */
	private void refreshTableRow(String id, Date pre_date, Date next_date) {
		for (int i = 0; i < ROW_COUNT; i++) {
			// System.out.println(strTableData[i][1]);
			if (StringUtil.cEmpty(strTableData[i][0]).equals(id)) {
				// System.out.println(strTableData[i][0]);
				// System.out.println(strTableData[i][1]);
				if (pre_date == null)
					strTableData[i][4] = "";
				else
					strTableData[i][4] = pre_date.toString();
				if (next_date == null)
					strTableData[i][5] = "";
				else
					strTableData[i][5] = next_date.toString();
				//System.out.println(pre_date.toString());
				//System.out.println(next_date.toString());
			}
		}
	}

	/**
	 * ���±����״̬
	 * 
	 * @param id
	 *            ��Id
	 * @param status
	 *            ״̬
	 */
	private void updateTableRowStatus(String id, String status) {
		for (int i = 0; i < ROW_COUNT; i++) {
			//System.out.println("*******"+strTableData[i][1]);
			if (strTableData[i][0] == null) {
			    break;
			}
			if (strTableData[i][0].equals(id)) {
				strTableData[i][3] = status;
				if (status.equals("��Ч") || status.equals("��ͣ"))
					strTableData[i][5] = "";
			}
		}
	}

	//�������¼�
	/**
	 * �������¼�
	 * 
	 * @param e
	 */
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel rowSM = table.getSelectionModel();
		//System.out.println(rowSM.getMaxSelectionIndex());
		JOptionPane.showMessageDialog(null, "��ȷ��Ҫ��ͣ���������"
				+ rowSM.getMaxSelectionIndex(), "��ͣ�������",
				JOptionPane.INFORMATION_MESSAGE);
	}
}