namespace Magic.ERP
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    public abstract class CacheContainer
    {
        public CacheContainer(ISession session)
        {
        }

        public abstract IList<T> ListAll<T>();
        public abstract T Get<T>(params object[] pkValues);
        public abstract void Set(object obj);
        public abstract void Remove(object obj);
    }
}