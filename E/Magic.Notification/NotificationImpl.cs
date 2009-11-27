namespace Magic.Notification
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Antlr.StringTemplate;

    /// <summary>
    /// <para>���쵥���㼶����ֵ��Notification����ʾ������Աע�����֪ͨ��:</para>
    /// <example>
    ///    Notification.Create(session, member.MemberID, 3)
    ///        .AddSingleParam("Name", member.Name)
    ///        .AddSingleParam("Title", member.Gender == PersonGender.Male ? "����" : "Ůʿ")
    ///        .AddSingleParam("RegisterTime", member.CreateTime)
    ///        .AddReceiver(member.MemberID, member.Name, member.Mobile);
    /// </example>
    /// <para>������и��Ӳ㼶��ϵ����ֵ��Notification����ʾ�������������ʼ�֪ͨ��:</para>
    /// <example>
    ///    Notification.Create(session, order.OrderNumber, 5)
    ///        .AddSingleParam("MemberName", member.Name)
    ///        .AddSingleParam("OrderNumber", order.OrderNumber)
    ///        .AddSingleParam("TotalAmt", order.TotalAmt)
    ///        .AddSingleParam("PayableAmt", order.PayableAmt)
    ///        .AddSingleParam("Address", order.Address)
    ///        .AddSingleParam("Contact", order.Contact)
    ///        .AddListParam("Lines")
    ///            .AddSingleParam("Product", "˿·����˿������Ĩ��")
    ///            .AddSingleParam("Qty", 1)
    ///            .AddSingleParam("Price", 139)
    ///        .NewSerial()
    ///            .AddSingleParam("Product", "�λ�֮������ħ������")
    ///            .AddSingleParam("Qty", 2)
    ///            .AddSingleParam("Price", 129)
    ///        .AddReceiver(member.MemberID, member.Name, member.EMail);
    /// </example>
    /// </summary>
    /// <remarks>
    /// <para>���Ͳ���:</para>
    /// <para>1. call ToSendList()</para>
    /// <para>2. for each Notification object in IList that returned by ToSendList(), call ReadyToSend()</para>
    /// <para>3. use properties Title, Content, Receivers, of Notification to send the message</para>
    /// </remarks>
    public partial class Notification
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(Notification));

        private int _subIndex = 1;
        private ISession _session;
        private static bool _categoryLoaded = false;
        private static object _lock = new object();
        private static IDictionary<int, NotificationCategory> _categories = new Dictionary<int, NotificationCategory>();
        public static NotificationCategory GetCategory(ISession session, int catId)
        {
            if (!_categoryLoaded)
            {
                lock (_lock)
                {
                    _categories.Clear();
                    IList<NotificationCategory> categories = session.CreateEntityQuery<NotificationCategory>().List<NotificationCategory>();
                    foreach (NotificationCategory cat in categories)
                        _categories.Add(cat.CatID, cat);
                }
            }
            NotificationCategory result = null;
            if (_categories.TryGetValue(catId, out result))
                return result;
            return null;
        }

        #region ����Notification����ķ���
        /// <summary>
        /// ������Ϣ֪ͨ����
        /// </summary>
        /// <param name="session"></param>
        /// <param name="triggerId">������Ϣ֪ͨ�Ķ����ʶ�������Աע��ʹ�û�ԱID������ȷ��ʹ�ö����Ż򶩵�ID��</param>
        /// <param name="categoryId">��Ϣ֪ͨ����ID���ο���BC_NOTIFY_CAT�е�����</param>
        /// <returns>��������Ϣ֪ͨ����</returns>
        public static Notification Create(ISession session, string triggerId, int categoryId)
        {
            Notification notification = new Notification();
            notification.CatID = categoryId;
            notification.TriggerID = triggerId;
            notification.CreateTime = DateTime.Now;
            notification.FinishTime = new DateTime(1900, 1, 1);
            notification.RefCount = 0;
            notification.Status = NotificationStatus.New;
            notification.ResultStatus = NotificationResultStatus.New;

            notification._session = session;
            NotificationCategory category = Notification.GetCategory(session, categoryId);
            if (category == null || !category.IsEnabled) return null;

            notification.Create(session);

            return notification;
        }
        /// <summary>
        /// ��Ӽ򵥲���
        /// </summary>
        /// <param name="name">��������</param>
        /// <param name="value">����ֵ��������int, string, float, double, decimal, DateTime����</param>
        /// <returns>��Ϣ֪ͨ������</returns>
        public Notification AddSingleParam(string name, object value)
        {
            NotificationParam param = new NotificationParam(this.NotifyID, null, 1, this._subIndex++, name, value);
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
            NotificationParam param = new NotificationParam(this._session, this.NotifyID, null, 1, this._subIndex++, name);
            param.Create(this._session);
            return param;
        }
        /// <summary>
        /// ��ӽ�����
        /// </summary>
        /// <param name="userId">�����߱�ʶ�������û�ID����ԱID��</param>
        /// <param name="userName">����������</param>
        /// <param name="postTo">�ʼ���Ϣ������ָ�������������ַ��������Ϣ������ָ���������ֻ�����</param>
        /// <returns></returns>
        public Notification AddReceiver(string userId, string userName, string postTo)
        {
            NotificationReceiver receiver = new NotificationReceiver(this.NotifyID, userId, userName, postTo);
            receiver.Create(this._session);
            return this;
        }
        #endregion

        #region ����Notification�ķ���
        private string _title;
        private string _content;
        private IList<NotificationReceiver> _receiver;
        private IDictionary<string, object> _param;
        /// <summary>
        /// ��������Ϣ�ı��⣬�����ʼ�����
        /// </summary>
        public string Title
        {
            get { return this._title; }
        }
        /// <summary>
        /// ��������Ϣ�����ݣ�������š��ʼ�����
        /// </summary>
        public string Content
        {
            get { return this._content; }
        }
        /// <summary>
        /// �������б�
        /// </summary>
        public IList<NotificationReceiver> Receivers
        {
            get { return this._receiver; }
        }
        /// <summary>
        /// �������б�������Mail, SMS����������
        /// </summary>
        /// <param name="session"></param>
        /// <param name="startingDate">��ʼʱ��</param>
        /// <returns></returns>
        public static IList<Notification> ToSendList(ISession session, DateTime startingDate)
        {
            IList<Notification> notifications = session.CreateObjectQuery(@"
select 1
from Notification n
inner join NotificationCategory cat on n.CatID=cat.CatID
where cat.Type in(?mail, ?sms) and n.CreateTime>=?startingDate and n.Status in(?new, ?open)
")
                .Attach(typeof(Notification)).Attach(typeof(NotificationCategory))
                .SetValue("?mail", NotificationType.Mail, "cat.Type")
                .SetValue("?sms", NotificationType.SMS, "cat.Type")
                .SetValue("?startingDate", startingDate, "n.CreateTime")
                .SetValue("?new", NotificationStatus.New, "n.Status")
                .SetValue("?open", NotificationStatus.Open, "n.Status")
                .List<Notification>();
            foreach (Notification n in notifications)
                n._session = session;
            return notifications;
        }
        /// <summary>
        /// <para>����֮ǰ������ø÷��������췢�ͱ��⡢���ݣ���ȡ�������б�Ȳ���</para>
        /// <para>ʹ��Title, Content, Receivers���ԾͿ��Ի�÷�����Ҫ��������Ϣ</para>
        /// </summary>
        /// <param name="session"></param>
        /// <returns>����������</returns>
        public int ReadyToSend()
        {
            this._title = "";
            this._content = "";

            //������
            IList<NotificationSubscriber> subscribers = NotificationSubscriber.GetList(this._session, this.CatID);
            foreach (NotificationSubscriber subscriber in subscribers)
            {
                NotificationReceiver receiver = NotificationReceiver.Retrieve(this._session, this.NotifyID, subscriber.UserID);
                if (receiver == null)
                {
                    //��ǰ�������Ƿ��Ѿ���ӵ��������б����ˣ�
                    receiver = new NotificationReceiver(this.NotifyID, subscriber.UserID, subscriber.UserName, subscriber.PostCode);
                    receiver.Create(this._session);
                }
            }
            //��ȡ�������б�
            this._receiver = NotificationReceiver.ToSendList(this._session, this.NotifyID);
            if (this._receiver.Count <= 0) return 0;

            //����
            this._param = new Dictionary<string, object>();
            IList<NotificationParam> paramList = this._session.CreateEntityQuery<NotificationParam>()
                .Where(Exp.Eq("NotifyID", this.NotifyID) & Exp.Eq("ParentID", 0))
                .List<NotificationParam>();
            foreach (NotificationParam p in paramList)
                this._param.Add(p.ParamName, this.BuildParam(p));

            NotificationCategory category = Notification.GetCategory(this._session, this.CatID);
            //��Ϣ���⣨�����ʼ���Ϣ��
            if (category.Type == NotificationType.Mail && !string.IsNullOrEmpty(category.TitleTemplate) && category.TitleTemplate.Trim().Length > 0)
            {
                StringTemplate st = new StringTemplate(category.TitleTemplate);
                SetAttribute(st, this._param);
                this._title = st.ToString();
            }

            //��Ϣ����
            if (!System.IO.File.Exists(category.TemplateFile))
            {
                log.ErrorFormat("category {0}: template file {1} not exists", category.CatID, category.TemplateFile);
                return this._receiver.Count;
            }
            string templateContent = null;
            using (System.IO.StreamReader reader = new System.IO.StreamReader(category.TemplateFile, System.Text.Encoding.UTF8))
            {
                templateContent = reader.ReadToEnd();
            }
            if (string.IsNullOrEmpty(templateContent) || templateContent.Length <= 0)
                return this._receiver.Count;
            StringTemplate stContent = new StringTemplate(templateContent);
            SetAttribute(stContent, this._param);
            this._content = stContent.ToString();

            return this._receiver.Count;
        }
        private object BuildParam(NotificationParam param)
        {
            if (param.DataType != NotificationDataType.ListOfNotificationParam)
            {
                log.DebugFormat("build single param, name: {0}, value: {1} ", param.ParamName, param.ParamValue);
                return param.ParamValue;
            }
            log.DebugFormat("build list param, name: {0}", param.ParamName, param.ParamValue);
            IList<IDictionary<string, object>> result = new List<IDictionary<string, object>>();
            System.Data.DataSet ds = this._session.CreateObjectQuery(@"select distinct SerialIndex from NotificationParam where ParentID=?pid order by SerialIndex")
                .Attach(typeof(NotificationParam))
                .SetValue("?pid", param.ParamID, "ParentID")
                .DataSet();
            foreach (System.Data.DataRow row in ds.Tables[0].Rows)
            {
                IDictionary<string, object> paramList = new Dictionary<string, object>();
                IList<NotificationParam> notifyParams = this._session.CreateEntityQuery<NotificationParam>()
                    .Where(Exp.Eq("ParentID", param.ParamID) & Exp.Eq("SerialIndex", row[0]))
                    .OrderBy("SubIndex")
                    .List<NotificationParam>();
                log.DebugFormat("serial: {0}, param count: {1}", row[0], notifyParams.Count);
                foreach (NotificationParam p in notifyParams)
                    paramList.Add(p.ParamName, BuildParam(p));
                result.Add(paramList);
            }

            return result;
        }
        private static void SetAttribute(StringTemplate st, IDictionary<string, object> p)
        {
            if (p == null) return;
            foreach (KeyValuePair<string, object> kv in p)
                st.SetAttribute(kv.Key, kv.Value);
        }
        /// <summary>
        /// ����Ƿ����㷢������
        /// </summary>
        /// <param name="receiver">������</param>
        /// <returns></returns>
        public bool CheckForSend(NotificationReceiver receiver)
        {
            if (receiver == null) return false;
            if (string.IsNullOrEmpty(this._content))
            {
                log.ErrorFormat("content is null, notification {0}, receiver {1} not sent", this.NotifyID, receiver.ReceiverID);
                return false;
            }
            if (string.IsNullOrEmpty(receiver.PostTo) || receiver.PostTo.Trim().Length <= 0)
            {
                log.ErrorFormat("receiver's PostTo is null or empty, notification {0}, receiver {1} not sent", this.NotifyID, receiver.ReceiverID);
                return false;
            }
            return true;
        }
        #endregion

        private static NotificationCodeDef GetCodeDef(ISession session, NotificationType type, string code)
        {
            IList<NotificationCodeDef> defs = session.CreateEntityQuery<NotificationCodeDef>()
                .Where(Exp.Eq("Type", type) & Exp.Eq("Code", code))
                .Or(Exp.Eq("Type", type) & Exp.Eq("Code", "-99999")) //-90000��-99999��״̬��Ϊϵͳ����
                .List<NotificationCodeDef>();
            return defs[0];
        }
        /// <summary>
        /// ����Ϣ֪ͨ��ĳ�������߷������ʱ�Ĵ���
        /// </summary>
        /// <param name="receiver">������</param>
        /// <param name="code">���ͽ��״̬��</param>
        /// <param name="text">�������ʱ�����쳣�����ı������쳣��Ϣ</param>
        /// <returns>������ͽ���������ͳɹ�������true�����򷵻�false�����ŵķ����Ƿ�ɹ��������ӳ�һ��ʱ���ѯ����ܻ�֪��</returns>
        public bool AfterSendDo(NotificationReceiver receiver, string code, string text)
        {
            //��ѯ���ͽ��״̬�����
            NotificationCodeDef codeDef = GetCodeDef(this._session, GetCategory(this._session, this.CatID).Type, code);

            //��¼һ��������־
            NotificationLog ntflog = new NotificationLog();
            ntflog.NotifyID = this.NotifyID;
            ntflog.ReceiverID = receiver.ReceiverID;
            ntflog.ResultCode = code;
            if (codeDef != null && codeDef.IsSuccess) ntflog.ErrorText = text;
            else ntflog.ErrorText = codeDef == null ? text : codeDef.Text + text;
            ntflog.LogTime = DateTime.Now;
            ntflog.Create(this._session);

            //������״̬����
            //TODO: �ȴ���ѯ���ŷ���״̬֮�������������
            if (codeDef!=null && codeDef.IsSuccess) receiver.ResultStatus = NotificationResultStatus.AllSuccess;
            else receiver.ResultStatus = NotificationResultStatus.AllError;
            receiver.FinishTime = DateTime.Now;
            receiver.Update(this._session, "ResultStatus", "FinishTime");

            return codeDef != null && codeDef.IsSuccess;
        }
        /// <summary>
        /// ��Ϣ֪ͨ�����н����߶�������Ϻ�Ĵ���
        /// </summary>
        public void AfterSendDo()
        {
            //TODO: �ȴ��ӷ�������ѯ״̬֮���ٸ��³������״̬
            IList<NotificationReceiver> receivers = this._session.CreateEntityQuery<NotificationReceiver>()
                .Where(Exp.Eq("NotifyID", this.NotifyID))
                .List<NotificationReceiver>();
            int successCount = 0, errorCount = 0;
            foreach (NotificationReceiver receiver in receivers)
            {
                if (receiver.ResultStatus == NotificationResultStatus.AllError) successCount++;
                else if (receiver.ResultStatus == NotificationResultStatus.AllSuccess) errorCount++;
            }
            if (successCount == receivers.Count) this.ResultStatus = NotificationResultStatus.AllSuccess;
            else if (errorCount == receivers.Count) this.ResultStatus = NotificationResultStatus.AllSuccess;
            else this.ResultStatus = NotificationResultStatus.PartError;
            this.Status = NotificationStatus.Close;
            this.FinishTime = DateTime.Now;
            this.Update(this._session, "ResultStatus", "Status", "FinishTime");
        }
    }
}