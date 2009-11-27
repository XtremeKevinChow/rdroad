using System;
using System.Web.UI.WebControls;
using Magic.Framework.ORM;


/// <summary>
/// Summary description for PageCommand
/// </summary>
public class PageCommand
{
    public PageCommand()
    {
        //
        // TODO: Add constructor logic here
        //
    }
}


/// <summary>
/// 命令执行后
/// </summary>
/// <param name="sender"></param>
/// <param name="e"></param>
public delegate void CommandExecutedHandler(object sender, ExcutedCmdEventArgs e);

public class ExcutedCmdEventArgs : CommandEventArgs
{
    public ExcutedCmdEventArgs(Session contextSession, CommandEventArgs e, bool success, Exception ex)
        : base(e)
    {
        _isSuccess = success;
        _failEx = ex;
        this._session = contextSession;
    }

    public ExcutedCmdEventArgs(Session contextSession, CommandEventArgs e, bool success)
        : this(contextSession, e, success, null)
    {
    }

    public ExcutedCmdEventArgs(CommandEventArgs e, bool success, Exception ex)
        : this(null, e, success, ex)
    {

    }

    public ExcutedCmdEventArgs(bool success)
        : this(success, null)
    {

    }

    public ExcutedCmdEventArgs(bool success, Exception ex)
        : this(new CommandEventArgs("", null), success, ex)
    {


    }


    private bool _isSuccess = false;

    private Session _session = null;

    public Session ContextSession
    {
        get { return _session; }
        set { _session = value; }
    }

    private Exception _failEx = null;
    /// <summary>
    /// 是否成功
    /// </summary>
    public bool isSuccess
    {
        get { return _isSuccess; }
    }

    /// <summary>
    /// 失败的异常
    /// </summary>
    public Exception FailedException
    {
        get { return _failEx; }
        set { _failEx = value; }
    }
}

