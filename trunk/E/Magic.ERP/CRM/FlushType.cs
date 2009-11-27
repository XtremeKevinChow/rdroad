namespace Magic.ERP.CRM
{
    using System.Collections.Generic;
    using Magic.Framework.Data;
	using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for FlushType
	/// 冲值方式
	/// </summary>
    [Table("MAGIC.S_FLUSHBALANCE_SOURCE")]
	public partial class  FlushType : IEntity
	{
		#region Private Fields
		private int _id;
		private string _name;
		private bool _enabled;
		#endregion

		#region Public Properties
		[Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int ID
		{
			get { return this._id; }
			set { this._id = value; }
		}

		///<summary>
		/// 名称描述
		///</summary>
		[Column(Name = "NAME", DbType = StdDbType.AnsiString, Length = 20)]
		public string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}

		///<summary>
		/// 是否有效
		///</summary>
		[Column(Name = "IS_USED", DbType = StdDbType.Bool)]
		public bool Enabled
		{
			get { return this._enabled; }
			set { this._enabled = value; }
		}
		#endregion

		#region Entity Methods
        public FlushType()
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
        public static FlushType Retrieve(ISession session, int id)
		{
            return EntityManager.Retrieve<FlushType>(session, id);
		}
		public static bool Delete(ISession session, int id)
		{
            return EntityManager.Delete<FlushType>(session, id);
		}
		#endregion

        public static IList<FlushType> EffectiveList(ISession session)
        {
            return session.CreateEntityQuery<FlushType>()
                .Where(Exp.Eq("Enabled", true))
                .List<FlushType>();
        }
	}
}