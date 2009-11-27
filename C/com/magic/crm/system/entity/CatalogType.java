/***********************************************************************
 * Module:  CatalogType.java
 * Author:  user
 * Purpose: Defines the Class CatalogType
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 3a0d1d88-0a07-4072-905c-4a2bf1c12b5d */
public class CatalogType {
   /** @pdOid e4f08084-d287-48d4-ba4c-036fc193c0c2 */
   private int id;
   /** @pdOid f7a2d12d-cfb2-4b51-a2ba-64c67ccbc6c0 */
   private String name;
   
   /** @pdOid 67d340f6-7d57-4eac-8d06-1eb240b5780a */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 1b5cc1c3-226e-4996-b988-2c214f741670 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid f6e2dfdc-04da-4ce7-9e4f-79ef23e86369 */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid b94a677f-dd2e-4a94-85b1-fc7457375d40 */
   public void setName(String newName) {
      name = newName;
   }

}