namespace Magic.Notification
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    internal partial class NotificationSubscriber : IEntity
    {
        public static IList<NotificationSubscriber> GetList(ISession session, int categoryId)
        {
            return session.CreateEntityQuery<NotificationSubscriber>()
                .Where(Exp.Eq("CatID", categoryId))
                .List<NotificationSubscriber>();
        }
    }
}