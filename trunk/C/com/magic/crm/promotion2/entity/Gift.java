/***********************************************************************
 * Module:  Gift.java
 * Author:  user
 * Purpose: Defines the Class Gift
 ***********************************************************************/

package com.magic.crm.promotion2.entity;

import com.magic.crm.product2.entity.Product;
import com.magic.crm.system.entity.Period;
import com.magic.crm.system.entity.GiftStatus;
import java.util.*;

/** @pdOid 393c48c3-e808-4a25-ba5d-e928c6461aa9 */
public class Gift {
   /** @pdRoleInfo migr=no name=Product assc=product mult=1..1 */
   private Product product;
   /** @pdRoleInfo migr=no name=Period assc=period mult=1..1 type=Aggregation */
   private Period period;
   
   /** @pdOid 71dc2409-844a-4822-8e8b-a0ba9a9ce060 */
   protected long giftId;
   /** @pdOid ae97d0cf-8619-4305-9154-09db745284e3 */
   protected double price;
   /** @pdOid f074aac8-56c4-46c1-af6b-0bfca41072b2 */
   protected int quantity;
   
   /** @pdRoleInfo migr=no name=GiftStatus assc=status mult=1..1 type=Composition */
   protected GiftStatus status;
   
   /** @pdOid f3d2837e-f63c-4e10-a3e8-de9045b3a24b */
   public long getGiftId() {
      return giftId;
   }
   
   /** @param newGiftId
    * @pdOid db8d4815-53a5-4748-b92c-71fdf1489996 */
   public void setGiftId(long newGiftId) {
      giftId = newGiftId;
   }
   
   /** @pdOid ac8bd942-022d-4a5f-9fd0-582b43277fa0 */
   public double getPrice() {
      return price;
   }
   
   /** @param newPrice
    * @pdOid de83ec9a-856e-410c-a0bd-76ad367d7284 */
   public void setPrice(double newPrice) {
      price = newPrice;
   }
   
   /** @pdOid a33995c2-f125-4539-a54d-a9afe52d2d80 */
   public int getQuantity() {
      return quantity;
   }
   
   /** @param newQuantity
    * @pdOid 72b9bf1d-ff66-4b86-b225-2e185b5545cb */
   public void setQuantity(int newQuantity) {
      quantity = newQuantity;
   }

}