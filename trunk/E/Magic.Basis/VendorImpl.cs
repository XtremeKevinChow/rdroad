namespace Magic.Basis
{
    using Magic.Framework.Utils;

	public partial class  Vendor
	{
        public static string VendorStatusText(VendorStatus statusValue)
        {
            switch (statusValue)
            {
                case VendorStatus.Delete: return "ɾ��";
                case VendorStatus.Enable: return "����";
                case VendorStatus.Disable: return "����";
            }
            return "";
        }
        public static string VendorStatusText(object statusValue)
        {
            return VendorStatusText(Cast.Enum<VendorStatus>(statusValue));
        }
	}
}
