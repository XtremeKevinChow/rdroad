/***********************************************************************
 * Module:  GroupPromtion.java
 * Author:  user
 * Purpose: Defines the Class GroupPromtion
 ***********************************************************************/

package com.magic.crm.promotion2.entity;

import java.util.*;

/** @pdOid 030e7dc5-6ee8-4666-bacc-49cd2cb3a959 */
public class GroupPromtion extends Promotion {
   /** @pdRoleInfo migr=no name=PromtionItem assc=promItems coll=java.util.Collection impl=java.util.ArrayList mult=1..* type=Aggregation */
   protected java.util.Collection promItems;
   /** @pdRoleInfo migr=no name=PromotionGift assc=promGifts coll=java.util.Collection impl=java.util.ArrayList mult=1..* type=Aggregation */
   protected java.util.Collection promGifts;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection getPromItems() {
      if (promItems == null)
         promItems = new java.util.ArrayList();
      return promItems;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorPromItems() {
      if (promItems == null)
         promItems = new java.util.ArrayList();
      return promItems.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newPromItems */
   public void setPromItems(java.util.Collection newPromItems) {
      removeAllPromItems();
      for (java.util.Iterator iter = newPromItems.iterator(); iter.hasNext();)
         addPromItems((PromtionItem)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPromtionItem */
   public void addPromItems(PromtionItem newPromtionItem) {
      if (newPromtionItem == null)
         return;
      if (this.promItems == null)
         this.promItems = new java.util.ArrayList();
      if (!this.promItems.contains(newPromtionItem))
         this.promItems.add(newPromtionItem);
   }
   
   /** @pdGenerated default remove
     * @param oldPromtionItem */
   public void removePromItems(PromtionItem oldPromtionItem) {
      if (oldPromtionItem == null)
         return;
      if (this.promItems != null)
         if (this.promItems.contains(oldPromtionItem))
            this.promItems.remove(oldPromtionItem);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPromItems() {
      if (promItems != null)
         promItems.clear();
   }
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