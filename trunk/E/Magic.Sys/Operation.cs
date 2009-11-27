//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-02-25 10:08:11
// ** Modified   :
//*******************************************

using System;
using System.Collections.Generic;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Framework.Data;
using Magic.Framework;
using Magic.Framework.Utils;
using Magic.Framework.Debug;
using Magic.Security;

namespace Magic.Sys
{
    public enum OperationStatus
    {
        Deactive =0,
        Active = 1
    }
	/// <summary>
	///  权限操作
	/// </summary>
    [Table("SYS_OPERATION")]
	public partial class  Operation : IOperation, IEntity
	{
        public const int ROOT_ID = 0;
		#region Private Fields
		
		private int _operationId;
		private int _parentId;
		private string _name;
		private OperationType _type;
		private string _description;
        private string _entry;
        private OperationStatus _status;
        private int _level;
        private string _image;
        private int _seqNo;

        private IList<Operation> _children = null;
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 权限操作
		/// </summary>
		public Operation()
		{
		}

        internal Operation(int id, string name, string desc, OperationType type)
        {
            _operationId = id;
            _name = name;
            _description = desc;
            _type = type;

        }
		#endregion

		#region Generated Public Properties
		
		/// <summary>
		/// 操作ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_OPERATION", IsPrimary = true, Name = "OPT_ID")]
		public virtual int OperationId
		{
			get { return this._operationId; }
			set { this._operationId = value; }
		}
		
		/// <summary>
		/// 父操作
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="PARENT_ID")]
		public virtual int ParentId
		{
			get { return this._parentId; }
			set { this._parentId = value; }
		}
		
		/// <summary>
		/// 名称
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=50, Nullable=false,Name="OPT_NAME")]
		public virtual string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}
		
		/// <summary>
		/// 操作类型
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="OPT_TYPE")]
		public virtual OperationType Type
		{
			get { return this._type; }
			set { this._type = value; }
		}

        [Column(DbType = StdDbType.Int16, Nullable = true, Name = "OPT_STATUS")]
        public virtual OperationStatus Status
        {
            get { return this._status; }
            set { this._status = value; }
        }

        /// <summary>
        /// 排序
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = true, Name = "OPT_SEQNUM")]
        public virtual int SeqNo
        {
            get { return this._seqNo; }
            set { this._seqNo = value; }
        }


        /// <summary>
        /// 图片
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 255, Nullable = true, Name = "OPT_IMAGE")]
        public virtual string Image
        {
            get { return this._image; }
            set { this._image = value; }
        }

        /// <summary>
        /// 层级
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = true, Name = "OPT_LEVEL")]
        public virtual int Level
        {
            get { return this._level; }
            set { this._level = value; }
        }

        /// <summary>
        /// 入口
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 250, Nullable = true, Name = "OPT_ENTRY")]
        public string Entry
        {
            get
            {
                return _entry;
            }
            set
            {
                _entry = value;
            }
        }
		
		/// <summary>
		/// 描述
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="OPT_DESC")]
		public virtual string Description
		{
			get { return this._description; }
			set { this._description = value; }
		}

      
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 权限操作 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 权限操作 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 权限操作 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 权限操作 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members    

        public static Operation GetRoot(ISession session)
        {
            return Retrieve(session, ROOT_ID);
        }
		/// <summary>
		///Retrive a 权限操作 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static Operation Retrieve(ISession session  ,int operationId  )
		{
			return EntityManager.Retrieve<Operation>(session,operationId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int operationId  )
        {
           return EntityManager.Delete<Operation>(session,operationId);
        }
       
        /// <summary>
        ///  Map a DataRow to a Operation Entity.
        /// </summary>
        /// <returns></returns>
       public static Operation Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            Operation entity = new Operation();
                        
		    if(!row.IsNull("OPT_ID"))
		    	    entity._operationId= (int)(row["OPT_ID"]);
		    if(!row.IsNull("PARENT_ID"))
		    	    entity._parentId= (int)(row["PARENT_ID"]);
		    if(!row.IsNull("OPT_NAME"))
		    	    entity._name= (string)(row["OPT_NAME"]);
		    if(!row.IsNull("OPT_TYPE"))
		    	    entity._type= (OperationType)Enum.Parse(typeof(OperationType),(row["OPT_TYPE"]).ToString());
		    if(!row.IsNull("OPT_DESC"))
		    	    entity._description= (string)(row["OPT_DESC"]);
            if (!row.IsNull("OPT_ENTRY"))
                entity._entry = Cast.String(row["OPT_ENTRY"]);
            entity._seqNo = Cast.Int(row["OPT_SEQNUM"]);
            entity._image = Cast.String(row["OPT_IMAGE"]);
            entity._level = Cast.Int(row["OPT_LEVEL"]);
		   
		   return entity;
       }
		#endregion

        #region Public Methods
       /// <summary>
       /// 是否是根节点
       /// </summary>
       public bool IsRoot
       {
           get
           {
               return (this._operationId == ROOT_ID);
           }
       }

       public override string ToString()
       {
           return Description;
       }

       public IList<Operation> Children
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
                foreach (Operation child in this._children)
                {
                    child.LoadSubTree(session);
                }
            }
        }

        private void GetChidren(ISession session)
        {
            EntityQuery qry = session.CreateEntityQuery<Operation>();
            qry.And(Exp.Eq("ParentId", this.OperationId));
            qry.OrderBy(new OrderBy("SeqNo", Order.Asc));
            this._children = qry.List<Operation>();
        }

        public static bool HasChildren(ISession session, int operationId)
        {
            EntityQuery qry = session.CreateEntityQuery<Operation>();
            qry.And(Exp.Eq("ParentId", operationId));
            return qry.Count() > 0;
        }

        public SimpleJson ToJSON()
        {
            SimpleJson json = new SimpleJson();
            json.Add("id", this.OperationId);
            json.Add("name", this.Name);
            json.Add("desc", this.Description);
            json.Add("parent", this.ParentId);
            json.Add("type", this.Type.ToString());
            json.Add("level", this.Level);
            json.Add("image", this.Image);
            json.Add("seq", this.SeqNo);
            json.Add("entry", this.Entry);
            json.Add("status", this.Status);
            return json;
        }

        public static Operation Retrieve(ISession session, string operationName)
        {
            EntityQuery qry = session.CreateEntityQuery<Operation>();
            qry.And(Exp.Eq("OPT_NAME", operationName));
            IList<Operation> list = qry.List<Operation>();
            if (list != null && list.Count > 0)
                return list[0];
            return null;
        }
        #endregion


        #region IOperation Members

     

        #endregion
    }
}