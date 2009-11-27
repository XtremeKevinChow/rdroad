/***********************************************************************
 * Module:  PromtionItem.java
 * Author:  user
 * Purpose: Defines the Class PromtionItem
 ***********************************************************************/

package com.magic.crm.promotion2.entity;

import com.magic.crm.product2.entity.Product;
import com.magic.crm.system.entity.Status;
import java.util.*;

/** @pdOid bf754a2f-d952-4a27-801b-41880e8513eb */
public class PromtionItem {
   /** @pdOid 598f6ac7-cb7d-4bc6-b7de-f12d594f6c45 */
   protected int promItemId;
   /** @pdOid 2afc32c5-3715-4756-8ec7-ed2f36f3f0d0 */
   protected int creatorId;
   /** @pdOid 0db10bcf-5799-4e01-af3e-a7d106771238 */
   protected Date createDate;
   /** @pdOid 17215b38-d998-4a31-9048-9c4d2575aab9 */
   protected Date modifierId;
   /** @pdOid c6645083-a2ec-4ca4-8d3d-4a67a8c1417c */
   protected Date modifyDate;
   
   /** @pdRoleInfo migr=no name=Product assc=product mult=1..1 type=Aggregation */
   protected Product product;
   /** @pdRoleInfo migr=no name=Status assc=status mult=1..1 type=Aggregation */
   protected Status status;
   
   /** @pdOid 4c883b93-c257-459f-8da2-873f2793c421 */
   public int getPromItemId() {
      return promItemId;
   }
   
   /** @param newPromItemId
    * @pdOid bf7bce81-67c5-42d6-94bc-c6cb7eda2c90 */
   public void setPromItemId(int newPromItemId) {
      promItemId = newPromItemId;
   }
   
   /** @pdOid 79b72512-d98c-4ebb-bfe7-836c96a8affe */
   public int getCreatorId() {
      return creatorId;
   }
   
   /** @param newCreatorId
    * @pdOid f4bff5d4-9803-411a-bc85-d06c72781f6d */
   public void setCreatorId(int newCreatorId) {
      creatorId = newCreatorId;
   }
   
   /** @pdOid 853a13fb-72bc-4123-9119-131c5f1b07cd */
   public Date getCreateDate() {
      return createDate;
   }
   
   /** @param newCreateDate
    * @pdOid 7b71c90a-dafa-496e-835b-3d4b86049814 */
   public void setCreateDate(Date newCreateDate) {
      createDate = newCreateDate;
   }
   
   /** @pdOid 4c68fa42-fe63-4cd6-b78f-7f966a856f8e */
   public Date getModifierId() {
      return modifierId;
   }
   
   /** @param newModifierId
    * @pdOid f67ffa1d-c977-4776-9f75-7753fffee57d */
   public void setModifierId(Date newModifierId) {
      modifierId = newModifierId;
   }
   
   /** @pdOid 34e3386b-3591-4e66-91b0-5e5593ada4d6 */
   public Date getModifyDate() {
      return modifyDate;
   }
   
   /** @param newModifyDate
    * @pdOid 92789007-906a-4fb4-9c44-d9dda62d44b0 */
   public void setModifyDate(Date newModifyDate) {
      modifyDate = newModifyDate;
   }

}