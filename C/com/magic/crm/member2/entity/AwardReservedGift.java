/***********************************************************************
 * Module:  AwardReservedGift.java
 * Author:  user
 * Purpose: Defines the Class AwardReservedGift
 ***********************************************************************/

package com.magic.crm.member2.entity;

import java.util.*;

/** @pdOid 37c212ed-f110-44d4-b094-5d4253f4cf4f */
public class AwardReservedGift extends AwardGift {
   /** @pdOid f807d197-7f0f-434d-83dd-d1a8003c7b76 */
   private int promLevel;
   
   /** @pdOid 9a4b7139-fc07-4d2e-8fcd-477afd0d36f4 */
   public int getPromLevel() {
      return promLevel;
   }
   
   /** @param newPromLevel
    * @pdOid c1861358-8be8-453a-8f1c-3046b16c87c9 */
   public void setPromLevel(int newPromLevel) {
      promLevel = newPromLevel;
   }

}