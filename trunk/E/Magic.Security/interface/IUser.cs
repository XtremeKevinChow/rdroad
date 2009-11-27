using System;
using System.Collections.Generic;
using System.Text;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;

namespace Magic.Security
{
    ///// <summary>
    ///// Security User Interface
    ///// </summary>
    //public interface UserBase
    //{
    //    /// <summary>
    //    /// ID
    //    /// </summary>
    //    int UserId { get; set; }
    //    /// <summary>
    //    /// 登陆名称
    //    /// </summary>
    //    string UserName { get; set; }
    //    /// <summary>
    //    /// 完整名称
    //    /// </summary>
    //    string FullName { get; set; }
    //    /// <summary>
    //    /// Email
    //    /// </summary>
    //    string Email { get; set; }
    //}
    /// <summary>
    /// User的抽象类
    /// </summary>
    [Table("SYS_USER")]
    public abstract class UserBase
    {
        /// <summary>
        /// ID
        /// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_USER", IsPrimary = true, Name = "USR_ID")]
        public virtual int UserId { get; set; }

        /// <summary>
        /// 登陆帐号
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 30, Nullable = false, Name = "USR_LOGIN_ID")]
        public virtual string UserName { get; set; }
        /// <summary>
        /// 完整名称
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 30, Nullable = true, Name = "USR_NAME")]
        public virtual string FullName { get; set; }
        /// <summary>
        /// Email
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 50, Nullable = true, Name = "USR_EMAIL")]
        public virtual string Email { get; set; }
    }
}
