namespace Magic.ERP
{
    using System;
    using Magic.Framework.ORM;

    /// <summary>
    /// 可签核的单据
    /// </summary>
    public interface IApprovable : IEntity
    {
        /// <summary>
        /// 单据类型
        /// </summary>
        string OrderTypeCode { get; }

        /// <summary>
        /// 单据号码
        /// </summary>
        string OrderNumber { get; }

        /// <summary>
        /// 签核结果
        /// </summary>
        ApproveStatus ApproveResult { get; set; }

        /// <summary>
        /// 签核人
        /// </summary>
        int ApproveUser { get; set; }

        /// <summary>
        /// 签核时间
        /// </summary>
        DateTime ApproveTime { get; set; }

        /// <summary>
        /// 签核说明
        /// </summary>
        string ApproveNote { get; set; }

        /// <summary>
        /// <para>签核完成后的回调方法</para>
        /// <para>这个session位于签核完成处理的事务之外，签核的事务已经成功提交或失败回滚了</para>
        /// <para>出入库单据，在签核完成后尝试使用一个新的session进行出入库交易，关闭被签核的单据，这些处理应该放在这个方法中，避免与签核的session产生死锁</para>
        /// </summary>
        /// <param name="session">这个session是只读的，即不能用它来更新数据，只能查询数据</param>
        void PostApprove(ISession session);

        /// <summary>
        /// <para>签核过程中的回调方法</para>
        /// <para>这个session处于签核完成处理的事务之中，被签核单据的状态更新等，应该放在这个方法中处理</para>
        /// </summary>
        /// <param name="session"></param>
        void OnApprove(ISession session);

        /// <summary>
        /// 单据创建者
        /// </summary>
        int CreateUser { get; }

        /// <summary>
        /// 单据创建时间
        /// </summary>
        DateTime CreateTime { get; }
    }
}