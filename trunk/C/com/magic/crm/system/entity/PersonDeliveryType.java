/***********************************************************************
 * Module:  PersonDeliveryType.java
 * Author:  user
 * Purpose: Defines the Class PersonDeliveryType
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 3e0fca3d-b508-4ae9-8eb1-e1751a5fa30c */
public class PersonDeliveryType extends DeliveryType {
  
   /** @pdOid b9182bec-e9cc-424d-aa64-b29bc950183f */
   protected int deliveryFee;
   
   /** @pdOid 16abee44-2eb0-4788-a3d6-c1196dc8a1f9 */
   public int getDeliveryFee() {
      return deliveryFee;
   }
   
   /** @param newDeliveryFee
    * @pdOid d3d2c8e9-0e70-41be-9394-d70b14a62a37 */
   public void setDeliveryFee(int newDeliveryFee) {
      deliveryFee = newDeliveryFee;
   }

}