<%@ Control Language="C#" ClassName="FunctionTitle" %>
<%@ Import Namespace="System.ComponentModel" %>
<script runat="server">
    private string _pageTitle = "";
    private string _extInfo = "";
    protected void Page_Load()
    {
        if (this.Page != null && string.IsNullOrEmpty(_pageTitle))
            _pageTitle = Page.Title;
    }

    [Description("功能名称")]
    [Browsable(true)]
    public string PageTitle
    {
        get { return _pageTitle; }
        set { _pageTitle = value; }
    }

    [Description("附加信息")]
    [Browsable(true)]
    public string ExtInfo
    {
        get { return _extInfo; }
        set { _extInfo = value; }
    }
</script>
<div style="height:16px;min-width:800px;_width:800px;">
    <div style="font-size:12px;font-weight:600;color:Blue;float:left;">&nbsp;&nbsp;&nbsp;<%= PageTitle %></div>
    <div style="float:left;font-size:12px;" class="tips">&nbsp;&nbsp;&nbsp;<%= ExtInfo %></div>
</div>