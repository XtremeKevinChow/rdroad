package com.magic.crm.member.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class PostCodeFileForm extends ActionForm {
	
	private static final long serialVersionUID = -4319597315394043375L;
	
	private FormFile file;

	
	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	
	
}
