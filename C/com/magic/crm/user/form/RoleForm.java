package com.magic.crm.user.form;


/**
 * <code>ActionForm</code> Class<br>
 * <br>
 * Autogenerated on 03/14/2003 01:39:32<br>
 * &nbsp;&nbsp;&nbsp; table = "roles"
*
* @author Generator
*/
public class RoleForm extends org.apache.struts.validator.ValidatorForm
     implements java.io.Serializable {


   /**
    * The roleId attribute.
   */
   protected String roleID = null;

   /**
    * Gets the roleId value.
   */
   public String getRoleID() {
      return roleID;
   }

   /**
    * Sets the roleId value.
   */
   public void setRoleID(String roleId) {
      this.roleID = roleId;
   }

   /**
    * The roleName attribute.
   */
   protected java.lang.String roleName = null;

   /**
    * Gets the roleName value.
   */
   public java.lang.String getRoleName() {
      return roleName;
   }

   /**
    * Sets the roleName value.
   */
   public void setRoleName(java.lang.String roleName) {
      this.roleName = roleName;
   }

   /**
    * The description attribute.
   */
   protected java.lang.String description = null;

   /**
    * Gets the description value.
   */
   public java.lang.String getDescription() {
      return description;
   }

   /**
    * Sets the description value.
   */
   public void setDescription(java.lang.String description) {
      this.description = description;
   }

    /**
    * The description attribute.
   */
   protected java.math.BigDecimal[] rights = null;

   /**
    * Gets the description value.
   */
   public java.math.BigDecimal[] getRights() {
      return rights;
   }

   /**
    * Sets the description value.
   */
   public void setRights(java.math.BigDecimal[] rights) {
      this.rights = rights;
   }

// *************************************************
   /*           add by jackey at 2005-1-26 
//    *************************************************
      /**
       * The roleId attribute.
      */
      protected String sroleId = null;

      public String getsRoleId() {
         return sroleId;
      }


      public void setsRoleId(String sroleId) {
         this.sroleId = sroleId;
      }
//    *************************************************   
//    *************************************************  
//*********************************************************//
   
//*********************************************************//
   /**
    * The roleId attribute.
   */
   protected java.math.BigDecimal rightid = null;

   /**
    * Gets the roleId value.
   */
   public java.math.BigDecimal getRightID() {
      return rightid;
   }

   /**
    * Sets the roleId value.
   */
   public void setRightID(java.math.BigDecimal rightId) {
      this.rightid = rightId;
   }
   protected String pathName = null;

   public String getpathName() {
      return pathName;
   }


   public void setpathName(String ipathName) {
      this.pathName = ipathName;
   }
   protected String path = null;

   public String getpath() {
      return path;
   }


   public void setpath(String ipath) {
      this.path = ipath;
   }
   public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
   	roleID = null;
      roleName = null;
      description = null;

   }

   
   public String toString() {
      StringBuffer results = new StringBuffer();

      results.append(getClass().getName() + "\n");
      results.append("\troleId=" + roleID + "\n");
      results.append("\troleName=" + roleName + "\n");
      results.append("\tdescription=" + description + "\n");

      return results.toString();
   }


}
