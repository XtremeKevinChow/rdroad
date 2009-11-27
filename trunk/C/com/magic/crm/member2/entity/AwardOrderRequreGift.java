/***********************************************************************
 * Module:  AwardOrderRequreGift.java
 * Author:  user
 * Purpose: Defines the Class AwardOrderRequreGift
 ***********************************************************************/

package com.magic.crm.member2.entity;

import java.util.*;

/** @pdOid 8cb2525d-25db-4a6c-bfee-ca7d5b4c76fc */
public class AwardOrderRequreGift extends AwardGift {
   /** @pdOid b311b6f3-4811-4087-8747-39dc76f03e05 */
   protected double orderRequire;
   
   /** @pdOid 12fb105f-764a-41cf-a6d5-6079c621602a */
   public double getOrderRequire() {
      return orderRequire;
   }
   
   /** @param newOrderRequire
    * @pdOid 09b984e5-f18c-4d2b-87e0-cdfdfda47665 */
   public void setOrderRequire(double newOrderRequire) {
      orderRequire = newOrderRequire;
   }

}