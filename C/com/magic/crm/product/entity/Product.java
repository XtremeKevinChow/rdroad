package com.magic.crm.product.entity;


/**
 * Data Bean Class<br>
 * <br>
 * Autogenerated on 01/28/2005 06:41:59<br>
 * &nbsp;&nbsp;&nbsp; table = "prd_items"
*
* @author Generator
*/
public class Product implements java.io.Serializable {

	
	/**
	 * UID
	 */
	private static final long serialVersionUID = 2006110600001L;
	
	protected int isReturn = 0;
	
	protected int returnDays = 0;
	
	protected int balanceMethod = 0;
	
	protected double returnGoodsRate = 0d;

	public int getBalanceMethod() {
		return balanceMethod;
	}
	public void setBalanceMethod(int balanceMethod) {
		this.balanceMethod = balanceMethod;
	}
	public int getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(int isReturn) {
		this.isReturn = isReturn;
	}
	public int getReturnDays() {
		return returnDays;
	}
	public void setReturnDays(int returnDays) {
		this.returnDays = returnDays;
	}
	public double getReturnGoodsRate() {
		return returnGoodsRate;
	}
	public void setReturnGoodsRate(double returnGoodsRate) {
		this.returnGoodsRate = returnGoodsRate;
	}
	protected int maxsalenum=0;
	public int getMaxsalenum() {
		return maxsalenum;
	}

	public void setMaxsalenum(int maxsalenum) {
		this.maxsalenum = maxsalenum;
	}
	
	/**
	 * The availQty attribute(add by user 2006-01-05)
	 */
	protected int availQty = 0;
	
	/**
	 * @return Returns the availQty.
	 */
	public int getAvailQty() {
		return availQty;
	}
	/**
	 * @param availQty The availQty to set.
	 */
	public void setAvailQty(int availQty) {
		this.availQty = availQty;
	}
   /**
    * The itemID attribute.
   */
   protected java.lang.String itemID = null;
   
   /**
    * Gets the itemID value.
   */
   public java.lang.String getItemID() {
      return itemID;
   }
   
   /**
    * Sets the itemID value.
   */
   public void setItemID(java.lang.String itemID) {
      this.itemID = itemID;
   }
	/**
    * The ifPresell attribute.
   */
   protected int ifPresell = 0;	   
   /**
    * Gets the ifPresell value.
   */
   public int getifPresell() {
      return ifPresell;
   }
   
   /**
    * Sets the ifPresell value.
   */
   public void setifPresell(int iifPresell) {
      this.ifPresell = iifPresell;
   }
   /**
    * The clubID attribute.
   */
   protected String clubID = null;
   
   /**
    * Gets the clubID value.
   */
   public String getClubID() {
      return clubID;
   }
   
   /**
    * Sets the clubID value.
   */
   public void setClubID(String clubID) {
      this.clubID = clubID;
   }

   /**
    * The replaceItemID attribute.
   */
   protected java.lang.String replaceItemID = null;
   
   /**
    * Gets the replaceItemID value.
   */
   public java.lang.String getReplaceItemID() {
      return replaceItemID;
   }
   
   /**
    * Sets the replaceItemID value.
   */
   public void setReplaceItemID(java.lang.String replaceItemID) {
      this.replaceItemID = replaceItemID;
   }

   /**
    * The unit attribute.
   */
   protected int unit = 0;
   
   /**
    * Gets the unit value.
   */
   public int getUnit() {
      return unit;
   }
   
   /**
    * Sets the unit value.
   */
   public void setUnit(int unit) {
      this.unit = unit;
   }

   /**
    * The isbn attribute.
   */
   protected java.lang.String isbn = null;
   
   /**
    * Gets the isbn value.
   */
   public java.lang.String getIsbn() {
      return isbn;
   }
   
   /**
    * Sets the isbn value.
   */
   public void setIsbn(java.lang.String isbn) {
      this.isbn = isbn;
   }

   /**
    * The author attribute.
   */
   protected java.lang.String author = null;
   
   /**
    * Gets the author value.
   */
   public java.lang.String getAuthor() {
      return author;
   }
   
   /**
    * Sets the author value.
   */
   public void setAuthor(java.lang.String author) {
      this.author = author;
   }

   /**
    * The comments attribute.
   */
   protected java.lang.String comments = null;
   
   /**
    * Gets the comments value.
   */
   public java.lang.String getComments() {
      return comments;
   }
   
   /**
    * Sets the comments value.
   */
   public void setComments(java.lang.String comments) {
      this.comments = comments;
   }

   /**
    * The peculiarity attribute.
   */
   protected java.lang.String peculiarity = null;
   
   /**
    * Gets the peculiarity value.
   */
   public java.lang.String getPeculiarity() {
      return peculiarity;
   }
   
   /**
    * Sets the peculiarity value.
   */
   public void setPeculiarity(java.lang.String peculiarity) {
      this.peculiarity = peculiarity;
   }

   /**
    * The title attribute.
   */
   protected java.lang.String title = null;
   
   /**
    * Gets the title value.
   */
   public java.lang.String getTitle() {
      return title;
   }
   
   /**
    * Sets the title value.
   */
   public void setTitle(java.lang.String title) {
      this.title = title;
   }

   /**
    * The status attribute.
   */
   protected int status = 0;
   
   /**
    * Gets the status value.
   */
   public int getStatus() {
      return status;
   }
   
   /**
    * Sets the status value.
   */
   public void setStatus(int status) {
      this.status = status;
   }
   /*
    * 白金卡价格
    */
	protected float platina_price = 0f;
	
	public float getPlatina_Price() {
		return platina_price;
	}
	public void setPlatina_Price(float platina_price) {
		this.platina_price = platina_price;
	}
	
   /**
    * The standardPrice attribute.
   */
   protected float standardPrice = 0.0f;
   
   /**
    * Gets the standardPrice value.
   */
   public float getStandardPrice() {
      return standardPrice;
   }
   
   /**
    * Sets the standardPrice value.
   */
   public void setStandardPrice(float standardPrice) {
      this.standardPrice = standardPrice;
   }

   /**
    * The type attribute.
   */
   protected int type = 0;
   
   /**
    * Gets the type value.
   */
   public int getType() {
      return type;
   }
   
   /**
    * Sets the type value.
   */
   public void setType(int type) {
      this.type = type;
   }

   /**
    * The inventoryStatus attribute.
   */
   protected int inventoryStatus = 0;
   
   /**
    * Gets the inventoryStatus value.
   */
   public int getInventoryStatus() {
      return inventoryStatus;
   }
   
   /**
    * Sets the inventoryStatus value.
   */
   public void setInventoryStatus(int inventoryStatus) {
      this.inventoryStatus = inventoryStatus;
   }

   /**
    * The isSelfProduced attribute.
   */
   protected int isSelfProduced = 0;
   
   /**
    * Gets the isSelfProduced value.
   */
   public int getIsSelfProduced() {
      return isSelfProduced;
   }
   
   /**
    * Sets the isSelfProduced value.
   */
   public void setIsSelfProduced(int isSelfProduced) {
      this.isSelfProduced = isSelfProduced;
   }

   /**
    * The isLastSel attribute.
   */
   protected int isLastSel = 0;
   
   /**
    * Gets the isLastSel value.
   */
   public int getIsLastSel() {
      return isLastSel;
   }
   
   /**
    * Sets the isLastSel value.
   */
   public void setIsLastSel(int isLastSel) {
      this.isLastSel = isLastSel;
   }

   /**
    * The purchasingCost attribute.
   */
   protected float purchasingCost = 0.0f;
   
   /**
    * Gets the purchasingCost value.
   */
   public float getPurchasingCost() {
      return purchasingCost;
   }
   
   /**
    * Sets the purchasingCost value.
   */
   public void setPurchasingCost(float purchasingCost) {
      this.purchasingCost = purchasingCost;
   }

   /**
    * The inventoryAvailable attribute.
   */
   protected int inventoryAvailable = 0;
   
   /**
    * Gets the inventoryAvailable value.
   */
   public int getInventoryAvailable() {
      return inventoryAvailable;
   }
   
   /**
    * Sets the inventoryAvailable value.
   */
   public void setInventoryAvailable(int inventoryAvailable) {
      this.inventoryAvailable = inventoryAvailable;
   }

   /**
    * The operateTime attribute.
   */
   protected java.util.Date operateTime = null;
   
   /**
    * Gets the operateTime value.
   */
   public java.util.Date getOperateTime() {
      return operateTime;
   }
   
   /**
    * Sets the operateTime value.
   */
   public void setOperateTime(java.util.Date operateTime) {
      this.operateTime = operateTime;
   }

   /**
    * The tax attribute.
   */
   protected float tax = 0.0f;
   
   /**
    * Gets the tax value.
   */
   public float getTax() {
      return tax;
   }
   
   /**
    * Sets the tax value.
   */
   public void setTax(float tax) {
      this.tax = tax;
   }

   /**
    * The deliverType attribute.
   */
   protected int deliverType = 0;
   
   /**
    * Gets the deliverType value.
   */
   public int getDeliverType() {
      return deliverType;
   }
   
   /**
    * Sets the deliverType value.
   */
   public void setDeliverType(int deliverType) {
      this.deliverType = deliverType;
   }

   /**
    * The userStatus attribute.
   */
   protected int userStatus = 0;
   
   /**
    * Gets the userStatus value.
   */
   public int getUserStatus() {
      return userStatus;
   }
   
   /**
    * Sets the userStatus value.
   */
   public void setUserStatus(int userStatus) {
      this.userStatus = userStatus;
   }

   /**
    * The length attribute.
   */
   protected float length = 0.0f;
   
   /**
    * Gets the length value.
   */
   public float getLength() {
      return length;
   }
   
   /**
    * Sets the length value.
   */
   public void setLength(float length) {
      this.length = length;
   }

   /**
    * The width attribute.
   */
   protected float width = 0.0f;
   
   /**
    * Gets the width value.
   */
   public float getWidth() {
      return width;
   }
   
   /**
    * Sets the width value.
   */
   public void setWidth(float width) {
      this.width = width;
   }

   /**
    * The height attribute.
   */
   protected float height = 0.0f;
   
   /**
    * Gets the height value.
   */
   public float getHeight() {
      return height;
   }
   
   /**
    * Sets the height value.
   */
   public void setHeight(float height) {
      this.height = height;
   }

   /**
    * The weight attribute.
   */
   protected float weight = 0.0f;
   
   /**
    * Gets the weight value.
   */
   public float getWeight() {
      return weight;
   }
   
   /**
    * Sets the weight value.
   */
   public void setWeight(float weight) {
      this.weight = weight;
   }

   /**
    * The isSet attribute.
   */
   protected java.lang.String isSet = null;
   
   /**
    * Gets the isSet value.
   */
   public java.lang.String getIsSet() {
      return isSet;
   }
   
   /**
    * Sets the isSet value.
   */
   public void setIsSet(java.lang.String isSet) {
      this.isSet = isSet;
   }

   /**
    * The contractTitle attribute.
   */
   protected java.lang.String contractTitle = null;
   
   /**
    * Gets the contractTitle value.
   */
   public java.lang.String getContractTitle() {
      return contractTitle;
   }
   
   /**
    * Sets the contractTitle value.
   */
   public void setContractTitle(java.lang.String contractTitle) {
      this.contractTitle = contractTitle;
   }

   /**
    * The icpCode attribute.
   */
   protected java.lang.String icpCode = null;
   
   /**
    * Gets the icpCode value.
   */
   public java.lang.String getIcpCode() {
      return icpCode;
   }
   
   /**
    * Sets the icpCode value.
   */
   public void setIcpCode(java.lang.String icpCode) {
      this.icpCode = icpCode;
   }

   /**
    * The publishingHouse attribute.
   */
   protected java.lang.String publishingHouse = null;
   
   /**
    * Gets the publishingHouse value.
   */
   public java.lang.String getPublishingHouse() {
      return publishingHouse;
   }
   
   /**
    * Sets the publishingHouse value.
   */
   public void setPublishingHouse(java.lang.String publishingHouse) {
      this.publishingHouse = publishingHouse;
   }
   protected String publishingHouseName = null;
   
/**
 * @return Returns the publishingHouseName.
 */
public String getPublishingHouseName() {
    return publishingHouseName;
}
/**
 * @param publishingHouseName The publishingHouseName to set.
 */
public void setPublishingHouseName(String publishingHouseName) {
    this.publishingHouseName = publishingHouseName;
}
   /**
    * The returnRate attribute.
   */
   protected float returnRate = 0.0f;
   
   /**
    * Gets the returnRate value.
   */
   public float getReturnRate() {
      return returnRate;
   }
   
   /**
    * Sets the returnRate value.
   */
   public void setReturnRate(float returnRate) {
      this.returnRate = returnRate;
   }

   /**
    * The unpurchasingCost attribute.
   */
   protected float unpurchasingCost = 0.0f;
   
   /**
    * Gets the unpurchasingCost value.
   */
   public float getUnpurchasingCost() {
      return unpurchasingCost;
   }
   
   /**
    * Sets the unpurchasingCost value.
   */
   public void setUnpurchasingCost(float unpurchasingCost) {
      this.unpurchasingCost = unpurchasingCost;
   }

   /**
    * The discount attribute.
   */
   protected float discount = 0.0f;
   
   /**
    * Gets the discount value.
   */
   public float getDiscount() {
      return discount;
   }
   
   /**
    * Sets the discount value.
   */
   public void setDiscount(float discount) {
      this.discount = discount;
   }

   /**
    * The isCurCatalog attribute.
   */
   protected java.lang.String isCurCatalog = null;
   
   /**
    * Gets the isCurCatalog value.
   */
   public java.lang.String getIsCurCatalog() {
      return isCurCatalog;
   }
   
   /**
    * Sets the isCurCatalog value.
   */
   public void setIsCurCatalog(java.lang.String isCurCatalog) {
      this.isCurCatalog = isCurCatalog;
   }

   /**
    * The isCommitment attribute.
   */
   protected java.lang.String isCommitment = null;
   
   /**
    * Gets the isCommitment value.
   */
   public java.lang.String getIsCommitment() {
      return isCommitment;
   }
   
   /**
    * Sets the isCommitment value.
   */
   public void setIsCommitment(java.lang.String isCommitment) {
      this.isCommitment = isCommitment;
   }

   /**
    * The itemCode attribute.
   */
   protected java.lang.String itemCode = null;
   
   /**
    * Gets the itemCode value.
   */
   public java.lang.String getItemCode() {
      return itemCode;
   }
   
   /**
    * Sets the itemCode value.
   */
   public void setItemCode(java.lang.String itemCode) {
      this.itemCode = itemCode;
   }

   /**
    * The name attribute.
   */
   protected java.lang.String name = null;
   
   /**
    * Gets the name value.
   */
   public java.lang.String getName() {
      return name;
   }
   
   /**
    * Sets the name value.
   */
   public void setName(java.lang.String name) {
      this.name = name;
   }

   /**
    * The barCode attribute.
   */
   protected java.lang.String barCode = null;
   
   /**
    * Gets the barCode value.
   */
   public java.lang.String getBarCode() {
      return barCode;
   }
   
   /**
    * Sets the barCode value.
   */
   public void setBarCode(java.lang.String barCode) {
      this.barCode = barCode;
   }

   /**
    * The productOwnerID attribute.
   */
   protected java.lang.String productOwnerID = null;
   
   /**
    * Gets the productOwnerID value.
   */
   public java.lang.String getProductOwnerID() {
      return productOwnerID;
   }
   
   /**
    * Sets the productOwnerID value.
   */
   public void setProductOwnerID(java.lang.String productOwnerID) {
      this.productOwnerID = productOwnerID;
   }

   /**
    * The operatorID attribute.
   */
   protected java.lang.String operatorID = null;
   
   /**
    * Gets the operatorID value.
   */
   public java.lang.String getOperatorID() {
      return operatorID;
   }
   
   /**
    * Sets the operatorID value.
   */
   public void setOperatorID(java.lang.String operatorID) {
      this.operatorID = operatorID;
   }

   /**
    * The categoryID attribute.
   */
   protected java.lang.String categoryID = null;
   
   /**
    * Gets the categoryID value.
   */
   public java.lang.String getCategoryID() {
      return categoryID;
   }
   
   /**
    * Sets the categoryID value.
   */
   public void setCategoryID(java.lang.String categoryID) {
      this.categoryID = categoryID;
   }

   /**
    * The supplierID attribute.
   */
   protected java.lang.String supplierID = null;
   
   /**
    * Gets the supplierID value.
   */
   public java.lang.String getSupplierID() {
      return supplierID;
   }
   
   /**
    * Sets the supplierID value.
   */
   public void setSupplierID(java.lang.String supplierID) {
      this.supplierID = supplierID;
   }
   
   protected float silverPrice = 0;
   
   /**
    * Gets the supplierID value.
   */
   public float getSilverPrice() {
      return silverPrice;
   }
   
   /**
    * Sets the silverPrice value.
   */
   public void setSilverPrice(float silverPrice) {
      this.silverPrice = silverPrice;
   }
   
   protected float godenPrice = 0;
   
   /**
    * Gets the godenPrice value.
   */
   public float getGodenPrice() {
      return godenPrice;
   }
   
   /**
    * Sets the godenPrice value.
   */
   public void setGodenPrice(float godenPrice) {
      this.godenPrice = godenPrice;
   }
   
   protected float webPrice = 0;
   
   /**
    * Gets the webPrice value.
   */
   public float getWebPrice() {
      return webPrice;
   }
   
   /**
    * Sets the webPrice value.
   */
   public void setWebPrice(float webPrice) {
      this.webPrice = webPrice;
   }
   protected int is_web = 0;//是否网站产品
   /**
    * Gets the is_web value.
   */
   public int getIs_Web() {
      return is_web;
   }
   
   protected float retailPrice = 0;
   /**
    * Gets the webPrice value.
   */
   public float getRetailPrice() {
      return retailPrice;
   }
   
   /**
    * Sets the webPrice value.
   */
   public void setRetailPrice(float retailPrice) {
      this.retailPrice = retailPrice;
   }
   
   /**
    * Sets the is_web value.
   */
   public void setIs_Web(int is_web) {
      this.is_web = is_web;
   }   
   public String toString() {
      StringBuffer results = new StringBuffer();

      results.append(getClass().getName() + "\n");
      results.append("\titemID=" + itemID + "\n");
      results.append("\tclubID=" + clubID + "\n");
      results.append("\treplaceItemID=" + replaceItemID + "\n");
      results.append("\tunit=" + unit + "\n");
      results.append("\tisbn=" + isbn + "\n");
      results.append("\tauthor=" + author + "\n");
      results.append("\tcomments=" + comments + "\n");
      results.append("\tpeculiarity=" + peculiarity + "\n");
      results.append("\ttitle=" + title + "\n");
      results.append("\tstatus=" + status + "\n");
      results.append("\tstandardPrice=" + standardPrice + "\n");
      results.append("\ttype=" + type + "\n");
      results.append("\tinventoryStatus=" + inventoryStatus + "\n");
      results.append("\tisSelfProduced=" + isSelfProduced + "\n");
      results.append("\tisLastSel=" + isLastSel + "\n");
      results.append("\tpurchasingCost=" + purchasingCost + "\n");
      results.append("\tinventoryAvailable=" + inventoryAvailable + "\n");
      results.append("\toperateTime=" + operateTime + "\n");
      results.append("\ttax=" + tax + "\n");
      results.append("\tdeliverType=" + deliverType + "\n");
      results.append("\tuserStatus=" + userStatus + "\n");
      results.append("\tlength=" + length + "\n");
      results.append("\twidth=" + width + "\n");
      results.append("\theight=" + height + "\n");
      results.append("\tweight=" + weight + "\n");
      results.append("\tisSet=" + isSet + "\n");
      results.append("\tcontractTitle=" + contractTitle + "\n");
      results.append("\ticpCode=" + icpCode + "\n");
      results.append("\tpublishingHouse=" + publishingHouse + "\n");
      results.append("\treturnRate=" + returnRate + "\n");
      results.append("\tunpurchasingCost=" + unpurchasingCost + "\n");
      results.append("\tdiscount=" + discount + "\n");
      results.append("\tisCurCatalog=" + isCurCatalog + "\n");
      results.append("\tisCommitment=" + isCommitment + "\n");
      results.append("\titemCode=" + itemCode + "\n");
      results.append("\tname=" + name + "\n");
      results.append("\tbarCode=" + barCode + "\n");
      results.append("\tproductOwnerID=" + productOwnerID + "\n");
      results.append("\toperatorID=" + operatorID + "\n");
      results.append("\tcategoryID=" + categoryID + "\n");
      results.append("\tsupplierID=" + supplierID + "\n");
      results.append("\tifPresell=" + ifPresell + "\n");
      results.append("\\isReturn=" + isReturn + "\n");
      results.append("\returnDays=" + returnDays + "\n");
      results.append("\balanceMethod=" + balanceMethod + "\n");
      results.append("\returnGoodsRate=" + returnGoodsRate + "\n");

      return results.toString();
   }

}
