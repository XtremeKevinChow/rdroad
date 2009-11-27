namespace Magic.ERP
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    /// <summary>
    /// 需要进行库存交易的单据
    /// </summary>
    public interface IWHTransHead
    {
        /// <summary>
        /// 预先锁定了库存
        /// </summary>
        bool PreLockStock
        {
            get;
        }

        /// <summary>
        /// 单据类型
        /// </summary>
        string OrderTypeCode { get; }

        /// <summary>
        /// 单据号码
        /// </summary>
        string OrderNumber { get; }

        /// <summary>
        /// 引用单据类型（直接触发某个交易的单据类型）
        /// 例如会员退货，引用单据类型为发货单类型
        /// </summary>
        string RefOrderType { get; }

        /// <summary>
        /// 引用单据号码（直接触发某个交易的单据号码）
        /// 例如会员退货，引用单据号码为发货单号码
        /// </summary>
        string RefOrderNumber { get; }

        /// <summary>
        /// 原始单据类型
        /// 例如会员退货，原始单据类型为会员订单（销售订单）类型
        /// </summary>
        string OriginalOrderType { get; }

        /// <summary>
        /// 原始单据号码
        /// 例如会员退货，原始单据号码为会员订单（销售订单）号码
        /// </summary>
        string OrginalOrderNumber { get; }

        /// <summary>
        /// 单据创建者
        /// </summary>
        int CreateUser { get; }

        /// <summary>
        /// 单据创建时间
        /// </summary>
        DateTime CreateTime { get; }

        /// <summary>
        /// 交易执行完毕的回调函数
        /// </summary>
        /// <param name="session"></param>
        void AfterTransaction(ISession session);

        /// <summary>
        /// 
        /// </summary>
        string Note { get; }

        /// <summary>
        /// 获取交易明细
        /// </summary>
        /// <returns></returns>
        IList<IWHTransLine> GetLines(ISession session);
    }
}