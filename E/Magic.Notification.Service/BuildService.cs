using Magic.Framework.ORM;


namespace Magic.Notification.Service
{
    public class BuildService
    {
        private static string GetMobile(params string[] phones)
        {
            if (phones == null) return null;
            string mobile = null, maybe = null;
            foreach (string s in phones)
            {
                if (string.IsNullOrEmpty(s) || s.Trim().Length <= 0) continue;
                string phone = s.Trim();
                if (phone.Length == 11)
                {
                    mobile = phone;
                    break;
                }
                if (maybe == null) maybe = phone;
            }
            return mobile == null ? maybe : mobile;
        }
        /// <summary>
        /// 订单发货短信通知
        /// </summary>
        /// <param name="session"></param>
        /// <param name="snId"></param>
        public static void OrderDeliverSms(ISession session, int snId)
        {
            //CRMSN sn = CRMSN.Retrieve(session, snId);
            //string mobile = GetMobile(sn.Mobile, sn.Phone);
            //Notification.Create(session, sn.OrderNumber, 1)
            //    .AddReceiver(sn.MemberID.ToString(), sn.Contact, mobile)
            //    .AddSingleParam("ShippingNumber", sn.ShippingNumber)
            //    .AddSingleParam("OrderNumber", sn.SaleOrderNumber);
        }
    }
}