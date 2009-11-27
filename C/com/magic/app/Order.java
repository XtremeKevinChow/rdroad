package com.magic.app;
/**
 * 订单信息JavaBean
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class Order 
{
  private int buyerID;
  private int supplierID;
  private int deliveryType;
  private int paymentType;
  private  int locationID;
  private  String contact;
  private  String postcode;
  private  String phone;
  private  String address;
  private  String comments;
  private  int[] v_item_id=new int[20];
  private  float[] v_quantity=new float[20];;
  private int prType;
  private int item_count=0;
  public Order()
  {
  }

  public int getBuyerID()
  {
    return buyerID;
  }

  public void setBuyerID(int buyer_id)
  {
    this.buyerID = buyer_id;
  }

  public int getSupplierID()
  {
    return supplierID;
  }

  public void setSupplierID(int supplierID)
  {
    this.supplierID = supplierID;
  }

  public int getDeliveryType()
  {
    return deliveryType;
  }

  public void setDeliveryType(int deliveryType)
  {
    this.deliveryType = deliveryType;
  }

  public int getPaymentType()
  {
    return paymentType;
  }

  public void setPaymentType(int paymentType)
  {
    this.paymentType = paymentType;
  }

  public int getLocationID()
  {
    return locationID;
  }

  public void setLocationID(int locationID)
  {
    this.locationID = locationID;
  }

  public String getContact()
  {
    return contact;
  }

  public void setContact(String contact)
  {
    this.contact = contact;
  }

  public String getPostcode()
  {
    return postcode;
  }

  public void setPostcode(String postcode)
  {
    this.postcode = postcode;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getComments()
  {
    return comments;
  }

  public void setComments(String comments)
  {
    this.comments = comments;
  }

  public int[] getItems()
  {
    return v_item_id;
  }

  public float[] getPrices()
  {
    return v_quantity;
  }

  public int getPrType()
  {
    return prType;
  }

  public void setPrType(int prType)
  {
    this.prType = prType;
  }

  void addItem(int item_id, float quantity)
  {
      item_count++;
      v_item_id[item_count]=item_id;
      v_quantity[item_count]=quantity;
  }

  void deleteItem(int item_id)
  {
  }

  void updateQuantity(int item_id, float quantity)
  {
     
  }


}