//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-02-26 19:30:05
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
	///  城市
	/// </summary>
    [Table("SYS_CITY")]
	public partial class  City : IEntity
	{
		#region Private Fields
		
		private int _cityId;
		private string _cityCode;
		private string _name;
		private int _provinceId;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 城市
		/// </summary>
		public City()
		{
		}
		#endregion

		#region Public Properties		
		/// <summary>
		/// 城市ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_City", IsPrimary = true, Name = "CITY_ID")]
		public virtual int CityId
		{
			get { return this._cityId; }
			set { this._cityId = value; }
		}
		
		/// <summary>
		/// 城市代码
		/// </summary>
        [Column(DbType = StdDbType.AnsiString, Length = 10, Nullable = true, Name = "CITY_CODE")]
		public virtual string CityCode
		{
			get { return this._cityCode; }
			set { this._cityCode = value; }
		}
		
		/// <summary>
		/// 城市名称
		/// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 50, Nullable = true, Name = "CITY_NAME")]
		public virtual string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}
		
		/// <summary>
		/// 省市ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, Name = "PRV_ID")]
		public virtual int ProvinceId
		{
			get { return this._provinceId; }
			set { this._provinceId = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 城市 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 城市 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 城市 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 城市 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 城市 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static City Retrieve(ISession session  ,int cityId  )
		{
			return EntityManager.Retrieve<City>(session,cityId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int cityId  )
        {
           return EntityManager.Delete<City>(session,cityId);
        }
       
        /// <summary>
        ///  Map a DataRow to a City Entity.
        /// </summary>
        /// <returns></returns>
       public static City Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            City entity = new City();

            entity._cityId = Cast.Int(row["CITY_ID"]);
            entity._cityCode = Cast.String(row["CITY_CODE"]);
            entity._name = Cast.String(row["CITY_NAME"]);
            entity._provinceId = Cast.Int(row["PRV_ID"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of City Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<City> Row2Entity(System.Data.DataTable dt)
       {
            IList<City> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<City>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    City  entity = Row2Entity(row);
                    if(entity != null)
                    {
                        list.Add(entity);
                    }
                 }
           }  
          return list; 
       }

       public static IList<City> Row2Entity(System.Data.DataRow[] rows)
       {
           IList<City> list = null;
           if (rows != null && rows.Length > 0)
           {
               list = new List<City>(rows.Length);
               foreach (System.Data.DataRow row in rows)
               {
                   City entity = Row2Entity(row);
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