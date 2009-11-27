package com.magic.crm.schedule;

//import java.util.Timer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
//import javax.swing.border.*;

import com.magic.exchange.*;
import com.magic.crm.util.*;

import java.sql.*;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SNPrinter2
    extends JFrame {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


  private int sel_type = 1;
  private int print_count = 200;

  //private String lot = "";

  private Connection conn = null;

  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JRadioButton jrdYJ = new JRadioButton();
  JRadioButton jrdZS = new JRadioButton();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  //JButton jbtnOK = new JButton();
  JLabel jLabel2 = new JLabel();
  JLabel jblYJ = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jblZS = new JLabel();
  JLabel jLabel6 = new JLabel();
  JTextField jtxCount = new JTextField();
  JButton jbtnPrint = new JButton();
  JLabel jLabel3 = new JLabel();
  JLabel jblHint = new JLabel();
  JLabel jLabel5 = new JLabel();
  private JLabel jLabel7;
  private JTextArea jtxaSO;
  private JScrollPane jScrollPane1;
  JTextField jtxLot = new JTextField();
  JButton jbtnQry = new JButton();
  //JButton jbtnReDo = new JButton();

  public SNPrinter2() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setSize(600, 400);
    jPanel1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
    jPanel1.setBounds(10, 10, 575, 360);
    jPanel1.setLayout(null);
    jLabel1.setToolTipText("");
    jLabel1.setText("待处理发货单");
    jLabel1.setBounds(new Rectangle(32, 34, 96, 30));
    //jrdYJ.setToolTipText("");
    //jrdYJ.setSelected(true);
    //jrdYJ.setText("邮寄");
    //jrdYJ.setBounds(new Rectangle(46, 166, 60, 32));
    //jrdYJ.addActionListener(new SNPrinter2_jrdYJ_actionAdapter(this));
    //jrdZS.setText("直送");
    //jrdZS.setBounds(new Rectangle(125, 167, 94, 24));
    //jrdZS.addActionListener(new SNPrinter2_jrdZS_actionAdapter(this));
    //jbtnOK.setBounds(new Rectangle(47, 199, 85, 25));
    //jbtnOK.setText("补货");
    //jbtnOK.addActionListener(new SNPrinter_jbtnOK_actionAdapter(this));
    jLabel2.setText("邮寄");
    jLabel2.setBounds(new Rectangle(33, 65, 43, 30));
    jblYJ.setToolTipText("");
    jblYJ.setText("0");
    jblYJ.setBounds(new Rectangle(83, 67, 65, 25));
    jLabel4.setText("直送");
    jLabel4.setBounds(new Rectangle(269, 66, 57, 26));
    jblZS.setText("0");
    jblZS.setBounds(new Rectangle(326, 65, 74, 28));
    jLabel6.setText("打印数量");
    jLabel6.setBounds(33, 115, 63, 32);
    jtxCount.setText("200");
    jtxCount.setBounds(174, 121, 78, 21);
    this.addWindowListener(new SNPrinter2_this_windowAdapter(this));
    jbtnPrint.setBounds(33, 283, 82, 24);
    jbtnPrint.setText("配货");
    jbtnPrint.addActionListener(new SNPrinter2_jbtnPrint_actionAdapter(this));
    jLabel3.setBorder(BorderFactory.createEtchedBorder());
    jLabel3.setDebugGraphicsOptions(0);
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel3.setText("@copyright 佰明国际贸易（上海）有限公司");
    jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
    jLabel3.setBounds(10, 317, 549, 27);
    jblHint.setText("提示：");
    jblHint.setBounds(new Rectangle(268, 194, 278, 28));
    jLabel5.setText("批号");
    jLabel5.setBounds(33, 254, 33, 19);
    jtxLot.setSelectionStart(0);
    jtxLot.setText("");
    jtxLot.setBounds(84, 253, 168, 20);
    jbtnQry.setBounds(new Rectangle(147, 39, 105, 19));
    jbtnQry.setToolTipText("");
    jbtnQry.setActionCommand("显示待处理订单");
    jbtnQry.setText("查询数量");
    jbtnQry.addActionListener(new SNPrinter2_jbtnQry_actionAdapter(this));
    //jbtnReDo.setBounds(new Rectangle(145, 198, 91, 25));
    //jbtnReDo.setToolTipText("");
    //jbtnReDo.setText("重新补货");
    //jbtnReDo.addActionListener(new SNPrinter2_jbtnReDo_actionAdapter(this));
    jPanel1.add(jLabel1, null);
    jPanel1.add(jLabel2, null);
    jPanel1.add(jblYJ, null);
    jPanel1.add(jblZS, null);
    jPanel1.add(jLabel4, null);
    jPanel1.add(jLabel3, null);
    jPanel1.add(jLabel6, null);
    jPanel1.add(jtxCount, null);
    jPanel1.add(jrdYJ, null);
    jPanel1.add(jrdZS, null);
    //jPanel1.add(jbtnOK, null);
    jPanel1.add(jLabel5, null);
    jPanel1.add(jtxLot, null);
    jPanel1.add(jbtnPrint, null);
    jPanel1.add(jblHint, null);
    jPanel1.add(jbtnQry, null);
    {
    	jLabel7 = new JLabel();
    	jPanel1.add(jLabel7);
    	jLabel7.setText("\u6253\u5370\u6307\u5b9a\u8ba2\u5355");
    	jLabel7.setBounds(33, 151, 72, 15);
    }
    {
    	jScrollPane1 = new JScrollPane();
    	jPanel1.add(jScrollPane1);
    	jScrollPane1.setBounds(32, 169, 220, 78);
    	jScrollPane1.setBorder(BorderFactory.createCompoundBorder(
    			null, 
    			null));
    	{
    		jtxaSO = new JTextArea();
    		jScrollPane1.setViewportView(jtxaSO);
    		jtxaSO.setBounds(331, 148, 204, 72);
    		jtxaSO.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    		jtxaSO.setColumns(50);
    		jtxaSO.setLineWrap(true);
    	}
    }
    //jPanel1.add(jbtnReDo, null);
    buttonGroup1.add(jrdZS);
    buttonGroup1.add(jrdYJ);
    this.getContentPane().add(jPanel1, null);

    // 1.定时器
    //Timer timer = new Timer();
    //TimeTaskUnprintedSN line = new TimeTaskUnprintedSN(this);
    //timer.schedule(line,0,60);
    // 2.connection init
    conn = DBManager2.getConnection();
    listUnPrintedSN();
  }

  public static void main(String[] args) {
    SNPrinter2 SNPrinter = new SNPrinter2();
    SNPrinter.pack();
    SNPrinter.setBounds(200, 200, 600, 400);
    SNPrinter.setTitle("发货单打印控制程序");
    SNPrinter.show();
  }

  void jbtnOK_actionPerformed(ActionEvent e) {
    try {
      //if (jbtnOK.getText().equals("补货")) {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        print_count = Integer.parseInt(jtxCount.getText());
        PrintShippingNotices pn = new PrintShippingNotices(sel_type,
            print_count, 1);
        String lot = pn.printSN(conn);
        jtxLot.setText(lot);
        jbtnQry_actionPerformed(null);
        ShippingNoticePDFGenerator pg = new ShippingNoticePDFGenerator(conn);
        if (pg.genSupplyPDF(lot)) {

          //pg.exePrint(pg.getFileName());
          new PrintThread(pg.file_dir,pg.fileName).start();
          Thread.sleep(30L * 1000L);
          JOptionPane.showMessageDialog(this, "补货单已打印，请在出库区货架调整后打印配货单");
          //jblHint.setText("提示：对应补货单号为");
        }
        else {
          JOptionPane.showMessageDialog(this, "没有产生补货单，请在出库区货架调整后打印配货单");
        }
         this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
     // }
    }
    catch (Exception e1) {
      JOptionPane.showMessageDialog(this, e1);
    }

  }

  private void createPDF(String lot) throws Exception {
    ShippingNoticePDFGenerator pg = new ShippingNoticePDFGenerator(conn);
    //if (pg.genOrderShipping(lot,1)) {
      //pg.exePrint(pg.getFileName());
    //  new PrintThread(pg.file_dir,pg.fileName).start();
   // }
   // Thread.sleep(20L * 1000L);

    /*if (pg.genOrderShipping(lot,2)) {
      //pg.exePrint(pg.getFileName());
      new PrintThread(pg.file_dir,pg.fileName).start();
    }
    Thread.sleep(20L * 1000L);*/

    if (pg.generatorPdf(lot)) {
      //pg.exePrint(pg.getFileName());
      new PrintThread(pg.file_dir,pg.fileName).start();
    }
    Thread.sleep(20L * 1000L);
  }

  /**
   * 列出待打印的发货单的数量
   */
  private void listUnPrintedSN() {

   PrintShippingNotices.createSN(conn);

    Statement st = null;
    try {
      String sql = "select count(*) as qty,decode(delivery_type,1,1,1,1,3) as delivery_type "
          + " from ord_shippingnotices "
          + " where status=0 "
          + " group by decode(delivery_type,1,1,1,1,3)";
      st = conn.createStatement();
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        int dt = rs.getInt("delivery_type");
        int qty = rs.getInt("qty");
        if (dt == 1) {
          jblYJ.setText(String.valueOf(qty));
        }
        else if (dt == 3) {
          jblZS.setText(String.valueOf(qty));
        }
      }
      rs.close();
      //owner.display.asyncExec(this);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        st.close();
      }
      catch (Exception e) {}
      ;
    }

  }

  /**
   * setDeliveryQry
   *
   * @param qty1 int
   * @param qty2 int
   */
  public void setDeliveryQry(int qty1, int qty2) {
    jblYJ.setText(String.valueOf(qty1));
    jblZS.setText(String.valueOf(qty2));
    //jblYJ.setText(String.valueOf(i++));
  }

  void jrdYJ_actionPerformed(ActionEvent e) {
    sel_type = 1;
  }

  void jrdZS_actionPerformed(ActionEvent e) {
    sel_type = 3;
  }



  void jbtnPrint_actionPerformed(ActionEvent e) {
    try {
     this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

      /*int i = PrintShippingNotices.fillSNTable(conn, jtxLot.getText().trim());
      if (i == -2) {
         JOptionPane.showMessageDialog(this, "出错：产品取货区货架不存在");
          this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         return;
      } else  if (i== -3) {
         JOptionPane.showMessageDialog(this, "出错：产品取货区库存不足");
          this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         return;
      } else if (i== -1) {
         JOptionPane.showMessageDialog(this, "出错：未知错误");
          this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         return;
      } else {

      }*/
      // 如果批号栏填写，则打印指定批号，如果指定订单填写，则打印指定订单，如果没有填写，则缺省打印指定数量订单
      if(jtxLot.getText()== null || "".equals(jtxLot.getText().trim())) {
    	  
    	  if(jtxaSO.getText()==null || "".equals(jtxaSO.getText())) {
	    	  print_count = Integer.parseInt(jtxCount.getText());
	    	  PrintShippingNotices pn = new PrintShippingNotices(sel_type,print_count, 1);
	    	  String lot = pn.printSN(conn);
	    	  jtxLot.setText(lot);
	    	  createPDF(jtxLot.getText().trim());
	          JOptionPane.showMessageDialog(this, "配货单/发货单已打印");
    	  } else {
    		  //print_count = Integer.parseInt(jtxCount.getText());
	    	  PrintShippingNotices pn = new PrintShippingNotices(sel_type,0, 1);
	    	  String errMsg = pn.checkSO(conn,jtxaSO.getText());
	    	  if (!"".equals(errMsg)) {
	    		  JOptionPane.showMessageDialog(this,errMsg);
	    		  
	    	  } else {
	    	  
		    	  String lot = pn.printSN(conn,jtxaSO.getText());
		    	  jtxLot.setText(lot);
		    	  createPDF(jtxLot.getText().trim());
		          JOptionPane.showMessageDialog(this, "配货单/发货单已打印");
	    	  }
    	  }
      }

      
    }
    catch (Exception e1) {
      JOptionPane.showMessageDialog(this, e1);
    }
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  void jbtnQry_actionPerformed(ActionEvent e) {
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    jblYJ.setText("0");
    jblZS.setText("0");
    listUnPrintedSN();
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }


  protected void finalize() {
  try {
     conn.close();
   }
   catch (Exception e1) {}
   ;

}



  void this_windowClosing(WindowEvent e) {
    try {
     conn.close();
   }
   catch (Exception e1) {}
   System.exit(0);
  }

  void jbtnReDo_actionPerformed(ActionEvent e) {

    try {
      //if (jbtnOK.getText().equals("补货")) {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //print_count = Integer.parseInt(jtxCount.getText());
        PrintShippingNotices pn = new PrintShippingNotices(sel_type,
            print_count, 1);
        int ret = pn.create_supply(conn,jtxLot.getText());
        if (ret == -100) {
          JOptionPane.showMessageDialog(this, "该批号补货单已经完成，不能重新补货");
          this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          return;
        }

       ShippingNoticePDFGenerator pg = new ShippingNoticePDFGenerator(conn);
        if (pg.genSupplyPDF(jtxLot.getText())) {

          //pg.exePrint(pg.getFileName());
          new PrintThread(pg.file_dir,pg.fileName).start();
          Thread.sleep(30L * 1000L);
          JOptionPane.showMessageDialog(this, "补货单已打印，请在出库区货架调整后打印配货单");
          //jblHint.setText("提示：对应补货单号为");
        }
        else {
          JOptionPane.showMessageDialog(this, "没有产生补货单，请在出库区货架调整后打印配货单");
        }
         this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
     // }
    }
    catch (Exception e1) {
      JOptionPane.showMessageDialog(this, e1);
    }



  }

}

class SNPrinter_jbtnOK_actionAdapter
    implements java.awt.event.ActionListener {
  SNPrinter2 adaptee;

  SNPrinter_jbtnOK_actionAdapter(SNPrinter2 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jbtnOK_actionPerformed(e);
  }
}

/*class SNPrinter2_jrdYJ_actionAdapter
    implements java.awt.event.ActionListener {
  SNPrinter2 adaptee;

  SNPrinter2_jrdYJ_actionAdapter(SNPrinter2 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jrdYJ_actionPerformed(e);
  }
}

class SNPrinter2_jrdZS_actionAdapter
    implements java.awt.event.ActionListener {
  SNPrinter2 adaptee;

  SNPrinter2_jrdZS_actionAdapter(SNPrinter2 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jrdZS_actionPerformed(e);
  }
}
*/
class SNPrinter2_this_windowAdapter
    extends java.awt.event.WindowAdapter {
  SNPrinter2 adaptee;

  SNPrinter2_this_windowAdapter(SNPrinter2 adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }

}

class SNPrinter2_jbtnPrint_actionAdapter
    implements java.awt.event.ActionListener {
  SNPrinter2 adaptee;

  SNPrinter2_jbtnPrint_actionAdapter(SNPrinter2 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jbtnPrint_actionPerformed(e);
  }
}

class SNPrinter2_jbtnQry_actionAdapter
    implements java.awt.event.ActionListener {
  SNPrinter2 adaptee;

  SNPrinter2_jbtnQry_actionAdapter(SNPrinter2 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jbtnQry_actionPerformed(e);
  }
}

class SNPrinter2_jbtnReDo_actionAdapter implements java.awt.event.ActionListener {
  SNPrinter2 adaptee;

  SNPrinter2_jbtnReDo_actionAdapter(SNPrinter2 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jbtnReDo_actionPerformed(e);
  }
}
