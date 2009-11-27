using System.Collections.Generic;

namespace Magic.Sys
{
    public class OrgCache
    {
        private static IDictionary<OrgType, Org> _roots = new Dictionary<OrgType, Org>();
        private static object _lock = new object();

        public static void Add(OrgType type, Org org)
        {
            lock (_lock)
            {
                _roots.Add(type, org);
            }
        }
        public static void Clear()
        {
            _roots.Clear();
        }
        public static Org Get(OrgType type)
        {
            Org org = null;
            _roots.TryGetValue(type, out org);
            return org;
        }
    }
}
