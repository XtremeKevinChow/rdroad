/***********************************************************************
 * Module:  Product.java
 * Author:  user
 * Purpose: Defines the Class Product
 ***********************************************************************/

package com.magic.crm.product2.entity;

import java.util.*;

/** @pdOid 41606ea0-2268-475b-8b94-e4bd4136437e */
public class Product {
   /** @pdOid 4d5353a9-d793-4d35-a461-01c84634232b */
   protected int itemId;
   /** @pdOid 38b291af-97c7-47e1-abed-fe1c138051bf */
   protected String itemCode;
   /** @pdOid 54759d7f-8d35-4988-9d00-0ac07fb1ed5e */
   protected String itemName;
   
   /** @pdOid 028ea8b1-08b4-4235-9d29-72593ea505c1 */
   public int getItemId() {
      return itemId;
   }
   
   /** @param newItemId
    * @pdOid ed10810f-6ad6-4818-a8b9-fd75a936d867 */
   public void setItemId(int newItemId) {
      itemId = newItemId;
   }
   
   /** @pdOid a5af2996-ecc8-4f97-9323-729694490565 */
   public String getItemCode() {
      return itemCode;
   }
   
   /** @param newItemCode
    * @pdOid e093b4c8-2b24-461d-a7ab-972c1eaa3c35 */
   public void setItemCode(String newItemCode) {
      itemCode = newItemCode;
   }
   
   /** @pdOid f95eb1c7-19f3-4a9f-a855-499d0e033a2d */
   public String getItemName() {
      return itemName;
   }
   
   /** @param newItemName
    * @pdOid 5e44da1c-4545-475e-9f4c-dc7bdb79e349 */
   public void setItemName(String newItemName) {
      itemName = newItemName;
   }

}