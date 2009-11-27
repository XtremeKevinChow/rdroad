/***********************************************************************
 * Module:  MusicPromotion.java
 * Author:  user
 * Purpose: Defines the Class MusicPromotion
 ***********************************************************************/

package com.magic.crm.promotion2.entity;

import java.util.*;

/** @pdOid 64cef575-4c79-4351-a0d1-57bce337909c */
public class MusicPromotion extends Promotion {
   /** @pdRoleInfo migr=no name=PromotionGift assc=promGifts coll=java.util.Collection impl=java.util.ArrayList mult=1..* type=Aggregation */
   protected java.util.Collection promGifts;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection getPromGifts() {
      if (promGifts == null)
         promGifts = new java.util.ArrayList();
      return promGifts;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorPromGifts() {
      if (promGifts == null)
         promGifts = new java.util.ArrayList();
      return promGifts.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newPromGifts */
   public void setPromGifts(java.util.Collection newPromGifts) {
      removeAllPromGifts();
      for (java.util.Iterator iter = newPromGifts.iterator(); iter.hasNext();)
         addPromGifts((PromotionGift)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPromotionGift */
   public void addPromGifts(PromotionGift newPromotionGift) {
      if (newPromotionGift == null)
         return;
      if (this.promGifts == null)
         this.promGifts = new java.util.ArrayList();
      if (!this.promGifts.contains(newPromotionGift))
         this.promGifts.add(newPromotionGift);
   }
   
   /** @pdGenerated default remove
     * @param oldPromotionGift */
   public void removePromGifts(PromotionGift oldPromotionGift) {
      if (oldPromotionGift == null)
         return;
      if (this.promGifts != null)
         if (this.promGifts.contains(oldPromotionGift))
            this.promGifts.remove(oldPromotionGift);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPromGifts() {
      if (promGifts != null)
         promGifts.clear();
   }

}