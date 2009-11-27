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
 * 后台服务控制台Java
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */

public class MainFrame extends JFrame implements ActionListener,
		ListSelectionListener {

	private static int job_id = 0;

	private static Scheduler sched;

	// 程序入口
	/**
	 * main 方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create Frame class`s Object
		MainFrame mainframe = new MainFrame("服务进程管理程序");
		//设置窗体的时间监听（关闭窗体事件）
		mainframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowevent) {
				// 正常退出 Java 虚拟机
				System.exit(0);
			}
		});
		// 显示主窗体***********************
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
	JLabel display_service = new JLabel("所有服务进程信息：", Label.LEFT);

	final String GROUP_NAME = "magic";

	private Vector jobs = new Vector();

	JButton no_service = new JButton("禁止服务");

	//JButton see_service = new JButton("查看日志");
	JButton now_service = new JButton("运行一次");

	JButton plash_service = new JButton("激活服务");

	final int ROW_COUNT = 40;

	JButton run_service = new JButton("运行服务");

	String strTableData[][];

	// Create Table Display All Schedule Servise
	String strTableTitle[] = { "进程ID", "进程名称", "进程描述", "进程状态", "上次运行时间",
			"下次运行时间" };

	// Creat Servise Control Button
	JButton suspension_service = new JButton("暂停服务");

	JTable table;

	JPanel table_panel = new JPanel();

	JTextArea textarea = new JTextArea(" >>> ……" + "\n" + " 服务进程管理系统正在启动...",
			13, 30);

	public MainFrame() {

	}

	//进程服务对象

	// Construct the Frame
	/**
	 * 创建Frame
	 * 
	 * @param setFrameTitle
	 */
	public MainFrame(String setFrameTitle) {

		super(setFrameTitle);
		loadProperties();
		display_service.setText("所有服务进程信息");

		// 在构造函数里产生 JTable 的对象
		strTableData = new String[ROW_COUNT][6];
		table = new JTable(strTableData, strTableTitle);
		DefaultTableModel dtm;
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(false);
		//添加组件到面板.窗体上去 ：addComponent -->JPanel -->JFrame
		add_label.add(display_service); // 添加 显示所有服务信息 标签
		add_button.add(suspension_service); // 添加 暂停服务 按钮
		add_button.add(run_service); // 添加 运行服务 按钮
		//add_button.add(no_service); // 添加 禁止服务 按钮
		//add_button.add(plash_service); // 添加 激活服务 按钮
		//add_button.add(see_service); // 添加 查看服务进程日志 按钮
		add_button.add(now_service); // 添加 更改调度 按钮

		// 在窗体上添加上包含了标签、表单和按钮这些组件的面板对象：add_label、add_table、add_button
		this.getContentPane().add(add_label, BorderLayout.NORTH); // 						 
		this.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER); // 
		//this.getContentPane().add(new
		// JScrollPane(textarea),BorderLayout.WEST); //
		this.getContentPane().add(add_button, BorderLayout.SOUTH);

		//添加监视
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
		 * 在此加进 ScheduleMain.java 中的代码
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

	// 处理按钮事件
	/**
	 * 处理按钮事件
	 * 
	 * @param actionevent
	 */
	public void actionPerformed(ActionEvent actionevent) {
		// 发生在 “暂停进程服务” 按钮上的事件处理
		if (actionevent.getSource() == suspension_service) {
			if (getSelectedJobID().equals(""))
				return;
			int i = JOptionPane.showConfirmDialog(null, "你确认要暂停该项服务吗？",
					"暂停服务进程", JOptionPane.INFORMATION_MESSAGE);
			if (i == 0) {
				try {
					sched.pauseJob(getSelectedJobID(), GROUP_NAME);
					updateTableRowStatus(getSelectedJobID(), "暂停");
					//System.out.println(getSelectedJobID());
				} catch (Exception e) {
				    
					e.printStackTrace();
				}
			}
		}
		//发生在 “运行进程服务” 按钮上的事件处理
		if (actionevent.getSource() == run_service) {
			if (getSelectedJobID().equals(""))
				return;
			int i = JOptionPane.showConfirmDialog(null, "你确认要运行该项服务吗？",
					"运行服务进程", JOptionPane.INFORMATION_MESSAGE);
			if (i == 0) {
				try {
					sched.resumeJob(getSelectedJobID(), GROUP_NAME);
					updateTableRowStatus(getSelectedJobID(), "有效");
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		//发生在 “禁止进程服务” 按钮上的事件处理
		//        if(actionevent.getSource() == no_service)
		//		{
		//			 String id=getSelectedJobID();
		//       if(id.equals("")) return;
		//     	 int
		// i=JOptionPane.showConfirmDialog(null,"你确认要禁止服务进程吗？","禁止服务进程",JOptionPane.INFORMATION_MESSAGE);
		//       if(i==0 )
		//       {
		//         try
		//         {
		//           dblink.executeUpdate("UPDATE BAS_JOBS SET STATUS = -1 where
		// id="+getSelectedJobID());
		//           sched.unscheduleJob(id, GROUP_NAME);
		//           sched.deleteJob(id, GROUP_NAME);
		//           updateTableRowStatus(getSelectedJobID(),"无效");
		//         }catch(Exception e)
		//         {
		//           e.printStackTrace();
		//         }
		//       }
		//      
		//		}
		//		 //发生在 “激活进程服务” 按钮上的事件处理plash_servise
		//    if(actionevent.getSource() == plash_service)
		//		{
		//			 String id=getSelectedJobID();
		//       if(id.equals("")) return;
		//     	 int
		// i=JOptionPane.showConfirmDialog(null,"你确认要激活服务进程吗？","激活服务进程",JOptionPane.INFORMATION_MESSAGE);
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
		//				// 判断服务进程调度类型：
		//			    // 若字段 SCHEDULE_TYPE==0侧为简单调度类型，若magic_SCHEDULE_TYPE==1，侧为复杂调度类型
		//				 if(schedule_type==0)
		//				 {
		//					 trigger = new SimpleTrigger(id+"", //
		//															   GROUP_NAME, //
		//															   new Date(),// 启动服务进程时间
		//															   null, //终止服务进程的时间为空
		//															   SimpleTrigger.REPEAT_INDEFINITELY,//
		//															   execute_interval_seconds*1000L);//间隔每分钟重复执行一次
		//				}
		//				else
		//				{
		//					// 按周调度
		//					if(execute_week!=0)
		//					{
		//						// 判断按星期时间启动服务进程：
		//						// 其值在 0--7 之间，若为 0 ，侧 execute_month==0 和 magic_execute_day==0
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
		//           updateTableRowStatus(getSelectedJobID(),"有效");
		//           refreshTable();
		//         }catch(Exception e)
		//         {
		//           System.out.println(e);
		//         }
		//       }
		//		}
		// 发生在 “查看进程日志” 按钮上的事件处理
		/*
		 * if(actionevent.getSource() == see_service) { // 在主窗体 JTable
		 * 表下面的文本窗格里显示出服务进程的调度
		 * JOptionPane.showMessageDialog(null,"查看服务进程日志..","查看服务进程日志",JOptionPane.INFORMATION_MESSAGE); }
		 */
		// 发生在 “立即运行” 按钮上的事件处理
		if (actionevent.getSource() == now_service) {
			String id = getSelectedJobID();
			if (id.equals(""))
				return;
			int i = JOptionPane.showConfirmDialog(null, "你确认要立即运行服务进程吗？",
					"立即运行服务进程", JOptionPane.INFORMATION_MESSAGE);
			if (i == 0) {
				try {

					JobElement je = (JobElement) jobs.get(Integer.parseInt(id));
					JobDetail job = new JobDetail("" + (job_id++), GROUP_NAME
							+ "_NOW", Class.forName(je.getClassName()));
					Trigger trigger = new SimpleTrigger("" + (job_id++), //
							GROUP_NAME + "_NOW", //
							new Date(),// 启动服务进程时间
							new Date(), //终止服务进程的时间为空
							SimpleTrigger.REPEAT_INDEFINITELY,//
							1000L);//间隔每分钟重复执行一次
					sched.scheduleJob(job, trigger);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			//JOptionPane.showMessageDialog(null,"你确认要更改进程调度吗？","更改进程调度",JOptionPane.INFORMATION_MESSAGE);
			//ScheduleService service = new ScheduleService("服务进程调度定义");
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
	 * 初始化表格数据
	 */
	private void initTable() {

		for (int i = 0; i < jobs.size(); i++) {
			JobElement je = (JobElement) jobs.get(i);
			strTableData[i][0] = i + "";
			strTableData[i][1] = je.getName();
			strTableData[i][2] = je.getDescription();
			if (je.getStatus() >= 0)
				strTableData[i][3] = "有效";
			else
				strTableData[i][3] = "无效";
			strTableData[i][4] = "";
			strTableData[i][5] = "";
		}

	}

	/**
	 * 从XML中读取服务配置
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
	 * 启动后台服务
	 * 
	 * @throws java.lang.Exception
	 */
	private void loadServices() throws Exception {
		textarea.setText(" 程序启动中... " + "\n");
		SchedulerFactory sf = new org.quartz.impl.StdSchedulerFactory();
		this.sched = sf.getScheduler();
		Trigger trigger = null;
		JobDetail job = null;

		textarea.append(" 注销上次运行的任务... " + "\n");

		String[] names = sched.getTriggerNames(GROUP_NAME);
		for (int j = 0; j < names.length; j++) {
			textarea.append(names[j]);
			sched.unscheduleJob(names[j], GROUP_NAME);
		}

		names = sched.getJobNames(GROUP_NAME);
		for (int j = 0; j < names.length; j++) {
			sched.deleteJob(names[j], GROUP_NAME);
		}

		textarea.append(" 注销完毕..." + "\n");
		textarea.append(" 成功启动..." + "\n");
		textarea.append(" 开始安排任务计划..." + "\n");

		// jobs can be scheduled before start() has been called

		// long ts = TriggerUtils.getNextGivenSecondDate(null, 15).getTime(); .

		for (int i = 0; i < jobs.size(); i++) {
			JobElement je = (JobElement) jobs.get(i);
			if (je.getStatus() == 1) {
				job = new JobDetail(i + "", GROUP_NAME, Class.forName(je
						.getClassName()));

				//  判断服务进程调度类型：
				//  若字段
				// SCHEDULE_TYPE==0侧为简单调度类型，若magic_SCHEDULE_TYPE==1，侧为复杂调度类型
				if (je.getScheduleType().toUpperCase().equals("SIMPLE")) {
					trigger = new SimpleTrigger(i + "", //
							GROUP_NAME, //
							new Date(),// 启动服务进程时间
							null, //终止服务进程的时间为空
							SimpleTrigger.REPEAT_INDEFINITELY,//
							Long.parseLong(je.getScheduleRule()) * 1000L);//间隔每分钟重复执行一次
				} else {

					trigger = new CronTrigger("" + i, GROUP_NAME, je
							.getScheduleRule());
				}
				Date ft = sched.scheduleJob(job, trigger);
				System.out.println("------------------------" + "\n");
				System.out.println("成功安排任务:" + je.getName() + "\n");
				System.out.println("简要任务描述:" + je.getDescription() + "\n");
				System.out
						.println("下次运行时间=" + trigger.getNextFireTime() + "\n");
				System.out.println("上次运行时间=" + trigger.getPreviousFireTime()
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
	 * 刷新表格
	 */
	private void refreshTable() {
		try {
			//System.out.println(sched.getSchedulerName());
			String[] names = sched.getTriggerNames(GROUP_NAME);
			//System.out.println("有效任务个数:"+names.length);
			for (int j = 0; j < names.length; j++) {
				//System.out.println("有效任务个数:"+j);
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
	 * 刷新表格行信息
	 * 
	 * @param id
	 *            表格id
	 * @param pre_date
	 *            上一次执行日期
	 * @param next_date
	 *            下一次执行日期
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
	 * 更新表格行状态
	 * 
	 * @param id
	 *            行Id
	 * @param status
	 *            状态
	 */
	private void updateTableRowStatus(String id, String status) {
		for (int i = 0; i < ROW_COUNT; i++) {
			//System.out.println("*******"+strTableData[i][1]);
			if (strTableData[i][0] == null) {
			    break;
			}
			if (strTableData[i][0].equals(id)) {
				strTableData[i][3] = status;
				if (status.equals("无效") || status.equals("暂停"))
					strTableData[i][5] = "";
			}
		}
	}

	//处理表格事件
	/**
	 * 处理表格事件
	 * 
	 * @param e
	 */
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel rowSM = table.getSelectionModel();
		//System.out.println(rowSM.getMaxSelectionIndex());
		JOptionPane.showMessageDialog(null, "你确认要暂停该项服务吗？"
				+ rowSM.getMaxSelectionIndex(), "暂停服务进程",
				JOptionPane.INFORMATION_MESSAGE);
	}
}