package com.magic.crm.user.entity;


/**
 * Data Bean Class<br>
 * <br>
 * Autogenerated on 03/17/2003 10:06:41<br>
 * &nbsp;&nbsp;&nbsp; table = "roles"
*
* @author Generator
*/
public class Role implements java.io.Serializable {


   /**
    * The roleId attribute.
   */
   protected String RoleID = null;

   /**
    * Gets the roleId value.
   */
   public String getRoleID() {
      return RoleID;
   }

   /**
    * Sets the roleId value.
   */
   public void setRoleID(String roleId) {
      this.RoleID = roleId;
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
   public String toString() {
      StringBuffer results = new StringBuffer();

      results.append(getClass().getName() + "\n");
      results.append("\tRoleId=" + RoleID + "\n");
      results.append("\tRoleName=" + roleName + "\n");
      results.append("\tdescription=" + description + "\n");

      return results.toString();
   }


}