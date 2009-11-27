namespace Magic.ERP
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.ERP.Cache;
    using Magic.ERP.Core;

    public class CacheManager : CacheContainer
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(CacheManager));
        private IDictionary<Type, CacheContainer> _cache = new Dictionary<Type, CacheContainer>();

        public CacheManager(ISession session)
            : base(session)
        {
            _cache.Add(typeof(TransTypeDef), new TransTypeDefCache(session));
            _cache.Add(typeof(OrderTypeDef), new OrderTypeDefCache(session));
            _cache.Add(typeof(OrderRuleDef), new OrderRuleDefCache(session));
        }
        public override IList<T> ListAll<T>()
        {
            if (this._cache.ContainsKey(typeof(T)))
                return this._cache[typeof(T)].ListAll<T>();
            log.ErrorFormat("{0} is not a cached type", typeof(T).Name);
            return new List<T>(0);
        }
        public override T Get<T>(params object[] pkValues)
        {
            if (this._cache.ContainsKey(typeof(T)))
                return this._cache[typeof(T)].Get<T>(pkValues);
            log.ErrorFormat("{0} is not a cached type", typeof(T).Name);
            return default(T);
        }
        public override void Set(object obj)
        {
            if (obj == null) return;
            if (!this._cache.ContainsKey(obj.GetType()))
            {
                log.ErrorFormat("{0} is not a cached type", obj.GetType().Name);
                return;
            }
            this._cache[obj.GetType()].Set(obj);
        }
        public override void Remove(object obj)
        {
            if (obj == null) return;
            if (!this._cache.ContainsKey(obj.GetType()))
            {
                log.ErrorFormat("{0} is not a cached type", obj.GetType().Name);
                return;
            }
            this._cache[obj.GetType()].Remove(obj);
        }
    }
}