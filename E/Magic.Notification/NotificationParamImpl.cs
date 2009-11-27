namespace Magic.Notification
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.Utils;

    public partial class NotificationParam
    {
        #region ���ڹ���NotificationParam�����ԡ�����
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
        /// ����������Ϣ��������
        /// </summary>
        /// <param name="parent">���������������ֻ��һ����ϵʹ��0</param>
        /// <param name="serialIndex">����ϵ�У�������ǵڼ����������������Ӷ���������ϣ�</param>
        /// <param name="subIndex">����������������Ƕ������е�ÿһ������������</param>
        /// <param name="name">��������</param>
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
        /// ��������Ϣ��������
        /// </summary>
        /// <param name="parent">���������������ֻ��һ����ϵʹ��0</param>
        /// <param name="serialIndex">����ϵ�У�������ǵڼ����������������Ӷ���������ϣ�</param>
        /// <param name="subIndex">����������������Ƕ������е�ÿһ������������</param>
        /// <param name="name">��������</param>
        /// <param name="value">����ֵ��Ŀǰֻ֧��int, string, float, double, decimal, DateTime�⼸�ֻ�����������</param>
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
                //�������ΪNotificationParam���ϣ������Ӳ㼶��ϵ
                //TODO: �ݲ�֧��ֱ�Ӵ���NotificationParam���ϴ���NotificationParam����
            }
            else if (value is NotificationParam)
            {
                this.DataType = (value as NotificationParam).DataType;
                this.ParamValue = (value as NotificationParam).ParamValue;
            }
            else
            {
                //����Ļ�����������Ϊint, decimal, float, double, string, datetime
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
        /// <para>���Ը��Ӳ㼶�ṹ�Ĳ�����Ч���÷�����ʼһ���µ���ϸ������һ��NotificationParam��ɣ�</para>
        /// <para>����AddParam������ӵĲ����������µ���ϸ����</para>
        /// </summary>
        /// <returns>���ز���������</returns>
        public NotificationParam NewSerial()
        {
            this.IsListParam();
            this._childSerial++;
            this._childIndex = 1;
            return this;
        }
        /// <summary>
        /// ��Ӽ򵥲���
        /// </summary>
        /// <param name="name">��������</param>
        /// <param name="value">����ֵ��������int, string, float, double, decimal, DateTime����</param>
        /// <returns>������Ϣ����������</returns>
        public NotificationParam AddSingleParam(string name, object value)
        {
            this.IsListParam();
            NotificationParam param = new NotificationParam(this.NotifyID, this, this._childSerial, this._childIndex++, name, value);
            param.Create(this._session);
            return this;
        }
        /// <summary>
        /// ��Ӽ��ϲ���
        /// </summary>
        /// <param name="name">���ϲ�����</param>
        /// <returns>����ӵļ��ϲ�������</returns>
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