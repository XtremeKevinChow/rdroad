namespace Magic.Notification
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Antlr.StringTemplate;

    /// <summary>
    /// <para>构造单个层级参数值的Notification对象示例（会员注册短信通知）:</para>
    /// <example>
    ///    Notification.Create(session, member.MemberID, 3)
    ///        .AddSingleParam("Name", member.Name)
    ///        .AddSingleParam("Title", member.Gender == PersonGender.Male ? "先生" : "女士")
    ///        .AddSingleParam("RegisterTime", member.CreateTime)
    ///        .AddReceiver(member.MemberID, member.Name, member.Mobile);
    /// </example>
    /// <para>构造具有父子层级关系参数值的Notification对象示例（订单发货邮件通知）:</para>
    /// <example>
    ///    Notification.Create(session, order.OrderNumber, 5)
    ///        .AddSingleParam("MemberName", member.Name)
    ///        .AddSingleParam("OrderNumber", order.OrderNumber)
    ///        .AddSingleParam("TotalAmt", order.TotalAmt)
    ///        .AddSingleParam("PayableAmt", order.PayableAmt)
    ///        .AddSingleParam("Address", order.Address)
    ///        .AddSingleParam("Contact", order.Contact)
    ///        .AddListParam("Lines")
    ///            .AddSingleParam("Product", "丝路花语丝滑苏绣抹胸")
    ///            .AddSingleParam("Qty", 1)
    ///            .AddSingleParam("Price", 139)
    ///        .NewSerial()
    ///            .AddSingleParam("Product", "梦幻之春晶采魔术文胸")
    ///            .AddSingleParam("Qty", 2)
    ///            .AddSingleParam("Price", 129)
    ///        .AddReceiver(member.MemberID, member.Name, member.EMail);
    /// </example>
    /// </summary>
    /// <remarks>
    /// <para>发送步骤:</para>
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

        #region 构造Notification对象的方法
        /// <summary>
        /// 创建消息通知对象
        /// </summary>
        /// <param name="session"></param>
        /// <param name="triggerId">触发消息通知的对象标识，例如会员注册使用会员ID、订单确认使用订单号或订单ID等</param>
        /// <param name="categoryId">消息通知分类ID，参考表BC_NOTIFY_CAT中的设置</param>
        /// <returns>创建的消息通知对象</returns>
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
        /// 添加简单参数
        /// </summary>
        /// <param name="name">参数名称</param>
        /// <param name="value">参数值，可以是int, string, float, double, decimal, DateTime类型</param>
        /// <returns>消息通知对象本身</returns>
        public Notification AddSingleParam(string name, object value)
        {
            NotificationParam param = new NotificationParam(this.NotifyID, null, 1, this._subIndex++, name, value);
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
            NotificationParam param = new NotificationParam(this._session, this.NotifyID, null, 1, this._subIndex++, name);
            param.Create(this._session);
            return param;
        }
        /// <summary>
        /// 添加接收者
        /// </summary>
        /// <param name="userId">接收者标识，例如用户ID、会员ID等</param>
        /// <param name="userName">接收者姓名</param>
        /// <param name="postTo">邮件消息该属性指定接收者邮箱地址，短信消息该属性指定接收者手机号码</param>
        /// <returns></returns>
        public Notification AddReceiver(string userId, string userName, string postTo)
        {
            NotificationReceiver receiver = new NotificationReceiver(this.NotifyID, userId, userName, postTo);
            receiver.Create(this._session);
            return this;
        }
        #endregion

        #region 发送Notification的方法
        private string _title;
        private string _content;
        private IList<NotificationReceiver> _receiver;
        private IDictionary<string, object> _param;
        /// <summary>
        /// 待发送消息的标题，例如邮件标题
        /// </summary>
        public string Title
        {
            get { return this._title; }
        }
        /// <summary>
        /// 待发送消息的内容，例如短信、邮件内容
        /// </summary>
        public string Content
        {
            get { return this._content; }
        }
        /// <summary>
        /// 接收者列表
        /// </summary>
        public IList<NotificationReceiver> Receivers
        {
            get { return this._receiver; }
        }
        /// <summary>
        /// 待发送列表，仅返回Mail, SMS这两种类型
        /// </summary>
        /// <param name="session"></param>
        /// <param name="startingDate">开始时间</param>
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
        /// <para>发送之前必须调用该方法，构造发送标题、内容，读取接收者列表等操作</para>
        /// <para>使用Title, Content, Receivers属性就可以获得发送需要的完整信息</para>
        /// </summary>
        /// <param name="session"></param>
        /// <returns>待发送数量</returns>
        public int ReadyToSend()
        {
            this._title = "";
            this._content = "";

            //接收者
            IList<NotificationSubscriber> subscribers = NotificationSubscriber.GetList(this._session, this.CatID);
            foreach (NotificationSubscriber subscriber in subscribers)
            {
                NotificationReceiver receiver = NotificationReceiver.Retrieve(this._session, this.NotifyID, subscriber.UserID);
                if (receiver == null)
                {
                    //当前订阅者是否已经添加到接收者列表中了？
                    receiver = new NotificationReceiver(this.NotifyID, subscriber.UserID, subscriber.UserName, subscriber.PostCode);
                    receiver.Create(this._session);
                }
            }
            //获取接收者列表
            this._receiver = NotificationReceiver.ToSendList(this._session, this.NotifyID);
            if (this._receiver.Count <= 0) return 0;

            //参数
            this._param = new Dictionary<string, object>();
            IList<NotificationParam> paramList = this._session.CreateEntityQuery<NotificationParam>()
                .Where(Exp.Eq("NotifyID", this.NotifyID) & Exp.Eq("ParentID", 0))
                .List<NotificationParam>();
            foreach (NotificationParam p in paramList)
                this._param.Add(p.ParamName, this.BuildParam(p));

            NotificationCategory category = Notification.GetCategory(this._session, this.CatID);
            //消息标题（对于邮件消息）
            if (category.Type == NotificationType.Mail && !string.IsNullOrEmpty(category.TitleTemplate) && category.TitleTemplate.Trim().Length > 0)
            {
                StringTemplate st = new StringTemplate(category.TitleTemplate);
                SetAttribute(st, this._param);
                this._title = st.ToString();
            }

            //消息内容
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
        /// 检查是否满足发送条件
        /// </summary>
        /// <param name="receiver">接收者</param>
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
                .Or(Exp.Eq("Type", type) & Exp.Eq("Code", "-99999")) //-90000至-99999的状态码为系统定义
                .List<NotificationCodeDef>();
            return defs[0];
        }
        /// <summary>
        /// 给消息通知中某个接收者发送完毕时的处理
        /// </summary>
        /// <param name="receiver">接收者</param>
        /// <param name="code">发送结果状态码</param>
        /// <param name="text">如果发送时发生异常，该文本传入异常信息</param>
        /// <returns>如果发送结果表明发送成功，返回true，否则返回false（短信的发送是否成功，还得延迟一段时间查询后才能获知）</returns>
        public bool AfterSendDo(NotificationReceiver receiver, string code, string text)
        {
            //查询发送结果状态码对象
            NotificationCodeDef codeDef = GetCodeDef(this._session, GetCategory(this._session, this.CatID).Type, code);

            //记录一条发送日志
            NotificationLog ntflog = new NotificationLog();
            ntflog.NotifyID = this.NotifyID;
            ntflog.ReceiverID = receiver.ReceiverID;
            ntflog.ResultCode = code;
            if (codeDef != null && codeDef.IsSuccess) ntflog.ErrorText = text;
            else ntflog.ErrorText = codeDef == null ? text : codeDef.Text + text;
            ntflog.LogTime = DateTime.Now;
            ntflog.Create(this._session);

            //接收者状态更新
            //TODO: 等待查询短信发送状态之后再做这个处理
            if (codeDef!=null && codeDef.IsSuccess) receiver.ResultStatus = NotificationResultStatus.AllSuccess;
            else receiver.ResultStatus = NotificationResultStatus.AllError;
            receiver.FinishTime = DateTime.Now;
            receiver.Update(this._session, "ResultStatus", "FinishTime");

            return codeDef != null && codeDef.IsSuccess;
        }
        /// <summary>
        /// 消息通知的所有接收者都发送完毕后的处理
        /// </summary>
        public void AfterSendDo()
        {
            //TODO: 等待从服务器查询状态之后再更新成已完成状态
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