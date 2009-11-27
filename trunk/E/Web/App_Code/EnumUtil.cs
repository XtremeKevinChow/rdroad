using System;
using System.Collections;
using System.Collections.Generic;
using System.Reflection;
using System.Web.UI.WebControls;
using Magic.Framework.ORM;

using Magic.Framework.ORM.Query;

using Magic.Sys;
using Magic.Security;

using Magic.Framework.Utils;

/// <summary>
/// Enum类型的辅助类
/// </summary>
public sealed class EnumUtil
{
    static EnumUtil()
    {
      
    }

    private static bool _isInit = false;

    public static void InitializeEnumText()
    {
        if (!_isInit)
        {
            MultiLang.Clear();

            InitializeEnumText<Boolean>(true, "是");
            InitializeEnumText<Boolean>(false, "否");
            InitializeEnumText<OrgType>(OrgType.Own, "内部用户");
            InitializeEnumText<UserGroupType>(UserGroupType.Administrative, "系统管理员");
            InitializeEnumText<UserGroupType>(UserGroupType.System, "系统");
            InitializeEnumText<UserGroupType>(UserGroupType.Users, "用户");
            InitializeEnumText<UserStatus>(UserStatus.Disabled, "禁用");
            InitializeEnumText<UserStatus>(UserStatus.Enabled, "启用");
         
            InitializeEnumText<Magic.Sys.MessageAccessibility>(MessageAccessibility.Public, "公共");
            InitializeEnumText<MessageAccessibility>(MessageAccessibility.Subscriber, "仅订阅者");
            InitializeEnumText<MessageStatus>(MessageStatus.New, "新消息");
            InitializeEnumText<MessageStatus>(MessageStatus.Sent, "已发送");
            InitializeEnumText<MessageStatus>(MessageStatus.Elite, "置顶");
            InitializeEnumText<MessageStatus>(MessageStatus.Expired, "过期");
      
            InitializeEnumText<DayOfWeek>(DayOfWeek.Sunday, "星期天");
            InitializeEnumText<DayOfWeek>(DayOfWeek.Monday, "星期一");
            InitializeEnumText<DayOfWeek>(DayOfWeek.Tuesday, "星期二");
            InitializeEnumText<DayOfWeek>(DayOfWeek.Wednesday, "星期三");
            InitializeEnumText<DayOfWeek>(DayOfWeek.Thursday, "星期四");
            InitializeEnumText<DayOfWeek>(DayOfWeek.Friday, "星期五");
            InitializeEnumText<DayOfWeek>(DayOfWeek.Saturday, "星期六");
      
            InitializeEnumText<DictionaryItemType>(DictionaryItemType.Boolean, "布尔型");
            InitializeEnumText<DictionaryItemType>(DictionaryItemType.Numric, "数值型");
            InitializeEnumText<DictionaryItemType>(DictionaryItemType.String, "字符型");
            InitializeEnumText<DictionaryItemType>(DictionaryItemType.Group, "组/集合");      
        }
        _isInit = true;
    }
    public static void InitializeEnumText<enumT>(object value, string text)
    {
        MultiLang.AddText(value, text);
    }

    /// <summary>
    /// 获取EnumType的文本信息
    /// </summary>
    /// <param name="type"></param>
    /// <returns></returns>
    public static string GetEnumTypeText<enumT>(object value)
    {
        return MultiLang.GetText<enumT>(value);
    }

    
    /// <summary>
    /// 为ListControl绑定给定enum类型的所有值
    /// </summary>
    /// <typeparam name="enumT"></typeparam>
    /// <param name="list">ListControl控件,包括：DropdonwList,CheckBoxList,RadioButtonList,ListBox等</param>
    /// <param name="useIntAsValue">使用Int类型作为枚举类型的值，在查询页面中使用</param>
    public static void BindEnumData2ListControl<enumT>(ListControl list, bool useIntAsValue)
    {
        Type type = typeof(enumT);
        Array arry = Enum.GetValues(type);
        if (arry != null && arry.Length > 0)
        {
            foreach (enumT obj in arry)
            {
                ListItem item = new ListItem();
                if (useIntAsValue) //强制将枚举转换成Int类型使用，
                    item.Value = Convert.ToInt32(obj).ToString();
                else
                    item.Value = obj.ToString();
                item.Text = GetEnumTypeText<enumT>(obj);
                list.Items.Add(item);
            }
        }
    }
    /// <summary>
    /// 绑定指定Enum类型的指定值到控件，
    /// </summary>
    /// <typeparam name="enumT"></typeparam>
    /// <param name="list"></param>
    /// <param name="useIntAsValue"></param>
    /// <param name="arry"></param>
    public static void BindEnumData2ListControl<enumT>(ListControl list, bool useIntAsValue, params enumT[] values)
    {
        if (values != null && values.Length > 0)
        {
            foreach (enumT obj in values)
            {
                ListItem item = new ListItem();
                if (useIntAsValue) //强制将枚举转换成Int类型使用，
                    item.Value = Convert.ToInt32(obj).ToString();
                else
                    item.Value = obj.ToString();
                item.Text = GetEnumTypeText<enumT>(obj);
                list.Items.Add(item);
            }
        }
    }

    public static void BindBoolean2ListControl(ListControl list, string trueText, string falseText, bool useIntAsValue)
    {
        ListItem item = new ListItem();
        item.Text = trueText;
        if (useIntAsValue)
            item.Value = "1";
        else
            item.Value = true.ToString(); ;
        list.Items.Add(item);

        item = new ListItem();
        item.Text = falseText;
        if (useIntAsValue)
            item.Value = "0";
        else
            item.Value = false.ToString();
        list.Items.Add(item);

    }

    public static void BindEntity2ListControl<entityT>(ListControl list, string dataTextProperty, string dataValueProperty)
    {
        using (ISession session = new Session())
        {
            BindEntity2ListControl<entityT>(session, list, dataTextProperty, dataValueProperty);
        }
    }

    public static void BindEntity2ListControl<entityT>(ISession session, ListControl listCtrl, string dataTextProperty, string dataValueProperty)
    {
        BindEntity2ListControl<entityT>(session, listCtrl, dataTextProperty, dataValueProperty, null);
    }

    public static void BindEntity2ListControl<entityT>(ISession session, ListControl listCtrl, string dataTextProperty, string dataValueProperty, params Magic.Framework.ORM.Query.AbstractExpression[] filterExps)
    {
       
        EntityQuery qry =  session.CreateEntityQuery<entityT>();
        if (filterExps != null && filterExps.Length > 0)
            foreach (AbstractExpression exp in filterExps)
                qry.And(exp);
        IList<entityT> list =qry.List<entityT>();
        BindEntity2ListControl<entityT>(listCtrl, dataTextProperty,dataValueProperty, list);
    }

    public static void BindEntity2ListControl<entityT>(ListControl listCtrl, string dataTextProperty, string dataValueProperty, IList<entityT> entityList)
    {
        Type type = typeof(entityT);
        PropertyInfo textP = type.GetProperty(dataTextProperty);
        PropertyInfo valueP = type.GetProperty(dataValueProperty);
        if (textP == null || valueP == null)
        {
            throw new Exception("绑定的属性字段不存在");
        }
        string value, text;
        ListItem item = null;
        if (entityList != null && entityList.Count > 0)
        {
            foreach (entityT entity in entityList)
            {
                if (entity != null)
                {
                    item = new ListItem();
                    item.Text = Convert.ToString(textP.GetValue(entity, null));
                    item.Value = Convert.ToString(valueP.GetValue(entity, null));
                    listCtrl.Items.Add(item);
                }
            }
        }
        //listCtrl.DataTextField = dataTextProperty;
        //listCtrl.DataValueField = dataValueProperty;
        //listCtrl.DataSource = entityList;
        //listCtrl.DataBind();
    }

    public static void BindEntity2ListControl<entityT>(ListControl listCtrl, string dataTextProperty, string dataValueProperty, params entityT[] entityArray)
    {
        IList<entityT> list = new List<entityT>(entityArray);
        
        BindEntity2ListControl<entityT>(listCtrl, dataTextProperty, dataValueProperty, list);
    }

    /// <summary>
    /// 绑定词汇表项
    /// </summary>
    /// <param name="listCtrl"></param>
    /// <param name="groupCode"></param>
    public static void BindDictionaryItems2ListControl(ListControl listCtrl, string groupCode)
    {
        using (ISession session = new Session())
        {
            BindDictionaryItems2ListControl(session, listCtrl, groupCode);
        }
    }

    /// <summary>
    /// 绑定词汇表项
    /// </summary>
    /// <param name="session"></param>
    /// <param name="listCtrl"></param>
    /// <param name="groupCode"></param>
    public static void BindDictionaryItems2ListControl(ISession session, ListControl listCtrl, string groupCode)
    {
        IList<DictionaryItem> list = DictionaryItem.GetDictionaryItemsByGroup(session, groupCode);
        if (list != null && list.Count > 0)
        {
            foreach (DictionaryItem itm in list)
            {
                ListItem item = new ListItem();
                item.Text = itm.Name;                
                item.Value = itm.ValueToString;
                listCtrl.Items.Add(item);
            }
        }
    }
}
