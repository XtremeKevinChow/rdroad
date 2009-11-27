namespace Magic.Basis
{
    using System.Collections.Generic;
    using Magic.Framework.Utils;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class ItemMaster
	{
        public static string ItemTypeText(ItemType typeValue)
        {
            switch (typeValue)
            {
                case ItemType.AssistantItem: return "辅料";
                case ItemType.NormalItem: return "商品";
                case ItemType.SerialItem: return "系列商品";
                case ItemType.SetItem: return "套装商品";
            }
            return "";
        }
        public static string ItemTypeText(object typeValue)
        {
            return ItemTypeText(Cast.Enum<ItemType>(typeValue));
        }
        public static ItemMaster Retrieve(ISession session, string itemCode)
        {
            IList<ItemMaster> master = session.CreateEntityQuery<ItemMaster>()
                .Where(Exp.Eq("ItemCode", itemCode))
                .List<ItemMaster>();
            return master == null || master.Count <= 0 ? null : master[0];
        }
	}
}
