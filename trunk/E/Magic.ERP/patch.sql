Alter Table ord_trans_def Add(user_index Number(4) Default 1  Null);

Alter Table PRD_ITEM Add Constraint PK_PRD_ITEM Primary Key (ITM_CODE);
Create Index IX_PRD_ITEM_1 On PRD_ITEM(ITM_ID);
Alter Table PRD_ITEM Add Constraint UK_PRD_ITEM_1 Unique (ITM_ID);

Alter Table prd_item_sku Add Constraint PK_prd_item_sku Primary Key  (SKU_ID);
Create Index IX_prd_item_sku_1 On prd_item_sku(ITM_ID,Color_Code,Size_Code);
Create Index IX_prd_item_sku_2 On prd_item_sku(ITM_BARCODE);
Alter Table prd_item_sku Add Constraint UK_prd_item_sku_2 Unique (ITM_BARCODE);

Create Index IX_ORD_MOV_LINE_1 On ORD_MOV_LINE(STO_ID_FROM,ORD_NUM);

Alter Table ORD_MOV_LINE Add(STO_ID_FROM Number Null);
Alter Table ord_mov_head Add(FROM_LOC Varchar(8) Null, TO_LOC Varchar(8) Null);

Insert Into ord_status_def(status_id,Ord_Type_Code,status_val,status_text,status_indx)
Values(seq_ord_status_def.nextval,'MV1',1,'新建',1);
Insert Into ord_status_def(status_id,Ord_Type_Code,status_val,status_text,status_indx)
Values(seq_ord_status_def.nextval,'MV1',2,'发布',2);
Insert Into ord_status_def(status_id,Ord_Type_Code,status_val,status_text,status_indx)
Values(seq_ord_status_def.nextval,'MV1',3,'待移库',3);
Insert Into ord_status_def(status_id,Ord_Type_Code,status_val,status_text,status_indx)
Values(seq_ord_status_def.nextval,'MV1',4,'完成',4);

Insert Into s_sell_type(Id,Name,Description,remark,type) Values(-77,'套装优惠',' ',' ',0);
Alter Table magic.ord_shippingnotice_lines Modify(areacode Varchar2(100));
Alter Table magic.ord_shippingnotice_lines Drop Column sto_dtl_id;alter table ord_headers add section varchar2(20);alter table ord_shippingnotices add section varchar2(20); 

Create Or Replace Synonym SEQ_PRD_ITEM_SKU_ID  For MAGIC.SEQ_PRD_ITEM_SKU_ID;
Create Or Replace Synonym SEQ_PRD_ITEM_ID  For MAGIC.SEQ_PRD_ITEM_ID;
Drop Sequence SEQ_PRD_ITEM;
Drop Sequence SEQ_PRD_ITEM_SKU;

drop table BAS_LOGIS_AREA;
Create Table BAS_LOGIS_AREA (
	"LCA_ID" Number   Not Null,
	"LC_ID" Number   Not Null,
	"P_ID" Varchar2(20)   Null,
	"P_NAME" NVarchar2(20)   Null,
	"C_ID" Varchar2(20)   Null,
	"C_NAME" NVarchar2(20)   Null,
	"D_ID" Varchar2(20)   Null,
	"D_NAME" NVarchar2(20)   Null,
	"SVC_AREA" NVarchar2(300)   Null,
	"UN_SVC_AREA" NVarchar2(300)   Null,
	"LCA_NOTE" NVarchar2(300)   Null,
	"HAS_ERR" Number(1)   Null,
	"ERR_MSG" NVarchar2(100)   Null
	, Constraint PK_BAS_LOGIS_AREA Primary Key("LCA_ID")) ;
Drop Sequence SEQ_BAS_LOGIS_AREA;
Create Sequence SEQ_BAS_LOGIS_AREA minvalue 1 maxvalue 999999999999 start with 1 increment by 1 cache 20;

Alter Table prd_item_image Add(image_origial Varchar2(100) Null);
Alter Table prd_item_image Add(IS_DEFAULT Number(1));
Alter Table prd_item_image Modify(
      IMG_BIG Varchar2(100),IMG_MID Varchar2(100),IMG_SMALL Varchar2(100)
);
Alter Table  prd_item_image Modify (img_type Number(8));
Alter Table prd_item_image Modify (color_code Varchar2(6));
Create Sequence SEQ_PRD_ITEM_IMAGE minvalue 1 maxvalue 999999999999 start with 1 increment by 1 cache 20;


--2008-08-28
--单个明细支持多货架发货
Alter Table magic.ord_shippingnotice_lines Modify(areacode Varchar2(100));
Drop Table ORD_SN_LINE_INV;
Create Table ORD_SN_LINE_INV (
	"ID" Number   Not Null,
	"SNDID" Number   Null,
	"STOCK_DTL_ID" Number   Not Null,
	"LOC_CODE" Varchar2(8)   Not Null,
	"AREA_CODE" Varchar2(8)   Not Null,
	"SEC_CODE" Varchar2(8)   Null,
	"QUANTITY" Number(8,2)   Null
	, Constraint PK_ORD_SN_LINE_INV Primary Key("ID")) ;
Drop Sequence SEQ_ORD_SN_LINE_INV;
Create Sequence SEQ_ORD_SN_LINE_INV minvalue 1 maxvalue 999999999999 start with 1 increment by 1 cache 20;
Create Index IX_ORD_SN_LINE_INV_1 On ORD_SN_LINE_INV(SNDID) Tablespace ERP;
Alter Table magic.ord_shippingnotice_lines Drop Column sto_dtl_id;
--f_get_sku_shelf

--2008-08-25
--退换货表结构
Drop Table ORD_RTN_HEAD;
Create Table ORD_RTN_HEAD (
	"ORD_NUM" Varchar2(16)   Not Null,
	"LOC_CODE" Varchar2(8)   Null,
	"ORD_TYPE_CODE" Char(3)   Not Null,
	"MBR_ID" Number   Null,
	"MBR_NAME" NVarchar2(20)   Null,
	"LOGIS_ID" Number   Null,
	"LOGIS_NAME" NVarchar2(20)   Null,
	"ORD_STATUS" Number   Null,
	"IS_AUTO_MATCH" Number(1)   Null,
	"IS_MAL" Number(1)   Null,
	"REF_ORD_ID" Number   Null,
	"REF_ORD_NUM" Varchar2(16)   Null,
	"ORG_ORD_NUM" Varchar2(16)   Null,
	"RSON_ID" Number   Null,
	"RSON_TEXT" NVarchar2(50)   Null,
	"APRV_RSLT" Number(1,0)   Null,
	"APRV_USR" Number   Null,
	"APRV_TIME" Date   Null,
	"APRV_NOTE" NVarchar2(40)   Null,
	"CREATE_USR" Number   Null,
	"CREATE_TIME" Date   Null,
	"ORD_NOTE" NVarchar2(50)   Null,
	"CUR_LINE_NUM" Varchar2(4)   Null
	, Constraint PK_ORD_RTN_HEAD Primary Key("ORD_NUM")) ;
drop table ORD_RTN_LINE;
Create Table ORD_RTN_LINE (
	"ORD_NUM" Varchar2(16)   Not Null,
	"LINE_NUM" Char(4)   Not Null,
	"SKU_ID" Number   Null,
	"TRANS_TYPE_CODE" Char(3)   Not Null,
	"REF_ORD_LINE_ID" Number   Null,
	"DLV_QTY" Number(8,2)   Null,
	"QUANTITY" Number(8,2)   Null,
	"AREA_CODE" Varchar2(8)   Null,
	"SEC_CODE" Varchar2(8)   Null
	, Constraint PK_ORD_RTN_LINE Primary Key("ORD_NUM","LINE_NUM")) ;
--初始数据设置
Delete From inv_trans_type_def Where trans_type_code In ('113','117','133');
Update inv_trans_type_def Set trans_type_text='会员退货',trans_type_def_desc=' ' Where trans_type_code='111';
Update inv_trans_type_def Set trans_type_text='会员换货',trans_type_def_desc=' ' Where trans_type_code='115';
Update inv_trans_type_def Set trans_type_text='物流退货',trans_type_def_desc=' ' Where trans_type_code='131';
Delete From inv_wh_use_cfg Where wh_cfg_code Not In (select trans_type_code From inv_trans_type_def);
Delete From ord_trans_def Where trans_def_id In (4,6);
Delete From ord_trans_def_opt;
Update ord_trans_def Set trans_type_code='115' Where trans_def_id=5;
Update ord_trans_def Set trans_type_code='203' Where trans_def_id=7;
Delete From ord_type_def Where ord_type_code In ('DL3');
Update ord_type_def Set ord_type_text='销售发货' Where ord_type_code='SD0';
Update ord_type_def Set ord_type_text='换货发货' Where ord_type_code='DL2';
--货架号长度由8改为10
Alter Table inv_wh_section Modify (sec_code Varchar2(10));
Alter Table inv_stock_detail Modify (sec_code Varchar2(10));
Alter Table inv_trans_line Modify (sec_code Varchar2(10));
Alter Table ord_dlv_line Modify (sec_code Varchar2(10));
Alter Table ord_inv_chk_line Modify (sec_code Varchar2(10));
Alter Table ord_mov_line Modify (sec_from Varchar2(10));
Alter Table ord_mov_line Modify (sec_to Varchar2(10));
Alter Table ord_rcv_line Modify (sec_code Varchar2(10));
Alter Table ord_sto_in_line Modify (sec_code Varchar2(10));

CREATE OR REPLACE Procedure F_GET_SKU_SHELF (v_sn_line_id In Number) Is
v_area Varchar2(8);
v_section Varchar2(10);
v_stoid Number;
v_qty Number(8,2);
v_sku Number;
Begin
   --对同一个发货明细不能重复调用这个函数，因为ERP会冻结发货数量
   Select l.sku_id,l.quantity Into v_sku,v_qty From ORD_SHIPPINGNOTICE_LINES l Where l.id=v_sn_line_id;
   
   Select sto.area_code,sto.sec_code,sto.stock_dtl_id Into v_area, v_section, v_stoid
   From inv_stock_detail sto
   Where sto.sku_id=v_sku And area_code In(
         --目前先这样简化处理了: 1. ORD_TRANS_DEF_OPT没用上  2. INV_WH_USE_CFG的REQ_ALL_CHILD没用上
         --TODO: 区分正常销售发货和换货发货类型
         Select Distinct whdef.area_code
         From ORD_TRANS_DEF odef
         Inner Join INV_WH_USE_CFG whdef On odef.trans_type_code=whdef.wh_cfg_code
         Where odef.Ord_Type_Code In ('SD0','DL2')
   ) And (sto.stock_qty-sto.frozen_qty)>v_qty And rownum<=1
   Order By (sto.stock_qty-sto.frozen_qty) Asc;

   Update inv_stock_detail Set frozen_qty=frozen_qty+v_qty Where stock_dtl_id=v_stoid;
   Update ord_shippingnotice_lines Set areacode=v_area,sectioncode=v_section,sto_dtl_id=v_stoid
   Where id=v_sn_line_id;

End F_GET_SKU_SHELF;
/


--2008-08-18
Alter Table ORD_RCV_LINE Add(TAX_VAL Number(12, 3) Default 0 Null);
Alter Table INV_TRANS_LINE Add(TAX_VAL Number(12, 3) Default 0 Null);

--2008-08-16
Alter Table ord_ic_head Drop Column box_num;
Alter Table magic.ord_shippingnotices Add(PACKAGE_COUNT Number Default 1 Null);

--2008-08-15
Drop Table EXCEL_TEMPLATE;
Create Table EXCEL_TEMPLATE (
	"ET_ID" Number   Not Null,
	"STATUS" Number   Null,
	"ET_NAME" NVarchar2(20)   Null,
	"FILE_V_PATH" Varchar2(150)   Null
	, Constraint PK_EXCEL_TEMPLATE Primary Key("ET_ID")) ;
Create Sequence SEQ_EXCEL_TEMPLATE minvalue 1 maxvalue 999999999999 start with 10 increment by 1 cache 20;
Alter Table ord_shippingnotices Add(LOGISTIC_ID Number Null);
Alter Table ord_ic_head Add (BOX_NUM Number Null);

CREATE OR REPLACE Function F_GET_SKU_SHELF (v_sku_id In Number ,v_qty In Number)Return Varchar2 Is
v_area Varchar2(8);
v_section Varchar2(8);
v_stoid Number;
Begin
   --对同一个发货明细不能重复调用这个函数，因为ERP会冻结库存数量
   Select sto.area_code,sto.sec_code,sto.stock_dtl_id Into v_area, v_section, v_stoid
   From inv_stock_detail sto
   Where sto.sku_id=v_sku_id And area_code In(
         --目前先这样简化处理了: 1. ORD_TRANS_DEF_OPT没用上  2. INV_WH_USE_CFG的REQ_ALL_CHILD没用上
         --TODO: 区分正常销售发货和换货发货类型
         Select Distinct whdef.area_code
         From ORD_TRANS_DEF odef
         Inner Join INV_WH_USE_CFG whdef On odef.trans_type_code=whdef.wh_cfg_code
         Where odef.Ord_Type_Code In ('SD0','DL2')
   ) And (sto.stock_qty-sto.frozen_qty)>v_qty And rownum<=1
   Order By (sto.stock_qty-sto.frozen_qty) Asc;
   
   Update inv_stock_detail Set frozen_qty=frozen_qty+v_qty Where stock_dtl_id=v_stoid;
   
   Return v_area || ';' || v_section;

End F_GET_SKU_SHELF;
/