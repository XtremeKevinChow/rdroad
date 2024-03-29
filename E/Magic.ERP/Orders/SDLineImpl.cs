namespace Magic.ERP.Orders
{
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for SDLine
    /// 销售订单发货单明细
    /// </summary>
    [Table("ORD_DLV_LINE")]
    public partial class SDLine : DeliverLine
	{
        public SDLine()
        {
        }

        public static SDLine Retrieve(ISession session, string orderNumber, string lineNumber)
        {
            return EntityManager.Retrieve<SDLine>(session, new string[] { "OrderNumber", "LineNumber" }, new object[] { orderNumber, lineNumber });
        }
        public static bool Delete(ISession session, string orderNumber, string lineNumber)
        {
            return EntityManager.Delete<SDLine>(session, new string[] { "OrderNumber", "LineNumber" }, new object[] { orderNumber, lineNumber }) > 0;
        }
	}
}