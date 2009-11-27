using System;
using System.Collections;
using System.Collections.Generic;
using System.Reflection;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Data;
using Magic.Framework;
using Magic.Framework.Utils;
using Magic.Framework.Debug;

namespace Magic.Sys
{
    public interface IOrgExtend : IEntity
    {
        int OrgId { get; set; }
        void Json(ISession session, SimpleJson json);
    }

    /// <summary>
    /// 组织结构的数据量很少，维护比较少，因此这个类中的方法无需考虑数据库性能开销问题
    /// </summary>
    /// <param name="session"></param>
    /// <returns></returns>
    public partial class Org
    {
        #region extended members of Org
        private IList<Org> _children;
        public IList<Org> Children
        {
            get { return this._children; }
        }

        public IOrgExtend ExtAttr
        {
            get;
            set;
        }

        private void AddChild(Org child)
        {
            if (child == null) return;
            if (this._children == null) this._children = new List<Org>(1);
            this._children.Add(child);
        }
        private void RemoveChild(Org child)
        {
            if (child == null || this._children == null) return;
            this._children.Remove(child);
        }

        public override int GetHashCode()
        {
            return this.OrgId.GetHashCode();
        }
        public override bool Equals(object obj)
        {
            if (obj == null || !(obj is Org)) return false;
            if (object.ReferenceEquals(this, obj)) return true;
            return this.OrgId == (obj as Org).OrgId;
        }
        public override string ToString()
        {
            return this.OrgName + (this.IsVirtual ? string.Empty : " (" + this.OrgCode + ")");
        }
        #endregion
        private static bool _defaultLoaded = false;
        private static bool _useDefaultOrg = false;
        private static int _defaultValue = -1;
        private const string ORG_USE_DEFAULT = "ORG_USE_DEFAULT";
        private const string ORG_DEFAULT_VALUE = "ORG_DEFAULT_VALUE";

        private IList<Org> NextLevelChildren(ISession session)
        {
            IList<Org> children = session.CreateEntityQuery<Org>()
                .Where(Exp.Eq("ParentId", this.OrgId) & Exp.NEq("Deleted", true))
                .OrderBy("OrgSeq")
                .List<Org>();
            //if (OrgTypeRegistry.HasExtAttr(this.OrgType) && children.Count > 0)
            //    foreach (Org o in children)
            //        o.ExtAttr = Partner.Retrieve(session, o.OrgId) as IOrgExtend;
            return children;
        }
        public static Org Root(ISession session, OrgType type)
        {
            IList<Org> list = session.CreateEntityQuery<Org>()
                .Where(Exp.Eq("IsRoot", true) & Exp.Eq("OrgType", type) & Exp.NEq("Deleted", true))
                .List<Org>();
            if (OrgTypeRegistry.HasExtAttr(type) && list.Count > 0)
                foreach (Org o in list)
                    //有问题，因为不会加载ExtAttr与Org的对应关系，但合作企业的根节点为虚拟节点，所以不会遇到
                    o.ExtAttr = EntityManager.Retrieve(session, OrgTypeRegistry.ExtAttrType(type), o.OrgId) as IOrgExtend;
            return list.Count <= 0 ? null : list[0];
        }
        private static Org LoadTree(ISession session, OrgType type)
        {
            Org root = Root(session, type);
            if (root == null) return null;
            LoadTree(session, root);
            return root;
        }
        private static void LoadTree(ISession session, Org org)
        {
            if (org == null) return;
            org._children = org.NextLevelChildren(session);
            if (org._children != null && org._children.Count > 0)
                for (int i = 0; i < org._children.Count; i++)
                    LoadTree(session, org._children[i]);
        }

        /// <summary>
        /// 获取整个Org树
        /// </summary>
        /// <param name="session"></param>
        /// <param name="type"></param>
        /// <returns></returns>
        public static Org Get(ISession session, OrgType type)
        {
            Org root = OrgCache.Get(type);
            if (root == null)
            {
                root = LoadTree(session, type);
                if (root != null)
                    OrgCache.Add(type, root);
            }
            return root;
        }
        /// <summary>
        /// 获取某个Org节点
        /// </summary>
        /// <param name="session"></param>
        /// <param name="type"></param>
        /// <returns></returns>
        public static Org Get(ISession session, OrgType type, int id)
        {
            return FindInTree(Org.Get(session, type), id);
        }
        private static Org FindInTree(Org org, int id)
        {
            if (org == null || org.OrgId == id) return org;
            if (org.Children != null && org.Children.Count > 0)
                foreach (Org o in org.Children)
                {
                    Org temp = Org.FindInTree(o, id);
                    if (temp != null) return temp;
                }
            return null;
        }
        public static SimpleJson GetOrgJSON(ISession session, OrgType type, int orgId)
        {
            return GetOrgJSON(session, Org.Get(session, type, orgId));
        }
        public static SimpleJson GetOrgJSON(ISession session, Org org)
        {
            SimpleJson json = new SimpleJson();
            if (org == null)
            {
                json.HandleError("Org is null");
                return json;
            }

            json.Add("id", org.OrgId)
                .Add("parent", org.ParentId)
                .Add("root", org.IsRoot)
                .Add("virtual", org.IsVirtual)
                .Add("seq", org.OrgSeq)
                .Add("code", org.OrgCode)
                .Add("name", org.OrgName)
                .Add("remark", org.Description)
                .Add("desc", org);

            if (org.CreateBy > 0)
            {
                User createBy = User.Retrieve(session, org.CreateBy);
                if (createBy != null)
                    json.Add("createBy", string.IsNullOrEmpty(createBy.FullName) ? createBy.UserName : createBy.FullName);
                else
                    Log.Warn<Org>("User {0} (CreateBy) not found when loading org {1}:{2}", org.CreateBy, org.OrgId, org.OrgCode);
            }
            else
                json.Add("createBy", "");
            json.Add("createTime", org.CreateDate);

            if (org.ModifyBy > 0)
            {
                User modefyBy = User.Retrieve(session, org.ModifyBy);
                if (modefyBy != null)
                    json.Add("modifyBy", string.IsNullOrEmpty(modefyBy.FullName) ? modefyBy.UserName : modefyBy.FullName);
                else
                    Log.Warn<Org>("User {0} (ModifyBy) not found when loading org {1}:{2}", org.ModifyBy, org.OrgId, org.OrgCode);
            }
            else
                json.Add("modifyBy", "");
            json.Add("modifyTime", org.ModifyDate);

            User manager = null;
            if (org.Manager > 0)
            {
                manager = User.Retrieve(session, org.Manager);
                if (manager == null)
                    Log.Warn<Org>("User {0} (Manager) not found when loading org {1}:{2}", org.Manager, org.OrgId, org.OrgCode);
            }
            if (manager != null)
                json.Add("managerId", manager.UserId)
                    .Add("manager", string.IsNullOrEmpty(manager.FullName) ? manager.UserName : manager.FullName);
            else
                json.Add("managerId", -1)
                    .Add("manager", "");

            if (OrgTypeRegistry.HasExtAttr(org.OrgType) && org.ExtAttr != null)
            {
                Type type = OrgTypeRegistry.ExtAttrType(org.OrgType);
                org.ExtAttr.Json(session, json);
            }

            return json;
        }

        public static SimpleJson SaveOrg(ISession session, Org org, OrgType type, string action, User oper)
        {
            Org original = null;
            switch (action)
            {
                case "create":
                    Org parent = Org.Get(session, type, org.ParentId);
                    if (parent == null) return new SimpleJson().HandleError(string.Format("父节点{0}不存在", org.ParentId));

                    org.CreateBy = oper.UserId;
                    org.CreateDate = DateTime.Now;
                    org.IsVirtual = false;
                    org.IsRoot = false;
                    org.OrgType = type;
                    org.Deleted = false;
                    org.OrgSeq = org.OrgSeq <= 0 ? (short)(Org.MaxSeq(session, org.ParentId) + 1) : org.OrgSeq;
                    org.Create(session);

                    if (OrgTypeRegistry.HasExtAttr(type) && org.ExtAttr != null)
                    {
                        IOrgExtend oext = org.ExtAttr as IOrgExtend;
                        oext.OrgId = org.OrgId;
                        oext.Create(session);
                    }

                    parent.AddChild(org);
                    Org.SortOrg(parent, org);
                    return Org.GetOrgJSON(session, org);
                case "update":
                    original = Org.Get(session, type, org.OrgId);
                    if (original == null) return new SimpleJson().HandleError(string.Format("{0}不存在", org.OrgId));
                    original.OrgCode = org.OrgCode;
                    original.OrgName = org.OrgName;
                    original.Description = org.Description;
                    original.ModifyBy = oper.UserId;
                    original.ModifyDate = DateTime.Now;
                    original.OrgSeq = org.OrgSeq;
                    original.Manager = org.Manager;
                    original.Update(session, "OrgCode", "OrgName", "Description", "ModifyBy", "ModifyDate", "OrgSeq", "Manager");

                    if (OrgTypeRegistry.HasExtAttr(type) && org.ExtAttr != null)
                    {
                        IOrgExtend oext = org.ExtAttr as IOrgExtend;
                        oext.OrgId = org.OrgId;
                        oext.Update(session);
                        original.ExtAttr = oext;
                    }

                    Org.SortOrg(Org.Get(session, type, original.ParentId), original);
                    return Org.GetOrgJSON(session, original);
                default:
                    return new SimpleJson().HandleError(string.Format("无效的操作{0}", action));
            }
        }
        private static short MaxSeq(ISession session, int parentId)
        {
            object obj = session.CreateObjectQuery("select max(OrgSeq) from Org where ParentId=?")
                .Attach(typeof(Org)).SetValue(0, parentId, "ParentId")
                .Scalar();
            return Cast.Int16(obj, 0);
        }
        private static void SortOrg(Org parent, Org item)
        {
            if (parent == null || item == null || parent.Children == null || parent.Children.Count <= 1 || parent.Children.IndexOf(item) < 0) return;
            int itemIndex = parent.Children.IndexOf(item);
            int exchangeIndex = 0;
            for (int i = 0; i < parent.Children.Count; i++)
            {
                exchangeIndex = i;
                if (i == itemIndex) continue;
                if (item.OrgSeq <= parent.Children[i].OrgSeq) break;
            }
            if (itemIndex != exchangeIndex)
            {
                Org temp = parent.Children[exchangeIndex];
                parent.Children[exchangeIndex] = item;
                parent.Children[itemIndex] = temp;
            }
        }
        public static void DeleteOrg(ISession session, OrgType type, int id)
        {
            Org org = Org.Get(session, type, id);
            if (org == null) return;
            org.Deleted = true;
            org.Update(session, "Deleted");

            Org parent = Org.Get(session, type, org.ParentId);
            if (parent == null) return;
            parent.RemoveChild(org);
        }
        private static void LoadOrgDefaultSettings(ISession session)
        {
            IList<DictionaryItem> items = session.CreateEntityQuery<DictionaryItem>()
                .Where(Exp.In("ItemCode", ORG_USE_DEFAULT, ORG_DEFAULT_VALUE))
                .List<DictionaryItem>();
            if (items.Count == 2)
            {
                _useDefaultOrg = items[0].ItemCode == ORG_USE_DEFAULT ? items[0].BoolValue : items[1].BoolValue;
                _defaultValue = items[0].ItemCode == ORG_DEFAULT_VALUE ? Cast.Int(items[0].StringValue, -1) : Cast.Int(items[1].StringValue, -1);
            }
            else
                _useDefaultOrg = false;
        }
        public static bool UseDefaultOrg(ISession session)
        {
            if (!_defaultLoaded)
            {
                LoadOrgDefaultSettings(session);
                _defaultLoaded = true;
            }
            return _useDefaultOrg;
        }
        public static int DefaultOrg(ISession session)
        {
            if (!_defaultLoaded)
            {
                LoadOrgDefaultSettings(session);
                _defaultLoaded = true;
            }
            return _defaultValue;
        }
    }
}