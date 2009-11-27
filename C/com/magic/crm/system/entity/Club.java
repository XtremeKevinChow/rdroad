/***********************************************************************
 * Module:  Club.java
 * Author:  user
 * Purpose: Defines the Class Club
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 0cf01d89-1bbf-42ca-acef-93136bff31f8 */
public class Club {
   /** @pdOid 147e8182-c2f7-4586-8758-d7e7ec27df8d */
   private int id;
   /** @pdOid 04acc369-50eb-49f9-be65-e3b1dfaf1096 */
   private int name;
   
   /** @pdOid b4813c94-1674-4986-8d9e-20d53fe05b53 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid e87727b6-abce-4515-8ee1-f33871588ca0 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 9f0cdfba-84f5-4795-b294-50ec0a5e77fe */
   public int getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid d1f0d804-9f83-407d-b370-ede3e3c24961 */
   public void setName(int newName) {
      name = newName;
   }

}