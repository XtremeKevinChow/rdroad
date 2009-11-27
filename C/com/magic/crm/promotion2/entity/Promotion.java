/***********************************************************************
 * Module:  Promotion.java
 * Author:  user
 * Purpose: Defines the Class Promotion
 ***********************************************************************/

package com.magic.crm.promotion2.entity;

import com.magic.crm.system.entity.Period;
import com.magic.crm.system.entity.PromotionStatus;
import java.util.*;

/** @pdOid 00fe41a2-2a74-47e5-9a2e-71cb4cedb1f8 */
public class Promotion {
   /** @pdOid 0f2cb8fe-c350-4ba2-b54b-a40c26c16d37 */
   protected int promotionId;
   /** @pdOid 06ea4dde-615b-4a6a-af4f-0602d5f380df */
   protected String promotionName;
   /** @pdOid 0d9819bf-0c74-41d7-8210-872044d1649e */
   protected int creatorId;
   /** @pdOid 8436d07c-ab42-4f22-812a-40b03fbd5af6 */
   protected Date createDate;
   /** @pdOid 449990b0-bd60-4180-b3dd-18d91ce6be10 */
   protected int modifierId;
   /** @pdOid 4fc2baf5-3c22-40fd-899a-a924897b6d29 */
   protected Date modifyDate;
   /** @pdOid e5d79ce3-a0c1-4673-a8dc-24c63c50ec9d */
   protected String description;
   
   /** @pdRoleInfo migr=no name=Period assc=peroid mult=1..1 type=Aggregation */
   protected Period peroid;
   /** @pdRoleInfo migr=no name=PromotionStatus assc=promStatus mult=1..1 type=Composition */
   protected PromotionStatus promStatus;
   
   /** @pdOid 5f885274-f917-4c61-b446-002dd3fd48fb */
   public int getPromotionId() {
      return promotionId;
   }
   
   /** @param newPromotionId
    * @pdOid e33e3338-1d8a-4b63-892d-ebb1ab502912 */
   public void setPromotionId(int newPromotionId) {
      promotionId = newPromotionId;
   }
   
   /** @pdOid a4a8be89-f938-4b35-b3db-b5e558e410aa */
   public String getPromotionName() {
      return promotionName;
   }
   
   /** @param newPromotionName
    * @pdOid edb423aa-e5db-4330-8e59-2e98cafba3c8 */
   public void setPromotionName(String newPromotionName) {
      promotionName = newPromotionName;
   }
   
   /** @pdOid e409c2d9-8c74-497e-a3ff-579612dafd4a */
   public int getCreatorId() {
      return creatorId;
   }
   
   /** @param newCreatorId
    * @pdOid b256b909-68c3-4369-83f5-7426a3b0c04f */
   public void setCreatorId(int newCreatorId) {
      creatorId = newCreatorId;
   }
   
   /** @pdOid 67fe06c5-4096-44bb-8bc4-0d0ec5416f8b */
   public Date getCreateDate() {
      return createDate;
   }
   
   /** @param newCreateDate
    * @pdOid f603e86d-7357-4203-b6b6-decda37cd4c1 */
   public void setCreateDate(Date newCreateDate) {
      createDate = newCreateDate;
   }
   
   /** @pdOid 31934150-39f2-42cb-a070-109c6f7e0c00 */
   public int getModifierId() {
      return modifierId;
   }
   
   /** @param newModifierId
    * @pdOid 2db24693-2ef6-4e99-9091-e2f73e19be75 */
   public void setModifierId(int newModifierId) {
      modifierId = newModifierId;
   }
   
   /** @pdOid 1b361a70-d1ac-4b87-a141-68dc83698033 */
   public Date getModifyDate() {
      return modifyDate;
   }
   
   /** @param newModifyDate
    * @pdOid 8529aa13-db67-4128-b7a4-3f7b2b7d7fb8 */
   public void setModifyDate(Date newModifyDate) {
      modifyDate = newModifyDate;
   }
   
   /** @pdOid 4995055c-2fc9-46c6-88ce-de0d41761229 */
   public String getDescription() {
      return description;
   }
   
   /** @param newDescription
    * @pdOid 8ba4b3ac-738d-413a-823c-b924e4bd90d6 */
   public void setDescription(String newDescription) {
      description = newDescription;
   }

}