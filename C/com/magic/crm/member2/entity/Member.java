/***********************************************************************
 * Module:  Member.java
 * Author:  user
 * Purpose: Defines the Class Member
 ***********************************************************************/

package com.magic.crm.member2.entity;

import com.magic.crm.promotion2.entity.Recruitment;
import com.magic.crm.system.entity.Club;
import com.magic.crm.system.entity.CatalogType;
import com.magic.crm.system.entity.Gender;
import com.magic.crm.system.entity.CardType;
import java.util.*;

/** @pdOid 8b92130d-800a-4d34-9427-756d19d6951d */
public class Member {
   /** @pdRoleInfo migr=no name=Deposit assc=deposit mult=1..1 type=Composition */
   private Deposit deposit;
   /** @pdRoleInfo migr=no name=Recruitment assc=recruitment mult=1..1 type=Aggregation */
   private Recruitment recruitment;
   /** @pdRoleInfo migr=no name=Club assc=club mult=1..1 type=Composition */
   private Club club;
   /** @pdRoleInfo migr=no name=Certificate assc=certificate mult=1..1 type=Aggregation */
   private Certificate certificate;
   /** @pdRoleInfo migr=no name=Member assc=cmdMember mult=0..1 */
   private Member cmdMemberB;
   /** @pdRoleInfo migr=no name=MemberAward assc=memberAward mult=1..1 */
   private MemberAward memberAward;
   /** @pdRoleInfo migr=no name=MainAddress assc=mainAddress mult=1..1 type=Composition */
   private MainAddress mainAddress;
   /** @pdRoleInfo migr=no name=CatalogType assc=catalogTye mult=1..1 type=Aggregation */
   private CatalogType catalogTye;
   /** @pdRoleInfo migr=no name=Gender assc=gender mult=1..1 type=Aggregation */
   private Gender gender;
   /** @pdRoleInfo migr=no name=CardType assc=cardType mult=1..1 type=Aggregation */
   private CardType cardType;
   
   /** @pdOid e912e8c3-9bb7-478f-bd71-201c42161d09 */
   protected int memberId;
   /** @pdOid d8638560-d76c-47fb-885c-ccf32e653774 */
   protected String cardId;
   /** @pdOid 2df6f542-738c-4a1e-a8c7-3829504c4540 */
   protected String name;
   /** @pdOid a7ad03ca-6281-491d-a435-234a873916a8 */
   protected String sex;
   /** @pdOid 4f09692c-8f43-426c-8f5c-4809cd14cef2 */
   protected Date createDate;
   /** @pdOid 6160c3e7-3e2c-4433-b902-4439aa6eece7 */
   protected int purchaseCount;
   /** @pdOid d394c6c4-dfab-4443-b876-3e214e363d33 */
   protected int animusCount;
   
   /** @pdRoleInfo migr=no name=Address assc=addresses coll=Collection impl=ArrayList mult=1..* */
   protected Collection addresses;
   
   
   /** @pdGenerated default getter */
   public Collection getAddresses() {
      if (addresses == null)
         addresses = new ArrayList();
      return addresses;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAddresses() {
      if (addresses == null)
         addresses = new ArrayList();
      return addresses.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAddresses */
   public void setAddresses(Collection newAddresses) {
      removeAllAddresses();
      for (java.util.Iterator iter = newAddresses.iterator(); iter.hasNext();)
         addAddresses((Address)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newAddress */
   public void addAddresses(Address newAddress) {
      if (newAddress == null)
         return;
      if (this.addresses == null)
         this.addresses = new ArrayList();
      if (!this.addresses.contains(newAddress))
         this.addresses.add(newAddress);
   }
   
   /** @pdGenerated default remove
     * @param oldAddress */
   public void removeAddresses(Address oldAddress) {
      if (oldAddress == null)
         return;
      if (this.addresses != null)
         if (this.addresses.contains(oldAddress))
            this.addresses.remove(oldAddress);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAddresses() {
      if (addresses != null)
         addresses.clear();
   }
   
   /** @pdOid 736d93bb-ed9f-4e75-a66b-b8203e387d39 */
   public int getMemberId() {
      return memberId;
   }
   
   /** @param newMemberId
    * @pdOid 366a7b1c-0ab2-4530-af9f-89f49070b85a */
   public void setMemberId(int newMemberId) {
      memberId = newMemberId;
   }
   
   /** @pdOid 94fa46a8-d81d-4dc0-a4f7-bd5edd14c4b2 */
   public String getCardId() {
      return cardId;
   }
   
   /** @param newCardId
    * @pdOid 842cb8a0-fa2b-4711-901e-bf0f7d6f52ae */
   public void setCardId(String newCardId) {
      cardId = newCardId;
   }
   
   /** @pdOid b7c30db3-1bc4-4b17-8c62-a28e6edfa1d8 */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid cc226926-6972-4f5d-b724-8c256f4bbf66 */
   public void setName(String newName) {
      name = newName;
   }
   
   /** @pdOid bdd9a365-a7cb-46d3-818e-619b3e220043 */
   public int getPurchaseCount() {
      return purchaseCount;
   }
   
   /** @param newPurchaseCount
    * @pdOid c17f1f58-623f-4c5f-a73d-19b21b4a56fd */
   public void setPurchaseCount(int newPurchaseCount) {
      purchaseCount = newPurchaseCount;
   }
   
   /** @pdOid 4e35fc94-a2ad-4082-b29d-26ad1ff530fa */
   public int getAnimusCount() {
      return animusCount;
   }
   
   /** @param newAnimusCount
    * @pdOid a64b5e60-9acb-4c6d-962f-aa8d1a52f9bd */
   public void setAnimusCount(int newAnimusCount) {
      animusCount = newAnimusCount;
   }
   
   /** @pdOid a1a6f0a4-b38a-4a28-905c-edc3895ea029 */
   public String getSex() {
      return sex;
   }
   
   /** @param newSex
    * @pdOid c5b531ba-1823-4402-a1eb-e02b7b15a0e9 */
   public void setSex(String newSex) {
      sex = newSex;
   }
   
   /** @pdOid 66c904f5-d55a-43fc-a38f-5fdd71896d31 */
   public Date getCreateDate() {
      return createDate;
   }
   
   /** @param newCreateDate
    * @pdOid 689fc99b-a1f5-4afd-9e02-e3d671ba6d1e */
   public void setCreateDate(Date newCreateDate) {
      createDate = newCreateDate;
   }

}