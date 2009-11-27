//*******************************************
// ** Description:  Data Access Object for OrderRuleDef
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:23:03
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for OrderRuleDef
    /// 单据号码生成规则
    /// </summary>
    [Table("ORD_RULE_DEF")]
    public partial class OrderRuleDef : IEntity
    {
        #region Private Fields
        private int _ruleDefineID;
        private bool _usePrefix;
        private string _prefixValue;
        private bool _useTimeStamp;
        private OrderRuleDefPrecision _timeStampPrecision;
        private DateTime _prevTimeStampValue;
        private string _timeStampPattern;
        private int _serialLength;
        private int _prevSerialValue;
        private DateTime _version;
        private string _ruleDefineText;
        #endregion

        #region Public Properties
        [Column(Name = "RULE_DEF_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_ORD_RULE_DEF")]
        public int RuleDefineID
        {
            get { return this._ruleDefineID; }
            set { this._ruleDefineID = value; }
        }

        [Column(Name = "RULE_DEF_TEXT", DbType = StdDbType.UnicodeString, Length = 40)]
        public string RuleDefineText
        {
            get { return this._ruleDefineText; }
            set { this._ruleDefineText = value; }
        }

        ///<summary>
        /// 是否使用前缀
        ///</summary>
        [Column(Name = "USE_PREFIX", DbType = StdDbType.Bool)]
        public bool UsePrefix
        {
            get { return this._usePrefix; }
            set { this._usePrefix = value; }
        }

        ///<summary>
        /// 前缀字符，3个以下英文字母
        ///</summary>
        [Column(Name = "PREFIX_VAL", DbType = StdDbType.AnsiString, Length = 4)]
        public string PrefixValue
        {
            get { return this._prefixValue; }
            set { this._prefixValue = value; }
        }

        ///<summary>
        /// 是否使用时间戳
        ///</summary>
        [Column(Name = "USE_TIME_STAMP", DbType = StdDbType.Bool)]
        public bool UseTimeStamp
        {
            get { return this._useTimeStamp; }
            set { this._useTimeStamp = value; }
        }

        ///<summary>
        /// 时间戳精度（精确到年、月、日？）
        /// 1: 年
        /// 2: 月
        /// 3: 日
        /// 4: 小时
        /// 5: 分
        ///</summary>
        [Column(Name = "TIME_STAMP_PRCS", DbType = StdDbType.Int32, Precision = 1)]
        public OrderRuleDefPrecision TimeStampPrecision
        {
            get { return this._timeStampPrecision; }
            set { this._timeStampPrecision = value; }
        }

        ///<summary>
        /// 生成前一个单号时使用的时间值
        ///</summary>
        [Column(Name = "PREV_TIME_STAMP_VAL", DbType = StdDbType.DateTime)]
        public DateTime PrevTimeStampValue
        {
            get { return this._prevTimeStampValue; }
            set { this._prevTimeStampValue = value; }
        }

        ///<summary>
        /// 例如yyMMdd，即DateTime.ToString()的参数
        ///</summary>
        [Column(Name = "TIME_STAMP_PAT", DbType = StdDbType.AnsiString, Length = 10)]
        public string TimeStampPattern
        {
            get { return this._timeStampPattern; }
            set { this._timeStampPattern = value; }
        }

        ///<summary>
        /// 流水号长度
        ///</summary>
        [Column(Name = "SER_LEN", DbType = StdDbType.Int32, Precision = 1)]
        public int SerialLength
        {
            get { return this._serialLength; }
            set { this._serialLength = value; }
        }

        ///<summary>
        /// 生成前一个单据时使用的流水号
        ///</summary>
        [Column(Name = "PREV_SER_VAL", DbType = StdDbType.Int32)]
        public int PrevSerialValue
        {
            get { return this._prevSerialValue; }
            set { this._prevSerialValue = value; }
        }

        ///<summary>
        /// 用于并发控制。生成单据号码前先更新该字段，锁住该记录不让其它事务更新
        ///</summary>
        [Column(Name = "RULE_VER", DbType = StdDbType.DateTime)]
        public DateTime Version
        {
            get { return this._version; }
            set { this._version = value; }
        }
        #endregion

        #region Entity Methods
        public OrderRuleDef()
        {
        }

        public bool Create(ISession session)
        {
            if (EntityManager.Create(session, this))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
        }
        public bool Update(ISession session)
        {
            if (EntityManager.Update(session, this))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
        }
        public bool Update(ISession session, params string[] propertyNames2Update)
        {
            if (EntityManager.Update(session, this, propertyNames2Update))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
        }
        public bool Delete(ISession session)
        {
            IList<OrderTypeDef> orderTypeDefines = session.CreateEntityQuery<OrderTypeDef>()
                .Where(Exp.Eq("RuleDefineID", this._ruleDefineID))
                .List<OrderTypeDef>();
            foreach (OrderTypeDef typeDef in orderTypeDefines)
            {
                typeDef.RuleDefineID = 0;
                typeDef.Update(session, "RuleDefineID");
            }
            if (EntityManager.Delete(session, this))
            {
                ERPUtil.Cache.Remove(this);
                return true;
            }
            return false;
        }
        public static OrderRuleDef Retrieve(ISession session, int ruleDefineID)
        {
            return ERPUtil.Cache.Get<OrderRuleDef>(ruleDefineID);
        }
        #endregion
    }
}