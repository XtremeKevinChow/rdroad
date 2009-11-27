/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.magic.crm.order.entity.ShoppingCart2;
import com.magic.crm.order.entity.ShoppingCart;
import javax.servlet.http.HttpServletRequest;

import com.magic.utils.Arith;
import com.magic.crm.common.WebForm;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.entity.TicketMoney;
import com.magic.crm.order.Constants;
import com.magic.crm.util.CodeName;

import org.apache.struts.action.ActionMapping;


/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OrderForm extends WebForm implements java.io.Serializable {
	
	


	ArrayList colors = new ArrayList();
	
	ArrayList sizes = new ArrayList();
	
	ArrayList packages = new ArrayList();
	
	ArrayList mscs = new ArrayList();
	
	public ArrayList getMscs() {
		return mscs;
	}
	public void setMscs(ArrayList mscs) {
		this.mscs = mscs;
	}


	String msc_code = "";
	
	public String getMsc_code() {
		return msc_code;
	}
	public void setMsc_code(String msc_code) {
		this.msc_code = msc_code;
	}


	private int package_type =1;
	
	private int use_deposit = 1;
	
	public int getUse_deposit() {
		return use_deposit;
	}
	public void setUse_deposit(int use_deposit) {
		this.use_deposit = use_deposit;
	}


	private String invoice_title;
	
	private String invoice_number;
	
	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public String getInvoice_number() {
		return invoice_number;
	}
	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
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


	// 购物车对象
	private ShoppingCart2 cart = new ShoppingCart2();
	//团购购物车
	private ShoppingCart orgCart = new ShoppingCart();
	
	public ShoppingCart getOrgCart() {
		return orgCart;
	}
	public void setOrgCart(ShoppingCart orgCart) {
		this.orgCart = orgCart;
	}
	public ShoppingCart2 getCart() {
		return cart;
	}
	public void setCart(ShoppingCart2 cart) {
		this.cart = cart;
	}
	
	
	//(仅用作从页面向Action传递数据)
	private double discount = 0;
	
	/**
	 * @return Returns the discount.
	 */
	public double getDiscount() {
		return (discount == 0.0 ? 1 : discount);
	}

	/**
	 * @param discount
	 *            The discount to set.
	 */

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	
	
/////////////////////////用于充值（到时候要弄掉）//////////////////////////////
	/** 邮局手工充款原因 **/
	private int reason;
	/** 是否升级 **/
	private int auto_level;
	
	/** 是否送礼金 **/
	private int auto_increase;
	
	/** 是否送礼品 **/
	private int auto_gift;

	public int getAuto_Increase() {
		return auto_increase;
	}
	public void setAuto_Increase(int auto_increase) {
		this.auto_increase = auto_increase;
	}
	public int getAuto_Level() {
		return auto_level;
	}
	public void setAuto_Level(int auto_level) {
		this.auto_level = auto_level;
	}
	public int getAuto_Gift() {
		return auto_gift;
	}
	public void setAuto_Gift(int auto_gift) {
		this.auto_gift = auto_gift;
	}
    public int getReason() {
        return reason;
    }
    public void setReason(int reason) {
        this.reason = reason;
    }
/////////////////////////用于充值end（到时候要弄掉）//////////////////////////////
    
    
/////////////////////////用于邮局包裹（到时候要弄掉）//////////////////////////////
   
	private String postPackageCode;

	private String postPackageFee;

	private String postPackageDate;

	private String postPackageWeight;

	/**
	 * @return Returns the postPackageCode.
	 */
	public String getPostPackageCode() {
		return postPackageCode;
	}

	/**
	 * @param postPackageCode
	 *            The postPackageCode to set.
	 */
	public void setPostPackageCode(String postPackageCode) {
		this.postPackageCode = postPackageCode;
	}

	/**
	 * @return Returns the postPackageDate.
	 */
	public String getPostPackageDate() {
		return postPackageDate;
	}

	/**
	 * @param postPackageDate
	 *            The postPackageDate to set.
	 */
	public void setPostPackageDate(String postPackageDate) {
		this.postPackageDate = postPackageDate;
	}

	/**
	 * @return Returns the postPackageFee.
	 */
	public String getPostPackageFee() {
		return postPackageFee;
	}

	/**
	 * @param postPackageFee
	 *            The postPackageFee to set.
	 */
	public void setPostPackageFee(String postPackageFee) {
		this.postPackageFee = postPackageFee;
	}

	/**
	 * @return Returns the postPackageWeight.
	 */
	public String getPostPackageWeight() {
		return postPackageWeight;
	}

	/**
	 * @param postPackageWeight
	 *            The postPackageWeight to set.
	 */
	public void setPostPackageWeight(String postPackageWeight) {
		this.postPackageWeight = postPackageWeight;
	}
    
    
    /////////////////////////用于邮局包裹end//////////////////////////////
	
	/**
     * @return Returns the reasonColl.
     */
    public java.util.Collection getReasonColl() {
        return Constants.reasonColl;
    }
	/* 订单ID */
	private int orderId = 0;

	private int orderType = 0;
	

	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	/**
	 * @param creatorList
	 *            The creatorList to set.
	 */
	public void setCreatorList(List creatorList) {
		this.creatorList = creatorList;
	}

	/**
	 * @param gifts
	 *            The gifts to set.
	 */
	public void setGifts(List gifts) {
		this.gifts = gifts;
	}

	/**
	 * @param items
	 *            The items to set.
	 */
	public void setItems(List items) {
		this.items = items;
	}

	/**
	 * @param prTypes
	 *            The prTypes to set.
	 */
	public void setPrTypes(List prTypes) {
		this.prTypes = prTypes;
	}

	/**
	 * @param statusList
	 *            The statusList to set.
	 */
	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	private String orderNumber = null;
	

	
	/* 会员ID */
	private int mbId = 0;
	
	/* 会员卡 */
	private String cardId;
	
	/* 会员姓名 */
	private String mbName;
	
	/* 会员电话 */
	private String mbTelephone;
	
	/* 会员应付 */
	private double mbPayable;
	
	public double getMbPayable() {
		return mbPayable;
	}
	public void setMbPayable(double mbPayable) {
		this.mbPayable = mbPayable;
	}
	public String getMbTelephone() {
		return mbTelephone;
	}
	public void setMbTelephone(String mbTelephone) {
		this.mbTelephone = mbTelephone;
	}


	//招募msc促销按钮控制
	private boolean isRecruitBtnActive = true;
	
	//
	private String msc = null;
		
	
	/* 暂存架id(仅用作从页面向Action传递数据) */	
	private long queryAwardId = -1;
	
	/* 要添加的商品编号(仅用作从页面向Action传递数据) */
	private String queryItemCode = null;

	/* 要添加的商品数量(仅用作从页面向Action传递数据) */
	private int queryItemQty = 0;
	
	private String queryColorCode = "";
	
	private String querySizeCode = "";

	public String getQueryColorCode() {
		return queryColorCode;
	}
	public void setQueryColorCode(String queryColorCode) {
		this.queryColorCode = queryColorCode;
	}
	public String getQuerySizeCode() {
		return querySizeCode;
	}
	public void setQuerySizeCode(String querySizeCode) {
		this.querySizeCode = querySizeCode;
	}


	/* 准备删除/更新的商品在页面中的序号 */
	private int operateId = -1;
	
	/* 准备删除礼品在页面的销售类型 */
	private int sellTypeId = -1;

	/* 选择的礼物编号(仅用作从页面向Action传递数据) */
	private int selectedGiftId = 0;

	/* 订单产品信息（List <ItemInfo>） */
	private List items = new ArrayList();

	/* 已经选择的礼品信息（List <ItemInfo>） */
	private List gifts = new ArrayList();

	/* 所有礼品信息（List <GiftInfo>） */
	private List allGifts = new ArrayList();

	
	/* 页面礼券编号(仅用作从页面向Action传递数据) */
	private String ticketNumber = null;

	/* 乐透卡密码(仅用作从页面向Action传递数据) */
	private String ticketPassword = null;

	

	/* 可选择的乐透卡代金券组 */
	private List ticketMoney = new ArrayList();
	
	/* 礼券号码（不是幸运卡） */
	private String otherGiftNumber;
	
	/* 礼券密码（不是幸运卡） */
	private String otherGiftPassword = null;
	
	/**
	 * @return Returns the otherGiftNumber.
	 */
	public String getOtherGiftNumber() {
		return otherGiftNumber;
	}
	
	/**
	 * @param otherGiftNumber
	 *  The otherGiftNumber to set.
	 */
	public void setOtherGiftNumber(String otherGiftNumber) {
		this.otherGiftNumber = otherGiftNumber;
	}
	
	public String getOtherGiftPassword() {
		return otherGiftPassword;
	}
	public void setOtherGiftPassword(String otherGiftPassword) {
		this.otherGiftPassword = otherGiftPassword;
	}

	/* 总价 */
	private double totalMoney = 0.0;

	/* 高级查询使用 */
	private String totalMoneyBottom = null;

	private String totalMoneyTop = null;

	/* 应付款 */
	private double payable = 0.0;

	/* 收货人 */
	private String receiptor = null;

	/* 收货人电话 */
	private String receiptorPhone = null;

	/* 收货人地址 */
	private int receiptorAddressId = 0;

	private String receiptorAddress = null;

	/* 收货人邮编 */
	private String receiptorPostCode = null;

	/* 送货方式 */
	private int deliveryTypeId = 0;

	private String deliveryTypeName = null;

	/* 送货费用 */
	private double deliveryFee = 0.0;

	/* 付款方式 */
	private int paymentTypeId = -1000;

	private String paymentTypeName = null;

	///////////////订单其他信息begin///////////////////
	/* 缺货处理方式(2:全部商品到齐发货；3:不等待，取消订单中缺货商品) */
	private int OOSPlan = 2;

	/* 是否需要发票 */
	private String needInvoice = "0";

	/* 备注信息 */
	private String remark = null;
	/* 关联目录 */
	private String catalog;
	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	/**
	 * @return Returns the oOSPlan.
	 */
	public int getOOSPlan() {
		return OOSPlan;
	}

	/**
	 * @param plan
	 *            The oOSPlan to set.
	 */
	public void setOOSPlan(int plan) {
		OOSPlan = plan;
	}

	/**
	 * @return Returns the needInvoice.
	 */
	public String getNeedInvoice() {
		return needInvoice;
	}

	/**
	 * @param needInvoice
	 *            The needInvoice to set.
	 */
	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
///////////////订单其他信息end///////////////////
	
	
	/* 订单来源列表 */
	private List prTypes = new ArrayList();

	/* 选中的订单来源 */
	private int prTypeId = 0;

	private String prTypeName = null;

	/* 订单状态 */
	private int statusId = -1000;

	private String statusName = null;

	/* 高级查询时 订单状态列表 */
	private List statusList = new ArrayList();

	/* 订单分类 */
	private int categoryId = -1000;

	private String categoryName = null;

	/* 创建日期 */
	private String createDate = null;

	/* 高级查询时 */
	private String createDateBottom = null;

	private String createDateTop = null;

	/* 创建人 */
	private int creatorId = 0;

	private String creatorName = null;

	/* 高级查询时 经办人列表 */
	private List creatorList = new ArrayList();

	/* 会员帐户 */
	private String mbMoney = null;

	/* 会员冻结帐户 */
	private String mbFrozenMoney = null;

	/* 购物金额 */
	//private double goodsFee = 0;

	/* 使用帐户金额 */
	//private double orderUse = 0;

	/* 还需支付金额 */
	//private double orderOwe = 0;

	/* 恶意退货次数 */
	//private int mbAnimus_count = 0;

	

	/**
	 * @return Returns the mbFrozenMoney.
	 */
	public String getMbFrozenMoney() {
		return mbFrozenMoney;
	}

	/**
	 * @param mbFrozenMoney
	 *            The mbFrozenMoney to set.
	 */
	public void setMbFrozenMoney(String mbFrozenMoney) {
		this.mbFrozenMoney = mbFrozenMoney;
	}

	/**
	 * @return Returns the mbMoney.
	 */
	public String getMbMoney() {
		return mbMoney;
	}

	/**
	 * @param mbMoney
	 *            The mbMoney to set.
	 */
	public void setMbMoney(String mbMoney) {
		this.mbMoney = mbMoney;
	}

	/**
	 * @return Returns the ticketNumber.
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @param ticketNumber
	 *            The ticketNumber to set.
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	/**
	 * @return Returns the totalMoneyBottom.
	 */
	public String getTotalMoneyBottom() {
		return totalMoneyBottom;
	}

	/**
	 * @param totalMoneyBottom
	 *            The totalMoneyBottom to set.
	 */
	public void setTotalMoneyBottom(String totalMoneyBottom) {
		this.totalMoneyBottom = totalMoneyBottom;
	}

	/**
	 * @return Returns the totalMoneyTop.
	 */
	public String getTotalMoneyTop() {
		return totalMoneyTop;
	}

	/**
	 * @param totalMoneyTop
	 *            The totalMoneyTop to set.
	 */
	public void setTotalMoneyTop(String totalMoneyTop) {
		this.totalMoneyTop = totalMoneyTop;
	}

	/**
	 * @return Returns the createDateBottom.
	 */
	public String getCreateDateBottom() {
		return createDateBottom;
	}

	/**
	 * @param createDateBottom
	 *            The createDateBottom to set.
	 */
	public void setCreateDateBottom(String createDateBottom) {
		this.createDateBottom = createDateBottom;
	}

	/**
	 * @return Returns the createDateTop.
	 */
	public String getCreateDateTop() {
		return createDateTop;
	}

	/**
	 * @param createDateTop
	 *            The createDateTop to set.
	 */
	public void setCreateDateTop(String createDateTop) {
		this.createDateTop = createDateTop;
	}

	/**
	 * @return Returns the creatorList.
	 */
	public List getCreatorList() {
		return creatorList;
	}

	/**
	 * @return Returns the statusList.
	 */
	public List getStatusList() {
		return statusList;
	}

	/**
	 * @return Returns the creatorId.
	 */
	public int getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId
	 *            The creatorId to set.
	 */
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return Returns the creatorName.
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName
	 *            The creatorName to set.
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return Returns the createDate.
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            The createDate to set.
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return Returns the orderNumber.
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            The orderNumber to set.
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return Returns the categoryId.
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            The categoryId to set.
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return Returns the categoryName.
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            The categoryName to set.
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return Returns the statusId.
	 */
	public int getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId
	 *            The statusId to set.
	 */
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return Returns the statusName.
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            The statusName to set.
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return Returns the prTypeId.
	 */
	public int getPrTypeId() {
		return prTypeId;
	}

	/**
	 * @param prTypeId
	 *            The prTypeId to set.
	 */
	public void setPrTypeId(int prTypeId) {
		this.prTypeId = prTypeId;
	}

	/**
	 * @return Returns the prTypeName.
	 */
	public String getPrTypeName() {
		return prTypeName;
	}

	/**
	 * @param prTypeName
	 *            The prTypeName to set.
	 */
	public void setPrTypeName(String prTypeName) {
		this.prTypeName = prTypeName;
	}

	/**
	 * @return Returns the gifts.
	 */
	public List getGifts() {
		return gifts;
	}

	
	/**
	 * @return Returns the deliveryTypeId.
	 */
	public int getDeliveryTypeId() {
		return deliveryTypeId;
	}

	/**
	 * @param deliveryTypeId
	 *            The deliveryTypeId to set.
	 */
	public void setDeliveryTypeId(int deliveryTypeId) {
		this.deliveryTypeId = deliveryTypeId;
	}

	/**
	 * @return Returns the paymentTypeId.
	 */
	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	/**
	 * @param paymentTypeId
	 *            The paymentTypeId to set.
	 */
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	/**
	 * @return Returns the receiptorAddressId.
	 */
	public int getReceiptorAddressId() {
		return receiptorAddressId;
	}

	/**
	 * @param receiptorAddressId
	 *            The receiptorAddressId to set.
	 */
	public void setReceiptorAddressId(int receiptorAddressId) {
		this.receiptorAddressId = receiptorAddressId;
	}

	/**
	 * @return Returns the deliveryFee.
	 */
	public double getDeliveryFee() {
		return deliveryFee;
	}

	/**
	 * @param deliveryFee
	 *            The deliveryFee to set.
	 */
	public void setDeliveryFee(double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	
	/*礼券抵用*/
	double appendFee = 0d;
	
	public double getAppendFee() {
		return appendFee;
	}
	public void setAppendFee(double appendFee) {
		this.appendFee = appendFee;
	}
	/**
	 * @return Returns the ticketKill.
	 */
	public double getTicketKill() {
		double kill = 0;
		Iterator iter = ticketMoney.iterator();
		while (iter.hasNext()) {
			TicketMoney ticket = (TicketMoney)iter.next();
			kill += ticket.getMoney();
		}
		return Math.abs(Arith.round(kill, 2));
		//return (getTicket() == null) ? 0.0 : Math.abs(getTicket().getMoney());
		
	}

	/**
	 * @return Returns the payable.
	 */
	public double getPayable() {
		return (getGifts().size() > 0 || getItems().size() > 0) ? getTotalMoney()
				- getTicketKill() + getDeliveryFee()
				: payable;

	}

	/**
	 * @param payable
	 *            The payable to set.
	 */
	public void setPayable(double payable) {
		this.payable = payable;
	}

	

	/**
	 * @return Returns the deliveryWay.
	 */
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}

	/**
	 * @param deliveryWay
	 *            The deliveryWay to set.
	 */
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}

	/**
	 * @return Returns the paymentWay.
	 */
	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	/**
	 * @param paymentWay
	 *            The paymentWay to set.
	 */
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	/**
	 * @return Returns the receiptor.
	 */
	public String getReceiptor() {
		return receiptor;
	}

	/**
	 * @param receiptor
	 *            The receiptor to set.
	 */
	public void setReceiptor(String receiptor) {
		this.receiptor = receiptor;
	}

	/**
	 * @return Returns the receiptorAddress.
	 */
	public String getReceiptorAddress() {
		return receiptorAddress;
	}

	/**
	 * @param receiptorAddress
	 *            The receiptorAddress to set.
	 */
	public void setReceiptorAddress(String receiptorAddress) {
		this.receiptorAddress = receiptorAddress;
	}

	/**
	 * @return Returns the receiptorPhone.
	 */
	public String getReceiptorPhone() {
		return receiptorPhone;
	}

	/**
	 * @param receiptorPhone
	 *            The receiptorPhone to set.
	 */
	public void setReceiptorPhone(String receiptorPhone) {
		this.receiptorPhone = receiptorPhone;
	}

	/**
	 * @return Returns the receiptorPostCode.
	 */
	public String getReceiptorPostCode() {
		return receiptorPostCode;
	}

	/**
	 * @param receiptorPostCode
	 *            The receiptorPostCode to set.
	 */
	public void setReceiptorPostCode(String receiptorPostCode) {
		this.receiptorPostCode = receiptorPostCode;
	}

	/**
	 * @return Returns the itemCodeToDelete.
	 */
	public int getOperateId() {
		return operateId;
	}

	/**
	 * @param itemCodeToDelete
	 *            The itemCodeToDelete to set.
	 */
	public void setOperateId(int operateId) {
		this.operateId = operateId;
	}
	
	/**
	 * @return Returns the sellTypeId.
	 */
	public int getSellTypeId() {
		return sellTypeId;
	}
	
	/**
	 * @param sellTypeId
	 *  The sellTypeId to set.
	 */
	public void setSellTypeId(int sellTypeId) {
		this.sellTypeId = sellTypeId;
	}
	
	/**
	 * @return Returns the selectedGiftId.
	 */
	public int getSelectedGiftId() {
		return selectedGiftId;
	}

	/**
	 * @param selectedGiftId
	 *            The selectedGiftId to set.
	 */
	public void setSelectedGiftId(int selectedGiftId) {
		this.selectedGiftId = selectedGiftId;
	}
	
	
	
	public long getQueryAwardId() {
		return queryAwardId;
	}
	
	public void setQueryAwardId(long queryAwardId) {
		this.queryAwardId = queryAwardId;
	}
	/**
	 * @return Returns the selectedItemCode.
	 */
	public String getQueryItemCode() {
		return queryItemCode;
	}

	/**
	 * @param selectedItemCode
	 *            The selectedItemCode to set.
	 */
	public void setQueryItemCode(String queryItemCode) {
		this.queryItemCode = queryItemCode;
	}

	/**
	 * @return Returns the selectedItemQty.
	 */
	public int getQueryItemQty() {
		return queryItemQty;
	}

	/**
	 * @param selectedItemQty
	 *            The selectedItemQty to set.
	 */
	public void setQueryItemQty(int queryItemQty) {
		this.queryItemQty = queryItemQty;
	}

	/**
	 * @return Returns the giftId.
	 */
	public List getAllGifts() {
		return allGifts;
	}

	/**
	 * @return Returns the items.
	 */
	public List getItems() {
		return items;
	}

	
	/**
	 * @return Returns the orderId.
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            The orderId to set.
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return Returns the memberId.
	 */
	public int getMbId() {
		return mbId;
	}

	/**
	 * @param memberId
	 *            The memberId to set.
	 */
	public void setMbId(int mbId) {
		this.mbId = mbId;
	}

	/**
	 * @return Returns 购物栏中的非礼品和礼品的总价
	 */
	public double getTotalMoney() {
		return (getGifts().size() > 0 || getItems().size() > 0) ? getNotGiftMoney()
				+ getGiftMoney()
				: totalMoney;
	}

	public double getTotalMoneyNoCard() {
		return (getGifts().size() > 0 || getItems().size() > 0) ? getNotGiftMoney()
				+ getNoCardGiftMoney()
				: totalMoney;
	}

	/**
	 * @param totalMoney
	 *            The totalMoney to set.
	 */
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	/**
	 * @return 购物栏中的非礼品总价
	 */
	public double getNotGiftMoney() {
		totalMoney = 0.0;
		// 累加购买产品（非礼品）的应付金额
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				totalMoney += ii.getItemMoney();
			}
		}

		return Arith.round(totalMoney,2);
	}
	
	/**
	 * 购物栏中的非礼品数量
	 * @return
	 */
	public long getNotGiftQty() {
		long qty = 0L;
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				qty += ii.getItemQty();
			}
		}
		return qty;
	}
	/**
	 * @return 购物栏中的礼品总价
	 */
	public double getGiftMoney() {
		totalMoney = 0.0;

		// 累加购买礼品的应付金额
		if (gifts != null || gifts.size() > 0) {
			Iterator it = gifts.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				totalMoney += ii.getItemMoney();
			}
		}

		return Arith.round(totalMoney,2);
	}

	/**
	 * @return 购物栏中的礼品总价(会员卡除外）
	 */
	public double getNoCardGiftMoney() {
		totalMoney = 0.0;

		// 累加购买礼品的应付金额
		if (gifts != null || gifts.size() > 0) {
			Iterator it = gifts.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if (!ii.getItemCode().equals("100000")
						&& !ii.getItemCode().equals("100002") && !ii.getItemCode().equals("100004")) {
					totalMoney += ii.getItemMoney();
				}
			}
		}

		return Arith.round(totalMoney,2);
	}

	

	/**
	 * @return Returns the prTypes.
	 */
	public List getPrTypes() {
		return prTypes;
	}

	
	/**
	 * order types
	 */
	private ArrayList types = new ArrayList();

	/**
	 * order delivery types
	 */
	private ArrayList deliverys = new ArrayList();

	/**
	 * order payments
	 */
	private ArrayList payments = new ArrayList();

	/**
	 * 产品编码
	 */
	private String itemCode;

	/**
	 * @return Returns the deliverys.
	 */
	public ArrayList getDeliverys() {
		return deliverys;
	}

	/**
	 * @param deliverys
	 *            The deliverys to set.
	 */
	public void setDeliverys(ArrayList deliverys) {
		this.deliverys = deliverys;
	}

	/**
	 * @return Returns the itemCode.
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode
	 *            The itemCode to set.
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return Returns the payments.
	 */
	public ArrayList getPayments() {
		return payments;
	}

	/**
	 * @param payments
	 *            The payments to set.
	 */
	public void setPayments(ArrayList payments) {
		this.payments = payments;
	}

	/**
	 * @return Returns the types.
	 */
	public ArrayList getTypes() {
		return types;
	}

	/**
	 * @param types
	 *            The types to set.
	 */
	public void setTypes(ArrayList types) {
		this.types = types;
	}

	private String retReason;

	/**
	 * @return Returns the retReason.
	 */
	public String getRetReason() {
		return retReason;
	}

	/**
	 * @param retReason
	 *            The retReason to set.
	 */
	public void setRetReason(String retReason) {
		this.retReason = retReason;
	}

	
	

	/**
	 * @return Returns the ticketPassword.
	 */
	public String getTicketPassword() {
		return ticketPassword;
	}

	/**
	 * @param ticketPassword
	 *            The ticketPassword to set.
	 */
	public void setTicketPassword(String ticketPassword) {
		this.ticketPassword = ticketPassword;
	}

	

	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
        ;       // Default implementation does nothing

    }
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getMbName() {
		return mbName;
	}
	public void setMbName(String mbName) {
		this.mbName = mbName;
	}
	
	/**
	 * 拷贝数据
	 * @param source
	 * @param dest
	 */
	public static void copyData(OrderForm source, OrderForm dest) {
		//购物车
		dest.setMscs(source.getMscs());
		dest.getCart().getItems().addAll(source.getCart().getItems());
		dest.getCart().getTickets().addAll(source.getCart().getTickets());
		dest.getCart().getGifts().addAll(source.getCart().getGifts());
		dest.getCart().getGifts2().addAll(source.getCart().getGifts2());
		dest.getCart().setSetGroupId(source.getCart().getSetGroupId());
		
		dest.getCart().getAllGifts().addAll(source.getCart().getAllGifts());
		dest.getCart().getAllGifts2().addAll(source.getCart().getAllGifts2());
		dest.getCart().setDeliveryInfo(source.getCart().getDeliveryInfo());
		dest.getCart().setMember(source.getCart().getMember());
		dest.getCart().setOtherInfo(source.getCart().getOtherInfo());
		dest.getCart().setOrder(source.getCart().getOrder());
		dest.getCart().getActiveMsc().addAll(source.getCart().getActiveMsc());
		dest.getCart().setPackageFee(source.getCart().getPackageFee());// add by user 2008-05-19
		// 定单信息
		dest.setOrderNumber(source.getOrderNumber());
		dest.setOrderId(source.getOrderId());
		dest.setCreatorId(source.getCreatorId());
		dest.setCreateDate(source.getCreateDate());
		dest.setPrTypeId(source.getPrTypeId());
		dest.setPrTypeName(source.getPrTypeName());
		//dest.setMsc(source.getMsc());
		dest.setUse_deposit(source.getUse_deposit());
		
	}
	public static void checkedDefaultItem (OrderForm data, java.util.Collection catalogList) {
		String catalog = data.getCart().getOtherInfo().getCatalog();
		Iterator it = catalogList.iterator();
		int i = 0;
		while (it.hasNext()) {
			CodeName codename = (CodeName)it.next();
			if (i == 0) {
				if (catalog == null || catalog.equals("（会员未提供）")) {
					codename.setChecked(true);
					break;
				}
			} else {
				if (codename.getCode()!=null && codename.getCode().equals(catalog)) {
					codename.setChecked(true);
					break;
				}
			}
			i ++;
		}
	}
	/**
	 * @return the isRecruitBtnActive
	 */
	public boolean isRecruitBtnActive() {
		return isRecruitBtnActive;
	}
	/**
	 * @param isRecruitBtnActive the isRecruitBtnActive to set
	 */
	public void setRecruitBtnActive(boolean isRecruitBtnActive) {
		this.isRecruitBtnActive = isRecruitBtnActive;
	}
	/**
	 * @return the msc
	 */
	public String getMsc() {
		return msc;
	}
	/**
	 * @param msc the msc to set
	 */
	public void setMsc(String msc) {
		this.msc = msc;
	}
	public ArrayList getPackages() {
		return packages;
	}
	public void setPackages(ArrayList packages) {
		this.packages = packages;
	}
	public int getPackage_type() {
		return package_type;
	}
	public void setPackage_type(int package_type) {
		this.package_type = package_type;
	}
}