//*******************************************
// ** Description:  Data Access Object for CRMSNLineINV
// ** Author     :  Code generator
// ** Created    :   2008-8-28 21:31:36
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using System;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for CRMSNLineINV
    /// </summary>
    [Table("ORD_SN_LINE_INV")]
    public partial class CRMSNLineINV : IEntity, IWHLockItem
    {
        #region Private Fields
        private int _iD;
        private int _ownerID;
        private int _stockDetailID;
        private string _locationCode;
        private string _areaCode;
        private string _sectionCode;
        private decimal _quantity;
        private IWHTransLine _owner;
        #endregion

        #region Public Properties
        [Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_ORD_SN_LINE_INV")]
        public int ID
        {
            get { return this._iD; }
            set { this._iD = value; }
        }

        [Column(Name = "SNDID", DbType = StdDbType.Int32)]
        public int OwnerID
        {
            get { return this._ownerID; }
            set { this._ownerID = value; }
        }

        [Column(Name = "STOCK_DTL_ID", DbType = StdDbType.Int32)]
        public int StockDetailID
        {
            get { return this._stockDetailID; }
            set { this._stockDetailID = value; }
        }

        [Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
        public string LocationCode
        {
            get { return this._locationCode; }
            set { this._locationCode = value; }
        }

        [Column(Name = "AREA_CODE", DbType = StdDbType.AnsiString, Length = 8)]
        public string AreaCode
        {
            get { return this._areaCode; }
            set { this._areaCode = value; }
        }

        [Column(Name = "SEC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
        public string SectionCode
        {
            get { return this._sectionCode; }
            set { this._sectionCode = value; }
        }

        [Column(Name = "QUANTITY", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
        public decimal Quantity
        {
            get { return this._quantity; }
            set { this._quantity = value; }
        }

        public IWHTransLine Owner
        {
            get { return this._owner; }
            set { this._owner = value; }
        }
        #endregion

        #region Entity Methods
        public CRMSNLineINV()
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
        public static CRMSNLineINV Retrieve(ISession session, int iD)
        {
            return EntityManager.Retrieve<CRMSNLineINV>(session, iD);
        }
        public static bool Delete(ISession session, int iD)
        {
            return EntityManager.Delete<CRMSNLineINV>(session, iD);
        }
        #endregion
    }
}
