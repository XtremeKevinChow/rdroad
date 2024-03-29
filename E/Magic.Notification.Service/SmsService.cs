﻿//------------------------------------------------------------------------------
// <autogenerated>
//     This code was generated by a tool.
//     Runtime Version: 1.1.4322.2032
//
//     Changes to this file may cause incorrect behavior and will be lost if 
//     the code is regenerated.
// </autogenerated>
//------------------------------------------------------------------------------

// 
// This source code was auto-generated by wsdl, Version=1.1.4322.2032.
// 
using System.Diagnostics;
using System.Xml.Serialization;
using System;
using System.Web.Services.Protocols;
using System.ComponentModel;
using System.Web.Services;


/// <remarks/>
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Web.Services.WebServiceBindingAttribute(Name="SmsServiceSoap", Namespace="http://tempuri.org/")]
public class SmsService : System.Web.Services.Protocols.SoapHttpClientProtocol {
    
    /// <remarks/>
    public SmsService() {
        this.Url = "http://webservice.cheerlong.com/SmsService/SmsService.asmx";
    }
    
    /// <remarks/>
    [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://tempuri.org/SendMessage",  Use=System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle=System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
    public SendState SendMessage(string UserId, string Password, string Msg, string Destnumbers) {
        object[] results = this.Invoke("SendMessage", new object[] {
                    UserId,
                    Password,
                    Msg,
                    Destnumbers});
        return ((SendState)(results[0]));
    }
    
    /// <remarks/>
    public System.IAsyncResult BeginSendMessage(string UserId, string Password, string Msg, string Destnumbers, System.AsyncCallback callback, object asyncState) {
        return this.BeginInvoke("SendMessage", new object[] {
                    UserId,
                    Password,
                    Msg,
                    Destnumbers}, callback, asyncState);
    }
    
    /// <remarks/>
    public SendState EndSendMessage(System.IAsyncResult asyncResult) {
        object[] results = this.EndInvoke(asyncResult);
        return ((SendState)(results[0]));
    }
    
    /// <remarks/>
    [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://tempuri.org/SendMessageWithSub",  Use=System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle=System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
    public SendState SendMessageWithSub(string UserId, string SubId, string Password, string Msg, string Destnumbers) {
        object[] results = this.Invoke("SendMessageWithSub", new object[] {
                    UserId,
                    SubId,
                    Password,
                    Msg,
                    Destnumbers});
        return ((SendState)(results[0]));
    }
    
    /// <remarks/>
    public System.IAsyncResult BeginSendMessageWithSub(string UserId, string SubId, string Password, string Msg, string Destnumbers, System.AsyncCallback callback, object asyncState) {
        return this.BeginInvoke("SendMessageWithSub", new object[] {
                    UserId,
                    SubId,
                    Password,
                    Msg,
                    Destnumbers}, callback, asyncState);
    }
    
    /// <remarks/>
    public SendState EndSendMessageWithSub(System.IAsyncResult asyncResult) {
        object[] results = this.EndInvoke(asyncResult);
        return ((SendState)(results[0]));
    }
    
    /// <remarks/>
    [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://tempuri.org/QueryUserState",  Use=System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle=System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
    public UserState QueryUserState(string UserId, string Password) {
        object[] results = this.Invoke("QueryUserState", new object[] {
                    UserId,
                    Password});
        return ((UserState)(results[0]));
    }
    
    /// <remarks/>
    public System.IAsyncResult BeginQueryUserState(string UserId, string Password, System.AsyncCallback callback, object asyncState) {
        return this.BeginInvoke("QueryUserState", new object[] {
                    UserId,
                    Password}, callback, asyncState);
    }
    
    /// <remarks/>
    public UserState EndQueryUserState(System.IAsyncResult asyncResult) {
        object[] results = this.EndInvoke(asyncResult);
        return ((UserState)(results[0]));
    }
    
    /// <remarks/>
    [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://tempuri.org/QueryFailedMessage",  Use=System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle=System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
    public MessageStateResponse QueryFailedMessage(string UserId, string Password, int groupId) {
        object[] results = this.Invoke("QueryFailedMessage", new object[] {
                    UserId,
                    Password,
                    groupId});
        return ((MessageStateResponse)(results[0]));
    }
    
    /// <remarks/>
    public System.IAsyncResult BeginQueryFailedMessage(string UserId, string Password, int groupId, System.AsyncCallback callback, object asyncState) {
        return this.BeginInvoke("QueryFailedMessage", new object[] {
                    UserId,
                    Password,
                    groupId}, callback, asyncState);
    }
    
    /// <remarks/>
    public MessageStateResponse EndQueryFailedMessage(System.IAsyncResult asyncResult) {
        object[] results = this.EndInvoke(asyncResult);
        return ((MessageStateResponse)(results[0]));
    }
    
    /// <remarks/>
    [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://tempuri.org/QueryMessageState",  Use=System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle=System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
    public MessageStateResponse QueryMessageState(string UserId, string Password, int groupId) {
        object[] results = this.Invoke("QueryMessageState", new object[] {
                    UserId,
                    Password,
                    groupId});
        return ((MessageStateResponse)(results[0]));
    }
    
    /// <remarks/>
    public System.IAsyncResult BeginQueryMessageState(string UserId, string Password, int groupId, System.AsyncCallback callback, object asyncState) {
        return this.BeginInvoke("QueryMessageState", new object[] {
                    UserId,
                    Password,
                    groupId}, callback, asyncState);
    }
    
    /// <remarks/>
    public MessageStateResponse EndQueryMessageState(System.IAsyncResult asyncResult) {
        object[] results = this.EndInvoke(asyncResult);
        return ((MessageStateResponse)(results[0]));
    }
}

/// <remarks/>
[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://tempuri.org/")]
public class SendState {
    
    /// <remarks/>
    public int state;
    
    /// <remarks/>
    public string WrongNumbers;
    
    /// <remarks/>
    public string YDGroupId;
    
    /// <remarks/>
    public string LTGroupId;
}

/// <remarks/>
[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://tempuri.org/")]
public class MessageState {
    
    /// <remarks/>
    public string DestNumber;
    
    /// <remarks/>
    public int state;
    
    /// <remarks/>
    public string SendTime;
}

/// <remarks/>
[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://tempuri.org/")]
public class MessageStateResponse {
    
    /// <remarks/>
    public int state;
    
    /// <remarks/>
    public string subuserid;
    
    /// <remarks/>
    public int totalCount;
    
    /// <remarks/>
    public string content;
    
    /// <remarks/>
    public MessageState[] MsgState;
}

/// <remarks/>
[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://tempuri.org/")]
public class UserState {
    
    /// <remarks/>
    public int state;
    
    /// <remarks/>
    public int total;
    
    /// <remarks/>
    public int dailyTotal;
    
    /// <remarks/>
    public int remained;
    
    /// <remarks/>
    public int todayRemained;
}
