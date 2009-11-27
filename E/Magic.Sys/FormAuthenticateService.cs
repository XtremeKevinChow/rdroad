using System;
using System.Collections.Generic;
using System.Text;
using Magic.Security;
using System.Web;
using System.Web.SessionState;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;

namespace Magic.Sys
{
    
    public class FormAuthenticateService:IAuthenticateService
    {
        log4net.ILog logger = log4net.LogManager.GetLogger(typeof(FormAuthenticateService));

        ConfigInfo _config = null;
        public FormAuthenticateService()
            :this(new ConfigInfo())
        {
        }

        public FormAuthenticateService(ConfigInfo config)
        {
            _config = config;
        }

        

        private const string USER_SESSION_ID = "User_Principal";
        private string _loginUrl = "";

        /// <summary>
        /// 
        /// </summary>
        public string LoginUrl
        {
            get
            {
                if (string.IsNullOrEmpty(_loginUrl))
                {
                    _loginUrl = _config.GetProerptyValue("loginurl");
                  
                }
                if (string.IsNullOrEmpty(_loginUrl))
                {
                    _loginUrl = "logon.aspx";
                }
                string absurl = System.IO.Path.Combine(HttpContext.Current.Request.ApplicationPath, _loginUrl);
                logger.Debug("LoginUrl:" + absurl);
                
                return absurl;
            }
            set
            {
                _loginUrl = value;
            }
        }

        private  void SetCurrentUser(UserBase user)
        {
            if (CheckHttpContext())
            {
                HttpContext.Current.Session[USER_SESSION_ID] = user;
            }
        }

        private void RemoveCurrentUser()
        {
            if (CheckHttpContext())
            {
                HttpContext.Current.Session.Remove(USER_SESSION_ID);
            }
        }

        private bool CheckHttpContext()
        {
            return (HttpContext.Current != null && HttpContext.Current.Session != null);
        }

        #region IAuthenticateService Members

        public bool AuthenticateUser(string username, string password)
        {
            //
            UserBase user = null;
            if (ValidateUser(username, password, out user))
            {
                SetCurrentUser(user);
                using (ISession session = new Session())
                {
                     User entity = (User)user;
                    entity.LastLogonTime = DateTime.Now;
                    entity.Update(session, "LastLogonTime");

                }
                return true;
            }
            return false;
        }

        /// <summary>
        /// 验证用户，并返回合法用户的
        /// </summary>
        /// <param name="session"></param>
        /// <param name="userName"></param>
        /// <param name="password"></param>
        /// <param name="validUser"></param>
        /// <returns>true:通过，false:不通过</returns>
        private bool ValidateUser(string userName, string password, out UserBase validUser)
        {
            validUser = null;

            if (string.IsNullOrEmpty(userName) || string.IsNullOrEmpty(password))
            {
                throw new ArgumentNullException("UserName and Password can not be NULL!");
            }
            using (ISession session = new Session())
            {
                IList<Magic.Sys.User> list = session.CreateEntityQuery<Magic.Sys.User>()
                .And(Exp.Eq("UserName", userName))
               // .And(Exp.Eq("Password", Magic.Security.Utility.Encryption.MD5(password, 32)))
               .And(Exp.Eq("Password",password))
                .List<User>();

                if (list == null || list.Count == 0)
                {
                    return false;
                }

                validUser = list[0];
                return true;
            }

        }

        public void SignOut()
        {
            RemoveCurrentUser();
            HttpContext.Current.Session.Clear();
            HttpContext.Current.Response.Redirect(LoginUrl);
        }

        public UserBase GetCurrentUser()
        {
            if (CheckHttpContext())
            {
                if (HttpContext.Current.Session[USER_SESSION_ID] != null)
                {
                    return HttpContext.Current.Session[USER_SESSION_ID] as UserBase;
                }
                else
                {
                   HttpContext.Current.Server.Transfer(LoginUrl, true);
                    return null;
                }
            }
            return null;
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="username"></param>
        /// <returns></returns>
        public bool Authenticated()
        {
            return CheckHttpContext() && HttpContext.Current.Session[USER_SESSION_ID] != null;
        }

        #endregion
    }
}
