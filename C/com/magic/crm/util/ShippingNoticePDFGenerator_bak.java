package com.magic.crm.util;

import java.io.*;
import com.magic.utils.*;
import java.sql.*;
import com.idautomation.linear.*;
import com.idautomation.linear.encoder.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.print.*;

/**
 * ���ɷ�����������ӡ
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 * 
 * modify history
 * name  date  comment
 * zhux  2005-02-17  rewrite
 * 
 */
public class ShippingNoticePDFGenerator_bak {
	private Connection conn = null;
	protected ResultSet rs = null;

	protected ResultSet subRs = null;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //���ڸ�ʽ

	Date today = new Date();

	private String fileName;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public boolean generatorPdf(String lot) {
		Statement stmt = null;
		ResultSet rs = null;
		Statement subSt = null;
		ResultSet subRs = null;
		boolean success = false;
		String sql = "";
		Document document = null;
		BaseFont bfChinese = null;
		Font headFont = null;
		Font subjectFont = null;
		Font font = null;
		Font lineFont = null;
		Font boldFont = null;
		Paragraph paragraph = null;
		String filename = null;
		try {
			
			String file_dir = "";
			file_dir = Config.getValue("PDF_FILE_DIR");
			
			filename = file_dir + "\\" + lot + "F.pdf";
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			subjectFont = new Font(bfChinese, 14, Font.NORMAL);
			headFont = new Font(bfChinese, 12, Font.NORMAL);
			font = new Font(bfChinese, 10, Font.NORMAL);
			lineFont = new Font(bfChinese, 10, Font.BOLD);
			boldFont = new Font(bfChinese, 12, Font.BOLD);
			document = new Document(PageSize.A4, 20, 20, 0, 5);
			Document.compress = false;
			PdfWriter.getInstance(document, new FileOutputStream(filename));
			String lines = "----------------------------------------------------------------------------------";
			lines = lines
					+ "---------------------------------------------------------------";

			float delivery_fee = 0.0f; //�ͻ�����
			String comments = ""; //��ע����
			Cell cell = null; //ͳһ��Cell
			Image jpeg = null; //������ͼƬ
			
			//��Ա������Ϣ��������
			float OLD_MEMBER_DEPOSIT = 0.0f; //����ǰ��Ա���ʻ����
			float MEMBER_DEPOSIT = 0.0f;//���ڵ��ʻ����
			double payed_money = 0.0f; //����ʹ�õ��ʻ����
			
			
			int OLD_MEMBER_EXP = 0; //����ǰ��Ա���ۼƻ���
			int created_MEMBER_EXP = 0; //���β������ۼƻ���
			int OLD_YEAR_EXP = 0; //����ǰ��Ա����Ȼ���
			int created_year_exp = 0; //���β�������Ȼ���
			
			//float OLD_EMONEY = 0; //����ǰ��Ա��EԪ
			float THIS_USED_EMONEY = 0.0f; //����ʹ�õ�EԪ
			//float created_emoney = 0.0f; //���β�����EԪ
			//float eMoney = 0; // ����eԪ
			int member_exp = 0;
			int MEMBER_YEAR_EXP =0;
			        
			float SHIPPING_SUM = 0; //Ӧ����
			float APPEND_FEE = 0; //��������
			float SHIPPING_TOTAL = 0; //�������ܶ�
			float SHIPPTINGTOTICES_MONEY = 0; //����������
			float entry_fee = 0; //�ƿ���
			int is_voice = 0; //�Ƿ���Ҫ��Ʊ
			String is_comments = ""; //ȱ����Ϣ
			int comments_type = 0; //��ע��־
			int use_number = 0; //��ȯʣ�����
			float use_money = 0.0f; //��ȯʣ���ܽ��   
			int delivery_type = 0; //�ͻ���ʽ
			float package_fee = 0; //��װ��
			float MEMBER_ARREARAGE = 0; //��ԱǷ��
			float goods_fee = 0.0f;//������
			

			float[] pdfColumnWidth = new float[7];
			pdfColumnWidth[0] = 30.0f;
			pdfColumnWidth[1] = 30.0f;
			pdfColumnWidth[2] = 110.0f;
			pdfColumnWidth[3] = 20.0f;
			pdfColumnWidth[4] = 20.0f;
			pdfColumnWidth[5] = 20.0f;
			pdfColumnWidth[6] = 30.0f;

			Table tblHeader = new Table(2);
			Table tblNotice = new Table(8);
			PdfPTable pdftblNotice = new PdfPTable(5);
			Table tblShip = new Table(8);
			Table tblline = new Table(8);
			Table tblAmount = new Table(6);
			PdfPTable pdfTblLine = new PdfPTable(pdfColumnWidth);
			Table tblFoot = new Table(8);
			Table tblComments = new Table(1);
			//�����
			Phrase phraseFoot = new Phrase("ҳ�룺", font);
			HeaderFooter footer = new HeaderFooter(phraseFoot, true);
			footer.setBorder(Rectangle.NO_BORDER);
			footer.setBorderWidth(0);
			document.setFooter(footer);
			//��Document
			document.open();

			sql = "Select a.*, b.name as delivery_type_name,c.city,d.card_id as cardid,d.company_phone,d.family_phone,d.name as member_name From ORD_SHIPPINGNOTICES a, s_delivery_type b,s_postcode c, mbr_members d Where a.delivery_type = b.id and substr(a.postcode,0,4) = c.postcode and a.member_id = d.id and a.lot = '" 
				+ lot + "' And a.status>=0 order by a.ref_order_id";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			//Ϊdocument�������
			int doc_id = 0;
			while (rs.next()) {
				doc_id = rs.getInt("ID");
				THIS_USED_EMONEY = rs.getFloat("PAYED_EMONEY"); //����ʹ�õ�EԪ
				payed_money = rs.getDouble("payed_money");
				OLD_MEMBER_DEPOSIT = rs.getFloat("OLD_MEMBER_DEPOSIT"); //����ǰ��Ա���ʻ����
				MEMBER_DEPOSIT = OLD_MEMBER_DEPOSIT - (float)payed_money; //���ڵ��ʻ����
				OLD_MEMBER_EXP = rs.getInt("OLD_MEMBER_EXP"); //����ǰ��Ա���ۼƻ���
				created_MEMBER_EXP = rs.getInt("created_MEMBER_EXP"); //���β������ۼƻ���
				member_exp = OLD_MEMBER_EXP + created_MEMBER_EXP;//�����ۼƻ���
				//OLD_EMONEY = rs.getFloat("OLD_EMONEY"); //����ǰ��Ա��EԪ
				
				//created_emoney = rs.getFloat("created_emoney");           //���β�����EԪ
				//eMoney = rs.getFloat("eMoney"); // ����eԪ
				OLD_YEAR_EXP = rs.getInt("OLD_YEAR_EXP"); //����ǰ��Ա����Ȼ���
				created_year_exp = rs.getInt("created_year_exp"); //���β�������Ȼ���
				MEMBER_YEAR_EXP = OLD_YEAR_EXP + created_year_exp;//��Ȼ���		
				SHIPPING_SUM = rs.getFloat("shipping_sum"); //Ӧ����
				APPEND_FEE = rs.getFloat("append_fee"); //��������
				SHIPPING_TOTAL = rs.getFloat("shipping_total"); //�������ܶ�
				SHIPPTINGTOTICES_MONEY = rs.getFloat("shipptingtotices_money"); //����������  
				entry_fee = rs.getFloat("entry_fee");
				is_voice = rs.getInt("is_invoice"); //�Ƿ���Ҫ��Ʊ
				is_comments = rs.getString("comments");
				comments_type = rs.getInt("shippingnotices_category");
				delivery_type = rs.getInt("delivery_type");
				package_fee = rs.getFloat("package_fee");
				MEMBER_ARREARAGE = rs.getFloat("MEMBER_ARREARAGE");
				goods_fee = rs.getFloat("goods_fee");

				String voice_is = "��";
				if (is_voice == 0) {
					voice_is = "��";
				}
				
				/////////////////////////////////��ȯ--��������ȥ����ȯ
				/*String ref_order_id = rs.getString("ref_order_id");
				String sql1 = "select gift_number,gift_money,person_num from mbr_gift_certificates " +
						"where gift_number = (select gift_number from ord_headers where id = " 
				+ ref_order_id + ")";
				double gift_money = 9.9;
				use_number = 10;
				Statement st = conn.createStatement();
				ResultSet rs3 = st.executeQuery(sql1);
				String gift_number = "";
				if( rs3.next()) {
					gift_number = rs3.getString("gift_number");
					use_number = rs3.getInt("person_num"); //��ȯʣ�����
				}
				rs3.close();
				sql1 = "select num from mbr_gift_ticket_use where mbrid = " + rs.getLong("member_id")
				     + " and ticket_num = '" + gift_number  + "'";
					 
				rs3 = st.executeQuery(sql1);
				if( rs3.next()) {
					use_number = use_number - rs3.getInt(1);
				}
				use_money = (float)(use_number * gift_money);
				rs3.close();
				st.close();*/
				///////////////////////////////////////////////

				document.setPageSize(PageSize.A4);
				document.setMargins(20f, 20f, 0f, 5f);
				document.newPage();

				tblHeader = new Table(4);
				tblNotice = new Table(8);
				pdftblNotice = new PdfPTable(5);
				tblShip = new Table(8);
				tblline = new Table(8);
				pdfTblLine = new PdfPTable(pdfColumnWidth);
				tblAmount = new Table(6);
				tblFoot = new Table(8);
				tblComments = new Table(1);

				//����Table��Width
				tblHeader.setWidth(100.0f);
				tblNotice.setWidth(100.0f);
				tblShip.setWidth(100.0f);
				tblline.setWidth(100.0f);
				tblAmount.setWidth(100.0f);
				tblFoot.setWidth(100.0f);
				pdftblNotice.setWidthPercentage(100.0f);
				pdfTblLine.setWidthPercentage(100.0f);

				//������������ͼƬJPEG
				String message = null;
				String imgName = null;
				message = rs.getString("BARCODE");
				imgName = "barcode.jpeg";
				if (message != null) {
					BarCode bc = new BarCode();
					bc.code = message;
					bc.barType = bc.CODE128;
					bc.autoSize = false;
					bc.setSize(120, 65);
					barCodeEncoder bce = new barCodeEncoder(bc, "JPEG", imgName);
					jpeg = Image.getInstance(imgName);
				}
				//END
				delivery_fee = rs.getFloat("delivery_fee"); //ȡ���ͻ���
				comments = rs.getString("comments") == null ? " " : rs
						.getString("comments"); //ȡ�ñ�ע��Ϣ

				//add ��ͷ������
				tblHeader.setBorderWidth(0);
				tblHeader.setPadding(0);
				tblHeader.setSpacing(0);
				tblHeader.setDefaultCellBorder(0);
				cell = new Cell(new Phrase("�Ϻ��ž�            ", font));
				cell.setColspan(4);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tblHeader.addCell(cell);
				cell = new Cell(new Phrase("����:              " + lot, font));
				tblHeader.addCell(cell);
				cell = new Cell(new Phrase("�ջ���:" + rs.getString("CONTACT"),font));
				tblHeader.addCell(cell);
				cell = new Cell(new Phrase("�ʱ�:" + rs.getString("POSTCODE"),font));
				tblHeader.addCell(cell);
				jpeg.setAlignment(Image.ALIGN_JUSTIFIED);
				cell = new Cell(jpeg);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setRowspan(4);
				tblHeader.addCell(cell);

				cell = new Cell(
						new Phrase("�˻���ʽ:    "
								+ StringUtil.cEmpty(rs
										.getString("delivery_type_name")), font));
				tblHeader.addCell(cell);
				cell = new Cell(new Phrase("��ַ:" + rs.getString("ADDRESS"),font));
				cell.setColspan(2);
				tblHeader.addCell(cell);
				
				cell = new Cell(new Phrase("����:              "
						+ StringUtil.cEmpty(rs.getString("city")), font));
				tblHeader.addCell(cell);
				cell = new Cell(new Phrase("�绰:" + rs.getString("PHONE"),font));
				tblHeader.addCell(cell);
				cell = new Cell(new Phrase("�绰:" + (null==rs.getString("family_phone")?" ":rs.getString("family_phone")),font));
				tblHeader.addCell(cell);
				cell = new Cell(new Phrase("�ܼ�Ӧ��:"
						+ Arith.formatValue(new Float(Arith.roundX(
								SHIPPING_SUM, 1)), "###0.00"), lineFont));
				cell.setColspan(3);
				tblHeader.addCell(cell);
				
				
				
				int[] columnWidth = new int[8];
				columnWidth[0] = 10;
				columnWidth[1] = 20;
				columnWidth[2] = 8;
				columnWidth[3] = 20;
				columnWidth[4] = 8;
				columnWidth[5] = 20;
				columnWidth[6] = 10;
				columnWidth[7] = 20;
				tblNotice.setWidths(columnWidth);
				tblNotice.setBorderWidth(0);
				tblNotice.setPadding(0);
				tblNotice.setSpacing(-1);
				tblNotice.setDefaultCellBorder(0);
				//�����ص�
				cell = new Cell(new Phrase(" ", font));
				cell.setColspan(8);
				tblNotice.addCell(cell);
				cell = new Cell(new Phrase("������ִ", subjectFont));
				cell.setColspan(8);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tblNotice.addCell(cell);
				tblNotice.addCell(new Phrase("��ӡ����:", font));
				tblNotice
						.addCell(new Phrase(sdf.format(today).toString(), font));
				tblNotice.addCell(new Phrase("������:", font));
				tblNotice
						.addCell(new Phrase( (null==rs.getString("ORDER_NUMBER")?" ":rs.getString("ORDER_NUMBER")), font));
				tblNotice.addCell(new Phrase("��Ա��:", font));
				tblNotice
						.addCell(new Phrase("" + rs.getString("CARDID"), font));
				cell = new Cell(jpeg);
				cell.setColspan(2);
				cell.setRowspan(5);
				tblNotice.addCell(cell);
				tblNotice.addCell(new Phrase("����:", font));
				String phone = "";
				if (rs.getString("family_phone") == null) {
					phone = StringUtil.cEmpty(rs.getString("company_phone"));
				} else {
					phone = rs.getString("family_phone");
				}
				tblNotice.addCell(new Phrase(rs.getString("CONTACT"), font));
				tblNotice.addCell(new Phrase("�绰:", font));
				tblNotice.addCell(new Phrase(StringUtil.cEmpty(rs
						.getString("PHONE")), font));
				tblNotice.addCell(new Phrase("�绰:", font));
				tblNotice.addCell(new Phrase(phone, font));
				tblNotice.addCell(new Phrase("��ַ:", font));
				Cell addrCell = new Cell(new Phrase(rs.getString("ADDRESS"),
						font));
				addrCell.setColspan(5);
				tblNotice.addCell(addrCell);
				tblNotice.addCell(new Phrase("�ʱ�:", font));
				cell = new Cell(new Phrase(rs.getString("POSTCODE"), font));
				cell.setColspan(2);
				tblNotice.addCell(cell);
				cell = new Cell(new Phrase("�Ƿ���Ҫ��Ʊ:", font));
				tblNotice.addCell(cell);
				cell = new Cell(new Phrase(voice_is, font));
				cell.setColspan(2);
				tblNotice.addCell(cell);
				cell = new Cell(new Phrase("�ͻ�����:" + (rs.getString("remark") == null?"":rs.getString("remark")),font));
				cell.setColspan(6);
				tblNotice.addCell(cell);
				//�˻���Ϣ
				pdftblNotice.getDefaultCell().setHorizontalAlignment(
						Element.ALIGN_CENTER);
				pdftblNotice.getDefaultCell().setVerticalAlignment(
						Element.ALIGN_MIDDLE);
				pdftblNotice.setHeaderRows(1);
				pdftblNotice.addCell(new Phrase("����", font));
				pdftblNotice.addCell(new Phrase("��Ʒ����", font));
				pdftblNotice.addCell(new Phrase("����", font));
				pdftblNotice.addCell(new Phrase("����", font));
				pdftblNotice.addCell(new Phrase("��Ԫ��", font));
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 5; j++)
						pdftblNotice.addCell(new Phrase("  ", font));
				}
				//����ԭ��
				columnWidth[2] = 15;
				columnWidth[4] = 10;
				tblShip.setWidths(columnWidth);
				tblShip.setBorderWidth(0);
				tblShip.setPadding(0);
				tblShip.setSpacing(0);
				tblShip.setDefaultCellBorder(0);
				tblShip.addCell(new Phrase("����ԭ��:", font));
				cell = new Cell(new Phrase("  ", font));
				cell.setColspan(7);
				tblShip.addCell(cell);
				tblShip.addCell(new Phrase("ʵ�ջ���:", font));
				tblShip.addCell(new Phrase(" ", font));
				tblShip.addCell(new Phrase("�ͻ�Աǩ��:", font));
				tblShip.addCell(new Phrase(" ", font));
				tblShip.addCell(new Phrase("�ͻ�ǩ��:", font));
				tblShip.addCell(new Phrase(" ", font));
				tblShip.addCell(new Phrase("ǩ������:", font));
				tblShip.addCell(new Phrase(" ", font));
				cell = new Cell(new Phrase("�ܼ�Ӧ��:"
						+ Arith.formatValue(new Float(Arith.roundX(
								SHIPPING_SUM, 1)), "###0.00"), lineFont));
				cell.setColspan(8);
				tblShip.addCell(cell);
				//�����嵥 
				columnWidth[2] = 8;
				tblline.setWidths(columnWidth);
				tblline.setBorderWidth(0);
				tblline.setPadding(0);
				tblline.setSpacing(0);
				tblline.setDefaultCellBorder(0);
				cell = new Cell(new Phrase("�Ϻ��ž��Ļ�ʵҵ���޹�˾", font));
				cell.setColspan(8);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tblline.addCell(cell);
				cell = new Cell(new Phrase("�����嵥", subjectFont));
				cell.setColspan(8);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tblline.addCell(cell);
				tblline.addCell(new Phrase("������:", font));
				tblline.addCell(new Phrase(rs.getString("ORDER_NUMBER"), font));
				tblline.addCell(new Phrase("��Ա��:", font));
				tblline.addCell(new Phrase("" + rs.getString("CARDID"), font));
				tblline.addCell(new Phrase("��Ա����:", font));
				tblline.addCell(new Phrase(rs.getString("MEMBER_NAME"), font));
				cell = new Cell(jpeg);
				cell.setColspan(2);
				cell.setRowspan(2);
				tblline.addCell(cell);
				tblline.addCell(new Phrase("��ӡ����:", font));
				tblline.addCell(new Phrase(sdf.format(today).toString(), font));
				tblline.addCell(new Phrase(" ", font));
				cell = new Cell(new Phrase(" ", font));
				cell.setColspan(3);
				tblline.addCell(cell);
				//�����Ϣ
				document.add(tblHeader);
				document.add(tblNotice);
				//document.add(pdftblNotice);
				document.add(tblShip);
				document.add(tblline);

				pdfTblLine.getDefaultCell().setHorizontalAlignment(
						Element.ALIGN_CENTER);
				pdfTblLine.getDefaultCell().setVerticalAlignment(
						Element.ALIGN_MIDDLE);
				pdfTblLine.setHeaderRows(1);
				pdfTblLine.addCell(new Phrase("����", font));
				pdfTblLine.addCell(new Phrase("����", font));
				pdfTblLine.addCell(new Phrase("��Ʒ����", font));
				pdfTblLine.addCell(new Phrase("����", font));
				pdfTblLine.addCell(new Phrase("����", font));
				pdfTblLine.addCell(new Phrase("�ۼ�", font));
				pdfTblLine.addCell(new Phrase("��Ԫ��", font));
				float amount = 0.0f;
				float sumQty = 0.0f;
				int j = 0;
				PdfPCell pdfCell = null;
				sql = "Select a.*,b.item_code,b.name,b.standard_price,c.name as item_type From ORD_SHIPPINGNOTICE_LINES a, prd_items b,s_item_type c Where a.item_id = b.item_id and a.sn_id="
						+ doc_id + " And a.status >=0  and b.item_type = c.id order by b.item_type,b.name";

				subSt = conn.createStatement();
				subRs = subSt.executeQuery(sql);
				while (subRs.next()) {
					if (j == 28) {
						j = 0;
						document.add(pdfTblLine);
						document.setPageSize(PageSize.A4);
						document.setMargins(20f, 20f, 260f, 0f);
						document.newPage();
						document.add(tblline);
						pdfTblLine = new PdfPTable(pdfColumnWidth);
						pdfTblLine.setWidthPercentage(100.0f);
						pdfTblLine.getDefaultCell().setHorizontalAlignment(
								Element.ALIGN_CENTER);
						pdfTblLine.getDefaultCell().setVerticalAlignment(
								Element.ALIGN_MIDDLE);
						pdfTblLine.setHeaderRows(1);
						pdfTblLine.addCell(new Phrase("����", font));
						pdfTblLine.addCell(new Phrase("����", font));
						pdfTblLine.addCell(new Phrase("��Ʒ����", font));
						pdfTblLine.addCell(new Phrase("����", font));
						pdfTblLine.addCell(new Phrase("����", font));
						pdfTblLine.addCell(new Phrase("�ۼ�", font));
						pdfTblLine.addCell(new Phrase("��Ԫ��", font));
					}
					pdfCell = new PdfPCell(new Phrase(subRs
							.getString("ITEM_TYPE"), font));
					pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pdfTblLine.addCell(pdfCell);
					pdfCell = new PdfPCell(new Phrase(subRs
							.getString("ITEM_CODE"), font));
					pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pdfTblLine.addCell(pdfCell);
					pdfTblLine.setHorizontalAlignment(Element.ALIGN_RIGHT);
					pdfCell = new PdfPCell(new Phrase(""
							+ subRs.getString("NAME") + " " +  (null==subRs.getString("comments")? " " :subRs.getString("comments")), font));
					pdfTblLine.addCell(pdfCell);
					pdfCell = new PdfPCell(new Phrase(""
							+ subRs.getInt("QUANTITY"), font));
					pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					pdfTblLine.addCell(pdfCell);
					pdfCell = new PdfPCell(new Phrase(Arith.formatValue(
							new Float(subRs.getFloat("STANDARD_PRICE")),
							"###0.00"), font));
					pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					pdfTblLine.addCell(pdfCell);
					pdfCell = new PdfPCell(new Phrase(Arith.formatValue(
							new Float(subRs.getFloat("PRICE")), "###0.00"),
							font));
					pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					pdfTblLine.addCell(pdfCell);
					pdfCell = new PdfPCell(new Phrase(Arith.formatValue(
							new Float((subRs.getFloat("PRICE") * subRs
									.getInt("QUANTITY"))), "###0.00"), font));
					pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					pdfTblLine.addCell(pdfCell);

					int quantity = 0;
					float price = 0.0f;
					price = subRs.getFloat("price");
					quantity = subRs.getInt("quantity");
					amount = amount + price * quantity;
					sumQty = sumQty + quantity;
					j = j + 1;
				}
				subRs.close();
				subSt.close();
				amount = SHIPPTINGTOTICES_MONEY;
				int[] acolumnWidth = new int[6];
				acolumnWidth[0] = 30;
				acolumnWidth[1] = 120;
				acolumnWidth[2] = 20;
				acolumnWidth[3] = 40;
				acolumnWidth[4] = 40;
				acolumnWidth[5] = 40;

				tblAmount.setBorderWidth(0);
				tblAmount.setPadding(0);
				tblAmount.setSpacing(0);
				tblAmount.setDefaultCellBorder(0);
				tblAmount.setWidths(acolumnWidth);

				cell = new Cell(new Phrase(" ", font));
				tblAmount.addCell(cell);
				cell = new Cell(new Phrase("�ܼ�:", font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tblAmount.addCell(cell);
				cell = new Cell(new Phrase(Arith.formatValue(new Float(sumQty),
						"###0"), font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tblAmount.addCell(cell);
				cell = new Cell(new Phrase(" ", font));
				tblAmount.addCell(cell);
				cell = new Cell(new Phrase("�ܼ�:", font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tblAmount.addCell(cell);
				cell = new Cell(new Phrase(Arith.formatValue(new Float(goods_fee),
						"###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tblAmount.addCell(cell);

				cell = new Cell(new Phrase("����Ӧ������Ϣ����:", font));
				cell.setColspan(5);
				tblAmount.addCell(cell);

				//�����ܼ�
				float amount1 = 0.0f; //Ӧ������
				float amountblNotice = 0.0f; //Ӧ���˷�
				float amount3 = 0.0f; //��������
				float amount4 = 0.0f; //�Ѹ���
				float amountblShip = 0.0f; //�ܼ�Ӧ����
				amount1 = amount;
				amountblNotice = delivery_fee;
				if (APPEND_FEE < 0) {
					amount3 = -APPEND_FEE;
				} else {
					amount3 = APPEND_FEE;
				}
				amount4 = SHIPPING_TOTAL - SHIPPING_SUM;
				amountblShip = SHIPPING_SUM;
				int[] columnsWidth = new int[8];
				columnsWidth[0] = 15;
				columnsWidth[1] = 20;
				columnsWidth[2] = 18;
				columnsWidth[3] = 20;
				columnsWidth[4] = 18;
				columnsWidth[5] = 20;
				columnsWidth[6] = 18;
				columnsWidth[7] = 20;
				tblFoot.setWidths(columnsWidth);
				tblFoot.setBorderWidth(0);
				tblFoot.setPadding(0);
				tblFoot.setSpacing(0);
				tblFoot.setDefaultCellBorder(0);
				tblFoot.addCell(new Phrase("������:", font));
				cell = new Cell(new Phrase(Arith.formatValue(
						new Float(goods_fee), "###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				tblFoot.addCell(new Phrase("ʹ��Ԥ����:", font));
				cell = new Cell(new Phrase(Arith.formatValue(new Float(payed_money), "0.00"),font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				///////////
				/*tblFoot.addCell(new Phrase("����eԪ:", font));
				cell = new Cell(new Phrase(Arith.formatValue(new Float(
						0.0f), "###0.00"), font));*/
				tblFoot.addCell(new Phrase("�����ۼƻ���:", font));
				cell = new Cell(new Phrase("" + created_MEMBER_EXP, font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				tblFoot.addCell(new Phrase("Ŀǰ�ۼƻ���:", font));
				cell = new Cell(new Phrase("" + member_exp, font));
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);

				tblFoot.addCell(new Phrase("���ͷ�:", font));
				cell = new Cell(new Phrase(Arith.formatValue(new Float(
						amountblNotice), "###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				tblFoot.addCell(new Phrase("ʹ��eԪ:", font));
				cell = new Cell(new Phrase(Arith.formatValue(new Float(
						THIS_USED_EMONEY), "###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				/////////////////
				tblFoot.addCell(new Phrase("������Ȼ���:", font));
				cell = new Cell(new Phrase("" + created_year_exp, font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				tblFoot.addCell(new Phrase("Ŀǰ��Ȼ���:", font));
				cell = new Cell(new Phrase("" + MEMBER_YEAR_EXP, font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				tblFoot.addCell(new Phrase("��װ��:", font));
				cell = new Cell(new Phrase(Arith.formatValue(new Float(
						package_fee), "###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				tblFoot.addCell(new Phrase("ʹ����ȯ:", font));
				cell = new Cell(new Phrase(Arith.formatValue(
						new Float(amount3), "###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				/////////////////////////////////////
				/*tblFoot.addCell(new Phrase("��ȯʣ����:", font));
				cell = new Cell(new Phrase(Arith.formatValue(new Float(
						use_money), "###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);*/
				
				tblFoot.addCell(new Phrase("Ŀǰ�ʻ����:", font));
				cell = new Cell(new Phrase(Arith.formatValue(new Float(
						MEMBER_DEPOSIT), "###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(3);
				tblFoot.addCell(cell);
				/*tblFoot.addCell(new Phrase("�ƿ���:", font));
				cell = new Cell(new Phrase(Arith.formatValue(new Float(
						entry_fee), "###0.00"), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				/////////////////////////
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tblFoot.addCell(cell);
				tblFoot.addCell(new Phrase("��ȯ���ô���:", font));
				cell = new Cell(new Phrase(use_number + "", font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(3);
				tblFoot.addCell(cell);*/
				if (MEMBER_ARREARAGE > 0) {
					tblFoot.addCell(new Phrase("�ϴ�Ƿ��:", font));
					cell = new Cell(new Phrase(Arith.formatValue(new Float(
							MEMBER_ARREARAGE), "###0.00"), font));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setColspan(7);
					tblFoot.addCell(cell);
				}
				tblFoot.addCell(new Phrase("�ܼ�Ӧ��:", boldFont));
				if (delivery_type == 1) {
					cell = new Cell(new Phrase(Arith.formatValue(new Float(
							Arith.roundX(amountblShip, 1)), "###0.00"),
							boldFont));
				} else {
					cell = new Cell(
							new Phrase(Arith.formatValue(new Float(Arith.round(
									amountblShip, 2)), "###0.00"), boldFont));
				}
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(7);
				tblFoot.addCell(cell);

				document.add(pdfTblLine);
				document.add(tblAmount);
				document.add(tblFoot);

				tblFoot = new Table(8);
				tblFoot.setWidth(100.0f);
				tblFoot.setBorderWidth(0);
				tblFoot.setPadding(0);
				tblFoot.setSpacing(0);
				tblFoot.setDefaultCellBorder(0);

				cell = new Cell(new Phrase("��ע:", font));
				cell.setColspan(8);
				tblFoot.addCell(cell);

				if (is_comments != null) {
					int i1 = is_comments.indexOf("<<");
					int i2 = is_comments.lastIndexOf(">>");
					if( i1 == -1) {
						cell = new Cell(new Phrase(is_comments, font));
						cell.setColspan(8);
						tblFoot.addCell(cell);
					} else {
						String c1 = is_comments.substring(i1,i2+2);
						if (i1>0) {
							cell = new Cell(new Phrase(is_comments.substring(0,i1), font));
							cell.setColspan(8);
							tblFoot.addCell(cell);
						} else {
							cell = new Cell(new Phrase(is_comments.substring(i2+2), font));
							cell.setColspan(8);
							tblFoot.addCell(cell);
							
						}
						
						//ȱ����Ǹ��
						cell = new Cell(new Phrase("�װ��Ļ�Ա:",font));
						cell.setColspan(8);
						tblFoot.addCell(cell);
						cell = new Cell(new Phrase("    ����!�ǳ���л�������žõ���Ʒ,��������֧�ֲ��������н���ĳɼ�.",font));
						cell.setColspan(8);
						tblFoot.addCell(cell);
						cell = new Cell(new Phrase("���Ǻܱ�Ǹ��֪ͨ��,�������е���Ʒ" + c1 + "���ڷǳ���������������ʱȱ����ϣ���ܵõ������½�!",font));
						cell.setColspan(8);
						tblFoot.addCell(cell);
					
					}
				}
				document.add(tblFoot);
				success = true;
				setFileName(filename);
			}
			//�ر�document
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		} finally {
			try {
				if (subRs != null)
					subRs.close();
				if (subSt != null)
					subSt.close();
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return success;
	}

	//�������������File
	public boolean getOrderShipping(String lot) {
		
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
		Cell cell = null; //ͳһ��Cell
		try {
			
			String file_dir = "";
			file_dir = Config.getValue("PDF_FILE_DIR");
			filename = file_dir + "\\" + lot + "P.pdf";
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
			//��Document
			sql = "Select lot,delivery_type_name,id From VW_ORDER_LOT_MST Where lot = '"
					+ lot + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			//Ϊdocument�������
			String doc_id = "";
			Paragraph p3 = null;
			Paragraph p4 = null;
			while (rs.next()) {

				doc_id = rs.getString("ID");
				tblHeader = new Table(8);
				tblNotice = new Table(8);

				//����Table��Width
				tblHeader.setWidth(100.0f);
				tblNotice.setWidth(100.0f);
				//Add contens

				//			    Paragraph p1 = new Paragraph("���������", subjectFont);
				//          p1.setAlignment(Element.ALIGN_CENTER);
				//          
				//			    String header = "���κ�:        "+rs.getString("lot");
				//			    p3 = new Paragraph(header, font);
				//			    header = "�˻���ʽ:        "+rs.getString("delivery_type_name") + "                                                                 ";
				//			    header = header + "                                   ����:        "+sdf.format(today).toString();
				//			    p4 = new Paragraph(header, font);
				Table t1 = new Table(6);
				t1.setPadding(0);
				t1.setSpacing(0);
				t1.setWidth(100.0f);
				t1.setDefaultCellBorder(0);
				t1.setBorderWidth(0);
				Paragraph p1 = new Paragraph("���������", subjectFont);
				cell = new Cell(p1);
				cell.setColspan(6);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				t1.addCell(cell);

				t1.addCell(new Phrase("���κ�:", font));
				t1.addCell(new Phrase(rs.getString("lot"), font));
				cell = new Cell(new Phrase(" ", font));
				cell.setColspan(4);
				t1.addCell(cell);
				t1.addCell(new Phrase("�˻���ʽ:", font));
				t1
						.addCell(new Phrase(rs.getString("delivery_type_name"),
								font));
				cell = new Cell(new Phrase(" ", font));
				cell.setColspan(2);
				t1.addCell(cell);
				t1.addCell(new Phrase("����:", font));
				t1.addCell(new Phrase(sdf.format(today).toString(), font));

				Phrase phraseHeader = new Phrase("�Ϻ��ž��Ļ�ʵҵ���޹�˾", font);
				phraseHeader.add(t1);
				HeaderFooter header1 = new HeaderFooter(phraseHeader, false);
				header1.setAlignment(Element.ALIGN_CENTER);
				document.setHeader(header1);

				Phrase phraseFoot = new Phrase("ҳ�룺", font);
				HeaderFooter footer = new HeaderFooter(phraseFoot, true);
				//footer.setBorder(Rectangle.NO_BORDER);
				footer.setAlignment(Element.ALIGN_RIGHT);
				document.setFooter(footer);
				document.open();
				//�б�����
				String[] TableName = new String[8];
				TableName[0] = "���";
				TableName[1] = "��Ʒ����";
				TableName[2] = "��Ʒ����";
				TableName[3] = "��Ʒ����";
				TableName[4] = "��λ";
				TableName[5] = "����";
				TableName[6] = "ʵ������";
				TableName[7] = "���ܺ�";
				float[] columnWidth = new float[8];
				columnWidth[0] = 30.0f;
				columnWidth[1] = 40.0f;
				columnWidth[2] = 130.0f;
				columnWidth[3] = 40.0f;
				columnWidth[4] = 25.0f;
				columnWidth[5] = 25.0f;
				columnWidth[6] = 40.0f;
				columnWidth[7] = 130.0f;
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
				for (int i = 0; i < 8; i++) {
					pdftblNotice.addCell(new Phrase(TableName[i], font));
				}
				pdftblNotice.setSkipFirstHeader(false);
				pdftblNotice.getDefaultCell().setLeading(1.2f, 1.2f);
				pdftblNotice.getDefaultCell().setGrayFill(0.0f);

				//------
				sql = "Select count(*) count From VW_ORDER_LOT_DTL Where id='"
						+ doc_id + "' order by item_stock asc";
				st1 = conn.createStatement();
				rs1 = st1.executeQuery(sql);
				int rowCount = 0;
				if (rs1.next()) {
					rowCount = rs1.getInt("count");
				}
				rs1.close();
				st1.close();
				//-------          
				String[][] data = new String[rowCount][8];
				int iCount = 0;
				sql = "Select * From VW_ORDER_LOT_DTL Where id='" + doc_id
						+ "' order by item_stock asc";
				st1 = conn.createStatement();
				subRs = st1.executeQuery(sql);
				while (subRs.next()) {
					data[iCount][0] = "" + (iCount + 1);
					data[iCount][1] = subRs.getString("ITEM_CODE");
					data[iCount][2] = "" + subRs.getString("ITEM_NAME");
					data[iCount][3] = subRs.getString("ITEM_TYPE_NAME");
					data[iCount][4] = subRs.getString("UNIT_NAME");
					data[iCount][5] = "" + subRs.getInt("QUANTITY");
					data[iCount][6] = "";
					data[iCount][7] = ""
							+ (subRs.getString("ITEM_STOCK") == null ? ""
									: subRs.getString("ITEM_STOCK"));
					iCount++;
				}
				subRs.close();
				st1.close();
				PdfPCell pdfCell = null;
				for (int j = 0; j < iCount; j++) {
					for (int i = 0; i < 8; i++) {
						pdfCell = new PdfPCell(new Phrase(data[j][i], font));
						if (i == 2 || i == 3 || i == 4) {
							pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else if (i == 1 || i == 0) {
							pdfCell
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						} else {
							pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						pdftblNotice.addCell(pdfCell);
					}
				}

				//����β
				tblNotice.setBorderWidth(0);
				tblNotice.setPadding(0);
				tblNotice.setSpacing(0);
				tblNotice.setDefaultCellBorder(0);
				tblNotice.addCell(new Phrase("�ⷿ����Ա:", font));
				tblNotice.addCell(new Phrase(" ", font));
				cell = new Cell(new Phrase(" ", font));
				cell.setColspan(4);
				tblNotice.addCell(cell);
				tblNotice.addCell(new Phrase("�ּ�Ա:", font));
				tblNotice.addCell(new Phrase(" ", font));
				//          document.add(p1);
				//          document.add(p3);
				//			    document.add(p4);
				document.add(pdftblNotice);
				document.add(tblNotice);

				success = true;
				setFileName(filename);
			}
			rs.close();
			//�ر�document
			document.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		} finally {
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
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return success;
	}

	//ִ�д�ӡ����
	public void exePrint(String fileName) throws Exception {
		PrintService printService = PrintServiceLookup
				.lookupDefaultPrintService();
		if (printService == null) {
			throw new Exception("��ӡ��û�а�װ");
		}
		String file_dir = "";
		file_dir = Config.getValue("PDF_FILE_DIR");
		String[] command = new String[2];
		command[0] = file_dir + "\\pdfprint.exe";
		command[1] = fileName;
		try {
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		}
	}

	public static void main(String[] args) {
		Connection conn = null;
		try {
			 conn = DBManager2.getConnection();
			ShippingNoticePDFGenerator pdf = new ShippingNoticePDFGenerator(conn);
			//pdf.getOrderShipping("Y20050601156");
			pdf.generatorPdf("Y20050501184");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			if (conn != null ) {
				try {
					conn.close();
				} catch(Exception e) {}
			}
		}
		
	}
	
	public ShippingNoticePDFGenerator_bak(Connection conn) {
		this.conn = conn;
		
	}
	
	
}