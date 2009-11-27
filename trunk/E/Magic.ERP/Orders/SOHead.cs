//*******************************************
// ** Description:  Data Access Object for SOHead
// ** Author     :  Code generator
// ** Created    :   2008-8-1 21:10:37
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
    using System.Collections.Generic;
    using System.Data;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for SOHead
	/// </summary>
	[Table("ORD_HEADERS")]
	public partial class  SOHead : IEntity
	{
		#region Private Fields
		private int _iD;
		private string _orderNumber;
        private SaleOrderType _orderType;
		#endregion

		#region Public Properties
		[Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int ID
		{
			get { return this._iD; }
			set { this._iD = value; }
		}

		[Column(Name = "SO_NUMBER", DbType = StdDbType.AnsiString, Length = 20)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

        [Column(Name = "ORDER_TYPE", DbType = StdDbType.Int32)]
        public SaleOrderType OrderType
        {
            get { return this._orderType; }
            set { this._orderType = value; }
        }

        private DateTime _releaseDate;
        [Column(Name = "CREATE_DATE", DbType = StdDbType.DateTime)]
        public DateTime ReleaseDate
        {
            get { return this._releaseDate; }
            set { this._releaseDate = value; }
        }

        public static SOHead Query(ISession session, string soNumber)
        {
            IList<SOHead> headers = session.CreateEntityQuery<SOHead>()
                .Where(Exp.Eq("OrderNumber", soNumber))
                .List<SOHead>();
            return headers.Count <= 0 ? null : headers[0];
        }
        public SOHead UpdateStatus(ISession session, int status)
        {
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand cmd = dbsession.CreateSqlStringCommand("Update ord_headers Set status=:status Where id=:orderId");
            dbsession.AddParameter(cmd, ":orderId", DbTypeInfo.Int32(), this.ID);
            dbsession.AddParameter(cmd, ":status", DbTypeInfo.Int32(), status);
            dbsession.ExecuteNonQuery(cmd);
            return this;
        }
        public SOHead UpdateLineStatus(ISession session, int status)
        {
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand cmd = dbsession.CreateSqlStringCommand("Update ord_lines Set status=:status Where order_id=:orderId");
            dbsession.AddParameter(cmd, ":orderId", DbTypeInfo.Int32(), this.ID);
            dbsession.AddParameter(cmd, ":status", DbTypeInfo.Int32(), status);
            dbsession.ExecuteNonQuery(cmd);
            return this;
        }
		#endregion

		#region Entity Methods
		public SOHead()
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
		public static SOHead Retrieve(ISession session, int iD)
		{
			return EntityManager.Retrieve<SOHead>(session, iD);
		}
		public static bool Delete(ISession session, int iD)
		{
			return EntityManager.Delete<SOHead>(session, iD);
		}
		#endregion
	}
}
