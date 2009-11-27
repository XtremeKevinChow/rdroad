/***********************************************************************
 * Module:  Catalog.java
 * Author:  user
 * Purpose: Defines the Class Catalog
 ***********************************************************************/

package com.magic.crm.promotion2.entity;

import com.magic.crm.system.entity.PriceListStatus;
import com.magic.crm.system.entity.Period;
import java.util.*;

/** @pdOid 7cc52300-f669-4204-ad6f-f446270b54de */
public class Catalog {
   /** @pdRoleInfo migr=no name=Recruitment assc=recruitment mult=1..1 type=Composition */
   private Recruitment recruitment;
   
   /** @pdOid 7ae04f25-0ac6-4df6-9b54-d1fe8db09786 */
   protected int catalogId;
   /** @pdOid 4c3ef736-8717-4948-8b36-012210b24f6f */
   protected String catalogNo;
   /** @pdOid 62c1c591-2696-4e66-a449-27012bd50b50 */
   protected String catalogName;
   /** @pdOid 5af733fc-05c2-4921-bcf6-c9c5c2953c62 */
   protected String description;
   
   /** @pdRoleInfo migr=no name=PriceListStatus assc=status mult=1..1 type=Composition */
   protected PriceListStatus status;
   /** @pdRoleInfo migr=no name=Period assc=period mult=1..1 type=Composition */
   protected Period period;
   
   /** @pdOid 622e09bf-4798-40a1-8216-310fb4cd90bb */
   public int getCatalogId() {
      return catalogId;
   }
   
   /** @param newCatalogId
    * @pdOid 06095433-5d20-4678-8f88-7ebddd41e602 */
   public void setCatalogId(int newCatalogId) {
      catalogId = newCatalogId;
   }
   
   /** @pdOid 0f9cf39b-d92d-4f29-a292-43daaf006f52 */
   public String getCatalogNo() {
      return catalogNo;
   }
   
   /** @param newCatalogNo
    * @pdOid 89cdc640-f379-4aa6-8d7e-313056748d0b */
   public void setCatalogNo(String newCatalogNo) {
      catalogNo = newCatalogNo;
   }
   
   /** @pdOid 49e35c93-4d24-4d4e-a0f4-12d7007e24c0 */
   public String getCatalogName() {
      return catalogName;
   }
   
   /** @param newCatalogName
    * @pdOid 8003e9a1-3f58-4653-b5f4-ca3e1a85ab2f */
   public void setCatalogName(String newCatalogName) {
      catalogName = newCatalogName;
   }
   
   /** @pdOid 42e6d62a-6979-43d0-8e90-976ac2c99811 */
   public String getDescription() {
      return description;
   }
   
   /** @param newDescription
    * @pdOid b828b0c2-7c86-482b-b6e0-cac32348b765 */
   public void setDescription(String newDescription) {
      description = newDescription;
   }

}