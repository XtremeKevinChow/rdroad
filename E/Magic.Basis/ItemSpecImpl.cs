namespace Magic.Basis
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.Utils;

    public partial class ItemSpec
	{
        public static string ItemStatusText(ItemSpecStatus statusValue)
        {
            switch (statusValue)
            {
                case ItemSpecStatus.Enable: return "ио╪э";
                case ItemSpecStatus.Disable: return "об╪э";
            }
            return "";
        }
        public static string ItemStatusText(object statusValue)
        {
            return ItemStatusText(Cast.Enum<ItemSpecStatus>(statusValue));
        }
        public static ItemSpec Retrieve(ISession session, string itemCode, string colorCode, string sizeCode)
        {
            IList<ItemSpec> skus = session.CreateEntityQuery<ItemSpec>()
                .Where(Exp.Eq("ItemCode", itemCode.Trim().ToUpper()) & Exp.Eq("ColorCode", colorCode.Trim().ToUpper()) & Exp.Eq("SizeCode", sizeCode.Trim().ToUpper()))
                .List<ItemSpec>();
            return skus == null || skus.Count <= 0 ? null : skus[0];
        }
        public static ItemSpec Retrieve(ISession session, string sku)
        {
            if (string.IsNullOrEmpty(sku) || sku.Trim().Length <= 0) return null;
            IList<ItemSpec> skus = session.CreateEntityQuery<ItemSpec>()
                .Where(Exp.Eq("BarCode", sku.Trim().ToUpper()))
                .List<ItemSpec>();
            return skus == null || skus.Count <= 0 ? null : skus[0];
        }
	}
}
