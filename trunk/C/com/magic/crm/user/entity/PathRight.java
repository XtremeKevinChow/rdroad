package com.magic.crm.user.entity;


/**
 * Data Bean Class<br>
 * <br>
 * Autogenerated on 03/17/2003 10:06:41<br>
 * &nbsp;&nbsp;&nbsp; table = "pathright"
*
* @author Generator
*/
public class PathRight implements java.io.Serializable {



   /**
    * The path attribute.
   */
   protected java.lang.String path = null;

   /**
    * Gets the path value.
   */
   public java.lang.String getPath() {
      return path;
   }

   /**
    * Sets the path value.
   */
   public void setPath(java.lang.String path) {
      this.path = path;
   }

   /**
    * The rightId attribute.
   */
   protected java.math.BigDecimal rightId = null;

   /**
    * Gets the rightId value.
   */
   public java.math.BigDecimal getRightId() {
      return rightId;
   }

   /**
    * Sets the rightId value.
   */
   public void setRightId(java.math.BigDecimal rightId) {
      this.rightId = rightId;
   }

   public String toString() {
      StringBuffer results = new StringBuffer();

      results.append(getClass().getName() + "\n");
      results.append("\tpath=" + path + "\n");
      results.append("\trightId=" + rightId + "\n");

      return results.toString();
   }


}
