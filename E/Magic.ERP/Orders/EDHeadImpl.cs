namespace Magic.ERP.Orders
{
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for EDHead
    /// 换货发货单主档 ED - Exchange Deliver
    /// 单据类型为： DL2（ORD_TYPE_DEF表中定义）
    /// </summary>
    [Table("ORD_DLV_HEAD")]
    public partial class EDHead : DeliverHead
    {
        public const string ORDER_TYPE = "DL2";

        public EDHead()
        {
        }

        public static EDHead Retrieve(ISession session, string orderNumber)
        {
            return EntityManager.Retrieve<EDHead>(session, orderNumber);
        }
        public static bool Delete(ISession session, string orderNumber)
        {
            return EntityManager.Delete<EDHead>(session, orderNumber);
        }

        public override string RefOrderType
        {
            get
            {
                return "RC3";
            }
        }
    }
}