/***********************************************************************
 * Module:  Period.java
 * Author:  user
 * Purpose: Defines the Class Period
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid d5d799d9-4e6a-4059-a1fc-610bbc99c2a8 */
public class Period {
   /** @pdOid 188e4324-a272-42d0-87dd-f5d3521fda5b */
   private Date startDate;
   /** @pdOid 9d98521d-96ea-4b04-8e75-25582495a43b */
   private Date endDate;
   
   /** @pdOid 1fe7eed7-0d60-468e-8282-7141177fa559 */
   public Date getStartDate() {
      return startDate;
   }
   
   /** @param newStartDate
    * @pdOid eb8cd482-b3e0-49bf-b55f-27b5bb925ce4 */
   public void setStartDate(Date newStartDate) {
      startDate = newStartDate;
   }
   
   /** @pdOid 7abe2a11-9072-4ecd-9b1b-39767332de0f */
   public Date getEndDate() {
      return endDate;
   }
   
   /** @param newEndDate
    * @pdOid 31fd1b06-8ebe-4654-b301-40517be37d2e */
   public void setEndDate(Date newEndDate) {
      endDate = newEndDate;
   }

}