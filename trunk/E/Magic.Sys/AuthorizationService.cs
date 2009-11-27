using System;
using System.Collections.Generic;
using System.Text;
using Magic.Security;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;

namespace Magic.Sys
{
    /// <summary>
    /// 
    /// </summary>
    public class AuthorizationService:IAuthorizationService
    {
        ConfigInfo _config = null;
        public AuthorizationService()
            :this(new ConfigInfo())
        {
        }

        public AuthorizationService(ConfigInfo config)
        {
            _config = config;
        }

        #region IAuthorizationService Members

        public void AddPermissionsToQuery(UserBase urser, string operation, Magic.Framework.ORM.IEntityQuery criteria)
        {
            
        }

        public bool IsAllowed(UserBase user, string operation)
        {
            return true;
        }

        public bool IsAllowed(UserBase user, string operation, IEntitySecurityKey key)
        {
            return true;
        }

        public AuthorizationInformation GetAuthorizationInformation(UserBase urser, string operation)
        {
            return new AuthorizationInformation();
        }     

        public IList<IUserGroup> GetUserGroupsForUser(UserBase user)
        {
            using (Session session = new Session())
            {
                AuthorizationRepository repository = new AuthorizationRepository(session);
                return repository.GetGroupsForUser(user);
            }
        }

        #endregion
    }
}
