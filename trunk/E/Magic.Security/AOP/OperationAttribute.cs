using System;
using System.Collections.Generic;
using System.Text;


namespace Magic.Security
{
    /// <summary>
    /// 指定权限操作的属性，用于修饰类、方法、属性等
    /// </summary>
    [AttributeUsage(AttributeTargets.All,AllowMultiple=false,Inherited=true)]
    public sealed class OperationAttribute : Attribute
    {
         private string _name,_description;
        private OperationType _opType = OperationType.Feature;
        /// <summary>
        /// 名称
        /// </summary>
        public string Name
        {
            get { return _name; }
            set { _name = value; }
        }

        /// <summary>
        /// 描述
        /// </summary>
        public string Description
        {
          get { return _description; }
          set { _description = value; }
        }

        /// <summary>
        /// 操作类型
        /// </summary>
        public OperationType OperationType
        {
            get { return _opType; }
            set { _opType = value; }
        }
        
        /// <summary>
        /// 构造一个属性
        /// </summary>
        /// <param name="name"></param>
        /// <param name="description"></param>
        public OperationAttribute(string name,string description)
            :this(name,description,OperationType.Entity)
        {
        }

        /// <summary>
        /// 构造一个属性
        /// </summary>
        /// <param name="name"></param>
        /// <param name="description"></param>
        /// <param name="opType"></param>
        public OperationAttribute(string name, string description, OperationType opType)
        {
            _name = name;
            _description = description;
            _opType = opType;
        }

        /// <summary>
        /// 默认的
        /// </summary>
        public OperationAttribute()
            :this("","")
        {
        }
    }
}
