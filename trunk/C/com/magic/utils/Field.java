package com.magic.utils;
/**
 * 文档Field Bean  (对应表S_DOC_FIELD)
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class Field {
    private String name;                  //名称
    private String caption;               //描述
    private int data_size=0;              //字段大小
    private String data_type;             //数据类型
    private boolean visible;             //是否可见
    private boolean detail;              //是否可查看明细
    private String refLookup;             //相关检索
    private int refDocType=0;             //相关文档类型
    private String refIdName;             //相关字段ID
    private String input_type;            //输入类型
    private boolean is_required;         //是否必要字段
    private String description;           //描述
    private boolean is_new;              //是否新增
    private boolean is_update;           //是否修改
    private boolean is_detail;           //是否明细
    private boolean is_query;            //是否查询
    private boolean is_list;             //是否列出
    private boolean is_quick_query;      //是否快速查询
    private String defaultValue;          //缺省值
    
   /**
    * 创建Field对象
    * @param name          名称
    * @param caption       描述
    * @param data_size     字段大小
    * @param data_type     字段类型
    * @param visible       是否可见
    * @param is_detail     是否明细
    * @param ref_lookup    相关查询
    * @param ref_doc_type  相关联的文档类型
    * @param ref_id_name   相关联的ID
    * @param input_type    字段输入类型
    */
    
    public Field(String name,String caption,int data_size,String data_type,int visible,int is_detail, String ref_lookup,int ref_doc_type,String ref_id_name,String input_type ){
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

    public boolean isRequired()
    {
        return is_required;
    }

    public void setRequired(boolean is_required)
    {
        this.is_required = is_required;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isNew()
    {
        return is_new;
    }

    public void setNew(boolean is_new)
    {
        this.is_new = is_new;
    }

    public boolean isUpdate()
    {
        return is_update;
    }

    public void setUpdate(boolean is_update)
    {
        this.is_update = is_update;
    }

    public boolean isDetail()
    {
        return is_detail;
    }

    public void setDetail(boolean is_detail)
    {
        this.is_detail = is_detail;
    }

    public boolean isQuery()
    {
        return is_query;
    }

    public void setQuery(boolean is_query)
    {
        this.is_query = is_query;
    }

    public boolean isList()
    {
        return is_list;
    }

    public void setList(boolean is_list)
    {
        this.is_list = is_list;
    }

  public boolean isQuickQuery()
  {
    return is_quick_query;
  }

  public void setQuickQuery(boolean is_quick_query)
  {
    this.is_quick_query = is_quick_query;
  }

  public String getDefaultValue()
  {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue)
  {
    this.defaultValue = defaultValue;
  }




}