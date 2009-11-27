using System;
using System.Web.UI.WebControls;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Basis;

public partial class numTwo_ASSItemEdit : System.Web.UI.Page
{
    private int SKUID
    {
        get
        {
            return Cast.Int(this.txtID.Value, 0);
        }
    }
    private bool IsNew
    {
        get
        {
            return this.SKUID <= 0;
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        //返回用户列表页面的URL（包含了用户列表页面的查询条件）
        if (Request["return"] != null) this.mbar["back"].NavigateUrl = Request["return"];
        if (!IsPostBack)
        {
            this.txtID.Value = "0";
            //加载下拉框
            this.ddlItemStatus.Items.Clear();
            this.ddlItemStatus.Items.Add(new ListItem("有效", ItemSpecStatus.Enable.ToString()));
            this.ddlItemStatus.Items.Add(new ListItem("停用", ItemSpecStatus.Disable.ToString()));
            //如果是编辑
            int skuID = WebUtil.ParamInt("id", 0);
            if (skuID > 0)
            {
                this.txtID.Value = skuID.ToString(); //隐藏ID 
                WebUtil.DisableControl(this.txtItemCode);
                using (ISession session = new Session())
                {
                    ItemSpec spec = ItemSpec.Retrieve(session, skuID);
                    this.ddlItemStatus.SelectedValue = Cast.Enum<ItemSpecStatus>(spec.Status).ToString();
                    int itemID = spec.ItemID;
                    ItemMaster master = ItemMaster.Retrieve(session, itemID);
                    this.txtItemCode.Text = master.ItemCode;
                    this.txtItemName.Text = master.ItemName;
                    this.txtItemSpec.Text = master.ItemDesc;
                }
            }
        }
    }

    protected void mbar_ItemCommand(object sender, Magic.Web.UI.MagicItemEventArgs args)
    {
        if (args.CommandName == "save")
        {
            using (ISession session = new Session())
            {
                try
                {
                    string itemCode = this.txtItemCode.Text.Trim();
                    string itemStatus = this.ddlItemStatus.SelectedValue;
                    string itemName = this.txtItemName.Text.Trim();
                    string itemDesc = this.txtItemSpec.Text.Trim();
                    if (this.IsNew)
                    {
                        //新增
                        ItemMaster master = new ItemMaster();
                        master.ItemCode = itemCode;
                        //判断输入的itemCode是否重复
                        if (session.CreateEntityQuery<ItemMaster>().Where(Exp.Eq("ItemCode", itemCode)).Count() > 0)
                        {
                            WebUtil.ShowMsg(this, string.Format("辅料编号{0}已经存在", master.ItemCode), "错误");
                            return;
                        }
                        master.ItemType = ItemType.AssistantItem;
                        master.ItemName = itemName;
                        master.ItemDesc = itemDesc;
                        session.BeginTransaction();
                        master.Create(session);
                        ItemSpec spec = new ItemSpec();
                        spec.ItemID = master.ItemID;
                        spec.ItemCode = itemCode;
                        spec.Status = Cast.Enum<ItemSpecStatus>(itemStatus);
                        spec.ColorCode = " ";
                        spec.BarCode = itemCode;
                        spec.OperateTime = DateTime.Now;
                        spec.Operator = 0;// Magic.Security.SecuritySession.CurrentUser.UserId;
                        spec.PubDate = DateTime.Now;
                        spec.AvgMoveCost = 0;
                        spec.EnableCost = true;
                        spec.EnableOS = false;
                        spec.Create(session);
                        session.Commit();
                    }
                    else
                    {
                        session.BeginTransaction();
                        ItemSpec spec = ItemSpec.Retrieve(session, this.SKUID);
                        spec.Status = Cast.Enum<ItemSpecStatus>(itemStatus);
                        spec.Update(session, "Status");

                        int itemID = spec.ItemID;
                        ItemMaster master = ItemMaster.Retrieve(session, itemID);
                        master.ItemName = itemName;
                        master.ItemDesc = itemDesc;
                        master.Update(session, "ItemName", "ItemDesc");
                        session.Commit();
                    }
                }
                catch (System.Exception e)
                {
                    session.Rollback();
                    throw e;
                }
            }

            this.Response.Redirect(WebUtil.Param("return"));
        }
    }
}
