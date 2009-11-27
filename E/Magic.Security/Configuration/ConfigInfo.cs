using System;
using System.Collections.Generic;
using System.Text;
using System.Configuration;

namespace Magic.Security
{
    /// <summary>
    /// Security Configuration
    /// </summary>
    public class ConfigInfo
    {
        private IDictionary<string, string> _configNameValues = new Dictionary<string, string>();

        /// <summary>
        /// 
        /// </summary>
        public ConfigInfo()
        {
        }        

        internal ServiceProvider AuthorizationServiceProvider
        {
            get;
            set;
        }

        internal ServiceProvider AuthenticateServiceProvider
        {
            get;
            set;
        }

        internal void AddProperty(string name, string value)
        {
            _configNameValues.Add(name, value);
        }

        internal void RemoveProperty(string name)
        {
            _configNameValues.Remove(name);
        }

        internal void ClearProperties()
        {
            _configNameValues.Clear();
        }

        /// <summary>
        /// 获取配置的属性值
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        public string GetProerptyValue(string name)
        {
            if (_configNameValues.ContainsKey(name))
            {
                return _configNameValues[name];
            }
            return string.Empty;
        }
       
    }

    internal class ServiceProvider
    {
        public ServiceProvider(string name, string type)
        {
            this.Name = name;
            this.Type = type;
        }
        public string Type { get; set; }
        public string Name { get; set; }
    }
}
