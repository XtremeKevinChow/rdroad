using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security.Utility
{
    /// <summary>
    /// 加密工具
    /// </summary>
    public sealed class Encryption
    {
        static Encryption()
        {
        }

        /// <summary>
        /// MD5加密方法
        /// </summary>
        /// <param name="toEncrypt">需要加密的字符串</param>
        /// <param name="code">加密位数，16或32</param>
        /// <returns></returns>
        public static string MD5(string toEncrypt, int code)
        {
            if (code == 16) //16位MD5加密（取32位加密的9~25字符）
            {
                return System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(toEncrypt, "MD5").ToLower().Substring(8, 16);
            }
            else //32位加密
            {
                return System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(toEncrypt, "MD5").ToLower();
            }
        }
    }
}
