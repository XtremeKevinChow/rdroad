using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Sys
{
    public enum OrgType
    {
        Own = 1,
    }

    public class OrgTypeInfo
    {
        public OrgTypeInfo(OrgType orgType, Type type, bool hasExtAttr)
        {
            this.OrgType = orgType;
            this.ExtAttrType = type;
            this.HasExtAttr = hasExtAttr;
        }

        public OrgType OrgType
        {
            get;
            set;
        }

        public Type ExtAttrType
        {
            get;
            set;
        }

        public bool HasExtAttr
        {
            get;
            set;
        }
    }

    public class OrgTypeRegistry
    {
        private static IDictionary<OrgType, OrgTypeInfo> _registry = new Dictionary<OrgType, OrgTypeInfo>(2);
        static OrgTypeRegistry()
        {
            _registry.Add(OrgType.Own, new OrgTypeInfo(OrgType.Own, null, false));
            //_registry.Add(OrgType.Partner, new OrgTypeInfo(OrgType.Partner, typeof(Partner), true));
            //_registry.Add(OrgType.Hospital, new OrgTypeInfo(OrgType.Hospital, null, false));
        }

        public static bool HasExtAttr(OrgType type)
        {
            OrgTypeInfo typeInfo = null;
            if (_registry.TryGetValue(type, out typeInfo))
                return typeInfo.HasExtAttr;
            return false;
        }
        public static Type ExtAttrType(OrgType type)
        {
            OrgTypeInfo typeInfo = null;
            if (_registry.TryGetValue(type, out typeInfo))
                return typeInfo.ExtAttrType;
            return null;
        }
    }
}
