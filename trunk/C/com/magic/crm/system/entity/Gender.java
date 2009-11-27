/***********************************************************************
 * Module:  Gender.java
 * Author:  user
 * Purpose: Defines the Class Gender
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 68c10421-c3af-41de-b3c3-e1c6cd4bc515 */
public class Gender {
   /** @pdOid 0b1a14aa-f5f4-4b6b-a330-f97abc99dad5 */
   private String code;
   /** @pdOid dedc22f8-6a6e-4c83-acfe-c65bfbc9d128 */
   private String name;
   /** @pdOid 3c9a5597-7018-4f3b-a4d2-d6550e021712 */
   private static final Gender FEMALE = new Gender("F", "Å®");
   /** @pdOid 19ce2261-e462-4076-ae85-efac62852d04 */
   private static final Gender MALE = new Gender("M", "ÄÐ");
   
   /** @pdOid fdb5e45e-9b76-44b3-8f5d-ecbaa8651735 */
   public String getCode() {
      return code;
   }
   
   /** @pdOid 322ff6d7-74d8-4f9d-b4fa-2223b7c2ccae */
   public String getName() {
      return name;
   }
   
   /** @pdOid 8fcc3b18-acfc-4bec-a29a-963b19ae0cc5 */
   public static Gender getFEMALE() {
      return FEMALE;
   }
   
   /** @pdOid 4f684da2-a907-4374-9020-03b4a4b37255 */
   public static Gender getMALE() {
      return MALE;
   }
   
   /** @param code 
    * @param name
    * @pdOid 9c8a2657-ff42-4af1-8893-645bc3256ec6 */
   public Gender(String code, String name) {
      // TODO: implement
	   this.code = code;
	   this.name = name;
   }

}