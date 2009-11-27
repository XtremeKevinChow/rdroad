//*******************************************
// ** Description:  Data Access Object for PaymentMethod
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.ERP.CRM
{
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for PaymentMethod
    /// 付款方式
    /// </summary>
    [Table("S_PAYMENT_METHOD")]
    public partial class PaymentMethod : IEntity
    {
        #region Private Fields
        private int _id;
        private string _name;
        private string _description;
        private string _mbrUse;
        private string _orgUse;
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
        /// 对个人会员是否有效
        ///</summary>
        [Column(Name = "PERSON_USE", DbType = StdDbType.AnsiString, Length = 1)]
        public string MbrUse
        {
            get { return this._mbrUse; }
            set { this._mbrUse = value; }
        }

        ///<summary>
        /// 对团购会员是否有效
        ///</summary>
        [Column(Name = "ORG_USE", DbType = StdDbType.AnsiString, Length = 1)]
        public string OrgUse
        {
            get { return this._orgUse; }
            set { this._orgUse = value; }
        }
        #endregion

        #region Entity Methods
        public PaymentMethod()
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
        public static PaymentMethod Retrieve(ISession session, int id)
        {
            return EntityManager.Retrieve<PaymentMethod>(session, id);
        }
        public static bool Delete(ISession session, int id)
        {
            return EntityManager.Delete<PaymentMethod>(session, id);
        }
        #endregion

        public static IList<PaymentMethod> EffectiveList(ISession session)
        {
            return session.CreateEntityQuery<PaymentMethod>()
                .Where(Exp.Eq("MbrUse", "Y"))
                .List<PaymentMethod>();
        }
    }
}
