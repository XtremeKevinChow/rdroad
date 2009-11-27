package com.magic.app;

import com.magic.utils.*;
import java.sql.*;
import java.util.HashMap;
/**
 * �ĵ�����(�����ĵ����ͳ���)
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class DocType
{

  //�ĵ����͵ĳ�������s_doc_type ���е��ĵ�����ƥ��
  public static final int MEMBER_RECRUITEMENT    = 2010; //��Ա��ļ
  public static final int MEMBER_PROMOTION   = 1510; //��Ա����
  public static final int MEMBER_DEPOSITS   = 2020; //��Ա����
  public static final int MEMBER_UPGRADE   = 2030; //��Ա����
  public static final int MEMBER_CARD   = 2040;  //��Ա����ʧ
  public static final int MEMBER_INQUIRY   = 2050;  //��Աѯ���¼�
  public static final int MEMBER_ORGANIZATION  = 1710; //�����Ա
	public static final int ORGANIZATION_ADDRESS  = 1720; //�����Ա��ַ
  public static final int MEMBER_ADDRESS=1080; //��Ա��ַ
  public static final int DOCUMENT_TEMPLATE = 3090; //�ĵ�ģ��
  public static final int PRODUCT   = 1500; //��Ʒ
	public static final int SET_PRODUCTS   = 1520; //��Ʒ�׼���ϸ
  public static final int MEMBER   = 1000; //��Ա
  public static final int MEMBER_STATUS   = 1010; //��Ա״̬
  public static final int MEMBER_ACTIVITY   = 2000; //��Ա�¼�
  public static final int MEMBER_PAY  = 2020; //��Ա����
  public static final int MEMBER_LOSECARD  = 2040; //��Ա��ʧ
  public static final int MEMBER_CANCEL  = 2090; //��Ա�˻�
  public static final int GET_MBR_GIFT  = 1800; //��Ա������Ʒ
  public static final int ORDER=4000;//��Ա����
  public static final int ORG_ORDER=4100;//�����Ա����
  public static final int PUB_HOUSE  = 10017; //������
  public static final int CATALOG  = 1680; //Ŀ¼
  public static final int CATALOG_LINE=1650;//Ŀ¼��
	public static final int MBR_GIFTS=1810;   //��������Ʒ
  public static final int CATALOG_EDITION  = 10019; //Ŀ¼���
  public static final int PRICELIST=1660;//��Ŀ��
  public static final int PRICELIST_LINE=1670;//��Ŀ����
	public static final int PRICELIST_PROMOTION_LINE = 1671;//�������Ŀ����
  public static final int MEMBER_GROUP=1090;//��Ա��
  public static final int ITEM_CATEGORY=1700;//��Ʒ���
  public static final int PRICELIST_GIFT = 1630;//��Ʒ����
	public static final int PROMOTION_PRICELIST_GIFT = 1631;//������Ʒ����
  public static final int FREE_DELIVERY = 1640;//���ͻ���
  //��������
  public static final int MEMBER_CATEGORIES = 10002;        //��Ա���
  public static final int RECRUITEMENT_CATEGORY = 10007;    //��Ա��ļ��������
  public static final int RECRUITEMENT_TYPE = 10008;        //��Ա��ļ������ϸ����
  public static final int INQUIRY_TYPE = 10023;             //��Աѯ���¼�����
  public static final int INQUIRY_LEVEL = 10024;           //��Աѯ���¼��ȼ�
  public static final int MALPRACTICE_TYPE = 10026;         //��Ա������Ϊ����
  public static final int DELIVERY_TYPE = 10011;            //������������
  public static final int ITEM_TYPE = 10016;                //��Ʒ����
  public static final int ITEM_TAX = 10025;                 //��Ʒ˰��
  public static final int SUPPLIER_DELIVERY_TYPE = 10022;   //��Ӧ���ͻ���ʽ
  public static final int MBR_FREE_COMMITMENT = 30001;      //��Ա����������
  public static final int MBR_EMONEY_RATES = 30002;         //��ԱeԪ��ñ���
  public static final int MBR_POSTCODE = 10029;             //�ʱ��ַά��

  public static final int SERVICE_CONFIG = 50001;           //��������

  public static final int ORDER_DELIVERY= 4010;
  public static final int ORDER_LINE= 4020;
  public static final int MBR_ORDER_CANCEL= 4050;           //����ȡ���¼�

	public static final int ORDERSHIPPING = 4400;             //���������
	public static final int ORDERSHIPPING_LINES = 4410;       //�������ϸ
	public static final int ORDER_SHIPPING_NOTICE = 4300;     //���˵��޸�

  //��֯�ṹ
  public static final int ORG_LOCATION  = 20001; //��˾��ַ
  public static final int DEPARTMENT_TYPE  = 20003; //��˾��������
  public static final int ORG_DEPARTMENT  = 20002; //��˾����
  public static final int EMPLOYEE_TYPE  = 20005; //��Ա����
  public static final int ORG_PERSON = 20004; //��˾��Ա
  public static final int ORG_ROLES = 20006; //��ɫ��Ϣ


  public static final int MEMBER_DRAWBACK  = 2070; //��Ա�˿�
  public static final int MEMBER_LEVEL  = 10006; //��Ա�ȼ�͸֧���
	public static final int MEMBER_RETURN_ORDER = 2080;  //��Ա�˻�
	public static final int MEMBER_ANIMUS_ORDER = 2100;  //�����˻�

	public static final int PROVIDERS = 1530;            //��Ӧ��
	public static final int SETITEM = 5010;              //��װ����
	public static final int GROUP_LIST = 5020;           //������Ʒ��
	public static final int MEMBER_ACTIVITY_SET = 5030;      //��Ա�
	public static final int MEMBER_INQUIRY_SET = 5040;       //δ���Ͷ��
	public static final int GROUP_ORDER = 5050;          //���嶩��
	public static final int CONFIG_KEYS = 5060;          //ҵ���������
  public static final int EMAIL=6001;           //�����ʼ�
  public static final int MEMBER_LETTERS = 5090;   //�ż�ģ���ӡ
  public static final int PERSON_PWD_UPDATE=20008; //�޸��û�����
  public static final int STO_PUR_MST = 20020;     //�ɹ������
  public static final int MBR_INQUIRY = 2051;      //��Աѯ��
  public static final int ORDER_CHANGE= 4200;
  public static final int MBR_GIFT_CERTIFICATE = 22100;   //��Ա��ȯ
  public static final int PRD_UOM = 22180 ;               //��Ʒ��λ
  public static final int MBR_REMITTANCE = 5085;          //������
  public static final int MBR_CARD_UPLOAD = 2061;         //��Ա������
  public static final int MBR_CARD_QUERY = 2065;          //��Ա�������ѯ
  public static final int BARCODE = 1501;                 //�����޸�������

   //����
    public static final int PDF_SELL_ALL=40100;//��Ʒ������ϸ��
    public static final int PDF_SELL_ANALYZE=40101;//��Ʒ�����۱���
    public static final int PDF_SELL_TYPE=40102;//��Ʒ���ͷ�������
    public static final int PDF_SELL_PR=40103;//���ݶ�����Դ����
    public static final int PDF_SELL_OWENER=40104;//���ݲɹ��༭����
    public static final int PDF_SELL_CATELOG=40105;//���ݲ�Ʒ������
    public static final int PDF_SELL_MONEY=40106;//���ݲ�Ʒ�ɱ��۷���
    public static final int PDF_SELL_PROVIDER=40107;//���ݹ�Ӧ�̷���
    public static final int PDF_SELL_DELIVERY=40108;//����������������
    public static final int PDF_ORDER_EVERYDAY=40201;//ÿ�ն���
    public static final int PDF_ORDER_WAITING=40202;//ÿ�ղ�Ʒ�۵���
    public static final int PDF_ORDER_SHORTAGE=40203;//�۵�������
    public static final int PDF_ORDER_ANALYZE=40204;//����������
    public static final int PDF_ORDER_ZONE=40205;//�������������
    public static final int PDF_ORDER_AGE=40206;//�������������
    public static final int PDF_CUSTOM_LEVEL=40301;//��Ա��������ֲ�
    public static final int PDF_CUSTOM_MONEY=40302;//��Ա����������
    public static final int PDF_CUSTOM_LIST=40303;//�����Ա�б�
    public static final int PDF_CUSTOM_POSTCODE=40305;//��Ա���۵���ֲ�
    public static final int PDF_CUSTOM_CITY=40312;//���յ������
    public static final int PDF_CUSTOM_AGE=40314;//��Ա��������ֲ�
    public static final int PDF_CUSTOM_MSC=40322;//��ļ��������
    public static final int PDF_CUSTOM_MSC_AGE=40323;//�����������ļ��������
    public static final int PDF_CUSTOM_GET=40327;//member get member ��Ʒ���۷���
    public static final int PDF_PRICELIST_TOTAL=40400;//Ŀ¼��Ҫ
    public static final int PDF_PRICELIST_QUANTITY=40411;//��������top20
    public static final int PDF_PRICELIST_MONEY=40412;//���۽��top20
    public static final int PDF_PRICELIST_PAGE=40413;//ҳ������top10
    public static final int PDF_PRICELIST_CATEGORY=40421;//���ݲ�Ʒ����
    public static final int PDF_PRICELIST_SMALL=40422;//����С���
    public static final int PDF_PRICELIST_EDITION=40443;//ҳ�����
    public static final int PDF_PRICELIST_COMMON=40444;//��������
    public static final int PDF_PRICELIST_DISCOUNT=40445;//��������
    public static final int PDF_PRICELIST_PRICE=40446;//��Ʒ��Ʒ
    public static final int PDF_PRICELIST_INTRODUCE=40447;//��������Ʒ
    public static final int PDF_PRICELIST_PROMOTION=40448;//Ŀ¼������ʽ
    public static final int PDF_PRICELIST_MSC=40449;//��ҳ����
    public static final int PDF_PERSON_ORDER=40500;//�ͷ�����ͳ��
    public static final int PDF_PERSON_EVENTS=40501;//��Ա�¼�������
    public static final int PDF_STOCK_TYPE=40601;//����Ʒ���ͷ���
    public static final int PDF_STOCK_AGE=40602;//����������
    public static final int PDF_STOCK_PUR=40603;//�ɹ�������
    public static final int PDF_MEMBER_DEPOSIT=40701;//��Ա��ֵ��¼
    public static final int PDF_MEMBER_CARD=40702;//��Ա������֧ͳ�Ʊ�
    public static final int PDF_SELL_ANALYZE_FOREGROUND=40109;//��Ʒ��ǰ̨���۱���
    public static final int PRODUCT_BASE_INFO = 1505;         //��Ʒ������ϢȨ��
  private static HashMap documents=new HashMap();
  private DocumentElement doc=null;

  public DocType(int doc_type)
  {
      //doc=(DocumentElement)documents.get(doc_type+"");
     // if(doc==null)
     // {
          doc=new DocumentElement(doc_type);
          documents.put(doc_type+"",doc);
     // }
  }
   /**
   * �õ��ĵ����͵�����
   * @return  �ĵ�����
   */
    public String getDocName()
    {
        return doc.getDocName();
    }
    
  /**
   * �õ��ĵ��������ֶ�
   * @return Field[]
   */
    public Field[] getFields()
    {
        return doc.getFields();
    }
    
  /**
   * �Ƿ�ֻ��
   * @return boolean
   */
    public boolean isReadonly()
    {
        return doc.isReadonly();
    }
  /**
   * �õ��ĵ�������Դ
   * @return �ĵ�����Դ�����ͼ
   */
    public String getDataSource()
    {
        return doc.getDataSource();
    }
  /**
   * �Ƿ�ȫ��ʹ��
   * @return boolean
   */
    public boolean is_global()
    {
        return doc.is_global();
    }
  /**
   *�õ������ֶ�����
   * @return String
   */
  public String getKeyField()
  {
    return doc.getKeyField();
  }

  public boolean isLogOperator()
  {
    return doc.isLogOperator();
  }


  public boolean isParentDoc()
  {
    return doc.isParentDoc();
  }
}