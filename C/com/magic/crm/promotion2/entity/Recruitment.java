/***********************************************************************
 * Module:  Recruitment.java
 * Author:  user
 * Purpose: Defines the Class Recruitment
 ***********************************************************************/

package com.magic.crm.promotion2.entity;

import com.magic.crm.system.entity.RecruitmemtType;
import com.magic.crm.system.entity.Period;
import com.magic.crm.system.entity.PriceListStatus;
import java.util.*;

/** @pdOid a94b95df-77a8-437e-9fc9-95ed41c14b05 */
public class Recruitment {
   /** @pdRoleInfo migr=no name=EntryGift assc=entryGift coll=Collection impl=ArrayList mult=0..* */
   private Collection entryGift;
   /** @pdRoleInfo migr=no name=RecruitmemtType assc=recruitmentType mult=1..1 type=Composition */
   private RecruitmemtType recruitmentType;
   /** @pdRoleInfo migr=no name=Period assc=period mult=1..1 type=Composition */
   private Period period;
   /** @pdRoleInfo migr=no name=PriceListStatus assc=status mult=1..1 type=Composition */
   private PriceListStatus status;
   
   
   /** @pdGenerated default getter */
   public Collection getEntryGift() {
      if (entryGift == null)
         entryGift = new ArrayList();
      return entryGift;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorEntryGift() {
      if (entryGift == null)
         entryGift = new ArrayList();
      return entryGift.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newEntryGift */
   public void setEntryGift(Collection newEntryGift) {
      removeAllEntryGift();
      for (java.util.Iterator iter = newEntryGift.iterator(); iter.hasNext();)
         addEntryGift((EntryGift)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newEntryGift */
   public void addEntryGift(EntryGift newEntryGift) {
      if (newEntryGift == null)
         return;
      if (this.entryGift == null)
         this.entryGift = new ArrayList();
      if (!this.entryGift.contains(newEntryGift))
         this.entryGift.add(newEntryGift);
   }
   
   /** @pdGenerated default remove
     * @param oldEntryGift */
   public void removeEntryGift(EntryGift oldEntryGift) {
      if (oldEntryGift == null)
         return;
      if (this.entryGift != null)
         if (this.entryGift.contains(oldEntryGift))
            this.entryGift.remove(oldEntryGift);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllEntryGift() {
      if (entryGift != null)
         entryGift.clear();
   }
   
   /** @pdOid 298529b2-c016-407e-b67e-7bd0241c1bcb */
   protected int recruitmentId;
   /** @pdOid 7ed6c014-a9ea-4d9a-aa22-a1a31f406f71 */
   protected String mscCode;
   /** @pdOid 9075cdc5-2178-4ed3-9d37-295c9dab7e1f */
   protected String recruitmentName;
   /** @pdOid 38a30d4d-1110-41b8-9965-c2060de1a6f9 */
   protected double entity;
   /** @pdOid 1ffa77c0-7770-4ded-a0d0-d6868e3758fc */
   protected String description;
   
   /** @pdOid 4b3a0d67-24f2-4a63-bcfe-948cbc0051f7 */
   public int getRecruitmentId() {
      return recruitmentId;
   }
   
   /** @param newRecruitmentId
    * @pdOid c0f26d64-06e0-4757-abf4-53b511305e38 */
   public void setRecruitmentId(int newRecruitmentId) {
      recruitmentId = newRecruitmentId;
   }
   
   /** @pdOid 34cec8e2-e563-43d0-8f3c-f5527d6a1a72 */
   public String getMscCode() {
      return mscCode;
   }
   
   /** @param newMscCode
    * @pdOid 53dcc56d-e76d-4a9c-943e-20dd64b7314f */
   public void setMscCode(String newMscCode) {
      mscCode = newMscCode;
   }
   
   /** @pdOid cce27579-be08-42e3-aef4-65a809447f98 */
   public String getRecruitmentName() {
      return recruitmentName;
   }
   
   /** @param newRecruitmentName
    * @pdOid 908c8fab-2024-46f9-83f0-5bc2f6895905 */
   public void setRecruitmentName(String newRecruitmentName) {
      recruitmentName = newRecruitmentName;
   }
   
   /** @pdOid e76e09da-0e5a-45f1-87eb-e97a1fd24186 */
   public double getEntity() {
      return entity;
   }
   
   /** @param newEntity
    * @pdOid 0dcd2435-dcf0-4760-be4e-db5a2e4a08da */
   public void setEntity(double newEntity) {
      entity = newEntity;
   }
   
   /** @pdOid 75d63674-e860-4a32-b980-ef0d590dd4dc */
   public String getDescription() {
      return description;
   }
   
   /** @param newDescription
    * @pdOid d631066d-a157-4c1b-b362-4466510a842e */
   public void setDescription(String newDescription) {
      description = newDescription;
   }

}