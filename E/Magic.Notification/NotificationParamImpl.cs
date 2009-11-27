namespace Magic.Notification
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.Utils;

    public partial class NotificationParam
    {
        #region 用于构造NotificationParam的属性、方法
        private int _childSerial = 1;
        private int _childIndex = 1;
        private ISession _session;
        private NotificationParam _parent;

        private void IsListParam()
        {
            if (this.DataType != NotificationDataType.ListOfNotificationParam)
                throw new Exception("this method is only validate for hierarchical NotificationParam");
        }
        /// <summary>
        /// 创建集合消息参数对象
        /// </summary>
        /// <param name="parent">父级参数对象，如果只有一级关系使用0</param>
        /// <param name="serialIndex">参数系列（用来标记第几个订单行这样的子对象参数集合）</param>
        /// <param name="subIndex">参数索引（用来标记订单行中的每一个参数索引）</param>
        /// <param name="name">参数名称</param>
        internal NotificationParam(ISession session, int notifyId, NotificationParam parent, int serialIndex, int subIndex, string name)
        {
            this._session = session;
            this._parent = parent;
            this.NotifyID = notifyId;
            this.ParentID = parent == null ? 0 : parent.ParamID;
            this.SerialIndex = serialIndex;
            this.SubIndex = subIndex;
            this.ParamName = name;
            this.DataType = NotificationDataType.ListOfNotificationParam;
            this.ParamValue = null;
        }
        /// <summary>
        /// 创建简单消息参数对象
        /// </summary>
        /// <param name="parent">父级参数对象，如果只有一级关系使用0</param>
        /// <param name="serialIndex">参数系列（用来标记第几个订单行这样的子对象参数集合）</param>
        /// <param name="subIndex">参数索引（用来标记订单行中的每一个参数索引）</param>
        /// <param name="name">参数名称</param>
        /// <param name="value">参数值，目前只支持int, string, float, double, decimal, DateTime这几种基本数据类型</param>
        internal NotificationParam(int notifyId, NotificationParam parent, int serialIndex, int subIndex, string name, object value)
        {
            this._parent = parent;
            this.NotifyID = notifyId;
            this.ParentID = parent == null ? 0 : parent.ParamID;
            this.SerialIndex = serialIndex;
            this.SubIndex = subIndex;
            this.ParamName = name;

            if (value == null)
            {
                this.DataType = NotificationDataType.String;
                this.ParamValue = null;
            }
            else if (value is IList<NotificationParam> || value is NotificationParam[])
            {
                //允许参数为NotificationParam集合，即父子层级关系
                //TODO: 暂不支持直接传入NotificationParam集合创建NotificationParam对象
            }
            else if (value is NotificationParam)
            {
                this.DataType = (value as NotificationParam).DataType;
                this.ParamValue = (value as NotificationParam).ParamValue;
            }
            else
            {
                //允许的基本数据类型为int, decimal, float, double, string, datetime
                switch (value.GetType().Name)
                {
                    case "Int32":
                    case "Int16":
                        this.DataType = NotificationDataType.Int;
                        this.ParamValue = Cast.Int(value, 0).ToString();
                        break;

                    case "Single":
                    case "Double":
                    case "Decimal":
                        this.DataType = NotificationDataType.Decimal;
                        this.ParamValue = Cast.Decimal(value, 0M).ToString("#0.##");
                        break;

                    case "DateTime":
                        this.DataType = NotificationDataType.DateTime;
                        this.ParamValue = Cast.DateTime(value, new DateTime(1900, 1, 1)).ToString("yyyy-MM-dd HH:mm").TrimEnd(":00".ToCharArray()).Trim();
                        break;

                    default:
                        this.DataType = NotificationDataType.String;
                        this.ParamValue = value.ToString().Trim();
                        break;
                }
            }
        }
        /// <summary>
        /// <para>仅对父子层级结构的参数有效，该方法开始一个新的明细对象（由一组NotificationParam组成）</para>
        /// <para>后续AddParam方法添加的参数归属于新的明细对象</para>
        /// </summary>
        /// <returns>返回参数对象本身</returns>
        public NotificationParam NewSerial()
        {
            this.IsListParam();
            this._childSerial++;
            this._childIndex = 1;
            return this;
        }
        /// <summary>
        /// 添加简单参数
        /// </summary>
        /// <param name="name">参数名称</param>
        /// <param name="value">参数值，可以是int, string, float, double, decimal, DateTime类型</param>
        /// <returns>集合消息参数对象本身</returns>
        public NotificationParam AddSingleParam(string name, object value)
        {
            this.IsListParam();
            NotificationParam param = new NotificationParam(this.NotifyID, this, this._childSerial, this._childIndex++, name, value);
            param.Create(this._session);
            return this;
        }
        /// <summary>
        /// 添加集合参数
        /// </summary>
        /// <param name="name">集合参数名</param>
        /// <returns>新添加的集合参数对象</returns>
        public NotificationParam AddListParam(string name)
        {
            this.IsListParam();
            NotificationParam param = new NotificationParam(this._session, this.NotifyID, this, this._childSerial, this._childIndex++, name);
            param.Create(this._session);
            return param;
        }
        public NotificationParam Parent
        {
            get
            {
                return this._parent == null ? this : this._parent;
            }
        }
        #endregion
    }
}