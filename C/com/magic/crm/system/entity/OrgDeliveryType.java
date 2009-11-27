/***********************************************************************
 * Module:  OrgDeliveryType.java
 * Author:  user
 * Purpose: Defines the Class OrgDeliveryType
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 7e5b13ed-9d3f-4f1f-bd4a-3873f911d8a4 */
public class OrgDeliveryType extends DeliveryType {
   /** @pdOid e3363aef-6fd6-40f9-a84b-6e8a9a861667 */
   private double deliveryFee;
   
   /** @pdOid 482768b9-51a7-4d32-9585-76b20aa85b94 */
   public double getDeliveryFee() {
      return deliveryFee;
   }
   
   /** @param newDeliveryFee
    * @pdOid 91af9150-3bea-49ee-bcd0-3314d9340fb9 */
   public void setDeliveryFee(double newDeliveryFee) {
      deliveryFee = newDeliveryFee;
   }

}