
package com.magic.crm.user.form;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * <code>ActionForm</code> Class<br>
 * <br>
 * Autogenerated on 03/14/2003 01:39:32<br>
 * &nbsp;&nbsp;&nbsp; table = "users"
*
* @author Generator
*/
public class LogonForm extends ValidatorForm{

		private String userName;

		public String getUserName() {

				return this.userName;

		}

		public void setUserName(String userName) {

				this.userName = userName;

		}

		private String password;

		public String getPassword() {

				return this.password;

		}

		public void setPassword(String password) {

			 	this.password = password;

		}
}
