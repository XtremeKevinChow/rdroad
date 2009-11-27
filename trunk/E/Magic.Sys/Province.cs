//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-02-26 19:30:01
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
	///  省市
	/// </summary>
    [Table("SYS_PROVINCE")]
	public partial class  Province : IEntity
	{
		#region Private Fields
		
		private int _provinceId;
		private string _name;
		private string _code;
		private string _alias;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 省市
		/// </summary>
		public Province()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 省市ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_PROVINCE", IsPrimary = true, Name = "PRV_ID")]
		public virtual int ProvinceId
		{
			get { return this._provinceId; }
			set { this._provinceId = value; }
		}
		
		/// <summary>
		/// 名称
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=false,Name="PRV_NAME")]
		public virtual string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}
		
		/// <summary>
		/// 省市代码
		/// </summary>
		[Column(DbType=StdDbType.AnsiString, Length=10, Nullable=false,Name="PRV_CODE")]
		public virtual string Code
		{
			get { return this._code; }
			set { this._code = value; }
		}
		
		/// <summary>
		/// 简称/别名
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=false,Name="PRV_ALIAS")]
		public virtual string Alias
		{
			get { return this._alias; }
			set { this._alias = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 省市 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 省市 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 省市 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 省市 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 省市 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static Province Retrieve(ISession session  ,int provinceId  )
		{
			return EntityManager.Retrieve<Province>(session,provinceId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int provinceId  )
        {
           return EntityManager.Delete<Province>(session,provinceId);
        }
       
        /// <summary>
        ///  Map a DataRow to a Province Entity.
        /// </summary>
        /// <returns></returns>
       public static Province Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            Province entity = new Province();
                        
		    entity._provinceId= Cast.Int(row["PRV_ID"]);
		    entity._name= Cast.String(row["PRV_NAME"]);
		    entity._code= Cast.String(row["PRV_CODE"]);
		    entity._alias= Cast.String(row["PRV_ALIAS"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of Province Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<Province> Row2Entity(System.Data.DataTable dt)
       {
            IList<Province> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<Province>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    Province  entity = Row2Entity(row);
                    if(entity != null)
                    {
                        list.Add(entity);
                    }
                 }
           }  
          return list; 
       }

       public static IList<Province> Row2Entity(System.Data.DataRow[] rows)
       {
           IList<Province> list = null;
           if (rows != null && rows.Length > 0)
           {
               list = new List<Province>(rows.Length);
               foreach (System.Data.DataRow row in rows)
               {
                   Province entity = Row2Entity(row);
                   if (entity != null)
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