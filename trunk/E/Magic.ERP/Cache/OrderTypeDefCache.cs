namespace Magic.ERP.Cache
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.ERP.Core;

    public class OrderTypeDefCache : CacheContainer
    {
        private IDictionary<string, OrderTypeDef> _cacheItems = new Dictionary<string, OrderTypeDef>();

        public OrderTypeDefCache(ISession session)
            : base(session)
        {
            IList<OrderTypeDef> defines = session.CreateEntityQuery<OrderTypeDef>().List<OrderTypeDef>();
            foreach (OrderTypeDef def in defines)
                this._cacheItems.Add(def.OrderTypeCode, def);
        }

        public override IList<T> ListAll<T>()
        {
            IList<T> result = new List<T>();
            foreach (KeyValuePair<string, OrderTypeDef> kv in this._cacheItems)
                result.Add((T)(kv.Value as object));
            return result;
        }
        public override T Get<T>(params object[] pkValues)
        {
            if (pkValues == null || pkValues.Length != 1) return default(T);
            string pk = pkValues[0].ToString();
            if (!this._cacheItems.ContainsKey(pk)) return default(T);
            return (T)(this._cacheItems[pk] as object);
        }
        public override void Remove(object obj)
        {
            OrderTypeDef def = obj as OrderTypeDef;
            if (def == null) return;
            if (this._cacheItems.ContainsKey(def.OrderTypeCode))
                this._cacheItems.Remove(def.OrderTypeCode);
        }
        public override void Set(object obj)
        {
            OrderTypeDef def = obj as OrderTypeDef;
            if (def == null) return;
            if (this._cacheItems.ContainsKey(def.OrderTypeCode))
                this._cacheItems[def.OrderTypeCode] = def;
            else
                this._cacheItems.Add(def.OrderTypeCode, def);
        }
    }
}