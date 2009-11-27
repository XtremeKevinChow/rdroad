namespace Magic.ERP.Cache
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.ERP.Core;

    public class TransTypeDefCache : CacheContainer
    {
        private IDictionary<string, TransTypeDef> _cacheItems = new Dictionary<string, TransTypeDef>();

        public TransTypeDefCache(ISession session)
            : base(session)
        {
            IList<TransTypeDef> typeDefines = session.CreateEntityQuery<TransTypeDef>().List<TransTypeDef>();
            foreach (TransTypeDef ttd in typeDefines)
                this._cacheItems.Add(ttd.TransTypeCode, ttd);
        }

        public override IList<T> ListAll<T>()
        {
            IList<T> result = new List<T>();
            foreach (KeyValuePair<string, TransTypeDef> kv in this._cacheItems)
                result.Add((T)(kv.Value as object));
            return result;
        }
        public override T Get<T>(params object[] pkValues)
        {
            if (pkValues == null || pkValues.Length != 1) return default(T);
            string typeDefcode = pkValues[0].ToString();
            if (!this._cacheItems.ContainsKey(typeDefcode)) return default(T);
            return (T)(this._cacheItems[typeDefcode] as object);
        }
        public override void Remove(object obj)
        {
            TransTypeDef typeDef = obj as TransTypeDef;
            if (typeDef == null) return;
            if (this._cacheItems.ContainsKey(typeDef.TransTypeCode))
                this._cacheItems.Remove(typeDef.TransTypeCode);
        }
        public override void Set(object obj)
        {
            TransTypeDef typeDef = obj as TransTypeDef;
            if (typeDef == null) return;
            if (this._cacheItems.ContainsKey(typeDef.TransTypeCode))
                this._cacheItems[typeDef.TransTypeCode] = typeDef;
            else
                this._cacheItems.Add(typeDef.TransTypeCode, typeDef);
        }
    }
}