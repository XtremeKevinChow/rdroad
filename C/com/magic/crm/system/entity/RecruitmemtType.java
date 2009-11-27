/***********************************************************************
 * Module:  RecruitmemtType.java
 * Author:  user
 * Purpose: Defines the Class RecruitmemtType
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 0499e048-159b-4a72-8220-31c6632cec67 */
public class RecruitmemtType {
   /** @pdRoleInfo migr=no name=RecruitmemtTypeCategory assc=category mult=1..1 type=Aggregation */
   private RecruitmemtTypeCategory category;
   
   /** @pdOid 2b517925-22c8-4512-b81e-02a8e1790733 */
   protected int id;
   /** @pdOid 4c8b030c-8d9b-4763-b524-11f5a300bdfe */
   protected String name;
   
   /** @pdOid 656566fe-3616-4e3f-9309-083638c53f37 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 108e15af-e049-4898-9e02-c89da4539fd8 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 3626caa8-15b0-4224-8a51-946ffe57d9ac */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid 611df22a-c1f4-49e1-9408-eecdc5fec00d */
   public void setName(String newName) {
      name = newName;
   }

	public RecruitmemtTypeCategory getCategory() {
		return (category == null ? new RecruitmemtTypeCategory() : category);
	}
	
	public void setCategory(RecruitmemtTypeCategory category) {
		this.category = category;
	}

}