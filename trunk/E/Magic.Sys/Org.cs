//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-01-25 14:29:50
// ** Modified   :
//*******************************************

using System;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;

namespace Magic.Sys
{

    /// <summary>
    ///  组织's Model
    /// </summary>
    [Table("SYS_ORG")]
    public partial class Org : IEntity
    {
        #region Class Constructor
        /// <summary>
        /// default constructor for 组织
        /// </summary>
        public Org()
        {
        }
        #endregion

        #region Public Properties
        /// <summary>
        /// 组织ID
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_ORG", IsPrimary = true, Name = "ORG_ID")]
        public virtual int OrgId
        {
            get;
            set;
        }

        /// <summary>
        /// 父节点ID
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, Name = "PARENT_ID")]
        public virtual int ParentId
        {
            get;
            set;
        }

        /// <summary>
        /// 组织代码
        /// </summary>
        [Column(DbType = StdDbType.AnsiString, Length = 15, Nullable = false, Name = "ORG_CODE")]
        public virtual string OrgCode
        {
            get;
            set;
        }

        /// <summary>
        /// 组织名称
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 20, Nullable = true, Name = "ORG_NAME")]
        public virtual string OrgName
        {
            get;
            set;
        }

        /// <summary>
        /// 类型
        /// </summary>
        [Column(DbType = StdDbType.Int16, Nullable = true, Name = "ORG_TYPE")]
        public virtual OrgType OrgType
        {
            get;
            set;
        }

        /// <summary>
        /// 是否虚拟节点
        /// </summary>
        [Column(DbType = StdDbType.Bool, Nullable = true, Name = "ORG_IS_VIRTUAL")]
        public virtual bool IsVirtual
        {
            get;
            set;
        }
        /// <summary>
        /// 是否根节点
        /// </summary>
        [Column(DbType = StdDbType.Bool, Nullable = true, Name = "ORG_IS_ROOT")]
        public virtual bool IsRoot
        {
            get;
            set;
        }

        /// <summary>
        /// 排列顺序
        /// </summary>
        [Column(DbType = StdDbType.Int16, Nullable = true, Name = "ORG_SEQ")]
        public short OrgSeq
        {
            get;
            set;
        }

        /// <summary>
        /// 删除标记
        /// </summary>
        [Column(DbType = StdDbType.Bool, Nullable = true, Name = "ORG_DEL_FLAG")]
        public bool Deleted
        {
            get;
            set;
        }

        /// <summary>
        /// 备注
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 50, Nullable = true, Name = "ORG_DESC")]
        public virtual string Description
        {
            get;
            set;
        }

        /// <summary>
        /// 负责人
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = true, Name = "ORG_MANAGER")]
        public virtual int Manager
        {
            get;
            set;
        }

        /// <summary>
        /// 创建日期
        /// </summary>
        [Column(DbType = StdDbType.DateTime, Nullable = true, Name = "CREATE_DATE")]
        public virtual DateTime CreateDate
        {
            get;
            set;
        }

        /// <summary>
        /// 创建人
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = true, Name = "CREATE_BY")]
        public virtual int CreateBy
        {
            get;
            set;
        }

        /// <summary>
        /// 更新日期
        /// </summary>
        [Column(DbType = StdDbType.DateTime, Nullable = true, Name = "MODIFY_DATE")]
        public virtual DateTime ModifyDate
        {
            get;
            set;
        }

        /// <summary>
        /// 更新人
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = true, Name = "MODIFY_BY")]
        public virtual int ModifyBy
        {
            get;
            set;
        }
        #endregion

        #region Public Methods
        /// <summary>
        /// Create new 组织 entity
        /// <returns></returns>
        /// </summary>
        public bool Create(ISession session)
        {
            return EntityManager.Create(session, this);
        }

        /// <summary>
        /// Update 组织 entity's whole properties
        /// <returns></returns>
        /// </summary>
        public bool Update(ISession session)
        {
            return EntityManager.Update(session, this);
        }

        /// <summary>
        ///Update 组织 entity's given properties
        /// <returns></returns>
        /// </summary>
        public bool Update(ISession session, params string[] propertyNames2Update)
        {
            return EntityManager.Update(session, this, propertyNames2Update);
        }

        /// <summary>
        ///Delete this 组织 entity
        /// <returns></returns>
        /// </summary>
        public bool Delete(ISession session)
        {
            return EntityManager.Delete(session, this);
        }

        /// <summary>
        ///Retrive a 组织 Entity from DB
        /// <returns></returns>
        /// </summary>
        public static Org Retrieve(ISession session, int orgId)
        {
            return EntityManager.Retrieve<Org>(session, orgId);
        }
        #endregion
    }
}