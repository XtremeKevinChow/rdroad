/***********************************************************************
 * Module:  DeliveryType.java
 * Author:  user
 * Purpose: Defines the Class DeliveryType
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 3eb0bcad-23a5-48ec-8058-b2b249a5cbaf */
public class DeliveryType {
   /** @pdOid 2935583d-be92-4446-96f6-216ecb3fb31c */
   protected int id;
   /** @pdOid 1502656a-ab65-4edd-9037-06278c4ee4c4 */
   protected String name;
   
   /** @pdOid 11f46991-1211-4eda-9783-01940470aa83 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 07dc68f7-08ad-4a5a-af82-6ca236ef6322 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 0d22e193-5326-4dc4-92d9-9e6f7e74de3b */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid 23ddb899-90d0-4468-9448-3bf65ccf23aa */
   public void setName(String newName) {
      name = newName;
   }

}