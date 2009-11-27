/*
 * Created on 2005-1-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import com.magic.crm.product.dao.Product2DAO;

/**
 * @author Water
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ItemInfo implements Serializable {
	private static final long serialVersionUID = -2007032700000000001L;
	
	private int max_count = 0;
	
	
	/* �ɹ��۸�(��˰) **/
	private double purchaseingCost = 0.0;
	
	/* �ɹ��۸�(����˰) **/
	protected double unPurchaseingCost = 0.0;
	
	
	/* �ۿۼ� **/
	protected double discountPrice = 0.0;
	
	/* ���ÿ�� **/
	protected int availQty = 0;
	
	/** ���������� */
	protected int availQty2 = 0;
	
	/* (������Ʒ)�ϼ� **/
	protected double groupItemMomey = 0.0;
	
	
	protected double standardPrice = 0;
	protected double item_cost = 0;
	
	protected int isDiscount = 0;
	
	public int getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(int isDiscount) {
		this.isDiscount = isDiscount;
	}
	/*
	 * ���ֲ�ID
	 */
    protected int clubID=0;
    
       
	/* line id */
	protected int lineId = 0;
	
	protected String itemCode = "";
	/* ���� */
	protected String itemName = "";
	/* �������� */
	protected int sellTypeId = -1;
	protected String sellTypeName = "";
	/* ���״̬ */
	protected int stockStatusId = 0;
	protected String stockStatusName = "";
	/* ���� */
	protected int itemQty = 1;
	/* ��λ */
	protected String itemUnit = "";
	/* ���� */
	protected double itemPrice = 0.0;
	/* �������� */
	protected String landDate = null;
	/* ��ƷӦ���� */
	protected double itemMoney = 0.0;
	
	/* ��Ʒ������ ��װ��Ʒ��̯ʹ�� ��ʱsilverprice ����װ�з�̯�۸�,itemsilverprice�ǲ�Ʒ����۸�.���Եõ���̯����*/
	protected double itemSilverPrice = 0.0;
	
	/* ��Ʒ�𿨼� ��װ��Ʒ��̯ʹ��*/
	protected double itemGoldenPrice = 0.0;
	
	/* ������Ա�۸� */
	protected double silverPrice = 0.0;
	
	protected double setSilverPrice = 0.0;
	public double getSetSilverPrice() {
		return setSilverPrice;
	}
	public void setSetSilverPrice(double setSilverPrice) {
		this.setSilverPrice = setSilverPrice;
	}
	/* �𿨻�Ա�۸� */
	protected double goldenPrice = 0.0;
	
	
	/* ��Ʒ���������� */
	protected double floorMoney = 0.0;
	/* ��Ʒ�Ƿ�ѡ���־λ */
	protected boolean selected = false;
	/* ��Ʒ�ɷ�ѡ���־ */
	protected boolean valid = false;
	/* ��Ʒ����Ŀ¼���� */
	protected String catalog = "";
	/* pricelist_id */
	protected int priceListLineId = 0;
	/* �Ƿ�Ϊ������ */
	protected boolean commitment = false;
	/* ���꼴ֹ��־ */
	protected boolean lastSell = false;
	/* �Ƿ���װ��Ʒ */
	protected boolean truss = false;
	/* ���ƷID */
	protected int replacerId = 0;
	/* ��Դ */
	protected boolean required = false;
	/* mbr_get_award�е���Ʒid */
	protected int awardId = 0;
	/* ��Ʒ��������Ʒ�� */
	protected int gift_group_id =0;
	/* ��Ʒ��������Ʒ��*/
	protected String gift_group_name="";
	
	/* ��Ʒ״̬ */
	protected String status = null;
	
	/* �Ƿ�Ԥ�� */
	protected int is_pre_sell = 0;
	
	/* ��ע **/
	protected String comments = "";
	
	/* ����ID **/
	protected int orderId = 0;
	
	/* �������� **/
	protected int shippedQty = 0;
	
	/* �������� **/
	protected int frozenQty = 0;
	
	/* �����Ʒ **/
	protected String frozenItem = "";
	
	/* ��վID **/
	protected int webId = 0;
	
	// ����ļ��Ʒ��id
	//protected int recruitPriceListId = 0;
	
	// ����ļ������
	protected String sectionType = "";
	
	// ����
	protected int groupId=0;
	
	// sku id
	protected int sku_id=0;
	//��ɫ���
	protected String color_code ="";
	//�ߴ���
	protected String size_code="";
	//��ɫ����
	protected String color_name="";
	//�ߴ�����
	protected String size_name="";
	
	//��װƷ����
	protected String set_code="";
	protected double set_price = 0;
	
	protected int set_group_id = 0;
	
	//��Ʒ���
	protected int item_category =0;
	
	//������Ʒ��Ӧ�Ĳ�Ʒ�飬ֻ�����������
	protected ArrayList prom_items = new ArrayList();
	
	//��Ʒ��ѡ�ߴ���
	protected ArrayList<LabelValueBean> sizes = new ArrayList<LabelValueBean>();
	
	//��Ʒ��ѡ��ɫ
	protected ArrayList<LabelValueBean> colors = new ArrayList<LabelValueBean>();
	
	
	public int getItem_category() {
		return item_category;
	}
	public void setItem_category(int item_category) {
		this.item_category = item_category;
	}
	public ArrayList getProm_items() {
		return prom_items;
	}
	public void setProm_items(ArrayList prom_items) {
		this.prom_items = prom_items;
	}
	
	public double getItemSilverPrice() {
		return itemSilverPrice;
	}
	public void setItemSilverPrice(double itemSilverPrice) {
		this.itemSilverPrice = itemSilverPrice;
	}
	public double getItemGoldenPrice() {
		return itemGoldenPrice;
	}
	public void setItemGoldenPrice(double itemGoldenPrice) {
		this.itemGoldenPrice = itemGoldenPrice;
	}
	public int getClubID(){
      	return this.clubID ;
      }
      public void setClubID(int iclubID){
      	this.clubID =iclubID;
      }
      
	public double getItem_cost() {
		return item_cost;
	}
	public void setItem_cost(double item_cost) {
		this.item_cost = item_cost;
	}
	
	public int getMax_count() {
		return max_count;
	}
	public void setMax_count(int max_count) {
		this.max_count = max_count;
	}
	
	public String getSet_code() {
		return set_code;
	}
	public void setSet_code(String set_code) {
		this.set_code = set_code;
	}
	public ArrayList getColors() {
		return colors;
	}
	public void setColors(ArrayList colors) {
		this.colors = colors;
	}
	public ArrayList getSizes() {
		return sizes;
	}
	public void setSizes(ArrayList sizes) {
		this.sizes = sizes;
	}
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public String getColor_code() {
		return color_code;
	}
	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}
	public String getSize_code() {
		return size_code;
	}
	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}
	public String getColor_name() {
		return color_name;
	}
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}
	public String getSize_name() {
		return size_name;
	}
	public void setSize_name(String size_name) {
		this.size_name = size_name;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the sectionType
	 */
	public String getSectionType() {
		return sectionType;
	}
	/**
	 * @param sectionType the sectionType to set
	 */
	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}
	/**
	 * @param itemMoney The itemMoney to set.
	 */
	public void setItemMoney(double itemMoney) {
		this.itemMoney = itemMoney;
	}
	/**
	 * @return Returns the awardId.
	 */
	public int getAwardId() {
		return awardId;
	}
	/**
	 * @param awardId The awardId to set.
	 */
	public void setAwardId(int awardId) {
		this.awardId = awardId;
	}
	/**
	 * @return Returns the required.
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * @param required The required to set.
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
	/**
	 * @return Returns the lineId.
	 */
	public int getLineId() {
		return lineId;
	}
	/**
	 * @param lineId The lineId to set.
	 */
	public void setLineId(int lineId) {
		this.lineId = lineId;
	}
	/**
	 * @return Returns the replacerId.
	 */
	public int getReplacerId() {
		return replacerId;
	}
	/**
	 * @param replacerId The replacerId to set.
	 */
	public void setReplacerId(int replacerId) {
		this.replacerId = replacerId;
	}
	/**
	 * @return Returns the truss.
	 */
	public boolean isTruss() {
		return truss;
	}
	/**
	 * @param truss The truss to set.
	 */
	public void setTruss(boolean truss) {
		this.truss = truss;
	}
	/**
	 * @return Returns the lastSell.
	 */
	public boolean isLastSell() {
		return lastSell;
	}
	/**
	 * @param lastSell The lastSell to set.
	 */
	public void setLastSell(boolean lastSell) {
		this.lastSell = lastSell;
	}
	/**
	 * @return Returns the priceListLineId.
	 */
	public int getPriceListLineId() {
		return priceListLineId;
	}
	/**
	 * @param priceListLineId The priceListLineId to set.
	 */
	public void setPriceListLineId(int priceListLineId) {
		this.priceListLineId = priceListLineId;
	}
	/**
	 * @return Returns the commitment.
	 */
	public boolean isCommitment() {
		return commitment;
	}
	/**
	 * @param commitment The commitment to set.
	 */
	public void setCommitment(boolean commitment) {
		this.commitment = commitment;
	}
	/**
	 * @return Returns the catalog.
	 */
	public String getCatalog() {
		return catalog;
	}
	/**
	 * @param catalog The catalog to set.
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	/**
	 * @return Returns the sellTypeId.
	 */
	public int getSellTypeId() {
		return sellTypeId;
	}
	/**
	 * @param sellTypeId The sellTypeId to set.
	 */
	public void setSellTypeId(int sellTypeId) {
		this.sellTypeId = sellTypeId;
	}
	/**
	 * @return Returns the floorMoney.
	 */
	public double getFloorMoney() {
		return floorMoney;
	}
	/**
	 * @param floorMoney The floorMoney to set.
	 */
	public void setFloorMoney(double floorMoney) {
		this.floorMoney = floorMoney;
	}
	/**
	 * @return Returns the selected.
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected The selected to set.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * @return Returns the valid.
	 */
	public boolean isValid() {
		return valid;
	}
	/**
	 * @param valid The valid to set.
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	/**
	 * @return Returns the goldenPrice.
	 */
	public double getGoldenPrice() {
		return goldenPrice;
	}
	/**
	 * @param goldenPrice The goldenPrice to set.
	 */
	public void setGoldenPrice(double goldenPrice) {
		this.goldenPrice = goldenPrice;
	}
	/**
	 * @return Returns the silverPrice.
	 */
	public double getSilverPrice() {
		return silverPrice;
	}
	/**
	 * @param silverPrice The silverPrice to set.
	 */
	public void setSilverPrice(double silverPrice) {
		this.silverPrice = silverPrice;
	}
	/**
	 * @return Returns the stockStatusId.
	 */
	public int getStockStatusId() {
		return stockStatusId;
	}
	/**
	 * @param stockStatusId The stockStatusId to set.
	 */
	public void setStockStatusId(int stockStatusId) {
		this.stockStatusId = stockStatusId;
	}
	
	/**
	 * @return Returns the itemCode.
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode The itemCode to set.
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return Returns the itemName.
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName The itemName to set.
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return Returns the itemPrice.
	 */
	public double getItemPrice() {
		return itemPrice;
	}
	/**
	 * @param itemPrice The itemPrice to set.
	 */
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	/**
	 * @return Returns the itemQty.
	 */
	public int getItemQty() {
		return itemQty;
	}
	/**
	 * @param itemQty The itemQty to set.
	 */
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	/**
	 * @return Returns the landDate.
	 */
	public String getLandDate() {
		return landDate;
	}
	/**
	 * @param landDate The landDate to set.
	 */
	public void setLandDate(String landDate) {
		this.landDate = landDate;
	}
	/**
	 * @return Returns the sellTypeName.
	 */
	public String getSellTypeName() {
		return sellTypeName;
	}
	/**
	 * @param sellTypeName The sellTypeName to set.
	 */
	public void setSellTypeName(String sellTypeName) {
		this.sellTypeName = sellTypeName;
	}
	/**
	 * @return Returns the stockStatus.
	 */
	public String getStockStatusName() {
		return stockStatusName;
	}
	/**
	 * @param stockStatus The stockStatus to set.
	 */
	public void setStockStatusName(String stockStatusName) {
		this.stockStatusName = stockStatusName;
	}
	/**
	 * @return Returns the unit.
	 */
	public String getItemUnit() {
		return itemUnit;
	}
	/**
	 * @param unit The unit to set.
	 */
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	/**
	 * @return Returns the itemMoney.
	 */
	public double getItemMoney() {
		return getItemPrice() * getItemQty();
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the gift_group_id.
	 */
	public int getGift_group_id() {
		return gift_group_id;
	}
	/**
	 * @param gift_group_id The gift_group_id to set.
	 */
	public void setGift_group_id(int gift_group_id) {
		this.gift_group_id = gift_group_id;
	}
	/**
	 * @return Returns the gift_group_name.
	 */
	public String getGift_group_name() {
		return gift_group_name;
	}
	/**
	 * @param gift_group_name The gift_group_name to set.
	 */
	public void setGift_group_name(String gift_group_name) {
		this.gift_group_name = gift_group_name;
	}
	/**
	 * @return Returns the is_pre_sell.
	 */
	public int getIs_pre_sell() {
		return is_pre_sell;
	}
	/**
	 * @param is_pre_sell The is_pre_sell to set.
	 */
	public void setIs_pre_sell(int is_pre_sell) {
		this.is_pre_sell = is_pre_sell;
	}
	protected int flag=0;
	public void setFlag(int iflag) {
		this.flag = iflag;
	}
	
	public int getFlag() {
		return flag;
	}	
	protected double addy=0;
	public void setAddy(double iaddy) {
		this.addy = iaddy;
	}
	
	public double getAddy() {
		return addy;
	}	
	/*�Ƿ�ת����Ʒ*/
	protected int is_transfer = 0;

	public int getIs_transfer() {
		return is_transfer;
	}
	public void setIs_transfer(int iis_transfer) {
		this.is_transfer = iis_transfer;
	}	
	/*������*/
	protected double prom_level = 0;

	public double getProm_level() {
		return prom_level;
	}
	public void setProm_level(double iprom_level) {
		this.prom_level = iprom_level;
	}	
	
	
	
	
    /**
     * @return Returns the frozenItem.
     */
    public String getFrozenItem() {
        return frozenItem;
    }
    /**
     * @param frozenItem The frozenItem to set.
     */
    public void setFrozenItem(String frozenItem) {
        this.frozenItem = frozenItem;
    }
    /**
     * @return Returns the frozenQty.
     */
    public int getFrozenQty() {
        return frozenQty;
    }
    /**
     * @param frozenQty The frozenQty to set.
     */
    public void setFrozenQty(int frozenQty) {
        this.frozenQty = frozenQty;
    }
    
    /**
     * @return Returns the orderId.
     */
    public int getOrderId() {
        return orderId;
    }
    /**
     * @param orderId The orderId to set.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    /**
     * @return Returns the shippedQty.
     */
    public int getShippedQty() {
        return shippedQty;
    }
    /**
     * @param shippedQty The shippedQty to set.
     */
    public void setShippedQty(int shippedQty) {
        this.shippedQty = shippedQty;
    }
    /**
     * @return Returns the webId.
     */
    public int getWebId() {
        return webId;
    }
    /**
     * @param webId The webId to set.
     */
    public void setWebId(int webId) {
        this.webId = webId;
    }
    /**
     * @return Returns the comments.
     */
    public String getComments() {
        return comments;
    }
    /**
     * @param comments The comments to set.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
	/**
	 * @return the standardPrice
	 */
	public double getStandardPrice() {
		return standardPrice;
	}
	/**
	 * @param standardPrice the standardPrice to set
	 */
	public void setStandardPrice(double standardPrice) {
		this.standardPrice = standardPrice;
	}
	public double getPurchaseingCost() {
		return purchaseingCost;
	}
	public void setPurchaseingCost(double purchaseingCost) {
		this.purchaseingCost = purchaseingCost;
	}
	public double getUnPurchaseingCost() {
		return unPurchaseingCost;
	}
	public void setUnPurchaseingCost(double unPurchaseingCost) {
		this.unPurchaseingCost = unPurchaseingCost;
	}
	public double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public int getAvailQty() {
		return availQty;
	}
	public void setAvailQty(int availQty) {
		this.availQty = availQty;
	}
	public double getGroupItemMomey() {
		return groupItemMomey;
	}
	public void setGroupItemMomey(double groupItemMomey) {
		this.groupItemMomey = groupItemMomey;
	}
	public int getAvailQty2() {
		return availQty2;
	}
	public void setAvailQty2(int availQty2) {
		this.availQty2 = availQty2;
	}
	public double getSet_price() {
		return set_price;
	}
	public void setSet_price(double set_price) {
		this.set_price = set_price;
	}
	
	public ItemInfo() {
		
	}
	
	public ItemInfo(ItemInfo ii) {
		awardId = ii.getAwardId();
		itemCode = ii.getItemCode();
		itemName = ii.getItemName();
		itemSilverPrice = ii.getSilverPrice();
		groupId = ii.getGroupId();
		truss = ii.isTruss();
		sellTypeId = ii.getSellTypeId();
		sellTypeName = ii.getSellTypeName();
		stockStatusName = ii.getStockStatusName();
		itemUnit = ii.getItemUnit();
		colors = ii.getColors();
		sizes = ii.getSizes();
	}
	public int getSet_group_id() {
		return set_group_id;
	}
	public void setSet_group_id(int set_group_id) {
		this.set_group_id = set_group_id;
	}
}
