package com.magic.utils;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


/**
* <p>Title: </p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: </p>
* @author not attributable
* @version 1.0
*/

public class ViewPDF extends BaseServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=GBK";
    private Document document=null;
    private BaseFont bfChinese =null; 
    private Font headFont = null;
    public int aaaaaaaaaaaa=0;
    private Font subjectFont = null;
    private Font font =null;
    private PDFView pv=null;
    //Initialize global variables
    public void init() throws ServletException {
    }

//Process the HTTP Service
    public void service(HttpServletRequest request, HttpServletResponse response) throws
    ServletException, IOException 
    {
         request.setCharacterEncoding("GB2312");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //make a pdf reportTable
            bfChinese =BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            subjectFont = new Font(bfChinese, 14, Font.NORMAL);
            headFont = new Font(bfChinese, 12, Font.NORMAL);
            font = new Font(bfChinese, 10, Font.NORMAL);
            document = new Document(PageSize.A4, 36,36,36,36);
            Document.compress = false;
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            buildPDF();
            // write ByteArrayOutputStream to the ServletOutputStream
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
            out.flush();
        // this is a workaround for a bug in MSIE
        }
        catch (Exception e) {
            System.out.println("Error in " + getClass().getName() + "\n" + e);
        }

    }

    public void buildPDF() 
    {
      try
      {
          
         
          addHeaderFooter();
          document.open();
          addSubject();
          addTableData();
          document.close();
      }
      catch(Exception e)
      {
        System.out.println("Error in buildPDF"+e);
      }
    }        
    
    void addHeaderFooter()
    {
          Date today=new Date();
          Phrase phraseHeader=new Phrase("上海文化实业有限公司           "+today.toString(),font);
          HeaderFooter header = new HeaderFooter(phraseHeader, false);
          document.setHeader(header);
    
          Phrase phraseFoot=new Phrase("页码：",font);
          HeaderFooter footer = new HeaderFooter(phraseFoot, true);
          footer.setBorder(Rectangle.NO_BORDER);
          document.setFooter(footer);
    }
    void addSubject()
    {
        try
        {
           Paragraph p1 = new Paragraph(pv.getSubject(), subjectFont);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setLeading(30.0f);
    
            Paragraph p3 = new Paragraph(pv.getSubtitle(), headFont);
            p3.setAlignment(Element.ALIGN_RIGHT);
            p3.setLeading(20.0f);
            
            document.add(p1);
            //document.add(p2);
            document.add(p3);
        }catch(Exception e)
        {
          System.out.println(e);
        }
       

    }
     
     void addTableData()
     {
        try {
     
          PdfPTable table = new PdfPTable(pv.getCols());
          table.setWidthPercentage(100.0f);
          table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
          table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
          table.getDefaultCell().setLeading(1.4f, 1.4f);
          table.getDefaultCell().setGrayFill(0.9f);
          table.setHeaderRows(1);
          //cell.setHeader(true);
          for(int i=0;i<pv.getCols();i++)
          {
              table.addCell(new Phrase(pv.getData(i,0), font));
          }
          table.setSkipFirstHeader(false);
          table.getDefaultCell().setLeading(1.2f, 1.2f);
          table.getDefaultCell().setGrayFill(0.0f);
          
          for(int i=0;i<pv.getCols();i++)
          {
            for(int j=1;i<pv.getRows();j++)
              table.addCell(new Phrase(pv.getData(i,0), font));
          }
          int recTotal = 0;
          float fundsTotal = 0.00f;
          
          /*table.getDefaultCell().setGrayFill(0.95f);
          PdfPCell cell = new PdfPCell(new Phrase("合 计", font));
          cell.setColspan(2);
          cell.setGrayFill(0.95f);
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
          
          table.addCell(cell);
          table.addCell(new Phrase(recTotal + "", font));
          table.addCell(new Phrase(fundsTotal + "", font));
          table.addCell(new Phrase("", font));*/
          document.add(table);
      }
      catch (Exception e) {
      System.out.println(e);
      }
     }
  
   
          // step 1: we add content to the document
        
      
          
          
          
         
   
          
        

      private PdfPTable createFundsTable(String sdate, String edate) {
      BaseFont bfChinese = null;
      try {
          bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        
          Font headFont = new Font(bfChinese, 12, Font.NORMAL);
          Font font = new Font(bfChinese, 10, Font.NORMAL);
      
      //create table
          PdfPTable table = new PdfPTable(new float[] {0.2f, 0.25f, 0.1f,
          0.1f, 0.25f});
          table.setWidthPercentage(100.0f);
          table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
          table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
          table.getDefaultCell().setLeading(1.4f, 1.4f);
          table.getDefaultCell().setGrayFill(0.9f);
          table.setHeaderRows(1);
          table.addCell(new Phrase("用户名", headFont));
          //table.addCell(new Phrase("e络通SN", headFont));
          table.addCell(new Phrase("用户帐号", headFont));
          table.addCell(new Phrase("充值点数", headFont));
          table.addCell(new Phrase("应收金额", headFont));
          table.addCell(new Phrase("备 注", headFont));
          
          table.setSkipFirstHeader(false);
          table.getDefaultCell().setLeading(1.2f, 1.2f);
          table.getDefaultCell().setGrayFill(0.0f);
          
          
          int recTotal = 0;
          float fundsTotal = 0.00f;
          
          
          table.getDefaultCell().setGrayFill(0.95f);
          PdfPCell cell = new PdfPCell(new Phrase("合 计", headFont));
          cell.setColspan(2);
          cell.setGrayFill(0.95f);
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
          
          table.addCell(cell);
          table.addCell(new Phrase(recTotal + "", headFont));
          table.addCell(new Phrase(fundsTotal + "", headFont));
          table.addCell(new Phrase("", headFont));
      /*}
      else {
      table.addCell("");
      table.addCell("");
      table.addCell("");
      table.addCell("");
      table.addCell("");
      table.addCell("");
      }
      */
      return table;
      }
      catch (Exception e) {
      return null;
      }
      }
      
    
} 