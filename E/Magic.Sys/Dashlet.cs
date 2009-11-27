//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-05-28 09:11:30
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
	
	/// <summary>
	///  Dashlet
	/// </summary>
    [Table("SYS_DASHLET")]
	public partial class  Dashlet : IEntity
	{
		#region Private Fields
		
		private int _dashletId;
		private string _category;
		private string _title;
		private string _link;
		private string _icon;
		private string _description;
		private string _instanceMethod;
		private string _instanceParameter;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for Dashlet
		/// </summary>
		public Dashlet()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// Dashlet ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_Dashlet", IsPrimary = true, Name = "DL_ID")]
		public virtual int DashletId
		{
			get { return this._dashletId; }
			set { this._dashletId = value; }
		}
		
		/// <summary>
		/// 分类
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=true,Name="DL_CAT")]
		public virtual string Category
		{
			get { return this._category; }
			set { this._category = value; }
		}
		
		/// <summary>
		/// 标题
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=false,Name="DL_TITLE")]
		public virtual string Title
		{
			get { return this._title; }
			set { this._title = value; }
		}
		
		/// <summary>
		/// 链接
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=255, Nullable=true,Name="DL_URL")]
		public virtual string Link
		{
			get { return this._link; }
			set { this._link = value; }
		}
		
		/// <summary>
		/// 图标
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=50, Nullable=true,Name="DL_ICON")]
		public virtual string Icon
		{
			get { return this._icon; }
			set { this._icon = value; }
		}
		
		/// <summary>
		/// 描述
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="DL_DESC")]
		public virtual string Description
		{
			get { return this._description; }
			set { this._description = value; }
		}
		
		/// <summary>
		/// 创建方式
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=false,Name="DL_INST_TYPE")]
		public virtual string InstanceMethod
		{
			get { return this._instanceMethod; }
			set { this._instanceMethod = value; }
		}
		
		/// <summary>
		/// 创建参数
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=1000, Nullable=true,Name="DL_PARAM")]
		public virtual string InstanceParameter
		{
			get { return this._instanceParameter; }
			set { this._instanceParameter = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new Dashlet entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update Dashlet entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update Dashlet entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this Dashlet entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a Dashlet Entity from DB
		/// <returns></returns>
		/// </summary>
		public static Dashlet Retrieve(ISession session  ,int dashletId  )
		{
			return EntityManager.Retrieve<Dashlet>(session,dashletId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int dashletId  )
        {
           return EntityManager.Delete<Dashlet>(session,dashletId);
        }
       
         /// <summary>
        /// 是否存在指定条件的Dashlet
        /// </summary>
        /// <param name="session"></param>
        /// <param name="propertyNames"></param>
        /// <param name="propertyValues"></param>
        /// <returns></returns>
        public static bool Exists(ISession session, string[] propertyNames, object[] propertyValues)
        {
            if (propertyNames != null && propertyValues != null && propertyNames.Length>0 && propertyValues.Length == propertyNames.Length)
            {
                EntityQuery qry = session.CreateEntityQuery<Dashlet>();
                for (int i = 0; i < propertyNames.Length; i++)
                {
                    qry.And(Exp.Eq(propertyNames[i], propertyValues[i]));
                }
                return qry.Count() > 0;
                    
            }
            return false;
        } 
       
        /// <summary>
        ///  Map a DataRow to a Dashlet Entity.
        /// </summary>
        /// <returns></returns>
       public static Dashlet Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            Dashlet entity = new Dashlet();
                        
		    entity._dashletId= Cast.Int(row["DL_ID"]);
		    entity._category= Cast.String(row["DL_CAT"]);
		    entity._title= Cast.String(row["DL_TITLE"]);
		    entity._link= Cast.String(row["DL_URL"]);
		    entity._icon= Cast.String(row["DL_ICON"]);
		    entity._description= Cast.String(row["DL_DESC"]);
		    entity._instanceMethod= Cast.String(row["DL_INST_TYPE"]);
		    entity._instanceParameter= Cast.String(row["DL_PARAM"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of Dashlet Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<Dashlet> Row2Entity(System.Data.DataTable dt)
       {
            IList<Dashlet> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<Dashlet>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    Dashlet  entity = Row2Entity(row);
                    if(entity != null)
                    {
                        list.Add(entity);
                    }
                 }
           }  
          return list; 
       }
		#endregion

	}
}