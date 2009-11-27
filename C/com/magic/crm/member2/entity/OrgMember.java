/***********************************************************************
 * Module:  OrgMember.java
 * Author:  user
 * Purpose: Defines the Class OrgMember
 ***********************************************************************/

package com.magic.crm.member2.entity;

import java.util.*;

/** @pdOid 124a3b72-62a0-4609-99be-7a112af06206 */
public class OrgMember extends Member {
   /** @pdOid dd1e80c9-6e6c-485b-bd2f-e383e7a1f2f8 */
   private java.lang.String orgName;
   
   /** @pdOid 9dd50b68-48e3-480c-b3f4-685b300a734b */
   public java.lang.String getOrgName() {
      return orgName;
   }
   
   /** @param newOrgName
    * @pdOid 0d7d2d55-7782-4cba-b9dc-58ff740c1d9d */
   public void setOrgName(java.lang.String newOrgName) {
      orgName = newOrgName;
   }

}