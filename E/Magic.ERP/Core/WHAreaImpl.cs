namespace Magic.ERP.Core
{
    using System.Data;
    using Magic.Framework;
    using Magic.Framework.Utils;
    using Magic.Framework.ORM;

    public partial class WHArea
    {
        public static SimpleJson GetJSON(ISession session, string areaCode)
        {
            WHArea area = WHArea.Retrieve(session, areaCode);
            if (area == null) return new SimpleJson().HandleError(string.Format("存储区域{0}不存在", areaCode));
            SimpleJson json = new SimpleJson()
                .Add("type", "a")
                .Add("code", area.AreaCode)
                .Add("status", (int)area.Status)
                .Add("name", area.Name)
                .Add("desc", area.Text)
                .Add("cap", area.AreaCapacity)
                .Add("hassec", area.HasSection)
                .Add("isqc", area.IsQC)
                .Add("isscrap", area.IsScrap)
                .Add("allowdelete", area.AllowDelete)
                .Add("allowchild", area.AllowChild)
                .Add("text", area.ToString());
            if (!string.IsNullOrEmpty(area.ParentArea) && area.ParentArea.Trim().Length > 0)
                json.Add("parentType", "a").Add("parent", area.ParentArea.Trim());
            else
                json.Add("parentType", "l").Add("parent", area.LocationCode.Trim());
            return json;
        }
        public SimpleJson GetJSON()
        {
            SimpleJson json = new SimpleJson()
                .Add("type", "a")
                .Add("code", this.AreaCode)
                .Add("status", (int)this.Status)
                .Add("name", this.Name)
                .Add("desc", this.Text)
                .Add("cap", this.AreaCapacity)
                .Add("hassec", this.HasSection)
                .Add("isqc", this.IsQC)
                .Add("isscrap", this.IsScrap)
                .Add("allowdelete", this.AllowDelete)
                .Add("allowchild", this.AllowChild)
                .Add("text", this.ToString());
            if (!string.IsNullOrEmpty(this.ParentArea) && this.ParentArea.Trim().Length > 0)
                json.Add("parentType", "a").Add("parent", this.ParentArea.Trim());
            else
                json.Add("parentType", "l").Add("parent", this.LocationCode.Trim());
            return json;
        }
        public override string ToString()
        {
            return this.Name + " (" + this.AreaCode + ")";
        }
        public static WHArea Row2Entity(DataRow row)
        {
            if (row == null) return null;
            WHArea area = new WHArea();
            area.AreaCode = Cast.String(row["AreaCode"]);
            area.LocationCode = Cast.String(row["LocationCode"]);
            area.ParentArea = Cast.String(row["ParentArea"]);
            area.Name = Cast.String(row["Name"]);
            area.Text = Cast.String(row["Text"]);
            area.Status = Cast.Enum<WHStatus>(row["Status"], WHStatus.Disable);
            area.AreaCapacity = Cast.Decimal(row["AreaCapacity"], 99999999M);
            area.HasSection = Cast.Bool(row["HasSection"], true);
            area.IsQC = Cast.Bool(row["IsQC"], false);
            area.IsScrap = Cast.Bool(row["IsScrap"], false);
            area.IsTransArea = Cast.Bool(row["IsTransArea"], false);
            area.IsReservedArea = Cast.Bool(row["IsReservedArea"], false);
            area.AllowDelete = Cast.Bool(row["AllowDelete"], false);
            area.AllowChild = Cast.Bool(row["AllowChild"], false);
            area.UseFixCost = Cast.Bool(row["UseFixCost"], false);
            area.CostFixValue = Cast.Decimal(row["CostFixValue"], 0M);
            area.CostTransRate = Cast.Decimal(row["CostTransRate"], 1M);
            area.FixedComsumeValue = Cast.Decimal(row["FixedComsumeValue"], 0M);
            return area;
        }
        public static SimpleJson GetWHInfo(ISession session, string area, string section)
        {
            if (string.IsNullOrEmpty(area) || area.Trim().Length <= 0)
                return new SimpleJson().HandleError("您没有选择库位");

            string a = area.Trim().ToUpper();
            string sec = string.IsNullOrEmpty(section) ? "" : section.Trim().ToUpper();
            SimpleJson json = new SimpleJson().Add("area", area).Add("section", sec);

            if (sec.Length <= 0)
            {
                //加载库位信息
                WHArea whArea = WHArea.Retrieve(session, a);
                if (whArea == null) return json.HandleError("库位" + a + "不存在");
                json.Add("capacity", Cast.Int(whArea.AreaCapacity));
                int stoQty = Cast.Int(session.CreateObjectQuery(@"select sum(StockQty) from StockDetail where AreaCode=?area group by AreaCode")
                    .Attach(typeof(StockDetail))
                    .SetValue("?area", a, "AreaCode")
                    .Scalar());
                json.Add("stored", stoQty);
            }
            else
            {
                //加载货架信息
                WHSection whSection = WHSection.Retrieve(session, a, sec);
                if (whSection == null) return json.HandleError("库位" + a + "中不存在货架号" + sec);
                json.Add("capacity", Cast.Int(whSection.SectionCapacity));
                int stoQty = Cast.Int(session.CreateObjectQuery(@"
select sum(StockQty) from StockDetail 
where AreaCode=?area and SectionCode=?section
group by AreaCode")
                    .Attach(typeof(StockDetail))
                    .SetValue("?area", a, "AreaCode")
                    .SetValue("?section", sec, "SectionCode")
                    .Scalar());
                json.Add("stored", stoQty);
            }
            return json;
        }
    }
}