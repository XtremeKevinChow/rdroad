package com.magic.crm.user.form;


/**
 * <code>ActionForm</code> Class<br>
 * <br>
 * Autogenerated on 03/14/2003 01:39:32<br>
 * &nbsp;&nbsp;&nbsp; table = "roleright"
*
* @author Generator
*/
public class RoleRightForm extends org.apache.struts.action.ActionForm
     implements java.io.Serializable {


   /**
    * The roleId attribute.
   */
   protected java.lang.String roleId = null;

   /**
    * Gets the roleId value.
   */
   public java.lang.String getRoleId() {
      return roleId;
   }

   /**
    * Sets the roleId value.
   */
   public void setRoleId(java.lang.String roleId) {
      this.roleId = roleId;
   }

   /**
    * The rightId attribute.
   */
   protected java.lang.String rightId = null;

   /**
    * Gets the rightId value.
   */
   public java.lang.String getRightId() {
      return rightId;
   }

   /**
    * Sets the rightId value.
   */
   public void setRightId(java.lang.String rightId) {
      this.rightId = rightId;
   }

   public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
      roleId = null;
      rightId = null;
   }

   public String toString() {
      StringBuffer results = new StringBuffer();

      results.append(getClass().getName() + "\n");
      results.append("\troleId=" + roleId + "\n");
      results.append("\trightId=" + rightId + "\n");

      return results.toString();
   }


}
