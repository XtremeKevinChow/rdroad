namespace Magic.Basis
{
    using Magic.Framework.Utils;

	public partial class  Vendor
	{
        public static string VendorStatusText(VendorStatus statusValue)
        {
            switch (statusValue)
            {
                case VendorStatus.Delete: return "É¾³ı";
                case VendorStatus.Enable: return "ÆôÓÃ";
                case VendorStatus.Disable: return "½ûÓÃ";
            }
            return "";
        }
        public static string VendorStatusText(object statusValue)
        {
            return VendorStatusText(Cast.Enum<VendorStatus>(statusValue));
        }
	}
}
