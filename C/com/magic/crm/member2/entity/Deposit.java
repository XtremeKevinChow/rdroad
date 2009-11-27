/***********************************************************************
 * Module:  Deposit.java
 * Author:  user
 * Purpose: Defines the Class Deposit
 ***********************************************************************/

package com.magic.crm.member2.entity;

import com.magic.crm.system.entity.MemberLevel;
import java.util.*;

/** @pdOid 426c7b01-be67-4edf-88d0-b04a532956ec */
public class Deposit {
   /** @pdOid 64071464-e1b2-4ca3-9245-0c1b1558b840 */
   private double deposit;
   /** @pdOid c0607bbb-9f6a-46b6-8800-0963800bcccc */
   private double frozenCredit;
   /** @pdOid f5e0c5f7-1ebb-43dd-81ca-5b2f34ef989b */
   private int exp;
   /** @pdOid b29520e3-1e8b-4279-af6f-b68b06e5e9e0 */
   private int amountExp;
   /** @pdOid 09a533c7-9fb4-462c-99d6-78ebadcd1214 */
   private int oldAmountExp;
   
   /** @pdRoleInfo migr=no name=MemberLevel assc=memberLevel mult=1..1 type=Composition */
   private MemberLevel memberLevel;
   
   /** @pdOid db9b02b9-a981-4c1f-a109-7de959f637be */
   public double getDeposit() {
      return deposit;
   }
   
   /** @param newDeposit
    * @pdOid 823c9b28-b0b5-4c70-89d7-2f7dd5e713db */
   public void setDeposit(double newDeposit) {
      deposit = newDeposit;
   }
   
   /** @pdOid 14abfea7-62ce-4d79-b436-b5f98f8fda94 */
   public double getFrozenCredit() {
      return frozenCredit;
   }
   
   /** @param newFrozenCredit
    * @pdOid d0f24d9f-a0c7-465e-9d09-c7b29ea31a08 */
   public void setFrozenCredit(double newFrozenCredit) {
      frozenCredit = newFrozenCredit;
   }
   
   /** @pdOid 187d63bc-7ba0-4372-9bd7-0b483b68ec21 */
   public int getAmountExp() {
      return amountExp;
   }
   
   /** @param newAmountExp
    * @pdOid 93258da1-55f1-43de-9e7a-977bc39ebd57 */
   public void setAmountExp(int newAmountExp) {
      amountExp = newAmountExp;
   }
   
   /** @pdOid 4faba5b4-6e49-4e89-a0e3-e9653c3df913 */
   public int getOldAmountExp() {
      return oldAmountExp;
   }
   
   /** @param newOldAmountExp
    * @pdOid 0dfe04b6-a2a4-46b4-a217-167cb731ef62 */
   public void setOldAmountExp(int newOldAmountExp) {
      oldAmountExp = newOldAmountExp;
   }

}