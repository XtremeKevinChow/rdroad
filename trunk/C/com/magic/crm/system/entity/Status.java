/***********************************************************************
 * Module:  Status.java
 * Author:  user
 * Purpose: Defines the Class Status
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 3ed748fc-8f16-4005-82b6-08f9affc904d */
public class Status {
   /** @pdOid 63f800e3-236e-4ec6-b721-4e54bcf15c83 */
   protected int statusId;
   /** @pdOid 45323e32-8431-4b0e-8ec7-bb48de635b11 */
   protected int statusName;
   
   /** @pdOid 2fae20c1-9a94-42d8-97a9-174932e372d0 */
   public int getStatusId() {
      return statusId;
   }
   
   /** @param newStatusId
    * @pdOid 9984e74b-07ca-4344-916e-45da409d6f2e */
   public void setStatusId(int newStatusId) {
      statusId = newStatusId;
   }
   
   /** @pdOid d0661a06-2d74-4131-b7e5-7a20165807d2 */
   public int getStatusName() {
      return statusName;
   }
   
   /** @param newStatusName
    * @pdOid 48e71fb0-0461-4bf9-bf73-41c159912348 */
   public void setStatusName(int newStatusName) {
      statusName = newStatusName;
   }

}