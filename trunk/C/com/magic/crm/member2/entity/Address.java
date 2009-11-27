/***********************************************************************
 * Module:  Address.java
 * Author:  user
 * Purpose: Defines the Class Address
 ***********************************************************************/

package com.magic.crm.member2.entity;

import com.magic.crm.system.entity.DeliveryType;
import com.magic.crm.system.entity.PayMethod;
import java.util.*;

/** @pdOid c525948a-7bb1-437f-831e-35decf9fdffb */
public class Address {
   /** @pdRoleInfo migr=no name=DeliveryType assc=deliveryType mult=1..1 type=Aggregation */
   private DeliveryType deliveryType;
   /** @pdRoleInfo migr=no name=PayMethod assc=payMethod mult=1..1 type=Aggregation */
   private PayMethod payMethod;
   
   /** @pdOid be7aaaf8-443e-4e43-ac98-fb43fccca784 */
   protected int id;
   /** @pdOid cbc00d4a-3759-481e-8ef4-6f9acf7ba068 */
   protected String persons;
   /** @pdOid d9182345-eb1b-410d-911f-3035e5bcf226 */
   protected String address;
   /** @pdOid 3c12e52c-e004-468a-bfc8-040722ee4fe7 */
   protected String telephone;
   /** @pdOid d5bac249-ddeb-49f5-aa7c-99d165a5e3e0 */
   protected String postcode;
   
   /** @pdOid 7d3da216-ac40-4558-8d9b-2a52e19c3aef */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 52b6e73d-7f15-4ee1-a361-fc4cd4c1a3e9 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 66d877b3-ea6d-4b4d-b12f-b6fb2f4dbea6 */
   public String getPersons() {
      return persons;
   }
   
   /** @param newPersons
    * @pdOid cb972674-01c7-4c80-bad6-c9bf3ceb9f45 */
   public void setPersons(String newPersons) {
      persons = newPersons;
   }
   
   /** @pdOid 17e2cb94-ed37-47c7-9dfd-eb34f8dd8378 */
   public String getAddress() {
      return address;
   }
   
   /** @param newAddress
    * @pdOid 4eb299d0-cd54-44ef-8729-d3d728cb210f */
   public void setAddress(String newAddress) {
      address = newAddress;
   }
   
   /** @pdOid 1786fbbd-435a-4e53-b6e4-6207fdb4d03e */
   public String getTelephone() {
      return telephone;
   }
   
   /** @param newTelephone
    * @pdOid 66001dc0-bfe0-4d03-a8c8-5ccb7f3d31bb */
   public void setTelephone(String newTelephone) {
      telephone = newTelephone;
   }
   
   /** @pdOid 899770e6-9d7f-4cb4-89e4-344159ab7428 */
   public String getPostcode() {
      return postcode;
   }
   
   /** @param newPostcode
    * @pdOid 459299cc-ce73-4157-9478-1a525c517ef3 */
   public void setPostcode(String newPostcode) {
      postcode = newPostcode;
   }

}