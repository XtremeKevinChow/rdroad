/***********************************************************************
 * Module:  MainAddress.java
 * Author:  user
 * Purpose: Defines the Class MainAddress
 ***********************************************************************/

package com.magic.crm.member2.entity;

import java.util.*;

/** @pdOid b2818b34-fb1c-4043-b561-e7e976f8a0d4 */
public class MainAddress {
   /** @pdOid d3386636-daa2-4c2e-b8c3-cb0d3e369e0b */
   protected String address;
   /** @pdOid df77017f-0632-427f-94b5-770e411e58fb */
   protected String telephone;
   /** @pdOid 1f803e48-5493-4b50-808f-de2ed8303987 */
   protected String companyPhone;
   /** @pdOid 1e0891cd-8ba7-4cc4-8746-e3be1fbe26ed */
   protected String familyPhone;
   /** @pdOid 72f90a15-f2aa-4bce-9b43-963ced3c6c11 */
   protected String postcode;
   /** @pdOid 7dbfb4c5-6b9e-4b68-924e-c1d48ed5ec01 */
   protected String email;
   
   /** @pdOid 161e78dd-e527-4213-b297-b08970cc78e5 */
   public String getAddress() {
      return address;
   }
   
   /** @param newAddress
    * @pdOid 373a4e14-1f18-4ba0-a129-7c754bad01fc */
   public void setAddress(String newAddress) {
      address = newAddress;
   }
   
   /** @pdOid 940cefb7-87f7-408a-ad78-221c6a1de945 */
   public String getTelephone() {
      return telephone;
   }
   
   /** @param newTelephone
    * @pdOid 88cd7501-c06e-48f5-a94b-0f0021c32253 */
   public void setTelephone(String newTelephone) {
      telephone = newTelephone;
   }
   
   /** @pdOid 1e9d1ec0-8fed-47b1-b322-16c48674a753 */
   public String getCompanyPhone() {
      return companyPhone;
   }
   
   /** @param newCompanyPhone
    * @pdOid 1df07df4-799b-4a87-b918-85d42e0df78e */
   public void setCompanyPhone(String newCompanyPhone) {
      companyPhone = newCompanyPhone;
   }
   
   /** @pdOid 8da14cc2-84f7-4bd4-be46-1edace923cd2 */
   public String getFamilyPhone() {
      return familyPhone;
   }
   
   /** @param newFamilyPhone
    * @pdOid ab5f1589-7567-44b4-ab51-08e54a507f98 */
   public void setFamilyPhone(String newFamilyPhone) {
      familyPhone = newFamilyPhone;
   }
   
   /** @pdOid 9754437c-51e6-4b20-8312-f02ab263820b */
   public String getPostcode() {
      return postcode;
   }
   
   /** @param newPostcode
    * @pdOid 1e4de961-ba2c-4da1-85af-d633f67ce2d0 */
   public void setPostcode(String newPostcode) {
      postcode = newPostcode;
   }
   
   /** @pdOid 4d8ae4b9-c76b-4922-9f5a-fd2b8c55d2be */
   public String getEmail() {
      return email;
   }
   
   /** @param newEmail
    * @pdOid bfb6bee2-4eaa-40f0-b457-39692f52f2da */
   public void setEmail(String newEmail) {
      email = newEmail;
   }

}