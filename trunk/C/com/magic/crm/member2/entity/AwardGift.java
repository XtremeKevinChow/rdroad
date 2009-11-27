/***********************************************************************
 * Module:  AwardGift.java
 * Author:  user
 * Purpose: Defines the Class AwardGift
 ***********************************************************************/

package com.magic.crm.member2.entity;

import com.magic.crm.system.entity.AwardGiftStatus;
import com.magic.crm.product2.entity.Product;
import com.magic.crm.system.entity.Period;
import java.util.*;

/** @pdOid 65d45c48-025d-43d7-84e4-85c8150e0a9b */
public class AwardGift {
   /** @pdRoleInfo migr=no name=Period assc=period mult=1..1 type=Aggregation */
   private Period period;
   /** @pdRoleInfo migr=no name=Product assc=product mult=1..1 */
   private Product product;
   
   /** @pdOid 68e67c7a-dba9-4499-bc97-1d1b31a51594 */
   protected long awardGiftId;
   /** @pdOid 726cf36b-054c-4257-ad9c-7450a5d5926e */
   protected double price;
   /** @pdOid 5140e329-e121-4dc6-ac2e-92d5b0313dc2 */
   protected int quantity;
   
   /** @pdRoleInfo migr=no name=AwardGiftStatus assc=status mult=1..1 type=Composition */
   protected AwardGiftStatus status;
   
   /** @pdOid 1c8819ce-a01f-42d4-ae6b-5fa1224904ff */
   public long getAwardGiftId() {
      return awardGiftId;
   }
   
   /** @param newAwardGiftId
    * @pdOid 5cbfbe9a-17b9-4af2-8c31-d765a5db893c */
   public void setAwardGiftId(long newAwardGiftId) {
      awardGiftId = newAwardGiftId;
   }
   
   /** @pdOid faf62dd3-1c1e-43e1-b91e-31fb65f10b1f */
   public int getQuantity() {
      return quantity;
   }
   
   /** @param newQuantity
    * @pdOid 17d3f7d7-3045-4300-9910-5451cf5b080d */
   public void setQuantity(int newQuantity) {
      quantity = newQuantity;
   }
   
   /** @pdOid 3afd3b68-ab37-4053-994b-2aea1fd34c13 */
   public double getPrice() {
      return price;
   }
   
   /** @param newPrice
    * @pdOid e16a34a7-72ee-4410-acf5-1b0584fa2dca */
   public void setPrice(double newPrice) {
      price = newPrice;
   }

}