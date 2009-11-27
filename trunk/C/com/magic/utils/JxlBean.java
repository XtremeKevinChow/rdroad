package com.magic.utils;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.write.WritableCellFormat;//��ʽ
import jxl.write.WritableFont;//����
import jxl.write.NumberFormat;
import jxl.write.Label;
import jxl.write.Number;



import java.io.File;
import java.util.StringTokenizer;
//import java.io.OutputStream;
/**
 * ����Excel�ļ�����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class JxlBean
{
	private WritableWorkbook wwb=null;//���ӱ��
	private WritableSheet ws=null;//��д��ҳ
	private WritableCellFormat wcf=null;//��Ԫ���ʽ

	public JxlBean()
	{
		
	}
	/**
	*�������ӱ��
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
	*������ҳ
	*/
	public void create_sheet(String title,int page)
	{
		ws=wwb.createSheet(title,page);
	}
	/**
	*���ӵ�Ԫ��
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
	*������ҹرյ��ӱ��
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
    *��ӱ���ͷ
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
     *��ӱ���ͷ+��Ԫ���ʽ
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

   /**���ñ������
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
   *�ϲ���Ԫ��
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
	*�����п�
	*/
	public void setColWidth(int col,int width)
	{
		ws.setColumnView(col,width);
	}
	/**
	*�����и�
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
	    testBean.create_sheet("����",0);
	    testBean.setTitle("����",8);
	    testBean.setRowHeigth(0,500);
	    String header="���,�Һ�,��Һ�,ÿ���˶��";
	    testBean.setHeader(header,8);
	 //   testBean.addCell(new Number(1,2,0.23,JxlBean.percent));
	    testBean.addCell(new Label(2,1,"���"));
	    testBean.close();

	}
}

