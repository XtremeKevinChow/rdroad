namespace Magic.ERP.Orders
{
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for SDHead
    /// ���۶������������� SD - Sales Deliver
    /// ��������Ϊ�� SD0��ORD_TYPE_DEF���ж��壩
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