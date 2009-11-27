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
                case ItemType.AssistantItem: return "����";
                case ItemType.NormalItem: return "��Ʒ";
                case ItemType.SerialItem: return "ϵ����Ʒ";
                case ItemType.SetItem: return "��װ��Ʒ";
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
