using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Framework.ORM;
using Magic.Basis;

public partial class example_Default : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        using (ISession session = new Session())
        {
            ItemMaster master = ItemMaster.Retrieve(session, "101001");
            this.Response.Write(master.ItemName);
        }
    }
}
