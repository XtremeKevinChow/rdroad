package com.magic.crm.filter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import com.magic.crm.user.entity.FieldView;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FieldViewFilter {
	
	/**
	 * 
	 * @param coll: all data from db
	 * @param fieldViewMap: fields which to filtrate
	 * @param clazz: exp:com.magic.stock.inbound.StockForm
	 * @throws Exception
	 */
	public static void filter(Collection coll, Map fieldViewMap, Class clazz) throws Exception {
	    
	    if (coll == null) {
	        return;
	    }
	    if (fieldViewMap.get(clazz.getName())== null) {
	    	return;
	    }
	    if (fieldViewMap.get(clazz.getName()) instanceof List) {
	        List fieldList = (java.util.ArrayList)fieldViewMap.get(clazz.getName());
			Iterator iter = coll.iterator();

			while(iter.hasNext()) {
			    Object obj = (Object)iter.next();
				for (int i = 0 ; fieldList != null && i < fieldList.size(); i ++) {		    
				    FieldView field = (FieldView)fieldList.get(i);
				    BeanUtils.setProperty(obj, field.getFieldName(), "<font color=red>***</font>");//利用反射重新设置bean的值
				}
			}
	    } else {
	        throw new Exception("the fieldViewMap is not List Type");
	    }

	}
}
