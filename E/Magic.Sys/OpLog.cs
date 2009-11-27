//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-01-25 21:53:33
// ** Modified   :
//*******************************************

using System;
using System.Collections.Generic;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;

namespace Magic.Sys
{
	/// <summary>
	/// 重要性, derive from int.
	/// </summary>
	public enum Priority:int
	{
	}
	
	
	/// <summary>
	///  系统日志's Model
	///  系统业务操作日志，记录系统用户在何时何IP对何对象作了何内容的更改。
	/// </summary>
    [Table("SYS_OP_LOG")]
	public partial class  OpLog
	{
		#region Private Fields
		
		private int _logId;
		private string _opObjectType;
		private string _opContent;
		private DateTime _opTime;
		private string _opObjectId;
		private short _operatorType;
		private string _operatorName;
		private string _iP;
		private int _operatorId;
		private string _opType;
		private Priority _logPriority;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 系统日志
		/// </summary>
		public OpLog()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 日志ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_OP_LOG", IsPrimary = true, Name = "LOG_ID")]
		public virtual int LogId
		{
			get { return this._logId; }
			set { this._logId = value; }
		}
		
		/// <summary>
		/// 操作对象类型
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=30, Nullable=true,Name="LOG_TARGET_TYPE")]
		public virtual string OpObjectType
		{
			get { return this._opObjectType; }
			set { this._opObjectType = value; }
		}
		
		/// <summary>
		/// 操作内容
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="LOG_DESC")]
		public virtual string OpContent
		{
			get { return this._opContent; }
			set { this._opContent = value; }
		}
		
		/// <summary>
		/// 操作时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="LOG_TIME")]
		public virtual DateTime OpTime
		{
			get { return this._opTime; }
			set { this._opTime = value; }
		}
		
		/// <summary>
		/// 操作对象标识
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=40, Nullable=true,Name="LOG_TARGET_ID")]
		public virtual string OpObjectId
		{
			get { return this._opObjectId; }
			set { this._opObjectId = value; }
		}
		
		/// <summary>
		/// 操作者类型
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="LOG_USR_TYPE")]
		public virtual short OperatorType
		{
			get { return this._operatorType; }
			set { this._operatorType = value; }
		}
		
		/// <summary>
		/// 操作者姓名
		/// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 40, Nullable = true, Name = "LOG_USR_NAME")]
		public virtual string OperatorName
		{
			get { return this._operatorName; }
			set { this._operatorName = value; }
		}
		
		/// <summary>
		/// 机器IP
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=15, Nullable=true,Name="LOG_USR_IP")]
		public virtual string IP
		{
			get { return this._iP; }
			set { this._iP = value; }
		}
		
		/// <summary>
		/// 操作者ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="LOG_USR_ID")]
		public virtual int OperatorId
		{
			get { return this._operatorId; }
			set { this._operatorId = value; }
		}
		
		/// <summary>
		/// 操作类型
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=true,Name="LOG_OPT_TYPE")]
		public virtual string OpType
		{
			get { return this._opType; }
			set { this._opType = value; }
		}
		
		/// <summary>
		/// 重要性
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="LOG_PRI")]
		public virtual Priority LogPriority
		{
			get { return this._logPriority; }
			set { this._logPriority = value; }
		}
		
		#endregion

		#region Public Methods
		/// <summary>
		/// Create new 系统日志 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 系统日志 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 系统日志 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 系统日志 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}

		/// <summary>
		///Retrive a 系统日志 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static OpLog Retrieve(ISession session  ,int logId  )
		{
			return EntityManager.Retrieve<OpLog>(session,logId);
		}

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="session"></param>
        /// <param name="logId"></param>
        /// <returns></returns>
        public static bool Delete(ISession session, int logId)
        {
            return EntityManager.Delete(session, typeof(OpLog), logId);
        }

		#endregion

	}
}