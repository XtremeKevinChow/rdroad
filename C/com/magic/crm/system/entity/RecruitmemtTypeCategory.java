/***********************************************************************
 * Module:  RecruitmemtTypeCategory.java
 * Author:  user
 * Purpose: Defines the Class RecruitmemtTypeCategory
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 0db39b37-d432-4807-840a-c6b7f872fd8b */
public class RecruitmemtTypeCategory {
   /** @pdOid 40e5439e-4cee-456f-844c-1543f10b7aec */
   private int id;
   /** @pdOid dfe46dcf-9e58-4667-bbc4-b72346310d62 */
   private String name;
   
   /** @pdOid 6acdf85d-46e0-4102-86d5-76b153d97a98 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 150ce27a-7064-4d96-a5f9-15e04bcf5d12 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 374526da-bdf4-46f5-b299-2b78f6fa6e1d */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid f6ce6594-66c4-41b5-a73d-00ebf1d33e76 */
   public void setName(String newName) {
      name = newName;
   }

}