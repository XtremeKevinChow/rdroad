/*
 * Created on 2005-1-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.common;

import org.apache.struts.action.ActionForm;

/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class WebForm extends ActionForm {
	/* 操作标志 */
	protected String actionType = null;

	/* 翻页处理 */
	protected PageAttribute pa = new PageAttribute(20);
    public void setPageAttribute(PageAttribute pa) {
    	this.pa = pa;
    }
	/**
	 * @param pageNo
	 *            The nPageNo to set.
	 */
	public void setPageNo(int pageNo) {
		pa.setPageNo(pageNo);
	}
	
	public void setRecordCount(int recordCount) {
		pa.setRecordCount(recordCount);
	}

	/**
	 * @return Returns the nPageCount.
	 */
	public String getPageNavigator() {
		StringBuffer buff = new StringBuffer();
		buff
				.append("\t\t  <table width=\"100%\" border=\"0\" align=\"center\">\n");
		buff.append("\t\t    <tr>\n");
		buff.append("\t\t      <td nowrap>共");
		buff.append(pa.getRecordCount());
		buff.append("条记录<input type=\"hidden\" name=\"recordCount\" value=\"");
		buff.append(pa.getRecordCount());
		buff.append("\">&nbsp;分");
		buff.append(pa.getPageCount());
		buff.append("页显示<input type=\"hidden\" name=\"pageCount\" value=\"");
		buff.append(pa.getPageCount());
		buff.append("\">&nbsp;\t\t当前第");
		buff.append(pa.getPageNo());
		buff.append("页<input type=\"hidden\" name=\"pageNo\" value=\"");
		buff.append(pa.getPageNo());
		buff.append("\"></td>\n");
		buff.append("\t\t      <td nowrap align=\"right\">\n");
		//buff.append("\t\t\t <ww:if test=\"pageNo > 1\">\n");
		// 第一页 前一页
		if (pa.getPageNo() > 1) {
			buff
					.append("\t\t\t\t <a href=\"javascript:toFirst()\" style=\"font-family:Webdings; font-size: 12pt\">9</a>\n");
			buff
					.append("\t\t        <a href=\"javascript:toPrior()\" style=\"font-family:Webdings; font-size: 12pt\">7</a>\n");
		} else {
			buff
					.append("\t\t\t\t <span style=\"font-family:Webdings; font-size: 12pt\">9</span>\n");
			buff
					.append("\t\t        <span style=\"font-family:Webdings; font-size: 12pt\">7</span>\n");
		}

		// 后一页 末页
		if (pa.getPageNo() < pa.getPageCount()) {
			buff
					.append("\t\t\t\t <a href=\"javascript:toNext()\" style=\"font-family:Webdings; font-size: 12pt\">8</a>\n");
			buff
					.append("\t\t        <a href=\"javascript:toLast()\" style=\"font-family:Webdings; font-size: 12pt\">:</a>\n");
		} else {
			buff
					.append("\t\t\t\t <span style=\"font-family:Webdings; font-size: 12pt\">8</span>\n");
			buff
					.append("\t\t        <span style=\"font-family:Webdings; font-size: 12pt\">:</span>\n");
		}
		
		buff.append("\t\t      </td>\n");
		buff.append("\t\t    </tr>\n");
		buff.append("\t\t  </table>\n");

		return buff.toString();
	}

	/**
	 * @return Returns the pa.
	 */
	public PageAttribute getPageAttribute() {
		return pa;
	}

	/**
	 * @return Returns the actionType.
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * @param actionType
	 *            The actionType to set.
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}