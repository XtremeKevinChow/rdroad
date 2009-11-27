/***********************************************************************
 * Module:  PromotionMode.java
 * Author:  user
 * Purpose: Defines the Class PromotionMode
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 8dfe603a-5264-421c-8cc8-8eee52c6fa41 */
public class PromotionMode {
   /** @pdOid f883de31-15f7-4ce7-b90c-2dd74ca00538 */
   private int id;
   /** @pdOid fcb4c75a-2293-4802-8b4e-2746a1d52f11 */
   private int name;
   
   /** @pdOid e5bef7a8-7363-475f-99b0-7e913f4cf8b9 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 66b5d761-0fde-4bc5-9ae6-9117cd9a6e04 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 04d0ee33-1be1-4dd5-be26-617a0c837367 */
   public int getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid 41d36147-0057-410d-ad6b-93021a177dbf */
   public void setName(int newName) {
      name = newName;
   }

}