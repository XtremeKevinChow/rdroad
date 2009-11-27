namespace Magic.ERP.Core
{
    using System.Data;
    using Magic.Framework;
    using Magic.Framework.Utils;
	using Magic.Framework.ORM;

    public partial class  WHSection
	{
        public static SimpleJson GetJSON(ISession session, string areaCode, string sectionCode)
        {
            WHSection section = WHSection.Retrieve(session, areaCode, sectionCode);
            if (section == null) return new SimpleJson().HandleError(string.Format("�洢����{0}�еĻ���{1}������", areaCode, sectionCode));
            SimpleJson json = new SimpleJson()
                .Add("parent", section.AreaCode)
                .Add("type", "s")
                .Add("code", section.SectionCode)
                .Add("status", (int)section.Status)
                .Add("desc", section.Text)
                .Add("cap", section.SectionCapacity)
                .Add("allowdelete", "1")
                .Add("allowchild", "0")
                .Add("parentType", "a")
                .Add("text", section.ToString());
            return json;
        }
        public SimpleJson GetJSON()
        {
            SimpleJson json = new SimpleJson()
                .Add("parent", this.AreaCode)
                .Add("type", "s")
                .Add("code", this.SectionCode)
                .Add("status", (int)this.Status)
                .Add("desc", this.Text)
                .Add("cap", this.SectionCapacity)
                .Add("allowdelete", "1")
                .Add("allowchild", "0")
                .Add("parentType", "a")
                .Add("text", this.ToString());
            return json;
        }
        public override string ToString()
        {
            return this.SectionCode;
        }
        public static WHSection Row2Entity(DataRow row)
        {
            if (row == null) return null;
            WHSection section = new WHSection();
            section.AreaCode = Cast.String(row["AreaCode"]);
            section.SectionCode = Cast.String(row["SectionCode"]);
            section.Text = Cast.String(row["Text"]);
            section.Status = Cast.Enum<WHStatus>(row["Status"], WHStatus.Disable);
            section.SectionCapacity = Cast.Decimal(row["SectionCapacity"], 99999999M);
            return section;
        }
	}
}