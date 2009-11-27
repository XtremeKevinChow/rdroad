package com.magic.crm.util;


/**
 * <p>Title: FE China Credit System</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FE China</p>
 * @author Liao Yu Hong
 * @version 1.0
 */
 
 public final class ControlledError {

		private String errorTitle;
		
		public String getErrorTitle(){
		
				return this.errorTitle;
		
		}
		
		public void setErrorTitle(String errorTitle) {
			
				this.errorTitle = errorTitle;	
			
		}
		
		private String errorBody;
		
		public String getErrorBody(){
			
				return this.errorBody;	
			
		} 
		
		public void setErrorBody(String errorBody){
		
				this.errorBody = errorBody;
		}
		
	
 }