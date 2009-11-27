/***********************************************************************
 * Module:  Certificate.java
 * Author:  user
 * Purpose: Defines the Class Certificate
 ***********************************************************************/

package com.magic.crm.member2.entity;

import com.magic.crm.system.entity.CertificateType;
import java.util.*;

/** @pdOid 26fbfd92-d85b-4c5b-99f2-17f8491b8987 */
public class Certificate {
   /** @pdOid c6aa20b6-004b-40d4-bc99-7220f29e356d */
   private int certificateName;
   /** @pdOid cabeb6ce-d431-4fef-a8ae-810a2a2ffa85 */
   private CertificateType certificateType;
   
   /** @pdOid bc70edad-f618-46e0-bbea-a97e9e4b7629 */
   public int getCertificateName() {
      return certificateName;
   }
   
   /** @param newCertificateName
    * @pdOid 26329541-a7ff-48af-9794-d1b95344ef9d */
   public void setCertificateName(int newCertificateName) {
      certificateName = newCertificateName;
   }
   
   /** @pdOid b71b0ebe-230b-4400-a5a2-e70529215939 */
   public CertificateType getCertificateType() {
      return certificateType;
   }
   
   /** @param newCertificateType
    * @pdOid c44cf1dd-3e13-4122-a84c-d69c9d13363d */
   public void setCertificateType(CertificateType newCertificateType) {
      certificateType = newCertificateType;
   }

}