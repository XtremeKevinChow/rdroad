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
    [Table("S_DELIVERY_TYPE")]
	public partial class  DeliverType : IEntity
	{
		#region Private Fields
		private int _id;
		private string _name;
		private string _description;
		private decimal _deliverFee;
        private decimal _goldDeliverFee;
        private decimal _platinaDeliverFee;
		#endregion

		#region Public Properties
		[Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int ID
		{
			get { return this._id; }
			set { this._id = value; }
		}

		///<summary>
		/// 送货方式名称
		///</summary>
		[Column(Name = "NAME", DbType = StdDbType.AnsiString, Length = 20)]
		public string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}

		///<summary>
		/// 送货方式描述
		///</summary>
		[Column(Name = "DESCRIPTION", DbType = StdDbType.AnsiString, Length = 200)]
		public string Description
		{
			get { return this._description; }
			set { this._description = value; }
		}

		///<summary>
		/// 发送费
		///</summary>
		[Column(Name = "DELIVERY_FEE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
		public decimal DeliverFee
		{
			get { return this._deliverFee; }
			set { this._deliverFee = value; }
		}

		///<summary>
		/// 金卡会员发送费
		///</summary>
		[Column(Name = "GOLD_DELIVERY_FEE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
		public decimal GoldDeliverFee
		{
			get { return this._goldDeliverFee; }
			set { this._goldDeliverFee = value; }
		}

		///<summary>
		/// 白金卡会员发送费
		///</summary>
		[Column(Name = "PLATINA_DELIVERY_FEE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
		public decimal PlatinaDeliverFee
		{
			get { return this._platinaDeliverFee; }
			set { this._platinaDeliverFee = value; }
		}
		#endregion

		#region Entity Methods
        public DeliverType()
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
        public static DeliverType Retrieve(ISession session, int id)
		{
            return EntityManager.Retrieve<DeliverType>(session, id);
		}
		public static bool Delete(ISession session, int id)
		{
            return EntityManager.Delete<DeliverType>(session, id);
		}
		#endregion
	}
}
