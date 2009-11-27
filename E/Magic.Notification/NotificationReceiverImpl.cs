namespace Magic.Notification
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class NotificationReceiver
    {
        internal NotificationReceiver(int notifyId, string userId, string userName, string postTo)
        {
            this.NotifyID = notifyId;
            this.UserID = userId;
            this.UserName = userName;
            this.PostTo = postTo;
            this.FinishTime = new DateTime(1900, 1, 1);
            this.ResultStatus = NotificationResultStatus.New;
        }
        internal static NotificationReceiver Retrieve(ISession session, int notifyId, string userId)
        {
            IList<NotificationReceiver> receivers = session.CreateEntityQuery<NotificationReceiver>()
                .Where(Exp.Eq("NotifyID", notifyId) & Exp.Eq("UserID", userId))
                .List<NotificationReceiver>();
            return receivers == null || receivers.Count <= 0 ? null : receivers[0];
        }
        internal static IList<NotificationReceiver> ToSendList(ISession session, int notifyId)
        {
            return session.CreateEntityQuery<NotificationReceiver>()
                .Where(Exp.Eq("NotifyID", notifyId) & Exp.Eq("ResultStatus", NotificationResultStatus.New))
                .List<NotificationReceiver>();
        }
    }
}
