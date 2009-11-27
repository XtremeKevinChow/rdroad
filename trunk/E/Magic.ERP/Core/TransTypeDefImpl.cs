namespace Magic.ERP.Core
{
    using System.Collections.Generic;
    using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;
    using Magic.ERP;
    using Magic.Framework.Utils;
    using Magic.Framework.ORM.Query;

	public partial class  TransTypeDef
	{
        public static string PriceSourceTypeText(object value)
        {
            TransTypePriceSource priceSource = Cast.Enum<TransTypePriceSource>(value);
            switch (priceSource)
            {
                case TransTypePriceSource.NoPrice:
                    return "����¼�۸�";
                case TransTypePriceSource.FromSourceOrder:
                    return "���ݼ۸�";
                case TransTypePriceSource.FromMovAvgCost:
                    return "�ƶ�ƽ����";
                case TransTypePriceSource.FromAreaCfg:
                    return "�ֿ�����ȷ��";
            }
            return "";
        }

        public bool Delete(ISession session)
        {
            foreach (OrderTransDef orderTransDef in
                session.CreateEntityQuery<OrderTransDef>()
                .Where(Exp.Eq("TransTypeCode", this._transTypeCode)).List<OrderTransDef>())
                orderTransDef.Delete(session);
            foreach (OrderTransDefOptions orderTransDefOption in
                session.CreateEntityQuery<OrderTransDefOptions>()
                .Where(Exp.Eq("TransTypeCode", this._transTypeCode)).List<OrderTransDefOptions>())
                orderTransDefOption.Delete(session);
            foreach (WHAreaCfg cfg in
                session.CreateEntityQuery<WHAreaCfg>()
                .Where(Exp.Eq("AreaCfgCode", this._transTypeCode)).List<WHAreaCfg>())
                cfg.Delete(session);

            if (EntityManager.Delete(session, this))
            {
                ERPUtil.Cache.Remove(this);
                return true;
            }
            return true;
        }
	}
}