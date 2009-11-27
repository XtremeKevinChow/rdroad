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
                case ApproveStatus.Approve: return "已签核";
                case ApproveStatus.Reject: return "驳回";
                case ApproveStatus.UnApprove: return "未签核";
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
                throw new Exception("该单据正在处理签核结果，请不要重复提交");
            lock (_lockObj)
            {
                if (_approvingItems.Contains(appId))
                    throw new Exception("该单据正在处理签核结果，请不要重复提交");
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
                        throw new Exception(string.Format("该单据已经签核完成，请不要重复提交 {0}", ar.ApproveID));

                    ar.ActiveItem = false;
                    ar.HasFinished = true;
                    ar.ApproveResult = status == ApproveStatus.Approve;
                    ar.ApproveTime = DateTime.Now;
                    ar.ApproveNote = appNote;
                    ar.Update(session, "ActiveItem", "HasFinished", "ApproveResult", "ApproveTime", "ApproveNote");

                    #region 驳回处理
                    if (status == ApproveStatus.Reject)
                    {
                        appOrder = ERPUtil.GetApproveItem(session, ai.OrderTypeCode, ai.OrderNumber);
                        if (appOrder == null)
                            throw new Exception(string.Format("Approve item for {{{0}, {1}}} not found", ai.OrderTypeCode, ai.OrderNumber));

                        //删除掉后面还没有签核的记录
                        session.CreateEntityQuery<OrderApproveResult>()
                            .Where(Exp.Eq("ApproveID", ai.ApproveID))
                            .And(Exp.Eq("HasFinished", false))
                            .Delete();
                        //更新签核主记录状态
                        ai.Status = ApproveStatus.Reject;
                        ai.ApproveTime = DateTime.Now;
                        ai.Update(session, "Status", "ApproveTime");
                        //单据的签核状态
                        appOrder.ApproveResult = ApproveStatus.Reject;
                        appOrder.ApproveTime = DateTime.Now;
                        appOrder.ApproveUser = ar.ApproveUser;
                        appOrder.ApproveNote = appNote;
                        appOrder.Update(session, "ApproveResult", "ApproveTime", "ApproveUser", "ApproveNote");
                        //执行回调方法
                        appOrder.OnApprove(session);
                        return;
                    }
                    #endregion

                    #region 签核通过处理
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
                        //单据签核过程还没有结束
                        next.ActiveItem = true;
                        next.Update(session, "ActiveItem");
                    }
                    else
                    {
                        //单据已经完成签核
                        appOrder = ERPUtil.GetApproveItem(session, ai.OrderTypeCode, ai.OrderNumber);
                        if (appOrder == null)
                            throw new Exception(string.Format("Approve item for {{{0}, {1}}} not found", ai.OrderTypeCode, ai.OrderNumber));

                        //更新签核主记录状态
                        ai.Status = ApproveStatus.Approve;
                        ai.ApproveTime = DateTime.Now;
                        ai.Update(session, "Status", "ApproveTime");
                        //被签核单据的状态
                        appOrder.ApproveResult = ApproveStatus.Approve;
                        appOrder.ApproveTime = DateTime.Now;
                        appOrder.ApproveUser = ar.ApproveUser;
                        appOrder.ApproveNote = appNote;
                        appOrder.Update(session, "ApproveResult", "ApproveTime", "ApproveUser", "ApproveNote");
                        //执行回调方法
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