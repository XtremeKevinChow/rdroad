using System;
using System.Collections.Generic;
using System.Text;
using System.Configuration;
using System.Xml;

namespace Magic.Security
{
    /// <summary>
    /// ConfigurationSectionHandler of  "Magic.Security"
    /// </summary>
    public class ConfigHandler: IConfigurationSectionHandler
    {

        #region IConfigurationSectionHandler Members
        /// <summary>
        /// 
        /// </summary>
        /// <param name="parent"></param>
        /// <param name="configContext"></param>
        /// <param name="section"></param>
        /// <returns></returns>
        public object Create(object parent, object configContext, System.Xml.XmlNode section)
        {
            
            ConfigInfo configInfo = new ConfigInfo();
            XmlNode aNode = section.SelectSingleNode("Authorization");
            configInfo.AuthorizationServiceProvider = GetServiceProvider(aNode);
           

            aNode = section.SelectSingleNode("Authenticate");
            configInfo.AuthenticateServiceProvider = GetServiceProvider(aNode);

            XmlNode propertiesNode = section.SelectSingleNode("Properties");
            if (propertiesNode != null && propertiesNode.ChildNodes != null && propertiesNode.ChildNodes.Count > 0)
            {
                foreach (XmlNode prop in propertiesNode.ChildNodes)
                {
                    if (prop.Attributes["name"] != null && prop.Attributes["value"] != null)
                    {
                        configInfo.AddProperty(prop.Attributes["name"].Value, prop.Attributes["value"].Value);
                    }
                }
            }
            return configInfo;
        }

        #endregion

        private ServiceProvider GetServiceProvider(XmlNode aNode)
        {
            XmlNode providerNode = null;
            string defaultProviderName = string.Empty;
            ServiceProvider sp = null;
            string name = string.Empty, type = string.Empty;
            #region
            if (aNode != null)
            {
                if (aNode.Attributes["defaultProvider"] != null)
                {
                    defaultProviderName = aNode.Attributes["defaultProvider"].Value.Trim();
                }
                if (!string.IsNullOrEmpty(defaultProviderName))
                {
                    providerNode = aNode.SelectSingleNode(string.Format("ServiceProvider[@name='{0}']", defaultProviderName));
                    if (providerNode != null)
                    {
                        if (providerNode.Attributes["name"] != null)
                            name = providerNode.Attributes["name"].Value;
                        else
                            throw new ConfigurationErrorsException("Magic.Security 配置错误!", aNode);
                        if (providerNode.Attributes["type"] != null)
                            type = providerNode.Attributes["type"].Value;
                        else
                            throw new ConfigurationErrorsException("Magic.Security 配置错误!", aNode);
                    }
                }
                if (providerNode == null)
                {
                    XmlNodeList list = aNode.SelectNodes("ServiceProvider");
                    if (list != null && list.Count > 0)
                    {
                        providerNode = list[0];

                        if (providerNode != null)
                        {
                            if (providerNode.Attributes["name"] != null)
                                name = providerNode.Attributes["name"].Value;
                            else
                                throw new ConfigurationErrorsException("Magic.Security 配置错误!", aNode);
                            if (providerNode.Attributes["type"] != null)
                                type = providerNode.Attributes["type"].Value;
                            else
                                throw new ConfigurationErrorsException("Magic.Security 配置错误!", aNode);
                        }
                    }
                }
            }
            #endregion
            sp = new ServiceProvider(name, type);
            return sp;
        }
    }
}
