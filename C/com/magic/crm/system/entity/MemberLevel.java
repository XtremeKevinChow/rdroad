/***********************************************************************
 * Module:  MemberLevel.java
 * Author:  user
 * Purpose: Defines the Class MemberLevel
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 5893c939-8d5b-45a3-b1ba-d5e062c161ce */
public class MemberLevel {
   /** @pdOid f34b7a6b-665d-4f5c-88c2-bcac1a6da9ae */
   private int id;
   /** @pdOid c9c45d62-f15c-465b-a138-03a60ba7b4a6 */
   private String name;
   
   /** @pdOid 970648b3-7034-45d7-8f8a-f5e81b091b47 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 8a6188b8-68ff-46b8-bd47-6bdfefdae171 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 1b130fad-966a-457e-9f4c-6f02aa28402d */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid aa94f608-158b-41b9-9181-6f4dbef192c0 */
   public void setName(String newName) {
      name = newName;
   }

}