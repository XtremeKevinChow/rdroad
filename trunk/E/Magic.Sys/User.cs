//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-01-25 23:20:43
// ** Modified   :
//*******************************************

using System;
using System.Collections.Generic;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Security;
using Magic.Framework.Utils;

namespace Magic.Sys
{
	/// <summary>
	/// 状态, derive from short.
	/// </summary>
	public enum UserStatus
	{
        /// <summary>
        /// 启用
        /// </summary>
        Enabled = 1,
        /// <summary>
        /// 禁用
        /// </summary>
        Disabled = 0,
        /// <summary>
        /// 删除
        /// </summary>
        Deleted = -1
	}
		
	/// <summary>
	///  用户's Model
	/// </summary>
    [PermissionCheck]
    [Table("SYS_USER")]
	public partial class  User: UserBase,IEntity
	{
		#region Private Fields
		
		private int _userId;
		private string _userName;
		private string _password;
		private string _fullName;
		private OrgType _userType;
		private string _email;
		private bool _gender;
		private string _employeeNo;
		private string _ext;
		private string _mobile;
		private string _homePhone;
		private string _homeAddress;
		private DateTime _birthday;
		private string _note;
		private UserStatus _status;
		private int _orgId;
		private DateTime _lastLogonTime;
		private DateTime _createTime;
		private int _createBy;
       private int _modifyBy;
        private DateTime _modifyTime;
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 用户
		/// </summary>
		public User()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 密码
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=true,Name="USR_PSW")]
		public virtual string Password
		{
			get { return this._password; }
			set { this._password = value; }
		}
		
        ///// <summary>
        ///// 用户姓名
        ///// </summary>
        //[Column(DbType=StdDbType.UnicodeString, Length=30, Nullable=true,Name="USR_NAME")]
        //new public  string FullName
        //{
        //    get { return this._fullName; }
        //    set { this._fullName = value; }
        //}
		
		/// <summary>
		/// 用户类型
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="USR_TYPE")]
		public virtual OrgType UserType
		{
			get { return this._userType; }
			set { this._userType = value; }
		}
		
        ///// <summary>
        ///// 电子邮件
        ///// </summary>
        //[Column(DbType=StdDbType.UnicodeString, Length=50, Nullable=true,Name="USR_EMAIL")]
        // public  string Email
        //{
        //    get { return this._email; }
        //    set { this._email = value; }
        //}
		
		/// <summary>
		/// 性别
		/// </summary>
		[Column(DbType=StdDbType.Bool, Nullable=true,Name="USR_GENDER")]
		public virtual bool Gender
		{
			get { return this._gender; }
			set { this._gender = value; }
		}
		
		/// <summary>
		/// 员工号
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=true,Name="USR_WORK_NUM")]
		public virtual string EmployeeNo
		{
			get { return this._employeeNo; }
			set { this._employeeNo = value; }
		}
		
		/// <summary>
		/// 分机
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=8, Nullable=true,Name="USR_EXT")]
		public virtual string Ext
		{
			get { return this._ext; }
			set { this._ext = value; }
		}
		
		/// <summary>
		/// 手机
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=25, Nullable=true,Name="USR_MOBILE")]
		public virtual string Mobile
		{
			get { return this._mobile; }
			set { this._mobile = value; }
		}
		
		/// <summary>
		/// 家庭电话
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=25, Nullable=true,Name="USR_HOME_PHONE")]
		public virtual string HomePhone
		{
			get { return this._homePhone; }
			set { this._homePhone = value; }
		}
		
		/// <summary>
		/// 家庭地址
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="USR_HOME_ADDR")]
		public virtual string HomeAddress
		{
			get { return this._homeAddress; }
			set { this._homeAddress = value; }
		}
		
		/// <summary>
		/// 生日
		/// </summary>
		[Column(DbType=StdDbType.Date, Nullable=true,Name="USR_BIRTHDAY")]
		public virtual DateTime Birthday
		{
			get { return this._birthday; }
			set { this._birthday = value; }
		}
		
		/// <summary>
		/// 备注
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="USR_NOTE")]
		public virtual string Note
		{
			get { return this._note; }
			set { this._note = value; }
		}
		
		/// <summary>
		/// 状态
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="USR_STATUS")]
		public virtual UserStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}
		
		/// <summary>
		/// 所属组织ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="ORG_ID")]
		public virtual int OrgId
		{
			get { return this._orgId; }
			set { this._orgId = value; }
		}
		
		/// <summary>
		/// 上次登陆时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="LAST_LOGIN")]
		public virtual DateTime LastLogonTime
		{
			get { return this._lastLogonTime; }
			set { this._lastLogonTime = value; }
		}
		
		/// <summary>
		/// 创建时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="CREATE_TIME")]
		public virtual DateTime CreateTime
		{
			get { return this._createTime; }
			set { this._createTime = value; }
		}
		
		/// <summary>
		/// 创建人
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="CREATE_BY")]
		public virtual int CreateBy
		{
			get { return this._createBy; }
			set { this._createBy = value; }
		}


        /// <summary>
        /// 最近更新人
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, Name = "MODIFY_BY")]
        public virtual int ModifyBy
        {
            get { return this._modifyBy; }
            set { this._modifyBy = value; }
        }

        /// <summary>
        /// 最近更新时间
        /// </summary>
        [Column(DbType = StdDbType.DateTime, Nullable = false, Name = "MODIFY_TIME")]
        public virtual DateTime ModifyTime
        {
            get { return this._modifyTime; }
            set { this._modifyTime = value; }
        }
		
		#endregion

		#region Public Methods
		/// <summary>
		/// Create new 用户 entity
		/// <returns></returns>
		/// </summary>
        [Operation("/Administration/User/Create", "创建用户")]
		public bool Create(ISession session)
		{
            EntityQuery qry = session.CreateEntityQuery<User>();
            qry.And(Exp.Eq("UserName", this.UserName));
            if (qry.Count() > 0)
                throw new ApplicationException("已经存在帐户名称为{0}的用户，请选择其他的名称！");
			return EntityManager.Create(session,this);
		}

        /// <summary>
        /// Update 用户 entity's whole properties
        /// <returns></returns>
        /// </summary>
        [Operation("/Administration/User/Edit", "更新用户")]
        public bool Update(ISession session)
        {
            return EntityManager.Update(session, this);
        }

		/// <summary>
		///Update 用户 entity's given properties
		/// <returns></returns>
		/// </summary>
        [Operation("/Administration/User/Edit", "更新用户")]
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

        /// <summary>
        ///Delete this 用户 entity
        /// <returns></returns>
        /// </summary>
        bool IEntity.Delete(ISession session)
        {
            //return EntityManager.Delete(session, this);
            return Delete(session, this);
        }

		/// <summary>
		///Retrive a 用户 Entity from DB
		/// <returns></returns>
		/// </summary>
        [Operation("/Administration/User/View", "查看用户")]
		public static User Retrieve(ISession session  ,int userId  )
		{
			return EntityManager.Retrieve<User>(session,userId);
		}

        /// <summary>
        /// 删除用户
        /// </summary>
        /// <returns></returns>
        [Operation("/Administration/User/Delete", "删除用户")]
        public static bool Delete(ISession session, User user)
        {
            //删除用户，需要删除该用户的所有关联，包括：
            // 用户组，用户权限
            user.Status = UserStatus.Deleted;
            return EntityManager.Update(session, user);
            //return EntityManager.Delete(session, user);
        }

        /// <summary>
        ///  Map a DataRow to a User Entity.
        /// </summary>
        /// <returns></returns>
        public static User Row2Entity(System.Data.DataRow row)
        {
            if (row == null) return null;

            User entity = new User();

            if (!row.IsNull("USR_ID"))
                entity._userId = (int)(row["USR_ID"]);
            if (!row.IsNull("USR_LOGIN_ID"))
                entity._userName = (string)(row["USR_LOGIN_ID"]);
            if (!row.IsNull("USR_PSW"))
                entity._password = (string)(row["USR_PSW"]);
            if (!row.IsNull("USR_NAME"))
                entity._fullName = (string)(row["USR_NAME"]);
            if (!row.IsNull("USR_TYPE"))
                entity._userType =(OrgType)Enum.Parse(typeof(OrgType),(row["USR_TYPE"]).ToString());
            if (!row.IsNull("USR_EMAIL"))
                entity._email = (string)(row["USR_EMAIL"]);
            if (!row.IsNull("USR_GENDER"))
                entity._gender = (bool)(row["USR_GENDER"]);
            if (!row.IsNull("USR_WORK_NUM"))
                entity._employeeNo = (string)(row["USR_WORK_NUM"]);
            if (!row.IsNull("USR_EXT"))
                entity._ext = (string)(row["USR_EXT"]);
            if (!row.IsNull("USR_MOBILE"))
                entity._mobile = (string)(row["USR_MOBILE"]);
            if (!row.IsNull("USR_HOME_PHONE"))
                entity._homePhone = (string)(row["USR_HOME_PHONE"]);
            if (!row.IsNull("USR_HOME_ADDR"))
                entity._homeAddress = (string)(row["USR_HOME_ADDR"]);
            if (!row.IsNull("USR_BIRTHDAY"))
                entity._birthday = (DateTime)(row["USR_BIRTHDAY"]);
            if (!row.IsNull("USR_NOTE"))
                entity._note = (string)(row["USR_NOTE"]);
            if (!row.IsNull("USR_STATUS"))
                entity._status = (UserStatus)Enum.Parse(typeof(UserStatus),(row["USR_STATUS"]).ToString());
            if (!row.IsNull("ORG_ID"))
                entity._orgId = (int)(row["ORG_ID"]);
            if (!row.IsNull("LAST_LOGIN"))
                entity._lastLogonTime = (DateTime)(row["LAST_LOGIN"]);
            if (!row.IsNull("CREATE_TIME"))
                entity._createTime = (DateTime)(row["CREATE_TIME"]);
            if (!row.IsNull("CREATE_BY"))
                entity._createBy = (int)(row["CREATE_BY"]);
            if (!row.IsNull("MODIFY_BY"))
                entity._modifyBy = (int)(row["MODIFY_BY"]);
            if (!row.IsNull("MODIFY_TIME"))
                entity._modifyTime = (DateTime)(row["MODIFY_TIME"]);

            return entity;
        }
       
		#endregion

        #region Static
        private static bool _cached = false;
        private static Dictionary<int, string> _userNameHash = new Dictionary<int, string>();
        public static string GetUserName(int userId)
        {
            CacheUser();
          if(_userNameHash.ContainsKey(userId))
            {
                return _userNameHash[userId];
            }
            else
            {
                return MultiLang.GetText("Unkown User");
            }
        
        }

        public static void CacheUser()
        {
            if (!_cached && _userNameHash.Count == 0)
            {
                using (ISession session = new Session())
                {
                    IList<User> users = session.CreateEntityQuery<User>()
                        .And(Exp.Eq("USR_STATUS", UserStatus.Enabled))
                        .List<User>();
                    if (users != null && users.Count > 0)
                    {
                        foreach (User user in users)
                        {
                            _userNameHash.Add(user.UserId, user.FullName);
                        }
                    }
                }
                _cached = true;
            }
        }

        public static string UserTypeText(object type)
        {
            switch(Magic.Framework.Utils.Cast.Enum<OrgType>(type))
            {
                case OrgType.Own: return "内部用户";
            }
            return "";
        }
        public static string UserStatusText(object status)
        {
            switch(Cast.Enum<UserStatus>(status))
            {
                case UserStatus.Deleted: return "删除";
                case UserStatus.Disabled: return "禁用";
                case UserStatus.Enabled: return "启用";
            }
            return "";
        }
        #endregion
    }
}