/*
 * Created on 2006-9-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.common.pager;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CompSQL {
    
    private static final String SPLITER = "${sql}";
    
    private static String pageSql = "SELECT * FROM (SELECT A.*, rownum r FROM ( ${sql} ) A WHERE rownum <= ?) B WHERE r > ?";
    
    public static String getNewSql(String innerSql) {
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(pageSql);
        String sql = matcher.replaceAll(innerSql);
        return sql;
    }
}
