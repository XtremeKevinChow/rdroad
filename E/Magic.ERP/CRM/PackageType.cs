//*******************************************
// ** Description:  Data Access Object for DeliverType
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.ERP.CRM
{
    using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
    /// Data Access Object for DeliverType
	/// 送货方式
	/// </summary>
    [Table("PACKAGE_TYPE")]
	public partial class  PackageType : IEntity
	{
		#region Private Fields
		private int _id;
		private string _name;
		private string _description;
        private string _image;
		private decimal _price;
		#endregion

		#region Public Properties
		[Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int ID
		{
			get { return this._id; }
			set { this._id = value; }
		}

		///<summary>
		/// 包装方式名称
		///</summary>
		[Column(Name = "NAME", DbType = StdDbType.AnsiString, Length = 50)]
		public string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}

		///<summary>
		/// 包装方式描述
		///</summary>
		[Column(Name = "DESCRIPTION", DbType = StdDbType.AnsiString, Length = 500)]
		public string Description
		{
			get { return this._description; }
			set { this._description = value; }
		}

		///<summary>
		/// 发送费
		///</summary>
		[Column(Name = "PRICE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
		public decimal Price
		{
			get { return this._price; }
			set { this._price = value; }
		}
        
		///<summary>
		/// 包装方式图片
		///</summary>
		[Column(Name = "IMAGE", DbType = StdDbType.AnsiString, Length = 100)]
		public string Image
		{
			get { return this._image;}
			set { this._image = value; }
		}
		#endregion

		#region Entity Methods
        public PackageType()
		{
		}

		public bool Create(ISession session)
		{
			return EntityManager.Create(session, this);
		}
		public bool Update(ISession session)
		{
			return EntityManager.Update(session, this);
		}
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session, this, propertyNames2Update);
		}
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session, this);
		}
        public static PackageType Retrieve(ISession session, int id)
		{
            return EntityManager.Retrieve<PackageType>(session, id);
		}
		public static bool Delete(ISession session, int id)
		{
            return EntityManager.Delete<PackageType>(session, id);
		}
		#endregion
	}
}
