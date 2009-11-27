/***********************************************************************
 * Module:  AwardExpGift.java
 * Author:  user
 * Purpose: Defines the Class AwardExpGift
 ***********************************************************************/

package com.magic.crm.member2.entity;

import java.util.*;

/** @pdOid 80667ad9-b679-45a5-b6fc-d4ee4f66fcba */
public class AwardExpGift extends AwardGift {
   /** @pdOid 6360cb43-8662-48dd-b3a6-e2ab3f5b937d */
   private int usedAmountExp;
   
   /** @pdOid 627c74b2-1b51-4050-8cbc-caec7d647ae1 */
   public int getUsedAmountExp() {
      return usedAmountExp;
   }
   
   /** @param newUsedAmountExp
    * @pdOid bfef4785-8382-47e0-87b1-7662c727a4c2 */
   public void setUsedAmountExp(int newUsedAmountExp) {
      usedAmountExp = newUsedAmountExp;
   }

}