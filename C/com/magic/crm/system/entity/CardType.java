/***********************************************************************
 * Module:  CardType.java
 * Author:  user
 * Purpose: Defines the Class CardType
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid dca1a264-c5db-452b-bca9-fc79d0c4acf6 */
public class CardType {
   /** @pdOid c2fa6004-60f1-4007-b69c-3c59bbcda5ee */
   private int id;
   /** @pdOid e5738d24-96e6-4be7-8bb2-1889c6014dbc */
   private String name;
   
   /** @pdOid f40513a2-d3cf-4bd1-ba62-3383358a3dc4 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 569640ad-a317-4419-bec6-e1cb7fbb3cf2 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 4fc27d34-8c67-4f56-a663-20d4eb2543d8 */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid c2e9a7c3-18bf-4d6a-a596-8c5dc14dae2b */
   public void setName(String newName) {
      name = newName;
   }

}