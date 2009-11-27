namespace Magic.ERP.Cache
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.ERP.Core;

    public class OrderRuleDefCache : CacheContainer
    {
        private IDictionary<int, OrderRuleDef> _cacheItems = new Dictionary<int, OrderRuleDef>();

        public OrderRuleDefCache(ISession session)
            : base(session)
        {
            IList<OrderRuleDef> ruleDefines = session.CreateEntityQuery<OrderRuleDef>().List<OrderRuleDef>();
            foreach (OrderRuleDef ord in ruleDefines)
                this._cacheItems.Add(ord.RuleDefineID, ord);
        }

        public override IList<T> ListAll<T>()
        {
            IList<T> result = new List<T>();
            foreach (KeyValuePair<int, OrderRuleDef> kv in this._cacheItems)
                result.Add((T)(kv.Value as object));
            return result;
        }
        public override T Get<T>(params object[] pkValues)
        {
            if (pkValues == null || pkValues.Length != 1) return default(T);
            int ruleDefcode = Magic.Framework.Utils.Cast.Int(pkValues[0]);
            if (!this._cacheItems.ContainsKey(ruleDefcode)) return default(T);
            return (T)(this._cacheItems[ruleDefcode] as object);
        }
        public override void Remove(object obj)
        {
            OrderRuleDef ruleDef = obj as OrderRuleDef;
            if (ruleDef == null) return;
            if (this._cacheItems.ContainsKey(ruleDef.RuleDefineID))
                this._cacheItems.Remove(ruleDef.RuleDefineID);
        }
        public override void Set(object obj)
        {
            OrderRuleDef ruleDef = obj as OrderRuleDef;
            if (ruleDef == null) return;
            if (this._cacheItems.ContainsKey(ruleDef.RuleDefineID))
                this._cacheItems[ruleDef.RuleDefineID] = ruleDef;
            else
                this._cacheItems.Add(ruleDef.RuleDefineID, ruleDef);
        }
    }
}