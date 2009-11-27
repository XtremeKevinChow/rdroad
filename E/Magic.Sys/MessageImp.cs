using System;
using System.Data;
using System.Collections.Generic;
using System.Text;
using Magic.Security;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Framework.OQL;

namespace Magic.Sys
{
  

    public partial class Message
    {
       static log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Message));

        #region public Methods
       
        /// <summary>
        /// 
        /// </summary>
        /// <param name="session"></param>
        /// <param name="userId"></param>
        /// <param name="sendTimeBegin"></param>
        /// <param name="sendTimeEnd"></param>
        /// <returns></returns>
        public static DataTable QueryUnreadMessageByUser(ISession session, int userId, DateTime sendTimeBegin, DateTime sendTimeEnd)
        {
            string oql = "select r.SendTime as SendTime, r.ReceiverId as ReceiverId, m.MessageId as MessageId, m.Title as Title,m.ViewEntry as ViewEntry, t.TypeName as TypeName  from Message m left outer join MsgReceiver r on m.MessageId=r.MessageId inner join MessageType as t on t.MsgTypeId=m.MsgTypeId ";
            return
                session.CreateObjectQuery(oql)
                 .Attach(typeof(Message))
                 .Attach(typeof(MsgReceiver))
                 .Attach(typeof(MessageType))
                 .Where(Exp.Eq("r.UserId", userId))
                 .And(Exp.In("m.Status",MessageStatus.Sent))
                  .And(Exp.Eq("r.ReadStatus", MessageReadStatus.Unread))
                 .And(Exp.Ge("r.SendTime", sendTimeBegin))
                 .And(Exp.Le("r.SendTime", sendTimeEnd))
                 .OrderBy(new OrderBy("r.SendTime", Order.Desc))
                 .DataSet().Tables[0];
        }

        /// <summary>
        /// 创建新消息
        /// </summary>
        /// <param name="session"></param>
        /// <param name="tmplCode"></param>
        /// <param name="title"></param>
        /// <param name="content"></param>
        /// <param name="createBy"></param>
        /// <param name="sentAtOnce"></param>
        /// <param name="sentTime"></param>
        public static void NewMessage(ISession session, string tmplCode, string title, string content, string createBy, bool sentAtOnce, DateTime sentTime)
        {
            MsgTemplate tmpl = MsgTemplate.Retrieve(session, tmplCode);
            if (tmpl != null)
            {
                Message msg = new Message();
                msg._accessibility = tmpl.Accessibility;
                msg._source = tmpl.Source;
                msg._destination = "";
                if(tmpl.Expires > 0)
                    msg._expireTime = DateTime.Now.AddSeconds(tmpl.Expires);
                msg._createBy = createBy;
                msg._createTime = DateTime.Now;
                msg._msgTypeId = tmpl.MsgTypeId;
                msg._responseEntry = tmpl.ResponseEntry;
                if (!sentAtOnce)
                {
                    if (sentTime < DateTime.Now)
                        throw new Exception("发送时间不能小于当前时间");
                    msg._sendTime = sentTime;
                }
                msg._status = MessageStatus.New;
                msg._title = title;
                msg._content = content;
                msg._tmplCode = tmplCode;
                if (!msg.Create(session))
                {
                    logger.Info(string.Format("创建消息失败.模板代码:{0},标题:{1},内容:{2},创建人:{3}",tmplCode,title,content,createBy));
                }

            }
            else
            {
                throw new Exception(string.Format("指定的消息模板代码不存在；模板代码:{0}",tmplCode));
            }
        }

        /// <summary>
        /// 发送新消息
        /// </summary>
        /// <param name="session"></param>
        public static void SendNewMessage(ISession session)
        {
            
            IList<Message> newMsgs = session.CreateEntityQuery<Message>().And(Exp.Eq("Status", MessageStatus.New)).List<Message>();

            if (newMsgs != null && newMsgs.Count > 0)
            {
                
                ((Session)session).BeginTransaction();
                try
                {
                    foreach (Message msg in newMsgs)
                    {
                        IList<User> sendingUsers = MsgSubscriber.GetSendingUser4Subscriber(session, msg.TmplCode);
                        foreach (User user in sendingUsers)
                        {
                            MsgReceiver msgrev = new MsgReceiver();
                            msgrev.MessageId = msg.MessageId;
                            msgrev.ReadStatus = MessageReadStatus.Unread;
                            msgrev.SendTime = msg.SendTime;
                            msgrev.SubscriberType = true;
                            msgrev.UserId = user.UserId;
                            msgrev.ReceiveTime = DateTime.Now;
                            msgrev.ExpireTime = msg.ExpireTime;
                            msgrev.Create(session);
                        }
                        msg.Status = MessageStatus.Sent;
                        msg.Update(session, "Status");
                    }
                    
                    ((Session)session).Commit();
                }
                catch
                {
                    ((Session)session).Rollback();
                    throw;
                }
            }
        }
        #endregion
    }
}
