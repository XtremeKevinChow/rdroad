消息通知组件，目前支持邮件、短信消息
Richie, 2008-09-23, v1.0.1

1. 
1.1 组成
Magic.Notification.dll
Job服务

1.2 依赖的组件
antlr.runtime.dll v2.7.7.3
StringTemplate.dll v3.0.1.6846
Magic.Framework.Common.dll
Magic.Framework.Data.dll
Magic.Framework.OQL.dll
Magic.Framework.ORM.dll

2. 使用
ERP: example/NotificationTest.aspx
2.1 创建消息通知:
using (ISession session = new Session())
{
    session.BeginTransaction();
    try
    {
        //创建Notification示例
        //Create参数说明:
        //triggerId: 触发Notification的对象标识，例如会员注册使用会员号、订单确认使用订单号或订单ID等
        //  目的是以后可以查到该会员注册或订单确认时到底有没有发送Notification，发送处理结果是什么状况
        //categoryId: Notification的类别，类别将指示消息是邮件还是短信消息、系统是否启用了这个消息，消息的模板文件，邮件的标题等信息
        Notification n = Notification.Create(session, "08092300912", 1);
        n.AddReceiver("Richie.Liu", "刘志斌", "richie.liu@thoughtsoft.com.cn")
            //AddSingleParam: 添加简单类型的参数值
            .AddSingleParam("MemberName", "刘志斌")
            .AddSingleParam("OrderNumber", "08092300912")
            .AddSingleParam("TotalAmt", 453.2M)
            .AddSingleParam("PayableAmt", 260)
            //AddListParam: 添加列表类型的参数值，例如订单明细记录
            .AddListParam("Lines")
                    //开始添加订单的第1个明细
                    //AddListParam返回的是新添加的列表参数NotificationParam对象，因此随后的AddSingleParam方法添加的参数都属于第1个明细
                    .AddSingleParam("Product", "丝路花语丝滑苏绣抹胸")
                    .AddSingleParam("Qty", 2)
                    .AddSingleParam("Price", 99)
                //NewSerial方法表示开始添加订单的第2个明细，随后的AddSingleParam添加的参数都属于第2个明细
                .NewSerial()
                    .AddSingleParam("Product", "梦幻之春晶采魔术文胸")
                    .AddSingleParam("Qty", 1)
                    .AddSingleParam("Price", 258);
        session.Commit();
        this.txtID.Text = n.NotifyID.ToString();
    }
    catch
    {
        session.Rollback();
        throw;
    }
}

2.2 Job发送消息通知
using (ISession session = new Session())
{
    //发送方法（由JOB服务调用）
    Notification n = Notification.ToSendList(session, new DateTime(2008,1,1))[0];
    n.ReadyToSend();
    this.divContainer.InnerHtml = n.Content;
    //n.Title 邮件标题
    //n.Receiver 接收者列表
    //具体的发送短信、邮件，依赖于使用的短信接口、邮件发送方式
}