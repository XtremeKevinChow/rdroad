package com.magic.utils;
import java.util.*;
import java.sql.*;
/**
 *  检索文档信息
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class Lookup 
{
    String name;
    String valueField;
    String displayField;
    String keyField;
    boolean companyConstraint;
    String dataSource;

    public Lookup(String name,String data_source,String value_field,String display_field, String key_field,int is_company_constraint)
    {
        this.name=name;
        this.dataSource=data_source;
        this.valueField=value_field;
        this.displayField=display_field;
        this.keyField=key_field;
        if(is_company_constraint==0)
            this.companyConstraint=false;
        else
            this.companyConstraint=true;
    }

    public String getName()
    {
        return name;
    }

    public String getValueField()
    {
        return valueField;
    }

    public String getDisplayField()
    {
        return displayField;
    }

    public String getKeyField()
    {
        return keyField;
    }

    public boolean isCompanyConstraint()
    {
        return companyConstraint;
    }

    public String getDataSource()
    {
        return dataSource;
    }
    
    
}