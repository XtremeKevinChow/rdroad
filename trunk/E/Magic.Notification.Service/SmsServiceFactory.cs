using System;
using System.Collections.Generic;
using System.Text;
using System.Configuration;
using System.Collections;

namespace Magic.Notification.Service
{
    public static class SmsServiceFactory
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(SmsServiceFactory));

        public static ISmsService GetSmsService(string serviceProviderName)
        {
            Hashtable configValues = ConfigurationManager.GetSection("SMSServiceFactory") as Hashtable;
            if (configValues == null)
            {
                throw new ApplicationException("请在配置文件中配置SMSServiceFactory结点，以获取SMSService");
            }
            string typeName = (string)configValues[serviceProviderName];

            ISmsService service = null;
            try
            {
                Type type = Type.GetType(typeName);
                if (type == null)
                {
                    throw new ApplicationException("SMSServiceFactory配置名称:"+  serviceProviderName+"，不能创建SMSService类型, 名称：" + typeName );
                }
                service = Activator.CreateInstance(type) as ISmsService;
            }
            catch (Exception ex)
            {
                throw new ApplicationException("SMSServiceFactory配置名称:" + serviceProviderName + "，配置的类型不能获取:" + ex.ToString());
            }

            if (service == null)
                throw new ApplicationException("SMSServiceFactory配置名称:" + serviceProviderName + "，配置的类型没有继承自ISmsService接口");

            log.Info("实例化短信发送服务成功，" + "SMSServiceFactory配置名称:" + serviceProviderName + "，服务类型名称:" + typeName);
            
            return service; 
        }

        public static ISmsService GetSmsService()
        {
            return GetSmsService("default");
        }

        
    }
}
