package com.magic.app;

import com.magic.utils.*;
import java.sql.*;
import java.util.HashMap;
/**
 * 文档类型(包含文档类型常量)
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class DocType
{

  //文档类型的常量，和s_doc_type 表中的文档类型匹配
  public static final int MEMBER_RECRUITEMENT    = 2010; //会员招募
  public static final int MEMBER_PROMOTION   = 1510; //会员促销
  public static final int MEMBER_DEPOSITS   = 2020; //会员付款
  public static final int MEMBER_UPGRADE   = 2030; //会员升级
  public static final int MEMBER_CARD   = 2040;  //会员卡挂失
  public static final int MEMBER_INQUIRY   = 2050;  //会员询问事件
  public static final int MEMBER_ORGANIZATION  = 1710; //团体会员
	public static final int ORGANIZATION_ADDRESS  = 1720; //团体会员地址
  public static final int MEMBER_ADDRESS=1080; //会员地址
  public static final int DOCUMENT_TEMPLATE = 3090; //文档模版
  public static final int PRODUCT   = 1500; //产品
	public static final int SET_PRODUCTS   = 1520; //产品套件明细
  public static final int MEMBER   = 1000; //会员
  public static final int MEMBER_STATUS   = 1010; //会员状态
  public static final int MEMBER_ACTIVITY   = 2000; //会员事件
  public static final int MEMBER_PAY  = 2020; //会员付款
  public static final int MEMBER_LOSECARD  = 2040; //会员挂失
  public static final int MEMBER_CANCEL  = 2090; //会员退会
  public static final int GET_MBR_GIFT  = 1800; //会员介绍赠品
  public static final int ORDER=4000;//会员订单
  public static final int ORG_ORDER=4100;//团体会员订单
  public static final int PUB_HOUSE  = 10017; //出版社
  public static final int CATALOG  = 1680; //目录
  public static final int CATALOG_LINE=1650;//目录行
	public static final int MBR_GIFTS=1810;   //介绍人礼品
  public static final int CATALOG_EDITION  = 10019; //目录版块
  public static final int PRICELIST=1660;//价目表
  public static final int PRICELIST_LINE=1670;//价目表行
	public static final int PRICELIST_PROMOTION_LINE = 1671;//促销活动价目表行
  public static final int MEMBER_GROUP=1090;//会员组
  public static final int ITEM_CATEGORY=1700;//产品类别
  public static final int PRICELIST_GIFT = 1630;//礼品组行
	public static final int PROMOTION_PRICELIST_GIFT = 1631;//促销礼品组行
  public static final int FREE_DELIVERY = 1640;//免送货费
  //参数配置
  public static final int MEMBER_CATEGORIES = 10002;        //会员类别
  public static final int RECRUITEMENT_CATEGORY = 10007;    //会员招募渠道类型
  public static final int RECRUITEMENT_TYPE = 10008;        //会员招募渠道详细类型
  public static final int INQUIRY_TYPE = 10023;             //会员询问事件类型
  public static final int INQUIRY_LEVEL = 10024;           //会员询问事件等级
  public static final int MALPRACTICE_TYPE = 10026;         //会员不良行为类型
  public static final int DELIVERY_TYPE = 10011;            //订单发送渠道
  public static final int ITEM_TYPE = 10016;                //产品类型
  public static final int ITEM_TAX = 10025;                 //产品税率
  public static final int SUPPLIER_DELIVERY_TYPE = 10022;   //供应商送货方式
  public static final int MBR_FREE_COMMITMENT = 30001;      //会员免义务条件
  public static final int MBR_EMONEY_RATES = 30002;         //会员e元获得比例
  public static final int MBR_POSTCODE = 10029;             //邮编地址维护

  public static final int SERVICE_CONFIG = 50001;           //服务配置

  public static final int ORDER_DELIVERY= 4010;
  public static final int ORDER_LINE= 4020;
  public static final int MBR_ORDER_CANCEL= 4050;           //订单取消事件

	public static final int ORDERSHIPPING = 4400;             //配货单主档
	public static final int ORDERSHIPPING_LINES = 4410;       //配货单明细
	public static final int ORDER_SHIPPING_NOTICE = 4300;     //发运单修改

  //组织结构
  public static final int ORG_LOCATION  = 20001; //公司地址
  public static final int DEPARTMENT_TYPE  = 20003; //公司部门类型
  public static final int ORG_DEPARTMENT  = 20002; //公司部门
  public static final int EMPLOYEE_TYPE  = 20005; //人员类型
  public static final int ORG_PERSON = 20004; //公司人员
  public static final int ORG_ROLES = 20006; //角色信息


  public static final int MEMBER_DRAWBACK  = 2070; //会员退款
  public static final int MEMBER_LEVEL  = 10006; //会员等级透支金额
	public static final int MEMBER_RETURN_ORDER = 2080;  //会员退货
	public static final int MEMBER_ANIMUS_ORDER = 2100;  //恶意退货

	public static final int PROVIDERS = 1530;            //供应商
	public static final int SETITEM = 5010;              //套装配置
	public static final int GROUP_LIST = 5020;           //促销产品组
	public static final int MEMBER_ACTIVITY_SET = 5030;      //会员活动
	public static final int MEMBER_INQUIRY_SET = 5040;       //未解决投诉
	public static final int GROUP_ORDER = 5050;          //团体订单
	public static final int CONFIG_KEYS = 5060;          //业务参数设置
  public static final int EMAIL=6001;           //电子邮件
  public static final int MEMBER_LETTERS = 5090;   //信件模板打印
  public static final int PERSON_PWD_UPDATE=20008; //修改用户密码
  public static final int STO_PUR_MST = 20020;     //采购单审核
  public static final int MBR_INQUIRY = 2051;      //会员询问
  public static final int ORDER_CHANGE= 4200;
  public static final int MBR_GIFT_CERTIFICATE = 22100;   //会员礼券
  public static final int PRD_UOM = 22180 ;               //产品单位
  public static final int MBR_REMITTANCE = 5085;          //汇款审核
  public static final int MBR_CARD_UPLOAD = 2061;         //会员卡导入
  public static final int MBR_CARD_QUERY = 2065;          //会员卡导入查询
  public static final int BARCODE = 1501;                 //单独修改条形码

   //报表
    public static final int PDF_SELL_ALL=40100;//产品销售明细表
    public static final int PDF_SELL_ANALYZE=40101;//产品日销售报表
    public static final int PDF_SELL_TYPE=40102;//产品类型分析报表
    public static final int PDF_SELL_PR=40103;//根据订单来源分析
    public static final int PDF_SELL_OWENER=40104;//根据采购编辑分析
    public static final int PDF_SELL_CATELOG=40105;//根据产品类别分析
    public static final int PDF_SELL_MONEY=40106;//根据产品成本价分析
    public static final int PDF_SELL_PROVIDER=40107;//根据供应商分析
    public static final int PDF_SELL_DELIVERY=40108;//根据销售渠道分析
    public static final int PDF_ORDER_EVERYDAY=40201;//每日订单
    public static final int PDF_ORDER_WAITING=40202;//每日产品扣单表
    public static final int PDF_ORDER_SHORTAGE=40203;//扣单分析表
    public static final int PDF_ORDER_ANALYZE=40204;//订单分析表
    public static final int PDF_ORDER_ZONE=40205;//订单地域分析表
    public static final int PDF_ORDER_AGE=40206;//订单年龄分析表
    public static final int PDF_CUSTOM_LEVEL=40301;//会员购书总体分布
    public static final int PDF_CUSTOM_MONEY=40302;//会员购书金额排行
    public static final int PDF_CUSTOM_LIST=40303;//购书会员列表
    public static final int PDF_CUSTOM_POSTCODE=40305;//会员销售地域分布
    public static final int PDF_CUSTOM_CITY=40312;//按照地域分析
    public static final int PDF_CUSTOM_AGE=40314;//会员销售年龄分布
    public static final int PDF_CUSTOM_MSC=40322;//招募渠道分析
    public static final int PDF_CUSTOM_MSC_AGE=40323;//按照年龄和招募渠道分析
    public static final int PDF_CUSTOM_GET=40327;//member get member 礼品销售分析
    public static final int PDF_PRICELIST_TOTAL=40400;//目录概要
    public static final int PDF_PRICELIST_QUANTITY=40411;//销售数量top20
    public static final int PDF_PRICELIST_MONEY=40412;//销售金额top20
    public static final int PDF_PRICELIST_PAGE=40413;//页面销售top10
    public static final int PDF_PRICELIST_CATEGORY=40421;//根据产品大类
    public static final int PDF_PRICELIST_SMALL=40422;//根据小类分
    public static final int PDF_PRICELIST_EDITION=40443;//页面表现
    public static final int PDF_PRICELIST_COMMON=40444;//正常销售
    public static final int PDF_PRICELIST_DISCOUNT=40445;//打折销售
    public static final int PDF_PRICELIST_PRICE=40446;//礼品赠品
    public static final int PDF_PRICELIST_INTRODUCE=40447;//介绍人赠品
    public static final int PDF_PRICELIST_PROMOTION=40448;//目录促销方式
    public static final int PDF_PRICELIST_MSC=40449;//单页促销
    public static final int PDF_PERSON_ORDER=40500;//客服订单统计
    public static final int PDF_PERSON_EVENTS=40501;//会员事件分析表
    public static final int PDF_STOCK_TYPE=40601;//库存产品类型分析
    public static final int PDF_STOCK_AGE=40602;//库存年龄分析
    public static final int PDF_STOCK_PUR=40603;//采购单分析
    public static final int PDF_MEMBER_DEPOSIT=40701;//会员充值记录
    public static final int PDF_MEMBER_CARD=40702;//会员卡费收支统计表
    public static final int PDF_SELL_ANALYZE_FOREGROUND=40109;//产品日前台销售报表
    public static final int PRODUCT_BASE_INFO = 1505;         //产品基本信息权限
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
   * 得到文档类型的名称
   * @return  文档类型
   */
    public String getDocName()
    {
        return doc.getDocName();
    }
    
  /**
   * 得到文档包含的字段
   * @return Field[]
   */
    public Field[] getFields()
    {
        return doc.getFields();
    }
    
  /**
   * 是否只读
   * @return boolean
   */
    public boolean isReadonly()
    {
        return doc.isReadonly();
    }
  /**
   * 得到文档的数据源
   * @return 文档数据源表或视图
   */
    public String getDataSource()
    {
        return doc.getDataSource();
    }
  /**
   * 是否全局使用
   * @return boolean
   */
    public boolean is_global()
    {
        return doc.is_global();
    }
  /**
   *得到主键字段名称
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