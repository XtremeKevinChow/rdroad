using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 安全服务提供者接口
    /// </summary>
    internal sealed class SecurityServiceProvider
    {
        private static bool _initialized = false;
        private static string _authorizationServiceProviderType = string.Empty;
        private static string _authenticateServiceProviderType = string.Empty;
        private static string _azRepositoryType = string.Empty;
        public static IAuthenticateService _atSvc = null;
        private static IAuthorizationService _azSvc = null;
        private static IAuthorizationRepository _azRepository = null;
        private static ConfigInfo _config = null;
        private static void Initialize()
        {
            if (!_initialized)
            {
                 _config = System.Configuration.ConfigurationManager.GetSection("Magic.Security") as ConfigInfo;
                if (_config != null)
                {
                    _authenticateServiceProviderType = _config.AuthenticateServiceProvider.Type;
                    _authorizationServiceProviderType = _config.AuthorizationServiceProvider.Type;
                }
            }
        }
        /// <summary>
        /// 获取用户验证服务对象
        /// </summary>
        /// <returns></returns>
        public static IAuthenticateService GetAuthenticateService()
        {
            Initialize();
            if (_atSvc == null)
            {
                _atSvc = System.Activator.CreateInstance(Type.GetType(_authenticateServiceProviderType),_config) as IAuthenticateService;
            }
            return _atSvc;
        }

        /// <summary>
        /// 获取权限认证服务对象
        /// </summary>
        /// <returns></returns>
        public static IAuthorizationService GetAuthorizationService()
        {
            Initialize();
            if (_azSvc == null)
            {
                _azSvc = System.Activator.CreateInstance(Type.GetType(_authorizationServiceProviderType),_config) as IAuthorizationService;
            }
            return _azSvc;
        }

        /// <summary>
        /// 获取权限系统存储服务
        /// </summary>
        /// <returns></returns>
        public static IAuthorizationRepository GetAuthorizationRepository()
        {
            Initialize();
            if (_azRepository == null)
            {
                _azRepository = System.Activator.CreateInstance(Type.GetType(_azRepositoryType),_config) as IAuthorizationRepository;
            }
            return _azRepository;
        }
        
    }
}
