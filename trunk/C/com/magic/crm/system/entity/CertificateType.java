/***********************************************************************
 * Module:  CertificateType.java
 * Author:  user
 * Purpose: Defines the Class CertificateType
 ***********************************************************************/

package com.magic.crm.system.entity;

import java.util.*;

/** @pdOid 71adc062-3135-435f-be86-4605897bac8b */
public class CertificateType {
   /** @pdOid a7d6017c-5da3-4254-aece-89e2a81a4716 */
   private int id;
   /** @pdOid ac05ac04-101e-4d62-a3c4-a129ffa74bdf */
   private String name;
   
   /** @pdOid e26db107-ff14-4ce0-9de3-907c4e32759e */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 5cc43d02-8a69-4230-8c86-d2a9c07895d1 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid 43f0c4a3-4806-4577-8cf5-00243351923d */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid 758f03d9-6931-4fd2-ad83-cb4d8c26d1d6 */
   public void setName(String newName) {
      name = newName;
   }

}