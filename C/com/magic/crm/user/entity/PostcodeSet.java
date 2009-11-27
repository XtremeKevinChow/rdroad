/***********************************************************************
 * Module:  PostcodeSet.java
 * Author:  user
 * Purpose: Defines the Class PostcodeSet
 ***********************************************************************/

package com.magic.crm.user.entity;


/** @pdOid fabc2b66-6e9f-4e2a-ad75-86aa77ab9545 */
public class PostcodeSet {
   /** @pdOid 752ba553-128c-42ac-a417-4e9438305b32 */
   private int id;
   /** @pdOid 90da850b-21fc-47b7-8d3d-e4946f7c919f */
   private String postcode;
   /** @pdOid 94cb462e-ac9a-4b18-aeb0-26d92c5c4aaf */
   private double postFee;
   /** @pdOid 4087194a-05ee-459a-b772-f772e39c0a8b */
   private int isValid = 1;
   private PostcodeGroup postcodeGroup;
   /** @pdOid 3e13eb1a-8753-4eed-ab70-85cdc9c130a4 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid d7fc6821-2216-4905-9d87-6e9d458993df */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 6b1715cb-8f80-49f6-8213-3da4a6bbbe4c */
   public String getPostcode() {
      return postcode;
   }
   
   /** @param newPostcode
    * @pdOid ec48030a-00fe-4128-badf-081b9af2db0a */
   public void setPostcode(String newPostcode) {
      postcode = newPostcode;
   }
   
   /** @pdOid ea0b86fc-b681-4965-b0d7-b7e8ae6cda1d */
   public double getPostFee() {
      return postFee;
   }
   
   /** @param newPostFee
    * @pdOid 15408496-1687-4cab-8d1d-09c5b2008797 */
   public void setPostFee(double newPostFee) {
	   
		  postFee = newPostFee <= 0 ? 0 : newPostFee;
	  
	   
   }
   
   /** @pdOid 948a8051-514d-4591-a787-eb4655d75899 */
   public int getIsValid() {
      return isValid;
   }
   
   /** @param newIsValid
    * @pdOid 9e062470-cc7a-4f7a-b164-b0d09cc28f2c */
   public void setIsValid(int newIsValid) {
      isValid = newIsValid;
   }

	public PostcodeGroup getPostcodeGroup() {
		if ( postcodeGroup == null ) {
			postcodeGroup = new PostcodeGroup();
		}
		return postcodeGroup;
	}
	
	public void setPostcodeGroup(PostcodeGroup postcodeGroup) {
		this.postcodeGroup = postcodeGroup;
	}
   
}