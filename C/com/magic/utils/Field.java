package com.magic.utils;
/**
 * �ĵ�Field Bean  (��Ӧ��S_DOC_FIELD)
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class Field {
    private String name;                  //����
    private String caption;               //����
    private int data_size=0;              //�ֶδ�С
    private String data_type;             //��������
    private boolean visible;             //�Ƿ�ɼ�
    private boolean detail;              //�Ƿ�ɲ鿴��ϸ
    private String refLookup;             //��ؼ���
    private int refDocType=0;             //����ĵ�����
    private String refIdName;             //����ֶ�ID
    private String input_type;            //��������
    private boolean is_required;         //�Ƿ��Ҫ�ֶ�
    private String description;           //����
    private boolean is_new;              //�Ƿ�����
    private boolean is_update;           //�Ƿ��޸�
    private boolean is_detail;           //�Ƿ���ϸ
    private boolean is_query;            //�Ƿ��ѯ
    private boolean is_list;             //�Ƿ��г�
    private boolean is_quick_query;      //�Ƿ���ٲ�ѯ
    private String defaultValue;          //ȱʡֵ
    
   /**
    * ����Field����
    * @param name          ����
    * @param caption       ����
    * @param data_size     �ֶδ�С
    * @param data_type     �ֶ�����
    * @param visible       �Ƿ�ɼ�
    * @param is_detail     �Ƿ���ϸ
    * @param ref_lookup    ��ز�ѯ
    * @param ref_doc_type  ��������ĵ�����
    * @param ref_id_name   �������ID
    * @param input_type    �ֶ���������
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