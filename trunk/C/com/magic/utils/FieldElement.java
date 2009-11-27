package com.magic.utils;
import java.util.HashMap;

public class FieldElement 
{
    private String name;
    private String caption;
    private int data_size=0;
    private String data_type;
    private boolean visible;
    private boolean detail;
    private String refLookup;
    private int refDocType=0;
    private String refIdName;
    private String input_type;
    private String description;
    
    public FieldElement(String name,String caption,int data_size,String data_type,int visible,int is_detail, String ref_lookup,int ref_doc_type,String ref_id_name,String input_type )
    {
        this.name=name;
        this.caption=caption;
        this.data_size=data_size;
        this.data_type=data_type;
        if(visible==0)
            this.visible=false;
        else
            this.visible=true;
        if(is_detail==0)
            this.detail=false;
        else
            this.detail=true;
        this.refLookup=ref_lookup;
        this.refDocType=ref_doc_type;
        this.refIdName=ref_id_name;
        this.input_type=input_type;    
    }

    public String getName()
    {
        return name;
    }

    public String getCaption()
    {
        return caption;
    }



    public int getDataSize()
    {
        return data_size;
    }



    public String getDataType()
    {
        return data_type;
    }


    public boolean getVisible()
    {
        return visible;
    }


    public boolean isRefDetail()
    {
        return detail;
    }

    public String getLookup()
    {
        return refLookup;
    }

    public int getDocType()
    {
        return refDocType;
    }

    public String getRefIdName()
    {
        return StringUtil.cEmpty(refIdName);
    }

    public String getInputType()
    {
        return input_type;
    }

 
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}