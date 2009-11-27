using System;
using System.Web.UI.WebControls;
using Magic.Framework.ORM;

public enum Mode : short
{
    /// <summary>
    /// 新增
    /// </summary>
    New = 0,
    /// <summary>
    /// 编辑已有的
    /// </summary>
    Edit = 2,
    /// <summary>
    /// 删除
    /// </summary>
    Delete = 4,
    /// <summary>
    /// 浏览
    /// </summary>
    View = 8,    
    /// <summary>
    /// 新增子项
    /// </summary>
    AddChild = 10,
    /// <summary>
    /// 未定义
    /// </summary>
    Undefined = -1

}

/// <summary>
/// Summary description for EditPage
/// </summary>
public class EditPage<TModel> : BasePage
{
    public EditPage()
        : base()
    {
        this.Init += new EventHandler(EditPage_Init);
        this.Load += new EventHandler(EditPage_Load);
    }

    /// <summary>
    /// Model的主键
    /// </summary>
    private string _identifier = null;
    protected TModel ModelEntity = default(TModel);
    protected Mode ViewMode = Mode.Undefined;



    void EditPage_Load(object sender, EventArgs e)
    {     
         if (!IsPostBack && this.ViewMode == Mode.Edit)
        {
            //第一次请求,绑定对象到控件
            Model2View();
        }
    }
    /// <summary>
    /// 页面Init
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    void EditPage_Init(object sender, EventArgs e)
    {
        if (Request["id"] != null) _identifier = Request["id"];

        if (Request["mode"] != null)
        {
            this.ViewMode = (Mode)Enum.Parse(typeof(Mode), Request["mode"], true);
        }

        if (this.ViewMode == Mode.Edit || this.ViewMode == Mode.Delete)
        {
            RetrieveEntity();
        }
    }

    protected virtual void Model2View()
    {
       
    }

    protected virtual void View2Model()
    {
      
    }

    /// <summary>
    /// 保存Model的命令处理
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    protected virtual void SaveModelCommand(object sender, CommandEventArgs e)
    {
        Trace.Write("SaveModelCommad");

        if (this.ViewMode != Mode.New && ModelEntity == null)
        {
            RetrieveEntity();
        }
        //从控件中获取Model的值
        View2Model();

        bool success = false;

        using (Session session = new Session())
        {

            try
            {
                if (this.ViewMode == Mode.New)
                    success = EntityManager.Create(session, ModelEntity);
                else
                    success = EntityManager.Update(session, ModelEntity);

                CommandExecutedHandler saveSuccess = this.Events[objAfterSaveModelEventStub] as CommandExecutedHandler;
                if (saveSuccess != null)
                    saveSuccess(sender, new ExcutedCmdEventArgs(session,e,success));
            }
            catch (Exception ex)
            {
                CommandExecutedHandler saveFail = this.Events[objAfterSaveModelEventStub] as CommandExecutedHandler;
                if (saveFail != null)
                {
                    saveFail(sender, new ExcutedCmdEventArgs(session,e,false, ex));
                }
            }
        }

    }

    private void RetrieveEntity()
    {
        Trace.Write("EditPage_RetrieveEntity");

        ModelEntity = default(TModel);

        using (Session session = new Session())
        {
            if (!string.IsNullOrEmpty(_identifier))
            {
                ModelEntity = EntityManager.Retrieve<TModel>(session, _identifier);
            }
        }

        if (ModelEntity == null)
        {
            this.ViewMode = Mode.New;
            ModelEntity = Activator.CreateInstance<TModel>();
        }

    }

    private object objAfterSaveModelEventStub = new object();
    /// <summary>
    /// 保存模型成功后执行
    /// </summary>
    public event CommandExecutedHandler AfterSaveModel
    {
        add
        {
            this.Events.AddHandler(objAfterSaveModelEventStub, value);
        }
        remove
        {
            this.Events.RemoveHandler(objAfterSaveModelEventStub, value);
        }
    }


}
