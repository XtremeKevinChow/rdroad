
package com.magic.crm.user;

import org.apache.struts.action.*;


/**
 * This is the authentication object to verify user's identity and access right,
 * pull necessary user info from the DB.
 * <p>Title: Build the Side bar</p>
 * <p>Description: For fe credit evaluation project</p>
 * <p>Copyright: Copyright (c) 2003 , FEChina Inc</p>
 * <p>Company: FEChina, Inc</p>
 * @author $Author: wli $
 * @version $Revision: 1.1 $
 */

public class Authentication {


  private ActionErrors errors = new ActionErrors();


  /**
   * Constructor.
   */
  public Authentication() {

  }

  /**
   * Get authenticaiton errors.
   * @return errors
   */
  public ActionErrors getErrors(){
    return this.errors;
  }

  /**
   * Verify user/passwd pair, put errors into <code>errors</code> if any.
   * @param user
   * @param password
   */
  public void login( String user, String password){
    // TODO: add some codes here to talk to the database and verify the
    // user/passwd pair, if any error occurs, put error message to the errors.

  }

}