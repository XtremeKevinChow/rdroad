namespace Magic.ERP.Orders
{
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for SDHead
    /// 销售订单发货单主档 SD - Sales Deliver
    /// 单据类型为： SD0（ORD_TYPE_DEF表中定义）
    /// </summary>
    [Table("ORD_DLV_HEAD")]
    public partial class SDHead : DeliverHead
    {
        public const string ORDER_TYPE = "SD0";

        public SDHead()
        {
        }

        public static SDHead Retrieve(ISession session, string orderNumber)
        {
            return EntityManager.Retrieve<SDHead>(session, orderNumber);
        }
        public static bool Delete(ISession session, string orderNumber)
        {
            return EntityManager.Delete<SDHead>(session, orderNumber);
        }

        public override string RefOrderType
        {
            get
            {
                return "SO0";
            }
        }
    }
}