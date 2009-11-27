/***********************************************************************
 * Module:  MemberAward.java
 * Author:  user
 * Purpose: Defines the Class MemberAward
 ***********************************************************************/

package com.magic.crm.member2.entity;

import java.util.*;

/** @pdOid 9a7662c6-ea09-49af-b94d-5f202775d55e */
public class MemberAward {
   /** @pdRoleInfo migr=no name=AwardGift assc=awardGifts coll=Collection impl=ArrayList mult=0..* type=Aggregation */
   private Collection awardGifts;
   
   
   /** @pdGenerated default getter */
   public Collection getAwardGifts() {
      if (awardGifts == null)
         awardGifts = new ArrayList();
      return awardGifts;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAwardGifts() {
      if (awardGifts == null)
         awardGifts = new ArrayList();
      return awardGifts.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAwardGifts */
   public void setAwardGifts(Collection newAwardGifts) {
      removeAllAwardGifts();
      for (java.util.Iterator iter = newAwardGifts.iterator(); iter.hasNext();)
         addAwardGifts((AwardGift)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newAwardGift */
   public void addAwardGifts(AwardGift newAwardGift) {
      if (newAwardGift == null)
         return;
      if (this.awardGifts == null)
         this.awardGifts = new ArrayList();
      if (!this.awardGifts.contains(newAwardGift))
         this.awardGifts.add(newAwardGift);
   }
   
   /** @pdGenerated default remove
     * @param oldAwardGift */
   public void removeAwardGifts(AwardGift oldAwardGift) {
      if (oldAwardGift == null)
         return;
      if (this.awardGifts != null)
         if (this.awardGifts.contains(oldAwardGift))
            this.awardGifts.remove(oldAwardGift);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAwardGifts() {
      if (awardGifts != null)
         awardGifts.clear();
   }
   
   /** @pdOid 78e899b6-8f20-4778-acf9-b64f0f372926 */
   protected int size;
   
   /** @pdOid 54bfb1e7-ec29-4acd-b481-47fd26bc1778 */
   public int getSize() {
      return size;
   }
   
   /** @param newSize
    * @pdOid 738ef97e-8181-4896-ae0b-73b2e706b565 */
   public void setSize(int newSize) {
      size = newSize;
   }

}