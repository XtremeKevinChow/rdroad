/***********************************************************************
 * Module:  PostcodeGroup.java
 * Author:  user
 * Purpose: Defines the Class PostcodeGroup
 ***********************************************************************/

package com.magic.crm.user.entity;

/** @pdOid 8ae1646e-2762-47d9-be60-dadc6ade7ec7 */
public class PostcodeGroup {
   /** @pdOid 978aa1d4-bace-4ac9-9d69-1791eb6c4752 */
   private int id;
   /** @pdOid 916e8e09-f465-4038-8a79-b58e9399fb36 */
   private String groupName;
   /** @pdOid 916e8e09-f465-4038-8a79-b58e9399fb36 */
   private String description;
   /** @pdOid f479c09b-22d3-4023-9d2b-41ea5206f34c */
   private int isValid = 1;
   
   /** @pdRoleInfo migr=no name=PostcodeSet assc=postcodeSet coll=java.util.Collection impl=java.util.ArrayList mult=0..* */
   private java.util.Collection postcodeSet;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection getPostcodeSet() {
      if (postcodeSet == null)
         postcodeSet = new java.util.ArrayList();
      return postcodeSet;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorPostcodeSet() {
      if (postcodeSet == null)
         postcodeSet = new java.util.ArrayList();
      return postcodeSet.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newPostcodeSet */
   public void setPostcodeSet(java.util.Collection newPostcodeSet) {
      removeAllPostcodeSet();
      for (java.util.Iterator iter = newPostcodeSet.iterator(); iter.hasNext();)
         addPostcodeSet((PostcodeSet)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPostcodeSet */
   public void addPostcodeSet(PostcodeSet newPostcodeSet) {
      if (newPostcodeSet == null)
         return;
      if (this.postcodeSet == null)
         this.postcodeSet = new java.util.ArrayList();
      if (!this.postcodeSet.contains(newPostcodeSet))
         this.postcodeSet.add(newPostcodeSet);
   }
   
   /** @pdGenerated default remove
     * @param oldPostcodeSet */
   public void removePostcodeSet(PostcodeSet oldPostcodeSet) {
      if (oldPostcodeSet == null)
         return;
      if (this.postcodeSet != null)
         if (this.postcodeSet.contains(oldPostcodeSet))
            this.postcodeSet.remove(oldPostcodeSet);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPostcodeSet() {
      if (postcodeSet != null)
         postcodeSet.clear();
   }
   
   /** @pdOid dcff2740-f0d0-46ec-9fbd-ae2ef7d46051 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 2d85f2bf-cc41-4412-84a9-4f1f4ae7116f */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 00521c91-5c14-418d-b547-58f5901811ad */
   public String getGroupName() {
      return groupName;
   }
   
   /** @param newGroupName
    * @pdOid 043c4524-77f0-4d9b-a882-1e4b56e4bd81 */
   public void setGroupName(String newGroupName) {
      groupName = newGroupName;
   }
   
   public String getDescription() {
	return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

/** @pdOid 33fdfe7d-2c49-45f2-b1d6-df042bd66407 */
   public int getIsValid() {
      return isValid;
   }
   
   /** @param newIsValid
    * @pdOid 2e1ae196-896a-4fae-8dd8-ffedf406d8d9 */
   public void setIsValid(int newIsValid) {
      isValid = newIsValid;
   }

}