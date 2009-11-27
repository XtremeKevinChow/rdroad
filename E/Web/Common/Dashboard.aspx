<%@ Page Language="C#" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Framework.Data" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.OQL" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Security" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Register assembly="Magic.Web.UI" namespace="Magic.Web.UI" tagprefix="mwu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script runat="server">
    int _currentDashpageId = -1;
    DashPage _currentPage = null;
    IList<Dashlet> _dashlets = null;
    IList<DashPage> _userDashPages = null;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                LoadDashPage(session);
                LoadDashlet(session);
            }
        }
        else
        {
            _currentDashpageId = Cast.Int(this.hidCurrentPageId.Value);
        }
    }

    private void LoadDashPage(ISession session)
    {
        _userDashPages = DashPage.FindDashPageByUser(session, SecuritySession.CurrentUser.UserId);
        if (_userDashPages != null && _userDashPages.Count > 0)
        {
            if (_currentDashpageId == -1)
            {
                _currentPage = _userDashPages[0];

                _currentDashpageId = _currentPage.DashpageId;
                this.hidCurrentPageId.Value = _currentDashpageId.ToString();
            }
            else
            {
                foreach (DashPage page in _userDashPages)
                {
                    if (page.DashpageId == _currentDashpageId)
                    {
                        _currentPage = page;
                    }
                }
            }
            this.rptDashPage.DataSource = _userDashPages;
            this.rptDashPage.DataBind();
        }
    }
    protected void LoadDashlet(ISession session)
    {
        if (_currentDashpageId > -1)
        {
            if (_currentPage == null) _currentPage = DashPage.Retrieve(session, _currentDashpageId);
           if (_currentPage == null) return;
           _dashlets = DashPage.FindDashletByUserAndPage(session, SecuritySession.CurrentUser.UserId, _currentDashpageId);           
        }
    }

    protected void btnLoadDashlet_Click(object sender, EventArgs e)
    {
        using (ISession session = new Session())
        {
            LoadDashlet(session);
        }
    }

    protected void btnDeleteDashlet_Click(object sender, EventArgs e)
    {
        int dashletId = Cast.Int( this.txtDeleteDashletId.Value, -1);
        if (dashletId > -1)
        {
            using (ISession session = new Session())
            {
                UserDashlet.Delete(session, SecuritySession.CurrentUser.UserId, _currentDashpageId, dashletId);
                LoadDashlet(session);
            }
        }
    }

    protected void btnAddDashPage_Click(object sender, EventArgs e)
    {
        string addName = txtAddPageName.Value.Trim();
        if (string.IsNullOrEmpty(addName)) addName = "未命名Page";
        DashPageLayout addLayout = Cast.Enum<DashPageLayout>(txtAddPageLayout.Value, DashPageLayout.OneColumn);

        DashPage page = new DashPage();
        page.CreateBy = SecuritySession.CurrentUser.UserId;
        page.CreateTime = DateTime.Now;
        page.Description = this.txtAddPageDesc.Value;
        page.HelpLink = "";
        page.Layout = addLayout;
        page.Status = DashStatus.Active;
        page.Title = addName;
        page.Type = DashPageType.Custom;

        UserDashpage userdp = new UserDashpage();
        userdp.UserId = SecuritySession.CurrentUser.UserId;
        
        using(ISession session = new Session())
        {
            page.Create(session);
            userdp.DashpageId = page.DashpageId;
            userdp.Create(session);

            LoadDashPage(session);
        }
    }

    protected void btnAddDashlet_Click(object sender, EventArgs e)
    {
        int dashlet = -1;
        int.TryParse(this.txtAddDashletId.Value,out dashlet);
        if( _currentDashpageId>-1 && dashlet>-1)
        {
            UserDashlet ud = new UserDashlet();
            ud.DashletId = dashlet;
            ud.DashpageId = _currentDashpageId;
            ud.Status = DashStatus.Active;
            ud.UserId = SecuritySession.CurrentUser.UserId;
            using (ISession session = new Session())
            {
                ud.Create(session);
                LoadDashlet(session);
            }
        }
    }
</script>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <link href="../CSS/common.css" rel="stylesheet" type="text/css" />
        <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
     <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
         .dashPageTab
         {
         	margin:0;
         	padding:0;
         	list-style:none;
         	width:auto;   
         	border-bottom:groove thin;      	
         	width:100%;
         }
          .dashPageTab li
          {          	
          	float:left;
          	border-width:thin;
          	border-style:groove;          	
          }
          .dashPageTab span
          {
          	  float:left;
          	  vertical-align:bottom;
          }
           .dashPageTab a
           {
           	display:block;
           	padding:0 2em;
           	line-height:1.5em;
           	text-decoration:none;               	
           	color:#656565;  	
           }
           .dashPageTab a:hover
           {
           	    color:#333;
           	    text-decoration:none;
           }
           .dashPageTab a:visited
           {
           	    color:#656565;
           	    text-decoration:none;
           }
           .dashPageTab .selected
           {
           	 background-color:#DDD;
           	 border-bottom:none;
           	   margin-bottom:2px;
           }
           .dashPageTab .selected a
           {
           	     background-position: right bottom;
           	     color:#fff;
           	     cursor:default;
           	     text-decoration:none;           	   
           	     
           }
           .dashPageTable
           {
           	 border-width:thin;
          	 border-style:groove;
          	 border-top:none;
           }
           .dashPageColumn
           {
           	    vertical-align:top;
           }
     </style>
          <script src="../script/jquery.js" type="text/javascript"></script>    
     <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/jquery.contextmenu.r2.js" type="text/javascript" ></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/QueryPage.js" type="text/javascript" ></script>
    <script src="../script/selector.js" type="text/javascript"></script>
    <script  src="../script/jquery.bgiframe.js" type="text/javascript"></script>
    
    <script type="text/javascript">
    $(document).ready(function(){
        var selPage = $("#hidCurrentPageId").val();
            $("#tabList li").each(function(i){
                if($(this).attr("id") ==("page_"+selPage))
                {
                   Dashboard_pageSelect(this);
                }
            }
            );   
            
            
            //新增Dashpage
             $("#addDashPage").bind("click", function(){  
               
                var q = new query("AddDashPage"); //实现了AddDashPage选择页面的服务器端控件名字
                q.fnPopup({
                    top:50, left:50, width:400, height:150, //选择框的位置信息
                    mode: "s", //s单选，m多选
                    title:"新增DashPage",
                    data: {
                        selected:[], //当前选择的                       
                        pi: 1 //当前页数
                    },
                    on_select: function(r) { //点击确认按钮的回调函数，参数r为返回结果（数组）
                      
                        $(r).each(function(i, e){ //返回了Dashlet的选择的数据
						$("#txtAddPageLayout").val(e.layout);
						$("#txtAddPageName").val(e.name);
						$("#txtAddPageDesc").val(e.desc);
                        });
                        if($.trim($("#txtAddPageName").val()) == "")
                        {
                            ShowMsg("名称不能为空","提示",true);
                        }
                        else
                        {
                            document.getElementById("btnAddDashPage").click();
                        }
                    }
                });
            });         
    });
    
    function FunctionNavigate(url,funcId,sender)
    {        
      window.location.href = url;
    }     
    
    function Dashboard_togglePage(pageId)
    {
         var selPage = $("#hidCurrentPageId").val();
         if(pageId == selPage) return;
        Dashlet_unselectPage( $("#page_"+selPage));
          $("#hidCurrentPageId").val(pageId);
         Dashboard_pageSelect( $("#page_" + pageId));
          
          document.getElementById("btnLoadDashlet").click();
    }
  
  function Dashlet_Refresh(dashletId)
  {
    
  }
    
    function Dashboard_pageSelect(el)
    {
         $(el).addClass("selected");
         $("#span_adv",$(el)).html("<img id='imgAddDashlet' src='../images/Dashboard_Advance.gif' align='absmiddle' border='0'/>");
         $("#imgAddDashlet",$(el)).bind("click",Dashpage_addDashlet);
    }
    
    function Dashlet_unselectPage(el)
    {
        $(el).toggleClass("selected");
         $("#span_adv",el).html("");
    }
    
    function Dashpage_addDashlet()
    {        		
               
                var q = new query("Dashlet"); //实现了Dashlet选择页面的服务器端控件名字
                q.fnPopup({
                    top:50, left:50, width:400, height:250, //选择框的位置信息
                    mode: "s", //s单选，m多选
                    title:"选择Dashlet",
                    data: {
                        selected: [ {dashletId: ""} ], //当前选择的
                        category:"",
                        title:"",
                        exclusive:true,
                        pi: 1 //当前页数
                    },
                    on_select: function(r) { //点击确认按钮的回调函数，参数r为返回结果（数组）
                        var str="";
                        $(r).each(function(i, e){ //返回了Dashlet的选择的数据
						$("#txtAddDashletId").val(e.dashletId);
						//$("#controlId").val(e.category);
						//$("#controlId").val(e.title);
						//$("#controlId").val(e.icon);
						//$("#controlId").val(e.description);
                        });
                        if($.trim($("#txtAddDashletId").val()) != "")
                        {
                                document.getElementById("btnAddDashlet").click();
                        }
                        
                    }
                });           
    }
    function Dashlet_Delete(dashletId)
    {
         $("#txtDeleteDashletId").val(dashletId);
         document.getElementById("btnDeleteDashlet").click();
    }
    </script>

</head>
<body>
<form runat="server">
<div>
    <ul id="tabList" class="dashPageTab">       
       <asp:Repeater runat="server" ID="rptDashPage">
        <ItemTemplate>
            <li id='page_<%# Eval("DashpageId") %>' >
                 <span ><a  href="javascript:Dashboard_togglePage('<%# Eval("DashpageId") %>');"><%# Eval("Title") %>  </a>  </span>
                <span id='span_adv' ></span>
            </li>
         </ItemTemplate>
       </asp:Repeater>  
       <li id="addPage" style="border:none">        
          <a href="#" id="addDashPage" style=" color:Black">+新增</a>
       </li>     
    </ul>
    <asp:Button ID="btnLoadDashlet" runat="server" onclick="btnLoadDashlet_Click" style="width:0px; height:0px; display:none;" />
    <asp:Button ID="btnAddDashPage" runat="server" onclick="btnAddDashPage_Click" style="width:0px; height:0px; display:none;" />
    <asp:Button ID="btnAddDashlet" runat="server" onclick="btnAddDashlet_Click" style="width:0px; height:0px; display:none;"  />
    <asp:Button ID="btnDeleteDashlet" runat="server" onclick="btnDeleteDashlet_Click" style="width:0px; height:0px; display:none;"  />
    <input id="hidCurrentPageId" runat="server" type="hidden" />
    <input id="txtAddPageName" type="hidden" runat="server" />
   <input id="txtAddPageLayout" type="hidden" runat="server" />
   <input id="txtAddPageDesc" type="hidden" runat="server" />
    <input id="txtAddDashletId" runat="server" type="hidden" />
    <input id="txtDeleteDashletId" runat="server" type="hidden" />
            
        <%
            if (_currentPage != null  && _dashlets !=null )
            {
                int cols = (int)_currentPage.Layout;
                int count = _dashlets.Count;
                string[] colWidth = new string[cols];
                switch (_currentPage.Layout)
                {
                    case DashPageLayout.OneColumn:
                        colWidth[0] = "100%";
                        break;
                    case DashPageLayout.TwoColumn:
                        colWidth[0] =colWidth[1]= "50%";
                        break;
                    case DashPageLayout.ThreeColumn:
                        colWidth[0] = colWidth[1] = "33%";
                        colWidth[2] = "34%";
                        break;
                }
                int rows = count / cols;
                int j=0;
                %> 
           <table id="tbDashletLayout" class="dashPageTable" width="100%">
                <tr style="width:100%; height:100%">
                <td width='<%=colWidth[j++]%>'>
                <ul class="dashPageColumn">
               <%
                for (int i = 0; i <  count; i++)
                {
                  
                   Dashlet dashlet = _dashlets[i];
                   
                    %>
                        <li style="list-style-type:none">
                            <table width="100%">
                        <tr>
                            <td width="99%">
                                <span><% =dashlet.Title  %></span>
                            </td>
                            <td nowrap="nowrap" width="1%">
                                <div style="width: 100%; text-align: right;">                                    
                                    <a href="#" onclick='javascript:Dashlet_Refresh(<%=dashlet.DashletId %>)'  >
                                      <img title="Refresh Dashlet" alt="Refresh Dashlet" src="../images/dashboard_refresh.gif"
                                                    align="absmiddle" border="0" height="13" width="13"></a> 
                                    <a href="#" onclick='javascript:Dashlet_Delete(<%=dashlet.DashletId %>)' >
                                        <img title="Delete Dashlet" alt="Delete Dashlet" src="../images/dashboard_close.gif"
                                            align="absmiddle" border="0" height="13" width="13">
                                    </a>                              
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                            <table width="100%"  border="0">
                                <tr>
                                    <td>
                                        <% = DashboardUtil.RenderDashlet(dashlet) %>
                                    </td>
                                </tr>
                            </table>
                            </td>
                        </tr>
                    </table>
                        </li>           
                    <%                   
                        if ( i>0 && i%cols==0)
                        {
                            %>
            </ul>
           </td>
           <td width='<%= colWidth[0] %>'>
           <ul class="dashPageColumn">
                            <%
                        }
                }
                %>
                   </ul></td>                
             </tr>
    </table>
                <%
            }
       %>
</div>

</form>
</body>
</html>
