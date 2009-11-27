using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using Magic.Framework.ORM;
using jmail;

namespace Magic.Notification.Service
{
    public class SendResult
    {
        public int SuccessCount = 0;
        public int ErrorCount = 0;
        public string ResultCode = null;
        public string ResultText = null;
    }
    public class SendService
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(SendService));
        private static ISmsService _smsService = null;
        private static Message _mailService = null;
        private static Regex _mailChecker = new Regex("[a-zA-Z0-9][-._a-zA-Z0-9]{0,}[a-zA-Z0-9]@[a-zA-Z0-9][-._a-zA-Z0-9]{0,}[a-zA-Z0-9][.][a-zA-Z0-9]{2,4}", RegexOptions.IgnoreCase | RegexOptions.Singleline);
        private static Regex _smsChecker = new Regex("[0-9]{3,12}", RegexOptions.Singleline);

        private static void OnLoad(ISession session, ConfigParam config)
        {         
            //短信发送服务
            _smsService = SmsServiceFactory.GetSmsService();

            //邮件发送服务
            _mailService = new MessageClass();
            //屏蔽发送异常，返回值true和false确定发送是否成功，发送结果状态码和错误信息分别在ErrorCode和ErrorMessage中
            _mailService.Silent = true;
            _mailService.Logging = true;
            _mailService.Charset = "GB2312";
            _mailService.Encoding = "GB2312";
            _mailService.ContentType = "text/html";
            //邮件服务器登陆认证的帐号、密码
            _mailService.MailServerUserName = config.MailAccount;
            _mailService.MailServerPassWord = config.MailPassword;
            //发送者邮箱地址（必须与登陆帐号、密码一致，即该邮箱必须属于登陆帐号的）
            _mailService.From = config.MailAddress;
            _mailService.FromName = config.MailSenderName;
        }
        private static void UnLoad()
        {
            _smsService = null;
            if (_mailService != null) _mailService.Close();
            _mailService = null;
        }

        private static bool CheckMail(string mail)
        {
            if (string.IsNullOrEmpty(mail) || mail.Trim().Length <= 0) return false;
            return _mailChecker.Replace(mail, "").Length <= 0;
        }
        private static bool CheckSms(ref string phone)
        {
            if (string.IsNullOrEmpty(phone) || phone.Trim().Length <= 0) return false;
            phone = phone.Trim();
            if (phone.Length == 11
                && (phone.StartsWith("13") || phone.StartsWith("15"))
                && _smsChecker.Replace(phone, "").Length <= 0)
                return true; //的确是手机号
            if (phone.Length == 12
                && (phone.StartsWith("013") || phone.StartsWith("015"))
                && _smsChecker.Replace(phone, "").Length <= 0)
            {
                phone = phone.Substring(1);
                return true; //的确是手机号，不过前面加了0
            }
            return false; //还有可能是小灵通
        }

        public static SendResult SendNotification(DateTime startingDate, ConfigParam config)
        {
            SendResult state = new SendResult();
            try
            {
                #region 发送消息
                using (ISession session = new Session())
                {
                    log.Debug("initializing..");
                    OnLoad(session, config); //发送前的初始化工作

                    IList<Notification> todolist = Notification.ToSendList(session, startingDate); //待发送列表
                    log.DebugFormat("{0} messages to send", todolist.Count);
                    if (todolist.Count <= 0) return state;
                    foreach (Notification msg in todolist)
                    {
                        int currentSuccessCount = state.SuccessCount + state.ErrorCount; //对msg的所有接收者发送之前，已经处理过的消息总数量
                        try
                        {
                            log.DebugFormat("to send message {0} ...", msg.NotifyID);
                            msg.ReadyToSend(); //消息通知发送前的预处理
                            NotificationCategory category = Notification.GetCategory(session, msg.CatID);

                            //各个Sender方法的注意点：对每个接收者发送消息时，如果抛出异常则可能导致后面还没有发送的接收者在本次任务时不会发送
                            //抛出异常时catch块中会尝试计算剩余未成功发送的消息数量
                            //如果不抛出异常，则成功时需将state.SuccessCount加1，失败时需要将state.ErrorCount加1
                            if (category.Type == NotificationType.SMS)
                                SMSSender(session, msg, config, state);
                            else if (category.Type == NotificationType.Mail)
                                MailSender(session, msg, config, state);
                            else
                            {
                                log.ErrorFormat("message {0}: type {1} is not supported now", msg.NotifyID, category.Type.ToString());
                                state.ErrorCount += msg.Receivers.Count;
                            }
                        }
                        catch (Exception er)
                        {
                            log.Error("message " + msg.NotifyID.ToString() + ": ", er);
                            state.ErrorCount += (msg.Receivers.Count - (state.ErrorCount + state.SuccessCount - currentSuccessCount));
                        }
                    }
                }
                #endregion
            }
            catch
            {
                throw;
            }
            finally
            {
                log.InfoFormat("{0} messages have been sent, {1} success, {2} failed", state.ErrorCount + state.SuccessCount, state.SuccessCount, state.ErrorCount);
                UnLoad(); //发送结束后释放资源
            }

            return state;
        }

        private static void SMSSender(ISession session, Notification message, ConfigParam config, SendResult state)
        {
            object s = null;
            string errorMessage = null, mobile;
            bool error = false;
            string errorCode = string.Empty ;
            foreach (NotificationReceiver receiver in message.Receivers)
            {
               s = null;
                error = false;
                errorMessage = null;
                errorCode = "";
                //合法性检查
                mobile = receiver.PostTo;
                if (!CheckSms(ref mobile))
                {
                    log.ErrorFormat("message {0}, receiver: {1} not send: 无效的手机号码", message.NotifyID, receiver.PostTo);
                    errorMessage = "无效的手机号码";
                    error = true;
                }

                //尝试发送
                if (!error)
                    try
                    {
                        s = _smsService.SendMessage(config.SMSAccount, config.SMSPassword, message.Content, mobile);
                        error =_smsService.ParseSendResult(s,out errorCode, out errorMessage);
                        log.DebugFormat("message {0} sent, receiver: {1}, content: {2}", message.NotifyID, mobile, message.Content);
                    }
                    catch (Exception er)
                    {
                        //发送过程如果发生异常，异常信息将尝试记入数据库
                        error = true;
                        errorMessage = er.Message;
                    }

                //记录发送结果状态
                try
                {
                    session.BeginTransaction();
                    bool success = message.AfterSendDo(receiver, error ? "-99998" : "-99999", errorMessage);
                    session.Commit();
                    if (success) state.SuccessCount++;
                    else state.ErrorCount++;
                }
                catch (Exception er)
                {
                    //如果记录发送结果状态时发生异常，只能将异常信息记入日志文件
                    session.Rollback();
                    state.ErrorCount++;
                    log.ErrorFormat("message: {0}, receiver: {1}, result code: {2}, result error: {3}, exception: {4}", message.NotifyID, receiver.PostTo, error?"-99998":errorCode, errorMessage, er.Message);
                }
            }
            message.AfterSendDo();
        }
        private static void MailSender(ISession session, Notification message, ConfigParam config, SendResult state)
        {
            bool error = false;
            string errorMessage = null, code = null;
            foreach (NotificationReceiver receiver in message.Receivers)
            {
                error = false;
                errorMessage = "";
                code = "0";
                //合法性检查
                if (!CheckMail(receiver.PostTo))
                {
                    log.ErrorFormat("message {0}, receiver: {1} not send: 无效的EMail地址", message.NotifyID, receiver.PostTo);
                    errorMessage = "无效的EMail地址";
                    code = "-99999";
                    error = true;
                }

                //尝试发送
                if (!error)
                    try
                    {
                        _mailService.Subject = message.Title;
                        _mailService.HTMLBody = message.Content;
                        _mailService.ClearRecipients();
                        _mailService.AddRecipient(receiver.PostTo, receiver.UserName, null);
                        error = !_mailService.Send(config.MailServer, false);
                        code = _mailService.ErrorCode.ToString();
                        errorMessage = _mailService.ErrorMessage + " " + _mailService.Log;
                        _mailService.Close();
                        if (error)
                        {
                            log.ErrorFormat("message {0}, receiver: {1} not send: {2} - {3}", message.NotifyID, receiver.PostTo, _mailService.ErrorCode, _mailService.ErrorMessage);
                        }
                        else
                            log.DebugFormat("message {0} sent, receiver: {1}", message.NotifyID, receiver.PostTo);
                    }
                    catch (Exception er)
                    {
                        //发送过程如果发生异常，异常信息将尝试记入数据库
                        error = true;
                        errorMessage = er.Message;
                        code = "-99999";
                    }

                //记录发送结果状态
                try
                {
                    session.BeginTransaction();
                    bool success = message.AfterSendDo(receiver, code, errorMessage);
                    session.Commit();
                    if (success) state.SuccessCount++;
                    else state.ErrorCount++;
                }
                catch (Exception er)
                {
                    //如果记录发送结果状态时发生异常，只能将异常信息记入日志文件
                    session.Rollback();
                    state.ErrorCount++;
                    log.ErrorFormat("message: {0}, receiver: {1}, result code: {2}, result error: {3}, exception: {4}", message.NotifyID, receiver.ReceiverID, code, errorMessage, er.Message);
                }
            }
            message.AfterSendDo();
        }
    }
}