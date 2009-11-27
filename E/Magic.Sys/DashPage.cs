//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-05-28 10:04:01
// ** Modified   :
//*******************************************

using System;
using System.Collections.Generic;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Security;

namespace Magic.Sys
{

    public enum DashPageLayout
    {
        OneColumn = 1,
        TwoColumn = 2,
        ThreeColumn = 3
    }

    /// <summary>
    /// 
    /// </summary>
    public enum DashPageType
    {
        System = 0,
        Org = 1,
        Role = 2,
        Custom = 3
    }

    public enum DashStatus
    {
        Deactive = 0,
        Active = 1
    }
	
	/// <summary>
	///  DashPage
	/// </summary>
    [Table("SYS_DASHPAGE")]
	public partial class  DashPage : IEntity
	{
		#region Private Fields
		
		private int _dashpageId;
		private string _title;
		private DashPageLayout _layout;
		private DashPageType _type;
		private string _helpLink;
		private string _description;
		private DashStatus _status;
		private DateTime _createTime;
		private int _createBy;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for DashPage
		/// </summary>
		public DashPage()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// DashPage ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_DashPage", IsPrimary = true, Name = "DP_ID")]
		public virtual int DashpageId
		{
			get { return this._dashpageId; }
			set { this._dashpageId = value; }
		}
		
		/// <summary>
		/// 标题
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=false,Name="DP_TITLE")]
		public virtual string Title
		{
			get { return this._title; }
			set { this._title = value; }
		}
		
		/// <summary>
		/// 布局
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="DP_LAYOUT")]
		public virtual DashPageLayout Layout
		{
			get { return this._layout; }
			set { this._layout = value; }
		}
		
		/// <summary>
		/// 类型
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="DP_TYPE")]
		public virtual DashPageType Type
		{
			get { return this._type; }
			set { this._type = value; }
		}
		
		/// <summary>
		/// 帮助链接
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=255, Nullable=true,Name="DP_HELP_URL")]
		public virtual string HelpLink
		{
			get { return this._helpLink; }
			set { this._helpLink = value; }
		}
		
		/// <summary>
		/// 描述
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="DP_DESC")]
		public virtual string Description
		{
			get { return this._description; }
			set { this._description = value; }
		}
		
		/// <summary>
		/// 状态
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="DP_STATUS")]
		public virtual DashStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}
		
		/// <summary>
		/// 创建时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="CREATE_TIME")]
		public virtual DateTime CreateTime
		{
			get { return this._createTime; }
			set { this._createTime = value; }
		}
		
		/// <summary>
		/// 创建人
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="CREATE_BY")]
		public virtual int CreateBy
		{
			get { return this._createBy; }
			set { this._createBy = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new DashPage entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update DashPage entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update DashPage entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this DashPage entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a DashPage Entity from DB
		/// <returns></returns>
		/// </summary>
		public static DashPage Retrieve(ISession session  ,int dashpageId  )
		{
			return EntityManager.Retrieve<DashPage>(session,dashpageId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int dashpageId  )
        {
           return EntityManager.Delete<DashPage>(session,dashpageId);
        }
       
         /// <summary>
        /// 是否存在指定条件的DashPage
        /// </summary>
        /// <param name="session"></param>
        /// <param name="propertyNames"></param>
        /// <param name="propertyValues"></param>
        /// <returns></returns>
        public static bool Exists(ISession session, string[] propertyNames, object[] propertyValues)
        {
            if (propertyNames != null && propertyValues != null && propertyNames.Length>0 && propertyValues.Length == propertyNames.Length)
            {
                EntityQuery qry = session.CreateEntityQuery<DashPage>();
                for (int i = 0; i < propertyNames.Length; i++)
                {
                    qry.And(Exp.Eq(propertyNames[i], propertyValues[i]));
                }
                return qry.Count() > 0;
                    
            }
            return false;
        } 
       
        /// <summary>
        ///  Map a DataRow to a DashPage Entity.
        /// </summary>
        /// <returns></returns>
       public static DashPage Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            DashPage entity = new DashPage();
                        
		    entity._dashpageId= Cast.Int(row["DP_ID"]);
		    entity._title= Cast.String(row["DP_TITLE"]);
		    entity._layout= Cast.Enum<DashPageLayout>(row["DP_LAYOUT"]);
		    entity._type= Cast.Enum<DashPageType>(row["DP_TYPE"]);
		    entity._helpLink= Cast.String(row["DP_HELP_URL"]);
		    entity._description= Cast.String(row["DP_DESC"]);
		    entity._status= Cast.Enum<DashStatus>(row["DP_STATUS"]);
		    entity._createTime= Cast.DateTime(row["CREATE_TIME"]);
		    entity._createBy= Cast.Int(row["CREATE_BY"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of DashPage Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<DashPage> Row2Entity(System.Data.DataTable dt)
       {
            IList<DashPage> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<DashPage>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    DashPage  entity = Row2Entity(row);
                    if(entity != null)
                    {
                        list.Add(entity);
                    }
                 }
           }  
          return list; 
       }
		#endregion

        #region Public 
        /// <summary>
        /// 查询某个用户某个DashPage下所有的Dashlet
        /// </summary>
        /// <param name="session"></param>
        /// <param name="dashpageId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
       public static IList<Dashlet> FindDashletByUserAndPage(ISession session, int userId, int dashpageId)
       {
           string oql = @"Select Dashlet  from Dashlet l, UserDashlet ul where l.DashletId=ul.DashletId and ul.DashpageId=? and ul.UserId=?";
          return session.CreateObjectQuery(oql)
               .Attach(typeof(Dashlet))
               .Attach(typeof(UserDashlet))
               .SetValue(0, dashpageId, DbTypeInfo.Int32())
               .SetValue(1, userId, DbTypeInfo.Int32()).List<Dashlet>();
            
       }
        /// <summary>
        /// 查询某个用户的DashPage
        /// </summary>
        /// <param name="session"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
       public static IList<DashPage> FindDashPageByUser(ISession session, int userId)
       {
           string oql = @"Select DashPage from DashPage p, UserDashpage up where p.DashpageId=up.DashpageId and up.UserId=?";
           return session.CreateObjectQuery(oql)
               .Attach(typeof(DashPage))
               .Attach(typeof(UserDashpage))
               .SetValue(0, userId, DbTypeInfo.Int32())
               .List<DashPage>();
       }
        
        #endregion
    }
}