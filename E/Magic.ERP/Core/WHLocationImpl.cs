namespace Magic.ERP.Core
{
    using System.Collections.Generic;
    using System.Data;
    using Magic.Framework;
    using Magic.Framework.Utils;
    using Magic.Framework.ORM;

    public partial class WHLocation
    {
        public static SimpleJson GetJSON(ISession session, string locationCode)
        {
            WHLocation location = WHLocation.Retrieve(session, locationCode);
            if (location == null) return new SimpleJson().HandleError(string.Format("²Ö´¢µØ{0}²»´æÔÚ", locationCode));
            SimpleJson json = new SimpleJson()
                .Add("parent", "")
                .Add("type", "l")
                .Add("code", location.LocationCode)
                .Add("name", location.Name)
                .Add("status", (int)location.Status)
                .Add("desc", location.Text)
                .Add("addr", location.Address)
                .Add("zipcode", location.ZipCode)
                .Add("contact", location.Contact)
                .Add("phone", location.Phone)
                .Add("fax", location.FaxNumber)
                .Add("allowdelete", "1")
                .Add("allowchild", "1")
                .Add("parentType", "")
                .Add("text", location.ToString())
                .Add("comp", location.CompanyID);
            return json;
        }
        public SimpleJson GetJSON()
        {
            SimpleJson json = new SimpleJson()
                .Add("parent", "")
                .Add("type", "l")
                .Add("code", this.LocationCode)
                .Add("status", (int)this.Status)
                .Add("name", this.Name)
                .Add("desc", this.Text)
                .Add("addr", this.Address)
                .Add("zipcode", this.ZipCode)
                .Add("contact", this.Contact)
                .Add("phone", this.Phone)
                .Add("fax", this.FaxNumber)
                .Add("allowdelete", "1")
                .Add("allowchild", "1")
                .Add("parentType", "")
                .Add("text", this.ToString())
                .Add("comp", this.CompanyID);
            return json;
        }
        public override string ToString()
        {
            return this.Name + " (" + this.LocationCode + ")";
        }
        public static WHLocation Row2Entity(DataRow row)
        {
            if (row == null) return null;
            WHLocation location = new WHLocation();
            location.LocationCode = Cast.String(row["LocationCode"]);
            location.CompanyID = Cast.Int(row["CompanyID"], -1);
            location.Name = Cast.String(row["Name"]);
            location.Status = Cast.Enum<WHStatus>(row["Status"], WHStatus.Disable);
            location.Address = Cast.String(row["Address"]);
            location.ZipCode = Cast.String(row["ZipCode"]);
            location.Contact = Cast.String(row["Contact"]);
            location.Phone = Cast.String(row["Phone"]);
            location.FaxNumber = Cast.String(row["FaxNumber"]);
            return location;
        }
        public static IList<WHLocation> EffectiveList(ISession session)
        {
            return session.CreateEntityQuery<WHLocation>()
                    .Where(Magic.Framework.ORM.Query.Exp.Eq("Status", WHStatus.Enable))
                    .OrderBy("LocationCode")
                    .List<WHLocation>();
        }
    }
}