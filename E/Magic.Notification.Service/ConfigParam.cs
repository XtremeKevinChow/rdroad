namespace Magic.Notification.Service
{
    public class ConfigParam
    {
        private string _smsAccount;
        /// <summary>
        /// 短信发送帐号
        /// </summary>
        public string SMSAccount
        {
            get { return this._smsAccount; }
            set { this._smsAccount = value; }
        }
        private string _smsPassword;
        /// <summary>
        /// 短信帐号密码
        /// </summary>
        public string SMSPassword
        {
            get { return this._smsPassword; }
            set { this._smsPassword = value; }
        }

        private string _mailAccount;
        /// <summary>
        /// 发送邮件的帐号（登陆邮箱的帐户名）
        /// </summary>
        public string MailAccount
        {
            get { return this._mailAccount; }
            set { this._mailAccount = value; }
        }
        private string _mailPassword;
        /// <summary>
        /// 发送邮件的密码
        /// </summary>
        public string MailPassword
        {
            get { return this._mailPassword; }
            set { this._mailPassword = value; }
        }
        private string _mailAddress;
        /// <summary>
        /// 发送者的邮箱地址（必须与登陆帐号一致，即登陆帐号拥有的是这个邮箱地址）
        /// </summary>
        public string MailAddress
        {
            get { return this._mailAddress; }
            set { this._mailAddress = value; }
        }
        private string _mailServer;
        /// <summary>
        /// 邮件服务器地址
        /// </summary>
        public string MailServer
        {
            get { return this._mailServer; }
            set { this._mailServer = value; }
        }
        private string _mailSenderName;
        /// <summary>
        /// 邮件发送者友好名称
        /// </summary>
        public string MailSenderName
        {
            get { return this._mailSenderName; }
            set { this._mailSenderName = value; }
        }

        private bool _mailEnableLog;
        /// <summary>
        /// 是否记录邮件发送日志
        /// </summary>
        public bool MailEnableLog
        {
            get { return this._mailEnableLog; }
            set { this._mailEnableLog = value; }
        }
    }
}