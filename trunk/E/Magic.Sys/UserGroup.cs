//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-02-17 20:20:37
// ** Modified   :
//*******************************************

using System;
using System.Collections.Generic;
using Magic.Framework;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Security;

namespace Magic.Sys
{

    /// <summary>
    ///  用户组's Model
    /// </summary>
    [Table("SYS_USERGROUP")]
    public partial class UserGroup:IUserGroup,IEntity
    {
        #region Static Members
        private static UserGroup _root = null;
        private static int _ROOT_ID = 0;
        
        public static UserGroup Root
        {
            get
            {
                if (_root == null)
                {
                    _root = new UserGroup();
                    _root._name = "SysGroup";
                    _root._parent = null;
                    _root._parentId = -1;
                    _root._groupId = _ROOT_ID;
                    _root._groupLevel = -1;
                    _root._groupType = UserGroupType.System;
                    _root._description = "系统用户组";
                }
                return _root;
            }
        }
        #endregion

        #region Private Fields

        private int _groupId;
        private int _parentId;
        private string _name;
        private string _description;
        private UserGroupType _groupType;
        private short _groupLevel;
        private DateTime _createTime;
        private DateTime _modifyTime;
        private int _createBy;
        private int _modifyBy;
        private UserGroup _parent = null;
        private IList<UserGroup> _children = null;
        #endregion

        #region Class Constructor
        /// <summary>
        /// default constructor for 用户组
        /// </summary>
        public UserGroup()
        {
        }
        #endregion

        #region Public Properties

        /// <summary>
        /// 用户组ID
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_USERGROUP", IsPrimary = true, Name = "UGP_ID")]
        public virtual int GroupId
        {
            get { return this._groupId; }
            set { this._groupId = value; }
        }

        /// <summary>
        /// 父用户组
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, Name = "PARENT_ID")]
        public virtual int ParentId
        {
            get { return this._parentId; }
            set { this._parentId = value; }
        }

        /// <summary>
        /// 名称
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 50, Nullable = true, Name = "UGP_NAME")]
        public virtual string Name
        {
            get { return this._name; }
            set { this._name = value; }
        }

        /// <summary>
        /// 描述
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 250, Nullable = true, Name = "UGP_DESC")]
        public virtual string Description
        {
            get { return this._description; }
            set { this._description = value; }
        }

        /// <summary>
        /// 类型
        /// </summary>
        [Column(DbType = StdDbType.Int16, Nullable = false, Name = "UGP_TYPE")]
        public virtual UserGroupType GroupType
        {
            get { return this._groupType; }
            set { this._groupType = value; }
        }

        /// <summary>
        /// 层级
        /// </summary>
        [Column(DbType = StdDbType.Int16, Nullable = true, Name = "UGP_LEVEL")]
        public virtual short GroupLevel
        {
            get { return this._groupLevel; }
            set { this._groupLevel = value; }
        }

        /// <summary>
        /// 创建时间
        /// </summary>
        [Column(DbType = StdDbType.DateTime, Nullable = true, Name = "CREATE_TIME")]
        public virtual DateTime CreateTime
        {
            get { return this._createTime; }
            set { this._createTime = value; }
        }

        /// <summary>
        /// 更新时间
        /// </summary>
        [Column(DbType = StdDbType.DateTime, Nullable = false, Name = "MODIFY_TIME")]
        public virtual DateTime ModifyTime
        {
            get { return this._modifyTime; }
            set { this._modifyTime = value; }
        }

        /// <summary>
        /// 创建人
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, Name = "CREATE_BY")]
        public virtual int CreateBy
        {
            get { return this._createBy; }
            set { this._createBy = value; }
        }

        /// <summary>
        /// 最近更新人
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, Name = "MODIFY_BY")]
        public virtual int ModifyBy
        {
            get { return this._modifyBy; }
            set { this._modifyBy = value; }
        }


        /// <summary>
        /// 父用户组
        /// </summary>
        public IUserGroup ParentGroup
        {
            get
            {
                if (_parent == null)
                {
                    using (ISession session = new Session())
                    {
                        _parent = UserGroup.Retrieve(session, _parentId);
                    }
                    if (_parent == null)
                    {
                        _parent = Root;
                    }
                }
                return _parent;
            }

        }
     
        #endregion

        #region Methods
        /// <summary>
        /// 是否是根节点
        /// </summary>
        public bool IsRoot
        {
            get
            {
                return (this._groupId == _ROOT_ID);
            }
        }

        public override string ToString()
        {
            return string.Format("{0}({1})", this._name,this._description);
        }

        public IList<UserGroup> Children
        {
            get
            {
                return _children;
            }
        }

        /// <summary>
        /// 载入当前节点的子树
        /// </summary>
        /// <param name="session"></param>
        public void LoadSubTree(ISession session)
        {
            GetChidren(session);
            if (this._children != null && this._children.Count > 0)
            {
                foreach (UserGroup child in this._children)
                {
                    child.LoadSubTree(session);
                }
            }
        }

        private void GetChidren(ISession session)
        {
            EntityQuery qry = session.CreateEntityQuery<UserGroup>();
            qry.And(Exp.Eq("ParentId", this.GroupId));
            this._children = qry.List<UserGroup>();
        }

        public static bool HasChildren(ISession session, int groupId)
        {
            EntityQuery qry = session.CreateEntityQuery<UserGroup>();
            qry.And(Exp.Eq("ParentId", groupId));
            return qry.Count() > 0;
        }

        public SimpleJson ToJSON()
        {
            SimpleJson json = new SimpleJson();
            json.Add("id", this.GroupId);
            json.Add("name", this.Name);
            json.Add("desc", this.Description);
            json.Add("parent", this.ParentId);
            json.Add("type", this.GroupType);
            return json;
        }
        #endregion
               

        #region IEntity Members

        bool IEntity.Create(ISession session)
        {
            throw new NotImplementedException();
        }

        bool IEntity.Delete(ISession session)
        {
            throw new NotImplementedException();
        }

        bool IEntity.Update(ISession session, params string[] propertyNames2Update)
        {
            throw new NotImplementedException();
        }

        bool IEntity.Update(ISession session)
        {
            throw new NotImplementedException();
        }

        #endregion
    }
}