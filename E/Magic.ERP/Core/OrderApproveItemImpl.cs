namespace Magic.ERP.Core
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.ORM.Mapping;

    public partial class OrderApproveItem
    {
        private static object _lockObj = new object();
        private static IList<int> _approvingItems = new List<int>();

        public static string StatusText(object status)
        {
            ApproveStatus statusValue = Magic.Framework.Utils.Cast.Enum<ApproveStatus>(status);
            switch (statusValue)
            {
                case ApproveStatus.Approve: return "��ǩ��";
                case ApproveStatus.Reject: return "����";
                case ApproveStatus.UnApprove: return "δǩ��";
            }
            return "";
        }

        public static void Approve(ISession session, int appId, string appNote)
        {
            OrderApproveItem.DoApprove(session, appId, ApproveStatus.Approve, appNote);
        }
        public static void Reject(ISession session, int appId, string appNote)
        {
            OrderApproveItem.DoApprove(session, appId, ApproveStatus.Reject, appNote);
        }
        private static void DoApprove(ISession session, int appId, ApproveStatus status, string appNote)
        {
            if (_approvingItems.Contains(appId))
                throw new Exception("�õ������ڴ���ǩ�˽�����벻Ҫ�ظ��ύ");
            lock (_lockObj)
            {
                if (_approvingItems.Contains(appId))
                    throw new Exception("�õ������ڴ���ǩ�˽�����벻Ҫ�ظ��ύ");
                _approvingItems.Add(appId);

                try
                {
                    IApprovable appOrder = null;
                    OrderApproveResult ar = OrderApproveResult.Retrieve(session, appId);
                    if (ar == null)
                        throw new Exception("Invalidate approve result " + appId.ToString());
                    OrderApproveItem ai = OrderApproveItem.Retrieve(session, ar.ApproveID);
                    if (ai == null)
                        throw new Exception(string.Format("Invalidate approve item {0}", ar.ApproveID));
                    if (ar.HasFinished)
                        throw new Exception(string.Format("�õ����Ѿ�ǩ����ɣ��벻Ҫ�ظ��ύ {0}", ar.ApproveID));

                    ar.ActiveItem = false;
                    ar.HasFinished = true;
                    ar.ApproveResult = status == ApproveStatus.Approve;
                    ar.ApproveTime = DateTime.Now;
                    ar.ApproveNote = appNote;
                    ar.Update(session, "ActiveItem", "HasFinished", "ApproveResult", "ApproveTime", "ApproveNote");

                    #region ���ش���
                    if (status == ApproveStatus.Reject)
                    {
                        appOrder = ERPUtil.GetApproveItem(session, ai.OrderTypeCode, ai.OrderNumber);
                        if (appOrder == null)
                            throw new Exception(string.Format("Approve item for {{{0}, {1}}} not found", ai.OrderTypeCode, ai.OrderNumber));

                        //ɾ�������滹û��ǩ�˵ļ�¼
                        session.CreateEntityQuery<OrderApproveResult>()
                            .Where(Exp.Eq("ApproveID", ai.ApproveID))
                            .And(Exp.Eq("HasFinished", false))
                            .Delete();
                        //����ǩ������¼״̬
                        ai.Status = ApproveStatus.Reject;
                        ai.ApproveTime = DateTime.Now;
                        ai.Update(session, "Status", "ApproveTime");
                        //���ݵ�ǩ��״̬
                        appOrder.ApproveResult = ApproveStatus.Reject;
                        appOrder.ApproveTime = DateTime.Now;
                        appOrder.ApproveUser = ar.ApproveUser;
                        appOrder.ApproveNote = appNote;
                        appOrder.Update(session, "ApproveResult", "ApproveTime", "ApproveUser", "ApproveNote");
                        //ִ�лص�����
                        appOrder.OnApprove(session);
                        return;
                    }
                    #endregion

                    #region ǩ��ͨ������
                    IList<OrderApproveResult> unfinished = session.CreateEntityQuery<OrderApproveResult>()
                        .Where(Exp.Eq("ApproveID", ar.ApproveID))
                        .And(Exp.Eq("HasFinished", false))
                        .OrderBy("ApproveResultID")
                        .SetLastOffset(1)
                        .List<OrderApproveResult>();
                    OrderApproveResult next = null;
                    if (unfinished != null && unfinished.Count > 0)
                        next = unfinished[0];

                    if (next != null)
                    {
                        //����ǩ�˹��̻�û�н���
                        next.ActiveItem = true;
                        next.Update(session, "ActiveItem");
                    }
                    else
                    {
                        //�����Ѿ����ǩ��
                        appOrder = ERPUtil.GetApproveItem(session, ai.OrderTypeCode, ai.OrderNumber);
                        if (appOrder == null)
                            throw new Exception(string.Format("Approve item for {{{0}, {1}}} not found", ai.OrderTypeCode, ai.OrderNumber));

                        //����ǩ������¼״̬
                        ai.Status = ApproveStatus.Approve;
                        ai.ApproveTime = DateTime.Now;
                        ai.Update(session, "Status", "ApproveTime");
                        //��ǩ�˵��ݵ�״̬
                        appOrder.ApproveResult = ApproveStatus.Approve;
                        appOrder.ApproveTime = DateTime.Now;
                        appOrder.ApproveUser = ar.ApproveUser;
                        appOrder.ApproveNote = appNote;
                        appOrder.Update(session, "ApproveResult", "ApproveTime", "ApproveUser", "ApproveNote");
                        //ִ�лص�����
                        appOrder.OnApprove(session);
                        session.PostCommit += appOrder.PostApprove;
                    }
                    #endregion
                }
                catch
                {
                    throw;
                }
                finally
                {
                    _approvingItems.Remove(appId);
                }
            }
        }
    }
}