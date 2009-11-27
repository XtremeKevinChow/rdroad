/***********************************************************************
 * Module:  PayMethod.java
 * Author:  user
 * Purpose: Defines the Class PayMethod
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 2452ceed-c336-40d1-95e5-3166aed4aaa3 */
public class PayMethod {
   /** @pdOid ed5e443b-468e-4b57-99bc-05e39e257894 */
   protected int id;
   /** @pdOid 13c65aa5-a65a-4517-b246-dd3131cdc356 */
   protected String name;
   
   /** @pdOid 0d553359-c541-4d36-83f6-28fa2522f3f1 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid c8a09c01-0eb0-48f0-9c68-2cd57a4b970e */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid b84f87c7-e7b5-411e-a76a-4ab0ffe87b6d */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid 9f5041d5-c804-43b4-b9e0-9a3fcf453f17 */
   public void setName(String newName) {
      name = newName;
   }

}