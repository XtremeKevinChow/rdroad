//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-02-26 19:30:09
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
	///  区县
	///  区县数据
	/// </summary>
    [Table("SYS_DISTRICT")]
	public partial class  District : IEntity
	{
		#region Private Fields
		
		private int _districtId;
		private int _cityId;
		private string _zipCode;
		private string _name;
		private bool _door2Door;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 区县
		/// </summary>
		public District()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 区/县ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_District", IsPrimary = true, Name = "DST_ID")]
		public virtual int DistrictId
		{
			get { return this._districtId; }
			set { this._districtId = value; }
		}
		
		/// <summary>
		/// 城市ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="CITY_ID")]
		public virtual int CityId
		{
			get { return this._cityId; }
			set { this._cityId = value; }
		}
		
		/// <summary>
		/// 邮政编码
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=10, Nullable=true,Name="DST_ZIPCODE")]
		public virtual string ZipCode
		{
			get { return this._zipCode; }
			set { this._zipCode = value; }
		}
		
		/// <summary>
		/// 名称
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=50, Nullable=true,Name="DST_NAME")]
		public virtual string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}
		
		/// <summary>
		/// 支持送货上门
		/// </summary>
		[Column(DbType=StdDbType.Bool, Nullable=false,Name="DST_SHIP_TO")]
		public virtual bool Door2Door
		{
			get { return this._door2Door; }
			set { this._door2Door = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 区县 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 区县 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 区县 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 区县 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 区县 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static District Retrieve(ISession session  ,int districtId  )
		{
			return EntityManager.Retrieve<District>(session,districtId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int districtId  )
        {
           return EntityManager.Delete<District>(session,districtId);
        }
       
        /// <summary>
        ///  Map a DataRow to a District Entity.
        /// </summary>
        /// <returns></returns>
       public static District Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            District entity = new District();
                        
		    entity._districtId= Cast.Int(row["DST_ID"]);
		    entity._cityId= Cast.Int(row["CITY_ID"]);
		    entity._zipCode= Cast.String(row["DST_ZIPCODE"]);
		    entity._name= Cast.String(row["DST_NAME"]);
		    entity._door2Door= Cast.Bool(row["DST_SHIP_TO"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of District Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<District> Row2Entity(System.Data.DataTable dt)
       {
            IList<District> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<District>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    District  entity = Row2Entity(row);
                    if(entity != null)
                    {
                        list.Add(entity);
                    }
                 }
           }  
          return list; 
       }

       public static IList<District> Row2Entity(System.Data.DataRow[] rows)
       {
           IList<District> list = null;
           if (rows != null && rows.Length > 0)
           {
               list = new List<District>(rows.Length);
               foreach (System.Data.DataRow row in rows)
               {
                   District entity = Row2Entity(row);
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