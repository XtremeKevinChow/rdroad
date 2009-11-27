package com.magic.utils;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.write.WritableCellFormat;//格式
import jxl.write.WritableFont;//字体
import jxl.write.NumberFormat;
import jxl.write.Label;
import jxl.write.Number;



import java.io.File;
import java.util.StringTokenizer;
//import java.io.OutputStream;
/**
 * 导出Excel文件处理
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class JxlBean
{
	private WritableWorkbook wwb=null;//电子表格
	private WritableSheet ws=null;//可写表页
	private WritableCellFormat wcf=null;//单元格格式

	public JxlBean()
	{
		
	}
	/**
	*创建电子表格
	*/
	public void create(File file)
	{

		try{
			wwb=Workbook.createWorkbook(file);

		}catch(Exception e)
		{
			System.out.println(e);
		}

	}

	public void create(java.io.OutputStream os)
		{

			try{
				wwb=Workbook.createWorkbook(os);

			}catch(Exception e)
			{
				System.out.println(e);
			}

	}
	

	/**
	*创建单页
	*/
	public void create_sheet(String title,int page)
	{
		ws=wwb.createSheet(title,page);
	}
	/**
	*增加单元格
	*/
	public void addCell(Label label)
	{
		try{
			ws.addCell(label);
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void addCell(Number number)
	{
			try{
				ws.addCell(number);
			}catch(Exception e)
			{
				System.out.println(e);
			}
	}


	/**
	*输出并且关闭电子表格
	*/
	public void close()
	{
		try{
			wwb.write();
			wwb.close();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
    /**
    *添加报表头
    */
    public void setHeader(String header,int row)
    {
		int col=0;
		StringTokenizer st=new StringTokenizer(header,",");
		while(st.hasMoreTokens())
		{
			this.addCell(new Label(col,row,st.nextToken()));
			col++;
		}

	}
    /**
     *添加报表头+单元格格式
     */
     public void setHeader(String header,int row,WritableCellFormat wcf)
     {
 		int col=0;
 		StringTokenizer st=new StringTokenizer(header,",");
 		while(st.hasMoreTokens())
 		{
 			this.addCell(new Label(col,row,st.nextToken(),wcf));
 			col++;
 		}

 	}
	public void setHeader(String header,int row,int col)
	{
			int cols=col;
			StringTokenizer st=new StringTokenizer(header,",");
			while(st.hasMoreTokens())
			{
				this.addCell(new Label(cols,row,st.nextToken()));
				cols++;
			}

	}


	public void setHeader(String header)
	    {
			int i=0;
			StringTokenizer st=new StringTokenizer(header,",");
			while(st.hasMoreTokens())
			{
				this.addCell(new Label(i,0,st.nextToken()));
				i++;
			}

	}

   /**设置报表标题
   */
   public void setTitle(String title,int cols)
   {
	   try{
		   jxl.write.WritableFont red = new jxl.write.WritableFont(jxl.write.WritableFont.ARIAL,
		                                           15,
		                                           jxl.write.WritableFont.NO_BOLD,
		                                           false,
		                                           jxl.format.UnderlineStyle.NO_UNDERLINE,
		                                           jxl.format.Colour.BLACK);

		//   jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(red);
	   wcf=new WritableCellFormat(red);
	   wcf.setAlignment(Alignment.CENTRE);
       //wcf.setBorder(Border.ALL,jxl.format.BorderLineStyle.THIN);

	   this.mergeCells(0,0,cols,0);
	   this.addCell(new Label(0,0,title,wcf));
   		}catch(Exception e)
   		{
			System.out.println(e);
		}
   }

   /**
   *合并单元格
   */
   public void mergeCells(int i,int j,int l,int m)
   {
	   try{
		   ws.mergeCells(i,j,l,m);
	   }catch(Exception e)
	   {
		   System.out.println(e);
	   }
   }


	/**
	*设置列宽
	*/
	public void setColWidth(int col,int width)
	{
		ws.setColumnView(col,width);
	}
	/**
	*设置行高
	*/
	public void setRowHeigth(int row,int heigth)
	{
		try{
			ws.setRowView(row,heigth);
		}catch(Exception e)
		{
			System.out.println(e);
		}

	}
	public static void main(String[] args)
	{
        File file=new File("pd222f.xls");
	    JxlBean testBean=new JxlBean();
	    testBean.create(file);
	    testBean.create_sheet("测试",0);
	    testBean.setTitle("测试",8);
	    testBean.setRowHeigth(0,500);
	    String header="你好,我好,大家好,每个人多好";
	    testBean.setHeader(header,8);
	 //   testBean.addCell(new Number(1,2,0.23,JxlBean.percent));
	    testBean.addCell(new Label(2,1,"你好"));
	    testBean.close();

	}
}

