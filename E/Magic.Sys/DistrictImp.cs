using Magic.Framework;
using Magic.Framework.ORM;

namespace Magic.Sys
{
    public partial class District
    {
        public static SimpleJson GetJSON(ISession session, int id)
        {
            District district = District.Retrieve(session, id);
            if (district == null) return new SimpleJson().HandleError(string.Format("区域{0}不存在", id));
            SimpleJson json = new SimpleJson()
                .Add("parent", district.CityId)
                .Add("type", "d")
                .Add("id", district.DistrictId)
                .Add("name", district.Name)
                .Add("zip", district.ZipCode)
                .Add("ship", district.Door2Door)
                .Add("desc", district);
            return json;
        }
        public SimpleJson GetJSON()
        {
            SimpleJson json = new SimpleJson()
                 .Add("parent", this.CityId)
                 .Add("type", "d")
                 .Add("id", this.DistrictId)
                 .Add("name", this.Name)
                 .Add("zip", this.ZipCode)
                 .Add("ship", this.Door2Door)
                 .Add("desc", this);
            return json;
        }
        public override string ToString()
        {
            return this.Name + " (" + this.ZipCode+")";
        }
    }
}