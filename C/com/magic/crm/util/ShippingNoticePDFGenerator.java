/*
 * Created on 2005-7-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.idautomation.linear.BarCode;
import com.idautomation.linear.encoder.barCodeEncoder;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class ShippingNoticePDFGenerator {

  SnEntity sn = new SnEntity();
  private Connection conn = null;

  Document document = null;
  BaseFont bfChinese = null;
  Font headFont = null;
  Font subjectFont = null;
  Font font = null;
  Font lineFont = null;
  Font boldFont = null;
  Font posFont = null;
  Paragraph paragraph = null;
  public String fileName = "";
  public String file_dir = "";

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //日期格式
  private DecimalFormat df = new DecimalFormat("####.##");
  private Date today = new Date();

  /**
   * 产生发货单详细页面
   * @param lot
   * @return
   */
  public boolean generatorPdf(String lot) {
    sn.lot = lot;
    Statement stmt = null;
    ResultSet rs = null;
    Statement subSt = null;
    ResultSet subRs = null;
    boolean success = false;
    String sql = "";
    String filename = null;
    try {

      file_dir = Config.getValue("PDF_FILE_DIR");
      filename = file_dir + "\\" + lot + "F.pdf";
      bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                                      BaseFont.NOT_EMBEDDED);
      subjectFont = new Font(bfChinese, 14, Font.NORMAL);
      headFont = new Font(bfChinese, 12, Font.NORMAL);
      font = new Font(bfChinese, 10, Font.NORMAL);
      lineFont = new Font(bfChinese, 10, Font.BOLD);
      boldFont = new Font(bfChinese, 13, Font.BOLD);
      posFont = new Font(bfChinese, 22, Font.BOLD);
      document = new Document(PageSize.A4, 20, 20, 0, 5);
      Document.compress = false;
      PdfWriter.getInstance(document, new FileOutputStream(filename));

      //报表底
      Phrase phraseFoot = new Phrase("页码：", font);
      HeaderFooter footer = new HeaderFooter(phraseFoot, true);
      footer.setBorder(Rectangle.NO_BORDER);
      footer.setBorderWidth(0);
      document.setFooter(footer);

      //打开Document
      document.open();

      sql = "Select a.*, b.name as delivery_type_name,a.city,d.card_id as cardid,d.company_phone," +
      		"d.family_phone,d.name as member_name,e.name as payment_name " +
      		" From ORD_SHIPPINGNOTICES a, s_delivery_type b,mbr_members d,s_payment_method e " +
      		" Where a.delivery_type = b.id and a.payment_method=e.id and a.member_id = d.id and a.lot = '"
          + lot + "' And a.status>=0 order by a.goods_fee ";
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);

      //为document添加内容

      while (rs.next()) {
        // get data from rs
        setSn(sn, rs);
        // step1 get table1
        Table tblHeader = getHeader();
        // step2 get table2
        Table tblNotice = getHeader2();
        
        // step4 get table4
        Table tblline = getTable4();
        
        
        Table tblfoot = getFoot();

        int j = 0;
        PdfPCell pdfCell = null;
        sql = " select t2.*,t3.itm_code,t4.itm_name, t3.standard_price,t3.color_code,"
        + " t5.catalog_name,nvl(t6.name,'无') as color_name,t3.size_code as size_name," +
        " nvl(t8.name,'无') as unit_name,areacode as source_shelf,t3.itm_barcode "
        + " from ord_shippingnotice_lines t2 "
        + " join prd_item_sku t3 on t2.sku_id=t3.SKU_ID"
        + " join prd_item t4 on t3.itm_code =t4.itm_code"
        + " left join prd_item_category t5 on t4.category_id=t5.CATALOG_ID"
        + " left join prd_item_color t6 on t3.color_code = t6.code"
        //+ " left join prd_item_size t7 on t3.size_code = t7.code" 
        + " left join s_uom t8 on t3.ITM_UNIT = t8.id " +
        " where t2.sn_id= " + sn.doc_id + " order by t2.id";
            
        subSt = conn.createStatement();
        subRs = subSt.executeQuery(sql);
        // the most important function
        // produce product and positio pair
        HashMap ret = parseContent(subRs);
        subRs.close();
        subSt.close();

        ArrayList content = (ArrayList) ret.get("content");
        //ArrayList back = (ArrayList) ret.get("back");
        PdfPTable tblAmount = (PdfPTable) ret.get("total");
        for (int i = 0; i < content.size(); i++) {
          document.newPage();
          //document.add( (Table) back.get(i));
          //document.add(new Paragraph());
          document.add(tblHeader);
          document.add(tblNotice);
          //document.add(tblShip);
          document.add(tblline);
          
          document.add( (PdfPTable) content.get(i));
          document.add(tblAmount);
          document.add(tblfoot);
          //document.newPage();
          //document.add(tblHeader);
        }
        success = true;
        //setFileName(filename);
      }
      //关闭document
      document.close();
      fileName = filename;
    }
    catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());

    }
    finally {
      try {
        if (subRs != null)
          subRs.close();
        if (subSt != null)
          subSt.close();
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();

      }
      catch (Exception e) {
        e.printStackTrace();
      }

    }
    return success;
  }

  //产生批量配货单(按照源货架排）
  public boolean genOrderShipping(String lot,int type ) {

    Statement stmt = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    Statement st1 = null;
    ResultSet subRs = null;
    boolean success = false;
    Document document = null;
    BaseFont bfChinese = null;
    Font headFont = null;
    Font subjectFont = null;
    Font font = null;
    Paragraph paragraph = null;
    String sql = "";
    String filename = "";
    Cell cell = null; //统一的Cell
    try {

      file_dir = Config.getValue("PDF_FILE_DIR");
      //if (type==1) {
        filename = file_dir + "\\" + lot + "P.pdf";
      //} else {
      //  filename = file_dir + "\\" + lot + "P2.pdf";
      //}
      bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                                      BaseFont.NOT_EMBEDDED);
      subjectFont = new Font(bfChinese, 14, Font.NORMAL);
      headFont = new Font(bfChinese, 12, Font.NORMAL);
      font = new Font(bfChinese, 10, Font.NORMAL);
      document = new Document(PageSize.A4, 20, 20, 10, 5);
      Document.compress = false;
      PdfWriter.getInstance(document, new FileOutputStream(filename));
      Table tblHeader = new Table(4);
      Table tblNotice = new Table(4);
      PdfPTable pdftblNotice = null;
      //打开Document
      sql =
          "Select lot,delivery_type_name,id From VW_ORDER_LOT_MST Where lot = '"
          + lot + "'";
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);

      //为document添加内容
      String doc_id = "";
      Paragraph p3 = null;
      Paragraph p4 = null;
      while (rs.next()) {

        doc_id = rs.getString("ID");
        tblHeader = new Table(9);
        tblNotice = new Table(9);

        //设置Table的Width
        tblHeader.setWidth(100.0f);
        tblNotice.setWidth(100.0f);
        //Add contens

        //			    Paragraph p1 = new Paragraph("批量配货单", subjectFont);
        //          p1.setAlignment(Element.ALIGN_CENTER);
        //
        //			    String header = "批次号:        "+rs.getString("lot");
        //			    p3 = new Paragraph(header, font);
        //			    header = "运货方式:        "+rs.getString("delivery_type_name") + "                                                                 ";
        //			    header = header + "                                   日期:        "+sdf.format(today).toString();
        //			    p4 = new Paragraph(header, font);
        Table t1 = new Table(6);
        t1.setPadding(0);
        t1.setSpacing(0);
        t1.setWidth(100.0f);
        t1.setDefaultCellBorder(0);
        t1.setBorderWidth(0);
        Paragraph p1 = new Paragraph("批量配货单", subjectFont);
        cell = new Cell(p1);
        cell.setColspan(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        t1.addCell(cell);

        t1.addCell(new Phrase("批次号:", font));
        t1.addCell(new Phrase(rs.getString("lot"), font));
        cell = new Cell(new Phrase(" ", font));
        cell.setColspan(4);
        t1.addCell(cell);
        t1.addCell(new Phrase("运货方式:", font));
        t1
            .addCell(new Phrase(rs.getString("delivery_type_name"),
                                font));
        cell = new Cell(new Phrase(" ", font));
        cell.setColspan(2);
        t1.addCell(cell);
        t1.addCell(new Phrase("日期:", font));
        t1.addCell(new Phrase(sdf.format(today), font));

        Phrase phraseHeader = new Phrase("佰明国际贸易（上海）有限公司", font);
        phraseHeader.add(t1);
        HeaderFooter header1 = new HeaderFooter(phraseHeader, false);
        header1.setAlignment(Element.ALIGN_CENTER);
        document.setHeader(header1);

        Phrase phraseFoot = new Phrase("页码：", font);
        HeaderFooter footer = new HeaderFooter(phraseFoot, true);
        //footer.setBorder(Rectangle.NO_BORDER);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.setFooter(footer);
        document.open();
        //列表内容
        String[] TableName = new String[9];
        TableName[0] = "序号";
        TableName[1] = "货号";
        TableName[2] = "名称";
        TableName[3] = "颜色";
        TableName[4] = "尺寸";
        TableName[5] = "产品类型";
        TableName[6] = "单位";
        TableName[7] = "货架";
        TableName[8] = "数量";
        //TableName[7] = "目的货架";
        float[] columnWidth = new float[9];
        columnWidth[0] = 30.0f;
        columnWidth[1] = 40.0f;
        columnWidth[2] = 130.0f;
        columnWidth[3] = 40.0f;
        columnWidth[4] = 40.0f;
        columnWidth[5] = 40.0f;
        columnWidth[6] = 25.0f;
        columnWidth[7] = 65.0f;
        columnWidth[8] = 40.0f;
        //columnWidth[7] = 65.0f;

        //////////////////////////////////////      主货架
        pdftblNotice = new PdfPTable(columnWidth);
        pdftblNotice.setWidthPercentage(100.0f);
        //pdftblNotice.setWidthPercentage(columnWidth,PageSize.A4);
        //end
        pdftblNotice.getDefaultCell().setHorizontalAlignment(
            Element.ALIGN_CENTER);
        pdftblNotice.getDefaultCell().setVerticalAlignment(
            Element.ALIGN_MIDDLE);
        pdftblNotice.getDefaultCell().setLeading(1.4f, 1.4f);
        pdftblNotice.getDefaultCell().setGrayFill(0.9f);
        pdftblNotice.setHeaderRows(1);
        
        for (int i = 0; i < TableName.length; i++) {
          pdftblNotice.addCell(new Phrase(TableName[i], font));
        }
        pdftblNotice.setSkipFirstHeader(false);
        pdftblNotice.getDefaultCell().setLeading(1.2f, 1.2f);
        pdftblNotice.getDefaultCell().setGrayFill(0.0f);

        //------
        sql = "Select count(*) count From VW_ORDER_LOT_DTL Where lot ='"
            + doc_id + "' ";
        st1 = conn.createStatement();
        rs1 = st1.executeQuery(sql);
        int rowCount = 0;

        if (rs1.next()) {
          rowCount = rs1.getInt("count");
        }
        rs1.close();
        st1.close();
        //-------
        String[][] data = new String[rowCount][TableName.length];
        int iCount = 0;
        //if (type == 1) {
          sql = "Select * From VW_ORDER_LOT_DTL Where lot='" + doc_id
              + "' order by source_shelf asc";
        //} else {
        //  sql = "Select * From VW_ORDER_LOT_DTL Where id='" + doc_id
        //      + "' and substr(target_shelf,-1) <='5' order by target_shelf asc";
        //}
        st1 = conn.createStatement();
        subRs = st1.executeQuery(sql);
        while (subRs.next()) {
          data[iCount][0] = "" + (iCount + 1);
          data[iCount][1] = "" + subRs.getString("ITM_CODE");
          data[iCount][2] = "" + subRs.getString("ITM_NAME");
          data[iCount][3] = "" + subRs.getString("color_name");
          data[iCount][4] = "" + subRs.getString("size_name");
          data[iCount][5] = "" + subRs.getString("catalog_name");
          data[iCount][6] = "" + subRs.getString("UNIT_NAME");
          data[iCount][8] = "" + subRs.getInt("qty");
          //data[iCount][5] =
          //    (subRs.getString("SOURCE_SHELF") == null ? "" : subRs.getString("SOURCE_SHELF"));
          data[iCount][7] = "" + subRs.getString("SOURCE_SHELF");
          //    (subRs.getString("TARGET_SHELF") == null ? "" : subRs.getString("TARGET_SHELF"));
          iCount++;
        }
        subRs.close();
        st1.close();
        PdfPCell pdfCell = null;
        for (int j = 0; j < iCount; j++) {
          for (int i = 0; i < TableName.length; i++) {
            pdfCell = new PdfPCell(new Phrase(data[j][i], font));
            if (i == 2 || i == 3 || i == 4) {
              pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            }
            else if (i == 1 || i == 0) {
              pdfCell
                  .setHorizontalAlignment(Element.ALIGN_CENTER);
            }
            else {
              pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            }
            pdftblNotice.addCell(pdfCell);
          }
        }

         document.add(pdftblNotice);
         /*document.newPage();
         document.add(new Phrase("以下为副货架",font));

         ///////////////////////////////////         副货架
         pdftblNotice = new PdfPTable(columnWidth);
         pdftblNotice.setWidthPercentage(100.0f);

         pdftblNotice.getDefaultCell().setHorizontalAlignment(
             Element.ALIGN_CENTER);
         pdftblNotice.getDefaultCell().setVerticalAlignment(
             Element.ALIGN_MIDDLE);
         pdftblNotice.getDefaultCell().setLeading(1.4f, 1.4f);
         pdftblNotice.getDefaultCell().setGrayFill(0.9f);
         pdftblNotice.setHeaderRows(1);
         for (int i = 0; i < 8; i++) {
           pdftblNotice.addCell(new Phrase(TableName[i], font));
         }
         pdftblNotice.setSkipFirstHeader(false);
         pdftblNotice.getDefaultCell().setLeading(1.2f, 1.2f);
         pdftblNotice.getDefaultCell().setGrayFill(0.0f);

         sql = "Select count(*) count From VW_ORDER_LOT_DTL Where id='"
             + doc_id + "' and substr(target_shelf,-1) >'5' ";
         st1 = conn.createStatement();
         rs1 = st1.executeQuery(sql);

         if (rs1.next()) {
           rowCount = rs1.getInt("count");
         }
         rs1.close();
         st1.close();

         data = new String[rowCount][8];
        if (type == 1) {
           sql = "Select * From VW_ORDER_LOT_DTL Where id='" + doc_id
               + "' and substr(target_shelf,-1) >'5' order by source_shelf asc";
         }
         else {
           sql = "Select * From VW_ORDER_LOT_DTL Where id='" + doc_id
               + "' and substr(target_shelf,-1) >'5' order by target_shelf asc";
         }
         st1 = conn.createStatement();
         subRs = st1.executeQuery(sql);
         iCount = 0;
         while (subRs.next()) {
           data[iCount][0] = "" + (iCount + 1);
           data[iCount][1] = subRs.getString("ITEM_CODE");
           data[iCount][2] = "" + subRs.getString("ITEM_NAME");
           data[iCount][3] = subRs.getString("ITEM_TYPE_NAME");
           data[iCount][4] = subRs.getString("UNIT_NAME");
           data[iCount][6] = "" + subRs.getInt("QUANTITY");
           data[iCount][5] =
               (subRs.getString("SOURCE_SHELF") == null ? "" :
                subRs.getString("SOURCE_SHELF"));
           data[iCount][7] =
               (subRs.getString("TARGET_SHELF") == null ? "" :
                subRs.getString("TARGET_SHELF"));
           iCount++;
         }
         subRs.close();
         st1.close();
         for (int j = 0; j < iCount; j++) {
           for (int i = 0; i < 8; i++) {
             pdfCell = new PdfPCell(new Phrase(data[j][i], font));
             if (i == 2 || i == 3 || i == 4) {
               pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
             }
             else if (i == 1 || i == 0) {
               pdfCell
                   .setHorizontalAlignment(Element.ALIGN_CENTER);
             }
             else {
               pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             }
             pdftblNotice.addCell(pdfCell);
           }
         }

         document.add(pdftblNotice);*/

        //报表尾
        tblNotice.setBorderWidth(0);
        tblNotice.setPadding(0);
        tblNotice.setSpacing(0);
        tblNotice.setDefaultCellBorder(0);
        tblNotice.addCell(new Phrase("库房保管员:", font));
        tblNotice.addCell(new Phrase(" ", font));
        cell = new Cell(new Phrase(" ", font));
        cell.setColspan(4);
        tblNotice.addCell(cell);
        tblNotice.addCell(new Phrase("分捡员:", font));
        tblNotice.addCell(new Phrase(" ", font));
        //          document.add(p1);
        //          document.add(p3);
        //			    document.add(p4);

        document.add(tblNotice);

        success = true;
        //setFileName(filename);
      }
      rs.close();
      //关闭document
      document.close();
      fileName = filename;

    }
    catch (Exception e) {
     e.printStackTrace();

    }
    finally {
      try {
        if (rs1 != null)
          rs1.close();
        if (subRs != null)
          subRs.close();
        if (stmt != null)
          stmt.close();
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();

      }
      catch (Exception e) {
        e.printStackTrace();
      }

    }
    return success;
  }

  //执行打印程序
  /*public void exePrint(String fileName) throws Exception {

  }*/

  public static void main(String[] args) {
    Connection conn = null;
    try {
      conn = getConnection();
      ShippingNoticePDFGenerator pdf = new ShippingNoticePDFGenerator(conn);
      //pdf.genOrderShipping("Z20080726200",2);
      pdf.generatorPdf("Z20080816552");
      //pdf.genSupplyPDF("Z20050915411");

    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
    finally {
      if (conn != null) {
        try {
          conn.close();
        }
        catch (Exception e) {}
      }
    }

  }

  public ShippingNoticePDFGenerator(Connection conn) {
    this.conn = conn;

  }

  public static Connection getConnection() throws SQLException {
    // 初始化数据源实例
    try {
      return DBManager2.getConnection();
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    }
  }

  private Table getFoot() throws Exception {
    //发货总计
    float amount1 = 0.0f; //应付货款
    float amountblNotice = 0.0f; //应付运费
    float amount3 = 0.0f; //其他费用
    float amount4 = 0.0f; //已付款
    float amountblShip = 0.0f; //总计应付款
    amount1 = sn.goods_fee;
    amountblNotice = sn.delivery_fee;
    amount3 = -sn.APPEND_FEE;
    amount4 = sn.SHIPPING_TOTAL - sn.SHIPPING_SUM;
    amountblShip = sn.SHIPPING_SUM;
    int[] columnsWidth = new int[4];
    columnsWidth[0] = 15;
    columnsWidth[1] = 20;
    columnsWidth[2] = 18;
    columnsWidth[3] = 20;
    
    Table tblFoot = new Table(columnsWidth.length);
    tblFoot.setWidth(100.0f);
    tblFoot.setWidths(columnsWidth);
    tblFoot.setBorderWidth(0);
    //tblFoot.setPadding(0);
    //tblFoot.setSpacing(0);
    tblFoot.setDefaultCellBorder(0);
    
    Cell cell = new Cell(new Phrase("付款方式: " + sn.payment,subjectFont));
    cell.setColspan(4);
    tblFoot.addCell(cell);
    
    cell = new Cell(new Phrase(" ",subjectFont));
    cell.setColspan(4);
    tblFoot.addCell(cell);
    
    tblFoot.addCell(new Phrase("本次订单总金额:", font));
    cell = new Cell(new Phrase(
        String.valueOf(sn.goods_fee), font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    tblFoot.addCell(new Phrase("原帐户金额:", font));
    //cell = new Cell(new Phrase(df.format(sn.MEMBER_DEPOSIT), font));
    cell = new Cell(new Phrase(String.valueOf(sn.OLD_MEMBER_DEPOSIT), font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    
    tblFoot.addCell(new Phrase("VIP优惠金额:", font));
    cell = new Cell(new Phrase(" ", font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    tblFoot.addCell(new Phrase("本次使用金额:", font));
    cell = new Cell(new Phrase(String.valueOf(sn.payed_money), font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    
    tblFoot.addCell(new Phrase("礼券抵扣金额:", font));
    cell = new Cell(new Phrase(String.valueOf(-amount3), font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    tblFoot.addCell(new Phrase("目前帐户余额:", font));
    //cell = new Cell(new Phrase(df.format(sn.MEMBER_DEPOSIT), font));
    cell = new Cell(new Phrase(String.valueOf(sn.MEMBER_DEPOSIT), font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    
    tblFoot.addCell(new Phrase("运费金额:", font));
    cell = new Cell(new Phrase(String.valueOf(
        amountblNotice), font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    tblFoot.addCell(new Phrase("原帐户积分:", font));
    cell = new Cell(new Phrase("" + sn.OLD_YEAR_EXP, font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    
    tblFoot.addCell(new Phrase("包装费:", font));
    cell = new Cell(new Phrase(String.valueOf(
        sn.package_fee), font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    tblFoot.addCell(new Phrase("本次获得积分:", font));
    cell = new Cell(new Phrase("" + sn.created_year_exp, font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    
    tblFoot.addCell(new Phrase("付款方式折扣金额:", font));
    cell = new Cell(new Phrase(String.valueOf(sn.discount_fee), font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    
    tblFoot.addCell(new Phrase("当前帐户积分:", font));
    cell = new Cell(new Phrase("" + sn.MEMBER_YEAR_EXP, font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tblFoot.addCell(cell);
    
    tblFoot.addCell(new Phrase("实际收款金额:", boldFont));
    cell = new Cell(new Phrase(String.valueOf(amountblShip), boldFont));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setColspan(3);
    tblFoot.addCell(cell);
    
    cell = new Cell(new Phrase(" ",font));
    cell.setColspan(4);
    tblFoot.addCell(cell);
    
    cell = new Cell(new Phrase("重要信息:",font));
    cell.setColspan(4);
    tblFoot.addCell(cell);
    
    cell = new Cell(new Phrase(sn.remark==null?"":sn.remark.replaceAll("<br>", "\r\n").replaceAll("</br>","\r\n") , font));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setColspan(4);
    tblFoot.addCell(cell);
    
    cell = new Cell(new Phrase("打印日期: " + sdf.format(today) ,font));
    cell.setColspan(4);
    tblFoot.addCell(cell);
    
    //cell = new Cell(new Phrase("送货公司: " ,font));
    //cell.setColspan(4);
    //tblFoot.addCell(cell);
    
    cell = new Cell(new Phrase("感谢您选购Mmuses产品 " ,font));
    cell.setColspan(4);
    tblFoot.addCell(cell);
    
    cell = new Cell(new Phrase("  " ,font));
    cell.setColspan(4);
    tblFoot.addCell(cell);
    
    cell = new Cell(new Phrase("如需退换货，请务必事先与售后服务人员联系（客服电话：4008-180-181）:" +
    		"——如您选择邮政退货，请退货至专用信箱：上海邮政信箱  438-028     邮编：200438   上海艾柏时尚服饰有限公司  收 " +
    		"——如您选择快递退货，请与我公司客服联系，我公司安排快递公司上门处理。" +
    		"快递送货地址为：公司名： 上海艾柏时尚服饰有限公司；地址：上海真陈路1000号园区大厦1楼 ；电话：021-36162946 " ,font));
    cell.setColspan(4);
    tblFoot.addCell(cell);

    /*cell = new Cell(new Phrase("备注:", font));
    cell.setColspan(8);
    tblFoot.addCell(cell);

    if (sn.is_comments != null) {
      int i1 = sn.is_comments.indexOf("<<");
      int i2 = sn.is_comments.lastIndexOf(">>");
      if (i1 == -1) {
        cell = new Cell(new Phrase(sn.is_comments, font));
        cell.setColspan(8);
        tblFoot.addCell(cell);
      }
      else {
        String c1 = sn.is_comments.substring(i1, i2 + 2);
        if (i1 > 0) {
          cell = new Cell(new Phrase(sn.is_comments.substring(0, i1), font));
          cell.setColspan(8);
          tblFoot.addCell(cell);
        }
        else {
          cell = new Cell(new Phrase(sn.is_comments.substring(i2 + 2), font));
          cell.setColspan(8);
          tblFoot.addCell(cell);

        }

        //缺货道歉信
        cell = new Cell(new Phrase("亲爱的会员:", font));
        cell.setColspan(8);
        tblFoot.addCell(cell);
        cell = new Cell(new Phrase("    您好!非常感谢您订购九久的商品,由于您的支持才让我们有今天的成绩.",
                                   font));
        cell.setColspan(8);
        tblFoot.addCell(cell);
        cell = new Cell(new Phrase("我们很抱歉的通知您,您订单中的商品" + c1 +
                                   "由于非常畅销，而导致暂时缺货，希望能得到您的谅解!", font));
        cell.setColspan(8);
        tblFoot.addCell(cell);
      }
    }*/
    
    

    return tblFoot;
  }

  public Table getHeader() throws Exception {

    Table tblHeader = new Table(1);
    tblHeader.setWidth(100.0f);

    //			产生发货条码图片JPEG
    String message = null;
    String imgName = null;
    message = sn.barcode;
    imgName = "barcode.jpeg";
    if (message != null) {
      BarCode bc = new BarCode();
      bc.code = message;
      bc.barType = bc.CODE128;
      bc.autoSize = false;
      bc.setSize(120, 65);
      barCodeEncoder bce = new barCodeEncoder(bc, "JPEG", imgName);
      sn.jpeg = Image.getInstance(imgName);
    }
    //END



    //add 表头的内容
    tblHeader.setBorderWidth(0);
    tblHeader.setDefaultCellBorder(0);
    Cell cell = null;
    /*cell = new Cell (new Phrase(" ",posFont));
    cell.setColspan(4);
    tblHeader.addCell(cell);
    cell = new Cell(new Phrase("批号:              " + sn.lot, font));
    tblHeader.addCell(cell);
    cell = new Cell(new Phrase("收货人:" + sn.contact, font));
    tblHeader.addCell(cell);
    cell = new Cell(new Phrase("邮编:" + sn.post_code, font));
    tblHeader.addCell(cell);*/
    sn.jpeg.setAlignment(Image.ALIGN_JUSTIFIED);
    cell = new Cell(sn.jpeg);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    //cell.setRowspan(4);
    tblHeader.addCell(cell);

    /*cell = new Cell(
        new Phrase("运货方式:    "
                   + (sn.delivery_type_name), font));
    tblHeader.addCell(cell);
    cell = new Cell(new Phrase("地址:" + sn.address, font));
    cell.setColspan(2);
    tblHeader.addCell(cell);

    cell = new Cell(new Phrase("城市:              "
                               + (sn.city), font));
    tblHeader.addCell(cell);
    cell = new Cell(new Phrase("电话:" + sn.phone, font));
    tblHeader.addCell(cell);
    cell = new Cell(new Phrase("电话:" +
                               (null == sn.family_phone ? " " : sn.family_phone),
                               font));
    tblHeader.addCell(cell);
    cell = new Cell(new Phrase("总计应付:"
                               + df.format(sn.SHIPPING_SUM), boldFont));
    cell.setColspan(3);
    tblHeader.addCell(cell);*/

    return tblHeader;
  }

  public void setSn(SnEntity sn, ResultSet rs) throws Exception {
    sn.doc_id = rs.getInt("ID");
    sn.THIS_USED_EMONEY = rs.getFloat("PAYED_EMONEY"); //本次使用的E元
    sn.payed_money = rs.getDouble("payed_money");
    sn.OLD_MEMBER_DEPOSIT = rs.getFloat("OLD_MEMBER_DEPOSIT"); //本单前会员的帐户余额
    sn.MEMBER_DEPOSIT = sn.OLD_MEMBER_DEPOSIT - (float) sn.payed_money; //现在的帐户余额
    sn.OLD_MEMBER_EXP = rs.getInt("OLD_MEMBER_EXP"); //本单前会员的累计积分
    sn.created_MEMBER_EXP = rs.getInt("created_MEMBER_EXP"); //本次产生的累计积分
    sn.member_exp = sn.OLD_MEMBER_EXP + sn.created_MEMBER_EXP; //本次累计积分
    //OLD_EMONEY = rs.getFloat("OLD_EMONEY"); //本单前会员的E元

    //created_emoney = rs.getFloat("created_emoney");           //本次产生的E元
    //eMoney = rs.getFloat("eMoney"); // 现在e元
    sn.OLD_YEAR_EXP = rs.getInt("OLD_YEAR_EXP"); //本单前会员的年度积分
    sn.created_year_exp = rs.getInt("created_year_exp"); //本次产生的年度积分
    sn.MEMBER_YEAR_EXP = sn.OLD_YEAR_EXP + sn.created_year_exp; //年度积分
    sn.SHIPPING_SUM = rs.getFloat("shipping_sum"); //应付款
    sn.APPEND_FEE = rs.getFloat("append_fee"); //其他费用
    sn.SHIPPING_TOTAL = rs.getFloat("shipping_total"); //发货单总额
    sn.SHIPPTINGTOTICES_MONEY = rs.getFloat("shipptingtotices_money"); //发货单货款
    sn.entry_fee = rs.getFloat("entry_fee");
    sn.is_voice = rs.getInt("is_invoice"); //是否需要发票
    sn.is_comments = rs.getString("comments");
    sn.comments_type = rs.getInt("shippingnotices_category");
    sn.delivery_type = rs.getInt("delivery_type");
    sn.package_fee = rs.getFloat("package_fee");
    sn.MEMBER_ARREARAGE = rs.getFloat("MEMBER_ARREARAGE");
    sn.goods_fee = rs.getFloat("goods_fee");

    sn.barcode = rs.getString("barcode");
    sn.delivery_fee = rs.getFloat("delivery_fee"); //取得送货费
    sn.comments = rs.getString("comments") == null ? " " : rs
        .getString("comments"); //取得备注信息

    sn.contact = rs.getString("CONTACT");
    sn.orderNumber = rs.getString("ORDER_NUMBER");
    sn.card_id = rs.getString("CARDID");
    sn.mb_name = rs.getString("MEMBER_NAME");
    sn.family_phone = rs.getString("family_phone");
    sn.post_code = rs.getString("POSTCODE");
    sn.company_phone = rs.getString("company_phone");
    sn.delivery_type_name = rs.getString("delivery_type_name");
    sn.address = rs.getString("ADDRESS");
    sn.city = rs.getString("city");
    sn.phone = rs.getString("PHONE");
    sn.phone1 = rs.getString("PHONE1");
    sn.remark = rs.getString("remark");
    sn.lot = rs.getString("lot");
    sn.invoice_title = rs.getString("invoice_title");
    // add zhux 20080830
    if(sn.is_voice == 1 && sn.invoice_title == null) {
    	sn.invoice_title = "个人";
    }
    sn.payment = rs.getString("payment_name");
    sn.discount_fee = rs.getDouble("discount_fee");
  }

  public Table getHeader2() throws Exception {

    int[] columnWidth = new int[2];
    columnWidth[0] = 40;
    columnWidth[1] = 60;
    
    Table tblNotice = new Table(columnWidth.length);
    tblNotice.setWidth(100.0f);
    tblNotice.setWidths(columnWidth);
    tblNotice.setBorderWidth(0);
    tblNotice.setPadding(0);
    tblNotice.setSpacing( -1);
    tblNotice.setDefaultCellBorder(0);
    
    Cell cell = null;
    //cell = new Cell(new Phrase(" ", posFont));
    //cell.setColspan(8);
    //tblNotice.addCell(cell);
    //cell = new Cell(new Phrase(" ", posFont));
    //cell.setColspan(8);
   //blNotice.addCell(cell);
    //cell = new Cell(new Phrase("发货回执", subjectFont));
    //cell.setColspan(8);
    //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //tblNotice.addCell(cell);
    cell = new Cell(new Phrase("订货信息", boldFont));
    //cell.setColspan(2);
    tblNotice.addCell(cell);
    cell = new Cell(new Phrase("收货信息", boldFont));
    //cell.setColspan(2);
    tblNotice.addCell(cell);
    
    tblNotice.addCell(new Phrase("订货人: "+ sn.mb_name,font));
    //tblNotice.addCell(new Phrase(,font));
    tblNotice.addCell(new Phrase("收货人: "+ sn.contact,font));
    //tblNotice.addCell(new Phrase(,font));
    
    tblNotice.addCell(new Phrase("会员号: " +sn.card_id, font));
    //tblNotice.addCell(new Phrase( , font));
    tblNotice.addCell(new Phrase("收货地址: "+ sn.city + " " + sn.address, font));
    //Cell addrCell = new Cell(new Phrase(sn.address, font));
    //tblNotice.addCell(addrCell);
    
    tblNotice.addCell(new Phrase("订单号: " + sn.orderNumber, font));
    tblNotice.addCell(new Phrase("邮编: " + sn.post_code, font));
    
    cell = new Cell(new Phrase("是否需要发票: " + ( sn.is_voice == 1 ? "是" : "否" ), font));
    tblNotice.addCell(cell);
    tblNotice.addCell(new Phrase("收货人电话: " + (sn.phone1==null?"":sn.phone1),font));
    
    tblNotice.addCell(new Phrase("发票抬头: " + (sn.invoice_title==null?"":sn.invoice_title)	,font));
    tblNotice.addCell(new Phrase("收货人手机: " + sn.phone,font));
    
    //cell = new Cell(sn.jpeg);
    //cell.setColspan(2);
    //cell.setRowspan(5);
    //tblNotice.addCell(cell);
    //tblNotice.addCell(new Phrase("姓名:", font));
    //String phone = "";
    /*if (sn.family_phone == null) {
      phone = sn.company_phone;
    }
    else {
      phone = sn.family_phone;
    }
    tblNotice.addCell(new Phrase(sn.contact, font));
    tblNotice.addCell(new Phrase("电话1:", font));
    tblNotice.addCell(new Phrase( (sn.phone), font));
    tblNotice.addCell(new Phrase("电话2:", font));
    tblNotice.addCell(new Phrase("" + phone, font));
    tblNotice.addCell(new Phrase("地址:", font));
    Cell addrCell = new Cell(new Phrase(sn.address,
                                        font));
    addrCell.setColspan(5);
    tblNotice.addCell(addrCell);
    
    cell = new Cell(new Phrase(sn.post_code, font));
    cell.setColspan(2);
    tblNotice.addCell(cell);
    
    tblNotice.addCell(cell);
    cell = new Cell(new Phrase(, font));
    cell.setColspan(2);
    tblNotice.addCell(cell);
    cell = new Cell(new Phrase("客户留言:" + (sn.remark == null ? "" : sn.remark),
                               font));
    cell.setColspan(6);
    tblNotice.addCell(cell);
    
    tblNotice.addCell(new Phrase("实收货款:", font));
    tblNotice.addCell(new Phrase(" ", font));
    tblNotice.addCell(new Phrase("送货员:", font));
    tblNotice.addCell(new Phrase(" ", font));
    tblNotice.addCell(new Phrase("客户签名:", font));
    tblNotice.addCell(new Phrase(" ", font));
    tblNotice.addCell(new Phrase("签收日期:", font));
    tblNotice.addCell(new Phrase(" ", font));
    cell = new Cell(new Phrase("总计应付:"
                               + df.format(sn.SHIPPING_SUM), boldFont));
    cell.setColspan(8);
    tblNotice.addCell(cell);
    cell = new Cell(new Phrase(" ",boldFont));
    cell.setColspan(8);
    tblNotice.addCell(cell);*/
    
    
    return tblNotice;
  }

  public Table getTable4() throws Exception {

    int[] columnWidth = new int[8];
    columnWidth[0] = 10;
    columnWidth[1] = 20;
    columnWidth[3] = 20;
    columnWidth[5] = 20;
    columnWidth[6] = 10;
    columnWidth[7] = 20;
    columnWidth[4] = 10;
    columnWidth[2] = 8;
    Table tblline = new Table(8);
    tblline.setWidth(100.0f);
    tblline.setWidths(columnWidth);
    tblline.setBorderWidth(0);
    tblline.setPadding(0);
    tblline.setSpacing(0);
    tblline.setDefaultCellBorder(0);
    Cell cell = new Cell(new Phrase("上海艾柏时尚服饰有限公司", font));
    cell.setColspan(8);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    tblline.addCell(cell);
     cell = new Cell(new Phrase("发货清单", subjectFont));
    cell.setColspan(8);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    tblline.addCell(cell);
    cell = new Cell(new Phrase(" ", subjectFont));
    cell.setColspan(8);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    tblline.addCell(cell);
    /*tblline.addCell(new Phrase("订单号:", font));
    tblline.addCell(new Phrase(sn.orderNumber, font));
    tblline.addCell(new Phrase("会员号:", font));
    tblline.addCell(new Phrase(sn.card_id, font));
    tblline.addCell(new Phrase("会员姓名:", font));
    tblline.addCell(new Phrase(sn.mb_name, font));
    //cell = new Cell(sn.jpeg);
    //cell.setColspan(2);
    //cell.setRowspan(2);
    //tblline.addCell(cell);
    tblline.addCell(new Phrase("打印日期:", font));
    tblline.addCell(new Phrase(sdf.format(today), font));
    //tblline.addCell(new Phrase(" ", font));
    //cell = new Cell(new Phrase(" ", font));
    //cell.setColspan(3);
    //tblline.addCell(cell);*/
    return tblline;
  }

  private HashMap parseContent(ResultSet rs) throws Exception {
    HashMap ret = new HashMap();

    ArrayList tb = new ArrayList();
    ArrayList tb_back = new ArrayList();
    float[] pdfColumnWidth = new float[9];
    pdfColumnWidth[0] = 40.0f;
    pdfColumnWidth[1] = 40.0f;
    pdfColumnWidth[2] = 80.0f;
    pdfColumnWidth[3] = 20.0f;
    pdfColumnWidth[4] = 20.0f;
    pdfColumnWidth[5] = 20.0f;
    pdfColumnWidth[6] = 20.0f;
    pdfColumnWidth[7] = 20.0f;
    pdfColumnWidth[8] = 30.0f;
    PdfPTable pdfTblLine = new PdfPTable(pdfColumnWidth);
    pdfTblLine.setWidthPercentage(100.0f);
    //Table back = new Table(4);
    //back.setWidth(100f);
    //back.setPadding(0);
    //back.setSpacing(0);
    int j = 0,i=0;
    int total_qty = 0;
    double total_goodsfee = 0;
    int addSet = 0;
    while (rs.next()) {
      /*if (j == 12) {
        i++;
        j = 0;
        tb.add(pdfTblLine);
        //tb_back.add(back);
      }*/
     if (j == 0) {
        pdfTblLine = new PdfPTable(pdfColumnWidth);
        pdfTblLine.setWidthPercentage(100.0f);
        pdfTblLine.getDefaultCell().setHorizontalAlignment(
            Element.ALIGN_CENTER);
        pdfTblLine.getDefaultCell().setVerticalAlignment(
            Element.ALIGN_MIDDLE);
        pdfTblLine.setHeaderRows(1);
        //pdfTblLine.addCell(new Phrase(" ", font));
        pdfTblLine.addCell(new Phrase("货架号", font));
        pdfTblLine.addCell(new Phrase("条形码", font));
        pdfTblLine.addCell(new Phrase("产品名称", font));
        pdfTblLine.addCell(new Phrase("尺码", font));
        pdfTblLine.addCell(new Phrase("颜色", font));
        pdfTblLine.addCell(new Phrase("数量", font));
        pdfTblLine.addCell(new Phrase("市场价", font));
        pdfTblLine.addCell(new Phrase("售价", font));
        pdfTblLine.addCell(new Phrase("小计", font));

        //back = new Table(4);
        //back.setWidth(100f);

      }
      ItemEntity item = setItem(rs);
      
      if( item.sellType == -77 ) {
    	  if(addSet == 0) {
    		  addSet = 1;
    		  addSetLine(pdfTblLine,item);
    	  } else {
    	  
    	  }
    	  
      } else {
    	  addSet = 0;
      }
      
      //PdfPCell pdfCell = new PdfPCell(new Phrase(String.valueOf((i*12)+j+ 1) , font));
      //pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      //pdfTblLine.addCell(pdfCell);
      PdfPCell pdfCell = new PdfPCell(new Phrase(item.shelf_no, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      pdfTblLine.addCell(pdfCell);
      pdfCell = new PdfPCell(new Phrase(item.barcode, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      pdfTblLine.addCell(pdfCell);
      pdfTblLine.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfCell = new PdfPCell(new Phrase(""
                                        + item.item_name + " " +
                                        (null == item.comments ? " " :
                                         item.comments), font));
      pdfTblLine.addCell(pdfCell);
      
      pdfCell = new PdfPCell(new Phrase("" + item.size_name, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      
      pdfCell = new PdfPCell(new Phrase("" + item.color_name, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      
      pdfCell = new PdfPCell(new Phrase("" + item.quantiy, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      if(item.sellType != -77) {
	      pdfCell = new PdfPCell(new Phrase(
	          String.valueOf(item.std_price),font));
      } else {
    	  pdfCell = new PdfPCell(new Phrase(
    	          String.valueOf(0),font));
      }
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      
      if(item.sellType != -77) {
	      pdfCell = new PdfPCell(new Phrase(
	          String.valueOf(item.price),
	          font));
      } else {
    	  pdfCell = new PdfPCell(new Phrase(
    	          String.valueOf(0),
    	          font));
      }
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      if(item.sellType != -77) {
	      pdfCell = new PdfPCell(new Phrase(
	          df.format(item.price * item.quantiy), font));
      } else {
    	  pdfCell = new PdfPCell(new Phrase(
    	          String.valueOf(0), font));
      }
      
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);

      
      total_qty += item.quantiy;
      total_goodsfee += (item.price * item.quantiy);
      j++;
    }

    //while (j<12)  {
      //back.addCell(new Phrase(" ", posFont));
    //  j++;
    //}

    tb.add(pdfTblLine);
    //tb_back.add(back);

    ret.put("content", tb);
    //ret.put("back", tb_back);

    /*int[] acolumnWidth = new int[6];
    acolumnWidth[0] = 30;
    acolumnWidth[1] = 120;
    acolumnWidth[2] = 20;
    acolumnWidth[3] = 40;
    acolumnWidth[4] = 40;
    acolumnWidth[5] = 40;*/

    PdfPTable tblAmount = new PdfPTable(pdfColumnWidth);
    tblAmount.setWidthPercentage(100.0f);
    //pdfTblLine = new PdfPTable();
    //tblAmount.stblAmountetWidthPercentage(100.0f);
    tblAmount.getDefaultCell().setHorizontalAlignment(
        Element.ALIGN_CENTER);
    tblAmount.getDefaultCell().setVerticalAlignment(
        Element.ALIGN_MIDDLE);
    //tblAmount.setBorderWidth(0);
    //tblAmount.setPadding(0);
    //tblAmount.setSpacing(0);
    //tblAmount.setDefaultCellBorder(0);
    //tblAmount.setWidths(acolumnWidth);
    PdfPCell cell = new PdfPCell(new Phrase("总计 ", font));
    cell.setColspan(3);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    tblAmount.addCell(cell);
    cell = new PdfPCell(new Phrase("  ", font));
    tblAmount.addCell(cell);
    cell = new PdfPCell(new Phrase("  ", font));
    tblAmount.addCell(cell);
    
    cell = new PdfPCell(new Phrase(String.valueOf(total_qty), font));
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    tblAmount.addCell(cell);
    cell = new PdfPCell(new Phrase("  ", font));
    tblAmount.addCell(cell);
    cell = new PdfPCell(new Phrase("  ", font));
    tblAmount.addCell(cell);
    
    cell = new PdfPCell(new Phrase(df.format(total_goodsfee), font));
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    tblAmount.addCell(cell);
    
    ret.put("total", tblAmount);
    return ret;
  }

  /**
   * 如果是套装打印时
   * 增加套装产品行
   * @param pdfTblLine
   * @param partItem
   * @throws Exception
   */
  private void addSetLine(PdfPTable pdfTblLine,ItemEntity partItem) throws Exception {
	  ItemEntity item = new ItemEntity();
	  String sql = "select t2.*,t1.order_id from ord_lines t1 join prd_item t2 on t1.set_code = t2.itm_code where t1.id = ?";
	  PreparedStatement ps = conn.prepareStatement(sql);
	  ps.setLong(1, partItem.orderLineId);
	  ResultSet rs = ps.executeQuery();
	  String set_code = "";
	  long order_id = 0;
	  if(rs.next()) {
		  set_code = rs.getString("itm_code");
		  item.item_code = rs.getString("itm_barcode");
		  item.item_name = rs.getString("itm_name");
		  item.quantiy = partItem.quantiy;
		  item.std_price = rs.getDouble("standard_price");
		  order_id = rs.getLong("order_id");
	  }
	  rs.close();
	  ps.close();
	  
	  sql = "select sum(price) from ord_lines where order_id = ? and set_code = ?";
	  ps = conn.prepareStatement(sql);
	  ps.setLong(1, order_id);
	  ps.setString(2, set_code);
	  rs = ps.executeQuery();
	  if(rs.next()) {
		  item.price = rs.getDouble(1);
	  }
	  
	  
	  PdfPCell pdfCell = new PdfPCell(new Phrase(item.shelf_no, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      pdfTblLine.addCell(pdfCell);
      pdfCell = new PdfPCell(new Phrase(item.barcode, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      pdfTblLine.addCell(pdfCell);
      pdfTblLine.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfCell = new PdfPCell(new Phrase(""
                                        + item.item_name + " " +
                                        (null == item.comments ? " " :
                                         item.comments), font));
      pdfTblLine.addCell(pdfCell);
      
      pdfCell = new PdfPCell(new Phrase("" + item.size_name, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      
      pdfCell = new PdfPCell(new Phrase("" + item.color_name, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      
      pdfCell = new PdfPCell(new Phrase("" + item.quantiy, font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      pdfCell = new PdfPCell(new Phrase(
          String.valueOf(item.std_price),
          font));
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      
      pdfCell = new PdfPCell(new Phrase(
	          String.valueOf(item.price),
	          font));
      
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
      
      pdfCell = new PdfPCell(new Phrase(
          df.format(item.price * item.quantiy), font));
      
      
      pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pdfTblLine.addCell(pdfCell);
  }
  
  public ItemEntity setItem(ResultSet rs) throws Exception {
    ItemEntity ret = new ItemEntity();
    ret.item_type = rs.getString("catalog_name");
    ret.item_code = rs.getString("ITM_CODE");
    ret.item_name = rs.getString("itm_NAME");
    ret.comments = rs.getString("comments");
    ret.quantiy = rs.getInt("QUANTITY");
    ret.std_price = rs.getDouble("STANDARD_PRICE");
    ret.price = rs.getDouble("PRICE");
    ret.shelf_no = rs.getString("source_shelf");
    ret.color_name = rs.getString("color_name") + ("(") + rs.getString("color_code") + ")";
    ret.size_name = rs.getString("size_name");
    ret.barcode = rs.getString("itm_barcode");
    ret.sellType = rs.getInt("sell_type");
    ret.orderLineId = rs.getLong("ref_order_line_id");
    return ret;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * 产生补货单
   * @param lot
   * @return
   */
  public boolean genSupplyPDF(String lot) {
    boolean ret = false;

    // 1.从数据库取得数据
    SupplyMstEntity data = getSupplyEntityFromDB(lot);
    if (data.getSupply_no() == null || data.getSupply_no().equals("")) {
      return false;
    }

    // 2 使用itext产生pdf
    Document document = null;

    String sql = "";
    String filename = "";
    Cell cell = null; //统一的Cell
    try {
      file_dir = Config.getValue("PDF_FILE_DIR");
      filename = file_dir + "\\" + lot + "B.pdf";
      bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                                      BaseFont.NOT_EMBEDDED);
      subjectFont = new Font(bfChinese, 14, Font.NORMAL);
      headFont = new Font(bfChinese, 12, Font.NORMAL);
      font = new Font(bfChinese, 10, Font.NORMAL);
      document = new Document(PageSize.A4, 20, 20, 10, 10);
      Document.compress = false;
      PdfWriter.getInstance(document, new FileOutputStream(filename));

      Table tblHeader = createHeader(data);
      Table tblBody = createBody(data);

      Phrase phraseHeader = new Phrase("上海艾柏时尚服饰有限公司", font);
      phraseHeader.add(tblHeader);
      HeaderFooter header1 = new HeaderFooter(phraseHeader, false);
      header1.setAlignment(Element.ALIGN_CENTER);
      document.setHeader(header1);

      Phrase phraseFoot = new Phrase("页码：", font);
      HeaderFooter footer = new HeaderFooter(phraseFoot, true);
      footer.setAlignment(Element.ALIGN_RIGHT);
      document.setFooter(footer);
      document.open();
      document.add(tblBody);
      document.close();

      fileName = filename;
      ret = true;
    }
    catch (Exception e) {
      e.printStackTrace();
      ret = false;
    }

    return ret;
  }

  /**
   * 产生补货单的头信息
   * @param data
   * @return
   * @throws Exception
   */
  private Table createHeader(SupplyMstEntity data) throws Exception {
    Table t = new Table(2);
    t.setBorder(0);
    t.setDefaultCellBorder(0);
    t.setWidth(100.0f);
    Cell cell = new Cell(new Phrase("出库补货单", subjectFont));
    cell.setColspan(2);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    t.addCell(cell);

    t.addCell(new Phrase("补货单号： " + data.getSupply_no(), font));
    cell = new Cell(new Phrase("出库单号： " + data.getCorrespond_no(), font));
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    t.addCell(cell);

    return t;
  }

  /**
   * 产生补货单的主体信息
   * @param data
   * @return
   * @throws Exception
   */
  private Table createBody(SupplyMstEntity data) throws Exception {
    Table t = new Table(10);
    float[] widths = {
        1f, 4f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
    t.setWidth(95f);
    t.setWidths(widths);
    t.addCell(new Phrase("货号", font));
    t.addCell(new Phrase("产品名称", font));
    t.addCell(new Phrase("出库量", font));
    t.addCell(new Phrase("目的区域", font));
    t.addCell(new Phrase("目的货架", font));
    t.addCell(new Phrase("现有数量", font));
    t.addCell(new Phrase("来源区域", font));
    t.addCell(new Phrase("来源货架", font));
    t.addCell(new Phrase("现有数量", font));
    t.addCell(new Phrase("补货数量", font));
    ArrayList dtls = data.getDtl();

    int row = 0;
    boolean bHead = false;

    for (int i = 0; i < dtls.size(); i++) {
      SupplyDtlEntity d1 = (SupplyDtlEntity) dtls.get(i);
      if ( row==0) {
        for (int j = i ; j < dtls.size(); j++) {
          SupplyDtlEntity d2 = (SupplyDtlEntity) dtls.get(j);
          if (d2.getItem_code().equals(d1.getItem_code())) {
            row++;
          } else {
            break;
          }
        }
        bHead = true;
      }

      Cell cell;
      if (bHead) {
        cell = new Cell(d1.getItem_code());
        cell.setRowspan(row);
        t.addCell(cell);

        cell = new Cell(new Phrase(d1.getItem_name(), font));
        cell.setRowspan(row);
        t.addCell(cell);

        cell = new Cell(String.valueOf(d1.getCorrespond_qty()));
        cell.setRowspan(row);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t.addCell(cell);
        bHead = false;
      }

      t.addCell(new Phrase(d1.getTarget_region(), font));
      t.addCell(new Phrase(d1.getTarget_shelf(), font));
      cell = new Cell(new Phrase(String.valueOf(d1.getTarget_qty()), font));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      t.addCell(cell);
      t.addCell(new Phrase(d1.getSource_region(), font));
      t.addCell(new Phrase(d1.getSource_shelf(), font));
      cell = new Cell(new Phrase(String.valueOf(d1.getSource_qty()), font));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      t.addCell(cell);
      t.addCell(new Phrase("", font));

      row--;
     }

    return t;
  }

  /**
   * 从数据库中得到补货单信息
   * @param lot
   * @return
   */
  private SupplyMstEntity getSupplyEntityFromDB(String lot) {
    SupplyMstEntity mst = new SupplyMstEntity();
    String sql = " select t1.supply_no,t1.qty,t1.correspond_qty,t1.target_shelf,t1.target_qty,t1.source_shelf,t1.source_qty, "
        + " t2.item_code, t2.name as item_name,t3.region_desc as target_name, t4.region_desc as source_name "
        +
        " from jxc.sto_supply_dtl t1,prd_items t2,jxc.sys_region t3,jxc.sys_region t4"
        + " where t1.correspond_no = '" + lot + "' "
        + " and t1.item_id = t2.item_id and t1.target_region = t3.region_no and t1.source_region =t4.region_no "
        + " order by t1.item_id,t1.target_region,t1.target_shelf ";
    Statement st = null;
    try {
      st = conn.createStatement();
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        SupplyDtlEntity dtl = new SupplyDtlEntity();
        dtl.setQty(rs.getInt("qty"));
        dtl.setCorrespond_no(lot);
        dtl.setCorrespond_qty(rs.getInt("correspond_qty"));
        dtl.setItem_code(rs.getString("item_code"));
        dtl.setItem_name(rs.getString("item_name"));
        dtl.setTarget_region(rs.getString("target_name"));
        dtl.setTarget_shelf(rs.getString("target_shelf"));
        dtl.setTarget_qty(rs.getInt("target_qty"));
        dtl.setSource_region(rs.getString("source_name"));
        dtl.setSource_shelf(rs.getString("source_shelf"));
        dtl.setSource_qty(rs.getInt("source_qty"));
        mst.setSupply_no(rs.getString("supply_no"));
        //mst.setCorrespond_no(dtl.getCorrespond_no());
        mst.getDtl().add(dtl);
      }
      rs.close();
      sql = " select * "
          + " from jxc.sto_supply_mst "
          + " where supply_no = '" + mst.getSupply_no() + "'";
      rs = st.executeQuery(sql);
      if (rs.next()) {
        mst.setSupply_name(rs.getString("supply_name"));
        mst.setSupply_date(rs.getDate("supply_date"));
        mst.setCorrespond_no(lot);
      }
      rs.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        st.close();
      }
      catch (Exception e) {}
    }
    return mst;
  }
}
